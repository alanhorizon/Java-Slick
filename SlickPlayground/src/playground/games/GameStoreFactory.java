package playground.games;

import java.io.IOException;

import playground.games.local.LocalGameStore;
import playground.games.woogley.WoogleyGameStore;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class GameStoreFactory {

	public static GameStore getGameStore(String type, String params) throws IOException {
		return new LocalGameStore(new WoogleyGameStore(), params);
	}
}
