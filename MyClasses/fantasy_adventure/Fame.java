package fantasy_adventure;

import java.io.*;

//************************************************************************
//imports

//************************************************************************
public class Fame implements Serializable{

  /* The purpose of this class is to encapsulate the fame rating
   * of a social object.
   *
   */

//************************************************************************
// static declarations
//************************************************************************

  static String[] FAME_TITLES = getTitlesFromFile();

//************************************************************************
// member declarations
//************************************************************************

//************************************************************************
// constructors
//************************************************************************

//************************************************************************
// public methods
//************************************************************************

//************************************************************************
// package methods
//************************************************************************

//************************************************************************
// private methods
//************************************************************************

  static private String[] getTitlesFromFile(){
    // purpose of this method is to read in the title arrays
    // to be repeatedly accessed during the program.

    String [] titles = new String[MyTextIO.getNumLines(FileNames.FAME_TITLES) - 2]; // one for the title, one for the index 0

    try{
      BufferedReader br = new BufferedReader(new FileReader(FileNames.FAME_TITLES));
      br.readLine(); // remove titles

      for (int i = 0; i < titles.length; i++){
        // for each index in the array, read the line from the file into the array.
        titles[i] = br.readLine();
//        Popup.createInfoPopup("Line read:" + MyTextIO.newline + titles[i]);
      } // end for loop

      br.close();
    } // end try

    catch(IOException e){
      Popup.createErrorPopup("Error: IO exception while reading file: " + FileNames.FAME_TITLES);
    } // end catch

    return titles;

  } // end getTitlesFromFile

//************************************************************************
// static methods
//************************************************************************

  static String getFameTitle(int fame){
    // purpose of this method is to read the int passed
    // and return the String title that best fits the value.

    // since the majority of calls will be for low fame first,
    // starting from the befinning of the list is fine.

    // go through each title, and parse off the minimum/maximum values
    // once found the correct value, return the final title word.

    String s = "";

    for (int i = 0; i < FAME_TITLES.length; i++){
      // for each line, we want to check the max value,
      // since that will tell us when to stop.
      s = new String(FAME_TITLES[i]);

      s = MyTextIO.trimPhrase(s); // removes the minimum
      int max = Integer.parseInt(MyTextIO.getNextPhrase(s)); // capture value

      if (fame > max){ // if greater, then move to next title
        continue;
      } // end if

      else { // i > max
        // then we've hit the correct fame title, so return result
        s = MyTextIO.trimPhrase(s); // removes the value
        return MyTextIO.getNextPhrase(s); // capture and return title.
      } // end else

    } // end for loop

    Popup.createWarningPopup("Fame not found, returning first title entry");
    MyTextIO.trimPhrase(s); // removes the value
    return MyTextIO.getNextPhrase(s); // capture and return title.
  } // end getFameTitle

//************************************************************************
} // end class Fame

//************************************************************************
