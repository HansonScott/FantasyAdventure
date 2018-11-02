package fantasy_adventure;

//*****************************************************************************
// imports
//*****************************************************************************
import java.io.*;
import java.util.*;
import java.awt.*;

//*****************************************************************************
public class ObjectCloner implements Serializable{
 // so that nobody can accidentally create an ObjectCloner object
 private ObjectCloner(){} // end constructor

//*****************************************************************************
// static methods
//*****************************************************************************
 static public Object deepCopy(Object oldObj){
   // returns a deep copy of an object`
   ObjectOutputStream oos = null;
   ObjectInputStream ois = null;
   try{
     ByteArrayOutputStream bos = new ByteArrayOutputStream();
     oos = new ObjectOutputStream(bos);

     // serialize and pass the object
     oos.writeObject(oldObj);
     oos.flush();
     ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
     ois = new ObjectInputStream(bin);

     // return the new object
     return ois.readObject();
   } // end try

   catch(NotSerializableException ne){
     Popup.createErrorPopup("NotSerializableExeption thrown: " + ne.getMessage());
     return null;
   } // end catch

   catch(IOException e){
     Popup.createErrorPopup("IO Exception in ObjectCloner");
     return null;
   } // end catch
   catch(ClassNotFoundException e){
     Popup.createErrorPopup("Class not found exception in ObjectCloner");
     return null;
   } // end classNotFoundException
   finally{
     try{
       oos.close();
       ois.close();
     } // end try
     catch(IOException e){}
   } // end finally
 } // end deepCopy
//*****************************************************************************
} // end class ObjectCloner
