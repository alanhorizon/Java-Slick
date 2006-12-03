package playground;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public abstract class State {
	protected ArrayList podGroups = new ArrayList();
	
	public abstract void reinit();
	
	public void update(GameContainer container, int delta) {
		for (int i=0;i<podGroups.size();i++) {
			((PodGroup) podGroups.get(i)).update(container, delta);
		}
	}
	
	public void render(GameContainer container, Graphics g) {
		for (int i=0;i<podGroups.size();i++) {
			((PodGroup) podGroups.get(i)).draw(container, g);
		}
	}
	
	public abstract void enter(int lastState, Playground app);
	
	public abstract void leave(Playground app);
	
	public abstract String getPrevLabel();
	
	public abstract String getNextLabel();
	
	public abstract String getBackLabel();
	
	public abstract void prevSelected();

	public abstract void nextSelected();
	
	public abstract void backSelected();
}
