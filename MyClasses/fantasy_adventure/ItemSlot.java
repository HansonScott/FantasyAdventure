package fantasy_adventure;

//**************************************************************************
// imports
//**************************************************************************

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

//**************************************************************************
public class ItemSlot extends JPanelWithBackground implements ActionListener,
                                                              MouseListener,
                                                              KeyListener{

//**************************************************************************
// static declarations
//**************************************************************************

      static boolean   justResetting;
      static int       ITEMSLOT_HEIGHT = 50;
      static int       ITEMSLOT_WIDTH  = 50;
      static int       BORDER_THICKNESS = 10; // 5 per edge

//**************************************************************************
// member declarations
//**************************************************************************

  boolean   filled, selected, useable;

  Item      item = null;

  Color     emptyColor      = Color.gray,
            useableColor    = Color.blue,
            notUseableColor = Color.red,
            selectedColor   = Color.yellow,
            currentColor    = emptyColor;

  JLabel    iconLabel;

  InvPanel  parent;

  int       slotNumber;

//**************************************************************************
// constructors
//**************************************************************************
  public ItemSlot(int slot, InvPanel invPanel) {
    super(FileNames.EMPTY_ITEMSLOT, JPanelWithBackground.TILE);
    setBackgroundImage(getBackgroundImageForSlot(slot));
    parent = invPanel;
    slotNumber = slot;
    setPreferredSize(new Dimension(ITEMSLOT_HEIGHT, ITEMSLOT_WIDTH));
    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    iconLabel = new JLabel();
    iconLabel.setPreferredSize(new Dimension(ITEMSLOT_HEIGHT - BORDER_THICKNESS,
                                             ITEMSLOT_WIDTH  - BORDER_THICKNESS));
    add(iconLabel);
    update();
    addMouseListener(this);

  } // end constructor

  public ItemSlot(){
    super(FileNames.EMPTY_ITEMSLOT, JPanelWithBackground.TILE);
    setPreferredSize(new Dimension(ITEMSLOT_HEIGHT, ITEMSLOT_WIDTH));
    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    iconLabel = new JLabel();
    iconLabel.setPreferredSize(new Dimension(ITEMSLOT_HEIGHT - BORDER_THICKNESS,
                                             ITEMSLOT_WIDTH  - BORDER_THICKNESS));
    add(iconLabel);
  } // end basic constructor

//**************************************************************************
// public methods
//**************************************************************************
  boolean isFilled()   {return filled;}
  boolean isSelected() {return selected;}
  boolean isUseable()  {return useable;}

  void setFilled(boolean b) {
    filled = b;
    updateBackground();
  } // end setFilled

  void setSelected(boolean b) {
    selected = b;
    updateBorder();
  } // end setSelected

  void setUseable(boolean b) {
    useable = b;
    updateBorder();
  } // end setUseable

//**************************************************************************
  void setItem(Item thisItem){
    // purpose of this method is to set the given item into this itemSlot
    // *NOTE: it is assumed that this item CAN fill the slot

    // if there is no item, then don't do anything
    if (thisItem == null) return;

    // assuming the item does exist, and can be set, then set the item basics
    item = thisItem;
    filled = true;
    update();

    resetItemDetailButtons();

    // if we are just updating, then we don't have to change stats.
    // if parent is null, then don't bother checking stats.
    if (ItemSlot.justResetting == true || parent == null) return;

    // now, we need to deal with the results of the item being set.
    if (slotNumber == Inventory.AMMO_SLOT_1   ||
        slotNumber == Inventory.AMMO_SLOT_2   ||
        slotNumber == Inventory.AMMO_SLOT_3){

      // if the itemSlot is an ammo, set it to be the equipped one.

        // at this point, we need to deal with which one of these slots is equipped, and/or change thusly.
        unequipAmmoSlots();
/*        if (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1] != null){
            parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1].setEquipped(false);
            parent.itemSlots[Inventory.AMMO_SLOT_1].update();
            } // end if

        if (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2] != null){
            parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2].setEquipped(false);
            parent.itemSlots[Inventory.AMMO_SLOT_2].update();
            } // end if

        if (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3] != null){
            parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3].setEquipped(false);
            parent.itemSlots[Inventory.AMMO_SLOT_3].update();
            } // end if

 */           parent.thisCharacter.inventory.items[slotNumber] = item;
            parent.thisCharacter.inventory.items[slotNumber].setEquipped(true);
            parent.itemSlots[slotNumber].update();

      } // end if ammo slots

    // if the itemSlot is a weapon, set it to be the equipped one.
     else if(slotNumber == Inventory.WEAPON_SLOT_1 ||
             slotNumber == Inventory.WEAPON_SLOT_2 ||
             slotNumber == Inventory.WEAPON_SLOT_3){

        // at this point, we need to deal with which one of these slots is equipped, and/or change thusly.
        unequipWeaponSlots();
/*        if (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1] != null){
            parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1].setEquipped(false);
            parent.itemSlots[Inventory.WEAPON_SLOT_1].update();
            } // end if

        if (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2] != null){
            parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2].setEquipped(false);
            parent.itemSlots[Inventory.WEAPON_SLOT_2].update();
            } // end if

        if (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3] != null){
            parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3].setEquipped(false);
            parent.itemSlots[Inventory.WEAPON_SLOT_3].update();
            } // end if
*/
        parent.thisCharacter.inventory.items[slotNumber] = item;
        parent.thisCharacter.inventory.items[slotNumber].setEquipped(true);
        parent.itemSlots[slotNumber].update();

        // at this point, we want to tell the user their ranks with the equipped weapon.
        int lvl = parent.thisCharacter.currentLevel.get(
                  parent.thisCharacter.inventory.items[slotNumber].basics.getGoverningProficiency());

        if (justResetting == false){
        parent.feedbackTextArea.setText("You have equipped a weapon of which you have " +
                                         lvl +
                                         " skill ranks," + MyTextIO.newline + "this applies an adjustment of " +
                                         Formulas.getBonusForNumRanks(lvl) +
                                         " points to your attack." + MyTextIO.newline);
        } // end just resetting

      } // end if weapon slots

    // if the item being set is neither ammo nor weapon...
      else{

        // if the itemSlot is one of the character's items at all
        if (slotNumber < 40){
          parent.thisCharacter.inventory.items[slotNumber] = item;
          parent.itemSlots[slotNumber].item = item;
          parent.thisCharacter.inventory.items[slotNumber].setEquipped(false);
        } // end if <40

        // if the itemSlot is one of the character's equipment slots
        if (slotNumber < 13) {
          parent.thisCharacter.inventory.items[slotNumber].setEquipped(true);
        } // end if <13

        // if the itemSlot is outside the character's equipment
        if (slotNumber >= 13){
          parent.itemSlots[slotNumber].item.setEquipped(false);
        } // end if >= 13
      } // end else (non-ammo, non-weapon)

  // check to see if casting failure should change.
  if (slotNumber < 13 &&
      item.basics.getCastingFailure() != 0 &&
      justResetting == false){
    parent.feedbackTextArea.append("Your casting failure may have changed when setting that item." + MyTextIO.newline);
  } // end if casting failure

      parent.itemSlots[slotNumber].update();
      parent.thisCharacter.updateStatsFromInventory();
      parent.thisCharacter.inventory.calcInvWeight();
      parent.refreshInfo(parent.thisCharacter);
  } // end set Item

//**************************************************************************
  void removeItem(){
    // the purpose of this method is to remove the item from this slot
    // it is assumed that there is an item in this slot, and it can be removed.

    // check to see if casting failure should change.
    if (item.basics.getCastingFailure() != 0 &&
        slotNumber < 13 &&
        justResetting == false){
      parent.feedbackTextArea.append("Your casting failure may have changed when removing that item." + MyTextIO.newline);
    } // end if casting failure

    // first set the basics
    filled = false;
    item = null; // this removes it from the screen, not the character's inventory, yet.
    update();

    // Remove the item from the character's inventory
    if (slotNumber < 40) // removing item from character's inventory (behind the scenes)
      parent.thisCharacter.inventory.items[slotNumber] = null;

    // now we can adjust all the borders and such.
      // deal with the auto-select of ammo slot, if we removed one.
      if (slotNumber == Inventory.AMMO_SLOT_1 ||
          slotNumber == Inventory.AMMO_SLOT_2 ||
          slotNumber == Inventory.AMMO_SLOT_3){

        // at this point, we need to deal with which one of these slots is equipped, and/or change thusly.

        if (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1]!= null){
            // we have to make sure that there is not another slot equipped already.
            if ((parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2] == null ||
                 (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2] != null &&
                parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2].isEquipped() == false)) &&

               ((parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3] == null ||
                 (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3]!= null &&
                parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3].isEquipped() == false))))
            parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1].setEquipped(true);

            parent.itemSlots[Inventory.AMMO_SLOT_1].item =
              parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1];

            parent.itemSlots[Inventory.AMMO_SLOT_1].update();
            } // end if

        else if (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2] != null){
            // we have to make sure that there is not another slot equipped already.
            if ((parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1] == null ||
                 (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1] != null &&
                parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1].isEquipped() == false)) &&

               ((parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3] == null ||
                 (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3]!= null &&
                parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3].isEquipped() == false))))
            parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2].setEquipped(true);

            parent.itemSlots[Inventory.AMMO_SLOT_2].item =
              parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2];

            parent.itemSlots[Inventory.AMMO_SLOT_2].update();
            } // end if

        else if (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3] != null){
            // we have to make sure that there is not another slot equipped already.
            if ((parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2] == null ||
                 (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2]!= null &&
                parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2].isEquipped() == false)) &&

               ((parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1] == null ||
                 (parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1]!= null &&
                parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1].isEquipped() == false))))
            parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3].setEquipped(true);

            parent.itemSlots[Inventory.AMMO_SLOT_3].item =
              parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3];

            parent.itemSlots[Inventory.AMMO_SLOT_3].update();
            } // end if
        else{}

      } // end if ammo slots

      //deal with the auto-select of weapons when removing one.
      else if (slotNumber == Inventory.WEAPON_SLOT_1 ||
               slotNumber == Inventory.WEAPON_SLOT_2 ||
               slotNumber == Inventory.WEAPON_SLOT_3){

          // at this point, we need to deal with which one of these slots is equipped, and/or change thusly.

          if (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1]!= null){
              // we have to make sure that there is not another slot equipped already.
              if ((parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2] == null ||
                   (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2]!= null &&
                  parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2].isEquipped() == false)) &&

                 ((parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3] == null ||
                   (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3]!= null &&
                  parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3].isEquipped() == false))))

              parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1].setEquipped(true);

              parent.itemSlots[Inventory.WEAPON_SLOT_1].update();
              } // end if

          else if (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2] != null){
              // we have to make sure that there is not another slot equipped already.
              if ((parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1] == null ||
                   (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1]!= null &&
                  parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1].isEquipped() == false)) &&

                 ((parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3] == null ||
                   (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3]!= null &&
                  parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3].isEquipped() == false))))

              parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2].setEquipped(true);

              parent.itemSlots[Inventory.WEAPON_SLOT_2].update();
              } // end if

          else if (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3] != null){
              // we have to make sure that there is not another slot equipped already.
              if ((parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2] == null ||
                   (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2]!= null &&
                  parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2].isEquipped() == false)) &&

                 ((parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1] == null ||
                   (parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1]!= null &&
                  parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1].isEquipped() == false))))

              parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3].setEquipped(true);

              parent.itemSlots[Inventory.WEAPON_SLOT_3].update();
              } // end if
          else{}

      } // end if weapon slots

      else{}

      // now update the character stats to reflect the change.
      parent.itemSlots[slotNumber].update();
      parent.thisCharacter.updateStatsFromInventory();
      parent.thisCharacter.inventory.calcInvWeight();
      parent.refreshInfo(parent.thisCharacter);
  } // end removeItem

