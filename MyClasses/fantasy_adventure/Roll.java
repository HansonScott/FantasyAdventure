package fantasy_adventure;

import java.io.Serializable;

class Roll implements Serializable{
  /* the purpose of this class is to provide static methods
   * for calling dice rolling related actions.
   *
   * These are used all throughout the game, so they are very functional.
   */

  static int D2()  {return D(2);}   // end D2
  static int D3()  {return D(3);}   // end D3
  static int D4()  {return D(4);}   // end D4
  static int D5()  {return D(5);}   // end D5
  static int D6()  {return D(6);}   // end D6
  static int D8()  {return D(8);}   // end D8
  static int D10() {return D(10);}  // end D10
  static int D12() {return D(12);}  // end D12
  static int D15() {return D(15);}  // end D15
  static int D20() {return D(20);}  // end D20
  static int D50() {return D(50);}  // end D50
  static int D100(){return D(100);} // end D100

  static int xDy (int x, int y){
    int result = 0;
    for (int i = 1; i <= y; i++){
      result += D(x);
    } // end for loop
    return result;
  } // end xDy(x,y)

  static int D(int x){
    return ((int)((Math.random() * x) + 1));
  } // end d(x)

  static int style4D6less1(){
    int tempDie1 = D(6);
    int tempDie2 = D(6);
    int tempDie3 = D(6);
    int tempDie4 = D(6);

  // now, find out what is the lowest die value:

    int tempLowest = tempDie1;

    if (tempLowest > tempDie2) tempLowest = tempDie2;
    if (tempLowest > tempDie3) tempLowest = tempDie3;
    if (tempLowest > tempDie4) tempLowest = tempDie4;

    // POST: tempLowest = lowest value of the four die.
  // return sum of all dice less tempLowest.
    return tempDie1 + tempDie2 + tempDie3 + tempDie4 - tempLowest;

  } // end 4D6less1()

} // end class Roll
