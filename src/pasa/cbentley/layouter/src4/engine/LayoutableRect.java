/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutRequestListener;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * 
 */
public class LayoutableRect implements ILayoutable {

   /**
    * 
    */
   private int                 h;

   /**
    * 
    */
   protected final LayouterCtx lc;

   /**
    * 
    */
   private int                 w;

   /**
    * 
    */
   private int                 x;

   /**
    * 
    */
   private int                 y;

   /**
    * 
    *
    * @param lc 
    */
   public LayoutableRect(LayouterCtx lc) {
      this.lc = lc;
   }

   /**
    * 
    *
    * @param lc 
    * @param w 
    * @param h 
    */
   public LayoutableRect(LayouterCtx lc, int w, int h) {
      this.lc = lc;
      set(w, h);
   }

   /**
    * 
    *
    * @param lay 
    * @param flags 
    */
   public void addDependency(ILayoutable lay, int flags) {

   }

   /**
    * 
    */
   public void repaintLayoutable() {
   }

   /**
    * 
    *
    * @return 
    */
   public Zer2DArea getArea() {
      return null;
   }

   /**
    * 
    *
    * @return 
    */
   public ILayoutable[] getDependencies() {
      return null;
   }

   /**
    * 
    *
    * @return 
    */
   public int getFontHeight() {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public int getFontWidth() {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public int getH() {
      return h;
   }

   /**
    * 
    *
    * @param source 
    * @return 
    */
   public ILayoutable getLayoutableDelegate(ILayoutable source) {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * 
    *
    * @param id 
    * @return 
    */
   public ILayoutable getLayoutableID(int id) {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * 
    *
    * @param dir 
    * @return 
    */
   public ILayoutable getLayoutableNav(int dir) {
      return null;
   }

   /**
    * 
    *
    * @return 
    */
   public ILayoutable getLayoutableParent() {
      return null;
   }

   /**
    * 
    *
    * @return 
    */
   public ILayoutable getLayoutableViewContext() {
      return null;
   }

   /**
    * 
    *
    * @return 
    */
   public ILayoutable getLayoutableViewPort() {
      return null;
   }

   /**
    * 
    *
    * @return 
    */
   public int getLayoutID() {
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public ILayoutRequestListener getLayoutRequestListener() {
      return null;
   }

   /**
    * 
    *
    * @return 
    */
   public int getPozeX() {
      return x;
   }

   /**
    * 
    *
    * @return 
    */
   public int getPozeXComputed() {
      return x;
   }

   /**
    * 
    *
    * @return 
    */
   public int getPozeY() {
      return y;
   }

   /**
    * 
    *
    * @return 
    */
   public int getPozeYComputed() {
      return y;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeDrawnHeight() {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeDrawnWidth() {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @param sizer 
    * @return 
    */
   public int getSizeEtalonH(ByteObject sizer) {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @param sizer 
    * @return 
    */
   public int getSizeEtalonW(ByteObject sizer) {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizePaddingWidth() {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizePaddingHeight() {
      return h;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeBorderWidth() {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeBorderHeight() {
      return h;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeContentWidth() {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeContentHeight() {
      return h;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeFromDeletgateHeight() {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeFromDeletgateWidth() {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizePreferredHeight() {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizePreferredWidth() {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeUnitHeight() {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeUnitWidth() {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getW() {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getWidthDelegate() {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getWidthDrawn() {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getWidthFont() {
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public int getWidthPreferred() {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getWidthUnit() {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getX() {
      return x;
   }

   /**
    * 
    *
    * @return 
    */
   public int getY() {
      return y;
   }

   /**
    * 
    */
   public void layoutInvalidate() {
   }

   /**
    * 
    */
   public void layoutInvalidatePosition() {
   }

   /**
    * 
    */
   public void layoutInvalidateSize() {
   }

   /**
    * 
    *
    * @return 
    */
   public boolean layoutIsValidPosition() {
      return true;
   }

   /**
    * 
    *
    * @return 
    */
   public boolean layoutIsValidSize() {
      return true;
   }

   /**
    * 
    *
    * @param type 
    */
   public void layoutUpdateDependencies(int type) {

   }

   /**
    * 
    */
   public void layoutUpdatePosition() {
   }

   /**
    * 
    */
   public void layoutUpdatePositionCheck() {
   }

   /**
    * 
    */
   public void layoutUpdateSize() {
   }

   /**
    * 
    */
   public void layoutUpdateSizeCheck() {

   }

   /**
    * 
    *
    * @param w 
    * @param h 
    */
   public void set(int w, int h) {
      this.set(0, 0, w, h);
   }

   /**
    * 
    *
    * @param x 
    * @param y 
    * @param w 
    * @param h 
    */
   public void set(int x, int y, int w, int h) {
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
   }

   /**
    * 
    *
    * @param area 
    */
   public void setArea(Zer2DArea area) {
   }

   /**
    * 
    *
    * @return 
    */
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   /**
    * 
    *
    * @return 
    */
   public String toString() {
      return Dctx.toString(this);
   }

   /**
    * 
    *
    * @param dc 
    */
   public void toString(Dctx dc) {
      dc.root(this, "LayoutableRect");
      toStringPrivate(dc);
   }

   /**
    * 
    *
    * @return 
    */
   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   /**
    * 
    *
    * @param dc 
    */
   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LayoutableRect");
      toStringPrivate(dc);
   }

   /**
    * 
    *
    * @return 
    */
   public UCtx toStringGetUCtx() {
      return lc.getUCtx();
   }

   /**
    * 
    *
    * @param dc 
    */
   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("x", x);
      dc.appendVarWithSpace("y", y);
      dc.appendVarWithSpace("w", w);
      dc.appendVarWithSpace("h", h);
   }

   /**
    * 
    *
    * @return 
    */
   public String toStringName() {
      return "rect " + x + "," + y + "|" + w + "," + h;
   }

   /**
    * 
    */
   public void layoutUpdatePositionXCheck() {

   }

   /**
    * 
    */
   public void layoutUpdatePositionYCheck() {

   }

   /**
    * 
    */
   public void layoutUpdateSizeHCheck() {

   }

   /**
    * 
    */
   public void layoutUpdateSizeWCheck() {

   }

   /**
    * 
    *
    * @param layoutable 
    * @return 
    */
   public int getSizeMaxHeight(ILayoutable layoutable) {
      return h;
   }

   /**
    * 
    *
    * @param layoutable 
    * @return 
    */
   public int getSizeMaxWidth(ILayoutable layoutable) {
      return w;
   }

   public ILayoutable getLayoutableEtalon(int etalonType) {
      return null;
   }

   //#enddebug

}
