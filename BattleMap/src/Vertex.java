import java.awt.Point;

/**
 * This is a single intersection of grid lines in the grid. It serves as the corners for each grid square
 * and it knows whether or not it is visible
 * 
 * @author Paladin
 * 
 */
public class Vertex
{
	/** This maintains the model location of the vertex*/
	Point vertex = null;
	
	/** This maintains visibility state*/
	private boolean isVisible;

    /**
	 * Constructor: Sets the initial location of the vertex and its visibility
	 */	
    public Vertex(int x, int y)
    {
    	vertex = new Point(x,y);
    	isVisible = false;
    }
    
    public void setVisible(boolean isVisible) {
    	this.isVisible = isVisible;
    }
    
    public boolean getVisible() {
    	return isVisible;
    }
}
