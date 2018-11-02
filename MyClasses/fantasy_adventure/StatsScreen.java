 package fantasy_adventure;

import java.awt.*;       // supports basic GUI components
import java.awt.event.*; // supports listeners
import javax.swing.*;    // supports swing GUI components

public class StatsScreen extends CGenScreen implements MouseListener{

  // static declarations
  static final int    POINTS = Constants.BUY_POINTS;
  static final int    STARTING_DIE_VALUE  = Constants.STARTING_DIE;
  static final int    COLUMN_TITLE_HEIGHT = 40;
  static final int    COLUMN_HEIGHT       = 450;
  static final int    TEXT_BOX_LINES      = 26;
  static final int    ROLL_COLUMN_WIDTH   = 100;
  static final int    DIE_COLUMN_WIDTH    = 40;
  static final int    POINT_COLUMN_WIDTH  = 50;
  static final int    ATT_COLUMN_WIDTH    = 130;
  static final int    BONUS_CALC_COLUMN_WIDTH = 150;
  static final int    ROLL_STYLE_VGAP     = 60;

  static final int    ROW_COMPONENT_HEIGHT = 30;
  static final int    COLUMN_VGAP = (int)((COLUMN_HEIGHT
                                           - (COLUMN_TITLE_HEIGHT
                                              + 6*ROW_COMPONENT_HEIGHT))
                                          / 8); // 8 for the spaces before and after title + components

  static final String upButtonText = "(+)";
  static final String dnButtonText = "(-)";

  // declare centralPanel items
  JPanel    rollStyleColumn,
            dieColumn,
            pointBuyColumn,
            attributeColumn,
            bonusCalculator;

  JTextArea descriptionPanel;

  // double-click handling
  boolean   doubleClick;

  String    attributeDescription;

  // rollStyleColumn objects and variables
  JButton      rollButton;
  JRadioButton rollStyle1RButton,
               rollStyle2RButton,
               rollStyle3RButton;

  // dieColumn objects and variables
  JLabel die0Label;
  Die die1, die2, die3, die4, die5, die6;

  // pointBuyColumn objects and variables
  JPanel die0Panel;  // title
  JLabel dieAdjTitle;

  JPanel die1AdjPanel, die2AdjPanel, die3AdjPanel,
         die4AdjPanel, die5AdjPanel, die6AdjPanel;

  JLabel pointStack;
  int    pointsAvailable;

  JPanel  up1Panel, dn1Panel,
          up2Panel, dn2Panel,
          up3Panel, dn3Panel,
          up4Panel, dn4Panel,
          up5Panel, dn5Panel,
          up6Panel, dn6Panel;

  JButton up1Button, dn1Button,
          up2Button, dn2Button,
          up3Button, dn3Button,
          up4Button, dn4Button,
          up5Button, dn5Button,
          up6Button, dn6Button;

  // attributeColumn objects and variables
  JLabel       titleAttributeRow;
  AttributeRow strAttributeRow,
               dexAttributeRow,
               conAttributeRow,
               intAttributeRow,
               wisAttributeRow,
               chaAttributeRow;

  // bonusCalculator objects and variables
  BonusCalculatorRow titleBonusRow,
                     strBonusRow,
                     dexBonusRow,
                     conBonusRow,
                     intBonusRow,
                     wisBonusRow,
                     chaBonusRow;

  int strDieValue,
      dexDieValue,
      conDieValue,
      intDieValue,
      wisDieValue,
      chaDieValue;

  // Description panel objects

