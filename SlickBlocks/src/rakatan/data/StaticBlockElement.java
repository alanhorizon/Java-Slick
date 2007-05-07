package rakatan.data;

import net.phys2d.raw.StaticBody;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.Box;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import rakatan.ShapeRenderer;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class StaticBlockElement extends LevelElement {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public StaticBlockElement(int x, int y,int width, int height, Image image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.color = Color.white;
		
		body = new StaticBody(new Box(width * Level.SCALE,height * Level.SCALE));
		body.setPosition((x+(width/2))*Level.SCALE, (y+(height/2))*Level.SCALE);
		
		shapes.add(new Rectangle(-(width/2)*Level.SCALE,-(height/2)*Level.SCALE,width*Level.SCALE,height*Level.SCALE));	
	}

	public boolean contains(float x, float y) {
		return false;
	}
	
	public void addToWorld(World world) {
		world.add(body);
	}

	protected void fillShape(Graphics g) {
		for (int i=0;i<shapes.size();i++) {
			ShapeRenderer.fill((Shape) shapes.get(i), image, 0.005f / Level.SCALE);
		}
	}

	/**
	 * @see rakatan.data.LevelElement#update(int)
	 */
	public void update(int delta) {
	}

}
