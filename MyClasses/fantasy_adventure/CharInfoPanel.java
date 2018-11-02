package fantasy_adventure;

//*******************************************************************
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.*;
//*******************************************************************

class CharInfoPanel extends    JPanelWithBackground
                    implements ActionListener,
                               MouseListener {

//*******************************************************************
// static declarations

  private static int CHARBUTTON_WIDTH    = (int)((Constants.CENTRALPANEL_WIDTH / 4) - 20); // for bio and AI, set dynamic?
  private static int CHARBUTTON_HEIGHT   = (int)(Constants.CENTRALPANEL_HEIGHT / 15);  // for bio and AI, set dynamic
  private static int TEXTBOX_WIDTH       = 28;  // set dynamic?
  private static int DETAILS_BOX_HEIGHT  = 35;  // # of lines showing for stats.
  private static int BASICS_BOX_HEIGHT   = 7;   // # of lines for title and attributes
  private static int COLOR_BUTTON_HEIGHT = 30;  // ColorChooserButton.buttonHeight
  private static int COLOR_BUTTON_WIDTH  = 110; // ColorChooserButton.buttonWidth

  private static int HORIZONTAL_SPACING  = (int)(Constants.CENTRALPANEL_WIDTH / 3);   // this needs to be dynamic! (used 3 times)
  private static int VERTICAL_SPACING    = (int)(Constants.CENTRALPANEL_HEIGHT / 18) - 20;
  private static int NAME_HEIGHT         = (int)(Constants.CENTRALPANEL_HEIGHT / 12);
  private static int FONT_SIZE           = (int)(Constants.CENTRALPANEL_HEIGHT / 32);
  private static int H_GAP               = (int)(Constants.CENTRALPANEL_WIDTH  / 40);
  private static int V_GAP               = (int)(Constants.CENTRALPANEL_HEIGHT / 50);

  private static String strLabelDesc = MyTextIO.createMultiLine(FileNames.STRENGTH_DESC, 50);
  private static String dexLabelDesc = MyTextIO.createMultiLine(FileNames.DEXTERITY_DESC, 50);
  private static String conLabelDesc = MyTextIO.createMultiLine(FileNames.CONSTITUTION_DESC, 50);
  private static String intLabelDesc = MyTextIO.createMultiLine(FileNames.INTELLIGENCE_DESC, 50);
  private static String wisLabelDesc = MyTextIO.createMultiLine(FileNames.WISDOM_DESC, 50);
  private static String chaLabelDesc = MyTextIO.createMultiLine(FileNames.CHARISMA_DESC, 50);

  private static String LINE = "--------------------------------------------";

//*******************************************************************
// member declarations

          JPanel             charBasicsPanel,   picPointsBox,
                             pointsBox,         HPMPPanel,
                             attDefPanel,       colorBox,
                             buttonPanel;

          JScrollPane        charDetailsPanel;

  private JPanel             basicsArea;
  private JLabel             attTitleLabel, strLabel, dexLabel, conLabel, intLabel, wisLabel, chaLabel,
                             baseLabel, strBaseLabel, dexBaseLabel, conBaseLabel, intBaseLabel, wisBaseLabel, chaBaseLabel,
                             tempLabel, strTempLabel, dexTempLabel, conTempLabel, intTempLabel, wisTempLabel, chaTempLabel,
                             bonusLabel, strBonusLabel, dexBonusLabel, conBonusLabel, intBonusLabel, wisBonusLabel, chaBonusLabel;

  private JTextArea          detailsArea;

  private JButton            BioButton,         AIButton;
          JButton            charInvButton,     partyControlButton;

  private ColorChooserButton skinColorButton,   hairColorButton,
                             majorColorButton,  minorColorButton;

  private JLabel             picBox,            nameLabel,
                             shortNameLabel,    longNameLabel,
                             HPLabel,           MPLabel,
                             attLabel,          defLabel;

          SocialObject       character;

//*******************************************************************
// constructors

  public CharInfoPanel () {
    // start with main settings
  super (FileNames.SESSION_TEXTURE,
         JPanelWithBackground.TILE);

    setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH, Constants.CENTRALPANEL_HEIGHT));
    setLayout(new BorderLayout());

    // initialize internal components *******************************

    // basics Panel *******************************
    charBasicsPanel = new JPanel();
    charBasicsPanel.setPreferredSize(new Dimension((Constants.CENTRALPANEL_WIDTH / 2) - 2,
                                                    Constants.CENTRALPANEL_HEIGHT - CHARBUTTON_HEIGHT - 2));

    charBasicsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_SPACING, VERTICAL_SPACING));

    //basics micro objects *************************
    nameLabel      = makeNameLabel();
      nameLabel.setPreferredSize(new Dimension((Constants.CENTRALPANEL_WIDTH / 2) - 2, NAME_HEIGHT));
    shortNameLabel = makeNameLabel();
    longNameLabel  = makeNameLabel();

    nameLabel.add(longNameLabel);
    nameLabel.add(shortNameLabel);

    //*************************
    picPointsBox = new JPanel();
    picPointsBox.setPreferredSize(new Dimension(330, 180));

    //*************************
    picBox = new JLabel();
    picBox.setPreferredSize(new Dimension(112, 174));
    picBox.setText("");
    picBox.setBorder(BorderFactory.createRaisedBevelBorder());
    picBox.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    //*************************
    pointsBox = new JPanel();
    pointsBox.setPreferredSize(new Dimension(160, 176));
    pointsBox.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));

    HPMPPanel = new JPanel();
    HPMPPanel.setPreferredSize(new Dimension(160, 50));
    HPMPPanel.setBorder (BorderFactory. createLineBorder(Color.black));
    HPMPPanel.setLayout (new FlowLayout(FlowLayout.CENTER, 100,5));

    HPLabel = new JLabel(" / ");
      HPLabel.setForeground(Color.red);
    MPLabel = new JLabel(" / ");
      MPLabel.setForeground(Color.blue);

    HPMPPanel.add(HPLabel);
    HPMPPanel.add(MPLabel);

    attDefPanel = new JPanel();
    attDefPanel.setPreferredSize(new Dimension(160,115));
    attDefPanel.setBorder (BorderFactory. createLineBorder(Color.black));
    attDefPanel.setLayout (new FlowLayout(FlowLayout.CENTER, 10, 5));

    attLabel = new JLabel("");
      attLabel.setForeground(Constants.GREEN_DARK);
      attLabel.setPreferredSize(new Dimension(145, 50));

    defLabel = new JLabel("");
      defLabel.setForeground(Constants.HAIR_COLOR); // brown
      defLabel.setPreferredSize(new Dimension(145, 50));

    attDefPanel.add(attLabel);
    attDefPanel.add(defLabel);

    pointsBox.add(HPMPPanel);
    pointsBox.add(attDefPanel);

    //*************************
     picPointsBox.setLayout(new FlowLayout(FlowLayout.CENTER,
                                           (int)(Math.min(H_GAP,
                                                 (picPointsBox.getPreferredSize().getWidth()
                                                 - picBox.getPreferredSize().getWidth()
                                                 - pointsBox.getPreferredSize().getWidth())
                                                 / 4)), 0));

    picPointsBox.add(picBox);
    picPointsBox.add(pointsBox);

    //*************************
    colorBox = new JPanel();
    colorBox.setPreferredSize(new Dimension(225, 80));
    colorBox.setLayout(new GridLayout(2, 2, H_GAP, V_GAP));

    skinColorButton  = new ColorChooserButton("Skin Color");
      skinColorButton.setPreferredSize(new Dimension(COLOR_BUTTON_WIDTH,COLOR_BUTTON_HEIGHT));

    hairColorButton  = new ColorChooserButton("Hair Color");
      hairColorButton.setPreferredSize(new Dimension(COLOR_BUTTON_WIDTH,COLOR_BUTTON_HEIGHT));

    majorColorButton = new ColorChooserButton("Major Color", this, character);
      majorColorButton.setPreferredSize(new Dimension(COLOR_BUTTON_WIDTH,COLOR_BUTTON_HEIGHT));

    minorColorButton = new ColorChooserButton("Minor Color", this, character);
      minorColorButton.setPreferredSize(new Dimension(COLOR_BUTTON_WIDTH,COLOR_BUTTON_HEIGHT));

    colorBox.add(skinColorButton);
    colorBox.add(hairColorButton);
    colorBox.add(majorColorButton);
    colorBox.add(minorColorButton);

    //*************************
    basicsArea = new JPanel();
    basicsArea.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 3),
                                              (int)(Constants.CENTRALPANEL_HEIGHT / 4)));
    basicsArea.setLayout(new GridLayout(7, 4, 2, 2));
    basicsArea.setBorder(BorderFactory.createLineBorder(Color.black));

    // add minor components to charBasicsArea*************************
    attTitleLabel = new JLabel("Attribute: ", SwingConstants.RIGHT);
    strLabel      = new JLabel("STR:", SwingConstants.RIGHT);
      strLabel.addMouseListener(this);
    dexLabel      = new JLabel("DEX:", SwingConstants.RIGHT);
      dexLabel.addMouseListener(this);
    conLabel      = new JLabel("CON:", SwingConstants.RIGHT);
      conLabel.addMouseListener(this);
    intLabel      = new JLabel("INT:", SwingConstants.RIGHT);
      intLabel.addMouseListener(this);
    wisLabel      = new JLabel("WIS:", SwingConstants.RIGHT);
      wisLabel.addMouseListener(this);
    chaLabel      = new JLabel("CHA:", SwingConstants.RIGHT);
      chaLabel.addMouseListener(this);

    baseLabel = new JLabel("Base", SwingConstants.CENTER);
    strBaseLabel = new JLabel("", SwingConstants.CENTER);
    dexBaseLabel = new JLabel("", SwingConstants.CENTER);
    conBaseLabel = new JLabel("", SwingConstants.CENTER);
    intBaseLabel = new JLabel("", SwingConstants.CENTER);
    wisBaseLabel = new JLabel("", SwingConstants.CENTER);
    chaBaseLabel = new JLabel("", SwingConstants.CENTER);

    tempLabel = new JLabel("Temp:", SwingConstants.CENTER);
    strTempLabel = new JLabel("", SwingConstants.CENTER);
    dexTempLabel = new JLabel("", SwingConstants.CENTER);
    conTempLabel = new JLabel("", SwingConstants.CENTER);
    intTempLabel = new JLabel("", SwingConstants.CENTER);
    wisTempLabel = new JLabel("", SwingConstants.CENTER);
    chaTempLabel = new JLabel("", SwingConstants.CENTER);

    bonusLabel = new JLabel("Bonus", SwingConstants.CENTER);
    strBonusLabel = new JLabel("", SwingConstants.CENTER);
    dexBonusLabel = new JLabel("", SwingConstants.CENTER);
    conBonusLabel = new JLabel("", SwingConstants.CENTER);
    intBonusLabel = new JLabel("", SwingConstants.CENTER);
    wisBonusLabel = new JLabel("", SwingConstants.CENTER);
    chaBonusLabel = new JLabel("", SwingConstants.CENTER);

    // add info to detailArea ************************************************
    basicsArea.add(attTitleLabel);
    basicsArea.add(baseLabel);
    basicsArea.add(tempLabel);
    basicsArea.add(bonusLabel);
    basicsArea.add(strLabel);
    basicsArea.add(strBaseLabel);
    basicsArea.add(strTempLabel);
    basicsArea.add(strBonusLabel);
    basicsArea.add(dexLabel);
    basicsArea.add(dexBaseLabel);
    basicsArea.add(dexTempLabel);
    basicsArea.add(dexBonusLabel);
    basicsArea.add(conLabel);
    basicsArea.add(conBaseLabel);
    basicsArea.add(conTempLabel);
    basicsArea.add(conBonusLabel);
    basicsArea.add(intLabel);
    basicsArea.add(intBaseLabel);
    basicsArea.add(intTempLabel);
    basicsArea.add(intBonusLabel);
    basicsArea.add(wisLabel);
    basicsArea.add(wisBaseLabel);
    basicsArea.add(wisTempLabel);
    basicsArea.add(wisBonusLabel);
    basicsArea.add(chaLabel);
    basicsArea.add(chaBaseLabel);
    basicsArea.add(chaTempLabel);
    basicsArea.add(chaBonusLabel);

    //*************************
    charBasicsPanel.add (nameLabel);
    charBasicsPanel.add (picPointsBox);
    charBasicsPanel.add (colorBox);
    charBasicsPanel.add (basicsArea);

    // initialize details Panel *******************************

    // detailsArea ************************************************************
    detailsArea = MyUtils.makeTextBox(TEXTBOX_WIDTH, DETAILS_BOX_HEIGHT, Constants.YELLOW_SOFT, Color.black, "");
    detailsArea.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));

    charDetailsPanel = new JScrollPane(detailsArea);
    charDetailsPanel.setPreferredSize(new Dimension((Constants.CENTRALPANEL_WIDTH / 2) - 20,
                                                   Constants.CENTRALPANEL_HEIGHT - CHARBUTTON_HEIGHT - 20));

    // initialize button Panel *******************************
    buttonPanel = new JPanel();
