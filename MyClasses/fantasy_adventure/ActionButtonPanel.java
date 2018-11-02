package fantasy_adventure;

import java.awt.Dimension;

//******************************************************************************
// imports


//******************************************************************************
class ActionButtonPanel extends JPanelWithBackground{
//******************************************************************************
// static declarations
//******************************************************************************

//******************************************************************************
// instance declarations
//******************************************************************************

//******************************************************************************
// constructores
//******************************************************************************
  public ActionButtonPanel(){
    // start with default super
    super();

    // now setup specifics
    setPreferredSize(new Dimension(ActionPanel.actionButtonPanelWidth,
                                   ActionPanel.controlPanelHeight));

    setBackground(Constants.YELLOW_SOFT);

  } // end constructor

//******************************************************************************
// static methods
//******************************************************************************

//******************************************************************************
// public methods
//******************************************************************************

//******************************************************************************
// package methods
//******************************************************************************

//******************************************************************************
// private methods
//******************************************************************************

//******************************************************************************
} // end class