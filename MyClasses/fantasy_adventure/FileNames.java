package fantasy_adventure;

import java.io.*;

//imports

public class FileNames implements Serializable{

  // static declarations

  // CGen/Genetics files *****************************************************************************************
  static String CGEN_GENETICS_SCREEN_INTRO = MyTextIO.readOneLineFromFile("Data/CGen/GeneticsScreenIntroduction.txt");

  static String RACES                    = "Data/CGen/Races.csv";
  static String CGEN_RACE_DESC           = MyTextIO.readOneLineFromFile("Data/CGen/RaceDescription.txt"  );
  static String CGEN_GENDER_DESC         = MyTextIO.readOneLineFromFile("Data/CGen/GenderDescription.txt");
  static String CGEN_NAME_DESC           = MyTextIO.readOneLineFromFile("Data/CGen/NameDescription.txt"  );
  static String CGEN_AGE_DESC            = MyTextIO.readOneLineFromFile("Data/CGen/AgeDescription.txt"   );
  static String CGEN_HUMAN_NAME_DESC     = MyTextIO.readOneLineFromFile("Data/CGen/HumanNamesDescription.txt"   );
  static String CGEN_ELF_NAME_DESC       = MyTextIO.readOneLineFromFile("Data/CGen/ElfNamesDescription.txt"     );
  static String CGEN_DWARF_NAME_DESC     = MyTextIO.readOneLineFromFile("Data/CGen/DwarfNamesDescription.txt"   );
  static String CGEN_GNOME_NAME_DESC     = MyTextIO.readOneLineFromFile("Data/CGen/GnomeNamesDescription.txt"   );
  static String CGEN_HALFLING_NAME_DESC  = MyTextIO.readOneLineFromFile("Data/CGen/HalflingNamesDescription.txt");
  static String CGEN_HALF_ORC_NAME_DESC  = MyTextIO.readOneLineFromFile("Data/CGen/HalfOrcNamesDescription.txt" );
  static String HUMAN_MALE_FIRST_NAMES   = "Data/Names/Human_M_Firstnames.csv";
  static String HUMAN_FEMALE_FIRST_NAMES = "Data/Names/Human_F_Firstnames.csv";
  static String HUMAN_LAST_NAMES         = "Data/Names/HumanLastnames.csv";
  static String ELF_PREFIXES             = "Data/Names/ElfPrefixes.csv";
  static String ELF_SUFFIXES             = "Data/Names/ElfSuffixes.csv";
  static String ELF_LINKING_PHRASE       = "Data/Names/ElfLinkingPhrases.csv";
  static String DWARF_FIRST_NAMES        = "Data/Names/DwarfFirstNames.csv";
  static String DWARF_LAST_NAMES         = "Data/Names/DwarfLastNames.csv";
  static String GNOME_PREFIXES           = "Data/Names/GnomePrefixes.csv";
  static String GNOME_SUFFIXES           = "Data/Names/GnomeSuffixes.csv";
  static String GNOME_M_CLAN             = "Data/Names/Gnome_M_Clan.csv";
  static String GNOME_F_CLAN             = "Data/Names/Gnome_F_Clan.csv";
  static String HALFLING_M_FIRSTNAMES    = "Data/Names/Halfling_M_FirstNames.csv";
  static String HALFLING_F_FIRSTNAMES    = "Data/Names/Halfling_F_FirstNames.csv";
  static String HALFLING_LAST_PREFIXES   = "Data/Names/HalflingLastPrefixes.csv";
  static String HALFLING_LAST_SUFFIXES   = "Data/Names/HalflingLastSuffixes.csv";
  static String HALFORC_NAMES            = "Data/Names/HalfOrcNames.csv";

  static String SKIN_COLOR_DESC          = "Data/CGen/SkinColorDescription.txt";
  static String HAIR_COLOR_DESC          = "Data/CGen/HairColorDescription.txt";
  static String MAJOR_COLOR_DESC         = "Data/CGen/MajorColorDescription.txt";
  static String MINOR_COLOR_DESC         = "Data/CGen/MinorColorDescription.txt";

  // CGen/Stats files ******************************************************************************************
  static String CGEN_STATS_SCREEN_INTRO  = MyTextIO.readOneLineFromFile("Data/CGen/StatisticsScreenIntroduction.txt");

  static String STYLE_3D6_DESC    = MyTextIO.readOneLineFromFile("Data/CGen/Style3D6Description.txt"    );
  static String STYLE_4D6_DESC    = MyTextIO.readOneLineFromFile("Data/CGen/Style4D6Description.txt"    );
  static String STYLE_BUY_DESC    = MyTextIO.readOneLineFromFile("Data/CGen/StyleBuyDescription.txt"    );

