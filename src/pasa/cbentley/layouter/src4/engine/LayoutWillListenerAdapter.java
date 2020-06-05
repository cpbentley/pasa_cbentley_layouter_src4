/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.layouter.src4.interfaces.ILayoutWillListener;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * 
 * Notifies just before a {@link ILayoutable} has some of its features computed
 * 
 * Class allows to set breakpoints for specific {@link ILayoutable}
 * 
 * See demo
 */
public class LayoutWillListenerAdapter implements ILayoutWillListener {

   /**
    * 
    */
   protected boolean isBreak;

   /**
    * 
    *
    * @param layoutable 
    */
   public void layoutWillComputePositions(ILayoutable layoutable) {
   }

   /**
    * Called before computing X value for {@link ILayoutable}
    *
    * @param layoutable the {@link ILayoutable} for which position X is going to be computed
    */
   public void layoutWillComputePositionX(ILayoutable layoutable) {
   }

   /**
    * 
    *
    * @param layoutable 
    */
   public void layoutWillComputePositionY(ILayoutable layoutable) {
   }

   /**
    * Called before computing size values for {@link ILayoutable}
    *
    * @param layoutable 
    */
   public void layoutWillComputeSizes(ILayoutable layoutable) {
   }

   /**
    * Called before computing W value for {@link ILayoutable}
    *
    * @param layoutable 
    */
   public void layoutWillComputeSizeW(ILayoutable layoutable) {
   }

   /**
    * 
    *
    * @param layoutable 
    */
   public void layoutWillComputeSizeH(ILayoutable layoutable) {
   }

}
