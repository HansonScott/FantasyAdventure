package fantasy_adventure;

import java.io.*;
import java.awt.*;

// imports

class LivingObject extends GameObject{

// declare objects and variables
  static final int MIN_BASE_STAT = Constants.MIN_ATTRIBUTE;

  private String fullName, shortName, gender;
  private Race race;
  private int age;
  private Color skinColor, hairColor, majorColor, minorColor;

  private String largePortraitFile, smallPortraitFile, picPathAndFile;

  private boolean immuneToFire,  immuneToEnergy, immuneToAir,   immuneToLife,
                  immuneToWater, immuneToNature, immuneToEarth, immuneToDeath,
                  immuneToCrushing, immuneToPiercing, immuneToSlashing,
                  immuneToMelee, immuneToRanged;

  private int baseStr, baseDex, baseCon, baseInt, baseWis, baseCha,
              tempStr, tempDex, tempCon, tempInt, tempWis, tempCha,
              baseStrBonus, baseDexBonus, baseConBonus, baseIntBonus, baseWisBonus, baseChaBonus,
              tempStrBonus, tempDexBonus, tempConBonus, tempIntBonus, tempWisBonus, tempChaBonus,

              resistFire,  resistEnergy, resistAir,   resistLife,
              resistWater, resistNature, resistEarth, resistDeath,
              resistCrushing, resistPiercing, resistSlashing,
              resistMelee, resistRanged,

              saveFort, saveReflex, saveSocial,

              saveFire,  saveEnergy, saveAir,   saveLife,
              saveWater, saveNature, saveEarth, saveDeath,

              baseHP, tempMaxHP, currentHP,
              baseMP, tempMaxMP, currentMP,

              castingFailure;

              // skills
  public MyHashMap exp;          // holds all the exp points for the various skill categories
  public MyHashMap baseLevel;    // holds all the lvl variables for the various skill categories
  public MyHashMap currentLevel; // holds all the lvl variables for the various skill categories

  private int meleeAttack,    thrownAttack,     launcherAttack,
              naturalDefense, equipmentDefense, totalDefense;

  private Alignment alignment; // constant in class Alignment
  private int relationToPC;    // constant in class RelationToPC

  HealthState  healthState;    // see class desc. for details

  private Skill[] skills;

  Inventory inventory;

// int         actionState – keeps track of current action (attacking, casting, walking, standing, etc.)
// AI          personalAI  – depending on int. & race, this guides the object to interact with the player in a certain way.
// Graphics    g?          – animation for moving, stopping, fighting, waiting, etc. (linked with actionState and healthState)
// SoundScheme sounds      – object of type ‘SoundScheme’ that tells which soundfile to play for feedback during game.

//****************************************************************
// constructors
//****************************************************************

  LivingObject(){

  super();  // a living object is a game object

    // settings for parent
  setLineOfSight (true); // allows looking around a person...
  setSelectable  (true); // allows for mouseListeners and interaction (implement selectable?)
  setHasActivator(true); // script?

    // initialize objects and variables
  fullName     = "unknown name";
  shortName    = "unknown";
  race         = new Race();
  gender       = "Male";
  age          = 25;
  alignment    = new Alignment();
  relationToPC = RelationToPC.UNKNOWN;

  castingFailure = 0;

  baseStr = MIN_BASE_STAT;
  baseDex = MIN_BASE_STAT;
  baseCon = MIN_BASE_STAT;
  baseInt = MIN_BASE_STAT;
  baseWis = MIN_BASE_STAT;
  baseCha = MIN_BASE_STAT;

  tempStr = baseStr;
  tempDex = baseDex;
  tempCon = baseCon;
  tempInt = baseInt;
  tempWis = baseWis;
  tempCha = baseCha;

  baseStrBonus = Formulas.calcBonusFromStat(tempStr);
  baseDexBonus = Formulas.calcBonusFromStat(tempDex);
  baseConBonus = Formulas.calcBonusFromStat(tempCon);
  baseIntBonus = Formulas.calcBonusFromStat(tempInt);
  baseWisBonus = Formulas.calcBonusFromStat(tempWis);
  baseChaBonus = Formulas.calcBonusFromStat(tempCha);

  tempStrBonus = baseStrBonus;
  tempDexBonus = baseStrBonus;
  tempConBonus = baseStrBonus;
  tempIntBonus = baseStrBonus;
  tempWisBonus = baseStrBonus;
  tempChaBonus = baseStrBonus;

  baseHP    = Constants.MinHPPerLevel;
  tempMaxHP = baseHP;
  currentHP = baseHP;
  baseMP    = 1;
  tempMaxMP = baseMP;
  currentMP = baseMP;

  immuneToFire     = false;
  immuneToEnergy   = false;
  immuneToAir      = false;
  immuneToLife     = false;
  immuneToWater    = false;
  immuneToNature   = false;
  immuneToEarth    = false;
  immuneToDeath    = false;
  immuneToCrushing = false;
  immuneToPiercing = false;
  immuneToSlashing = false;
  immuneToMelee    = false;
  immuneToRanged   = false;

  resistFire     = 0;
  resistEnergy   = 0;
  resistAir      = 0;
  resistLife     = 0;
  resistWater    = 0;
  resistNature   = 0;
  resistEarth    = 0;
  resistDeath    = 0;
  resistCrushing = 0;
  resistPiercing = 0;
  resistSlashing = 0;
  resistMelee    = 0;
  resistRanged   = 0;

  saveFort   = 0;
  saveReflex = 0;
  saveSocial = 0;
  saveFire   = 0;
  saveEnergy = 0;
  saveAir    = 0;
  saveLife   = 0;
  saveWater  = 0;
  saveNature = 0;
  saveEarth  = 0;
  saveDeath  = 0;

  // set possible number of skills to the total amount of skills presented
  // in the three skill files.
  skills = new Skill[MyTextIO.getNumLines(FileNames.CB_SKILLS) +
                     MyTextIO.getNumLines(FileNames.SS_SKILLS) +
                     MyTextIO.getNumLines(FileNames.PE_SKILLS)];

  inventory = new Inventory();

  healthState = new HealthState();

  meleeAttack    = Formulas.calcMeleeAttack(this);
  thrownAttack   = Formulas.calcThrownAttack(this);
  launcherAttack = Formulas.calcLauncherAttack(this);

  resetNaturalDefense();
  equipmentDefense = 0;
  setTotalDefense();

  exp          = new MyHashMap("exp", 30);          // holds all the exp points for the various skill categories
  baseLevel    = new MyHashMap("baseLevel", 30);    // holds all the lvl variables for the various skill categories
  currentLevel = new MyHashMap("currentLevel", 30); // holds all the lvl variables for the various skill categories

  // set lvl and exp defaults
  baseLevel.put("Axe Class Weapons",        0);
  baseLevel.put("Blunt Class Weapons",      0);
  baseLevel.put("Hand to Hand Class Weapons",  0);
  baseLevel.put("Long Blade Class Weapons", 0);
  baseLevel.put("Launcher Class Weapons",   0);
  baseLevel.put("Polearm Class Weapons",    0);
  baseLevel.put("Short Blade Class Weapons",0);
  baseLevel.put("Spiked Class Weapons",     0);
  baseLevel.put("Thrown Class Weapons",     0);

  currentLevel.put("Axe Class Weapons",        0);
  currentLevel.put("Blunt Class Weapons",      0);
  currentLevel.put("Hand to Hand Class Weapons",  0);
  currentLevel.put("Long Blade Class Weapons", 0);
  currentLevel.put("Launcher Class Weapons",   0);
  currentLevel.put("Polearm Class Weapons",    0);
  currentLevel.put("Short Blade Class Weapons",0);
  currentLevel.put("Spiked Class Weapons",     0);
  currentLevel.put("Thrown Class Weapons",     0);

  exp.put("Axe Class Weapons",        0);
  exp.put("Blunt Class Weapons",      0);
  exp.put("Hand to Hand Class Weapons",  0);
  exp.put("Long Blade Class Weapons", 0);
  exp.put("Launcher Class Weapons",   0);
  exp.put("Polearm Class Weapons",    0);
  exp.put("Short Blade Class Weapons",0);
  exp.put("Spiked Class Weapons",     0);
  exp.put("Thrown Class Weapons",     0);

  baseLevel.put("Unarmored Proficiency",    0);
  baseLevel.put("Light Armor Proficiency",  0);
  baseLevel.put("Medium Armor Proficiency", 0);
  baseLevel.put("Heavy Armor Proficiency",  0);
  baseLevel.put("Shield Proficiency",       0);

  currentLevel.put("Unarmored Proficiency",    0);
  currentLevel.put("Light Armor Proficiency",  0);
  currentLevel.put("Medium Armor Proficiency", 0);
  currentLevel.put("Heavy Armor Proficiency",  0);
  currentLevel.put("Shield Proficiency",       0);

  exp.put("Unarmored Proficiency",    0);
  exp.put("Light Armor Proficiency",  0);
  exp.put("Medium Armor Proficiency", 0);
  exp.put("Heavy Armor Proficiency",  0);
  exp.put("Shield Proficiency",       0);

  baseLevel.put("Stealth",       0);
  baseLevel.put("Backstab",      0);
  baseLevel.put("Snipe",         0);
  baseLevel.put("Pickpocket",    0);
  baseLevel.put("Locks",         0);
  baseLevel.put("Traps",         0);

  currentLevel.put("Stealth",    0);
  currentLevel.put("Backstab",   0);
  currentLevel.put("Snipe",      0);
  currentLevel.put("Pickpocket", 0);
  currentLevel.put("Locks",      0);
  currentLevel.put("Traps",      0);

  exp.put("Stealth",             0);
  exp.put("Backstab",            0);
  exp.put("Snipe",               0);
  exp.put("Pickpocket",          0);
  exp.put("Locks",               0);
  exp.put("Traps",               0);

  baseLevel.put("Spells Fire",   0);
  baseLevel.put("Spells Energy", 0);
  baseLevel.put("Spells Air",    0);
  baseLevel.put("Spells Life",   0);
  baseLevel.put("Spells Water",  0);
  baseLevel.put("Spells Nature", 0);
  baseLevel.put("Spells Earth",  0);
  baseLevel.put("Spells Death",  0);

  currentLevel.put("Spells Fire",   0);
  currentLevel.put("Spells Energy", 0);
  currentLevel.put("Spells Air",    0);
  currentLevel.put("Spells Life",   0);
  currentLevel.put("Spells Water",  0);
  currentLevel.put("Spells Nature", 0);
  currentLevel.put("Spells Earth",  0);
  currentLevel.put("Spells Death",  0);

  exp.put("Spells Fire",   0);
  exp.put("Spells Energy", 0);
  exp.put("Spells Air",    0);
  exp.put("Spells Life",   0);
  exp.put("Spells Water",  0);
  exp.put("Spells Nature", 0);
  exp.put("Spells Earth",  0);
  exp.put("Spells Death",  0);

  } // end constructor()

//***************************************************************************
// package methods
//***************************************************************************

