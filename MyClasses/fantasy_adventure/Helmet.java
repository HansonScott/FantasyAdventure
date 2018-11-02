package fantasy_adventure;

import java.io.*;

// imports

class Helmet extends Armor {

//********************************************************
// static declarations

  static ItemBasics BASICS_HELM;
  static ItemBasics BASICS_BEAST;
  static ItemBasics BASICS_CIRCLET;
  static ItemBasics BASICS_HOOD;

  static int BLOCK_HELM    = 50;
  static int BLOCK_BEAST   = 25;
  static int BLOCK_CIRCLET = 10;
  static int BLOCK_HOOD    = 0;

//********************************************************
// member declarations

  private int blockCritical;

//********************************************************
// constructor
//********************************************************

  public Helmet (int type) {
    // first set item generics
    super();
    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcHelmetStats();
    updateDetails();
  } // end constructor

//********************************************************
//public methods
//********************************************************

  void setBlockCritical(int i) {blockCritical = i;} // end set
  int  getBlockCritical()      {return blockCritical;} // end get

//******************************************************************************
  void writeToFile(BufferedWriter bw) throws IOException {
    // the purpose of this method is to write this item to a one-line string,
    // using tab delimiters to separate values and varables.
    super.writeToFile(bw);

    // now we can write our own details onto the file...
    bw.write("Helmet properites:");
    bw.write(MyTextIO.tab + "Block:" + MyTextIO.tab + getBlockCritical());
    bw.newLine();
  } // end writeToFile

//******************************************************************************
  static Helmet readFromFile (int type, BufferedReader br) throws IOException{

    Helmet thisItem = new Helmet(type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...
    String thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // remove title "Ammo properties"

    thisLine = MyTextIO.trimPhrase(thisLine); // remove variable title
    thisItem.setBlockCritical(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable

    thisItem.updateDetails();

    return thisItem;

  } // end readFromFile

//********************************************************
//static methods
//********************************************************
  public static Item generateRandomHelm() {
    Helmet thisItem = new Helmet(Item.HELMET_NORMAL);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcHelmetStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomHelm()

//********************************************************
  public static Item generateRandomCirclet() {
    Helmet thisItem = new Helmet(Item.HELMET_CIRCLET);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcHelmetStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomCirclet

//********************************************************
  public static Item generateRandomBeast() {
    Helmet thisItem = new Helmet(Item.HELMET_BEAST);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcHelmetStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomBeast

  //********************************************************
    public static Item generateRandomHood() {
    Helmet thisItem = new Helmet(Item.HELMET_HOOD);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcHelmetStats();
    thisItem.updateDetails();
    return thisItem;
    } // end generateRandomBeast

//********************************************************
  public static Item generateRandomHelmet(){
    switch(Roll.D4()){
      case 1:  return generateRandomHelm();
      case 2:  return generateRandomBeast();
      case 3:  return generateRandomCirclet();
      case 4:  return generateRandomHood();
      default: return generateRandomHelm();
    } // end switch
  } // end generateRandomHelmet()

//********************************************************
//private methods
//********************************************************
  private void calcHelmetStats(){
    // purpose of this method is to use the material and quality
    // to create the detailed stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

    // now set specifics based on type
    if      (getType() == Item.HELMET_NORMAL){
      setBlockCritical(BLOCK_HELM);
      setInventoryIcon(BASICS_HELM.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_HELM.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_HELM.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_HELM.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_HELM.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setEquipmentDefense(
        Formulas.calcItemStat(BASICS_HELM.getEquipmentDefense(),
                              getMaterial().getDefenseFactor(),
                              getQuality().getDefenseFactor()));

    } // end normal

    else if (getType() == Item.HELMET_BEAST){
      setBlockCritical(BLOCK_BEAST);
      setInventoryIcon(BASICS_BEAST.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_BEAST.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_BEAST.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_BEAST.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_BEAST.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setEquipmentDefense(
        Formulas.calcItemStat(BASICS_BEAST.getEquipmentDefense(),
                              getMaterial().getDefenseFactor(),
                              getQuality().getDefenseFactor()));

    } // end beast

    else if (getType() == Item.HELMET_CIRCLET){
      setBlockCritical(BLOCK_CIRCLET);
      setInventoryIcon(BASICS_CIRCLET.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_CIRCLET.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_CIRCLET.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_CIRCLET.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_CIRCLET.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setEquipmentDefense(
        Formulas.calcItemStat(BASICS_CIRCLET.getEquipmentDefense(),
                              getMaterial().getDefenseFactor(),
                              getQuality().getDefenseFactor()));

    } // end circlet

    else if (getType() == Item.HELMET_HOOD){
      setBlockCritical(BLOCK_HOOD);
      setInventoryIcon(BASICS_HOOD.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_HOOD.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_HOOD.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_HOOD.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_HOOD.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

      basics.setEquipmentDefense(
        Formulas.calcItemStat(BASICS_HOOD.getEquipmentDefense(),
                              getMaterial().getDefenseFactor(),
                              getQuality().getDefenseFactor()));

    } // end normal

    else {}

    basics.setName(""+ getQuality().getName() + " " + getMaterial().getName() + " " + Item.getTypeName(getType()));

  } // end calcHelmetStats

//********************************************************
} // end class Helmet