  String style3D6Desc = FileNames.STYLE_3D6_DESC,
         style4D6Desc = FileNames.STYLE_4D6_DESC,
         styleBuyDesc = FileNames.STYLE_BUY_DESC,
         strDesc      = FileNames.STRENGTH_DESC,
         dexDesc      = FileNames.DEXTERITY_DESC,
         conDesc      = FileNames.CONSTITUTION_DESC,
         intDesc      = FileNames.INTELLIGENCE_DESC,
         wisDesc      = FileNames.WISDOM_DESC,
         chaDesc      = FileNames.CHARISMA_DESC;

//***********************************************
// constructors
//***********************************************

public StatsScreen(){

  // create basic JPanel, with borderLayout
  super();
  title = "Fantasy Adventure - Character Generation - Step 2: Statistics";
  setIntroMessage(FileNames.CGEN_STATS_SCREEN_INTRO);
  centralPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
  setBorder();

  // initialize items

  // initialize rollStyleColumn
  rollStyleColumn = new JPanel();
  rollStyleColumn.setPreferredSize(new Dimension(ROLL_COLUMN_WIDTH,
                                                 COLUMN_HEIGHT));
  rollStyleColumn.setBorder(BorderFactory.createEtchedBorder());
  rollStyleColumn.setLayout(new FlowLayout(FlowLayout.CENTER,
                                           50,
                                           ROLL_STYLE_VGAP));

  // initialize internal objects of rollStyleColumn
  rollButton = new JButton("Roll Die");
  rollButton.setActionCommand("Roll Die");
  rollButton.addActionListener(this);
  rollStyle1RButton = new JRadioButton("3D6 Style");
  rollStyle1RButton.addActionListener(this);
  rollStyle2RButton = new JRadioButton("4D6 Style");
  rollStyle2RButton.addActionListener(this);
  rollStyle3RButton = new JRadioButton("Buy Style");
  rollStyle3RButton.addActionListener(this);

  // and add items to rollStyleColumn
  rollStyleColumn.add (rollButton);
  rollStyleColumn.add (rollStyle1RButton);
  rollStyleColumn.add (rollStyle2RButton);
  rollStyleColumn.add (rollStyle3RButton);

  // initialize dieColumn
  dieColumn = new JPanel();
  dieColumn.setPreferredSize(new Dimension(DIE_COLUMN_WIDTH,
                                           COLUMN_HEIGHT));
  dieColumn.setBorder(BorderFactory.createEtchedBorder());
  dieColumn.setLayout(new FlowLayout(FlowLayout.CENTER, DIE_COLUMN_WIDTH, COLUMN_VGAP));
  // initialize internal objects of dieColumn

  die0Label = new JLabel("Dice");
  die0Label.setPreferredSize(new Dimension(DIE_COLUMN_WIDTH - 4,
                                           COLUMN_TITLE_HEIGHT));
  die0Label.setHorizontalAlignment(JLabel.CENTER);
  die0Label.setVerticalAlignment(JLabel.TOP);
  die0Label.setOpaque(true);
//  die0Label.setBackground(Color.blue);

  die1 = new Die(this);
  die2 = new Die(this);
  die3 = new Die(this);
  die4 = new Die(this);
  die5 = new Die(this);
  die6 = new Die(this);

  // and add items to dieColumn
  dieColumn.add(die0Label);
  dieColumn.add(die1.label);
  dieColumn.add(die2.label);
  dieColumn.add(die3.label);
  dieColumn.add(die4.label);
  dieColumn.add(die5.label);
  dieColumn.add(die6.label);

  // initialize pointBuyColumn
  pointBuyColumn = new JPanel();
  pointBuyColumn.setPreferredSize(new Dimension(POINT_COLUMN_WIDTH,
                                                COLUMN_HEIGHT));
  pointBuyColumn.setBorder(BorderFactory.createEtchedBorder());
  pointBuyColumn.setLayout(new FlowLayout(FlowLayout.CENTER, POINT_COLUMN_WIDTH, COLUMN_VGAP));

  // initialize internal objects and variables of pointBuyColumn

    pointsAvailable = POINTS;  // start with default.  Will adjust when using.
    dieAdjTitle = new JLabel("<html>Buy<br>" + "(" + pointsAvailable + ")</html>");
    dieAdjTitle.setPreferredSize(new Dimension(POINT_COLUMN_WIDTH - 4,
                                               COLUMN_TITLE_HEIGHT));
    dieAdjTitle.setHorizontalAlignment(JLabel.CENTER);
    dieAdjTitle.setVerticalAlignment(JLabel.TOP);
    dieAdjTitle.setOpaque(true);
//    dieAdjTitle.setBackground(Color.green);

  die1AdjPanel = makeDieAdjPanel();
    up1Panel = makeUpDnPanel();
      up1Button = makeUpDnButton(upButtonText);
    dn1Panel = makeUpDnPanel();
      dn1Button = makeUpDnButton(dnButtonText);
  up1Panel.add(up1Button);
  dn1Panel.add(dn1Button);
  die1AdjPanel.add(up1Panel);
  die1AdjPanel.add(dn1Panel);

  die2AdjPanel = makeDieAdjPanel();
    up2Panel = makeUpDnPanel();
      up2Button = makeUpDnButton(upButtonText);
    dn2Panel = makeUpDnPanel();
      dn2Button = makeUpDnButton(dnButtonText);
  up2Panel.add(up2Button);
  dn2Panel.add(dn2Button);
  die2AdjPanel.add(up2Panel);
  die2AdjPanel.add(dn2Panel);

  die3AdjPanel = makeDieAdjPanel();
    up3Panel = makeUpDnPanel();
      up3Button = makeUpDnButton(upButtonText);
    dn3Panel = makeUpDnPanel();
      dn3Button = makeUpDnButton(dnButtonText);
  up3Panel.add(up3Button);
  dn3Panel.add(dn3Button);
  die3AdjPanel.add(up3Panel);
  die3AdjPanel.add(dn3Panel);

  die4AdjPanel = makeDieAdjPanel();
    up4Panel = makeUpDnPanel();
      up4Button = makeUpDnButton(upButtonText);
    dn4Panel = makeUpDnPanel();
      dn4Button = makeUpDnButton(dnButtonText);
  up4Panel.add(up4Button);
  dn4Panel.add(dn4Button);
  die4AdjPanel.add(up4Panel);
  die4AdjPanel.add(dn4Panel);

  die5AdjPanel = makeDieAdjPanel();
    up5Panel = makeUpDnPanel();
      up5Button = makeUpDnButton(upButtonText);
    dn5Panel = makeUpDnPanel();
      dn5Button = makeUpDnButton(dnButtonText);
  up5Panel.add(up5Button);
  dn5Panel.add(dn5Button);
  die5AdjPanel.add(up5Panel);
  die5AdjPanel.add(dn5Panel);

  die6AdjPanel = makeDieAdjPanel();
    up6Panel = makeUpDnPanel();
      up6Button = makeUpDnButton(upButtonText);
    dn6Panel = makeUpDnPanel();
      dn6Button = makeUpDnButton(dnButtonText);
  up6Panel.add(up6Button);
  dn6Panel.add(dn6Button);
  die6AdjPanel.add(up6Panel);
  die6AdjPanel.add(dn6Panel);

  // add items to pointBuyColumn
  pointBuyColumn.add(dieAdjTitle);
  pointBuyColumn.add(die1AdjPanel);
  pointBuyColumn.add(die2AdjPanel);
  pointBuyColumn.add(die3AdjPanel);
  pointBuyColumn.add(die4AdjPanel);
  pointBuyColumn.add(die5AdjPanel);
  pointBuyColumn.add(die6AdjPanel);

  // initialize and add items to attributeColumn
  attributeColumn = new JPanel();
  attributeColumn.setPreferredSize(new Dimension(ATT_COLUMN_WIDTH,
                                                 COLUMN_HEIGHT));
  attributeColumn.setBorder(BorderFactory.createEtchedBorder());
  attributeColumn.setLayout(new FlowLayout(FlowLayout.CENTER, 0, COLUMN_VGAP));

  titleAttributeRow = new JLabel("<html><c>Click Attribute<br>for details</c></html>");
  titleAttributeRow.setPreferredSize(new Dimension(ATT_COLUMN_WIDTH - 4,
                                                   COLUMN_TITLE_HEIGHT));
  titleAttributeRow.setHorizontalAlignment(JLabel.CENTER);
  titleAttributeRow.setVerticalAlignment(JLabel.TOP);
  titleAttributeRow.setOpaque(true);
//  titleAttributeRow.setBackground(Color.yellow);

  strAttributeRow = new AttributeRow("Strength", this);
  dexAttributeRow = new AttributeRow("Dexterity", this);
  conAttributeRow = new AttributeRow("Constitution", this);
  intAttributeRow = new AttributeRow("Intelligence", this);
  wisAttributeRow = new AttributeRow("Wisdom", this);
  chaAttributeRow = new AttributeRow("Charisma", this);

  attributeColumn.add(titleAttributeRow);
  attributeColumn.add(strAttributeRow);
  attributeColumn.add(dexAttributeRow);
  attributeColumn.add(conAttributeRow);
  attributeColumn.add(intAttributeRow);
  attributeColumn.add(wisAttributeRow);
  attributeColumn.add(chaAttributeRow);

  // initialize and add items to bonusCalculator
  bonusCalculator = new JPanel();
  bonusCalculator.setLayout(new FlowLayout(FlowLayout.LEFT, 0, COLUMN_VGAP));
  bonusCalculator.setBorder(BorderFactory.createEtchedBorder());
  bonusCalculator.setPreferredSize(new Dimension(BONUS_CALC_COLUMN_WIDTH,
                                                 COLUMN_HEIGHT));

  titleBonusRow = new BonusCalculatorRow("Title");
//  titleBonusRow.setBackground(Color.red);
  strBonusRow = new BonusCalculatorRow("Strength");
  dexBonusRow = new BonusCalculatorRow("Dexterity");
  conBonusRow = new BonusCalculatorRow("Constitution");
  intBonusRow = new BonusCalculatorRow("Intelligence");
  wisBonusRow = new BonusCalculatorRow("Wisdom");
  chaBonusRow = new BonusCalculatorRow("Charisma");

  bonusCalculator.add(titleBonusRow);
  bonusCalculator.add(strBonusRow);
  bonusCalculator.add(dexBonusRow);
  bonusCalculator.add(conBonusRow);
  bonusCalculator.add(intBonusRow);
  bonusCalculator.add(wisBonusRow);
  bonusCalculator.add(chaBonusRow);

  // initialize and add items to descriptionPanel
  attributeDescription = "Click on a Roll Style or an attribute to see a specific description.";
  descriptionPanel = MyUtils.makeTextBox(24,                   // columns
                                        TEXT_BOX_LINES,        // rows
                                        Color.white,           // font color
                                        Color.black,           // background color
                                        attributeDescription); // intial text


  descriptionPanel.setBorder(BorderFactory.createMatteBorder(15,5,15,5,Color.BLACK));

  // initialize and add items to centralPanel:
  centralPanel.add(rollStyleColumn);
  centralPanel.add(dieColumn);
  centralPanel.add(pointBuyColumn);
  centralPanel.add(attributeColumn);
  centralPanel.add(bonusCalculator);
  centralPanel.add(descriptionPanel);

} // end constructor

//*******************************************************************
// public methods
//*******************************************************************

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    if (Constants.debugMode == true)
    setErrorLabel("command: " + command + " was captured.", Color.blue);

