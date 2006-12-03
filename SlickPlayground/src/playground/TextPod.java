package playground;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import playground.games.GameRecord;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class TextPod extends Pod {
	private Font font;
	private String[] lines;
	private Color shadow = new Color(0,0,0,0.5f);
	private String text;
	
	public TextPod(PodListener listener, Font font, String text) {
		this.listener = listener;
		this.font = font;
		this.text = text;
		rect = new Rectangle(0,0,0,0);
	}
	
	private String[] split(Font font, String text, int max) {
		ArrayList lines = new ArrayList();
		StringTokenizer tokens = new StringTokenizer(text);
		
		String line = "";
		while (tokens.hasMoreTokens()) {
			String current = tokens.nextToken();
			
			if (font.getWidth(line+current) < max) {
				line += " "+current;
			} else {
				lines.add(line);
				line = current;
			}
		}
		
		lines.add(line);
		return (String[]) lines.toArray(new String[0]);
	}
	
	public void draw(GameContainer container, Graphics g) {
		g.setFont(font);
		center(container, font, g, text, (int) y+250);
	}

	public void center(GameContainer container, Font font, Graphics g, String text, int y) {
		int xo = (container.getWidth()-font.getWidth(text))/2;
		g.setColor(shadow);
		g.drawString(text, xo+1, y+1);
		g.setColor(Color.white);
		g.drawString(text, xo, y);
	}
	
	public void update(GameContainer container, int d) {
	}

}
