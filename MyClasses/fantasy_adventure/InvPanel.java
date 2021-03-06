package fantasy_adventure;

import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.*;

class InvPanel extends JPanelWithBackground implements ActionListener{

//****************************************************************************
// static variables
    static int WIDTH              = Constants.CENTRALPANEL_WIDTH;
    static int HEIGHT             = Constants.CENTRALPANEL_HEIGHT;
    static int TITLE_HEIGHT       = (int)HEIGHT / 20;
    static int FONT_SIZE          = (int)(HEIGHT / 30);
    static int BTN_SECTION_HEIGHT = (int)(HEIGHT / 9);
    static int MAIN_PANEL_HEIGHT  = (HEIGHT - (TITLE_HEIGHT + BTN_SECTION_HEIGHT + 6));
    static int INV_SPACER_H       = (int)(Math.max((MAIN_PANEL_HEIGHT / 80) - 10, 2));
    static int INV_SPACER_W       = (int)(WIDTH / 30);
    static int INV_HEIGHT         = (INV_SPACER_H + ItemSlot.ITEMSLOT_HEIGHT + 10) * 2;
    static int INV_WIDTH          = (INV_SPACER_W + ItemSlot.ITEMSLOT_WIDTH + 8) * 8;
    static int LEFT_RIGHT_HEIGHT  = MAIN_PANEL_HEIGHT - INV_HEIGHT - 10;
    static int LABEL_V_GAP        = (int)(HEIGHT / 200);

    static String PACK_FILE       = Constants.INSTALL_DIRECTORY + "/Images/items/pack.jpg";
    static String GOLD_FILE       = Constants.INSTALL_DIRECTORY + "/Images/items/bag.jpg";
    static String INV_PIC_FILE    = Constants.INSTALL_DIRECTORY + "/Images/items/inventoryBackground.jpg";

//****************************************************************************
// member variables

  JPanelWithBackground equippedSection;

  JPanel  titleBar,
          mainPanel, // holds both left and right panel
          invSection,
          btnSection,

          leftPanel,  // holds left 1/3 of screen
          ammoSection,
          wpnSection,
          quickSection,
          groundSection,
            groundRow1,
            groundRow2,

          rightPanel, // holds 2/3 of screen
            leftColumn,
            centerColumn,
            rightColumn,
          infoSection,
          detailsSection,
            pointsBox,
            HPMPPanel,
            attDefPanel,
          packSection;

  JScrollPane  feedbackSection;

  JTextArea feedbackTextArea;

  JLabel  titleLabel,
          weightLabel,
          goldLabel,
          HPLabel,
          MPLabel,
          attLabel,
          defLabel;

  JButton descButton,
          dropButton,
          charInfoButton,
          miscButton;

  ItemSlot[] itemSlots;

  int     weightAllowance; // keeps track of current character's weight allowance