  void setFullName      (String n)   {fullName = n;}     // end setName(String)
  void setShortName     (String n)   {shortName = n;}    // end setName(String)
  void setRace          (Race r)     {race = r;}         // end setRace(Race)
  void setGender        (String g)   {gender = g;}       // end setGender(String)
  void setAge           (int a)      {age = a;}          // end setAge(int)
  void setSkinColor     (Color c)    {skinColor = c;}    // end setSkinColor(Color)
  void setHairColor     (Color c)    {hairColor = c;}    // end setHairColor(Color)
  void setMajorColor    (Color c)    {majorColor = c;}   // end setMajorColor(Color)
  void setMinorColor    (Color c)    {minorColor = c;}   // end setMinorColor(Color)

  void setBaseHP        (int h)      {baseHP = h;}       // end setBaseHP(int)
  void setCurrentHP     (int h)      {currentHP = h;}    // end setCurrentHP(int)
  void setTempMaxHP     (int h)      {tempMaxHP = h;}    // end setTempMaxHP(int)
  void setBaseMP        (int m)      {baseMP = m;}       // end setBaseMP(int)
  void setCurrentMP     (int m)      {currentMP = m;}    // end setCurrentMP(int)
  void setTempMaxMP     (int m)      {tempMaxMP = m;}    // end setTempMaxMP(int)

  void setAlignment     (Alignment a){alignment = a;}    // end setAlignment(int)
  void setRelationToPC  (int r)      {relationToPC = r;} // end setRelationToPC(int)
  void setCastingFailure(int c)      {castingFailure = c;}// end setCastingFailure(int)

  String getFullName    ()        {return fullName;}     // end getName()
  String getShortName   ()        {return shortName;}    // end getName()
  Race   getRace        ()        {return race;}         // end getRace()
  String getGender      ()        {return gender;}       // end getGender()
  int    getAge         ()        {return age;}          // end getAge()
  Color  getSkinColor   ()        {return skinColor;}    // end getSkinColor()
  Color  getHairColor   ()        {return hairColor;}    // end getHairColor()
  Color  getMajorColor  ()        {return majorColor;}   // end getMajorColor()
  Color  getMinorColor  ()        {return minorColor;}   // end getMinorColor()
Alignment getAlignment  ()        {return alignment;}    // end getAlignment()
  int    getRelationToPC()        {return relationToPC;} // end getRelationToPC()
  int    getCastingFailure()      {return castingFailure;}// end getCastingFailure()

  void setBaseStr       (int v)   {baseStr = v;}         // end setBaseStr(int)
  void setBaseDex       (int v)   {baseDex = v;}         // end setBaseDex(int)
  void setBaseCon       (int v)   {baseCon = v;}         // end setBaseCon(int)
  void setBaseInt       (int v)   {baseInt = v;}         // end setBaseInt(int)
  void setBaseWis       (int v)   {baseWis = v;}         // end setBaseWis(int)
  void setBaseCha       (int v)   {baseCha = v;}         // end setBaseCha(int)

  void setTempStr       (int v)   {tempStr = v;}         // end setBaseStr(int)
  void setTempDex       (int v)   {tempDex = v;}         // end setBaseDex(int)
  void setTempCon       (int v)   {tempCon = v;}         // end setBaseCon(int)
  void setTempInt       (int v)   {tempInt = v;}         // end setBaseInt(int)
  void setTempWis       (int v)   {tempWis = v;}         // end setBaseWis(int)
  void setTempCha       (int v)   {tempCha = v;}         // end setBaseCha(int)

  int getBaseStr        ()        {return baseStr;}      // end setBaseStr()
  int getBaseDex        ()        {return baseDex;}      // end setBaseDex()
  int getBaseCon        ()        {return baseCon;}      // end setBaseCon()
  int getBaseInt        ()        {return baseInt;}      // end setBaseInt()
  int getBaseWis        ()        {return baseWis;}      // end setBaseWis()
  int getBaseCha        ()        {return baseCha;}      // end setBaseCha()

  int getTempStr        ()        {return tempStr;}      // end setBaseStr()
  int getTempDex        ()        {return tempDex;}      // end setBaseDex()
  int getTempCon        ()        {return tempCon;}      // end setBaseCon()
  int getTempInt        ()        {return tempInt;}      // end setBaseInt()
  int getTempWis        ()        {return tempWis;}      // end setBaseWis()
  int getTempCha        ()        {return tempCha;}      // end setBaseCha()

  void setBaseStrBonus  (int b)   {baseStrBonus = b;}    // end setBaseStr()
  void setBaseDexBonus  (int b)   {baseDexBonus = b;}    // end setBaseDex()
  void setBaseConBonus  (int b)   {baseConBonus = b;}    // end setBaseCon()
  void setBaseIntBonus  (int b)   {baseIntBonus = b;}    // end setBaseInt()
  void setBaseWisBonus  (int b)   {baseWisBonus = b;}    // end setBaseWis()
  void setBaseChaBonus  (int b)   {baseChaBonus = b;}    // end setBaseCha()

  void setTempStrBonus  (int b)   {tempStrBonus = b;}    // end setBaseStr()
  void setTempDexBonus  (int b)   {tempDexBonus = b;}    // end setBaseDex()
  void setTempConBonus  (int b)   {tempConBonus = b;}    // end setBaseCon()
  void setTempIntBonus  (int b)   {tempIntBonus = b;}    // end setBaseInt()
  void setTempWisBonus  (int b)   {tempWisBonus = b;}    // end setBaseWis()
  void setTempChaBonus  (int b)   {tempChaBonus = b;}    // end setBaseCha()

  int getBaseStrBonus   ()        {return baseStrBonus;} // end setBaseStr()
  int getBaseDexBonus   ()        {return baseDexBonus;} // end setBaseDex()
  int getBaseConBonus   ()        {return baseConBonus;} // end setBaseCon()
  int getBaseIntBonus   ()        {return baseIntBonus;} // end setBaseInt()
  int getBaseWisBonus   ()        {return baseWisBonus;} // end setBaseWis()
  int getBaseChaBonus   ()        {return baseChaBonus;} // end setBaseCha()

  int getTempStrBonus   ()        {return tempStrBonus;} // end setBaseStr()
  int getTempDexBonus   ()        {return tempDexBonus;} // end setBaseDex()
  int getTempConBonus   ()        {return tempConBonus;} // end setBaseCon()
  int getTempIntBonus   ()        {return tempIntBonus;} // end setBaseInt()
  int getTempWisBonus   ()        {return tempWisBonus;} // end setBaseWis()
  int getTempChaBonus   ()        {return tempChaBonus;} // end setBaseCha()

  void addTempStrAdj    (int v)   {tempStr += v;}        // end addTempStrAdg(int)
  void addTempDexAdj    (int v)   {tempDex += v;}        // end addTempDexAdg(int)
  void addTempConAdj    (int v)   {tempCon += v;}        // end addTempConAdg(int)
  void addTempIntAdj    (int v)   {tempInt += v;}        // end addTempIntAdg(int)
  void addTempWisAdj    (int v)   {tempWis += v;}        // end addTempWisAdg(int)
  void addTempChaAdj    (int v)   {tempCha += v;}        // end addTempChaAdg(int)

  void subTempStrAdj    (int v)   {tempStr -= v;}        // end subTempStrAdg(int)
  void subTempDexAdj    (int v)   {tempDex -= v;}        // end subTempDexAdg(int)
  void subTempConAdj    (int v)   {tempCon -= v;}        // end subTempConAdg(int)
  void subTempIntAdj    (int v)   {tempInt -= v;}        // end subTempIntAdg(int)
  void subTempWisAdj    (int v)   {tempWis -= v;}        // end subTempWisAdg(int)
  void subTempChaAdj    (int v)   {tempCha -= v;}        // end subTempChaAdg(int)

