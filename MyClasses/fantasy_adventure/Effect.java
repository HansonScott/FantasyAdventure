package fantasy_adventure;

import java.io.Serializable;

//************************************************************************
//imports


//************************************************************************

public class Effect implements Serializable{

  /* The purpose of this class is to control all the effects
   * within the game, be it magical, item, etc.
   *
   */

//************************************************************************
// static declarations
//************************************************************************

//************************************************************************
// member declarations
//************************************************************************

  private String when;   // possible: permanent, level, equip #(time)
  private String what;   // possible:
  private String bonus;  // possible:
  private int    amount; // possible: 1/0 for true/false, otherwise, int.

  boolean enabled;
  boolean active;
  boolean expired;

//************************************************************************
// constructors
//************************************************************************

  public Effect (String thisPhrase) {
    // the purpose of this constructor is to parse out the effect phrase into an effect
    active  = false;
    enabled = false;
    expired = false;

    when = MyTextIO.getNextWord(thisPhrase);
      thisPhrase = MyTextIO.trimWord(thisPhrase);
    what  = MyTextIO.getNextWord(thisPhrase);
      thisPhrase = MyTextIO.trimWord(thisPhrase);
    bonus = MyTextIO.getNextWord(thisPhrase);
      thisPhrase = MyTextIO.trimWord(thisPhrase);
    if (MyTextIO.getNextWord(thisPhrase).equalsIgnoreCase("Enable")){
      enabled = true;
    } // end if word
    else{
      amount = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    } // end else
  } // end constructor

//************************************************************************
// public methods
//************************************************************************

  public String  getWhen()            {return when;}
  public String  getWhat()            {return what;}
  public String  getBonus()           {return bonus;}
  public int     getAmount()          {return amount;}

  public void    setActive(boolean b) {active = b;}
  public boolean isActive()           {return active;}

  public void    setEnabled(boolean b) {enabled = b;}
  public boolean isEnabled()           {return enabled;}

  public void    setExpired(boolean b) {expired = b;}
  public boolean isExpired()           {return expired;}

//************************************************************************
// package methods
//************************************************************************


//************************************************************************
// private methods
//************************************************************************


//************************************************************************
} // end class Effect
//************************************************************************
