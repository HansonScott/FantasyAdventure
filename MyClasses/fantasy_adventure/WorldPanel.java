package fantasy_adventure;

//imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class WorldPanel extends    JScrollPane
                        implements MouseInputListener,
                                   MouseWheelListener,
                                   ActionListener,
                                   KeyListener {

  /* The purpose of this class is to fill the center of the Session screen
   * with an opportunity to view the world map, scroll, and potentially
   * change the local area the character is in.
   */

//************************************************************************
// static declarations
//************************************************************************

  static final String WORLD_MAP_FILE =  FileNames.WORLD_MAP;
  static final int START_X = 400;  // starting view (left/Right)
  static final int START_Y = 250;  // starting view (up/Down)

  static final int NUM_MAP_ROWS    = 19;
  static final int NUM_MAP_COLUMNS = 14;
  static final int NUM_MAP_BUTTONS = (NUM_MAP_ROWS * NUM_MAP_COLUMNS); // (19x14=266)

  static final int EDGE_DISTANCE = 150; // future: make dynamic, so large screens have large edge distance?
  static final int SHIFT_MULTIPLIER = 3;

  static double oldMouseX = 0;
  static double oldMouseY = 0;
  static double mouseX = 0;
  static double mouseY = 0;
  static Point viewPoint;

//************************************************************************
// member declarations
//************************************************************************

          JPanelWithBackground mapPanel;
  private WorldMapButton[]     mapButtons;
  private PlayerCharacter      playerCharacter;

  private boolean              edgeScrolling; // start and stop when mouse at edge?
  private boolean              dragScrolling;
  private boolean              shiftPressed;
  private int                  shiftMultiplier;

//************************************************************************
// constructors
//************************************************************************

  public WorldPanel (PlayerCharacter PC){
    // the purpose of this constructor is to create the window of which
    // the user can navigate and click on areas to travel.

    // since we want to handle the scrolling ourselves,
    // we can turn the scrollBars off.
    super (JScrollPane.VERTICAL_SCROLLBAR_NEVER,
           JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    // handle basic variable stuff
    playerCharacter = PC;

    // setup the details of this window
    setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                   Constants.CENTRALPANEL_HEIGHT));

    // setup mapPanel
    mapPanel = new JPanelWithBackground(FileNames.WORLD_MAP,
                                       JPanelWithBackground.TILE);


    mapPanel.setPreferredSize(new Dimension(Res.getImage(mapPanel.backgroundImage).getImage().getWidth(this),
                                            Res.getImage(mapPanel.backgroundImage).getImage().getHeight(this)));

    mapPanel.setLayout(new GridLayout(NUM_MAP_ROWS,
                                      NUM_MAP_COLUMNS,
                                      0,   // no gap
                                      0)); // no gap

    mapPanel.addMouseWheelListener(this);

    // NOW setup mapButtons
    mapButtons = new WorldMapButton[NUM_MAP_BUTTONS];

    // Popup.createInfoPopup("constructing mapButtons");
    for (int i = 0; i < mapButtons.length; i++){
      mapButtons[i] = makeMapButton(i);
    } // end for loop

    // add mapPanel to WorldPanel (scrollPane)
    this.setViewportView(mapPanel);
    viewport.setViewPosition(new Point(START_X, START_Y));

  } // end constructor

//************************************************************************
// public methods
//************************************************************************
  public void mouseClicked(MouseEvent e) {
    // since the buttons are covering the map, then this is called
    // when we have clicked on a button
//    Popup.createInfoPopup("WorldPanel captured a mouseClick from a mapButton!");

    oldMouseX = mouseX;
    oldMouseY = mouseY;

    // future: do something here to 'travel' to this map button.

  } // mouseClicked

//************************************************************************
  public void mouseEntered(MouseEvent e) {
    // user has entered this component.
    // if it is a mapButton, then highlight it.
    // highlighting should be handled internally by the mapButtons, not here.
  } // end mouseEntered

//************************************************************************
  public void mouseExited(MouseEvent e) {
    // user has exited this component,
    // if it is a mapButton, then unhighlight it. (handled internally, not here.)
  } // end mouseExited

