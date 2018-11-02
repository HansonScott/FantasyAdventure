package fantasy_adventure;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class Questionnaire extends JFrame implements ActionListener,
                                                     KeyListener{

/* The purpose of this class is to present the user with a series of questions or scenarios
 * to determine their desired alignment.  Each question or scenario should have
 * multiple possible answers, to allow for more complex and accurate calculation.
 *
 * For the purpose of coding, each 'question' has an array of 'answers'. (Class below)
 * Answers are simply JButtons with String titles.
 * This 'questionnaire' keeps track of the points from each answer given.
 */

// declarations
static final String QUESTIONNAIRE_FILENAME = FileNames.QUESTIONNAIRE;
static final int    NUM_QUESTIONS      = MyTextIO.getNumLines(QUESTIONNAIRE_FILENAME);
static final int    MAX_LINE_LENGTH        = 20;
static final int    MAX_NUM_ANSWERS        = 10;

Question[] questions;
String [] answers;

int currentQuestion      = 0;  // keeps track of what question we are on. (start with 0).
int numQuestions         = 0;  // will change dynamically based on file read.

int lawfulGoodPoints     = 0;
int lawfulNeutralPoints  = 0;
int lawfulEvilPoints     = 0;
int neutralGoodPoints    = 0;
int trueNeutralPoints    = 0;
int neutralEvilPoints    = 0;
int chaoticGoodPoints    = 0;
int chaoticNeutralPoints = 0;
int chaoticEvilPoints    = 0;

Container content;
JPanel    mainPanel;   // holds titleArea and answerArea
JPanel    buttonPanel; // for 'cancel' and 'start/next/finish'

JTextArea titleArea;   // also becomes 'questionArea'
JPanel    answerArea;  // by having a separate answerArea, we can removeAll() from it
                       // without knowing how many answers there were.

JButton cancelButton;
JButton backButton;
JButton nextButton;    // also acts as the 'start' button and 'finish' button

String result;         // Answer from questionnaire

//****************************************************************
// constructor
//****************************************************************

public Questionnaire(){
 /* The purpose of this constructor is to create a window in which we will
  * ask the user a series of questions and keep track of the responses given
  * to determine their choice of alignment.
  * each question, possible answers, and point values will all be read from a .csv file.
  */

  // setup JFrame properties
  super("Alignment Questionnaire");
  setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT)); // full screen
  setResizable(false);
  setBackground(Color.darkGray);
  content = getContentPane();
  content.setLayout(new BorderLayout());
  setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

  // setup mainPanel
  mainPanel = new JPanel();
  mainPanel.setPreferredSize(new Dimension(512, 568));
  mainPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder((int)(Constants.SCREEN_HEIGHT - 600) / 2,
                                                        (int)(Constants.SCREEN_HEIGHT - 600) / 2,
                                                        (int)(Constants.SCREEN_WIDTH - 800) / 2,
                                                        (int)(Constants.SCREEN_HEIGHT - 600) / 2,
                                                        new ImageIcon(getRandomBorder())),
                        BorderFactory.createRaisedBevelBorder()));
  mainPanel.setLayout(new BorderLayout());

  // setup objects within mainPanel
  titleArea = new JTextArea();
  titleArea.setLineWrap(true);
  titleArea.setWrapStyleWord(true);
  titleArea.setDisabledTextColor(Color.white);
  titleArea.setForeground(Color.white);
  titleArea.setBackground(Color.black);
  titleArea.setBorder(BorderFactory.createMatteBorder(4,15,4,10,Color.black));
  titleArea.setEditable(false);
  titleArea.setRows(10);
  titleArea.setSelectedTextColor(Color.white);
  titleArea.setSelectionColor(Color.black);

  titleArea.append(MyTextIO.newline + "Welcome to the Alignment Questionnaire."      + MyTextIO.newline + MyTextIO.newline);
  titleArea.append("This will walk you through a series of questions and scenarios." + MyTextIO.newline);
  titleArea.append("By answering honestly of how your character would respond,"      + MyTextIO.newline +
                   "you will accumulate points toward a specific alignment."         + MyTextIO.newline +
                   "Remember to think like your character, and choose for them.");

  answerArea = new JPanel();
  answerArea.setPreferredSize(new Dimension(512,350));

  // add objects to mainPanel
  mainPanel.add(titleArea, BorderLayout.NORTH);
  mainPanel.add(answerArea, BorderLayout.CENTER);

  // setup buttonPanel
  buttonPanel = new JPanel();
  buttonPanel.setPreferredSize(new Dimension (512,50));
  buttonPanel.setBackground(Color.lightGray);
  buttonPanel.setLayout(new GridLayout(1,3));

  // setup objects within buttonPanel
  cancelButton = new JButton("Cancel Questionnaire");
  cancelButton.setActionCommand("Cancel");
  cancelButton.addActionListener(this);
  cancelButton.setPreferredSize(new Dimension(250, 50));

  nextButton = new JButton("Start Questionnaire");
  nextButton.setActionCommand("Start");
  nextButton.addActionListener(this);
  nextButton.setPreferredSize(new Dimension(250, 50));

  backButton = new JButton("");
  backButton.setPreferredSize(new Dimension(250, 50));

  // add objects to buttonPanel
  buttonPanel.add(cancelButton);
  buttonPanel.add(backButton);
  buttonPanel.add(nextButton);

  // add titlePanel to this frame
  content.add(mainPanel, BorderLayout.CENTER);
  content.add(buttonPanel, BorderLayout.SOUTH);

} // end constructor

