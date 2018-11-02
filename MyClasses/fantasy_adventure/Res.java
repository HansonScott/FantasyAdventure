package fantasy_adventure;

//*******************************************************************************
import java.util.*;
import javax.swing.ImageIcon;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;

//*******************************************************************************
class Res implements Serializable{
  // the purpose of this class is to have a central place to gather all of our
  // different resource HashMaps into one place.

//*******************************************************************************
// static declarations
//*******************************************************************************

  static HashMap pics = new HashMap();
  static HashMap sounds = new HashMap(); // eventually
  static HashMap areas = new HashMap();

//*******************************************************************************
// static methods
//*******************************************************************************
  static ImageIcon getImage(String fileName){
    // purpose of this method is to return the image desired.  If we have it in memory,
    // then return it immediately, if not, then load it from the file and store it in the
    // hasMap, and return the newly made reference.

    // because we will be getting the entire filepath, we can store the path as the key.
    ImageIcon img = (ImageIcon)pics.get(fileName);

    if (img != null){
//      Popup.createInfoPopup("Image recognized from resource array,"
//                           + MyTextIO.newline
//                           +"skipping new file load!");
    } // end not null

    if (img == null) {
      try {
        img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(fileName));

//        if (img != null){Popup.createInfoPopup("img file found: '"  + fileName + "', returning img.");}
//        if (img == null){Popup.createInfoPopup("img file not found: '"  + fileName + "', returning null.");}

      } // end try

      catch(IllegalArgumentException e){
        Popup.createErrorPopup("Illegal Armument Exception while opening file: " + fileName);
        return null;
      } // end catch

      pics.put(fileName, img);
    } // end if

    return img;
  } // end getImage

//*******************************************************************************
  static Area getArea(String fileName){
    // purpose of this method is to return the area desired.
    // if we have the area in memory, then return it immediately,
    // if not, then laod it from the file and store it in the hasMap for later.
    // then return the newly created reference.

//    Popup.createInfoPopup("Res attempting to get area: '" + fileName + "'");

    // because we will be getting the entire filepath, we can store the path as the key.
    Area area = (Area)areas.get(fileName);
    if (area == null){
      try{
        BufferedReader br = new BufferedReader(new FileReader (fileName));
        area = new Area(br);
//        Popup.createInfoPopup("new area created from file: " + area.getAreaName());
        areas.put(fileName, area);
      } // end try
      catch(FileNotFoundException e){
        Popup.createErrorPopup("Res: file not found exception when attempting to find file: '" + fileName + "'");
        return null;
      } // end catch
      catch(IOException e){
        Popup.createErrorPopup("IO Exception when getting area: '" + fileName + "'");
        return null;
      } // end catch
    } //end null
    return area;
  } // end getArea

//*******************************************************************************
} // end class Res
