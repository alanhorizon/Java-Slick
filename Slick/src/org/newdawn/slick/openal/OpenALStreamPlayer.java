package org.newdawn.slick.openal;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.OpenALException;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

/**
 * A generic tool to work on a supplied stream, pulling out PCM data and buffered it to OpenAL
 * as required.
 * 
 * @author Kevin Glass
 */
public class OpenALStreamPlayer {
	/** The number of buffers to maintain */
	public static final int BUFFER_COUNT = 3;
	/** The size of the sections to stream from the stream */
	private static final int sectionSize = 4096 * 10;
	
	/** The buffer read from the data stream */
	private byte[] buffer = new byte[sectionSize];
	/** Holds the OpenAL buffer names */
	private IntBuffer bufferNames;
	/** The byte buffer passed to OpenAL containing the section */
	private ByteBuffer bufferData = BufferUtils.createByteBuffer(sectionSize);
	/** The buffer holding the names of the OpenAL buffer thats been fully played back */
	private IntBuffer unqueued = BufferUtils.createIntBuffer(1);
	/** The source we're playing back on */
    private int source;
	/** The number of buffers remaining */
    private int remainingBufferCount;
	/** True if we should loop the track */
	private boolean loop;
	/** True if we've completed play back */
	private boolean done = true;
	/** The stream we're currently reading from */
	private AudioInputStream audio;
	/** The source of the data */
	private String ref;
	/** The source of the data */
	private URL url;
	
	/**
	 * Create a new player to work on an audio stream
	 * 
	 * @param source The source on which we'll play the audio
	 * @param ref A reference to the audio file to stream
	 */
	public OpenALStreamPlayer(int source, String ref) {
		this.source = source;
		this.ref = ref;
		
		bufferNames = BufferUtils.createIntBuffer(BUFFER_COUNT);
		AL10.alGenBuffers(bufferNames);
	}

	/**
	 * Create a new player to work on an audio stream
	 * 
	 * @param source The source on which we'll play the audio
	 * @param url A reference to the audio file to stream
	 */
	public OpenALStreamPlayer(int source, URL url) {
		this.source = source;
		this.url = url;
		
		bufferNames = BufferUtils.createIntBuffer(BUFFER_COUNT);
		AL10.alGenBuffers(bufferNames);
	}
	
	/**
	 * Initialise our connection to the underlying resource
	 * 
	 * @throws IOException Indicates a failure to open the underling resource
	 */
	private void initStreams() throws IOException {
		if (audio != null) {
			audio.close();
		}
		
		if (url != null) {
			audio = new OggInputStream(url.openStream());
		} else {
			audio = new OggInputStream(ResourceLoader.getResourceAsStream(ref));
		}
	}
	
	/**
	 * Get the source of this stream
	 * 
	 * @return The name of the source of string
	 */
	public String getSource() {
		return (url == null) ? ref : url.toString();
	}
	
	/**
	 * Clean up the buffers applied to the sound source
	 */
	private void removeBuffers() {
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		int queued = AL10.alGetSourcei(source, AL10.AL_BUFFERS_QUEUED);
		
		while (queued > 0)
		{
			AL10.alSourceUnqueueBuffers(source, buffer);
			queued--;
		}
	}
	
	/**
	 * Start this stream playing
	 * 
	 * @param loop True if the stream should loop 
	 * @throws IOException Indicates a failure to read from the stream
	 */
	public void play(boolean loop) throws IOException {
		this.loop = loop;
		initStreams();
		
		done = false;

		AL10.alSourceStop(source);
		removeBuffers();
	    AL10.alSourcei(source, AL10.AL_LOOPING, AL10.AL_FALSE);
		
		remainingBufferCount = BUFFER_COUNT;
	
		for (int i=0;i<BUFFER_COUNT;i++) {
	        stream(bufferNames.get(i));
		}
		
        AL10.alSourceQueueBuffers(source, bufferNames);
		AL10.alSourcePlay(source);
	}
	
	/**
	 * Setup the playback properties
	 * 
	 * @param pitch The pitch to play back at
	 * @param gain The volume to play back at
	 */
	public void setup(float pitch, float gain) {
		AL10.alSourcef(source, AL10.AL_PITCH, pitch);
		AL10.alSourcef(source, AL10.AL_GAIN, gain); 
	}
	
	/**
	 * Check if the playback is complete. Note this will never
	 * return true if we're looping
	 * 
	 * @return True if we're looping
	 */
	public boolean done() {
		return done;
	}
	
	/**
	 * Poll the bufferNames - check if we need to fill the bufferNames with another
	 * section. 
	 * 
	 * Most of the time this should be reasonably quick
	 */
	public void update() {
		if (done) {
			return;
		}
		
		int processed = AL10.alGetSourcei(source, AL10.AL_BUFFERS_PROCESSED);
		while (processed > 0) {
			unqueued.clear();
			
			AL10.alSourceUnqueueBuffers(source, unqueued);
	        if (stream(unqueued.get(0))) {
	        	AL10.alSourceQueueBuffers(source, unqueued);
	        } else {
	        	remainingBufferCount--;
	        	if (remainingBufferCount == 0) {
	        		done = true;
	        	}
	        }
	        processed--;
		}
		
		int state = AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE);
	    
	    if (state != AL10.AL_PLAYING) {
	    	AL10.alSourcePlay(source);
	    }
	}
	
	/**
	 * Stream some data from the audio stream to the buffer indicates by the ID
	 * 
	 * @param bufferId The ID of the buffer to fill
	 * @return True if another section was available
	 */
	public boolean stream(int bufferId) {
		try {
			int count = audio.read(buffer);
			
			if (count != -1) {
				bufferData.clear();
				bufferData.put(buffer,0,count);
				bufferData.flip();

				int format = audio.getChannels() > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
				try {
					AL10.alBufferData(bufferId, format, bufferData, audio.getRate());
				} catch (OpenALException e) {
					e.printStackTrace();
					Log.error("Failed to loop buffer: "+bufferId+" "+format+" "+count+" "+audio.getRate());
					return false;
				}
				 
			} else {
				if (loop) {
					initStreams();
					stream(bufferId);
				} else {
					done = true;
					return false;
				}
			}
			
			return true;
		} catch (IOException e) {
			Log.error(e);
			return false;
		}
	}
}

