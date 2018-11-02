package fantasy_adventure;

import java.io.*;

//************************************************************************
//imports


//************************************************************************

public class Quality implements Serializable{

  /* The purpose of this class is to hold all the different qualities
   * in the game and set/get all related stats for item generation
   */

//************************************************************************
// static declarations
//************************************************************************

  static Quality[] qualities = createQualitiesFromFile();

//************************************************************************
// member declarations
//************************************************************************

  // the name is a simple string that will be used to identify and
  // to label the quality of an item.
  private String name;

  // the factors are all ratings that will affect number calculation for
  // an item's properties.  These numbers should be percentages mostly,
  // except for value and break, which are unique.
  //
  private int    weightFactor,   // percentage
                 damageFactor,   // percentage
                 defenseFactor,  // percentage
                 attackFactor,   // percentage
                 castFailFactor, // percentage
                 breakFactor,    // percentage
                 valueFactor,    // percentage
                 magicFactor;    // percentage

//************************************************************************
// constructors
//************************************************************************

  public Quality (String s) {

    // go through the fields of this line and capture the variables.
    weightFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s);

    damageFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s);

    defenseFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s);

    attackFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s);

    castFailFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s);

    breakFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s);

    valueFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s);

    magicFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s);

    name = MyTextIO.getNextPhrase(s);
    s = MyTextIO.trimPhrase(s);

  } // end constructor

//************************************************************************
// public methods
//************************************************************************

//************************************************************************
// package methods
//************************************************************************
  String getName           () {return name;}           // end getName
  int    getWeightFactor   () {return weightFactor;}   // end getWeightFactor
  int    getDamageFactor   () {return damageFactor;}   // end getDamageFactor
  int    getDefenseFactor  () {return defenseFactor;}  // end getDefenseFactor
  int    getAttackFactor   () {return attackFactor;}   // end getAttackFactor
  int    getCastFailFactor () {return castFailFactor;} // end getCastFailFactor
  int    getBreakFactor    () {return breakFactor;}    // end getBreakFactor
  int    getValueFactor    () {return valueFactor;}    // end getValueFactor
  int    getMagicFactor    () {return magicFactor;}    // end getMagicFactor

/* NOTE: since data is permanent, there should be not 'set' after creation.

  void setName           (String s) {name           = s;} // end setName
  void setWeightFactor   (int i;)   {weightFactor   = i;} // end setWeightFactor
  void setDamageFactor   (int i;)   {damageFactor   = i;} // end setDamageFactor
  void setDefenseFactor  (int i;)   {defenseFactor  = i;} // end setDefenseFactor
  void setAttackFactor   (int i;)   {attackFactor   = i;} // end setAttackFactor
  void setCastFailFactor (int i;)   {castFailFactor = i;} // end setCastFailFactor
  void setBreakFactor    (int i;)   {breakFactor    = i;} // end setBreakFactor
  void setValueFactor    (int i;)   {valueFactor    = i;} // end setValueFactor
  void setMagicFactor    (int i;)   {magicFactor    = i;} // end setMagicFactor
*/

//************************************************************************
// private methods
//************************************************************************

//************************************************************************
// static methods
//************************************************************************
  static Quality getQualityFromName(String s){
    // look for the material that has the name given.

    for (int i = 0; i < qualities.length; i++){
      if (qualities[i].getName().equalsIgnoreCase(s)){
        return qualities[i];
      } // end if found
    } // end for loop

    Popup.createInfoPopup("Material: '" + s + "' not found, returning null");
    return null;
  } // end getMaterialFromName

//************************************************************************
  static Quality generateRandomQuality(){
    // purpose of this method is to returna random material.
    // this is called MANY times and used in creation of an item.

    return qualities[Roll.D(qualities.length) - 1];

  } // end getRandomMaterial

//************************************************************************
  private static Quality[] createQualitiesFromFile(){
    // purpose of this method is to be called once,
    // loading all qualities for the game from a file.
    // this allows for external editing of the content of the game.

    qualities = new Quality[MyTextIO.getNumLines(FileNames.QUALITIES) - 2]; // -2 for the title and blank

    try{
      BufferedReader br = new BufferedReader(new FileReader(FileNames.QUALITIES));
      // now that we know it exists
      // remove the title line
      br.readLine();

      // and read in new materials into static array.
      for (int i = 0; i < qualities.length; i++){
        // for each material in the array
        qualities[i] = new Quality(br.readLine());
      } // end for loop

      br.close();
      return qualities;
    } // end try

    catch(FileNotFoundException e){
      Popup.createErrorPopup("Qualities File not found"
                            + MyTextIO.newline
                            + "'" + FileNames.QUALITIES + "'");
      return null;
    } // end catch

    catch(IOException evt){
      Popup.createErrorPopup("IO error when reading from Qualities File."
                            + MyTextIO.newline
                            + "'" + FileNames.QUALITIES + "'");
      return null;
    } // end catch

  } // end createQualitiesFromFile()

//************************************************************************
} // end class Quality
//************************************************************************