//**************************************************************************
  public void actionPerformed(ActionEvent e) {
    // *NOTE: this is capturing actions from the item's details buttons, not its own.

    String command = e.getActionCommand();

    if (command.equals("Set Equipped")){
//      Popup.createInfoPopup("Handle equipping this item coming soon...");
      equipThisItem(item, slotNumber);
    } // end if set equipped

  } // end actionPerformed

//**************************************************************************
  public void mouseClicked(MouseEvent e){

    if (e.getClickCount() == 1 &&
        (e.isAltDown() || e.isControlDown()) &&
        parent.itemSlots[slotNumber].isFilled() &&
        parent.itemSlots[slotNumber].item.getQuantity() > 1){
      handleSplitStack(parent.itemSlots[slotNumber].item);
    } // end single-click with meta keys

    if (e.getClickCount() == 1){ // handle all single-clicks

     // if this slot is filled then we can deal with the item within it.
     if (filled == true){

       if (item.getType() == Item.GOLD){
         // user has double-clicked on gold, so add gold amount and remove item.
         parent.thisCharacter.inventory.goldAmount += this.item.getQuantity();
         removeItem();
       } // end if gold

      else if (getSelectedSlotNumber() != -1){ // then there is another slot number selected
        Item otherItem = parent.itemSlots[getSelectedSlotNumber()].item;

      // first check for a stacking action
//      Popup.createInfoPopup("About to test for stacking...");
/*      Popup.createInfoPopup("item.isEaqualTo(otherItem)?: " + String.valueOf(item.isEqualTo(otherItem)) + MyTextIO.newline +
                            "item != otherItem?: " + String.valueOf(item != otherItem) + MyTextIO.newline +
                            "item is stackable?: " + String.valueOf(item.basics.isStackable()) + MyTextIO.newline +
                            "otherItem is stackable?: " + String.valueOf(otherItem.basics.isStackable()));
 */
      if (item != otherItem             &&
          item.isEqualTo(otherItem)     &&
          item.basics.isStackable()     &&
          otherItem.basics.isStackable()){

//        Popup.createInfoPopup("I think you're trying to stack items...");

        // then the user is attempting to combine like items.
        // need to check against stack size, so we don't go over.

        if (item.getQuantity() + otherItem.getQuantity() > Constants.STACK_SIZE){
          // if so, then we need to transfer only the amount possible.

          // find out how many will fit, and capture that amount.
          int transferAmount = (Constants.STACK_SIZE - item.getQuantity());

          // now that we know how many can be transfered,
          // then we can add that amount to the new slot:
          item.setQuantity(item.getQuantity() + transferAmount);

          // then we can remove that amount from the old slot,
          otherItem.setQuantity(otherItem.getQuantity() - transferAmount);

          otherItem.updateDetails();
          parent.itemSlots[getSelectedSlotNumber()].update();
        } // end if too many

        else{ // not too many for stacking
          item.setQuantity(item.getQuantity() + otherItem.getQuantity());
          parent.itemSlots[getSelectedSlotNumber()].removeItem();
        } // end else - not too many

        item.updateDetails();
        this.update();
        return; // we're done, so quit this method
      } // end types match (stacking)
    } // end if other item exists at all

    //**********************************************
    // besides stacking, we are just selecting another item, so adjust visuals
    // if this is filled, then highlight this one.
    for (int i = 0; i < parent.itemSlots.length; i++){
      parent.itemSlots[i].setSelected(false);
    } // end for loop
    setSelected(true);
    update(); // update borders and info to reflect selection change.

    //**********************************************
    // handle any right clicks or other buttons
    if (e.getButton() == e.BUTTON2 ||
        e.getButton() == e.BUTTON3){
      parent.showItemDetails();
    } // end other button
    return;
  } // end if filled

  //**********************************************
  // single click still, but not filled,
  else if (filled == false){

    // first ignore right-clicks on empty space, for now anyway.
    if (e.getButton() == e.BUTTON2 ||
        e.getButton() == e.BUTTON3){
      return;
    } // end other button

    //  now find out if there is a second (other) slot selected for interaction
    int i = 0;
    for (i = 0; i < parent.itemSlots.length; i++){
      if(parent.itemSlots[i].isFilled()   == true &&
         parent.itemSlots[i].isSelected() == true &&
         parent.itemSlots[i] != this){break;}
    } // end for loop

    // at this point, i is either length, or the one we want.
    // if i == length then there were no other slots selected, so do nothing.
    if (i == parent.itemSlots.length){return;}

    // otherwise, user is attempting to transfer item from selected to this slot
    // so, check if that item can fill this slot.

    // first, get rid of gold, as it 'always' works.
    if (parent.itemSlots[i].item.getType() == Item.GOLD){
      // user has attempted to transfer gold to somewhere, so add it to the character's total
      parent.thisCharacter.inventory.goldAmount
          += parent.itemSlots[i].item.getQuantity();
      parent.itemSlots[i].removeItem();
      return; // since we're done with this action
    } // end if gold

    // now check item against slot capacity by type
    if (canHoldItem(parent.itemSlots[i].item) == false){
      parent.feedbackTextArea.append("Selected item cannot be placed in that slot." + MyTextIO.newline);
      return;
    } // end can hold item.

    if (slotNumber == Inventory.OFF_HAND_SLOT){
      // user is attempting to transfer something to the off-hand.
      // need to check that there are no 2Handed weapons equipped.
      if ((parent.itemSlots[Inventory.WEAPON_SLOT_1].isFilled() &&
           parent.itemSlots[Inventory.WEAPON_SLOT_1].item.basics.isTwoHanded()) ||
          (parent.itemSlots[Inventory.WEAPON_SLOT_2].isFilled() &&
           parent.itemSlots[Inventory.WEAPON_SLOT_2].item.basics.isTwoHanded()) ||
          (parent.itemSlots[Inventory.WEAPON_SLOT_3].isFilled() &&
           parent.itemSlots[Inventory.WEAPON_SLOT_3].item.basics.isTwoHanded())){
        parent.feedbackTextArea.append("You cannot equip a shield while wielding a two-handed weapon."
                                            + MyTextIO.newline);
        return;
      } // end if
    } // end set shield with 2H equipped

    // check transfer something to the weapon hand.
    // need to check that there is not a shield equipped.
    if ((slotNumber == Inventory.WEAPON_SLOT_1 ||
         slotNumber == Inventory.WEAPON_SLOT_2 ||
         slotNumber == Inventory.WEAPON_SLOT_3) &&
         parent.itemSlots[i].item.basics.isTwoHanded() &&
         parent.itemSlots[Inventory.OFF_HAND_SLOT].isFilled()){
      parent.feedbackTextArea.append(
      "You cannot equip a two-handed weapon while wielding a shield."
      + MyTextIO.newline);
      return;
    } // end set 2H with shield equipped

    // assuming the item can fit into this slot, now check for character ability:
    if (slotNumber < 19 &&
        parent.itemSlots[i].item.testRequirements(parent.thisCharacter) != true){
      parent.feedbackTextArea.append("You do not currently meet the requirements of this item." + MyTextIO.newline);
      return;
    } // end if slotNumber is equipment

    // at this point, either the character can hold it, or it is just in the pack inventory
    // so fill this slot with item and remove it from that slot.
    setItem(parent.itemSlots[i].item);
    parent.itemSlots[i].removeItem();
    parent.thisCharacter.updateStatsFromInventory();
   } // end if single click and isFilled == false
  } // end if clickcount == 1

//****************************************************************
// Double click handling
//****************************************************************
    if (e.getClickCount() >= 2 && parent.itemSlots[slotNumber].isFilled()){
      // if user double-clicked on an ammo slot...
      if      (slotNumber == Inventory.AMMO_SLOT_1 ||
               slotNumber == Inventory.AMMO_SLOT_2 ||
               slotNumber == Inventory.AMMO_SLOT_3) {
        // then unselect all slots then select this one...
//        Popup.createInfoPopup("unselecting ammo slots...");
        unequipAmmoSlots();
//        Popup.createInfoPopup("selecting slot number: " + slotNumber);
        parent.itemSlots[slotNumber].item.setEquipped(true);
        parent.thisCharacter.inventory.items[slotNumber].setEquipped(true);
/*        Popup.createInfoPopup("Status of quick ammo after double-click:" + MyTextIO.newline
                             + "(invPanel)AmmoSlot 1 equipped: " + parent.itemSlots[Inventory.AMMO_SLOT_1].item.isEquipped() + MyTextIO.newline
                             + "(character)AmmoSlot 1 equipped: " + parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1].isEquipped() + MyTextIO.newline
                             + "(invPanel)AmmoSlot 2 equipped: " + parent.itemSlots[Inventory.AMMO_SLOT_2].item.isEquipped() + MyTextIO.newline
                             + "(character)AmmoSlot 2 equipped: " + parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2].isEquipped() + MyTextIO.newline
                             + "(invPanel)AmmoSlot 3 equipped: " + parent.itemSlots[Inventory.AMMO_SLOT_3].item.isEquipped() + MyTextIO.newline
                             + "(character)AmmoSlot 3 equipped: " + parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3].isEquipped());
*/
    } // end if ammo slots

      // if user double-clicked on a weapon slot...
      else if (slotNumber == Inventory.WEAPON_SLOT_1 ||
               slotNumber == Inventory.WEAPON_SLOT_2 ||
               slotNumber == Inventory.WEAPON_SLOT_3) {
        // then unselect all slots then select this one...
//        Popup.createInfoPopup("unselecting weapon slots...");

        // if user has double-clicked on a different weapon than is highlighted,
        if (parent.itemSlots[slotNumber].item.isEquipped() == false){
          // then give feedback of weapon change
          // at this point, we want to tell the user their ranks with the equipped weapon.
          int lvl = parent.thisCharacter.currentLevel.get(
              parent.thisCharacter.inventory.items[slotNumber].basics.
              getGoverningProficiency());

          parent.feedbackTextArea.setText(
              "You have equipped a weapon of which you have " +
              lvl +
              " skill ranks," + MyTextIO.newline +
              "this applies an adjustment of " +
              Formulas.getBonusForNumRanks(lvl) +
              " points to your attack." + MyTextIO.newline);
          if (slotNumber < 13 &&
              item.basics.getCastingFailure() != 0 &&
              justResetting == false) {
            parent.feedbackTextArea.append(
                "Your casting failure may have changed when switching to that item." +
                MyTextIO.newline);
          } // end if casting failure
        } // end if double-click on different item

        unequipWeaponSlots();
//        Popup.createInfoPopup("selecting slot number: " + slotNumber);
        parent.itemSlots[slotNumber].item.setEquipped(true);
        parent.thisCharacter.inventory.items[slotNumber].setEquipped(true);
/*        Popup.createInfoPopup("Status of quick weapons after double-click:" + MyTextIO.newline
                             + "(invPanel)WpnSlot 1 equipped: " + parent.itemSlots[Inventory.WEAPON_SLOT_1].item.isEquipped() + MyTextIO.newline
                             + "(character)WpnSlot 1 equipped: " + parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1].isEquipped() + MyTextIO.newline
                             + "(invPanel)WpnSlot 2 equipped: " + parent.itemSlots[Inventory.WEAPON_SLOT_2].item.isEquipped() + MyTextIO.newline
                             + "(character)WpnSlot 2 equipped: " + parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2].isEquipped() + MyTextIO.newline
                             + "(invPanel)WpnSlot 3 equipped: " + parent.itemSlots[Inventory.WEAPON_SLOT_3].item.isEquipped() + MyTextIO.newline
                             + "(character)WpnSlot 3 equipped: " + parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3].isEquipped());
*/
      } // end if WEAPON slots

      // update any changes to stats and visual invPanel
      parent.thisCharacter.updateStatsFromInventory();
      parent.refreshInv(parent.thisCharacter);
    } // end if double-click
  } // end mouseClicked

