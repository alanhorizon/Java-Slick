package virium;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class ZombieActorController implements ActorController {

	/**
	 * @see virium.ActorController#update(virium.GameContext, virium.Actor, int)
	 */
	public void update(GameContext context, Actor actor, int delta) {
		float dx = context.getPlayer1().getX() - actor.getX();
		float dy = context.getPlayer1().getY() - actor.getY();
		
		int tolerance = 3;
		
		if (dx < -tolerance) {
			dx = -1;
		}
		if (dx > tolerance) {
			dx = 1;
		}
		if (dy < -tolerance) {
			dy = -1;
		}
		if (dy > tolerance) {
			dy = 1;
		}
		
		actor.applyDirection((int) dx, (int) dy);
	}
	/**
	 * @see virium.ActorController#init(virium.Actor)
	 */
	public void init(Actor actor) {
		actor.setSpeed(0.05f);
	}

}
