package fantasy_adventure;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

//imports

//**************************************************************************
class StackSplitter extends    JFrame
                    implements KeyListener,
                               MouseListener,
                               ActionListener{

//**************************************************************************
// static declarations
//**************************************************************************
  static StackSplitter thisSplitter;
  static final int SPLIT_WIDTH  = 250;
  static final int SPLIT_HEIGHT = 150;
  static final int TRANSFER_BOX_GRID_SIZE = 18;
  static final int FONT_SIZE = 14;
  static       int OLD_STACK_QTY = 2;
  static       ItemSlot destinationSlot;

//**************************************************************************
// member declarations
//**************************************************************************
  private Container  frameContent;
  private JLabel     titleRowLabel;
  private JPanel     transferRow;
  private JPanel     buttonRow;
  private JButton    resetButton, cancelButton, splitButton;
  private ItemSlot   oldStack, newStack;
  private JPanel     transferBox;
  private JButton    upLabel, dnLabel, addLabel, removeLabel;
  private JTextField inputSpace;

//**************************************************************************
// constructor
//**************************************************************************

  private StackSplitter(){
    // purpose of this constuctor is to setup all the needed GUI components

    // create and setup main JFrame
    super();
    setUndecorated(true);
    setSize(SPLIT_WIDTH, SPLIT_HEIGHT);
    setLocation((int)((Constants.SCREEN_WIDTH  - SPLIT_WIDTH)  / 2),
                (int)((Constants.SCREEN_HEIGHT - SPLIT_HEIGHT) / 2));
    frameContent = getContentPane();
    frameContent.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    // create and setup internal components ********************************
    titleRowLabel = new JLabel("Choose amount to transfer");
    titleRowLabel.addKeyListener(this);

    transferRow = new JPanel();
    transferRow.setPreferredSize(new Dimension(SPLIT_WIDTH - 20,
                                               ItemSlot.ITEMSLOT_HEIGHT + 10));
    transferRow.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));

    oldStack = new ItemSlot();
      oldStack.addMouseListener(this);
      oldStack.addKeyListener(this);

    newStack = new ItemSlot();
      newStack.addMouseListener(this);
      newStack.addKeyListener(this);

    transferBox = new JPanel();
      transferBox.setPreferredSize(new Dimension(TRANSFER_BOX_GRID_SIZE * 4,
                                                 TRANSFER_BOX_GRID_SIZE * 3));
      transferBox.setBorder(BorderFactory.createRaisedBevelBorder());
      transferBox.setLayout(new GridLayout(3, 3, 2, 2));

    upLabel     = makeAdjButton("+");
    dnLabel     = makeAdjButton("-");
    addLabel    = makeAdjButton(">");
    removeLabel = makeAdjButton("<");

    inputSpace = new JTextField("1", 3);
      inputSpace.setHorizontalAlignment(JTextField.CENTER);
      inputSpace.setBackground(Color.BLACK);
      inputSpace.setForeground(Color.WHITE);
      inputSpace.setFont(new Font(inputSpace.getFont().getFontName(),
                                  Font.BOLD, FONT_SIZE));
      inputSpace.addKeyListener(this);

    buttonRow = new JPanel();
      buttonRow.setPreferredSize(new Dimension(SPLIT_WIDTH, ItemSlot.ITEMSLOT_HEIGHT));
      buttonRow.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    resetButton  = makeActionButton ("Reset", 4);
    cancelButton = makeActionButton ("Cancel", 4);
    splitButton  = makeActionButton ("Split", 2);

    // add components to their container and to the JFrame
    transferBox.add(new JLabel(""));
    transferBox.add(upLabel);
    transferBox.add(new JLabel(""));
    transferBox.add(removeLabel);
    transferBox.add(inputSpace);
    transferBox.add(addLabel);
    transferBox.add(new JLabel(""));
    transferBox.add(dnLabel);
    transferBox.add(new JLabel(""));

    transferRow.add(oldStack);
    transferRow.add(transferBox);
    transferRow.add(newStack);

    buttonRow.add(resetButton);
    buttonRow.add(splitButton);
    buttonRow.add(cancelButton);

    frameContent.add(titleRowLabel);
    frameContent.add(transferRow);
    frameContent.add(buttonRow);
  } // end constructor

