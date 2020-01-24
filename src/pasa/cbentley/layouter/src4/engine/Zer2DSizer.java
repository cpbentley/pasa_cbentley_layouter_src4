/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.tech.ITechSizer;

/**
 * 
 */
public class Zer2DSizer extends Zer2DAbstract implements ITechSizer {

   /**
    * 
    *
    * @param lc 
    * @param sizerW 
    * @param sizerH 
    */
   public Zer2DSizer(LayouterCtx lc, ByteObject sizerW, ByteObject sizerH) {
      super(lc, sizerW, sizerH);
   }

   /**
    * 
    *
    * @param lc 
    */
   public Zer2DSizer(LayouterCtx lc) {
      super(lc, null, null);
   }

   /**
    * 
    *
    * @param sizerH 
    */
   public void setSizerH(ByteObject sizerH) {
      this.er2dH = sizerH;
   }

   public void setSizerWH(ByteObject sizerW, ByteObject sizerH) {
      this.er2dW = sizerW;
      this.er2dH = sizerH;
   }

   /**
    * 
    *
    * @param sizerW 
    */
   public void setSizerW(ByteObject sizerW) {
      this.er2dW = sizerW;
   }

   /**
    * 
    *
    * @return 
    */
   public Object clone() {
      Zer2DSizer clone = new Zer2DSizer(lc);
      super.cloneSuper(clone);
      return clone;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getSizerH() {
      return er2dH;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getSizerW() {
      return er2dW;
   }

   /**
    * 
    *
    * @param dc 
    */
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "Zer2DSizer");
      toStringPrivate(dc);
      super.toString(dc.sup());
      SizerFactory sizerFactory = lc.getSizerFactory();
      sizerFactory.toStringSizer(er2dW, dc.nLevel(), "W");
      sizerFactory.toStringSizer(er2dH, dc.nLevel(), "H");
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
      dc.root1Line(this, "Zer2DSizer");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
      SizerFactory sizerFactory = lc.getSizerFactory();
      sizerFactory.toString1LineContentShort(er2dW);
      sizerFactory.toString1LineContentShort(er2dH);
   }

   //#enddebug

}
