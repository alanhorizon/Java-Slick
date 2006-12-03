package playground;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import playground.games.GameRecord;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class GamesData {
	private HashMap logos = new HashMap();
	private HashMap thumbs = new HashMap();
	private String dataCacheLocation;
	
	public GamesData(String cache) {
		dataCacheLocation = cache;
	}
	
	public void cache(GameRecord info) {
		getThumbImage(info, false);
		getLogoImage(info, false);
	}
	
	public void update(GameRecord info) {
		getThumbImage(info, true);
		getLogoImage(info, true);
	}

	public Image getThumbImage(GameRecord info) {
		return getThumbImage(info, false);
	}
	
	public Image getThumbImage(GameRecord info, boolean update) {
		if ((thumbs.get(info) == null) || (update)) {
			File thumb = new File(dataCacheLocation+"/"+info.getID()+"/thumb.png");
			
			thumb.getParentFile().mkdirs();
			
			if (!thumb.exists() && !update) {
				try {
					URLConnection thumbURL = new URL(info.getThumbURL()).openConnection();
					thumbURL.setUseCaches(false);
					
					if ((!thumb.exists()) ||(thumbURL.getLastModified() > thumb.lastModified()) ||
						(thumbURL.getContentLength() != thumb.length())) {
						copy(thumbURL.getInputStream(), new FileOutputStream(thumb));
					}
				} catch (Exception e) {
					Log.warn("Unable to download: "+info.getThumbURL());
				}
			}
			
			try {
				thumbs.put(info, new Image("res/nothumb.png"));
				if (thumb.exists()) {
					thumbs.put(info, new Image(new FileInputStream(thumb), thumb.getAbsolutePath(), true));
				}
			} catch (Exception e) {
				Log.error(e);
			}
		}
		
		return (Image) thumbs.get(info);
	}

	public Image getLogoImage(GameRecord info) {
		return getLogoImage(info, false);
	}
	
	public Image getLogoImage(GameRecord info, boolean update) {
		if ((logos.get(info) == null) || (update)) {
			File logo = new File(dataCacheLocation+"/"+info.getID()+"/logo.png");

			logo.getParentFile().mkdirs();
			
			if (!logo.exists() && !update) {
				try {
					URLConnection logoURL = new URL(info.getLogoURL()).openConnection();
					logoURL.setUseCaches(false);
					
					if ((!logo.exists()) ||(logoURL.getLastModified() > logo.lastModified()) ||
					    (logoURL.getContentLength() != logo.length())) {
						copy(logoURL.getInputStream(), new FileOutputStream(logo));
					}
				} catch (Exception e) {
					Log.warn("Unable to download: "+info.getLogoURL());
				}
			}
			
			try {
				logos.put(info, new Image("res/nologo.png"));
				if (logo.exists()) {
					logos.put(info, new Image(new FileInputStream(logo), logo.getAbsolutePath(), true));
				} 
			} catch (Exception e) {
				Log.error(e);
			}
		}
		
		return (Image) logos.get(info);
	}
	
	private void copy(InputStream is, OutputStream out) throws IOException {
		byte[] buffer = new byte[4096];
		
		int len;
		while ((len = is.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		
		out.close();
	}
}
