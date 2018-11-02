package fantasy_adventure;
// purpose of this class is to create the GUI for the main menu

// imports
import java.awt.*;       // support basic GUI components
import javax.swing.*;    // support swing GUI components

public class MainMenu extends JFrame{

// initialize variables and objects
  JLabel  titleLabel, authorLabel, blankLabel;
  JButton newGameButton, savedGameButton, optionsButton, exitGameButton;
  CGen    myCGen;

// constructor
public MainMenu() {

  // create and setup JFrame, adjust to screen size

  super("Fantasy Adventure - Main Menu");

  setUndecorated(true);
  Container mainMenuContent = getContentPane();
  setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
  setLocation(1,1);
  setResizable(false);
  setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

  // adjust verticle spacing to fit screen size.
  int vertSpacing = (int)(Constants.SCREEN_HEIGHT - 600)/10 + 1;
  mainMenuContent.setLayout(new FlowLayout(FlowLayout.CENTER, Constants.SCREEN_WIDTH, vertSpacing));

  // create components within Main Menu

  titleLabel      = new JLabel (new ImageIcon("images/title.jpg"));

  newGameButton   = makeMenuButton("Start New Game", null);
  savedGameButton = makeMenuButton("Load Saved Game", null);
  optionsButton   = makeMenuButton("Game Options", "Start Game Options");
  exitGameButton  = makeMenuButton("Exit Game", null);

  // add listeners to buttons - done by FantasyAdventure!

  // add components to Main Menu

  mainMenuContent.add(titleLabel);
  mainMenuContent.add(newGameButton);
  mainMenuContent.add(savedGameButton);
  mainMenuContent.add(optionsButton);
  mainMenuContent.add(exitGameButton);

} // end constructor

// public methods

// private methods

  private JButton makeMenuButton(String title, String command){
    // purpose of this method is to refactor the code to make each of the buttons
    // for the main menu.  This allows us to change code for the buttons and have
    // it all in one place.
    JButton button = new JButton(title);
    button.setBorder(BorderFactory.createRaisedBevelBorder());

    int buttonWidth = (int)Constants.SCREEN_WIDTH / 5;
    int buttonHeight = (int)Constants.SCREEN_HEIGHT / 15;

    button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
    if (command != null)button.setActionCommand(command);
    return button;
  } // end makeMenuButton

} // end class MainMenu
