package fantasy_adventure;

import java.io.*;

//************************************************************************
//imports


//************************************************************************

class Material implements Serializable{

  /* The purpose of this class is to hold all the different materials
   * int the game and set/get all related stats for item generation
   */

//************************************************************************
// static declarations
//************************************************************************

  static Material[] materials = createMaterialsFromFile();

//************************************************************************
// member declarations
//************************************************************************

  // the name is a simple string that will be used to identify and
  // to label the material of an item.
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

  public Material (String s) {

//    Popup.createInfoPopup(s);
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
  String getName          () {return name;}           // end getName
  int    getWeightFactor  () {return weightFactor;}   // end getWeightFactor
  int    getDamageFactor  () {return damageFactor;}   // end getWeightFactor
  int    getDefenseFactor () {return defenseFactor;}  // end getWeightFactor
  int    getAttackFactor  () {return attackFactor;}   // end getWeightFactor
  int    getCastFailFactor() {return castFailFactor;} // end getWeightFactor
  int    getBreakFactor   () {return breakFactor;}    // end getWeightFactor
  int    getValueFactor   () {return valueFactor;}    // end getWeightFactor
  int    getMagicFactor   () {return magicFactor;}    // end getWeightFactor

/* NOTE: since data is permanent, there should be not 'set' after creation.

  void setName           (String s) {name           = s;} // end setName
  void setWeightFactor   (int i;)   {weightFactor   = i;} // end setWeightFactor
  void setDamageFactor   (int i;)   {damageFactor   = i;} // end setWeightFactor
  void setDefenseFactor  (int i;)   {defenseFactor  = i;} // end setWeightFactor
  void setAttackFactor   (int i;)   {attackFactor   = i;} // end setWeightFactor
  void setCastFailFactor (int i;)   {castFailFactor = i;} // end setWeightFactor
  void setBreakFactor    (int i;)   {breakFactor    = i;} // end setWeightFactor
  void setValueFactor    (int i;)   {valueFactor    = i;} // end setWeightFactor
  void setMagicFactor    (int i;)   {magicFactor    = i;} // end setWeightFactor
*/

//************************************************************************
// private methods
//************************************************************************

//************************************************************************
// static methods
//************************************************************************
  static Material getMaterialFromName(String s){
    // look for the material that has the name given.
    // we need to use the materails array to match, so if the arry is null,

    for (int i = 0; i < materials.length; i++){
      if (materials[i].getName().equalsIgnoreCase(s)){
        return materials[i];
      } // end if found
    } // end for loop

    Popup.createInfoPopup("Material: '" + s + "' not found, returning null");
    return null;
  } // end getMaterialFromName

//************************************************************************
  static Material generateRandomMaterial(){
    // purpose of this method is to returna random material.
    // this is called MANY times and used in creation of an item.

//    Popup.createInfoPopup("Generating new Material...");
    return (materials[Roll.D(materials.length) - 1]);

  } // end getRandomMaterial

//************************************************************************
  private static Material[] createMaterialsFromFile(){
    // purpose of this method is to be called once,
    // loading all materials for the game from a file.
    // this allows for external editing of the content of the game.

    int n = MyTextIO.getNumLines(FileNames.MATERIALS) - 2; // -2 for the title row and last blank

//    Popup.createInfoPopup("Num lines in materials file: " + n);
    materials = new Material[n]; // -1 for the title row
    try{
      BufferedReader br = new BufferedReader(new FileReader(FileNames.MATERIALS));
      // now that we know it exists
      // remove the title line
      br.readLine();

      // and read in new materials into static array.
      for (int i = 0; i < materials.length; i++){
        // for each material in the array
        materials[i] = new Material(br.readLine());
      } // end for loop

      br.close();
      return materials;
    } // end try

    catch(FileNotFoundException e){
      Popup.createErrorPopup("Materials File not found"
                            + MyTextIO.newline + "'"
                            + FileNames.MATERIALS + "'");
      return null;
    } // end catch

    catch(IOException evt){
      Popup.createErrorPopup("IO error when reading from Materials File."
                            + MyTextIO.newline + "'"
                            + FileNames.MATERIALS + "'");
      return null;
    } // end catch

  } // end createMaterialsFromFile()

//************************************************************************
} // end class Material
//************************************************************************
