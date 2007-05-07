package rakatan.data;

import java.io.PrintStream;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.ConvexPolygon;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;
import org.w3c.dom.Element;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class DynamicWedgeElement extends LevelElement {
	private int x;
	private int y;
	private int width;
	private int height;
	
	DynamicWedgeElement() {
	}
	
	public DynamicWedgeElement(int x, int y,int width, int height, Image image, Color c) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.color = c;
		
		ConvexPolygon poly = new ConvexPolygon(new Vector2f[] {new Vector2f(0, -(height/2)*Level.SCALE),
												   new Vector2f((width/2)*Level.SCALE, (height/2)*Level.SCALE),
												   new Vector2f(-(width/2)*Level.SCALE, (height/2)*Level.SCALE)});
	
		body = new Body(poly, 1000);
		body.setPosition((x+(width/2))*Level.SCALE, (y+(height/2))*Level.SCALE);
		body.setRotation(10);
		
		Polygon p = new Polygon();
		p.addPoint(0,(-(height/2))*Level.SCALE);
		p.addPoint(((width/2))*Level.SCALE,(height/2)*Level.SCALE);
		p.addPoint((-(width/2))*Level.SCALE,(height/2)*Level.SCALE);
		shapes.add(p);
	}

	/**
	 * @see rakatan.data.LevelElement#save(java.io.PrintStream)
	 */
	public void save(PrintStream pout) {
		pout.print("<Wedge ");
		pout.print("id=\""+id+"\" ");
		pout.print("width=\""+width+"\" ");
		pout.print("height=\""+height+"\" ");
		pout.print("rot=\""+body.getRotation()+"\" ");
		pout.print("x=\""+body.getPosition().getX()+"\" ");
		pout.print("y=\""+body.getPosition().getY()+"\" ");
		pout.println(">");
		pout.print("   <Color ");
		pout.print("red=\""+color.r+"\" ");
		pout.print("green=\""+color.g+"\" ");
		pout.print("blue=\""+color.b+"\" ");
		pout.println("/>");
		pout.println("</Wedge>");
	}
	
	/**
	 * @see rakatan.data.LevelElement#load(org.w3c.dom.Element, org.newdawn.slick.Image, org.newdawn.slick.Image)
	 */
	public LevelElement load(Element xml, Image stat, Image dynamic) {
		int id = Integer.parseInt(xml.getAttribute("id"));
		float width = Float.parseFloat(xml.getAttribute("width"));
		float height = Float.parseFloat(xml.getAttribute("height"));
		float x = Float.parseFloat(xml.getAttribute("x"));
		float y = Float.parseFloat(xml.getAttribute("y"));
		float rot = Float.parseFloat(xml.getAttribute("rot"));

		Element c = (Element) xml.getElementsByTagName("Color").item(0);
		float r = Float.parseFloat(c.getAttribute("red"));
		float g = Float.parseFloat(c.getAttribute("green"));
		float b = Float.parseFloat(c.getAttribute("blue"));
		Color col = new Color(r,g,b);
		
		DynamicWedgeElement element = new DynamicWedgeElement(0,0,(int) width,(int) height,dynamic,col);
		element.setID(id);
		element.setPosition(x, y);
		element.setRotation(rot);
		
		return element;
	}
	
	public boolean isSameKind(LevelElement other) {
		if (other instanceof DynamicWedgeElement) {
			DynamicWedgeElement o = (DynamicWedgeElement) other;
			
			return (o.width == this.width) && (o.height == this.height);
		}
		
		return false;
	}
}