//************************************************************************
  public void mousePressed(MouseEvent e) {
    // user has pressed down the mouse button.
    // we need to capture the location of the mouse and the viewPort point

    // note: the getX and getY are relative only to the button in which user pressed
    // note: getLocation.getX is relative to the entire map, not the view point.

    // so, to get the location within the viewport, we have to do some math...

    oldMouseX = mouseX;
    oldMouseY = mouseY;

    mouseX = e.getX() + ((WorldMapButton)e.getSource()).getLocation().getX() - viewport.getViewPosition().getX();
    mouseY = e.getY() + ((WorldMapButton)e.getSource()).getLocation().getY() - viewport.getViewPosition().getY();

    viewPoint = this.viewport.getViewPosition();

    if(e.isShiftDown()){shiftPressed = true;}
    else shiftPressed = false;
    resetMultiplier();

  } // end mousePressed

//************************************************************************
  public void mouseReleased(MouseEvent e) {
    // the user has released the mouse button, but since we are not dragging
    // anything, then it doesn't really matter, so do nothing.
    dragScrolling = false;

    oldMouseX = mouseX;
    oldMouseY = mouseY;

    mouseX = e.getX() + ((WorldMapButton)e.getSource()).getLocation().getX() - viewport.getViewPosition().getX();
    mouseY = e.getY() + ((WorldMapButton)e.getSource()).getLocation().getY() - viewport.getViewPosition().getY();

    if(e.isShiftDown()){shiftPressed = true;}
    else shiftPressed = false;
    resetMultiplier();

  } // end mouseReleased

//************************************************************************
  public void mouseMoved(MouseEvent e){
    // when the user moves the mouse,
    // check for edges of screen, to move the map based on which edge.

    oldMouseX = mouseX;
    oldMouseY = mouseY;

    mouseX = e.getX() + ((WorldMapButton)e.getSource()).getLocation().getX() - viewport.getViewPosition().getX();
    mouseY = e.getY() + ((WorldMapButton)e.getSource()).getLocation().getY() - viewport.getViewPosition().getY();

    if(e.isShiftDown()){shiftPressed = true;}
    else shiftPressed = false;
    resetMultiplier();

    if (mouseX < EDGE_DISTANCE){
      edgeScrolling = true;
        if (mouseY < EDGE_DISTANCE) {moveViewUpLeft(Constants.EDGE_SCROLL_SPEED);} // end if
        else if (mouseY > Constants.SCREEN_HEIGHT - EDGE_DISTANCE) {moveViewDownLeft(Constants.EDGE_SCROLL_SPEED);} // end if
        else {moveViewLeft(Constants.EDGE_SCROLL_SPEED);}
    } // mouse at left

    else if (mouseX > viewport.getWidth() - EDGE_DISTANCE) {
      edgeScrolling = true;
        if (mouseY < EDGE_DISTANCE) {moveViewUpRight(Constants.EDGE_SCROLL_SPEED);} // end if
        else if (mouseY > Constants.SCREEN_HEIGHT - EDGE_DISTANCE) {moveViewDownRight(Constants.EDGE_SCROLL_SPEED);} // end if
        else {moveViewRight(Constants.EDGE_SCROLL_SPEED);}
    } // end mouse at right

    else if (mouseY < EDGE_DISTANCE){
      edgeScrolling = true;
      moveViewUp(Constants.EDGE_SCROLL_SPEED);
    } // end mouse at top

    else if (mouseY > viewport.getHeight() - EDGE_DISTANCE) {
      edgeScrolling = true;
      moveViewDown(Constants.EDGE_SCROLL_SPEED);
    } // end mouse at bottom

    else {
      edgeScrolling = false;
    } // end else

  } // end mouseMoved

