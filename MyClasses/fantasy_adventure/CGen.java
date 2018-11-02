package fantasy_adventure;

/* purpose: to walk the user through the process of
 * creating a new character saving the newly created data
 * after each CGen screen.
 */

import java.awt.*;       // supports basic GUI components
import java.awt.event.*; // supports listeners
import javax.swing.*;    // supports swing GUI components

public class CGen extends JFrame implements ActionListener{

  // initialize variables and objects
  CardLayout myCardLayout;  // style of layout to display CGen screens.
  private Container cGenContent;

  GeneticsScreen     geneticsScreen;
  StatsScreen        statsScreen;
  AlignmentScreen    alignmentScreen;
  TrainingScreen     trainingScreen;
  FinalizeCGenScreen finalizeCGenScreen;

  PlayerCharacter    playerCharacter;

  // constructor
  public CGen (){

    // create and setup JFrame
    super("Fantasy Adventure - Character Generation");
    setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    cGenContent = getContentPane();
    myCardLayout = new CardLayout();
    cGenContent.setLayout(myCardLayout);  // JFrame cGen uses a CardLayout

    // create each new screen, then add to the card layout
    geneticsScreen = new GeneticsScreen();
    geneticsScreen.backButton.setActionCommand("Cancel CGen");
    geneticsScreen.nextButton.addActionListener(this);
    myCardLayout.addLayoutComponent(geneticsScreen, "Genetics");

    statsScreen = new StatsScreen();
    statsScreen.backButton.addActionListener(this);
    statsScreen.nextButton.addActionListener(this);
    myCardLayout.addLayoutComponent(statsScreen, "Statistics");

    alignmentScreen = new AlignmentScreen();
    alignmentScreen.backButton.addActionListener(this);
    alignmentScreen.nextButton.addActionListener(this);
    myCardLayout.addLayoutComponent(alignmentScreen, "Alignment");

    trainingScreen = new TrainingScreen();
    trainingScreen.backButton.addActionListener(this);
    trainingScreen.nextButton.addActionListener(this);
    myCardLayout.addLayoutComponent(trainingScreen, "Training");

    finalizeCGenScreen = new FinalizeCGenScreen();
    finalizeCGenScreen.backButton.addActionListener(this);
    finalizeCGenScreen.nextButton.setActionCommand("Finish CGen");
    myCardLayout.addLayoutComponent(finalizeCGenScreen, "Finalize");

  } // end constructor

