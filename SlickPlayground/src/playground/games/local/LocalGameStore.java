package playground.games.local;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.newdawn.slick.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import playground.games.GameList;
import playground.games.GameRecord;
import playground.games.GameStore;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class LocalGameStore implements GameStore {
	private String[] categories = new String[] {"Unspecified"};
	private GList allgames = new GList();
	private File cacheFile;
	private GameStore remoteStore;
	private long lastUpdated;
	
	public LocalGameStore(GameStore remote, String cacheLocation) throws IOException {
		cacheFile = new File(cacheLocation, "store.xml");
		remoteStore = remote;
		
		if (!cacheFile.exists()) {
			Log.info("Getting initial games from remote game store");
			update();
		} else {
			Log.info("Loading cached games list");
			load();
		}
	}
	
	/**
	 * @see playground.games.GameStore#getCategories()
	 */
	public String[] getCategories() {
		return categories;
	}

	/**
	 * @see playground.games.GameStore#getGames(java.lang.String)
	 */
	public GameList getGames(String category) {
		GList list = new GList();
		
		for (int i=0;i<allgames.size();i++) {
			if (allgames.getGame(i).getCategory().equals(category)) {
				list.add(allgames.getGame(i));
			}
		}
		
		return list;
	}

	private class GList extends ArrayList implements GameList {

		/**
		 * @see playground.games.GameList#getGame(int)
		 */
		public GameRecord getGame(int index) {
			return (GameRecord) get(index);
		}
		
	}

	/**
	 * @see playground.games.GameStore#getGames()
	 */
	public GameList getGames() {
		return allgames;
	}

	/**
	 * @see playground.games.GameStore#update()
	 */
	public void update() throws IOException {
		try {
			remoteStore.update();
		} catch (IOException e) {
			Log.error(e);
			Log.warn("Failed to update remote store");
			return;
		}

		try {
			cache();
			load();
		} catch (IOException e) {
			Log.error(e);
			Log.warn("Failed to cache remote store");
		}
	}
	
	/**
	 * Load the locally stored games information
	 * 
	 * @throws IOException Indicates a failure to read the cache
	 */
	private void load() throws IOException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(cacheFile);
			Element root = document.getDocumentElement();
			
			lastUpdated = Long.parseLong(root.getAttribute("lastCache"));
			
			NodeList cats = root.getElementsByTagName("category");
			categories = new String[cats.getLength()];
			for (int i=0;i<categories.length;i++) {
				Element cat = (Element) cats.item(i);
				categories[i] = cat.getAttribute("name");
			}
			
			allgames.clear();
			NodeList games = root.getElementsByTagName("game");
			for (int i=0;i<games.getLength();i++) {
				Element element = (Element) games.item(i);
				allgames.add(elementToGame(element));
			}
		} catch (Exception e) {
			Log.error(e);
			throw new IOException("Failed to load cached store");
		}
	}
	
	/**
	 * Cache the remote store locally
	 * 
	 * @throws IOException Indicates a failure to write the cache
	 */
	private void cache() throws IOException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.newDocument();
			Element root =  document.createElement("store");
			
			lastUpdated = System.currentTimeMillis();
			root.setAttribute("lastCache", ""+lastUpdated);
			
			String[] cats = remoteStore.getCategories();
			for (int i=0;i<cats.length;i++) {
				Element cat = document.createElement("category");
				cat.setAttribute("name", cats[i]);
				root.appendChild(cat);
			}
			
			GameList list = remoteStore.getGames();
			for (int i=0;i<list.size();i++) {
				root.appendChild(gameToElement(document, list.getGame(i)));
			}

			document.appendChild(root);
			
            Result result = new StreamResult(new OutputStreamWriter(new FileOutputStream(cacheFile), "utf-8"));   
			DOMSource source = new DOMSource(document);
			
            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setAttribute("indent-number", new Integer(2));
			Transformer xformer = factory.newTransformer();
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
            xformer.transform(source, result);
		} catch (Exception e) {
			Log.error(e);
			throw new IOException("Failed to cache store");
		}
	}
	
	private GameRecord elementToGame(Element element) {
		String id = element.getAttribute("id");
		String name = element.getAttribute("name");
		String category = element.getAttribute("category");
		String author = element.getAttribute("author");
		String comments = element.getAttribute("comments");
		String description = element.getAttribute("description");
		String jnlp = element.getAttribute("jnlp");
		String logo = element.getAttribute("logo");
		String thumb = element.getAttribute("thumb");
		float rating = Float.parseFloat(element.getAttribute("rating"));
		float java = Float.parseFloat(element.getAttribute("java"));
		float opengl = Float.parseFloat(element.getAttribute("opengl"));
		
		return new GameInfo(id, name, author, description, ""+java,
							jnlp, thumb, logo, category, comments, ""+rating, ""+opengl);
	}
	
	/**
	 * Create an XML element represeting a given game
	 * 
	 * @param doc The document the element is created within
	 * @param record The record describing the game
	 * @return The element reprsenting the game in XML
	 */
	private Element gameToElement(Document doc, GameRecord record) {
		Element element = doc.createElement("game");

		element.setAttribute("id", record.getID());
		element.setAttribute("name", record.getName());
		element.setAttribute("author", record.getAuthor());
		element.setAttribute("comments", record.getComments());
		element.setAttribute("category", record.getCategory());
		element.setAttribute("description", record.getDescription());
		element.setAttribute("jnlp", record.getJNLP());
		element.setAttribute("logo", record.getLogoURL());
		element.setAttribute("thumb", record.getThumbURL());
		element.setAttribute("rating", ""+record.getRating());
		element.setAttribute("java", ""+record.getRequiredJavaVersion());
		element.setAttribute("opengl", ""+record.getRequiredOpenGLVersion());
		
		return element;
	}

	/**
	 * @see playground.games.GameStore#lastUpdated()
	 */
	public long lastUpdated() {
		return lastUpdated;
	}
}
