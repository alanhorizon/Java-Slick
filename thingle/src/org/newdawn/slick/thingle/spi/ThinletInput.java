package org.newdawn.slick.thingle.spi;

/**
 * The input is expected to fire appropriate methods into the 
 * @author kglass
 *
 */
public interface ThinletInput {
	public static final int ENTER_KEY = 1;
	public static final int ESCAPE_KEY = 2;
	public static final int F6_KEY = 3;
	public static final int F8_KEY = 4;
	public static final int LEFT_KEY = 5;
	public static final int RIGHT_KEY = 6;
	public static final int UP_KEY = 7;
	public static final int DOWN_KEY = 8;
	public static final int F10_KEY = 9;
	public static final int TAB_KEY = 10;
	public static final int PRIOR_KEY = 11;
	public static final int NEXT_KEY = 12;
	public static final int HOME_KEY = 13;
	public static final int END_KEY = 14;
	public static final int RETURN_KEY = 15;
	public static final int BACK_KEY = 16;
	public static final int A_KEY = 17;
	public static final int DELETE_KEY = 18;
	public static final int X_KEY = 19;
	public static final int V_KEY = 20;
	public static final int C_KEY = 21;
	
	public void update(int delta);

	public boolean isShiftDown();
	
	public boolean isPopupTrigger();
	
	public boolean isControlDown();
	
	public boolean isAltDown();
	
	public int getKeyCode(int keyMapping);
	
	public void enable();
	
	public void disable();
}
