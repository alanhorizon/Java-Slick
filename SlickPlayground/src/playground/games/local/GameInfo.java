package playground.games.local;

import org.newdawn.slick.SlickException;

import playground.games.GameRecord;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class GameInfo implements GameRecord {
	private String id;
	private String name;
	private String author;
	private String description;
	private float javaVersion;
	private String comments;
	private float rating;
	private float glVersion;
	private String jnlp;
	
	private String thumb;
	private String logo;
	private String category;
	
	public GameInfo(String id, String name, String author, String description, String javaVersion,
					String jnlp, String thumbUrl, String logoUrl, String category, String comments,
					String rating, String glVersion) {
		this.category = category;
		this.id = id;
		this.name = name;
		this.author = author;
		this.comments = comments;
		this.glVersion = Float.parseFloat(glVersion);
		this.rating = Float.parseFloat(rating);
		this.description = description;
		this.javaVersion = Float.parseFloat(javaVersion);
		this.jnlp = jnlp;
		
		this.thumb = thumbUrl;
		this.logo = logoUrl;
	}
	
	public String getThumbURL() {
		return thumb;
	}
	
	public String getLogoURL() {
		return logo;
	}
	
	public String getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getJNLP() {
		return jnlp;
	}

	/**
	 * @see playground.games.GameRecord#comment(java.lang.String)
	 */
	public void comment(String comment) {
	}

	/**
	 * @see playground.games.GameRecord#getCategory()
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @see playground.games.GameRecord#getComments()
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @see playground.games.GameRecord#getRating()
	 */
	public float getRating() {
		return rating;
	}

	/**
	 * @see playground.games.GameRecord#getRequiredJavaVersion()
	 */
	public float getRequiredJavaVersion() {
		return javaVersion;
	}

	/**
	 * @see playground.games.GameRecord#getRequiredOpenGLVersion()
	 */
	public float getRequiredOpenGLVersion() {
		return glVersion;
	}

	/**
	 * @see playground.games.GameRecord#rate(float)
	 */
	public void rate(float rate) {
	}
}