  SocialObject thisCharacter;

//****************************************************************************
// constructor
//****************************************************************************
  public InvPanel () {
    // start with parent (JPanel stuff)
    super(FileNames.SESSION_TEXTURE,
         JPanelWithBackground.TILE);

    // setup itemSlots
    itemSlots = new ItemSlot[46]; // 40 to match character, 6 for ground/container

    for (int i = 0; i < itemSlots.length; i++){
      // for each itemSlot, create, and fill with empty by default.
      itemSlots[i] = new ItemSlot(i, this);
    } // end for loop

    // setup main panel options
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // create macro section
    titleBar = new JPanel();
    titleBar.setPreferredSize(new Dimension(WIDTH, TITLE_HEIGHT));
    titleBar.setBorder(BorderFactory.createRaisedBevelBorder());
    titleBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // create micro details
    titleLabel = new JLabel("Inventory");
    titleLabel.setPreferredSize(titleBar.getPreferredSize());
    titleLabel.setFont(new Font("Arial", Font.ITALIC, FONT_SIZE));
    titleLabel.setHorizontalAlignment(JLabel.CENTER);

    // add details to macro section
    titleBar.add(titleLabel);

    // reate macro section
    btnSection = new JPanel();
    btnSection.setPreferredSize(new Dimension(WIDTH, BTN_SECTION_HEIGHT));
    btnSection.setBorder(BorderFactory.createLineBorder(Color.black));

    // create micro details
    descButton     = makeInvPanelButton("Item Description");
    dropButton     = makeInvPanelButton("Drop Item to Ground");
    charInfoButton = makeInvPanelButton("Character Info");
    miscButton     = makeInvPanelButton("Generate Random Item");

    // add details to macro section
    btnSection.add(descButton);
    btnSection.add(dropButton);
    btnSection.add(charInfoButton);
    btnSection.add(miscButton);

    // create container panels
    mainPanel = new JPanel();
    mainPanel.setPreferredSize(new Dimension(WIDTH - 4, MAIN_PANEL_HEIGHT));
    mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // create macro section
    invSection = new JPanel();
    invSection.setPreferredSize(new Dimension(INV_WIDTH, INV_HEIGHT));
    invSection.setLayout(new FlowLayout(FlowLayout.CENTER, INV_SPACER_W, INV_SPACER_H));
    invSection.setBorder(BorderFactory.createTitledBorder("Inventory"));

    // create micro details
    // add details to macro section

    invSection.add(itemSlots[Inventory.PACK_SLOT_1]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_2]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_3]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_4]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_5]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_6]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_7]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_8]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_9]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_10]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_11]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_12]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_13]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_14]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_15]);
    invSection.add(itemSlots[Inventory.PACK_SLOT_16]);

    // create macro section
    leftPanel = new JPanel();
    leftPanel.setPreferredSize(new Dimension((int)(WIDTH / 3) - 4, LEFT_RIGHT_HEIGHT));
    leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

    rightPanel = new JPanel();
    rightPanel.setPreferredSize(new Dimension((int)(WIDTH * 2 / 3) - 4, LEFT_RIGHT_HEIGHT));
    rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

    // create macro section
    ammoSection = new JPanel();
    ammoSection.setPreferredSize(new Dimension((int)leftPanel.getPreferredSize().getWidth()  - 2,
                                               ((int)(leftPanel.getPreferredSize().getHeight() / 5) + 4)));
    ammoSection.setBorder(BorderFactory.createTitledBorder("Launcher Ammo"));
    ammoSection.setLayout(new FlowLayout(FlowLayout.CENTER, INV_SPACER_W - 5, INV_SPACER_H));

    ammoSection.add(itemSlots[Inventory.AMMO_SLOT_1]);
    ammoSection.add(itemSlots[Inventory.AMMO_SLOT_2]);
    ammoSection.add(itemSlots[Inventory.AMMO_SLOT_3]);

    wpnSection = new JPanel();
    wpnSection.setPreferredSize(new Dimension((int)leftPanel.getPreferredSize().getWidth() - 2,
                                              ((int)(leftPanel.getPreferredSize().getHeight() / 5)) + 4));
    wpnSection.setBorder(BorderFactory.createTitledBorder("Quick Weapons"));
    wpnSection.setLayout(new FlowLayout(FlowLayout.CENTER, INV_SPACER_W - 5, INV_SPACER_H));

    wpnSection.add(itemSlots[Inventory.WEAPON_SLOT_1]);
    wpnSection.add(itemSlots[Inventory.WEAPON_SLOT_2]);
    wpnSection.add(itemSlots[Inventory.WEAPON_SLOT_3]);

    quickSection = new JPanel();
    quickSection.setPreferredSize(new Dimension((int)leftPanel.getPreferredSize().getWidth() - 2,
                                                ((int)(leftPanel.getPreferredSize().getHeight() / 5)) + 4));
    quickSection.setBorder(BorderFactory.createTitledBorder("Quick Items"));
    quickSection.setLayout(new FlowLayout(FlowLayout.CENTER, INV_SPACER_W - 5, INV_SPACER_H));

    quickSection.add(itemSlots[Inventory.QUICK_SLOT_1]);
    quickSection.add(itemSlots[Inventory.QUICK_SLOT_2]);
    quickSection.add(itemSlots[Inventory.QUICK_SLOT_3]);

    groundSection = new JPanel();
    groundSection.setPreferredSize(new Dimension((int)leftPanel.getPreferredSize().getWidth() - 2,
                                                 ((int)(leftPanel.getPreferredSize().getHeight() * 2 / 5) - 12)));
    groundSection.setBorder(BorderFactory.createTitledBorder("Ground / Container"));