//    buttonPanel.setBorder(BorderFactory.createEtchedBorder());
    buttonPanel.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                               CHARBUTTON_HEIGHT + 2));
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
    buttonPanel.setOpaque(false);

    BioButton = makeCharButton("Character Biography");
    AIButton  = makeCharButton("AI Settings");

    charInvButton = makeCharButton("Inventory");
    charInvButton.setActionCommand("inv");

    partyControlButton = makeCharButton("Party Controls");
    partyControlButton.setActionCommand("party");

    // add minor components to buttonPanel
    buttonPanel.add(partyControlButton);
    buttonPanel.add(charInvButton);
    buttonPanel.add(BioButton);
    buttonPanel.add(AIButton);

    // add major components to screen
    add(charBasicsPanel,  BorderLayout.WEST);
    add(charDetailsPanel, BorderLayout.EAST);
    add(buttonPanel,      BorderLayout.SOUTH);

  } // end constructor

//****************************************************************************
// public methods
//****************************************************************************
  public void mousePressed (MouseEvent m){
    if (m.getSource() == strLabel){
      Popup.createInfoPopup(strLabelDesc);
    } // end strLabel

    else if (m.getSource() == dexLabel){
      Popup.createInfoPopup(dexLabelDesc);
    } // end dexLabel

    else if (m.getSource() == conLabel){
      Popup.createInfoPopup(conLabelDesc);
    } // end conLabel

    else if (m.getSource() == intLabel){
      Popup.createInfoPopup(intLabelDesc);
    } // end intLabel

    else if (m.getSource() == wisLabel){
      Popup.createInfoPopup(wisLabelDesc);
    } // end wisLabel

    else if (m.getSource() == chaLabel){
      Popup.createInfoPopup(chaLabelDesc);
    } // end chaLabel

    else {
      Popup.createWarningPopup("MousePressed unHandled: " + m.getSource().toString());
    } // end else
  } // end mousePressed

  public void mouseReleased(MouseEvent m){} // end mouseReleased

  public void mouseClicked (MouseEvent m){} // end mouseClicked