//****************************************************************
// package methods
//****************************************************************

public void keyPressed  (KeyEvent k) {
  if (Constants.debugMode == true){
    Popup.createInfoPopup("Key Pressed: " + k.toString());
  } // end if
} // end keyPressed

public void keyReleased (KeyEvent k) {} // end keyReleased

public void keyTyped    (KeyEvent k) {
  Popup.createInfoPopup("Key Typed captured: " + k.toString());

  if (k.KEY_TYPED == KeyEvent.VK_ENTER){
    Popup.createInfoPopup("You pressed Enter!  Good for you.");
  }  // if enter

} // end keyTyped

public void actionPerformed(ActionEvent e){
  String command = e.getActionCommand();
  if (command == "Cancel"){
    hide();
    dispose();
  } // if command == "Cancel"

  else if (command == "Start"){
    // user has chosen to start the questions, so we need to change the title panel

    /* Here is where we create and show the actual questions.  Do the following:
     *
     * 1) - change the "Start" button to be "Next Question".
     * 2) - change the actionCommand to be "Next".
     * 3) - read a file and create an array of questions (and answers)
     * 4) - load the first question (and answers) into the main area.
     */

    // Show user that you're loading the questions
    answerArea.add(new JLabel("Loading questions..."));
    answerArea.repaint();

    // 1) - change the "Start" button to be "Next Question".
    nextButton.setText("Next Question");

    // 2) - change the actionCommand to be "Next".
    if (currentQuestion == 0)
    nextButton.setActionCommand("Next");

    // 3) - Create an array of questions (with answers) read a file to fill them.
    questions = new Question[NUM_QUESTIONS];
    answers = new String[NUM_QUESTIONS];

    String[] stringArray = MyTextIO.readFile(QUESTIONNAIRE_FILENAME, NUM_QUESTIONS);

    for (int i = 0; stringArray[i] != null   &&
                    stringArray[i] != "null" &&
                    stringArray[i] != "null\t" &&
                    stringArray[i] != "null\n"; i++){
//Popup.createInfoPopup("Creating question at index: " + i);
      questions[i] = new Question(stringArray[i]);
      numQuestions++;
    } // end for loop

    // 4) - load the first question
//Popup.createInfoPopup("Loading question # " + currentQuestion);
    loadNextQuestion(questions[currentQuestion]);

  } // end start

  else if (command == "Back"){
    // first check if we are at the second question, so we know if we need to remove ourself.
    if (currentQuestion == 1){
      backButton.setText("");
      backButton.removeActionListener(this);
      buttonPanel.validate();
    } // end if going to first question

    // user wants to go back one question
    // we need to set currentQuestion -= 1
    currentQuestion--;

    // we need to remove points from the last question's answer.
    removePoints(answers[currentQuestion]);

    // we need to load next question, given the reduced currentQuestion
      loadNextQuestion(questions[currentQuestion]);
      answerArea.validate();

    // if we had finished, but are now returning to change some,
    // then we have to reset the button ActionCommand accordingly.
    if (currentQuestion == numQuestions - 1){
      nextButton.setText("Next Question");
      nextButton.setActionCommand("Next");
    } // end returning from end

  } // end if back button chosen

  else if (command == "Next" && answerChosen() == false){
    // user has chosen to move on, but has not selected an answer
    Popup.createInfoPopup("Please choose an answer before moving on.");
  } // end if Next and not chosen

  else if (command == "Next" && answerChosen() == true){
    // user has chosen to move onto the next question.
    // PRE: the user is looking at a question within the questionnaire
    // We need to capture the value and append the point count,
    // then move on to the next question.

    // capture the value of the choice and increment the point count
    captureChoice();

    if (currentQuestion == 0){
      backButton.setText("Previous Question");
      backButton.setActionCommand("Back");
      backButton.addActionListener(this);
    } // end if second question

    // move on to the next question
    currentQuestion++;
    if (currentQuestion < numQuestions){
      loadNextQuestion(questions[currentQuestion]);
      answerArea.validate();
    } // end if not null

    else{ //currentQuestion > numQuestions
      // user has reached the end of the questionnaire
      // so, capture the highest point value alignment
      // and show the result in the text area.
      // change the next button to be 'finish'
      // change the cancel button to be 'finish' too, since user is done.

      // capture the highest point value alignment
      result = getHighestAlignment();

      // and show the result in the text area.
      titleArea.setText(MyTextIO.newline + "You have finished the Alignment Questionnaire. Your results are: " + MyTextIO.newline);

      titleArea.append(MyTextIO.tab + "Lawful Good: " + MyTextIO.tab + MyTextIO.tab + lawfulGoodPoints     +" Points" + MyTextIO.newline);
      titleArea.append(MyTextIO.tab + "Lawful Neutral: " + MyTextIO.tab + MyTextIO.tab + lawfulNeutralPoints  +" Points" + MyTextIO.newline);
      titleArea.append(MyTextIO.tab + "Lawful Evil: " + MyTextIO.tab + MyTextIO.tab + lawfulEvilPoints     +" Points" + MyTextIO.newline);
      titleArea.append(MyTextIO.tab + "Neutral Good: " + MyTextIO.tab + MyTextIO.tab + neutralGoodPoints    +" Points" + MyTextIO.newline);
      titleArea.append(MyTextIO.tab + "True Neutral: " + MyTextIO.tab + MyTextIO.tab + trueNeutralPoints    +" Points" + MyTextIO.newline);
      titleArea.append(MyTextIO.tab + "Neutral Evil: " + MyTextIO.tab + MyTextIO.tab + neutralEvilPoints    +" Points" + MyTextIO.newline);
      titleArea.append(MyTextIO.tab + "Chaotic Good: " + MyTextIO.tab + MyTextIO.tab + chaoticGoodPoints    +" Points" + MyTextIO.newline);
      titleArea.append(MyTextIO.tab + "Chaotic Neutral: " + MyTextIO.tab + chaoticNeutralPoints +" Points"+MyTextIO.newline);
      titleArea.append(MyTextIO.tab + "Chaotic Evil: " + MyTextIO.tab + MyTextIO.tab + chaoticEvilPoints    +" Points" + MyTextIO.newline + MyTextIO.newline);

      answerArea.removeAll();

      answerArea.add(new JLabel("<html>Based on the answers you gave, your character alignment choice should be:<br><Br><text-align: center>" + result + "</html>"));

      answerArea.repaint();

      // change the next button to be 'finish'
      nextButton.setText("Finish Questionnaire");
      nextButton.setActionCommand("Finish Questionnaire");

      cancelButton.setText("Finish Questionnaire");
    } // end if null

  } // end if Next and answer chosen is true

  else if (command == "answer"){
    // user has clicked on an answer button
    JRadioButton thisAnswer = (JRadioButton)e.getSource();
    unselectAllAnswers(questions[currentQuestion]);
    thisAnswer.setSelected(true);
  } // end answer

  else if (command == "Finish Questionnaire"){}
  else{
    if (Constants.debugMode == true)
    Popup.createErrorPopup("Reached the end of the Questionnaire: ActionPerformed, but the action was uncaptured.");
  }  // end else

} // end actionPerformed

