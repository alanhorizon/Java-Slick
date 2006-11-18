package virium;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public interface Entity {

	public void setMap(AreaMap map);
	
	public void draw(Graphics g);
	
	public void update(GameContext context, int delta);
	
	public Circle getBounds();
}
