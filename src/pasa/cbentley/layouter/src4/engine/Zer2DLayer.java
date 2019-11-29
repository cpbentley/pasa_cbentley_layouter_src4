/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.tech.ITechSizer;

/**
 * 
 */
public class Zer2DLayer implements ITechSizer {

   /**
    * 
    */
   protected final LayouterCtx lc;

   /**
    * 
    */
   protected ByteObject        pozerX;

   /**
    * 
    */
   protected ByteObject        pozerY;

   /**
    * 
    */
   protected ByteObject        sizerH;

   /**
    * 
    */
   protected ByteObject        sizerW;

   /**
    * 
    */
   private ByteObject pozerXEnd;

   /**
    * 
    */
   private ByteObject pozerYBot;

   /**
    * 
    *
    * @param lc 
    */
   public Zer2DLayer(LayouterCtx lc) {
      this.lc = lc;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerX() {
      return pozerX;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerY() {
      return pozerY;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getSizerH() {
      return sizerH;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getSizerW() {
      return sizerW;
   }

   /**
    * 
    *
    * @param pozerX 
    */
   public void setPozerXStart(ByteObject pozerX) {
      this.pozerX = pozerX;
   }

   /**
    * 
    *
    * @param pozerY 
    */
   public void setPozerYTop(ByteObject pozerY) {
      this.pozerY = pozerY;
   }

   /**
    * 
    *
    * @param pozerX 
    */
   public void setPozerXEnd(ByteObject pozerX) {
      this.pozerXEnd = pozerX;
   }

   /**
    * 
    *
    * @param pozerY 
    */
   public void setPozerYBot(ByteObject pozerY) {
      this.pozerYBot = pozerY;
   }
   
   /**
    * 
    *
    * @param sizerH 
    */
   public void setSizerH(ByteObject sizerH) {
      this.sizerH = sizerH;
   }

   /**
    * 
    *
    * @param sizerW 
    */
   public void setSizerW(ByteObject sizerW) {
      this.sizerW = sizerW;
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
      dc.root(this, "Zer2DLayer");
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
      dc.root1Line(this, "Zer2DLayer");
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

   }

   //#enddebug

}
