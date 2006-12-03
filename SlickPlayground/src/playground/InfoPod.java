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
public class InfoPod extends Pod {
	private Font font;
	private GameRecord info;
	private Image logo;
	private String[] lines;
	private Color shadow = new Color(0,0,0,0.5f);
	
	public InfoPod(PodListener listener, Font font) {
		this.listener = listener;
		this.font = font;
		rect = new Rectangle(0,0,0,0);
	}
	
	public void setInfo(String dataCacheLocation, GameRecord info, Image l) throws SlickException {
		this.info = info;
		this.logo = l;
		lines = split(font, info.getDescription(), 760);
		
		int maxHeight = 200;
		if (logo.getHeight() > maxHeight) {
			float rat = maxHeight / (float) logo.getHeight();
			logo = logo.getScaledCopy((int) (logo.getWidth()*rat),(int) (logo.getHeight()*rat));
		}
		
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