  if (command == "Roll Die"){
    // user has chosen to roll the die using currently selected style.

    // Now, determine what style we are using

    // if style 1, then roll 3D6 and set text of die.
    if (rollStyle1RButton.isSelected()){
      // reset everything back to defaults, in case we are starting over:
      resetAll();

      // set all 6 die values to xDy (3,6);
      die1.setValue(Roll.xDy(3,6));
      die2.setValue(Roll.xDy(3,6));
      die3.setValue(Roll.xDy(3,6));
      die4.setValue(Roll.xDy(3,6));
      die5.setValue(Roll.xDy(3,6));
      die6.setValue(Roll.xDy(3,6));
    } // end roll 3D6

    else if (rollStyle2RButton.isSelected()){
      // reset everything back to defaults, in case we are starting over:
      resetAll();

      // roll4D6, dropping the lowest D6, then set text of die.
      die1.setValue(Roll.style4D6less1());
      die2.setValue(Roll.style4D6less1());
      die3.setValue(Roll.style4D6less1());
      die4.setValue(Roll.style4D6less1());
      die5.setValue(Roll.style4D6less1());
      die6.setValue(Roll.style4D6less1());
    } // end 4D6 - 1 style.

    else if (rollStyle3RButton.isSelected()){
      // reset everything back to defaults, in case we are starting over:
      resetAll();

      //then set set pointsAvailable to POINTS and die to defaults.
      pointsAvailable = POINTS;
      dieAdjTitle.setText("<html>Buy<br>" + " (" + pointsAvailable + ")</html>");

      die1.setValue(STARTING_DIE_VALUE);
      die2.setValue(STARTING_DIE_VALUE);
      die3.setValue(STARTING_DIE_VALUE);
      die4.setValue(STARTING_DIE_VALUE);
      die5.setValue(STARTING_DIE_VALUE);
      die6.setValue(STARTING_DIE_VALUE);

    } // end rollStyle3RButton
    else { // no style buttons are selected!
      descriptionPanel.setText("First choose a 'Roll Style' before rolling the die.");
    } // end else
  } // end "Roll Die" response

//*****************************************************************
   else if (command == "3D6 Style"){
    // user has chosen to change the style to the 3D6 rolling style
    // give feedback in the descriptionPanel
    descriptionPanel.setText(style3D6Desc);

    // change Roll Button text, just in case:
    rollButton.setText("Roll Die");

    // change the radio buttons to reflect choice
    if(!rollStyle1RButton.isSelected()) rollStyle1RButton.setSelected(true);
    rollStyle2RButton.setSelected(false);
    rollStyle3RButton.setSelected(false);
    setAdjustButtons(Color.gray);
  } // end "3D6 Style" response

//*****************************************************************
  else if (command == "4D6 Style"){
    // user has chosen to change the style to the 4D6 - 1 style.
    // give feedback in the descriptionPanel
    descriptionPanel.setText(style4D6Desc);

    // change Roll Button text, just in case:
    rollButton.setText("Roll Die");

    // change the radio buttons to reflect choice
    rollStyle1RButton.setSelected(false);
    if(!rollStyle2RButton.isSelected()) rollStyle2RButton.setSelected(true);
    rollStyle3RButton.setSelected(false);
    setAdjustButtons(Color.gray);
  } // end "4D6 Style - 1" response

//*****************************************************************
  else if (command == "Buy Style"){
    // user has chosen to change the style to point buy style.
    // give feedback in the descriptionPanel
    descriptionPanel.setText(styleBuyDesc);
    resetAll();

    // change Roll Button text, just in case:
    rollButton.setText("Reset Die");

    // change the radio buttons to reflect choice
    rollStyle1RButton.setSelected(false);
    rollStyle2RButton.setSelected(false);
    setAdjustButtons(Color.black);
    if(!rollStyle3RButton.isSelected()) rollStyle3RButton.setSelected(true);

    // setup die for buy style
    pointsAvailable = POINTS;
    updateBuyTitle();
    die1.setValue(STARTING_DIE_VALUE);
    die2.setValue(STARTING_DIE_VALUE);
    die3.setValue(STARTING_DIE_VALUE);
    die4.setValue(STARTING_DIE_VALUE);
    die5.setValue(STARTING_DIE_VALUE);
    die6.setValue(STARTING_DIE_VALUE);
   }  // end "Buy Style" response

//******************************************************************
    else if (e.getSource() == up1Button &&
             rollStyle3RButton.isSelected() &&
           die1.isAllocated() == false) {
    // user has chosen to increase a point to die 1
    addPointToDie(die1);
  } // end "1up" response

  else if (e.getSource() == dn1Button &&
           rollStyle3RButton.isSelected() &&
           die1.isAllocated() == false) {
    // user has chosen to decrease a point from die 1
    takePointFromDie(die1);
  } // end "1dn" response

  else if (e.getSource() == up2Button &&
           rollStyle3RButton.isSelected() &&
           die2.isAllocated() == false) {
    // user has chosen to increase a point to die 2
    addPointToDie(die2);
  } // end "2up" response

