/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

/**
 * 
 */
public interface ILayoutDidListener {

   /**
    * Called after X and Y positions are computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutDidComputePositions(ILayoutable layoutable);

   /**
    * Called after X position is computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutDidComputePositionX(ILayoutable layoutable);

   /**
    * Called after Y position is computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutDidComputePositionY(ILayoutable layoutable);

   /**
    * Called before Width and Height sizes are computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutDidComputeSizes(ILayoutable layoutable);

   /**
    * Called after width is computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutDidComputeSizeW(ILayoutable layoutable);

   /**
    * Called after height is computed on the {@link ILayoutable}
    * @param layoutable 
    */
   public void layoutDidComputeSizeH(ILayoutable layoutable);
}
