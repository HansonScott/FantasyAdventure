package fantasy_adventure;

import java.io.*;

//imports


//******************************************************************************
abstract class NonLivingObject extends GameObject{

  /* this class is an abstract class to setup objects and methods
   * of all non-living things in the game.  NonLiving objects
   * have the purpose of impeding or aiding Living objects
   * as they interact with one another.
   */

//******************************************************************************
// declarations
//******************************************************************************

  /* all NonLiving objects can be picked up or moved, regardless
   * of size.  This allows for dramatic superhuman abilities of
   * moving houses, mountains, trees, etc.  As long as the character
   * has adequate strength. (use LOTR trolls and giants for theoretical reference)
   */

private int weight;  // expressed in pounds

//******************************************************************************
// constructors
//******************************************************************************
public NonLivingObject (){
  // start with the parent
  super();

} // end constructor

//******************************************************************************
// initializations
//******************************************************************************


//******************************************************************************
// end constructors
//******************************************************************************


//******************************************************************************
// package methods
//******************************************************************************

void setWeight (int i) {weight = i;}    // end setWeight
int  getWeight ()      {return weight;} // end getWeight

//******************************************************************************

void writeToFile(BufferedWriter bw) throws IOException{
  // purpose of this method is to write the information from this class to
  // the file passed.

  // start by writing parent information first
  super.writeToFile(bw);

  // now we can write the specific info from this class
  bw.write("NonLivingObject properties:");
  bw.write(MyTextIO.tab + "Object Weight:" + MyTextIO.tab + weight);
  bw.newLine();
} // end writeToFile

//******************************************************************************
void readFromFile(BufferedReader br) throws IOException{
  // purpose of this method is to read the information from the file passed.

  // start by reading parent information first
  super.readFromFile(br);

  // now we can write the specific info from this FileLine
  String thisLine = br.readLine();

//  Popup.createInfoPopup("Attempting to read Non-LivingObject from file, given string:" + MyTextIO.newline + thisLine);

  // now, from this line, we can filli nthe information
  thisLine = MyTextIO.trimPhrase(thisLine); // removes "NonLivingObject properties:"
  thisLine = MyTextIO.trimPhrase(thisLine); // removes "Object Weight"
  weight = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));

} // end writeToFile

//******************************************************************************
// private methods
//******************************************************************************

} // end class NonLivingObject