//**************************************************************************
  public void unequipAmmoSlots(){
    // purpose of this method is to unselect (unhighlight) all the quick ammo slots
    // perhaps change to character inventory rather than invPanel itemSlots (more accurate?)

    if (parent.itemSlots[Inventory.AMMO_SLOT_1].isFilled() &&
        parent.itemSlots[Inventory.AMMO_SLOT_1].item != null){
//      Popup.createInfoPopup("Ammo slot 1 filled, setting equipped as false");
      parent.itemSlots[Inventory.AMMO_SLOT_1].item.setEquipped(false);
//      parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1].setEquipped(false);
    } // end if

    if (parent.itemSlots[Inventory.AMMO_SLOT_2].isFilled() &&
        parent.itemSlots[Inventory.AMMO_SLOT_2].item != null){
//      Popup.createInfoPopup("Ammo slot 2 filled, setting equipped as false");
      parent.itemSlots[Inventory.AMMO_SLOT_2].item.setEquipped(false);
//      parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2].setEquipped(false);
    } // end if

    if (parent.itemSlots[Inventory.AMMO_SLOT_3].isFilled() &&
        parent.itemSlots[Inventory.AMMO_SLOT_3].item != null){
//      Popup.createInfoPopup("Ammo slot 3 filled, setting equipped as false");
      parent.itemSlots[Inventory.AMMO_SLOT_3].item.setEquipped(false);
//     parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3].setEquipped(false);
    } // end if

  } // end unselectAmmoSlots()