public String getResult() {return result;} // end getResult()
//*********************************************************************************
// private methods
//**********************************************************************************

private void unselectAllAnswers(Question thisQuestion){
  // the purpose of this method is to clear the selection from all the answer buttons.
  for(int i = 0; i < MAX_NUM_ANSWERS; i++){
    try {thisQuestion.getAnswer(i).setSelected(false);}
    catch (NullPointerException e){};
  } // end for loop
} // end unselectAllAnswers

private String getHighestAlignment(){
  // the purpose of this method is to compare all the alignment values
  // and return the name of the highest alignment point value.
  // NOTE: if there is a tie, the FIRST alignment in the tie will be returned.
  //  (This makes getting a Chaotic Evil alignment slightly less likely.)

  // cycle through the alignments, getting the highest value.
  int highestAlignment = 0;
  String result = "unknown";

  if (lawfulGoodPoints > highestAlignment){
    highestAlignment = lawfulGoodPoints;
    result = "Lawful Good";}

  if (lawfulNeutralPoints > highestAlignment){
    highestAlignment = lawfulNeutralPoints;
    result = "Lawful Neutral";}

  if (lawfulEvilPoints > highestAlignment){
    highestAlignment = lawfulEvilPoints;
    result = "Lawful Evil";}

  if (neutralGoodPoints > highestAlignment){
    highestAlignment = neutralGoodPoints;
    result = "Neutral Good";}

  if (trueNeutralPoints > highestAlignment){
    highestAlignment = trueNeutralPoints;
    result = "True Neutral";}

  if (neutralEvilPoints > highestAlignment){
    highestAlignment = neutralEvilPoints;
    result = "Neutral Evil";}

  if (chaoticGoodPoints > highestAlignment){
    highestAlignment = chaoticGoodPoints;
    result = "Chaotic Good";}

  if (chaoticNeutralPoints > highestAlignment){
    highestAlignment = chaoticNeutralPoints;
    result = "Chaotic Neutral";}

  if (chaoticEvilPoints > highestAlignment){
    highestAlignment = chaoticEvilPoints;
    result = "Chaotic Evil";}

  return result;

} // end getHighestAlignment()

