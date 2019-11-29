package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.tech.ITechSizer;

public class Zer2DLayer implements ITechSizer {

   protected final LayouterCtx lc;

   protected ByteObject        pozerX;

   protected ByteObject        pozerY;

   protected ByteObject        sizerH;

   protected ByteObject        sizerW;

   private ByteObject pozerXEnd;

   private ByteObject pozerYBot;

   public Zer2DLayer(LayouterCtx lc) {
      this.lc = lc;
   }

   public ByteObject getPozerX() {
      return pozerX;
   }

   public ByteObject getPozerY() {
      return pozerY;
   }

   public ByteObject getSizerH() {
      return sizerH;
   }

   public ByteObject getSizerW() {
      return sizerW;
   }

   public void setPozerXStart(ByteObject pozerX) {
      this.pozerX = pozerX;
   }

   public void setPozerYTop(ByteObject pozerY) {
      this.pozerY = pozerY;
   }

   public void setPozerXEnd(ByteObject pozerX) {
      this.pozerXEnd = pozerX;
   }

   public void setPozerYBot(ByteObject pozerY) {
      this.pozerYBot = pozerY;
   }
   
   public void setSizerH(ByteObject sizerH) {
      this.sizerH = sizerH;
   }

   public void setSizerW(ByteObject sizerW) {
      this.sizerW = sizerW;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "Zer2DLayer");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "Zer2DLayer");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return lc.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