//**************************************************************************
  public void unequipWeaponSlots(){
    // purpose of this method is to unselect(unhighlight) all the quick weapon slots
    // perhaps change to character inventory rather than invPanel itemSlots (more accurate?)

    if (parent.itemSlots[Inventory.WEAPON_SLOT_1].isFilled() &&
        parent.itemSlots[Inventory.WEAPON_SLOT_1].item != null){
//      Popup.createInfoPopup("Weapon slot 1 filled, setting equipped as false");
      parent.itemSlots[Inventory.WEAPON_SLOT_1].item.setEquipped(false);
//      parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1].setEquipped(false);
    } // end if

    if (parent.itemSlots[Inventory.WEAPON_SLOT_2].isFilled() &&
        parent.itemSlots[Inventory.WEAPON_SLOT_2].item != null){
//      Popup.createInfoPopup("Weapon slot 2 filled, setting equipped as false");
      parent.itemSlots[Inventory.WEAPON_SLOT_2].item.setEquipped(false);
//      parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2].setEquipped(false);
    } // end if

    if (parent.itemSlots[Inventory.WEAPON_SLOT_3].isFilled() &&
        parent.itemSlots[Inventory.WEAPON_SLOT_3].item != null){
//      Popup.createInfoPopup("Weapon slot 3 filled, setting equipped as false");
      parent.itemSlots[Inventory.WEAPON_SLOT_3].item.setEquipped(false);
//      parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3].setEquipped(false);
    } // end if
  } // end unselectWeaponSlots

