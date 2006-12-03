package playground;

import java.io.IOException;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.FastTrig;
import org.newdawn.slick.util.Log;

import playground.games.GameList;
import playground.games.GameRecord;
import playground.games.GameStore;
import playground.games.GameStoreFactory;
import playground.jnlp.LaunchConfig;
import playground.jnlp.Launcher;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class Playground extends BasicGame implements PodListener {
	private PodGroup alt = new PodGroup();
	
	private Image logo;
	private GameContainer container;
	private String launchCacheLocation;
	private String dataCacheLocation;
	private String storeCacheLocation;
	
	private Pod quitPod;
	private Pod nextPod;
	private Pod prevPod;
	
	private String version;
	private GameRecord current;
	private boolean reinit;
	private boolean waitingForDownload;
	private LaunchConfig config;
	private double ang;
	
	private GameStore store;
	private State currentState;
	private State[] states = new State[10];
	private GamesData gData;
	private GamesListState gamesListState;
	private int lastID;
	private InfoState infoState;
	
	private AppGameContainer app;
	
	public Playground() {
		super("Playground");

		storeCacheLocation = System.getProperty("user.home")+"/"+".playground";
		dataCacheLocation = System.getProperty("user.home")+"/"+".playground/imagecache";
		gData = new GamesData(dataCacheLocation);
		
		launchCacheLocation = System.getProperty("user.home")+"/"+".playground/cache";
		version = "JRE "+System.getProperty("java.version").substring(0,3);
	}
	
	public void setGamesList(GameList list) {
		gamesListState.setList(list);
	}
	
	public GamesData getGamesData() {
		return gData;
	}
	
	public void enterState(int id) {
		if (id < 0) {
			return;
		}
		
		int oldID = lastID;
		if (lastID == id) {
			return;
		}
		lastID = id;
		
		if (currentState != null) {
			currentState.leave(this);
		}
		
		State state = states[id];
		currentState = state;
		
		updateLabels(currentState);
		currentState.enter(oldID, this);
	}
	
	private void updateLabels(State state) {
		quitPod.setEnabled(currentState.getBackLabel() != null);
		prevPod.setEnabled(currentState.getPrevLabel() != null);
		nextPod.setEnabled(currentState.getNextLabel() != null);
		if (state.getBackLabel() != null) {
			quitPod.setLabel(state.getBackLabel());
		}
		if (state.getPrevLabel() != null) {
			prevPod.setLabel(state.getPrevLabel());
		}
		if (state.getNextLabel() != null) {
			nextPod.setLabel(state.getNextLabel());
		}
	}
	
	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		if (container instanceof AppGameContainer) {
			app = (AppGameContainer) container;
		}
		
		try {
			store = GameStoreFactory.getGameStore("",storeCacheLocation);
		} catch (IOException e) {
			Sys.alert("Error", "Unable to contact game server");
			container.exit();
			return;
		}
		
		container.setIcons(new String[] {"res/icon.png","res/icon32.png", "res/icon24.png"});
		
		alt = new PodGroup();
		this.container = container;
		
		Resources.init();
		
		logo = new Image("res/logo.png");
		container.setShowFPS(false);
		container.setVSync(true);

		GameList list = store.getGames();
		for (int i=0;i<list.size();i++) {
			gData.cache(list.getGame(i));
		}
		
		if (!reinit) {
			states[MainMenuState.ID] = new MainMenuState(this, store, reinit);
			states[GamesListState.ID] = gamesListState = new GamesListState(this, store);
			states[CategoriesState.ID] = new CategoriesState(this, store);
			states[InfoState.ID] = infoState = new InfoState(this);
			
			gamesListState.setList(store.getGames());
		} else {
			for (int i=0;i<states.length;i++) {
				if (states[i] != null) {
					states[i].reinit();
				}
			}
		}
		
		quitPod = new Pod(this, null, Resources.font, 365,570,60,20,"Quit");
		prevPod = new Pod(this, null, Resources.font, 265,570,60,20,"Prev");
		nextPod = new Pod(this, null, Resources.font, 465,570,60,20,"Next");
		
		prevPod.setEnabled(false);
		nextPod.setEnabled(false);
		alt.add(quitPod);
		alt.add(prevPod);
		alt.add(nextPod);
		
		if (reinit) {
			updateLabels(currentState);
		} else {
			enterState(MainMenuState.ID);
		}
		reinit = true;
	}

	public void exit() {
		container.exit();
	}
	
	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) throws SlickException {
		if (config != null) {
			try { Thread.sleep(100); } catch (Exception e) {}
			return;
		}
		ang += delta * 0.01f;
		
		if (waitingForDownload) {
			return;
		}
		
		currentState.update(container, delta);
		alt.update(container, delta);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) throws SlickException {
		Texture.bindNone();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(0.3f, 0.4f, 0.7f);
			GL11.glVertex3f(0, 0, 0);
			GL11.glColor3f(0.3f, 0.3f, 0.6f);
			GL11.glVertex3f(800, 0, 0);
			GL11.glColor3f(0.1f, 0.1f, 0.4f);
			GL11.glVertex3f(800, 600, 0);
			GL11.glColor3f(0.2f, 0.2f, 0.5f);
			GL11.glVertex3f(0, 600, 0);
		GL11.glEnd();
			
		logo.draw(600,400,200,200,new Color(0,0,0,0.1f));
		
		currentState.render(container, g);
		alt.draw(container, g);

		Texture.bindNone();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(0.2f, 0.2f, 0.4f);
			GL11.glVertex3f(0, 0, 0);
			GL11.glVertex3f(800, 0, 0);
			GL11.glColor3f(0.0f, 0.0f, 0.2f);
			GL11.glVertex3f(800, 31, 0);
			GL11.glVertex3f(0, 31, 0);
			
			GL11.glColor3f(0.4f, 0.4f, 0.7f);
			GL11.glVertex3f(0, 31, 0);
			GL11.glVertex3f(800, 31, 0);
			GL11.glColor3f(0.2f, 0.2f, 0.5f);
			GL11.glVertex3f(800, 33, 0);
			GL11.glVertex3f(0, 33, 0);
		GL11.glEnd();
		
		Resources.font2.drawString(6, 2, "Playground",Color.white);
		Resources.font2.drawString(container.getWidth()-10-Resources.font2.getWidth(version), 2, version,Color.white);

		if (waitingForDownload) {
			if (config != null) {
				waitingForDownload = false;
				LaunchConfig local = config;
				config = null;
				
				DisplayMode mode = Display.getDisplayMode();
				try {
					Display.update();
					Mouse.destroy();
					Keyboard.destroy();
					Display.destroy();
					
					local.run();
				} catch (Exception e) {
					Log.error(e);
				}
				
				try {
					Display.setDisplayMode(mode);
					Display.create();
					container.reinit();
				} catch (Exception e) {
					Log.error(e);
					System.exit(0);
				}
			} else {
				Texture.bindNone();
				GL11.glBegin(GL11.GL_QUADS);
					GL11.glColor4f(0,0,0,0.9f);
					GL11.glVertex3f(0, 0, 0);
					GL11.glVertex3f(800, 0, 0);
					GL11.glVertex3f(800, 600, 0);
					GL11.glVertex3f(0, 600, 0);
				GL11.glEnd();
				
				Resources.font.drawString(180,(int) (300+(FastTrig.cos(ang)*10)),"Getting game.. (this needs work)");
				try { Thread.sleep(50); } catch (Exception e) {};
			}
		}
	}
	
	/**
	 * @see playground.PodListener#podMoveCompleted(playground.Pod)
	 */
	public void podMoveCompleted(Pod pod) {
	}

	public void setInfo(GameRecord info) {
		 infoState.setInfo(dataCacheLocation, launchCacheLocation, info);
	}
	
	/**
	 * @see playground.PodListener#podSelected(playground.Pod, java.lang.String)
	 */
	public void podSelected(Pod pod, String name) {
		if (pod == prevPod) {
			currentState.prevSelected();
		}
		if (pod == nextPod) {
			currentState.nextSelected();
		}
		if (pod == quitPod) {
			currentState.backSelected();
		}
	}

	public void doDownload(final GameRecord current, final boolean update, final boolean run) {
		Thread t = new Thread() {
			public void run() {
				waitingForDownload = true;
				config = null;
				try {
					Launcher launcher = new Launcher(launchCacheLocation);
					LaunchConfig localConfig = launcher.getLaunch(current.getID(), current.getJNLP(), update);
					if (run) {
						config = localConfig;
					} else {
						waitingForDownload = false;
					}
				} catch (IOException e) {
					waitingForDownload = false;
				}
			}
		};
		t.setDaemon(false);
		t.start();
	}

	public void keyPressed(int key, char c) {
		if (key == Input.KEY_F1) {
			if (app != null) {
				try {
					app.setFullscreen(!app.isFullscreen());
				} catch (SlickException e) {
					Log.error(e);
				}
			}
		}
		if (key == Input.KEY_ESCAPE) {
			container.exit();
		}
	}
	
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new Playground(), 800, 600, false);
			container.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
