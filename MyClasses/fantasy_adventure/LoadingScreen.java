package fantasy_adventure;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Dimension;

//*********************************************************************
public class LoadingScreen extends JFrame {

//*********************************************************************
// static declarations

//*********************************************************************
// instance declarations

    JPanelWithBackground panel;
    JLabel               loadingTextLabel;

//*********************************************************************
  public LoadingScreen(String s) {
    super(s);

    // purpose of this method is to create a Loading Screen that will be used during the game.
    setSize(Constants.SCREEN_WIDTH,
            Constants.SCREEN_HEIGHT);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    Container content = getContentPane();

    // setup content
    content.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

    // now create and add internal objects
    panel = new JPanelWithBackground(FileNames.getLoadingPic(),
                                     JPanelWithBackground.TILE);

    panel.setPreferredSize(new Dimension(Res.getImage(panel.backgroundImage).getImage().getWidth(panel),
                                         Res.getImage(panel.backgroundImage).getImage().getHeight(panel)));
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

    loadingTextLabel = new JLabel("Loading, please wait.");

    // add internal parts to Panel's content.
    panel.add(loadingTextLabel);
    content.add(panel);

  } // end constructor

//*********************************************************************
// public methods
//*********************************************************************

  public void changePic(){
    // purpose of this method is to update the background picture.
    panel.setBackgroundImage(FileNames.getLoadingPic());
  } // end changePic

//*********************************************************************
// private methods
//*********************************************************************

//*********************************************************************
// static methods
//*********************************************************************

} // end class