//**************************************************************************
  public void mouseEntered(MouseEvent e){
    // user's mouse has entered this
    // if mouse is currently dragging, then prepare for possible dropping...
  } // end mouseEntered

//**************************************************************************
  public void mouseExited(MouseEvent e){
    // user's mouse exited this
    // if mouse is pressed, then being dragging this item...
  } // end mouseExited

//**************************************************************************
  public void mousePressed(MouseEvent e){
    // prepare for possible dragging...
  } // end mouse Pressed

//**************************************************************************
  public void mouseReleased(MouseEvent e){
    // user's mouse has released on this
    // if currently dragging, then 'drop' item to this slot...
  } // end mouse released

//**************************************************************************
  public void paintComponent(Graphics g) {
    // this is overriding the paintComponent method ENTIRELY!

    if (backgroundImage != null)
    g.drawImage(Res.getImage(backgroundImage).getImage(), 0,
                                 0,
                                 (int)this.getPreferredSize().getWidth(),
                                 (int)this.getPreferredSize().getHeight(),
                                 0,
                                 0,
                                 Res.getImage(backgroundImage).getImage().getWidth(this),
                                 Res.getImage(backgroundImage).getImage().getHeight(this),
                                 this.getBackground(),
                                 this);

    // we need to draw the quantity if mulitple or common stackable item
    if (item != null &&
        (item.getQuantity() != 1               ||
         item.getType() == item.GOLD           ||
         item.getType() == item.AMMO_ARROW     ||
         item.getType() == item.AMMO_BOLT      ||
         item.getType() == item.AMMO_BULLET    ||
         item.getType() == item.THROWN_DAGGER  ||
         item.getType() == item.THROWN_KNIFE   ||
         item.getType() == item.THROWN_DART    ||
         item.getType() == item.THROWN_AXE     ||
         item.getType() == item.THROWN_STONE   ||
         item.getType() == item.THROWN_JAVELIN
         )){
      g.setColor(Color.white);
      g.setFont(new Font("Arial", Font.PLAIN, 10));
      if (item.getQuantity() < 10){
        g.fillRect(37, 34, 7, 10); // make a one-char wide blank spot
        g.setColor(Color.black);
        g.drawString(String.valueOf(item.getQuantity()), 38, 42);
      } // end if

      else if (item.getQuantity() < 100){
        g.fillRect(31, 34, 14, 10); // make a two-char wide blank spot
        g.setColor(Color.black);
         g.drawString(String.valueOf(item.getQuantity()), 32, 42);
      } // end else if

      else if (item.getQuantity() < 1000){
        g.fillRect(25, 34, 21, 10); // make a three-char wide blank spot
        g.setColor(Color.black);
         g.drawString(String.valueOf(item.getQuantity()), 26, 42);
      } // end else if

      else{
        g.fillRect(19, 34, 28, 10); // make a four-char wide blank spot (NOTE: 9999 max stack, visually)
        g.setColor(Color.black);
        g.drawString(String.valueOf(item.getQuantity()), 20, 42);
      } // end else if
    } // end if

    // now we want to draw the image's special internal border
    // based on special item attributes or item status.
    if (item != null){
      g.setColor(item.getCurrentColor());
      g.drawRect(5, 5, 39, 39); // inside the itemSlot border.
    } // if item not null

  } // end paintComponent

