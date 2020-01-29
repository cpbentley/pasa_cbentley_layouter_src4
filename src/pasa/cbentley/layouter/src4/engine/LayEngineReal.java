package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.I2DReal;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * When the Gui component does not use the {@link LayEngine} {@link Zer2DRect} as model,
 * 
 * we need to update the model when those values are computed.
 * 
 * @author Charles Bentley
 *
 */
public class LayEngineReal extends LayEngine {

   private I2DReal real;

   public LayEngineReal(LayouterCtx lc, ILayoutable layoutable, I2DReal real) {
      super(lc, layoutable);
      this.real = real;
   }

   private void applyRectToComponentPosition() {
      real.setLocation(rect.getX(), rect.getY());
      setAppliedPositionTrue();
   }

   private boolean isAppliedPositionX;

   private boolean isAppliedPositionY;

   private boolean isAppliedSizeH;

   private boolean isAppliedSizeW;

   protected void setAppliedPositionTrue() {
      isAppliedPositionX = true;
      isAppliedPositionY = true;
   }

   protected void setAppliedSizeTrue() {
      isAppliedSizeH = true;
      isAppliedSizeW = true;
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

   public void layoutUpdatePositionCheck() {
      if (layoutIsValidPosition()) {
         if (!isAppliedPositionX || !isAppliedPositionY) {
            applyRectToComponentPosition();
         }
         return;
      }

      //#debug
      lac.getDebugBreaks().checkForBreakPointPos(layoutable);

      layoutUpdatePositionXCheck();
      layoutUpdatePositionYCheck();
      applyRectToComponentPosition();
   }

}
