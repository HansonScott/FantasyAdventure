package fantasy_adventure;

import java.io.*;

//***************************************************************

class Ring extends Loot {

  static ItemBasics BASICS_RING;

//***************************************************************
  Ring(int type){
    super();
    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcRingStats();
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
  static Ring readFromFile (int type, BufferedReader br) throws IOException{

    Ring thisItem = new Ring(type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...

    thisItem.updateDetails();

    return thisItem;

  } // end readFromFile

//***************************************************************
// static methods
//***************************************************************
  public static Item generateRandomRing() {
    Ring thisItem = new Ring(Item.RING);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcRingStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomRing()

//***************************************************************
// private methods
//***************************************************************
  private void calcRingStats(){
    // purpose of this method is to use the material and quality
    // to create the detailed stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

      setInventoryIcon(BASICS_RING.getImageName());
      basics.setValue(
        Formulas.calcItemStat(BASICS_RING.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_RING.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_RING.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_RING.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

    basics.setName(""+ getQuality().getName() + " " + getMaterial().getName() + " " + Item.getTypeName(Item.RING));

  } // end calcRingStats

//***************************************************************

} // end class Ring