//************************************************************************
  public void mouseDragged(MouseEvent e){
    // when the user has already clicked, and is dragging, then...
    // we want to 'drag' the map along with the mouse.

    // instead of doing just !=, we should do a > or < to move the correct way.
    // also, this will give us which direction, similar to above, and we can then set
    // the maximum speed with a Math.min(actualSpeed, ArbitraryMax).
    // By calling subroutine, it will keep us from going off the edge of the map.

    oldMouseX = mouseX;
    oldMouseY = mouseY;

    mouseX = e.getX() + ((WorldMapButton)e.getSource()).getLocation().getX() - viewport.getViewPosition().getX();
    mouseY = e.getY() + ((WorldMapButton)e.getSource()).getLocation().getY() - viewport.getViewPosition().getY();

    if(e.isShiftDown()){shiftPressed = true;}
    else shiftPressed = false;
    resetMultiplier();

    // first calculate change in X and Y

    int changeX = (int)(mouseX - oldMouseX);

    int changeY = (int)(mouseY - oldMouseY);

    // now we can check for a change and move accordingly

    if (changeX < 0){
      if      (changeY < 0){
        moveViewDownRight((int)(Math.abs(changeX) + Math.abs(changeY) / 2));
      } // end Y < 0

      else if (changeY > 0){
        moveViewUpRight((int)(Math.abs(changeX) + Math.abs(changeY) / 2));
      } // end Y > 0

      else {moveViewRight(-changeX);} // end else
    } // end X < 0

    else if (changeX > 0){
      if      (changeY < 0){
        moveViewDownLeft((int)(Math.abs(changeX) + Math.abs(changeY) / 2));
      } // end Y < 0

      else if (changeY > 0){
        moveViewUpLeft((int)(Math.abs(changeX) + Math.abs(changeY) / 2));
      } // end Y > 0

      else {moveViewLeft(changeX);} // end else
    } // end X > 0

    else if (changeY < 0){moveViewDown(-changeY);} // end Y < 0

    else if (changeY > 0){moveViewUp(changeY);} // end Y > 0

  } // end mouseDragged

//************************************************************************
  public void mouseWheelMoved(MouseWheelEvent e){
    // user has adjusted the mouseWheel, so we need to scroll the map.
    int adjust = e.getWheelRotation();

    if(e.isShiftDown()){shiftPressed = true;}
    else shiftPressed = false;
    resetMultiplier();

    if (e.isAltDown() || e.isControlDown()){
      if      (adjust > 0) moveViewRight(Constants.WHEEL_SCROLL_SPEED);
      else if (adjust < 0) moveViewLeft(Constants.WHEEL_SCROLL_SPEED);
    } // end if modifiers

    else{
      if (adjust > 0) moveViewDown(Constants.WHEEL_SCROLL_SPEED);
      else if (adjust < 0) moveViewUp(Constants.WHEEL_SCROLL_SPEED);
    } // end else
  } // end mouseWheelMoved

//************************************************************************
  public void keyPressed(KeyEvent k){
    // when the user presses down a key, such as the arrow keys...
//    Popup.createInfoPopup("KeyPressed caught");
    int keyCode = k.getKeyCode();

    if(k.isShiftDown()){shiftPressed = true;}
    else shiftPressed = false;
    resetMultiplier();

    if      (keyCode == KeyEvent.VK_LEFT ||
             keyCode == KeyEvent.VK_NUMPAD4 ||
             keyCode == KeyEvent.VK_4){
      moveViewLeft(Constants.KEY_SCROLL_SPEED);
    } // end if left

    else if (keyCode == KeyEvent.VK_RIGHT ||
             keyCode == KeyEvent.VK_NUMPAD6 ||
             keyCode == KeyEvent.VK_6){
      moveViewRight(Constants.KEY_SCROLL_SPEED);
    } // end if right

    else if (keyCode == KeyEvent.VK_UP ||
             keyCode == KeyEvent.VK_NUMPAD8 ||
             keyCode == KeyEvent.VK_8){
      moveViewUp(Constants.KEY_SCROLL_SPEED);
    } // end if up

    else if (keyCode == KeyEvent.VK_DOWN ||
             keyCode == KeyEvent.VK_NUMPAD2 ||
             keyCode == KeyEvent.VK_2){
      moveViewDown(Constants.KEY_SCROLL_SPEED);
    } // end if down

    else if (keyCode == KeyEvent.VK_NUMPAD1 ||
             keyCode == KeyEvent.VK_1 ||
             keyCode == KeyEvent.VK_END){
      moveViewDownLeft(Constants.KEY_SCROLL_SPEED);
    } // end if down left

    else if (keyCode == KeyEvent.VK_NUMPAD3 ||
             keyCode == KeyEvent.VK_3 ||
             keyCode == KeyEvent.VK_PAGE_DOWN){
      moveViewDownRight(Constants.KEY_SCROLL_SPEED);
    } // end if down right

    else if (keyCode == KeyEvent.VK_NUMPAD7 ||
             keyCode == KeyEvent.VK_7 ||
             keyCode == KeyEvent.VK_HOME){
      moveViewUpLeft(Constants.KEY_SCROLL_SPEED);
    } // end if up left

    else if (keyCode == KeyEvent.VK_NUMPAD9 ||
             keyCode == KeyEvent.VK_9 ||
             keyCode == KeyEvent.VK_PAGE_UP){
      moveViewUpRight(Constants.KEY_SCROLL_SPEED);
    } // end if up right

    else{}

  } // end keyPressed

