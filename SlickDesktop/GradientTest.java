package slickdesktop;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.SpecularGradientPainter;
import org.jvnet.substance.theme.SubstanceBottleGreenTheme;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

/**
 * A test for gradient fill on polygons
 *
 * @author kevin
 */
public class GradientTest extends BasicGame {
	/** The container for the test */
	private GameContainer container;
	/** The paint we'll use */
	private GradientFill gradient;
	/** The paint we'll use */
	private GradientFill gradient2;
	/** The paint we'll use */
	private GradientFill gradient4;
	/** The shape to render */
	private Rectangle rect;
	/** The shape to render */
	private Rectangle center;
	/** The shape to render */
	private RoundedRectangle round;
	/** The shape to render */
	private RoundedRectangle round2;
	/** The shape to render */
	private Polygon poly;
	/** The angle of rotation */
	private float ang;
	
        private SlickDesktop desktop = null;

        private Image img = null;
        private int renderInterval = (1000/30);
        private int tick = 1000;

        /**
	 * Create a new gradient test
	 */
	public GradientTest() {
		super("Gradient Test");
	}
	
	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
        desktop = new SlickDesktop( "Test", 800, 600 );
        desktop.getJDesktop().setOpaque( true );
        desktop.getJDesktop().setBackground( new java.awt.Color(0.0f, 0.0f, 0.0f, 0.0f) );
        desktop.setInput( container.getInput() );
        
        createFrame();
        createFrame();
        createFrame();
        
        JButton b = new JButton( "test" );
        b.setBounds( 200, 0, 75, 20 );
        desktop.getJDesktop().add( b );

        this.container = container;
	
		rect = new Rectangle(400,100,200,150);
		round = new RoundedRectangle(150,100,200,150,50);
		round2 = new RoundedRectangle(150,300,200,150,50);
		center = new Rectangle(350,250,100,100);
		
		poly = new Polygon();
		poly.addPoint(400,350);
		poly.addPoint(550,320);
		poly.addPoint(600,380);
		poly.addPoint(620,450);
		poly.addPoint(500,450);
		
		gradient = new GradientFill(0,-75,Color.red,0,75,Color.yellow);
		gradient2 = new GradientFill(0,-75,Color.blue,0,75,Color.white);
		gradient4 = new GradientFill(-50,-40,Color.green,50,40,Color.cyan);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		
		g.rotate(400, 300, ang);
		g.fill(rect, gradient);
		g.fill(round, gradient);
		g.fill(poly, gradient2);
		g.fill(center, gradient4);

		g.setAntiAlias(true);
		g.setLineWidth(10);
		g.draw(round2, gradient2);
		g.setLineWidth(2);
		g.draw(poly, gradient);
		g.setAntiAlias(false);
		
		g.fill(center, gradient4);
		g.setAntiAlias(true);
		g.setColor(Color.black);
		g.draw(center);
		g.setAntiAlias(false);
                
                g.resetTransform();

        if( tick > renderInterval )
        {
            tick = 0;
            img = desktop.render( container, g );
        }
        if( img != null )
        {
            g.drawImage( img, 0, 0 );
        }
        }

    private void createFrame()
    {
        TestIFrame iframe = new TestIFrame();
        iframe.setVisible(true); //necessary as of 1.3
        desktop.getJDesktop().add(iframe);
        try {
            iframe.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }

        /**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) {
		ang += (delta * 0.01f);
        tick += delta;
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
//        try
//        {
//            UIManager.setLookAndFeel(new SubstanceLookAndFeel());
//            SubstanceLookAndFeel.setCurrentGradientPainter( new SpecularGradientPainter() );
//            SubstanceLookAndFeel.setCurrentTheme( 
//                    new SubstanceBottleGreenTheme()
//            );
//            UIManager.setLookAndFeel(new SubstanceLookAndFeel());
//            
//            JFrame.setDefaultLookAndFeelDecorated( true );
//            JDialog.setDefaultLookAndFeelDecorated( true );
//        } 
//        catch( UnsupportedLookAndFeelException ex )
//        {
//            ex.printStackTrace();
//        }

            try {
			AppGameContainer container = new AppGameContainer(new GradientTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see org.newdawn.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			container.exit();
		}
	}
}
