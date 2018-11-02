package fantasy_adventure;

import java.io.*;
import javax.swing.*;

class Race implements Serializable{

//***************************************************************************
// static declarations

  static Race[] races   = getRacesFromFile();
  static String picPath = "Images/Portraits/";

//***************************************************************************
// instance variables - each race has this data.

  private String  raceName; // this will be the key for all other race information
  private boolean playable;
  private int     avgLifespan;
  private int     baseHD, baseMD;
  private int     meleeAdj, rangedAdj, thrownAdj;
  private int     naturalDefenseAdj;
  private int     strAdj,      dexAdj,      conAdj,      intAdj,   wisAdj,    chaAdj;
  private boolean imFire,      imEnergy,    imAir,       imLife,   imWater,   imNature, imEarth, imDeath;
  private boolean imCrushing,  imPiercing,  imSlashing,  imMelee,  imRanged;
  private int     resFire,     resEnergy,   resAir,      resLife,  resWater,  resNature, resEarth, resDeath;
  private int     resCrushing, resPiercing, resSlashing, resMelee, resRanged;
  private int     saveFire,    saveEnergy,  saveAir,     saveLife, saveWater, saveNature, saveEarth, saveDeath, saveReflex, saveFort, saveSocial;
  private int     fameAdj;
  private double  goldMultiplier;
  private int     youngGoldFactor, teenGoldFactor, adultGoldFactor, matureGoldFactor, oldGoldFactor;
  private int     dumbGoldFactor, avgGoldFactor, smartGoldFactor, wizeGoldFactor;
  private String  descFileName;

//***************************************************************************
// constructors
//***************************************************************************

