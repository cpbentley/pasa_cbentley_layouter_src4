/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

/**
 * 
 */
//#mdebug
public interface IDebugBreaks {
   
   /**
    * 
    *
    * @param layoutable 
    */
   public void checkForBreakPointPos(ILayoutable layoutable);

   /**
    * 
    *
    * @param layoutable 
    */
   public void checkForBreakPointPosX(ILayoutable layoutable);

   /**
    * 
    *
    * @param layoutable 
    */
   public void checkForBreakPointPosY(ILayoutable layoutable);

   /**
    * 
    *
    * @param layoutable 
    */
   public void checkForBreakPointSize(ILayoutable layoutable);

   /**
    * 
    *
    * @param layoutable 
    */
   public void checkForBreakPointSizeW(ILayoutable layoutable);

   /**
    * 
    *
    * @param layoutable 
    */
   public void checkForBreakPointSizeH(ILayoutable layoutable);
}
//#enddebug