//**************************************************************************
// static methods
//**************************************************************************
  public static void splitStack(Item oldItem, ItemSlot destSlot){
    // purpose of this method is to take the item, which is assumed to be a stack
    // and split it using a small GUI
    // adding the new amount to a new item
    // and placing it into the destination slot
    // and reducing the original item's quantity.

    // Note: since these are both complex objects, they are being passed by reference
    // so we have full control over the usual data and methods.

    // create the GUI in which we will be working, use the same one if we have used it before.
    if (thisSplitter == null)
      thisSplitter = new StackSplitter();

    // update information into the frame for this use
    thisSplitter.oldStack.setItem(oldItem);
    Item newItem = Item.createNewItem(oldItem); // make a copy

    OLD_STACK_QTY = oldItem.getQuantity();
    destinationSlot = destSlot;

    // transfer one unit automatically
    oldItem.setQuantity(OLD_STACK_QTY - 1);
    newItem.setQuantity(1);
    thisSplitter.newStack.setItem(newItem);

    // show the frame and wait for results
    thisSplitter.show();
  } // end splitStack

//**************************************************************************
// public methods
//**************************************************************************
  public void keyPressed(KeyEvent k){
    // purpose of this method is to capture any pressed keys
/*    Popup.createInfoPopup("KeyPressed recognized by the stackSplitter class!"   + MyTextIO.newline
                         +"getKeyChar: "  + k.getKeyChar()             + MyTextIO.newline
                         +"getKeyCode: "  + k.getKeyCode()             + MyTextIO.newline
                         +"getKeyText: "  + k.getKeyText(k.getKeyCode()) + MyTextIO.newline
                         +"isActionKey: " + k.isActionKey());
*/
    if      (k.getKeyCode() == KeyEvent.VK_ENTER)    {handleSplitButton();}  // end enter
    else if (k.getKeyCode() == KeyEvent.VK_ESCAPE)   {handleCancelButton();} // end escape
    else if (k.getKeyCode() == KeyEvent.VK_UP)       {handleUpButton();}     // end up
    else if (k.getKeyCode() == KeyEvent.VK_DOWN)     {handleDnButton();}   // end down
    else if (k.getKeyCode() == KeyEvent.VK_RIGHT)    {handleAddButton();}    // end add
    else if (k.getKeyCode() == KeyEvent.VK_LEFT)     {handleRemoveButton();} // end remove
    else if (k.getKeyCode() == KeyEvent.VK_NUMPAD1) {inputSpace.setText("1");}   // end 1
    else if (k.getKeyCode() == KeyEvent.VK_NUMPAD2) {inputSpace.setText("2");}   // end 2
    else if (k.getKeyCode() == KeyEvent.VK_NUMPAD3) {inputSpace.setText("3");}   // end 3
    else if (k.getKeyCode() == KeyEvent.VK_NUMPAD4) {inputSpace.setText("4");}   // end 4
    else if (k.getKeyCode() == KeyEvent.VK_NUMPAD5) {inputSpace.setText("5");}   // end 5
    else if (k.getKeyCode() == KeyEvent.VK_NUMPAD6) {inputSpace.setText("6");}   // end 6
    else if (k.getKeyCode() == KeyEvent.VK_NUMPAD7) {inputSpace.setText("7");}   // end 7
    else if (k.getKeyCode() == KeyEvent.VK_NUMPAD8) {inputSpace.setText("8");}   // end 8
    else if (k.getKeyCode() == KeyEvent.VK_NUMPAD9) {inputSpace.setText("9");}   // end 9
    else if (k.getKeyCode() == KeyEvent.VK_NUMPAD0) {inputSpace.setText("0");}   // end 10
    else if (k.getKeyCode() == KeyEvent.VK_R)        {handleResetButton();}       // end "R"

    else{}

  } // end keyPressed