//**************************************************************************
  public void keyPressed (KeyEvent k){
    Popup.createInfoPopup("KeyPressed recognized by the ItemSlot class!" + MyTextIO.newline
                         +"getKeyChar: "  + k.getKeyChar()               + MyTextIO.newline
                         +"getKeyCode: "  + k.getKeyCode()               + MyTextIO.newline
                         +"getKeyText: "  + k.getKeyText(k.getKeyCode()) + MyTextIO.newline
                         +"isActionKey: " + k.isActionKey());

    if (k.getKeyCode() == KeyEvent.VK_ESCAPE){unHighlightAllItemSlots();} // end escape

  } // end keyPressed

//**************************************************************************
  public void keyReleased (KeyEvent k){
    Popup.createInfoPopup("KeyReleased recognized by the ItemSlot class!" + MyTextIO.newline
                         +"getKeyChar: "  + k.getKeyChar()                + MyTextIO.newline
                         +"getKeyCode: "  + k.getKeyCode()                + MyTextIO.newline
                         +"getKeyText: "  + k.getKeyText(k.getKeyCode())  + MyTextIO.newline
                         +"isActionKey: " + k.isActionKey());
  } // end keyReleased

//**************************************************************************
  public void keyTyped (KeyEvent k){
    Popup.createInfoPopup("KeyTyped recognized by the ItemSlot class!"   + MyTextIO.newline
                         +"getKeyChar: "  + k.getKeyChar()               + MyTextIO.newline
                         +"getKeyCode: "  + k.getKeyCode()               + MyTextIO.newline
                         +"getKeyText: "  + k.getKeyText(k.getKeyCode()) + MyTextIO.newline
                         +"isActionKey: " + k.isActionKey());
  } // end keyTyped

//**************************************************************************
// private methods
//**************************************************************************
  void handleSplitStack(Item itemToBeSplit){
    // purpose of this method is to take the passed slotNumber and handle the
    // GUI and data relating to splitting the stack of items at hand,
    // placing the results into the closest empty slot available.
    // NOTE: this assumes that their is a stack of items at the passed slotNumber

    // find out where the first open slot is, then split the stack to that slot.

    int i, j;
    for (i = 21; i < 37; i++){
      if (parent.itemSlots[i].isFilled() == false)break;
    } // end for loop

    if (i < 37){
      StackSplitter.splitStack(itemToBeSplit, parent.itemSlots[i]);
      return;
    } // end if

    // at this point there are no more free inventory slots, so add to the ground/container slot
    for (j = 40; i < 45; i++){
      if (parent.itemSlots[i].isFilled() == false) break;
    } // end for loop

    if (j < 45){
      StackSplitter.splitStack(itemToBeSplit, parent.itemSlots[i]);
    } // end if

    // at this point, if there are no open slots anywher, so send an error message.
    else{
      Popup.createErrorPopup("No empty slots found, splitting failed." + MyTextIO.newline +
                             "Clear inventory space before attempting to split stack.");
      return;
    } // end else

  } // end handleSplitStack

//**************************************************************************
  private int getSelectedSlotNumber(){
    // purpose of this method is to find and return the selected itemSlot.
    // if none, then return null

    for (int i = 0; i < parent.itemSlots.length; i++){
      if (parent.itemSlots[i].isFilled() &&
          parent.itemSlots[i].isSelected() == true) return i;
    } // end for loop
    return -1;
  } // end getSelectedSlot(

//**************************************************************************
  private void unHighlightAllItemSlots(){
    // purpose of this method is to clear the highlighting of the slots,
    // but leave the equipped weapon and ammo slots alone.

    for (int i = 0; i < parent.itemSlots.length; i++){
      if (parent.itemSlots[i].isFilled()){
        parent.itemSlots[i].setSelected(false);
      } // end if
    } // end for loop
  } // end unHighlightAllItemSlots()

//**************************************************************************
  void update(){
    updateBackground();
    updateBorder();
  } // end update

//**************************************************************************
  private void updateBackground(){
    // purpose of this method is to check the state of the itemSlot and
    // draw the appropriate background - either the empty icon or item icon

    if (filled == true)
       setBackgroundImage(item.basics.getImageName());

    else { // if isFilled == false
      setBackgroundImage(getBackgroundImageForSlot(slotNumber));
    } // end else

  } // end updateBackground()

//**************************************************************************
  private void updateBorder(){
    // purpose of this method is to update the color of the border based on
    // the state of the itemSlot.

    // first evaluate the itemSlot and determine the correct color
    // and set the currentColor to be that color.

    // if we don't have a character to match against (stacksplitter),
    // then don't bother updating the border
    if (parent == null) return;

    if (filled == false) currentColor = emptyColor;

    // else (filled and selected)
    else if (isSelected() == true) currentColor = selectedColor;

    // else (filled, not selected, not useable)
    else if (item.testRequirements(parent.thisCharacter) == false) currentColor = notUseableColor;

    // else (filled, not selected, useable)
    else {currentColor = useableColor;}


    // Finally, draw the correct border according to stats.
    if (filled == false){
    setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
                                                 BorderFactory.createCompoundBorder(
                                                   BorderFactory.createLineBorder(currentColor),
                                                   BorderFactory.createLoweredBevelBorder())));
    } // if filled == false

    if (filled == true){
    setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
                                                 BorderFactory.createLineBorder(currentColor)));
    } // end if filled == true

  } // end updateBorder

