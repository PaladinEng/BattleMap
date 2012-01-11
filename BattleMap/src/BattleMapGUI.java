// Package Statements
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.*;


/**
 * This is the GUI for the Battle Map Application
 * This starts the program and listens for inputs an makes state changes to the model
 * @author Paladin
 */

@SuppressWarnings("serial")
public class BattleMapGUI extends JFrame implements KeyListener, ActionListener, FocusListener, ChangeListener, MouseListener, MouseMotionListener
{
	
    /** This sets the pixel width of the frame */
    private static int frameWidth = 750;
    /** This sets the pixel height of the frame */
    private static int frameHeight = 750;
    /** This sets the x screen location of the frame */
    private static int screenX = 100;
    /** This sets the y screen location of the frame */
    private static int screenY = 100;
	/** This creates an instance of the backend battlemap model */
    private GridModel battlemap = null;
    /** This creates an instance of the battlemap painting program */
    private BattleMapPainter mapPainter = null;
    
    /** This creates a display of the current list of pogs*/
    SidePanel innerFrame = null;
    
    /** This knows the current display hardware */
    private GraphicsDevice graphicsDevice = null;
    /** This tracks the display mode at startup */
    private DisplayMode origDisplayMode = null;
    /** This tracks the new display mode */
    private DisplayMode newDisplayMode = null;
    /** This tracks whether the program is running in fullscreen mode */
    private boolean fullScreen = false;
    /** This is the main menubar */
    private JMenuBar menuBar;
    
    
    /**
     * Constructor: Creates a new GUI and calls the setUpGUI method
     */
    public BattleMapGUI(GraphicsDevice graphicsDevice)
    {
    	// Create a model of the battlefield
    	battlemap = new GridModel();
    	// Create a model viewer
    	mapPainter = new BattleMapPainter(frameHeight, frameWidth);
    	// Create a sidebar to display the list of pogs in order
    	innerFrame = new SidePanel("Test Inner Frame", battlemap);
    	
    	//Save a reference to the graphics device as an instance variable so that it can be used later to exit the full-screen mode.
        this.graphicsDevice = graphicsDevice;
        
        //Get and save a reference to the original display mode as an instance variable so that it can be restored later.
        origDisplayMode = graphicsDevice.getDisplayMode();
        
        //Enter fullscreen exclusive mode
        //makeFullScreen();
        
        
    }
 
    /**
	 * Startup method for the application. Creates the GUI.
	 * 
	 * @param args The program does not accept command line input at this time
	 */
	public static void main(String[] args)
	{	
		//Get and display a list of graphics devices solely for information purposes.
	    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] devices =  graphicsEnvironment.getScreenDevices();
	    
	    for(int cnt = 0;cnt < 1;cnt++)
	    {
	      //System.out.println(devices[cnt]);
	    }//end for loop
		
	    BattleMapGUI g = new BattleMapGUI(devices[0]); //creates a JFrame
		
		g.mapPainter.sketchMap = g.battlemap; //set the viewer model equal to the main model
		
