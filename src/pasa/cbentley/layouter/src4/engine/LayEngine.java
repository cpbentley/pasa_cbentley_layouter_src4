package pasa.cbentley.layouter.src4.engine;

import java.awt.Dimension;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDependencies;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechLayout;
import pasa.cbentley.layouter.src4.tech.ITechSizer;

/**
 * This lay engine only computes values and put them into {@link Zer2DRect}
 * 
 * Another class has to apply them
 * @author Charles Bentley
 *
 */
public class LayEngine implements IStringable, ITechLayout, ITechSizer {

   private int                 cycleCounterH;

   private int                 cycleCounterW;

   private int                 cycleCounterX;

   private int                 cycleCounterY;

   private ILayoutDependencies dependencies;

   /**
    * the x value in rect is up to date
    */
   private boolean             isValidPositionX;

   private boolean             isValidPositionY;

   private boolean             isValidSizeH;

   private boolean             isValidSizeW;

   private LayData             data;

   protected Zer2DRect         rect;

   protected final LayouterCtx lac;

   protected ILayoutable       layoutable;

   public LayEngine(LayouterCtx lac, ILayoutable layoutable) {
      this.lac = lac;
      this.layoutable = layoutable;
      data = new LayData(lac);
   }

   public LayData getLay() {
      return data;
   }

   private void layoutCheckLoopH() {
      if (cycleCounterH != 0) {
         //we have a loop
         String msg = "UI layout has a loop cycle while computing Height of " + this.toString1Line();
         //#debug
         toDLog().pNull(msg, this, LayoutableAdapterAbstract.class, "layoutCheckLoopH", LVL_10_SEVERE, false);
         throw new IllegalStateException("UI layout H has a loop cycle for " + layoutable.toStringName());
      }
      cycleCounterH++;
   }

   private void layoutCheckLoopW() {
      if (cycleCounterW != 0) {
         if (isValidSizeW) {
            //we are calling layout when it is already valid
            throw new IllegalStateException("Calling layout Width on an already valid width. Consider using check");
         }
         //we have a loop
         String msg = "UI layout has a loop cycle while computing Width of " + this.toString1Line();
         //#debug
         toDLog().pNull(msg, this, LayoutableAdapterAbstract.class, "layoutCheckLoopW", LVL_10_SEVERE, false);
         throw new IllegalStateException("UI layout W has a loop cycle for " + layoutable.toStringName());
      }
      cycleCounterW++;
   }

   public void layoutInvalidate() {
      isValidPositionX = false;
      isValidPositionY = false;
      isValidSizeW = false;
      isValidSizeH = false;
      cycleCounterH = 0;
      cycleCounterW = 0;
      cycleCounterX = 0;
      cycleCounterY = 0;
   }

   protected Zer2DArea getArea() {
      return data.getArea();
   }

   /**
    * 
    */
   public void layoutInvalidatePosition() {
      isValidPositionX = false;
      isValidPositionY = false;
      cycleCounterX = 0;
      cycleCounterY = 0;
      Zer2DArea area = getArea();
      if (area.getPositionComputeFlagX() == COMPUTE_3_BOTH) {
         isValidSizeW = false;
         cycleCounterW = 0;
      }
      if (area.getPositionComputeFlagY() == COMPUTE_3_BOTH) {
         isValidSizeH = false;
         cycleCounterH = 0;
      }
   }

   public void layoutInvalidateSize() {
      isValidSizeW = false;
      isValidSizeH = false;
      cycleCounterH = 0;
      cycleCounterW = 0;
      Zer2DArea area = getArea();
      //may invalidates the x,y left/top
      if (area.getPositionComputeFlagX() == COMPUTE_2_INVERSE) {
         isValidPositionX = false;
         cycleCounterX = 0;
      }
      if (area.getPositionComputeFlagY() == COMPUTE_2_INVERSE) {
         isValidPositionY = false;
         cycleCounterY = 0;
      }
   }

   public boolean layoutIsValidPosition() {
      return isValidPositionX && isValidPositionY;
   }

   public boolean layoutIsValidSize() {
      return isValidSizeH && isValidSizeW;
   }

   public void layoutUpdateDependencies(int flags) {
      if (dependencies != null) {
         dependencies.layoutUpdateDependencies(flags);
      }
   }

