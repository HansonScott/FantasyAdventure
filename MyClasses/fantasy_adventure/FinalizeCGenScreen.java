package fantasy_adventure;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FinalizeCGenScreen extends CGenScreen implements ActionListener{

  // purpose of this class is to review all the details of the character
  // and either accept them and move on, or return to the screen and
  // modify the information.

//**************************************************************************
//declarations
//**************************************************************************
  String topText = FileNames.CGEN_FINALIZE_SCREEN_INTRO;

  JPanel charPanel;

  // genetics declarations
  JLabel fullNameLabel,
         shortNameLabel,
         genderRaceLabel,
         ageLabel,
         picPanel,
  // stats declarations
         HPMPLabel,
         strLabel,
         dexLabel,
         conLabel,
         intLabel,
         wisLabel,
         chaLabel,
  // alignment declarations
         alignmentLabel;
  // training declarations
  JPanel SIPanel;
  JLabel SIPanelTitleLabel = new JLabel("<html><u>Feats:</u></html>" );

//**************************************************************************
//constructor
//**************************************************************************
  public FinalizeCGenScreen(){
  super();

  title = "Fantasy Adventure - Character Generation - Step " + "5: " + screenName + ".";
  setTitle(title);
  centralPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
  setBorder();
  topTextArea.setText(topText);

  charPanel = new JPanel();  // left side, with char data
  charPanel.setPreferredSize(new Dimension(365,430));
  charPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
  charPanel.setBorder(BorderFactory.createEtchedBorder());

// internal objects of charPanel
  picPanel        = createLabel();
    picPanel.setBorder(BorderFactory.createRaisedBevelBorder());
  fullNameLabel   = createLabel();
  shortNameLabel  = createLabel();
  genderRaceLabel = createLabel();
  ageLabel        = createLabel();
  alignmentLabel  = createLabel();
  HPMPLabel       = createLabel();
  strLabel        = createLabel();
  dexLabel        = createLabel();
  conLabel        = createLabel();
  intLabel        = createLabel();
  wisLabel        = createLabel();
  chaLabel        = createLabel();

  charPanel.add (picPanel);
  charPanel.add (fullNameLabel);
  charPanel.add (shortNameLabel);
  charPanel.add (genderRaceLabel);
  charPanel.add (ageLabel);
  charPanel.add (alignmentLabel);
  charPanel.add (HPMPLabel);
  charPanel.add (strLabel);
  charPanel.add (dexLabel);
  charPanel.add (conLabel);
  charPanel.add (intLabel);
  charPanel.add (wisLabel);
  charPanel.add (chaLabel);

//***************************
  SIPanel = new JPanel(); // right side, with skills data
  SIPanel.setPreferredSize(new Dimension(365,430));
  SIPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 15, 5));
  SIPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.black, 10),
                    BorderFactory.createLineBorder(Color.white, 2)));
  SIPanel.setBackground(Color.black);
  SIPanel.setAutoscrolls(true);

// internal objects of SIPanel
  SIPanelTitleLabel.setForeground(Color.white);
  SIPanelTitleLabel.setBackground(Color.black);
  SIPanel.add(SIPanelTitleLabel);
//**************************
  centralPanel.add(charPanel);
  centralPanel.add(SIPanel);

  } // end constructor

//**************************************************************************
public FinalizeCGenScreen(PlayerCharacter pc){

  this();
  setCharacter(pc);

} // end constructor(PC)

//**************************************************************************
// public methods
//**************************************************************************
  void setCharacter(PlayerCharacter PC){
    playerCharacter = PC;
    fullNameLabel.setText("Full Name: " + playerCharacter.getFullName());
    shortNameLabel.setText("Short Name: " + playerCharacter.getShortName());
    genderRaceLabel.setText(playerCharacter.getGender() + ", " + playerCharacter.getRace().getName());
    ageLabel.setText("Age: " + playerCharacter.getAge());
    HPMPLabel.setText("HP: " + playerCharacter.getBaseHP() + " / MP: " + playerCharacter.getBaseMP());
    alignmentLabel.setText("Alignment: " + playerCharacter.getAlignment().getName());

    strLabel.setText("Strength: "     + String.valueOf(playerCharacter.getBaseStr()) +
                     "("              + String.valueOf(playerCharacter.getBaseStrBonus() + ")"));
    dexLabel.setText("Dexterity: "    + String.valueOf(playerCharacter.getBaseDex()) +
                     "("              + String.valueOf(playerCharacter.getBaseDexBonus() + ")"));
    conLabel.setText("Constitution: " + String.valueOf(playerCharacter.getBaseCon()) +
                     "("              + String.valueOf(playerCharacter.getBaseConBonus() + ")"));
    intLabel.setText("Intelligence: " + String.valueOf(playerCharacter.getBaseInt()) +
                     "("              + String.valueOf(playerCharacter.getBaseIntBonus() + ")"));
    wisLabel.setText("Wisdom: "       + String.valueOf(playerCharacter.getBaseWis()) +
                     "("              + String.valueOf(playerCharacter.getBaseWisBonus() + ")"));
    chaLabel.setText("Charisma: "     + String.valueOf(playerCharacter.getBaseCha()) +
                     "("              + String.valueOf(playerCharacter.getBaseChaBonus() + ")"));


    picPanel.setIcon(new ImageIcon(playerCharacter.getPortraitPictureLarge()));

    picPanel.setPreferredSize(new Dimension(115, 176)); // size of pic minus border

    // load skills into panel.
    SIPanel.removeAll();
    SIPanel.add(SIPanelTitleLabel);

    for (int i = 0; i < playerCharacter.getSkills().length; i++){
      // for each skill, we want to simply add the title to the panel
      if (playerCharacter.getSkill(i) == null)continue;
      else {
        JLabel label = new JLabel(MyTextIO.trimWord(playerCharacter.getSkill(i).getTitle()));
        label.setBackground(Color.black);
        label.setForeground(Color.white);
        label.setPreferredSize(new Dimension(350, 15));
        SIPanel.add(label);
      } // end else
    } // end for loop
    this.validate();
    this.repaint();
  } // end setCharacterInfo

//**************************************************************************
  PlayerCharacter getCharacter(){return playerCharacter;} // end getCharacterInfo

  public void actionPerformed(ActionEvent e){
  String command = e.getActionCommand();
  if (command == "Refresh Character data") {
  } // end refresh

} // end ActionPerformed

  void clearInfo(){
  // since nothing is created here, we don't need to clear anything.
} // end clearInfo

//**************************************************************************
// private methods
//**************************************************************************
  private JLabel createLabel(){
    JLabel label = new JLabel();
    label.setPreferredSize(new Dimension(300,15));
    label.setHorizontalAlignment(JLabel.CENTER);

    return label;
  } // end createLabel

} // end class
