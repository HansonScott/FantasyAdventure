package fantasy_adventure;

import java.io.*;

class Skill implements Serializable{

  /* the purpose of this class is to encapsolate everything related to a skill.
   * This is a non-visual object, intended to be used for number crunching.
   * in order to show the skills, use the class SkillButton, which adds the GUI.
   * NOTE: this class now refers to 'feats' although the name says skill.
   * if referring to skills as in specific levels of mastry,
   * use LivingObject myHashMaps.
   */

  // declare objects and variables of a skill
  private String   oldString;
  private String   newString;
  private String   title;          // title will be recognized, displayed and written to files.
  private String   description;    // lists both the effect or ability and the requirements.
  private int      numEffects;     // keeps track so we know how many to look for
  Effect[]         effects;        // list of effects
  private int      numRequirements;// keeps track so we know how many to look for
  Requirement[]    requirements;   // each string is a requirement, requiring parsing.
  private boolean  stackable;      // refers to skills that can be used x times per day.
  private int      category;       // one of the three categories

  final static int CB_SKILL = 0;
  final static int SS_SKILL = 1;
  final static int PE_SKILL = 2;

  final static int MAX_EFFECTS = 5;
  final static int MAX_REQUIREMENTS = 5;

  static Skill[] CBSkills = new Skill[MyTextIO.getNumLines(FileNames.CB_SKILLS)];
  static Skill[] SSSkills = new Skill[MyTextIO.getNumLines(FileNames.SS_SKILLS)];
  static Skill[] PESkills = new Skill[MyTextIO.getNumLines(FileNames.PE_SKILLS)];

// *************************************************************************
//constructors
// *************************************************************************

Skill(String s){
  /* Parameter is a complex, comma delimited String, in the following format:
   * 1)Title,
   * 2)description,
   * 3)stackable,
   * 5)numEffects,
   * 6)effect[]  // there may be multiple effects, numbered by previous word
   * 7)numRequirements
   * 8)requirement[] // there may be multiple requirements, numbered by previous word
   */

  oldString = s;

  // parse each piece of the string, and save the data for each independently.

  // 1) getNextphrase and save as title
  setTitle (MyTextIO.getNextPhrase(oldString));
    newString = MyTextIO.trimPhrase(oldString);
    oldString = newString;

  // 2) getNextphrase and save as description
  setDescription (MyTextIO.getNextPhrase(oldString));
  newString = MyTextIO.trimPhrase(oldString);
  oldString = newString;

  // 3) getNextphrase and convert to boolean and save as boolean stackable
  if (MyTextIO.getNextPhrase(oldString) == "true")  setStackable(true);
  else                             setStackable(false);
  newString = MyTextIO.trimPhrase(oldString);
  oldString = newString;

  // 5) get number of effects, so we know how many to prepare for:

  numEffects = Integer.parseInt(MyTextIO.getNextPhrase(oldString));
  newString = MyTextIO.trimPhrase(oldString);
  oldString = newString;

  // 6) repeat getNextphrase and save as effect until numEffects

  effects = new Effect[MAX_EFFECTS];

//  Popup.createInfoPopup("Creating effects for Skill: " + title);

  for (int i = 0; i < numEffects; i++) {
    // if inside the for loop, there is another effect, so capture and save

    // capture this effect

    if (! MyTextIO.getNextPhrase(oldString).equalsIgnoreCase("null"))
    effects[i] = new Effect(MyTextIO.getNextPhrase(oldString));  // once we have stored the phrase, increment i.
    newString = MyTextIO.trimPhrase(oldString);
    oldString = newString;

  } // end for loop

  // 7) get number of requirements, so we know how many to prepare for:
  numRequirements = Integer.parseInt(MyTextIO.getNextPhrase(oldString));
  newString = MyTextIO.trimPhrase(oldString);
  oldString = newString;

  // 8) repeat getNextphrase and save as requirement until numEffects

  requirements = new Requirement[MAX_REQUIREMENTS];

  for (int i = 0; i < numRequirements; i++) { // only look for as many as we are expecting!
    // if inside the for loop, there is another requirement, so capture and save

    // capture this requirement
    requirements[i] = new Requirement(MyTextIO.getNextPhrase(oldString));  // once we have stored the phrase, increment i.
    newString = MyTextIO.trimPhrase(oldString);
    oldString = newString;

  } // end for loop

  category = Integer.parseInt(MyTextIO.getNextPhrase(oldString));

/*  Popup.createInfoPopup("<html>Skill Created!<br>" +
                        "Name: " + getTitle() + "<br>" +
                        "Cat:" + getCategory() + "</html>");
*/
} // end constructor

// ***********************************************************************
// package methods
// ***********************************************************************

String   getTitle ()         {return title;}
String   getDescription ()   {return description;}
int      getNumRequirements(){return requirements.length;}
boolean  isStackable ()      {return stackable;}
int      getCategory   ()    {return category;}

void  setTitle (String s)      {title = s;}
void  setDescription(String s) {description = s;}
void  setStackable (boolean b) {stackable = b;}
void  setCategory   (int i)    {category = i;}

String getRequirementDesc(int i) {return requirements[i].getDescription();} // end getRequirement

boolean canBeTrained(LivingObject character){
  /* The purpose of this method is to check whether a character meets the requirements to
   * 'train' or 'purchase' this skill and add it to his/her skill list.
   * NOTE: this is not quite the same as canBeUsed(), which also takes into account
   * temporary stats of the character.
   */

  // check each requirement of the calling skill.
  for (int i = 0; i < MAX_REQUIREMENTS &&
                  requirements[i] != null; i++){

  // now, we can analyze the four words and make sense of them.
  if (requirements[i].test(character) == false)
    return false; // if ANY one of the requirements is false, then return false
  } // end for loop
  return true; // we want to have false as default, but left over to be true.
} // end canBeTrained

//***************************************************************************************
  public void writeSkillToFile(BufferedWriter bw) throws IOException {
    // purpose of this method is to write this skill to a file.
    // since we will be match the skills from the static list,
    // we only need to write the title, and we can create it from the title
    // again later when we need to load it.

    bw.write(MyTextIO.tab + title);
  } // end writeToFile

//***************************************************************************************
  public void readSkillFromFile(BufferedReader br) throws IOException {
    String thisLine = br.readLine();
    title = MyTextIO.getNextPhrase(thisLine);
    thisLine = MyTextIO.trimPhrase(thisLine);

    description = MyTextIO.getNextPhrase(thisLine);
    thisLine = MyTextIO.trimPhrase(thisLine);

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    numEffects = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes variable

    for (int i = 0; i < numEffects; i++){
      effects[i] = new Effect(MyTextIO.getNextPhrase(thisLine));
      thisLine = MyTextIO.trimPhrase(thisLine);
    } // end for loop

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    numRequirements = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes variable

    for (int i = 0; i < numRequirements; i++){
      requirements[i] = new Requirement(MyTextIO.getNextPhrase(thisLine));
      thisLine = MyTextIO.trimPhrase(thisLine);
    } // end for loop

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    stackable = Boolean.getBoolean(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes variable

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    category = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes variable
  } // end read

//***************************************************************************************
  public static String getSkillNameFromRequirement(Requirement req) {
    // purpose of this method is to trim the front end of a string,
    // returning a title for the skill passed.
    // Pre: the string passed takes the form: "Skill: <skillName>"
    if (req == null) return null;
    if (req.getVariable().equalsIgnoreCase("Skill:")) return MyTextIO.trimWord(req.getDescription());
    return null;
  } // end getSkillNameFromRequirement

//***************************************************************************************
  public static Skill createSkillFromTitle(String title){
    // purpose is to recreate a character's skill from the stored title.
    // look for the title in the three skill arrays.
    Skill tempSkill = Skill.findSkillFromTitle(Skill.CBSkills, title);
    if (tempSkill != null) return tempSkill;

    tempSkill = Skill.findSkillFromTitle(Skill.SSSkills, title);
    if (tempSkill != null) return tempSkill;

    tempSkill = Skill.findSkillFromTitle(Skill.PESkills, title);
    if (tempSkill != null) return tempSkill;

    // if we have reached here, then the skill is not found!
    Popup.createWarningPopup("Skill with title: '" + title + "' not found!");
    // else, return null.
    return null;
  } // end createSkillFromTitle

//***************************************************************************************
//  private methods
//***********************************************************************
  private static Skill findSkillFromTitle(Skill[] skillArray, String title){
      // look for the title in the three skill arrays.
      for (int i = 0; i < (skillArray.length - 1); i++){
//        Popup.createInfoPopup("Comparing title: '" + title + "'" + MyTextIO.newline +
  //                            "With: '" + skillArray[i].getTitle() + "'");
        if (skillArray[i].getTitle().equalsIgnoreCase(title)){
          return skillArray[i];
        } // end if
      } // end for loop
      return null;
  } // end findSkillFromTitle

//***************************************************************************************
//  static methods
//***********************************************************************
  static void fillSkillArray(Skill[] skills, String fileName){
    // this method takes a StringFilename and creates a skill array from it
    // by creating skills on the fly.

    int length = MyTextIO.getNumLines(fileName);
    String[] lines = new String[length];
    lines = MyTextIO.readFile(fileName, length);

    for (int i = 0; i <= length; i++){
      // for each line, create the skill from it
      if (lines[i] == null) break;
      skills[i] = new Skill(lines[i]);
    } // end for loop

  } // end createSkillArray

} // end class Skill