//    groundSection.setBackground(Constants.HAIR_COLOR); // brown
    groundSection.setLayout(new FlowLayout(FlowLayout.CENTER, (int)groundSection.getPreferredSize().getWidth(), (INV_SPACER_H * 2) + 15));

    // create micro details
    groundRow1 = new JPanel();
    groundRow1.setLayout(new FlowLayout(FlowLayout.CENTER, INV_SPACER_W - 5, (INV_SPACER_H * 2)));
    groundRow1.setPreferredSize(new Dimension((int)groundSection.getPreferredSize().getWidth() - 6,
                                ItemSlot.ITEMSLOT_HEIGHT + 10));
    groundRow1.add(itemSlots[Inventory.GROUND_SLOT_1]);
    groundRow1.add(itemSlots[Inventory.GROUND_SLOT_2]);
    groundRow1.add(itemSlots[Inventory.GROUND_SLOT_3]);

    groundRow2 = new JPanel();
    groundRow2.setLayout(new FlowLayout(FlowLayout.CENTER, INV_SPACER_W - 5, (INV_SPACER_H * 2)));
    groundRow2.setPreferredSize(new Dimension((int)groundSection.getPreferredSize().getWidth() - 6,
                                ItemSlot.ITEMSLOT_HEIGHT + 10));
    groundRow2.add(itemSlots[Inventory.GROUND_SLOT_4]);
    groundRow2.add(itemSlots[Inventory.GROUND_SLOT_5]);
    groundRow2.add(itemSlots[Inventory.GROUND_SLOT_6]);

    // add details to macro section
    groundSection.setLayout(new FlowLayout(FlowLayout.CENTER,
                                          (int)groundSection.getPreferredSize().getWidth(),
                                          (int)(groundSection.getPreferredSize().getHeight()
                                            - groundRow1.getPreferredSize().getHeight()
                                            - groundRow2.getPreferredSize().getHeight()) / 4 - 6));
    groundSection.add(groundRow1);
    groundSection.add(groundRow2);

    // create macro section
    feedbackTextArea = MyUtils.makeTextBox((int)(WIDTH * 2 / 3) - 20, 4, Constants.YELLOW_SOFT, Color.black, null);
    feedbackTextArea.setAutoscrolls(true);
    feedbackSection = new JScrollPane(feedbackTextArea);
    feedbackSection.setPreferredSize(new Dimension((int)(WIDTH * 2 / 3) - 2, (int)(HEIGHT / 8)));
    feedbackSection.setBackground(Color.black);

    // create micro details

    // add details to macro section

    // create macro section
    equippedSection = new JPanelWithBackground(
                            FileNames.INV_EQUIP_BACKGROUND,
                            JPanelWithBackground.STRETCH);

    equippedSection.setPreferredSize(new Dimension((int)(rightPanel.getPreferredSize().getWidth() / 2) - 2,
                                                   (int)(rightPanel.getPreferredSize().getHeight() - 2
                                                        - feedbackSection.getPreferredSize().getHeight())));
    equippedSection.setBorder(BorderFactory.createTitledBorder("Equipment"));
    equippedSection.setBackground(Color.white);
    equippedSection.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // create micro details
    leftColumn = new JPanel();
    leftColumn.setOpaque(false);
//    leftColumn.setBorder(BorderFactory.createLineBorder(Color.black));
    leftColumn.setPreferredSize(new Dimension((int)equippedSection.getPreferredSize().getWidth() / 3 - 5,
                                              (int)equippedSection.getPreferredSize().getHeight() - 2));
    leftColumn.setLayout(new FlowLayout(FlowLayout.CENTER, (int)(INV_SPACER_W / 3) + 100,
                                       (int)(leftColumn.getPreferredSize().getHeight()
                                             - (3 * ItemSlot.ITEMSLOT_HEIGHT)) / 5));

    leftColumn.add(itemSlots[Inventory.CLOAK_SLOT]);
    leftColumn.add(itemSlots[Inventory.FOREARMS_SLOT]);
    leftColumn.add(itemSlots[Inventory.RING_LEFT_SLOT]);

    centerColumn = new JPanel();
    centerColumn.setOpaque(false);
