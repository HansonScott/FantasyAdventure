package fantasy_adventure;

import java.io.*;

//imports


class MyHashMap implements Serializable{

  /* The purpose of this class is to work as a HashMap, but have an index too.
   */

// member declarations

  int       length;
  String    name;
  Element[] elements;

//************************************************************************
// constructors
//************************************************************************

  public MyHashMap (String name, int i) {
    // the purpose of this constructor is to create essentially an array

    this.name = name;
    length = i;
    elements = new Element[i];

  } // end constructor

//************************************************************************
// public methods
//************************************************************************

  void put (String key, int value) {
    // the purpose of this method is to enter a value linked to the key
    // find the first blank space, and fill it with the info.

    int i = 0;
    for (i = 0; i < elements.length; i++){
      if (elements[i] == null) break;
    } // end for loop

    // now we know that i is the first null slot, so add these values.
    elements[i] = new Element(key, value);

  } // end put()

//**********************************************************************************
  int get (String key){
    // purpose of this method is to capture the value of the element with
    // the given key

    // search through array to find element with key given, and return value

    for (int i = 0; i < elements.length; i++){
      if (elements[i] == null) break;
      else if (elements[i].getKey().equals(key)) return elements[i].getValue();
    } // end for
    // if we get here, then the key was not found, so just return a default.
    Popup.createInfoPopup("Key not found: " + key);
    return 0;
  } // end get()

//**********************************************************************************
  void setValue(String key, int value){
    // Assuming the key already exists, this sets the value to the key.

//    Popup.createInfoPopup("Attempting to setValue on key: '" + key + MyTextIO.newline + "with value: " + value);

    // find the key
    for (int i = 0; i < elements.length && elements[i] != null; i++){
      if (elements[i].getKey().equals(key)){
      // if the key matches, then...
      elements[i].setValue(value);
      break;
      } // end if
    } // end for loop

  } // end value

//**********************************************************************************
  void writeMyHashMapToFile(BufferedWriter bw) throws IOException{
    bw.write(name + MyTextIO.tab + length);
    for (int i = 0; i < elements.length; i++){
      if (elements[i] == null) break;
      bw.write(MyTextIO.tab + elements[i].getKey() +
               MyTextIO.tab + elements[i].getValue());
    } // end for loop
    bw.newLine();
  } // end write

//**********************************************************************************
  static MyHashMap readMyHashMapFromFile(BufferedReader br) throws IOException{
    String thisLine = br.readLine();

    String n = MyTextIO.getNextPhrase(thisLine); // capture name
    thisLine = MyTextIO.trimPhrase(thisLine); // trim name

    int i    = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture length
    thisLine = MyTextIO.trimPhrase(thisLine); // trim length

    MyHashMap map = new MyHashMap(n, i);

    String tempKey;
    int    tempValue;

    while (thisLine != null){
      // if not null, then it must have another element in it

      tempKey = MyTextIO.getNextPhrase(thisLine);
      thisLine = MyTextIO.trimPhrase(thisLine);

      tempValue = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
      thisLine = MyTextIO.trimPhrase(thisLine);

      map.put(tempKey, tempValue);
    } // end while
    return map;
  } // end read

} // end class MyHashMap
//************************************************************************

class Element {

  private String key;
  private int    value;

  Element(String key, int value){
    this.key   = key;
    this.value = value;
  } // end constructor

  //*NOTE: there is no setKey() method because it can only be created, never edited.

  String getKey()        {return key;}
  int    getValue()      {return value;}
  void   setValue(int i) {value = i;}

} // end class Element
