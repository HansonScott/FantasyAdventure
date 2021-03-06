package fantasy_adventure;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

class Area extends JPanel{

/* the purpose of this class is to create an area
 * which is the immediate location displayed in the ActionPane
 * on which the objects will interact with and respond to mouse and keyboard input.
 * This is a very large object with lots of object creation nested within it.
 * An area takes considerable amount of time and power to generate, as
 * it incorporates random generation of all the internal parts.
 * It will be created by the session controller, which will assign
 * the constraints for creation and difficulty (passed as parameters).
 */

//************************************************************************
// Static Variables
//************************************************************************
// for use in describing and attributing area types:
  static final int BLANK     = 0; // empty, black background.  Used for testing.
  static final int GRASSLAND = 1; // grass, herds, creeks, low hills.  Shrubs.
  static final int DESERT    = 2; // dry, cacti plants, very seasonal plants.
  static final int TUNDRA    = 3; // gets snow annually, if not constantly.  Tough, hardy plants.
  static final int FOREST    = 4; // trees. - eventually, perhaps have different types of trees.
  static final int AQUATIC   = 5; // water. - solid water.  Eventually add water areas?

  static String thisLine; // for use in reading/writing files.

  static int arrayIncrement = 5; // size of array increase for objects
//************************************************************************
// instance variablers
//************************************************************************

  String    areaID;      // unique identifier, in the form of �areaXXxYYyZZ� ex: �area06x02y28�
  String    areaName;    // descriptive area name that shows on the title bar for the user.
  int       areaTypeInt; // the static int representation of the type, listed below.
  boolean   discovered;  // if so, then change worldMap from black to colored.
  boolean   visited;     // if so, then change area is saved in the character save folder.
  boolean   occupied;    // if so, then use party Icon on worldMap and should be the pc's currentArea
  Point     mapLoc;      // location on world map (has an x and a y)
  int       roomNumber;  // if internal room of area X,Y, then list room number
                         // (0 or null for outside area.)
  String    mapPic;      // custom made minimap of the area (computer generated after creation?)
  Dimension size;        // total size of the area, in pixels.

