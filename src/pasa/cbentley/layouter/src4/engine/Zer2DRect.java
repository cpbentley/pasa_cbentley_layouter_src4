/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;

/**
 * 
 */
public class Zer2DRect implements IStringable {

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
    */
   private int                 w;

   /**
    * 
    */
   private int                 h;
   
   /**
    * 
    */
   private int                 pw;

   /**
    * 
    */
   private int                 ph;

   /**
    * 
    */
   protected final LayouterCtx lc;

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
   public int getX() {
      return x;
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
    * @return 
    */
   public int getY() {
      return y;
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
   public int getW() {
      return w;
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
    * @return 
    */
   public int getH() {
      return h;
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
   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("x", x);
      dc.appendVarWithSpace("y", y);
      dc.appendVarWithSpace("w", w);
      dc.appendVarWithSpace("h", h);
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
    * @return 
    */
   public int getPw() {
      return pw;
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
    * @return 
    */
   public int getPh() {
      return ph;
   }

   /**
    * 
    *
    * @param ph 
    */
   public void setPh(int ph) {
      this.ph = ph;
   }

   //#enddebug
   

}
