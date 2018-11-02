package fantasy_adventure;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// imports


//******************************************************************************
class Item extends NonLivingObject implements ActionListener,
                                              KeyListener,
                                              WindowStateListener{
  /* The purpose of this class is to encapsolate all items in the game.
   * this includes items to be collected, carried, used, bought/sold,
   * throughout the game.
   */

//******************************************************************************
// static declarations
//******************************************************************************

  // item types
  static final int GOLD               = 11;
  static final int GEM                = 12;
  static final int ARTIFACT           = 13;
  static final int BOOK               = 14;
  static final int POTION             = 21;
  static final int SCROLL             = 31;
  static final int AMMO_CLASS         = 4100;
  static final int AMMO_ARROW         = 411;
  static final int AMMO_BOLT          = 412;
  static final int AMMO_BULLET        = 413;
  static final int LAUNCHER_CLASS     = 4300;
  static final int LAUNCHER_BOW            = 431;
  static final int LAUNCHER_BOW_SHORT      = 4311;
  static final int LAUNCHER_BOW_LONG       = 4312;
  static final int LAUNCHER_BOW_COMPOSITE  = 4313;
  static final int LAUNCHER_CROSSBOW       = 432;
  static final int LAUNCHER_CROSSBOW_LIGHT = 4321;
  static final int LAUNCHER_CROSSBOW_HEAVY = 4322;
  static final int LAUNCHER_SLING          = 433;

  static final int HELMET_CLASS       = 4400;
  static final int HELMET_NORMAL      = 441;
  static final int HELMET_CIRCLET     = 442;
  static final int HELMET_BEAST       = 443;
  static final int HELMET_HOOD        = 444;
  static final int CUIRASS_CLASS      = 4500;
  static final int CUIRASS_LIGHT      = 451;
  static final int CUIRASS_MEDIUM     = 452;
  static final int CUIRASS_HEAVY      = 453;
  static final int CUIRASS_ROBE       = 454;
  static final int RING               = 46;
  static final int CLOAK              = 47;
  static final int AMULET             = 48;
  static final int SHIELD_CLASS       = 4900;
  static final int SHIELD_BUCKLER     = 491;
  static final int SHIELD_ROUND       = 492;
  static final int SHIELD_MEDIUM      = 493;
  static final int SHIELD_TOWER       = 494;
  static final int ARMS_CLASS         = 5000;
  static final int ARMS_BRACERS       = 501;
  static final int ARMS_GAUNTLETS     = 502;
  static final int ARMS_GLOVES        = 503;
  static final int BOOTS_CLASS        = 5100;
  static final int BOOTS_SOFT         = 511;
  static final int BOOTS_HARD         = 512;
  static final int BOOTS_GREAVES      = 513;
  static final int BELT_CLASS         = 5200;
  static final int BELT_LIGHT         = 521;
  static final int BELT_HEAVY         = 522;

  static final int AXE_CLASS          = 6100;
  static final int AXE_SMALL          = 61;
  static final int AXE_MEDIUM         = 62;
  static final int AXE_LARGE          = 63;
  static final int AXE_MASSIVE        = 611;
  static final int BLUNT_CLASS        = 6400;
  static final int BLUNT_CLUB         = 64;
  static final int BLUNT_HAMMER       = 65;
  static final int BLUNT_MACE         = 66;
  static final int BLUNT_MAUL         = 641;
  static final int BLUNT_STAFF        = 67;
  static final int SPIKED_CLASS       = 6800;
  static final int SPIKED_FLAIL       = 68;
  static final int SPIKED_STAR        = 69;
  static final int HAND2HAND_CLASS    = 7000;
  static final int HAND2HAND_FIST     = 700;
  static final int HAND2HAND_KNUCKLES = 70;
  static final int HAND2HAND_CLAWS    = 71;
  static final int HAND2HAND_DAGGER   = 72;
  static final int LONG_BLADE_CLASS   = 7300;
  static final int LONG_BLADE_STRAIGHT= 73;
  static final int LONG_BLADE_CURVED  = 74;
  static final int LONG_BLADE_2H      = 75;
  static final int POLEARM_CLASS      = 8000;
  static final int POLEARM_SPEAR      = 80;
  static final int POLEARM_HALBERD    = 81;
  static final int SHORT_BLADE_CLASS  = 8300;
  static final int SHORT_BLADE_SHORT  = 83;
  static final int SHORT_BLADE_DAGGER = 84;
  static final int THROWN_CLASS       = 7600;
  static final int THROWN_AXE         = 76;
  static final int THROWN_DAGGER      = 77;
  static final int THROWN_DART        = 78;
  static final int THROWN_JAVELIN     = 79;
  static final int THROWN_KNIFE       = 761;
  static final int THROWN_STONE       = 762;

  static final int detailsWidth           = 300;
  static final int detailsHeight          = 300;
  static final int detailsInset           = 30; // 15 per side.
  static final int detailsVGap            = 10;
  static final int detailsButtonRowHeight = 75;


  static Color magicalColor      = Color.blue,
               cursedColor       = Color.red,
               unidentifiedColor = Color.white,
               uniqueColor       = Color.orange,
               essentialColor    = Color.pink,
               equippedColor     = Color.yellow;

//******************************************************************************
// member declarations
//******************************************************************************
  private int         type,
                      quantity;
  private Material    material;
  private Quality     quality;

          ItemBasics  basics;

  private boolean     equipped;

  private Color       currentColor;

//private ImageIcon   inventoryIcon;  NOTE* Depreciated, use basics.getImageName() instead.

  private JScrollPane detailsPane;
  private JTextArea   detailsArea;
          JFrame      details; // this has packaage Access.
  private JPanel      buttonRow;
          JButton     closeButton, splitButton, equipButton;

//******************************************************************************
// constructors
//******************************************************************************

  public Item (){
    // the purpose of this constructor is to create the basic, default item.
    // wrapper methods should be used to fill in random stats and/or specific details

    detailsArea = MyUtils.makeTextBox((int)(Constants.SCREEN_WIDTH / 2),
                                      18,
                                      Color.white,
                                      Color.black,
                                      null);

    detailsPane = new JScrollPane(detailsArea);
    detailsPane.setPreferredSize(new Dimension(detailsWidth - detailsInset,
                                               detailsHeight - detailsInset - detailsButtonRowHeight));

    details = new JFrame("Item Details");
    details.setSize(new Dimension(detailsWidth, detailsHeight));

    details.setLocation((int)((Constants.SCREEN_WIDTH / 2)
                            - (details.getSize().getWidth() / 2)),
                        (int)((Constants.SCREEN_HEIGHT / 2)
                            - (details.getSize().getWidth() / 2)));
    details.setResizable(false);
    details.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    details.addWindowStateListener(this);

    buttonRow = new JPanel();
    buttonRow.setPreferredSize(new Dimension(detailsWidth - detailsInset,
                                             detailsButtonRowHeight));

    closeButton = makeDetailsButton("Close Details");
      closeButton.addActionListener(this);
    splitButton = makeDetailsButton("Split Stack");
      splitButton.addActionListener(this);
    equipButton = makeDetailsButton("Set Equipped");
    //equipButton.addActionListener() added by calling itemSlot

   buttonRow.add(closeButton);
   buttonRow.add(splitButton);
   buttonRow.add(equipButton);

    details.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, detailsWidth, detailsVGap));
    details.getContentPane().add(detailsPane);
    details.getContentPane().add(buttonRow);

    // since we don't know who is going to have the focus, we'll capture ALL keystrokes.
    detailsArea.addKeyListener(this);
    details.addKeyListener(this);
    detailsPane.addKeyListener(this);

    // now, set default item stats
    setQuantity(1);

    basics = new ItemBasics();

  } // end default constructor

