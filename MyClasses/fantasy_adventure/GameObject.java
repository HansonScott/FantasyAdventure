package fantasy_adventure;

import java.io.*;
import java.awt.Rectangle;
import java.awt.Point;

// imports

//************************************************************************************
abstract class GameObject implements Serializable{

//************************************************************************************
  // declarations
//************************************************************************************

  public int version;

  private Rectangle position;     // all objects have a size and position, regardless of content
  private boolean   lineOfSight;  // allows sight view, removing fog and detection of objects
  private int       passable;     // int% for speed of movement adjustment (0 for false, 1 for true)
  private boolean   selectable;   // ability to mouse-select it, viewing stats or description
  private boolean   hasActivator; // item can be 'used' and activates a unique listening script
  private int       cover;        // int% of missles fired through this object stop.

//Icon icon; - stationary icon for background or stopped objects
//BoundingBox box; - for colisions, AI navigation, placement clutter, etc.

//************************************************************************************
  // constructor
//************************************************************************************

  GameObject(){

      // initialize objects and variables

        version      = Constants.VERSION;
        position     = new Rectangle();

        lineOfSight  = false;
        selectable   = false; // no feedback unless specifically wanted.
        hasActivator = false; // no added complexity unless specifically wanted.
        passable     = 100;   // default to passable for ease of gameplay, make sure to set false for decorations
        cover        = 0;     // default to no cover, for ease of gameplay.  Set to % desired for special objects.

  } // end constructor

//************************************************************************************
  //package methods
//************************************************************************************

  void     setLocation      (Point p)         {position.setLocation(p);}    // end setLocation
  void     setSize          (int w, int h)    {position.setSize(w, h);} // end setSize(int, int)
  void     setLineOfSight   (boolean LOS)     {lineOfSight = LOS;}    // end setLineOfSight(boolean)
  void     setPassable      (int p)           {passable = p;}         // end setPassable(int)
  void     setSelectable    (boolean b)       {selectable = b;}       // end setSelectable(boolean)
  void     setHasActivator  (boolean b)       {hasActivator = b;}     // end setHasActivator(boolean)
  void     setCover         (int c)           {cover = c;}            // end setCover

  Point    getLocation      () {return position.getLocation();}     // end getLocation
  int      getWidth         () {return (int)(position.getSize().getWidth());} // end getWidth
  int      getHeight        () {return (int)(position.getSize().getHeight());} // end getWidth
  boolean  allowsLineOfSight() {return lineOfSight;}  // end allowsLineOfSight(boolean)
  int      getPassable      () {return passable;}     // end getPassable()
  boolean  isSelectable     () {return selectable;}   // end isSelectable()
  boolean  hasActivator     () {return hasActivator;} // end hasActivator()
  int      getCover         () {return cover;}        // end getCover

//************************************************************************************
  void writeToFile(BufferedWriter bw) throws IOException {
    // The purpose of this method is to write all the details of this class to a string
    // and write it to the buffered writer passed.
    // Because this is an abstract class, we know this will only be a part of a larger,
    // more specific object, so we can only write one line for this layer of detail.

    bw.write("GameObject properites:");

    bw.write(MyTextIO.tab + "Version:"      + MyTextIO.tab + version);
    bw.write(MyTextIO.tab + "X Location:"   + MyTextIO.tab + (int)(getLocation().getX()));
    bw.write(MyTextIO.tab + "Y Location:"   + MyTextIO.tab + (int)(getLocation().getY()));

    bw.write(MyTextIO.tab + "Size Width:"   + MyTextIO.tab + getWidth());
    bw.write(MyTextIO.tab + "Size Height:"  + MyTextIO.tab + getHeight());

    bw.write(MyTextIO.tab + "lineOfSight:"  + MyTextIO.tab + lineOfSight);
    bw.write(MyTextIO.tab + "passable:"     + MyTextIO.tab + passable);
    bw.write(MyTextIO.tab + "selectable:"   + MyTextIO.tab + selectable);
    bw.write(MyTextIO.tab + "hasActivator:" + MyTextIO.tab + hasActivator);
    bw.write(MyTextIO.tab + "cover:"        + MyTextIO.tab + cover);
    bw.newLine();
  } // end writeToFile

//************************************************************************************
  void readFromFile(BufferedReader br) throws IOException{
    // purpose of this method is to read from br and create a GameObject

    String thisLine = br.readLine();

//    Popup.createInfoPopup("Reading GameObject from string:" + MyTextIO.newline + thisLine);

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "GameObject properties:"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "Version:"
    version = (Integer.parseInt(MyTextIO.getNextPhrase(thisLine)));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes version

    if (version != Constants.VERSION){
      Popup.createErrorPopup(  "Game object version does not match program version."
                             +   MyTextIO.newline
                             + "There may be errors while loading this file.");
    } // end version mismatch

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "XLocation:"
    int x = (Integer.parseInt(MyTextIO.getNextPhrase(thisLine)));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "x"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "YLocation:"
    int y = (Integer.parseInt(MyTextIO.getNextPhrase(thisLine)));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "y"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "Size Width:"
    int w = (Integer.parseInt(MyTextIO.getNextPhrase(thisLine)));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "w"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "Size Height:"
    int h = (Integer.parseInt(MyTextIO.getNextPhrase(thisLine)));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "h"

    position.setBounds(x, y, w, h);

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "lineOfSight:"
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
    lineOfSight = true;
    else lineOfSight = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "passable:"
    passable = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes int

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "selectable:"
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      selectable = true;
      else selectable = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false:"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "hasActivator:"
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      hasActivator = true;
      else hasActivator = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false:"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "cover:"
    cover = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes int
//    Popup.createWarningPopup("Remaining on Game Object line:" + MyTextIO.newline + "'" + thisLine + "'");
  } // end readGameObjectFromFile


//************************************************************************************
//private methods
//************************************************************************************

} // end class
