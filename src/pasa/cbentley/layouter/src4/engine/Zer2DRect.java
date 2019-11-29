package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;

public class Zer2DRect implements IStringable {

   private int                 x;

   private int                 y;

   private int                 w;

   private int                 h;
   private int                 pw;

   private int                 ph;

   protected final LayouterCtx lc;

   public Zer2DRect(LayouterCtx lc) {
      this.lc = lc;
   }

   public int getX() {
      return x;
   }

   public void setX(int x) {
      this.x = x;
   }

   public int getY() {
      return y;
   }

   public void setY(int y) {
      this.y = y;
   }

   public int getW() {
      return w;
   }

   public void setW(int w) {
      this.w = w;
   }

   public int getH() {
      return h;
   }

   public void setH(int h) {
      this.h = h;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "Zer2DRect");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("x", x);
      dc.appendVarWithSpace("y", y);
      dc.appendVarWithSpace("w", w);
      dc.appendVarWithSpace("h", h);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "Zer2DRect");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return lc.getUCtx();
   }

   public int getPw() {
      return pw;
   }

   public void setPw(int pw) {
      this.pw = pw;
   }

   public int getPh() {
      return ph;
   }

   public void setPh(int ph) {
      this.ph = ph;
   }

   //#enddebug
   

}