//    centerColumn.setBorder(BorderFactory.createLineBorder(Color.black));
    centerColumn.setPreferredSize(new Dimension((int)equippedSection.getPreferredSize().getWidth() / 3 - 5,
                                              (int)equippedSection.getPreferredSize().getHeight() - 2));
    centerColumn.setLayout(new FlowLayout(FlowLayout.CENTER, (int)(INV_SPACER_W / 3) + 100,
                                         (int)(leftColumn.getPreferredSize().getHeight()
                                           - (4 * ItemSlot.ITEMSLOT_HEIGHT)) / 5));


    centerColumn.add(itemSlots[Inventory.HELMET_SLOT]);
    centerColumn.add(itemSlots[Inventory.CUIRASS_SLOT]);
    centerColumn.add(itemSlots[Inventory.BELT_SLOT]);
    centerColumn.add(itemSlots[Inventory.BOOTS_SLOT]);

    rightColumn = new JPanel();
    rightColumn.setOpaque(false);
//    rightColumn.setBorder(BorderFactory.createLineBorder(Color.black));
    rightColumn.setPreferredSize(new Dimension((int)equippedSection.getPreferredSize().getWidth() / 3 - 5,
                                              (int)equippedSection.getPreferredSize().getHeight() - 2));
    rightColumn.setLayout(new FlowLayout(FlowLayout.CENTER, (int)(INV_SPACER_W / 3) + 100,
                                        (int)(leftColumn.getPreferredSize().getHeight()
                                            - (3 * ItemSlot.ITEMSLOT_HEIGHT)) / 5));


    rightColumn.add(itemSlots[Inventory.NECK_SLOT]);
    rightColumn.add(itemSlots[Inventory.OFF_HAND_SLOT]);
    rightColumn.add(itemSlots[Inventory.RING_RIGHT_SLOT]);

    // add details to macro section

    // attempt to add picture to background, then add normal stuff.

    equippedSection.add(leftColumn);
    equippedSection.add(centerColumn);
    equippedSection.add(rightColumn);

    // create macro section
    infoSection = new JPanel();
    infoSection.setPreferredSize(new Dimension((int)(rightPanel.getPreferredSize().getWidth() / 2) - 2,
                                                   (int)(rightPanel.getPreferredSize().getHeight() - 2
                                                        - feedbackSection.getPreferredSize().getHeight())));

//    infoSection.setBorder(BorderFactory.createLineBorder(Color.orange));
    infoSection.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // create micro details

    // add details to macro section
    detailsSection = new JPanel();
    detailsSection.setPreferredSize(new Dimension((int)infoSection.getPreferredSize().getWidth() - 2,
                                    173));
//    detailsSection.setBorder(BorderFactory.createLineBorder(Color.green));
    detailsSection.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    //*************************
    pointsBox = new JPanel();
    pointsBox.setPreferredSize(new Dimension(160, 180));
    pointsBox.setLayout(new FlowLayout(FlowLayout.CENTER, 50, LABEL_V_GAP));

    HPMPPanel = new JPanel();
    HPMPPanel.setPreferredSize(new Dimension(160, 50));
    HPMPPanel.setBorder (BorderFactory. createLineBorder(Color.black));
    HPMPPanel.setLayout (new FlowLayout(FlowLayout.CENTER, 100,LABEL_V_GAP));

    HPLabel = new JLabel(" / ");
      HPLabel.setForeground(Color.red);
    MPLabel = new JLabel(" / ");
      MPLabel.setForeground(Color.blue);

    HPMPPanel.add(HPLabel);
    HPMPPanel.add(MPLabel);

    attDefPanel = new JPanel();
    attDefPanel.setPreferredSize(new Dimension(160,115));
    attDefPanel.setBorder (BorderFactory. createLineBorder(Color.black));
    attDefPanel.setLayout (new FlowLayout(FlowLayout.CENTER, 10,LABEL_V_GAP));

    attLabel = new JLabel("");
      attLabel.setForeground(Constants.GREEN_DARK);
      attLabel.setPreferredSize(new Dimension(145, 50));

    defLabel = new JLabel("");
      defLabel.setForeground(Constants.HAIR_COLOR); // brown
      defLabel.setPreferredSize(new Dimension(145, 50));

    attDefPanel.add(attLabel);
    attDefPanel.add(defLabel);

    pointsBox.add(HPMPPanel);
    pointsBox.add(attDefPanel);

    detailsSection.add(pointsBox);

    packSection = new JPanel();
    packSection.setPreferredSize(new Dimension((int) (infoSection.getPreferredSize().getWidth() - 2),
                                               (int)(infoSection.getPreferredSize().getHeight()
                                                      - detailsSection.getPreferredSize().getHeight() - 10)));

    packSection.setLayout(new FlowLayout(FlowLayout.CENTER,
                                        (int)packSection.getPreferredSize().getWidth(),
                                        (int)packSection.getPreferredSize().getHeight() / 15));

    weightLabel = new JLabel("Weight: 0 / 0");
    weightLabel.setBackground(Color.black);
    weightLabel.setIcon(new ImageIcon(PACK_FILE));
    goldLabel   = new JLabel("Gold: 0");
    goldLabel.setIcon(new ImageIcon(GOLD_FILE));

    packSection.add(weightLabel);
    packSection.add(goldLabel);

    infoSection.add(detailsSection);
    infoSection.add(packSection);

    // add sections to main container panels

    leftPanel.add(ammoSection);
    leftPanel.add(wpnSection);
    leftPanel.add(quickSection);
    leftPanel.add(groundSection);

    rightPanel.add(equippedSection);
    rightPanel.add(infoSection);
    rightPanel.add(feedbackSection);

    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);
    mainPanel.add(invSection);

    add(titleBar);
    add(mainPanel);
    add(btnSection);

  } // end constructor