private boolean answerChosen(){
  // the purpose of this method is to find out if the user has made a choice from the possible answers.
  // At this point, we do not care what the choice is, but that one as been made.
  if (getSelectedAnswer(questions[currentQuestion]) != null) return true;
  else return false;
} // end answerChosen()

private void captureChoice(){
  // the purpose of this method is to find the user's answer from the current question
  // and allocate the point to the correct alignment.
  // PRE: this assumes there is one and only one JRadioButton selected at this time.
  String s = questions[currentQuestion].
      getAlignmentResult(getSelectedAnswer(questions[currentQuestion]));

  answers[currentQuestion] = s;

  assignPoints(s);

  // method is void.

} // end captureChoice

//*****************************************************************************

private void assignPoints(String s){
  // the purpose of this method is to analyze the passed string and
  // increment the associated alignment variable.

  if (s.equalsIgnoreCase("lawful")){
    lawfulGoodPoints    += 1;
    lawfulNeutralPoints += 1;
    lawfulEvilPoints    += 1;
  } // end lawful

  else if (s.equalsIgnoreCase("good")){
    lawfulGoodPoints     += 1;
    neutralGoodPoints    += 1;
    chaoticGoodPoints    += 1;
  } // end good

  else if (s.equalsIgnoreCase("neutral")){
    lawfulNeutralPoints  += 1;
    neutralGoodPoints    += 1;
    trueNeutralPoints    += 1;
    neutralEvilPoints    += 1;
    chaoticNeutralPoints += 1;
  } // end neutral

  else if (s.equalsIgnoreCase("chaotic")){
    chaoticGoodPoints    += 1;
    chaoticNeutralPoints += 1;
    chaoticEvilPoints    += 1;
  } // end chaotic

  else if (s.equalsIgnoreCase("evil")){
    lawfulEvilPoints     += 1;
    neutralEvilPoints    += 1;
    chaoticEvilPoints    += 1;
  } // end evil

  else if (s.equalsIgnoreCase("lawful good")){
    lawfulGoodPoints     += 1;
  } // end lawful good

  else if (s.equalsIgnoreCase("lawful neutral")){
    lawfulNeutralPoints     += 1;
  } // end lawful neutral

  else if (s.equalsIgnoreCase("lawful evil")){
    lawfulEvilPoints     += 1;
  } // end lawful evil

  else if (s.equalsIgnoreCase("neutral good")){
    neutralGoodPoints    += 1;
  } // end neutral good

  else if (s.equalsIgnoreCase("true neutral")){
    trueNeutralPoints    += 1;
  } // end true neutral

  else if (s.equalsIgnoreCase("neutral evil")){
    neutralEvilPoints    += 1;
  } // end neutral evil

  else if (s.equalsIgnoreCase("chaotic good")){
    chaoticGoodPoints    += 1;
  } // end chaotic good

  else if (s.equalsIgnoreCase("chaotic neutral")){
    chaoticNeutralPoints += 1;
  } // end chaotic neutral

  else if (s.equalsIgnoreCase("chaotic evil")){
    chaoticEvilPoints    += 1;
  } // end chaotic evil

  else
    Popup.createErrorPopup("I don't know where to assing points to the phrase: '" + s + "'");

} // end assignPoints()

