package org.newdawn.slick.geom;

/**
 * Implemenation of a bunch of maths functions to do with lines. Note
 * that lines can't be used as dynamic shapes right now - also collision 
 * with the end of a line is undefined.
 * 
 * @author Kevin Glass
 */
public strictfp class Line extends Shape {
	/** The start point of the line */
	private Vector2f start;
	/** The end point of the line */
	private Vector2f end;
	/** The vector between the two points */
	private Vector2f vec;
	/** The length of the line squared */
	private float lenSquared;
	
	/** Temporary storage - declared globally to reduce GC */
	private Vector2f loc = new Vector2f(0,0);
	/** Temporary storage - declared globally to reduce GC */
	private Vector2f v = new Vector2f(0,0);
	/** Temporary storage - declared globally to reduce GC */
	private Vector2f v2 = new Vector2f(0,0);
	/** Temporary storage - declared globally to reduce GC */
	private Vector2f proj = new Vector2f(0,0);

	/** Temporary storage - declared globally to reduce GC */
	private Vector2f closest = new Vector2f(0,0);
	/** Temporary storage - declared globally to reduce GC */
	private Vector2f other = new Vector2f(0,0);

	/** True if this line blocks on the outer edge */
	private boolean outerEdge = true;
	/** True if this line blocks on the inner edge */
	private boolean innerEdge = true;
	
	/**
	 * Create a new line based on the origin and a single point
	 * 
	 * @param x The end point of the line
	 * @param y The end point of the line
	 * @param inner True if this line blocks on it's inner edge
	 * @param outer True if this line blocks on it's outer edge
	 */
	public Line(float x, float y, boolean inner, boolean outer) {
		this(0,0,x,y);
	}

	/**
	 * Create a new line based on the origin and a single point
	 * 
	 * @param x The end point of the line
	 * @param y The end point of the line
	 */
	public Line(float x, float y) {
		this(x,y,true,true);
	}
	
	/**
	 * Create a new line based on two points
	 * 
	 * @param x1 The x coordinate of the start point
	 * @param y1 The y coordinate of the start point
	 * @param x2 The x coordinate of the end point
	 * @param y2 The y coordinate of the end point
	 */
	public Line(float x1, float y1, float x2, float y2) {
		this(new Vector2f(x1,y1), new Vector2f(x2,y2));
	}

	/**
	 * Create a line with relative second point
	 * 
	 * @param x1 The x coordinate of the start point
	 * @param y1 The y coordinate of the start point
	 * @param dx The x change to get to the second point
	 * @param dy The y change to get to the second point
	 * @param dummy A dummy value
	 */
	public Line(float x1, float y1, float dx, float dy, boolean dummy) {
		this(new Vector2f(x1,y1), new Vector2f(x1+dx,y1+dy));
	}
	
	/**
	 * Create a new line based on two points
	 * 
	 * @param start The start point
	 * @param end The end point
	 */
	public Line(Vector2f start, Vector2f end) {
		super();
		
		set(start,end);
	}
	
	/**
	 * Get the start point of the line
	 * 
	 * @return The start point of the line
	 */
	public Vector2f getStart() {
		return start;
	}

	/**
	 * Get the end point of the line
	 * 
	 * @return The end point of the line
	 */
	public Vector2f getEnd() {
		return end;
	}
	
	/**
	 * Find the length of the line
	 * 
	 * @return The the length of the line
	 */
	public float length() {
		return vec.length();
	}
	
	/**
	 * Find the length of the line squared (cheaper and good for comparisons)
	 * 
	 * @return The length of the line squared
	 */
	public float lengthSquared() {
		return vec.lengthSquared();
	}
	
	/**
	 * Configure the line
	 * 
	 * @param start The start point of the line
	 * @param end The end point of the line
	 */
	public void set(Vector2f start, Vector2f end) {
		super.pointsDirty = true;
		this.start = start;
		this.end = end;
		
		vec = new Vector2f(end);
		vec.sub(start);
		
		lenSquared = vec.length();
		lenSquared *= lenSquared;
	}
	
	/**
	 * Get the x direction of this line
	 * 
	 * @return The x direction of this line
	 */
	public float getDX() {
		return end.getX() - start.getX();
	}

	/**
	 * Get the y direction of this line
	 * 
	 * @return The y direction of this line
	 */
	public float getDY() {
		return end.getY() - start.getY();
	}
	
	/**
	 * Get the x coordinate of the start point
	 * 
	 * @return The x coordinate of the start point
	 */
	public float getX1() {
		return start.getX();
	}

	/**
	 * Get the y coordinate of the start point
	 * 
	 * @return The y coordinate of the start point
	 */
	public float getY1() {
		return start.getY();
	}

	/**
	 * Get the x coordinate of the end point
	 * 
	 * @return The x coordinate of the end point
	 */
	public float getX2() {
		return end.getX();
	}

	/**
	 * Get the y coordinate of the end point
	 * 
	 * @return The y coordinate of the end point
	 */
	public float getY2() {
		return end.getY();
	}
	
	/**
	 * Get the shortest distance from a point to this line
	 * 
	 * @param point The point from which we want the distance
	 * @return The distance from the line to the point
	 */
	public float distance(Vector2f point) {
		return (float) Math.sqrt(distanceSquared(point));
	}
	
	/**
	 * Check if the given point is on the line
	 * 
	 * @param point The point to check
	 * @return True if the point is on this line
	 */
	public boolean on(Vector2f point) {
		return distanceSquared(point) == 0;
	}
	
	/** 
	 * Get the shortest distance squared from a point to this line
	 * 
	 * @param point The point from which we want the distance
	 * @return The distance squared from the line to the point
	 */
	public float distanceSquared(Vector2f point) {
		getClosestPoint(point, closest);
		closest.sub(point);
		
		float result = closest.lengthSquared();
		
		return result;
	}
	
	/**
	 * Get the closest point on the line to a given point
	 * 
	 * @param point The point which we want to project
	 * @param result The point on the line closest to the given point
	 */
	public void getClosestPoint(Vector2f point, Vector2f result) {
		loc.set(point);
		loc.sub(start);
		
		v.set(vec);
		v2.set(vec);
		v2.scale(-1);
		
		v.normalise();
		loc.projectOntoUnit(v, proj);
		if (proj.lengthSquared() > vec.lengthSquared()) {
			result.set(end);
			return;
		}
		proj.add(start);
		
		other.set(proj);
		other.sub(end);
		if (other.lengthSquared() > vec.lengthSquared()) {
			result.set(start);
			return;
		}
		
		result.set(proj);
		return;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[Line "+start+","+end+"]";
	}

	/**
	 * Intersect this line with another
	 * 
	 * @param other The other line we should intersect with
	 * @return The intersection point or null if the lines are parallel
	 */
	public Vector2f intersect(Line other) {
		return intersect(other, false);
	}
	
	/**
	 * Intersect this line with another
	 * 
	 * @param other The other line we should intersect with
	 * @param limit True if the collision is limited to the extent of the lines
	 * @return The intersection point or null if the lines don't intersect
	 */
	public Vector2f intersect(Line other, boolean limit) {
		float dx1 = end.getX() - start.getX();
		float dx2 = other.end.getX() - other.start.getX();
		float dy1 = end.getY() - start.getY();
		float dy2 = other.end.getY() - other.start.getY();
		float denom = (dy2 * dx1) - (dx2 * dy1);
		
		if (denom == 0) {
			return null;
		}
		
		float ua = (dx2 * (start.getY() - other.start.getY())) - (dy2 * (start.getX() - other.start.getX()));
		ua /= denom;
		float ub = (dx1 * (start.getY() - other.start.getY())) - (dy1 * (start.getX() - other.start.getX()));
		ub /= denom;
		
		if ((limit) && ((ua < 0) || (ua > 1) || (ub < 0) || (ub > 1))) {
			return null;			
		}
		
		float u = ua;
		
		float ix = start.getX() + (u * (end.getX() - start.getX()));
		float iy = start.getY() + (u * (end.getY() - start.getY()));
		
		return new Vector2f(ix,iy);
	}

	/**
	 * @see org.newdawn.slick.geom.Shape#createPoints()
	 */
	protected void createPoints() {
		points = new float[4];
		points[0] = getX1();
		points[1] = getY1();
		points[2] = getX2();
		points[3] = getY2();
	}

	/**
	 * @see org.newdawn.slick.geom.Shape#transform(org.newdawn.slick.geom.Transform)
	 */
	public Shape transform(Transform transform) {
		float[] temp = new float[4];
		transform.transform(points, 0, temp, 0, 2);
		
		return new Line(temp[0],temp[1],temp[2],temp[3]);
	}
	
	/**
	 * @see org.newdawn.slick.geom.Shape#closed()
	 */
	public boolean closed() {
		return false;
	}
}
