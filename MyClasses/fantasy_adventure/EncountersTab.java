package fantasy_adventure;

import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.*;

//************************************************************************
//imports


//************************************************************************

public class EncountersTab extends    JPanelWithBackground
                           implements ActionListener,
                                      MouseListener,
                                      KeyListener{

  /* The purpose of this class is to handle all the information and interface
   * relating to the tab showing the encounters information for the character
   */

//************************************************************************
// static declarations
//************************************************************************

  static JPanel      eLeftSide;
  static JLabel      eTitleLabel;
  static JPanel      eTitleList;
  static JScrollPane eTitlePane;

  static JPanel      eRightSide;
  static JPanel      ePicPanel;
  static JLabel      ePicLabel;

  static JTextArea   eBasicsArea;
  static JScrollPane eBasicsPane;

  static JTextArea   eDetailsArea;
  static JScrollPane eDetailsPane;

  static PlayerCharacter character;

  static JButton[]   encounters;


//************************************************************************
// member declarations
//************************************************************************



//************************************************************************
// constructors
//************************************************************************

  public EncountersTab (PlayerCharacter pc) {
    // the purpose of this constructor is to create the GUI for interfacing
    // and showing all the encounters information
    super();
    setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                  (Constants.CENTRALPANEL_HEIGHT - Constants.INSET_FOR_TAB)));

    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // setup tab ********************************************
    character = pc;

    // now setup all interface components*********************************
    // left side**********************************************************
    eLeftSide = new JPanel();
    eLeftSide.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 3) - 4,
                                             Constants.CENTRALPANEL_HEIGHT -
                                             Constants.INSET_FOR_TAB));
    eLeftSide.setBorder(BorderFactory.createEtchedBorder());

    eTitleLabel = new JLabel("<Currently selected encounter>");
    eTitleLabel.setFont(new Font(eTitleLabel.getFont().getName(), Font.ITALIC, 18));
//    eTitleLabel.setBorder(BorderFactory.createEtchedBorder());

    eTitleList = new JPanel();
    eTitleList.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 3) - 10,
                                              Constants.CENTRALPANEL_HEIGHT -
                                              Constants.INSET_FOR_TAB - 20));
    eTitleList.setBorder(BorderFactory.createEtchedBorder());

    eTitlePane = new JScrollPane (eTitleList);
    eTitlePane.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 3),
                                              Constants.CENTRALPANEL_HEIGHT -
                                              Constants.INSET_FOR_TAB - 10));
    eTitlePane.setBorder(BorderFactory.createEtchedBorder());

    eLeftSide.add(eTitleLabel);
    eLeftSide.add(eTitlePane);

    // right side**********************************************************
    eRightSide = new JPanel();
    eRightSide.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH * 2 / 3),
                                             Constants.CENTRALPANEL_HEIGHT -
                                             Constants.INSET_FOR_TAB));
    eRightSide.setBorder(BorderFactory.createEtchedBorder());

    ePicPanel = new JPanel();
    ePicPanel.setPreferredSize(new Dimension(120, 180));
//    ePicPanel.setBorder(BorderFactory.createEtchedBorder());

    ePicLabel = new JLabel(new ImageIcon(character.getPortraitPictureLarge()));
    ePicPanel.add(ePicLabel);

    eBasicsArea = MyUtils.makeTextBox(30, 10, Color.white, Color.black, null);

    eBasicsPane = new JScrollPane (eBasicsArea);
    eBasicsPane.setPreferredSize(new Dimension((int)(eRightSide.getPreferredSize().getWidth() -
                                                   ePicPanel.getPreferredSize().getWidth() - 20),
                                                   (int)(ePicPanel.getPreferredSize().getHeight())));
    eBasicsPane.setBorder(BorderFactory.createEtchedBorder());

    eDetailsArea = MyUtils.makeTextBox(30, 25, Color.white, Color.black, null);

    eDetailsPane = new JScrollPane (eDetailsArea);
    eDetailsPane.setPreferredSize(new Dimension((int)(eRightSide.getPreferredSize().getWidth()),
                                                   (int)(eRightSide.getPreferredSize().getHeight() -
                                                   ePicPanel.getPreferredSize().getHeight())));

    eRightSide.add(ePicPanel);
    eRightSide.add(eBasicsPane);
    eRightSide.add(eDetailsPane);

    // finish up**********************************************************
    add(eLeftSide);
    add(eRightSide);

    // load the data into the structure.
    updateDatabase();
  } // end constructor

//************************************************************************
// public methods
//************************************************************************
  public void actionPerformed(ActionEvent a){
    String command = a.getActionCommand();
//    Popup.createInfoPopup("action performed with command: " + command);

    processActionPeformed(command);

  } // end actionPerformed

//************************************************************************
  public void mousePressed(MouseEvent m){}

//************************************************************************
  public void mouseReleased(MouseEvent m){}

//************************************************************************
  public void mouseClicked(MouseEvent m){}