  public Race(){}

//***************************************************************************
  public Race (int i, String s){
    // the purpose of this constructor is to create a new race from a long line of details.
    // the index passed is irrelivant, except that is distinguishes this constructor
    // we need to go through and set all the details according to the line:
/*  <var> = Integer.parseInt(MyTextIO.getNextPhrase(s));
    <boo> = Boolean.valueOf(MyTextIO.getNextPhrase(s));
    <Str> = MyTextIO.getNextPhrase(s); */

//    Popup.createInfoPopup("Creating a new race with the line:" + MyTextIO.newline + s);

    raceName = MyTextIO.getNextPhrase(s);
    s = MyTextIO.trimPhrase(s); // remove variable

    playable = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    avgLifespan = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    baseHD = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    baseMD = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    meleeAdj = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    rangedAdj = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    thrownAdj = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    naturalDefenseAdj = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    strAdj = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    dexAdj = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    conAdj = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    intAdj = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    wisAdj = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    chaAdj = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    imFire = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imEnergy = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imAir = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imLife = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imWater = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imNature = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imEarth = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imDeath = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imCrushing = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imPiercing = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imSlashing = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imMelee = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    imRanged = Boolean.valueOf(MyTextIO.getNextPhrase(s)).booleanValue();
    s = MyTextIO.trimPhrase(s); // remove variable

    resFire = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resEnergy = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resAir = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resLife = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resWater = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resNature = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resEarth = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resDeath = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resCrushing = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resPiercing = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resSlashing = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resMelee = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    resRanged = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    saveFire = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    saveEnergy = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    saveAir = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    saveLife = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    saveWater = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    saveNature = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    saveEarth = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    saveDeath = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    saveReflex = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    saveFort = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    saveSocial = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    fameAdj = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    goldMultiplier = Double.parseDouble(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    youngGoldFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    teenGoldFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    adultGoldFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    matureGoldFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    oldGoldFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    dumbGoldFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    avgGoldFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    smartGoldFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    wizeGoldFactor = Integer.parseInt(MyTextIO.getNextPhrase(s));
    s = MyTextIO.trimPhrase(s); // remove variable

    descFileName = MyTextIO.getNextPhrase(s);
    s = MyTextIO.trimPhrase(s);

  } // end constructor from file line

//***************************************************************************
// package methods
//***************************************************************************

  String  getName()        {return raceName;}    // end getName
  boolean isPlayable()     {return playable;}    // end isPlayable

  int     getAvgLifespan() {return avgLifespan;} // end getAvgLifespan
  int     getBaseHD()      {return baseHD;}      // end getBaseHD
  int     getBaseMD()      {return baseMD;}      // end getBaseMD

  int     getMeleeAdj()    {return meleeAdj;}    // end getMeleeAdj
  int     getRangedAdj()   {return rangedAdj;}   // end getRangedAdj
  int     getThrownAdj()   {return thrownAdj;}   // end getThrownAdj
  int     getNatDefAdj()   {return naturalDefenseAdj;} // end getNatDefAdj

  int     getStrBonus()    {return strAdj;}     // end getStrAdj
  int     getDexBonus()    {return dexAdj;}     // end getDexAdj
  int     getConBonus()    {return conAdj;}     // end getConAdj
  int     getIntBonus()    {return intAdj;}     // end getIntAdj
  int     getWisBonus()    {return wisAdj;}     // end getWisAdj
  int     getChaBonus()    {return chaAdj;}     // end getChaAdj

  boolean isImFire()       {return imFire;}     // end getImFire
  boolean isImEnergy()     {return imEnergy;}   // end getImEngery
  boolean isImAir()        {return imAir;}      // end getImAir
  boolean isImLife()       {return imLife;}      // end getImLife
  boolean isImWater()      {return imWater;}    // end getImWater
  boolean isImNature()     {return imNature;}   // end getImNature
  boolean isImEarth()      {return imEarth;}    // end getImEarth
  boolean isImDeath()      {return imDeath;}    // end getImDeath

  boolean isImCrushing()   {return imCrushing;} // end getImCrushing
  boolean isImPiercing()   {return imPiercing;} // end getImPiercing
  boolean isImSlashing()   {return imSlashing;} // end getImSlashing
  boolean isImMelee()      {return imMelee;}    // end getImMelee
  boolean isImRanged()     {return imRanged;}   // end getImRanged

  int     getResFire()     {return resFire;}    // end getResFire
  int     getResEnergy()   {return resEnergy;}  // end getResEnergy
  int     getResAir()      {return resAir;}     // end getResAir
  int     getResLife()     {return resLife;}    // end getResLife
  int     getResWater()    {return resWater;}   // end getResWater
  int     getResNature()   {return resNature;}  // end getResNature
  int     getResEarth()    {return resEarth;}   // end getResEarth
  int     getResDeath()    {return resDeath;}   // end getResDeath

  int     getResCrushing() {return resCrushing;}// end getResCrushing
  int     getResPiercing() {return resPiercing;}// end getResPiercing
  int     getResSlashing() {return resSlashing;}// end getResSlashing
  int     getResMelee()    {return resMelee;}   // end getResMelee
  int     getResRanged()   {return resRanged;}  // end getResRanged

  int     getSaveFire()    {return saveFire;}    // end getSaveFire
  int     getSaveEnergy()  {return saveEnergy;}  // end getSaveEnergy
  int     getSaveAir()     {return saveAir;}     // end getSaveAir
  int     getSaveLife()    {return saveLife;}    // end getSaveLife
  int     getSaveWater()   {return saveWater;}   // end getSaveWater
  int     getSaveNature()  {return saveNature;}  // end getSaveNature
  int     getSaveEarth()   {return saveEarth;}   // end getSaveEarth
  int     getSaveDeath()   {return saveDeath;}   // end getSaveDeath

  int     getSaveReflex()  {return saveReflex;}  // end getSaveNature
  int     getSaveFort()    {return saveFort;}    // end getSaveEarth
  int     getSaveSocial()  {return saveSocial;}  // end getSaveDeath

  int     getFameAdj()     {return fameAdj;}     // end getFameAdj

  double  getGoldMultiplier()   {return goldMultiplier;}   // end getGoldMultiplier
  int     getYoungGoldFactor()  {return youngGoldFactor;}  // end getYoungGoldFactor}
  int     getTeenGoldFactor()   {return teenGoldFactor;}   // end getTeenGoldFactor
  int     getAdultGoldFactor()  {return adultGoldFactor;}  // end getAdultGoldFactor
  int     getMatureGoldFactor() {return matureGoldFactor;} // end getMatureGoldFactor
  int     getOldGoldFactor()    {return oldGoldFactor;}    // end getOldGoldFactor
  int     getDumbGoldFactor()   {return dumbGoldFactor;}   // end getDumbGoldFactor
  int     getAvgGoldFactor()    {return avgGoldFactor;}    // end getAvgGoldFactor
  int     getSmartGoldFactor()  {return smartGoldFactor;}  // end getSmartGoldFactor
  int     getWizeGoldFactor()   {return wizeGoldFactor;}   // end getWizeGoldFactor

//***************************************************************************
  String  getRaceDescription(){
    return MyTextIO.readOneLineFromFile(descFileName);
  } // end getRaceDescription

//***************************************************************************
static Race getRace (String raceName){
     // purpose of this constructor is to return reference to the race variables
     // from the static list to be used over and over during the game.
     // NOTE: we can use a reference because we will not edit the race at any time,
     // only getting information from it.

     for (int i = 0; i < races.length; i++){
       if (races[i].getName().equalsIgnoreCase(raceName)){
         return races[i];
       } // end if

     } // end for loop

     Popup.createErrorPopup("Race: '" + raceName + "' not found, returning default.");
     return races[0];
   } // default

//***************************************************************************
static String getAvgLifeSpanStr(String raceName){
  // purpose of this method is to get the average life span of a given race.
  // we need to look through the array list of races and return
  // the matching race's life span.

    for (int i = 0; i < races.length; i++){
      if (races[i].getName().equalsIgnoreCase(raceName)){
        return ("" + races[i].getAvgLifespan());
      } // end if

    } // end for loop

    Popup.createErrorPopup("Race: '" + raceName + "' not found, returning default.");
    return ("" + races[0].getAvgLifespan());

} // end getAvgLifespan()

//***************************************************************************
static int getAvgLifeSpanInt(String raceName)   {
  // purpose of this method is to get the average life span of a given race.
  // we need to look through the array list of races and return
  // the matching race's life span.

    for (int i = 0; i < races.length; i++){
      if (races[i].getName().equalsIgnoreCase(raceName)){
        return races[i].getAvgLifespan();
      } // end if

    } // end for loop

    Popup.createErrorPopup("Race: '" + raceName + "' not found, returning default.");
    return races[0].getAvgLifespan();

} // end getAvgLifespan()

//***************************************************************************
static String[] getPlayableRaceNames(){
   // the purpose of this method is to read the race names of the playable races
   // into the array.

   int numRaces = MyTextIO.getNumLines(FileNames.RACES) - 2; // -2 for the title and 0 index
   int numPlayableRaces = 0;
   String tempResult = "";

   for (int i = 0; i < numRaces; i++){
     // for each race in the array, we need to make sure that it is playable.
     if (Race.races[i].isPlayable()){
       numPlayableRaces +=1;
       tempResult += (Race.races[i].getName() + " ");
     } // end if
   } // end for loop

   //at this point, we know how many are playable, so we can create the restulting string Array

//    Popup.createInfoPopup("number of playable races: " + numPlayableRaces);

   String[] playableRaces = new String[numPlayableRaces];

   //now we can fill the array with the names of the playable races.

   for (int i = 0; i < playableRaces.length; i++){
     playableRaces[i] = MyTextIO.getNextWord(tempResult);
     tempResult = MyTextIO.trimWord(tempResult);
   } // end for loop

   return playableRaces;

  } // end getPlayableRaceNames()

//***************************************************************************
static ImageIcon getRacePic(Race r){
  // purpose of this method is to use the race given to select an appropriate picture.
  // we should get a random picture from this race's pic folder.

//  Popup.createInfoPopup("Getting pic for race: "+ r.getName());

  // first, see if there is an exact match in the main folder.
  if (new File(picPath + r.getName() + ".jpg").exists()){
//    Popup.createInfoPopup("pic exists directly");
//    Popup.createInfoPopup("attempting to load pic for path:" + MyTextIO.newline + (picPath + r.getName() + ".jpg"));
    ImageIcon pic = new ImageIcon(picPath + r.getName() + ".jpg");
    return pic;
  }   // end if
  // if not, then see if there is a subdirectory for this race
  else if(new File(picPath + r.getName()).isDirectory()){
//    Popup.createInfoPopup("pic does not exist directly, but is directory");
    File path = new File(picPath + r.getName());
    File [] files = path.listFiles();
    // since we have the array let's first check for an exact match here.
    for (int i = 0; i < files.length; i++){
      if (files[i].getName().endsWith(".jpg")){
//        Popup.createInfoPopup("pic does exist indirectly");
//        Popup.createInfoPopup("attempting to load pic for path:" + MyTextIO.newline +
//                              picPath + r.getName() + "/" + (files[i].getPath()));
        return new ImageIcon(picPath + r.getName() + "/" +
                             files[i].getPath());
      } // end if pic
    } // end for loop
    // at this point, there are no picture files in this directory, but there are files,
    // so there are probably pics in a 3rd sub-folder, check those.
//    Popup.createInfoPopup("pic does not exist directly or indirectly, checking 3rd depth.");

      int i = Roll.D(files.length) - 1;
      // for each of the files, check this it is a directory
      if (files[i].isDirectory()){
        // then we can return a random pic within this folder.
        int result = Roll.D(files[i].list().length) - 1; // minus one for the index 0
//        Popup.createInfoPopup("attempting to load pic for path:" + MyTextIO.newline +
//                              picPath + r.getName() + "/" + files[i].getName() + "/" +
//                              (files[i].list())[result]);

        // only accept files that are picture files, otherwise, call again.
        // *Caution: this will yield a circular reference if there are no pictures.
        if (!(files[i].list()[result].endsWith(".jpg") &&
            !(files[i].list()[result].endsWith(".gif")))){
//        Popup.createWarningPopup("file without jpg or gif found, trying again.");
          return getRacePic(r);
        } // end if thumbs.db file.

        return new ImageIcon(picPath +
                             r.getName() + "/" +
                             files[i].getName() + "/" +
                             (files[i].list())[result]);
      } // end dir

    // at this point, we are out of options, so return blank.
    Popup.createWarningPopup("No pics found from path: " + path.getPath());
    return null;
  } // end else if
  else{ // no pics found at all, so leave blank...
    Popup.createWarningPopup("No pics found from race: " + r.getName());
    return null;
  } // end else
} // end getRacePic

//***************************************************************************
static Race[] getRacesFromFile(){
    // purpose of this method is to read in all the races and their details from a file.

    // for each line of the file, we can create a new race and set all the details.

    Race[] races;

    try{
      races = new Race[MyTextIO.getNumLines(FileNames.RACES) - 2]; // removes title and index 0

      BufferedReader br = new BufferedReader(new FileReader(FileNames.RACES));
      br.readLine(); // removes title row

      for (int i = 0; i < races.length; i++){
        races[i] = new Race(i, br.readLine()); // create a new race from the line.
      } // end for loop

      br.close();
    } // end try

    catch(FileNotFoundException e){
      Popup.createErrorPopup("File not found: '" + FileNames.RACES + "'");
      return null;
    } // end catch

    catch(IOException e){
      Popup.createErrorPopup("IO exception while attempting to read filename: '" + FileNames.RACES + "'");
      return null;
    } // end catch

    return races;

  } // end getRacesFromFile

//***************************************************************************
// private methods
//***************************************************************************
} // end class Race