  boolean isImmuneToFire()    {return immuneToFire;}
  boolean isImmuneToEnergy()  {return immuneToEnergy;}
  boolean isImmuneToAir()     {return immuneToAir;}
  boolean isImmuneToLife()    {return immuneToLife;}
  boolean isImmuneToWater()   {return immuneToWater;}
  boolean isImmuneToNature()  {return immuneToNature;}
  boolean isImmuneToEarth()   {return immuneToEarth;}
  boolean isImmuneToDeath()   {return immuneToDeath;}
  boolean isImmuneToCrushing(){return immuneToCrushing;}
  boolean isImmuneToPiercing(){return immuneToPiercing;}
  boolean isImmuneToSlashing(){return immuneToSlashing;}
  boolean isImmuneToMelee()   {return immuneToMelee;}
  boolean isImmuneToRanged()  {return immuneToRanged;}

  void setImmuneToFire     (boolean b) {immuneToFire     = b;}
  void setImmuneToEnergy   (boolean b) {immuneToEnergy   = b;}
  void setImmuneToAir      (boolean b) {immuneToAir      = b;}
  void setImmuneToLife     (boolean b) {immuneToLife     = b;}
  void setImmuneToWater    (boolean b) {immuneToWater    = b;}
  void setImmuneToNature   (boolean b) {immuneToNature   = b;}
  void setImmuneToEarth    (boolean b) {immuneToEarth    = b;}
  void setImmuneToDeath    (boolean b) {immuneToDeath    = b;}
  void setImmuneToCrushing (boolean b) {immuneToCrushing = b;}
  void setImmuneToPiercing (boolean b) {immuneToPiercing = b;}
  void setImmuneToSlashing (boolean b) {immuneToSlashing = b;}
  void setImmuneToMelee    (boolean b) {immuneToMelee    = b;}
  void setImmuneToRanged   (boolean b) {immuneToRanged   = b;}

  int getResistToFire()    {return resistFire;}
  int getResistToEnergy()  {return resistEnergy;}
  int getResistToAir()     {return resistAir;}
  int getResistToLife()    {return resistLife;}
  int getResistToWater()   {return resistWater;}
  int getResistToNature()  {return resistNature;}
  int getResistToEarth()   {return resistEarth;}
  int getResistToDeath()   {return resistDeath;}
  int getResistToCrushing(){return resistCrushing;}
  int getResistToPiercing(){return resistPiercing;}
  int getResistToSlashing(){return resistSlashing;}
  int getResistToMelee()   {return resistMelee;}
  int getResistToRanged()  {return resistRanged;}

  void setResistToFire    (int r) {resistFire     = r;}
  void setResistToEnergy  (int r) {resistEnergy   = r;}
  void setResistToAir     (int r) {resistAir      = r;}
  void setResistToLife    (int r) {resistLife     = r;}
  void setResistToWater   (int r) {resistWater    = r;}
  void setResistToNature  (int r) {resistNature   = r;}
  void setResistToEarth   (int r) {resistEarth    = r;}
  void setResistToDeath   (int r) {resistDeath    = r;}
  void setResistToCrushing(int r) {resistCrushing = r;}
  void setResistToPiercing(int r) {resistPiercing = r;}
  void setResistToSlashing(int r) {resistSlashing = r;}
  void setResistToMelee   (int r) {resistMelee    = r;}
  void setResistToRanged  (int r) {resistRanged   = r;}

  void setSaveFort   (int i) {saveFort   = i;} // end setSaveFort
  void setSaveReflex (int i) {saveReflex = i;} // end setSaveReflex
  void setSaveSocial (int i) {saveSocial = i;} // end setSaveSocial
  void setSaveFire   (int i) {saveFire   = i;} // end setSaveFire
  void setSaveEnergy (int i) {saveEnergy = i;} // end setSaveEnergy
  void setSaveAir    (int i) {saveAir    = i;} // end setSaveAir
  void setSaveLife   (int i) {saveLife   = i;} // end setSaveLife
  void setSaveWater  (int i) {saveWater  = i;} // end setSaveWater
  void setSaveNature (int i) {saveNature = i;} // end setSaveNature
  void setSaveEarth  (int i) {saveEarth  = i;} // end setSaveEarth
  void setSaveDeath  (int i) {saveDeath  = i;} // end setSaveDeath

  int getSaveFort   () {return saveFort   ;} // end getSaveFort
  int getSaveReflex () {return saveReflex ;} // end getSaveReflex
  int getSaveSocial () {return saveSocial ;} // end getSaveSocial
  int getSaveFire   () {return saveFire   ;} // end getSaveFire
  int getSaveEnergy () {return saveEnergy ;} // end getSaveEnergy
  int getSaveAir    () {return saveAir    ;} // end getSaveAir
  int getSaveLife   () {return saveLife   ;} // end getSaveLife
  int getSaveWater  () {return saveWater  ;} // end getSaveWater
  int getSaveNature () {return saveNature ;} // end getSaveNature
  int getSaveEarth  () {return saveEarth  ;} // end getSaveEarth
  int getSaveDeath  () {return saveDeath  ;} // end getSaveDeath

//********************************************************************
  void setPortraitPictureSmall(String s){smallPortraitFile = s;}  // end setPicSmall
  void setPortraitPictureLarge(String l){largePortraitFile = l;}  // end setPicLarge

  String getPortraitPictureSmall(){return smallPortraitFile;}  // end getPicSmall
  String getPortraitPictureLarge(){return largePortraitFile;}  // end getPicLarge

  void   setPicPath(String s){picPathAndFile = s;}        // end setPicPath
  String getPicPath()        {return picPathAndFile;}     // end getPicPath

  Inventory getInventory() {return inventory;}

  int getBaseHP    () {return baseHP;}       // end setBaseHP(int)
  int getCurrentHP () {return currentHP;}    // end setCurrentHP(int)
  int getTempMaxHP () {return tempMaxHP;}    // end setTempMaxHP(int)
  int getBaseMP    () {return baseMP;}       // end setBaseMP(int)
  int getCurrentMP () {return currentMP;}    // end setCurrentMP(int)
  int getTempMaxMP () {return tempMaxMP;}    // end setTempMaxMP(int)

  void drainHP (int d) {
    currentHP -= d;
    if (currentHP < 1) healthState.setStateUnconscious(true);
    if (currentHP < -this.getBaseCon()){
      healthState.setStateDead(true);
      healthState.setRegenerating(false);
    } // end if dead
  }   // end damageHP(int)

  void fillHP (int h){
    currentHP += h;
    if (currentHP > tempMaxHP) currentHP = tempMaxHP;
    if (currentHP > 0) healthState.setStateUnconscious(false);
    healthState.setRegenerating(true);
  } // end healHP

  void damageHP (int d){
    tempMaxHP -= d;
    if (tempMaxHP < 1) tempMaxHP = 1;
  } // end damageHP

  void healHP (int h){
    tempMaxHP += h;
    if (tempMaxHP > baseHP) tempMaxHP = baseHP;
  } // end healHP

  void drainMP (int d) {
    currentMP -= d;
    if (currentMP < 1) currentMP = 0;
  }   // end damageHP(int)

  void fillMP (int h) {
    currentMP += h;
    if (currentMP > tempMaxMP) currentMP = tempMaxMP;
  } // end fillMP(int)

  void damageMP (int d){
    tempMaxMP -= d;
    if (tempMaxMP < 0) tempMaxMP = 0;
  } // end damageMP

  void healMP (int h){
    tempMaxMP += h;
    if (tempMaxMP > baseMP) tempMaxMP = baseMP;
  } // end healMP

  int getMeleeAttack    () {return meleeAttack;}
  int getThrownAttack   () {return thrownAttack;}
  int getLauncherAttack () {return launcherAttack;}

  void setMeleeAttack    (int a) {meleeAttack    = a;}
  void setThrownAttack   (int a) {thrownAttack   = a;}
  void setLauncherAttack (int a) {launcherAttack = a;}

  int getNaturalDefense()   {return naturalDefense;}   // end getNaturalDefense
  int getEquipmentDefense() {return equipmentDefense;} // end getEquipmentDefense

