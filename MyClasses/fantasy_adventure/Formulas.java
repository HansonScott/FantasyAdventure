package fantasy_adventure;

import java.io.Serializable;

// The purpose of this class is to group together all of the complex formulas
// so that they can be edited from one central area instead of trying to find them
// throughout the collection of classes throughout the package.

// imports

class Formulas implements Serializable{

//*********************************************************************
// static methods
//*********************************************************************
  static int getBonusForNumRanks(int lvl){
    // the purpose of this method is to calculate the current bonus for a
    // given rank in a certain skill.
    // the formula is, 0 = -2, and 1 for every 2 lvls thereafter.

    if (lvl == 0){ return -2;}

    else {return ((int)(lvl / 2));} // end else

  } // end getBonusForNumRanks

//*********************************************************************
  static int calcBeginningGold(LivingObject character){
    // purpose of this method is to calculate beginning gold based upon
    // the stats of the passed character.

    // Thanks to Gabriel Button for his insight regarding the
    // logical factors and formulas affecting beginning gold.
    // (August, 2006)

//    Popup.createInfoPopup("setting gold for race named: '" + character.getRace().getName() + "'");

    int    intFactor      = getIntFactor(character);       // is the X in the form of XdY
    int    ageFactor      = getAgeFactor(character);       // int fulxuates
    double raceMultiplier = character.getRace().getGoldMultiplier(); // double multiplier

    return ((int)(  intFactor * Roll.D(6)
                  * ageFactor
                  * raceMultiplier
                  )
            );

  } // end calcBeginningGold

//*********************************************************************
  private static int getAgeFactor(LivingObject character){
    // need to return the int which will be a multiplication factor
    // based on the age of the character.

    if (character.getAge() < (int)(0.2 * character.getRace().getAvgLifespan()))
      {return character.getRace().getYoungGoldFactor();} // end if young

    else if (character.getAge() < (int)(0.4 * character.getRace().getAvgLifespan()))
      {return character.getRace().getTeenGoldFactor();} // end if teen

    else if (character.getAge() < (int)(0.8 * character.getRace().getAvgLifespan()))
      {return character.getRace().getAdultGoldFactor();} // end if adult

    else if (character.getAge() < (int)(character.getRace().getAvgLifespan()))
      {return character.getRace().getMatureGoldFactor();} // end if mature

    else {return character.getRace().getOldGoldFactor();} // end if old

  } // end getAgeFactor

//*********************************************************************
  private static int getIntFactor(LivingObject character){
    // need to return the int which will be the X in XdY
    // based on the int of the character.

    if (character.getBaseInt() < 6)
      {return character.getRace().getDumbGoldFactor();} // end if dumb

    else if (character.getBaseInt() < 11)
      {return character.getRace().getAvgGoldFactor();} // end if avg

    else if (character.getBaseInt() < 16)
      {return character.getRace().getSmartGoldFactor();} // end if smart

    else {return character.getRace().getWizeGoldFactor();} // end if wize
  } // end getIntFactor

//*********************************************************************
  static int calcItemStat(int base, int mod1, int mod2){
    // the purpose of this mod is to calculate the adjustment
    // of the base number by the other two mods, which is
    // assumed to be a percent 100+/-

    // NOTE: because we are using int types, any division will truncate
    // and decimal parts, so we must do all multiplication first.

    int result = base * mod1 * mod2; // this should result in something around 10,000

    // now, since we know that mod1 and mod2 are supposed to be
    // percentages, we can divide by 100 and 100, or 10,000

    return ((int)((result / 10000) + .5)); // +.5 changes from a truncate to a round.
  } // end calcItemStat

//*********************************************************************
  static int SkillPointsPerLevel(int bonusOne, int bonusTwo){
    /* the purpose of this method is to calculate the number of skill points a
     * character will receive, given the two attribute bonuses.
     * the int returned is the number of skill points the character will receive.
     */
    int skillPoints = ( bonusOne / 2 ) + ( bonusTwo / 2 );

    if (skillPoints > 0) return skillPoints;
    else return 0;
  } // end SkillPointsPerLevel

//*********************************************************************
  static int getHPPerLevel(LivingObject character){
    /* Purpose of this method is to calculate how many HP a character should
     * gain when leveling up.
     * variables include:
       - baseHD for specific race,
       - con Bonus for specific stats,
       - skill Bonus for specific permanent skills
     */

    // get baseHD for race, plus Con bonus:
//    Popup.createInfoPopup("BaseHP: " + Constants.getBaseHD(character.getRace()));
//    Popup.createInfoPopup("Con Bonus: " + character.getBaseConBonus());

      int HP = character.getRace().getBaseHD() + 2 * (character.getBaseConBonus());

//    Popup.createInfoPopup("HP before skills: " + HP);

    // get skillBonus from character's skill array.
    Skill[] skills = character.getSkills();

    // look through skill array and search for a permanent HP bonus:
    for (int i = 0; i < skills.length; i++){
      // for each skill, look for a specific effect
      if (skills[i] != null){

        for (int j = 0; j < skills[i].effects.length; j++) {
          if (skills[i].effects[j] == null){continue;}
          if (skills[i].effects[j].getWhen().equalsIgnoreCase("Permanent") &&
              skills[i].effects[j].getWhat().equalsIgnoreCase("Level") &&
              skills[i].effects[j].getBonus().equalsIgnoreCase("MaxHP")){
            // effect found, so then...
            HP += skills[i].effects[j].getAmount();
//          Popup.createInfoPopup("Adding skill bonus of: " + effects[j].getAmount() + " to HP");
          } // end if correct effect
        } // end for loop effects
      }  // end if not null skill
    } // end for loop each skill

    // now we are done checking skills, so
    if (HP < Constants.MinHPPerLevel){
//     Popup.createInfoPopup("Too low, returning min HP: " + Constants.MinHPPerLevel);
      return Constants.MinHPPerLevel;}

    else {
//      Popup.createInfoPopup("In range, returning HP: " + HP);
      return HP;
    } // end else

  } // end HPPerLevel

//*********************************************************************
  static int getMPPerLevel(LivingObject character){
    /* Purpose of this method is to calculate how many Mana points a character
     * will receive when leveling up.
     */

    // start with standard stat amounts
    int MP = (2 * character.getBaseIntBonus()) +
                  character.getBaseWisBonus() +
                  character.getRace().getBaseMD();

/*        Popup.createInfoPopup("int bonus: " + character.getBaseIntBonus() + MyTextIO.newline +
                                "wis bonud: " + character.getBaseWisBonus() + MyTextIO.newline +
                                   "normal: " + Constants.normalMPPerLevel  + MyTextIO.newline +
                                    "total: " + MP);
*/
    // now search for skill to boost MP per level
    // get skillBonus from character's skill array.
    Skill[] skills = character.getSkills();

    // look through skill array and search for a permanent HP bonus:
    for (int i = 0; i < skills.length; i++){
      // for each skill, look for a specific effect
      if (skills[i] != null){

        for (int j = 0; j < skills[i].effects.length; j++) {
          if (skills[i].effects[j] == null) continue;
          else if (skills[i].effects[j].getWhen().equalsIgnoreCase("Permanent") &&
                   skills[i].effects[j].getWhat().equalsIgnoreCase("Level") &&
                   skills[i].effects[j].getBonus().equalsIgnoreCase("MaxMP") ){
            // effect found, so then...
//          Popup.createInfoPopup("Adding skill bonus of: " + effects[j].getAmount() + " to MP");
            MP += skills[i].effects[j].getAmount();
          } // end if correct effect
        } // end for loop effects
      }  // end if not null skill
    } // end for loop each skill

    if (MP < Constants.MinMPPerLevel) return Constants.MinMPPerLevel;
    else return MP;
  } // end MPPerLevel

//********************************************************************
  static int calcMeleeAttack(LivingObject character){
    return ( 5 + character.getRace().getMeleeAdj() +
                 character.getTempStrBonus() +
          (int)((character.getTempDexBonus() / 2) + 0.5)); // half, round up.

  } // end calcMeleeAttack

//********************************************************************
  static int calcThrownAttack(LivingObject character){
    return ( 5 + character.getRace().getRangedAdj() +
                 character.getTempDexBonus() +
          (int)((character.getTempStrBonus() / 2) + 0.5)); // half, round up.

  } // end calcMeleeAttack

//********************************************************************
  static int calcLauncherAttack(LivingObject character){
    return ( 5 + character.getRace().getRangedAdj() +
                 character.getTempDexBonus() +
          (int)((character.getTempIntBonus() / 2) + 0.5)); // half, round up.

  } // end calcMeleeAttack

//********************************************************************
  static int calcNaturalDefense(LivingObject character){
    return ( Constants.BASE_NATURAL_DEFENSE +
             character.getBaseDexBonus() +
             character.getRace().getNatDefAdj());
  } // end calcNaturalDefense

//********************************************************************
  static int getWeightAllowance(int str){
    return (str * 20) - 40;
  } // end getWeightAllowance

//********************************************************************
  static int calcBonusFromStat(int stat){
    // purpose of this method is to calculate and return the correct bonus
    // from the passed stat value.
    // This is loosly based on the DnD 3D6 format, but extends into the 20s and 30s
    // for extreme bonus or magical situations

    int result = 0;

    return result;

  } // end calcBonusFromStat()

//********************************************************************
} // end class formula

