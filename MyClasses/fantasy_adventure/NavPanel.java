package fantasy_adventure;

import java.awt.*;       // supports basic GUI components
import javax.swing.*;
import java.awt.event.*;    // supports swing GUI components


public class NavPanel extends JPanelWithBackground implements KeyListener{

/* the purpose of this class is to offer the user buttons that will
 * show different parts of the session in the centralPanel
 * of the session.  Each button corresponds to a panel within the
 * session.centralPanel.cardLayout.
 */

//**********************************************************************
// static declarations
//**********************************************************************

//**********************************************************************
// instance declarations
//**********************************************************************

  Session   thisSession;

  JPanelWithBackground    buttonPanel;
  JPanel                  clockPanel;

  JButton   actionPanelButton,
            areaPanelButton,
            worldPanelButton,
            journalPanelButton,
            invPanelButton,
            spellPanelButton,
            charInfoPanelButton,
            partyControlPanelButton,
            sessionOptionsPanelButton,
            filePanelButton;

//**********************************************************************
// constructor
//**********************************************************************

public NavPanel(Session s){

//**********************************************************************
  // create and setup JPanelWithBackground

  super(FileNames.SESSION_TEXTURE,
        JPanelWithBackground.TILE);

  thisSession = s; // capture parent

  setPreferredSize(new Dimension(Constants.NAVPANEL_WIDTH,
                                 Constants.NAVPANEL_HEIGHT));

  setBorder(BorderFactory.createRaisedBevelBorder());
  setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

//**********************************************************************
   clockPanel = ClockPanel.makeNewClockPanel();

  buttonPanel = new JPanelWithBackground(FileNames.SESSION_TEXTURE,
                                         JPanelWithBackground.TILE);

   buttonPanel.setPreferredSize(new Dimension(Constants.NAVPANEL_BUTTON_WIDTH,
                                              (int)(Constants.NAVPANEL_HEIGHT
                                              - clockPanel.getPreferredSize().getHeight()
                                              - 4))); // 4 for the border

   buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, Constants.NAVPANEL_HGAP,
                                        Constants.NAVPANEL_VGAP));


//**********************************************************************
  // initialize main objects (ActionListeners to be added by centralPanel);

  actionPanelButton         = makeNavPanelButton("Action Area", "action" , thisSession);
  areaPanelButton           = makeNavPanelButton("Area Map"   , "area"   , thisSession);
  worldPanelButton          = makeNavPanelButton("World Map"  , "world"  , thisSession);
  journalPanelButton        = makeNavPanelButton("Journal"    , "journal", thisSession);
  invPanelButton            = makeNavPanelButton("Inventory"  , "inv"    , thisSession);
  spellPanelButton          = makeNavPanelButton("Spellbook"  , "spells" , thisSession);
  charInfoPanelButton       = makeNavPanelButton("Character"  , "info"   , thisSession);
  partyControlPanelButton   = makeNavPanelButton("Party"      , "party"  , thisSession);
  sessionOptionsPanelButton = makeNavPanelButton("Options"    , "options", thisSession);
  filePanelButton           = makeNavPanelButton("File"       , "file"   , thisSession);

//**********************************************************************
  // add objects to NavPanel

  buttonPanel.add(actionPanelButton);
  buttonPanel.add(areaPanelButton);
  buttonPanel.add(worldPanelButton);
  buttonPanel.add(journalPanelButton);
  buttonPanel.add(invPanelButton);
  buttonPanel.add(spellPanelButton);
  buttonPanel.add(charInfoPanelButton);
  buttonPanel.add(partyControlPanelButton);
  buttonPanel.add(sessionOptionsPanelButton);
  buttonPanel.add(filePanelButton);

  add(buttonPanel);
  add(clockPanel);

} // end constructor()

//**********************************************************************
// public/package methods
//**********************************************************************

//**********************************************************************
  public void keyPressed(KeyEvent k){
    // we need to pass along this key event to the appropriate listener
    if (thisSession.centralPanel.worldPanel.isShowing()){
      // then pass this keyPressed event to it.
      thisSession.centralPanel.worldPanel.keyPressed(k);
    } // end if worldPanel showing
  } // end keyPressed

//**********************************************************************
  public void keyReleased(KeyEvent k){
      // we need to pass along this key event to the appropriate listener
      if (thisSession.centralPanel.worldPanel.isShowing()){
        // then pass this keyPressed event to it.
        thisSession.centralPanel.worldPanel.keyReleased(k);
      } // end if worldPanel showing
  } // end keyReleased

//**********************************************************************
  public void keyTyped(KeyEvent k){
      // we need to pass along this key event to the appropriate listener
      if (thisSession.centralPanel.worldPanel.isShowing()){
        // then pass this keyPressed event to it.
        thisSession.centralPanel.worldPanel.keyTyped(k);
      } // end if worldPanel showing
  } // end keyTyped

//**********************************************************************
// private methods
//**********************************************************************

  private JButton makeNavPanelButton(String title, String actionCommand, Session thisSession){
    JButton button = new JButton(title);
    button.setHorizontalAlignment(SwingConstants.CENTER);
    button.setPreferredSize(new Dimension(Constants.NAVPANEL_BUTTON_WIDTH,
                                          Constants.NAVPANEL_BUTTON_HEIGHT));

    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.setActionCommand(actionCommand);
    button.addActionListener(thisSession);
    button.addKeyListener(this);

    return button;
  } // end makeNavPanelButton

//**********************************************************************
} // end class NavPanel
