package rakatan.data;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.ConvexPolygon;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

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
		
		body = new StaticBody(new Box(width,height));
		body.setPosition(x+(width/2), y+(height/2));
	}

	public boolean contains(float x, float y) {
		return false;
	}
	
	public void addToWorld(World world) {
		world.add(body);
	}
	
	/**
	 * @see rakatan.data.LevelElement#render(org.newdawn.slick.Graphics)
	 */
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x,y,width,height,image,0,0);
	}

	/**
	 * @see rakatan.data.LevelElement#update(int)
	 */
	public void update(int delta) {
	}

}
