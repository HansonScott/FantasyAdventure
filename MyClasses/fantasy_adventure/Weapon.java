package fantasy_adventure;

//************************************************************************
//imports


//************************************************************************

public class Weapon extends Item{

  /* The purpose of this class is to generalize a few variables
   * of all weapons
   */

//************************************************************************
// static declarations
//************************************************************************

   static final int MELEE    = 0;
   static final int THROWN   = 1;
   static final int LAUNCHER = 2;

//************************************************************************
// member declarations
//************************************************************************

  String  dmgFormula;  // this is the way the damage displays on the details Screen
                       // in the form of : XdY + Z

//************************************************************************
// constructors
//************************************************************************

  public Weapon () {

    super(); // create the item first

  } // end constructor

//************************************************************************
// public methods
//************************************************************************

  public void updateDmgFormula(){
    // purpose of this method is to create the dmgFormula from the variables.
    dmgFormula = (""+ basics.getDmgDieNum() + "d" + basics.getDmgDieSides());
    if (basics.getDmgAddition() > 0){
      dmgFormula += (" + " + basics.getDmgAddition());
    }// end if
  } // end updateDmgFormula

  public String getDmgFormula(){
    updateDmgFormula();
    return dmgFormula;
  } // end getDmgFormula

//************************************************************************
// package methods
//************************************************************************

//************************************************************************
// private methods
//************************************************************************

//******************************************************************************
// static methods
//************************************************************************
  public static Item generateRandomWeapon(){
    // purpose of this method is to generate a random weapon,
    // which includes all weapon types and launchers

    switch (Roll.D(9)){

      case 1:{
        Item item = Launcher.generateRandomLauncher();
        if (item != null) return item;
      } // end case

      case 2:{
        Item item = Axe.generateRandomAxe();
        if (item != null) return item;
      } // end case

      case 3:{
        Item item = Blunt.generateRandomBlunt();
        if (item != null) return item;
      } // end case

      case 4:{
        Item item = Spiked.generateRandomSpiked();
        if (item != null) return item;
      } // end case

      case 5:{
        Item item = Hand2Hand.generateRandomHand2Hand();
        if (item != null) return item;
      } // end case

      case 6:{
        Item item = LongBlade.generateRandomLongBlade();
        if (item != null) return item;
      } // end case

      case 7:{
        Item item = Thrown.generateRandomThrown();
        if (item != null) return item;
      } // end case

      case 8:{
        Item item = PoleArm.generateRandomPoleArm();
        if (item != null) return item;
      } // end case

      case 9:{
        Item item = ShortBlade.generateRandomShortBlade();
        if (item != null) return item;
      } // end case

      default: {
        return LongBlade.generateRandomLongBlade();
      } // end default

    } // end switch

  } // end generate randomWeapon()

  static String getSpeedName(int i){
    if      (i == 0 ||
             i == 1)   return "Very Fast";

    else if (i == 2 ||
             i == 3)   return "Fast";

    else if (i == 4 ||
             i == 5 ||
             i == 6)   return "Normal";

    else if (i == 7 ||
             i == 8)   return "Slow";

    else if (i == 9 ||
             i == 10)  return "Very Slow";

    else return ("unknown speed: " + i);
  } // end getSpeedName

//************************************************************************
} // end class Weapon
//************************************************************************