  int getTotalDefense()     {
    setTotalDefense();
    return totalDefense;
  } // end getTotalDefense

//***************************************************************************
  void setNaturalDefense   (int i) {
    // if the natural defense has changed, then we need to update the totalDefense too.
    naturalDefense = i;
    setTotalDefense();
  }   // end setNaturalDefense

//***************************************************************************
  void setEquipmentDefense (int i) {
    // if the equipment defense has changed, then we need to update the totalDefense
    equipmentDefense = i;
    setTotalDefense();
  } // end setEquipmentDefense

//***************************************************************************
  void setTotalDefense     ()      {totalDefense = naturalDefense + equipmentDefense;} // end setTotalDefense

//***************************************************************************
  boolean hasSkill(String skillTitle) {
    // we need to check all the skills that the character has
    // then check each skill title with the string passed.

    // first, find out how many skills the character has:
    int a = 0;
    boolean done = false;
    for (a = 0; !done; a++){
      if (skills[a] == null){
        done = true;
      } // end if null
    } // end for loop (a)

    // now, for all the skills the character has, check the title.
    for(int i = 0; i < a - 1; i++){
    // check the title of this skill against the passed string.
    try{
      if (skillTitle.equalsIgnoreCase(skills[i].getTitle())){
        return true;
      } // end if
    } // end try
    catch(NullPointerException e){
      Popup.createErrorPopup("Null Pointer Exception when attempting" +
                             " to get title at Skills index:" + i);
      return false;
    } // end catch
    } // end for loop
    return false;
  } // end hasSkill(string)

//***************************************************************************
  void addSkill(Skill thisSkill){
    // purpose of this method is to add the skill to the end of the list.
    // find the first null location, and set that location to be thisSkill.
    int i = 0;
    for (i = 0; i < skills.length; i++){
      if (skills[i] == null){
        break;
      } // end if
    } // end for loop
    skills[i] = thisSkill;
  } // end addSkill

//****************************************************************************
  void removeSkill(Skill thisSkill){
    // purpose of this method is to remove the passed skill from the character
    // and adjust the list accordinly.
    int i = 0;
    for (i = 0; i < skills.length; i++){
      if (thisSkill == skills[i]){
        skills[i] = null;
        //now, slide the rest of the skills back one.
        try{
          for (int j = i; j < skills.length && skills[j] != null; j++){
            skills[j] = skills[j+1];
          } // end for loop
        } // end try
        catch(ArrayIndexOutOfBoundsException e){
          Popup.createErrorPopup("Array Index Out Of Bounds Exception: " + i + MyTextIO.newline + "Skills.length = " + skills.length);
        } // end catch
      } // end if
    } // end for loop
  } // end removeSkill

//****************************************************************************
  void removeAllSkills(){
    for (int i = 0 ; i < skills.length; i++){
      skills[i] = null;
    } // end for loop
  } // end removeAllSkills

//***************************************************************************
  Skill getSkill(int i) {
    try{
      return skills[i];
    } // end try
    catch (NullPointerException e){
      Popup.createErrorPopup("NPE while getting skill at index " + i);
      return null;
    } // end catch
  } // end getSkill

//****************************************************************************
  Skill[] getSkills(){ return skills; } // end getSkills();

//****************************************************************************
  int getNumSkills(){
    int i = 0;
    while (skills[i] != null){
      i++;
    } // end while loop
    return i;
  } // end getNumSkills

//***************************************************************************
  void writeToFile(BufferedWriter bw) throws IOException{
    // purpose of method is to write all details to a file
    // start with parent class:
    super.writeToFile(bw);

    // now we can go through all of our variables and write them to a file
    bw.write("LivingObject properies:" + MyTextIO.tab);

    // major character details
    bw.write("Full Name:"  + MyTextIO.tab + fullName        + MyTextIO.tab);
    bw.write("short Name:" + MyTextIO.tab + shortName       + MyTextIO.tab);
    bw.write("Gender:"     + MyTextIO.tab + gender          + MyTextIO.tab);
    bw.write("Race:"       + MyTextIO.tab + race.getName()  + MyTextIO.tab);
    bw.write("Age:"        + MyTextIO.tab + age             + MyTextIO.tab);

    bw.write("Skin Color:" + MyTextIO.tab +
             skinColor.getRed() + " " +
             skinColor.getGreen() + " " +
             skinColor.getBlue()
             + MyTextIO.tab);

    bw.write("Hair Color:" + MyTextIO.tab +
             hairColor.getRed() + " " +
             hairColor.getGreen() + " " +
             hairColor.getBlue()
             + MyTextIO.tab);

    bw.write("Major Color:" + MyTextIO.tab +
             majorColor.getRed() + " " +
             majorColor.getGreen() + " " +
             majorColor.getBlue()
             + MyTextIO.tab);

    bw.write("Minor Color:" + MyTextIO.tab +
             minorColor.getRed() + " " +
             minorColor.getGreen() + " " +
             minorColor.getBlue()
             + MyTextIO.tab);

    bw.write("BaseHP:"                 + MyTextIO.tab + baseHP     + MyTextIO.tab);
    bw.write("TempMaxHP"               + MyTextIO.tab + tempMaxHP  + MyTextIO.tab);
    bw.write("CurrentHP:"              + MyTextIO.tab + currentHP  + MyTextIO.tab);
    bw.write("BaseMP:"                 + MyTextIO.tab + baseMP     + MyTextIO.tab);
    bw.write("TempMaxMP"               + MyTextIO.tab + tempMaxMP  + MyTextIO.tab);
    bw.write("CurrentMP:"              + MyTextIO.tab + currentMP); bw.newLine();

    alignment.writeAlignmentToFile(bw);

    bw.write("RelationToPC:"           + MyTextIO.tab + relationToPC); bw.newLine();
    bw.write("Casting Failure:"        + MyTextIO.tab + castingFailure); bw.newLine();

    bw.write("Large Portrait File:"    + MyTextIO.tab + largePortraitFile); bw.newLine();
    bw.write("Small Portrait File:"    + MyTextIO.tab + smallPortraitFile); bw.newLine();

    bw.flush();

    // now write stats.
    bw.write("Base Strength:"          + MyTextIO.tab + baseStr + MyTextIO.tab);
    bw.write("Base Dexterity:"         + MyTextIO.tab + baseDex + MyTextIO.tab);
    bw.write("Base Constitution:"      + MyTextIO.tab + baseCon + MyTextIO.tab);
    bw.write("Base Intelligence:"      + MyTextIO.tab + baseInt + MyTextIO.tab);
    bw.write("Base Wisdom:"            + MyTextIO.tab + baseWis + MyTextIO.tab);
    bw.write("Base Charisma:"          + MyTextIO.tab + baseCha); bw.newLine();

    bw.write("Temp Strength:"          + MyTextIO.tab + tempStr + MyTextIO.tab);
    bw.write("Temp Dexterity:"         + MyTextIO.tab + tempDex + MyTextIO.tab);
    bw.write("Temp Constitution:"      + MyTextIO.tab + tempCon + MyTextIO.tab);
    bw.write("Temp Intelligence:"      + MyTextIO.tab + tempInt + MyTextIO.tab);
    bw.write("Temp Wisdom:"            + MyTextIO.tab + tempWis + MyTextIO.tab);
    bw.write("Temp Charisma:"          + MyTextIO.tab + tempCha); bw.newLine();

    bw.write("Base Strength Bonus:"    + MyTextIO.tab + baseStrBonus + MyTextIO.tab);
    bw.write("Base Dexterity Bonus:"   + MyTextIO.tab + baseDexBonus + MyTextIO.tab);
    bw.write("Base Constitution Bonus:"+ MyTextIO.tab + baseConBonus + MyTextIO.tab);
    bw.write("Base Intelligence Bonus:"+ MyTextIO.tab + baseIntBonus + MyTextIO.tab);
    bw.write("Base Wisdom Bonus:"      + MyTextIO.tab + baseWisBonus + MyTextIO.tab);
    bw.write("Base Charisma Bonus:"    + MyTextIO.tab + baseChaBonus); bw.newLine();

    bw.write("Temp Strength Bonus:"    + MyTextIO.tab + tempStrBonus + MyTextIO.tab);
    bw.write("Temp Dexterity Bonus:"   + MyTextIO.tab + tempDexBonus + MyTextIO.tab);
    bw.write("Temp Constitution Bonus:"+ MyTextIO.tab + tempConBonus + MyTextIO.tab);
    bw.write("Temp Intelligence Bonus:"+ MyTextIO.tab + tempIntBonus + MyTextIO.tab);
    bw.write("Temp Wisdom Bonus:"      + MyTextIO.tab + tempWisBonus + MyTextIO.tab);
    bw.write("Temp Charisma Bonus:"    + MyTextIO.tab + tempChaBonus); bw.newLine();

    bw.write("Immunity to Fire:"       + MyTextIO.tab + immuneToFire   + MyTextIO.tab);
    bw.write("Immunity to Energy:"     + MyTextIO.tab + immuneToEnergy + MyTextIO.tab);
    bw.write("Immunity to Air:"        + MyTextIO.tab + immuneToAir    + MyTextIO.tab);
    bw.write("Immunity to Life:"       + MyTextIO.tab + immuneToLife   + MyTextIO.tab);
    bw.write("Immunity to Water:"      + MyTextIO.tab + immuneToWater  + MyTextIO.tab);
    bw.write("Immunity to Nature:"     + MyTextIO.tab + immuneToNature + MyTextIO.tab);
    bw.write("Immunity to Earth:"      + MyTextIO.tab + immuneToEarth  + MyTextIO.tab);
    bw.write("Immunity to Death:"      + MyTextIO.tab + immuneToDeath  + MyTextIO.tab);
    bw.write("Immunity to Crushing:"   + MyTextIO.tab + immuneToCrushing + MyTextIO.tab);
    bw.write("Immunity to Piercing:"   + MyTextIO.tab + immuneToPiercing + MyTextIO.tab);
    bw.write("Immunity to Slashing:"   + MyTextIO.tab + immuneToSlashing + MyTextIO.tab);
    bw.write("Immunity to Melee:"      + MyTextIO.tab + immuneToMelee    + MyTextIO.tab);
    bw.write("Immunity to Ranged:"     + MyTextIO.tab + immuneToRanged);   bw.newLine();

    bw.write("Fire Resistance:"        + MyTextIO.tab + resistFire   + MyTextIO.tab);
    bw.write("Energy Resistance:"      + MyTextIO.tab + resistEnergy + MyTextIO.tab);
    bw.write("Air Resistance:"         + MyTextIO.tab + resistAir    + MyTextIO.tab);
    bw.write("Life Resistance:"        + MyTextIO.tab + resistLife   + MyTextIO.tab);
    bw.write("Water Resistance:"       + MyTextIO.tab + resistWater  + MyTextIO.tab);
    bw.write("Nature Resistance:"      + MyTextIO.tab + resistNature + MyTextIO.tab);
    bw.write("Earth Resistance:"       + MyTextIO.tab + resistEarth  + MyTextIO.tab);
    bw.write("Death Resistance:"       + MyTextIO.tab + resistDeath  + MyTextIO.tab);
    bw.write("Crushing Resistance:"    + MyTextIO.tab + resistCrushing + MyTextIO.tab);
    bw.write("Piercing Resistance:"    + MyTextIO.tab + resistPiercing + MyTextIO.tab);
    bw.write("Slashing Resistance:"    + MyTextIO.tab + resistSlashing + MyTextIO.tab);
    bw.write("Melee Resistance:"       + MyTextIO.tab + resistMelee    + MyTextIO.tab);
    bw.write("Ranged Resistance:"      + MyTextIO.tab + resistRanged);   bw.newLine();

    bw.write("Fortitude Save:"         + MyTextIO.tab + saveFort    + MyTextIO.tab);
    bw.write("Reflex Save:"            + MyTextIO.tab + saveReflex  + MyTextIO.tab);
    bw.write("Social Save:"            + MyTextIO.tab + saveSocial  + MyTextIO.tab);
    bw.write("Fire Save:"              + MyTextIO.tab + saveFire    + MyTextIO.tab);
    bw.write("Energy Save:"            + MyTextIO.tab + saveEnergy  + MyTextIO.tab);
    bw.write("Air Save:"               + MyTextIO.tab + saveAir     + MyTextIO.tab);
    bw.write("Life Save:"              + MyTextIO.tab + saveLife    + MyTextIO.tab);
    bw.write("Water Save:"             + MyTextIO.tab + saveWater   + MyTextIO.tab);
    bw.write("Nature Save:"            + MyTextIO.tab + saveNature  + MyTextIO.tab);
    bw.write("Earth Save:"             + MyTextIO.tab + saveEarth   + MyTextIO.tab);
    bw.write("Death Save:"             + MyTextIO.tab + saveDeath);   bw.newLine();

    bw.write("Melee Attack: "          + MyTextIO.tab + meleeAttack    + MyTextIO.tab);
    bw.write("Thrown Attack: "         + MyTextIO.tab + thrownAttack   + MyTextIO.tab);
    bw.write("Launcher Attack: "       + MyTextIO.tab + launcherAttack + MyTextIO.tab);

    bw.write("Natural Defense: "       + MyTextIO.tab + naturalDefense   + MyTextIO.tab);
    bw.write("Equipment Defense: "     + MyTextIO.tab + equipmentDefense); bw.newLine();

    bw.flush();

    // now write levels and experience points
    baseLevel.    writeMyHashMapToFile(bw);
    currentLevel. writeMyHashMapToFile(bw);
    exp.          writeMyHashMapToFile(bw);

    bw.flush();

    bw.write("Skills:");
    for (int i = 0; i < skills.length; i++){
      if (skills[i] == null) continue;
      else{
        bw.write(MyTextIO.tab + skills[i].getTitle());
      } // end else - skill does exist
    } // end for loop
    bw.newLine();

    healthState.writeHealthStateToFile(bw);

    bw.flush();

    inventory.writeInventoryToFile(bw);

    bw.flush();

  } // end writeLivingObjectToFile(bw)

//***************************************************************************
  void readFromFile(BufferedReader br) throws IOException{
    // purpose of this method is to read from the br and create
    // a LivingObject from it.
    // first read the parent class
    super.readFromFile(br);

    // now we can read these details in
    String thisLine = br.readLine();

//    Popup.createInfoPopup("Attempting to read LivingObject from file:" + MyTextIO.newline + thisLine);

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "LivingObject properies:"

//******************************************************************
    // read major character details
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "Full Name:"
    fullName = MyTextIO.getNextPhrase(thisLine);
    thisLine = MyTextIO.trimPhrase(thisLine); // removes fullName

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "short Name:"
    shortName = MyTextIO.getNextPhrase(thisLine);
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes shortName

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "Gender:"
    gender   = MyTextIO.getNextPhrase(thisLine);
    thisLine = MyTextIO.trimPhrase(thisLine); // removes gender

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "Race:"
    race     = Race.getRace(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes race

    thisLine = MyTextIO.trimPhrase(thisLine); // removes "Age:"
    age      = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes age

//******************************************************************

    // each phrase is a color, so each color should work with a single phrase
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "Skin Color:"

    String thisPhrase = MyTextIO.getNextPhrase(thisLine); // capture ints

    int red    = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    thisPhrase = MyTextIO.trimWord(thisPhrase);
    int green = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    thisPhrase  = MyTextIO.trimWord(thisPhrase);
    int blue  = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    skinColor = new Color(red, green, blue);

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "int, int, int"
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "Hair Color:"

    thisPhrase = MyTextIO.getNextPhrase(thisLine); // capture ints

    red   = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    thisPhrase  = MyTextIO.trimWord(thisPhrase);
    green = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    thisPhrase  = MyTextIO.trimWord(thisPhrase);
    blue  = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    hairColor = new Color(red, green, blue);

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes int, int, int
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "Major Color:"

    thisPhrase = MyTextIO.getNextPhrase(thisLine);

    red   = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    thisPhrase  = MyTextIO.trimWord(thisPhrase);
    green = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    thisPhrase  = MyTextIO.trimWord(thisPhrase);
    blue  = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    majorColor = new Color(red, green, blue);

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes int, int, int
    thisLine  = MyTextIO.trimPhrase(thisLine); // removes "Minor Color:"

    thisPhrase = MyTextIO.getNextPhrase(thisLine);

    red   = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    thisPhrase  = MyTextIO.trimWord(thisPhrase);
    green = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    thisPhrase  = MyTextIO.trimWord(thisPhrase);
    blue  = Integer.parseInt(MyTextIO.getNextWord(thisPhrase));
    minorColor = new Color(red, green, blue);

    thisLine  = MyTextIO.trimPhrase(thisLine); // removes int, int, int

//******************************************************************
     thisLine  = MyTextIO.trimPhrase(thisLine); // removes "BaseHP"
     baseHP    = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
     thisLine  = MyTextIO.trimPhrase(thisLine); // removes stat

     thisLine  = MyTextIO.trimPhrase(thisLine); // removes "TempMaxHP"
     tempMaxHP = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
     thisLine  = MyTextIO.trimPhrase(thisLine); // removes stat

     thisLine  = MyTextIO.trimPhrase(thisLine); // removes "currentHP"
     currentHP = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
     thisLine  = MyTextIO.trimPhrase(thisLine); // removes stat

     thisLine = MyTextIO.trimPhrase(thisLine); // removes "BaseMP"
     baseMP   = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
     thisLine = MyTextIO.trimPhrase(thisLine); // removes stat

     thisLine  = MyTextIO.trimPhrase(thisLine); // removes "TempMaxHP"
     tempMaxMP = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
     thisLine  = MyTextIO.trimPhrase(thisLine); // removes stat

     thisLine  = MyTextIO.trimPhrase(thisLine); // removes "BaseMP"
     currentMP = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
     thisLine  = MyTextIO.trimPhrase(thisLine); // removes stat

//******************************************************************
    alignment = alignment.readAlignmentFromFile(br);

//******************************************************************
    thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    relationToPC = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes relationToPC

//******************************************************************
    thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    castingFailure = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes castingFailure

//******************************************************************
    thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    largePortraitFile = MyTextIO.getNextPhrase(thisLine);

//******************************************************************
    thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    smallPortraitFile = MyTextIO.getNextPhrase(thisLine);

//******************************************************************
// now read the stats. (base)
    thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseStr = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseStr

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseDex = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseDex

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseCon = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseCon

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseInt = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseInt

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseWis = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseWis

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseCha = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseCha

//******************************************************************
// now read the stats. (temp)
    thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempStr = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempStr

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempDex = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempDex

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempCon = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempCon

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempInt = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempInt

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempWis = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempWis

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempCha = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempCha

//******************************************************************
// now read the stats. (baseBonuse)
    thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseStrBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseStrBonus

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseDexBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseDexBonus

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseConBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseConBonus

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseIntBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseIntBonus

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseWisBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseWisBonus

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    baseChaBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes baseChaBonus

//******************************************************************
    // now read the stats. (tempBonuse)
    thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempStrBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempStrBonus

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempDexBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempDexBonus

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempConBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempConBonus

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempIntBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempIntBonus

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempWisBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempWisBonus

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    tempChaBonus = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes tempChaBonus

//******************************************************************
    // now read the stats. (Immunities)
    thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToFire = true;
    else immuneToFire = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToEnergy = true;
    else immuneToEnergy = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToAir = true;
    else immuneToAir = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToLife = true;
    else immuneToLife = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToWater = true;
    else immuneToWater = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToNature = true;
    else immuneToNature = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToEarth = true;
    else immuneToEarth = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToDeath = true;
    else immuneToDeath = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToCrushing = true;
    else immuneToCrushing = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToPiercing = true;
    else immuneToPiercing = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToSlashing = true;
    else immuneToSlashing = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToMelee = true;
    else immuneToMelee = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    if (MyTextIO.getNextPhrase(thisLine).equalsIgnoreCase("true"))
      immuneToRanged = true;
    else immuneToRanged = false;
    thisLine = MyTextIO.trimPhrase(thisLine); // removes "true/false"

//******************************************************************
    // now read the stats. (Resistances)
    thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistFire = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistFire

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistEnergy = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistEnergy

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistAir = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistAir

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistLife = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistLife

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistWater = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistWater

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistNature = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistNature

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistEarth = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistEarth

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistDeath = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistDeath

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistCrushing = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistCrushing

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistPiercing = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistPiercing

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistSlashing = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistSlashing

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistMelee = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistSlashing

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    resistRanged = Integer.parseInt(MyTextIO.getNextPhrase(thisLine));
    thisLine = MyTextIO.trimPhrase(thisLine); // removes resistSlashing

//******************************************************************
     thisLine = br.readLine();

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    saveFort = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
    thisLine = MyTextIO.trimPhrase(thisLine); // removes value

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    saveReflex = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
    thisLine = MyTextIO.trimPhrase(thisLine); // removes value

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    saveSocial = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
    thisLine = MyTextIO.trimPhrase(thisLine); // removes value

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    saveFire = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
    thisLine = MyTextIO.trimPhrase(thisLine); // removes value

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    saveEnergy = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
    thisLine = MyTextIO.trimPhrase(thisLine); // removes value

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    saveAir = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
    thisLine = MyTextIO.trimPhrase(thisLine); // removes value

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    saveLife = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
    thisLine = MyTextIO.trimPhrase(thisLine); // removes value

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    saveWater = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
    thisLine = MyTextIO.trimPhrase(thisLine); // removes value

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    saveNature = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
    thisLine = MyTextIO.trimPhrase(thisLine); // removes value

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    saveEarth = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
    thisLine = MyTextIO.trimPhrase(thisLine); // removes value

    thisLine = MyTextIO.trimPhrase(thisLine); // removes title
    saveDeath = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
    thisLine = MyTextIO.trimPhrase(thisLine); // removes value

//******************************************************************
    thisLine = br.readLine();

   thisLine = MyTextIO.trimPhrase(thisLine); // removes title
   meleeAttack = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
   thisLine = MyTextIO.trimPhrase(thisLine); // removes value

   thisLine = MyTextIO.trimPhrase(thisLine); // removes title
   thrownAttack = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
   thisLine = MyTextIO.trimPhrase(thisLine); // removes value

   thisLine = MyTextIO.trimPhrase(thisLine); // removes title
   launcherAttack = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
   thisLine = MyTextIO.trimPhrase(thisLine); // removes value

   thisLine = MyTextIO.trimPhrase(thisLine); // removes title
   naturalDefense = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
   thisLine = MyTextIO.trimPhrase(thisLine); // removes value

   thisLine = MyTextIO.trimPhrase(thisLine); // removes title
   equipmentDefense = Integer.parseInt(MyTextIO.getNextPhrase(thisLine)); // capture value
   thisLine = MyTextIO.trimPhrase(thisLine); // removes value

   // since we don't need any info to set totalDefense, just call it.
   setTotalDefense();

//******************************************************************
   // now read levels and experience points (CB)

   baseLevel    = MyHashMap.readMyHashMapFromFile(br);
   currentLevel = MyHashMap.readMyHashMapFromFile(br);
   exp          = MyHashMap.readMyHashMapFromFile(br);

//******************************************************************
//now read skills

    thisLine = br.readLine();
    thisLine = MyTextIO.trimPhrase(thisLine); // removes title

    if (thisLine != null){
      for (int i = 0; thisLine.length() > 0; i++) {

        Skill tempSkill = Skill.createSkillFromTitle(MyTextIO.getNextPhrase(
            thisLine));
        if (tempSkill != null) {
          skills[i] = tempSkill;
        } // end if
        thisLine = MyTextIO.trimPhrase(thisLine); // removes title
        if (thisLine == null)break;
      } // end for loop
    } // if thisLine != null

//***************************************************************************
  healthState.readHealthStateFromFile(br);

//***************************************************************************
  inventory.readFromFile(br);

//***************************************************************************
  } // end readLivingObjectFromFile

//***************************************************************************
//***************************************************************************
  void calculateBaseHPMP(){
    // purpose of this method is to calculate base HP for a character

    // set HP
    if (Constants.debugMode)
      Popup.createInfoPopup("Formula generated HP of: " + Formulas.getHPPerLevel(this));
    setBaseHP(Formulas.getHPPerLevel(this));
    setTempMaxHP(getBaseHP());
    setCurrentHP(getTempMaxHP());

    // set MP
    if (Constants.debugMode)
      Popup.createInfoPopup("Formula generated MP of: " + Formulas.getMPPerLevel(this));
    setBaseMP(Formulas.getMPPerLevel(this));
    setTempMaxMP(getBaseMP());
    setCurrentMP(getTempMaxMP());

  } // end calculateBaseHPMP()

//***************************************************************************
  String createLabelofLevels(){
    // purpose of this method is to gather the information from the character,
    // create a string for the charInfoPanel levelsPanel.

    String result = MyTextIO.newline + "Levels:" + MyTextIO.newline;

    // check all levels for any greater than 0;

    for (int i = 0; i < baseLevel.length; i++){
      if (baseLevel.elements[i] == null) break;

      else if (baseLevel.elements[i].getValue() > 0){

        // then we are interested in the value, so append a line.
        String title = baseLevel.elements[i].getKey();

        // check if title has "class weapons" as the last two words, and remove them.
        if (MyTextIO.getLastWord(title).equalsIgnoreCase("Weapons")){
          title = MyTextIO.trimLastWord(MyTextIO.trimLastWord(title)); // removes "Weapons"
        } // end if weapons

        // check if title has "proficiency" as the last word, and remove it.
        else if (MyTextIO.getLastWord(title).equalsIgnoreCase("Proficiency")){
          title = MyTextIO.trimLastWord(title); // removes "proficiency"
        } // end if proficiency

        result += (title + MyTextIO.tab +
                   " lvl " + baseLevel.elements[i].getValue() +
                   ", exp: " + exp.get(baseLevel.elements[i].getKey()) + MyTextIO.newline);

      } // end if non-zero

    } // end for loop

    return result;
  } // end createLableofSphereLevels

//****************************************************************************
  String createLabelofFeats(){
    String result = MyTextIO.newline + "Feats:" + MyTextIO.newline;

    Skill[] skills = getSkills();

    for (int i = 0; i < skills.length; i++){
      if (skills[i] == null) continue;
      result += (MyTextIO.trimWord(skills[i].getTitle()) + MyTextIO.newline);
    } // end for loop

    return result;
  } // end createLabelofFeats

//****************************************************************************
  void updateLevels(){
    // purpose of this method is to view the different skills the character has chosen
    // and to update the appropriate levels, and remove the skills from the character's array

    // look through the list of skills, if the first word is: "Feat:", then skip it.

    for (int i = 0;skills[i] != null &&  i < skills.length; i++){
      // for each skill that the charater does actually have:

      if (MyTextIO.getNextWord(skills[i].getTitle()).equals("Feat:")){
        // if it is a feat, then...
        continue; // on to the next skill
      } // end if feat

      else {
        // if the skill is a path of training, then...
        String skillName = skills[i].getTitle();

        // set the level of that key on the baseLevel HashMap
        baseLevel.setValue(skillName, 1);
        currentLevel.setValue(skillName, 1);
        exp.setValue(skillName, 1000);
//        Popup.createInfoPopup("removing skill: " + skills[i].getTitle());
        skills[i] = null;
      } // end else (is skill, not feat)
    } // end for loop
  } // end updateLevels

//****************************************************************************
  public void updateStatsFromInventory(){
    /* the purpose of this method is to take the current inventory and update
     * the character's stats.  This will be a large method, as it must go through
     * each and every item in the character's inventory and pull out any
     * effects or alterations that change any part the character data.
     * NOTE: remember to reset the information first, or to recalculate it,
     * since it will be far to difficult to just add/subtract content based on items.
     */

     // before we begin the loop, we need to reset some variables.

     // reset Defense Variables
     setEquipmentDefense(0);      // reset equipment
     tempDexBonus = baseDexBonus; // reset dexBonus
     resetNaturalDefense();
     resetResistances();
     resetAttacks();              // reset Melee, Thrown, Launcher
     resetCastingFailure();       // reset to zero, but checks skill bonuses too.

   // resetImmunities(); // test races and future effects...)

     // we need to go through all items in character's inventory.
     for (int i = 0; i < inventory.items.length; i++){
       // NOTE: we are going through the entire inventory, not just equipment
       //       this will allow us more freedom in having charms, artifacts, etc. affect the character.
       //       essentially, we need to go through each itemSlot and update info.
       // first go through the standard bonuses and changes, then add in magic later.

       //******************************
       // check this item as gold.
       if (inventory.items[i] != null &&
           inventory.items[i].getType() == Item.GOLD){

         // then add the amount to character's gold total
         inventory.goldAmount += inventory.items[i].getQuantity();
         // and remove the item from the inventory
         inventory.items[i] = null;
       } // end if gold

       //******************************
       // check this item for equipment defense and maxDexBonus
       // if this item is a piece of armor, then add it to equipmentDefense.
       if (inventory.items[i] != null &&
           inventory.items[i].isArmor() &&
           inventory.items[i].isEquipped()){

//         Popup.createInfoPopup("armor piece found, updating equipment defense" + MyTextIO.newline +
//                               "equipment Def was: " + equipmentDefense + ", adding: "
//                               + ((Armor)(inventory.items[i])).getEquipmentDefense());

         equipmentDefense += ((Armor)(inventory.items[i])).basics.getEquipmentDefense();
         tempDexBonus = Math.min(tempDexBonus, ((Armor)(inventory.items[i])).basics.getMaxDexBonus());
         resetNaturalDefense();
       } // end if armor

       //******************************
       // now check this item is the character's equipped weapon
       if (inventory.items[i] != null      &&
           inventory.items[i].isEquipped() &&
            (inventory.items[i].isWeapon() ||
             inventory.items[i].isLauncher())){
         // we want to check this equipped weapon against the character's stats,
         // adding or removing numbers to their three attacks.

         int lvl = currentLevel.get(inventory.items[i].basics.getGoverningProficiency());

         if      (inventory.items[i].basics.getAttackType() == Weapon.MELEE){
           // character is equipping a melee weapon, so check against skill for melee bonus
           meleeAttack = (Math.max(meleeAttack + Formulas.getBonusForNumRanks(lvl), 0));
         } // end melee type

         else if (inventory.items[i].basics.getAttackType() == Weapon.THROWN){
           // character is equipping a thrown weapon, so check against skill for thrown bonus
           thrownAttack = (Math.max(thrownAttack + Formulas.getBonusForNumRanks(lvl), 0));
         } // end thrown type

         else if (inventory.items[i].basics.getAttackType() == Weapon.LAUNCHER){
           launcherAttack = (Math.max(launcherAttack + Formulas.getBonusForNumRanks(lvl), 0));
         } // end launcher type

         else{}
       } // end if item exists and is equipped weapon

       //******************************
       // check this item for any resistance effects
    /*  if (inventory.items[i] != null){
          for (int j = 0; j < inventory.items[i].effects.length; j++){
            // for each effect, check for a resistance effect.
            if (inventory.items[i].effects[j].getWhat().equalsIgnoreCase("resist") &&
                (inventory.items[i].effects[j].getWhen().equalsIgnoreCase("permanent") ||
                 inventory.items[i].effects[j].getWhen().equalsIgnoreCase("equipped"))){
              // now, we know this effect is a resistance, so get the bonus (sphere) and add the amt to it.
              String sphere = inventory.items[i].effects[j].getBonus();
              if      (sphere.equalsIgnoreCase("Fire"))     setResistToFire     (getResistToFire()     + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Energy"))   setResistToEnergy   (getResistToEnergy()   + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Air"))      setResistToAir      (getResistToAir()      + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Life"))     setResistToLife     (getResistToLife()     + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Water"))    setResistToWater    (getResistToWater()    + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Nature"))   setResistToNature   (getResistToNature()   + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Earth"))    setResistToEarth    (getResistToEarth()    + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Death"))    setResistToDeath    (getResistToDeath()    + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Crushing")) setResistToCrushing (getResistToCrushing() + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Piercing")) setResistToPiercing (getResistToPiercing() + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Slashing")) setResistToSlashing (getResistToSlashing() + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Melee"))    setResistToMelee    (getResistToSlashing() + inventory.items[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Ranged"))   setResistToRanged   (getResistToSlashing() + inventory.items[i].effects[j].getAmount());
              else{} // end else
            } // end if effect is a "resist"
          } // end for loop (effects)
        } // end if item exists
    */
       //******************************
       // check this item for any immunity effects
    /*  if (inventory.items[i] != null){
          for (int j = 0; j < inventory.items[i].effects.length; j++){
            // for each effect, check for a resistance effect.
            if (inventory.items[i].effects[j].getWhat().equalsIgnoreCase("immune") &&
                (inventory.items[i].effects[j].getWhen().equalsIgnoreCase("permanent") ||
                 inventory.items[i].effects[j].getWhen().equalsIgnoreCase("equipped"))){
              // now, we know this effect is a resistance, so get the bonus (sphere) and add the amt to it.
              String sphere = inventory.items[i].effects[j].getBonus();
              if      (sphere.equalsIgnoreCase("Fire"))     setImmuneToFire     (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Energy"))   setImmuneToEnergy   (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Air"))      setImmuneToAir      (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Life"))     setImmuneToLife     (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Water"))    setImmuneToWater    (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Nature"))   setImmuneToNature   (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Earth"))    setImmuneToEarth    (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Death"))    setImmuneToDeath    (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Crushing")) setImmuneToCrushing (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Piercing")) setImmuneToPiercing (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Slashing")) setImmuneToSlashing (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Melee"))    setImmuneToMelee    (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else if (sphere.equalsIgnoreCase("Ranged"))   setImmuneToRanged   (Boolean.getValueOf(inventory.items[i].effects[j].getAmount()));
              else{} // end else
            } // end if effect is a "resist"
          } // end for loop (effects)
        } // end if item exists
    */
       //******************************
       // check this item for any save effects
    /*
        if (inventory.items[i] != null){
          for (int j = 0; j < inventory.items[i].effects.length; j++){
            // for each effect, check for a save effect.
            if (inventory.items[i].effects[j].getWhat().equalsIgnoreCase("save") &&
                (inventory.items[i].effects[j].getWhen().equalsIgnoreCase("permanent") ||
                 inventory.items[i].effects[j].getWhen().equalsIgnoreCase("equipped"))){
              // now, we know this effect is a resistance, so get the bonus (sphere) and add the amt to it.
              String sphere = inventory.items[i].effects[j].getBonus();
              if      (sphere.equalsIgnoreCase("Fire"))     setSaveFire     (getSaveFire()   + skills[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Energy"))   setSaveEnergy   (getSaveEnergy() + skills[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Air"))      setSaveAir      (getSaveAir()    + skills[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Life"))     setSaveLife     (getSaveLife()   + skills[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Water"))    setSaveWater    (getSaveWater()  + skills[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Nature"))   setSaveNature   (getSaveNature() + skills[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Earth"))    setSaveEarth    (getSaveEarth()  + skills[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Death"))    setSaveDeath    (getSaveDeath()  + skills[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Fortitude"))setSaveFort     (getSaveFort()   + skills[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Reflex"))   setSaveReflex   (getSaveReflex() + skills[i].effects[j].getAmount());
              else if (sphere.equalsIgnoreCase("Social"))   setSaveSocial   (getSaveSocial() + skills[i].effects[j].getAmount());
              else{} // end else
            } // end if effect is a "save"
          } // end for loop (effects)
        } // end if item exists
    */
       //******************************
       // now check this item for casting Failure,
       // which only affects equipped items, so only check those.
       if (inventory.items[i] != null && inventory.items[i].isEquipped()){
         castingFailure += inventory.items[i].basics.getCastingFailure();
       } // end if item exists

       //******************************
       // now check this item for any attack bonuses,
       // which only affects equipped items, so only check those.
       if (inventory.items[i] != null      &&
           inventory.items[i].isEquipped() &&
           ( inventory.items[i].getType() == Item.ARMS_GLOVES  ||
             inventory.items[i].getType() == Item.ARMS_BRACERS ||
             inventory.items[i].getType() == Item.ARMS_GAUNTLETS)){
         meleeAttack    += ((Arms)(inventory.items[i])).getMeleeBonus();
         thrownAttack   += ((Arms)(inventory.items[i])).getThrownBonus();
         launcherAttack += ((Arms)(inventory.items[i])).getLauncherBonus();
       } // end if item exists, equipped, and is type: arms

       //******************************
       // now check this item for...
       if (inventory.items[i] != null){
       } // end if item exists and...

       //******************************
     } // end for loop (# of inventory items)

     // now that we're done with the inventory,
     // set some of the derived stats accordingly.

     resetSaves(); // uses the newly set tempDexBonus and race to calculate.

  } // end updateStatsFromInventory

//****************************************************************************
  void resetSaves(){
    // purpose of this method is to reset the character's base saves.
    // this method is called when clearing the character's stats duirng CGen
    // the base is a combination of race, stat, and skill.

  // start here - with the race + stat + Constants.base.

  setSaveFire   (race.getSaveFire   () + Constants.BASE_SAVE);
  setSaveEnergy (race.getSaveEnergy () + Constants.BASE_SAVE);
  setSaveAir    (race.getSaveAir    () + Constants.BASE_SAVE);
  setSaveLife   (race.getSaveLife   () + Constants.BASE_SAVE);
  setSaveWater  (race.getSaveWater  () + Constants.BASE_SAVE);
  setSaveNature (race.getSaveNature () + Constants.BASE_SAVE);
  setSaveEarth  (race.getSaveEarth  () + Constants.BASE_SAVE);
  setSaveDeath  (race.getSaveDeath  () + Constants.BASE_SAVE);

  setSaveFort   (race.getSaveFort   () + getTempConBonus() + Constants.BASE_SAVE);
  setSaveReflex (race.getSaveReflex () + getTempDexBonus() + Constants.BASE_SAVE);
  setSaveSocial (race.getSaveSocial () + getTempChaBonus() + Constants.BASE_SAVE);

  // now search through the character's skills (feats) for any save bonuses.

    for (int i = 0; i < skills.length; i++){

      // for each skill, check if it is a feat of resistance.
      if (skills[i] == null) continue;

      // now, go through each effect and see if anyone is a resistance.

      for (int j = 0; j < skills[i].effects.length; j++){
        // the format is: (when)Permanent (what)resist (bonus)Fire (amount)5

        if (skills[i].effects[j] == null) continue;

        if (skills[i].effects[j].getWhat().equalsIgnoreCase("save")){

          // now, we know this effect is a save, so get the bonus (type) and add the amt to it.
          String type = skills[i].effects[j].getBonus();

          if      (type.equalsIgnoreCase("Fire"))      setSaveFire   (getSaveFire()   + skills[i].effects[j].getAmount());
          else if (type.equalsIgnoreCase("Energy"))    setSaveEnergy (getSaveEnergy() + skills[i].effects[j].getAmount());
          else if (type.equalsIgnoreCase("Air"))       setSaveAir    (getSaveAir()    + skills[i].effects[j].getAmount());
          else if (type.equalsIgnoreCase("Life"))      setSaveLife   (getSaveLife()   + skills[i].effects[j].getAmount());
          else if (type.equalsIgnoreCase("Water"))     setSaveWater  (getSaveWater()  + skills[i].effects[j].getAmount());
          else if (type.equalsIgnoreCase("Nature"))    setSaveNature (getSaveNature() + skills[i].effects[j].getAmount());
          else if (type.equalsIgnoreCase("Earth"))     setSaveEarth  (getSaveEarth()  + skills[i].effects[j].getAmount());
          else if (type.equalsIgnoreCase("Death"))     setSaveDeath  (getSaveDeath()  + skills[i].effects[j].getAmount());
          else if (type.equalsIgnoreCase("Fortitude")) setSaveFort   (getSaveFort()   + skills[i].effects[j].getAmount());
          else if (type.equalsIgnoreCase("Reflex"))    setSaveReflex (getSaveReflex() + skills[i].effects[j].getAmount());
          else if (type.equalsIgnoreCase("Social"))    setSaveSocial (getSaveSocial() + skills[i].effects[j].getAmount());
          else{} // end else
        } // end if resist
      } // end for loop (effects)
    } // end for loop (skills)
  } // end resetSaves

//*************************************************************************
  void resetResistances() {
    // purpose of this method is to reset the character's base resistances.
    // this method is called when clearing the character's stats during CGen

    // *NOTE: begin with a racial base, instead of 0. (especially for monsters!)

    setResistToFire     (race.getResFire());
    setResistToEnergy   (race.getResEnergy());
    setResistToAir      (race.getResAir());
    setResistToLife     (race.getResLife());
    setResistToWater    (race.getResWater());
    setResistToNature   (race.getResNature());
    setResistToEarth    (race.getResEarth());
    setResistToDeath    (race.getResDeath());
    setResistToCrushing (race.getResCrushing());
    setResistToPiercing (race.getResPiercing());
    setResistToSlashing (race.getResSlashing());
    setResistToMelee    (race.getResMelee());
    setResistToRanged   (race.getResRanged());

    // now search through the character's skills or feats for any resistances.

    for (int i = 0; i < skills.length; i++){

      // for each skill, check if it is a feat of resistance.
      if (skills[i] == null) continue;

      // now, go through each effect and see if anyone is a resistance.

      for (int j = 0; j < skills[i].effects.length; j++){
        // the format is: (when)Permanent (what)resist (bonus)Fire (amount)5

        if (skills[i].effects[j] == null) continue;

        if (skills[i].effects[j].getWhat().equalsIgnoreCase("resist")){
          // now, we know this effect is a resistance, so get the bonus (sphere) and add the amt to it.
          String sphere = skills[i].effects[j].getBonus();
          if      (sphere.equalsIgnoreCase("Fire"))     setResistToFire     (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Energy"))   setResistToEnergy   (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Air"))      setResistToAir      (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Life"))     setResistToLife     (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Water"))    setResistToWater    (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Nature"))   setResistToNature   (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Earth"))    setResistToEarth    (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Death"))    setResistToDeath    (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Crushing")) setResistToCrushing (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Piercing")) setResistToPiercing (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Slashing")) setResistToSlashing (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Melee"))    setResistToMelee    (skills[i].effects[j].getAmount());
          else if (sphere.equalsIgnoreCase("Ranged"))   setResistToRanged   (skills[i].effects[j].getAmount());
          else{} // end else
        } // end if resist
      } // end for loop (effects)
    } // end for loop (skills)
  } // end resetResistances

