package rakatan.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.CollisionListener;
import net.phys2d.raw.World;
import net.phys2d.raw.strategies.BruteCollisionStrategy;

import org.newdawn.slick.Graphics;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class Level {
	private World world;
	private ArrayList elements = new ArrayList();
	private HashMap bodyMap = new HashMap();
	
	private int remainder = 0;
	private int step = 5;
	
	public Level() {
		world = new World(new Vector2f(0,1f), 20, new BruteCollisionStrategy());
		world.setDamping(0.9f);
	}
	
	public int getCollisions(LevelElement source) {
		final Body body = source.getBody();
		final ArrayList bodies = new ArrayList();
		
		CollisionListener listener = new CollisionListener() {
			public void collisionOccured(CollisionEvent event) {
				if ((event.getBodyA() == body) || (event.getBodyB() == body)) {
					bodies.add(body);
				}
			}
		};
		
		world.addListener(listener);
		world.collide(0);
		world.removeListener(listener);
	
		return bodies.size();
	}
	
	public void update(int delta) {
		delta += remainder;
		remainder = delta % step;
		for (int i = 0; i < delta / step; i++) {
			world.step(step / 30.0f);
		}
		
		for (int i=0;i<elements.size();i++) {
			((LevelElement) elements.get(i)).update(delta);
		}
	}
	
	public LevelElement getElementAt(int x, int y) {
		float distance = Float.MAX_VALUE;
		LevelElement found = null;
		
		for (int i=0;i<elements.size();i++) {
			LevelElement e = ((LevelElement) elements.get(i));
			if (e.contains(x,y)) {
				return e;
			}
		}
		
		return found;
	}
	
	public void render(Graphics g) {
		for (int i=0;i<elements.size();i++) {
			((LevelElement) elements.get(i)).render(g);
		}
	}
	
	public void add(LevelElement element) {
		elements.add(element);
		element.addToWorld(world);
		bodyMap.put(element.getBody(), element);
		
		element.init();
	}
}
