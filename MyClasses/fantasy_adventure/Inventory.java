package fantasy_adventure;

import java.io.*;

class Inventory implements Serializable{
  // purpose of this class is to hold all the items a character can carry.
  // this includes both equipped and unequipped items.

  Item[] items;

  boolean[] isSlotFilled;

  int totalWeight = 0;
  int goldAmount = 0;

  // itemSlots index is listed below, so we can capture the item type

  static int HELMET_SLOT     = 0;
  static int NECK_SLOT       = 1;
  static int CUIRASS_SLOT    = 2;
  static int CLOAK_SLOT      = 3;
  static int WEAPON_SLOT_1   = 4;
  static int WEAPON_SLOT_2   = 5;
  static int WEAPON_SLOT_3   = 6;
  static int OFF_HAND_SLOT   = 7;
  static int RING_LEFT_SLOT  = 8;
  static int RING_RIGHT_SLOT = 9;
  static int FOREARMS_SLOT   = 10;
  static int BELT_SLOT       = 11;
  static int BOOTS_SLOT      = 12;

  static int AMMO_SLOT_1     = 13;
  static int AMMO_SLOT_2     = 14;
  static int AMMO_SLOT_3     = 15;

  static int QUICK_SLOT_1    = 16;
  static int QUICK_SLOT_2    = 17;
  static int QUICK_SLOT_3    = 18;

  static int MISC_SLOT_1     = 19;
  static int MISC_SLOT_2     = 20;

  static int PACK_SLOT_1     = 21;
  static int PACK_SLOT_2     = 22;
  static int PACK_SLOT_3     = 23;
  static int PACK_SLOT_4     = 24;
  static int PACK_SLOT_5     = 25;
  static int PACK_SLOT_6     = 26;
  static int PACK_SLOT_7     = 27;
  static int PACK_SLOT_8     = 28;
  static int PACK_SLOT_9     = 29;
  static int PACK_SLOT_10    = 30;
  static int PACK_SLOT_11    = 31;
  static int PACK_SLOT_12    = 32;
  static int PACK_SLOT_13    = 33;
  static int PACK_SLOT_14    = 34;
  static int PACK_SLOT_15    = 35;
  static int PACK_SLOT_16    = 36;

  static int MISC_SLOT_3     = 37;
  static int MISC_SLOT_4     = 38;
  static int MISC_SLOT_5     = 39;

  static int GROUND_SLOT_1   = 40;
  static int GROUND_SLOT_2   = 41;
  static int GROUND_SLOT_3   = 42;
  static int GROUND_SLOT_4   = 43;
  static int GROUND_SLOT_5   = 44;
  static int GROUND_SLOT_6   = 45;

//***************************************************************************
// constructor
//***************************************************************************

Inventory (){
  items = new Item[40]; // NOTE: does not include ground slots!
  isSlotFilled = new boolean[40];
} // end constructor

//***************************************************************************
// package methods
//***************************************************************************

  void setHelmet        (Item i) {items[HELMET_SLOT]     = i;}
  void setNeckSlot      (Item i) {items[NECK_SLOT]       = i;}
  void setCuirassSlot   (Item i) {items[CUIRASS_SLOT]    = i;}
  void setCloakSlot     (Item i) {items[CLOAK_SLOT]      = i;}
  void setWeaponSlot1   (Item i) {items[WEAPON_SLOT_1]   = i;}
  void setWeaponSlot2   (Item i) {items[WEAPON_SLOT_2]   = i;}
  void setWeaponSlot3   (Item i) {items[WEAPON_SLOT_3]   = i;}
  void setOffHandSlot   (Item i) {items[OFF_HAND_SLOT]   = i;}
  void setRingLeftSlot  (Item i) {items[RING_LEFT_SLOT]  = i;}
  void setRingRightSlot (Item i) {items[RING_RIGHT_SLOT] = i;}
  void setForearmsSlot  (Item i) {items[FOREARMS_SLOT]   = i;}
  void setBeltSlot      (Item i) {items[BELT_SLOT]       = i;}
  void setBootsSlot     (Item i) {items[BOOTS_SLOT]      = i;}

