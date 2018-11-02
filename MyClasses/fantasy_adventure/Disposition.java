package fantasy_adventure;

import java.io.*;

//************************************************************************
//imports


//************************************************************************

public class Disposition implements Serializable{

  /* The purpose of this class is to give the user a psychological side.
   * During the game, the character's needs and wants will change with
   * the setting and situation.  This is similar to one's attitude as well.
   * This set of information will keep track and give appropriate feedback
   * during game play.
   *
   * The numbers will be on a 1 - 100 scale.  This will affect the percent
   * chance of causing action or reaction to stimulous.
   */

//************************************************************************
// static declarations
//************************************************************************


//************************************************************************
// member declarations
//************************************************************************

  private int fear;      // manages flight, shock, paralysis tendancy
  private int courage;   // manages fight, persuance, etc.
  private int energy;    // manages speed, drowsiness, reaction-time, etc.
  private int soberiety; // manages drunkenness, humor, etc.
  private int happiness; // manages social-interaction, party dynamics, etc.
  private int resolve;   // manages persistance, etc.

//************************************************************************
// constructors
//************************************************************************

  public Disposition () {
    // the purpose of this constructor is to setup a basic set of stats.
    // this constructor is not meant to be permanent, but only a place holder
    // until the character owner can set these stats based on their attributes
    // and situation.

  fear      = 25; // a new character knows not what to fear!
  courage   = 50; // middle ground average.
  energy    = 75; // start fresh.
  soberiety = 50; // jovial, but not silly.
  happiness = 50; // middle ground average.
  resolve   = 75; // we do what we choose to do.

  } // end constructor

//************************************************************************
// public methods
//************************************************************************

//************************************************************************
// package methods
//************************************************************************

  void setFear     (int i) {fear      = i;} // end setFear
  void setCourage  (int i) {courage   = i;} // end setCourage
  void setEnergy   (int i) {energy    = i;} // end setEnergy
  void setSoberiety(int i) {soberiety = i;} // end setSoberiety
  void setHappiness(int i) {happiness = i;} // end setHappiness
  void setResolve  (int i) {resolve   = i;} // end setResolve

  int getFear     () {return fear;}      // end setFear
  int getCourage  () {return courage;}   // end setCourage
  int getEnergy   () {return energy;}    // end setEnergy
  int getSoberiety() {return soberiety;} // end setSoberiety
  int getHappiness() {return happiness;} // end setHappiness
  int getResolve  () {return resolve;}   // end setResolve

  void adjFear     (int i) {fear      += i;} // end adjFear
  void adjCourage  (int i) {courage   += i;} // end adjCourage
  void adjEnergy   (int i) {energy    += i;} // end adjEnergy
  void adjSoberiety(int i) {soberiety += i;} // end adjSoberiety
  void adjHappiness(int i) {happiness += i;} // end adjHappiness
  void adjResolve  (int i) {resolve   += i;} // end adjResolve

//************************************************************************
  void writeToFile(BufferedWriter bw) throws IOException{
    bw.write("Disposition:");

    bw.write(MyTextIO.tab + "Fear:"      + MyTextIO.tab + fear);
    bw.write(MyTextIO.tab + "Courage:"   + MyTextIO.tab + courage);
    bw.write(MyTextIO.tab + "Energy:"    + MyTextIO.tab + energy);
    bw.write(MyTextIO.tab + "Soberiety:" + MyTextIO.tab + soberiety);
    bw.write(MyTextIO.tab + "Happiness:" + MyTextIO.tab + happiness);
    bw.write(MyTextIO.tab + "Resolve:"   + MyTextIO.tab + resolve);

    bw.newLine();
  } // end writeDispositionToFile

//************************************************************************
  void readFromFile(BufferedReader br) throws IOException{
    String thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    fear = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes int

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    courage = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes int

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    energy = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes int

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    soberiety = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes int

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    happiness = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes int

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resolve = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes int

//    Popup.createInfoPopup("Remaining content on Disposition line:" + MyTextIO.newline + thisLine);
  } // end readDispositionFromFile

  String getDisposition(){
    // purpose of this method is to look at all the disposition numbers and
    // return the dominent 'feeling' of the character.


  if (fear      > 90) {return "Terrified";}
  if (courage   > 90) {return "Unstoppable";}
  if (energy    > 90) {return "Wildly Energetic";}
  if (soberiety > 90) {return "Dead Serious";}
  if (happiness > 90) {return "Joyful";}
  if (resolve   > 90) {return "Steadfast";}

  if (fear      > 75) {return "Afraid";}
  if (courage   > 75) {return "Courageous";}
  if (energy    > 75) {return "Energetic";}
  if (soberiety > 75) {return "Serious";}
  if (happiness > 75) {return "Happy";}
  if (resolve   > 75) {return "Determined";}

  if (energy > 50 && courage > 50) {return "Battle Ready";}

  if (fear      < 25) {return "Fearless";}
  if (courage   < 25) {return "Weak";}
  if (energy    < 25) {return "Lazy";}
  if (soberiety < 25) {return "Silly";}
  if (happiness < 25) {return "Sad";}
  if (resolve   < 25) {return "Apathetic";}

  if (fear      < 10) {return "";}
  if (courage   < 10) {return "";}
  if (energy    < 10) {return "";}
  if (soberiety < 10) {return "";}
  if (happiness < 10) {return "";}
  if (resolve   < 10) {return "";}

  else return "Fine";
  } // end getDisposition

//************************************************************************
// private methods
//************************************************************************


//************************************************************************
} // end class <ClassName>
//************************************************************************
