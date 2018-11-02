package fantasy_adventure;

//************************************************************************
//imports
import java.awt.*;       // supports basic GUI components
import java.awt.event.*; // supports listeners
import javax.swing.*;    // supports swing GUI components

//************************************************************************

public class RelationsTab extends    JPanelWithBackground
                          implements ActionListener,
                                     MouseListener,
                                     KeyListener{

  /* The purpose of this class is to communicate all the
   * componenets and options relating to the party relations
   * with one another, including dispositions.
   */

//************************************************************************
// static declarations
//************************************************************************


//************************************************************************
// member declarations
//************************************************************************


//************************************************************************
// constructors
//************************************************************************

  public RelationsTab() {
    // the purpose of this constructor is to create the interface
    super();

    setPreferredSize(new Dimension(Constants.CENTRALPANEL_HEIGHT - Constants.INSET_FOR_TAB,
                                   Constants.CENTRALPANEL_WIDTH));

    // now create the internal components and add them.


  } // end constructor

//************************************************************************
// public methods
//************************************************************************

  public void actionPerformed(ActionEvent a){

  } // end actionPerformed

//************************************************************************
  public void mousePressed(MouseEvent m){

  } // end mouseEvent

//************************************************************************
  public void mouseReleased(MouseEvent m){

  } // end mouseEvent

//************************************************************************
  public void mouseClicked(MouseEvent m){

  } // end mouseEvent

//************************************************************************
  public void keyPressed(KeyEvent k){

  } // end keyPressed

//************************************************************************
  public void keyReleased(KeyEvent k){

  } // end keyReleased

//************************************************************************
  public void keyTyped(KeyEvent k){

  } // end keyTyped

//************************************************************************
// package methods
//************************************************************************

//************************************************************************
// static methods
//************************************************************************

//************************************************************************
// private methods
//************************************************************************

//************************************************************************
} // end class RelationsTab
//************************************************************************