  static String STRENGTH_DESC     = MyTextIO.readOneLineFromFile("Data/CGen/StrengthDescription.txt"    );
  static String DEXTERITY_DESC    = MyTextIO.readOneLineFromFile("Data/CGen/DexterityDescription.txt"   );
  static String CONSTITUTION_DESC = MyTextIO.readOneLineFromFile("Data/CGen/ConstitutionDescription.txt");
  static String INTELLIGENCE_DESC = MyTextIO.readOneLineFromFile("Data/CGen/IntelligenceDescription.txt");
  static String WISDOM_DESC       = MyTextIO.readOneLineFromFile("Data/CGen/WisdomDescription.txt"      );
  static String CHARISMA_DESC     = MyTextIO.readOneLineFromFile("Data/CGen/CharismaDescription.txt"    );

  // CGen/Alignment files ******************************************************************************************
  static String CGEN_ALIGNMENT_SCREEN_INTRO = MyTextIO.readOneLineFromFile("Data/CGen/AlignmentScreenIntroduction.txt");

  static String LAWFUL_GOOD_DESC     = MyTextIO.readOneLineFromFile("Data/CGen/LawfulGoodDescription.txt"    );
  static String LAWFUL_NEUTRAL_DESC  = MyTextIO.readOneLineFromFile("Data/CGen/LawfulNeutralDescription.txt" );
  static String LAWFUL_EVIL_DESC     = MyTextIO.readOneLineFromFile("Data/CGen/LawfulEvilDescription.txt"    );
  static String NEUTRAL_GOOD_DESC    = MyTextIO.readOneLineFromFile("Data/CGen/NeutralGoodDescription.txt"   );
  static String TRUE_NEUTRAL_DESC    = MyTextIO.readOneLineFromFile("Data/CGen/TrueNeutralDescription.txt"   );
  static String NEUTRAL_EVIL_DESC    = MyTextIO.readOneLineFromFile("Data/CGen/NeutralEvilDescription.txt"   );
  static String CHAOTIC_GOOD_DESC    = MyTextIO.readOneLineFromFile("Data/CGen/ChaoticGoodDescription.txt"   );
  static String CHAOTIC_NEUTRAL_DESC = MyTextIO.readOneLineFromFile("Data/CGen/ChaoticNeutralDescription.txt");
  static String CHAOTIC_EVIL_DESC    = MyTextIO.readOneLineFromFile("Data/CGen/ChaoticEvilDescription.txt"   );

  static String GOOD_VS_EVIL_DESC    = MyTextIO.readOneLineFromFile("Data/CGen/GoodVsEvilDescription.txt"    );
  static String LAW_VS_CHAOS_DESC    = MyTextIO.readOneLineFromFile("Data/CGen/LawVsChaosDescription.txt"    );
  static String NEUTRAL_DESC         = MyTextIO.readOneLineFromFile("Data/CGen/NeutralDescription.txt"       );

  static String QUESTIONNAIRE        = "Data/CGen/Questionnaire.csv";

  // CGen/Training files *******************************************************************************************
  static String CGEN_TRAINING_SCREEN_INTRO = MyTextIO.readOneLineFromFile("Data/CGen/TrainingScreenIntroduction.txt");

  static String SKILLS_DESC           = MyTextIO.readOneLineFromFile("Data/CGen/SkillsDescription.txt");
  static String CB_DESC               = MyTextIO.readOneLineFromFile("Data/CGen/CBDescription.txt");
  static String SS_DESC               = MyTextIO.readOneLineFromFile("Data/CGen/SSDescription.txt");
  static String PE_DESC               = MyTextIO.readOneLineFromFile("Data/CGen/PEDescription.txt");

  static String SKILLS_AVAILABLE_DESC = MyTextIO.readOneLineFromFile("Data/CGen/AvailableSkillsDescription.txt");
  static String CROSSOVER_DESC        = MyTextIO.readOneLineFromFile("Data/CGen/CrossoverSkillsDescription.txt");
  static String SKILLS_TRAINED_DESC   = MyTextIO.readOneLineFromFile("Data/CGen/TrainedSkillsDescription.txt");

  static String CB_SKILLS             = "Data/cbSkills.csv"; // tab-delimited file
  static String SS_SKILLS             = "Data/ssSkills.csv"; // tab-delimited file
  static String PE_SKILLS             = "Data/peSkills.csv"; // tab-delimited file

