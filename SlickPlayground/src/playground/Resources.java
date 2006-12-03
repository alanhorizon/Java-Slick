package playground;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class Resources {

	public static Font font;
	public static Font font2;
	public static Font font3;
	public static Image podImage;
	
	public static void init() throws SlickException {
		Resources.font = new AngelCodeFont("res/font2.fnt","res/font2_00.png");
		Resources.font3 = new AngelCodeFont("res/font3.fnt","res/font3_00.tga");
		Resources.font2 = new AngelCodeFont("res/font.fnt","res/font_00.png");
		Resources.podImage = new Image("res/pod.png");
	}
}