//******************************************************************************
  public void keyReleased(KeyEvent k){
    // purpose of this method is to capture any released keys
/*    Popup.createInfoPopup("KeyReleased recognized by the stackSplitter class!"  + MyTextIO.newline
                         +"getKeyChar: "  + k.getKeyChar()             + MyTextIO.newline
                         +"getKeyCode: "  + k.getKeyCode()             + MyTextIO.newline
                         +"getKeyText: "  + k.getKeyText(k.getKeyCode()) + MyTextIO.newline
                         +"isActionKey: " + k.isActionKey());
*/
  } // end keyReleased

//******************************************************************************
  public void keyTyped(KeyEvent k){
    // purpose of this method is to capture any typed keys
/*    Popup.createInfoPopup("KeyTyped recognized by the stackSplitter class!" + MyTextIO.newline
                         +"getKeyChar: "  + k.getKeyChar()             + MyTextIO.newline
                         +"getKeyCode: "  + k.getKeyCode()             + MyTextIO.newline
                         +"getKeyText: "  + k.getKeyText(k.getKeyCode()) + MyTextIO.newline
                         +"isActionKey: " + k.isActionKey());
*/
  } // end keyTyped  public void mousePressed(MouseEvent m){}

//******************************************************************************
  public void mousePressed(MouseEvent m){}
  public void mouseReleased(MouseEvent m){}
  public void mouseClicked(MouseEvent m){}
  public void mouseEntered(MouseEvent m){}
  public void mouseExited(MouseEvent m){}

//******************************************************************************
  public void actionPerformed(ActionEvent e){
    // handle the up button
    if (e.getActionCommand().equalsIgnoreCase("+")){
      handleUpButton();
    } // end handle up

    // handle the dn button
    if (e.getActionCommand().equalsIgnoreCase("-")){
      handleDnButton();
    } // end handle up

    // handle add button
    if (e.getActionCommand().equalsIgnoreCase(">")){
      handleAddButton();
    } // end handle add

    // handle remove button
    if (e.getActionCommand().equalsIgnoreCase("<")){
      handleRemoveButton();
    } // end handle remove

    // handle the reset button
    if (e.getActionCommand().equalsIgnoreCase("Reset")){
      handleResetButton();
    } // end handle Reset

    // handle the cancel button
    if (e.getActionCommand().equalsIgnoreCase("Cancel")){
      handleCancelButton();
    } // end handle cancel

    // handle the split button
    if (e.getActionCommand().equalsIgnoreCase("Split")){
      handleSplitButton();
    } // end handle split

  } // end actionPerformed

//**************************************************************************
// private methods
//**************************************************************************
  private void handleUpButton(){
    // the purpose of this method is to increase the amount shown in the
    // input box, thus affecting the amount that will be transferred
    // for future clicks on add/remove buttons

    inputSpace.setText("" + Math.min((Integer.parseInt(inputSpace.getText()) + 1),
                                (OLD_STACK_QTY - 1)));

  } // end handle up button

//**************************************************************************
  private void handleDnButton(){
    // the purpose of this method is to decrease the amount shown in the
    // input box, thus affecting the amount that will be transferred
    // for future clicks on add/remove buttons

    inputSpace.setText("" + Math.max((Integer.parseInt(inputSpace.getText()) - 1), 1));

  } // end handle dn button

//**************************************************************************
  private void handleAddButton(){
    // the purpose of this method is to transfer the stated quantity in the
    // input box from the oldStack to the newStack.

//    Popup.createInfoPopup("Attempting to handle adding");
    int transferAmount;
    // read quantity showing within the input box.
    try{
      transferAmount = Integer.parseInt(inputSpace.getText());
    } // end try
    catch (NumberFormatException nfe){
      // the user has typed in something that is not a number
      transferAmount = 1;
      inputSpace.setText("1");
    } // end catch

    // attempt to transfer that quantity from oldStack to newStack
    // if quantity is zero, do nothing, but don't close
    if (transferAmount < 1) return;

    // if quantity it too high, transfer 1 less than quantity available.
    if (transferAmount > oldStack.item.getQuantity() - 1)
        transferAmount = oldStack.item.getQuantity() - 1;

    // if transfer would add too many to stack, then transfer max.
    if ((newStack.item.getQuantity() + transferAmount) > Constants.STACK_SIZE)
        transferAmount = (Constants.STACK_SIZE - newStack.item.getQuantity());

    // now we know we have a valid amount to transfer.
    newStack.item.setQuantity(newStack.item.getQuantity() + transferAmount);
    oldStack.item.setQuantity(oldStack.item.getQuantity() - transferAmount);

    updateStacks();
  } // end handle add button

