package fantasy_adventure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

//************************************************************************
//imports


//************************************************************************

public class Requirement implements Serializable{

  /* The purpose of this class is to control all the requirements
   * within the game, be it magical, item, skill, etc.
   *     1        2       3
   * VARIABLE OPERATOR VALUE
   *
   * For example, a typical requirement of str >= 13 would look like:
   * "BaseStrength >= 13"  // char base strength is 13+
   *
   * "Equip != Heavy" // char is NOT wearing heavy armor
   *
   * "Alignment != LAWFUL_GOOD"  // char alignment is NOT lawful good.
   *
   * "Feat == Bash"  // the feat "Bash" is required.
   *
   * "StealthLevel >= 5" // the character has a stealth level of 5 or greater.
   */

//************************************************************************
// static declarations
//************************************************************************
/*
  static void readItemRequirementsFromFile(){
    // the purpose of this method is to get the file holding all the requirements
    // into the various specific item static variables.
    try{
      BufferedReader br = new BufferedReader(new FileReader(FileNames.ITEM_REQUIREMENTS));
      // now that we know it exists
      // remove the title line
      String thisLine = br.readLine(); // gets title, which we don't need.

      // and read in new materials into static array.
      for (int i = 0; i < MyTextIO.getNumLines(FileNames.ITEM_REQUIREMENTS) - 1; i++){ // -1 for title
        // for each line in the file...
        // each line of the file has a list of requirements for a given item.
        thisLine = br.readLine();
        if (thisLine == null) break;
//        Popup.createInfoPopup("Creating requirement for line:" + MyTextIO.newline + thisLine);

        // create an array of requirements based on the first 'phrase' of the line which MUST be a digit
        Requirement[] requirements = new Requirement[Integer.parseInt(MyTextIO.getNextPhrase(thisLine))];
        thisLine = MyTextIO.trimPhrase(thisLine);

        // fill array with new requirements given from the subsequent 'phrases' of the line
        for (int j = 0; j < requirements.length; j++){
          // for each requirement, create and fill req, and trim line.
          requirements[j] = new Requirement(MyTextIO.getNextPhrase(thisLine));
          thisLine = MyTextIO.trimPhrase(thisLine);
        } // end for loop

//        Popup.createInfoPopup("allocating requirement array to item name: '" + MyTextIO.getNextPhrase(thisLine) + "'");
        // store the array of requirements to the static item variables in the class of the given item name.
        if      (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.AMMO_ARROW)))         Ammo.REQ_ARROW         = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.AMMO_BOLT)))          Ammo.REQ_BOLT          = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.AMMO_BULLET)))        Ammo.REQ_BULLET        = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.AMULET)))             Amulet.REQ_AMULET      = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.ARMS_GLOVES)))        Arms.REQ_GLOVES        = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.ARMS_GAUNTLETS)))     Arms.REQ_GAUNTLETS     = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.ARMS_BRACERS)))       Arms.REQ_BRACERS       = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.AXE_SMALL)))          Axe.REQ_SMALL          = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.AXE_MEDIUM)))         Axe.REQ_MEDIUM         = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.AXE_LARGE)))          Axe.REQ_LARGE          = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.AXE_MASSIVE)))        Axe.REQ_MASSIVE        = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.BELT_LIGHT)))         Belt.REQ_LIGHT         = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.BELT_HEAVY)))         Belt.REQ_HEAVY         = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.BLUNT_CLUB)))         Blunt.REQ_CLUB         = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.BLUNT_MACE)))         Blunt.REQ_MACE         = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.BLUNT_HAMMER)))       Blunt.REQ_HAMMER       = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.BLUNT_MAUL)))         Blunt.REQ_MAUL         = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.BLUNT_STAFF)))        Blunt.REQ_STAFF        = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.BOOK)))               Book.REQ_BOOK          = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.BOOTS_SOFT)))         Boots.REQ_SOFT         = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.BOOTS_HARD)))         Boots.REQ_HARD         = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.BOOTS_GREAVES)))      Boots.REQ_GREAVES      = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.CLOAK)))              Cloak.REQ_CLOAK        = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.CUIRASS_ROBE)))       Cuirass.REQ_ROBE       = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.CUIRASS_LIGHT)))      Cuirass.REQ_LIGHT      = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.CUIRASS_MEDIUM)))     Cuirass.REQ_MEDIUM     = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.CUIRASS_HEAVY)))      Cuirass.REQ_HEAVY      = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.GEM)))                Gem.REQ_GEM            = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.GOLD)))          Gold.REQ_GOLD          = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.HAND2HAND_FIST)))     Hand2Hand.REQ_FIST     = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.HAND2HAND_KNUCKLES))) Hand2Hand.REQ_KNUCKLES = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.HAND2HAND_CLAWS)))    Hand2Hand.REQ_CLAWS    = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.HAND2HAND_DAGGER)))   Hand2Hand.REQ_DAGGER   = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.HELMET_NORMAL)))      Helmet.REQ_HELM        = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.HELMET_BEAST)))       Helmet.REQ_BEAST       = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.HELMET_CIRCLET)))     Helmet.REQ_CIRCLET     = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.HELMET_HOOD)))        Helmet.REQ_HOOD        = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.LAUNCHER_BOW)))       Launcher.REQ_BOW       = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.LAUNCHER_CROSSBOW)))  Launcher.REQ_CROSSBOW  = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.LAUNCHER_SLING)))     Launcher.REQ_SLING     = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.LONG_BLADE_STRAIGHT)))LongBlade.REQ_STRAIGHT = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.LONG_BLADE_CURVED)))  LongBlade.REQ_CURVED   = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.LONG_BLADE_2H)))      LongBlade.REQ_2H       = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.POLEARM_SPEAR)))      PoleArm.REQ_SPEAR      = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.POLEARM_HALBERD)))    PoleArm.REQ_HALBERD    = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.POTION)))             Potion.REQ_POTION      = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.RING)))               Ring.REQ_RING          = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.SCROLL)))             Scroll.REQ_SCROLL      = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.SHIELD_BUCKLER)))     Shield.REQ_BUCKLER     = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.SHIELD_ROUND)))       Shield.REQ_ROUND       = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.SHIELD_MEDIUM)))      Shield.REQ_MEDIUM      = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.SHIELD_TOWER)))       Shield.REQ_TOWER       = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.SHORT_BLADE_SHORT)))  ShortBlade.REQ_SHORT   = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.SHORT_BLADE_DAGGER))) ShortBlade.REQ_DAGGER  = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.SPIKED_FLAIL)))       Spiked.REQ_FLAIL       = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.SPIKED_STAR)))        Spiked.REQ_STAR        = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.THROWN_AXE)))         Thrown.REQ_AXE         = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.THROWN_DART)))        Thrown.REQ_DART        = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.THROWN_KNIFE)))       Thrown.REQ_KNIFE       = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.THROWN_JAVELIN)))     Thrown.REQ_JAVALIN     = requirements;
        else if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase(Item.getTypeName(Item.THROWN_STONE)))       Thrown.REQ_STONE       = requirements;

      } // end for loop

      br.close();
    } // end try

    catch(FileNotFoundException e){
      Popup.createErrorPopup("Item Requirements File not found"
                            + MyTextIO.newline
                            + "'" + FileNames.QUALITIES + "'");
    } // end catch
    catch(IOException evt){
      Popup.createErrorPopup("IO error when reading from Qualities File."
                            + MyTextIO.newline
                            + "'" + FileNames.QUALITIES + "'");
    } // end catch

  } // end readItemRequirementsFromFile
*/
//************************************************************************
// member declarations
//************************************************************************

// variable possibles include: BaseStrength, tempStrength, alignment, age, gender, fame, SkillName, etc.
  private String variable;

// operator words include "==", or "!=", or ">=" or "<=".
  private String operator;

// value usally be a number, or maybe true, or false. (1, 0)
  private String value;