//************************************************************************
  public void keyReleased(KeyEvent k){
    // when the user releases a key, such as the arrow keys...
  } // end keyReleased

//************************************************************************
  public void keyTyped(KeyEvent k){
    // when the user types a key, such as letters, numbers, or other...

    if(k.isShiftDown()){shiftPressed = true;}
    else shiftPressed = false;

    resetMultiplier();

    switch (k.getKeyChar()){
      case 'a':{
        // user has typed the 'a' character
//        Popup.createInfoPopup("Key recognized and handled: 'a'");
        break;
      } // end case

      case '4':{
        // user has typed the 'a' character
        moveViewLeft(Constants.KEY_SCROLL_SPEED);
        break;
      } // end case

      case '7':{
        // user has typed the 'a' character
        moveViewUpLeft(Constants.KEY_SCROLL_SPEED);
        break;
      } // end case

      case '8':{
        // user has typed the 'a' character
        moveViewUp(Constants.KEY_SCROLL_SPEED);
        break;
      } // end case

      case '9':{
        // user has typed the 'a' character
        moveViewUpRight(Constants.KEY_SCROLL_SPEED);
        break;
      } // end case

      case '6':{
        // user has typed the 'a' character
        moveViewRight(Constants.KEY_SCROLL_SPEED);
        break;
      } // end case

      case '3':{
        // user has typed the 'a' character
        moveViewDownRight(Constants.KEY_SCROLL_SPEED);
        break;
      } // end case

      case '2':{
        // user has typed the 'a' character
        moveViewDown(Constants.KEY_SCROLL_SPEED);
        break;
      } // end case

      case '1':{
        // user has typed the 'a' character
        moveViewDownLeft(Constants.KEY_SCROLL_SPEED);
        break;
      } // end case

      default: {
//        Popup.createInfoPopup("Key recognized, but not handled: '" + k.getKeyChar() + "'");
      } // end default
    } // end switch

  } // end keyTyped

//************************************************************************
  public void actionPerformed(ActionEvent e){} // end actionPerformed

//************************************************************************
  public void centerViewOnParty(){
    // center map on character, but watch to avoid edges of the map,
    // since we don't want to actually 'center' if the character is at the
    // corner or edge of the map.
    // try to use moveX() and moveY() if possible, for code reuse.

  } // end centerViewOnParty

//************************************************************************
// private methods
//************************************************************************
  private void resetMultiplier(){
    if(shiftPressed == true){
      shiftMultiplier = SHIFT_MULTIPLIER;
    } // end if
    else{
      shiftMultiplier = 1;
    } // end else

  } // end resetMultiplier

//************************************************************************
  private void moveViewUp(int speed){
    // purpose of this method is to respond to the user's desire
    // to 'move' the view of the map
    viewport.setViewPosition(new Point((int)viewport.getViewPosition().getX(),
                                       (int)Math.max(0, (viewport.getViewPosition().getY() - speed * shiftMultiplier))));
  } // end moveViewUp