  else if (e.getSource() == dn2Button &&
           rollStyle3RButton.isSelected() &&
           die2.isAllocated() == false) {
    // user has chosen to decrease a point from die 2
    takePointFromDie(die2);
  } // end "2dn" response

  else if (e.getSource() == up3Button &&
           rollStyle3RButton.isSelected() &&
           die3.isAllocated() == false) {
    // user has chosen to increase a point to die 3
    addPointToDie(die3);
  } // end "3up" response

  else if (e.getSource() == dn3Button &&
           rollStyle3RButton.isSelected() &&
           die3.isAllocated() == false) {
    // user has chosen to decrease a point from die 3
    takePointFromDie(die3);
  } // end "3dn" response

  else if (e.getSource() == up4Button &&
           rollStyle3RButton.isSelected() &&
           die4.isAllocated() == false) {
    // user has chosen to increase a point to die 4
    addPointToDie(die4);
  } // end "4up" response

  else if (e.getSource() == dn4Button &&
           rollStyle3RButton.isSelected() &&
           die4.isAllocated() == false) {
    // user has chosen to decrease a point from die 4
    takePointFromDie(die4);
  } // end "4dn" response

  else if (e.getSource() == up5Button &&
           rollStyle3RButton.isSelected() &&
           die5.isAllocated() == false) {
    // user has chosen to increase a point to die 5
    addPointToDie(die5);
  } // end "5up" response

  else if (e.getSource() == dn5Button &&
           rollStyle3RButton.isSelected() &&
           die5.isAllocated() == false) {
    // user has chosen to decrease a point from die 5
    takePointFromDie(die5);
  } // end "5dn" response

  else if (e.getSource() == up6Button &&
           rollStyle3RButton.isSelected() &&
           die6.isAllocated() == false) {
    // user has chosen to increase a point to die 6
    addPointToDie(die6);
  } // end "6up" response

  else if (e.getSource() == dn6Button &&
           rollStyle3RButton.isSelected() &&
           die6.isAllocated() == false) {
    // user has chosen to decrease a point from die 6
    takePointFromDie(die6);
  } // end "6dn" response

} // end actionPerformed

//*************************************************************************************
  public void mouseClicked (MouseEvent e){
    JLabel placeClicked = (JLabel)e.getSource();

    // setup for double clicking operations.
    if (e.getClickCount() >= 2){             // user clicked fast enough
    doubleClick = true;
    } // end double-click

    else{
    doubleClick = false;
    } // end else

    //figure out what was clicked on:
    if      (placeClicked == die1.label) {checkStateAndRespond(die1);} // end die1.label
    else if (placeClicked == die2.label) {checkStateAndRespond(die2);} // end die2.label
    else if (placeClicked == die3.label) {checkStateAndRespond(die3);} // end die3.label
    else if (placeClicked == die4.label) {checkStateAndRespond(die4);} // end die4.label
    else if (placeClicked == die5.label) {checkStateAndRespond(die5);} // end die5.label
    else if (placeClicked == die6.label) {checkStateAndRespond(die6);} // end die6.label

    else if (placeClicked == strAttributeRow.attributeLabel ||
             placeClicked == strAttributeRow.dieSlot){
      descriptionPanel.setText(strDesc);
      checkStateAndRespond(strAttributeRow);

      // post die bonus from die value
      strBonusRow.updateBonusFromDie(strAttributeRow.die.getValue());
      strBonusRow.updateTotBonusLabel();
      playerCharacter.setBaseStr(strAttributeRow.die.getValue());
      playerCharacter.setBaseStrBonus(strBonusRow.totBonusValue);
    } // end str.label

    else if (placeClicked == dexAttributeRow.attributeLabel ||
             placeClicked == dexAttributeRow.dieSlot){
      descriptionPanel.setText(dexDesc);
      checkStateAndRespond(dexAttributeRow);

      // post die bonus from die value
      dexBonusRow.updateBonusFromDie(dexAttributeRow.die.getValue());
      dexBonusRow.updateTotBonusLabel();
      playerCharacter.setBaseDex(dexAttributeRow.die.getValue());
      playerCharacter.setBaseDexBonus(dexBonusRow.totBonusValue);
    } // end dex.label

    else if (placeClicked == conAttributeRow.attributeLabel ||
             placeClicked == conAttributeRow.dieSlot){
      descriptionPanel.setText(conDesc);
      checkStateAndRespond(conAttributeRow);

      // post die bonus from die value
      conBonusRow.updateBonusFromDie(conAttributeRow.die.getValue());
      conBonusRow.updateTotBonusLabel();
      playerCharacter.setBaseCon(conAttributeRow.die.getValue());
      playerCharacter.setBaseConBonus(conBonusRow.totBonusValue);
    } // end con.label

    else if (placeClicked == intAttributeRow.attributeLabel ||
             placeClicked == intAttributeRow.dieSlot){
      descriptionPanel.setText(intDesc);
      checkStateAndRespond(intAttributeRow);

      // post die bonus from die value
      intBonusRow.updateBonusFromDie(intAttributeRow.die.getValue());
      intBonusRow.updateTotBonusLabel();
      playerCharacter.setBaseInt(intAttributeRow.die.getValue());
      playerCharacter.setBaseIntBonus(intBonusRow.totBonusValue);
    } // end int.label

    else if (placeClicked == wisAttributeRow.attributeLabel ||
             placeClicked == wisAttributeRow.dieSlot){
      descriptionPanel.setText(wisDesc);
      checkStateAndRespond(wisAttributeRow);

      // post die bonus from die value
      wisBonusRow.updateBonusFromDie(wisAttributeRow.die.getValue());
      wisBonusRow.updateTotBonusLabel();
      playerCharacter.setBaseWis(wisAttributeRow.die.getValue());
      playerCharacter.setBaseWisBonus(wisBonusRow.totBonusValue);
    } // end wis.label

    else if (placeClicked == chaAttributeRow.attributeLabel ||
             placeClicked == chaAttributeRow.dieSlot){
      descriptionPanel.setText(chaDesc);
      checkStateAndRespond(chaAttributeRow);

      // post die bonus from die value
      chaBonusRow.updateBonusFromDie(chaAttributeRow.die.getValue());
      chaBonusRow.updateTotBonusLabel();
      playerCharacter.setBaseCha(chaAttributeRow.die.getValue());
      playerCharacter.setBaseChaBonus(chaBonusRow.totBonusValue);
    } // end cha.label

    else {
      if (Constants.debugMode == true)
      setErrorLabel("Sorry, but I don't recognize your click on that object.", Color.red);
    } // end else
   }// end mouseClicked()

//********************************************************************
  public void mousePressed (MouseEvent e){} // end mousePressed()
  public void mouseEntered (MouseEvent e){} // end mouseEntered()
  public void mouseExited  (MouseEvent e){} // end mouseExited()
  public void mouseReleased(MouseEvent e){} // end mouseReleased()

