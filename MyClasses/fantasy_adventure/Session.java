package fantasy_adventure;

import java.awt.*;       // supports basic GUI components
import java.awt.event.*; // supports listeners
import javax.swing.*;    // supports swing GUI components


public class Session extends JFrame implements ActionListener{

/* the purpose of this class is to be the main handler for the entire session of game play.
 * Each different 'window' will be added/showed/removed by this session.
 * Exiting the session will return to the MainMenu window, allowing you to exit the game.
 * Adding the exit on close allows system exit, which also is linked to the alt+F4 combo.
 * The session must be created with a character, so all the related files can be added
 * to the session windows. (such as the journal, knowledge base, party info, etc.)
 */

//***********************************************************************************
// declarations
//***********************************************************************************

// macro objects
NavPanel     navPanel;        // each button within causes the central panel to 'flip' (right side)
PartyPanel   partyPanel;      // includes and displays a list of all members in the party (left side)
CentralPanel centralPanel;    // this is the area in which we load all the specific panels below (center)

Thread       clockThread;

// details of the Session JFrame
Container    content;
PlayerCharacter playerCharacter;

//***********************************************************************************
// constructor
//***********************************************************************************

public Session(PlayerCharacter PC){
  // PC is the character (thus saved game) with which the user chose to play this session

  // set settings for Session JFrame
  super("Fantasy Adventure");

  setUndecorated(true);

  playerCharacter = PC;

  // now setup the details.
  content = getContentPane();
  content.setLayout(new BorderLayout());
  setSize(Constants.SCREEN_SIZE);
  setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  setResizable(false);

//***********************************************************************************
  // initialize macro components

  // before creating items and/or loading items, make sure the statics are loaded.
    ItemBasics.createItemBasicsFromFile();

//***********************************************************************************
  // initialize navPanel
//  Popup.createInfoPopup("Creating navPanel...");
  navPanel     = new NavPanel(this);

//***********************************************************************************
  // initialize clock
//  Popup.createInfoPopup("creating a new thread for the clock (from session)");
  clockThread = new Thread(ClockPanel.thisClock);
//  Popup.createInfoPopup("starting the clock thread (from Session)");
  clockThread.start();

//***********************************************************************************
  // initialize partyPanel

//  Popup.createInfoPopup("Creating partyPanel...");
  partyPanel   = new PartyPanel(playerCharacter);

  // setup and add objects to PartyPanel
  partyPanel.addSocialToParty(PC);

  for( int i = 0; i < partyPanel.MAX_IN_PARTY; i++){
    if (partyPanel.charsInParty[i] != null)
      partyPanel.charsInParty[i].addActionListener(this);
  } // end for loop

  // eventually, add the party members to the panel too!
    /* for (int i = 0; i < PC.party.length; i++){
    if PC.party[i] != null{
      partyPanel.addSocialToParty(PC.party[1];}
    } // end for loop
    */

//***********************************************************************************
  // initialize centralPanel
  centralPanel = new CentralPanel(PC); // some of the sub-windows need reference to the PC

// Popup.createInfoPopup("Linking details...");

  // customize centralPanel - add all 'cross panel' buttons
  centralPanel.invPanel.charInfoButton.addActionListener(this);

  centralPanel.charInfoPanel.charInvButton.addActionListener(this);
  centralPanel.charInfoPanel.partyControlButton.addActionListener(this);

//***********************************************************************************
  // add objects to Session
  content.add(partyPanel,   BorderLayout.WEST);
  content.add(centralPanel, BorderLayout.CENTER);
  content.add(navPanel,     BorderLayout.EAST);

//***********************************************************************************
  // begin working the sesssion
  centralPanel.charInfoPanel.refreshInfo(partyPanel.charsInParty[0].getChar());
  centralPanel.invPanel.refreshInv(partyPanel.charsInParty[0].getChar());

  // show the correct first screen.
  centralPanel.myCardLayout.show(centralPanel, "info");
} // end constructor(PC)

//***********************************************************************************
// public/package methods
//***********************************************************************************

public void actionPerformed(ActionEvent e){
  String command = e.getActionCommand();

//***********************************************************************************
  if (command.equals("action")  ||
      command.equals("area")    ||
      command.equals("world")   ||
      command.equals("journal") ||
      command.equals("spells")  ||
      command.equals("inv")     ||
      command.equals("info")    ||
      command.equals("party")   ||
      command.equals("options") ||
      command.equals("file")    ){

    // user has chosen a specific navPanelButton, so show the 'command' panel
    // first, get selected character, and update screen in preparation.
    centralPanel.charInfoPanel.refreshInfo(partyPanel.getSelectedCharacter());
    centralPanel.myCardLayout.show(centralPanel, command);
  } // end if NavPanelButton pressed

  else if (command.equals( "Character Info")){
    // user has clicked on the 'character info' button in the inventory panel
    // then jump over to the char info panel.
    centralPanel.myCardLayout.show(centralPanel, "info");
  } // end if

//***********************************************************************************
  for (int i = 0; i < partyPanel.charsInParty.length; i++){
    // check that the command is a click of a party character
    if (partyPanel.charsInParty[i] == null)break;

    if (command.equals(partyPanel.charsInParty[i].getChar().getShortName())){
      // the user has clicked on a specific character, so respond.
      centralPanel.charInfoPanel.refreshInfo(partyPanel.charsInParty[i].getChar());
      centralPanel.invPanel.refreshInv(partyPanel.charsInParty[i].getChar());

    } // end if match name
  } // end for loop

//***********************************************************************************

} // end actionPerformed (evt)

public PlayerCharacter getPlayerCharacter(){
  // purpose of this method is to capture the current state of the party leader,
  // the player Character, along with all the details it has accumulated and return it.

  // do any last minute updating before returning character,
  // so it is the most up-to-date as possible...
  // BUT, perhaps, since it is being passed around by reference, there is only one version,
  // so it is current!

  return playerCharacter;
} // end getPlayerCharacter

//***********************************************************************************
// private methods
//***********************************************************************************

} // end class Session
