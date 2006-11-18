package virium;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.PackedSpriteSheet;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class Generator implements Entity {
	private SpriteSheet sheet;
	private Animation anim;
	private int x;
	private int y;
	private int spawnInterval = 1000;
	private int nextSpawn;
	private Circle bounds;
	private PackedSpriteSheet pack;
	private AreaMap map;
	
	public Generator(PackedSpriteSheet pack,int x, int y) throws SlickException {
		this.pack = pack;
		sheet = pack.getSpriteSheet("generator");
		anim = new Animation();
		anim.addFrame(sheet.getSprite(0,0), 50);
		anim.addFrame(sheet.getSprite(1,0), 50);
		anim.addFrame(sheet.getSprite(2,0), 50);
		
		this.x = x;
		this.y = y;
		
		nextSpawn = spawnInterval;
		bounds = new Circle(x,y,25);
	}
	
	public Circle getBounds() {
		return bounds;
	}
	
	/**
	 * @see virium.Entity#update(virium.GameContext, int)
	 */
	public void update(GameContext context, int delta) {
		nextSpawn -= delta;
		if (nextSpawn <= 0) {
			spawn();
			nextSpawn = spawnInterval;
		}
	}

	protected Actor createActor(int x, int y) {
		return new Actor(x,y,
				pack, "alien1", false,
				new ZombieActorController());
	}
	
	private void spawn() {
		Actor p1 = createActor(x,y-40);
		Actor p2 = createActor(x,y+40);
		Actor p3 = createActor(x-40,y);
		Actor p4 = createActor(x+40,y);
		
		ArrayList possibles = new ArrayList();
		if (!map.intersects(p1)) {
			possibles.add(p1);
		}
		if (!map.intersects(p2)) {
			possibles.add(p2);
		}
		if (!map.intersects(p3)) {
			possibles.add(p3);
		}
		if (!map.intersects(p4)) {
			possibles.add(p4);
		}
		
		if (possibles.size() > 0) {
			int r = (int) (Math.random() * possibles.size());
			map.addActor((Actor) possibles.get(r));
		}
	}
	
	/**
	 * @see virium.Entity#render(org.newdawn.slick.Graphics)
	 */
	public void draw(Graphics g) {
		int ofs = sheet.getHeight() / 2;
		anim.draw(x-ofs,y-ofs);
	}

	/**
	 * @see virium.Entity#setMap(virium.AreaMap)
	 */
	public void setMap(AreaMap map) {
		this.map = map;
	}

}