//********************************************************************
  void setCharacter(PlayerCharacter PC){
    playerCharacter = PC;
    updateRaceBonuses();
    // update total bonus too!
    strBonusRow.updateTotBonusLabel();
    dexBonusRow.updateTotBonusLabel();
    conBonusRow.updateTotBonusLabel();
    intBonusRow.updateTotBonusLabel();
    wisBonusRow.updateTotBonusLabel();
    chaBonusRow.updateTotBonusLabel();
  } // end setCharacter

//********************************************************************
  void clearInfo(){
    // we have gone back to the genetics screen, so we need to start over
    resetAll();
    setPlayerCharacterStats();
  } // end clearInfo

//********************************************************************
  PlayerCharacter getCharacter(){
    // this method is overriding the parent method from CGenScreen

    // we need to make sure and update all info and add any derived stats here
    setPlayerCharacterStats();
    return playerCharacter;
  } // end getCharacter

//********************************************************************
// private methods
//********************************************************************
  private JButton makeUpDnButton(String text){
    JButton button = new JButton(text);
    button.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    button.setPreferredSize(new Dimension(50, ROW_COMPONENT_HEIGHT -4));
    button.setBorderPainted(false);
    button.addActionListener(this);
    return button;
  } // end makeUpDnButton

//********************************************************************
  private JPanel makeUpDnPanel(){
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension((int)(StatsScreen.POINT_COLUMN_WIDTH / 2) - 4,
                                         ROW_COMPONENT_HEIGHT));
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    return panel;
  } // end makeUpDnPanel

//********************************************************************
  private JPanel makeDieAdjPanel(){
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(45, ROW_COMPONENT_HEIGHT));
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    return panel;
  } // end makeDieAdjPanel

//********************************************************************
  private void resetAll(){
    resetToDefaults(die1);
    resetToDefaults(die2);
    resetToDefaults(die3);
    resetToDefaults(die4);
    resetToDefaults(die5);
    resetToDefaults(die6);
    resetToDefaults(strAttributeRow);
    resetToDefaults(dexAttributeRow);
    resetToDefaults(conAttributeRow);
    resetToDefaults(intAttributeRow);
    resetToDefaults(wisAttributeRow);
    resetToDefaults(chaAttributeRow);

    resetBonusToDefauls();

  } // end resetAll

//********************************************************************
  private void checkStateAndRespond(Die die){

    if (die.isHighlighted() == false){
      die1.unhighlight();
      die2.unhighlight();
      die3.unhighlight();
      die4.unhighlight();
      die5.unhighlight();
      die6.unhighlight();
      die.highlight();
    }  // end if

    if (die.isAllocated() == false && doubleClick == true){ // die is still here

      // the purpose of the following code is to find the first open slot, and fill it.
      // this allows us to double-click on any die and fill the first slot, wherever it is.

      if      (strAttributeRow.isFilled() == false) {
        assignDieToRow (strAttributeRow, die);
      } // end if str

      else if (dexAttributeRow.isFilled() == false) {
        assignDieToRow (dexAttributeRow, die);
      } // end if str

      else if (conAttributeRow.isFilled() == false) {
        assignDieToRow (conAttributeRow, die);
      } // end if str

      else if (intAttributeRow.isFilled() == false) {
        assignDieToRow (intAttributeRow, die);
      } // end if str

      else if (wisAttributeRow.isFilled() == false) {
        assignDieToRow (wisAttributeRow, die);
      } // end if str

      else if (chaAttributeRow.isFilled() == false) {
        assignDieToRow (chaAttributeRow, die);
      } // end if str

      else{}

      return; // since we are done, don't continue processing.

    } // end if double-click

    // State response
    if (die.isAllocated()            == true &&
        getHighlightedAttributeRow() != null ){

      // die can be restored from the row...
      if (getHighlightedAttributeRow().isFilled()){
        removeDieFromRow(die, getHighlightedAttributeRow());} // end if

    } // end if die allocated and row filled

  } // end checkStateAndRespond

//********************************************************************
  private void updateBonusesFromRows(){
    updateBonusesFromRows(strAttributeRow);
    updateBonusesFromRows(dexAttributeRow);
    updateBonusesFromRows(conAttributeRow);
    updateBonusesFromRows(intAttributeRow);
    updateBonusesFromRows(wisAttributeRow);
    updateBonusesFromRows(chaAttributeRow);
  } // end updateBonusesFromRows

//********************************************************************
  private void updateBonusesFromRows(AttributeRow attRow){
    if (attRow == strAttributeRow) {
//      Popup.createInfoPopup("Updating Bonuses from Rows, Str: " + strAttributeRow.die.getValue());
      strBonusRow.updateBonusFromDie(strAttributeRow.die.getValue());
      strBonusRow.updateTotBonusLabel();
    } // end if

    else if (attRow == dexAttributeRow) {
      dexBonusRow.updateBonusFromDie(dexAttributeRow.die.getValue());
      dexBonusRow.updateTotBonusLabel();
    } // end if dex

    else if (attRow == conAttributeRow){
      conBonusRow.updateBonusFromDie(conAttributeRow.die.getValue());
      conBonusRow.updateTotBonusLabel();
    } // end if con

    else if (attRow == intAttributeRow){
      intBonusRow.updateBonusFromDie(intAttributeRow.die.getValue());
      intBonusRow.updateTotBonusLabel();
    } // end if int

    else if (attRow == wisAttributeRow){
      wisBonusRow.updateBonusFromDie(wisAttributeRow.die.getValue());
      wisBonusRow.updateTotBonusLabel();
    } // end if wis

    else if (attRow == chaAttributeRow){
      chaBonusRow.updateBonusFromDie(chaAttributeRow.die.getValue());
      chaBonusRow.updateTotBonusLabel();
    } // end if cha

    else{}

  } // end update

//********************************************************************
  private void checkStateAndRespond(AttributeRow attRow){
    if (attRow.isHighlighted() == false){
      unHighlightAllAttRows();
      attRow.setHighlighted(true);
    } // end false

    if (attRow.isFilled() == true && doubleClick == true){

       // purpose of the following code is to handle double-clicking a row
       // in order to remove the die from it and fill the next available empty die slot.

       if      (die1.isAllocated() == true){
          removeDieFromRow(die1, attRow);
       } // end str

       else if (die2.isAllocated() == true){
          removeDieFromRow(die2, attRow);
       } // end str

       else if (die3.isAllocated() == true){
          removeDieFromRow(die3, attRow);
       } // end str

       else if (die4.isAllocated() == true){
          removeDieFromRow(die4, attRow);
       } // end str

       else if (die5.isAllocated() == true){
          removeDieFromRow(die5, attRow);
       } // end str

       else if (die6.isAllocated() == true){
          removeDieFromRow(die6, attRow);
       } // end str

       // since this is all we want to do in a double-click event, then we're done.
       // ? update bonuses from rows?
       return;

    } // end double-click filled AttRow


    // find out which die is highlighted
    if (attRow.isFilled() == false &&
        getHighlightedDie() != null){
      if (getHighlightedDie().isAllocated() == false &&
          getHighlightedDie().getValue() != 0) {
        assignDieToRow(attRow, getHighlightedDie());
      } // end if allocated
    } // end if not filled

    // whatever we did, update the bonusRows.
//    updateBonusesFromRows();
  } // end checkStateAndRespond