   public void layoutUpdatePositionCheck() {
      if (layoutIsValidPosition()) {
         return;
      }

      //#debug
      lac.getDebugBreaks().checkForBreakPointPos(layoutable);

      layoutUpdatePositionXCheck();
      layoutUpdatePositionYCheck();
   }

   public void layoutUpdatePositionX() {
      Zer2DArea area = getArea();
      int typeX = area.getPositionComputeFlagX();
      layoutUpdatePositionX(typeX);
   }

   private void layoutUpdatePositionX(int typeX) {
      //#debug
      lac.getDebugBreaks().checkForBreakPointPosX(layoutable);

      LayoutOperator operator = lac.getLayoutOperator();
      Zer2DArea area = getArea();
      if (typeX == COMPUTE_0_INVALID) {
         throw new IllegalStateException("Cannot compute X position without at least one X pozer");
      } else if (typeX == COMPUTE_1_NORMAL) {
         ByteObject pozerXStart = area.getPozerXStart();
         int x = operator.getPozXWidth(pozerXStart, layoutable);
         rect.setX(x);
      } else if (typeX == COMPUTE_2_INVERSE) {
         ByteObject pozerXEnd = area.getPozerXEnd();
         int x2 = operator.getPozXWidth(pozerXEnd, layoutable);
         //we need a valid computed width
         layoutUpdateSizeWCheck();
         //int x = x2 - rect.getW();
         rect.setX(x2);
      } else if (typeX == COMPUTE_3_BOTH) {
         ByteObject pozerXStart = area.getPozerXStart();
         ByteObject pozerXEnd = area.getPozerXEnd();
         int x = operator.getPozXPure(pozerXStart, layoutable);
         int x2 = operator.getPozXPure(pozerXEnd, layoutable);
         int width = x2 - x;
         rect.setX(x);
         rect.setW(width);
      }
      isValidPositionX = true;
   }

   public void layoutUpdatePositionXCheck() {
      if (isValidPositionX) {
         return;
      }
      layoutUpdatePositionX();
   }

   public void layoutUpdatePositionY() {
      Zer2DArea area = getArea();
      int typeY = area.getPositionComputeFlagY();
      layoutUpdatePositionY(typeY);
   }

   private void layoutUpdatePositionY(int typeY) {
      //#debug
      lac.getDebugBreaks().checkForBreakPointPosY(layoutable);

      LayoutOperator operator = lac.getLayoutOperator();
      Zer2DArea area = getArea();
      if (typeY == COMPUTE_0_INVALID) {
         throw new IllegalStateException("Cannot compute Y position without at least one Y pozer");
      } else if (typeY == COMPUTE_1_NORMAL) {
         int y = operator.getPozYHeight(area.getPozerYTop(), layoutable);
         rect.setY(y);
      } else if (typeY == COMPUTE_2_INVERSE) {
         int y2 = operator.getPozYHeight(area.getPozerYBot(), layoutable);
         //we need to make sure the H is correct
         layoutUpdateSizeHCheck();
         //int y = y2 - rect.getH();
         rect.setY(y2);
      } else if (typeY == COMPUTE_3_BOTH) {
         int y = operator.getPozYPure(area.getPozerYTop(), layoutable);
         int y2 = operator.getPozYPure(area.getPozerYBot(), layoutable);
         int h = y2 - y;
         rect.setY(y);
         rect.setH(h);
      }
      isValidPositionY = true;
   }

   public void layoutUpdatePositionYCheck() {
      if (isValidPositionY) {
         return;
      }
      layoutUpdatePositionY();
   }

   //   public void layoutUpdateSize() {
   //
   //      //#debug
   //      slc.getDebugBreaks().checkForBreakPointSize(layoutable);
   //
   //      //check if we have a size defined
   //      if (area.isValidSize()) {
   //         layoutUpdateSizeH();
   //         layoutUpdateSizeW();
   //         Dimension size = new Dimension(rect.getW(), rect.getH());
   //         c.setSize(size);
   //         c.setPreferredSize(size);
   //      } else {
   //         c.setSize(c.getPreferredSize());
   //         isValidSizeH = true;
   //         isValidSizeW = true;
   //      }
   //   }