//****************************************************************************
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();

    if (command.equals("Major Color") || command.equals("Minor Color")){
      ((ColorChooserButton)(e.getSource())).showColorChooser(new JColorChooser());
    } // end if major

    else if (command.equals("Character Biography")){
      if (Constants.debugMode)
      Popup.createInfoPopup("BIO Button recognized, function coming soon.");
    } // end if BIo

    else if (command.equals("AI Settings")){
      if (Constants.debugMode)
      Popup.createInfoPopup("AI Button recognized, function coming soon.");
    } // end if AI

  } // end actionPerformed

//****************************************************************************
  public void refreshInfo(SocialObject newlySelectedCharacter){
    /* purpose of this method is to load all the info from this character
     * into the fields available.  This will also be called many times when
     * the window is newly shown, or when a different character is selected.
     */

    character = newlySelectedCharacter; // setup info for passed character.

//    Popup.createInfoPopup("refreshing CharInfoPanel");
    // add info to charPicPanel **********************************************
    // charPicPanel (JPanel) shows large image, and color buttons

    longNameLabel.setText(character.getFullName());
    shortNameLabel.setText("''" + character.getShortName() + "''");

    picBox.setIcon(new ImageIcon(character.getPortraitPictureLarge()));

    // pointsArea ************************************************************
    HPLabel.setText("HP: " + character.getCurrentHP() + " / " + character.getTempMaxHP());
    MPLabel.setText("MP: " + character.getCurrentMP() + " / " + character.getTempMaxMP());

    // att/def area
    attLabel.setText("<html>" + character.getMeleeAttack()    + " - Melee Attack"    + "<br>" +
                                character.getThrownAttack()   + " - Thrown Attack"   + "<br>" +
                                character.getLauncherAttack() + " - Launcher Attack" + "</html>");

    defLabel.setText("<html>" + character.getNaturalDefense()   + " - Natural Defense"   + "<br>" +
                                character.getEquipmentDefense() + " - Equipment Defense" + "<br>" +
                                character.getTotalDefense()     + " - Total Defense"     + "</html>");

    skinColorButton.colorSwatch.setBackground(character.getSkinColor());
    hairColorButton.colorSwatch.setBackground(character.getHairColor());
    majorColorButton.colorSwatch.setBackground(character.getMajorColor());
    minorColorButton.colorSwatch.setBackground(character.getMinorColor());

    // update basicsArea ************************************************
    updateBasicsData();

    // clear text and set Gender ********************************************
    detailsArea.setText("Gender: " + character.getGender());
    nl(detailsArea);

    // Race ****************************************************************
    detailsArea.append("Race: " + character.getRace().getName());
    nl(detailsArea);

    // alignment**************************************************************
    detailsArea.append("Alignment: " + character.getAlignment().getName());
    nl(detailsArea);

    // level, experiences,**************************************************************
    nl(detailsArea);
    detailsArea.append(LINE);

    detailsArea.append(character.createLabelofLevels());

    // health status,**************************************************************
    nl(detailsArea);
    detailsArea.append(LINE);
    nl(detailsArea);

    detailsArea.append("Health State:");
      nl(detailsArea);

    // for each health state, we need to check the boolean then print if true
    if (character.healthState.isDead() == true){
      detailsArea.append("Dead");
      nl(detailsArea);
    } // end if dead

    if (character.healthState.isUnconscious() == true){
      detailsArea.append("Unconscious");
      nl(detailsArea);
    } // end if unconscious

    if (character.healthState.isStoned() == true){
      detailsArea.append("Stoned");
      nl(detailsArea);
    } // end if stoned

    if (character.healthState.isParalyzed() == true){
      detailsArea.append("Paralyzed");
      nl(detailsArea);
    } // end if paralyzed

    if (character.healthState.isHeld() == true){
      detailsArea.append("Held");
      nl(detailsArea);
    } // end if held

    if (character.healthState.isStunned() == true){
      detailsArea.append("Stunned");
      nl(detailsArea);
    } // end if stunned

    if (character.healthState.isDominated() == true){
      detailsArea.append("Dominated");
      nl(detailsArea);
    } // end if dominated

    if (character.healthState.isCharmed() == true){
      detailsArea.append("Charmed");
      nl(detailsArea);
    } // end if charmed

    if (character.healthState.isLevelDrained() == true){
      detailsArea.append("LevelDrained");
      nl(detailsArea);
    } // end if level drained

    if (character.healthState.isPoisoned() == true){
      detailsArea.append("Poisoned");
      nl(detailsArea);
    } // end if poisioned

    if (character.healthState.isDiseased() == true){
      detailsArea.append("Diseased");
      nl(detailsArea);
    } // end if diseased

    if (character.healthState.isVampire() == true){
      detailsArea.append("Vampire");
      nl(detailsArea);
    } // end if vampire

    if (character.healthState.isCursed() == true){
      detailsArea.append("Cursed");
      nl(detailsArea);
    } // end if cursed

    if (character.healthState.isBerzerk() == true){
      detailsArea.append("Berzerk");
      nl(detailsArea);
    } // end if berzerk

    if (character.healthState.isConfused() == true){
      detailsArea.append("Confused");
      nl(detailsArea);
    } // end if confused

    if (character.healthState.isSlowed() == true){
      detailsArea.append("Slowed");
      nl(detailsArea);
    } // end if confused

    if (character.healthState.isDrunk() == true){
      detailsArea.append("Drunk");
      nl(detailsArea);
    } // end if drunk

    if (character.healthState.isTired() == true){
      detailsArea.append("Tired");
      nl(detailsArea);
    } // end if tired

    if (character.healthState.isDead()         == false  &&
        character.healthState.isUnconscious()  == false  &&
        character.healthState.isStoned()       == false  &&
        character.healthState.isParalyzed()    == false  &&
        character.healthState.isHeld()         == false  &&
        character.healthState.isStunned()      == false  &&
        character.healthState.isDominated()    == false  &&
        character.healthState.isCharmed()      == false  &&
        character.healthState.isLevelDrained() == false  &&
        character.healthState.isPoisoned()     == false  &&
        character.healthState.isDiseased()     == false  &&
        character.healthState.isVampire()      == false  &&
        character.healthState.isCursed()       == false  &&
        character.healthState.isBerzerk()      == false  &&
        character.healthState.isConfused()     == false  &&
        character.healthState.isSlowed()       == false  &&
        character.healthState.isDrunk()        == false  &&
        character.healthState.isTired()        == false){
      detailsArea.append("Healthy");
      nl(detailsArea);
    } // end if healthy

    // disposition,**************************************************************
    nl(detailsArea);
    detailsArea.append(LINE);
    nl(detailsArea);

    detailsArea.append("Disposition: " + MyTextIO.newline + character.disposition.getDisposition());
    nl(detailsArea);

    // res,****************************************************************************
    nl(detailsArea);
    detailsArea.append(LINE);
    nl(detailsArea);

    detailsArea.append("Resistances:");
    nl(detailsArea);

    if (character.getResistToFire() != 0){
      detailsArea.append(character.getResistToFire() + "%" + " - Fire");
      nl(detailsArea);
    } // end fire

    if (character.getResistToEnergy() != 0){
      detailsArea.append(character.getResistToEnergy() + "%" + " - Energy");
      nl(detailsArea);
    } // end Energy

    if (character.getResistToAir() != 0){
      detailsArea.append(character.getResistToAir() + "%" + " - Air");
      nl(detailsArea);
    } // end Air

    if (character.getResistToLife() != 0){
      detailsArea.append(character.getResistToLife() + "%" + " - Life");
      nl(detailsArea);
    } // end Life

    if (character.getResistToWater() != 0){
      detailsArea.append(character.getResistToWater() + "%" + " - Water");
      nl(detailsArea);
    } // end Water

    if (character.getResistToNature() != 0){
      detailsArea.append(character.getResistToNature() + "%" + " - Nature");
      nl(detailsArea);
    } // end Nature

    if (character.getResistToEarth() != 0){
      detailsArea.append(character.getResistToEarth() + "%" + " - Earth");
      nl(detailsArea);
    } // end Earth

    if (character.getResistToDeath() != 0){
      detailsArea.append(character.getResistToDeath() + "%" + " - Death");
      nl(detailsArea);
    } // end Death

    if (character.getResistToCrushing() != 0){
      detailsArea.append(character.getResistToCrushing() + "%" + " - Crushing");
      nl(detailsArea);
    } // end Death

    if (character.getResistToPiercing() != 0){
      detailsArea.append(character.getResistToPiercing() + "%" + " - Piercing");
      nl(detailsArea);
    } // end Death

    if (character.getResistToSlashing() != 0){
      detailsArea.append(character.getResistToSlashing() + "%" + " - Slashing");
      nl(detailsArea);
    } // end Death

    if (character.getResistToMelee() != 0){
      detailsArea.append(character.getResistToMelee() + "%" + " - Melee");
      nl(detailsArea);
    } // end Death

    if (character.getResistToRanged() != 0){
      detailsArea.append(character.getResistToRanged() + "%" + " - Ranged");
      nl(detailsArea);
    } // end Death

    if (character.getResistToFire()     == 0 &&
        character.getResistToEnergy()   == 0 &&
        character.getResistToAir()      == 0 &&
        character.getResistToLife()     == 0 &&
        character.getResistToWater()    == 0 &&
        character.getResistToNature()   == 0 &&
        character.getResistToEarth()    == 0 &&
        character.getResistToDeath()    == 0 &&
        character.getResistToCrushing() == 0 &&
        character.getResistToPiercing() == 0 &&
        character.getResistToSlashing() == 0 &&
        character.getResistToMelee()    == 0 &&
        character.getResistToRanged()   == 0){

      detailsArea.append("None");
      nl(detailsArea);
    } // end if none

    // immunities ***********************************************************
    nl(detailsArea);
    detailsArea.append(LINE);
    nl(detailsArea);

    detailsArea.append("Immunities:");
    nl(detailsArea);

    if (character.isImmuneToFire()){
      detailsArea.append("Fire");
      nl(detailsArea);
    } // end if fire

    if (character.isImmuneToEnergy()){
      detailsArea.append("Energy");
      nl(detailsArea);
    } // end if energy

    if (character.isImmuneToAir()){
      detailsArea.append("Air");
      nl(detailsArea);
    } // end if air

    if (character.isImmuneToLife()){
      detailsArea.append("Life");
      nl(detailsArea);
    } // end if life

    if (character.isImmuneToWater()){
      detailsArea.append("Water");
      nl(detailsArea);
    } // end if water

    if (character.isImmuneToNature()){
      detailsArea.append("Nature");
      nl(detailsArea);
    } // end if nature

    if (character.isImmuneToEarth()){
      detailsArea.append("Earth");
      nl(detailsArea);
    } // end if earth

    if (character.isImmuneToDeath()){
      detailsArea.append("Death");
      nl(detailsArea);
    } // end if death

    if (character.isImmuneToCrushing()){
      detailsArea.append("Crushing");
      nl(detailsArea);
    } // end if death

    if (character.isImmuneToPiercing()){
      detailsArea.append("Piercing");
      nl(detailsArea);
    } // end if death

    if (character.isImmuneToSlashing()){
      detailsArea.append("Slashing");
      nl(detailsArea);
    } // end if death

    if (character.isImmuneToMelee()){
      detailsArea.append("Melee");
      nl(detailsArea);
    } // end if death

    if (character.isImmuneToRanged()){
      detailsArea.append("Ranged");
      nl(detailsArea);
    } // end if death

    if (!character.isImmuneToFire()     &&
        !character.isImmuneToEnergy()   &&
        !character.isImmuneToAir()      &&
        !character.isImmuneToLife()     &&
        !character.isImmuneToWater()    &&
        !character.isImmuneToNature()   &&
        !character.isImmuneToEarth()    &&
        !character.isImmuneToDeath()    &&
        !character.isImmuneToCrushing() &&
        !character.isImmuneToPiercing() &&
        !character.isImmuneToSlashing() &&
        !character.isImmuneToMelee()    &&
        !character.isImmuneToRanged()   ){

      detailsArea.append("None");
      nl(detailsArea);
    } // end if none

    // Saves ***************************************************************
    nl(detailsArea);
    detailsArea.append(LINE);
    nl(detailsArea);

    detailsArea.append("Saves:");
    nl(detailsArea);

    detailsArea.append(character.getSaveFort() + " - Fortitude");
    nl(detailsArea);

    detailsArea.append(character.getSaveReflex() + " - Reflex");
    nl(detailsArea);

    detailsArea.append(character.getSaveSocial() + " - Social");
    nl(detailsArea);
    nl(detailsArea);

    detailsArea.append(character.getSaveFire() + " - Fire");
    nl(detailsArea);

    detailsArea.append(character.getSaveEnergy() + " - Energy");
    nl(detailsArea);

    detailsArea.append(character.getSaveAir() + " - Air");
    nl(detailsArea);

    detailsArea.append(character.getSaveLife() + " - Life");
    nl(detailsArea);

    detailsArea.append(character.getSaveWater() + " - Water");
    nl(detailsArea);

    detailsArea.append(character.getSaveNature() + " - Nature");
    nl(detailsArea);

    detailsArea.append(character.getSaveEarth() + " - Earth");
    nl(detailsArea);

    detailsArea.append(character.getSaveDeath() + " - Death");
    nl(detailsArea);

    // skills,**************************************************************
    nl(detailsArea);
    detailsArea.append(LINE);
    nl(detailsArea);

    detailsArea.append("" + Math.max(0, character.getCastingFailure()) + "%");

    // show a negative casting failure, if negative
    if (character.getCastingFailure() < 0)
      detailsArea.append(" (" + character.getCastingFailure() + "%)");

    detailsArea.append(" - Casting Failure");
    nl(detailsArea);

    // skills,**************************************************************
    nl(detailsArea);
    detailsArea.append(LINE);

    detailsArea.append(character.createLabelofFeats());

    // groups,**************************************************************
    nl(detailsArea);
    detailsArea.append(LINE);
    nl(detailsArea);

    detailsArea.append("Groups and Memberships:");
    nl(detailsArea);

    if (character.getNumMemberships() == 0){
      detailsArea.append("None");
      nl(detailsArea);
    } // end if
    else {
      GroupMembership memberships[] =
          character.getMemberships();

      for (int i = 0; i < memberships.length && memberships[i] != null; i++){
        // for each membership, append the "title, then rank"
        detailsArea.append(memberships[i].getGroupName() + ", " + memberships[i].getRankName());
        nl(detailsArea);
      } // end for loop
      // list all memeberships
    } // end else - have memeberships

    // Fame **************************************************************
    nl(detailsArea);
    detailsArea.append(LINE);
    nl(detailsArea);
    detailsArea.append("Fame: ");
    nl(detailsArea);
    detailsArea.append(String.valueOf(character.getFame()));
    detailsArea.append(" - " + Fame.getFameTitle(character.getFame()));
    nl(detailsArea);

    /* etc.**************************************************************
    nl(detailsArea);
    detailsArea.append(LINE);
    nl(detailsArea);

    nl(detailsArea);
    detailsArea.append("Other:");
    nl(detailsArea);
*/

    detailsArea.setCaretPosition(0);
  } // end refreshInfo
