package fantasy_adventure;

//************************************************************************
//imports

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

//************************************************************************

public class QuestsTab  extends    JPanelWithBackground
                        implements ActionListener,
                                   MouseListener,
                                   KeyListener{

  /* The purpose of this class is to handle all the information and interface
   * relating to the tab showing the quest information for the character
   */

//************************************************************************
// static declarations
//************************************************************************

  static final int BUTTONPANEL_HEIGHT = (int)(Constants.CENTRALPANEL_HEIGHT / 10);

  static boolean   showCompleteTopics   = true;
  static boolean   showIncompleteTopics = true;

//************************************************************************
// member declarations
//************************************************************************
  static private JPanel        buttonPanel;
  static private JRadioButton  chronButton,      alphaButton;
  static private JCheckBox     incompleteButton, completeButton;

  static private JPanel        mainPanel,  topicPanel;
  static private JTextArea     content;
  static private JScrollPane   topicPane,  contentPane;

  static private JLabel        spacerLabel;

  PlayerCharacter character;
  Topic currentSelectedTopic;

//************************************************************************
// constructors
//************************************************************************
  public QuestsTab (PlayerCharacter pc) {
    // the purpose of this constructor is to create the GUI for interfacing
    // and showing all the quest information
    super();
    setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                  (Constants.CENTRALPANEL_HEIGHT - Constants.INSET_FOR_TAB)));

    setBackground(Color.yellow);
    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // setup tab ********************************************
    character = pc;

    // setup internal components*****************************
    // setup buttonPanel ************************************

    buttonPanel = new JPanel();
    buttonPanel.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                               BUTTONPANEL_HEIGHT));

    buttonPanel.setBorder(BorderFactory.createRaisedBevelBorder());

    chronButton      = makeJRadioButton("Chronological");
    alphaButton      = makeJRadioButton("Alphabetical");
    incompleteButton = makeJCheckButton("<html><center>Incomplete<br>Quests</html>");
    completeButton   = makeJCheckButton("<html><center>Complete<br>Quests</html>");

    spacerLabel = new JLabel("");
    spacerLabel.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 5),
                                                     BUTTONPANEL_HEIGHT - 2));

    buttonPanel.add(chronButton);
    buttonPanel.add(alphaButton);
    buttonPanel.add(spacerLabel);
    buttonPanel.add(incompleteButton);
    buttonPanel.add(completeButton);

    // setup mainPanel    ************************************
    mainPanel = new JPanel();
    mainPanel.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                             Constants.CENTRALPANEL_HEIGHT -
                                             Constants.INSET_FOR_TAB -
                                             BUTTONPANEL_HEIGHT));

    // setup topicPanel   ************************************
    topicPanel   = new JPanel();
    topicPanel.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 3) - 30,
                                             Constants.CENTRALPANEL_HEIGHT -
                                             Constants.INSET_FOR_TAB - 4 -
                                             BUTTONPANEL_HEIGHT - 10));
    topicPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
                                       (int)(Constants.CENTRALPANEL_WIDTH / 3) - 10, 2));

    topicPane   = new JScrollPane(topicPanel);
    topicPane.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 3 - 10),
                                             Constants.CENTRALPANEL_HEIGHT -
                                             Constants.INSET_FOR_TAB -
                                             BUTTONPANEL_HEIGHT - 10));

    mainPanel.add(topicPane);

    // setup contentPanel ************************************
    content = MyUtils.makeTextBox(30, 25, Constants.BROWN_LIGHT,
                                          Constants.BROWN_DARK,
                                          FileNames.QUEST_INTRO);
    content.setFont(new Font(content.getFont().getName(), Font.ITALIC, 14));

    contentPane = new JScrollPane(content);
    contentPane.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH * 2 / 3) - 10,
                                             Constants.CENTRALPANEL_HEIGHT -
                                             Constants.INSET_FOR_TAB -
                                             BUTTONPANEL_HEIGHT - 10));

    mainPanel.add(contentPane);

    // add components to view
    add(buttonPanel);
    add(mainPanel);

    // now fill topics from character into list.
    refreshTopicList();

  } // end constructor

//************************************************************************
// public methods
//************************************************************************
  public void actionPerformed(ActionEvent a){
    String command = a.getActionCommand();
    //********************************************************************
    if (command.equals("Chronological")){
      // user has clicked on the chronological button
      processChronButton();
    } // end if add new topic

    //********************************************************************
    else if (command.equals("Alphabetical")){
      // user has clicked on the alphabetical button
      processAlphaButton();
    } // end ok

    //********************************************************************
    else if (command.equals("Incomplete Quests")){
      // user has clicked on the incomplete button
      processIncompleteButton();
    } // end ok

    //********************************************************************
    else if (command.equals("Complete Quests")){
      // user has clicked on the incomplete button
      processCompleteButton();
    } // end ok

    //********************************************************************
    else if (command.equals("")){
    } // end ok

    //********************************************************************
    // if not for an action button, then it must be a button from the topic list
    // so check through the list of topics and display the content of that topic
    // in the content text area.

    for (int i = 0; i < character.questList.topics.length &&
                        character.questList.topics[i] != null; i++){
      if (character.questList.topics[i].getTitle().equals(command)){
        // when the user clicks on a button to change topics, we need to do a few things...
        // display the new topic's text
        content.setText(character.questList.topics[i].getContent());

        // update the reference for action buttons
        currentSelectedTopic = character.questList.topics[i];
      } // end if
    } // end for loop
  } // end actionPerformed

