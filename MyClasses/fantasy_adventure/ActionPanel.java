package fantasy_adventure;

import java.awt.*;       // supports basic GUI components
import javax.swing.*;    // supports swing GUI components

//******************************************************************************
public class ActionPanel extends JPanelWithBackground {

/* the purpose of this class is to show all the info relating to the immediate
 * local area.  This will include:
 * - top text bar which will hold minor labels of text information (date, temp, etc.)
 * - the action area the party is in, (Center - uses all available space)
 * - the feedback console panel for text input/output (adjustable height)
 * - a wrapper control panel (width set by screen, height static)
 * - the quickStats of the character selected, (left side of UIPanel
 * - the actionButtons available to the character currently selected.(Right side of UIPanel)
 */

//******************************************************************************
// static declarations
//******************************************************************************
  // component heights
  static int topTextBarHeight       = 25;
  static int consoleTextHeight      = 200;
  static int controlPanelHeight     = 160;
  static int actionAreaHeight;  // calc the leftover height with resetActionHeight()

  // component widths
  static int numTopLabels           = 5;
  static int topLabelWidth          = 75;
  static int quickStatsPanelWidth   = (int)(Constants.CENTRALPANEL_WIDTH / 2);
  static int actionButtonPanelWidth = (int)(Constants.CENTRALPANEL_WIDTH / 2);

//******************************************************************************
// instance declarations
//******************************************************************************

  // main panels
  JPanel        topTextBar;        // shows basic feedback of time, gold, weather, etc.
  JLabel        dateLabel;
  JLabel        weatherLabel;
  JLabel        areaLabel;
  JLabel        goldLabel;
  JLabel        otherLabel;

  String        dateLabelText    = "<date>";        // NOTE: not final, will be updated regularly
  String        weatherLabelText = "<weather>";     // NOTE: not final, will be updated regularly
  String        areaLabelText    = "<area title>";  // NOTE: not final, will be updated regularly
  String        goldLabelText    = "<party gold>";  // NOTE: not final, will be updated regularly
  String        otherLabelText   = "<other label>"; // NOTE: not final, will be updated regularly

  ActionArea    actionArea;        // shows battle action and interacts with user.
  JScrollPane   actionAreaPane;    // allows for navigating the actionArea

  JPanel        consoleTextArea;   // holds text (and conversation)
  ConsoleViewer consolePane;       // gives text feedback and holds conversation text w/ interaction.

  JPanel            controlPanel;      // a simple wrapper to hold the quickStatsPanel and the ActionButtonPanel.
  QuickStatsPanel   quickStatsPanel;   // lists basic info of living Object selected (blank for none)
  ActionButtonPanel actionButtonPanel; // a set of buttons to direct the selected character.

//******************************************************************************
// constructor
//******************************************************************************
public ActionPanel (PlayerCharacter PC){
  // setup LocalPanel
  super (FileNames.SESSION_TEXTURE,
         JPanelWithBackground.TILE);

  setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
  setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                 Constants.CENTRALPANEL_HEIGHT));

  //**********************************************
  // setup topTextBar
  topTextBar = new JPanelWithBackground();
  topTextBar.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                            topTextBarHeight));
  topTextBar.setBackground(Constants.RED_DARK);
  topTextBar.setBorder(null);
  topTextBar.setLayout(new FlowLayout(FlowLayout.LEFT, ((int)(Constants.CENTRALPANEL_WIDTH
                                                        - (topLabelWidth * (numTopLabels + 1)))
                                                        / numTopLabels),
                                                        1));

  // setup labels
  dateLabel    = makeTopLabel(dateLabelText);
  weatherLabel = makeTopLabel(weatherLabelText);
  areaLabel    = makeTopLabel(areaLabelText);
  goldLabel    = makeTopLabel(goldLabelText);
  otherLabel   = makeTopLabel(otherLabelText);

  // add labels to bar
  topTextBar.add(dateLabel);
  topTextBar.add(weatherLabel);
  topTextBar.add(areaLabel);
  topTextBar.add(goldLabel);
  topTextBar.add(otherLabel);

  //**********************************************
  // setup ActionArea
  actionArea = new ActionArea();

  //**********************************************
  // setup ActionArea
  actionAreaPane = new JScrollPane(actionArea);
  actionAreaPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  actionAreaPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
  actionAreaPane.setBackground(Color.black);
  resetActionAreaHeight();

  // now, hook up all the mouse-related listeners for navigation...

  //**********************************************
  // setup ConsolePanel (text area)
  // NOTE: this has to be a JPanel because of dialog-related stuff (seperate popups?)
  consoleTextArea = new JPanelWithBackground();
  consoleTextArea.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                                 consoleTextHeight));
  consoleTextArea.setBackground(Constants.BROWN_DARK);

  //**********************************************
  // setup consolePane (viewer to hold text area)
  consolePane = new ConsoleViewer(consoleTextArea, this);

  //**********************************************
  // setup ControlPanel
  // (this is just a container for the quickStats and actionButtons)
  controlPanel = new JPanelWithBackground();
  controlPanel.setLayout(new BorderLayout());
  controlPanel.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                              controlPanelHeight));
  controlPanel.setBackground(Constants.BROWN_LIGHT);

  //**********************************************
  // setup quickStatsPanel (selected char pic and basic/general stats)
  quickStatsPanel = new QuickStatsPanel();

  //**********************************************
  // setup actionButtonpanel (char buttons for actions)
  actionButtonPanel = new ActionButtonPanel();

  //**********************************************
  // add panels to UIPanel
  controlPanel.add(quickStatsPanel,   BorderLayout.WEST);
  controlPanel.add(actionButtonPanel, BorderLayout.EAST);

  //**********************************************
  // add panels to LocalPanel
  add(topTextBar);
  add(actionAreaPane);
  add(consolePane);
  add(controlPanel);

} // end constructor(PC)

//******************************************************************************
// public/package methods
//******************************************************************************


//******************************************************************************
// private methods
//******************************************************************************
  void resetActionAreaHeight(){
    // purpose of this method is to take the heights of all the different componenets
    // in this panel and set the height to be the rest of the area.
    actionAreaHeight   = (Constants.CENTRALPANEL_HEIGHT -
                          topTextBarHeight -
                          controlPanelHeight -
                          ConsoleViewer.consolePanelHeight);

    actionAreaPane.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                               actionAreaHeight));

    this.revalidate();
    this.repaint();

  } // end resetActionHeight

//******************************************************************************
  private JLabel makeTopLabel(String title){
    // purpose of this method is to setup the top bar labels
    // they each have dynamically updating information,
    // so there are unique methods to update the material.
    // the String title is just for initial placement

    // NOTE: because these labels are just feedback, we do not attach listeners.

    JLabel label = new JLabel(title);
    label.setPreferredSize(new Dimension(topLabelWidth,topTextBarHeight));
    label.setForeground(Constants.YELLOW_SOFT);
    label.setBackground(Color.BLACK);
    label.setBorder(null);

    return label;
  } // end makeTopLabel

//******************************************************************************
} // end class LocalPanel
