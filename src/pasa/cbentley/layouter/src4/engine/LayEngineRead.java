/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

public class LayEngineRead extends LayEngine {

   public LayEngineRead(LayouterCtx lac, ILayoutable layoutable) {
      super(lac, layoutable);
   }

   public int getPozeX() {
      return rect.getX();
   }

   public int getPozeXComputed() {
      layoutUpdatePositionXCheck();
      return getPozeX();
   }

   public int getSizeHComputed() {
      layoutUpdateSizeHCheck();
      return getSizeH();
   }

   public int getSizeWComputed() {
      layoutUpdateSizeWCheck();
      return getSizeW();
   }

   /**
    * Minimum size of this drawable
    * @return 0 if none
    */
   public int getSizeMinH() {
      ByteObject sizerH = getArea().getSizerH();
      if (sizerH == null) {
         return 0;
      }
      return lac.getLayoutOperator().getMin(sizerH, layoutable, CTX_2_HEIGHT);
   }

   public int getSizeMaxH() {
      ByteObject sizerH = getArea().getSizerH();
      if (sizerH == null) {
         return -1;
      }
      return lac.getLayoutOperator().getMax(sizerH, layoutable, CTX_2_HEIGHT);
   }

   public int getSizeMaxW() {
      ByteObject sizerW = getArea().getSizerW();
      if (sizerW == null) {
         return -1;
      }
      return lac.getLayoutOperator().getMax(sizerW, layoutable, CTX_1_WIDTH);
   }

   /**
    * Compute the max size
    * <br>
    * @return -1 if no maximum size is defined
    */
   public int getSizeMinW() {
      ByteObject sizerW = getArea().getSizerW();
      if (sizerW == null) {
         return 0;
      }
      return lac.getLayoutOperator().getMin(sizerW, layoutable, CTX_1_WIDTH);
   }

   /**
    * No state impact on current rect. but may require the computation of {@link ILayoutable}
    * from which this sizerW depends on
    * @return
    */
   public int getSizeWComputedFree() {
      layoutUpdateSizeWCheck();
      return getSizeW();
   }

   public int getPozeY() {
      return rect.getY();
   }

   public int getSizeW() {
      return rect.getW();
   }

   public int getSizeH() {
      return rect.getH();
   }

   public int getSizePH() {
      return rect.getPh();
   }

   public int getSizePW() {
      return rect.getPw();
   }

   /**
    * Returns the top y coordinate, compute it if invalid
    */
   public int getPozeYComputed() {
      layoutUpdatePositionYCheck();
      return getPozeY();
   }

   public int getSizeContentHeight() {
      return getSizeDrawnHeight();
   }

   public int getSizeContentWidth() {
      return getSizeDrawnWidth();
   }

   /**
    * H as currently computed
    * @return
    */
   public int getSizeDrawnHeight() {
      //we read data from rect.. because underlying component is update later
      return rect.getH();
   }

   public int getSizeDrawnWidth() {
      return rect.getW();
   }

   public int getSizeUnitHeight() {
      return layoutable.getSizePreferredHeight();
   }

   public int getSizeUnitWidth() {
      return layoutable.getSizePreferredWidth();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, LayEngineRead.class);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayEngineRead.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
