package fantasy_adventure;

import java.io.*;

// imports

public class ShortBlade extends Weapon{

//**********************************************************
// static declarations

  static ItemBasics BASICS_SHORT;
  static ItemBasics BASICS_DAGGER;

//**********************************************************
// member declarations

//**********************************************************
// constructor
//**********************************************************

  public ShortBlade(){} // end default constrcutor

  public ShortBlade(int type){
    // first set item generics
    super();
    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcShortBladeStats();
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
  static ShortBlade readFromFile (int type, BufferedReader br) throws IOException{

    ShortBlade thisItem = new ShortBlade (type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...

    thisItem.updateDetails();

    return thisItem;

  } // end readFromFile

//**********************************************************
// static methods
//**********************************************************
  public static Item generateRandomDagger() {
    ShortBlade thisItem = new ShortBlade(Item.SHORT_BLADE_DAGGER);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcShortBladeStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomDagger

//**********************************************************
  public static Item generateRandomShort() {
    ShortBlade thisItem = new ShortBlade(Item.SHORT_BLADE_SHORT);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcShortBladeStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomShort();

//**********************************************************
  public static Item generateRandomShortBlade() {
    switch(Roll.D2()){
      case 1:  return generateRandomDagger();
      case 2:  return generateRandomShort();
      default: return generateRandomDagger();
    } // end switch
  } // end generateRandomShortBlade

//**********************************************************
// private methods
//**********************************************************

  private void calcShortBladeStats(){
    // purpose of this method is to use the material and quality
    // to create the detailed stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

    // now set specifics based on type
    if (getType() == Item.SHORT_BLADE_DAGGER){
      setInventoryIcon(BASICS_DAGGER.getImageName());
      basics.setValue(
        Formulas.calcItemStat(BASICS_DAGGER.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_DAGGER.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_DAGGER.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_DAGGER.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setDmgDieSides(
        Formulas.calcItemStat(BASICS_DAGGER.getDmgDieSides(),
                              getMaterial().getDamageFactor(),
                              getQuality().getDamageFactor()));

    } // end if dagger

    else if (getType() == Item.SHORT_BLADE_SHORT){
      setInventoryIcon(BASICS_SHORT.getImageName());
      basics.setValue(
        Formulas.calcItemStat(BASICS_SHORT.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_SHORT.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_SHORT.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_SHORT.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setDmgDieSides(
        Formulas.calcItemStat(BASICS_SHORT.getDmgDieSides(),
                              getMaterial().getDamageFactor(),
                              getQuality().getDamageFactor()));

    } // end if short

    else {
      Popup.createInfoPopup("ItemType unknown while creating new Short Blade."
                           + MyTextIO.newline
                           + "Returning null.");
    } // all types accounted for.

    updateDmgFormula();
    basics.setName(""+ getQuality().getName() + " " + getMaterial().getName() + " " + Item.getTypeName(getType()));
  } // end calcShortBladeStats

//*****************************************************************

} // end class ShortBlade
