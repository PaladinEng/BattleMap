import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * This is the backend model for the battlemap. It renders the map and the pogs
 * and draws the gridlines. The Grid model assumes a discrete unit grid, where grid squares are indivisible
 * 
 * @author Paladin
 * 
 */
public class GridModel implements ChangeListener
{
	// set up the battlefield model
	/** sets the number of columns in the model */
	private int gridWidth = 50;
	/** sets the number of rows in the model */
	private int gridHeight = 50;
	/** Sets the unit grid movement on the model of the battlemap */
	private static int stepSize = 1;
	
	// the main elements of the battlemap
	/** Array of Grid squares monitors visibility for the map */
	private GridSquare[][] battlefield = new GridSquare[gridWidth][gridHeight];
	/** Array of Points used to link vertices in all grid squares */
	private Vertex[][] vertexfield = new Vertex[gridWidth+1][gridHeight+1];
	
	//vision blocking walls
	/** maintains a list of walls */
	private ArrayList<Wall> wallList = new ArrayList<Wall>();

	//set up the initiative tracker
	/** Sets the initial initiative list size */
	private static int initialSize = 5;
	/** Tracks the index of the pog whose turn it is */
	private int currentPog = 0;
	/** Creates a list of Pogs */
	private ArrayList<Pog> initiativeList = new ArrayList<Pog>();
	