  private String description;

//************************************************************************
// constructors
//************************************************************************

  public Requirement(){} // default constructor

  public Requirement (String thisPhrase) {
    // the purpose of this constructor is to parse out the effect phrase into a requirement
//    Popup.createInfoPopup("new Requirement being created from String:" + MyTextIO.newline + thisPhrase);

    // capture the first word (the 'variable' word)
    variable  = MyTextIO.getNextWord(thisPhrase);
      thisPhrase = MyTextIO.trimWord(thisPhrase);

    // capture the second word (the 'operator' word)
    operator = MyTextIO.getNextWord(thisPhrase);
      thisPhrase = MyTextIO.trimWord(thisPhrase);

    // capture the last word or words in the case of skills that are multi-worded.
    value = thisPhrase;

    // now combine the words for the description.
    if (operator == null)
      description = variable;

    else if (value == null)
      description = variable + " " + operator;

    else{
      description = variable + " " + operator + " " + value;
    } // end else

  } // end constructor

  public Requirement (Requirement source){
    // purpose of this constructor is to create a 'deep' copy.
    this();
    setVariable    (source.getVariable());
    setOperator    (source.getOperator());
    setValue       (source.getValue());
    setDescription (source.getDescription());
  } // end source

//************************************************************************
// package methods
//************************************************************************

