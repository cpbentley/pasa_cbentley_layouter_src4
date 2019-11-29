package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;

public class Zer2DPozer extends Zer2DAbstract {

   public Zer2DPozer(LayouterCtx lc, ByteObject pozerX, ByteObject pozerY) {
      super(lc, pozerX, pozerY);
   }

   public Zer2DPozer(LayouterCtx lc) {
      super(lc, null, null);
   }

   public Object clone() {
      Zer2DPozer clone = new Zer2DPozer(lc);
      super.cloneSuper(clone);
      return clone;
   }

   public ByteObject getPozerX() {
      return er2dW;
   }

   public ByteObject getPozerY() {
      return er2dH;
   }

   public void setPozerY(ByteObject pozerY) {
      this.er2dH = pozerY;
   }

   public void setPozerX(ByteObject pozerX) {
      this.er2dW = pozerX;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "Zer2DPozer");
      toStringPrivate(dc);
      super.toString(dc.sup());
      PozerFactory pozerFactory = lc.getPozerFactory();
      pozerFactory.toStringPozerX(er2dW, dc.nlLvl());
      pozerFactory.toStringPozerY(er2dH, dc.nlLvl());
   }

   private void toStringPrivate(Dctx dc) {
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "Zer2DPozer");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
