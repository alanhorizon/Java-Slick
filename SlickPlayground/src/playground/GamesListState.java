package playground;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import playground.games.GameList;
import playground.games.GameRecord;
import playground.games.GameStore;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class GamesListState extends State implements PodListener {
	public static final int ID = 2;
		
	private Playground playground;
	private PodGroup group;
	private boolean on;
	private int nextState;
	private float offsetx = 0;
	private boolean vertical;
	private int lastState;
	private GameList lastList;
	
	public GamesListState(Playground app, GameStore store) {
		playground = app;
		
		group = new PodGroup();

		podGroups.add(group);
		on = true;
	}
	
	public void setList(GameList list) {
		lastList = list;
		
		group.clear();
		for (int i=0;i<list.size();i++) {
			group.add(getGameInfoPod(list.getGame(i)));
		}
		group.arrange();
		
		if (list.size() == 0) {
			group.add(new TextPod(this,  Resources.font, "No Games Listed"));
		}
	}
	
	private Pod getGameInfoPod(GameRecord info) {
		Pod pod = new Pod(this, Resources.podImage, Resources.font, 0, 0, "");
		pod.addImage(playground.getGamesData().getThumbImage(info));
		pod.setUserData(info);
		
		return pod;
	}
	
	/**
	 * @see playground.State#getBackLabel()
	 */
	public String getBackLabel() {
		return "Back";
	}

	/**
	 * @see playground.State#getNextLabel()
	 */
	public String getNextLabel() {
		return "Next";
	}

	/**
	 * @see playground.State#getPrevLabel()
	 */
	public String getPrevLabel() {
		return "Prev";
	}

	/**
	 * @see playground.PodListener#podMoveCompleted(playground.Pod)
	 */
	public void podMoveCompleted(Pod pod) {
		if (nextState > 0) {
			playground.enterState(nextState);
		}
	}

	/**
	 * @see playground.PodListener#podSelected(playground.Pod, java.lang.String)
	 */
	public void podSelected(Pod pod, String name) {
		if (pod.getUserData() != null) {
			GameRecord record = (GameRecord) pod.getUserData();
			playground.setInfo(record);
			nextState = InfoState.ID;
			group.move(0, 800);
			vertical = true;
		}
	}

	/**
	 * @see playground.State#backSelected()
	 */
	public void backSelected() {
		nextState = lastState;
		group.move(800-offsetx, 0);
	}

	/**
	 * @see playground.State#enter(int, playground.Playground)
	 */
	public void enter(int lastState, Playground app) {
		if ((lastState == MainMenuState.ID) || (lastState == CategoriesState.ID)) {
			this.lastState = lastState;
		} 
		
		nextState = -1;
		if (vertical) {
			group.move(0, -800);
		} else {
			group.setPosition(800,0);
			group.move(-800+offsetx, 0);
		}
		vertical = false;
	}

	/**
	 * @see playground.State#leave(playground.Playground)
	 */
	public void leave(Playground app) {
	}

	/**
	 * @see playground.State#nextSelected()
	 */
	public void nextSelected() {
		if (offsetx < (group.width()+2) * 255) {
			return;
		}
		nextState = -1;
		offsetx -= 255;
		group.move(-255, 0);
	}

	/**
	 * @see playground.State#prevSelected()
	 */
	public void prevSelected() {
		if (offsetx >= 0) {
			return;
		}
		nextState = -1;
		offsetx += 255;
		group.move(255, 0);
	}

	/**
	 * @see playground.State#reinit()
	 */
	public void reinit() {
		setList(lastList);
	}
}
