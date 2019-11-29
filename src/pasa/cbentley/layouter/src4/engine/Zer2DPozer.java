/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;

/**
 * 
 */
public class Zer2DPozer extends Zer2DAbstract {

   /**
    * 
    *
    * @param lc 
    * @param pozerX 
    * @param pozerY 
    */
   public Zer2DPozer(LayouterCtx lc, ByteObject pozerX, ByteObject pozerY) {
      super(lc, pozerX, pozerY);
   }

   /**
    * 
    *
    * @param lc 
    */
   public Zer2DPozer(LayouterCtx lc) {
      super(lc, null, null);
   }

   /**
    * 
    *
    * @return 
    */
   public Object clone() {
      Zer2DPozer clone = new Zer2DPozer(lc);
      super.cloneSuper(clone);
      return clone;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerX() {
      return er2dW;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerY() {
      return er2dH;
   }

   /**
    * 
    *
    * @param pozerY 
    */
   public void setPozerY(ByteObject pozerY) {
      this.er2dH = pozerY;
   }

   /**
    * 
    *
    * @param pozerX 
    */
   public void setPozerX(ByteObject pozerX) {
      this.er2dW = pozerX;
   }

   /**
    * 
    *
    * @param dc 
    */
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "Zer2DPozer");
      toStringPrivate(dc);
      super.toString(dc.sup());
      PozerFactory pozerFactory = lc.getPozerFactory();
      pozerFactory.toStringPozerX(er2dW, dc.nlLvl());
      pozerFactory.toStringPozerY(er2dH, dc.nlLvl());
   }

   /**
    * 
    *
    * @param dc 
    */
   private void toStringPrivate(Dctx dc) {
   }

   /**
    * 
    *
    * @param dc 
    */
   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "Zer2DPozer");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