//********************************************************************
  private void unHighlightAllAttRows() {
    strAttributeRow.setHighlighted(false);
    dexAttributeRow.setHighlighted(false);
    conAttributeRow.setHighlighted(false);
    intAttributeRow.setHighlighted(false);
    wisAttributeRow.setHighlighted(false);
    chaAttributeRow.setHighlighted(false);
  } // end unHighlightAllAttRows()

//********************************************************************
  private void assignDieToRow(AttributeRow attRow, Die d) {
    // set die value to attRow.label.value
    attRow.die.setValue(d.getValue());
    attRow.updateDieSlotText(d.getValue());
    attRow.setFilled(true);

    // now update the die used
    d.setAllocated(true);
    d.unhighlight();

    updateBonusesFromRows(attRow);
    setPlayerCharacterStats();
  } // end assignDieToRow

//********************************************************************
  private void removeDieFromRow(Die die, AttributeRow attRow){
    // first, capture value of tempAttRow and save into this die
    die.setValue(attRow.die.getValue());
    die.setAllocated(false);
    die.unhighlight();

    // then, remove value from attRow.
    attRow.die.setValue(0);
    attRow.die.updateDieLabel();
    attRow.updateDieSlotText(0);
    attRow.setFilled(false);

/*    Popup.createInfoPopup("setting"
                          + attRow.attributeLabel.getText()
                          + " with die value: "
                          + attRow.die.getValue());
*/
    updateBonusesFromRows(attRow);
    setPlayerCharacterStats();
  } // end removeDieFromRow

//********************************************************************
  private AttributeRow getHighlightedAttributeRow() {
    if      (strAttributeRow.isHighlighted()) return strAttributeRow;
    else if (dexAttributeRow.isHighlighted()) return dexAttributeRow;
    else if (conAttributeRow.isHighlighted()) return conAttributeRow;
    else if (intAttributeRow.isHighlighted()) return intAttributeRow;
    else if (wisAttributeRow.isHighlighted()) return wisAttributeRow;
    else if (chaAttributeRow.isHighlighted()) return chaAttributeRow;
    else return null;
  } // end getRow

//********************************************************************
  private Die getHighlightedDie(){
    if      (die1.isHighlighted()) return die1;
    else if (die2.isHighlighted()) return die2;
    else if (die3.isHighlighted()) return die3;
    else if (die4.isHighlighted()) return die4;
    else if (die5.isHighlighted()) return die5;
    else if (die6.isHighlighted()) return die6;
    else return null;
  } // end areAnyHighlighted

//********************************************************************
  private void addPointToDie(Die die){
    // the purpose of this method is to increase the die value,
    // and decrease the points available.
    // we need to find out what the die value is currently,
    // to determine how many points it costs to incease it, if at all.

    int points = 1;

    if (die.getValue()  > 17){ return;} // die maxed out
    else{

      if (die.getValue() == 8  || die.getValue() == 12) points = 2;
      if (die.getValue() == 7  || die.getValue() == 13) points = 2;
      if (die.getValue() == 6  || die.getValue() == 14) points = 3;
      if (die.getValue() == 5  || die.getValue() == 15) points = 3;
      if (die.getValue() == 4  || die.getValue() == 16) points = 4;
      if (die.getValue() == 3  || die.getValue() == 17) points = 4;

      // now, we have determined the number of points need to add
      if (pointsAvailable >= points){
        die.setValue(die.getValue() + 1);
        pointsAvailable -= points;
        updateBuyTitle();
      } // end if have enough points
    } // end else - can add
  } // end addPointToDie

//********************************************************************
  private void takePointFromDie(Die die){

    int points = 1;

    if (die.getValue()  < 4){ return;} // die minned out
    else{

      if (die.getValue() == 9  || die.getValue() == 13) points = 2;
      if (die.getValue() == 8  || die.getValue() == 14) points = 2;
      if (die.getValue() == 7  || die.getValue() == 15) points = 3;
      if (die.getValue() == 6  || die.getValue() == 16) points = 3;
      if (die.getValue() == 5  || die.getValue() == 17) points = 4;
      if (die.getValue() == 4  || die.getValue() == 18) points = 4;

      // now, we have determined the number of points need to add
      die.setValue(die.getValue() - 1);
      pointsAvailable += points;
      updateBuyTitle();
    } // end else - can take
  } // end takePointFromDie

//********************************************************************
  private void updateBuyTitle(){
    dieAdjTitle.setText("<html>Buy<br>" + " (" + pointsAvailable + ")</html>");
  } // end updateBuyTitle

//********************************************************************
  private void resetToDefaults(Die die){
    // purpose of this method is to reset each die's defaults.
    die.setValue(0);
    die.setAllocated(false);
    die.unhighlight();
  } // end resetToDefaults

//********************************************************************
  private void resetToDefaults(AttributeRow attRow){
  attRow.die.setValue(0);
  attRow.setFilled(false);
  attRow.updateDieSlotText(0);
  } // end resetAttRow

//********************************************************************
  private void resetBonusToDefauls(){
    strBonusRow.updateBonusFromDie(STARTING_DIE_VALUE);
    strBonusRow.updateTotBonusLabel();
    dexBonusRow.updateBonusFromDie(STARTING_DIE_VALUE);
    dexBonusRow.updateTotBonusLabel();
    conBonusRow.updateBonusFromDie(STARTING_DIE_VALUE);
    conBonusRow.updateTotBonusLabel();
    intBonusRow.updateBonusFromDie(STARTING_DIE_VALUE);
    intBonusRow.updateTotBonusLabel();
    wisBonusRow.updateBonusFromDie(STARTING_DIE_VALUE);
    wisBonusRow.updateTotBonusLabel();
    chaBonusRow.updateBonusFromDie(STARTING_DIE_VALUE);
    chaBonusRow.updateTotBonusLabel();
  } // end reset(bRow)

