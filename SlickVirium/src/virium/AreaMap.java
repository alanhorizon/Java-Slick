package virium;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.PackedSpriteSheet;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.tiled.TiledMap;

/**
 * TODO: Document this class
 * 
 * @author kevin
 */
public class AreaMap extends TiledMap {
	private boolean blocked[][];

	private ArrayList entities = new ArrayList();
	private ParticleSystem system;
	private ParticleSystem glowSystem;
	private BulletEmitter bulletEmitter;
	private BloodEmitter bloodEmitter;
	private ArrayList removeMe = new ArrayList();
	
	public AreaMap(PackedSpriteSheet sheet, String ref) throws SlickException {
		super(ref);

		blocked = new boolean[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int l = 0; l < 2; l++) {
					int id = getTileId(x, y, l);

					if (id != 0) {
						String b = getTileProperty(id, "blocked", "false");
						if (b.equals("true")) {
							blocked[x][y] = true;
						}
					}
				}
			}
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int id = getTileId(x, y, 2);

				if (id != 0) {
					String e = getTileProperty(id, "entity", "");
					if (!e.equals("")) {
						if (e.equals("alien")) {
							addEntity(new Actor((x * 16) + 8, (y * 16) + 8,
									sheet, "alien1", false,
									new ZombieActorController()));
						}
						if (e.equals("generator")) {
							addEntity(new Generator(sheet,(x * 16) + 8, (y * 16) + 8));
						}
					}
				}
			}
		}
		
		glowSystem = new ParticleSystem(new Image("res/particle.tga"),200);
		glowSystem.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
		system = new ParticleSystem(new Image("res/splat.tga"),200);
		bulletEmitter = new BulletEmitter();
		bloodEmitter = new BloodEmitter();
		glowSystem.addEmitter(bulletEmitter);
		system.addEmitter(bloodEmitter);
	}

	public void addSplat(float x, float y) {
		bloodEmitter.addSplat(x,y);
	}
	
	public void addBulletParticle(float x, float y) {
		bulletEmitter.add(x,y);
	}
	
	public boolean isBlocked(int x, int y) {
		x /= 16;
		y /= 16;

		if ((x < 0) || (x >= width) || (y < 0) || (y >= height)) {
			return true;
		}

		return blocked[x][y];
	}

	public boolean intersects(Entity against) {
		Circle sourceBounds = against.getBounds();

		for (int i = 0; i < entities.size(); i++) {
			Entity current = (Entity) entities.get(i);
			
			if ((current != against) && 
					(current.getOwner() != against.getOwner()) && 
					(against.getOwner() != current.getOwner())) {
				if (sourceBounds.intersects(current.getBounds())) {
					against.hitEntity(current);
					return true;
				}
			}
		}

		return false;
	}

	public void addEntity(Entity entity) {
		entity.setMap(this);
		entities.add(entity);
	}

	public void removeEntity(Entity entity) {
		removeMe.add(entity);
	}

	public void draw(Graphics g, int cx, int cy) {
		int sx = (cx - 400) / 16;
		int sy = (cy - 300) / 16;
		int ox = (cx - 400) % 16;
		int oy = (cy - 300) % 16;

		int x = -ox;
		int y = -oy;
		int height = 40;
		int width = 52;

		for (int i = 0; i < 2; i++) {
			for (int ty = 0; ty < height; ty++) {
				Layer layer = (Layer) layers.get(i);
				layer.render(x, y, sx, sy, width, ty, true);
			}

			if (i == 0) {
				g.translate(-cx + 400, -cy + 300);
				system.render();
				glowSystem.render();
				g.resetTransform();
				
				for (int j = 0; j < entities.size(); j++) {
					Entity entity = ((Entity) entities.get(j));
					float xp = -cx + 400 + entity.getX();
					float yp = -cy + 300 + entity.getY();
					
					if ((xp > -50) && (yp > -50) && (xp < 850) && (yp < 650)) {
						g.translate(-cx + 400, -cy + 300);
						entity.draw(g);
						g.resetTransform();
					}
				}
			}
		}

		// debug blocking areas
		// g.setColor(new Color(1,0,0,0.5f));
		// for (int xp=0;xp<width;xp++) {
		// for (int yp=0;yp<height;yp++) {
		// if ((sx+xp > 0) && (sy+yp > 0)) {
		// if (blocked[sx+xp][sy+yp]) {
		// g.fillRect(x+(xp*16),y+(yp*16),16,16);
		// }
		// }
		// }
		// }
	}

	public void update(GameContext context, int delta) {
		system.update(delta);
		glowSystem.update(delta);
		
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = (Entity) entities.get(i);
			entity.update(context, delta);
		}
		
		entities.removeAll(removeMe);
		removeMe.clear();
	}
}
