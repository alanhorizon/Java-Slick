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
	public static final float SCALE = 1;
	
	private World world;
	private ArrayList elements = new ArrayList();
	
	private int remainder = 0;
	private int step = 5;
	private long lastLevelUpdate;
	
	public Level() {
		world = new World(new Vector2f(0,1f * SCALE), 30, new BruteCollisionStrategy());
		world.enableRestingBodyDetection(0.01f, 0.000001f, 100f);
	}
	
	public void clearResting(LevelElement from) {
		//world.clearRestingState();
		from.clearResting();
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
		g.scale(1/SCALE, 1/SCALE);
		for (int i=0;i<elements.size();i++) {
			((LevelElement) elements.get(i)).render(g);
		}
		g.resetTransform();
	}
	
	public void add(LevelElement element) {
		elements.add(element);
		element.addToWorld(world);
		
		element.init();
	}
}
