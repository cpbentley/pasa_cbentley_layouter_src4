/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;

/**
 * Encapsulates x,y,w,h of a rectangle.
 * 
 * <br>
 * <br>
 * Additionally, we have the concept of preferred width and height.
 * 
 * Preferred size is wehn have a w sizer with 2 pozers.. that's pref size
 * The 2 pozers override the w inner size.
 * 
 * It can also simply record a domain specific size to be used later.
 */
public class Zer2DRect implements IStringable {

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
   private int                 ph;

   /**
    * 
    */
   private int                 pw;

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
   public Zer2DRect(LayouterCtx lc) {
      this.lc = lc;
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
    * @return 
    */
   public int getPh() {
      return ph;
   }

   /**
    * 
    *
    * @return 
    */
   public int getPw() {
      return pw;
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
    *
    * @param h 
    */
   public void setH(int h) {
      this.h = h;
   }

   /**
    * 
    *
    * @param ph 
    */
   public void setPh(int ph) {
      this.ph = ph;
   }

   /**
    * 
    *
    * @param pw 
    */
   public void setPw(int pw) {
      this.pw = pw;
   }

   /**
    * 
    *
    * @param w 
    */
   public void setW(int w) {
      this.w = w;
   }

   /**
    * 
    *
    * @param x 
    */
   public void setX(int x) {
      this.x = x;
   }

   /**
    * 
    *
    * @param y 
    */
   public void setY(int y) {
      this.y = y;
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
      dc.root(this, "Zer2DRect");
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
      dc.root1Line(this, "Zer2DRect");
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
      dc.appendVarWithSpace("pw", pw);
      dc.appendVarWithSpace("ph", ph);
   }

   //#enddebug

}