//***************************************************************************
private void removePoints(String s){
    // the purpose of this method is to analyze the passed string and
    // increment the associated alignment variable.

    if (s.equalsIgnoreCase("lawful")){
      lawfulGoodPoints    -= 1;
      lawfulNeutralPoints -= 1;
      lawfulEvilPoints    -= 1;
    } // end lawful

    else if (s.equalsIgnoreCase("good")){
      lawfulGoodPoints     -= 1;
      neutralGoodPoints    -= 1;
      chaoticGoodPoints    -= 1;
    } // end good

    else if (s.equalsIgnoreCase("neutral")){
      lawfulNeutralPoints  -= 1;
      neutralGoodPoints    -= 1;
      trueNeutralPoints    -= 1;
      neutralEvilPoints    -= 1;
      chaoticNeutralPoints -= 1;
    } // end neutral

    else if (s.equalsIgnoreCase("chaotic")){
      chaoticGoodPoints    -= 1;
      chaoticNeutralPoints -= 1;
      chaoticEvilPoints    -= 1;
    } // end chaotic

    else if (s.equalsIgnoreCase("evil")){
      lawfulEvilPoints     -= 1;
      neutralEvilPoints    -= 1;
      chaoticEvilPoints    -= 1;
    } // end evil

    else if (s.equalsIgnoreCase("lawful good")){
      lawfulGoodPoints     -= 1;
    } // end lawful good

    else if (s.equalsIgnoreCase("lawful neutral")){
      lawfulNeutralPoints    -= 1;
    } // end lawful neutral

    else if (s.equalsIgnoreCase("lawful evil")){
      lawfulEvilPoints     -= 1;
    } // end lawful evil

    else if (s.equalsIgnoreCase("neutral good")){
      neutralGoodPoints    -= 1;
    } // end neutral good

    else if (s.equalsIgnoreCase("true neutral")){
      trueNeutralPoints    -= 1;
    } // end true neutral

    else if (s.equalsIgnoreCase("neutral evil")){
      neutralEvilPoints    -= 1;
    } // end neutral evil

    else if (s.equalsIgnoreCase("chaotic good")){
      chaoticGoodPoints    -= 1;
    } // end chaotic good

    else if (s.equalsIgnoreCase("chaotic neutral")){
      chaoticNeutralPoints -= 1;
    } // end chaotic neutral

    else if (s.equalsIgnoreCase("chaotic evil")){
      chaoticEvilPoints    -= 1;
    } // end chaotic evil

    else
      Popup.createErrorPopup("I don't know where to remove points from the phrase: '" + s + "'");

  } // end assignPoints()

