package fantasy_adventure;

import java.io.*;

//********************************************************

public class Ammo extends Weapon {

//********************************************************
// static declarations

  static ItemBasics BASICS_ARROW;
  static ItemBasics BASICS_BOLT;
  static ItemBasics BASICS_BULLET;

//********************************************************
// instance declarations

   private int launcherType;

//********************************************************
// constructor
//********************************************************
//  public Ammo(){} // end default constructor

  public Ammo(int type){
    super();
    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    setQuantity(Roll.D(Constants.STACK_SIZE));
    calcAmmoStats();

    // now, set laucher type

    if      (type == Item.AMMO_ARROW)  setLauncherType(Item.LAUNCHER_BOW);
    else if (type == Item.AMMO_BOLT)   setLauncherType(Item.LAUNCHER_CROSSBOW);
    else if (type == Item.AMMO_BULLET) setLauncherType(Item.LAUNCHER_SLING);
    else{}

  } // end constructor

//********************************************************
// public methods
//********************************************************
  void setLauncherType(int i) { launcherType = i;}    // end set
  int  getLauncherType()      { return launcherType;} // end get

//******************************************************************************
  void writeToFile(BufferedWriter bw) throws IOException {
    // the purpose of this method is to write this item to a one-line string,
    // using tab delimiters to separate values and varables.
    super.writeToFile(bw);

    // now we can write our own details onto the file...
    bw.write("Ammo properites:");
    bw.write(MyTextIO.tab + "Launcher type:"     + MyTextIO.tab + getLauncherType());
    bw.newLine();
  } // end writeToFile

//******************************************************************************
  static Ammo readFromFile (int type, BufferedReader br) throws IOException{

    Ammo thisItem = new Ammo(type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...
    String thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // remove title "Ammo properties"

    thisLine = MyTextIO.trimPhrase(thisLine); // remove variable title
    thisItem.setLauncherType(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable

    thisItem.updateDetails();

    return thisItem;
  } // end readFromFile

//********************************************************
// static methods
//********************************************************
  public static Item generateRandomAmmo(){
    switch (Roll.D3()){
      case 1: {
        return generateRandomArrow();
      } // end case

      case 2: {
        return generateRandomBolt();
      } // end case

      case 3: {
        return generateRandomBullet();
      } // end case

      default: { // shouldn't be reached!
        return generateRandomArrow();
      } // end default

    } // end switch
  } // end generateRandomAmmo()

//********************************************************
  public static Item generateRandomArrow() {
    Ammo thisItem = new Ammo(Item.AMMO_ARROW);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.getQualityFromName("Good"));
    thisItem.calcAmmoStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomArrow()

//********************************************************
  public static Item generateRandomBolt() {
    Ammo thisItem = new Ammo(Item.AMMO_BOLT);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.getQualityFromName("Good"));
    thisItem.calcAmmoStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomArrow()

//********************************************************
  public static Item generateRandomBullet() {
    Ammo thisItem = new Ammo(Item.AMMO_BULLET);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.getQualityFromName("Good"));
    thisItem.calcAmmoStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomArrow()

//********************************************************
  private void calcAmmoStats(){
    // purpose of this method is to use the quality and material
    // to calculate the specific stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

    if(getType() == Item.AMMO_ARROW) {
      setInventoryIcon(BASICS_ARROW.getImageName());
      launcherType = Item.LAUNCHER_BOW;

      basics.setValue(
        Formulas.calcItemStat(BASICS_ARROW.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_ARROW.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_ARROW.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setDmgDieSides(
        Formulas.calcItemStat(BASICS_ARROW.getDmgDieSides(),
                              getMaterial().getDamageFactor(),
                              getQuality().getDamageFactor()));

    } // end arrow

    else if(getType() == Item.AMMO_BOLT) {
      setInventoryIcon(BASICS_BOLT.getImageName());
      launcherType = Item.LAUNCHER_CROSSBOW;

      basics.setValue(
        Formulas.calcItemStat(BASICS_BOLT.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_BOLT.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_BOLT.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setDmgDieSides(
        Formulas.calcItemStat(BASICS_BOLT.getDmgDieSides(),
                              getMaterial().getDamageFactor(),
                              getQuality().getDamageFactor()));

    } // end bolt

    else if(getType() == Item.AMMO_BULLET) {
      setInventoryIcon(BASICS_BULLET.getImageName());
      launcherType = Item.LAUNCHER_SLING;

      basics.setValue(
        Formulas.calcItemStat(BASICS_BULLET.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setCastingFailure(
        Formulas.calcItemStat(BASICS_BULLET.getCastingFailure(),
                              getMaterial().getCastFailFactor(),
                              getQuality().getCastFailFactor()));

      basics.setChanceToBreak(
        Formulas.calcItemStat(BASICS_BULLET.getChanceToBreak(),
                              getMaterial().getBreakFactor(),
                              getQuality().getBreakFactor()));

      basics.setDmgDieSides(
        Formulas.calcItemStat(BASICS_BULLET.getDmgDieSides(),
                              getMaterial().getDamageFactor(),
                              getQuality().getDamageFactor()));

    } // end bullet

    else {}

    updateDmgFormula();
    basics.setName("" + getMaterial().getName() + " " + Item.getTypeName(getType()));
  } // end calcAmmoStats

//*************************************************************************
} // end class Ammo
