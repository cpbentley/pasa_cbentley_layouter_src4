/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ObjectLC;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * Encapsulates x,y,w,h,pw,ph of a rectangle in a {@link ILayoutable} context.
 * 
 * 
 * Preferred width/height situations arise when
 * <li>Preferred size is wehn have a w sizer with 2 pozers.. that's pref size
 * The 2 pozers override the w inner size.
 * <li> Externally decided by the {@link ILayoutable} using this {@link Zer2DRect}
 * 
 * @author Charles Bentley
 */
public class Zer2DRect extends ObjectLC implements IStringable {

   private int h;

   private int ph;

   private int pw;

   private int w;

   private int x;

   private int y;

   /**
    * @param lc 
    */
   public Zer2DRect(LayouterCtx lac) {
      super(lac);
   }

   /**
    * Returns the height of the rect.
    * @return int
    */
   public int getH() {
      return h;
   }

   /**
    * Returns the preferred height of the rect.
    * @return int
    */
   public int getPh() {
      return ph;
   }

   /**
    * Returns the preferred width of the rect.
    * @return int
    */
   public int getPw() {
      return pw;
   }

   /**
    * Returns the width of the rect.
    * @return int
    */
   public int getW() {
      return w;
   }

   /**
    * Returns the x coordinate of the rect.
    * @return int
    */
   public int getX() {
      return x;
   }

   /**
    * Returns the y coordinate of the rect.
    * @return int
    */
   public int getY() {
      return y;
   }

   /**
    * Increment the height of the rect by <code>incr</code>.
    * @param incr 
    */
   public void incrH(int incr) {
      h += incr;
   }

   /**
    * Increment the preferred height of the rect by <code>incr</code>.
    * @param incr 
    */
   public void incrPh(int incr) {
      this.ph += incr;
   }

   /**
    * Increment the preferred width of the rect by <code>incr</code>.
    * @param incr 
    */
   public void incrPw(int incr) {
      this.pw += incr;
   }

   /**
    * Increment the width of the rect by <code>incr</code>.
    * @param incr 
    */
   public void incrW(int incr) {
      w += incr;
   }

   /**
    * Set the height of the rect.
    * @param h 
    */
   public void setH(int h) {
      this.h = h;
   }

   /**
    * Set the preferred height of the rect.
    * @param ph 
    */
   public void setPh(int ph) {
      this.ph = ph;
   }

   /**
    * Set the preferred height of the rect as the current height.
    */
   public void setPhAsDh() {
      ph = h;
   }

   /**
    * Set the preferred width of the rect.
    * @param pw 
    */
   public void setPw(int pw) {
      this.pw = pw;
   }

   /**
    * Set the preferred width of the rect as the current width.
    */
   public void setPwAsDw() {
      pw = w;
   }

   /**
    * Set the width of the rect.
    * @param w 
    */
   public void setW(int w) {
      this.w = w;
   }

   /**
   * Set the x coordinate.
    * @param x 
    */
   public void setX(int x) {
      this.x = x;
   }

   /**
    * Set the y coordinate.
    * @param y 
    */
   public void setY(int y) {
      this.y = y;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, Zer2DRect.class, 185);
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nl();
      dc.append("[");
      dc.append(x);
      dc.append(',');
      dc.append(y);
      dc.append(' ');
      dc.append(w);
      dc.append(',');
      dc.append(h);
      dc.append(']');
      dc.append(' ');
      dc.append('p');
      dc.append('[');
      dc.append(pw);
      dc.append(',');
      dc.append(ph);
      dc.append(']');
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, Zer2DRect.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
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
