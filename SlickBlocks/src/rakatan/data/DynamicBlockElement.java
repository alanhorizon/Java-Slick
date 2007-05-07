package rakatan.data;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.Collide;
import net.phys2d.raw.Contact;
import net.phys2d.raw.World;
import net.phys2d.raw.collide.ColliderFactory;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.ConvexPolygon;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class DynamicBlockElement extends LevelElement {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public DynamicBlockElement(int x, int y,int width, int height, Image image, Color c) {
		this(x,y,width,height,image,c,0);
	}

	public DynamicBlockElement(int x, int y,int width, int height, Image image, Color c, float rot) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.color = c;
		
		shapes.add(new Rectangle(-(width/2)*Level.SCALE,-(height/2)*Level.SCALE,width*Level.SCALE,height*Level.SCALE));
		
//		ConvexPolygon poly = new ConvexPolygon(new Vector2f[] {
//							new Vector2f(-width/2,-height/2),
//							new Vector2f(width/2,-height/2),
//							new Vector2f(width/2,height/2),
//							new Vector2f(-width/2,height/2),
//		});
		
		body = new Body(new Box(width*Level.SCALE,height*Level.SCALE), 1000);
		//body = new Body(poly, 1);
		body.setPosition((x+(width/2))*Level.SCALE, (y+(height/2))*Level.SCALE);
		body.setRotation(rot);
	}
	
	public void addToWorld(World world) {
		world.add(body);
	}

}
