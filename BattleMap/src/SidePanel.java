import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


@SuppressWarnings("serial")
public class SidePanel extends JPanel implements ListSelectionListener
{
	GridModel battlemap = null;
	private DefaultListModel listModel;
	private JList list;
	
	/**
	 * Constructor: Set up the right side panel with the initiative list
	 * @param Title
	 * @param initiativeList
	 */
	public SidePanel(String Title, GridModel battlemap)
	{
		this.battlemap = battlemap;
		
		//Create a list from the model initiative array
		listModel = new DefaultListModel();
		
		//add elements of the model initiative list to the list model
		for(int i = 0; i < battlemap.getInitiativeList().size(); i++){
			listModel.add(i, battlemap.getInitiativeList().get(i));			
		}
		
		//Create a new list using the list model	
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(battlemap.getCurrentPog());		
        list.addListSelectionListener(this);
        list.setVisibleRowCount(battlemap.getInitiativeList().size());

		this.add(list);
		
		//super(Title, resizable, closable);
		//this.moveToFront();
		//this.setLocation(25, 25);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(200,500));
		this.setSize(200,500);
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		//this.setBorder(BorderFactory.createLineBorder (Color.blue, 2));
	}	
	/**
	 * 
	 * @param Title
	 * @param resizable
	 * @param closable
	 */
	public SidePanel(String Title, boolean resizable, boolean closable){
		//super(Title, resizable, closable);
		//this.moveToFront();
		//this.setLocation(25, 25);
		this.setSize(200,500);	
	}
	//This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) 
    {
        if (e.getValueIsAdjusting() == false) 
        {
        	System.out.println(list.getSelectedIndex());
            battlemap.setCurrentPog(list.getSelectedIndex());
        }
    }
	/**
	 * @return the list
	 */
	public JList getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(JList list) {
		this.list = list;
	}
}
