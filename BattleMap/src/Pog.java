import java.awt.*;


/**
 * This class represents a miniature.
 * @author Paladin
 *
 */
public class Pog 
{
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return pogName;
	}

	/** This keeps the display name of the pog */
    private String pogName = "NewName";
    /** This creates a unique ID number for each Pog, in case no name is given*/
    private static int pogNextID = 1;
    /** This object keeps track of the top left corner of the model grid location for the pog */ 
    private Point anchor = null;
    
    //is visible?
    //initiative number? 
    /** 
     * Constructor: This sets the pog in near the top left of the visual grid
     * and gives it a default name
     */
    public Pog()
    {
    	anchor = new Point();
    	pogName = pogName + pogNextID++; 
    }
    
    /** 
     * Constructor: This sets the pog at a specific position in the visual grid
     * and gives it a default name
     */
    public Pog(int x, int y)
    {
    	anchor = new Point(x, y);
    	pogName = pogName + pogNextID++;
    }
	
    /**
	 * @return the pogName
	 */
	public String getPogName() {
		return pogName;
	}
	
	/**
	 * @param pogName the pogName to set
	 */
	public void setPogName(String pogName) {
		this.pogName = pogName;
	}
	
	/**
	 * @return the pogNextID
	 */
	public int getPogNextID() {
		return pogNextID;
	}
    
	/**
	 * @return the anchor
	 */
	public Point getAnchor() {
		return anchor;
	}
	
	/**
	 * @param anchor the anchor to set
	 */
	public void setAnchor(Point anchor) {
		this.anchor = anchor;
	}

	     
}
