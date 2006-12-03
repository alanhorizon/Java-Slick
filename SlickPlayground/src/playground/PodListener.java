package playground;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public interface PodListener {

	public void podSelected(Pod pod, String name);
	
	public void podMoveCompleted(Pod pod);
}
