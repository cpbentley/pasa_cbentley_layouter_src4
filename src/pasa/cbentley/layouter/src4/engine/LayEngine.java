/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDependencies;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.interfaces.ILayoutWillListener;
import pasa.cbentley.layouter.src4.tech.ITechLayout;
import pasa.cbentley.layouter.src4.tech.ITechSizer;

/**
 * This class provides a base implementation of the {@link ILayoutable} for your own Gui components.
 * 
 * For each {@link ILayoutable} implementation, instantiate a {@link LayEngine} to help out. 
 * 
 * You may want an Engine that supports event hook ups.
 * 
 * There are several level of implementation
 * 
 * @see LayEngineRead
 * @see LayEngineReal
 * 
 * This lay engine only computes values and put them into {@link Zer2DRect}
 * 
 * Another class has to apply them
 * 
 * 
 * @author Charles Bentley
 *
 */
public class LayEngine implements IStringable, ITechLayout, ITechSizer {

   private int                 cycleCounterH;

   private int                 cycleCounterW;

   private int                 cycleCounterX;

   private int                 cycleCounterY;

   private Area2DConfigurator  data;

   private ILayoutDependencies dependencies;

   private boolean             isManualOverrideH;

   private boolean             isManualOverrideW;

   private boolean             isManualOverrideX;

   private boolean             isManualOverrideY;

   /**
    * the x value in rect is up to date
    */
   private boolean             isValidPositionX;

   private boolean             isValidPositionY;

   private boolean             isValidSizeH;

   private boolean             isValidSizeW;

   protected final LayouterCtx lac;

   protected ILayoutable       layoutable;

   private ILayoutWillListener layoutListener;

   protected Zer2DRect         rect;

   /**
    * 
    * @param lac
    * @param layoutable
    */
   public LayEngine(LayouterCtx lac, ILayoutable layoutable) {
      this.lac = lac;
      this.layoutable = layoutable;
      data = new Area2DConfigurator(lac);
      rect = new Zer2DRect(lac);

      //#debug
      //toDLog().pInit("LayEngine created for ", layoutable, LayEngine.class, "LayEngine", LVL_04_FINER, true);
   }

   public void addDependency(ILayoutable layout, int type) {
      if (layout == null) {
         throw new NullPointerException();
      }
      if (dependencies == null) {
         dependencies = new LayoutDependenciesArray(lac);
      }
      dependencies.addDependency(layout, type);
   }

   private void generateSizerException(boolean isW) {
      String sizerStr = "SizerH";
      if (isW) {
         sizerStr = "SizerW";
      }
      //we have a loop
      String msg = sizerStr + " is not defined for " + layoutable.toString1Line() + " " + layoutable.toStringName();
      //#debug
      toDLog().pNull(msg, this, LayEngine.class, "generateSizerException", LVL_10_SEVERE, false);
      throw new IllegalStateException(msg);
   }

   public Zer2DArea getArea() {
      return data.getArea();
   }

   public ILayoutable[] getDependencies() {
      if (dependencies != null) {
         return dependencies.getDependencies();
      }
      return null;
   }

   public Area2DConfigurator getLay() {
      return data;
   }

   public ILayoutWillListener getLayoutListener() {
      return layoutListener;
   }

   private int getSizeComputerFlagH() {
      //area might not be valid because of manual overrides
      if (isManualOverrideW || isManualOverrideX) {
         return COMPUTE_1_NORMAL;
      } else {
         return getArea().getSizeComputeFlagH();
      }
   }

   private int getSizeComputerFlagW() {
      //area might not be valid because of manual overrides
      if (isManualOverrideH || isManualOverrideY) {
         return COMPUTE_1_NORMAL;
      } else {
         return getArea().getSizeComputeFlagW();
      }
   }

   /**
    * True when Etalon is {@link ITechLayout#ETALON_0_SIZEE_CTX}
    * <br>
    * @param sizer
    * @return
    */
   public boolean isContextual(ByteObject sizer) {
      boolean is = false;
      if (sizer != null) {
         int mode = sizer.get1(SIZER_OFFSET_02_MODE1);
         if (mode == MODE_2_RATIO) {
            if (sizer.get1(SIZER_OFFSET_03_ETALON1) == ETALON_0_SIZEE_CTX) {
               is = true;
            }
         }
      }
      return is;
   }

   /**
    * Is Drawable Height to be computed based on content
    * @return
    */
   public boolean isContextualH() {
      ByteObject sizerH = data.getArea().getSizerH();
      return isContextual(sizerH);
   }

   /**
    * Is Drawable Width to be computed based on content.
    * <br>
    * True when 
    * <br>
    * Etalon is {@link ISizer#ETALON_0_SIZEE_CTX}
    * <br>
    * And
    * <br>
    * Mode is {@link ISizer#MODE_3_UNIT} or {@link ISizer#MODE_2_RATIO}
    * @return
    */
   public boolean isContextualW() {
      ByteObject sizerW = data.getArea().getSizerW();
      return isContextual(sizerW);
   }

