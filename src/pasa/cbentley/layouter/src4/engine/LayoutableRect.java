/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;
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
   protected final LayouterCtx lac;

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
      this.lac = lc;
   }

   /**
    * 
    *
    * @param lc 
    * @param w 
    * @param h 
    */
   public LayoutableRect(LayouterCtx lc, int w, int h) {
      this.lac = lc;
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

   public ILayoutable getLayoutableEtalon(int etalonType) {
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

   public ILayoutDelegate getLayoutDelegate() {
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
      return h;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeDrawnWidth() {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeFontHeight() {
      return h;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeFontWidth() {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizePreferredHeight() {
      return h;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizePreferredWidth() {
      return w;
   }

   public int getSizePropertyValueH(int property) {
      return h;
   }

   public int getSizePropertyValueW(int property) {
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
   public void layoutUpdateSize() {
   }

   /**
    * 
    */
   public void layoutUpdateSizeCheck() {

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
    */
   public void repaintLayoutable() {
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
      dc.root(this, LayoutableRect.class, 469);
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
      dc.root1Line(this,  LayoutableRect.class);
      toStringPrivate(dc);
   }

   /**
    * 
    *
    * @return 
    */
   public UCtx toStringGetUCtx() {
      return lac.getUCtx();
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
    *
    * @param dc 
    */
   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("x", x);
      dc.appendVarWithSpace("y", y);
      dc.appendVarWithSpace("w", w);
      dc.appendVarWithSpace("h", h);
   }

   //#enddebug

}
