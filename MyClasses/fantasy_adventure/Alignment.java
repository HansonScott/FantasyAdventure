package fantasy_adventure;

import java.io.*;

class Alignment implements Serializable{

  /* the theory for the Alignment class is to keep track of all nine alignments
   * at the same time, keeping track of a point value for each one.
   * when we call for the character's alignment, it will just return the
   * one with the most 'dominent' alignment (essentially the one with the highest point).
   * This allows the character to adjust or change their alignment during the game.
   */

static int UNKNOWN         = 0;
static int LAWFUL_GOOD     = 1;
static int LAWFUL_NEUTRAL  = 2;
static int LAWFUL_EVIL     = 3;
static int NEUTRAL_GOOD    = 4;
static int TRUE_NEUTRAL    = 5;
static int NEUTRAL_EVIL    = 6;
static int CHAOTIC_GOOD    = 7;
static int CHAOTIC_NEUTRAL = 8;
static int CHAOTIC_EVIL    = 9;

int buffer = 4;  // this is the starting buffer, but will change later...

int LGpoints;
int LNpoints;
int LEpoints;
int NGpoints;
int TNpoints;
int NEpoints;
int CGpoints;
int CNpoints;
int CEpoints;

String name; // represents the current 'dominant' alignemnt
int value;   // represents the current 'dominant' alignment

//***************************************************************************
// constructor
//***************************************************************************

  Alignment(){
    value = 0;
    name = getAlignmentName(value);
    LGpoints = 0;
    LNpoints = 0;
    LEpoints = 0;
    NGpoints = 0;
    TNpoints = 0;
    NEpoints = 0;
    CGpoints = 0;
    CNpoints = 0;
    CEpoints = 0;
  } // end constructor

//***************************************************************************
  Alignment(int a){
  value = a;
  name = getAlignmentName(value);
    LGpoints = 0;
    LNpoints = 0;
    LEpoints = 0;
    NGpoints = 0;
    TNpoints = 0;
    NEpoints = 0;
    CGpoints = 0;
    CNpoints = 0;
    CEpoints = 0;

  // now we need to set this alignment to be dominant by points,
  // so we need to get add a number of points to that alignment.

  addAlignmentPoints(a, buffer); // 'a' must be an Alignment Constant

  } // end constructor(int)

//***************************************************************************
// package methods
//***************************************************************************

  void writeAlignmentToFile(BufferedWriter bw) throws IOException {
    // purpose of this method is to continue writing to a file
    // specifically to add this classes information to the file in progress.

    bw.write("Alignment:" + MyTextIO.tab +
             value        + MyTextIO.tab +
             name         + MyTextIO.tab); bw.newLine();

    bw.write("Alignment Points:"   + MyTextIO.tab +
              "LG:" + MyTextIO.tab + LGpoints  + MyTextIO.tab +
              "LN:" + MyTextIO.tab + LNpoints  + MyTextIO.tab +
              "LE:" + MyTextIO.tab + LEpoints  + MyTextIO.tab +
              "NG:" + MyTextIO.tab + NGpoints  + MyTextIO.tab +
              "TN:" + MyTextIO.tab + TNpoints  + MyTextIO.tab +
              "NE:" + MyTextIO.tab + NEpoints  + MyTextIO.tab +
              "CG:" + MyTextIO.tab + CGpoints  + MyTextIO.tab +
              "CN:" + MyTextIO.tab + CNpoints  + MyTextIO.tab +
              "CE:" + MyTextIO.tab + CEpoints); bw.newLine();

  } // end writeToFile

  Alignment readAlignmentFromFile(BufferedReader br) throws IOException {
    String thisLine = br.readLine();
//    Popup.createInfoPopup("reading alignment from string:" + MyTextIO.newline + thisLine);

    Alignment alignment = new Alignment();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "Alignment:"

    alignment.value    = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine);

    alignment.name     = MyTextIO.getNextPhrase(thisLine);
    thisLine = MyTextIO.trimPhrase(thisLine);

//    Popup.createWarningPopup("Alignment created:" + MyTextIO.newline + alignment.name + ", " + alignment.value);
    thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "Alignment Points:"

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "LG:"
    alignment.LGpoints = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes LCpoints

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "LN:"
    alignment.LNpoints = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes LNpoints

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "LE:"
    alignment.LEpoints = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes LEpoints

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "NG:"
    alignment.NGpoints = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes NGpoints

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "TN:"
    alignment.TNpoints = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes TNpoints

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "NE:"
    alignment.NEpoints = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes NEpoints

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "CG:"
    alignment.CGpoints = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes CGpoints

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "CN:"
    alignment.CNpoints = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes CNpoints

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "CE:"
    alignment.CEpoints = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes CEpoints

//    Popup.createWarningPopup("Alignment string has content remaining:" + MyTextIO.newline + thisLine);

    return alignment;
  } // end readAlignmentFromFile

//***************************************************************************
  void addAlignmentPoints(int alignment, int count){
    for (int i = 0; i < count; i++){
      addAlignmentPoint(alignment);
    } // end for loop
  } // end addAlignmentPoints(int, int)

