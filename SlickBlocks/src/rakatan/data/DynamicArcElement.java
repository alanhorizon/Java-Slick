package rakatan.data;

import java.util.ArrayList;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.ConvexPolygon;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class DynamicArcElement extends LevelElement {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public DynamicArcElement(int x, int y,int width, int height, int segs, Image image, Color c) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.color = c;
		
		ArrayList pts = new ArrayList();
		for (int i=0;i<segs+1;i++) {
			float ang = (float) Math.toRadians((180.0f / segs) * i);
			float yo = (float) ((height/2) - (Math.sin(ang) * height));
			float xo = (float) -(Math.cos(ang) * (width/2));
			pts.add(new Vector2f(xo,yo));
		}
		
		Vector2f[] ptsArray = (Vector2f[]) pts.toArray(new Vector2f[0]);
		
		ConvexPolygon poly = new ConvexPolygon(ptsArray);
	
		body = new Body(poly, 1000);
		body.setPosition(x+(width/2), y+(height/2));
		
		Polygon p = new Polygon();
		for (int i=0;i<ptsArray.length;i++) {
			p.addPoint(ptsArray[i].getX(), ptsArray[i].getY());
		}
		shapes.add(p);
	}
	
	public void addToWorld(World world) {
		world.add(body);
	}

}
