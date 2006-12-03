package playground;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public class PodGroup {
	private ArrayList pods = new ArrayList();
	private double vx;
	private double vy;
	private double dx;
	private double dy;
	private double x;
	private double y;
	
	public PodGroup() {
		
	}
	
	public void arrange() {
		arrange(50,50,255,180);
	}
	
	public void arrange(int xoffset, int yoffset, int width, int height) {
		if (pods.size() <= 9) {
			for (int y=0;y<3;y++) {
				for (int x=0;x<3;x++) {
					int index = (y*3)+x;
					if (index < pods.size()) {
						Pod pod = (Pod) pods.get(index);
						pod.setX(xoffset+(x*width));
						pod.setY(yoffset+(y*height));
					} 
				}
			}
		} else {
			for (int x=0;x<((pods.size()-1)/3)+1;x++) {
				for (int y=0;y<3;y++) {
					int index = (x*3)+y;
					if (index < pods.size()) {
						Pod pod = (Pod) pods.get(index);
						pod.setX(xoffset+(x*width));
						pod.setY(yoffset+(y*height));
					}
				}
			}
		}
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(Pod pod) {
		pod.setGroup(this);
		pods.add(pod);
	}
	
	public boolean contains(Pod pod) {
		return pods.contains(pod);
	}
	
	public void update(GameContainer container, int d) {
		double mx = 0;
		double my = 0;
		
		if ((dx != 0) || (dy != 0)) {
			double delta = (d * 0.2);
			if (dx < 0) {
				vx -= delta;
			}
			if (dx > 0) {
				vx += delta;
			}
			if (dx == 0) {
				vx = 0;
			}
			if (dy < 0) {
				vy -= delta;
			}
			if (dy > 0) {
				vy += delta;
			}
			if (dy == 0) {
				vy = 0;
			}
			
			mx = vx * delta * 0.1;
			my = vy * delta * 0.1;
			
			if (Math.abs(mx) > Math.abs(dx)) {
				mx = dx;
				vx = 0;
			}
			if (Math.abs(my) > Math.abs(dy)) {
				my = dy;
				vy = 0;
			}

			dx -= mx;
			dy -= my;
			if ((dx == 0) && (dy == 0)) {
				for (int i=0;i<pods.size();i++) {
					((Pod) pods.get(i)).fireMoveComplete();
				}
			}
		} 
		
		x += mx;
		y += my;
		for (int i=0;i<pods.size();i++) {
			((Pod) pods.get(i)).update(container, d, (float) x, (float) y);
		}
	}

	public int width() {
		return pods.size() / 3;
	}
	
	public boolean moving() {
		return ((dx != 0) || (dy != 0));
	}
	
	public void clear() {
		pods.clear();
	}
	
	public void move(double dx, double dy) {
		if (moving()) {
			return;
		}
		this.dx = dx;
		this.dy = dy;
		vx = 0;
		vy = 0;
	}
	
	public void draw(GameContainer container, Graphics g) {
		g.translate((float) x,(float) y);
		for (int i=0;i<pods.size();i++) {
			((Pod) pods.get(i)).draw(container, g);
		}
		g.translate((float) -x,(float) -y);
	}
}