	/**
	 * @return the gridWidth
	 */
	public int getGridWidth() {
		return gridWidth;
	}
	/**
	 * @param gridWidth the gridWidth to set
	 */
	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}
	/**
	 * @return the gridHeight
	 */
	public int getGridHeight() {
		return gridHeight;
	}
	/**
	 * @param gridHeight the gridHeight to set
	 */
	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}
	/**
	 * @return the battlefield
	 */
	public GridSquare[][] getBattlefield() {
		return battlefield;
	}
	/**
	 * @param battlefield the battlefield to set
	 */
	public void setBattlefield(GridSquare[][] battlefield) {
		this.battlefield = battlefield;
	}
	/**
	 * @param vertexfield the vertexfield to set
	 */
	public void setVertexfield(Vertex[][] vertexfield) {
		this.vertexfield = vertexfield;
	}
	/**
	 * @return the vertexfield
	 */
	public Vertex[][] getVertexfield() {
		return vertexfield;
	}
	/**
	 * @return the wallList
	 */
	public ArrayList<Wall> getWallList() {
		return wallList;
	}
	/**
	 * @param wallList the wallList to set
	 */
	public void setWallList(ArrayList<Wall> wallList) {
		this.wallList = wallList;
	}
	/**
	 * @return the initialSize
	 */
	public static int getInitialSize() {
		return initialSize;
	}
	/**
	 * @param initialSize the initialSize to set
	 */
	public static void setInitialSize(int initialSize) {
		GridModel.initialSize = initialSize;
	}
	/**
	 * @return the currentPog
	 */
	public int getCurrentPog() 
	{
		return currentPog;
	}
	public void setCurrentPog(int current) 
	{
		currentPog = current;
	}
	
	/**
	 * @return the initiativeList
	 */
	public ArrayList<Pog> getInitiativeList() 
	{
		return initiativeList;
	}
	/**
	 * @param initiativeList the initiativeList to set
	 */
	public void setInitiativeList(ArrayList<Pog> initiativeList) 
	{
		this.initiativeList = initiativeList;
	}
	
	/**
	 * Constructor: Sets the initial model height and width of the battlemap and initial model location of the pogs
	 */
	public GridModel()
	{
		/* Initializes the test list with 5 pogs. */
		setUpInitiativeList();

		/*initialize the model with test walls*/
		setUpWallList();
		
		/* initialize an empty grid */
		setUpGrid();
		
		/* links every vertex to every corner */
		setUpVertices();
		
		/* Establish LOS */
		checkLineOfSight();
	
	}

	/**
	 * Adds pogs to the initiative list for functional testing 
	 */
	private void setUpInitiativeList(){
		for (int i = 0; i < initialSize; i++)
		{
			initiativeList.add(new Pog(i*5,i*5)); //5 is magic number just to spread out test pogs in the battlefield
		}
	}	
	/**
	 * Adds walls to the wall list for functional testing of vision blocking 
	 */
	private void setUpWallList(){
		/*initialize the model with test walls*/
		wallList.add(new Wall(20,20,40, 20));
		wallList.add(new Wall(2,2,2,4));
		wallList.add(new Wall(10,10,20,10));
		wallList.add(new Wall(30,30,20,40));
		wallList.add(new Wall(20,8,20,2));
	}
	/** This method iterates through the array an initializes a grid object at each row and column */
	private void setUpGrid(){
		
		for (int i = 0; i < gridWidth; i++)
		{
			for(int j = 0; j < gridHeight; j++ )
			{
				battlefield[i][j] = new GridSquare(i,j);
			}//end for loop
		}//end for loop
			
		//this loop links each grid square object to its neighboring squares
		for (int i = 0; i < gridWidth; i++)
		{
			for(int j = 0; j < gridHeight; j++ )
			{	
				
				if(i > 0 && j > 0)
				{
					battlefield[i][j].NORTHWEST = battlefield[i-1][j-1];
				}
				
				if(j > 0)
				{ 
					battlefield[i][j].NORTH = battlefield[i][j-1]; 
				}
				
				if(i > gridWidth-1 && j > 0)
				{
					battlefield[i][j].NORTHEAST = battlefield[i+1][j-1]; 
				}
				
				if(i < gridWidth-1)
				{
					battlefield[i][j].EAST = battlefield[i+1][j]; 
				}
				
				if(i < gridWidth-1 && j < gridHeight-1)
				{
					battlefield[i][j].SOUTHEAST = battlefield[i+1][j+1]; 
				}
				
				if(j < gridHeight-1)
				{
					battlefield[i][j].SOUTH = battlefield[i][j+1]; 
				}
				
				if(i > 0 && j < gridHeight-1)
				{	
					battlefield[i][j].SOUTHWEST = battlefield[i-1][j+1]; 
				}
				
				if(i > 0)
				{
					battlefield[i][j].WEST = battlefield[i-1][j]; 
				}	
			}//end for loop
		}//end for loop
	}//end method
	/** This method iterates through an array of points links each vertex to the appropriate corner reference for each grid square*/
	public void setUpVertices(){
		for (int i = 0; i < gridWidth+1; i++)
		{
			for(int j = 0; j < gridHeight+1; j++ )
			{	
				vertexfield[i][j] = new Vertex(i,j);
			}//end for loop
		}//end for loop
		
		for (int i = 0; i < gridWidth; i++)//this is missing the outer east and south vertices
		{
			for(int j = 0; j < gridHeight; j++ )
			{	
				battlefield[i][j].anchor = vertexfield[i][j];
				if(i > 0)
				{
				battlefield[i-1][j].NE = vertexfield[i][j];
				}
				
				if(i > 0 && j > 0)
				{
				battlefield[i-1][j-1].SE = vertexfield[i][j];
				}
				if(j > 0)
				{
				battlefield[i][j-1].SW = vertexfield[i][j];
				}
			}//end for loop
		}//end for loop
	}//end method
	/** This method cycles through the initiative list */
	public void nextPog()
	{
		if(currentPog < initiativeList.size()-1  )
		{	
			currentPog++;
		}
		else
		{
			currentPog = 0;
		}
		checkLineOfSight();
	}//end nextPog()

	//these methods need error checking to prevent moving off the grid into null space
	/** This method moves a pog up and calls for a new line of sight check */
	public void moveUp() 
	{
		initiativeList.get(currentPog).getAnchor().translate(0, -stepSize);
		checkLineOfSight();
	}
	/** This method moves a pog down and calls for a new line of sight check */
	public void moveDown() 
	{
		initiativeList.get(currentPog).getAnchor().translate(0, stepSize);
		checkLineOfSight();
	}
	/** This method moves a pog left and calls for a new line of sight check */
	public void moveLeft() 
	{
		initiativeList.get(currentPog).getAnchor().translate(-stepSize, 0);
		checkLineOfSight();
	}
	/** This method moves a pog right and calls for a new line of sight check */
	public void moveRight() 
	{
		initiativeList.get(currentPog).getAnchor().translate(stepSize, 0);
		checkLineOfSight();
	}
	/** This method moves a pog to a set of coordinates and calls for a new line of sight check */
	public void moveTo(int x, int y) 
	{
		if( x >= 0 && y >= 0 && x < gridWidth && y < gridHeight){
		initiativeList.get(currentPog).getAnchor().setLocation(x, y);
		}
		checkLineOfSight();
	}
	
    /**
     * This method creates 4 rays, once from each observer corner, to each vertex in the grid.
     * at each vertex, it sets the default visibility, and if all of the rays intersect one of the walls, then
     * the square is not visible.
     */
	public void checkLineOfSight(){
		
		// get the x position of the current pog's anchor vertex
		int colNum = initiativeList.get(currentPog).getAnchor().x;
		// get the y position of the current pog's anchor vertex
		int rowNum = initiativeList.get(currentPog).getAnchor().y;
		//the base grid is the grid containing the pog from which Line of sight will be calculated
		GridSquare baseGrid = battlefield[colNum][rowNum];
		
		for (int i = 0; i < gridWidth; i++) //for each row 
		{
			for(int j = 0; j < gridHeight; j++ ) //for each column
			{
				vertexfield[i][j].setVisible(true); // set all grid squares to be visible initially
				
				//Create a ray from the target vertex to each of the corners of the base grid
				Line2D raycastAn = new Line2D.Float(baseGrid.anchor.vertex,vertexfield[i][j].vertex);
				Line2D raycastNE = new Line2D.Float(baseGrid.NE.vertex,vertexfield[i][j].vertex);
				Line2D raycastSE = new Line2D.Float(baseGrid.SE.vertex,vertexfield[i][j].vertex);
				Line2D raycastSW = new Line2D.Float(baseGrid.SW.vertex,vertexfield[i][j].vertex);
				
				for(int k = 0; k < wallList.size(); k++) //for each wall in the model
				{
					/**If all four rays intersect a wall then the square is not visible */
					if(raycastAn.intersectsLine(wallList.get(k).getWall()) &&
					   raycastNE.intersectsLine(wallList.get(k).getWall()) &&	
					   raycastSE.intersectsLine(wallList.get(k).getWall()) &&
					   raycastSW.intersectsLine(wallList.get(k).getWall()) )
					{
						//set the grid square visibility to false 
						vertexfield[i][j].setVisible(false);
						//System.out.println("i:"+i+"j:"+j);
					}
				}

				
			}//end column for loop
		}//end row for loop
		
	}// end method

	public boolean twoGridSquareLOS(GridSquare baseSquare,GridSquare testSquare ){
	
		boolean visible = false;
		boolean intersectsWalls = false;
		Line2D raycast = null;
		
		
		Point[] baseCornerList = new Point[4];		
		baseCornerList[0] = baseSquare.anchor.vertex;
		baseCornerList[1] = baseSquare.NE.vertex;
		baseCornerList[2] = baseSquare.SE.vertex;
		baseCornerList[3] = baseSquare.SW.vertex;

		Point[] testCornerList = new Point[4];		
		testCornerList[0] = testSquare.anchor.vertex;
		testCornerList[1] = testSquare.NE.vertex;
		testCornerList[2] = testSquare.SE.vertex;
		testCornerList[3] = testSquare.SW.vertex;
		
		
		Line2D storedWall = null;
		
		int baseCorner = 0;
		while(baseCorner < 4 && intersectsWalls == false)
		{
			int testCorner = 0;
			while(testCorner < 4 && intersectsWalls == false)
			{
				
				raycast = new Line2D.Float(baseCornerList[baseCorner],testCornerList[testCorner]);	
				
			
				for(int i= 0; i < wallList.size(); i++) //for each raycast, check every wall for a collision
				{
					if (raycast.intersectsLine( wallList.get(i).getWall() ) ) //if the ray intersects any wall
					{
						intersectsWalls = true; //then there is an intersection
						if(storedWall == null){
						storedWall = wallList.get(i).getWall(); //remember which wall was intersected
						}
						//and we need to find the closest wall, if there are multiple intersections
						if(wallList.get(i).getWall().ptLineDist(baseCornerList[baseCorner]) //if the distance from the current wall
								< storedWall.ptLineDist(baseCornerList[baseCorner]) ) //is less than the stored wall
						{
							storedWall = wallList.get(i).getWall();
						}
						if(2<3) //the distance between the wall intersection is shorter then the ray, then the square is not visible
						{
							
						}
					}
					
				}
			testCorner++;
			}
		
		baseCorner++;
		}

	
	return visible;
	
	}
	
/**
 * This Method attempts to uses shadowcasting and the linked list to determine visible areas
 */
	public void shadowCast(){
		// get the x position of the current pog's anchor vertex
		int colNum = initiativeList.get(currentPog).getAnchor().x;
		// get the y position of the current pog's anchor vertex
		int rowNum = initiativeList.get(currentPog).getAnchor().y;
		//the base grid is the grid containing the pog from which Line of sight will be calculated
		GridSquare baseGrid = battlefield[colNum][rowNum];
		
		
		
		
	}
@Override
public void stateChanged(ChangeEvent e) {
	// TODO Auto-generated method stub
	
}
}//end class