//***************************************************************************
private void  loadNextQuestion(Question thisQuestion){
  // the purpose of this method is to clear the main area from everything and
  // load the next question as well as the series of answers to that question

  // set title for this question
  setTitle("Alignment Questionnaire, Question: " +
           (currentQuestion + 1) + " of " + numQuestions); // +1 because we start with 0.

  // set question area with new question
  titleArea.setText(thisQuestion.getQuestionText());

  // clear answerArea from the previous question
  answerArea.removeAll();

  // load buttons from the thisQuestion.getAnswer(i);
    for(int i = 0; i < MAX_NUM_ANSWERS && thisQuestion.getAnswer(i) != null; i++){

      thisQuestion.getAnswer(i).addActionListener(this);
      thisQuestion.getAnswer(i).addKeyListener(this);

      // add to panel
      answerArea.add(thisQuestion.getAnswer(i));
    } // end for loop

    // show the new answers in the answerArea
    answerArea.repaint();

  } // end loadNextQuestion

private JRadioButton getSelectedAnswer(Question thisQuestion){
  // the purpose of this method is to cycle through all the answers from the question passed
  // and return the JRadioButton that is selected.

  JRadioButton thisAnswer = thisQuestion.getAnswer(0);  // we can assume at least one answer
  for (int i = 0; i < MAX_NUM_ANSWERS; i++){
    try{
      thisAnswer = thisQuestion.getAnswer(i);
      if (thisAnswer.isSelected() == true) {
        return thisAnswer;
      } // if isSelected()
    } // end try
    catch (NullPointerException e){}
  } // end for loop
  // if we get to here, then there was no answer chosen, which is NOT a prerequisite, so return null.
  return null;

} // end getSelectedAnswer

private String getRandomBorder(){
  File f = new File(Constants.INSTALL_DIRECTORY + "/Images/Background");
  String[] s = f.list();
  int picNumber = Roll.D(s.length) - 1;
  return new String("images/Background/" + s[picNumber]);
} // end getRandomBorder

} // end class Questionnaire

//**********************************************************************

