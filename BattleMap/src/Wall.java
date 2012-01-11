import java.awt.Point;
import java.awt.geom.Line2D;


public class Wall
{
	
   
    /** This object keeps track of the lines start and end points */ 
    private Point start = null;
    private Point end = null;
    
    /** This object keeps track of the top left corner of the model grid location for the square */ 
    private Line2D wall = null;

    /** These variables keep track of visibility conditions*/
    private boolean isVisible= true;
    private boolean isTest= true;

    //every grid square has a position in the array of grid squares that make up the model.
    int gridLayer; 
    
    public Wall(int x1, int y1, int x2, int y2)
    {
    	start = new Point(x1, y1);
    	end = new Point(x2,y2);
    	wall = new Line2D.Float(start, end);
    }

	/**
	 * @return the start
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Point start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Point end) {
		this.end = end;
	}

	/**
	 * @return the wall
	 */
	public Line2D getWall() {
		return wall;
	}

	/**
	 * @param wall the wall to set
	 */
	public void setWall(Line2D wall) {
		this.wall = wall;
	}

	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * @return the isTest
	 */
	public boolean isTest() {
		return isTest;
	}

	/**
	 * @param isTest the isTest to set
	 */
	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}

	/**
	 * @return the gridLayer
	 */
	public int getGridLayer() {
		return gridLayer;
	}

	/**
	 * @param gridLayer the gridLayer to set
	 */
	public void setGridLayer(int gridLayer) {
		this.gridLayer = gridLayer;
	}
    
}