//************************************************************************
  public void mousePressed(MouseEvent m){}

//************************************************************************
  public void mouseReleased(MouseEvent m){}

//************************************************************************
  public void mouseClicked(MouseEvent m){}

//************************************************************************
  public void keyPressed(KeyEvent k){
//    Popup.createInfoPopup("KeyPressed recognized by the JournalTab class!" + MyTextIO.newline
//                         +"getKeyChar: "  + k.getKeyChar()               + MyTextIO.newline
//                         +"getKeyCode: "  + k.getKeyCode()               + MyTextIO.newline
//                         +"getKeyText: "  + k.getKeyText(k.getKeyCode()) + MyTextIO.newline
//                         +"isActionKey: " + k.isActionKey());

    if (k.getKeyCode() == KeyEvent.VK_ESCAPE){
    } // end escape

    else if (k.getKeyCode() == KeyEvent.VK_ENTER){
    } // end enter

  } // end key pressed

//************************************************************************
  public void keyReleased(KeyEvent k){} // end key released

//************************************************************************
  public void keyTyped(KeyEvent k){} // end key typed

//************************************************************************
// package methods
//************************************************************************
  void refreshTopicList(){
    // remove all the topics first, then reload them.
    topicPanel.removeAll();

    for (int i = 0; i < character.questList.topics.length &&
         character.journal.topics[i]!= null; i++){

      // only show those topics that fit the criteria specified.
      if ((character.questList.topics[i].isComplete() == true  && showCompleteTopics) ||
          (character.questList.topics[i].isComplete() == false && showIncompleteTopics)){

        character.questList.topics[i].getLabel().setActionCommand(
        character.questList.topics[i].getTitle());
        character.questList.topics[i].getLabel().addActionListener(this);
        topicPanel.add(character.questList.topics[i].getLabel());
      } // end if match
    } // end for loop
    revalidate();
//    repaint();
  } // end refreshTopicList

//************************************************************************
// private methods
//************************************************************************
   private void processChronButton(){
     // purpose of this method is to reorder the topics chronologically
     // this requires access to the topic's timestamp, sorted.

     alphaButton.setSelected(false);
     chronButton.setSelected(true);

//     Popup.createInfoPopup("Sorting will be implemented in the future.");
//     Topic.sortByDate(character.questList.topics);
     refreshTopicList();

   } // end processChronButton

//************************************************************************
   private void processAlphaButton(){
     // purpose of this method is to reorder the topics alphabetically
     // this requires access to the topic's title, sorted.

     alphaButton.setSelected(true);
     chronButton.setSelected(false);

//     Popup.createInfoPopup("Sorting will be implemented in the future.");
//     Topic.sortByTitle(character.questList.topics);
     refreshTopicList();

   } // end processAlphaButton

//************************************************************************
   private void processIncompleteButton(){
     // purpose of this method is to toggle the showing/hiding of the
     // quests that are still incomplete
     toggleIncompleteButton();
   } // end processIncompleteButton

//************************************************************************
   private void processCompleteButton(){
     // purpose of this method is to toggle the showing/hiding of the
     // quests that have been completed.
     toggleCompleteButton();
   } // end processCompleteButton

//************************************************************************
  private void toggleCompleteButton(){
    // purpose of this method is to toggle the state of the
    // check box responsible for the includion/exclusion of the
    // complete quests within the quest list.
    // NOTE: since this will change what is shown, it needs to call
    // an update to the topic list.

    if (completeButton.isSelected() == true){
      showCompleteTopics = true;
    } // end true
    else{
      showCompleteTopics = false;
    } // end false

    refreshTopicList();

  }  // end toggleCompleteButton()

//************************************************************************
  private void toggleIncompleteButton(){
    // purpose of this method is to toggle the state of the
    // check box responsible for the includion/exclusion of the
    // incomplete quests within the quest list.
    // NOTE: since this will change what is shown, it needs to call
    // an update to the topic list.

    if (incompleteButton.isSelected() == true){
      showIncompleteTopics = true;
    } // end true
    else{
      showIncompleteTopics = false;
    } // end false

    refreshTopicList();

  } // end toggleIncompleteButton()

//************************************************************************
  private JRadioButton makeJRadioButton(String title){
    // purpose of this method is to create a button for the user to choose
    // the ordering options of the quests
    JRadioButton button = new JRadioButton(title);
    button.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 6),
                                                BUTTONPANEL_HEIGHT - 20));
//    button.setBackground(Color.red);
    button.setActionCommand(title);
    button.addActionListener(this);
    return button;
  } // end makeJRadioButton

//************************************************************************
  private JCheckBox    makeJCheckButton(String title){
    // purpose of this method is to create a check box that allows the
    // user toggle options to show some of the quests but not all.
    JCheckBox button = new JCheckBox(title, true); // default showing all.
    button.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 6),
                                                BUTTONPANEL_HEIGHT - 20));
//    button.setBackground(Color.yellow);
    button.setActionCommand(title);
    button.addActionListener(this);
    return button;
  } // end makeJCheckButton

//************************************************************************
} // end class QuestsTab
