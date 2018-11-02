package fantasy_adventure;

import java.io.*;

// imports

public class PoleArm extends Weapon{

//**********************************************************
// static declarations

  static ItemBasics BASICS_SPEAR;
  static ItemBasics BASICS_HALBERD;

//**********************************************************
// member declarations

//**********************************************************
// constructor
//**********************************************************

  public PoleArm(int type){
    // first set item generics
    super();
    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcPoleArmStats();
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
  static PoleArm readFromFile (int type, BufferedReader br) throws IOException{

    PoleArm thisItem = new PoleArm(type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...

    thisItem.updateDetails();

    return thisItem;

  } // end readFromFile

//**********************************************************
// static methods
//**********************************************************
  public static Item generateRandomSpear() {
    PoleArm thisItem = new PoleArm(Item.POLEARM_SPEAR);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcPoleArmStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomSpear

//**********************************************************
  public static Item generateRandomHalberd() {
    PoleArm thisItem = new PoleArm(Item.POLEARM_HALBERD);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcPoleArmStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomHalberd();

//**********************************************************
  public static Item generateRandomPoleArm() {
    switch(Roll.D2()){
      case 1:  return generateRandomSpear();
      case 2:  return generateRandomHalberd();
      default: return generateRandomSpear();
    } // end switch
  } // end generateRandomPoleArm

//**********************************************************
// private methods
//**********************************************************
  private void calcPoleArmStats(){
    // purpose of this method is to use the material and quality
    // to create the detailed stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

    // now set specifics based on type
    if (getType() == Item.POLEARM_SPEAR){
      setInventoryIcon(BASICS_SPEAR.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_SPEAR.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_SPEAR.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_SPEAR.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_SPEAR.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setDmgDieSides(
        Formulas.calcItemStat(BASICS_SPEAR.getDmgDieSides(),
                              getMaterial().getDamageFactor(),
                              getQuality().getDamageFactor()));

    } // end if straight

    else if (getType() == Item.POLEARM_HALBERD){
      setInventoryIcon(BASICS_HALBERD.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_HALBERD.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_HALBERD.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_HALBERD.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_HALBERD.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setDmgDieSides(
        Formulas.calcItemStat(BASICS_HALBERD.getDmgDieSides(),
                              getMaterial().getDamageFactor(),
                              getQuality().getDamageFactor()));

    } // end if curved


    else {} // all types accounted for.

    updateDmgFormula();
    basics.setName(""+ getQuality().getName() + " " + getMaterial().getName() + " " + Item.getTypeName(getType()));

  } // end calcPoleArmStats

//*****************************************************************
} // end class PoleArm