  int          numAreaMarkers;  // keeps track for marker array creation
  AreaMarker[] areaMarkers;     // asrray of markers for the areaPanel
  int          numActors;  // keeps track for object array creation
  LivingObject[] areaActors;    //
  int          numProps;  // keeps track for object array creation
  NonLivingObject[] areaProps;  // array of static items in the area

//************************************************************************
// constructors
//************************************************************************
Area(){
  super();
  areaID           = new String("area00x00y00");
  areaName         = new String("Wilderness");
  areaTypeInt      = GRASSLAND;
  discovered       = false;
  visited          = false;
  occupied         = false;
  Point mapLoc     = new Point(0,0);
  roomNumber       = 0;
  mapPic           = FileNames.DEFAULT_AREA_MAP;
  size             = new Dimension(Constants.AREA_WIDTH, Constants.AREA_HEIGHT);
  numAreaMarkers   = 0;
  areaMarkers      = new AreaMarker[numAreaMarkers];
  numActors = 0;
  areaActors       = new LivingObject[numActors];
  numProps   = 0;
  areaProps        = new NonLivingObject[numProps];
} // end default constructr

//************************************************************************
Area (BufferedReader br) throws IOException{
  // this creates a new area directly from the file,
  // this will be most likely for saved games.
  // This assumes the correct file.
  this();

  thisLine = br.readLine();

//  Popup.createInfoPopup(thisLine);

  // from this area line, we need to capture all the variables.
  thisLine = MyTextIO.trimPhrase(thisLine);
  areaID = MyTextIO.getNextPhrase(thisLine);
  thisLine = MyTextIO.trimPhrase(thisLine);

//  Popup.createInfoPopup("areaID set to: '" + areaID + "'");
//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
//  Popup.createInfoPopup("setting areaName to: '" + MyTextIO.getNextPhrase(thisLine) + "'");
  areaName = MyTextIO.getNextPhrase(thisLine);
//  Popup.createInfoPopup("areaName set to: '" + this.getAreaName() + "'");
  thisLine = MyTextIO.trimPhrase(thisLine);

//  Popup.createInfoPopup("areaName set to: '" + areaName + "'");
//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  areaTypeInt = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);

//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  discovered = Boolean.getBoolean(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);

//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  visited = Boolean.getBoolean(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);

//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  occupied = Boolean.getBoolean(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);

//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  int x = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);

//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  int y = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);

  mapLoc = new Point(x, y);

//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  roomNumber = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);

//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  mapPic = MyTextIO.getNextPhrase(thisLine);
  thisLine = MyTextIO.trimPhrase(thisLine);

//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  size.width = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);

//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  size.height = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);

//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  numAreaMarkers = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);
  areaMarkers = new AreaMarker[numAreaMarkers];

//  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  numActors = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);
  areaActors = new LivingObject[numActors];

  //  Popup.createInfoPopup(thisLine);

  thisLine = MyTextIO.trimPhrase(thisLine);
  numProps = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
  thisLine = MyTextIO.trimPhrase(thisLine);
  areaProps = new NonLivingObject[numProps];

  // now go through the arrays to load markers
    for (int i = 0;  i < areaMarkers.length; i++){
      areaMarkers[i] = new AreaMarker(br);
    } // end for loop

    // now go through the arrays to load actors
    for (int i = 0;  i < numActors; i++){
      areaActors[i].readFromFile(br);  // will this call the deepest read method?
    } // end for loop

    // now go through the arrays to load props
    for (int i = 0;  i < numProps; i++){
      areaProps[i].readFromFile(br);  // will this call the deepest read method?
    } // end for loop

//    Popup.createInfoPopup("Area(br) constructor finished, returning...");
} // end constructor

//************************************************************************
Area(int areaTypeInt){
/* the purpose of this constructor is to create a new area given a certain type.
 * Based on that type, it will generate:
 * -> the correct background tiles based on type, (forest, grass, desert, etc.)
 * -> non-living objects, (Mountains, rivers, rocks, buildings, fences, chests, decoration, etc.)
 * -> activators (attached to non-living objects), (stashes, chests, doors, areaEdges, traps, etc.)
 * -> living objects, (animals, monsters, etc.)
 * -> social objects, (NPCs, townfolk, etc.)
 * -> and items. (loose items on the ground)
 */

} // end constructor(String)

//************************************************************************
Area(Point loc, int room){
  // this constructor includes specific directions gathered from the premade area found at location loc and room.

  /* if room == 0, then it is the exterior. - get the filename from loc.
   * Else, we are dealing with an interior. - get the filename from loc and room.
   */

  // get area numbers from loc

  // start with the basics
  areaID = "area" + String.valueOf(loc.x) + "x"
                  + String.valueOf(loc.y) + "y"
                  + String.valueOf(room);

  // get file from the String

  // openfile(areaFileName) ? or whatever the document processing code is...

  // call private methods for processing document and populating area.

  // <insert method calls here>

} // end constructor(Location, int)

//************************************************************************
Area(Point p){
  // This constructor is called to generate a new random area for the given settings at loc.
  // This, without a room number, assumes it is the main, exterior area.

  this(p, 0);  // just set the room to zero, which assumes the exterior.

} // end constructor(Location)

//************************************************************************
// package methods
//************************************************************************
  String getAreaName(){return areaName;} // end getName
  String getID()      {return areaID;}   // end getID

//************************************************************************
  String getMapPic(){
//    Popup.createInfoPopup("getting mapPic: '" + mapPic + "'");
    return mapPic;
  }   // end getMapPic

//************************************************************************
 ImageIcon generateMapPic(){
   // purpose of this method is to create the map picture from the actual area
   // details.  This requires the size ratio between the screen size and the area
   // size to determine the number of pixels to draw.

   // for now, just return...
   if (size == null) return null;

   int pixelReductionH = (size.height / AreaPanel.mapDisplayHeight);
   int pixelReductionW = (size.width / AreaPanel.mapDisplayWidth);

   // create a new image.
   BufferedImage mapPic = new BufferedImage(AreaPanel.mapDisplayWidth,
                                            AreaPanel.mapDisplayWidth,
                                            BufferedImage.TYPE_INT_RGB);

   // now, draw on the new image based on the informatin of the area.

   // save the image to a filename for later use.

   // return the filename
   return null;
 } // end generateMapPic()

//************************************************************************
  void add(GameObject o){

    /* NOTE: notice the abstract className in the parameter list.
     * We will need to overwrite this method with specific classTypes as we need them.
     * the abstract apect will lead to difficulties when handling interaction.
     */

    /* The purpose of this method is to add a new object to the area.
     * This will be called many times in order to populate the area with
     * static objects as well as interactive social objects.
     * One major concern of this method will be to handle expanding
     * the area's GameObject array as needed.
     */

} // end add(GameObject)

//************************************************************************
  void addNewMarker(Point p, String text, String markerIconFileName){
    // purpose of this method is to add the new marker to the array of markers
    // for the displayed area, "thisArea".

    // find first open array index, if none, then ammend the array.
    int i = 0;
    for (i = 0; i < areaMarkers.length; i++){
      if (areaMarkers[i] == null) break;
    } // end for loop

    if (i >= areaMarkers.length - 1){
      // then ammend the array
      areaMarkers = expandMarkerArray(areaMarkers, 1);
    } // end if full

    // store the new marker into the array at appropriate index.
    areaMarkers[i] = new AreaMarker(p, text, markerIconFileName);

    // update the info:
    numAreaMarkers += 1;

  } // end addNewMarker

//************************************************************************
  void writeToFile(String filePath){
    // purpose of this method is to write to file every piece of information
    // necessary to remember all the details of this area.
    // this needs to match the constructor exactly!

    String fileName = "area";
    if (mapLoc.getX() < 10) fileName += "0";
    fileName += ((int)(mapLoc.getX()) + "x");

    if (mapLoc.getY() < 10) fileName += "0";
    fileName += ((int)(mapLoc.getY()) + "y");

    if (roomNumber < 10) fileName += "0";
    fileName += roomNumber;

//    Popup.createInfoPopup("When writing area to file, using area name: '" + fileName + "'");

    try{
      BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath)));

      // write all info to the newly created file.
      bw.write(               "areaID:"     + MyTextIO.tab + areaID);
      bw.write(MyTextIO.tab + "areaName:"   + MyTextIO.tab + areaName);
      bw.write(MyTextIO.tab + "areaType:"   + MyTextIO.tab + areaTypeInt);
      bw.write(MyTextIO.tab + "discovered:" + MyTextIO.tab + discovered);
      bw.write(MyTextIO.tab + "visited:"    + MyTextIO.tab + visited);
      bw.write(MyTextIO.tab + "occupied:"   + MyTextIO.tab + occupied);
      bw.write(MyTextIO.tab + "Loc X:"      + MyTextIO.tab + (int)mapLoc.getX());
      bw.write(MyTextIO.tab + "Loc Y:"      + MyTextIO.tab + (int)mapLoc.getY());
      bw.write(MyTextIO.tab + "room:"       + MyTextIO.tab + roomNumber);
      bw.write(MyTextIO.tab + "mapPic:"     + MyTextIO.tab + mapPic);
      bw.write(MyTextIO.tab + "width:"      + MyTextIO.tab + (int)size.getWidth());
      bw.write(MyTextIO.tab + "height:"     + MyTextIO.tab + (int)size.getHeight());
      bw.write(MyTextIO.tab + "numMarkers:" + MyTextIO.tab + numAreaMarkers);
      bw.write(MyTextIO.tab + "numActors:" + MyTextIO.tab + numActors);
      bw.write(MyTextIO.tab + "numProps:" + MyTextIO.tab + numProps);
      bw.newLine();

      for (int i = 0;  i < areaMarkers.length; i++){
        if (areaMarkers[i] != null){
            areaMarkers[i].writeToFile(bw);
        } // end if
      } // end for loop

      for (int i = 0;  i < numActors; i++){
        if (areaActors[i] != null){
          areaActors[i].writeToFile(bw);
        } // end if
      } // end for loop

      for (int i = 0;  i < numActors; i++){
        if (areaProps[i] != null){
          areaProps[i].writeToFile(bw);
        } // end if
      } // end for loop

      bw.close();
    } // end try
    catch(IOException e){
      Popup.createErrorPopup("IOException whena attempting to write to file:" +
                             MyTextIO.newline + filePath + ", " + fileName);
    } // end catch
  } // end writeAreaToFile

//************************************************************************
// static methods
//************************************************************************
  static Area getStartingArea() {
    return Res.getArea(FileNames.STARTING_AREA_ID);
  } // end getStartingArea

//************************************************************************
// private methods
//************************************************************************
  AreaMarker[] expandMarkerArray(AreaMarker[] markers, int inc){
    // purpose of this class it to expand the marker array by inc.
    AreaMarker[] newMarkers = new AreaMarker[markers.length + inc];
    for (int i = 0; i < markers.length; i++){
      newMarkers[i] = markers[i];
    } // end for loop

    return newMarkers;
  } // end expandArray

//************************************************************************
} // end class Area
