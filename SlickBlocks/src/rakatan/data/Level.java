package rakatan.data;

import java.util.ArrayList;

import net.phys2d.math.Vector2f;
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
	
	private int remainder = 0;
	private int step = 1;
	
	public Level() {
		world = new World(new Vector2f(0,1f), 5, new BruteCollisionStrategy());
	}
	
	public int getCollisions(final LevelElement source) {
		final ArrayList bodies = new ArrayList();
		
		CollisionListener listener = new CollisionListener() {
			public void collisionOccured(CollisionEvent event) {
				if ((source.containsBody(event.getBodyA())) || (source.containsBody(event.getBodyB()))) {
					bodies.add(event);
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
		
		element.init();
	}
}
