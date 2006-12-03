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
 * A Pod display the game information and controls to download, rate, play and mark the
 * entry.
 *
 * @author kevin
 */
public class InfoPod extends Pod {
	/** The font to render to the text */
	private Font font;
	/** The record of data being display */
	private GameRecord info;
	/** The image displayed as the logo of the game */
	private Image logo;
	/** The lines of text to dispaly */
	private String[] lines;
	/** The colour used for shadowing the text */
	private Color shadow = new Color(0,0,0,0.5f);
	
	/**
	 * Create a new information Pod
	 * 
	 * @param listener The listener to report updates to
	 * @param font The font to display the text with
	 */
	public InfoPod(PodListener listener, Font font) {
		this.listener = listener;
		this.font = font;
		rect = new Rectangle(0,0,0,0);
	}
	
	/**
	 * Set the information to be displayed
	 * 
	 * @param info The information to be displayed
	 * @param l The logo image
	 */
	public void setInfo(GameRecord info, Image l) {
		this.info = info;
		this.logo = l;
		lines = split(font, info.getDescription(), 760);
		
		int maxHeight = 200;
		if (logo.getHeight() > maxHeight) {
			float rat = maxHeight / (float) logo.getHeight();
			logo = logo.getScaledCopy((int) (logo.getWidth()*rat),(int) (logo.getHeight()*rat));
		}
		
	}
	
	/**
	 * Split a piece of text into seperate lines based on the width of the font
	 *
	 * @param font The font to check against
	 * @param text The full text to be displayed
	 * @param max The maximum length of a line
	 * @return The lines of text
	 */
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
	
	/**
	 * @see playground.Pod#draw(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	public void draw(GameContainer container, Graphics g) {
		if (info != null) {
			g.setFont(font);
			int xp = (container.getWidth() - logo.getWidth()) / 2;
			logo.draw(xp,(int) y+50);
			center(container, font, g, info.getName(), (int) y+250);
			center(container, font, g, "("+info.getAuthor()+")", (int) y+280);
			
			for (int i=0;i<lines.length;i++) {
				center(container, font, g, lines[i], (int) y+330+(i*30));
			}
		}
	}

	/**
	 * Center a piece of text on the screen
	 * 
	 * @param container The container in which the graphics are being displayed
	 * @param font The font to draw the text with
	 * @param g The graphics context that should be used to draw the text
	 * @param text The text to write
	 * @param y The y coordinate to draw the font at
	 */
	private void center(GameContainer container, Font font, Graphics g, String text, int y) {
		int xo = (container.getWidth()-font.getWidth(text))/2;
		g.setColor(shadow);
		g.drawString(text, xo+1, y+1);
		g.setColor(Color.white);
		g.drawString(text, xo, y);
	}

}