//*****************************************************************************
 private boolean canHoldItem(Item item){
   // purpose of this method is to analyze the item and the itemSlot
   // and return if the item can be placed in this slot.
   // NOTE: this does not check if the character can equip the item.
   //       this only checks item type for slot matching.
//  Popup.createInfoPopup("Testing slot #" + slotNumber + " against item Type:" + item.getTypeName(item.getType()));

   if (slotNumber == Inventory.AMMO_SLOT_1 ||
       slotNumber == Inventory.AMMO_SLOT_2 ||
       slotNumber == Inventory.AMMO_SLOT_3){
     if (item.getType() == Item.AMMO_ARROW ||
         item.getType() == Item.AMMO_BOLT  ||
         item.getType() == Item.AMMO_BULLET)
     return true;
     else {return false;}
   } // end if ammoSlot

   else if (slotNumber == Inventory.WEAPON_SLOT_1 ||
            slotNumber == Inventory.WEAPON_SLOT_2 ||
            slotNumber == Inventory.WEAPON_SLOT_3 ){
     if (item.isWeapon() || item.isLauncher()) return true;
     else {return false;}
   } // end if weaponSlot

   else if (slotNumber == Inventory.QUICK_SLOT_1 ||
            slotNumber == Inventory.QUICK_SLOT_2 ||
            slotNumber == Inventory.QUICK_SLOT_3 ){
     if (item.getType() == Item.POTION   ||
         item.getType() == Item.SCROLL   ||
         item.getType() == Item.ARTIFACT )
       return true;
     else {return false;}
   } // end if quickSlot

   else if (slotNumber == Inventory.HELMET_SLOT){
     if (item.getType() == Item.HELMET_NORMAL  ||
         item.getType() == Item.HELMET_CIRCLET ||
         item.getType() == Item.HELMET_HOOD    ||
         item.getType() == Item.HELMET_BEAST   )
       return true;
     else {return false;}
   } // end helmet slot

   else if (slotNumber == Inventory.NECK_SLOT){
     if (item.getType() == Item.AMULET)
       return true;
     else {return false;}
   } // end neck slot

   else if (slotNumber == Inventory.RING_LEFT_SLOT ||
            slotNumber == Inventory.RING_RIGHT_SLOT){
     if (item.getType() == Item.RING )
       return true;
     else {return false;}
   } // end ring slots

   else if (slotNumber == Inventory.CUIRASS_SLOT){
     if (item.getType() == Item.CUIRASS_LIGHT  ||
         item.getType() == Item.CUIRASS_MEDIUM ||
         item.getType() == Item.CUIRASS_HEAVY  ||
         item.getType() == Item.CUIRASS_ROBE   )
       return true;
     else {return false;}
   } // end cuirass slot

   else if (slotNumber == Inventory.CLOAK_SLOT){
     if (item.getType() == Item.CLOAK)
       return true;
     else {return false;}
   } // end cloak slot

   else if (slotNumber == Inventory.OFF_HAND_SLOT){
     if (item.getType() == Item.SHIELD_BUCKLER ||
         item.getType() == Item.SHIELD_ROUND   ||
         item.getType() == Item.SHIELD_MEDIUM  ||
         item.getType() == Item.SHIELD_TOWER   )
       return true;
     else {return false;}
   } // end off-hand slot

   else if (slotNumber == Inventory.FOREARMS_SLOT){
     if (item.getType() == Item.ARMS_BRACERS   ||
         item.getType() == Item.ARMS_GAUNTLETS ||
         item.getType() == Item.ARMS_GLOVES    )
       return true;
     else {return false;}
   } // end arms slot

   else if (slotNumber == Inventory.BELT_SLOT){
     if (item.getType() == Item.BELT_LIGHT ||
         item.getType() == Item.BELT_HEAVY)
       return true;
     else {return false;}
   } // end belt slot

   else if (slotNumber == Inventory.BOOTS_SLOT){
     if (item.getType() == Item.BOOTS_SOFT ||
         item.getType() == Item.BOOTS_HARD ||
         item.getType() == Item.BOOTS_GREAVES)
       return true;
     else {return false;}
   } // end boots slot

   else {
//     Popup.createInfoPopup("Finished checking specifics, so return true.");
     return true; // this takes care of ground slots and pack slots
   } // end else

 } // end canHoldItem

//**************************************************************************
  private String getBackgroundImageForSlot(int slotNumber){
    // purpose of this method is to look up the slotNumber and return the correct
    // background image.
    // basically, if it is special, then return the correct image, otherwise
    // just return the standard black slot.
    // Remember this index corresponds to the character's inventory.

    if      (slotNumber == Inventory.HELMET_SLOT)    {return FileNames.EMPTY_HELMET;}
    else if (slotNumber == Inventory.NECK_SLOT)      {return FileNames.EMPTY_AMULET;}
    else if (slotNumber == Inventory.CLOAK_SLOT)     {return FileNames.EMPTY_CLOAK;}
    else if (slotNumber == Inventory.CUIRASS_SLOT)   {return FileNames.EMPTY_CUIRASS;}
    else if (slotNumber == Inventory.WEAPON_SLOT_1)  {return FileNames.EMPTY_WEAPON;}
    else if (slotNumber == Inventory.WEAPON_SLOT_2)  {return FileNames.EMPTY_WEAPON;}
    else if (slotNumber == Inventory.WEAPON_SLOT_3)  {return FileNames.EMPTY_WEAPON;}
    else if (slotNumber == Inventory.OFF_HAND_SLOT)  {return FileNames.EMPTY_SHIELD;}
    else if (slotNumber == Inventory.RING_LEFT_SLOT) {return FileNames.EMPTY_RING;}
    else if (slotNumber == Inventory.RING_RIGHT_SLOT){return FileNames.EMPTY_RING;}
    else if (slotNumber == Inventory.FOREARMS_SLOT)  {return FileNames.EMPTY_FOREARMS;}
    else if (slotNumber == Inventory.BELT_SLOT)      {return FileNames.EMPTY_BELT;}
    else if (slotNumber == Inventory.BOOTS_SLOT)     {return FileNames.EMPTY_BOOTS;}
    else if (slotNumber == Inventory.AMMO_SLOT_1)    {return FileNames.EMPTY_AMMO;}
    else if (slotNumber == Inventory.AMMO_SLOT_2)    {return FileNames.EMPTY_AMMO;}
    else if (slotNumber == Inventory.AMMO_SLOT_3)    {return FileNames.EMPTY_AMMO;}
    else {return FileNames.EMPTY_ITEMSLOT;}

  } // end getBackgroundImageForSlot(slot)