//*************************************************************************
  void resetImmunities(){
      // purpose of this method is to reset the character's base resistances.
      // this method is called when clearing the character's stats during CGen

      // *NOTE: begin with a racial base, instead of 0. (especially for monsters!)

      setImmuneToFire     (race.isImFire());
      setImmuneToEnergy   (race.isImEnergy());
      setImmuneToAir      (race.isImAir());
      setImmuneToLife     (race.isImLife());
      setImmuneToWater    (race.isImWater());
      setImmuneToNature   (race.isImNature());
      setImmuneToEarth    (race.isImEarth());
      setImmuneToDeath    (race.isImDeath());
      setImmuneToCrushing (race.isImCrushing());
      setImmuneToPiercing (race.isImPiercing());
      setImmuneToSlashing (race.isImSlashing());
      setImmuneToMelee    (race.isImMelee());
      setImmuneToRanged   (race.isImRanged());

      // now search through the character's skills or feats for any resistances.

      for (int i = 0; i < skills.length; i++){

        // for each skill, check if it is a feat of resistance.
        if (skills[i] == null) continue;

        // now, go through each effect and see if anyone is a resistance.

        for (int j = 0; j < skills[i].effects.length; j++){
          // the format is: (when)Permanent (what)resist (bonus)Fire (amount)5

          if (skills[i].effects[j] == null) continue;

          if (skills[i].effects[j].getWhat().equalsIgnoreCase("immune")){
            // now, we know this effect is a resistance, so get the bonus (sphere) and add the amt to it.
            String sphere = skills[i].effects[j].getBonus();
            if      (sphere.equalsIgnoreCase("Fire"))     setImmuneToFire     (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Energy"))   setImmuneToEnergy   (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Air"))      setImmuneToAir      (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Life"))     setImmuneToLife     (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Water"))    setImmuneToWater    (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Nature"))   setImmuneToNature   (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Earth"))    setImmuneToEarth    (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Death"))    setImmuneToDeath    (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Crushing")) setImmuneToCrushing (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Piercing")) setImmuneToPiercing (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Slashing")) setImmuneToSlashing (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Melee"))    setImmuneToMelee    (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else if (sphere.equalsIgnoreCase("Ranged"))   setImmuneToRanged   (Boolean.valueOf(skills[i].effects[j].getBonus()).booleanValue());
            else{} // end else
          } // end if resist
        } // end for loop (effects)
      } // end for loop (skills)
  } // end resetImmunities

