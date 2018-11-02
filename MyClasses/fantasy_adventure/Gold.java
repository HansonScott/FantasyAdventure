package fantasy_adventure;

import java.io.*;

//******************************************************************

public class Gold extends Item{

//******************************************************************
// static variables

  static ItemBasics BASICS_GOLD;
  static final int SMALL  = 50;
  static final int MEDIUM = 250;
  static final int LARGE  = 1000;

//******************************************************************
// member variables

//******************************************************************
// Constructors
//******************************************************************
  public Gold (){
    super();
    setType(Item.GOLD);
    basics = ItemBasics.getBasicsForType(Item.GOLD);
    calcGoldStats();
  } // end constructor

//******************************************************************
  public Gold (int v){ // used to create a specific amount of gold.
    this();
    setQuantity(v);
    calcGoldStats();
  } // end constructor

//******************************************************************
// Public methods
//******************************************************************
  void writeToFile(BufferedWriter bw) throws IOException {
    // the purpose of this method is to write this item to a one-line string,
    // using tab delimiters to separate values and varables.
    super.writeToFile(bw);

    // now we can write our own details onto the file...

  } // end writeToFile

//******************************************************************************
  static Gold readFromFile (int type, BufferedReader br) throws IOException{

    Gold thisItem = new Gold();
    thisItem.readFromFile(br);

    // now we can read our details onto the line...

    return thisItem;

  } // end readFromFile

//******************************************************************
// static methods
//******************************************************************
  public static Item generateRandomGold() {
    //Popup.createInfoPopup("Creating item: Gold");
    switch (Roll.D(3)){
      case 1:  {return new Gold(Roll.D(SMALL));}
      case 2:  {return new Gold(Roll.D(MEDIUM));}
      default: {return new Gold(Roll.D(LARGE));}
    } // end switch
  } // end generateRandomGold

//******************************************************************
// Private methods
//******************************************************************
  private void calcGoldStats(){
    setInventoryIcon(FileNames.INV_GOLD);
    basics.setValue(getQuantity());
    updateDetails();
  } // end calcGoldStats
} // end class Gold