//************************************************************************
  private void moveViewDown(int speed){
    // purpose of this method is to respond to the user's desire
    // to 'move' the view of the map
    viewport.setViewPosition(new Point((int)viewport.getViewPosition().getX(),
                                       (int)Math.min(mapPanel.getHeight() - viewport.getHeight(),
                             viewport.getViewPosition().getY() + speed * shiftMultiplier)));
  } // end moveViewDown

//************************************************************************
  private void moveViewLeft(int speed){
    // purpose of this method is to respond to the user's desire
    // to 'move' the view of the map
    viewport.setViewPosition(new Point((int)Math.max(0, (viewport.getViewPosition().getX() - speed * shiftMultiplier)),
                                       (int)viewport.getViewPosition().getY()));
  } // end moveViewLeft

//************************************************************************
  private void moveViewUpLeft(int speed){
    // purpose of this method is to respond to the user's desire
    // to 'move' the view of the map
    viewport.setViewPosition(new Point((int)Math.max(0, (viewport.getViewPosition().getX() - speed * shiftMultiplier)),
                                       (int)Math.max(0, (viewport.getViewPosition().getY() - speed * shiftMultiplier))));
  } // end moveViewUpLeft

//************************************************************************
  private void moveViewDownLeft(int speed){
    // purpose of this method is to respond to the user's desire
    // to 'move' the view of the map
    viewport.setViewPosition(new Point((int)Math.max(0, (viewport.getViewPosition().getX() - speed * shiftMultiplier)),
                                       (int)Math.min((mapPanel.getHeight() - viewport.getHeight()),
                                                     (viewport.getViewPosition().getY() + speed * shiftMultiplier))));
  } // end moveViewDownLeft

//************************************************************************
  private void moveViewRight(int speed){
    // purpose of this method is to respond to the user's desire
    // to 'move' the view of the map
    viewport.setViewPosition(new Point((int)Math.min((mapPanel.getWidth() - viewport.getWidth()),
                                                     (viewport.getViewPosition().getX() + speed * shiftMultiplier)),
                                       (int)viewport.getViewPosition().getY()));
  } // end moveViewRight

//************************************************************************
  private void moveViewUpRight(int speed){
    // purpose of this method is to respond to the user's desire
    // to 'move' the view of the map
    viewport.setViewPosition(new Point((int)Math.min((mapPanel.getWidth() - viewport.getWidth()),
                                                     (viewport.getViewPosition().getX() + speed * shiftMultiplier)),
                                       (int)Math.max(0, (viewport.getViewPosition().getY() - speed * shiftMultiplier))));
  } // end moveViewUpRight

//************************************************************************
  private void moveViewDownRight(int speed){
    // purpose of this method is to respond to the user's desire
    // to 'move' the view of the map
    viewport.setViewPosition(new Point((int)Math.min((mapPanel.getWidth() - viewport.getWidth()),
                                                     (viewport.getViewPosition().getX() + speed * shiftMultiplier)),
                                       (int)Math.min((mapPanel.getHeight() - viewport.getHeight()),
                                                     (viewport.getViewPosition().getY() + speed * shiftMultiplier))));
  } // end moveViewDownRight

//************************************************************************
  private WorldMapButton makeMapButton(int index){
    // purpose of this method is to create and setup a map button for use in
    // the mapPanel.

    WorldMapButton button = new WorldMapButton(this, index);

    // now, tailor buttons based on button index (background color for 'fuzzy' undiscovered map
    // get playerCharacter information to set button booleans: discovered, entered, occupied.
    // PC needs a record (array?) of all three booleans for every mapButton.

    // for now, just to see the map, set a few default states
    // NOTE: remove these when we have player data.
    //*********
    button.setDiscovered(true);
    button.setEntered(true);
    button.setOccupied(false);

    if (index == 50){
      button.setOccupied(true);
    } // end starting position
    //*********

    mapPanel.add(button);
    return button;

  } // end makeMapButton

//************************************************************************
} // end class WorldPanel
//************************************************************************