//****************************************************************************
// public / package methods
//****************************************************************************
  public void refreshInv(SocialObject character){
    // this needs to be called in order to load all the specific information from
    // the selected character

    if (character == null){
      if(Constants.debugMode == true)
        Popup.createWarningPopup("attempted to refereshInv with a null character, aborting...");
      return;
    } // if null

    thisCharacter = character;

    // to avoid sending excess feedback, set the justResetting variable to true
    ItemSlot.justResetting = true;

    // titleBar section************************************************************
    titleLabel.setText(thisCharacter.getShortName() + "'s Inventory");

    // ammoWpnItmSection************************************************************
    itemSlots[Inventory.AMMO_SLOT_1]    .setItem(thisCharacter.inventory.getAmmoSlot1());
    itemSlots[Inventory.AMMO_SLOT_2]    .setItem(thisCharacter.inventory.getAmmoSlot2());
    itemSlots[Inventory.AMMO_SLOT_3]    .setItem(thisCharacter.inventory.getAmmoSlot3());

    itemSlots[Inventory.WEAPON_SLOT_1]  .setItem(thisCharacter.inventory.getWeaponSlot1());
    itemSlots[Inventory.WEAPON_SLOT_2]  .setItem(thisCharacter.inventory.getWeaponSlot2());
    itemSlots[Inventory.WEAPON_SLOT_3]  .setItem(thisCharacter.inventory.getWeaponSlot3());

    itemSlots[Inventory.QUICK_SLOT_1]   .setItem(thisCharacter.inventory.getQuickSlot1());
    itemSlots[Inventory.QUICK_SLOT_2]   .setItem(thisCharacter.inventory.getQuickSlot2());
    itemSlots[Inventory.QUICK_SLOT_3]   .setItem(thisCharacter.inventory.getQuickSlot3());

    // equippedSection************************************************************
    itemSlots[Inventory.HELMET_SLOT]    .setItem(thisCharacter.inventory.getHelmet());
    itemSlots[Inventory.CLOAK_SLOT]     .setItem(thisCharacter.inventory.getCloakSlot());
    itemSlots[Inventory.NECK_SLOT]      .setItem(thisCharacter.inventory.getNeckSlot());
    itemSlots[Inventory.CUIRASS_SLOT]   .setItem(thisCharacter.inventory.getCuirassSlot());
    itemSlots[Inventory.OFF_HAND_SLOT]  .setItem(thisCharacter.inventory.getOffHandSlot());
    itemSlots[Inventory.FOREARMS_SLOT]  .setItem(thisCharacter.inventory.getForearmsSlot());
    itemSlots[Inventory.BELT_SLOT]      .setItem(thisCharacter.inventory.getBeltSlot());
    itemSlots[Inventory.RING_LEFT_SLOT] .setItem(thisCharacter.inventory.getRingLeftSlot());
    itemSlots[Inventory.RING_RIGHT_SLOT].setItem(thisCharacter.inventory.getRingRightSlot());
    itemSlots[Inventory.BOOTS_SLOT]     .setItem(thisCharacter.inventory.getBootsSlot());

    // invSection************************************************************
    itemSlots[Inventory.PACK_SLOT_1]    .setItem(thisCharacter.inventory.getPackSlot1());
    itemSlots[Inventory.PACK_SLOT_2]    .setItem(thisCharacter.inventory.getPackSlot2());
    itemSlots[Inventory.PACK_SLOT_3]    .setItem(thisCharacter.inventory.getPackSlot3());
    itemSlots[Inventory.PACK_SLOT_4]    .setItem(thisCharacter.inventory.getPackSlot4());
    itemSlots[Inventory.PACK_SLOT_5]    .setItem(thisCharacter.inventory.getPackSlot5());
    itemSlots[Inventory.PACK_SLOT_6]    .setItem(thisCharacter.inventory.getPackSlot6());
    itemSlots[Inventory.PACK_SLOT_7]    .setItem(thisCharacter.inventory.getPackSlot7());
    itemSlots[Inventory.PACK_SLOT_8]    .setItem(thisCharacter.inventory.getPackSlot8());
    itemSlots[Inventory.PACK_SLOT_9]    .setItem(thisCharacter.inventory.getPackSlot9());
    itemSlots[Inventory.PACK_SLOT_10]   .setItem(thisCharacter.inventory.getPackSlot10());
    itemSlots[Inventory.PACK_SLOT_11]   .setItem(thisCharacter.inventory.getPackSlot11());
    itemSlots[Inventory.PACK_SLOT_12]   .setItem(thisCharacter.inventory.getPackSlot12());
    itemSlots[Inventory.PACK_SLOT_13]   .setItem(thisCharacter.inventory.getPackSlot13());
    itemSlots[Inventory.PACK_SLOT_14]   .setItem(thisCharacter.inventory.getPackSlot14());
    itemSlots[Inventory.PACK_SLOT_15]   .setItem(thisCharacter.inventory.getPackSlot15());
    itemSlots[Inventory.PACK_SLOT_16]   .setItem(thisCharacter.inventory.getPackSlot16());

    // now that we're done resetting the itemSlots, we can turn back on the feedback
    ItemSlot.justResetting = false;

    refreshInfo(thisCharacter);

  } // end refreshInv

  void refreshInfo(SocialObject thisCharacter){
      // update weight allowance before displaying
  weightAllowance = Formulas.getWeightAllowance(thisCharacter.getTempStr());

  // add weight bonus depending on belt worn
  if (itemSlots[Inventory.BELT_SLOT].isFilled() == true){
      weightAllowance += ((Belt)(itemSlots[Inventory.BELT_SLOT].item)).getWeightBonus();
  } // end if has belt, add weight bonus

  // color label correctly, depending on total weight.
  thisCharacter.getInventory().calcInvWeight();

  if (thisCharacter.getInventory().getTotalWeight() <= (.9 * weightAllowance))
        weightLabel.setForeground(Constants.GREEN_DARK);

  if (thisCharacter.getInventory().getTotalWeight() > (.9 * weightAllowance))
        weightLabel.setForeground(Constants.YELLOW_DARK);

  if (thisCharacter.getInventory().getTotalWeight() > weightAllowance)
        weightLabel.setForeground(Constants.RED_DARK);

  weightLabel.setText("Weight: " + thisCharacter.getInventory().getTotalWeight()
                      + " / " + weightAllowance);

  goldLabel.setText("Gold: " + thisCharacter.getInventory().getGold());

  // infoSection / pointsArea ************************************************************
  HPLabel.setText("HP: " + thisCharacter.getCurrentHP() + " / " + thisCharacter.getTempMaxHP());
  MPLabel.setText("MP: " + thisCharacter.getCurrentMP() + " / " + thisCharacter.getTempMaxMP());

  // infoSection / att/def area************************************************************
  attLabel.setText("<html>" + thisCharacter.getMeleeAttack()    + " - Melee Attack"    + "<br>" +
                              thisCharacter.getThrownAttack()   + " - Thrown Attack"   + "<br>" +
                              thisCharacter.getLauncherAttack() + " - Launcher Attack" + "</html>");

  defLabel.setText("<html>" + thisCharacter.getNaturalDefense()   + " - Natural Defense"   + "<br>" +
                              thisCharacter.getEquipmentDefense() + " - Equipment Defense" + "<br>" +
                              thisCharacter.getTotalDefense()     + " - Total Defense"     + "</html>");

  // feedbackSection************************************************************
  // btnSection************************************************************

  // now update all items and itemslots to reflect (possibly new) character stats.
  testItemReqs(thisCharacter);

  } // end refreshInfo