//******************************************************************************

  public Item (Item oldItem){
  this();
  type          = oldItem.type;
  quantity      = oldItem.getQuantity();
  material      = Material.getMaterialFromName(oldItem.getMaterial().getName());
  quality       = Quality.getQualityFromName(oldItem.getQuality().getName());
  basics        = new ItemBasics(oldItem.basics);
  equipped      = oldItem.isEquipped();
  currentColor  = oldItem.getCurrentColor();
//  inventoryIcon = oldItem.getInventoryIcon();
  } // end copy constructor

//******************************************************************************
  // public/package methods
//******************************************************************************

  void setType       (int i)      {type       = i;}
  void setQuantity   (int v)      {quantity   = v;}

  void setQuality    (Quality q)  {quality    = q;}
  void setMaterial   (Material m) {material   = m;}

  void setCursed     (boolean b) {
    basics.setCursed(b);
    resetColor();
  } // end setCursed

  void setMagical    (boolean b) {
    basics.setMagic(b);
    resetColor();
  } // end setMagical

  void setIdentified (boolean b) {
    basics.setIdentified(b);
    resetColor();
  } // end setIdentified

  void setUnique     (boolean b) {
    basics.setUnique(b);
    resetColor();
  } // end set unique

  void setEssential  (boolean b) {
    basics.setEssential(b);
    resetColor();
  } // end set essential

  void setEquipped  (boolean b) {
    equipped  = b;
    resetColor();
  } // end set essential

  int      getType       () {return type;}
  int      getQuantity   () {return quantity;}
  Quality  getQuality    () {return quality;}
  Material getMaterial   () {return material;}
  boolean  isEquipped    () {return equipped;}

//  ImageIcon getInventoryIcon ()     {return inventoryIcon;}

  Color     getCurrentColor() {return currentColor;} // end getCurrentcolor

//******************************************************************************
  boolean testRequirements(LivingObject character){
    // purpose of this method is to go through the array of requirements
    // and test against the character.

    if (basics.requirements == null || basics.requirements.length == 0) return true;

    boolean result = true;

    // go through list and check each one.

    for (int i = 0; i < basics.requirements.length && result == true; i++){
      result = basics.requirements[i].test(character);
    } // end for loop

    return result;

  } // end testRequirements

//******************************************************************************
  boolean isAmmo(){
    // purpose of this method is to determine if the type of this item fits the group
    // of items under ammo (arrows, bolts, bullets)

    if (type == Item.AMMO_ARROW ||
        type == Item.AMMO_BOLT  ||
        type == Item.AMMO_BULLET){
      return true;
    } // end if
    else {return false;}
  } // end isAmmo

//******************************************************************************
  boolean isJewelry(){
    // purpose of this method is to determine if the type of this item fits the group
    // of items under jewelry (rings, amulet)

    if (type == Item.AMULET ||
        type == Item.RING){
      return true;
    } // end if
    else {return false;}
  } // end isAmmo

//******************************************************************************
  boolean isArmor(){
    // purpose of this method is to determine if the type of this item fits the group
    // of items under armor (ammy, helm, cloak, cuirass, arms, belt, boots, rings, shield)

    if(type == Item.HELMET_NORMAL  ||
       type == Item.HELMET_CIRCLET ||
       type == Item.HELMET_BEAST   ||
       type == Item.HELMET_HOOD    ||
       type == Item.CUIRASS_LIGHT  ||
       type == Item.CUIRASS_MEDIUM ||
       type == Item.CUIRASS_HEAVY  ||
       type == Item.CUIRASS_ROBE   ||
       type == Item.CLOAK          ||
       type == Item.ARMS_BRACERS   ||
       type == Item.ARMS_GAUNTLETS ||
       type == Item.ARMS_GLOVES    ||
       type == Item.BELT_LIGHT     ||
       type == Item.BELT_HEAVY     ||
       type == Item.BOOTS_SOFT     ||
       type == Item.BOOTS_HARD     ||
       type == Item.BOOTS_GREAVES  ||
       type == Item.SHIELD_BUCKLER ||
       type == Item.SHIELD_ROUND   ||
       type == Item.SHIELD_MEDIUM  ||
       type == Item.SHIELD_TOWER){
      return true;
    } // end if
    else {return false;}

  } // end isArmor

//******************************************************************************
  boolean isLauncher(){
     if (type == Item. LAUNCHER_BOW_SHORT      ||
         type == Item. LAUNCHER_BOW_LONG       ||
         type == Item. LAUNCHER_BOW_COMPOSITE  ||
         type == Item. LAUNCHER_CROSSBOW_LIGHT ||
         type == Item. LAUNCHER_CROSSBOW_HEAVY ||
         type == Item. LAUNCHER_SLING){
       return true;
     } // end if
     else return false;
   } // end isLauncher

