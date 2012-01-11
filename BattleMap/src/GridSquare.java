
/**
 * This is a single unit grid square. It knows the location of it's corners and their visibility 
 * It also know the loaction of every adjacent grid square and it's own visibility
 * 
 * @author Paladin
 * 
 */
public class GridSquare 
{
    /** the model unit size. every grid square is a unit size of 1 */
    private final int unitWidth = 1; // do I really need to declare this to avoid using a "magic number"
    
    /** This is a unique identifier for every grid square**/
    private final String GRID_ID;
   
    /** These objects keeps track of the corners of the model grid location for the square */ 
    Vertex anchor = null; //the top left corner is the base point assumed for all uses.
    Vertex NE = null;
    Vertex SE = null;
    Vertex SW = null;
    
    /** These are direct references to the surrounding squares*/
    GridSquare NORTHWEST = null;
    GridSquare NORTH = null;
    GridSquare NORTHEAST = null;
    GridSquare EAST = null;
    GridSquare SOUTHEAST = null;
    GridSquare SOUTH = null;
    GridSquare SOUTHWEST = null;
    GridSquare WEST = null;
    

    /** every grid square has a position in the array of grid squares that make up the model */
    private int gridLayer;  //may be used for vision blocking layer implementation
    private int gridRow;    //currently unused, but every square can know it's position in the grid
    private int gridColumn;
    
    //visibility count for degrees of fog?
    /** The GridSquare keeps track of whether or not it is currently visible to the observer */
    private boolean isVisible = false;
    private int visCount = 0;
     
    /**
	 * Constructor: Sets the initial ID and the locations of the corners
	 */
    public GridSquare(int x, int y)
    {
    	GRID_ID = "x: " + x +" " + "y: " +y;
    	anchor = new Vertex(x, y);
    	NE =  new Vertex(x+unitWidth, y);
    	SE =  new Vertex(x+unitWidth, y+unitWidth);
    	SW =  new Vertex(x, y+unitWidth);
    }

	/**
	 * @return the grid ID
	 */
	public String getGRID_ID() {
		return GRID_ID;
	}
    
	/**
	 * @return the anchor
	 */
	public Vertex getAnchor() {
		return anchor;
	}

	/**
	 * @param anchor the anchor to set
	 */
	public void setAnchor(Vertex anchor) {
		this.anchor = anchor;
	}
	
	/**
	 * @return NE vertex
	 */
	public Vertex getNE() {
		return NE;
	}	
	/**
	 * @return SE vertex
	 */
	public Vertex getSE() {
		return SE;
	}	
	/**
	 * @return SW vertex
	 */
	public Vertex getSW() {
		return SW;
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
	
	/**
	 * @return the gridRow
	 */
	public int getGridRow() {
		return gridRow;
	}
	/**
	 * @param gridRow the gridRow to set
	 */
	public void setGridRow(int gridRow) {
		this.gridRow = gridRow;
	}

	/**
	 * @return the gridColumn
	 */
	public int getGridColumn() {
		return gridColumn;
	}
	/**
	 * @param gridRow the gridColumn to set
	 */
	public void setGridColumn(int gridColumn) {
		this.gridColumn = gridColumn;
	}
	
	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		if (anchor.getVisible() || NE.getVisible() || SE.getVisible() || SW.getVisible()){
			isVisible = true;
		}else{
			isVisible = false;
		}
		
		return isVisible;
	}
	/**
	 * @param isVisible the isVisible to set
	 
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
    */
    
	/**
	 * @return the isVisible
	 */
	public int getVisCount() {
		visCount = 0;
		if (anchor.getVisible()){
			visCount++;
		}
			
		if (NE.getVisible()){
			visCount++;
		}
				
		if (SE.getVisible()){
			visCount++;
		}
				
		if (SW.getVisible()){
			visCount++;
		}
		
		return visCount;
		
	}
	
    /*Implement the following:
     *  Ideas to consider:
     * -Edge information flag? 
     * 	empty: neither of the cells which share this edge contains a solid tile shape
 	   	solid: each tile shape has at least one edge which matches a grid edge perfectly;
		interesting: edges which were adjacent to a cell containing a solid tile shape, but which didn't match the shape perfectly were labeled "interesting", 
		indicating that we needed to perform further collision tests. For instance, the two edges on the outside of a triangle's hypotenuse are "interesting".
     * 
     */
}
