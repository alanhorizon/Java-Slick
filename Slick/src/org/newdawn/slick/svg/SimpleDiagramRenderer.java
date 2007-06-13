package org.newdawn.slick.svg;

import org.newdawn.slick.Graphics;

/**
 * A very primtive implementation for rendering a diagram. This simply
 * sticks the shapes on the screen in the right fill and stroke colours
 *
 * @author kevin
 */
public class SimpleDiagramRenderer {
	/** The diagram to be rendered */
	public Diagram diagram;
	
	/**
	 * Create a new simple renderer
	 * 
	 * @param diagram The diagram to be rendered
	 */
	public SimpleDiagramRenderer(Diagram diagram) {
		this.diagram = diagram;
	}
	
	/**
	 * Render the diagram to the given graphics context
	 * 
	 * @param g The graphics context to which we should render the diagram
	 */
	public void render(Graphics g) {
		for (int i=0;i<diagram.getFigureCount();i++) {
			Figure figure = diagram.getFigure(i);
			
			if (figure.getData().isColor(NonGeometricData.FILL)) {
				g.setColor(figure.getData().getAsColor(NonGeometricData.FILL));
				g.fill(diagram.getFigure(i).getShape());
			}
			if (figure.getData().isColor(NonGeometricData.STROKE)) {
				g.setColor(figure.getData().getAsColor(NonGeometricData.STROKE));
				g.draw(diagram.getFigure(i).getShape());
			}
		}
	}
}