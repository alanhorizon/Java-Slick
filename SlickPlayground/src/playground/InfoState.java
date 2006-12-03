package playground;

import java.io.File;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import playground.games.GameRecord;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class InfoState extends State implements PodListener {
	public static final int ID = 3;

	private InfoPod infoPod;
	private Pod getPod;
	private Pod updatePod;
	private Pod playPod;
	
	private Playground playground;
	private PodGroup group;
	private boolean on;
	private int nextState;
	
	private GameRecord current;
	private String lastDataCache;
	private String lastLaunchCache;
	
	public InfoState(Playground app) {
		playground = app;
		
		group = new PodGroup();
		reinit();
		on = true;
	}
	
	public void setInfo(String dataCacheLocation, String launchCacheLocation, GameRecord info) {
		lastDataCache = dataCacheLocation;
		lastLaunchCache = launchCacheLocation;
		
		try {
			infoPod.setInfo(dataCacheLocation, info, playground.getGamesData().getLogoImage(info));
		} catch (SlickException e) {
			Log.error(e);
		}
		this.current = info;
		
		if (new File(launchCacheLocation+"/"+info.getID()).exists()) {
			playPod.setEnabled(true);
			getPod.setEnabled(false);
			updatePod.setEnabled(true);
		} else {
			playPod.setEnabled(false);
			getPod.setEnabled(true);
			updatePod.setEnabled(false);
		}
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
		if (pod == getPod) {
			playground.doDownload(current, true, true);
		}
		if (pod == playPod) {
			playground.doDownload(current, false, true);
		}
		if (pod == updatePod) {
			playground.doDownload(current, true, false);
		}
	}

	/**
	 * @see playground.State#backSelected()
	 */
	public void backSelected() {
		nextState = GamesListState.ID;
		group.move(0, -800);
	}

	/**
	 * @see playground.State#enter(int, playground.Playground)
	 */
	public void enter(int lastState, Playground app) {
		nextState = -1;
		group.setPosition(0, -800);
		group.move(0, 800);
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
		
		infoPod = new InfoPod(this, Resources.font3);
		playPod = new Pod(this, Resources.podImage, Resources.font, 140,500,160,40,"Play");
		getPod = new Pod(this, Resources.podImage, Resources.font, 320,500,160,40,"Get It!");
		updatePod = new Pod(this, Resources.podImage, Resources.font, 500,500,160,40,"Update");
		group.add(infoPod);
		group.add(playPod);
		group.add(getPod);
		group.add(updatePod);
		
		podGroups.add(group);
		
		if (current != null) {
			setInfo(lastDataCache, lastLaunchCache, current);
		}
	}
}
