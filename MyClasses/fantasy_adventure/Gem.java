package fantasy_adventure;

import java.io.*;

class Gem extends Loot {

  // static variables
    static ItemBasics BASICS_GEM;

  // member variables

  // constructors
  public Gem(int type) {
    super();
    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcGemStats();
    updateDetails();
  } // end constructor

//******************************************************************************
// public methods
//******************************************************************************
  void writeToFile(BufferedWriter bw) throws IOException {
    // the purpose of this method is to write this item to a one-line string,
    // using tab delimiters to separate values and varables.
    super.writeToFile(bw);

    // now we can write our own details onto the file...

  } // end writeToFile

//******************************************************************************
  static Gem readFromFile (int type, BufferedReader br) throws IOException{

    Gem thisItem = new Gem(type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...

    thisItem.updateDetails();

    return thisItem;

  } // end readFromFile

//******************************************************************************
// static methods
//******************************************************************************

  public static Item generateRandomGem() {
    Gem thisItem = new Gem(Item.GEM);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.getQualityFromName("Good"));
    thisItem.calcGemStats();
    thisItem.updateDetails();
    return thisItem;

  } // end generateRandomGem

//******************************************************************************
// private methods
//******************************************************************************
  private void calcGemStats(){
    // purpose of this method is to use the material and quality
    // to create the detailed stats of this item.

    if (getMaterial() == null || getQuality() == null) return;
      setInventoryIcon(FileNames.ITEM_GEM);
      basics.setValue(
        Formulas.calcItemStat(BASICS_GEM.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_GEM.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_GEM.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_GEM.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

    basics.setName(""+ getMaterial().getName() + " " + Item.getTypeName(Item.GEM));
  } // end calcGemStats

//******************************************************************************
} // end class
