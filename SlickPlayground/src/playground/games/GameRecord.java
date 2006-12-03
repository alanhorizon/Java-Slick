package playground.games;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public interface GameRecord {

	public String getName();
	
	public String getAuthor();
	
	public String getDescription();
	
	public float getRating();
	
	public float getRequiredJavaVersion();
	
	public float getRequiredOpenGLVersion();
	
	public String getThumbURL();
	
	public String getLogoURL();
	
	public String getJNLP();
	
	public String getComments();
	
	public String getID();
	
	public void rate(float rate);
	
	public void comment(String comment);
	
	public String getCategory();
}
