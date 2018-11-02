package fantasy_adventure;

import java.awt.*;
import javax.swing.*;
import java.io.Serializable;

public class Popup implements Serializable{
/* the purpose of this class is to simplify the process of creating message dialogs
 * for feedback during the writing of this project.
 */

//***************************************************************
  public static void createInfoPopup(String s){
    // user wants to show the String s as an information popup
    try{
      JOptionPane.showMessageDialog(null,s, "FYI",
                                  JOptionPane.INFORMATION_MESSAGE);
    } // end try
    catch(HeadlessException e){}

  } // end createInfoPopup

//***************************************************************
  public static void createWarningPopup(String s){
    // user wants to show the String s as a Warning popup
    try{
      JOptionPane.showMessageDialog(null, s, "Warning!",
                                  JOptionPane.WARNING_MESSAGE);
    } // end try
    catch(HeadlessException e){}

  } // end createWarningPopup

//***************************************************************
  public static void createErrorPopup(String s){
    // user wants to show the String s as an Error popup
    try{
      JOptionPane.showMessageDialog(null, s, "Error!",
                                  JOptionPane.ERROR_MESSAGE);
    } // end try
    catch(HeadlessException e){}

  } // end createErrorPopup

} // end class Popup