  // CGen/Finalize Screen ******************************************************************************************
  static String CGEN_FINALIZE_SCREEN_INTRO = MyTextIO.readOneLineFromFile("Data/CGen/FinalizeScreenIntro.txt");
  static String SAVE_CHR_PATH = Constants.INSTALL_DIRECTORY + "/Save"; // directory relative to install path!

  // Session/WorldMap *****************************************************************************************
  static String WORLD_MAP             = "Images/worldMap.gif";
  static String PC_MAP_ICON           = "Images/partyIcon.gif";
  static String LOADING_PIC           = getLoadingPic();
  static String STARTING_AREA_ID      = "Data/Areas/area03x08y00.csv";

  // Session/AreaMap ************************************************************
  static String DEFAULT_AREA_MAP      = "Images/defaultAreaMap.gif";
  static String DEFAULT_AREA_MARKER   = "Images/marker01.gif";
  static String AREA_MARKER_01        = "Images/marker01.gif";
  static String AREA_MARKER_02        = "Images/marker02.gif";
  static String AREA_MARKER_03        = "Images/marker03.gif";
  static String AREA_MARKER_04        = "Images/marker04.gif";
  static String AREA_MARKER_05        = "Images/marker05.gif";

  // NavPanel ********************************************************************
  static String CLOCK_BACKGROUND      = "Images/clock_background.gif";
  static String CLOCK_SUN             = "Images/clock_sun.gif";
  static String CLOCK_MOON            = "Images/clock_moon.gif";
  static String CLOCK_FOREGROUND      = "Images/clock_foreground.gif";

  // Session/CharInfoPanel *****************************************************************************************
  static String CGEN_TEMP_CHAR_SCREEN_INTRO = MyTextIO.readOneLineFromFile("Data/CGen/TempCharScreen.txt");
  static String CGEN_TEMP_CHAR_SCREEN_TITLE = MyTextIO.readOneLineFromFile("Data/CGen/TempCharScreenTitle.txt");

  static String FAME_TITLES = "Data/fameTitles.csv";

  // Session/Journal *****************************************************************************************
  static String JOURNAL_INTRO        = MyTextIO.readOneLineFromFile("Data/journalIntro.txt");
  static String QUEST_INTRO          = MyTextIO.readOneLineFromFile("Data/questIntro.txt");

  // Session / Inventory ******************************************************************************************
  static String MATERIALS            = "Data/materials.csv";    // tab-delimited file
  static String QUALITIES            = "Data/qualities.csv";    // tab-delimited file
  static String ITEM_REQUIREMENTS    = "Data/requirements.csv"; // tab-delimited file
  static String ITEM_BASICS          = "Data/itemBasics.csv";   // tab-delimited file

  static String SESSION_TEXTURE      = "Images/background/session_texture.jpg";

  static String EMPTY_ITEMSLOT       = "Images/items/emptySlot.jpg";
  static String EMPTY_HELMET         = "Images/items/empty_helmet.jpg";
  static String EMPTY_AMULET         = "Images/items/empty_amulet.jpg";
  static String EMPTY_CLOAK          = "Images/items/empty_Cloak.jpg";
  static String EMPTY_CUIRASS        = "Images/items/empty_Cuirass.jpg";
  static String EMPTY_WEAPON         = "Images/items/empty_Weapon.jpg";
  static String EMPTY_SHIELD         = "Images/items/empty_shield.jpg";
  static String EMPTY_RING           = "Images/items/empty_Ring.jpg";
  static String EMPTY_FOREARMS       = "Images/items/empty_arms.jpg";
  static String EMPTY_BELT           = "Images/items/empty_Belt.jpg";
  static String EMPTY_BOOTS          = "Images/items/empty_Boots.jpg";
  static String EMPTY_AMMO           = "Images/items/empty_Ammo.jpg";

  static String INV_GOLD             = "Images/items/gold.gif";
  static String INV_EQUIP_BACKGROUND = "Images/items/equipmentBackground.gif";

