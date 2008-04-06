package org.newdawn.commet.object;

import org.newdawn.commet.space.Blob;

/**
 * Simple test to stimualte the encoder analysis
 * 
 * @author kevin
 */
public class EncoderTest {

	/**
	 * Entry point in the test
	 * 
	 * @param argv No arugments
	 * @throws ClassEncodingException Indicates a failure to build an encoder for the class
	 */
	public static void main(String[] argv) throws ClassEncodingException {
		ObjectEncoder encoder = new ObjectEncoder(Blob.class, false);
	}
}