//********************************************************************
  private void updateRaceBonuses(){
    strBonusRow.raceBonusValue = playerCharacter.getRace().getStrBonus();
    strBonusRow.updateBonusLabel(strBonusRow.raceBonusLabel, strBonusRow.raceBonusValue);
    dexBonusRow.raceBonusValue = playerCharacter.getRace().getDexBonus();
    dexBonusRow.updateBonusLabel(dexBonusRow.raceBonusLabel, dexBonusRow.raceBonusValue);
    conBonusRow.raceBonusValue = playerCharacter.getRace().getConBonus();
    conBonusRow.updateBonusLabel(conBonusRow.raceBonusLabel, conBonusRow.raceBonusValue);
    intBonusRow.raceBonusValue = playerCharacter.getRace().getIntBonus();
    intBonusRow.updateBonusLabel(intBonusRow.raceBonusLabel, intBonusRow.raceBonusValue);
    wisBonusRow.raceBonusValue = playerCharacter.getRace().getWisBonus();
    wisBonusRow.updateBonusLabel(wisBonusRow.raceBonusLabel, wisBonusRow.raceBonusValue);
    chaBonusRow.raceBonusValue = playerCharacter.getRace().getChaBonus();
    chaBonusRow.updateBonusLabel(chaBonusRow.raceBonusLabel, chaBonusRow.raceBonusValue);
  } // end update Race Bonuses

//********************************************************************
  private void setAdjustButtons(Color c){
    // purpose is to 'grey' out the buttons so the user doesn't try to click on them.
    dieAdjTitle.setForeground(c);
    up1Button.setForeground(c);
    dn1Button.setForeground(c);
    up2Button.setForeground(c);
    dn2Button.setForeground(c);
    up3Button.setForeground(c);
    dn3Button.setForeground(c);
    up4Button.setForeground(c);
    dn4Button.setForeground(c);
    up5Button.setForeground(c);
    dn5Button.setForeground(c);
    up6Button.setForeground(c);
    dn6Button.setForeground(c);
  } // end setAdjustButtons()

//********************************************************************
  private void setPlayerCharacterStats(){
    playerCharacter.setBaseStr(strAttributeRow.die.getValue());
    playerCharacter.setBaseDex(dexAttributeRow.die.getValue());
    playerCharacter.setBaseCon(conAttributeRow.die.getValue());
    playerCharacter.setBaseInt(intAttributeRow.die.getValue());
    playerCharacter.setBaseWis(wisAttributeRow.die.getValue());
    playerCharacter.setBaseCha(chaAttributeRow.die.getValue());

    playerCharacter.setTempStr(strAttributeRow.die.getValue());
    playerCharacter.setTempDex(dexAttributeRow.die.getValue());
    playerCharacter.setTempCon(conAttributeRow.die.getValue());
    playerCharacter.setTempInt(intAttributeRow.die.getValue());
    playerCharacter.setTempWis(wisAttributeRow.die.getValue());
    playerCharacter.setTempCha(chaAttributeRow.die.getValue());

    playerCharacter.setBaseStrBonus(strBonusRow.totBonusValue);
    playerCharacter.setBaseDexBonus(dexBonusRow.totBonusValue);
    playerCharacter.setBaseConBonus(conBonusRow.totBonusValue);
    playerCharacter.setBaseIntBonus(intBonusRow.totBonusValue);
    playerCharacter.setBaseWisBonus(wisBonusRow.totBonusValue);
    playerCharacter.setBaseChaBonus(chaBonusRow.totBonusValue);

    playerCharacter.setTempStrBonus(playerCharacter.getBaseStrBonus());
    playerCharacter.setTempDexBonus(playerCharacter.getBaseDexBonus());
    playerCharacter.setTempConBonus(playerCharacter.getBaseConBonus());
    playerCharacter.setTempIntBonus(playerCharacter.getBaseIntBonus());
    playerCharacter.setTempWisBonus(playerCharacter.getBaseWisBonus());
    playerCharacter.setTempChaBonus(playerCharacter.getBaseChaBonus());

    playerCharacter.setNaturalDefense(
                      Formulas.calcNaturalDefense(playerCharacter));

    playerCharacter.setEquipmentDefense(0);

    playerCharacter.setMeleeAttack    (Formulas.calcMeleeAttack(playerCharacter));
    playerCharacter.setThrownAttack   (Formulas.calcThrownAttack(playerCharacter));
    playerCharacter.setLauncherAttack (Formulas.calcLauncherAttack(playerCharacter));

  } // end setPlayerCharacterStat
} // end StatsScreen class

// ************************************************************

class Die {

  private int value;
  private boolean highlighted;
  private boolean allocated;

  JLabel label;

  // constructor
  Die(StatsScreen parent){
    value = 0;
    allocated = false;
    label = new JLabel();
    label.setPreferredSize(new Dimension(StatsScreen.ROW_COMPONENT_HEIGHT,
                                         StatsScreen.ROW_COMPONENT_HEIGHT));
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.addMouseListener(parent);
    updateDieLabel();
    unhighlight();
  } // end constructor

  // public methods

  boolean isHighlighted(){return highlighted;} // end isHighlighted
  boolean isAllocated(){return allocated;} // end isAllocated
  int getValue() {return value;} // end getValue

  void setValue(int v) {
    value = v;
    updateDieLabel();
  } // end setValue

  void highlight() {
    highlighted = true;
    if (!isAllocated()) {
      label.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createRaisedBevelBorder(),
                      BorderFactory.createLineBorder(Color.yellow)));
    } // end if
  } // end highlight

  void unhighlight() {
    highlighted = false;
    if(allocated == false)
      label.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createRaisedBevelBorder(),
                      BorderFactory.createLineBorder(Color.orange)));
  } // end unhighlight

  void setAllocated(boolean b) {
    allocated = b;
    if (allocated){
      label.setBorder(BorderFactory.createLineBorder(Color.gray));
      label.setText("");
    } // end if
    if (!allocated){
      label.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createRaisedBevelBorder(),
                      BorderFactory.createLineBorder(Color.yellow)));
      label.setText(String.valueOf(getValue()));
    } // end if
  } // end allocate

  void updateDieLabel(){
    if ( value == 0){
      label.setText("");
    } // end if
    else {
      label.setText(String.valueOf(value));
    } // end else
  } // end updatedDieValue

  // private methods

}  // end class Die

// ************************************************************

class BonusCalculatorRow extends JPanel {

  static final int H_SPACER_DIVISOR = 8;

  JLabel dieBonusLabel;
  JLabel plusLabel;
  JLabel raceBonusLabel;
  JLabel equalsLabel;
  JLabel totBonusLabel;

  int dieBonusValue = 0;  // these are defaults, but will be changed before showing screen!
  int raceBonusValue = 0;
  int totBonusValue;

