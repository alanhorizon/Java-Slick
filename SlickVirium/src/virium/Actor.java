package virium;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.PackedSpriteSheet;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class Actor {
	private Animation animation;
	private float x;
	private float y;
	private boolean moving;
	private int dx;
	private int dy;
	private float ang;
	private AreaMap map;
	
	private float scalar;
	private int applyDX;
	private int applyDY;
	private ActorController controller;
	
	private Circle bounds;
	private float speed = 0.1f;
	private boolean type;
	
	public Actor(float x, float y, PackedSpriteSheet sheet, String ref, boolean type) {
		this(x,y,sheet,ref,type,null);
	}
	
	public Actor(float x, float y, PackedSpriteSheet sheet, String ref, boolean type, ActorController controller) {
		this.x = x;
		this.y = y;
		this.type = type;
		
		scalar = (float) Math.sqrt(0.5f);
		
		bounds = new Circle(x,y,14);
		
		this.controller = controller;
		if (controller != null) {
			controller.init(this);
		}
		
		animation = new Animation();
		SpriteSheet temp = sheet.getSpriteSheet(ref);
		if (type) {
			animation.addFrame(temp.getSprite(1,0), 75);
			animation.addFrame(temp.getSprite(0,0), 75);
			animation.addFrame(temp.getSprite(1,0), 75);
			animation.addFrame(temp.getSprite(2,0), 75);
			animation.addFrame(temp.getSprite(3,0), 75);
			animation.addFrame(temp.getSprite(2,0), 75);
		} else {
			animation.addFrame(temp.getSprite(0,1), 75);
			animation.addFrame(temp.getSprite(0,0), 75);
			animation.addFrame(temp.getSprite(0,1), 75);
			animation.addFrame(temp.getSprite(0,2), 75);
			animation.addFrame(temp.getSprite(0,3), 75);
			animation.addFrame(temp.getSprite(0,2), 75);
		}
		animation.stop();
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public Circle getBounds() {
		return bounds;
	}
	
	public void setMap(AreaMap map) {
		this.map = map;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void draw(Graphics g) {
		int xp = (int) x;
		int yp = (int) y;
		
		g.rotate(xp, yp, ang);
		animation.draw(xp-16,yp-16);
		g.resetTransform();
	}
	
	private boolean validPosition(float x, float y) {
		for (int xs=2;xs<=28;xs+=13) {
			for (int ys=2;ys<=28;ys+=13) {
				if (map.isBlocked((int) x+xs-16,(int)y+ys-16)) {
					return false;
				}
			}
		}
		
		bounds.setX(x);
		bounds.setY(y);
		
		if (map.intersects(this)) {
			return false;
		}
		
		return true;
	}
	
	public void applyDirection(int x,int y) {
		applyDX = x;
		applyDY = y;
	}
	
	public void update(GameContext context, int delta) {
		if (controller != null) {
			controller.update(context, this, delta);
		}
		
		boolean oldMoving = moving;
		moving = (applyDX != 0) || (applyDY != 0);
		
		int olddx = (int) this.dx;
		int olddy = (int) this.dy;
		
		if (moving) {
			this.dx = applyDX;
			this.dy = applyDY;
			
			float oldx = x;
			float oldy = y;
			
			float s = 1;
			if ((applyDX != 0) && (applyDY != 0)) {
				s = scalar;
			}
			
			x += applyDX * delta * speed * s;
			y += applyDY * delta * speed * s;
			
			//if (validPosition(oldx, oldy)) {
				if (!validPosition(x,y)) {
					if (!validPosition(x,oldy)) {
						if (!validPosition(oldx,y)) {
							x = oldx;
							y = oldy;
							
							if (!type) {
								x -= applyDY * delta * speed * s;
								y += applyDX * delta * speed * s;
								
								if (!validPosition(x,y)) {
									x = oldx;
									y = oldy;
									x += applyDY * delta * speed * s;
									y -= applyDX * delta * speed * s;

									if (!validPosition(x,y)) {
										x = oldx;
										y = oldy;
									}
								}
							}
						} else {
							x = oldx;
						}
					} else {
						y = oldy;
					}
				} 
			//}
		}
		
		if ((olddx != this.dx) || (olddy != this.dy)) {
			ang = (float) Math.toDegrees(Math.atan2(this.dy, this.dx)) + 90;
		}
		
		if (oldMoving != moving) {
			if (!moving) {
				animation.stop();
			} else {
				animation.restart();
			}
		}
		
		bounds.setX(x);
		bounds.setY(y);
	}
}
