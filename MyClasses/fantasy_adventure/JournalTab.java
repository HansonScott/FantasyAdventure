package fantasy_adventure;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

//************************************************************************
//imports


//************************************************************************

public class JournalTab extends    JPanelWithBackground
                        implements ActionListener,
                                   MouseListener,
                                   KeyListener,
                                   FocusListener,
                                   WindowStateListener{

  /* The purpose of this class is to handle all the information and interface
   * relating to the tab showing the journal information for the character
   */

//************************************************************************
// static declarations
//************************************************************************
  static private JFrame     inputFrame;
  static private JPanel     inputPanel;
  static private JButton    okButton, cancelButton;
  static private JTextField inputField;

  static private JFrame     confirmFrame;
  static private JPanel     confirmPanel;
  static private JButton    confirmDeleteButton, keepButton;
  static private JLabel     confirmLabel;

//************************************************************************
// member declarations
//************************************************************************
  JPanel          topicSide, topicArea;
  JButton         newTopicButton, deleteTopicButton;
  JTextArea       content;
  JScrollPane     topicPane, contentPane;
  PlayerCharacter character;
  Topic           currentlySelectedTopic;

//************************************************************************
// constructors
//************************************************************************
  public JournalTab (PlayerCharacter pc) {
    // the purpose of this constructor is to create the GUI for interfacing
    // and showing all the journal information
    super();
    setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                  (Constants.CENTRALPANEL_HEIGHT - Constants.INSET_FOR_TAB)));

//    setBackground(Color.magenta);
    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // setup tab ********************************************
    character = pc;

    // setup internal components*****************************
    topicArea   = new JPanel();
    topicArea.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 3) - 30,
                                             Constants.CENTRALPANEL_HEIGHT -
                                             Constants.INSET_FOR_TAB - 4));
    topicArea.setLayout(new FlowLayout(FlowLayout.CENTER,
                                       (int)(Constants.CENTRALPANEL_WIDTH / 3) - 10, 2));

//    topicArea.setBorder(BorderFactory.createTitledBorder("topic area"));
    topicSide = new JPanel();
    topicSide.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 3) - 4,
                                             Constants.CENTRALPANEL_HEIGHT -
                                             Constants.INSET_FOR_TAB));

    topicPane   = new JScrollPane(topicArea);
    topicPane.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 3),
                                             Constants.CENTRALPANEL_HEIGHT -
                                             Constants.INSET_FOR_TAB));

//    topicPane.setBorder(BorderFactory.createTitledBorder("topic Pane"));

    content     = MyUtils.makeEditableTextBox(30, 25, Constants.BROWN_LIGHT,
                                              Constants.BROWN_DARK, FileNames.JOURNAL_INTRO);
    content.addFocusListener(this);
    content.setFont(new Font(content.getFont().getName(), Font.ITALIC, 14));


    contentPane = new JScrollPane(content);
    contentPane.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH * 2 / 3),
                                             Constants.CENTRALPANEL_HEIGHT -
                                             Constants.INSET_FOR_TAB));

//    contentPane.setBorder(BorderFactory.createTitledBorder("content Pane"));

    newTopicButton    = makeTopicButton("<add new topic>");
    deleteTopicButton = makeTopicButton("<delete current topic>");

    topicSide.add(newTopicButton);
    topicSide.add(deleteTopicButton);

    // now fill topics from character into list.
    refreshTopicList();

    topicSide.add(topicPane);
    // add components to view
    add(topicSide);
    add(contentPane);

  } // end constructor

