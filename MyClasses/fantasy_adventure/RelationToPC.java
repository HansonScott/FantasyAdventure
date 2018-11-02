package fantasy_adventure;

import java.io.Serializable;



class RelationToPC implements Serializable{

  final static int UNKNOWN = 0; // also used for self?
  final static int ENEMY   = 1;
  final static int NEUTRAL = 2;
  final static int ALLY    = 3;
  final static int PARTY   = 4;

  int status;

//***************************************************************************
// constructors
//***************************************************************************

//***************************************************************************
//public methods
//***************************************************************************

//***************************************************************************

//***************************************************************************
} // end class RelationToPC