		g.setUpFrame();	//call the set-up method
		
	}
	   
    /**
     * Determines the actions for each key press.
     * Pressing an arrow key will move the current pog 1 square
     */
    public void keyPressed(KeyEvent e)
    {
    	if(e.getKeyCode() == KeyEvent.VK_UP) //if the "up" arrow key is pressed
    	{
    		//System.out.println("UP");
    		battlemap.moveUp(); //move the pog up in the model
    		repaint();
    	}
    	if(e.getKeyCode() == KeyEvent.VK_DOWN) //if the "down" arrow key is pressed
    	{
    		//System.out.println("DOWN");
    		battlemap.moveDown(); //move the pog down in the model
    		repaint();
    	}
    	if(e.getKeyCode() == KeyEvent.VK_RIGHT) //if the "right" arrow key is pressed
    	{
    		//System.out.println("RIGHT");
    		battlemap.moveRight(); //move the pog right in the model
    		repaint();
    	}
    	if(e.getKeyCode() == KeyEvent.VK_LEFT) //if the "left" arrow key is pressed
    	{
    		//System.out.println("LEFT");
    		battlemap.moveLeft(); //move the pog left in the model
    		repaint();
    	}
    	if(e.getKeyCode() == KeyEvent.VK_ENTER) //if the "enter" key is pressed
    	{ 
    		//System.out.println("ENTER");
    		battlemap.nextPog(); //make the next pog in the initiative list active
    		innerFrame.getList().setSelectedIndex(battlemap.getCurrentPog());
    		repaint();
    	}
    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE) //if the "escape" key is pressed
    	{ 
    		//System.out.println("ESCAPE");
    		System.exit(0) ; //end the program
    	}
    	if(e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET )  //if the "[" key is pressed
    	{ 
    		//System.out.println("ZOOM OUT");
    		mapPainter.zoomOut(); // zoom out of the battlefield graphic
    		repaint();
    	}
    	if(e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET ) //if the "]" key is pressed
    	{ 
    		//System.out.println("ZOOM IN");
    		mapPainter.zoomIn(); // zoom into the battlefield graphic
    		repaint();
    	}
    	
    	//Switch to fullscreen mode
    	if(e.getKeyCode() == KeyEvent.VK_EQUALS ) //if the "]" key is pressed
    	{ 
    		//System.out.println("FULLSCREEN");
    		makeFullScreen();
    		repaint();
    	}
    	//switch to windowed mode
    	if(e.getKeyCode() == KeyEvent.VK_MINUS ) //if the "]" key is pressed
    	{ 
    		//System.out.println("WINDOWED");
    		makeWindowed();
    		repaint();
    	}
    	
    }
    
	public void keyTyped(KeyEvent e)
    {
    	//throw new UnsupportedOperationException("Not supported yet.");
    }
    public void keyReleased(KeyEvent e)
    {
    	//throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    /**
     * Sets up the GUI, calls the menu setup method and adds key listeners
     */
    private void setUpFrame()
    {
    	
		setTitle("Battle Map"); // sets the title of the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // tells the program to exit when the x is clicked
		setLayout(new BorderLayout());
		
		if(fullScreen == false)
		{
		setSize(frameWidth+200,frameHeight); //sets the height and width of the frame in pixels
		setLocation(screenX,screenY); //sets the (x,y) location of the frame in the screen
		//pack(); //size the frame so that all components are at or above their preferred size
		}

		setFocusable(true);
		
    	//Setup and add the menubar
    	setUpMenuBar();
    	setJMenuBar(menuBar);
    	
    	//mapPainter.setFocusable(true);
    	//mapPainter.addFocusListener(this);
    	add(mapPainter, BorderLayout.CENTER);
    	
    	
    	//innerFrame.setFocusable(true);
    	//innerFrame.addFocusListener(this);
    	add(innerFrame,BorderLayout.EAST);
    	
    	
    	//Key listener methods
    	addKeyListener(this);
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	
		setVisible(true);
		
		//System.out.println(mapPainter.getLocation().getX()+","+mapPainter.getLocation().getY());
        //System.out.println(menuBar.getLocation().getX()+","+menuBar.getLocation().getY());
        //System.out.println(innerFrame.getLocation().getX()+","+innerFrame.getLocation().getY());
        //System.out.println(menuBar.getHeight());
    }
    /**
     * Sets up the Menu Bar, and defines all menus and submenus.
     */
    private void setUpMenuBar(){
    	/* http://download.oracle.com/javase/tutorial/uiswing/components/menu.html */
    	/* For these to do anything, action listener must be implemented */
    	
        /** These are example menus*/
        JMenu menu, submenu;
        /** This is a menuItem*/
        JMenuItem menuItem;
        /** This is a JRadioButtonMenuItem*/
        JRadioButtonMenuItem rbMenuItem;
        /** This is a JCheckBoxMenuItem*/
        JCheckBoxMenuItem cbMenuItem;
    	
    	
    	//Create the menu bar
		menuBar = new JMenuBar();
		
		//Build the first menu
		menu = new JMenu("BattleMap");
		//add settings to the menu here like menu.setMnemonic()
		menuBar.add(menu);
		//A group of JMenuItems
		menuItem = new JMenuItem("About BattleMap");
		menu.add(menuItem);		
		menuItem = new JMenuItem("Preferences");
		menu.add(menuItem);
		menuItem = new JMenuItem("Quit");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		
		
		//Build the A menu
		menu = new JMenu("A Menu");
		//add settings to the menu here like menu.setMnemonic()
		menuBar.add(menu);
		
		//A group of JMenuItems
		menuItem = new JMenuItem("A Menu item1");
		menu.add(menuItem);
		menuItem = new JMenuItem("A Menu item2");
		menu.add(menuItem);
		menuItem = new JMenuItem("A Menu item3");
		menu.add(menuItem);
		
		//A group of radiobuttom menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("Radio Button Menu Item 1");
		rbMenuItem.setSelected(true);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("Radio Button Menu Item 2");
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
		
		// a group of check box menu items
		menu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("check box menu item 1");
		menu.add(cbMenuItem);
		
		cbMenuItem = new JCheckBoxMenuItem("check box menu item 2");
		menu.add(cbMenuItem);		
		
		//a submenu
		menu.addSeparator();
		submenu= new JMenu("A Submenu");
		menu.add(submenu);
		
		//Build the B Menu
		menu = new JMenu("B Menu");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("B Menu item1");
		menu.add(menuItem);
		menuItem = new JMenuItem("B Menu item2");
		menu.add(menuItem);
		menuItem = new JMenuItem("B Menu item3");
		menu.add(menuItem);
		
		
		//Build the C Menu
		menu = new JMenu("C Menu");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("C Menu item1");
		menu.add(menuItem);
		menuItem = new JMenuItem("C Menu item2");
		menu.add(menuItem);
		menuItem = new JMenuItem("C Menu item3");
		menu.add(menuItem);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void makeFullScreen(){
		
		 if (graphicsDevice.isFullScreenSupported())
	        {
	          // Enter full-screen mode with an undecorated, non-re-sizable JFrame object.
	          setUndecorated(true);
	          setResizable(false);
	          
	          //Make it happen!
	          graphicsDevice.setFullScreenWindow(this);
	          fullScreen = true;
	          validate();
	        }else{
	          System.out.println("Full-screen mode not supported");
	        }//end else  
	}
	
	private void makeWindowed() {
		// TODO Auto-generated method stub
			graphicsDevice.setDisplayMode(origDisplayMode);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
	
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Determines Actions for a mouse press.
	 * Moves the current pog in the model to a new position
	 */
	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		moveIt(event);
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent event) 
	{
		moveIt(event);
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void moveIt(MouseEvent event)
	{
		int adjX = event.getX() / mapPainter.getPixelwidth() ;
		int adjY = (event.getY() - menuBar.getHeight())/ mapPainter.getPixelwidth();
		String position = "(" + event.getX() +","+ event.getY() +")";
		String adjPosition = "(" + adjX+","+ adjY +")";
		System.out.println(position+":"+ adjPosition + "Factor" + mapPainter.getPixelwidth());
		battlemap.moveTo(adjX, adjY);
		repaint();
	}
	
    
}