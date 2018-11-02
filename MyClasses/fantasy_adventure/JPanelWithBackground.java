package fantasy_adventure;

//************************************************************************
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.io.File;
import java.awt.Graphics;
import javax.swing.event.*;
import java.awt.event.*;

//************************************************************************
class JPanelWithBackground extends    JPanel
                           implements ImageObserver,
                                      MouseInputListener{

//************************************************************************
// static variables

  static final int STRETCH = 0;
  static final int TILE    = 1;

//************************************************************************
// member variables

  String backgroundImage;
  int style;
  int imgWidth;
  int imgHeight;
  int thisWidth;
  int thisHeight;

//************************************************************************
// constructor
//************************************************************************

  public JPanelWithBackground(){
    super();
    style = STRETCH;
    setDoubleBuffered(true);
    backgroundImage = null;
  } // end default Constructor

  public JPanelWithBackground(String imageName, int style){
    super();
    this.style = style;
    setDoubleBuffered(true);
    setBackgroundImage(imageName);
  } // end constructor

//************************************************************************
// public methods
//************************************************************************

  public void setBackgroundImage(String imageName){
//    Popup.createInfoPopup("Setting background: " + imageName);
    if (imageName == null) {backgroundImage = null; return;}
      try{
        backgroundImage = imageName;
        imgWidth  = Res.getImage(backgroundImage).getImage().getWidth(this);
        imgHeight = Res.getImage(backgroundImage).getImage().getHeight(this);
      } // end try

      catch(NullPointerException evt){
        backgroundImage = null;
        imgWidth  = 0;
        imgHeight = 0;
      } // end catch
  } // end setBackgroundImage

//************************************************************************
  public void paintComponent(Graphics g) {

   super.paintComponent(g);

   thisWidth  = (int)this.getPreferredSize().getWidth();
   thisHeight = (int)this.getPreferredSize().getHeight();

   // this version of the method scales the image down or up,
   // according to the size of the container on which it is drawn.

   if (backgroundImage != null){
     if (style == STRETCH){
       g.drawImage(Res.getImage(backgroundImage).getImage(), 0,
                                    0,
                                    thisWidth,
                                    thisHeight,
                                    0,
                                    0,
                                    imgWidth,
                                    imgHeight,
                                    this.getBackground(),
                                    this);
     } // end if style  == STRETCH

     else if (style == TILE){
       // we need to fill this component with the image.
       // we need to have a two-tiered for loop to step across and down the component
       // for each square, we can drawImage at that index.

       for (int i = 1; i < thisWidth - 1; i += imgWidth){
         for (int j = 1; j < thisHeight - 1; j += imgHeight){
           g.drawImage(Res.getImage(backgroundImage).getImage(), i, j, this);
         } // end for j loop
       } // end for i loop

     } // end if style == TILE

   } // end if image != null

  }// end paintComponent

//************************************************************************
  public void mousePressed (MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mouseClicked (MouseEvent e){}
  public void mouseEntered (MouseEvent e){}
  public void mouseExited  (MouseEvent e){}
  public void mouseMoved   (MouseEvent e){}
  public void mouseDragged (MouseEvent e){}
//************************************************************************

} // end class EquipmentPanel
