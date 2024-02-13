/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ObjectLC;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * 
 */
public class LayoutableRect extends ObjectLC implements ILayoutable {

   private int                 h;

   private int                 w;

   private int                 x;

   private int                 y;

   /**
    *
    * @param lc 
    */
   public LayoutableRect(LayouterCtx lac) {
      this(lac, 0, 0);
   }

   /**
    * 
    *
    * @param lc 
    * @param w 
    * @param h 
    */
   public LayoutableRect(LayouterCtx lac, int w, int h) {
      super(lac);
      set(w, h);
   }

   /**
    * 
    *
    * @param lay 
    * @param flags 
    */
   public void setDependency(ILayoutable lay, int flags) {

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


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, LayoutableRect.class, 420);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }


   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayoutableRect.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   

   /**
    * @return 
    */
   public String toStringName() {
      return "rect " + x + "," + y + "|" + w + "," + h;
   }

   /**
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
