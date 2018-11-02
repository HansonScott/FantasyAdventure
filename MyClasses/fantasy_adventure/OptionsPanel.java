package fantasy_adventure;

import java.awt.*;       // supports basic GUI components
import java.awt.event.*; // supports listeners
import javax.swing.*;    // supports swing GUI components

public class OptionsPanel extends JTabbedPane implements ActionListener{

/* purpose of this class is to give the user the ability to change
   many of the options about the game.  The Divisions of this are:
   - Gameplay
   - Feedback
   - Graphics
   - Audio

   This jPanel will contain a tab for each, and allow the user
   the button options at the bottom to choose:
   - OK - save the new options and return to the action screen
   - CANCEL - return to the options screen without saving
   - RESET - return the options to the default setting
 */

//********************************************************************
// initialize variables and objects

  GameplayTab gameplayTab;
  FeedbackTab feedbackTab;
  GraphicsTab graphicsTab;
  AudioTab    audioTab;

//********************************************************************
// constructor
//********************************************************************

  public OptionsPanel(){

  super();

  setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                 Constants.CENTRALPANEL_HEIGHT));

  // first, setup the tabbed panes
  gameplayTab = new GameplayTab();
  feedbackTab = new FeedbackTab();
  graphicsTab = new GraphicsTab();
  audioTab    = new AudioTab();

  // add details to each tabbed pane

  // gameplay details

  // feedback details

  // graphics details

  // audio details


    // add panel to tabbedPane
   addTab("<html><font size=+1>  Game Play </font></html>", gameplayTab);
   addTab("<html><font size=+1>  Feedback </font></html>",  feedbackTab);
   addTab("<html><font size=+1>  Graphics </font></html>",  graphicsTab);
   addTab("<html><font size=+1>    Audio   </font></html>", audioTab);

  } // end constructor

//********************************************************************
// public methods
//********************************************************************

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();

  } // end actionPerformed

//********************************************************************
// private methods
//********************************************************************

} // end class OptionsPanel
