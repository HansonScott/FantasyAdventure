package fantasy_adventure;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// imports
//******************************************************************************

class MarkerMaker extends JFrame implements ActionListener,
                                            FocusListener,
                                            KeyListener{

//******************************************************************************
// static variables
//******************************************************************************
  static           MarkerMaker thisMarkerMaker;
  static private   final int   frameWidth  = 300;
  static private   final int   frameHeight = 150;
  static private   JPanel     mPanel;
  static private   JLabel     mTitle;
  static private   String     titleText = "Choose marker color and set marker text.";
  static private   JPanel     mIconPanel;
  static private   JTextField mTextField;
  static private   String     defaultMarkerText = "<Put marker text here.>";
  static private   JButton    mCancelButton;
  static private   String     cancelButtonText = "Cancel new marker";
  static private   JButton    mConfirmButton;
  static private   String     confirmButtonText = "Set new marker";

  static private   JToggleButton marker01;
  static private   JToggleButton marker02;
  static private   JToggleButton marker03;
  static private   JToggleButton marker04;
  static private   JToggleButton marker05;

  static private   String markerImageChosen = FileNames.DEFAULT_AREA_MARKER;

  static private   Area   thisArea;
  static private   Point  thisPoint;
//******************************************************************************
// member variables
//******************************************************************************

//******************************************************************************
// constructor
//******************************************************************************

  MarkerMaker(){
    super();
    this.setUndecorated(true);
    this.setSize(frameWidth, frameHeight);
    this.setLocation((int)((Constants.SCREEN_WIDTH  - frameWidth) / 2),
                     (int)((Constants.SCREEN_HEIGHT - frameHeight) / 2));

    this.addFocusListener(this);

    Container content = this.getContentPane();

    mPanel = new JPanelWithBackground();
    mPanel.setPreferredSize(new Dimension(frameWidth, frameHeight));
    mPanel.setBorder(BorderFactory.createRaisedBevelBorder());
    mPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

    mTitle = new JLabel(titleText);

    mIconPanel = new JPanelWithBackground();
    mIconPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
    mIconPanel.setPreferredSize(new Dimension(frameWidth - 10, 32));

    marker01 = makeMarkerButton(1);
    marker02 = makeMarkerButton(2);
    marker03 = makeMarkerButton(3);
    marker04 = makeMarkerButton(4);
    marker05 = makeMarkerButton(5);

    mIconPanel.add(marker01);
    mIconPanel.add(marker02);
    mIconPanel.add(marker03);
    mIconPanel.add(marker04);
    mIconPanel.add(marker05);

    mTextField = new JTextField(defaultMarkerText);
    mTextField.setForeground(Color.WHITE);
    mTextField.setBackground(Color.BLACK);
    mTextField.setColumns(20);
    mTextField.addKeyListener(this);

    mCancelButton  = makeButton(cancelButtonText);
    mConfirmButton = makeButton(confirmButtonText);

    mPanel.add(mTitle);
    mPanel.add(mIconPanel);
    mPanel.add(mTextField);
    mPanel.add(mCancelButton);
    mPanel.add(mConfirmButton);

    content.add(mPanel);

  } // end constructor

//******************************************************************************
// static methods
//******************************************************************************

  static void processMarkerUI(Area a, Point p){
    // purpose of this static method is to create and show the new marker UI
    // and handle all the results here.
    // Either way, we will close the window, but store or not store based
    // on the button pressed by the user.

    thisArea = a;
    thisPoint = p;

    if (thisMarkerMaker == null){
      thisMarkerMaker = new MarkerMaker();
    } // if null
    thisMarkerMaker.show();

  } // end processMarkerUI

//******************************************************************************
// public methods
//******************************************************************************

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();

    if (e.getSource() == marker01 ||
        e.getSource() == marker02 ||
        e.getSource() == marker03 ||
        e.getSource() == marker04 ||
        e.getSource() == marker05){
      processMarkerButton((JToggleButton)e.getSource());
    } // end if markerColorButton

    else if (command == cancelButtonText){
      processCancel();
    } // end cancel

    else if (command == confirmButtonText){
      processConfirm();
    } // end confirm

    else {
      Popup.createWarningPopup("Action performed not caught: " + command);
    } // end else
  } // end actionPerformed

  public void focusGained(FocusEvent f){
  } // end focusGained

  public void focusLost(FocusEvent f){
    // user has clicked somewhere else.
    processCancel();
  }  // end focusLost

  public void keyPressed(KeyEvent k){
//    Popup.createInfoPopup("KeyPressed recognized" + MyTextIO.newline +
//                          "getKeyChar: " + k.getKeyChar() + MyTextIO.newline +
//                          "getKeyCode: " + k.getKeyCode() + MyTextIO.newline +
//                          "isActionKey: " + k.isActionKey());

      if (k.getKeyCode() == KeyEvent.VK_ENTER){
        processConfirm();
      } // end if enter

      else if (k.getKeyCode() == KeyEvent.VK_ESCAPE){
        processCancel();
      } // end if cancel

  } // end keyPressed

  public void keyReleased(KeyEvent k){
//    Popup.createInfoPopup("KeyReleased recognized" + MyTextIO.newline +
//                          "getKeyChar: " + k.getKeyChar() + MyTextIO.newline +
//                          "getKeyCode: " + k.getKeyCode() + MyTextIO.newline +
//                          "isActionKey: " + k.isActionKey());
  } // end keyReleased

  public void keyTyped(KeyEvent k){
//    Popup.createInfoPopup("KeyTyped recognized" + MyTextIO.newline +
//                          "getKeyChar: " + k.getKeyChar() + MyTextIO.newline +
//                          "getKeyCode: " + k.getKeyCode() + MyTextIO.newline +
//                          "isActionKey: " + k.isActionKey());
  } // end keyTyped

