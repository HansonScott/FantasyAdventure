package fantasy_adventure;

//*****************************************************************************
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

//*****************************************************************************
class AreaMarker extends JButton implements MouseListener{
  // purpose of this class is to hold and handle the UI of the area map markers.
  // this should hold the UI for changing the tag associated with the icon
  // as well as (future) changing the icon associated with this marker.

//*****************************************************************************
// static declarations
//*****************************************************************************

//*****************************************************************************
// member declarations
//*****************************************************************************

  String     markerText; // will be replaced by user or plot.
  String     markerImage; // future: have many colors available?
  Point      markerPoint;

//*****************************************************************************
// constructor
//*****************************************************************************
  public AreaMarker (){
    super(Res.getImage(FileNames.DEFAULT_AREA_MARKER));
    markerText = "<marker text>";
    markerPoint = new Point(50,50);
  } // end defaultConstructor

//*****************************************************************************
  public AreaMarker(BufferedReader br) throws IOException{
    // purpose of this constructor is to create a new areaMarker directly
    // from the buffered reader, so the filename is assumed and opened already.
    this();


    String thisLine = br.readLine();
//    Popup.createInfoPopup("reading area Marker from file.  Setting marker Text as: " + thisLine);
    setMarkerText(thisLine);
//    Popup.createInfoPopup("marker Text set as: " + markerText);

    thisLine = br.readLine();
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "image:"
    markerImage = MyTextIO.getNextPhrase(thisLine); // captures filename
    thisLine = MyTextIO.trimPhrase(thisLine); // removes filename

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "x:"
    markerPoint.x = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes x

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "y:"
    markerPoint.y = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes y

  } // end constructor

//*****************************************************************************
  public AreaMarker (Point p, String t){
    this();
    setPoint(p);
    setText(t);
  } // end point/text constructor

//*****************************************************************************
  public AreaMarker (Point p, String t, String imageIconFileName){
    super(Res.getImage(imageIconFileName));
//    Popup.createInfoPopup("Creating new Area marker from the following info:");
//    Popup.createInfoPopup("Point p: " + p.x + ", " + p.y);
//    Popup.createInfoPopup("Text: '" + t + "'");
//    Popup.createInfoPopup("ImageName: '" + imageIconFileName + "'");
    markerImage = imageIconFileName;
    setMarkerText(t);

    // for the point, we need to adjust the size of the marker,
    // so it appears to the user that the marker is centered over where they clicked.
    setPoint(p);
    markerPoint.x = markerPoint.x - (int)((Res.getImage(imageIconFileName)).getIconWidth() / 2);
    markerPoint.y = markerPoint.y - (int)((Res.getImage(imageIconFileName)).getIconHeight() / 2);
  } // end imageConstructor

//*****************************************************************************
// public methods
//*****************************************************************************
  public void mousePressed(MouseEvent me){
  } // end mousePressed

//*****************************************************************************
  public void mouseReleased(MouseEvent me){
  } // end mouseReleased

//*****************************************************************************
  public void mouseClicked(MouseEvent me){
    // user has selected this button, show text.
  } // end mousePressed

//*****************************************************************************
  public void mouseEntered(MouseEvent me){
    // user has hovered over button, highlight and show text.
  } // end mousePressed

//*****************************************************************************
  public void mouseExited(MouseEvent me){
    // user has left button, unhighlight and clear text.
  } // end mousePressed

//*****************************************************************************
// package methods
//*****************************************************************************
  void setPoint(Point p){markerPoint = p;} // end setPoint p

//*****************************************************************************
  void setMarkerText(String t){markerText = t;} // end setText

//*****************************************************************************
  void setImageIconFileName(String fileName){markerImage = fileName;} // end setImageIconFileName(imageIconFileName);

//*****************************************************************************
  void writeToFile(BufferedWriter bw) throws IOException{
    // purpose of this method is to write all the details of the area marker
    // to the passed bufferedWriter, so the filename is assumed and opened.
    bw.write(markerText);
    bw.newLine();

    bw.write(               "image:" + MyTextIO.tab + markerImage);
    bw.write(MyTextIO.tab + "x:"     + MyTextIO.tab + markerPoint.x);
    bw.write(MyTextIO.tab + "y:"     + MyTextIO.tab + markerPoint.y);
    bw.newLine();
  } // end writeAreaMarkerToFile

//*****************************************************************************
// static methods
//*****************************************************************************

//*****************************************************************************
// private methods
//*****************************************************************************
} // end class
