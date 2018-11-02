package fantasy_adventure;

//*****************************************************************************
// imports
//*****************************************************************************
import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

//*****************************************************************************
public class ConsoleViewer extends    JPanelWithBackground
                           implements ActionListener{

//*****************************************************************************
// static declarations
//*****************************************************************************
  static int         consolePanelHeight = 30; // NOTE: this will change dynamically!
  static JScrollPane consoleViewer;
  static JPanel      buttonPanel;
  static JButton     upButton;
  static JButton     dnButton;

  static String      upButtonText = "+";
  static String      dnButtonText = "-";

  static int         adjusetIncrement = 30;
  static int         minConsolePanelHeight = 30;
  static int         maxConsolePanelHeight = calcMaxConsolePanelHeight();

//*****************************************************************************
// member declarations
//*****************************************************************************

  ActionPanel parent;

//*****************************************************************************
// constructors
//*****************************************************************************
  public ConsoleViewer(JComponent content, ActionPanel parent){
    // purpose of this constructor is to create the top adjustable border and the viewer below.
    super();
    setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));
    setBorder(null);

    this.parent = parent;

    // setup top border - for adjusting
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new BorderLayout());
    buttonPanel.setBorder(null);

    upButton   = makeAdjButton(upButtonText);
    dnButton = makeAdjButton(dnButtonText);

    buttonPanel.add(upButton,   BorderLayout.NORTH);
    buttonPanel.add(dnButton, BorderLayout.SOUTH);

    // setup viewer
    consoleViewer = new JScrollPane(content);

    consoleViewer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    consoleViewer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    consoleViewer.setBackground(Constants.YELLOW_DARK);
    consoleViewer.setBorder(null);

    // put parts together
    add(consoleViewer);
    add(buttonPanel);

    // set sizes
    refreshComponentSizes();

  } // end constructor

//*****************************************************************************
// static methods
//*****************************************************************************

//*****************************************************************************
// public methods
//*****************************************************************************
  public void actionPerformed(ActionEvent e){
    // purpose of this method is to respond to the user's choice to change
    // the size of the text console view window.

    String command = e.getActionCommand();

    if (command.equalsIgnoreCase(upButtonText)){
      changeViewSize(adjusetIncrement);
    } // end if up

    else if (command.equalsIgnoreCase(dnButtonText)){
      changeViewSize(-adjusetIncrement);
    } // end if

    else{ // command unknown
      Popup.createWarningPopup("ConsoleViewer/ActionPeformed unhandled.");
    } // end else

  } // end actionPerformed

//*****************************************************************************
// package methods
//*****************************************************************************
  void changeViewSize(int adj){
    // purpose of this method is to change the height of the viewer.
    // we added a property change listener to the parent, so this should
    // trigger the methods and therefore the redrawing of the parent.

    consolePanelHeight += adj;

    if (consolePanelHeight < minConsolePanelHeight)
      consolePanelHeight = minConsolePanelHeight;

    if (consolePanelHeight > maxConsolePanelHeight)
      consolePanelHeight = maxConsolePanelHeight;

    // reset the sizes of all children.
    refreshComponentSizes();

  } // end changeViewSize

//*****************************************************************************
// private methods
//*****************************************************************************
  private void refreshComponentSizes(){
    // purpose of this method is to set all the sizes for the components
    // from one place, so they can be called from anywhere.

    // main panel
    setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                   consolePanelHeight));

    // button panel
    buttonPanel.setPreferredSize(new Dimension(Constants.CONSOLE_ADJ_WIDTH,
                                               consolePanelHeight));

    // consoleViewer (actual scrollPane)
    consoleViewer.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH -
                                                   Constants.CONSOLE_ADJ_WIDTH,
                                                 consolePanelHeight));

    // adjustment buttons
    upButton.setPreferredSize(new Dimension(Constants.CONSOLE_ADJ_WIDTH,
                                            Math.min((int)(consolePanelHeight / 2),
                                                           Constants.CONSOLE_MAX_ADJ_HEIGHT)));

    dnButton.setPreferredSize(new Dimension(Constants.CONSOLE_ADJ_WIDTH,
                                            Math.min((int)(consolePanelHeight / 2),
                                                           Constants.CONSOLE_MAX_ADJ_HEIGHT)));

    // now, reset parent to change actionArea view.
    parent.resetActionAreaHeight();

    revalidate();
    repaint();
  } // end refreshComponentSizes()

//*****************************************************************************
  private JButton makeAdjButton(String title){
    // purpose of this method is to create the buttons
    // that will adjust the height of the viewer
    JButton button = new JButton(title);
    button.setHorizontalTextPosition(JButton.CENTER);
    button.setActionCommand(title);
    button.addActionListener(this);
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    return button;
  } // end makeAdjButton

  private static int calcMaxConsolePanelHeight(){
    int result = Constants.CENTRALPANEL_HEIGHT -
                          ActionPanel.topTextBarHeight -
                          ActionPanel.controlPanelHeight;

    return result;
  } // end calcMaxConsolePanelHeight

} // end class ConsoleViewer
