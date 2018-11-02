package fantasy_adventure;

//************************************************************************
//imports


//************************************************************************

public class Loot extends Item{

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

  public Loot () {
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
  public static Item generateRandomLoot(){
    // purpose of this method is to generate a random piece of armor,
    // which includes helm, cuirass, shield, belt, boots, and arms.

    switch (Roll.D(5)){
      case 1:{
        Item item = Ring.generateRandomRing();
        if (item != null) return item;
      } // end case

      case 2:{
        Item item = Amulet.generateRandomAmulet();
        if (item != null) return item;
      } // end case

      case 3:{
        Item item = Book.generateRandomBook();
        if (item != null) return item;
      } // end case

      case 4:{
        Item item = Artifact.generateRandomArtifact();
        if (item != null) return item;
      } // end case

      case 5:{
        Item item = Gem.generateRandomGem();
        if (item != null) return item;
      } // end case

      default: {
        return Gold.generateRandomGold();
      } // end default

    } // end switch

  } // end generateRandomArmor

//************************************************************************
} // end class Loot
//************************************************************************
