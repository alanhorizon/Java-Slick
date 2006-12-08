package playground;

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

import org.newdawn.slick.Color;
import org.newdawn.slick.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class LocalSettings {
	private static ArrayList faves = new ArrayList();
	private static Color theme = Color.blue;
	private static boolean fullscreen = false;
	private static File file;
	
	public static void init(String cacheLocation) {
		File loc = new File(cacheLocation);
		file = new File(loc, "config.xml");
	}
	
	public static void load() throws IOException {
		if (file.exists()) {
			try {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = builder.parse(file);
				Element root = doc.getDocumentElement();
				fullscreen = "true".equals(root.getAttribute("fullscreen"));
				
				NodeList list = root.getElementsByTagName("theme");
				
				if (list.getLength() > 0) {
					Element themeElement = (Element) list.item(0);
					float r = Float.parseFloat(themeElement.getAttribute("r"));
					float g = Float.parseFloat(themeElement.getAttribute("g"));
					float b = Float.parseFloat(themeElement.getAttribute("b"));
					theme = new Color(r,g,b,1f);
				}
				
				NodeList favesList = root.getElementsByTagName("fave");
				for (int i=0;i<favesList.getLength();i++) {
					Element f = (Element) favesList.item(i);
					faves.add(f.getAttribute("id"));
				}
			} catch (Exception e) {
				Log.error(e);
				throw new IOException(e.getMessage());
			}
		}
	}
	
	public static void save() throws IOException {
		file.getParentFile().mkdirs();

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.newDocument();
			
			Element root = doc.createElement("config");
			root.setAttribute("fullscreen",""+fullscreen);
			Element t = doc.createElement("theme");
			t.setAttribute("r", ""+theme.r);
			t.setAttribute("g", ""+theme.g);
			t.setAttribute("b", ""+theme.b);
			root.appendChild(t);
			
			for (int i=0;i<faves.size();i++) {
				Element f = doc.createElement("fave");
				f.setAttribute("id", ""+faves.get(i));
				root.appendChild(f);
			}
			doc.appendChild(root);

            Result result = new StreamResult(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));   
			DOMSource source = new DOMSource(doc);
			
            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setAttribute("indent-number", new Integer(2));
			Transformer xformer = factory.newTransformer();
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
            xformer.transform(source, result);
		} catch (Exception e) {
			Log.error(e);
			throw new IOException(e.getMessage());
		}
	}
	
	public static void setFullscreen(boolean fs) {
		fullscreen = fs;
	}
	
	public static boolean getFullscreen() {
		return fullscreen;
	}
	
	public static ArrayList getFaves() {
		return faves;
	}
	
	public static void addFave(String id) {
		faves.add(id);
	}
	
	public static void removeFace(String id) {
		faves.remove(id);
	}
	
	public static Color getTheme() {
		return theme;
	}
	
	public static void setTheme(Color col) {
		theme = col;
	}
}
