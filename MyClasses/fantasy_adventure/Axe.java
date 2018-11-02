package fantasy_adventure;

import java.io.*;

// imports

public class Axe extends Weapon{

//**********************************************************
// static declarations

  static ItemBasics BASICS_SMALL;
  static ItemBasics BASICS_MEDIUM;
  static ItemBasics BASICS_LARGE;
  static ItemBasics BASICS_MASSIVE;

//**********************************************************
// member declarations

//**********************************************************
// constructor
//**********************************************************

  public Axe(){} // default constructor

  public Axe(int type){
    // first set item generics
    super();
    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcAxeStats();
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
  static Axe readFromFile (int type, BufferedReader br) throws IOException{

    Axe thisItem = new Axe(type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...
    return thisItem;

  } // end readFromFile

//**********************************************************
// static methods
//**********************************************************
  public static Item generateRandomSmall() {
    Axe thisItem = new Axe(Item.AXE_SMALL);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcAxeStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomSmall

//**********************************************************
  public static Item generateRandomMedium() {
    Axe thisItem = new Axe(Item.AXE_MEDIUM);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcAxeStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomMedium();

//**********************************************************
  public static Item generateRandomLarge() {
    Axe thisItem = new Axe(Item.AXE_LARGE);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcAxeStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomLarge()

//**********************************************************
  public static Item generateRandomMassive() {
    Axe thisItem = new Axe(Item.AXE_MASSIVE);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcAxeStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomMassive

//**********************************************************
  public static Item generateRandomAxe() {
    switch(Roll.D4()){
      case 1:  return generateRandomSmall();
      case 2:  return generateRandomMedium();
      case 3:  return generateRandomLarge();
      case 4:  return generateRandomMassive();
      default: return generateRandomSmall();
    } // end switch
  } // end generateRandomAxe

//**********************************************************
  private void calcAxeStats(){
    // purpose of this method is to use the material and quality
    // to create the detailed stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

    // now set specifics based on type
    if (getType() == Item.AXE_SMALL){
      setInventoryIcon(BASICS_SMALL.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_SMALL.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_SMALL.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_SMALL.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_SMALL.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setDmgDieSides(
        Formulas.calcItemStat(BASICS_SMALL.getDmgDieSides(),
                              getMaterial().getDamageFactor(),
                              getQuality().getDamageFactor()));

    } // end if small

    else if (getType() == Item.AXE_MEDIUM){
      setInventoryIcon(BASICS_MEDIUM.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_MEDIUM.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_MEDIUM.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_MEDIUM.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_MEDIUM.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setDmgDieSides(
        Formulas.calcItemStat(BASICS_MEDIUM.getDmgDieSides(),
                              getMaterial().getDamageFactor(),
                              getQuality().getDamageFactor()));

    } // end if medium

    else if (getType() == Item.AXE_LARGE){
      setInventoryIcon(BASICS_LARGE.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_LARGE.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_LARGE.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_LARGE.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_LARGE.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setDmgDieSides(
        Formulas.calcItemStat(BASICS_LARGE.getDmgDieSides(),
                              getMaterial().getDamageFactor(),
                              getQuality().getDamageFactor()));

    } // end if large

    else if (getType() == Item.AXE_MASSIVE){
      setInventoryIcon(BASICS_MASSIVE.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_MASSIVE.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_MASSIVE.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_MASSIVE.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_MASSIVE.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setDmgDieSides(
        Formulas.calcItemStat(BASICS_MASSIVE.getDmgDieSides(),
                              getMaterial().getDamageFactor(),
                              getQuality().getDamageFactor()));

    } // end if massive

    else {} // all types accounted for.

    updateDmgFormula();
    basics.setName(""+ getQuality().getName() + " " + getMaterial().getName() + " " + Item.getTypeName(getType()));

  } // end calcAxeStats

//**********************************************************
} // end class Axe