  void setAmmoSlot1     (Item i) {items[AMMO_SLOT_1]     = i;}
  void setAmmoSlot2     (Item i) {items[AMMO_SLOT_2]     = i;}
  void setAmmoSlot3     (Item i) {items[AMMO_SLOT_3]     = i;}

  void setQuickSlot1    (Item i) {items[QUICK_SLOT_1]    = i;}
  void setQuickSlot2    (Item i) {items[QUICK_SLOT_2]    = i;}
  void setQuickSlot3    (Item i) {items[QUICK_SLOT_3]    = i;}

  void setBlankSlot1    (Item i) {items[MISC_SLOT_1]    = i;}

  void setPackSlot1     (Item i) {items[PACK_SLOT_1]     = i;}
  void setPackSlot2     (Item i) {items[PACK_SLOT_2]     = i;}
  void setPackSlot3     (Item i) {items[PACK_SLOT_3]     = i;}
  void setPackSlot4     (Item i) {items[PACK_SLOT_4]     = i;}
  void setPackSlot5     (Item i) {items[PACK_SLOT_5]     = i;}
  void setPackSlot6     (Item i) {items[PACK_SLOT_6]     = i;}
  void setPackSlot7     (Item i) {items[PACK_SLOT_7]     = i;}
  void setPackSlot8     (Item i) {items[PACK_SLOT_8]     = i;}
  void setPackSlot9     (Item i) {items[PACK_SLOT_9]     = i;}
  void setPackSlot10    (Item i) {items[PACK_SLOT_10]    = i;}
  void setPackSlot11    (Item i) {items[PACK_SLOT_11]    = i;}
  void setPackSlot12    (Item i) {items[PACK_SLOT_12]    = i;}
  void setPackSlot13    (Item i) {items[PACK_SLOT_13]    = i;}
  void setPackSlot14    (Item i) {items[PACK_SLOT_14]    = i;}
  void setPackSlot15    (Item i) {items[PACK_SLOT_15]    = i;}
  void setPackSlot16    (Item i) {items[PACK_SLOT_16]    = i;}

  void setMiscSlot1     (Item i) {items[MISC_SLOT_2]     = i;}
  void setMiscSlot2     (Item i) {items[MISC_SLOT_3]     = i;}
  void setMiscSlot3     (Item i) {items[MISC_SLOT_4]     = i;}

  void setTotalWeight   (int i) {totalWeight = i;}

//**************************************************************************

  Item getHelmet        () {return items[HELMET_SLOT]    ;}
  Item getNeckSlot      () {return items[NECK_SLOT]      ;}
  Item getCuirassSlot   () {return items[CUIRASS_SLOT]   ;}
  Item getCloakSlot     () {return items[CLOAK_SLOT]     ;}
  Item getWeaponSlot1   () {return items[WEAPON_SLOT_1]  ;}
  Item getWeaponSlot2   () {return items[WEAPON_SLOT_2]  ;}
  Item getWeaponSlot3   () {return items[WEAPON_SLOT_3]  ;}
  Item getOffHandSlot   () {return items[OFF_HAND_SLOT]  ;}
  Item getRingLeftSlot  () {return items[RING_LEFT_SLOT] ;}
  Item getRingRightSlot () {return items[RING_RIGHT_SLOT];}
  Item getForearmsSlot  () {return items[FOREARMS_SLOT]  ;}
  Item getBeltSlot      () {return items[BELT_SLOT]      ;}
  Item getBootsSlot     () {return items[BOOTS_SLOT]     ;}

  Item getAmmoSlot1     () {return items[AMMO_SLOT_1]    ;}
  Item getAmmoSlot2     () {return items[AMMO_SLOT_2]    ;}
  Item getAmmoSlot3     () {return items[AMMO_SLOT_3]    ;}

