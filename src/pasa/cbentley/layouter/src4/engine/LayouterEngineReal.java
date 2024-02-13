/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.I2DReal;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * When the Gui component does not use the {@link LayouterEngine} {@link Zer2DRect} as model,
 * 
 * we need to update the model when those values are computed.
 * 
 * @author Charles Bentley
 *
 */
public class LayouterEngineReal extends LayouterEngineRead {

   private boolean isAppliedPositionX;

   private boolean isAppliedPositionY;

   private boolean isAppliedSizeH;

   private boolean isAppliedSizeW;

   private I2DReal real;

   public LayouterEngineReal(LayouterCtx lc, ILayoutable layoutable, I2DReal real) {
      super(lc, layoutable);
      this.real = real;
   }

   private void applyRectToComponentPosition() {
      real.setLocation(rect.getX(), rect.getY());
      setAppliedPositionTrue();
   }

   private void applyRectToComponentSize() {
      real.setSize(rect.getW(), rect.getH());
      setAppliedSizeTrue();
   }

   public I2DReal getReal() {
      return real;
   }

   public void layoutInvalidate() {
      super.layoutInvalidate();
      isAppliedPositionX = false;
      isAppliedPositionY = false;
      isAppliedSizeH = false;
      isAppliedSizeW = false;
   }

   /**
    * 
    */
   public void layoutInvalidatePosition() {
      super.layoutInvalidatePosition();
      isAppliedPositionX = false;
      isAppliedPositionY = false;
      Zer2DArea area = getArea();
      if (area.getPositionComputeFlagX() == COMPUTE_3_BOTH) {
         isAppliedSizeW = false;
      }
      if (area.getPositionComputeFlagY() == COMPUTE_3_BOTH) {
         isAppliedSizeH = false;
      }
   }

   public void layoutInvalidateSize() {
      super.layoutInvalidateSize();
      isAppliedSizeH = false;
      isAppliedSizeW = false;
      Zer2DArea area = getArea();
      if (area.getPositionComputeFlagX() == COMPUTE_2_INVERSE) {
         isAppliedPositionX = false;
      }
      if (area.getPositionComputeFlagY() == COMPUTE_2_INVERSE) {
         isAppliedPositionY = false;
      }
   }

   public void layoutUpdateSizeCheck() {
      if (layoutIsValidSize()) {
         if (!isAppliedSizeH || !isAppliedSizeW) {
            applyRectToComponentSize();
         }
         return;
      }
      //#debug
      lac.getDebugBreaks().layoutWillComputeSizes(layoutable);

      //both methods will check the position flag and know what to do
      layoutUpdateSizeHCheck();
      layoutUpdateSizeWCheck();
      applyRectToComponentSize();
   }

   /**
    * Completely override the way it works
    */
   public void layoutUpdatePositionCheck() {
      if (layoutIsValidPosition()) {
         if (!isAppliedPositionX || !isAppliedPositionY) {
            applyRectToComponentPosition();
         }
         return;
      }

      //#debug
      lac.getDebugBreaks().layoutWillComputePositions(layoutable);

      layoutUpdatePositionXCheck();
      layoutUpdatePositionYCheck();
      applyRectToComponentPosition();
   }

   protected void setAppliedPositionTrue() {
      isAppliedPositionX = true;
      isAppliedPositionY = true;
   }

   protected void setAppliedSizeTrue() {
      isAppliedSizeH = true;
      isAppliedSizeW = true;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, LayouterEngineReal.class);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayouterEngineReal.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isAppliedPositionX", isAppliedPositionX);
      dc.appendVarWithSpace("isAppliedPositionY", isAppliedPositionY);
      dc.appendVarWithSpace("isAppliedSizeH", isAppliedSizeH);
      dc.appendVarWithSpace("isAppliedSizeW", isAppliedSizeW);
   }

   //#enddebug

}
