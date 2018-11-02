package fantasy_adventure;

import java.awt.*;
import javax.swing.*;

//**************************************************************
public class ActionArea extends JPanelWithBackground{

/* The purpose of this class is to handle the many operations that
 * take place directly on the actionArea itself.  This will integrate
 * many other classes and components, as they all affect and are affected
 * by this class.
 * For the most part, this will deal with the graphical side of displaying
 * area and drawing all the objects/characters/effects within that area.
 */
//**************************************************************
// static declarations
//**************************************************************

//**************************************************************
// instance declarations
//**************************************************************

//**************************************************************
// constructor
//**************************************************************

public ActionArea(){
  // start with default super-constructor
  super();

  // now setup all the details.
  setSize(Constants.AREA_WIDTH,
          Constants.AREA_HEIGHT);
  setBackground(Constants.GREEN_DARK);

} // end constructor

//**************************************************************
// static methods
//**************************************************************

//**************************************************************
// public methods
//**************************************************************

//**************************************************************
// package methods
//**************************************************************

//**************************************************************
// private methods
//**************************************************************

//**************************************************************
} // end class
