package fantasy_adventure;

import java.awt.*;
import javax.swing.*;

// declare variables and objects

public class Introduction extends JFrame {

private JLabel titleLabel, blankLabel, clickLabel;
private Container frameContent;

  // constructor

  public Introduction (){

    super("Fantasy Adventure - Introduction");
    setSize(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
    setUndecorated(true);

    frameContent = getContentPane();
    frameContent.setLayout(new FlowLayout(FlowLayout.CENTER,1100,10));

    titleLabel   = new JLabel (new ImageIcon("images/title.jpg"));
    blankLabel = new JLabel(" ");
    int buttonHeight = (int)(Constants.SCREEN_HEIGHT / 10);
    blankLabel.setPreferredSize(new Dimension(50, buttonHeight));
    clickLabel = new JLabel("Click on the screen to continue.");

  // add components to Intro frame:

    frameContent.add(titleLabel);
    frameContent.add(blankLabel);
    frameContent.add(clickLabel);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

  } // end constructor

  // public methods

 public Container getFrameContent(){return frameContent;}

  // private methods

} // end class Introduction