//****************************************************************************
  void testItemReqs(LivingObject character){
    // purpose of this method is to update all the itemslots from the character stats.
    // specifically, this checks the pack and ground slots.

    for (int i = 0; i < itemSlots.length; i++){
      // for each itemSlot...

      // if empty, then skip
      if (itemSlots[i].isFilled() == false) continue;

      else { // if itemSlots[i].isFilled == true

        // test the item against the character stats.
        itemSlots[i].item.testRequirements(thisCharacter);
        itemSlots[i].update();

      } // end else (filled)
    } // end for loop
  } // end testItemReqs

//****************************************************************************
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();

    if (command.equals("Item Description")){
      // user has chosen to view the description on the item selected.
      // first check that there is an item selected
      // if not, then do nothing
      // if so, then show a new dialog/JFrame (modal?) that displays the description.
      showItemDetails();

    } // end if

    else if (command.equals("Drop Item to Ground")){
      // user has chosen to drop the selected item to the ground
      // first check that there is an item selected
      ItemSlot thisItemSlot = getHighlightedItemSlot();

      if (thisItemSlot == null || thisItemSlot.isFilled() == false){
        return;
      } // end if itemslot is empty

      else {
        // add this item to the first empty ground slot.  If not empty, then reset and add to ground.
        addToGround(thisItemSlot.item);

        // then remove thisItem from it's item slot,
        thisItemSlot.removeItem();

      } // end item exists
    } // end else if

    else if (command.equals("Generate Random Item" )){
      // user wants to generate a random item, and place it on the ground.
      // So, generate a new item and fill the empty slot with it.

      addToGround(Item.generateRandomItem());

    } // end else if

    else{}

  } // end actionPerformed

