package fantasy_adventure;

import java.io.*;

//***************************************************************

class Scroll extends Loot {

  static ItemBasics BASICS_SCROLL;
  static int VALUE_SCROLL = 50 + Roll.D100();

//***************************************************************
  Scroll(int type){
    super();
    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcScrollStats();
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
  static Scroll readFromFile (int type, BufferedReader br) throws IOException{

    Scroll thisItem = new Scroll(type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...

    thisItem.updateDetails();

    return thisItem;

  } // end readFromFile
//***************************************************************
// static methods
//***************************************************************
  public static Item generateRandomScroll() {
    Scroll thisItem = new Scroll(Item.SCROLL);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.getQualityFromName("Good"));
    thisItem.calcScrollStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomScroll()

//***************************************************************
// private methods
//***************************************************************

  private void calcScrollStats(){
    // purpose of this method is to use the material and quality
    // to create the detailed stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

      setInventoryIcon(BASICS_SCROLL.getImageName());
      basics.setValue(
        Formulas.calcItemStat(BASICS_SCROLL.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_SCROLL.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_SCROLL.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_SCROLL.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

    basics.setName("" + getMaterial().getName() + " " + Item.getTypeName(Item.SCROLL));

  } // end calcScrollStats

} // end class Scroll
