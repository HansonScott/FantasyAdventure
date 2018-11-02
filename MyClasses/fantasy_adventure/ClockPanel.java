package fantasy_adventure;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class ClockPanel extends    JPanel
                        implements MouseListener,
                                   Runnable{

/* the purpose of this class is to create and maintain the game clock.
 * this has visual components that show at the bottom of the navPanel,
 * as well as variables that keep track of timing and length of time in
 * the game and for the user as well.
 */

//**********************************************************************
// static declarations
//**********************************************************************

  static boolean    gameIsPaused = false;

  static double currentTime = 2000; // in total minutes (1440 = 1 day) (24 x 60)
  static String clockText;
  static String ampm;
  static int    days;
  static int    hrs;

  static ClockPanel thisClock;
  static Timer      clockTimer;
  static TimerTask  timerTask;
  static int        delay       = 60; // milliseconds per game minute. (10x compression w/ 6000))
  static final int  clockWidth  = Constants.NAVPANEL_BUTTON_WIDTH;
  static final int  clockHeight = clockWidth; // this assures the clock is square!

  static final double clockMargin = 2;
  static final double SunMoonSize = Res.getImage(FileNames.CLOCK_SUN).getImage().getWidth(null);

  static final double clockMean   = ((Constants.NAVPANEL_BUTTON_WIDTH
                                      - (3 * clockMargin)
                                      - SunMoonSize)
                                     / 2);

  static private int sunX = 0;
  static private int sunY = 0;
  static private int moonX = 0;
  static private int moonY = 0;

//**********************************************************************
// instance declarations
//**********************************************************************

//**********************************************************************
// constructor
//**********************************************************************
  public ClockPanel() {
    super();

    setPreferredSize(new Dimension(clockWidth,
                                   clockHeight));
    addMouseListener(this);
    updateClock();
  } // end constructor

//**********************************************************************
// public/package methods
//**********************************************************************
  public void run(){
    // this is called as the new thread is started.

    if (clockTimer == null){
      clockTimer = new Timer();
      timerTask = new TimerTask(){ // start internal class!
        public void run(){
          // as long as the game is not paused, it should update the
          // clock from the time passing.
          // if the game is paused, then don't.

          if (!gameIsPaused){
            // transfer one game minute to the game clock
            currentTime++;

            // reduce processing time by only updating the clock every four minutes
            // affects the smoothness of dawn/sunset, by skipping a few increments.
            if (currentTime % 4 == 0){
              ClockPanel.thisClock.updateClock();
            } // end if time is even
          } // end if not paused
        } // end internal timerTask run
      }; // end internal, anonymous class

      clockTimer.scheduleAtFixedRate(timerTask, 0, delay);
    } // end if null
  } // end run

//**********************************************************************
  public void mousePressed(MouseEvent me){}

//**********************************************************************
  public void mouseReleased(MouseEvent me){}

//**********************************************************************
  public void mouseClicked(MouseEvent me){
    // user has clicked on the clock, toggle pause.
    togglePause();
  } // end mouseClicked

//**********************************************************************
  public void mouseEntered(MouseEvent me){
    // user has hovered over the clock, show date, time.
  } // end mouseEntered

//**********************************************************************
  public void mouseExited(MouseEvent me){
    // user has moved away from the clock, hide date, time.
  } // end mouseExited

//**********************************************************************
  public void paintComponent(Graphics g){
    super.paintComponent(g); // this paints the background

    // paint the sun and moon, based on the game time.
    g.drawImage(Res.getImage(FileNames.CLOCK_SUN).getImage(), sunX, sunY, null);
    g.drawImage(Res.getImage(FileNames.CLOCK_MOON).getImage(), moonX, moonY, null);

    // Paint the foreground (horizon), using FileNames.CLOCK_FOREGROUND
    g.drawImage(Res.getImage(FileNames.CLOCK_FOREGROUND).getImage(), 0, 0, null);

    // Paint any weather over all else
    /* if (currentWeather != Weather.CLEAR){
         Weather.drawClockWeather(g);
       } // end if weather
     */

    // if time is paused, then type "Paused" in square
    if (gameIsPaused){
      g.setColor(Color.RED);
      g.drawString("Paused", 15, 60);
    } // end if paused

  } // end paintComponent

//**********************************************************************
  void togglePause(){
    gameIsPaused = !gameIsPaused;
    this.repaint();
  } // end togglePause()

//**********************************************************************
// static methods
//**********************************************************************
  public static ClockPanel makeNewClockPanel(){
    if (thisClock == null)
      thisClock = new ClockPanel();
    return thisClock;
  } // end static constructor

//**********************************************************************
  public void updateClock(){
    // purpose of this method is to combine the calculations
    // and call repaint to the text label and visual clock

//    Popup.createInfoPopup("Clock update: current time: " + currentTime);
    refigureSunAndMoonLocations();
    refigureBackgroundColor();
    resetClockText();
  } // end updateClock

//**********************************************************************
// private methods
//**********************************************************************
  private void refigureSunAndMoonLocations(){
    // purpose of this method is to use the current game time
    // and calculate the correct position for the sun and the moon.
    // this assumes a current time of hours, with minutes as a decimal portion of hours.

//    Popup.createInfoPopup("refiguring sun and moon with current time: " + currentTime);
      sunX = (int)((((Math.cos(((currentTime - 360)/720) * Math.PI)) + 1 ) * clockMean) + clockMargin);
      sunY = (int)((((-Math.sin(((currentTime - 360)/720) * Math.PI)) + 1 ) * clockMean) + clockMargin);

      moonX = (int)((((Math.cos(((currentTime + 360)/720) * Math.PI)) + 1 ) * clockMean) + clockMargin);
      moonY = (int)((((-Math.sin(((currentTime + 360)/720) * Math.PI)) + 1 ) * clockMean) + clockMargin);

//    Popup.createInfoPopup("Sun: " + sunX + ", " + sunY + MyTextIO.newline +
//                          "Moon: " + moonX + ", " + moonY);
    if (thisClock != null)
      thisClock.repaint();

  } // end refigureSunAndMoonLocations

//**********************************************************************
  private void refigureBackgroundColor(){
    // purpose of this method is to update the background color of the clock
    // to reflect the time of day.
    // The color gradient moves from light to dark blue.

    int timeOfDay = (int)(currentTime % 1440);

    int redComponent;
    int greenComponent;
    int blueComponent;

    redComponent   = (int)(Math.min(Math.max(((-2*  (Math.abs(timeOfDay - 720)) + 960     )), 0), 240));
    greenComponent = (int)(Math.min(Math.max(((-2*  (Math.abs(timeOfDay - 720)) + 960     )), 0), 240));
    blueComponent  = (int)(Math.min(Math.max(((-1.5*(Math.abs(timeOfDay - 720)) + 720 + 75)), 75), 255));

    this.setBackground(new Color(redComponent,
                                 greenComponent,
                                 blueComponent));

  } // end refigureBackgroundColor

//**********************************************************************
  private void resetClockText(){
    // purpose of this method is to set the tool tip display
    // with the correct time.

    // currentTime variable is in total minutes.
    synchronized (this){
      days =(int)  (currentTime / 1440);         // 1440 minutes == 1 day
      hrs  =(int)(((currentTime % 1440)) / 60);

      if (hrs == 0){
        setToolTipText("<html><c>Day: " + days + "<br>"
                       + "midnight" + "</c></html>");
      } // midnight

      else if(hrs == 12){
        setToolTipText("<html><c>Day: " + days + "<br>"
                       + "noon" + "</c></html>");
      } // noon

      else { // other times during the day

        if (hrs > 12){
          ampm = "pm";
          hrs -= 12;
        } // afternoon
        else{ // hrs < 12
          ampm = "am";
        } // end else am

        setToolTipText("<html><c>Day: " + days + "<br>"
                       + hrs + " " + ampm + "</c></html>");
      }
    } // end synchronized
  } // end setClockText()

//**********************************************************************

} // end class
