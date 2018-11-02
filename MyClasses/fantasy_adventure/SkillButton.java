package fantasy_adventure;

 // supports listeners
import java.awt.*;       // supports basic GUI components
import javax.swing.*;    // supports swing GUI components

class SkillButton extends JButton{

  // The purpose this class is to create a button containing a skill and showing it's title.
  // This provides a GUI for skills that can be viewed, trained, and untrained during CGen.

  // A skillButton has four states: UNAVAILABLE, AVAILABLE, USED, and EXPIRED.
  // method setState(int) should be called to update the state while navigation the panel.

  // declare objects and variables of a skill
  Skill    thisSkill;
  JPanel   skillDescArea;
  JLabel   titleLabel;
  boolean  isHighlighted;
  int      state;        // defined by the static states listed below.

  final static int UNAVAILABLE   = 0;
  final static int AVAILABLE     = 1;
  final static int USED          = 2;
  final static int EXPIRED       = 3;
  final static int BUTTON_HEIGHT = 41;
  final static int BUTTON_WIDTH  = 212;

//constructors***********************************************************

SkillButton(Skill s){
  super();  // setup button

  thisSkill = s;

  // set button defaults
  setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
  setMargin(new Insets(2, 2, 2, 2));
  isHighlighted = false;  // default for buttons anyway, I think.

  // initialize and setup objects within the skillButton
  skillDescArea = new JPanel();
  skillDescArea.setBorder(BorderFactory.createLoweredBevelBorder());
  skillDescArea.setPreferredSize(new Dimension(BUTTON_WIDTH - 5,38));

  titleLabel = new JLabel(" " + thisSkill.getTitle() + " ");
  titleLabel.setPreferredSize(new Dimension(BUTTON_WIDTH - 10, 20));

  skillDescArea.add(titleLabel);

  add (skillDescArea);

  setState(UNAVAILABLE);   // defined by the static states listed above.
} // end constructor

//*******************************************************************
// package methods
//*******************************************************************

  boolean isHighlighted () {return isHighlighted;}
  int     getState      () {return state;}

  void setHighlighted (boolean b){
   isHighlighted = b;

   if (b == true) setBackground(Color.yellow);
  if (b == false) setBackground(Color.gray);

  } // end setHighlighted

  void setState (int s){
    state = s;
    if (state == UNAVAILABLE){
      // we need to show that the skill cannot be added at this time
      titleLabel.setForeground(Color.gray);
      titleLabel.setBackground(Color.lightGray);
      skillDescArea.setBackground(Color.lightGray);
    } // end if UNAVAILABLE

    else if (state == AVAILABLE){
      // we need to show that the skill can be added at this time
      titleLabel.setForeground(Color.white);
      titleLabel.setBackground(Color.black);
      skillDescArea.setBackground(Color.black);
    } // end if AVAILABLE

    else if (state == USED){
      // we need to show that the skill can be added, but has been used already
      titleLabel.setForeground(Color.yellow);
    } // end if USED

    else if (state == EXPIRED){
      // we need to show that the skill can no longer be added
      titleLabel.setForeground(Color.gray); // same as unavailable
    } // end if EXPIRED

    else {} // else unknown state
  } // end setState


//*******************************************************************
// private methods
//*******************************************************************


} // end class SkillButton