      // constructor
  BonusCalculatorRow(String attribute){
    super();
    setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

    // each row consists of: dieBonus, "+", raceBonus, "=" totalBonus.

    // given the corresponding die, calculate dieBonus
    if (attribute == "Title"){
      setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
      dieBonusLabel = new JLabel("Die");
      dieBonusLabel.setHorizontalAlignment(JLabel.CENTER);
      dieBonusLabel.setVerticalAlignment(JLabel.TOP);
      dieBonusLabel.setPreferredSize(new Dimension((int)(2 * StatsScreen.BONUS_CALC_COLUMN_WIDTH / H_SPACER_DIVISOR) - 5,
                                                         StatsScreen.COLUMN_TITLE_HEIGHT));
      plusLabel = new JLabel(" + ");
      plusLabel.setHorizontalAlignment(JLabel.CENTER);
      plusLabel.setVerticalAlignment(JLabel.TOP);
      plusLabel.setPreferredSize(new Dimension((int)(StatsScreen.BONUS_CALC_COLUMN_WIDTH / H_SPACER_DIVISOR) + 3,
                                                         StatsScreen.COLUMN_TITLE_HEIGHT));
      raceBonusLabel = new JLabel("Race");
      raceBonusLabel.setHorizontalAlignment(JLabel.CENTER);
      raceBonusLabel.setVerticalAlignment(JLabel.TOP);
      raceBonusLabel.setPreferredSize(new Dimension((int)(2 * StatsScreen.BONUS_CALC_COLUMN_WIDTH / H_SPACER_DIVISOR),
                                                         StatsScreen.COLUMN_TITLE_HEIGHT));
      equalsLabel = new JLabel(" = ");
      equalsLabel.setHorizontalAlignment(JLabel.CENTER);
      equalsLabel.setVerticalAlignment(JLabel.TOP);
      equalsLabel.setPreferredSize(new Dimension((int)(StatsScreen.BONUS_CALC_COLUMN_WIDTH / H_SPACER_DIVISOR),
                                                         StatsScreen.COLUMN_TITLE_HEIGHT));
      totBonusLabel = new JLabel("Total");
      totBonusLabel.setHorizontalAlignment(JLabel.CENTER);
      totBonusLabel.setVerticalAlignment(JLabel.TOP);
      totBonusLabel.setPreferredSize(new Dimension((int)(2 * StatsScreen.BONUS_CALC_COLUMN_WIDTH / H_SPACER_DIVISOR),
                                                         StatsScreen.COLUMN_TITLE_HEIGHT));
      add(dieBonusLabel);
      add(plusLabel);
      add(raceBonusLabel);
      add(equalsLabel);
      add(totBonusLabel);
    } // end title attribute

   else {//(attribute is a stat)
      dieBonusLabel = createEtchedLabel();
      add(dieBonusLabel);
      add(new JLabel(" + "));
      raceBonusLabel = createEtchedLabel();
      add(raceBonusLabel);
      add(new JLabel(" = "));
      totBonusValue = dieBonusValue + raceBonusValue;
      totBonusLabel = createEtchedLabel();
      add(totBonusLabel);
      }   // end else - dieBonusRow for a stat

  } // end constructor

// package methods

  // private methods

  private JLabel createEtchedLabel(){
    JLabel label = new JLabel("0");
    label.setPreferredSize(new Dimension(StatsScreen.ROW_COMPONENT_HEIGHT,
                                         StatsScreen.ROW_COMPONENT_HEIGHT));
    label.setBorder(BorderFactory.createEtchedBorder());
    label.setHorizontalAlignment(SwingConstants.CENTER);
    return label;
  } // end createColoredTextLabel

  void updateBonusFromDie(int dieValue){
//    Popup.createInfoPopup("setting total with dieValue: " + dieValue);
    if(dieValue != 0){
      dieBonusValue = (dieValue - 10) / 2;
      updateBonusLabel(dieBonusLabel, dieBonusValue);
    } // end if
    else { // dieValue == 0
     dieBonusValue = 0;
     updateBonusLabel(dieBonusLabel, 0);
    } // end else

    updateTotBonusLabel();
  } // end setBonusFromDie

  void updateBonusLabel(JLabel label, int value){
     if (value < 0) label.setForeground(Color.red);
     else if (value == 0)label.setForeground(Color.black);
     else if (value > 0) label.setForeground(Color.green);
     label.setText(String.valueOf(value));
   } // end updateBonusLabel

  void updateTotBonusLabel(){
    totBonusValue = dieBonusValue + raceBonusValue;
    updateBonusLabel(totBonusLabel, totBonusValue);
  } //
} // end class BonusCalculatorRow

// ************************************************************

class AttributeRow extends JPanel implements MouseListener{

  JLabel attributeLabel;
  JLabel dieSlot;
  Die    die;
  boolean filled;
  boolean highlighted;

//constructor
  AttributeRow (String attribute, StatsScreen parent) {
//    setBorder(BorderFactory.createEtchedBorder());
    setPreferredSize(new Dimension(115, StatsScreen.ROW_COMPONENT_HEIGHT));
    attributeLabel = new JLabel("   " + attribute);
    highlighted = false;
    changeBorder();

    dieSlot = new JLabel();
    dieSlot.setPreferredSize(new Dimension(28,10));
    dieSlot.setHorizontalAlignment(SwingConstants.CENTER);
    dieSlot.setBorder(BorderFactory.createLoweredBevelBorder());

    die = new Die(parent);
    die.setValue(0);
    this.setLayout(new BorderLayout());
    add(dieSlot, BorderLayout.WEST);
    add(attributeLabel, BorderLayout.CENTER);

    attributeLabel.addMouseListener(parent);
    dieSlot.addMouseListener(parent);

  } // end constructor

// package methods

  public void mousePressed(MouseEvent m){}
  public void mouseReleased(MouseEvent m){}
  public void mouseClicked(MouseEvent m){}
  public void mouseEntered(MouseEvent m){}
  public void mouseExited(MouseEvent m){}

  void updateDieSlotText(int v) {
    if (v == 0){
      dieSlot.setText("");
    } // end if
    else {
      dieSlot.setText(String.valueOf(v));
    } // else if
  } // endUpdateDieSlotText

  boolean isFilled(){return filled;} // end isFilled

  void setFilled(boolean b){
    filled = b;
    if (filled)  dieSlot.setBorder(BorderFactory.createLineBorder(Color.gray));
    if (!filled) dieSlot.setBorder(BorderFactory.createLoweredBevelBorder());
  } // end setFilled

  boolean isHighlighted() {return highlighted;}

  void setHighlighted(boolean b) {
    highlighted = b;
    changeBorder();
  } // end setHighlighted

  void changeBorder() {
    if (isHighlighted() == true){
      setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
    } // end true

    else {
      setBorder(BorderFactory.createEtchedBorder());
    } // end false
  } // end changeBorder()

// private methods

} // end class Attribute
