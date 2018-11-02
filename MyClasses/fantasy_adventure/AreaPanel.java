package fantasy_adventure;

//imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class AreaPanel  extends    JPanel
                        implements ActionListener,
                                   MouseListener,
                                   MouseMotionListener {

  /* The purpose of this class is to fill the center of the Session screen
   * with an opportunity to view the current area map,
   * and add/remove titled markers
   */

//************************************************************************
// static declarations
//************************************************************************

  static final String defaultMessage         = "This is where the marker text will go.";
  static final String addMarkerButtonText    = "add new marker";
  static final String removeMarkerButtonText = "remove marker";
  static final String areaTitleText          = "<Area title here>";

  static final int FONT_SIZE                 = (int)(Constants.CENTRALPANEL_HEIGHT / 30);
  static final int buttonHeight              = (int)(Constants.CENTRALPANEL_HEIGHT / 10);
  static final int titleHeight               = 150; // includes title name and marker text block.
  static final int mapDisplayHeight          = (Constants.SCREEN_HEIGHT
                                                - buttonHeight
                                                - titleHeight);

  static final int mapDisplayWidth           = Constants.CENTRALPANEL_WIDTH;

  static boolean isDeleting = false;
  static boolean isAdding   = false;

//************************************************************************
// member declarations
//************************************************************************

  JLabel                    areaTitle;
  JLabel                    markerTextLabel;
  static AreaJPanelWithBackground  mapDisplay; // so MarkerMaker can repaint it.
  JPanel                    buttonRow;
  JButton                   addMarkerButton;
  JButton                   removeMarkerButton;
  Area                      thisArea;
  PlayerCharacter           playerCharacter;

//************************************************************************
// constructors
//************************************************************************

  public AreaPanel (PlayerCharacter PC){
    // the purpose of this constructor is

    // start by creating parent()
    super ();

    // transfer PC
    playerCharacter = PC;

    // now, we can setup the details of this window
    setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                   Constants.CENTRALPANEL_HEIGHT));
    setLayout(new FlowLayout(FlowLayout.CENTER, Constants.CENTRALPANEL_WIDTH, 4));

    areaTitle = new JLabel (areaTitleText, JLabel.CENTER);
    areaTitle.setFont(new Font("Arial", Font.ITALIC, FONT_SIZE));
    areaTitle.setBackground(Color.LIGHT_GRAY);
    areaTitle.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH, 60));

    markerTextLabel = new JLabel(defaultMessage, JLabel.CENTER);
    markerTextLabel.setFont(new Font(markerTextLabel.getFont().getName(),
                                     Font.BOLD,
                                     markerTextLabel.getFont().getSize() + 2));
    markerTextLabel.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 2), 40));
    markerTextLabel.setBorder(BorderFactory.createEtchedBorder());

    mapDisplay = new AreaJPanelWithBackground(playerCharacter, thisArea,
                                              null, JPanelWithBackground.STRETCH);
    mapDisplay.setBorder(BorderFactory.createRaisedBevelBorder());
    mapDisplay.setBackground(Color.BLACK);
    mapDisplay.setLayout(null); // sets to pixel layout(?)
    mapDisplay.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                              mapDisplayHeight));

    mapDisplay.addMouseListener(this);

//    buttonRow = new JPanelWithBackground();
    buttonRow = new JPanel();
    buttonRow.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 4));
    buttonRow.setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH - 2,
                                             buttonHeight));

    addMarkerButton    = makeButton(addMarkerButtonText);
    removeMarkerButton = makeButton(removeMarkerButtonText);

    buttonRow.add(addMarkerButton);
    buttonRow.add(removeMarkerButton);

    add(areaTitle);
    add(markerTextLabel);
    add(mapDisplay);
    add(buttonRow);

    // load area from PC information
    loadCurrentArea();

  } // end constructor

