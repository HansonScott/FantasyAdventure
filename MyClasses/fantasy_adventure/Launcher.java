package fantasy_adventure;

import java.io.*;

public class Launcher extends Item{

  static ItemBasics BASICS_BOW_SHORT;
  static ItemBasics BASICS_BOW_LONG;
  static ItemBasics BASICS_BOW_COMPOSITE;
  static ItemBasics BASICS_CROSSBOW_LIGHT;
  static ItemBasics BASICS_CROSSBOW_HEAVY;
  static ItemBasics BASICS_SLING;


//**************************************************
// member declarations
//**************************************************

  private int ammoType;

//**************************************************
// Constructor
//**************************************************

  public Launcher(int type){
    // first, setup general item details
    super();
    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcLauncherStats();
    updateDetails();
  } // end constructor

//**************************************************
// public methods
//**************************************************

  public int  getAmmoType()      {return ammoType;} // end getAmmoType
  public void setAmmoType(int t) {ammoType = t;}    // end setAmmoType

  // because we snuck in more data, we need to mask the function for our purposes
  public int getAttackBonus()    {return basics.getDmgDieSides();} // end getAttackBonus
  public int getDamageBonus()    {return basics.getDmgAddition();} // end getDamageBonus

//******************************************************************************
  void writeToFile(BufferedWriter bw) throws IOException {
    // the purpose of this method is to write this item to a one-line string,
    // using tab delimiters to separate values and varables.
    super.writeToFile(bw);

    // now we can write our own details onto the file...
    bw.write("Launcher properites:");
    bw.write(MyTextIO.tab + "Ammo Type:" + MyTextIO.tab + getAmmoType());
    bw.newLine();
  } // end writeToFile

//**************************************************
// static methods
//**************************************************

  static Launcher readFromFile (int type, BufferedReader br) throws IOException{

    Launcher thisItem = new Launcher(type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...
    String thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // remove title "Ammo properties"

    thisLine = MyTextIO.trimPhrase(thisLine); // remove variable title
    thisItem.setAmmoType(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable

    thisItem.updateDetails();

    return thisItem;

  } // end readFromFile

//**************************************************
  public static Item generateRandomBowShort() {
    Launcher thisItem = new Launcher(Item.LAUNCHER_BOW_SHORT);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcLauncherStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomBowShort

//**************************************************
  public static Item generateRandomBowLong() {
    Launcher thisItem = new Launcher(Item.LAUNCHER_BOW_LONG);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcLauncherStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomBowLong

//**************************************************
  public static Item generateRandomBowComposite() {
    Launcher thisItem = new Launcher(Item.LAUNCHER_BOW_COMPOSITE);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcLauncherStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomBowComposite

//**************************************************
  public static Item generateRandomCrossBowLight() {
    Launcher thisItem = new Launcher(Item.LAUNCHER_CROSSBOW_LIGHT);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcLauncherStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomCrossBowLight

//**************************************************
  public static Item generateRandomCrossBowHeavy() {
    Launcher thisItem = new Launcher(Item.LAUNCHER_CROSSBOW_HEAVY);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcLauncherStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomCrossBowHeavy

//**************************************************
  public static Item generateRandomSling() {
    Launcher thisItem = new Launcher(Item.LAUNCHER_SLING);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.generateRandomQuality());
    thisItem.calcLauncherStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomSling

//**************************************************
  public static Item generateRandomLauncher() {
    switch(Roll.D6()){
      case 1: return generateRandomBowShort();
      case 2: return generateRandomBowLong();
      case 3: return generateRandomBowComposite();
      case 4: return generateRandomCrossBowLight();
      case 5: return generateRandomCrossBowHeavy();
      default: return generateRandomSling();
    } // end switch
  } // end generateRandomLauncher

//**************************************************
// private methods
//**************************************************

  private void calcLauncherStats(){
    // purpose of this method is to use the material and quality
    // to create the detailed stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

    // now setup specific details
    if (getType() == Item.LAUNCHER_BOW_SHORT){
      ammoType = Item.AMMO_ARROW;
      setInventoryIcon(BASICS_BOW_SHORT.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_BOW_SHORT.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_BOW_SHORT.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_BOW_SHORT.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_BOW_SHORT.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

    }  // end bow short

    // now setup specific details

    else if (getType() == Item.LAUNCHER_BOW_LONG){
      ammoType = Item.AMMO_ARROW;
      setInventoryIcon(BASICS_BOW_LONG.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_BOW_LONG.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_BOW_LONG.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_BOW_LONG.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_BOW_LONG.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

    }  // end bow Long

    else if (getType() == Item.LAUNCHER_BOW_COMPOSITE){
      ammoType = Item.AMMO_ARROW;
      setInventoryIcon(BASICS_BOW_COMPOSITE.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_BOW_COMPOSITE.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_BOW_COMPOSITE.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_BOW_COMPOSITE.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_BOW_COMPOSITE.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

    }  // end bow Composite

    else if (getType() == Item.LAUNCHER_CROSSBOW_LIGHT){
      ammoType = Item.AMMO_BOLT;
      setInventoryIcon(BASICS_CROSSBOW_LIGHT.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_CROSSBOW_LIGHT.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_CROSSBOW_LIGHT.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_CROSSBOW_LIGHT.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_CROSSBOW_LIGHT.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

    } // end crossbow Light

    else if (getType() == Item.LAUNCHER_CROSSBOW_HEAVY){
      ammoType = Item.AMMO_BOLT;
      setInventoryIcon(BASICS_CROSSBOW_HEAVY.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_CROSSBOW_HEAVY.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_CROSSBOW_HEAVY.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_CROSSBOW_HEAVY.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_CROSSBOW_HEAVY.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

    } // end crossbow Heavy

    else if (getType() == Item.LAUNCHER_SLING){
      ammoType = Item.AMMO_BULLET;
      setInventoryIcon(BASICS_SLING.getImageName());

      basics.setValue(
        Formulas.calcItemStat(BASICS_SLING.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_SLING.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_SLING.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_SLING.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));

    } // end sling
    else{}

    basics.setName(""+ getQuality().getName() + " " + getMaterial().getName() + " " + Item.getTypeName(getType()));

  } // end calcLauncherStats

//*****************************************************************
} // end class
