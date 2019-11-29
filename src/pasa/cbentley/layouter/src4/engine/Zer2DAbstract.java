/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;

/**
 * 
 */
public abstract class Zer2DAbstract implements IStringable {

   /**
    * 
    */
   protected final LayouterCtx lc;

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
      this.lc = lc;
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
      dc.root(this, "Zer2DAbstract");
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

   }

   /**
    * 
    *
    * @param dc 
    */
   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "Zer2DAbstract");
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

   //#enddebug

}
