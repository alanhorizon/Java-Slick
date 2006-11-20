package virium;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.PackedSpriteSheet;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class InGameState extends BasicGameState implements GameContext {
	private static final int ID = 1;
	private AreaMap map;
	private PackedSpriteSheet packed;
	
	private Actor player1;
	private Actor player2;
	
	/**
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		packed = new PackedSpriteSheet("res/pack.def");
		
		player1 = new Actor(230,130,packed,"player1",true);
		//player2 = new Actor(250,150,packed,"player2",true);
		
		map = new AreaMap(packed, "res/base1.tmx");
		
		map.addEntity(player1);
	}

	/**
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	public void render(StateBasedGame game, Graphics g) throws SlickException {
		map.draw(g,(int) player1.getX(),(int) player1.getY());
	}

	/**
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		int x = 0;
		int y = 0;
		Input input = container.getInput();
		
		if (input.isKeyDown(Keyboard.KEY_LEFT)) {
			x -= 1;
		}
		if (input.isKeyDown(Keyboard.KEY_RIGHT)) {
			x += 1;
		}
		if (input.isKeyDown(Keyboard.KEY_UP)) {
			y -= 1;
		}
		if (input.isKeyDown(Keyboard.KEY_DOWN)) {
			y += 1;
		}
		player1.setFire(input.isKeyDown(Keyboard.KEY_LCONTROL));
		
		player1.applyDirection(x, y);
		
		map.update(this, delta);
	}

	/**
	 * @see virium.GameContext#getPlayer1()
	 */
	public Actor getPlayer1() {
		return player1;
	}

	/**
	 * @see virium.GameContext#getPlayer2()
	 */
	public Actor getPlayer2() {
		return player2;
	}

}
