package fantasy_adventure;

import javax.swing.*;
import java.awt.*;

//************************************************************************
//imports

//************************************************************************
  class Topic extends JLabel{
  // purpose of this class is to link the JLabel with the content.

//************************************************************************
// static declarations
//************************************************************************

   static Topic   tempTopic;
   static boolean changeMade;

//************************************************************************
// instance declarations
//************************************************************************

    private JButton topicLabel;
    private String  content;
    private boolean complete;
    private int     date;

//************************************************************************
    Topic (String title){
      topicLabel = new JButton(title);
      topicLabel.setPreferredSize(new Dimension((int)(Constants.CENTRALPANEL_WIDTH / 3) - 32,
                                                Constants.JOURNAL_TOPIC_HEIGHT));
      topicLabel.setBorder(BorderFactory.createRaisedBevelBorder());
    } // end constructor

//************************************************************************
  String  getTitle()            {return topicLabel.getText();}
  JButton getLabel()            {return topicLabel;}
  String  getContent()          {return content;}
  boolean isComplete()          {return complete;}
  int     getDate()             {return date;}

  void setTitle      (String s) {topicLabel.setText(s);}
  void setContent    (String s) {content = s;}
  void setComplete   (boolean b){complete = b;}
  void setDate       (int d)    {date = d;}

  void appendContent (String s) {
    if (content == null) setContent(s);
    else{
      content += (MyTextIO.newline + s);
    } // end else
  } // end appendContent

//************************************************************************
  static void sortByDate(Topic[] topics){
/*    // purpose of this method is to re-arrange the topic array
    // by the variable date.

    if (topics == null || topics.length < 2) return;

    else{
      // we need to sort the list...
      // we'll assume at this point that the user wants the first item
      // in the list to be the most recent, while the last item will be
      // the earliest, or oldest date.

      // setup the flag
      changeMade = true;

      // so, we need to go through the list
      for (int i = 0; i < topics.length && changeMade = true; i++){
        // set the flag
        changeMade = false;
        // for each item in the list, we want to do the following:
        // capture the reference to the temp slot
        tempTopic = topics[i];

        // check the next item in the list for an earlier date,
        if (tempTopic
        // if so, then put the 2nd one in the 1st one's place,
        // and mark a boolean that we changed something.
        // and repeat that cycle until we're out of items, OR
        // there were no changes that took place.

      } // end for loop

    } // end else - topics > 1
*/
  } // end sortByDate

//************************************************************************
  static void sortByTitle(Topic[] topics){
    // purpose of this method is to re-arrange the topic array
    // by the variable title

    if (topics == null || topics.length < 2) return;

    else{
      // we need to sort the list...

    } // end else - topics > 1

  } // end sortByTitle

//************************************************************************
} // end class Topic
