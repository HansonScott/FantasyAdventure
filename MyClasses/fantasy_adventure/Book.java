package fantasy_adventure;

import java.io.*;

//***************************************************************

class Book extends Loot {

  static ItemBasics BASICS_BOOK;

//***************************************************************
  Book(int type){
    super();

    setType(type);
    basics = ItemBasics.getBasicsForType(type);
    calcBookStats();
    updateDetails();
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
  static Book readFromFile (int type, BufferedReader br) throws IOException{

    Book thisItem = new Book(type);
    thisItem.readFromFile(br);

    // now we can read our details onto the line...

    thisItem.updateDetails();

    return thisItem;

  } // end readFromFile

//***************************************************************
// static methods
//***************************************************************
  public static Item generateRandomBook() {
    Book thisItem = new Book(Item.BOOK);
    thisItem.setMaterial(Material.generateRandomMaterial());
    thisItem.setQuality(Quality.getQualityFromName("Good"));
    thisItem.calcBookStats();
    thisItem.updateDetails();
    return thisItem;
  } // end generateRandomBook()

//***************************************************************
  private void calcBookStats(){
    // purpose of this method is to use the material and quality
    // to create the detailed stats of this item.

    if (getMaterial() == null || getQuality() == null) return;

      basics.setValue(
        Formulas.calcItemStat(BASICS_BOOK.getValue(),
                              getMaterial().getValueFactor(),
                              getQuality().getValueFactor()));

      basics.setWeight(
        Formulas.calcItemStat(BASICS_BOOK.getWeight(),
                              getMaterial().getWeightFactor(),
                              getQuality().getWeightFactor()));


    basics.setName(""+ getMaterial().getName() + " bound " + Item.getTypeName(Item.BOOK));

  } // end calcBookStats

} // end class Book
