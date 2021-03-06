package fantasy_adventure;

import java.io.*;

//************************************************************************
//imports


//************************************************************************

public class HealthState implements Serializable{

  /* The purpose of this class is to give a general state of health of
   * the character.  This will be shown on the quick stats panel when
   * the character is selected.  This will also affect AI.
   */

//************************************************************************
// static declarations
//************************************************************************


//************************************************************************
// member declarations
//************************************************************************

  // priorities shown by this list as it is ordered.
  // NOTE: these should all be false for a healthy character!

  boolean dead;         // HP higher than -Con stat.
  boolean unconscious;  // HP higher than 0;
  boolean stoned;       // free from 'turn to stone' effect
  boolean paralyzed;    // free from 'paralize' effect
  boolean held;         // free from physical holding (obstacles, snags, mud, etc.)
  boolean stunned;      // have control of body/mind
  boolean dominated;    // another does not have control of the mind
  boolean charmed;      // free from enemy charisma effect
  boolean levelDrained; // operating at full levels earned
  boolean poisoned;     // free from immediate poison
  boolean diseased;     // free from long term poison or disease
  boolean vampire;      // free from a vampiric curse
  boolean cursed;       // free from item-curses or plot-curses
  boolean berzerk;      // have control of body/mind
  boolean confused;     // have control of mind
  boolean slowed;       // moving and attacking at full speed
  boolean drunk;        // free from alcoholic effects
  boolean tired;        // free from sleep depravation

  boolean regenerating; // toggle for HP accumulation

//************************************************************************
// constructors
//************************************************************************

  public HealthState () {
    // the purpose of this constructor is to setup a blank slate.
  dead         = false;
  unconscious  = false;
  stoned       = false;
  paralyzed    = false;
  held         = false;
  stunned      = false;
  dominated    = false;
  charmed      = false;
  levelDrained = false;
  poisoned     = false;
  diseased     = false;
  vampire      = false;
  cursed       = false;
  berzerk      = false;
  confused     = false;
  slowed       = false;
  drunk        = false;
  tired        = false;
  } // end constructor

//************************************************************************
// public methods
//************************************************************************

  public void writeHealthStateToFile(BufferedWriter bw) throws IOException {
    // the purpose of this method is to write details to file

    bw.write("HealthState:");

    bw.write(MyTextIO.tab + "dead:"          + MyTextIO.tab + dead);
    bw.write(MyTextIO.tab + "unconscious:"   + MyTextIO.tab + unconscious);
    bw.write(MyTextIO.tab + "stoned:"        + MyTextIO.tab + stoned);
    bw.write(MyTextIO.tab + "paralyzed:"     + MyTextIO.tab + paralyzed);
    bw.write(MyTextIO.tab + "held:"          + MyTextIO.tab + held);
    bw.write(MyTextIO.tab + "stunned:"       + MyTextIO.tab + stunned);
    bw.write(MyTextIO.tab + "dominated:"     + MyTextIO.tab + dominated);
    bw.write(MyTextIO.tab + "charmed:"       + MyTextIO.tab + charmed);
    bw.write(MyTextIO.tab + "Level Drained:" + MyTextIO.tab + levelDrained);
    bw.write(MyTextIO.tab + "poisoned:"      + MyTextIO.tab + poisoned);
    bw.write(MyTextIO.tab + "diseased:"      + MyTextIO.tab + diseased);
    bw.write(MyTextIO.tab + "vampire:"       + MyTextIO.tab + vampire);
    bw.write(MyTextIO.tab + "cursed:"        + MyTextIO.tab + cursed);
    bw.write(MyTextIO.tab + "berzerk:"       + MyTextIO.tab + berzerk);
    bw.write(MyTextIO.tab + "confused:"      + MyTextIO.tab + confused);
    bw.write(MyTextIO.tab + "slowed:"        + MyTextIO.tab + slowed);
    bw.write(MyTextIO.tab + "drunk:"         + MyTextIO.tab + drunk);
    bw.write(MyTextIO.tab + "tired:"         + MyTextIO.tab + tired);

    bw.newLine();
  } // end writeHealthStateToFile(bw)

//************************************************************************
  public void readHealthStateFromFile(BufferedReader br) throws IOException {
    // the purpose of this method is to get all th details from a file and
    // fill in the variables

    String thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      dead = true;
    else dead = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      unconscious = true;
    else unconscious = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      stoned = true;
    else stoned = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      paralyzed = true;
    else paralyzed = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      held = true;
    else held = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      stunned = true;
    else stunned = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      dominated = true;
    else dominated = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      charmed = true;
    else charmed = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      levelDrained = true;
    else levelDrained = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      poisoned = true;
    else poisoned = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      diseased = true;
    else diseased = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      vampire = true;
    else vampire = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      cursed = true;
    else cursed = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      berzerk = true;
    else berzerk = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      confused = true;
    else confused = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true")){
      slowed = true;}
    else slowed = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      drunk = true;
    else drunk = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      tired = true;
    else tired = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    //Popup.createInfoPopup("Remaining content on HealthState line:" + MyTextIO.newline + thisLine);

  } // end readHealthStateFromFile(br)

