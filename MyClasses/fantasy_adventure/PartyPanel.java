package fantasy_adventure;

import java.awt.*;       // supports basic GUI components
import java.awt.event.*; // supports listeners
import javax.swing.*;    // supports swing GUI components


public class PartyPanel extends JPanelWithBackground implements ActionListener{

/* the purpose of this class is to display a small picture of each character
 * in the party as well as basic information about mana and health, etc.
 */

//****************************************************************************
// static declarations
//****************************************************************************
  static final int MAX_IN_PARTY = Constants.MAX_CHARS_IN_PARTY;

//****************************************************************************
// member declarations
//****************************************************************************
  PartyPortrait[] charsInParty;
  int             numCurrentlyInParty;
  PlayerCharacter playerCharacter;

//****************************************************************************
// constructors
//****************************************************************************
public PartyPanel(PlayerCharacter pc){

//****************************************************************************
  // setup JPanel
  super(FileNames.SESSION_TEXTURE,
        JPanelWithBackground.TILE);

  setPreferredSize(new Dimension(Constants.PARTYPANEL_WIDTH,
                                 Constants.PARTYPANEL_HEIGHT));

  setLayout(new FlowLayout(FlowLayout.CENTER, Constants.PARTYPANEL_HGAP,
                                              Constants.PARTYPANEL_VGAP));

  setBorder(BorderFactory.createRaisedBevelBorder());

//****************************************************************************
  // initialize objects within PartyPanel
  charsInParty = new PartyPortrait[MAX_IN_PARTY];

  /* *NOTE: no characters are put into a party by default.
   *  all characters must be added through method: addSocialToParty
   */
  playerCharacter = pc;
  numCurrentlyInParty = playerCharacter.getNumInParty();

} // end constructor()

//****************************************************************************
// public/package methods
//****************************************************************************
  void addSocialToParty(SocialObject character){
  // here is where most of the work is done.  Each character must be added to show up.
  // find first null slot in the charsInParty array, if none, then do nothing.

  int i = -1; // -1 means the party is full.
  i = firstOpenPartySlot();
  if (i == -1) return;


  // else there is a slot open, at index i
  charsInParty[i] = new PartyPortrait(character, this);
  charsInParty[i].setActionCommand(charsInParty[i].character.getShortName());

  add(charsInParty[i]);

  numCurrentlyInParty ++;

  // show partyPanel again?

} // end addSocialToParty

  SocialObject getSelectedCharacter(){
    // purpose of this method is to return the currently selected character.
    // for all chars in party, get selected.  If none, then return 0.

    int selected = 0;

    for (int i = 0; i < numCurrentlyInParty; i++){
      if (charsInParty[i].isCharSelected()) return charsInParty[i].getChar();
    } // end for loop

    // at this point, if we have not returned i, then we don't have one selected,
    // so return the party leader.
    return charsInParty[0].getChar();
  } // end getSelectedCharacter

  public void actionPerformed (ActionEvent e){
    String command = e.getActionCommand();
    PartyPortrait character = (PartyPortrait) e.getSource();

    Popup.createInfoPopup("'" + command
                          + "'" + "recognized"
                          + MyTextIO.newline
                          + character.character.getShortName()
                          + "'s portrait selected.");
    highlightCorrectPortrait(character);
  } // end actionperformed

//****************************************************************************
  void removeSocialFromParty(SocialObject character){
  // find char in list, if not then do nothing, if so, then remove component and remove from array!

  for (int i = 0; i < charsInParty.length; i++){
    // if the character matches, then remove it
    if (character == charsInParty[i].getChar()){
      charsInParty[i] = null;
    } // end if

    // now, we need to sift the list, if we removed a character in the middle.
    if ( (i < charsInParty.length - 1) &&
         (charsInParty[i] == null)){

      if (charsInParty[i+1] == null)
      charsInParty[i] = charsInParty[i+1];

    } // end if

  } // end for loop
  numCurrentlyInParty -= 1;

} // end removeSocialFromParty(character)

  void highlightCorrectPortrait(PartyPortrait character){
    // purpose of this method is to clear the highlighting and
    // select the passed portrait

    for (int i = 0; i < numCurrentlyInParty; i++ ){
      charsInParty[i].setCharSelected(false);
    } // end for loop

    character.setCharSelected(true);
  }// end highlightCorrectPortrait()

//****************************************************************************
// private methods
//****************************************************************************

  private int firstOpenPartySlot(){
    for (int i = 0; i < charsInParty.length; i++){
      if (charsInParty[i] == null) return i;
    } // end for loop

    // POST: i = charsInParty.length, there are no null slots!
    return -1;

  } // end firstOpenPartySlot

//****************************************************************************
} // end class PartyPanel