//****************************************************************************
  void updateBasicsData(){
//    Popup.createInfoPopup("Updating basics...");
   // purpose of this method is to update all the labels in the basics grid.
   strBaseLabel.setText("" + character.getBaseStr());
   dexBaseLabel.setText("" + character.getBaseDex());
   conBaseLabel.setText("" + character.getBaseCon());
   intBaseLabel.setText("" + character.getBaseInt());
   wisBaseLabel.setText("" + character.getBaseWis());
   chaBaseLabel.setText("" + character.getBaseCha());

   if      (character.getTempStr() > character.getBaseStr()) {strTempLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempStr() < character.getBaseStr()) {strTempLabel.setForeground(Color.red);}   // end if less
   else {strTempLabel.setForeground(Color.black);} // end if equal
   strTempLabel.setText("" + character.getTempStr());

   if      (character.getTempDex() > character.getBaseDex()) {dexTempLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempDex() < character.getBaseDex()) {dexTempLabel.setForeground(Color.red);}   // end if less
   else {strTempLabel.setForeground(Color.black);} // end if equal
   dexTempLabel.setText("" + character.getTempDex());

   if      (character.getTempCon() > character.getBaseCon()) {conTempLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempCon() < character.getBaseCon()) {conTempLabel.setForeground(Color.red);}   // end if less
   else {conTempLabel.setForeground(Color.black);} // end if equal
   conTempLabel.setText("" + character.getTempCon());

   if      (character.getTempInt() > character.getBaseInt()) {intTempLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempInt() < character.getBaseInt()) {intTempLabel.setForeground(Color.red);}   // end if less
   else {intTempLabel.setForeground(Color.black);} // end if equal
   intTempLabel.setText("" + character.getTempInt());

   if      (character.getTempWis() > character.getBaseWis()) {wisTempLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempWis() < character.getBaseWis()) {wisTempLabel.setForeground(Color.red);}   // end if less
   else {wisTempLabel.setForeground(Color.black);} // end if equal
   wisTempLabel.setText("" + character.getTempWis());

   if      (character.getTempCha() > character.getBaseCha()) {chaTempLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempCha() < character.getBaseCha()) {chaTempLabel.setForeground(Color.red);}   // end if less
   else {chaTempLabel.setForeground(Color.black);} // end if equal
   chaTempLabel.setText("" + character.getTempCha());

   if      (character.getTempStrBonus() > 0) {strBonusLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempStrBonus() < 0) {strBonusLabel.setForeground(Color.red);}   // end if less
   else {strBonusLabel.setForeground(Color.black);} // end if equal
   strBonusLabel.setText("" + character.getTempStrBonus());

   if      (character.getTempDexBonus() > 0) {dexBonusLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempDexBonus() < 0) {dexBonusLabel.setForeground(Color.red);}   // end if less
   else {dexBonusLabel.setForeground(Color.black);} // end if equal
   dexBonusLabel.setText("" + character.getTempDexBonus());

   if      (character.getTempConBonus() > 0) {conBonusLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempConBonus() < 0) {conBonusLabel.setForeground(Color.red);}   // end if less
   else {conBonusLabel.setForeground(Color.black);} // end if equal
   conBonusLabel.setText("" + character.getTempConBonus());

   if      (character.getTempIntBonus() > 0) {intBonusLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempIntBonus() < 0) {intBonusLabel.setForeground(Color.red);}   // end if less
   else {intBonusLabel.setForeground(Color.black);} // end if equal
   intBonusLabel.setText("" + character.getTempIntBonus());

   if      (character.getTempWisBonus() > 0) {wisBonusLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempWisBonus() < 0) {wisBonusLabel.setForeground(Color.red);}   // end if less
   else {wisBonusLabel.setForeground(Color.black);} // end if equal
   wisBonusLabel.setText("" + character.getTempWisBonus());

   if      (character.getTempChaBonus() > 0) {chaBonusLabel.setForeground(Constants.GREEN_DARK);} // end if greater
   else if (character.getTempChaBonus() < 0) {chaBonusLabel.setForeground(Color.red);}   // end if less
   else {chaBonusLabel.setForeground(Color.black);} // end if equal
   chaBonusLabel.setText("" + character.getTempChaBonus());

  } // end updateBasicsData

//****************************************************************************
// private methods
//****************************************************************************

  private JButton makeCharButton(String title){
    JButton button = new JButton(title);
    button.setPreferredSize(new Dimension(CHARBUTTON_WIDTH, CHARBUTTON_HEIGHT));
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.setActionCommand(title);
    button.addActionListener(this);
    return button;
  } // end makeCharButton

  private JLabel makeNameLabel(){
    JLabel label = new JLabel("");
    label.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH / 2 - 10, FONT_SIZE + 10));
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    label.setFont(new Font("Arial", Font.ITALIC, FONT_SIZE));
    return label;
  } // end makeNameLabel

  private void nl(JTextArea area){area.append(MyTextIO.newline);} // end nl();
//****************************************************************************
} // end class CharInfoPanel
