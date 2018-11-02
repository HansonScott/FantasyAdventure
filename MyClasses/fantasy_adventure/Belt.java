package fantasy_adventure;

import java.io.*;

// imports

class Belt extends Armor{

//***********************************************************
// static declarations

  static ItemBasics BASICS_LIGHT;
  static ItemBasics BASICS_HEAVY;

  static int WEIGHT_BONUS_LIGHT = 15;
  static int WEIGHT_BONUS_HEAVY = 40;

//***********************************************************
// member declarations

  private int weightBonus;

//***********************************************************
// constructor
//***********************************************************

  public Belt (){} // end constructor

  public Belt (int type){
    // first set item generics
    super();
    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcBeltStats();
    updateDetails();
  } // end constructor

//***********************************************************
// public methods
//***********************************************************

  void setWeightBonus (int i) {weightBonus = i;} // end setWeightBonus
  int  getWeightBonus ()      {return weightBonus;} // end getWeightBonus

//******************************************************************************
  void writeToFile(BufferedWriter bw) throws IOException {
    // the purpose of this method is to write this item to a one-line string,
    // using tab delimiters to separate values and varables.
    super.writeToFile(bw);

    // now we can write our own details onto the file...
    bw.write("Belt properites:");
    bw.write(MyTextIO.tab + "wightBonus:" + MyTextIO.tab + getWeightBonus());
    bw.newLine();
  } // end writeToFile

//******************************************************************************
  static Belt readFromFile (int type, BufferedReader br) throws IOException{

    Belt thisItem = new Belt(type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...
    String thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // remove title "Ammo properties"

    thisLine = MyTextIO.trimPhrase(thisLine); // remove variable title
    thisItem.setWeightBonus(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable

    thisItem.updateDetails();

    return thisItem;

  } // end readFromFile

//***********************************************************
// static methods
//***********************************************************
  public static Item generateRandomLight(){
    Belt thisItem = new Belt(Item.BELT_LIGHT);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcBeltStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomLight()

//***********************************************************
  public static Item generateRandomHeavy(){
    Belt thisItem = new Belt(Item.BELT_HEAVY);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcBeltStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomHeavy()

//***********************************************************
  public static Item generateRandomBelt() {
    switch (Roll.D2()){
      case 1: return  generateRandomLight();
      case 2: return  generateRandomHeavy();
      default: return generateRandomLight();
    } // end switch
  } // end constructor

//***********************************************************
// private methods
//***********************************************************
  private void calcBeltStats(){
    // purpose of this method is to use the material and quality
    // to create the detailed stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

    // now set specifs based on item type
    if      (getType() == Item.BELT_LIGHT){
      setWeightBonus(WEIGHT_BONUS_LIGHT);
      setInventoryIcon(BASICS_LIGHT.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_LIGHT.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_LIGHT.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_LIGHT.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_LIGHT.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setEquipmentDefense(
        Formulas.calcItemStat(BASICS_LIGHT.getEquipmentDefense(),
                              getMaterial().getDefenseFactor(),
                              getQuality().getDefenseFactor()));

    } // end if light

    else if (getType() == Item.BELT_HEAVY){
      setWeightBonus(WEIGHT_BONUS_HEAVY);
      setInventoryIcon(BASICS_HEAVY.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_HEAVY.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_HEAVY.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_HEAVY.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_HEAVY.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setEquipmentDefense(
        Formulas.calcItemStat(BASICS_HEAVY.getEquipmentDefense(),
                              getMaterial().getDefenseFactor(),
                              getQuality().getDefenseFactor()));

    } // end if light

    else {}

    basics.setName(""+ getQuality().getName() + " " + getMaterial().getName() + " " + Item.getTypeName(getType()));

  } // end calcBeltStats
//***********************************************************
} // end class
