package rakatan;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import rakatan.data.DynamicBlockElement;
import rakatan.data.DynamicWedgeElement;
import rakatan.data.Level;
import rakatan.data.LevelElement;
import rakatan.data.StaticBlockElement;

/**
 * TODO: Document this class
 * 
 * @author kevin
 */
public class InGameState extends BasicGameState implements GameState {
	private static final int ID = 1;
	private Level level;
	private Image back;
	private LevelElement selected;
	private float originalRotation;
	private float xoffset;
	private float yoffset;
	private GameContainer container;
	private float oldmx;
	private float oldmy;
	
	private AngelCodeFont big;
	private AngelCodeFont small;
	private AngelCodeFont tiny;
	
	private float xp = 800;
	private String instructions = ".. Drag with LMB to move .. Hold RMB to rotate .. Press R to reset ..";
	private boolean rmb;
	
	/**
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame)
	 */
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.container = container;
		container.setTargetFrameRate(100);
		
		big = new AngelCodeFont("res/big.fnt","res/big.png");
		small = new AngelCodeFont("res/small.fnt","res/small.png");
		tiny = new AngelCodeFont("res/tiny.fnt","res/tiny.png");
		
		back = new Image("res/back.png");
		reset();
	}
	
	private void reset() throws SlickException {
		selected = null;
		level = new Level();
		level.add(new StaticBlockElement(-50, 560, 1000, 50, new Image(
				"res/floor.png")));
		level.add(new DynamicBlockElement(300, 300, 50, 50, new Image(
				"res/block.png"), Color.yellow));
		level.add(new DynamicBlockElement(340, 200, 100, 30, new Image(
				"res/block.png"), Color.blue));
		level.add(new DynamicBlockElement(250, 100, 70, 40, new Image(
				"res/block.png"), Color.green));
		level.add(new DynamicWedgeElement(150, 100, 70, 40, new Image(
				"res/block.png"), Color.red));
		level.add(new DynamicBlockElement(400, 300, 50, 50, new Image(
				"res/block.png"), Color.yellow));
		level.add(new DynamicBlockElement(440, 200, 100, 30, new Image(
				"res/block.png"), Color.blue));
		level.add(new DynamicBlockElement(550, 100, 70, 40, new Image(
				"res/block.png"), Color.green));
		level.add(new DynamicWedgeElement(550, 0, 70, 40, new Image(
				"res/block.png"), Color.red));
	}

	/**
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.scale(2, 2);
		g.fillRect(0, 0, container.getWidth(), container.getHeight(), back, 0, 0);
		g.resetTransform();

		level.render(g);
		
		String title = "Toy Blocks";
		int x = (container.getWidth() - big.getWidth(title)) / 2;
		big.drawString(x, 7, title);
		
		g.setFont(tiny);
		String line = "phys2d@cokeandcode.com";
		x = (container.getWidth() - g.getFont().getWidth(line)) / 2;
		g.drawString(line, x, 50);
		line = "http://www.cokeandcode.com";
		x = (container.getWidth() - g.getFont().getWidth(line)) / 2;
		g.drawString(line, x, 65);

		small.drawString(xp, 570, instructions);
	}

	/**
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		xp -= (delta * 0.1f);
		if (xp < -small.getWidth(instructions)) 
		{
			xp = 900;
		}
		
		float x = 0;
		float y = 0;
		
		if (selected != null) {
			x = selected.getX();
			y = selected.getY();
		}
		level.update(delta);
		if (selected != null) {
			selected.setPosition(x, y);
			selected.setRotation(originalRotation);
			selected.makeStill();
		}
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if (selected != null) {
			if (rmb) {
				originalRotation += (newx - oldx) * 0.01f;
			} else {
				float x = selected.getX();
				float y = selected.getY();
				float nx = x + (newx - oldx);
				float ny = y + (newy - oldy);
	
				int before = level.getCollisions(selected);
				selected.setPosition(nx, ny);
				int after = level.getCollisions(selected);
				if (after > before) {
					selected.setPosition(x, y);
				}
			}
		}
	}

	public void mousePressed(int button, int x, int y) {
		if (button == 0) {
			selected = level.getElementAt(x, y);
			if (selected != null) {
				xoffset = x - selected.getX();
				yoffset = y - selected.getY();

				originalRotation = selected.getRotation();
			}
			container.setMouseGrabbed(true);
		}
		if (button != 0) {
			rmb = true;
		}
	}

	public void mouseReleased(int button, int x, int y) {
		if (button == 0) {
			container.setMouseGrabbed(false);
			if (selected != null) {
				Mouse.setCursorPosition((int) selected.getX(), (int) (container.getHeight()-selected.getY()));
				selected = null;
			}
		}
		if (button != 0) {
			rmb = false;
		}
	}

	public void keyPressed(int key, char c) {
		if (key == Input.KEY_R) {
			try {
				reset();
			} catch (SlickException e) {
				Log.error(e);
			}
		}
		if (key == Input.KEY_ESCAPE) {
			container.exit();
		}
	}
}
