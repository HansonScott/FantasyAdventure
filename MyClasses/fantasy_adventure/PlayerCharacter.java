package fantasy_adventure;

//**********************************************************************
// imports
//**********************************************************************
import java.io.*;
import javax.swing.*;

//**********************************************************************
class PlayerCharacter extends SocialObject{

//**********************************************************************
// static variables
//**********************************************************************
  private static ImageIcon mapIcon = new ImageIcon(FileNames.PC_MAP_ICON);
  private static String    currentCharFile; // temp holding place for filePath transfer

//**********************************************************************
// member variables
//**********************************************************************
  String         newCharDir; // to place all the sub files into the new folder.
  String         oldCharDir; // to remember where all the old sub files are
  Journal        journal;
  QuestList      questList;
  String[]       encounters; // used for monster/NPC database.
  Area           currentArea;
  SocialObject[] party;

//**********************************************************************
// Constructors
//**********************************************************************
  PlayerCharacter(){
    super();
    journal     = new Journal();
    questList   = new QuestList();
    encounters  = loadBasicEncounters();
    currentArea = Area.getStartingArea();
    oldCharDir  = Constants.INSTALL_DIRECTORY + File.separator + "Data";
    party       = new SocialObject[Constants.MAX_CHARS_IN_PARTY];
  } // end constructor

//**********************************************************************
  PlayerCharacter(File charFile){
    // purpose of this constructor is to read the passed file
    // and read each line to create and fill in all the details of the character.

    this();
    try{
      BufferedReader br = new BufferedReader(new FileReader(charFile.getPath()));
      readFromFile(br);

      // capture the filename for the old save path, and write it to the character
      currentCharFile = charFile.getPath();
      this.oldCharDir = currentCharFile.substring(0, currentCharFile.length() - 4);
      br.close();
    } // end try

    catch(IOException e){
      Popup.createErrorPopup("IO exception while reading character file.");
    } // end catch

  } // end constructor

//**********************************************************************
// package methods
//**********************************************************************
  public ImageIcon getMapIcon() {
    // purpose of this method is to return the mapIcon of the PC
    return mapIcon;
  } // end getMapIcon()

//**********************************************************************
  void setCurrentArea(Area a){currentArea = a;} // end set current area
  Area getCurrentArea(){return currentArea;} // end getCurrentArea

//**********************************************************************
  int  getNumInParty() {
    // purpose of this method is to return the number of social objects
    // that are with the PC.  NOTE: this does not include the PC!
    int result = 0;
    for (int i = 0; i < party.length; i++){
      if (party[i] != null) result++;
    } // end for loop
    return result;
  } // end getNumInParty

//**********************************************************************
  void saveToFile(File charFile){
    // purpose of this method is to create a file in the new directory
    // notice we capture the new charFile and therefore adjust all the subfiles
    // according to this newly created directory.

//   Popup.createInfoPopup("newCharFile Path:" + MyTextIO.newline + charFile);
   newCharDir = charFile.getPath().substring(0, charFile.getPath().length() - 4);
//   Popup.createInfoPopup("newCharDir set to: '" + newCharDir + "'");
   new File(newCharDir).mkdir();
   new File(newCharDir, "Areas").mkdir();

   journal.setFilePath(new File(newCharDir, "journal.txt").getPath());
//   Popup.createInfoPopup("journal save Path:" + MyTextIO.newline + "'" + journal.getFilePath() + "'");

   questList.setFilePath(new File(newCharDir, "quests.txt").getPath());
//   Popup.createInfoPopup("quests save Path:" + MyTextIO.newline + "'" + questList.getFilePath() + "'");

    try{
      BufferedWriter bw = new BufferedWriter(new FileWriter(charFile.getPath()));
      writeToFile(bw);
      bw.close();
    } // end try

    catch(IOException e){
      Popup.createErrorPopup("IO exception when write to character file.");
    } // end catch
  } // end saveToFile(charFile)

//**********************************************************************
  void writeToFile(BufferedWriter bw) throws IOException{
    // the purpose of this method is to go through ALL details of the character
    // and write each one to a line in the file.
    // start with writing lines from parent class:
    super.writeToFile(bw);

    // now we can add specific character variables
    bw.write("PlayerCharacter properties:");
    bw.newLine();

    bw.write("CharDir:"+ MyTextIO.tab + newCharDir);
    bw.write(MyTextIO.tab + "current area:" + MyTextIO.tab + currentArea.getID());
    bw.newLine();

    bw.write("Journal path:" + MyTextIO.tab + journal.getFilePath());
    bw.newLine();

    bw.write("QuestList path:" + MyTextIO.tab + questList.getFilePath());
    bw.newLine();

    writeEncounters(bw);
    bw.close();

    // set external files from char info
    journal.writeJournalToFile();
    questList.writeQuestListToFile();

    // transfer all areas from old charDir to new charDir
    transferOldAreasToNewFolder();

    // save all newly changed areas in memory to new charDir
    saveAllAreasToFile();

  } // end writePlayerCharacterToFile(bw);

//**********************************************************************
   void readFromFile(BufferedReader br) throws IOException{
     // purpose is to use br to fill a new character.
     // start with parent class
     super.readFromFile(br);

     String thisLine = br.readLine(); // removes "PlayerCharacter properties:"

     // read charDir
     thisLine = br.readLine();
     thisLine = MyTextIO.trimPhrase(thisLine);

     // we use the old directory, because this is the only one we know, until resaving time.
//     Popup.createInfoPopup("char read from file / oldcharDir, thisLine reads:" + MyTextIO.newline + "'" + thisLine + "'");
     oldCharDir = MyTextIO.getNextPhrase(thisLine);
     thisLine = MyTextIO.trimPhrase(thisLine); // removes oldCharDir
//     Popup.createInfoPopup("PC/ReadFromFile: oldCharDir set to: '" + oldCharDir + "'");


     thisLine = MyTextIO.trimPhrase(thisLine); // removes "current dir:"
//     Popup.createInfoPopup("when reading character from file, thisLine reads:" + MyTextIO.newline + "'" + thisLine + "'");
     currentArea = Res.getArea(new File(oldCharDir + File.separator + "Areas" + File.separator +
                                        MyTextIO.getNextPhrase(thisLine) + ".csv").getPath());

     // read journal path
     journal.setFilePath(MyTextIO.trimPhrase(br.readLine()));

     // read questList path
     questList.setFilePath(MyTextIO.trimPhrase(br.readLine()));

     // read encounters path
     thisLine = br.readLine();
     readEncounters(thisLine, br.readLine());

     br.close();

    // get external files from char info
     journal.readJournalFromFile();
     questList.readQuestListFromFile();

   } // end readCharFromFile

//**********************************************************************
  private void readEncounters(String firstLine, String thisLine){
    // the purpose of this method is to read the string and create the
    // array and fill it with encounters (race names)
    firstLine = MyTextIO.trimPhrase(firstLine);
    encounters = new String[Integer.parseInt(MyTextIO.getNextPhrase(firstLine))];

    // Now, we can fill the array with 'thisLine'
    for (int i = 0; i < encounters.length; i++){
      if (thisLine == null) break;
      encounters[i] = MyTextIO.getNextPhrase(thisLine);
      thisLine = MyTextIO.trimPhrase(thisLine);
    } // end for loop
  } // end readEncounters

//**********************************************************************
  private void writeEncounters(BufferedWriter bw) throws IOException{
    // purpose of this method is to write all the known encounters (race names)
    // to the file.
    bw.write("Encounters:" + MyTextIO.tab + encounters.length);
    bw.newLine();
    String thisLine = "";
    for (int i = 0; i < encounters.length; i++){
      if (encounters[i] != null) thisLine = thisLine + encounters[i] + MyTextIO.tab;
    } // end for loop
    bw.write(thisLine);
    bw.newLine();
  } // end writeEncounters

//**********************************************************************
  private String[] loadBasicEncounters(){
    // purpose of this method is to load all of the playable races as
    // the starting encounterDatabase

    // use the static array RACES for reference, and use all races that are
    // playable to start, but add all encounters from then on.
    return Race.getPlayableRaceNames();
  } // end loadBasicEncounters

//**********************************************************************
  private void transferOldAreasToNewFolder(){
    // purpose of this method is to copy all the old (but unique) area files
    // from the old save game to the new save game.

    // if we just started, then we will have no oldCharDir, so return
    if (oldCharDir == null){
      Popup.createInfoPopup("oldCharDir = null, skipping area transfer.");
      return;
    } // end null

    // get a list of all the area files in the old directory
//    Popup.createInfoPopup("getting files to transfer from: '" + oldCharDir + File.separator + "Areas'");
    File[] areas = (new File(oldCharDir + File.separator + "Areas")).listFiles(new FileFilter(){
      public boolean accept(File f){
//        Popup.createInfoPopup("file filter checking to include file: " + f.getPath());
        if ( f.getPath().endsWith(".csv") || f.getPath().endsWith(".gif")){
//          Popup.createInfoPopup("filename matched requirements, returning true.");
          return true;
        } // end if
//        Popup.createInfoPopup("filename did not match requirements, returning false.");
        return false;
      }// end accept
    }// end internal FileFilter class
    ); // end listFiles

    // go through the list...
//    Popup.createInfoPopup("Copying all areas..." + MyTextIO.newline +
//                          "oldCharDir: '" + oldCharDir + "'" + MyTextIO.newline +
//                          "newCharDir: '" + newCharDir + "'");
    if (areas == null) return;
    for (int i = 0; i < areas.length; i++){
      // and copy each area over to the new directory.

      MyUtils.copyFile(areas[i], new File(newCharDir + File.separator + "Areas"+ File.separator +
                                          areas[i].getName().substring(areas[i].getName().length()-16,
                                          areas[i].getName().length())));
    } // end for loop
  } // end transfer

//**********************************************************************
  private void saveAllAreasToFile(){
    // purpose of this method is to save all the newly changed area files
    // into the new save game.
    // NOTE: this will overwrite some of the area files we just transfered, but
    // it is simple and will be faster than keeping track of area version, etc.

    // go through the Res.getArea() array and save all of them to the newCharDir.
    this.getCurrentArea().writeToFile(this.newCharDir + File.separator +
                                      "Areas" + File.separator +
                                      this.getCurrentArea().areaID + ".csv");
  } // end saveAreas

//**********************************************************************
} // end class PlayerCharacter