   /**
    * True when size can be computed without the context. 
    * @return
    */
   public boolean isNoCtxSize() {
      ByteObject sizerW = data.getArea().getSizerW();
      ByteObject sizerH = data.getArea().getSizerH();
      boolean noCtxW = sizerW == null || sizerW.hasFlag(SIZER_OFFSET_01_FLAG, SIZER_FLAG_7_DEFINED);
      boolean noCtxH = sizerH == null || sizerH.hasFlag(SIZER_OFFSET_01_FLAG, SIZER_FLAG_7_DEFINED);
      return noCtxW && noCtxH;
   }

   private void layoutCheckLoopH() {
      if (cycleCounterH != 0) {
         //we have a loop
         String msg = "UI layout has a loop cycle while computing Height of " + this.toString1Line();
         //#debug
         toDLog().pNull(msg, this, LayEngine.class, "layoutCheckLoopH@line197", LVL_10_SEVERE, false);
         throw new IllegalStateException(msg);
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
         toDLog().pNull(msg, this, LayEngine.class, "layoutCheckLoopW@line217", LVL_10_SEVERE, false);
         throw new IllegalStateException(msg);
      }
      cycleCounterW++;
   }

   private void layoutCheckLoopX() {
      if (cycleCounterX != 0) {
         //we have a loop
         String msg = "UI layout has a loop cycle while computing X of " + this.toString1Line();
         //#debug
         toDLog().pNull(msg, this, LayEngine.class, "layoutCheckLoopX@line228", LVL_10_SEVERE, false);
         throw new IllegalStateException(msg);
      }
      cycleCounterX++;
   }

