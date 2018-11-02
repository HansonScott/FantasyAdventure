package fantasy_adventure;

//************************************************************************
//imports


//************************************************************************

public class Armor extends Item{

  /* The purpose of this class is to generalize a few variables
   * of all armor pieces
   */

//************************************************************************
// static declarations
//************************************************************************

//************************************************************************
// member declarations
//************************************************************************

//************************************************************************
// constructors
//************************************************************************

  public Armor () {
    // the purpose of this constructor is to assume certain details about
    // the item/Equipment properties before making the item.

    super();
  } // end constructor

//************************************************************************
// public methods
//************************************************************************

//************************************************************************
// package methods
//************************************************************************


//************************************************************************
// private methods
//************************************************************************

//************************************************************************
// static methods
//************************************************************************
  public static Item generateRandomArmor(){
    // purpose of this method is to generate a random piece of armor,
    // which includes helm, cuirass, shield, belt, boots, and arms.

    switch (Roll.D(7)){
      case 1:{
        Item item = Helmet.generateRandomHelmet();
        if (item != null) return item;
      } // end case

      case 2:{
        Item item = Cloak.generateRandomCloak();
        if (item != null) return item;
      } // end case

      case 3:{
        Item item = Cuirass.generateRandomCuirass();
        if (item != null) return item;
      } // end case

      case 4:{
        Item item = Arms.generateRandomArms();
        if (item != null) return item;
      } // end case

      case 5:{
        Item item = Shield.generateRandomShield();
        if (item != null) return item;
      } // end case

      case 6:{
        Item item = Belt.generateRandomBelt();
        if (item != null) return item;
      } // end case

      case 7:{
        Item item = Boots.generateRandomBoots();
        if (item != null) return item;
      } // end case

      default: {
        return Cuirass.generateRandomCuirass();
      } // end default

    } // end switch

  } // end generateRandomArmor

//************************************************************************
} // end class Armor
//************************************************************************