//************************************************************************
// package methods
//************************************************************************

  void setStateDead         (boolean b) {dead         = b;} // end setStateDead
  void setStateUnconscious  (boolean b) {unconscious  = b;} // end setStateUnconscious
  void setStateStoned       (boolean b) {stoned       = b;} // end setStateStoned
  void setStateParalyzed    (boolean b) {paralyzed    = b;} // end setStateParalyzed
  void setStateHeld         (boolean b) {held         = b;} // end setStateHeld
  void setStateStunned      (boolean b) {stunned      = b;} // end setStateStunned
  void setStateDominated    (boolean b) {dominated    = b;} // end setStateStunned
  void setStateCharmed      (boolean b) {charmed      = b;} // end setStateStunned
  void setStateLevelDrained (boolean b) {levelDrained = b;} // end setStateStunned
  void setStatePoisoned     (boolean b) {poisoned     = b;} // end setStatePoisoned
  void setStateDiseased     (boolean b) {diseased     = b;} // end setStateDiseased
  void setStateVampire      (boolean b) {vampire      = b;} // end setStateVampire
  void setStateCursed       (boolean b) {cursed       = b;} // end setStateCursed
  void setStateBerzerk      (boolean b) {berzerk      = b;} // end setStateBerzerk
  void setStateConfused     (boolean b) {confused     = b;} // end setStateConfused
  void setStateSlowed       (boolean b) {slowed       = b;} // end setStateConfused
  void setStateDrunk        (boolean b) {drunk        = b;} // end setStateDrunk
  void setStateTired        (boolean b) {tired        = b;} // end setStateTired

  void setRegenerating      (boolean b) {regenerating = b;} // end setRegenerating

  boolean isDead         () {return dead;}        // end getStateDead
  boolean isUnconscious  () {return unconscious;} // end getStateUnconscious
  boolean isStoned       () {return stoned;}      // end getStateStoned
  boolean isParalyzed    () {return paralyzed;}   // end getStateParalyzed
  boolean isHeld         () {return held;}        // end getStateHeld
  boolean isStunned      () {return stunned;}     // end getStateStunned
  boolean isDominated    () {return dominated;}   // end getStateDominated
  boolean isCharmed      () {return charmed;}     // end getStateCharmed
  boolean isLevelDrained () {return levelDrained;}// end getStateLevelDrained
  boolean isPoisoned     () {return poisoned;}    // end getStatePoisoned
  boolean isDiseased     () {return diseased;}    // end getStateDiseased
  boolean isVampire      () {return vampire;}     // end getStateVampire
  boolean isCursed       () {return cursed;}      // end getStateCursed
  boolean isBerzerk      () {return berzerk;}     // end getStateBerzerk
  boolean isConfused     () {return confused;}    // end getStateConfused
  boolean isSlowed       () {return slowed;}      // end getStateConfused
  boolean isDrunk        () {return drunk;}       // end getStateDrunk
  boolean isTired        () {return tired;}       // end getStateTired

  boolean getRegenerating      () {return regenerating;}// end getRegenerating

//************************************************************************
// private methods
//************************************************************************


//************************************************************************
} // end class HealthState
//************************************************************************
