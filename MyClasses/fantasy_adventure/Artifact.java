package fantasy_adventure;

import java.io.*;

public class Artifact extends Item{

//******************************************************************************
// static declarations

static ItemBasics BASICS_ARTIFACT;

//******************************************************************************
// instance declarations

//******************************************************************************
// constructor
//******************************************************************************
  public Artifact (){} // default constructor

  public Artifact(int type){
    super();
    this.setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcArtifactStats();

  } // end constructor

//******************************************************************************
// public methods
//******************************************************************************
  void writeToFile(BufferedWriter bw) throws IOException {
    // the purpose of this method is to write this item to a one-line string,
    // using tab delimiters to separate values and varables.
    super.writeToFile(bw);

    // now we can write our own details onto the file...

  } // end writeToFile

//******************************************************************************
  static Artifact readFromFile (int type, BufferedReader br) throws IOException{

    Artifact thisItem = new Artifact(type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...
    return thisItem;

  } // end readFromFile


//******************************************************************************
// static methods
//******************************************************************************
  public static Item generateRandomArtifact() {

    return null; // for now, until we have artifacts to make.
  } // end generateRandomArtifact

//******************************************************************************
// private methods
//******************************************************************************
  private void calcArtifactStats(){

    // purpose of this method is to use the quality and material
    // to calculate the specific stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

    // setInventoryIcon
    // setValue
    // set other details...
  } // calcArtifactStats
//******************************************************************************
} // end class Artifact