//******************************************************************************
  boolean isWeapon(){
    // purpose of this method is to determine if the type of this item fits the group
    // of items under weapons.

    if(type == Item. AXE_SMALL           ||
       type == Item. AXE_MEDIUM          ||
       type == Item. AXE_LARGE           ||
       type == Item. AXE_MASSIVE         ||
       type == Item. BLUNT_CLUB          ||
       type == Item. BLUNT_HAMMER        ||
       type == Item. BLUNT_MACE          ||
       type == Item. BLUNT_MAUL          ||
       type == Item. BLUNT_STAFF         ||
       type == Item. HAND2HAND_FIST      ||
       type == Item. HAND2HAND_KNUCKLES  ||
       type == Item. HAND2HAND_CLAWS     ||
       type == Item. HAND2HAND_DAGGER    ||
       type == Item. LONG_BLADE_STRAIGHT ||
       type == Item. LONG_BLADE_CURVED   ||
       type == Item. LONG_BLADE_2H       ||
       type == Item. POLEARM_HALBERD     ||
       type == Item. POLEARM_SPEAR       ||
       type == Item. SHORT_BLADE_SHORT   ||
       type == Item. SHORT_BLADE_DAGGER  ||
       type == Item. SPIKED_FLAIL        ||
       type == Item. SPIKED_STAR         ||
       type == Item. THROWN_AXE          ||
       type == Item. THROWN_DAGGER       ||
       type == Item. THROWN_DART         ||
       type == Item. THROWN_JAVELIN      ||
       type == Item. THROWN_KNIFE        ||
       type == Item. THROWN_STONE){
         return true;
       } // end if

       else {
         return false;
       } // end else

  } // end isWeapon

//******************************************************************************
  boolean isEqualTo(Item otherItem){
/*    Popup.createInfoPopup("Comparing items..." + MyTextIO.newline +
        this.getType()               + " vs " + otherItem.getType()               + " = " + String.valueOf(this.getType()               == otherItem.getType())               + MyTextIO.newline +
        this.getQuality().getName()  + " vs " + otherItem.getQuality().getName()  + " = " + String.valueOf(this.getQuality().getName().equalsIgnoreCase(otherItem.getQuality().getName()))            + MyTextIO.newline +
        this.getMaterial().getName() + " vs " + otherItem.getMaterial().getName() + " = " + String.valueOf(this.getMaterial().getName().equalsIgnoreCase(otherItem.getMaterial().getName()))           + MyTextIO.newline +
        this.basics.getDmgDieSides() + " vs " + otherItem.basics.getDmgDieSides() + " = " + String.valueOf(this.basics.getDmgDieSides() == otherItem.basics.getDmgDieSides()) + MyTextIO.newline +
        this.basics.getWeight()      + " vs " + otherItem.basics.getWeight()      + " = " + String.valueOf(this.basics.getWeight()      == otherItem.basics.getWeight())      + MyTextIO.newline +
        this.basics.getValue()       + " vs " + otherItem.basics.getValue()       + " = " + String.valueOf(this.basics.getValue()       == otherItem.basics.getValue())       + MyTextIO.newline +
        this.basics.getName()        + " vs " + otherItem.basics.getName()        + " = " + String.valueOf(this.basics.getName() .equals(otherItem.basics.getName())));
*/    // since this is mostly used for stacking purposes, check important basics: type, dmg, weight, value.
    if (this.getType()               == otherItem.getType()               &&
        this.getQuality().getName().equalsIgnoreCase(otherItem.getQuality().getName())   &&
        this.getMaterial().getName().equalsIgnoreCase(otherItem.getMaterial().getName()) &&
        this.basics.getDmgDieSides() == otherItem.basics.getDmgDieSides() &&
        this.basics.getWeight()      == otherItem.basics.getWeight()      &&
        this.basics.getValue()       == otherItem.basics.getValue()       &&
        this.basics.getName().equals(otherItem.basics.getName())
      ) return true;
    else return false;
  } // end isEqualTo

//******************************************************************************
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();

    if (command.equals("Close Details")){
      details.hide();
      detailsArea.setCaretPosition(0); // go back to the top of the detailsArea for next view.
    } // end if close

    else if (command.equals("Split Stack") && getQuantity() > 1){
      Popup.createInfoPopup("Sorry, but indirect item splitting coming soon..." + MyTextIO.newline +
                            "Use [ctrl] + [right click] on item to split stack directly.");
    //handleSplitStack(item);
    } // end if split


  } // end actionPerformed

//******************************************************************************
  public void windowStateChanged(WindowEvent e){
    // The purpose of this method is to keep an eye on the details panel,
    // so if the window state changes, and it is now no longer in focus...
    if (e.getNewState() == WindowEvent.WINDOW_LOST_FOCUS){
      // ...Then hide the window, so there are not multiple windows open.
      details.hide();
//      detailsArea.setCaretPositon(0);
    } // end if lost focus


  } // end windowStateChanged

//******************************************************************************
  void setInventoryIcon (String s) {
    // assumes s to be the correct filename for the imageIcon
    basics.setImageName(s);
  } // end setInventoryIcon

//******************************************************************************
  void resetColor(){
    // purpose of this method is to clear the item borderColor and set to correct state.

    if      (equipped)    currentColor = equippedColor;
    else if (basics.isCursed())      currentColor = cursedColor;
    else if (basics.isUnique())      currentColor = uniqueColor;
    else if (basics.isEssential())   currentColor = essentialColor;
    else if (basics.isMagic())       currentColor = magicalColor;
    else if (!basics.isIdentified()) currentColor = unidentifiedColor;
    else {currentColor = null;}

  } // end resetColor

