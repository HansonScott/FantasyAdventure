package fantasy_adventure;

// imports
import java.awt.*;       // supports basic GUI components
import java.awt.event.*; // supports listeners
import javax.swing.*;    // supports swing GUI components

public class AlignmentScreen extends CGenScreen{

  // class declaration of objects and variables within AlginmentScreen CentralPanel
  JPanel alignmentPanel;
  JTextArea descPanel;

  // declare objects of alignmentPanel
  JPanel           alignBtnPanel;
  JToggleButton LGButton;
  JToggleButton LNButton;
  JToggleButton LEButton;
  JToggleButton NGButton;
  JToggleButton TNButton;
  JToggleButton NEButton;
  JToggleButton CGButton;
  JToggleButton CNButton;
  JToggleButton CEButton;

  JButton qButton;
  Questionnaire questionnaire;

  JPanel discussionPanel;

  JLabel discussionTitle;
  JButton goodEvilButton;
  JButton lawfulChaoticButton;
  JButton neutralButton;

  // declare objects of descPanel
  String descMessage;

  String LGdesc  = FileNames.LAWFUL_GOOD_DESC;
  String LNdesc  = FileNames.LAWFUL_NEUTRAL_DESC;
  String LEdesc  = FileNames.LAWFUL_EVIL_DESC;
  String NGdesc  = FileNames.NEUTRAL_GOOD_DESC;
  String TNdesc  = FileNames.TRUE_NEUTRAL_DESC;
  String NEdesc  = FileNames.NEUTRAL_EVIL_DESC;
  String CGdesc  = FileNames.CHAOTIC_GOOD_DESC;
  String CNdesc  = FileNames.CHAOTIC_NEUTRAL_DESC;
  String CEdesc  = FileNames.CHAOTIC_EVIL_DESC;
  String GvEdesc = FileNames.GOOD_VS_EVIL_DESC;
  String LvCdesc = FileNames.LAW_VS_CHAOS_DESC;
  String Ndesc   = FileNames.NEUTRAL_DESC;

  public AlignmentScreen(){

  // setup main frame stuff;
  super();
  setTitle("Fantasy Adventure - Character Generation - Step 3: Alignment");
  setIntroMessage(FileNames.CGEN_ALIGNMENT_SCREEN_INTRO);
  setBorder();

  // initialize alignmentPanel (leftSide)
  alignmentPanel = new JPanel();
  alignmentPanel.setLayout(new FlowLayout(FlowLayout.CENTER,250,15));
  alignmentPanel.setPreferredSize(new Dimension(375, 400));

  // setup alignmentbuttonPanel stuff:
  alignBtnPanel = new JPanel();
  alignBtnPanel.setLayout(new GridLayout(3,3));
  alignBtnPanel.setBorder(BorderFactory.createRaisedBevelBorder());
  alignBtnPanel.setPreferredSize(new Dimension(300, 230));

  // setup specific alignemnt buttons
  LGButton = makeMultilineJButton("Lawful", "Good");
    LGButton.setActionCommand("Lawful Good");
    LGButton.addActionListener(this);
  LNButton = makeMultilineJButton("Lawful", "Neutral");
    LNButton.setActionCommand("Lawful Neutral");
    LNButton.addActionListener(this);
  LEButton = makeMultilineJButton("Lawful", "Evil");
    LEButton.setActionCommand("Lawful Evil");
    LEButton.addActionListener(this);
  NGButton = makeMultilineJButton("Neutral", "Good");
    NGButton.setActionCommand("Neutral Good");
    NGButton.addActionListener(this);
  TNButton = makeMultilineJButton("True", "Neutral");
    TNButton.setActionCommand("True Neutral");
    TNButton.addActionListener(this);
  NEButton = makeMultilineJButton("Neutral", "Evil");
    NEButton.setActionCommand("Neutral Evil");
    NEButton.addActionListener(this);
  CGButton = makeMultilineJButton("Chaotic", "Good");
    CGButton.setActionCommand("Chaotic Good");
    CGButton.addActionListener(this);
  CNButton = makeMultilineJButton("Chaotic", "Neutral");
    CNButton.setActionCommand("Chaotic Neutral");
    CNButton.addActionListener(this);
  CEButton = makeMultilineJButton("Chaotic", "Evil");
    CEButton.setActionCommand("Chaotic Evil");
    CEButton.addActionListener(this);

  // add buttons to btnPanel
  alignBtnPanel.add(LGButton);
  alignBtnPanel.add(LNButton);
  alignBtnPanel.add(LEButton);
  alignBtnPanel.add(NGButton);
  alignBtnPanel.add(TNButton);
  alignBtnPanel.add(NEButton);
  alignBtnPanel.add(CGButton);
  alignBtnPanel.add(CNButton);
  alignBtnPanel.add(CEButton);

  // setup QButton for alignmentPanel
  qButton = new JButton("Take Questionnaire");
  qButton.setPreferredSize(new Dimension(175, 30));
  qButton.setBorder(BorderFactory.createRaisedBevelBorder());
    qButton.addActionListener(this);

  // JPanel discussionPanel;
  discussionPanel = new JPanel();
  discussionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));
  discussionTitle = new JLabel("Read theory behind:");
  goodEvilButton = new JButton("Good vs Evil");
    goodEvilButton.addActionListener(this);
  lawfulChaoticButton = new JButton("Lawful vs Chaotic");
    lawfulChaoticButton.addActionListener(this);
  neutralButton = new JButton("Neutral");
    neutralButton.addActionListener(this);

  // add objects to discussionPanel;
  discussionPanel.add(goodEvilButton);
  discussionPanel.add(neutralButton);
  discussionPanel.add(lawfulChaoticButton);

  // add objects to alignmentPanel
  alignmentPanel.add(alignBtnPanel);
  alignmentPanel.add(discussionTitle);
  alignmentPanel.add(discussionPanel);
  alignmentPanel.add(qButton);

  // initialize and add items to descriptionPanel
  descMessage = "Click on an alignment button to see a specific description.";

  descPanel = MyUtils.makeTextBox(32, 25, Color.white, Color.black, descMessage);

  descPanel.setBorder(BorderFactory.createMatteBorder(15,15,15,15,Color.BLACK));

  // add main objects to centralPanel
  centralPanel.add(alignmentPanel);
  centralPanel.add(descPanel);

  } // end constructor