//***************************************************************************
  void addAlignmentPoint(int alignment){
    // purpose of this method is to increase the point value of the alignment passed.

    if      ( alignment == LAWFUL_GOOD    ) { LGpoints++; } // end LG
    else if ( alignment == LAWFUL_NEUTRAL ) { LNpoints++; } // end LN
    else if ( alignment == LAWFUL_EVIL    ) { LEpoints++; } // end LE
    else if ( alignment == NEUTRAL_GOOD   ) { NGpoints++; } // end NG
    else if ( alignment == TRUE_NEUTRAL   ) { TNpoints++; } // end TN
    else if ( alignment == NEUTRAL_EVIL   ) { NEpoints++; } // end NE
    else if ( alignment == CHAOTIC_GOOD   ) { CGpoints++; } // end CG
    else if ( alignment == CHAOTIC_NEUTRAL) { CNpoints++; } // end CN
    else if ( alignment == CHAOTIC_EVIL   ) { CEpoints++; } // end CE
    else {
      Popup.createErrorPopup("Alignment not found, no points given.");
    } // end else

    if (alignment != value){
      // if the alignment choice is different from the dominent alignment,
      // then we'll have to check if this choice is sufficient to take dominance.

      if (getNumPoints(alignment) > getNumPoints(value) + buffer){
        // at this point, we know we have too many points in a different
        // alignment, so we need to change the dominent alignment.
        changeDominentAlignment(alignment);
      } // end if
    } // end if
  } // end addAlignmentPoint(int)

//***************************************************************************
  String getName(){return name;} // end getName

//***************************************************************************
  int getValue() {return value;} // end getValue

//***************************************************************************
  String getAlignmentName(int a){
//    Popup.createInfoPopup("Creating alignment from value: " + a);
    if      (a == UNKNOWN)         return "unknown";
    else if (a == LAWFUL_GOOD)     return "Lawful Good";
    else if (a == LAWFUL_NEUTRAL)  return "Lawful Neutral";
    else if (a == LAWFUL_EVIL)     return "Lawful Evil";
    else if (a == NEUTRAL_GOOD)    return "Neutral Good";
    else if (a == TRUE_NEUTRAL)    return "True Neutral";
    else if (a == NEUTRAL_EVIL)    return "Neutral Evil";
    else if (a == CHAOTIC_GOOD)    return "Chaotic Good";
    else if (a == CHAOTIC_NEUTRAL) return "Chaotic Neutral";
    else if (a == CHAOTIC_EVIL)    return "Chaotic Evil";
    else return "unknown";
  } // end get Name

//***************************************************************************
  int getAlignmentFromName(String a){
    if (a == "Lawful Good")     return LAWFUL_GOOD;
    if (a == "Lawful Neutral")  return LAWFUL_NEUTRAL;
    if (a == "Lawful Evil")     return LAWFUL_EVIL;
    if (a == "Neutral Good")    return NEUTRAL_GOOD;
    if (a == "True Neutral")    return TRUE_NEUTRAL;
    if (a == "Neutral Evil")    return NEUTRAL_EVIL;
    if (a == "Chaotic Good")    return CHAOTIC_GOOD;
    if (a == "Chaotic Neutral") return CHAOTIC_NEUTRAL;
    if (a == "Chaotic Evil")    return CHAOTIC_EVIL;
    else return UNKNOWN;
  } // end getFromName

//***************************************************************************
//private methods
//***************************************************************************

  private int getNumPoints(int alignment){
    // the purpose of this method is to return the number of points
    // for the passed alignment.
    // if the alignment passed is not recognized, then it returns 0.

    if      ( alignment == LAWFUL_GOOD    ) { return LGpoints; } // end LN
    else if ( alignment == LAWFUL_NEUTRAL ) { return LNpoints; } // end LN
    else if ( alignment == LAWFUL_EVIL    ) { return LEpoints; } // end LE
    else if ( alignment == NEUTRAL_GOOD   ) { return NGpoints; } // end NG
    else if ( alignment == TRUE_NEUTRAL   ) { return TNpoints; } // end TN
    else if ( alignment == NEUTRAL_EVIL   ) { return NEpoints; } // end NE
    else if ( alignment == CHAOTIC_GOOD   ) { return CGpoints; } // end CG
    else if ( alignment == CHAOTIC_NEUTRAL) { return CNpoints; } // end CN
    else if ( alignment == CHAOTIC_EVIL   ) { return CEpoints; } // end CE
    else {
      Popup.createErrorPopup("Alignment not found.");
      return 0;
    } // end else
  } // end getNumPoints

//***************************************************************************
  private void changeDominentAlignment(int alignment){
    // purpose of this method is to change the alignment settings to the new alignment,
    // since PRE: this new alignment's points are higher than the current one's.
    value  = alignment;
    name   = getAlignmentName(alignment);
    buffer+= 2; // makes it harder to change back and forth between alignments...
  } // end changeDominentAlignment(int)
//***************************************************************************
} // end class Alignment
