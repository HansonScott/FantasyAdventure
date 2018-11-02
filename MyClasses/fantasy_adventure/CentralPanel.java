package fantasy_adventure;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;    // supports swing GUI components

//******************************************************************
class CentralPanel extends    JPanelWithBackground{

//******************************************************************
  // declarations
//******************************************************************

  CardLayout          myCardLayout;

  ActionPanel         actionPanel;
  AreaPanel           areaPanel;
  WorldPanel          worldPanel;
  JournalPanel        journalPanel;
  SpellPanel          spellPanel;
  InvPanel            invPanel;
  CharInfoPanel       charInfoPanel;
  PartyControlPanel   partyControlPanel;
  OptionsPanel        optionsPanel;
  FilePanel           filePanel;

//******************************************************************
  // constructor
//******************************************************************

CentralPanel(PlayerCharacter PC){

  super (FileNames.SESSION_TEXTURE,
         JPanelWithBackground.TILE);

  setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                 Constants.CENTRALPANEL_HEIGHT));

//************************************************************************
// initialize objects

  myCardLayout = new CardLayout();
  setLayout(myCardLayout);

//  Popup.createInfoPopup("constructing actionPanel");
  actionPanel         = new ActionPanel(PC);       // action Screen
//  Popup.createInfoPopup("constructing areaPanel");
  areaPanel           = new AreaPanel(PC);         // need PC's area references
//  Popup.createInfoPopup("constructing worldPanel");
  worldPanel          = new WorldPanel(PC);        // need PC's map references
//  Popup.createInfoPopup("constructing journalPanel");
  journalPanel        = new JournalPanel(PC);      // need PC's file references
//  Popup.createInfoPopup("constructing spellPanel");
  spellPanel          = new SpellPanel();          // will change based on char selected
//  Popup.createInfoPopup("constructing invPanel");
  invPanel            = new InvPanel();            // will change based on char selected
//  Popup.createInfoPopup("constructing charInfoPanel");
  charInfoPanel       = new CharInfoPanel();       // will change based on char selected
//  Popup.createInfoPopup("constructing PartyControlPanel");
  partyControlPanel   = new PartyControlPanel();
//  Popup.createInfoPopup("constructing OptionsPanel");
  optionsPanel        = new OptionsPanel();        // session static, so PC doesn't matter.
//  Popup.createInfoPopup("constructing filePanel");
  filePanel           = new FilePanel();           // session static, so PC doesn't matter.

//************************************************************************
// add objects to cardLayout

  add ("party",   partyControlPanel);
  add ("journal", journalPanel);
  add ("options", optionsPanel);
  add ("file",    filePanel);
  add ("action",  actionPanel);
  add ("area",    areaPanel);
  add ("world",   worldPanel);
  add ("inv",     invPanel);
  add ("spells",  spellPanel);
  add ("info",    charInfoPanel);

} // end constructor

//******************************************************************
  // public methods
//******************************************************************

//******************************************************************
  // package methods
//******************************************************************


//******************************************************************
  // private methods
//******************************************************************

}  // end class CentralPanel
