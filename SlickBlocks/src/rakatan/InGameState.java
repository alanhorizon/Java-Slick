package rakatan;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
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

import rakatan.data.DynamicArcElement;
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
	public static boolean RESTING_BODDIES = false;
	
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
	private String instructions = ".. Drag with LMB to move .. Hold RMB to rotate .. Press R to reset .." +
	                              " Press F to toggle Fullscreen ..";
	private boolean rmb;
	
	private LevelElement over;
	private int mouseMoveIgnoreCount = 0;
	private boolean editMode = false;
	
	private String[] messages = new String[] {"","","","","",""};
	
	/**
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	public int getID() {
		return ID;
	}

	private void addMessage(String message) {
		for (int i=0;i<messages.length-1;i++) {
			messages[i] = messages[i+1];
		}
		messages[messages.length-1] = message;
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
		level.add(new StaticBlockElement(-500, container.getHeight()-40, container.getWidth()+1000, 50, new Image(
				"res/floor.png")));
		
		level.add(new DynamicBlockElement(140, 500, 50, 50, new Image(
				"res/block.png"), Color.yellow));
		level.add(new DynamicBlockElement(200, 490, 50, 50, new Image(
				"res/block.png"), Color.yellow));
		level.add(new DynamicBlockElement(140, 580, 50, 50, new Image(
				"res/block.png"), Color.yellow));
		level.add(new DynamicBlockElement(200, 590, 50, 50, new Image(
				"res/block.png"), Color.yellow));
		
		level.add(new DynamicBlockElement(300, 500, 250, 20, new Image(
			    "res/block.png"), Color.cyan));
		level.add(new DynamicBlockElement(280, 525, 250, 20, new Image(
	    		"res/block.png"), Color.cyan));
		
		level.add(new DynamicBlockElement(440, 700, 100, 30, new Image(
				"res/block.png"), Color.blue));
		level.add(new DynamicBlockElement(440, 660, 100, 30, new Image(
			"res/block.png"), Color.blue));
		level.add(new DynamicBlockElement(440, 620, 100, 30, new Image(
			"res/block.png"), Color.blue));
		
		level.add(new DynamicBlockElement(650, 600, 70, 40, new Image(
				"res/block.png"), Color.green));
		level.add(new DynamicBlockElement(650, 520, 70, 40, new Image(
		"res/block.png"), Color.green));
		level.add(new DynamicBlockElement(650, 680, 70, 40, new Image(
		"res/block.png"), Color.green));
		
		level.add(new DynamicWedgeElement(850, 600, 70, 40, new Image(
				"res/block.png"), Color.red));
		level.add(new DynamicWedgeElement(850, 650, 70, 40, new Image(
		"res/block.png"), Color.red));
		level.add(new DynamicWedgeElement(850, 550, 70, 40, new Image(
		"res/block.png"), Color.red));
		level.add(new DynamicWedgeElement(850, 500, 70, 40, new Image(
		"res/block.png"), Color.red));
		
//		level.add(new DynamicArcElement(500, 300, 100, 50, 10, new Image("res/block.png"), Color.magenta));
//		level.add(new DynamicArcElement(350, 300, 100, 50, 10, new Image("res/block.png"), Color.magenta));
//		level.add(new DynamicArcElement(650, 300, 100, 50, 10, new Image("res/block.png"), Color.magenta));
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
		if (over != null) {
			g.setLineWidth(2);
			g.scale(1/Level.SCALE, 1/Level.SCALE);
			over.render(g, Color.white);
			g.resetTransform();
			g.setLineWidth(1);
		}
		
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
		if (editMode) {
			line = "[EDIT MODE]";
			x = (container.getWidth() - g.getFont().getWidth(line)) / 2;
			g.drawString(line, x, 80);
		
			for (int i=0;i<messages.length;i++) {
				
			}
		}
		
		small.drawString(xp, container.getHeight()-30, instructions);
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
		if (mouseMoveIgnoreCount > 0) {
			mouseMoveIgnoreCount--;
			return;
		}
		
		if (selected != null) {
			if (rmb) {
				float oldRot = selected.getRotation();
				int before = level.getCollisions(selected);
				selected.setRotation(oldRot + (newx - oldx) * 0.01f);
				int after = level.getCollisions(selected);
				if (after > before) {
					selected.setRotation(oldRot);
				} else {
					level.clearResting(selected);
					originalRotation = selected.getRotation();
				}
			} else {
				float x = selected.getX();
				float y = selected.getY();
				float nx = x + ((newx - oldx) * Level.SCALE);
				float ny = y + ((newy - oldy) * Level.SCALE);
				
				int before = level.getCollisions(selected);
				selected.setPosition(nx, ny);
				int after = level.getCollisions(selected);
				if (after > before) {
					selected.setPosition(x, y);
				} else {
					level.clearResting(selected);
				}
			}
		} else {
			over = level.getElementAt(newx, newy);
		}
	}

	public void mousePressed(int button, int x, int y) {
		if (button == 0) {
			selected = level.getElementAt(x, y);
			if (selected != null) {
				xoffset = x - selected.getX();
				yoffset = y - selected.getY();

				originalRotation = selected.getRotation();
				mouseMoveIgnoreCount = 5;
				container.setMouseGrabbed(true);
			}
		}
		if (button != 0) {
			rmb = true;
		}
	}

	public void mouseReleased(int button, int x, int y) {
		if (button == 0) {
			container.setMouseGrabbed(false);
			if (selected != null) {
				selected.makeStill();
				Mouse.setCursorPosition((int) selected.getX(), (int) (container.getHeight()-selected.getY()));
				mouseMoveIgnoreCount+=5;
				level.clearResting(selected);
				over = null;
				selected = null;
			}
			rmb = false;
		}
		if (button != 0) {
			rmb = false;
		}
	}

	public void keyPressed(int key, char c) {
		if (key == Input.KEY_F10) {
			editMode = !editMode;
		}
		if (key == Input.KEY_F11) {
			RESTING_BODDIES = !RESTING_BODDIES;
		}
		if (editMode) {
			if (key == Input.KEY_I) {
				addMessage("Inital state saved");
			}
			if (key == Input.KEY_I) {
				addMessage("Key block selected");
			}
			if (key == Input.KEY_T) {
				addMessage("Target state saved");
			}
		}
		
		if (key == Input.KEY_R) {
			try {
				reset();
			} catch (SlickException e) {
				Log.error(e);
			}
		}
		if (key == Input.KEY_F) {
			try {
				boolean isFullscreen = ((AppGameContainer) container).isFullscreen();
				((AppGameContainer) container).setFullscreen(!isFullscreen);
			} catch (SlickException e) {
				Log.error(e);
			}
		}
		if (key == Input.KEY_ESCAPE) {
			container.exit();
		}
	}
}