   private void layoutCheckLoopY() {
      if (cycleCounterY != 0) {
         //we have a loop
         String msg = "UI layout has a loop cycle while computing Y of " + this.toString1Line();
         //#debug
         toDLog().pNull(msg, this, LayEngine.class, "layoutCheckLoopY@line239", LVL_10_SEVERE, false);
         throw new IllegalStateException(msg);
      }
      cycleCounterY++;
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

   /**
    * <li> {@link ITechLayout#DEPENDENCY_0_NONE}
    * <li> {@link ITechLayout#DEPENDENCY_1_SIZE}
    * <li> {@link ITechLayout#DEPENDENCY_2_POZE}
    * @param flags
    */
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
      lac.getDebugBreaks().layoutWillComputePositions(layoutable);
      if (layoutListener != null) {
         layoutListener.layoutWillComputePositions(layoutable);
      }

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
      lac.getDebugBreaks().layoutWillComputePositionX(layoutable);
      if (layoutListener != null) {
         layoutListener.layoutWillComputePositionX(layoutable);
      }

      //check loop
      layoutCheckLoopX();

      LayoutOperator operator = lac.getLayoutOperator();
      Zer2DArea area = getArea();
      if (typeX == COMPUTE_0_INVALID) {
         if (!isManualOverrideX) {
            String msg = "LayEngine needs an area with at least one X pozer";
            //#debug
            toDLog().pNull(msg, this, LayEngine.class, "layoutUpdatePositionX", LVL_05_FINE, false);
            throw new IllegalStateException(msg);
         }
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
      lac.getDebugBreaks().layoutWillComputePositionY(layoutable);
      if (layoutListener != null) {
         layoutListener.layoutWillComputePositionY(layoutable);
      }
      //in override, we ignore the pozerY definition and accept the Y in rect.
      if (isManualOverrideY) {
         isValidPositionY = true;
         return;
      }
      layoutCheckLoopY();

      LayoutOperator operator = lac.getLayoutOperator();
      Zer2DArea area = getArea();
      if (typeY == COMPUTE_0_INVALID) {
         String msg = "LayEngine needs an area with at least one Y pozer";
         //#debug
         toDLog().pNull(msg, this, LayEngine.class, "layoutUpdatePositionX", LVL_05_FINE, false);
         throw new IllegalStateException(msg);
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

   /**
    * 
    */
   public void layoutUpdateSizeCheck() {
      if (layoutIsValidSize()) {
         return;
      }
      //#debug
      lac.getDebugBreaks().layoutWillComputeSizes(layoutable);
      if (layoutListener != null) {
         layoutListener.layoutWillComputeSizes(layoutable);
      }

      //both methods will check the position flag and know what to do
      layoutUpdateSizeHCheck();
      layoutUpdateSizeWCheck();

   }

   /**
    * Update the Height Size
    */
   public void layoutUpdateSizeH() {
      //#debug
      lac.getDebugBreaks().layoutWillComputeSizeH(layoutable);

      if (layoutListener != null) {
         layoutListener.layoutWillComputeSizeH(layoutable);
      }
      if (isManualOverrideH) {
         isValidSizeH = true;
         return;
      }
      Zer2DArea area = getArea();
      layoutCheckLoopH();
      LayoutOperator operator = lac.getLayoutOperator();

      int typeY = getSizeComputerFlagH();
      if (typeY == COMPUTE_3_BOTH) {
         //height is defined by 2 pozers. if sizerH is not null.. compute it as ph
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
         //don't do anything, assume the raw H in the rect is absolute and correct
         String msg = "LayEngine needs an area with at least one H pozer";
         //#debug
         toDLog().pNull(msg, this, LayEngine.class, "layoutUpdateSizeH", LVL_05_FINE, false);
         throw new IllegalStateException(msg);
      }
      isValidSizeH = true;
   }

   /**
    * Check for height validity flag and update height if false.
    */
   public void layoutUpdateSizeHCheck() {
      if (isValidSizeH) {
         return;
      }
      layoutUpdateSizeH();
   }

   private void layoutUpdateSizeW() {
      //#debug
      lac.getDebugBreaks().layoutWillComputeSizeW(layoutable);
      if (layoutListener != null) {
         layoutListener.layoutWillComputeSizeW(layoutable);
      }
      if (isManualOverrideW) {
         isValidSizeW = true;
         return;
      }
      //check loop
      layoutCheckLoopW();

      LayoutOperator operator = lac.getLayoutOperator();
      Zer2DArea area = getArea();

      int typeX = getSizeComputerFlagW();
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
         String msg = "LayEngine needs an area with at least one Width pozer";
         //#debug
         toDLog().pNull(msg, this, LayEngine.class, "layoutUpdateSizeW", LVL_05_FINE, false);
         throw new IllegalStateException(msg);
      }
      isValidSizeW = true;
   }

   /**
    * Check for width validity flag and update width if false.
    */
   public void layoutUpdateSizeWCheck() {
      if (isValidSizeW) {
         return;
      }
      layoutUpdateSizeW();
   }

   public void removeDependency(ILayoutable layout, int type) {
      dependencies.removeDependency(layout, type);
   }

   public void setLayoutListener(ILayoutWillListener layoutListener) {
      this.layoutListener = layoutListener;
   }

   /**
    * Tell the layout engine to only read {@link Zer2DRect} value directly.
    * 
    * {@link Area2DConfigurator} is ignored.
    * 
    * In manual override, the engine does not compute values from
    * 
    * @param isManualOverride
    */
   public void setManualOverride(boolean isManualOverride) {
      this.setOverrideXYWH(isManualOverride, isManualOverride, isManualOverride, isManualOverride);
   }

   public void setManualOverrideH(boolean b) {
      this.isManualOverrideH = b;
   }

   public void setManualOverrideW(boolean b) {
      this.isManualOverrideW = b;
   }

   public void setManualOverrideX(boolean b) {
      this.isManualOverrideX = b;
   }

   public void setManualOverrideY(boolean b) {
      this.isManualOverrideY = b;
   }

   public void setOverrideH(int h) {
      rect.setH(h);
   }

   public void setOverrideW(int w) {
      rect.setW(w);
   }

   /**
    * Sets x directly without changing.
    * 
    * Also {@link Area2DConfigurator} might not have any PozerX
    * @param x
    */
   public void setOverrideX(int x) {
      rect.setX(x);
   }

   public void setOverrideXYWH(boolean x, boolean y, boolean w, boolean h) {
      isManualOverrideX = x;
      isManualOverrideY = y;
      isManualOverrideW = w;
      isManualOverrideH = h;
   }

   public void setOverrideY(int y) {
      rect.setY(y);
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(layoutable);
   }

   public void toString(Dctx dc) {
      dc.root(this, LayEngine.class, 642);
      toStringPrivate(dc);

      dc.nl();
      dc.appendVarWithSpace("isManualOverrideX", isManualOverrideX);
      dc.appendVarWithSpace("isManualOverrideY", isManualOverrideY);
      dc.appendVarWithSpace("isManualOverrideW", isManualOverrideW);
      dc.appendVarWithSpace("isManualOverrideH", isManualOverrideH);
      dc.nl();
      dc.appendVarWithSpace("getSizeComputerFlagW", getSizeComputerFlagW());
      dc.appendVarWithSpace("getSizeComputerFlagH", getSizeComputerFlagH());

      //we want nice message
      dc.nlLvl(rect, "Rect");
      dc.nlLvl(data, "Laydata");
      dc.nlLvl(dependencies, "Depedencies");
      dc.nlLvl(layoutable, "layoutable");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayEngine.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return lac.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {
      dc.append("isValid=[");
      dc.appendVarWithSpace("W", isValidSizeW);
      dc.appendVarWithSpace("H", isValidSizeH);
      dc.appendVarWithSpace("X", isValidPositionX);
      dc.appendVarWithSpace("Y", isValidPositionY);
      dc.append("] cycleCounter=[");
      dc.appendVarWithSpace("W", cycleCounterW);
      dc.appendVarWithSpace("H", cycleCounterH);
      dc.appendVarWithSpace("X", cycleCounterX);
      dc.appendVarWithSpace("Y", cycleCounterY);
      dc.append("]");
   }

   //#enddebug

}
