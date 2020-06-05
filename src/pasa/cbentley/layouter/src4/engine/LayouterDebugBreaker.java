/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.interfaces.ILayoutWillListener;

public class LayouterDebugBreaker implements ILayoutWillListener {

   protected final LayouterCtx lac;

   private ILayoutable         layoutable;

   private boolean             breakPointPositions;

   public LayouterDebugBreaker(LayouterCtx lac, ILayoutable layoutable) {
      this.lac = lac;
      this.layoutable = layoutable;
   }

   public void layoutWillComputePositions(ILayoutable layoutable) {
      if (this.layoutable == layoutable) {
         breakPointPositions = true;
      }
   }

   public void layoutWillComputePositionX(ILayoutable layoutable) {
      if (this.layoutable == layoutable) {
         breakPointPositions = true;
      }
   }

   public void layoutWillComputePositionY(ILayoutable layoutable) {
      if (this.layoutable == layoutable) {
         breakPointPositions = true;
      }
   }

   public void layoutWillComputeSizes(ILayoutable layoutable) {
      if (this.layoutable == layoutable) {
         breakPointPositions = true;
      }
   }

   public void layoutWillComputeSizeW(ILayoutable layoutable) {
      if (this.layoutable == layoutable) {
         breakPointPositions = true;
      }
   }

   public void layoutWillComputeSizeH(ILayoutable layoutable) {
      if (this.layoutable == layoutable) {
         breakPointPositions = true;
      }
   }
}