  Item getQuickSlot1    () {return items[QUICK_SLOT_1]   ;}
  Item getQuickSlot2    () {return items[QUICK_SLOT_2]   ;}
  Item getQuickSlot3    () {return items[QUICK_SLOT_3]   ;}

  Item getBlankSlot1    () {return items[MISC_SLOT_1]   ;}

  Item getPackSlot1     () {return items[PACK_SLOT_1]    ;}
  Item getPackSlot2     () {return items[PACK_SLOT_2]    ;}
  Item getPackSlot3     () {return items[PACK_SLOT_3]    ;}
  Item getPackSlot4     () {return items[PACK_SLOT_4]    ;}
  Item getPackSlot5     () {return items[PACK_SLOT_5]    ;}
  Item getPackSlot6     () {return items[PACK_SLOT_6]    ;}
  Item getPackSlot7     () {return items[PACK_SLOT_7]    ;}
  Item getPackSlot8     () {return items[PACK_SLOT_8]    ;}
  Item getPackSlot9     () {return items[PACK_SLOT_9]    ;}
  Item getPackSlot10    () {return items[PACK_SLOT_10]   ;}
  Item getPackSlot11    () {return items[PACK_SLOT_11]   ;}
  Item getPackSlot12    () {return items[PACK_SLOT_12]   ;}
  Item getPackSlot13    () {return items[PACK_SLOT_13]   ;}
  Item getPackSlot14    () {return items[PACK_SLOT_14]   ;}
  Item getPackSlot15    () {return items[PACK_SLOT_15]   ;}
  Item getPackSlot16    () {return items[PACK_SLOT_16]   ;}

  Item getMiscSlot1     () {return items[MISC_SLOT_2]    ;}
  Item getMiscSlot2     () {return items[MISC_SLOT_3]    ;}
  Item getMiscSlot3     () {return items[MISC_SLOT_4]    ;}

  Item getActiveWeaponSlot(){
    // purpose of this method is to return one of the weapon slots
    // whichever one is selected.

    // go through the three weapon slots, and check if selected is true.
    // this assumes there is only one selected, or else the first one is returned.

    if (items[WEAPON_SLOT_1].isEquipped() == true) return items[WEAPON_SLOT_1];
    if (items[WEAPON_SLOT_2].isEquipped() == true) return items[WEAPON_SLOT_2];
    if (items[WEAPON_SLOT_3].isEquipped() == true) return items[WEAPON_SLOT_3];
    return null;
  } // end getActiveWeaponSlot()

//***************************************************************************
   Item getActiveAmmoSlot(){
     // purpose of this method is to return one of the ammo slots
     // whichever one is selected.
     if (items[AMMO_SLOT_1].isEquipped() == true) return items[AMMO_SLOT_1];
     if (items[AMMO_SLOT_2].isEquipped() == true) return items[AMMO_SLOT_2];
     if (items[AMMO_SLOT_3].isEquipped() == true) return items[AMMO_SLOT_3];
     return null;
   } // end getActiveAmmoSlot()

   //***************************************************************************
    Item getQuickItemSlotSelected(){
      // purpose of this method is to return one of the ammo slots
      // whichever one is selected.
      if (items[QUICK_SLOT_1].isEquipped() == true) return items[QUICK_SLOT_1];
      if (items[QUICK_SLOT_2].isEquipped() == true) return items[QUICK_SLOT_2];
      if (items[QUICK_SLOT_3].isEquipped() == true) return items[QUICK_SLOT_3];
      return null;
    } // end getActiveAmmoSlot()

