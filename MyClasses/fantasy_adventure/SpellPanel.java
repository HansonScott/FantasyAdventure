package fantasy_adventure;

// Imports******************************************************************
import java.awt.*;       // supports basic GUI components
import java.awt.event.*; // supports listeners
import javax.swing.*;    // supports swing GUI components


public class SpellPanel extends JTabbedPane {

 /* Purpose of this class is to show the information relating to all the spheres of magic
  * both that the character has and can attain.  The specifics for each sphere will be
  * loaded into its own tab, but this is the general container.
  */

//********************************************************************
// static variables and objects

  static String summaryTitle      = "Summary";
  static String fireSphereTitle   = "Fire";
  static String energySphereTitle = "Energy";
  static String airSphereTitle    = "Air";
  static String lifeSphereTitle   = "Life";
  static String waterSphereTitle  = "Water";
  static String natureSphereTitle = "Nature";
  static String earthSphereTitle  = "Earth";
  static String deathSphereTitle  = "Death";
//********************************************************************
// instance variables and objects

   SpellSummaryTab spellSummaryTab;
   SphereTab       fireTab;
   SphereTab       energyTab;
   SphereTab       airTab;
   SphereTab       lifeTab;
   SphereTab       waterTab;
   SphereTab       natureTab;
   SphereTab       earthTab;
   SphereTab       deathTab;


//********************************************************************
// constructor
//********************************************************************

public SpellPanel(){
  super();

  setPreferredSize(new Dimension(Constants.CENTRALPANEL_WIDTH,
                                 Constants.CENTRALPANEL_HEIGHT));

   // first, setup the tabbed panes
   spellSummaryTab = new SpellSummaryTab(summaryTitle);
   fireTab         = new SphereTab(fireSphereTitle);
   energyTab       = new SphereTab(energySphereTitle);
   airTab          = new SphereTab(airSphereTitle);
   lifeTab         = new SphereTab(lifeSphereTitle);
   waterTab        = new SphereTab(waterSphereTitle);
   natureTab       = new SphereTab(natureSphereTitle);
   earthTab        = new SphereTab(earthSphereTitle);
   deathTab        = new SphereTab(deathSphereTitle);

   // add details to each tabbed pane

   // gameplay details

   // feedback details

   // graphics details

   // audio details


     // add panel to tabbedPane
    addTab(summaryTitle,       spellSummaryTab);
    addTab(fireSphereTitle,    fireTab);
    addTab(energySphereTitle,  energyTab);
    addTab(airSphereTitle,     airTab);
    addTab(lifeSphereTitle,    lifeTab);
    addTab(waterSphereTitle,   waterTab);
    addTab(natureSphereTitle,  natureTab);
    addTab(earthSphereTitle,   earthTab);
    addTab(deathSphereTitle,   deathTab);

   } // end constructor

//********************************************************************
// public methods
//********************************************************************

   public void actionPerformed(ActionEvent e){
     String command = e.getActionCommand();

   } // end actionPerformed

//********************************************************************
// private methods
//********************************************************************

} // end class