//******************************************************************************
// private methods
//******************************************************************************
  private void processMarkerButton(JToggleButton thisButton){
    // purpose of this method is to setup the correct marker Icon for the new marker

//    Popup.createInfoPopup("processing Marker Button");

    if (thisButton == marker01){
      markerImageChosen = FileNames.AREA_MARKER_01;
    } // end if

    else if(thisButton == marker02){
      markerImageChosen = FileNames.AREA_MARKER_02;
    } // end if 02

    else if(thisButton == marker03){
      markerImageChosen = FileNames.AREA_MARKER_03;
    } // end if 03

    else if(thisButton == marker04){
      markerImageChosen = FileNames.AREA_MARKER_04;
    } // end if 04

    else { // thisButton == marker05
      markerImageChosen = FileNames.AREA_MARKER_05;
    } // end if 05

    // reset all selections
    marker01.setSelected(false);
    marker02.setSelected(false);
    marker03.setSelected(false);
    marker04.setSelected(false);
    marker05.setSelected(false);
    thisButton.setSelected(true);

    // reset all borders (visual feedback of selection
    marker01.setBorder(BorderFactory.createRaisedBevelBorder());
    marker02.setBorder(BorderFactory.createRaisedBevelBorder());
    marker03.setBorder(BorderFactory.createRaisedBevelBorder());
    marker04.setBorder(BorderFactory.createRaisedBevelBorder());
    marker05.setBorder(BorderFactory.createRaisedBevelBorder());
    thisButton.setBorder(BorderFactory.createLoweredBevelBorder());
  } // end

//******************************************************************************
  private void processCancel(){
    this.hide();
    AreaPanel.mapDisplay.repaint();
  } // end processCancel

//******************************************************************************
  private void processConfirm(){
    // purpose of this method is to capture the text and color icon chosen
    // and create the new marker from that information.
//    Popup.createInfoPopup("adding a new marker from the following info:");
//    Popup.createInfoPopup("thisPoint: " + thisPoint.x + ", " + thisPoint.y);
//    Popup.createInfoPopup("Text: " + mTextField.getText());
//    Popup.createInfoPopup("image file: '" + markerImageChosen);
    thisArea.addNewMarker(thisPoint, mTextField.getText(), markerImageChosen);
    processCancel();
  } // end processConfirm

//******************************************************************************

  private JButton makeButton(String title){
    JButton button = new JButton(title);
    button.setPreferredSize(new Dimension((frameWidth / 2) - 20, frameHeight / 3));
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.setActionCommand(title);
    button.addActionListener(this);
    button.addKeyListener(this);
    return button;
  } // end makeButton

//******************************************************************************
  private JToggleButton makeMarkerButton(int i){
    JToggleButton button;
    if (i == 1){
      button = new JToggleButton(Res.getImage(FileNames.AREA_MARKER_01));
      button.setSelected(true);
    } // end if
    else if (i == 2){
      button = new JToggleButton(Res.getImage(FileNames.AREA_MARKER_02));
    } // end if 2

    else if (i == 3){
      button = new JToggleButton(Res.getImage(FileNames.AREA_MARKER_03));
    } // end if 2

    else if (i == 4){
      button = new JToggleButton(Res.getImage(FileNames.AREA_MARKER_04));
    } // end if 2

    else{// i == 5+)
      button = new JToggleButton(Res.getImage(FileNames.AREA_MARKER_05));
    } // end else

    // now setup the specifics
    button.setPreferredSize(new Dimension(32, 32));
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    if (i == 1) button.setBorder(BorderFactory.createLoweredBevelBorder());
    button.setActionCommand(String.valueOf(i));
    button.addActionListener(this);

    return button;
  } // end makeMarkerButton

//******************************************************************************
} // end class
