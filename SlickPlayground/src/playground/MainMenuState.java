package playground;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.FastTrig;
import org.newdawn.slick.util.Log;

import playground.games.GameStore;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class MainMenuState extends State implements PodListener {
	public static final int ID = 1;
		
	private Playground playground;
	private PodGroup group;
	private boolean on;
	private int nextState;
	private GameStore store;
	private boolean updating;
	private float ang;
	private SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	public MainMenuState(Playground app, GameStore store, boolean reinit) {
		playground = app;
	
		group = new PodGroup();
		this.store = store;
		reinit();
		on = !reinit;
	}

	/**
	 * @see playground.State#getBackLabel()
	 */
	public String getBackLabel() {
		return null;
	}

	/**
	 * @see playground.State#getNextLabel()
	 */
	public String getNextLabel() {
		return null;
	}

	/**
	 * @see playground.State#getPrevLabel()
	 */
	public String getPrevLabel() {
		return null;
	}

	/**
	 * @see playground.PodListener#podMoveCompleted(playground.Pod)
	 */
	public void podMoveCompleted(Pod pod) {
		playground.enterState(nextState);
	}

	/**
	 * @see playground.PodListener#podSelected(playground.Pod, java.lang.String)
	 */
	public void podSelected(Pod pod, String name) {
		if (name.equals("All Games")) {
			nextState = GamesListState.ID;
			playground.setGamesList(store.getGames());
			group.move(-800, 0);
		}
		if (name.equals("Categories")) {
			nextState = CategoriesState.ID;
			group.move(-800, 0);
		}
		if (name.equals("Update")) {
			updating = true;
			Thread t = new Thread() {
				public void run() {
					try {
						try {
							store.update();
						} catch (IOException e) {
							Log.error(e);
						}
						
						playground.getGamesData().update(store);
					} catch (Throwable e) {
						Log.error(e);
					}
					updating = false;
				}
			};
			t.start();
		}
		if (name.equals("Quit")) {
			playground.exit();
		}
	}

	/**
	 * @see playground.State#backSelected()
	 */
	public void backSelected() {
		playground.exit();
	}

	/**
	 * @see playground.State#enter(int, playground.Playground)
	 */
	public void enter(int lastState, Playground app) {
		nextState = -1;
		if (!on) {
			group.setPosition(-800, 0);
			group.move(800, 0);
			on = true;
		}
	}

	/**
	 * @see playground.State#leave(playground.Playground)
	 */
	public void leave(Playground app) {
		 on = false;
	}

	/**
	 * @see playground.State#nextSelected()
	 */
	public void nextSelected() {
	}

	/**
	 * @see playground.State#prevSelected()
	 */
	public void prevSelected() {
	}

	/**
	 * @see playground.State#reinit()
	 */
	public void reinit() {
		podGroups.clear();
		group.clear();
		
		try {
			Pod pod = new Pod(this, Resources.podImage, Resources.font, 90,150, "All Games");
			pod.addImage(new Image("res/gamepad.png"));
			group.add(pod);
			pod = new Pod(this, Resources.podImage, Resources.font, 300,150, "Categories");
			pod.addImage(new Image("res/cat.png"));
			group.add(pod);
			pod = new Pod(this, Resources.podImage, Resources.font, 510,150, "Favourites");
			pod.addImage(new Image("res/faves.png"));
			group.add(pod);
			pod = new Pod(this, Resources.podImage, Resources.font, 90,300, "Update");
			pod.addImage(new Image("res/update.png"));
			group.add(pod);
			pod = new Pod(this, Resources.podImage, Resources.font, 300,300, "Setup");
			pod.addImage(new Image("res/help.png"));
			group.add(pod);
			pod = new Pod(this, Resources.podImage, Resources.font, 510,300, "Quit");
			pod.addImage(new Image("res/exit.png"));
			group.add(pod);
			
			podGroups.add(group);
		} catch (SlickException e) {
			Log.error(e);
		}
	}

	public void render(GameContainer container, Graphics g) {
		super.render(container, g);

		Date date = new Date(store.lastUpdated());
		
		Resources.font3.drawString(6, 575, "Last Update: "+format.format(date),new Color(0,0,0,0.3f));
	
		if (updating) {
			Texture.bindNone();
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glColor4f(0,0,0,0.9f);
				GL11.glVertex3f(0, 0, 0);
				GL11.glVertex3f(800, 0, 0);
				GL11.glVertex3f(800, 600, 0);
				GL11.glVertex3f(0, 600, 0);
			GL11.glEnd();
			
			Resources.font.drawString(180,(int) (300+(FastTrig.cos(ang)*10)),"Updating Files.. (this needs work)");
			try { Thread.sleep(50); } catch (Exception e) {};
		}
	}
	
	public void update(GameContainer container, int delta) {
		ang += delta * 0.01f;
		if (updating) {
			return;
		}
		super.update(container, delta);
	}

}