//**************************************************************************
  private void handleRemoveButton(){
    // purpose of this method is to reduce the quantity of the destination
    // by the amount currently in the input box, and restore them back to
    // the source itemSlot.

//    Popup.createInfoPopup("Attempting to handle removing");
    int transferAmount;

    // read quantity showing within the input box.
    try{
      transferAmount = Integer.parseInt(inputSpace.getText());
    } // end try
    catch(NumberFormatException nfe){
      transferAmount = 1;
      inputSpace.setText("1");
    } // end catch nfe

    // attempt to transfer that quantity from newStack to oldStack
    // if quantity is zero, do nothing, but don't close
    if (transferAmount < 1) return;

    // if quantity it too high, transfer 1 less than quantity available.
    if (transferAmount > newStack.item.getQuantity() - 1)
        transferAmount = newStack.item.getQuantity() - 1;

    // now we know we have a valid amount to transfer.
    oldStack.item.setQuantity(oldStack.item.getQuantity() + transferAmount);
    newStack.item.setQuantity(newStack.item.getQuantity() - transferAmount);

    updateStacks();
  } // end handle remove button

//**************************************************************************
  private void handleResetButton(){
    // purpose of this method is to respond to the user's click on the
    // reset button - setting the two quantities back to their original value.

    // set the oldStack back to it's original quantity, and set the newStack to zero.
    newStack.item.setQuantity(1);
    oldStack.item.setQuantity(OLD_STACK_QTY - 1);

    newStack.item.updateDetails();
    oldStack.item.updateDetails();

  } // end handle reset button

//**************************************************************************
  private void handleCancelButton(){
    // purpose of this method is to cancel the splitting,
    // So, reset the old item, and hide interface.
    oldStack.item.setQuantity(OLD_STACK_QTY);
    this.hide();
  } // end handle cancel button

//**************************************************************************
  private void handleSplitButton(){
    // purpose of this method is to respond to the user's click on the
    // split button.

    // since we have handled all the numbers in other methods,
    // all we have to do is transfer the new item into the user's inventory
    // and the old slot picture should update automatically
    // update the item details though, to it reflects the change.

    destinationSlot.setItem(thisSplitter.newStack.item);
    destinationSlot.setFilled(true);
    updateStacks();

    // then close.
    this.hide();

  } // end handle split button

//**************************************************************************
  private void updateStacks(){
    newStack.item.updateDetails();
    newStack.update();
    newStack.repaint();
    oldStack.item.updateDetails();
    oldStack.update();
    oldStack.repaint();
  } // end updateStacks

  private JButton makeActionButton(String name, int size){
    JButton button = new JButton(name);
      button.setBorder(BorderFactory.createRaisedBevelBorder());
      button.setPreferredSize(new Dimension((SPLIT_WIDTH / size), ItemSlot.ITEMSLOT_HEIGHT));
      button.setActionCommand(name);
      button.addActionListener(this);
      button.addKeyListener(this);
    return button;
  } // end makeActionButton

//**************************************************************************
  private JButton makeAdjButton(String name){
    JButton button = new JButton(name);
      button.setBorder(BorderFactory.createEmptyBorder());
      button.setPreferredSize(new Dimension(TRANSFER_BOX_GRID_SIZE, TRANSFER_BOX_GRID_SIZE));
      button.setFont(new Font(button.getFont().getFontName(),
                              button.getFont().getStyle(),
                              FONT_SIZE));
      button.setActionCommand(name);
      button.addActionListener(this);
      button.addKeyListener(this);
      return button;
  } // end makeAdjButton

//**************************************************************************
} // end class StackSplitter