  // public methods

public void actionPerformed(ActionEvent e){

  String command = e.getActionCommand();
//****************************************************
  if (command.equals("Back: Genetics")){
    myCardLayout.show(cGenContent, "Genetics");
    statsScreen.hide();
    }// end Next: Statistics

//****************************************************
  else if (command.equals("Back: Statistics")){
    myCardLayout.show(cGenContent, "Statistics");
    alignmentScreen.hide();
    }// end Next: Statistics

//****************************************************
  else if (command.equals("Back: Alignment")){
    myCardLayout.show(cGenContent, "Alignment");
    trainingScreen.hide();
    trainingScreen.HAS_BEEN_VIEWED = false;
    }// end Next: Statistics

//****************************************************
  else if (command.equals("Back: Training")){
    myCardLayout.show(cGenContent, "Training");
    finalizeCGenScreen.hide();
    }// end Next: Statistics

//****************************************************
  else if (command.equals("Next: Statistics")){
    playerCharacter = geneticsScreen.getCharacter();
    statsScreen.setCharacter(playerCharacter); // transfer character

    if (statsScreen.visited == true){
      statsScreen.clearInfo();
    } // end if visited

    statsScreen.visited = true;

    myCardLayout.show(cGenContent, "Statistics");
    geneticsScreen.hide();
    }// end Next: Statistics

//****************************************************
  else if (command.equals("Next: Alignment")){
    playerCharacter = statsScreen.getCharacter();
    if (playerCharacter.getBaseStr() == 0 ||
        playerCharacter.getBaseDex() == 0 ||
        playerCharacter.getBaseCon() == 0 ||
        playerCharacter.getBaseInt() == 0 ||
        playerCharacter.getBaseWis() == 0 ||
        playerCharacter.getBaseCha() == 0){
      Popup.createErrorPopup("Please finish assigning the die to your character" + MyTextIO.newline +
                             "before moving on to the Stats Screen.");
      return;
    } // end if
    alignmentScreen.setCharacter(playerCharacter); // transfer character

    if (alignmentScreen.visited == true){
      alignmentScreen.clearInfo();
    } // end if visited

    alignmentScreen.visited = true;
    myCardLayout.show(cGenContent, "Alignment");
    statsScreen.hide();
    }// end Next: Statistics

//****************************************************
  else if (command.equals("Next: Training")){
    playerCharacter = alignmentScreen.getCharacter();

    if (playerCharacter.getAlignment() == null) {
      Popup.createErrorPopup("Please choose an alignment for your character" +MyTextIO.newline +
                             "before continuing on to the Training Screen.");
      return;
    } // end if

    trainingScreen.setCharacter(playerCharacter); // transfer character

    if (trainingScreen.visited == true){
      trainingScreen.clearInfo();
    } // end if visited

    myCardLayout.show(cGenContent, "Training");
    alignmentScreen.hide();
    trainingScreen.visited = true;
    }// end Next: Statistics

//****************************************************
  else if (command.equals("Next: Finalize CGen")){
    playerCharacter = trainingScreen.getCharacter();

    if ((trainingScreen.cbSkillsRemaining > 0 && moreSkillsTrainable(trainingScreen.cbButtons))||
        (trainingScreen.ssSkillsRemaining > 0 && moreSkillsTrainable(trainingScreen.ssButtons))||
        (trainingScreen.peSkillsRemaining > 0 && moreSkillsTrainable(trainingScreen.peButtons))||
        (trainingScreen.crossoverSkillsRemaining > 0 && (moreSkillsTrainable(trainingScreen.cbButtons) &&
                                                         moreSkillsTrainable(trainingScreen.ssButtons) &&
                                                         moreSkillsTrainable(trainingScreen.peButtons)))){

      Popup.createErrorPopup("Please finish assigning skills and feats to your character" + MyTextIO.newline +
                             "before completing your character.");
      return;
    } // end if

    finalizeCGenScreen.setCharacter(playerCharacter); // transfer Character

    if (finalizeCGenScreen.visited == true){
      finalizeCGenScreen.clearInfo();
    } // end if visited

    myCardLayout.show(cGenContent, "Finalize");
    trainingScreen.hide();
    finalizeCGenScreen.visited = true;
    }// end Next: Statistics

//****************************************************
    else{
      if (Constants.debugMode == true)
        Popup.createErrorPopup("I don't know where to go with the command: " +
                               command + ", sorry.");
    } // end else

//****************************************************
  } // end actionPerformed()

void setCharacter(PlayerCharacter PC){

  playerCharacter = PC;
  geneticsScreen.setCharacter(playerCharacter);

} // end setCharacter

PlayerCharacter getCharacter(){return playerCharacter;} // end getFinishedCharacter()

//********************************************************************
  // private methods
  //********************************************************************
   private boolean moreSkillsTrainable(SkillButton[] buttonList){
     // purpose of this method is to check if there are any more skills
     // available to be trained in the given list of buttons.
     // go through the list and see if any of the buttons can be trained by the character.
     for (int i = 0; i < buttonList.length && buttonList[i] != null; i++){

       // for each button, if it can be trained, then return true, else continue.
       if (playerCharacter.hasSkill(buttonList[i].thisSkill.getTitle()) == false &&
           buttonList[i].thisSkill.canBeTrained(playerCharacter)){
//         Popup.createInfoPopup("skill still trainable: " + buttonList[i].thisSkill.getTitle());
         return true;
       } // end if

     } // end for loop
     return false;
   } // end moreSkillsTrainable()

   //********************************************************************

}  // end class CGen
