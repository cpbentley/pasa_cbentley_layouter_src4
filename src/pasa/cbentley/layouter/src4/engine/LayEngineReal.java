package pasa.cbentley.layouter.src4.engine;

import java.awt.Dimension;

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

   private void applyRectToComponentSize() {
      int width = rect.getW();
      int height = rect.getH();
      Dimension preferredSize;
      Zer2DArea area = getArea();
      int sizeComputeFlag = area.getSizeComputeFlag();
      if (sizeComputeFlag == COMPUTE_SIZE_0_NORMAL) {
         preferredSize = new Dimension(width, height);
      } else if (sizeComputeFlag == COMPUTE_SIZE_1_ONLY_W) {
         preferredSize = new Dimension(width, height);
      } else if (sizeComputeFlag == COMPUTE_SIZE_2_ONLY_H) {
         preferredSize = new Dimension(width, height);
      } else {
         preferredSize = new Dimension(rect.getPw(), rect.getPh());
      }
      real.setSize(width, height);
      real.setPreferredSize(preferredSize);
      setAppliedSizeTrue();

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
