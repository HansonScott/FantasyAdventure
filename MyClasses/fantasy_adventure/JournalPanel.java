package fantasy_adventure;

import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;

class JournalPanel extends    JTabbedPane
                   implements ActionListener{

/* purpose of this class is to give the user the ability to view and change
   three of the record-keeping parts of the game.  The Divisions of this are:
   - Quests
   - Journal
   - Encounters

   This JPanel will contain a tab for each
 */

//********************************************************************
// member variables and objects
//********************************************************************

  QuestsTab       questsTab;
  JournalTab      journalTab;
  EncountersTab   encountersTab;
  PlayerCharacter character;

//********************************************************************
// constructor
//********************************************************************

  public JournalPanel(PlayerCharacter PC){
    super();
    setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                   Constants.CENTRALPANEL_HEIGHT));

    character = PC;

    // first, setup the tabbed panes
    questsTab     = new QuestsTab(PC);
    journalTab    = new JournalTab(PC);
    encountersTab = new EncountersTab(PC);

    // add panel to tabbedPane
    addTab("<html><font size=+1>   Quests </font></html>", questsTab);
    addTab("<html><font size=+1>  Journal </font></html>",  journalTab);
    addTab("<html><font size=+1> Encounters </font></html>",  encountersTab);

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

} // end class JournalPanel
