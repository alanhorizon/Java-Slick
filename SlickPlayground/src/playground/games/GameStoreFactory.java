package playground.games;

import java.io.IOException;

import playground.games.local.LocalGameStore;
import playground.games.woogley.WoogleyGameStore;

/**
 * The factory used to produce the store which the client will use
 * to list the games available
 *
 * @author kevin
 */
public class GameStoreFactory {
	/**
	 * Get a game store for the current client
     *
     * @param cacheLocation The location we should cache to
	 * @return The new game store
	 * @throws IOException Indicates a failure to access the game store (or backing system)
	 */
	public static GameStore getGameStore(String cacheLocation) throws IOException {
		return new LocalGameStore(new WoogleyGameStore(), cacheLocation);
	}
}