  // FUTURE: this will not be a one-to one match,
  // but rather a choice from all the possible pics for that item & power level.
  static String ITEM_GEM             = "Images/items/gem01.jpg";
  static String ITEM_AMULET          = "Images/items/amulet01.jpg";
  static String ITEM_RING            = "Images/items/ring01.jpg";
  static String ITEM_POTION          = "Images/items/potion01.jpg";
  static String ITEM_SCROLL          = "Images/items/scroll01.jpg";
  static String ITEM_BOOK            = "Images/items/book01.jpg";
  static String ITEM_ARROW           = "Images/items/ammo_arrow01.jpg";
  static String ITEM_BOLT            = "Images/items/ammo_bolt01.jpg";
  static String ITEM_BULLET          = "Images/items/ammo_bullet01.jpg";
  static String ITEM_CUIRASS_LIGHT   = "Images/items/cuirass_light01.jpg";
  static String ITEM_CUIRASS_MEDIUM  = "Images/items/cuirass_medium01.jpg";
  static String ITEM_CUIRASS_HEAVY   = "Images/items/cuirass_heavy01.jpg";
  static String ITEM_CUIRASS_ROBE    = "Images/items/cuirass_robe01.jpg";
  static String ITEM_CLOAK           = "Images/items/cloak01.jpg";
  static String ITEM_HAND2HAND_KNUCKLES = "Images/items/hand2hand_knuckles01.jpg";
  static String ITEM_HAND2HAND_CLAWS    = "Images/items/hand2hand_claws01.jpg";
  static String ITEM_HAND2HAND_DAGGER   = "Images/items/hand2hand_dagger01.jpg";
  static String ITEM_HELMET_NORMAL   = "Images/items/helmet_normal01.jpg";
  static String ITEM_HELMET_BEAST    = "Images/items/helmet_beast01.jpg";
  static String ITEM_HELMET_CIRCLET  = "Images/items/helmet_circlet01.jpg";
  static String ITEM_HELMET_HOOD     = "Images/items/helmet_hood01.jpg";
  static String ITEM_ARMS_BRACERS    = "Images/items/arms_bracers01.jpg";
  static String ITEM_ARMS_GAUNTLETS  = "Images/items/arms_gauntlets01.jpg";
  static String ITEM_ARMS_GLOVES     = "Images/items/arms_gloves01.jpg";
  static String ITEM_SHIELD_BUCKLER  = "Images/items/shield_buckler01.jpg";
  static String ITEM_SHIELD_ROUND    = "Images/items/shield_round01.jpg";
  static String ITEM_SHIELD_MEDIUM   = "Images/items/shield_medium01.jpg";
  static String ITEM_SHIELD_TOWER    = "Images/items/shield_tower01.jpg";
  static String ITEM_BELT_LIGHT      = "Images/items/belt_light01.jpg";
  static String ITEM_BELT_HEAVY      = "Images/items/belt_heavy01.jpg";
  static String ITEM_BOOTS_SOFT      = "Images/items/boots_soft01.jpg";
  static String ITEM_BOOTS_HARD      = "Images/items/boots_hard01.jpg";
  static String ITEM_BOOTS_GREAVES   = "Images/items/boots_greaves01.jpg";
  static String ITEM_AXE_SMALL       = "Images/items/axe_small01.jpg";
  static String ITEM_AXE_MEDIUM      = "Images/items/axe_medium01.jpg";
  static String ITEM_AXE_LARGE       = "Images/items/axe_large01.jpg";
  static String ITEM_AXE_MASSIVE     = "Images/items/axe_massive01.jpg";
  static String ITEM_BLUNT_CLUB      = "Images/items/blunt_club01.jpg";
  static String ITEM_BLUNT_HAMMER    = "Images/items/blunt_hammer01.jpg";
  static String ITEM_BLUNT_MACE      = "Images/items/blunt_mace01.jpg";
  static String ITEM_BLUNT_STAFF     = "Images/items/blunt_staff01.jpg";
  static String ITEM_BLUNT_MAUL      = "Images/items/blunt_maul01.jpg";

//*********************************************************************************
  static String getLoadingPic(){
    //NOTE: future, move this method to the loadingScreen class.
    // purpose of this method is to allow any part of the game to call for a loading pic
    // at random and not have to do any work itself.
    String path = "Images/loadPics";

    // get all the picture files within the folder "Image/loadPics"
    File f = new File(path);

    String[] pics = f.list();

    // and choose randomly from them, returning the whole path.
    // use -1 to avoid missing the 0 index and avoid index out of bounds error
    int picNum = Roll.D(pics.length - 1); // avoid the hidden thumbs.db file!

//    Popup.createInfoPopup("getLoadingPic #" + picNum + MyTextIO.newline
//    + "Returning pic path: " + path + "/" + (pics[picNum - 1]));

    if (pics[picNum].endsWith(".jpg") == false &&
        pics[picNum].endsWith(".JPG") == false ){
      // then we have a pic that doesn't work, so get a new one!
      return getLoadingPic();
    } // end if

    else{
      return (path + "/" + (pics[picNum - 1]));
    } // end else
  } // end getLoadingPic

//*********************************************************************************
} // end class