//************************************************************************
  public void keyPressed(KeyEvent k){}

//************************************************************************
  public void keyReleased(KeyEvent k){}

//************************************************************************
  public void keyTyped(KeyEvent k){}

//************************************************************************
// package methods
//************************************************************************
  void updateDatabase(){
    // purpose of this method is to read the character information
    // and load all the encounter information that the character knows.

    // for every race/type that the character knows, load it into the list.
    encounters = null;
    encounters = new JButton[character.encounters.length];

    for (int i = 0; i < character.encounters.length; i++){
      if (character.encounters[i] != null){
        encounters[i] = makeTitleButton(character.encounters[i]);
        eTitleList.add(encounters[i]);
      } // end if
    } // end for loop
    fillInfoForRace(Race.getRace(encounters[0].getText()));
  } // end updateDatabase

//************************************************************************
// private methods
//************************************************************************
  private void processActionPeformed(String command){
    // purpose of this method is to carry out the reactions of the GUI to
    // the user prssing on a button.
    // Since the only buttons on this tab are the encounter (race) buttons,
    // we want to do the same stuff regardless.

    // take the command as the race, and fill in the information for that race.
    fillInfoForRace(Race.getRace(command));

  } // end processActionPeformed()

//************************************************************************
  private void fillInfoForRace(Race r){
    // get info from race and fill the 3 parts of the screen with it...

    // 0. the label - just the title of the race we're looking at.
    eTitleLabel.setText(r.getName());

    // 1. the picture - a representative pic of the race given.
    ePicLabel.setIcon(Race.getRacePic(r));

    // 2. the basics  - the basic description of the race
    eBasicsArea.setText(r.getRaceDescription());

    // 3. the details - all the info from the race, similar to the charInfoPanel
    // go through many of the details and show any adjustments, leaving defaults blank.
    // also, make sure to show the number encountered/killed.

    // remove old data
    eDetailsArea.setText("");

    // load new data
    eDetailsArea.append("Details for race: " + r.getName() + MyTextIO.newline +
                        "---------------------------------------------------------" +
                        MyTextIO.newline);
    eDetailsArea.append(r.getAvgLifespan() + " - Average Life Span:" + MyTextIO.newline);
    eDetailsArea.append(r.getBaseHD() + " - Hit die per level:" + MyTextIO.newline);
    eDetailsArea.append(r.getBaseMD() + " - Mana per level:"    + MyTextIO.newline);

    // for most of the stats, we only want to show them if they are non-zero.
    if (r.getMeleeAdj() != 0)
      eDetailsArea.append(r.getMeleeAdj() + " - Melee Adjustment" + MyTextIO.newline);

    if (r.getRangedAdj() != 0)
      eDetailsArea.append(r.getRangedAdj() + " - Ranged Adjustment:" + MyTextIO.newline);

    if (r.getThrownAdj() != 0)
      eDetailsArea.append(r.getThrownAdj() + " - Thrown Adjustment:" + MyTextIO.newline);

    if (r.getNatDefAdj() != 0)
      eDetailsArea.append(r.getNatDefAdj() + " - Defense Adjustment:" + MyTextIO.newline);

    if (r.getStrBonus() != 0)
      eDetailsArea.append(r.getStrBonus() + " - Str. Adjustment:" + MyTextIO.newline);

    if (r.getDexBonus() != 0)
      eDetailsArea.append(r.getDexBonus() + " - Dex. Adjustment:" + MyTextIO.newline);

    if (r.getConBonus() != 0)
      eDetailsArea.append(r.getConBonus() + " - Con. Adjustment:" + MyTextIO.newline);

    if (r.getIntBonus() != 0)
      eDetailsArea.append(r.getIntBonus() + " - Int. Adjustment:" + MyTextIO.newline);

    if (r.getWisBonus() != 0)
      eDetailsArea.append(r.getWisBonus() + " - Wis. Adjustment:" + MyTextIO.newline);

    if (r.getChaBonus() != 0)
      eDetailsArea.append(r.getChaBonus() + " - Cha. Adjustment:" + MyTextIO.newline);

    if (r.isImFire())
      eDetailsArea.append("Immune to Fire" + MyTextIO.newline);

    if (r.isImEnergy())
      eDetailsArea.append("Immune to Energy" + MyTextIO.newline);

    if (r.isImAir())
      eDetailsArea.append("Immune to Air" + MyTextIO.newline);

    if (r.isImLife())
      eDetailsArea.append("Immune to Life" + MyTextIO.newline);

    if (r.isImWater())
      eDetailsArea.append("Immune to Water" + MyTextIO.newline);

    if (r.isImNature())
      eDetailsArea.append("Immune to Nature" + MyTextIO.newline);

    if (r.isImEarth())
      eDetailsArea.append("Immune to Earth" + MyTextIO.newline);

    if (r.isImDeath())
      eDetailsArea.append("Immune to Death" + MyTextIO.newline);

    if (r.isImCrushing())
      eDetailsArea.append("Immune to Crushing damage" + MyTextIO.newline);

    if (r.isImPiercing())
      eDetailsArea.append("Immune to Piercing damage" + MyTextIO.newline);

    if (r.isImSlashing())
      eDetailsArea.append("Immune to Slashing damage" + MyTextIO.newline);

    if (r.isImMelee())
      eDetailsArea.append("Immune to Melee damage" + MyTextIO.newline);

    if (r.isImRanged())
      eDetailsArea.append("Immune to Ranged damage" + MyTextIO.newline);


    if (r.getResFire() != 0)
      eDetailsArea.append(r.getResFire() + "%" + " - Fire resistance" + MyTextIO.newline);

    if (r.getResEnergy() != 0)
      eDetailsArea.append(r.getResEnergy() + "%" + " - Energy resistance" + MyTextIO.newline);

    if (r.getResAir() != 0)
      eDetailsArea.append(r.getResAir() + "%" + " - Air resistance" + MyTextIO.newline);

    if (r.getResLife() != 0)
      eDetailsArea.append(r.getResLife() + "%" + " - Life resistance" + MyTextIO.newline);

    if (r.getResWater() != 0)
      eDetailsArea.append(r.getResWater() + "%" + " - Water resistance" + MyTextIO.newline);

    if (r.getResNature() != 0)
      eDetailsArea.append(r.getResNature() + "%" + " - Nature resistance" + MyTextIO.newline);

    if (r.getResEarth() != 0)
      eDetailsArea.append(r.getResEarth() + "%" + " - Earth resistance" + MyTextIO.newline);

    if (r.getResDeath() != 0)
      eDetailsArea.append(r.getResDeath() + "%" + " - Death resistance" + MyTextIO.newline);

    if (r.getResCrushing() != 0)
      eDetailsArea.append(r.getResCrushing() + "%" + " - Crushing resistance" + MyTextIO.newline);

    if (r.getResPiercing() != 0)
      eDetailsArea.append(r.getResPiercing() + "%" + " - Piercing resistance" + MyTextIO.newline);

    if (r.getResSlashing() != 0)
      eDetailsArea.append(r.getResSlashing() + "%" + " - Slashing resistance" + MyTextIO.newline);

    if (r.getResMelee() != 0)
      eDetailsArea.append(r.getResMelee() + "%" + " - Melee resistance" + MyTextIO.newline);

    if (r.getResRanged() != 0)
      eDetailsArea.append(r.getResRanged() + "%" + " - Ranged resistance" + MyTextIO.newline);

    if (r.getSaveFire() != 0)
      eDetailsArea.append(r.getSaveFire() + " - Save vs. Fire Adjustment:" + MyTextIO.newline);

    if (r.getSaveEnergy() != 0)
      eDetailsArea.append(r.getSaveEnergy() + " - Save vs. Energy Adjustment:" + MyTextIO.newline);

    if (r.getSaveAir() != 0)
      eDetailsArea.append(r.getSaveAir() + " - Save vs. Air Adjustment:" + MyTextIO.newline);

    if (r.getSaveLife() != 0)
      eDetailsArea.append(r.getSaveLife() + " - Save vs. Life Adjustment:" + MyTextIO.newline);

    if (r.getSaveWater() != 0)
      eDetailsArea.append(r.getSaveWater() + " - Save vs. Water Adjustment:" + MyTextIO.newline);

    if (r.getSaveNature() != 0)
      eDetailsArea.append(r.getSaveNature() + " - Save vs. Nature Adjustment:" + MyTextIO.newline);

    if (r.getSaveEarth() != 0)
      eDetailsArea.append(r.getSaveEarth() + " - Save vs. Earth Adjustment:" + MyTextIO.newline);

    if (r.getSaveDeath() != 0)
      eDetailsArea.append(r.getSaveDeath() + " - Save vs. Death Adjustment:" + MyTextIO.newline);

    if (r.getSaveReflex() != 0)
      eDetailsArea.append(r.getSaveReflex() + " - Reflex Adjustment:" + MyTextIO.newline);

    if (r.getSaveFort() != 0)
      eDetailsArea.append(r.getSaveFort() + " - Fortification Adjustment:" + MyTextIO.newline);

    if (r.getSaveSocial() != 0)
      eDetailsArea.append(r.getSaveSocial() + " - Social Adjustment:" + MyTextIO.newline);

    // reset carot to top of screen, in case it has dragged us down too far.
    eDetailsArea.setCaretPosition(0);

  } // end fillInfoForRace

//************************************************************************
  private JButton makeTitleButton(String title){
    JButton button = new JButton(title);
    button.setPreferredSize(new Dimension((int)eTitleList.getPreferredSize().getWidth(), 30));
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.setActionCommand(title);
    button.addActionListener(this);
    return button;
  } // end makeTitleButton

//************************************************************************
} // end class EncountersTab
//************************************************************************
