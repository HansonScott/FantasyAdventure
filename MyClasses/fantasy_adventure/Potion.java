package fantasy_adventure;

//***************************************************************
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

//***************************************************************
class Potion extends Loot {

//***************************************************************
// static variables
//***************************************************************

  static ItemBasics BASICS_POTION;

//***************************************************************
// member variables
//***************************************************************


//***************************************************************
// constructor
//***************************************************************
  Potion(int type){
    super();
    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcPotionStats();
    updateDetails();
  } // end constructor

//***************************************************************
// public methods
//***************************************************************
    void writeToFile(BufferedWriter bw) throws IOException {
      // the purpose of this method is to write this item to a one-line string,
      // using tab delimiters to separate values and varables.
      super.writeToFile(bw);

      // now we can write our own details onto the file...

    } // end writeToFile

//***************************************************************
// static methods
//***************************************************************
    static Potion readFromFile (int type, BufferedReader br) throws IOException{

      Potion thisItem = new Potion(type);
      thisItem.readFromFile(br);

      // now we can read our details onto the line...

      thisItem.updateDetails();

      return thisItem;

  } // end readFromFile

//***************************************************************
  public static Item generateRandomPotion() {
    Potion thisItem = new Potion(Item.POTION);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.getQualityFromName("Good"));
    thisItem.calcPotionStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomPotion()

//***************************************************************
// private methods
//***************************************************************

  private void calcPotionStats(){
    // purpose of this method is to use the material and quality
    // to create the detailed stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

    setInventoryIcon(BASICS_POTION.getImageName());

    basics.setValue(
      Formulas.calcItemStat(BASICS_POTION.getValue(),
                            getMaterial().getValueFactor(),
                            getQuality().getValueFactor()));

    basics.setCastingFailure(
      Formulas.calcItemStat(BASICS_POTION.getCastingFailure(),
                            getMaterial().getCastFailFactor(),
                            getQuality().getCastFailFactor()));

    basics.setWeight(
      Formulas.calcItemStat(BASICS_POTION.getWeight(),
                            getMaterial().getWeightFactor(),
                            getQuality().getWeightFactor()));

    basics.setName("" + getMaterial().getName() + " " + Item.getTypeName(Item.POTION));

  } // end calcPotionStats

} // end class Potion
