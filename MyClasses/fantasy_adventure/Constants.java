package fantasy_adventure;

import java.awt.*;
import java.io.File;
import java.io.Serializable;

//imports

public class Constants implements Serializable{

  /* The purpose of this class is to provide a central place to put all
   * the constants in the game.
   * This will interface greatly with the options screen that will change
   * the constants of the game.
   */

//************************************************************************
// static declarations
//************************************************************************
static final int     VERSION   = 95;    // update this number when updating save data structures.
static final boolean debugMode = false;

// Fantasy Adventure Settings ********************************************
static int SCREEN_WIDTH  = getWidth();
static int SCREEN_HEIGHT = getHeight();

static final Dimension SCREEN_SIZE   = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);

static String INSTALL_DIRECTORY = System.getProperty("user.dir");
static final int DOUBLE_CLICK_SPEED = 250; // in milliseconds

// CGen ******************************************************************
static int BUY_POINTS       = 30;
static int STARTING_DIE     = 10;
static int MinHPPerLevel    = 2;
static int MinMPPerLevel    = 2;
static int MIN_ATTRIBUTE    = 3;
static int BASE_NATURAL_DEFENSE = 5;
static int BASE_SAVE        = 4;

static Color SKIN_COLOR     = new Color(255, 204, 153);
static Color HAIR_COLOR     = new Color(102, 51,  0);

static Color BROWN_DARK     = new Color(75, 25, 0);
static Color BROWN_MEDIUM   = new Color(150, 100, 0);
static Color BROWN_LIGHT    = new Color(225, 175, 0);

static Color GREEN_DARK     = new Color(0, 100, 0);
static Color GREEN_MEDIUM   = new Color(0, 175, 0);
static Color GREEN_LIGHT    = new Color(0, 100, 0);

static Color RED_DARK       = new Color(180, 0, 0);
static Color RED_MEDIUM     = new Color(235, 10, 10);
static Color RED_LIGHT      = new Color(255, 20, 20);

static Color YELLOW_DARK    = new Color (150, 150, 0);
static Color YELLOW_MEDIUM  = new Color (235, 215, 90);
static Color YELLOW_LIGHT   = new Color (255, 255, 0);
static Color YELLOW_SOFT    = new Color (255, 255, 200);

// Session ***************************************************************
static final int NAVPANEL_WIDTH         = 75;
static final int NAVPANEL_HEIGHT        = SCREEN_HEIGHT;
static final int NAVPANEL_HGAP          = 10;
static final int NAVPANEL_VGAP          = 10;
static final int NAVPANEL_BUTTON_WIDTH  = NAVPANEL_WIDTH - 2;
static final int NAVPANEL_BUTTON_HEIGHT = 40;

static final int MAX_CHARS_IN_PARTY     = 8;
static final int PARTYPANEL_WIDTH       = 75;
static final int PARTYPANEL_HEIGHT      = SCREEN_HEIGHT;
static final int PARTYPANEL_HGAP        = 10;
static final int PARTYPANEL_VGAP        = 1;

static final int PORTRAIT_SMALL_WIDTH   = PARTYPANEL_WIDTH - (4 + 3 + 3); // 75 - 10 = ( 65 )
static final int PORTRAIT_SMALL_HEIGHT  = 90; // use set number, since pics have to be set too.

static final int CENTRALPANEL_WIDTH     = SCREEN_WIDTH - (NAVPANEL_WIDTH + PARTYPANEL_WIDTH);
static final int CENTRALPANEL_HEIGHT    = SCREEN_HEIGHT;

static final int INSET_FOR_TAB          = 40;
static final int JOURNAL_TOPIC_HEIGHT   = 40;

// Items *****************************************************************
static final int STACK_SIZE             = 50;

// WorldPanel ************************************************************
static final int MAP_BUTTON_WIDTH       = 100; // NOTE: change this to by dynamic based on map picture size?
static final int MAP_BUTTON_HEIGHT      = 100;
static final int WORLD_MAP_COLUMNS      = 20;
static final int WORLD_MAP_ROWS         = 20;
static final int NUM_WORLD_AREAS        = WORLD_MAP_COLUMNS * WORLD_MAP_ROWS;
static final int PC_MAP_ICON_W          = 40;
static final int PC_MAP_ICON_H          = 40;
static final int KEY_SCROLL_SPEED       = 12;
static final int EDGE_SCROLL_SPEED      = 4;
static final int WHEEL_SCROLL_SPEED     = 30;

static final int AREA_WIDTH             = (1024 * 10); // 10 screens wide
static final int AREA_HEIGHT            = (768 * 10);  // 10 screens high

static final int CONSOLE_ADJ_WIDTH      = 20;
static final int CONSOLE_MAX_ADJ_HEIGHT = 25;

//************************************************************************
// private methods
//************************************************************************
private static int getWidth(){
    // capture and adjust for screen size
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice(); //this line uses the primary monitor only
    DisplayMode dm = gd.getDisplayMode();
    return dm.getWidth();
} // end getWidth

//************************************************************************
private static int getHeight(){
    // capture and adjust for screen size
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice(); //this line uses the primary monitor only
    DisplayMode dm = gd.getDisplayMode();
    return dm.getHeight();
} // end getHeight

//************************************************************************
} // end class Constants
