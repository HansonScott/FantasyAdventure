package fantasy_adventure;

//imports

//************************************************************************************
class Activator extends NonLivingObject{
  /* Purpose is to define what an activator is, and how it works.
   * An activator is a transparent object layered over another object
   * that provides interaction with the user.
   * for all living objects, this will be the selectable portion of the character
   * that, when selected, will result in feedback information and interaction
   * with the user.  This will be strongly integrated with a mouse listener, as
   * the selection process will trigger the commandButtons to change as well as the
   * quick stats pane to show details from the selected living object.
   *
   * Activators can also be simple interative points, such as a chest.
   * when the character 'uses' the activator on a static object, then it triggers
   * a unique script that will interact in a unique way.
   * This is the spawn point for traps, as the character finds traps, they can
   * interact with the trap in trying to disarm the trap, or activate the trap unknowningly.
   *
   * Activators can also be things like doors and map edges that will cause the
   * map or other container to adjust its own properties.  Trigger events like wheels or
   * levers that will cause a reaction would also fall under the category of activators.
   */

//************************************************************************************
// declarations
//************************************************************************************

//************************************************************************************
// constructors
//************************************************************************************

  Activator(){

    // initialize objects and variables

   } // end constructor()

//************************************************************************************
// public methods
//************************************************************************************

//************************************************************************************
// private methods
//************************************************************************************

} // end class Activator