//***************************************************************
// public methods
//***************************************************************
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    if (Constants.debugMode == true)
    setErrorLabel("command '" + command + "' recognized.", Color.black);

    if (command == "Take Questionnaire"){
      questionnaire = new Questionnaire();
      questionnaire.nextButton.addActionListener(this);
      questionnaire.show();
    } // end Take Questionnaire
    //***************************************************************
    else if (command == "Good vs Evil"){descPanel.setText(GvEdesc);} // end Good vs Evil
    //***************************************************************
    else if (command == "Lawful vs Chaotic"){descPanel.setText(LvCdesc);} // end Lawful vs Chaotic
    //***************************************************************
    else if (command == "Neutral"){descPanel.setText(Ndesc);} // end Neutral
    //***************************************************************
    else if (command == "Finish Questionnaire"){
      // this command comes from the questionnaire's nextbutton.
      // so we need to get the result
      String result = questionnaire.getResult();
      questionnaire.hide();
      questionnaire.dispose();
      // now, set the button that was the result.
      chooseAlignment(result);
    } // end FinishQuestionnaire

    else{ // user clicked an alignment button
      chooseAlignment(command);
    } // end else commands
  } // end actionPerformed

  void clearInfo(){
    // we need to clear the alignment
    playerCharacter.setAlignment(null);

    LGButton.setSelected(false);
    LNButton.setSelected(false);
    LEButton.setSelected(false);
    NGButton.setSelected(false);
    TNButton.setSelected(false);
    NEButton.setSelected(false);
    CGButton.setSelected(false);
    CNButton.setSelected(false);
    CEButton.setSelected(false);

  } // end clearInfo

//***************************************************************
// private methods
//***************************************************************

  private void chooseAlignment(String command){
    resetToggleButtons();
    if (command == "Lawful Good") {
      LGButton.setSelected(true);
      descPanel.setText(LGdesc);
      playerCharacter.setAlignment(new Alignment(Alignment.LAWFUL_GOOD));
    } // end Lawful Good response

    else if (command == "Lawful Neutral") {
      LNButton.setSelected(true);
      descPanel.setText(LNdesc);
      playerCharacter.setAlignment(new Alignment(Alignment.LAWFUL_NEUTRAL));
    } // end Lawful Neutral response

    else if (command == "Lawful Evil") {
      LEButton.setSelected(true);
      descPanel.setText(LEdesc);
      playerCharacter.setAlignment(new Alignment(Alignment.LAWFUL_EVIL));
    } // end Lawful Evil response

    else if (command == "Neutral Good") {
      NGButton.setSelected(true);
      descPanel.setText(NGdesc);
      playerCharacter.setAlignment(new Alignment(Alignment.NEUTRAL_GOOD));
    } // end Neutral Good response

    else if (command == "True Neutral") {
      TNButton.setSelected(true);
      descPanel.setText(TNdesc);
      playerCharacter.setAlignment(new Alignment(Alignment.TRUE_NEUTRAL));
    } // end True Neutral response

    else if (command == "Neutral Evil") {
      NEButton.setSelected(true);
      descPanel.setText(NEdesc);
      playerCharacter.setAlignment(new Alignment(Alignment.NEUTRAL_EVIL));
    } // end Neutral Evil response

    else if (command == "Chaotic Good") {
      CGButton.setSelected(true);
      descPanel.setText(CGdesc);
      playerCharacter.setAlignment(new Alignment(Alignment.CHAOTIC_GOOD));
    } // end Chaotic Neutral response

    else if (command == "Chaotic Neutral") {
      CNButton.setSelected(true);
      descPanel.setText(CNdesc);
      playerCharacter.setAlignment(new Alignment(Alignment.CHAOTIC_NEUTRAL));
    } // end Chaotic Neutral response

    else if (command == "Chaotic Evil") {
      CEButton.setSelected(true);
      descPanel.setText(CEdesc);
      playerCharacter.setAlignment(new Alignment(Alignment.CHAOTIC_EVIL));
    } // end Chaotic Evil response

    else { // unknown
    } // end unknown
  } // end chooseAlignment

  private void resetToggleButtons(){
    LGButton.setSelected(false);
    LNButton.setSelected(false);
    LEButton.setSelected(false);
    NGButton.setSelected(false);
    TNButton.setSelected(false);
    NEButton.setSelected(false);
    CGButton.setSelected(false);
    CNButton.setSelected(false);
    CEButton.setSelected(false);

  } // end resetTOggleButtons

  private JToggleButton makeMultilineJButton (String topStr, String bottomStr){
    // the purpose of this method is to create a multiline button
    JToggleButton button = new JToggleButton();
    button.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 10));

    // figure out the width of the longest string, to set the size properly
    int length = topStr.length();
    if (bottomStr.length() > topStr.length()) length = bottomStr.length();

    button.setPreferredSize(new Dimension(length, 30));
    button.add(new JLabel(topStr));
    button.add(new JLabel(bottomStr));

    return button;
  } // end makeMultilineJButton
} // end class


