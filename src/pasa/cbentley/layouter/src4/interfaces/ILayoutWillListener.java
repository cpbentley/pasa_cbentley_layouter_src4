/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

/**
 * 
 */
public interface ILayoutWillListener {

   /**
    * Called before X and Y positions are computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutWillComputePositions(ILayoutable layoutable);

   /**
    * Called before X position is computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutWillComputePositionX(ILayoutable layoutable);

   /**
    * Called before Y position is computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutWillComputePositionY(ILayoutable layoutable);

   /**
    * Called before Width and Height sizes are computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutWillComputeSizes(ILayoutable layoutable);

   /**
    * Called before width is computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutWillComputeSizeW(ILayoutable layoutable);

   /**
    * Called before height is computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutWillComputeSizeH(ILayoutable layoutable);
}
