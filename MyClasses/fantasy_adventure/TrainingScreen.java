package fantasy_adventure;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TrainingScreen extends CGenScreen {

//********************************************************************
// static declarations
//********************************************************************
  static final int MAX_SKILLBUTTONS_PER_PANEL = 7;
  static final int CROSSOVER_SKILLS_AVAILABLE = 2; // remember humans get +1 bonus.

  static String CBSKILLS_FILENAME = FileNames.CB_SKILLS;
  static String SSSKILLS_FILENAME = FileNames.SS_SKILLS;
  static String PESKILLS_FILENAME = FileNames.PE_SKILLS;

  static String skillsDescription   = FileNames.SKILLS_DESC;
  static String cbDescription       = FileNames.CB_DESC;
  static String ssDescription       = FileNames.SS_DESC;
  static String peDescription       = FileNames.PE_DESC;

  static String skillsAvailableTitleDesc = FileNames.SKILLS_AVAILABLE_DESC;
  static String crossoverSkillsDesc      = FileNames.CROSSOVER_DESC;
  static String skillsTrainedTitleDesc   = FileNames.SKILLS_TRAINED_DESC;

  static int    MAX_SKILLBUTTONS = MyTextIO.getNumLines(CBSKILLS_FILENAME) +
                                   MyTextIO.getNumLines(CBSKILLS_FILENAME) +
                                   MyTextIO.getNumLines(CBSKILLS_FILENAME);

  static boolean canRemove        = false;
  static boolean havePointToSpend = false;
  static SkillButton thisButton;

  static Skill[] playerSkills;

  static SkillButton[] cbButtons;
  static SkillButton[] ssButtons;
  static SkillButton[] peButtons;
  static SkillButton[] trainedButtons;

  static long lastClick = 0;
  static long thisClick = 0;
  static String command; // there can only be one command at a time, it just changes states.
  static boolean HAS_BEEN_VIEWED = false;

//********************************************************************
// variable and object declarations
//********************************************************************
  // title bar objects
  JPanel        skillsTitleBar;

  JPanel        skillsCategoryPanel;
  JRadioButton  cbSkillsButton;
  String        cbButtonText = "Combat and Battle Skills: ";
  JRadioButton  ssSkillsButton;
  String        ssButtonText = "Stealth and Social Skills: ";
  JRadioButton  peSkillsButton;
  String        peButtonText = "Power and Energy Skills: ";

  int           cbSkillsStart;
  int           peSkillsStart;
  int           ssSkillsStart;
  int           crossoverSkillsStart;
  int           cbSkillsRemaining;
  int           peSkillsRemaining;
  int           ssSkillsRemaining;
  int           crossoverSkillsRemaining;

  JLabel        skillsAvailableTitleLabel;
  JLabel        crossoverSkillsLeftLabel;
  String        crossoverLabelText = "Crossover Skills Available: ";
  JLabel        skillsTrainedTitleLabel;

  // SkillsTransferPanel and internal objects
  JPanel        skillsTransferPanel;

  int           CBpanelHeight;
  int           SSpanelHeight;
  int           PEpanelHeight;
  int           TpanelHeight  = 0;

  int           paneHeight = (MAX_SKILLBUTTONS_PER_PANEL * SkillButton.BUTTON_HEIGHT);
  int           paneWidth  = (SkillButton.BUTTON_WIDTH) + 18; // + width of scrollBar.

  JPanel        CBskillsAvailablePanel;
  JPanel        SSskillsAvailablePanel;
  JPanel        PEskillsAvailablePanel;
  JScrollPane   skillsAvailableScrollPane;

  JTextArea     descriptionArea;
  JScrollPane   descriptionScrollPane;

  JPanel        skillsTrainedPanel;
  JScrollPane   skillsTrainedScrollPane;

  int           currentAvailablePanelView = 0;
  int           currentCBPanelView        = 0;
  int           currentSSPanelView        = 0;
  int           currentPEPanelView        = 0;
  int           currentTrainedPanelView   = 0;

  int           numCBSkills;
  int           numSSSkills;
  int           numPESkills;
  int           numTrainedSkills;

  // bottom button bar
  JPanel        skillsAddRemovePanel;
  JButton       addSkillButton;
  JButton       removeSkillButton;

//********************************************************************
// constructor
//********************************************************************

public TrainingScreen(){
  super();
  setTitle("Fantasy Adventure - Character Generation - Step 4: Training");

  topTextArea.setText(FileNames.CGEN_TRAINING_SCREEN_INTRO);
  centralPanel.setLayout(new FlowLayout(FlowLayout.CENTER, Constants.SCREEN_WIDTH, 5 ));
  setBorder();

  // initialize skillCategoryPanel - holds the three category buttons
  skillsCategoryPanel = new JPanel();
  skillsCategoryPanel.setPreferredSize(new Dimension((Constants.SCREEN_WIDTH
                                                      - this.leftInset
                                                      - this.rightInset
                                                      - 8), 50));
  skillsCategoryPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
  skillsCategoryPanel.setBorder(BorderFactory.createEtchedBorder());
  skillsCategoryPanel.setBackground(Color.WHITE);

  // initialize objects inside skillCategoryPanel
  int SkillButtonWidth = (int)(Constants.SCREEN_WIDTH
                               - this.leftInset
                               - this.rightInset
                               + 90) / 3;

  cbSkillsButton = new JRadioButton(cbButtonText + cbSkillsRemaining);
  cbSkillsButton.setActionCommand("Combat and Battle Skills");
  cbSkillsButton.addActionListener(this);
  cbSkillsButton.setSize(SkillButtonWidth, 25);

  ssSkillsButton = new JRadioButton(ssButtonText + ssSkillsRemaining);
  ssSkillsButton.setActionCommand("Stealth and Social Skills");
  ssSkillsButton.addActionListener(this);
  ssSkillsButton.setSize(SkillButtonWidth, 25);

  peSkillsButton = new JRadioButton(peButtonText + peSkillsRemaining);
  peSkillsButton.setActionCommand("Power and Energy Skills");
  peSkillsButton.addActionListener(this);
  peSkillsButton.setSize(SkillButtonWidth, 25);

  // add objects to skillCategoryPanel
  skillsCategoryPanel.add(cbSkillsButton);
  skillsCategoryPanel.add(ssSkillsButton);
  skillsCategoryPanel.add(peSkillsButton);

  // initialize skillsTitleBar - just a bar holding the labels above the main three panels
  skillsTitleBar = new JPanel();
  skillsTitleBar.setPreferredSize(new Dimension(740, 30));
  skillsTitleBar.setLayout(new GridLayout(1, 3));

  // initialize objects inside skillsTitleBar
  skillsAvailableTitleLabel = new JLabel("Skills Available");
  skillsAvailableTitleLabel.setSize(200, 25);
  skillsAvailableTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

  crossoverSkillsLeftLabel = new JLabel(crossoverLabelText
                                        + String.valueOf(crossoverSkillsRemaining));
  crossoverSkillsLeftLabel.setSize(200, 25);
  crossoverSkillsLeftLabel.setHorizontalAlignment(SwingConstants.CENTER);

  skillsTrainedTitleLabel = new JLabel("Skills Trained");
  skillsTrainedTitleLabel.setSize(200, 25);
  skillsTrainedTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

  // add objects to skillsTitleBar
  skillsTitleBar.add(skillsAvailableTitleLabel);
  skillsTitleBar.add(crossoverSkillsLeftLabel);
  skillsTitleBar.add(skillsTrainedTitleLabel);

  // initialize skillsTransferPanel - holds the three panels
  skillsTransferPanel = new JPanel();
  // the rest of details don't really matter, since this is a non-visual container

  // create and setup skillsAvailablePanel
  CBskillsAvailablePanel = createSkillPanel("CB");
  SSskillsAvailablePanel = createSkillPanel("SS");
  PEskillsAvailablePanel = createSkillPanel("PE");
  skillsAvailableScrollPane = new JScrollPane(CBskillsAvailablePanel);
  skillsAvailableScrollPane.setPreferredSize(new Dimension(paneWidth, paneHeight));

  // create and setup skillsTrainedPanel
  skillsTrainedPanel = createSkillPanel("T");

  skillsTrainedScrollPane = new JScrollPane(skillsTrainedPanel);
  skillsTrainedScrollPane.setPreferredSize(new Dimension(paneWidth, paneHeight));

  refreshButtonsAndSkills();
  refreshPoints();
  resetPaneHeights();

 // description area and panel setup
  descriptionArea = MyUtils.makeTextBox(20,14 ,Color.white, Color.black, skillsDescription);
  descriptionArea.setBorder(BorderFactory.createMatteBorder(10,5,10,5,Color.BLACK));

  descriptionScrollPane = new JScrollPane(descriptionArea);
  descriptionScrollPane.setPreferredSize(new Dimension(paneWidth, paneHeight));

  // add objects to skillsTransferPanel
  skillsTransferPanel.add(skillsAvailableScrollPane);
  skillsTransferPanel.add(descriptionScrollPane);
  skillsTransferPanel.add(skillsTrainedScrollPane);

  // initialize skillAddRemovePanel
  skillsAddRemovePanel = new JPanel();
  skillsAddRemovePanel.setPreferredSize(new Dimension(740, 50));
  skillsAddRemovePanel.setLayout(new GridLayout(1, 2));

  // initialize objects inside skillAddRemovePanel
  addSkillButton    = makeActionButton("Add Skill");
  removeSkillButton = makeActionButton("Remove Skill");

  // add objects to skillAddRemovePanel
  skillsAddRemovePanel.add(addSkillButton);
  skillsAddRemovePanel.add(errorLabel);
  skillsAddRemovePanel.add(removeSkillButton);

  // add macro objects to centralPanel
  centralPanel.add(skillsCategoryPanel);
  centralPanel.add(skillsTitleBar);
  centralPanel.add(skillsTransferPanel);
  centralPanel.add(skillsAddRemovePanel);

  } // end constructor

// ********************************************************************************
// public methods
// ********************************************************************************
  public void actionPerformed(ActionEvent e){
    command = e.getActionCommand();
    lastClick = thisClick; // moves the time a step forward
    thisClick = e.getWhen(); // set time for new step.

    /* as soon as the user clicks on anything in the screen,
     * then we should load the character's skill buttons.
     * we have waited until now to be sure that the character
     * is not null and it will have stats associated with it.
    */
    if (HAS_BEEN_VIEWED == false){
      resetTrainingScreen();
      HAS_BEEN_VIEWED = true;
    } // end if false

    // first check for basic description mouseclicks.
    if (e.getSource() == crossoverSkillsLeftLabel){
      // user has clicked on the crossoverSkillsLeftLabel
      descriptionArea.setText(crossoverSkillsDesc);
      descriptionArea.setCaretPosition(0);
    } // end if crossoverSkillsLeftLabel

    else if (e.getSource() == skillsAvailableTitleLabel){
      // user has clicked on the skillsAvailableTitleLabel
      descriptionArea.setText(skillsAvailableTitleDesc);
      descriptionArea.setCaretPosition(0);
    } // end if crossoverSkillsLeftLabel

    else if (e.getSource() == skillsTrainedTitleLabel){
      // user has clicked on the skillsTrainedTitleLabel
      descriptionArea.setText(skillsTrainedTitleDesc);
      descriptionArea.setCaretPosition(0);
    } // end if crossoverSkillsLeftLabel

// ********************************************************************************
    // now we get to the real action, checking for button clicks, etc.
    else if (command == "Combat and Battle Skills"){
      // user has chosen to view the combat and battle skills
      clearRadioButtons();
      cbSkillsButton.setSelected(true);
      skillsAvailableTitleLabel.setText("Combat and Battle Skills Available");
      descriptionArea.setText(cbDescription);
      descriptionArea.setCaretPosition(0);

      unHighlightAllSkillButtons();
      loadButtonPanel(cbButtons, CBskillsAvailablePanel); // load buttons
      skillsAvailableScrollPane.setViewportView(CBskillsAvailablePanel);
      resetPaneView();
    } // end cb radio button

    else if (command == "Stealth and Social Skills"){
      // user has chosen to view the combat and battle skills
      clearRadioButtons();
      ssSkillsButton.setSelected(true);
      skillsAvailableTitleLabel.setText("Stealth and Social Skills Available");
      descriptionArea.setText(ssDescription);
      descriptionArea.setCaretPosition(0);

      unHighlightAllSkillButtons();
     loadButtonPanel(ssButtons, SSskillsAvailablePanel);
      skillsAvailableScrollPane.setViewportView(SSskillsAvailablePanel);
      resetPaneView();
    } // end ss radio button

    else if (command == "Power and Energy Skills"){
      // user has chosen to view the combat and battle skills
      clearRadioButtons();
      peSkillsButton.setSelected(true);
      skillsAvailableTitleLabel.setText("Power and Energy Skills Available");
      descriptionArea.setText(peDescription);
      descriptionArea.setCaretPosition(0);

      unHighlightAllSkillButtons();
      loadButtonPanel(peButtons, PEskillsAvailablePanel);
      skillsAvailableScrollPane.setViewportView(PEskillsAvailablePanel);
      resetPaneView();
    } // end pe radio button

// ********************************************************************************
    else if (command == "skillsAvailableButton" ||
             command == "skillsTrainedButton"     ){
      // User has chosen to click on a skill button.
      // first, find out which one:
      SkillButton thisSkillButton = (SkillButton)e.getSource();

      // now, we need to find out what the state of the button is,
      // and adjust the state and/or highlighting of the button accordingly.
      checkButtonStateAndRespond(thisSkillButton, e);
    } // end if skills button pressed

//******************************************************************
    else if (command == "Add Skill") {
      // user has clicked on the 'Add Skill' button
      // We need to check that there is a button highlighted, and that it can be added.
      // Cycle through all the buttons in the list and see if any are highlighted.

      thisButton = null;
      if     (cbSkillsButton.isSelected())  {thisButton = getHighlightedButton(cbButtons);} // end  cb check
      else if(ssSkillsButton.isSelected())  {thisButton = getHighlightedButton(ssButtons);} // end  ss check
      else if(peSkillsButton.isSelected())  {thisButton = getHighlightedButton(peButtons);} // end (pe check)
      else {}

      if (thisButton == null)
        {descriptionArea.setText("You need to select a skill to be added first.");}

//******************************************************************
         // otherwise, a button is highlighted, so...

      else{ //(thisButton != null)
        processAddingButton(thisButton);
      } // end if button highlighted

    } // end response to command 'Add Skill'

// ********************************************************************************
    else if (command == "Remove Skill") {
      // user has clicked on the 'Remove Skill' button

      processRemovingButton(getHighlightedButton(trainedButtons));

    } // end remove Skill response

// ********************************************************************************

    else {
      if (Constants.debugMode == true)
      Popup.createErrorPopup("Command: " + command + "unrecognized, sorry.");
    } // end else
  } // end actionPerformed(e)

//*******************************************************************************
  void setCharacter(PlayerCharacter PC){

      playerCharacter = PC;

      playerCharacter.removeAllSkills();
      playerCharacter.resetResistances(); // just in case these have been set.

      CBskillsAvailablePanel.removeAll();
      SSskillsAvailablePanel.removeAll();
      PEskillsAvailablePanel.removeAll();

      skillsAvailableScrollPane.setViewportView(CBskillsAvailablePanel);

      skillsTrainedPanel.removeAll();
      skillsTrainedScrollPane.setViewportView(skillsTrainedPanel);

      cbSkillsStart = calculateSkillPoints(playerCharacter.getBaseStrBonus(),
                                           playerCharacter.getBaseConBonus());
      if (cbSkillsStart < 0) cbSkillsStart = 0;
      cbSkillsRemaining = cbSkillsStart;
      cbSkillsButton.setText("Combat and Battle Skills: " + cbSkillsRemaining);

      ssSkillsStart = calculateSkillPoints(playerCharacter.getBaseDexBonus(),
                                           playerCharacter.getBaseChaBonus());
      if (ssSkillsStart < 0) ssSkillsStart = 0;
      ssSkillsRemaining = ssSkillsStart;
      ssSkillsButton.setText("Stealth and Social Skills: " + ssSkillsRemaining);

      peSkillsStart = calculateSkillPoints(playerCharacter.getBaseIntBonus(),
                                           playerCharacter.getBaseWisBonus());
      if (peSkillsStart < 0) peSkillsStart = 0;
      peSkillsRemaining = peSkillsStart;
      peSkillsButton.setText("Power and Energy Skills: " + peSkillsRemaining);

      if (playerCharacter.getRace().getName() == "Human"){
        crossoverSkillsRemaining = crossoverSkillsStart + 1;
        crossoverSkillsLeftLabel.setText(crossoverLabelText + crossoverSkillsRemaining);
      } // end if human
  } // end setCharacterInfo

//*******************************************************************************
  public void resetTrainingScreen(){
    // the purpose of this method is to reset everything, so we are seeing
    // the screen clean again, including all character stats and points.

    refreshButtonsAndSkills();
    refreshPoints();
    resetPaneView();
    setCharacter(playerCharacter);
    visited = false;
  } // end resetTrainingScreen

//*******************************************************************************
  public void refreshButtonsAndSkills() {
    cbButtons = createSkillButtonsFromSkillArray(Skill.CBSkills);
    ssButtons = createSkillButtonsFromSkillArray(Skill.SSSkills);
    peButtons = createSkillButtonsFromSkillArray(Skill.PESkills);

    trainedButtons = new SkillButton[MAX_SKILLBUTTONS];

    /* trainedButtons does not need to be created from a file,
     * since we know the character's skills will be empty at this point.
     * when we are done however, we will be saving the array of skills
     * to the player character for later use and recall.
     */

    // set the index for use by the scrollbar for the different panels, but don't use them yet.
    for (numCBSkills = 0; cbButtons[numCBSkills] != null; numCBSkills++){} // note these will be one less (matching index but not amount)
    for (numSSSkills = 0; ssButtons[numSSSkills] != null; numSSSkills++){}
    for (numPESkills = 0; peButtons[numPESkills] != null; numPESkills++){}
    numTrainedSkills = 0;

    resetPaneHeights();

  } // end refreshButtonsAndSkills

//*******************************************************************************
  public void refreshPoints() {
    cbSkillsStart = 0;
    cbSkillsRemaining = cbSkillsStart;
    ssSkillsStart = 0;
    ssSkillsRemaining = ssSkillsStart;
    peSkillsStart = 0;
    peSkillsRemaining = peSkillsStart;
    crossoverSkillsStart = CROSSOVER_SKILLS_AVAILABLE;
    crossoverSkillsRemaining = crossoverSkillsStart;
      crossoverSkillsLeftLabel.
        setText(crossoverLabelText
      + String.valueOf(crossoverSkillsRemaining));

  } // end refreshPoints

//*******************************************************************************
  void clearInfo(){
    // the purpose of this method is to clear the information that may have been
    // stored in player character that comes from this screen: skills.
    playerCharacter.removeAllSkills();
    // now that the skills are removed, then we can set the correct resistances (0);
    playerCharacter.resetResistances();
    refreshButtonsAndSkills();
    refreshPoints();
    clearRadioButtons();

  } // end clearInfo

//*******************************************************************************
  public PlayerCharacter getCharacter(){
    // before passing the character away, finish up other calculations.
    playerCharacter.calculateBaseHPMP();
    playerCharacter.updateLevels();
    playerCharacter.resetResistances();
    playerCharacter.resetImmunities();
    playerCharacter.resetSaves();
    playerCharacter.resetCastingFailure();
    playerCharacter.resetFame();
    playerCharacter.inventory.goldAmount = Formulas.calcBeginningGold(playerCharacter);

    return playerCharacter;
  } // end getCharacter

// ********************************************************************************
// private methods
// ********************************************************************************
  private void checkButtonStateAndRespond(SkillButton thisButton, ActionEvent e){
    // this method is called when user clicks on either an avaiable or trained button

    if (thisButton.isHighlighted() == false){
      // user has clicked on an unhighlighted skill
      // we need to unghighlight all the buttons, then highlight this one.
      unHighlightAllSkillButtons();
      thisButton.setHighlighted(true);
    // set skill description
    descriptionArea.setText(thisButton.thisSkill.getDescription());

    // add description of skill requirements
    descriptionArea.append(MyTextIO.newline +
                            MyTextIO.newline +
                           "Skill Requirements:" +
                            MyTextIO.newline);

      for (int i = 0; i < thisButton.thisSkill.getNumRequirements(); i++){
        displaySkillRequirement(thisButton, i);
      } // end for loop

    descriptionArea.setCaretPosition(0);

    } // end if not highlighted

    else{ // if thisButton is highlighted
      // check for a double click.
      if (thisClick - lastClick <= Constants.DOUBLE_CLICK_SPEED && thisClick != lastClick){
        lastClick = 0; // reset timer to avoid another double-click

        // we need to check which 'way' the user is doubleclicking
        if (thisButton == getHighlightedButton(trainedButtons)){
          command = "Remove Skill";
          processRemovingButton(thisButton);
        } // end if trainedButton

        else { // thisButton is from one of the AvailableButtons (CB,SS,PE)
          command = "Add Skill";
          processAddingButton(thisButton);
        } // end if availableButton
      } // end if user double-clicked
    } // end thisButton is highlighted
  } // end checkButtonStateAndRespond

//********************************************************************************
  private int calculateSkillPoints(int a, int b){
    int result = 0;
    result = (int)( (a + b + 1) / 2); // sum of skils, divided by 2, rounded up.
    return result;
  } // end calcPoints

//*******************************************************************************
  private void transfer(SkillButton thisButton, String command){
    /* the purpose of this method is to transfer the given button
     * depending upon the command and the button's skill's category.
     * We assume the points have been taken care of, all we need to do is:
     * - if the command is add, then remove the button from the category
     *   and add it to the trained
     * - if the command is remove, then remove the button from the trained
     *   and add it to the category
     */

/*      Popup.createInfoPopup("When starting transfer method:" + MyTextIO.newline +
                                  "CBSkills: " + numCBSkills + MyTextIO.newline +
                                  "SSSkills: " + numSSSkills + MyTextIO.newline +
                                  "PESkills: " + numPESkills + MyTextIO.newline +
                                  "TrainedSkills: " + numTrainedSkills);
  */

    if (command == "Add Skill"){
      // so we want to transfer thisButton from it's category to the character...

      // add button to the character trained panel
      numTrainedSkills += 1;
      addButton(thisButton, trainedButtons, numTrainedSkills);

      // add skill to character
      playerCharacter.addSkill(thisButton.thisSkill);

      // spend the point appropriately
      if  (thisButton.thisSkill.getCategory() == Skill.CB_SKILL &&
          cbSkillsRemaining > 0){
        cbSkillsRemaining -= 1;
        cbSkillsButton.setText(cbButtonText + cbSkillsRemaining);
      } // end if have category skill

      else if (thisButton.thisSkill.getCategory() == Skill.SS_SKILL &&
          ssSkillsRemaining > 0){
        ssSkillsRemaining -= 1;
        ssSkillsButton.setText(ssButtonText + ssSkillsRemaining);
      } // end if have category skill

      else if (thisButton.thisSkill.getCategory() == Skill.PE_SKILL &&
          peSkillsRemaining > 0){
        peSkillsRemaining -= 1;
        peSkillsButton.setText(peButtonText + peSkillsRemaining);
      } // end if have category skill

      else {
        crossoverSkillsRemaining -=1;
        crossoverSkillsLeftLabel.setText(crossoverLabelText + crossoverSkillsRemaining);
      } // end else use crossover

      // Now, remove it from it's category
      if (thisButton.thisSkill.getCategory() == Skill.CB_SKILL){
        removeButton(thisButton, cbButtons);
        numCBSkills -= 1;
      } // end CB_Skill

      else if (thisButton.thisSkill.getCategory() == Skill.SS_SKILL){
        removeButton(thisButton, ssButtons);
        numSSSkills -= 1;
      } // end SS_Skill

      else if (thisButton.thisSkill.getCategory() == Skill.PE_SKILL){
        removeButton(thisButton, peButtons);
        numPESkills -= 1;
      } // end PE_Skill

      else {
        if (Constants.debugMode == true)
        Popup.createErrorPopup("Skill category unknown!");
      } // end unknown
    } // end if command == "Add Skill"

//****************************************************************************

    else if (command == "Remove Skill"){

      // remove thisButton from the character's panel
      removeButton(thisButton, trainedButtons);

      // decrease the lastSkill for the character's array
      numTrainedSkills -= 1;

      // remove the skill from the character
      playerCharacter.removeSkill(thisButton.thisSkill);

      // add thisButton to the category panel
      // increase the lastSkill for the category panel
      // set the category's panel scrollbarMaximum
      if (thisButton.thisSkill.getCategory() == Skill.CB_SKILL){
        numCBSkills += 1;
        addButton(thisButton, cbButtons, numCBSkills);
      } // end CB

      else if (thisButton.thisSkill.getCategory() == Skill.SS_SKILL){
        numSSSkills += 1;
        addButton(thisButton, ssButtons, numSSSkills);
      }  // end SS

      else if (thisButton.thisSkill.getCategory() == Skill.PE_SKILL){
        numPESkills += 1;
        addButton(thisButton, peButtons, numPESkills);
      } // end PE
      else {}

      // add point to correct category, or crossover
      if (cbSkillsRemaining < cbSkillsStart && thisButton.thisSkill.getCategory() == Skill.CB_SKILL){
        cbSkillsRemaining += 1;
        cbSkillsButton.setText(cbButtonText + cbSkillsRemaining);
        } // end if cb

      else if (ssSkillsRemaining < ssSkillsStart && thisButton.thisSkill.getCategory() == Skill.SS_SKILL){
        ssSkillsRemaining += 1;
        ssSkillsButton.setText(ssButtonText + ssSkillsRemaining);
        } // end if ss

      else if (peSkillsRemaining < peSkillsStart && thisButton.thisSkill.getCategory() == Skill.PE_SKILL){
        peSkillsRemaining += 1;
        peSkillsButton.setText(peButtonText + peSkillsRemaining);
        } // end if pe

      else {
        crossoverSkillsRemaining += 1;
        crossoverSkillsLeftLabel.setText(crossoverLabelText + crossoverSkillsRemaining);
      } // end else

    } // end if Remove Skill
    else {} //not add or remove

/*   Popup.createInfoPopup("When ending transfer method:" + MyTextIO.newline +
                         "CBSkills: " + numCBSkills + MyTextIO.newline +
                         "SSSkills: " + numSSSkills + MyTextIO.newline +
                         "PESkills: " + numPESkills + MyTextIO.newline +
                         "TrainedSkills: " + numTrainedSkills);
*/
   // redraw category and character panel
   resetPaneHeights();
   if (thisButton.thisSkill.getCategory() == Skill.CB_SKILL)
   loadButtonPanel(cbButtons, CBskillsAvailablePanel);
   else if (thisButton.thisSkill.getCategory() == Skill.SS_SKILL)
   loadButtonPanel(ssButtons, SSskillsAvailablePanel);
   else if (thisButton.thisSkill.getCategory() == Skill.PE_SKILL)
   loadButtonPanel(peButtons, PEskillsAvailablePanel);
   else{}

   loadButtonPanel(trainedButtons, skillsTrainedPanel);
   thisButton = null;
} // end transfer

//***********************************************************************************
  private void processAddingButton(SkillButton thisButton){
    // user has attempted to add the highlighted skillButton
    // we need to check that the character can add this skill.
    if (thisButton.getState() == SkillButton.UNAVAILABLE){
      descriptionArea.setText("You do not meet the requirements for this skill, yet.");
    } // if canBeTrained == false

    else{ // getState() == AVAILABLE
    // if so, now we can actually do the transfer of the skillButton (skill)

    boolean havePointToSpend = true;
    if (cbSkillsButton.isSelected() && ( cbSkillsRemaining < 1 && crossoverSkillsRemaining < 1) ||
        ssSkillsButton.isSelected() && ( ssSkillsRemaining < 1 && crossoverSkillsRemaining < 1) ||
        peSkillsButton.isSelected() && ( peSkillsRemaining < 1 && crossoverSkillsRemaining < 1)){
      descriptionArea.setText("You do not have enough points to add this skill.");
      havePointToSpend = false;
    } // end if poor

    // at this point, we can add a skill since we DO have points to spend
    if (havePointToSpend)
      transfer(thisButton, command);

  } // end canBeTrained == true
 } // end processAddingButton()

//***********************************************************************************
  private void addButton(SkillButton thisButton,
                         SkillButton[] buttons,
                         int numSkills) {
    // purpose of this method is to add thisButton the end of the skillButton[].
    // Since the lastCBSkill is passed by content (being an int) we had to deal
    // with the adjustment outside of this method, but we can adjust the buttons
    // array with the new button because they are passed by reference.

/*    Popup.createInfoPopup("numSkills = " + numSkills + MyTextIO.newline +
                          "buttons.length = " + buttons.length);
*/
    buttons[numSkills - 1] = thisButton;  // minus one to adjust for 0 index start.
  } // end addButton

// ********************************************************************************
  private JButton makeActionButton(String s){
    JButton btn = new JButton(s);
    btn.setSize(250, 50);
    btn.addActionListener(this);
    return btn;
  } // end makeActionButton()

// ********************************************************************************
  private JPanel createSkillPanel(String s){
//    refreshButtonsAndSkills();
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
//    panel.setBorder(BorderFactory.createLoweredBevelBorder());
    panel.setBackground(Color.gray);
    return panel;
  } // end createASkillsPanel

// ********************************************************************************
  private void processRemovingButton(SkillButton thisButton){

      if (thisButton == null){
        // user has tried to remove a skill without selecting one.
        descriptionArea.setText("You need to select a skill to be removed first.");
      } // end if null

      else /* there is a button selected */{
        // *We need to check that this skill is not a prerequisite
        // of another trained skill!
        playerSkills = playerCharacter.getSkills();
        canRemove = true;

        for (int i = 0; i < playerSkills.length && canRemove; i++){
          // for each item, we need to check for a requirement
          if (playerSkills[i] == null) break;
          Requirement[] reqs = playerSkills[i].requirements;

          // for each req, we need to make sure it is not the same title.
          for (int j = 0; j < reqs.length && canRemove; j++){
            String a = Skill.getSkillNameFromRequirement(reqs[j]);
              if (a == null) continue;
            String b = thisButton.thisSkill.getTitle();

            if (a.equalsIgnoreCase(b)){
              descriptionArea.setText("You cannot remove this skill without first removing all skills that depend upon it.");
              canRemove = false;
            } // end if
          } // end J for loop
        } // end i for loop
        if (canRemove == true)
          transfer(thisButton, command);
      } // end if there is a button selected

      // since we now are (or are not) done transferring the button, clear the command for reset.
      command = null;

  } // end processRemovingButton

// ********************************************************************************
  private void removeButton(SkillButton thisButton, SkillButton[] buttons){
    // purpose of this method is to clear thisButton from the array,
    // then slide the rest down an index, so there is no blank.

    int index = 0;
    // find the index that needs to be removed
    for (index = 0; index < buttons.length; index++){
      if (buttons[index] == thisButton)break;
    } // end for loop
    // now we know that index is the place to be removed.
    // so we need to remove it
    buttons[index] = null;
    // and slide the rest of the list down an index.
    int i = index;

    // we need to find the last item in the list so we know when to stop.
    int a = i + 1;
    for (a = i + 1; a < buttons.length; a++){
      if (buttons[a] == null) break;
    } // end for loop(a)

    // so for the 'rest' of the list (a), shift the next button to this one.
    for (i = index; i < a - 1; i++){
      try{
        // copy the next button up a slot
        buttons[i] = buttons[i + 1];
        // and remove the next button to continue the cycle.
        buttons[i + 1] = null;
      }
      catch (ArrayIndexOutOfBoundsException aioe){
        if (Constants.debugMode == true)
        Popup.createErrorPopup("AIOE when removing this button.");
      } // end catch
    } // end for loop
    resetPaneHeights();
    skillsAvailableScrollPane.repaint();
    skillsTrainedScrollPane.repaint();

  } // end removeButton

  private void resetPaneView(){
    skillsAvailableScrollPane.getViewport().setViewPosition(new Point(0,0));
    skillsTrainedScrollPane.getViewport().setViewPosition(new Point(0,0));
  } // end resetPaneView

  private void resetPaneHeights(){
    CBpanelHeight = (numCBSkills * SkillButton.BUTTON_HEIGHT);
      CBskillsAvailablePanel.setPreferredSize(new Dimension(SkillButton.BUTTON_WIDTH, CBpanelHeight));

    SSpanelHeight = (numSSSkills * SkillButton.BUTTON_HEIGHT);
      SSskillsAvailablePanel.setPreferredSize(new Dimension(SkillButton.BUTTON_WIDTH, SSpanelHeight));

    PEpanelHeight = (numPESkills * SkillButton.BUTTON_HEIGHT);
      PEskillsAvailablePanel.setPreferredSize(new Dimension(SkillButton.BUTTON_WIDTH, PEpanelHeight));

    TpanelHeight = (numTrainedSkills * SkillButton.BUTTON_HEIGHT);
      skillsTrainedPanel.setPreferredSize(new Dimension(SkillButton.BUTTON_WIDTH, TpanelHeight));

  } // end resetPaneHeights

// ********************************************************************************
  private void displaySkillRequirement(SkillButton thisSkillButton, int i){
    if (thisSkillButton.thisSkill.requirements[i] != null) {
      descriptionArea.append(thisSkillButton.thisSkill.requirements[i].getDescription());
      descriptionArea.append(MyTextIO.newline);
    } // end if not null
  } // end displaySkillRequirement()

// ********************************************************************************
  private SkillButton[] createSkillButtonsFromSkillArray(Skill[] skills){
    SkillButton[] buttons = new SkillButton[skills.length];
    for (int i = 0; i < (skills.length - 1); i++){
      buttons[i] = new SkillButton(skills[i]);
    } // end for loop
    return buttons;
  } // end createSkillButtonsFromFile()

// ********************************************************************************
  private SkillButton getHighlightedButton(SkillButton[] buttons){
    // the purpose of this method is to cycle through the list of buttons and
    // see if any are highlighted, returning that button or null if none.
    // PRE: the list has items in it.

    for (int i = 0; buttons[i] != null; i++){
      if (buttons[i].isHighlighted() == true) return buttons[i];
    } // end for

    // if we get to this point, we have come to a null slot in the list
    // so either we have gone through the list, or there is a gap (error).
    // We have not found any highlighted buttons, so return null.
    return null;
  } // end getHighlighted

// ********************************************************************************
  private void loadButtonPanel(SkillButton[] buttons, JPanel buttonPanel){
      // the purpose of this method is to load the buttonPanel with just a few buttons
      // Specifically, the buttons starting with int view, and ending with view + max.
      // PRE: - the buttons[] does exist, (but empty is ok)
      // Note: we also need to set the state of the buttons according to the character!


      // Remove all buttons from the panel first, before adding new ones.
      buttonPanel.removeAll();

  for(int i = 0; i < buttons.length; i++){

    if (buttons[i] == null) continue;

    // for each button

    // set the correct state of the button
    if (buttons[i].thisSkill.canBeTrained(playerCharacter) == true){
        buttons[i].setState(SkillButton.AVAILABLE);
    } // end if can be trained == true

    else buttons[i].setState(SkillButton.UNAVAILABLE);

    // add button to the viewable panel
    buttonPanel.add(buttons[i]);

    // add listener to this button;
    if (buttonPanel == CBskillsAvailablePanel ||
        buttonPanel == SSskillsAvailablePanel ||
        buttonPanel == PEskillsAvailablePanel)
      buttons[i].setActionCommand("skillsAvailableButton");
    else if (buttonPanel == skillsTrainedPanel)
      buttons[i].setActionCommand("skillsTrainedButton");
    else { // buttonPanel == unknown
      if (Constants.debugMode == true)
      Popup.createWarningPopup("Cannot setActonCommand() for this button, buttonPanel unknown.");
    } // end else
    buttons[i].addActionListener(this);
  } // end for loop
  } // end loadButtonPanel

// ********************************************************************************
  private void clearRadioButtons(){
    cbSkillsButton.setSelected(false);
    ssSkillsButton.setSelected(false);
    peSkillsButton.setSelected(false);
  } // end clearRadioButtons

// ********************************************************************************
  private void unHighlightAllSkillButtons(){
    // purpose of this method is to clear all the buttons
    // within this panel of their highlighting.

    // loop through the four button panels
    unHighlightTheseButtons(cbButtons);
    unHighlightTheseButtons(ssButtons);
    unHighlightTheseButtons(peButtons);
    unHighlightTheseButtons(trainedButtons);
  } // end unHighlight

// ********************************************************************************
  private void unHighlightTheseButtons(SkillButton[] buttons){
    int i;
    for (i = 0; i< buttons.length; i++){
      // for each skillButton, set to unhighlight
      if (buttons[i] != null) buttons[i].setHighlighted(false);
    } // end for loop
  } // end all

// ********************************************************************************
} // end class