//****************************************************************************
  void showItemDetails(){
    ItemSlot thisItemSlot = getHighlightedItemSlot();

    if (thisItemSlot != null && thisItemSlot.isFilled() == true){
      thisItemSlot.item.details.show(); // for a new showing
      thisItemSlot.item.details.toFront(); // in case it was already showing.
    } // end if item

 } // end showItemDetails

//****************************************************************************
// private methods
//****************************************************************************
  private void addToGround(Item item){
//    Popup.createInfoPopup("Adding item to ground...");
    for (int i = Inventory.GROUND_SLOT_1; i <= Inventory.GROUND_SLOT_6; i++){
      if (itemSlots[i].isFilled() == false){
        itemSlots[i].setItem(item);
        this.repaint(); // referring to the whole panel
        return;
      } // end if empty
    } // end for loop

    // if we have reached this space, then there are no empty slots on the ground
    // so erase the ground slots and fill in the first one.
    for (int i = Inventory.GROUND_SLOT_1; i <= Inventory.GROUND_SLOT_6; i++){
      itemSlots[i].removeItem();
      // *NOTE: this should never be done in the real game,
      // since this deletes the item from the game. instead, we should scroll through items.
    } // end for loop

    // now we know it is all empty, so fill in ground.
    addToGround(item); // recursive call, since we know the ground is now empty.
  } // end addToGround

//****************************************************************************
  private ItemSlot getHighlightedItemSlot() {
    // purpose of this method is to capture the itemSlot that is highlighted(sleected)
    // if none are highlighted, then return null

    for (int i = 0; i < itemSlots.length; i++){
      if (itemSlots[i].isSelected()) return itemSlots[i];
    } // end for loop

    return null;

  } // end getHighlightedItemSlot

//****************************************************************************
  private JButton makeInvPanelButton(String title){
    JButton button = new JButton(title);
    button.setPreferredSize(new Dimension( (int)(WIDTH / 4) - 8, (int)(BTN_SECTION_HEIGHT*0.9)));
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.setActionCommand(title);
    button.addActionListener(this);
    return button;
  } // end makeInvPanelButton()

//****************************************************************************
} // end class
