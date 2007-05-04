package rakatan;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class Rakatan extends StateBasedGame {

	public Rakatan() {
		super("Rakatan");
	}

	/**
	 * @see org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick.GameContainer)
	 */
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new InGameState());
	}
	
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new Rakatan(), 800, 600, false);
			container.start();
		} catch (Exception e) {
			Log.error(e);
		}
	}
}