//**************************************************************************
  private void resetItemDetailButtons(){
    // the purpose of this method is to update the buttons that are found within
    // the item's details panel, because the item changes position and changes state.

    // check and update the split button
    if (item.getQuantity() > 1){
      item.splitButton.setEnabled(true);
//      item.splitButton.addActionListener(this); *WARNING* this causes NotSerializable error!
    } // end if

    else{ // quantity  = 1
      item.splitButton.setEnabled(false);
//      item.splitButton.removeActionListener(this);
    } // end else

    // check and update the equip button
    if (slotNumber == Inventory.AMMO_SLOT_1   ||
        slotNumber == Inventory.AMMO_SLOT_2   ||
        slotNumber == Inventory.AMMO_SLOT_3   ||
        slotNumber == Inventory.WEAPON_SLOT_1 ||
        slotNumber == Inventory.WEAPON_SLOT_2 ||
        slotNumber == Inventory.WEAPON_SLOT_3){
      item.equipButton.setEnabled(true);
    } // end if

    else{ // slotNumber != AMMO or WEAPON
      item.equipButton.setEnabled(false);
      item.equipButton.removeActionListener(this);
    } // end else

  } // end resetItemDetailsButtons

//**************************************************************************
  void equipThisItem(Item item, int slotNumber){
    // purpose of this item is to equip this item out of its possible peers.
    // NOTE: the following code has been directly copied from the double-click section of mouseClicked();

//    Popup.createInfoPopup("attempting to set this item.");

      // if user double-clicked on an ammo slot...
      if      (slotNumber == Inventory.AMMO_SLOT_1 ||
               slotNumber == Inventory.AMMO_SLOT_2 ||
               slotNumber == Inventory.AMMO_SLOT_3) {
        // then unselect all slots then select this one...
//        Popup.createInfoPopup("unselecting ammo slots...");
        unequipAmmoSlots();
//        Popup.createInfoPopup("selecting slot number: " + slotNumber);
        parent.itemSlots[slotNumber].item.setEquipped(true);
        parent.thisCharacter.inventory.items[slotNumber].setEquipped(true);
/*        Popup.createInfoPopup("Status of quick ammo after double-click:" + MyTextIO.newline
                             + "(invPanel)AmmoSlot 1 equipped: " + parent.itemSlots[Inventory.AMMO_SLOT_1].item.isEquipped() + MyTextIO.newline
                             + "(character)AmmoSlot 1 equipped: " + parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_1].isEquipped() + MyTextIO.newline
                             + "(invPanel)AmmoSlot 2 equipped: " + parent.itemSlots[Inventory.AMMO_SLOT_2].item.isEquipped() + MyTextIO.newline
                             + "(character)AmmoSlot 2 equipped: " + parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_2].isEquipped() + MyTextIO.newline
                             + "(invPanel)AmmoSlot 3 equipped: " + parent.itemSlots[Inventory.AMMO_SLOT_3].item.isEquipped() + MyTextIO.newline
                             + "(character)AmmoSlot 3 equipped: " + parent.thisCharacter.inventory.items[Inventory.AMMO_SLOT_3].isEquipped());
*/
    } // end if ammo slots

      // if user double-clicked on a weapon slot...
      else if (slotNumber == Inventory.WEAPON_SLOT_1 ||
               slotNumber == Inventory.WEAPON_SLOT_2 ||
               slotNumber == Inventory.WEAPON_SLOT_3) {
        // then unselect all slots then select this one...
//        Popup.createInfoPopup("unselecting weapon slots...");

        // if user has double-clicked on a different weapon than is highlighted,
        if (parent.itemSlots[slotNumber].item.isEquipped() == false){
          // then give feedback of weapon change
          // at this point, we want to tell the user their ranks with the equipped weapon.
          int lvl = parent.thisCharacter.currentLevel.get(
              parent.thisCharacter.inventory.items[slotNumber].basics.
              getGoverningProficiency());

          parent.feedbackTextArea.setText(
              "You have equipped a weapon of which you have " +
              lvl +
              " skill ranks," + MyTextIO.newline +
              "this applies an adjustment of " +
              Formulas.getBonusForNumRanks(lvl) +
              " points to your attack." + MyTextIO.newline);
          if (slotNumber < 13 &&
              item.basics.getCastingFailure() != 0 &&
              justResetting == false) {
            parent.feedbackTextArea.append(
                "Your casting failure may have changed when switching to that item." +
                MyTextIO.newline);
          } // end if casting failure
        } // end if double-click on different item

        unequipWeaponSlots();
//        Popup.createInfoPopup("selecting slot number: " + slotNumber);
        parent.itemSlots[slotNumber].item.setEquipped(true);
        parent.thisCharacter.inventory.items[slotNumber].setEquipped(true);
/*        Popup.createInfoPopup("Status of quick weapons after double-click:" + MyTextIO.newline
                             + "(invPanel)WpnSlot 1 equipped: " + parent.itemSlots[Inventory.WEAPON_SLOT_1].item.isEquipped() + MyTextIO.newline
                             + "(character)WpnSlot 1 equipped: " + parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_1].isEquipped() + MyTextIO.newline
                             + "(invPanel)WpnSlot 2 equipped: " + parent.itemSlots[Inventory.WEAPON_SLOT_2].item.isEquipped() + MyTextIO.newline
                             + "(character)WpnSlot 2 equipped: " + parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_2].isEquipped() + MyTextIO.newline
                             + "(invPanel)WpnSlot 3 equipped: " + parent.itemSlots[Inventory.WEAPON_SLOT_3].item.isEquipped() + MyTextIO.newline
                             + "(character)WpnSlot 3 equipped: " + parent.thisCharacter.inventory.items[Inventory.WEAPON_SLOT_3].isEquipped());
*/
      } // end if WEAPON slots

      // update any changes to stats and visual invPanel
      parent.thisCharacter.updateStatsFromInventory();
      parent.refreshInv(parent.thisCharacter);

  } // end equipThisItem

//**************************************************************************
} // end class ItemSlot
