import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;


/**
 * This class is responsible for translating the state of the battlefield model into screen viewing
 * 
 * @author Paladin
 * 
 */
@SuppressWarnings("serial")
public class BattleMapPainter extends JPanel
{
	/** This variable knows the drawn height of the map */
	private int mapHeight = 200;
	/** This variable knows the drawn width of the map */
	private int mapWidth = 200;
	/** This variable knows the step size of pog movement on the map */
	private int pixelwidth = 10;
	/** This variable knows the scale factor for the map*/
	private int scalefactor = 10; 
	/** Sets the degree of Transparency for colors */
	private int transparency = 75;
	/** This variable sets the color of the screening that overlays the background image to highlight visible areas */
	Color screen = new Color(Color.GRAY.getRed(), Color.GRAY.getGreen(), Color.GRAY.getBlue(), transparency);
	/** This variable sets the color of the shadowed area/fog of war*/
	Color darkness = Color.BLACK;
	/** This object tracks the current background image */
	BufferedImage img = null;
	/** This is an instance of the battlefield model*/
	GridModel sketchMap = new GridModel();

	/**
	 * Constructor: Sets the initial ID and the locations of the corners
	 * 
	 * @param height the height of the frame
	 * @param width the width of the frame
	 */
	public BattleMapPainter(int height, int width){
		super();
		//this.mapHeight = height;
		//this.mapWidth = width;
		this.setSize(mapWidth,mapHeight);
		
		
	}
	
	public void paint(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g;
		
		/* Draw the background image */
		//loadBackgroundImage(g2);
		
		/* Get the position of every pog and draw it*/
		for (int i = 0; i < sketchMap.getInitiativeList().size(); i++) //for each pog in the initiative list
		{
			int x = sketchMap.getInitiativeList().get(i).getAnchor().x;//get the x-coordinate
			int y = sketchMap.getInitiativeList().get(i).getAnchor().y;//get the y-coordinate
			pogDraw(g2,x*pixelwidth,y*pixelwidth, i); // draw it on the screen at an adjusted scale 
		}
		
		/* Draw the walls */
		for (int i = 0; i < sketchMap.getWallList().size(); i++) //for each wall in the wall list
		{
			wallDraw(g2, sketchMap.getWallList().get(i).getWall()); //draw the wall
		}
		
		/* Scan the grid for visibility, visible squares get screened */ 
		for (int i = 0; i < sketchMap.getGridWidth(); i++) // for each column in the grid
		{
			for(int j = 0; j < sketchMap.getGridHeight(); j++ ) //for each row in the gird
			{	
				/* If the given grid square is visible */
				if(sketchMap.getBattlefield()[i][j].isVisible()) 
				{ 
					g2.setColor(screen); //set the color to the screening color
					gridDraw(g2,sketchMap.getBattlefield()[i][j].getAnchor().vertex);//draw a screening tile overlay
				}
				/* If the given grid square is NOT visible */
				if(!sketchMap.getBattlefield()[i][j].isVisible())
				{ 
					g2.setColor(darkness); //set the color to the shadowed color
					gridDraw(g2,sketchMap.getBattlefield()[i][j].getAnchor().vertex); //draw a blocking tile overlay
				}		
			}
		}
		
	}
	/**
	 * This method draws the pogs in the viewer
	 * 
	 * @param g2 the graphics object
	 * @param x the x position on the frame
	 * @param y the x position on the frame
	 */
	public void pogDraw(Graphics2D g2, int x, int y, int i)
	{
		/* since the current model uses fixed width, this method call uses magic numbers (for now) to make the pog
		 * smaller than the grid square it occupies  */
		if (i == sketchMap.getCurrentPog()){
			g2.setColor(Color.GREEN); // set the current pog color to green
		}else{
			g2.setColor(Color.BLUE); // otherwise set the pog color to blue
		}
		 //draw a circle, offset down and right from the grid anchor point
		Ellipse2D.Float miniature = new Ellipse2D.Float(x+1,y+1, pixelwidth-2, pixelwidth-2 );
		g2.fill(miniature); //fill the circle
		g2.draw(miniature); //draw the circle
	}
	
	/**
	 * This method draws the walls in the viewer
	 * 
	 * @param g2 the graphics object
	 * @param wall the wall to be drawn
	 */
	public void wallDraw(Graphics2D g2, Line2D wall)
	{
		g2.setColor(Color.RED); //set the wall color to red
		int x1 = (int) wall.getX1()*pixelwidth; //get the model x1 position and adjust it to the current image size 
		int y1 = (int) wall.getY1()*pixelwidth; //get the model y1 position and adjust it to the current image size
		int x2 = (int) wall.getX2()*pixelwidth; //get the model x2 position and adjust it to the current image size
		int y2 = (int) wall.getY2()*pixelwidth; //get the model y2 position and adjust it to the current image size
		Stroke originalStroke = g2.getStroke(); //save the original line width
		g2.setStroke(new BasicStroke(2*pixelwidth/scalefactor)); //adjust the stroke to be twice as wide as the grid lines
		g2.drawLine(x1, y1, x2, y2); //draw the line
		g2.setStroke(originalStroke); //restore the original line width
	}
	
	/**
	 * This method fills a grid square in the graphics frame
	 * 
	 * @param g2 the graphics object
	 * @param anchor the top left corner of the square the frame
	 */
	public void gridDraw(Graphics2D g2, Point anchor)
	{
    	Rectangle2D.Double area2 = new Rectangle2D.Double(anchor.getX()* pixelwidth,anchor.getY()* pixelwidth, pixelwidth, pixelwidth);
		g2.fill(area2);
		g2.draw(area2);
	}

	/**
	 * This method increases the apparent size of the battlemap 
	 */
	public void zoomIn(){
		this.pixelwidth = pixelwidth + scalefactor;
	}
	/**
	 * This method decreases the apparent size of the battlemap 
	 */
	public void zoomOut(){
		if(pixelwidth > 10){
		this.pixelwidth = pixelwidth - scalefactor;
		}
	}
	/**
	 * This loads an image into the background of the battle map 
	 */
	public void loadBackgroundImage(Graphics2D g2){
		
		try
		{
			img = ImageIO.read(new File("test.jpg")); // set the background image to the selected file - test.jpg currently hardcoded
		}
		catch (IOException e)
		{
		
		}
		g2.scale(.5, .5); //set the relative size of the image - magic numbers hardcoded for test image
		g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null); //draw the image
		g2.scale(2, 2); // reset the scale - magic numbers hardcoded for test image
		
	}

	/**
	 * @return the pixelwidth
	 */
	public int getPixelwidth() {
		return pixelwidth;
	}

	/**
	 * @param pixelwidth the pixelwidth to set
	 */
	public void setPixelwidth(int pixelwidth) {
		this.pixelwidth = pixelwidth;
	}

	/**
	 * @return the scalefactor
	 */
	public int getScalefactor() {
		return scalefactor;
	}

	/**
	 * @param scalefactor the scalefactor to set
	 */
	public void setScalefactor(int scalefactor) {
		this.scalefactor = scalefactor;
	}

	
}