   //***************************************************************************
    Item getPackItemSlotSelected(){
      // purpose of this method is to return one of the ammo slots
      // whichever one is selected.
      if (items[PACK_SLOT_1].isEquipped() == true) return items[PACK_SLOT_1];
      if (items[PACK_SLOT_2].isEquipped() == true) return items[PACK_SLOT_2];
      if (items[PACK_SLOT_4].isEquipped() == true) return items[PACK_SLOT_4];
      if (items[PACK_SLOT_5].isEquipped() == true) return items[PACK_SLOT_5];
      if (items[PACK_SLOT_6].isEquipped() == true) return items[PACK_SLOT_6];
      if (items[PACK_SLOT_7].isEquipped() == true) return items[PACK_SLOT_7];
      if (items[PACK_SLOT_8].isEquipped() == true) return items[PACK_SLOT_8];
      if (items[PACK_SLOT_9].isEquipped() == true) return items[PACK_SLOT_9];
      if (items[PACK_SLOT_10].isEquipped() == true) return items[PACK_SLOT_10];
      if (items[PACK_SLOT_11].isEquipped() == true) return items[PACK_SLOT_11];
      if (items[PACK_SLOT_12].isEquipped() == true) return items[PACK_SLOT_12];
      if (items[PACK_SLOT_13].isEquipped() == true) return items[PACK_SLOT_13];
      if (items[PACK_SLOT_14].isEquipped() == true) return items[PACK_SLOT_14];
      if (items[PACK_SLOT_15].isEquipped() == true) return items[PACK_SLOT_15];
      if (items[PACK_SLOT_16].isEquipped() == true) return items[PACK_SLOT_16];
      return null;
    } // end getActiveAmmoSlot()

   //***************************************************************************
    Item getMiscSlotSelected(){
      // purpose of this method is to return one of the ammo slots
      // whichever one is selected.
      if (items[MISC_SLOT_1].isEquipped() == true) return items[MISC_SLOT_1];
      if (items[MISC_SLOT_2].isEquipped() == true) return items[MISC_SLOT_2];
      if (items[MISC_SLOT_3].isEquipped() == true) return items[MISC_SLOT_3];
      if (items[MISC_SLOT_4].isEquipped() == true) return items[MISC_SLOT_4];
      if (items[MISC_SLOT_5].isEquipped() == true) return items[MISC_SLOT_5];
      return null;
    } // end getActiveAmmoSlot()

//***************************************************************************
  int getTotalWeight(){return totalWeight;} // end getTotalWeight

  int getGold() {
    return goldAmount;
  } // end getGold

//***************************************************************************
  void calcInvWeight() {
    totalWeight = 0;
    for ( int i = 0; i < items.length; i++){
      if (items[i] != null){
        totalWeight +=
         (items[i].basics.getWeight() * items[i].getQuantity());
      } // end if
    } // end for loop
  } // end calcInvWeight()

//***************************************************************************
  void writeInventoryToFile(BufferedWriter bw) throws IOException {
    // purpose of this method is to continue writing to a file
    // specifically to add this classes information to the file in progress.
    bw.write("Inventory properties:" + MyTextIO.tab + "Gold:" + MyTextIO.tab + getGold() + MyTextIO.tab);

    // now, update inventory, so we know who has items and who doesn't.

    for (int i = 0; i < items.length; i++){
      if (items[i] != null) {
//        Popup.createInfoPopup("Slot at index#" + i + " is not null, so setting as true.");
        isSlotFilled[i] = true;
      } // end if
      else isSlotFilled[i] = false;
    } // end for loop

    // so we can write this array.
    for (int i = 0; i < items.length; i++){
      bw.write(isSlotFilled[i] + MyTextIO.tab);
    } // end for loop

    bw.newLine(); // ends inventory index

    // now that we know who is filled and who isn't, then we can write the correct items.
    for (int i = 0; i < items.length; i++){
      if (items[i] != null){
//        Popup.createInfoPopup("Writing item to file from inventory slot: " + i);
        // we have to figure out which item type to call the constructor for.
        decideItemTypeAndSubRoutine(items[i], bw);
        bw.flush();
      } // end if
    } // end for loop
  } // end writeToFile

//***************************************************************************

