/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

public interface ILayoutDelegateMax {
   /**
    * 
    *
    * @param layoutable 
    * @return 
    */
   public int getSizeMaxHeight(ILayoutable layoutable);

   /**
    * Ask on the Parent of {@link ILayoutable},
    * the parent returns the maximum size,
    * might be preferred size or something else
    * The parent acts as a delegate.
    * 
    * @param layoutable 
    * @return 
    */
   public int getSizeMaxWidth(ILayoutable layoutable);
}