//******************************************************************************
  void writeToFile(BufferedWriter bw) throws IOException {
    // the purpose of this method is to write this item to a one-line string,
    // using tab delimiters to separate values and varables.

    // first, start by calling parent.
    super.writeToFile(bw);

    // now we can write our own details onto the file...
    bw.write("Item properites:");
    bw.write(MyTextIO.tab + "type:"     + MyTextIO.tab + getType());
    bw.write(MyTextIO.tab + "quantity:" + MyTextIO.tab + getQuantity());
    bw.write(MyTextIO.tab + "material:" + MyTextIO.tab + getMaterial().getName());
    bw.write(MyTextIO.tab + "quality:"  + MyTextIO.tab + getQuality().getName());
    bw.write(MyTextIO.tab + "equipped:" + MyTextIO.tab + isEquipped());

    // now go through all the basics info:
    bw.write(MyTextIO.tab + "name:"        + MyTextIO.tab + basics.getName());
    bw.write(MyTextIO.tab + "imageName:"   + MyTextIO.tab + basics.getImageName());
    bw.write(MyTextIO.tab + "value:"       + MyTextIO.tab + basics.getValue());
    bw.write(MyTextIO.tab + "weight:"      + MyTextIO.tab + basics.getWeight());
    bw.write(MyTextIO.tab + "CastFail:"    + MyTextIO.tab + basics.getCastingFailure());
    bw.write(MyTextIO.tab + "Break%:"      + MyTextIO.tab + basics.getChanceToBreak());
    bw.write(MyTextIO.tab + "Stackable:"   + MyTextIO.tab + basics.isStackable());
    bw.write(MyTextIO.tab + "Cursed:"      + MyTextIO.tab + basics.isCursed());
    bw.write(MyTextIO.tab + "Magical:"     + MyTextIO.tab + basics.isMagic());
    bw.write(MyTextIO.tab + "Identified:"  + MyTextIO.tab + basics.isIdentified());
    bw.write(MyTextIO.tab + "Uniuqe:"      + MyTextIO.tab + basics.isUnique());
    bw.write(MyTextIO.tab + "Essential:"   + MyTextIO.tab + basics.isEssential());
    bw.write(MyTextIO.tab + "EuipmentDef:" + MyTextIO.tab + basics.getEquipmentDefense());
    bw.write(MyTextIO.tab + "MaxDexBonus:" + MyTextIO.tab + basics.getMaxDexBonus());

 // now add in specific variables for weapons
    bw.write(MyTextIO.tab + "twoHanded:"   + MyTextIO.tab + basics.isTwoHanded());
    bw.write(MyTextIO.tab + "Speed:"       + MyTextIO.tab + basics.getSpeed());
    bw.write(MyTextIO.tab + "crushing:"    + MyTextIO.tab + basics.doesCrushing());
    bw.write(MyTextIO.tab + "slashing:"    + MyTextIO.tab + basics.doesSlashing());
    bw.write(MyTextIO.tab + "piercing:"    + MyTextIO.tab + basics.doesPiercing());
    bw.write(MyTextIO.tab + "DmgDieNum:"   + MyTextIO.tab + basics.getDmgDieNum());
    bw.write(MyTextIO.tab + "DmgDieSides:" + MyTextIO.tab + basics.getDmgDieSides());
    bw.write(MyTextIO.tab + "DmgAddition:" + MyTextIO.tab + basics.getDmgAddition());
    bw.write(MyTextIO.tab + "GovernProf:"  + MyTextIO.tab + basics.getGoverningProficiency());

  // now add in specific variables for ammo
  // ? - something about chance to lose ammo?

  // now add in specific variables for launcher
  // ? - something about which item type used for ammo?

  // now add in specific variables for other specific items.
  // ? - artifact details?

  // now add all possible requirements

    bw.write(MyTextIO.tab + "NumReqs:"     + MyTextIO.tab + basics.getReqNum());

    for (int i = 0; i < basics.requirements.length; i++ ){
      // for each requirement, read it to a phrase.
      bw.write(MyTextIO.tab + basics.requirements[i].getDescription());
    } // end for loop

    bw.newLine();

  } // end writeToFile

