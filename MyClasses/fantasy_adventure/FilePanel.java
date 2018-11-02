package fantasy_adventure;

//************************************************************************
//imports
import java.io.*;
import java.awt.*;       // supports basic GUI components
import java.awt.event.*; // supports listeners
import javax.swing.*;    // supports swing GUI components


//************************************************************************

public class FilePanel extends    JPanelWithBackground
                       implements ActionListener{

  /* The purpose of this class is to let the user:
   * - save the session and stay at this screen
   * - end the session and return to the mainMenu
   * - start a new session from a different saved character.
   */

//************************************************************************
// static declarations
//************************************************************************

  private static int BUTTON_WIDTH  = ((int)Constants.SCREEN_WIDTH / 4);
  private static int BUTTON_HEIGHT = ((int)Constants.SCREEN_HEIGHT / 8);
  private static int V_GAP         = (BUTTON_HEIGHT / 2);
  private static int FONT_SIZE     = ((int)Constants.SCREEN_HEIGHT / 30 ); // (12 - 25)

//************************************************************************
// member declarations
//************************************************************************

  JLabel blankSpacer;
  JButton saveButton, exitButton, loadButton;

//************************************************************************
// constructors
//************************************************************************

  public FilePanel () {
    // the purpose of this constructor is to create the panel that the user
    // can interact with and do the desired operations.

    // setupFileScreen JPanel
  super (FileNames.SESSION_TEXTURE,
         JPanelWithBackground.TILE);

    setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH, Constants.CENTRALPANEL_HEIGHT));
    setLayout(new FlowLayout(FlowLayout.CENTER, Constants.SCREEN_WIDTH, V_GAP));

    // setup internal components

    blankSpacer = new JLabel("");
    blankSpacer.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

    saveButton = makeFileButton("Save");
    loadButton = makeFileButton("Load");
    exitButton = makeFileButton("Exit");

    add(blankSpacer);
    add(saveButton);
    add(loadButton);
    add(exitButton);

  } // end constructor

//************************************************************************
// public methods
//************************************************************************

  public void actionPerformed (ActionEvent e) {
    // the purpose of this method is to

    String command = e.getActionCommand();

    if (command.equals("Save")){
      // user has chosen to save their game
      saveCurrentSession();
    } // end if

    else if (command.equals("Load")){
      // user has chosen to Load their game
      loadNewSession();
    } // end if

    else if (command.equals("Exit")){
      // user has chosen to Exit this session
//      System.exit(0);
    } // end if

  } // end actionPerformed()

//************************************************************************
// package methods
//************************************************************************


//************************************************************************
// private methods
//************************************************************************
  private void loadNewSession(){
    // purpose of this method is to allow the user to load a save file and
    // start a new session, dumping the current one.
    // (*remember to warn user to save current session separately)

  } // end loadNewSession()

//************************************************************************
  private void saveCurrentSession(){
    // the purpose of this method is to save the currently running session
    // popup the fileChooserBox, and save into a save game file (chr)

    //  something like this:
    //  thisSession.saveToFile();

  } // end saveCurrentSession()

//************************************************************************
  private JButton makeFileButton(String title){
    // purpose of this method is to create a button for the fileScreen

    JButton button = new JButton(title);
    button.setFont(new Font("Ariel", Font.ITALIC, FONT_SIZE));
    button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.setActionCommand(title);
    button.addActionListener(this);
    return button;
  } // end makeFileButton

//************************************************************************
} // end class filePanel
//************************************************************************