//****************************************************************************
  public void resetCastingFailure(){
    // start with a base of zero, no defaults based on race, yet.
    castingFailure = 0;

//    Popup.createInfoPopup("resetting casting failure...");

    // now, look for any feat effects to change casting failure.
    for (int i = 0; i < skills.length && skills[i]!= null; i++){

//      Popup.createInfoPopup("checking skill #" + i);
      // for each skill, check all effects
      for (int j = 0; j < skills[i].effects.length && skills[i].effects[j] != null; j++){

        // look for the effect to have the 'what' as "castingFailure"
//        Popup.createInfoPopup("checking effect #" + i + " for skill titled: " + skills[i].getTitle());
        if (skills[i].effects[j].getWhat().equalsIgnoreCase("CastingFailure")){
//          Popup.createInfoPopup("skill match found, adjusting castingFailure.");
          castingFailure += skills[i].effects[j].getAmount();
        } // end if match text
      } // end for j loop // all effects of a skill
    } // end for i loop // all skills
  } // end resetCastingFailure

//****************************************************************************
  public void resetNaturalDefense(){
    // purpose of this method is to recalculate the natural defense of the passed character.

    // start with basics: default + race + stats
    naturalDefense = Formulas.calcNaturalDefense(this);

    // now add any bonuses due to skills or feats
    for (int i = 0; i < skills.length && skills[i]!= null; i++){

//      Popup.createInfoPopup("checking skill #" + i);
      // for each skill, check all effects
      for (int j = 0; j < skills[i].effects.length && skills[i].effects[j] != null; j++){

        // look for the effect to have the 'what' as "NatDef"
//        Popup.createInfoPopup("checking effect #" + i + " for skill titled: " + skills[i].getTitle());
        if (skills[i].effects[j].getWhat().equalsIgnoreCase("NatDef")){
//          Popup.createInfoPopup("match found, adjusting NatDef. (LivingObject/resetNaturalDefense)");
          naturalDefense += skills[i].effects[j].getAmount();
        } // end if match text

      } // end for j loop

    } // end for i loop

  } // end resetNaturalDefense

//****************************************************************************
  public void resetAttacks(){
    // purpose of this method is to reset the three attacks of the character to prepare for
    // inventory modification.

    setMeleeAttack    (Formulas.calcMeleeAttack    (this));
    setThrownAttack   (Formulas.calcThrownAttack   (this));
    setLauncherAttack (Formulas.calcLauncherAttack (this));

  } // end resetAttacks()

//****************************************************************************
} // end class LivingObject
