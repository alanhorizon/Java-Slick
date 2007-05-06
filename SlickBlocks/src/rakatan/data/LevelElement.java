package rakatan.data;

import java.util.ArrayList;

import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.Collide;
import net.phys2d.raw.Contact;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.ConvexPolygon;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

import rakatan.ShapeRenderer;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public abstract class LevelElement {
	protected Body body;
	protected Color color;
	protected ArrayList shapes = new ArrayList();
	protected Image image;
	protected boolean selected;
	
	public void init() {
		body.setFriction(1);
		body.setRestitution(0);
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void update(int delta) {
	}

	public void setPosition(float x, float y) {
		body.setPosition(x,y);
	}
	
	public boolean containsBody(Body body) {
		return this.body == body;
	}
	
	public float getX() {
		return body.getPosition().getX();
	}

	public float getY() {
		return body.getPosition().getY();
	}

	protected void makeStill(Body body) {
		body.setForce(0, 0);
		body.adjustAngularVelocity(-body.getAngularVelocity());
		body.adjustBiasedAngularVelocity(-body.getBiasedAngularVelocity());
		
		Vector2f vel = new Vector2f(body.getVelocity());
		vel.scale(-1);
		body.adjustVelocity(vel);
	}
	
	public void makeStill() {
		makeStill(body);
	}
	
	public void setRotation(float rotation) {
		body.setRotation(rotation);
	}
	
	public float getRotation() {
		return body.getRotation();
	}
	
	protected void fillShape(Graphics g) {
		for (int i=0;i<shapes.size();i++) {
			ShapeRenderer.fill((Shape) shapes.get(i), image, 0.015f);
		}
	}
	
	protected void drawShape(Graphics g) {
		for (int i=0;i<shapes.size();i++) {
			g.draw((Shape) shapes.get(i));
		}
	}
	
	/**
	 * @see rakatan.data.LevelElement#render(org.newdawn.slick.Graphics)
	 */
	public void render(Graphics g) {
		ROVector2f pos = body.getPosition();
		float rot = body.getRotation();
		
		g.translate(pos.getX(), pos.getY());
		g.rotate(0, 0, (float) Math.toDegrees(rot));

		g.setColor(color);
		fillShape(g);

		if (selected) {
			g.setColor(Color.white);
		} else {
			g.setColor(Color.black);
		}
		g.setAntiAlias(true);
		drawShape(g);
		g.setAntiAlias(false);
		
		g.setColor(Color.white);
		g.rotate(0, 0, (float) -Math.toDegrees(rot));
		g.translate(-pos.getX(), -pos.getY());
	}
	
	protected boolean boxContains(Body body, float x, float y) {
		Box hit = new Box(5,5);
		Body hitBody = new Body(hit, 1);
		hitBody.setPosition(x,y);
		
		Contact[] contacts = new Contact[] {new Contact(), new Contact()};
		return Collide.collide(contacts, hitBody, body, 0) != 0;
	}
	
	public boolean contains(float x, float y) {
		if (body.getShape() instanceof ConvexPolygon) {
			x -= body.getPosition().getX();
			y -= body.getPosition().getY();
			return ((ConvexPolygon) body.getShape()).contains(new Vector2f(x,y));
		}
		
		if (body.getShape() instanceof Box) {
			return boxContains(body, x, y);
		}
		
		for (int i=0;i<shapes.size();i++) {
			if (((Shape) shapes.get(i)).contains(x,y)) {
				return true;
			}
		}
		
		return false;
	}
	
	public abstract void addToWorld(World world);
}