  String getVariable()    {return variable;}
  String getOperator()    {return operator;}
  String getValue()       {return value;}
  String getDescription() {return description;}

  void setVariable    (String s) { variable    = s;}
  void setOperator    (String s) { operator    = s;}
  void setValue       (String s) { value       = s;}
  void setDescription (String s) { description = s;}

//************************************************************************
  boolean test(LivingObject character){
    // test the stats of this requirement against the given character

    //************************************************************************
    // test race:

    if (variable.equalsIgnoreCase("Race")){
       //Because race is a specific name, we only need to check == and !=,
//       Popup.createInfoPopup("Testing Race");

      if      (operator.matches("==")){return  character.getRace().getName().equalsIgnoreCase(value);} // end if operator == "=="
      else if (operator.matches("!=")){return !character.getRace().getName().equalsIgnoreCase(value);} // end if operator == "!="
      else {
        Popup.createErrorPopup("Unable to match operator: '" + operator + "' to check, sorry. (variable = '" + variable + "')");
      } // end else
    } // end test Race

    //************************************************************************
    // test gender:

    else if (variable.equalsIgnoreCase("Gender")){
       //Because Gender is a specific name, we only need to check == and !=,
//       Popup.createInfoPopup("Testing Gender");

      if      (operator.matches("==")){return  character.getGender().equalsIgnoreCase(value);} // end if operator == "=="
      else if (operator.matches("!=")){return !character.getGender().equalsIgnoreCase(value);} // end if operator == "!="
      else {
        Popup.createErrorPopup("Unable to match operator: '" + operator + "' to check, sorry. (variable = '" + variable + "')");
      } // end else
    } // end test Gender


    //************************************************************************
    // test age

    else if (variable.equalsIgnoreCase("Age")){
       //Because Age is an integer, we need to check integer values,
//       Popup.createInfoPopup("Testing Age");

      if      (operator.matches("==")){return  character.getAge() == Integer.parseInt(value);} // end if operator == "=="
      else if (operator.matches("!=")){return  character.getAge() != Integer.parseInt(value);} // end if operator == "!="
      else if (operator.matches(">=")){return  character.getAge() >= Integer.parseInt(value);} // end if operator == "!="
      else if (operator.matches("<=")){return  character.getAge() <= Integer.parseInt(value);} // end if operator == "!="
      else {
        Popup.createErrorPopup("Unable to match operator: '" + operator + "' to check, sorry. (variable = '" + variable + "')");
      } // end else
    } // end test Gender

    //************************************************************************
    // test alignment

    else if (variable.equalsIgnoreCase("Alignment")){
      /* Because alignment is a specific name, we only need to check == and !=,
       * since < and > have no meaning.
       * We do need to check for 'partial' alignments though, like LAWFUL, and GOOD
       */
//      Popup.createInfoPopup("Testing Alignment");

        if (operator.matches("==")){
          if((value.equalsIgnoreCase("LAWFUL_GOOD"))     ||
             (value.equalsIgnoreCase("LAWFUL_NEUTRAL"))  ||
             (value.equalsIgnoreCase("LAWFUL_EVIL"))     ||
             (value.equalsIgnoreCase("NEUTRAL_GOOD"))    ||
             (value.equalsIgnoreCase("TRUE_NEUTRAL"))    ||
             (value.equalsIgnoreCase("NEUTRAL_EVIL"))    ||
             (value.equalsIgnoreCase("CHAOTIC_GOOD"))    ||
             (value.equalsIgnoreCase("CHAOTIC_NEUTRAL")) ||
             (value.equalsIgnoreCase("CHAOTIC_EVIL")))
               {return character.getAlignment().getName().equalsIgnoreCase(value);}

          else if (value.equalsIgnoreCase("LAWFUL")){
            return character.getAlignment().getValue() == Alignment.LAWFUL_GOOD    ||
                   character.getAlignment().getValue() == Alignment.LAWFUL_NEUTRAL ||
                   character.getAlignment().getValue() == Alignment.LAWFUL_EVIL;
            } // end if LAWFUL

          else if (value.equalsIgnoreCase("NEUTRAL")){
            return character.getAlignment().getValue() == Alignment.NEUTRAL_GOOD   ||
                   character.getAlignment().getValue() == Alignment.NEUTRAL_EVIL   ||
                   character.getAlignment().getValue() == Alignment.LAWFUL_NEUTRAL ||
                   character.getAlignment().getValue() == Alignment.TRUE_NEUTRAL   ||
                   character.getAlignment().getValue() == Alignment.CHAOTIC_NEUTRAL;
            } // end if NEUTRAL

          else if (value.equalsIgnoreCase("CHAOTIC")){
            return character.getAlignment().getValue() == Alignment.CHAOTIC_GOOD    ||
                   character.getAlignment().getValue() == Alignment.CHAOTIC_NEUTRAL ||
                   character.getAlignment().getValue() == Alignment.CHAOTIC_EVIL;
          } // end if CHAOTIC

          else if (value.equalsIgnoreCase("GOOD")){
            return character.getAlignment().getValue() == Alignment.LAWFUL_GOOD  ||
                   character.getAlignment().getValue() == Alignment.NEUTRAL_GOOD ||
                   character.getAlignment().getValue() == Alignment.CHAOTIC_GOOD;
          } // end if GOOD

          else{// (value == "EVIL")
            return character.getAlignment().getValue() == Alignment.LAWFUL_EVIL  ||
                   character.getAlignment().getValue() == Alignment.NEUTRAL_EVIL ||
                   character.getAlignment().getValue() == Alignment.CHAOTIC_EVIL;
          } // end if EVIL
        } // end if operator == "=="

        else{//(operator == "!=")
          if((value.equalsIgnoreCase("LAWFUL_GOOD"))     ||
             (value.equalsIgnoreCase("LAWFUL_NEUTRAL"))  ||
             (value.equalsIgnoreCase("LAWFUL_EVIL"))     ||
             (value.equalsIgnoreCase("NEUTRAL_GOOD"))    ||
             (value.equalsIgnoreCase("TRUE_NEUTRAL"))    ||
             (value.equalsIgnoreCase("NEUTRAL_EVIL"))    ||
             (value.equalsIgnoreCase("CHAOTIC_GOOD"))    ||
             (value.equalsIgnoreCase("CHAOTIC_NEUTRAL")) ||
             (value.equalsIgnoreCase("CHAOTIC_EVIL")))
               {return !character.getAlignment().getName().equalsIgnoreCase(value);}

          else if (value.equalsIgnoreCase("LAWFUL")){
            return character.getAlignment().getValue() != Alignment.LAWFUL_GOOD    &&
                   character.getAlignment().getValue() != Alignment.LAWFUL_NEUTRAL &&
                   character.getAlignment().getValue() != Alignment.LAWFUL_EVIL;
            } // end if LAWFUL

          else if (value.equalsIgnoreCase("NEUTRAL")){
            return character.getAlignment().getValue() != Alignment.NEUTRAL_GOOD   &&
                   character.getAlignment().getValue() != Alignment.NEUTRAL_EVIL   &&
                   character.getAlignment().getValue() != Alignment.LAWFUL_NEUTRAL &&
                   character.getAlignment().getValue() != Alignment.TRUE_NEUTRAL   &&
                   character.getAlignment().getValue() != Alignment.CHAOTIC_NEUTRAL;
            } // end if NEUTRAL

          else if (value.equalsIgnoreCase("CHAOTIC")){
            return character.getAlignment().getValue() != Alignment.CHAOTIC_GOOD    &&
                   character.getAlignment().getValue() != Alignment.CHAOTIC_NEUTRAL &&
                   character.getAlignment().getValue() != Alignment.CHAOTIC_EVIL;
          } // end if CHAOTIC

          else if (value.equalsIgnoreCase("GOOD")){
            return character.getAlignment().getValue() != Alignment.LAWFUL_GOOD  &&
                   character.getAlignment().getValue() != Alignment.NEUTRAL_GOOD &&
                   character.getAlignment().getValue() != Alignment.CHAOTIC_GOOD;
          } // end if GOOD

          else if (value == "EVIL"){
            return character.getAlignment().getValue() != Alignment.LAWFUL_EVIL  &&
                   character.getAlignment().getValue() != Alignment.NEUTRAL_EVIL &&
                   character.getAlignment().getValue() != Alignment.CHAOTIC_EVIL;
          } // end if EVIL

          else{ // unknown
             Popup.createErrorPopup("Unable to match value: " + value + ", to check within variable = '" + variable + "'");
          } // end else

        } // end if operator == "!="
    Popup.createErrorPopup("Unable to match category to check, sorry. (variable = '" + variable + "')");
    } // end test Alignment

    //************************************************************************
    // next test for attributes

      else if (variable.equalsIgnoreCase("BaseStrength") ||
               variable.equalsIgnoreCase("Strength")){
        if      (operator.matches("==")) {return character.getBaseStr() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")) {return character.getBaseStr() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")) {return character.getBaseStr() >= Integer.parseInt(value);} // end if operator == ">="
        else                             {return character.getBaseStr() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Bstr

      else if (variable.equalsIgnoreCase("TempStrength")){
        if      (operator.matches("==")) {return character.getTempStr() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")) {return character.getTempStr() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")) {return character.getTempStr() >= Integer.parseInt(value);} // end if operator == ">="
        else                             {return character.getTempStr() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Tstr

      else if (variable.equalsIgnoreCase("BaseDexterity") ||
               variable.equalsIgnoreCase("Dexterity")){
        if      (operator.matches("==")) {return character.getBaseDex() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")) {return character.getBaseDex() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")) {return character.getBaseDex() >= Integer.parseInt(value);} // end if operator == ">="
        else                             {return character.getBaseDex() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Bdex

      else if (variable.equalsIgnoreCase("TempDexterity")){
        if      (operator.matches("==")) {return character.getTempDex() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")) {return character.getTempDex() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")) {return character.getTempDex() >= Integer.parseInt(value);} // end if operator == ">="
        else                             {return character.getTempDex() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Tdex

      else if (variable.equalsIgnoreCase("BaseConstitution") ||
               variable.equalsIgnoreCase("Constitution")){
        if      (operator.matches("==")){return character.getBaseCon() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")){return character.getBaseCon() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")){return character.getBaseCon() >= Integer.parseInt(value);} // end if operator == ">="
        else                            {return character.getBaseCon() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Bcon

      else if (variable.equalsIgnoreCase("TempConstitution")){
        if      (operator.matches("==")){return character.getTempCon() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")){return character.getTempCon() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")){return character.getTempCon() >= Integer.parseInt(value);} // end if operator == ">="
        else                            {return character.getTempCon() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Tcon

      else if (variable.equalsIgnoreCase("BaseIntelligence") ||
               variable.equalsIgnoreCase("Intelligence")){
        if      (operator.matches("==")){return character.getBaseInt() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")){return character.getBaseInt() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")){return character.getBaseInt() >= Integer.parseInt(value);} // end if operator == ">="
        else                            {return character.getBaseInt() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Bint

      else if (variable.equalsIgnoreCase("TempIntelligence")){
        if      (operator.matches("==")){return character.getTempInt() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")){return character.getTempInt() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")){return character.getTempInt() >= Integer.parseInt(value);} // end if operator == ">="
        else                            {return character.getTempInt() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Tint

      else if (variable.equalsIgnoreCase("BaseWisdom") ||
               variable.equalsIgnoreCase("Wisdom")){
        if      (operator.matches("==")){return character.getBaseWis() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")){return character.getBaseWis() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")){return character.getBaseWis() >= Integer.parseInt(value);} // end if operator == ">="
        else                            {return character.getBaseWis() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Bwis

      else if (variable.equalsIgnoreCase("TempWisdom")){
        if      (operator.matches("==")){return character.getTempWis() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")){return character.getTempWis() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")){return character.getTempWis() >= Integer.parseInt(value);} // end if operator == ">="
        else                            {return character.getTempWis() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Twis

      else if (variable.equalsIgnoreCase("BaseCharisma") ||
               variable.equalsIgnoreCase("Charisma")){
        if      (operator.matches("==")){return character.getBaseCha() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")){return character.getBaseCha() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")){return character.getBaseCha() >= Integer.parseInt(value);} // end if operator == ">="
        else                            {return character.getBaseCha() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Bcha

      else if (variable.equalsIgnoreCase("TempCharisma")){
        if      (operator.matches("==")){return character.getTempCha() == Integer.parseInt(value);} // end if operator == "=="
        else if (operator.matches("!=")){return character.getTempCha() != Integer.parseInt(value);} // end if operator == "!="
        else if (operator.matches(">=")){return character.getTempCha() >= Integer.parseInt(value);} // end if operator == ">="
        else                            {return character.getBaseCha() <= Integer.parseInt(value);} // end if operator == "<="
      } // end if variable == Tcha


  //************************************************************************
  // test skill level

    else if (variable.equalsIgnoreCase( "Axe")         ||
             variable.equalsIgnoreCase( "Blunt")       ||
             variable.equalsIgnoreCase( "Spiked")      ||
             variable.equalsIgnoreCase( "Hand2Hand")   ||
             variable.equalsIgnoreCase( "Long")        ||
             variable.equalsIgnoreCase( "Launcher")    ||
             variable.equalsIgnoreCase( "Thrown")      ||
             variable.equalsIgnoreCase( "Polearm")     ||
             variable.equalsIgnoreCase( "Short")){

      if      (operator.matches("==")) {return character.currentLevel.get(variable) == Integer.parseInt(value);} // end if ==
      else if (operator.matches("!=")) {return character.currentLevel.get(variable) != Integer.parseInt(value);} // end if ==
      else if (operator.matches(">=")) {return character.currentLevel.get(variable) >= Integer.parseInt(value);} // end if ==
      else                             {return character.currentLevel.get(variable) <= Integer.parseInt(value);} // end if ==
    } // end if weapon skill

    //************************************************************************
    else if (variable.equalsIgnoreCase( "Unarmored") ||
             variable.equalsIgnoreCase( "Light")     ||
             variable.equalsIgnoreCase( "Medium")    ||
             variable.equalsIgnoreCase( "Heavy")     ||
             variable.equalsIgnoreCase( "Shield")){

      if      (operator.matches("==")) {return character.currentLevel.get(variable) == Integer.parseInt(value);} // end if ==
      else if (operator.matches("!=")) {return character.currentLevel.get(variable) != Integer.parseInt(value);} // end if ==
      else if (operator.matches(">=")) {return character.currentLevel.get(variable) >= Integer.parseInt(value);} // end if ==
      else                             {return character.currentLevel.get(variable) <= Integer.parseInt(value);} // end if ==
    } // end if armor skill

    //************************************************************************
    else if (variable.equalsIgnoreCase( "Stealth")    ||
             variable.equalsIgnoreCase( "Backstab")   ||
             variable.equalsIgnoreCase( "Snipe")      ||
             variable.equalsIgnoreCase( "Pickpocket") ||
             variable.equalsIgnoreCase( "Locks")      ||
             variable.equalsIgnoreCase( "Traps")){

      if      (operator.matches("==")) {return character.currentLevel.get(variable) == Integer.parseInt(value);} // end if ==
      else if (operator.matches("!=")) {return character.currentLevel.get(variable) != Integer.parseInt(value);} // end if ==
      else if (operator.matches(">=")) {return character.currentLevel.get(variable) >= Integer.parseInt(value);} // end if ==
      else                             {return character.currentLevel.get(variable) <= Integer.parseInt(value);} // end if ==
    } // end if stealth or Social Skill

    //************************************************************************
    else if (variable.equalsIgnoreCase( "Fire")   ||
             variable.equalsIgnoreCase( "Energy") ||
             variable.equalsIgnoreCase( "Air")    ||
             variable.equalsIgnoreCase( "Life")   ||
             variable.equalsIgnoreCase( "Water")  ||
             variable.equalsIgnoreCase( "Nature") ||
             variable.equalsIgnoreCase( "Earth")  ||
             variable.equalsIgnoreCase( "Death")){

      if      (operator.matches("==")) {return character.currentLevel.get(variable) == Integer.parseInt(value);} // end if ==
      else if (operator.matches("!=")) {return character.currentLevel.get(variable) != Integer.parseInt(value);} // end if ==
      else if (operator.matches(">=")) {return character.currentLevel.get(variable) >= Integer.parseInt(value);} // end if ==
      else                             {return character.currentLevel.get(variable) <= Integer.parseInt(value);} // end if ==
    } // end if sphere skill

    //************************************************************************
    // test skills *NOTE: this is only a boolean check. either character has it or doesn't.
    // there are no prefixes to this, so we need to remove the word skill, but not in the title.
    else if (variable.equalsIgnoreCase("Skill:")){
      // we need to go through the character's list of skills and match the feat name.
      for (int i = 0; character.getSkill(i) != null && i < character.getSkills().length; i++){
        // now, we need to trim the first word, which is either "Skill:" or "Feat:"
        if (MyTextIO.trimWord(description).equalsIgnoreCase(character.getSkill(i).getTitle()))
            {return true;}
      } // end for loop

      // if we didn't find it, then return false.
      return false;
    } // end if feat

   //************************************************************************
   // test feats NOTE: this is only a boolean check. either character has it or doesn't.
   // since a feat has the word 'feat:' in front, it has to be removed for unique checking.

    else if (variable.equalsIgnoreCase("Feat:")){
      // we need to go through the character's list of skills and match the feat name.

      for (int i = 0; character.getSkill(i) != null && i < character.getSkills().length; i++){
        // now, we need to trim the first word, which is either "Skill:" or "Feat:"
        if (MyTextIO.trimWord(description).equalsIgnoreCase(MyTextIO.trimWord(character.getSkill(i).getTitle())))
            {return true;}
      } // end for loop

      // if we didn't find it, then return false.
      return false;
    } // end if feat

    //************************************************************************
    // test equipment

    else if (variable.equalsIgnoreCase("Equip")){
      // to check if the character is equipping a certain item,
      // we have to look at the 'class' of equipment.
      // This is highly dependent on what classes of equipment are established
      // NOTE: variable = null, so only look at 'operator' and 'value'

      if (operator.matches("==")){
        if      (value.equalsIgnoreCase("Light"))    {return character.getInventory().getCuirassSlot().getType() == Item.CUIRASS_LIGHT;}  // if light
        else if (value.equalsIgnoreCase("Medium"))   {return character.getInventory().getCuirassSlot().getType() == Item.CUIRASS_MEDIUM;} // if Medium
        else if (value.equalsIgnoreCase("Heavy"))    {return character.getInventory().getCuirassSlot().getType() == Item.CUIRASS_HEAVY;}  // if Heavy
        else if (value.equalsIgnoreCase("Cloth"))    {return character.getInventory().getCuirassSlot().getType() == Item.CUIRASS_ROBE;}   // if Cloth

        else if (value.equalsIgnoreCase("Buckler"))  {return character.getInventory().getOffHandSlot().getType() == Item.SHIELD_BUCKLER;} // if Shield
        else if (value.equalsIgnoreCase("Round"))    {return character.getInventory().getOffHandSlot().getType() == Item.SHIELD_ROUND;}   // if Shield
        else if (value.equalsIgnoreCase("Shield"))   {return character.getInventory().getOffHandSlot().getType() == Item.SHIELD_MEDIUM;}  // if Shield
        else if (value.equalsIgnoreCase("Tower"))    {return character.getInventory().getOffHandSlot().getType() == Item.SHIELD_TOWER;}   // if Shield

        else if (value.equalsIgnoreCase("Axe"))      {return character.getInventory().getActiveWeaponSlot().getType() == Item.AXE_SMALL;}          // if Axe
        else if (value.equalsIgnoreCase("Blunt"))    {return character.getInventory().getActiveWeaponSlot().getType() == Item.BLUNT_CLUB;}         // if Blunt
        else if (value.equalsIgnoreCase("Hand"))     {return character.getInventory().getActiveWeaponSlot().getType() == Item.HAND2HAND_CLAWS;}    // if Hand
        else if (value.equalsIgnoreCase("Long"))     {return character.getInventory().getActiveWeaponSlot().getType() == Item.LONG_BLADE_STRAIGHT;}// if Long
        else if (value.equalsIgnoreCase("BowShort")) {return character.getInventory().getActiveWeaponSlot().getType() == Item.LAUNCHER_BOW_SHORT;} // if short bow
        else if (value.equalsIgnoreCase("BowLong"))  {return character.getInventory().getActiveWeaponSlot().getType() == Item.LAUNCHER_BOW_LONG;} // if long bow
        else if (value.equalsIgnoreCase("BowComposite")) {return character.getInventory().getActiveWeaponSlot().getType() == Item.LAUNCHER_BOW_COMPOSITE;} // if composite bow
        else if (value.equalsIgnoreCase("CrossbowLight"))  {return character.getInventory().getActiveWeaponSlot().getType() == Item.LAUNCHER_CROSSBOW_LIGHT;} // if light crossbow
        else if (value.equalsIgnoreCase("CrossbowHeavy"))  {return character.getInventory().getActiveWeaponSlot().getType() == Item.LAUNCHER_CROSSBOW_HEAVY;} // if heavy crossbow
        else if (value.equalsIgnoreCase("Polearm"))  {return character.getInventory().getActiveWeaponSlot().getType() == Item.POLEARM_HALBERD;}    // if Polearm
        else if (value.equalsIgnoreCase("Short"))    {return character.getInventory().getActiveWeaponSlot().getType() == Item.SHORT_BLADE_DAGGER;} // if Short
        else if (value.equalsIgnoreCase("Spiked"))   {return character.getInventory().getActiveWeaponSlot().getType() == Item.SPIKED_FLAIL;}       // if Spiked
        else if (value.equalsIgnoreCase("Thrown"))   {return character.getInventory().getActiveWeaponSlot().getType() == Item.THROWN_AXE;}         // if Thrown

        else {} // end else
      } // end if op == "=="

      else if (operator.matches("!=")){
        if (character.getInventory().getCuirassSlot() == null) return true;

        if      (value.equalsIgnoreCase("Light"))    {return character.getInventory().getCuirassSlot().getType() != Item.CUIRASS_LIGHT;}  // if Light
        else if (value.equalsIgnoreCase("Medium"))   {return character.getInventory().getCuirassSlot().getType() != Item.CUIRASS_MEDIUM;} // if Medium
        else if (value.equalsIgnoreCase("Heavy"))    {return character.getInventory().getCuirassSlot().getType() != Item.CUIRASS_HEAVY;}  // if Heavy
        else if (value.equalsIgnoreCase("Cloth"))    {return character.getInventory().getCuirassSlot().getType() != Item.CUIRASS_ROBE;}   // if Cloth

        else if (value.equalsIgnoreCase("Buckler"))  {return character.getInventory().getOffHandSlot().getType() == Item.SHIELD_BUCKLER;} // if Shield
        else if (value.equalsIgnoreCase("Round"))    {return character.getInventory().getOffHandSlot().getType() == Item.SHIELD_ROUND;}   // if Shield
        else if (value.equalsIgnoreCase("Shield"))   {return character.getInventory().getOffHandSlot().getType() == Item.SHIELD_MEDIUM;}  // if Shield
        else if (value.equalsIgnoreCase("Tower"))    {return character.getInventory().getOffHandSlot().getType() == Item.SHIELD_TOWER;}   // if Shield


        else if (value.equalsIgnoreCase("Axe"))      {return character.getInventory().getActiveWeaponSlot().getType() == Item.AXE_SMALL;}          // if Axe
        else if (value.equalsIgnoreCase("Blunt"))    {return character.getInventory().getActiveWeaponSlot().getType() == Item.BLUNT_CLUB;}         // if Blunt
        else if (value.equalsIgnoreCase("Hand"))     {return character.getInventory().getActiveWeaponSlot().getType() == Item.HAND2HAND_CLAWS;}    // if Hand
        else if (value.equalsIgnoreCase("Long"))     {return character.getInventory().getActiveWeaponSlot().getType() == Item.LONG_BLADE_STRAIGHT;}// if Long
        else if (value.equalsIgnoreCase("BowShort")) {return character.getInventory().getActiveWeaponSlot().getType() == Item.LAUNCHER_BOW_SHORT;} // if short bow
        else if (value.equalsIgnoreCase("BowLong"))  {return character.getInventory().getActiveWeaponSlot().getType() == Item.LAUNCHER_BOW_LONG;} // if long bow
        else if (value.equalsIgnoreCase("BowComposite")) {return character.getInventory().getActiveWeaponSlot().getType() == Item.LAUNCHER_BOW_COMPOSITE;} // if composite bow
        else if (value.equalsIgnoreCase("CrossbowLight"))  {return character.getInventory().getActiveWeaponSlot().getType() == Item.LAUNCHER_CROSSBOW_LIGHT;} // if light crossbow
        else if (value.equalsIgnoreCase("CrossbowHeavy"))  {return character.getInventory().getActiveWeaponSlot().getType() == Item.LAUNCHER_CROSSBOW_HEAVY;} // if heavy crossbow
        else if (value.equalsIgnoreCase("Polearm"))  {return character.getInventory().getActiveWeaponSlot().getType() == Item.POLEARM_HALBERD;}    // if Polearm
        else if (value.equalsIgnoreCase("Short"))    {return character.getInventory().getActiveWeaponSlot().getType() == Item.SHORT_BLADE_DAGGER;} // if Short
        else if (value.equalsIgnoreCase("Spiked"))   {return character.getInventory().getActiveWeaponSlot().getType() == Item.SPIKED_FLAIL;}       // if Spiked
        else if (value.equalsIgnoreCase("Thrown"))   {return character.getInventory().getActiveWeaponSlot().getType() == Item.THROWN_AXE;}         // if Thrown

        else {

        } // end else
      } // end if op == "!="
      else {
        Popup.createErrorPopup("Unable to match operator: '" + operator + "' to check, sorry. (variable = '" + variable + "')");
      } // end else
    } // end if category == "Equip"

    //************************************************************************

    else{
    } // end else
    Popup.createInfoPopup("Requirement test aborted, variable/operator not found." + MyTextIO.newline +
                          "'" + variable + "'" + "'" + operator + "'" + value + "'");
    return true;
  } // end test Living

//************************************************************************
  boolean test(SocialObject character){
    // test the stats of this requirement against the given character

    //************************************************************************
    // test GroupMembership

    //************************************************************************
    // test Fame

    //************************************************************************
    // test disposition

    return true;
  } // end test Social

//************************************************************************
  boolean test(PlayerCharacter character){
    // test the stats of this requirement against the given character

    //************************************************************************
    // test quests

    //************************************************************************
    // test partySize

    return true;
  } // end test PC

//************************************************************************
// private methods
//************************************************************************

//************************************************************************
} // end class Requirement
//************************************************************************
