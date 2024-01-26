/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ObjectLC;

/**
 * 
 */
public abstract class Zer2DAbstract extends ObjectLC implements IStringable {


   /**
    * 
    */
   protected ByteObject        er2dH;

   /**
    * 
    */
   protected ByteObject        er2dW;

   /**
    * 
    *
    * @param lc 
    * @param sizerW 
    * @param sizerH 
    */
   public Zer2DAbstract(LayouterCtx lc, ByteObject sizerW, ByteObject sizerH) {
      super(lc);
      this.er2dW = sizerW;
      this.er2dH = sizerH;
   }

   /**
    * 
    *
    * @return 
    */
   public boolean isFullyDefined() {
      return er2dH != null && er2dW != null;
   }

   /**
    * 
    *
    * @param clone 
    */
   protected void cloneSuper(Zer2DAbstract clone) {
      if (this.er2dH != null) {
         clone.er2dH = this.er2dH.cloneMe();
      }
      if (this.er2dW != null) {
         clone.er2dW = this.er2dW.cloneMe();
      }
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, Zer2DAbstract.class, "@line70");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, Zer2DAbstract.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

   
}