//******************************************************************************
  void readFromFile (BufferedReader br) throws IOException{

    // read nonLiving first.
    super.readFromFile(br);

    // now we can read our details onto the line...
    String thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove title "Item properties"

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    setType(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    setQuantity(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    setMaterial(Material.getMaterialFromName(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    setQuality(Quality.getQualityFromName(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    setEquipped(Boolean.valueOf(MyTextIO.getNextPhrase(thisLine)).booleanValue()); // capture variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    // now go through all the basics info:
    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setName(MyTextIO.getNextPhrase(thisLine)); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);              // remove variable title
    basics.setImageName(MyTextIO.getNextPhrase(thisLine)); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);              // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setValue(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setWeight(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setCastingFailure(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setChanceToBreak(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setStackable(Boolean.valueOf(MyTextIO.getNextPhrase(thisLine)).booleanValue()); // capture variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setCursed(Boolean.valueOf(MyTextIO.getNextPhrase(thisLine)).booleanValue()); // capture variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setMagic(Boolean.valueOf(MyTextIO.getNextPhrase(thisLine)).booleanValue()); // capture variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setIdentified(Boolean.valueOf(MyTextIO.getNextPhrase(thisLine)).booleanValue()); // capture variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setUnique(Boolean.valueOf(MyTextIO.getNextPhrase(thisLine)).booleanValue()); // capture variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setEssential(Boolean.valueOf(MyTextIO.getNextPhrase(thisLine)).booleanValue()); // capture variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setEquipmentDefense(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setMaxDexBonus(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    // now add in specific variables for weapons

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setTwoHanded(Boolean.valueOf(MyTextIO.getNextPhrase(thisLine)).booleanValue()); // capture variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setSpeed(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setCrushing(Boolean.valueOf(MyTextIO.getNextPhrase(thisLine)).booleanValue()); // capture variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setSlashing(Boolean.valueOf(MyTextIO.getNextPhrase(thisLine)).booleanValue()); // capture variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setPiercing(Boolean.valueOf(MyTextIO.getNextPhrase(thisLine)).booleanValue()); // capture variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setDmgDieNum(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setDmgDieSides(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setDmgAddition(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setGoverningProficiency(MyTextIO.getNextPhrase(thisLine)); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

  // now add all possible requirements

    thisLine = MyTextIO.trimPhrase(thisLine);         // remove variable title
    basics.setReqNum(Integer.parseInt(MyTextIO.getNextPhrase(thisLine))); // captures variable
    thisLine = MyTextIO.trimPhrase(thisLine);         // removes variable

    basics.requirements = new Requirement[basics.getReqNum()];

    for (int i = 0; i < basics.requirements.length; i++ ){
      // for each requirement, read it to a phrase.
      basics.requirements[i] = new Requirement(MyTextIO.getNextPhrase(thisLine));
      thisLine = MyTextIO.trimPhrase(thisLine);
    } // end for loop

   // now add all possible effects!

  } // end readItemFromFile

//******************************************************************************
  void updateDetails(){
    // purpose of this method is to fill the detailsArea with the information
    // of the item to be viewed by the user.
    detailsArea.setText(""); // clears anything prior.

    // now set all information from this item

    detailsArea.append(basics.getName()    + MyTextIO.newline + MyTextIO.newline);

    detailsArea.append("Item Details:"     + MyTextIO.newline);

    if (quantity != 1){
    detailsArea.append("Quantity:"         + MyTextIO.tab +
                       quantity            + MyTextIO.newline);}

    detailsArea.append("Value:"            + MyTextIO.tab + "$" +
                       basics.getValue() + " ");

    // make sure to show that this value represent per item, if there is a stack.
    if (quantity > 1) detailsArea.append("(each)");
    detailsArea.append(MyTextIO.newline);

    detailsArea.append("Weight:"           + MyTextIO.tab +
                       basics.getWeight()  + " ");

    // make sure to show that this weight represent per item, if there is a stack.
    if (quantity > 1) detailsArea.append("(each)");
    detailsArea.append(MyTextIO.newline);

    detailsArea.append("Break%:"           + MyTextIO.tab + "(" +
                       basics.getChanceToBreak()  + "/100) %" + MyTextIO.newline);

    detailsArea.append("Cast Fail:"        + MyTextIO.tab +
                       basics.getCastingFailure() + MyTextIO.newline);

    if (basics.isStackable() == true)
    detailsArea.append("Stackable"         + MyTextIO.newline);

    if (basics.isEssential() == true)
      detailsArea.append("Essential"       + MyTextIO.newline);

    if (basics.isIdentified() == false)
      detailsArea.append("Unidentified"    + MyTextIO.newline);

    else if (basics.isCursed() == true)
      detailsArea.append("Cursed"          + MyTextIO.newline);

    else if (basics.isMagic() == true)
      detailsArea.append("Magical"         + MyTextIO.newline);

    else if (basics.isUnique() == true)
      detailsArea.append("unique"          + MyTextIO.newline);

    else{}

    // Item specifics ***************************

    //Armor specifics ***************************
    if (this.isArmor()){
      detailsArea.append(MyTextIO.newline);
      detailsArea.append("Armor-specific details:" + MyTextIO.newline);
      detailsArea.append("" + basics.getEquipmentDefense() + " = Equipment Defense" + MyTextIO.newline);
      detailsArea.append("" + basics.getMaxDexBonus() + " = Maximum Dex Bonus" + MyTextIO.newline);
      detailsArea.append("Governing Proficiency: " + basics.getGoverningProficiency() + MyTextIO.newline);

      //***************************
      if (this.getType() == Item.BOOTS_SOFT ||
          this.getType() == Item.BOOTS_HARD ||
          this.getType() == Item.BOOTS_GREAVES){
        detailsArea.append(MyTextIO.newline);
        detailsArea.append("Boots-specific details:" + MyTextIO.newline);
        detailsArea.append("" + ((Boots)(this)).getMaxSpeed() + " = Maximum Speed" + MyTextIO.newline);
      } // end if boots

      //***************************
      else if (this.getType() == Item.BELT_LIGHT ||
               this.getType() == Item.BELT_HEAVY){
        detailsArea.append(MyTextIO.newline);
        detailsArea.append( "Belt-specific details:" + MyTextIO.newline);
        detailsArea.append("" + ((Belt)(this)).getWeightBonus() + " = Maximum Weight Bonus" + MyTextIO.newline);
      } // end if belt

      //***************************
      else if (this.getType() == Item.ARMS_BRACERS   ||
               this.getType() == Item.ARMS_GAUNTLETS ||
               this.getType() == Item.ARMS_GLOVES){
        detailsArea.append(MyTextIO.newline);
        detailsArea.append( "Arms-specific details:" + MyTextIO.newline);
        detailsArea.append("" + ((Arms)(this)).getMeleeBonus()    + " = Melee Bonus"    + MyTextIO.newline);
        detailsArea.append("" + ((Arms)(this)).getThrownBonus()   + " = Thrown Bonus"   + MyTextIO.newline);
        detailsArea.append("" + ((Arms)(this)).getLauncherBonus() + " = Launcher Bonus" + MyTextIO.newline);
      } // end if arms

      //***************************
      else if (this.getType() == Item.SHIELD_BUCKLER ||
               this.getType() == Item.SHIELD_ROUND   ||
               this.getType() == Item.SHIELD_MEDIUM  ||
               this.getType() == Item.SHIELD_TOWER){
        detailsArea.append(MyTextIO.newline);
        detailsArea.append( "Shield-specific details:" + MyTextIO.newline);
        detailsArea.append("" + ((Shield)(this)).getBlockChance() + "% = Chance to block missle" + MyTextIO.newline);
      } // end if shield

      //***************************
      else if (this.getType() == Item.HELMET_NORMAL ||
               this.getType() == Item.HELMET_BEAST  ||
               this.getType() == Item.HELMET_HOOD   ||
               this.getType() == Item.HELMET_CIRCLET){
        detailsArea.append(MyTextIO.newline);
        detailsArea.append( "Helmet-specific details:" + MyTextIO.newline);
        detailsArea.append("" + ((Helmet)(this)).getBlockCritical() + "% = Chance to block critical hit" + MyTextIO.newline);
      } // end if helmet

    } // end if armor

    //Weapon specifics ***************************
    if (this.isWeapon()){
    // then append weapon-specific information
      detailsArea.append(MyTextIO.newline);
      detailsArea.append("Weapon-specific details:" + MyTextIO.newline);
      detailsArea.append("Governing Proficiency: " + basics.getGoverningProficiency() + MyTextIO.newline);

      //***************************
      detailsArea.append("Weapon speed: " + Weapon.getSpeedName(basics.getSpeed()) + MyTextIO.newline);
      if (basics.doesCrushing()){
        detailsArea.append("Crushing Damage" + MyTextIO.newline);
      } // end crushing
      if (basics.doesSlashing()){
        detailsArea.append("Slashing Damage" + MyTextIO.newline);
      } // end crushing
      if (basics.doesPiercing()){
        detailsArea.append("Piercing Damage" + MyTextIO.newline);
      } // end crushing

      //***************************
      if (basics.isTwoHanded()){
        detailsArea.append("2-Handed" + MyTextIO.newline);
      } // end if 2-handed
      else {
        detailsArea.append("1-Handed" + MyTextIO.newline);
      } // end else - not 2-handed

      //***************************
      detailsArea.append("Damage: " + ((Weapon)(this)).getDmgFormula() + MyTextIO.newline);

    } // end if weapon

    //Launcher specifics ***************************
    if (this.isLauncher()){

      detailsArea.append(MyTextIO.newline);
      detailsArea.append("Launcher-specific Details:" + MyTextIO.newline);
      detailsArea.append("Governing Proficiency: " + basics.getGoverningProficiency() + MyTextIO.newline);
      detailsArea.append("Ammo Type: " + Item.getTypeName(((Launcher)(this)).getAmmoType()) + MyTextIO.newline);

      if (((Launcher)this).getAttackBonus() != 0){
        detailsArea.append("Attack Bonus: " + ((Launcher)this).getAttackBonus() + MyTextIO.newline);
      } // end if attack bonus

      if (((Launcher)this).getDamageBonus() != 0){
        detailsArea.append("Damage Bonus: " + ((Launcher)this).getDamageBonus() + MyTextIO.newline);
      } // end if attack bonus

    } // end if launcher

    // Ammo specifics ***************************
    if (this.isAmmo()){
      detailsArea.append(MyTextIO.newline);
      detailsArea.append("Ammo-specific details:" + MyTextIO.newline);
      detailsArea.append("Launcher Type: " + Item.getTypeName(((Ammo)(this)).getLauncherType()) + MyTextIO.newline);
      detailsArea.append("Damage: "       + ((Ammo)(this)).getDmgFormula() + MyTextIO.newline);
    } // end if weapon

    // requirements **************************
    if (basics.requirements != null && basics.requirements.length != 0){

      detailsArea.append(MyTextIO.newline + "Requirements:" + MyTextIO.newline);

      for (int i = 0; i < basics.requirements.length; i++){
        detailsArea.append(basics.requirements[i].getDescription() + MyTextIO.newline);
      } // end for loop
    } // end if reqs

    detailsArea.setCaretPosition(0); // go back to the top of the detailsArea
  } // end updateDetails

//******************************************************************************
  int getItemClass(){
    // purpose of this method is to get the item type and return the 'class' of
    // item that it belongs to.   Because there are groups, this needs to go through
    // all the types and return the group number.

    if      (this.getType() == Item.GOLD)     {return Item.GOLD;}     // end if gold
    else if (this.getType() == Item.GEM)      {return Item.GEM;}      // end if gem
    else if (this.getType() == Item.ARTIFACT) {return Item.ARTIFACT;} // end if artifact
    else if (this.getType() == Item.BOOK)     {return Item.BOOK;}     // end if book
    else if (this.getType() == Item.POTION)   {return Item.POTION;}   // end if potion
    else if (this.getType() == Item.SCROLL)   {return Item.SCROLL;}   // end if scroll
    else if (this.getType() == Item.RING)     {return Item.RING;}     // end if RING
    else if (this.getType() == Item.CLOAK)    {return Item.CLOAK;}    // end if CLOAK
    else if (this.getType() == Item.AMULET)   {return Item.AMULET;}   // end if AMULET

    else if (this.getType() == Item.AMMO_ARROW  ||
             this.getType() == Item.AMMO_BOLT   ||
             this.getType() == Item.AMMO_BULLET )
    {return Item.AMMO_CLASS;}    // end if ammo

    else if (this.getType() == Item.LAUNCHER_BOW_SHORT      ||
             this.getType() == Item.LAUNCHER_BOW_LONG       ||
             this.getType() == Item.LAUNCHER_BOW_COMPOSITE  ||
             this.getType() == Item.LAUNCHER_CROSSBOW_LIGHT ||
             this.getType() == Item.LAUNCHER_CROSSBOW_HEAVY ||
             this.getType() == Item.LAUNCHER_SLING    )
    {return Item.LAUNCHER_CLASS;}    // end if launcher

    else if (this.getType() == Item.HELMET_NORMAL  ||
             this.getType() == Item.HELMET_CIRCLET ||
             this.getType() == Item.HELMET_BEAST   ||
             this.getType() == Item.HELMET_HOOD    )
    {return Item.HELMET_CLASS;}    // end if helmet

    else if (this.getType() == Item.CUIRASS_LIGHT  ||
             this.getType() == Item.CUIRASS_MEDIUM ||
             this.getType() == Item.CUIRASS_HEAVY  ||
             this.getType() == Item.CUIRASS_ROBE    )
    {return Item.CUIRASS_CLASS;}    // end if cuirass

    else if (this.getType() == Item.SHIELD_BUCKLER ||
             this.getType() == Item.SHIELD_ROUND   ||
             this.getType() == Item.SHIELD_MEDIUM  ||
             this.getType() == Item.SHIELD_TOWER    )
    {return Item.SHIELD_CLASS;}    // end if shield

    else if (this.getType() == Item.ARMS_BRACERS   ||
             this.getType() == Item.ARMS_GAUNTLETS ||
             this.getType() == Item.ARMS_GLOVES     )
    {return Item.ARMS_CLASS;}    // end if arms

    else if (this.getType() == Item.BOOTS_SOFT    ||
             this.getType() == Item.BOOTS_HARD    ||
             this.getType() == Item.BOOTS_GREAVES  )
    {return Item.BOOTS_CLASS;}    // end if boots

    else if (this.getType() == Item.BELT_LIGHT    ||
             this.getType() == Item.BELT_HEAVY     )
    {return Item.BELT_CLASS;}    // end if belt

    else if (this.getType() == Item.AXE_SMALL   ||
             this.getType() == Item.AXE_MEDIUM  ||
             this.getType() == Item.AXE_LARGE   ||
             this.getType() == Item.AXE_MASSIVE  )
    {return Item.AXE_CLASS;}    // end if axe

    else if (this.getType() == Item.BLUNT_CLUB   ||
             this.getType() == Item.BLUNT_HAMMER ||
             this.getType() == Item.BLUNT_MACE   ||
             this.getType() == Item.BLUNT_MAUL   ||
             this.getType() == Item.BLUNT_STAFF  )
    {return Item.BLUNT_CLASS;}    // end if blunt

    else if (this.getType() == Item.SPIKED_FLAIL ||
             this.getType() == Item.SPIKED_STAR   )
    {return Item.SPIKED_CLASS;}    // end if spiked

    else if (this.getType() == Item.HAND2HAND_FIST     ||
             this.getType() == Item.HAND2HAND_KNUCKLES ||
             this.getType() == Item.HAND2HAND_CLAWS    ||
             this.getType() == Item.HAND2HAND_DAGGER    )
    {return Item.HAND2HAND_CLASS;}    // end if hand2hand

    else if (this.getType() == Item.LONG_BLADE_STRAIGHT ||
             this.getType() == Item.LONG_BLADE_CURVED   ||
             this.getType() == Item.LONG_BLADE_2H        )
    {return Item.LONG_BLADE_CLASS;}    // end if long blade

    else if (this.getType() == Item.POLEARM_SPEAR   ||
             this.getType() == Item.POLEARM_HALBERD  )
    {return Item.POLEARM_CLASS;}    // end if poleArm

    else if (this.getType() == Item.SHORT_BLADE_SHORT  ||
             this.getType() == Item.SHORT_BLADE_DAGGER  )
    {return Item.SHORT_BLADE_CLASS;}    // end if short blade

    else if (this.getType() == Item.THROWN_AXE     ||
             this.getType() == Item.THROWN_DAGGER  ||
             this.getType() == Item.THROWN_DART    ||
             this.getType() == Item.THROWN_JAVELIN ||
             this.getType() == Item.THROWN_KNIFE   ||
             this.getType() == Item.THROWN_STONE    )
    {return Item.THROWN_CLASS;}    // end if thrown

    else{
      Popup.createInfoPopup("ItemType not found in a group: " + this.getTypeName(this.getType()));
      return 0;
    } // end else

  } // end getItemClass

//******************************************************************************
  static String getTypeName(int type){
    // purpose of this method is to return the word equivalent of the type number.

    if      (type == GOLD)                {return "Gold";}
    else if (type == ARTIFACT)            {return "Artifact";}
    else if (type == GEM)                 {return "Gem";}
    else if (type == BOOK)                {return "Book";}
    else if (type == POTION)              {return "Potion";}
    else if (type == SCROLL)              {return "Scroll";}
    else if (type == AMMO_CLASS)          {return "Ammo";}
    else if (type == AMMO_ARROW)          {return "Arrow";}
    else if (type == AMMO_BOLT)           {return "Bolt";}
    else if (type == AMMO_BULLET)         {return "Bullet";}
    else if (type == LAUNCHER_CLASS)      {return "Launcher";}
    else if (type == LAUNCHER_BOW)        {return "Bow";}
    else if (type == LAUNCHER_BOW_SHORT)  {return "Short Bow";}
    else if (type == LAUNCHER_BOW_LONG)   {return "Long Bow";}
    else if (type == LAUNCHER_BOW_COMPOSITE)  {return "Composite Bow";}
    else if (type == LAUNCHER_CROSSBOW)       {return "Crossbow";}
    else if (type == LAUNCHER_CROSSBOW_LIGHT) {return "Light Crossbow";}
    else if (type == LAUNCHER_CROSSBOW_HEAVY) {return "Heavy Crossbow";}
    else if (type == LAUNCHER_SLING)      {return "Sling";}
    else if (type == HELMET_CLASS)        {return "Helmet";}
    else if (type == HELMET_NORMAL)       {return "Normal Helmet";}
    else if (type == HELMET_CIRCLET)      {return "Circlet";}
    else if (type == HELMET_BEAST)        {return "Beast Head";}
    else if (type == HELMET_HOOD)         {return "Hood";}
    else if (type == CUIRASS_CLASS)       {return "Armor";}
    else if (type == CUIRASS_LIGHT)       {return "Light Armor";}
    else if (type == CUIRASS_MEDIUM)      {return "Medium Armor";}
    else if (type == CUIRASS_HEAVY)       {return "Heavy Armor";}
    else if (type == CUIRASS_ROBE)        {return "Robe";}
    else if (type == RING)                {return "Ring";}
    else if (type == CLOAK)               {return "Cloak";}
    else if (type == AMULET)              {return "Amulet";}
    else if (type == SHIELD_CLASS)        {return "Shield";}
    else if (type == SHIELD_BUCKLER)      {return "Buckler";}
    else if (type == SHIELD_ROUND)        {return "Round Shield";}
    else if (type == SHIELD_MEDIUM)       {return "Medium Shield";}
    else if (type == SHIELD_TOWER)        {return "Tower Shield";}
    else if (type == ARMS_CLASS)          {return "Arms";}
    else if (type == ARMS_BRACERS)        {return "Bracers";}
    else if (type == ARMS_GAUNTLETS)      {return "Gauntlets";}
    else if (type == ARMS_GLOVES)         {return "Gloves";}
    else if (type == BOOTS_CLASS)         {return "Boots";}
    else if (type == BOOTS_SOFT)          {return "Soft Boots";}
    else if (type == BOOTS_HARD)          {return "Hard Boots";}
    else if (type == BOOTS_GREAVES)       {return "Greaves";}
    else if (type == BELT_LIGHT)          {return "Light Belt";}
    else if (type == BELT_CLASS)          {return "Belt";}
    else if (type == BELT_HEAVY)          {return "Heavy Belt";}
    else if (type == AXE_CLASS)           {return "Axe";}
    else if (type == AXE_SMALL)           {return "Small Axe";}
    else if (type == AXE_MEDIUM)          {return "Medium Axe";}
    else if (type == AXE_LARGE)           {return "Large Axe";}
    else if (type == AXE_MASSIVE)         {return "Massive Axe";}
    else if (type == BLUNT_CLASS)         {return "Blunt";}
    else if (type == BLUNT_CLUB)          {return "Club";}
    else if (type == BLUNT_MACE)          {return "Mace";}
    else if (type == BLUNT_HAMMER)        {return "Hammer";}
    else if (type == BLUNT_MAUL)          {return "Maul";}
    else if (type == BLUNT_STAFF)         {return "Staff";}
    else if (type == HAND2HAND_CLASS)     {return "Hand to Hand";}
    else if (type == HAND2HAND_KNUCKLES)  {return "Knuckles";}
    else if (type == HAND2HAND_FIST)      {return "Fist";}
    else if (type == HAND2HAND_CLAWS)     {return "Claws";}
    else if (type == HAND2HAND_DAGGER)    {return "Punch Dagger";}
    else if (type == LONG_BLADE_CLASS)    {return "Long Blade";}
    else if (type == LONG_BLADE_STRAIGHT) {return "Straight Long Blade";}
    else if (type == LONG_BLADE_CURVED)   {return "Curved Long Blade";}
    else if (type == LONG_BLADE_2H)       {return "Two-Handed Blade";}
    else if (type == POLEARM_CLASS)       {return "Polearm";}
    else if (type == POLEARM_SPEAR)       {return "Spear";}
    else if (type == POLEARM_HALBERD)     {return "Halberd";}
    else if (type == SHORT_BLADE_CLASS)   {return "Short Blade";}
    else if (type == SHORT_BLADE_SHORT)   {return "Short Sword";}
    else if (type == SHORT_BLADE_DAGGER)  {return "Dagger";}
    else if (type == SPIKED_CLASS)        {return "Spiked";}
    else if (type == SPIKED_FLAIL)        {return "Flail";}
    else if (type == SPIKED_STAR)         {return "Morning Star";}
    else if (type == THROWN_CLASS)        {return "Thrown";}
    else if (type == THROWN_AXE)          {return "Throwing Axe";}
    else if (type == THROWN_DART)         {return "Throwing Dart";}
    else if (type == THROWN_DAGGER)       {return "Throwing Dagger";}
    else if (type == THROWN_KNIFE)        {return "Throwing Knife";}
    else if (type == THROWN_JAVELIN)      {return "Javelin";}
    else if (type == THROWN_STONE)        {return "Throwing Stone";}
    else {return ("unknown type: " + type);}

  } // end getTypeName

//******************************************************************************
  public void keyPressed(KeyEvent k){
    // purpose of this method is to capture any pressed keys
/*    Popup.createInfoPopup("KeyPressed recognized by the Item class!"   + MyTextIO.newline
                         +"getKeyChar: "  + k.getKeyChar()             + MyTextIO.newline
                         +"getKeyCode: "  + k.getKeyCode()             + MyTextIO.newline
                         +"getKeyText: "  + k.getKeyText(k.getKeyCode()) + MyTextIO.newline
                         +"isActionKey: " + k.isActionKey());
*/
      details.hide();
      detailsArea.setCaretPosition(0); // go back to the top of the detailsArea for next view.
  } // end keyPressed

//******************************************************************************
  public void keyReleased(KeyEvent k){
    // purpose of this method is to capture any released keys
/*    Popup.createInfoPopup("KeyReleased recognized by the Item class!"  + MyTextIO.newline
                         +"getKeyChar: "  + k.getKeyChar()             + MyTextIO.newline
                         +"getKeyCode: "  + k.getKeyCode()             + MyTextIO.newline
                         +"getKeyText: "  + k.getKeyText(k.getKeyCode()) + MyTextIO.newline
                         +"isActionKey: " + k.isActionKey());
*/
      details.hide();
      detailsArea.setCaretPosition(0); // go back to the top of the detailsArea for next view.
  } // end keyReleased

//******************************************************************************
  public void keyTyped(KeyEvent k){
    // purpose of this method is to capture any typed keys
/*    Popup.createInfoPopup("KeyTyped recognized by the Item class!"     + MyTextIO.newline
                         +"getKeyChar: "  + k.getKeyChar()             + MyTextIO.newline
                         +"getKeyCode: "  + k.getKeyCode()             + MyTextIO.newline
                         +"getKeyText: "  + k.getKeyText(k.getKeyCode()) + MyTextIO.newline
                         +"isActionKey: " + k.isActionKey());

*/      details.hide();
      detailsArea.setCaretPosition(0); // go back to the top of the detailsArea for next view.
  } // end keyTyped

//******************************************************************************
// private methods
//******************************************************************************

//******************************************************************************
// static methods
//******************************************************************************
  public static Item generateRandomLoot(){
    // purpose of this method is to rendomly generate an item
    // from the small list of possible 'loot' items.
    // essentially, this includes all items that are neither weapons or armor.

    switch(Roll.D(8)){
      case 1:{
        Item item = Gold.generateRandomGold();
        if (item != null) return item;
      } // end case

      case 2:{
        Item item = Gem.generateRandomGem();
        if (item != null) return item;
      } // end case

      case 3:{
        Item item = Potion.generateRandomPotion();
        if (item != null) return item;
      } // end case

      case 4:{
        Item item = Scroll.generateRandomScroll();
        if (item != null) return item;
      } // end case

      case 5:{
        Item item = Artifact.generateRandomArtifact();
        if (item != null) return item;
      } // end case

      case 6:{
        Item item = Ring.generateRandomRing();
        if (item != null) return item;
      } // end case

      case 7:{
        Item item = Amulet.generateRandomAmulet();
        if (item != null) return item;
      } // end case

      case 8:{
        Item item = Book.generateRandomBook();
        if (item != null) return item;
      } // end case

      default:{
        Item item = Gold.generateRandomGold();
        if (item != null) return item;
      } // end default
    } // end switch
  return null;
  } // end generateRandomLoot

//******************************************************************************
  public static Item generateRandomItem(){
    // purpose of this method is to randomly generate an item,

    // since there is no such thing just an item, we should farm this task
    // out to the specific type of item we will generate.

    // Roll to decide the type, then call that type's constructor.

    switch (Roll.D4()){
      case 1:{
        Item item = generateRandomLoot();
        if (item != null) return item;
      } // end case

      case 2:{
        Item item = Ammo.generateRandomAmmo();
        if (item != null) return item;
      } // end case

      case 3:{
        Item item = Weapon.generateRandomWeapon();
        if (item != null) return item;
      } // end case

      case 4:{
        Item item = Armor.generateRandomArmor();
        if (item != null) return item;
      } // end case

      default:{
        return Gold.generateRandomGold();
      } // end default case
    } // end switch
} // end createRandomItem

//**************************************************************************
  public static Item createNewItem(Item oldItem){
    // purpose of this method is to return a deep copy of the oldItem
    return (Item)ObjectCloner.deepCopy(oldItem);
  } // make a copy

//**************************************************************************
  private JButton makeDetailsButton(String name){
    JButton button = new JButton(name);
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.setPreferredSize(new Dimension((int)((detailsWidth - detailsInset) / 3 - detailsVGap),
                                           (int)(detailsButtonRowHeight / 2)));
// NOTE: set the Action Listener specific to the button,
// since the split and equip will have to be handled by the ItemSlot class.
//  button.addActionListener(this);
    button.setActionCommand(name);
    return button;
  } // end makeDetailsButton

//**************************************************************************
} // end class Item