//************************************************************************
// public methods
//************************************************************************
  public void actionPerformed(ActionEvent a){
    String command = a.getActionCommand();

    if (command.equals("<add new topic>")){
      handleAddNewTopic();
    } // end if add new topic

    //********************************************************************
    else if (command.equals("<delete current topic>")){
      handleDeleteTopic();
    } // end if delete

    //********************************************************************
    else if (command.equals("OK")){
      // user has confirmed the new journal topic creation.
//      Popup.createInfoPopup("OK captured.");
      if (inputField.getText() != null){
        makeNewTopic(inputField.getText());
        resetInputFrame();
      } // title non-null
    } // end ok

    //********************************************************************
    else if (command.equals("Cancel")){
      // user has chosen to cancel making a new topic, so hide the window.
      if (inputFrame != null){
        resetInputFrame();
      } // end if non-null
    } // end cancel

    //********************************************************************
    else if (command.equals("Yes, Delete.")){
      // user has confirmed deletion of the current topic
      deleteTopic();
    } // end delete topic

    //********************************************************************
    else if (command.equals("No, Keep it.")){
      if (confirmFrame != null){
        confirmFrame.hide();
      } // end non-null
      return;
    } // end keep topic


    // if not for an action button, then it must be a button from the topic list
    // so check through the list of topics and display the content of that topic
    // in the content text area.

    for (int i = 0; i < character.journal.topics.length &&
                        character.journal.topics[i] != null; i++){
      if (character.journal.topics[i].getTitle().equals(command)){
        // when the user clicks on a button to change topics, we need to do a few things...
        // display the new topic's text
        content.setText(character.journal.topics[i].getContent());

        // update the reference for action buttons
        currentlySelectedTopic = character.journal.topics[i];
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
      if (k.getSource() == inputFrame ||
          k.getSource() == inputPanel ||
          k.getSource() == inputField){
        resetInputFrame();
      } // end if inputFrame

      else if (k.getSource() == confirmFrame ||
              k.getSource() == confirmDeleteButton ||
              k.getSource() == confirmPanel){
        if (confirmFrame != null){
          confirmFrame.hide();
        } // end non-null
      } // end if confirmFrame
    } // end escape

    else if (k.getKeyCode() == KeyEvent.VK_ENTER){
      if(k.getSource() == inputFrame ||
         k.getSource() == inputPanel ||
         k.getSource() == inputField){
        if (inputField.getText() != null){
          makeNewTopic(inputField.getText());
          resetInputFrame();
        } // title non-null
      } // end inputFrame

      else if(k.getSource() == confirmFrame ||
              k.getSource() == confirmDeleteButton ||
              k.getSource() == confirmPanel){
        deleteTopic();
      } // end confirmFrame
    } // end enter

  } // end key pressed

//************************************************************************
  public void keyReleased(KeyEvent k){} // end key released

//************************************************************************
  public void keyTyped(KeyEvent k){} // end key typed

//************************************************************************
  public void focusLost(FocusEvent f){
    processHidingWindow(f.getSource());
  } // end focusLost

//************************************************************************
  public void focusGained(FocusEvent f){}

//************************************************************************
  public void windowStateChanged(WindowEvent w){
    if (w.getNewState() == WindowEvent.WINDOW_LOST_FOCUS){
      processHidingWindow(w.getSource());
    }// end if
  } // end windowStateChanged

//************************************************************************
// package methods
//************************************************************************
  void refreshTopicList(){
    // remove all the topics first, then reload them.
    topicArea.removeAll();

    //NOTE: at this point, the following yields none.  Did we delete the topics?
    for (int i = 0; i < character.journal.topics.length &&
         character.journal.topics[i]!= null; i++){
//      Popup.createInfoPopup("adding journal topic #" + i);
      character.journal.topics[i].getLabel().setActionCommand(
      character.journal.topics[i].getTitle());
      character.journal.topics[i].getLabel().addActionListener(this);
      topicArea.add(character.journal.topics[i].getLabel());
    } // end for loop
    revalidate();
    repaint();
  } // end refreshTopicList

//************************************************************************
// private methods
//************************************************************************
  private void processHidingWindow(Object o){
      // if the focus has been lost on this component...
  if (o == inputFrame){
    inputFrame.hide();
  } // end inputFrame
  else if (o == confirmFrame){
    confirmFrame.hide();
  } // end confirmFrame

  else if (currentlySelectedTopic != null){
    // then we need to capture the material.
    currentlySelectedTopic.setContent(content.getText());
  } // end not null

  } // end processHidingWindow

  private void deleteTopic(){
    // purpose of this class is to process the deletion of the
    // curretnly selected topic

    if (currentlySelectedTopic == null) return;
    if (character.journal.numTopics == 0) return;

    character.journal.removeTopicFromJournal(currentlySelectedTopic);
    currentlySelectedTopic = null;

    // once done, close the frame.
    if (confirmFrame != null){
      confirmFrame.hide();
    } // end non-null

    if (character.journal.topics.length > 0 &&
        character.journal.topics[0] != null) {
      content.setText(character.journal.topics[0].getContent());
      currentlySelectedTopic = character.journal.topics[0];
    } // end if non-null
    else{
      content.setText(FileNames.JOURNAL_INTRO);
    } // end no topics

    refreshTopicList();
  } // end deleteTopic

//************************************************************************
  private void makeNewTopic(String s){
    // we can actually create a new topic with the title.
    // and attach this topic to the character's journal.
    Topic t = new Topic(s);
    character.journal.addTopicToJournal(t);
    content.setText(t.getContent());
    currentlySelectedTopic = t;
    refreshTopicList();
  } // end makeNewTopic

//************************************************************************
  private void resetInputFrame(){
    // purpose of this method is simply to consolidate code for
    // closing and resetting the new topic input frame.
    inputFrame.hide();
    inputField.setText("<Type new title here>");
  } // end resetInputFrame

//************************************************************************
  private void handleAddNewTopic(){
//    Popup.createInfoPopup("new topic handling coming soon...");
    // the purpose of this method it to allow the user to create a new topic
    // so, just create a small interface and wait to capture the material.
    if (inputFrame == null){
      createInputFrame();
    } // end if null
    inputFrame.show();
  } // end handleAddNewTopic

//************************************************************************
  private void handleDeleteTopic(){
//    Popup.createInfoPopup("delete topic handling coming soon...");

    // use currentlySelectedTopic as reference to topic being deleted.
    if (currentlySelectedTopic == null)return;
    else {
      // user wants to delete current entry.
      if (confirmFrame == null){
        createConfirmFrame();
      } // end if
      confirmFrame.show();
    } // end else
  } // end handleDeleteTopic

//************************************************************************
  private void createConfirmFrame(){
    // purpose of this method is to create the static dialog that confirms
    // the deletion of a topic.
    confirmFrame = new JFrame();

    confirmFrame.setSize(300, 120);
    confirmFrame.setUndecorated(true);
    confirmFrame.setLocation( (int) ((Constants.SCREEN_WIDTH - 300) / 2),
                              (int) ((Constants.SCREEN_HEIGHT - 100) / 2));
    confirmFrame.addFocusListener(this);
    confirmFrame.addWindowStateListener(this);

    confirmPanel = new JPanel();
    confirmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
    confirmPanel.setPreferredSize(confirmFrame.getSize());
    confirmPanel.setBorder(BorderFactory.createLineBorder(Color.black));

    confirmDeleteButton = makeInputButton("Yes, Delete.");
    keepButton          = makeInputButton("No, Keep it.");
    confirmLabel        = new JLabel("<html>Are you sure you want to delete this topic?<br>This action cannot be undone.</html>");

    confirmPanel.add(confirmLabel);
    confirmPanel.add(confirmDeleteButton);
    confirmPanel.add(keepButton);

    confirmFrame.getContentPane().add(confirmPanel);

  } // end createConfirmFrame

//************************************************************************
  private void createInputFrame(){
    inputFrame = new JFrame();
    inputFrame.setSize(300, 120);
    inputFrame.setUndecorated(true);
    inputFrame.setLocation( (int) ((Constants.SCREEN_WIDTH - 300) / 2),
                            (int) ((Constants.SCREEN_HEIGHT - 100) / 2));
    inputFrame.addFocusListener(this);
    inputFrame.addKeyListener(this);
    inputFrame.addWindowStateListener(this);

    inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
    inputPanel.setPreferredSize(inputFrame.getSize());
    inputPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    inputPanel.addKeyListener(this);

    inputField = new JTextField("<Type new title here>", 20);
    inputField.setBorder(BorderFactory.createLoweredBevelBorder());
    inputField.addKeyListener(this);

    okButton     = makeInputButton("OK");
    cancelButton = makeInputButton("Cancel");

    inputPanel.add(inputField);
    inputPanel.add(cancelButton);
    inputPanel.add(okButton);

    inputFrame.getContentPane().add(inputPanel);
  } // end createTitleInputBox

//************************************************************************
  private JButton makeInputButton(String title){
    // the purpose of this method is to create the small buttons that
    // appear on the topic confirmation and new topic input boxes
    JButton button = new JButton(title);
    button.setPreferredSize(new Dimension(100, 40));
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.setActionCommand(title);
    button.addActionListener(this);
    button.addKeyListener(this);
    return button;
  } // end makeInputButton

 //************************************************************************
  private JButton makeTopicButton(String title){
    // the purpose of this method is to create the large buttons that
    // appear on the left side of the screen, for the topic options as well as
    // the buttons for each topic in the journal.
    JButton button = new JButton(title);
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.addActionListener(this);
    button.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 3) - 32,
                                          Constants.JOURNAL_TOPIC_HEIGHT));
    return button;
  } // end makeTopicButton

//************************************************************************
} // end class JournalTab