//************************************************************************
// public methods
//************************************************************************
  public void mouseMoved(MouseEvent me){} // end mouseMoved
  public void mouseEntered(MouseEvent me){} // end mouseEntered
  public void mouseExited(MouseEvent me){} // end mouseExited

//************************************************************************
  public void mousePressed(MouseEvent me){
    // purpose of this method is to capture the mouse position, and if it is
    // inside of one of the marker bounds, then display the text associated with
    // that marker.

    int x = me.getX();
    int y = me.getY();

    // go through the array of markers for this area...
    for (int i = 0; i < thisArea.areaMarkers.length; i++){
      // for each marker,  compare the location x's and y's.

      if (thisArea.areaMarkers[i] != null &&
          thisArea.areaMarkers[i].markerPoint != null &&
          x >= thisArea.areaMarkers[i].markerPoint.x &&
          x <= thisArea.areaMarkers[i].markerPoint.x +
            (Res.getImage(thisArea.areaMarkers[i].markerImage).getIconWidth()) &&
          y >= thisArea.areaMarkers[i].markerPoint.y &&
          y <= thisArea.areaMarkers[i].markerPoint.y +
            (Res.getImage(thisArea.areaMarkers[i].markerImage).getIconHeight())){
          // then the user clicked inside of the image for the marker, so respond!
//          Popup.createInfoPopup("Attempting to set text for this marker with:" + MyTextIO.newline +
//                                 "'" + thisArea.areaMarkers[i].markerText + "'");
          if (isDeleting == true){
            thisArea.areaMarkers[i] = null;
            thisArea.numAreaMarkers--;
            isDeleting = false;
            return;
          } // end isDeleting == true

          else if (isDeleting == false && isAdding == false){
            markerTextLabel.setText(thisArea.areaMarkers[i].markerText);
            markerTextLabel.setBorder(BorderFactory.createEtchedBorder());
          } // end if false
          else {
          } // end else
          return;
        } // end if
    } // end for loop
  } // end mouseMoved

//************************************************************************
  public void mouseReleased(MouseEvent me){} // end mouseMoved

//************************************************************************
  public void mouseClicked(MouseEvent me){
//    Popup.createInfoPopup("MouseClicked captured" + MyTextIO.newline +
//                          "Location: " + me.getX() + ", " + me.getY());

    if (me.getButton() != MouseEvent.BUTTON1 ||
        (me.getButton() == MouseEvent.BUTTON1 &&
        isAdding == true)){
      MarkerMaker.processMarkerUI(thisArea, new Point(me.getX(), me.getY()));
      isAdding = false;
    } // end if not-left click

    // redraw with new marker
    mapDisplay.repaint();
  } // end mouseMoved

//************************************************************************
  public void mouseDragged(MouseEvent me){} // end mouseDragged

//************************************************************************
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();

    if (command == addMarkerButtonText){
      if (isAdding == false){
        Popup.createInfoPopup(
            "Click where you would like to make a new marker." +
            MyTextIO.newline +
            "You can also right-click to achieve the same result faster.");
        isAdding = true;
        isDeleting = false;
        return;
      } // end if not adding
      else{
        Popup.createInfoPopup("new marker aborted, process canceled.");
        isAdding = false;
      } // end else (isAdding = true)
    } // end if addMarkerButtonText

//**********************************************
    else if (command == removeMarkerButtonText){
      if (isDeleting == false){
        Popup.createInfoPopup("Click on the marker to be deleted," +
                              MyTextIO.newline +
                              "or click on the remove button again to cancel.");
        isAdding = false;
        isDeleting = true;
      } // end is Deleting false
      else{
        Popup.createInfoPopup("Marker delete aborted, process canceled.");
        isDeleting = false;
      } // end else (isDeleting = true)

    } // end if removeMarkerButtonText

    else {
      Popup.createErrorPopup("ActionPerformed uncaught: " + command );
    } // end else
  } // end actionPerformed

