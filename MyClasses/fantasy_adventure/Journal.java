package fantasy_adventure;

import java.io.*;

//************************************************************************
//imports


//************************************************************************

public class Journal implements Serializable{

  /* The purpose of this class is to keep track of the character's journal.
   *
   *
   *
   */

//************************************************************************
// static declarations
//************************************************************************
   static final String journalName = "journal.txt";
   static final String topicIdentifier = "<new Topic>";

//************************************************************************
// member declarations
//************************************************************************

  String  filePath; // to where the journal is saved (matches the save fileName)
  Topic[] topics;
  int     numTopics;

//************************************************************************
// constructors
//************************************************************************

  public Journal () {
    numTopics = 0;
    topics = new Topic[numTopics];
  } // end constructor

//************************************************************************
// package methods
//************************************************************************
  String getFilePath(){return filePath;} // end getFilePath

//************************************************************************
  void setFilePath(String s) {filePath = s;} // end setFilePath

//************************************************************************
  void addTopicToJournal(Topic t){
    // purpose of this method is to find the next open slot in the journal,
    int i = 0;
    for (i = 0; i < topics.length; i++){
      if (topics[i] == null) break;
    } // end for loop

    // if there are no more open slots...
   if (i == topics.length){
       // then increase the size of the journal
       numTopics += 1;
       ammendJournalLength();
   } // end if

   // then add the new topic there.
   topics[i] = t;

  } // end addTopicToJournal

//************************************************************************
  void removeTopicFromJournal(Topic t){
    if(t == null)return;
    for (int i = 0; i < topics.length; i++){
      if (topics[i] == t){
//        Popup.createInfoPopup("removing journal topic #" + i);
        topics[i] = null;
        // and slide down the rest of the items in the journal.
        for (int j = i + 1; j < topics.length; j++){
          // for the rest of the list, if the new item is non-null, slide down one.
          if (topics[j] != null){
//            Popup.createInfoPopup("sliding topic #" + j + " up one line.");
            topics [j - 1] = topics[j];
            topics[j] = null;
          } // end non-null
        } // end for loop j
      } // end if
    } // end for loop i
    numTopics -= 1;
    ammendJournalLength();
  } // end remove

//************************************************************************
  void readJournalFromFile(){
    /* purpose of this method is to make JLabel topics from the character's
     * journal entries, then fill that array with the titles & entries.

     * NOTE: the format of a journal entry, for storage purposes:
     * the name of the file is "journal.txt"
     * so the location (path) of this file is found from the playerCharacter saveName
     * as the folder, followed by "/journal.txt"
     * the first line of the journal file: "number of topics: " + tab + <int> + newline
     * for each topic, the format is:
     * the topicIdentifier + newline
     * <string Title> + newline
     * then content of each journal entry until
     * either the end of the file, or a new topic (topicIdentifier) is reached.
     */

    // so, first we need to capture the correct journal file
    try{
//      Popup.createInfoPopup("filePath, used for Journal/fileReader:" +
//                            MyTextIO.newline + "'" + filePath + "'");
      BufferedReader br = new BufferedReader(new FileReader(filePath));

      // from the file, read the first line to determine the length of the array
      String thisLine = br.readLine();

      thisLine = MyTextIO.trimPhrase(thisLine); // trims variable title "numTopics:"
      numTopics = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture variable

      ammendJournalLength();

      // just once, remove the topicIdentifier, since we will be removing it at the end
      // of each topic from now on.
      br.readLine(); // this discards a line from the file, since we have not captured it (identifier).

      // loop through the length and capture each topic
      for (int i = 0; i < topics.length; i++) {

        // since we know we will be starting with a topic, we can begin by
        // capturing the title.
        topics[i] = new Topic(br.readLine());

        // then, to support the multiple paragraph content, we need to capture each line,
        thisLine = br.readLine();

        // check that we have a line, or we're done...
        if (thisLine == null)return;

        // if it is anything else but the identifier,
        while (thisLine.equals(topicIdentifier) == false) {

          // then append it to the current topic's content.
          topics[i].appendContent(thisLine);
          thisLine = br.readLine();
          if (thisLine == null) break;
        } // end while

        // otherwise it is the topicIdentifier,
        // so continue the loop and begin a new topic.
      } // end for loop
    } // end try

    catch(IOException e){
      Popup.createErrorPopup("IOException while reading journal from file.");
    } // end catch

    return;
  } // end read

//************************************************************************
  void writeJournalToFile(){
    // PRE: the path has been set, so we can use the path variable.

    try{
      BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
      writeJournal(bw);
      bw.close();
    } // end try

    catch(IOException e){
      Popup.createErrorPopup("IO Exception when writing to journal file.");
    } // end catch

  } // end write

//************************************************************************
// private methods
//************************************************************************
  private void writeJournal(BufferedWriter bw){
  /* purpose of this method is to read the journal
   * and make JLabel topics from the character's
   * journal entries, then fill that array with the titles & entries.

   * NOTE: the format of a journal entry, for storage purposes:
   * the name of the file is "journal.txt"
   * so the location (path) of this file is found from the playerCharacter saveName
   * as the folder, followed by "/journal.txt"
   * the first line of the journal file: "number of topics: " + tab + <int> + newline
   * for each topic, the format is:
   * the topicIdentifier + newline
   * <string Title> + newline
   * then content of each journal entry until
   * either the end of the file, or a new topic (topicIdentifier) is reached.
   */
//  Popup.createInfoPopup("Attempting to write the journal to file...");

  try{
    bw.write("Number of Topics:" + MyTextIO.tab + numTopics);

    // loop through the length and capture each topic
    for (int i = 0; i < topics.length && topics[i] != null; i++) {

      bw.write(MyTextIO.newline + topicIdentifier);

      bw.write(MyTextIO.newline + topics[i].getTitle());

      bw.write(MyTextIO.newline + topics[i].getContent());
      bw.flush();

    } // end for loop
  } // end try

  catch(IOException e){
    Popup.createErrorPopup("IOException while attempting to write to journal file.");
  } // end ioexception
  } // end write Journal

//************************************************************************
  private void ammendJournalLength(){
    if (numTopics != topics.length){
      // then we need to create a new array with the correct amount.
      Topic[] temp = new Topic[numTopics];

      // and transfer the contents from the current list to the new list.
      for (int i = 0; i < topics.length && i < temp.length; i++){
        temp[i] = topics[i];
      } // end for loop

      // now, clear the old list, and redirect the new list.
      topics = null;
      topics = temp;
    } // end if
  } // end ammendJournalLength

//************************************************************************
} // end class Journal
//************************************************************************