class Question extends JPanel{
/* The purpose of this class is to create a question or scenario
 * for the user to evaluate and respond by choosing one of the given answers listed below.
 *
 * Each question consists of a String which holds the question or scenario
 * followed by an array of JRadioButtons that act as the answer choices
 * and an array of Strings that correspond to give the resulting alignment points.
 */

//declarations

static final int MAX_NUM_ANSWERS_PER_QUESTION = 10;
static final int MAX_CHARS_PER_ANSWER_LINE    = 60;
private String         questionText;
private JRadioButton[] answers;
private String[]       alignmentRelation;  // this represents the String of which alignment
                                           // gets a point if the corresponding answers[] is chosen

//*******************************************************************
// constructor
//*******************************************************************

Question(String workingString){
  /* we need to take the string and pull it apart to find the pieces.
   * The parameter String workingString consists of:
   * 1)  - the question or scenario to be read
   *
   * 2a) - an answer sentence or phrase
   * 2b) - a word or phrase that is the alignment getting a point if this answer is chosen
   *
   * 3a) - (this cycle continues until there are no more answer strings.)
   * 3b) -
   */
  // so, step one is to find the first 'phrase' (tab delimited)
  questionText           = MyTextIO.getNextPhrase(workingString);
  answers                = new JRadioButton[(MAX_NUM_ANSWERS_PER_QUESTION)];
  alignmentRelation      = new String      [(MAX_NUM_ANSWERS_PER_QUESTION)];

  String remainingString = MyTextIO.trimPhrase(workingString);
  workingString = remainingString;

  // now, with the rest of the phrase, loop unil we are done (str == null || str == "null").
  try{
    for (int i = 0; (MyTextIO.getNextPhrase(workingString) != null &&
                    !MyTextIO.getNextPhrase(workingString).equalsIgnoreCase ("null") &&
                    !MyTextIO.getNextPhrase(workingString).equalsIgnoreCase ("null\t") &&
                    !MyTextIO.getNextPhrase(workingString).equalsIgnoreCase ("null\n")); i++){

      // within each loop, capture two things: 1) answer:
// Popup.createInfoPopup("Creating a JRadioButton with the string: \n" + MyTextIO.getNextPhrase(workingString));
      answers[i] = new JRadioButton(MyTextIO.getNextPhrase(workingString));
      answers[i].setPreferredSize(new Dimension(510, 20));
      answers[i].setMnemonic(KeyEvent.VK_ENTER);
      answers[i].setActionCommand("answer");

      if (answers[i].getText().length() > MAX_CHARS_PER_ANSWER_LINE){
        answers[i].setText(MyTextIO.createMultiLine(answers[i].getText(), MAX_CHARS_PER_ANSWER_LINE));
        answers[i].setPreferredSize(new Dimension(510,50));
      }// end if too long

      remainingString = MyTextIO.trimPhrase(workingString);
      workingString = remainingString;

      // and 2) the alignment point.
      alignmentRelation[i] = MyTextIO.getNextPhrase(workingString);
      remainingString = MyTextIO.trimPhrase(workingString);
      workingString = remainingString;
    } // end for loop
} // end try

  catch(NullPointerException e){
Popup.createErrorPopup("Sorry, N.P.E. while loading answers to this question.");
  } // end catch NPE

  catch(ArrayIndexOutOfBoundsException a){
Popup.createErrorPopup("Sorry, ArrayOutOfBoundsException while loading answers to question: " + questionText);
  } // end catch AOOB

} // end constructor(str)

//*****************************************************************************
// package methods
//*****************************************************************************

String       getQuestionText     ()      {return questionText;}
String       getRelatedAlignment (int i) {return alignmentRelation[i];}

void         setAnswer           (int i, JRadioButton r) {answers[i] = r;}

JRadioButton getAnswer (int i){
  try{return answers[i];}
  catch(ArrayIndexOutOfBoundsException e){
    Popup.createErrorPopup("ArrayIndexOutOfBounds when attempting to get this question's answer at [" + i + "]");
    return null;
  } // end catch
} // end getAnswer

String getAlignmentResult  (JRadioButton b){
  // purpose of this method is to find the radio button that was selected,
  // and return the alignment relation.
  // If the button does not match one from the list, then return null

  // cycle through the available buttons and return value if match found.

  for (int i = 0; answers[i] != null; i++){
    if (answers[i] == b){
//      Popup.createInfoPopup("Answer found, point given to: " + alignmentRelation[i]);
      return alignmentRelation[i];
    } // end if
  } // end for loop

  // we have gone through the list of answers, and found no match
  Popup.createWarningPopup("No match found when assigning a point from the answer chosen.");
  return null;

} // end getAlignmentResult

int getNumAnswers() {
  int numAnswers;
  for (numAnswers = 0; numAnswers < (MAX_NUM_ANSWERS_PER_QUESTION - 1) &&
                       answers[numAnswers] != null; numAnswers++){
  } // end for loop
  return numAnswers;
} // end getNumAnswers()

} // end class Question
