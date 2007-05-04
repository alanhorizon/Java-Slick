package rakatan.data;

import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.World;
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
	protected Shape shape;
	protected Image image;
	
	public void init() {
		body.setFriction(0.8f);
	}
	
	public void update(int delta) {
	}

	public void setPosition(float x, float y) {
		body.setPosition(x,y);
	}
	
	public Body getBody() {
		return body;
	}
	
	public float getX() {
		return body.getPosition().getX();
	}

	public float getY() {
		return body.getPosition().getY();
	}
	
	public void makeStill() {
		body.setForce(0, 0);
		body.adjustAngularVelocity(-body.getAngularVelocity());
		body.adjustBiasedAngularVelocity(-body.getBiasedAngularVelocity());
		
		Vector2f vel = new Vector2f(body.getVelocity());
		vel.scale(-1);
		body.adjustVelocity(vel);
	}
	
	public void setRotation(float rotation) {
		body.setRotation(rotation);
	}
	
	public float getRotation() {
		return body.getRotation();
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
		ShapeRenderer.fill(shape, image, 0.015f);

		g.setColor(Color.black);
		g.setAntiAlias(true);
		g.draw(shape);
		g.setAntiAlias(false);
		
		g.setColor(Color.white);
		g.rotate(0, 0, (float) -Math.toDegrees(rot));
		g.translate(-pos.getX(), -pos.getY());
	}
	
	public boolean contains(float x, float y) {
		x -= body.getPosition().getX();
		y -= body.getPosition().getY();
		
		if (body.getShape() instanceof ConvexPolygon) {
			return ((ConvexPolygon) body.getShape()).contains(new Vector2f(x,y));
		}
		
		return shape.contains(x, y);
	}
	
	public abstract void addToWorld(World world);
}
