package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.IBOSizer;

public class LayouterEngineV8 extends LayouterEngine {

   public LayouterEngineV8(LayouterCtx lac, ILayoutable layoutable) {
      super(lac, layoutable);
      properties = new LayoutablePropertyHolder(lac);
   }

   public LayoutablePropertyHolder getProperties() {
      return properties;
   }

   public int getSizeFontHeight() {
      return properties.getFontH();
   }

   public int getSizeFontWidth() {
      return properties.getFontW();
   }

   public void setSizeFontH(int sizeFontH) {
      properties.setFontH(sizeFontH);
   }

   public void setSizeFontW(int sizeFontW) {
      properties.setFontW(sizeFontW);
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

   public void addStyleToHeight(ByteObject style) {
      // TODO Auto-generated method stub

   }

   /**
    * Compute the style width pixel consumption
    * and add it to the.
    * 
    * @param style
    */
   public void addStyleToWidth(ByteObject style) {
      // TODO Auto-generated method stub

   }

   private LayoutablePropertyHolder properties;

   /**
    * Drawable height minus style decoration height pixels.
    * <br>
    * <br>
    * @return
    */
   public int getContentH() {
      return properties.getContentH();
   }

   /**
    * Drawable width minus decoration width pixels.
    * @return
    */
   public int getContentW() {
      return properties.getContentW();
   }

   /**
    * Content x coordinate. Take into account Left Margin/Padding/Border.
    * @return
    */
   public int getContentX() {
      return properties.getContentX();
   }

   /**
    * Content y coordinate. Take into account Top Margin/Padding/Border.
    * @return
    */
   public int getContentY() {
      return properties.getContentY();
   }

   public int getH() {
      return rect.getH();
   }

   private LayoutOperator getLayOp() {
      return lac.getLayoutOperator();
   }

   public int getPh() {
      return rect.getPh();
   }

   public int getPw() {
      return rect.getPw();
   }

   public int getSizePotentialH() {
      return getSizeWComputed();
   }

   /**
    * Compute the max size the drawable could use.
    * 
    * Computed size may include shrank value
    * <br>
    * @return -1 if no maximum size is defined
    */
   public int getSizePotentialW() {
      return getSizeWComputed();
   }

   /**
    * Current drawable width.
    * <br>
    * @return
    */
   public int getW() {
      return rect.getW();
   }

   public int getX() {
      return rect.getX();
   }

   public int getY() {
      return rect.getY();
   }

   private boolean hasFlagSizer(ByteObject sizer, int flag) {
      if (sizer != null) {
         return sizer.hasFlag(IBOSizer.SIZER_OFFSET_01_FLAG, IBOSizer.SIZER_FLAG_2_ALLOW_SHRINK);
      }
      return false;
   }

   public boolean hasFlagSizerH(int flag) {
      ByteObject sizer = getArea().getSizerH();
      return hasFlagSizer(sizer, flag);
   }

   public boolean hasFlagSizerW(int flag) {
      ByteObject sizer = getArea().getSizerW();
      return hasFlagSizer(sizer, flag);
   }

   public void incrDh(int incr) {
      rect.incrH(incr);
   }

   public void incrDw(int incr) {
      rect.incrW(incr);
   }

   public void incrPh(int ph) {
      rect.incrPh(ph);
   }

   public void incrPw(int pw) {
      rect.incrPw(pw);
   }

   public boolean isPhBiggerThanDh() {
      return getPh() > getH();
   }

   public boolean isPwBiggerThanDw() {
      return getPw() > getW();
   }

   public boolean isPwOrPhEqualsZero() {
      return getPw() == 0 || getPh() == 0;
   }

   public void setPh(int ph) {
      rect.setPh(ph);
   }

   public void setPhAsDh() {
      rect.setPhAsDh();
   }

   public void setPw(int pw) {
      rect.setPw(pw);
   }

   public void setPwAsDw() {
      rect.setPwAsDw();
   }

   public void setX(int x) {
      rect.setX(x);
      setManualOverrideX(true);
   }

   public void setXInvalidate(int x) {
      setX(x);
      layoutInvalidatePosition();
   }

   /**
    * <Li> {@link C#LOGIC_1_TOP_LEFT}
    * <Li> {@link C#LOGIC_2_CENTER}
    * <Li> {@link C#LOGIC_3_BOTTOM_RIGHT}
    * @param xLogic
    */
   public void setXLogic(int xLogic) {
      switch (xLogic) {
         case C.LOGIC_1_TOP_LEFT:
            getLay().layPoz_StartToStart_OfParent();
            break;
         case C.LOGIC_2_CENTER:
            getLay().layPoz_MidXToMid_OfParent();
            break;
         case C.LOGIC_3_BOTTOM_RIGHT:
            getLay().layPoz_EndToEnd_Parent();
            break;
         default:
            throw new IllegalArgumentException();
      }
   }

   public void setXYInvalidate(int x, int y) {
      setX(x);
      setY(y);
      layoutInvalidatePosition();
   }

   public void setY(int y) {
      rect.setY(y);
      setManualOverrideY(true);
   }

   public void setYInvalidate(int y) {
      setY(y);
      layoutInvalidatePosition();
   }

   /**
    * <Li> {@link C#LOGIC_1_TOP_LEFT}
    * <Li> {@link C#LOGIC_2_CENTER}
    * <Li> {@link C#LOGIC_3_BOTTOM_RIGHT}
    * @param yLogic
    */
   public void setYLogic(int yLogic) {
      switch (yLogic) {
         case C.LOGIC_1_TOP_LEFT:
            getLay().layPoz_TopToTop_OfParent();
            break;
         case C.LOGIC_2_CENTER:
            getLay().layPoz_MidYToMid_OfParent();
            break;
         case C.LOGIC_3_BOTTOM_RIGHT:
            getLay().layPoz_BotToBot_OfParent();
            break;
         default:
            throw new IllegalArgumentException();
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, LayouterEngineV8.class, 150);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayouterEngineV8.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
