package playground;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * A pod is a section of the the GUI that can slide around and display images and text
 *
 * @author kevin
 */
public class Pod {
	protected Rectangle rect;
	protected Image image;
	protected float x;
	protected float y;
	protected Font font;
	protected String label;
	protected PodListener listener;
	protected ArrayList images = new ArrayList();
	protected Object data;
	protected boolean enabled = true;
	protected boolean hasBeenReleased = true;
	protected boolean over;
	
	private PodGroup group = new PodGroup();
	
	protected Pod() {
	}
	
	public Pod(PodListener listener, Image image, Font font, int x, int y, String label) {
		this(listener, image, font, x,y, image.getWidth(),image.getHeight(),label);
	}

	public Pod(PodListener listener, Image image, Font font, int x, int y, int width, int height, String label) {
		this.font = font;
		this.x = x;
		this.y = y;
		this.label = label;
		this.listener = listener;
		
		this.image = image;
		rect = new Rectangle(x,y,width,height);
	}
	
	public void setGroup(PodGroup group) {
		this.group = group;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setUserData(Object data) {
		this.data = data;
	}
	
	public Object getUserData() {
		return data;
	}
	
	public void setX(int x) {
		rect.x = x;
		this.x = x;
	}
	
	public void setY(int y) {
		rect.y = y;
		this.y = y;
	}
	
	public void draw(GameContainer container, Graphics g) {
		if (!enabled) {
			return;
		}
		
		int offset = 0;
		Color col = new Color(0,0,0,0.4f);
		if (image == null) {
			col = new Color(0,0,0,0.3f);
		}
		if (!group.moving()) {
			if (over) {
				offset = -2;
				col = new Color(0,0,0,0.6f);
				if (image == null) {
					col = Color.white;
					offset += 2;
				}
			}
		}

		if (image != null) {
			image.draw((int) x,(int) y+offset, (int) rect.width, (int) rect.height);
		}
		for (int i=0;i<images.size();i++) {
			Image image = (Image) images.get(i);
			int xp = (int) ((rect.width - image.getWidth()) / 2);
			int yp = (int) ((rect.height - image.getHeight()) / 2);
			
			image.draw((int) (x+xp),(int) (y+yp+offset));
		}
		
		int xo = (int) ((rect.width - font.getWidth(label)) / 2);
		int yo = (int) ((rect.height - font.getHeight(label)) / 2);
		
		font.drawString((int) (x+xo), (int) (y+yo-5)+offset, label, col);
	}
	
	public void addImage(Image image) {
		images.add(image);
	}
	
	public void update(GameContainer container, int d, float xoffset, float yoffset) {
		if (!enabled) {
			hasBeenReleased = true;
			return;
		}
		
		over = false;
		if ((!group.moving()) && (hasBeenReleased)) {
			if (rect.contains(container.getInput().getMouseX()-xoffset, container.getInput().getMouseY()-yoffset)) {
				if (container.getInput().isMouseButtonDown(0)) {
					listener.podSelected(this, label);
					hasBeenReleased = false;
				}
				over = true;
			} 
		}
		if (!container.getInput().isMouseButtonDown(0)) {
			hasBeenReleased = true;
		}
	}
	
	public void fireMoveComplete() {
		listener.podMoveCompleted(this);
	}
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
}
