package fantasy_adventure;

//************************************************************************
//imports

import java.awt.*;
import javax.swing.*;

//************************************************************************

public class ColorChooserButton extends JButton{

  /* The purpose of this class is to create a button that shows
   * the currently selected playerCharacter color choices, and
   * allows the user to click on the button to select the color
   * of their choice for this particular character aspect.
   */

//************************************************************************
// static declarations
//************************************************************************

static int buttonWidth = 80;
static int buttonHeight = 20;

static int colorSwatchWidth = 25;
static int colorSwatchHeight = 25;

//************************************************************************
// member declarations
//************************************************************************

// every ColorChoosingButton has:

private JLabel        label;
private String        description;
        JPanel        colorSwatch;
private CGenScreen    thisWindow;
private CharInfoPanel listener;
private LivingObject  character;

//************************************************************************
// constructors
//************************************************************************

  public ColorChooserButton (String s, CGenScreen window) {
    // the purpose of this constructor is to create the button and setup
    // the components to their defaults.

    this(s);

    thisWindow = window;
    this.addActionListener(window);

  } // end constructor

  public ColorChooserButton (String s, CharInfoPanel listener, LivingObject linkedChar){

    this(s);

    this.listener = listener;
    character = linkedChar;
    this.addActionListener(listener);

  } // end constructor

  public ColorChooserButton (String s){

    super();

      // setup button
      setPreferredSize(new Dimension(buttonWidth, buttonHeight));
      setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
      setBorder(BorderFactory.createRaisedBevelBorder());

      // setup label properties
      label = new JLabel(s);
      label.setHorizontalAlignment(CENTER);
      label.setVerticalTextPosition(CENTER);
//    label.setPreferredSize(new Dimension(buttonWidth, 18));
      // setup colorSwatch properties
      colorSwatch = new JPanel();
      colorSwatch.setSize(new Dimension(colorSwatchWidth, colorSwatchHeight));
      colorSwatch.setBorder(BorderFactory.createLoweredBevelBorder());
      colorSwatch.setBackground(ColorChooserButton.getRandomColor());

      // add components to button
      this.add(label);
      this.add(colorSwatch);
      this.setActionCommand(s);

  } // end constructor(String)

//************************************************************************
// public methods
//************************************************************************

  public Color getColor() {return colorSwatch.getBackground();} // end getColor
  public String getDesc() {return description;}
  public void setDesc(String s) {description = s;}

  public void  setColor(Color c) {
    colorSwatch.setBackground(c);
    repaint();
  }    // end setColor

  public String getTitle() {return label.getText();} // end getTitle

//************************************************************************
// package methods
//************************************************************************
    void showColorChooser(JColorChooser colorChooser){
      // purpose of this method is to handle the changing of the color
      // within the ColorChooserButton

      // first make sure the color chooser has our color selected
      // to begin with.
//      colorChooser.setColor(this.getColor());


      // now, use the static method to do the work
      Color choice = colorChooser.showDialog(this,
                                             this.label.getText(),
                                             this.getColor());

      if (thisWindow != null){ // if we are in the CGenScreen, then we can get the character.
        character = thisWindow.getCharacter();}

      if (listener != null){ // if we are in the CharInfoPanel, then we can get the character.
        character = listener.character;
      }

      if (choice != null){
        setColor(choice);

        if (getTitle() == "Skin Color"){
          character.setSkinColor(choice);
        } // end skin
        else if (getTitle() == "Hair Color"){
          character.setHairColor(choice);
        } // end skin
        else if (getTitle() == "Major Color"){
          character.setMajorColor(choice);
        } // end skin
        else if (getTitle() == "Minor Color"){
          character.setMinorColor(choice);
        } // end skin
        else{}
      } // end if != null

      else {
        if (Constants.debugMode)
        Popup.createInfoPopup("Color Chooser returned null, so no color change.");
      } // end else
    } // end showColorChooser()



//************************************************************************
// private methods
//************************************************************************

//************************************************************************
// static methods
//***********************************************************************
  public static Color getRandomColor(){
    return new Color(Roll.D(255), Roll.D(255), Roll.D(255));
  } // end getRandomColor()

//************************************************************************
} // end class colorChoosingButton
//************************************************************************