//************************************************************************
// package methods
//************************************************************************
   void loadArea(Area a){
     thisArea = a;
     mapDisplay.thisArea = thisArea;
     refreshContent();
   } // end loadArea

//************************************************************************
// private methods
//************************************************************************
  private void loadCurrentArea(){
    // purpose of this method is to capture the current area that the
    // PC and therefore the party is in.  then call refreshContent();
    if (playerCharacter.getCurrentArea() == null){
      Popup.createWarningPopup("Unable to find 'current area' for player, skipping area load...");
      return;
    } // end if
//    Popup.createInfoPopup("Attempting to load current area: " +
//                          playerCharacter.getCurrentArea().getAreaName());
    loadArea(playerCharacter.getCurrentArea());

  } // end loadCurrentArea

//************************************************************************
  private void refreshContent(){
     // purpose of this method is to load the content for thisArea into the
     // UI and allow the user to view the information and to adjust it as
     // appropriate.

     // load the title from the area into the areaPanel title
     if (thisArea != null){
       areaTitle.setText(thisArea.getAreaName());

       // load the map graphic for the background of the mapDisplay
       // create the map graphic the first time the area is created/loaded? then store for later.
//       Popup.createInfoPopup("setting background image to: '" + playerCharacter.oldCharDir + File.separator +
//                                     "Areas" + File.separator +
//                                     thisArea.getMapPic());

       mapDisplay.setBackgroundImage(playerCharacter.oldCharDir + File.separator +
                                     "Areas" + File.separator +
                                     thisArea.getMapPic());

     } // end if non-null

     else{
       Popup.createWarningPopup("ThisArea is null, skipping the refresh.");
     } // end else
  } // end refreshContent

//************************************************************************
  private JButton makeButton(String title){
    JButton button = new JButton(title);
    button.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 4),
                                          buttonHeight - 8));
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.setActionCommand(title);
    button.addActionListener(this);
    return button;
  } // end make button

//************************************************************************
} // end class AreaPanel
//************************************************************************

class AreaJPanelWithBackground extends JPanelWithBackground{

  Area thisArea;
  PlayerCharacter playerCharacter;

  AreaJPanelWithBackground(PlayerCharacter pc, Area a, String img, int style){
    super(img, style);
    thisArea = a;
    playerCharacter = pc;
  } // end constructor

//************************************************************************
  public void paint(Graphics g){
    super.paint(g);

    // draw pc, party, and enemy icons
    drawFriendlyIcon(playerCharacter.getLocation(), g);

    // go though the party and draw their placement
    for (int i = 0; i < playerCharacter.party.length; i++){
      if (playerCharacter.party[i] != null)
        drawFriendlyIcon(playerCharacter.party[i].getLocation(), g);
       } // end for loop

    // paint all the markers
    for (int i = 0; i < thisArea.areaMarkers.length; i++){
      // for each marker, draw the image.
//      Popup.createInfoPopup("Attempting to draw image for marker with info: ");
//      Popup.createInfoPopup(thisArea.areaMarkers[i].markerImage);
//      Popup.createInfoPopup("for marker # " + i + " of " + thisArea.areaMarkers.length);
//      if (thisArea.areaMarkers[i].markerPoint == null){
//        Popup.createErrorPopup("thisArea.areaMarkers[" + i + "].markerPoint is null!" +
//                               MyTextIO.newline + "Using 50,50 instead.");
//      } // end if
      if (thisArea.areaMarkers[i] != null){
        g.drawImage(Res.getImage(thisArea.areaMarkers[i].markerImage).getImage(),
                    thisArea.areaMarkers[i].markerPoint.x,
                    thisArea.areaMarkers[i].markerPoint.y, this);
      } // end if non-null
    } // end for loop

  } // end paint
//************************************************************************

  private void drawFriendlyIcon(Point p, Graphics g){
    // purpose of this method is to draw the icon for the characters in the area.
    // this is an oval, or dot, or something...
  } // end drawFriendlsyIcon

} // end class
//************************************************************************