  private void decideItemTypeAndSubRoutine(Item item, BufferedWriter bw) throws IOException{
    // purpose of this method is to figure out what type of item we are dealing
    // with and then call the appropriate item writer.

    // write a single line with the type, so the reader can call the correct item from this line.
//    Popup.createInfoPopup("Writing type # to file: '" + item.getType() + "'");
    bw.write("type:" + MyTextIO.tab + item.getType());
    bw.newLine();

    item.writeToFile(bw); // uses the deepest method? YES!

  } // end

//***************************************************************************
  void readFromFile(BufferedReader br) throws IOException {
    // purpose of this method is to continue writing to a file
    // specifically to add this classes information to the file in progress.
    String thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title "ItemProperties:"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title "Gold:"
    goldAmount = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // captures gold amount
    thisLine = MyTextIO.trimPhrase(thisLine); // removes gold variable

    // now, we can read in the index file of the inventory,
    // which kept track of who was filled and who wasn't.
    for (int i = 0; i < items.length; i++){
      isSlotFilled[i] = new Boolean(MyTextIO.getNextPhrase(thisLine)).booleanValue();
      thisLine = MyTextIO.trimPhrase(thisLine);
    } // end for loop

    for (int i = 0; i < items.length; i++){
        // if we know the item exists, then we can read it in.
        if (isSlotFilled[i] == true){
          items[i] = decideItemTypeAndSubRoutineReader(br);
        } // end if
    } // end for loop
  } // end writeToFile

//***************************************************************************
  Item decideItemTypeAndSubRoutineReader(BufferedReader br) throws IOException{
  // purpose of this method is to look at the buffered reader and decide what item reader to call
  // based on the next line, which MUST have the item number on it.

  String thisLine = br.readLine();
  thisLine = MyTextIO.trimPhrase(thisLine); // removes "type:"
  int type = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));

  // now, we know what type the following item must be, so we can call the correct reader.
  if      (type == Item.AMMO_ARROW)         return Ammo.readFromFile(type, br);
  else if (type == Item.AMMO_BOLT)          return Ammo.readFromFile(type, br);
  else if (type == Item.AMMO_BULLET)        return Ammo.readFromFile(type, br);
  else if (type == Item.AMULET)             return Amulet.readFromFile(type, br);
  else if (type == Item.ARMS_GLOVES)        return Arms.readFromFile(type, br);
  else if (type == Item.ARMS_GAUNTLETS)     return Arms.readFromFile(type, br);
  else if (type == Item.ARMS_BRACERS)       return Arms.readFromFile(type, br);
  else if (type == Item.AXE_SMALL)          return Axe.readFromFile(type, br);
  else if (type == Item.AXE_MEDIUM)         return Axe.readFromFile(type, br);
  else if (type == Item.AXE_LARGE)          return Axe.readFromFile(type, br);
  else if (type == Item.AXE_MASSIVE)        return Axe.readFromFile(type, br);
  else if (type == Item.BELT_LIGHT)         return Belt.readFromFile(type, br);
  else if (type == Item.BELT_HEAVY)         return Belt.readFromFile(type, br);
  else if (type == Item.BLUNT_CLUB)         return Blunt.readFromFile(type, br);
  else if (type == Item.BLUNT_MACE)         return Blunt.readFromFile(type, br);
  else if (type == Item.BLUNT_HAMMER)       return Blunt.readFromFile(type, br);
  else if (type == Item.BLUNT_MAUL)         return Blunt.readFromFile(type, br);
  else if (type == Item.BLUNT_STAFF)        return Blunt.readFromFile(type, br);
  else if (type == Item.BOOK)               return Book.readFromFile(type, br);
  else if (type == Item.BOOTS_SOFT)         return Boots.readFromFile(type, br);
  else if (type == Item.BOOTS_HARD)         return Boots.readFromFile(type, br);
  else if (type == Item.BOOTS_GREAVES)      return Boots.readFromFile(type, br);
  else if (type == Item.CLOAK)              return Cloak.readFromFile(type, br);
  else if (type == Item.CUIRASS_ROBE)       return Cuirass.readFromFile(type, br);
  else if (type == Item.CUIRASS_LIGHT)      return Cuirass.readFromFile(type, br);
  else if (type == Item.CUIRASS_MEDIUM)     return Cuirass.readFromFile(type, br);
  else if (type == Item.CUIRASS_HEAVY)      return Cuirass.readFromFile(type, br);
  else if (type == Item.GEM)                return Gem.readFromFile(type, br);
  else if (type == Item.GOLD)               return Gold.readFromFile(type, br);
  else if (type == Item.HAND2HAND_FIST)     return Hand2Hand.readFromFile(type, br);
  else if (type == Item.HAND2HAND_KNUCKLES) return Hand2Hand.readFromFile(type, br);
  else if (type == Item.HAND2HAND_CLAWS)    return Hand2Hand.readFromFile(type, br);
  else if (type == Item.HAND2HAND_DAGGER)   return Hand2Hand.readFromFile(type, br);
  else if (type == Item.HELMET_NORMAL)      return Helmet.readFromFile(type, br);
  else if (type == Item.HELMET_BEAST)       return Helmet.readFromFile(type, br);
  else if (type == Item.HELMET_CIRCLET)     return Helmet.readFromFile(type, br);
  else if (type == Item.HELMET_HOOD)        return Helmet.readFromFile(type, br);
  else if (type == Item.LAUNCHER_BOW_SHORT) return Launcher.readFromFile(type, br);
  else if (type == Item.LAUNCHER_BOW_LONG)  return Launcher.readFromFile(type, br);
  else if (type == Item.LAUNCHER_BOW_COMPOSITE)  return Launcher.readFromFile(type, br);
  else if (type == Item.LAUNCHER_CROSSBOW_LIGHT) return Launcher.readFromFile(type, br);
  else if (type == Item.LAUNCHER_CROSSBOW_HEAVY) return Launcher.readFromFile(type, br);
  else if (type == Item.LAUNCHER_SLING)     return Launcher.readFromFile(type, br);
  else if (type == Item.LONG_BLADE_STRAIGHT)return LongBlade.readFromFile(type, br);
  else if (type == Item.LONG_BLADE_CURVED)  return LongBlade.readFromFile(type, br);
  else if (type == Item.LONG_BLADE_2H)      return LongBlade.readFromFile(type, br);
  else if (type == Item.POLEARM_SPEAR)      return PoleArm.readFromFile(type, br);
  else if (type == Item.POLEARM_HALBERD)    return PoleArm.readFromFile(type, br);
  else if (type == Item.POTION)             return Potion.readFromFile(type, br);
  else if (type == Item.RING)               return Ring.readFromFile(type, br);
  else if (type == Item.SCROLL)             return Scroll.readFromFile(type, br);
  else if (type == Item.SHIELD_BUCKLER)     return Shield.readFromFile(type, br);
  else if (type == Item.SHIELD_ROUND)       return Shield.readFromFile(type, br);
  else if (type == Item.SHIELD_MEDIUM)      return Shield.readFromFile(type, br);
  else if (type == Item.SHIELD_TOWER)       return Shield.readFromFile(type, br);
  else if (type == Item.SHORT_BLADE_DAGGER) return ShortBlade.readFromFile(type, br);
  else if (type == Item.SHORT_BLADE_SHORT)  return ShortBlade.readFromFile(type, br);
  else if (type == Item.SPIKED_FLAIL)       return Spiked.readFromFile(type, br);
  else if (type == Item.SPIKED_STAR)        return Spiked.readFromFile(type, br);
  else if (type == Item.THROWN_AXE)         return Thrown.readFromFile(type, br);
  else if (type == Item.THROWN_DAGGER)      return Thrown.readFromFile(type, br);
  else if (type == Item.THROWN_DART)        return Thrown.readFromFile(type, br);
  else if (type == Item.THROWN_KNIFE)       return Thrown.readFromFile(type, br);
  else if (type == Item.THROWN_JAVELIN)     return Thrown.readFromFile(type, br);
  else if (type == Item.THROWN_STONE)       return Thrown.readFromFile(type, br);
  else {
    Popup.createInfoPopup("Item name not found: '" + Item.getTypeName(type) + "'");
  } // end else
  return null;

  } // end read

//***************************************************************************

} // end class Inventory