   /**
    * 
    */
   public void layoutUpdateSizeCheck() {
      if (layoutIsValidSize()) {
         return;
      }
      //#debug
      lac.getDebugBreaks().checkForBreakPointSize(layoutable);

      //both methods will check the position flag and know what to do
      layoutUpdateSizeHCheck();
      layoutUpdateSizeWCheck();

   }

   private void generateSizerException(boolean isW) {
      String sizerStr = "SizerH";
      if (isW) {
         sizerStr = "SizerW";
      }
      //we have a loop
      String msg = sizerStr + " is not defined for" + layoutable.toStringName();
      //#debug
      toDLog().pNull(msg, this, LayoutableAdapterAbstract.class, "generateSizerException", LVL_10_SEVERE, false);
      throw new IllegalStateException(msg);
   }

   /**
    * Update the Height Size
    */
   public void layoutUpdateSizeH() {
      //#debug
      lac.getDebugBreaks().checkForBreakPointSizeH(layoutable);

      layoutCheckLoopH();
      LayoutOperator operator = lac.getLayoutOperator();
      Zer2DArea area = getArea();
      int typeY = area.getPositionComputeFlagY();
      if (typeY == COMPUTE_3_BOTH) {
         ByteObject sizerH = area.getSizerH();
         if (sizerH != null) {
            //when in mode both and we do have a H sizer.. the sizer defines the preferred size
            int ph = operator.getPixelSizeH(sizerH, layoutable);
            rect.setPh(ph);
         }
         isValidSizeH = true;
         layoutUpdatePositionYCheck();
      } else if (typeY == COMPUTE_1_NORMAL || typeY == COMPUTE_2_INVERSE) {
         ByteObject sizerH = area.getSizerH();
         if (sizerH == null) {
            generateSizerException(false);
         }
         int h = operator.getPixelSizeH(sizerH, layoutable);
         rect.setH(h);
      } else if (typeY == COMPUTE_0_INVALID) {
         throw new IllegalStateException();
      }
      isValidSizeH = true;
   }

   public void layoutUpdateSizeHCheck() {
      if (isValidSizeH) {
         return;
      }
      layoutUpdateSizeH();
   }

   private void layoutUpdateSizeW() {
      //#debug
      lac.getDebugBreaks().checkForBreakPointSizeW(layoutable);

      //check loop
      layoutCheckLoopW();

      LayoutOperator operator = lac.getLayoutOperator();
      Zer2DArea area = getArea();
      int typeX = area.getPositionComputeFlagX();
      if (typeX == COMPUTE_3_BOTH) {
         //if we have a w sizer with 2 pozers.. that's pref size
         ByteObject sizerW = area.getSizerW();
         if (sizerW != null) {
            //pref size
            int pw = operator.getPixelSizeW(sizerW, layoutable);
            rect.setPw(pw);
         }
         isValidSizeW = true;
         layoutUpdatePositionXCheck();
      } else if (typeX == COMPUTE_1_NORMAL || typeX == COMPUTE_2_INVERSE) {
         ByteObject sizerW = area.getSizerW();
         if (sizerW == null) {
            generateSizerException(true);
         }
         int w = operator.getPixelSizeW(sizerW, layoutable);
         rect.setW(w);
      } else if (typeX == COMPUTE_0_INVALID) {
         throw new IllegalStateException();
      }
      isValidSizeW = true;
   }

   public void layoutUpdateSizeWCheck() {
      if (isValidSizeW) {
         return;
      }
      layoutUpdateSizeW();
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(layoutable);
   }

   public void toString(Dctx dc) {
      dc.root(this, "LayoutableAdapter");
      toStringPrivate(dc);

      //we want nice message

      dc.nlLvl(rect, "Rect");
      dc.nlLvl(dependencies, "Depedencies");

   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LayoutableAdapter");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return lac.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isValidSizeW", isValidSizeW);
      dc.appendVarWithSpace("isValidSizeH", isValidSizeH);
      dc.appendVarWithSpace("isValidPositionX", isValidPositionX);
      dc.appendVarWithSpace("isValidPositionY", isValidPositionY);
      dc.appendVarWithSpace("cycleCounterW", cycleCounterW);
      dc.appendVarWithSpace("cycleCounterH", cycleCounterH);
      dc.appendVarWithSpace("cycleCounterX", cycleCounterX);
      dc.appendVarWithSpace("cycleCounterY", cycleCounterY);
   }

   //#enddebug

}
