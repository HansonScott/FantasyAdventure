package fantasy_adventure;

import java.io.*;

//***********************************************************
// imports
//***********************************************************

class Biography{

  // purpose of this class is to encapsolate various information
  // about a character in terms of historial data.
  // this is attached to all social objects, so it needs to
  // be a member variable of the SocialObject class.(done)

  // this class also has a static UI that shows the data
  // for the chosen character.  this UI is simply a read-only
  // popup of sorts that shows all the current data.

  // the data of the biography should be updated throughout
  // the game's progress, and stored with the character.

//***********************************************************
// static declarations
//***********************************************************

//***********************************************************
// member declarations
//***********************************************************

//***********************************************************
// constructors
//***********************************************************

  Biography(){} // end constructor

//***********************************************************
// public methods
//***********************************************************

//***********************************************************
// package methods
//***********************************************************
  void writeToFile(BufferedWriter bw)throws IOException{
    // purpose of this method is to add the content of the Biography
    // to the given writer

    bw.write("Biography:");
    bw.newLine();
    bw.flush();

  } // end writeBiographyToFile(bw)

  void readFromFile(BufferedReader br)throws IOException{
    // purpose of this method is to read the biography material
    // from the given reader
    String thisLine = br.readLine();

  }// end readBiographyFromFile(br)

//***********************************************************
// private methods
//***********************************************************

//***********************************************************
// static methods
//***********************************************************

} // end class
