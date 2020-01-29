/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.I2DReal;
import pasa.cbentley.layouter.src4.interfaces.ILayoutRequestListener;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

public class LayoutableAdapterI2DReal extends LayoutableAdapterAbstract implements ILayoutable, ITechLayout {

   private I2DReal c;

   public LayoutableAdapterI2DReal(LayouterCtx slc, I2DReal compo) {
      super(slc);
      this.c = compo;
   }

   public I2DReal getComponent() {
      return c;
   }

   public int getFontHeight() {
      return c.getFontHeight();
   }

   public int getFontWidth() {
      return c.getFontWidth();
   }

   public ILayoutable getLayoutableDelegate(ILayoutable source) {
      //don't have delegate here. let caller deal with null
      return null;
   }

   public ILayoutable getLayoutableID(int id) {
      //find id in pool otherwise null
      return null;
   }

   public ILayoutable getLayoutableNav(int dir) {
      //adapter does not know of such config
      return null;
   }

   protected ILayoutable getLayoutableParentSub() {
      // TODO Auto-generated method stub
      return null;
   }

   public ILayoutRequestListener getLayoutRequestListener() {
      // TODO Auto-generated method stub
      return null;
   }

   public int getPozeXComputed() {
      layoutUpdatePositionXCheck();
      return getPozeX();
   }

   /**
    * Returns the top y coordinate, compute it if invalid
    */
   public int getPozeYComputed() {
      layoutUpdatePositionYCheck();
      return getPozeY();
   }

   public int getSizeBorderHeight() {
      return getSizeDrawnHeight();
   }

   public int getSizeBorderWidth() {
      return getSizeDrawnWidth();
   }

   public int getSizeContentHeight() {
      return getSizeDrawnHeight();
   }

   public int getSizeContentWidth() {
      return getSizeDrawnWidth();
   }

   public int getSizeFromDeletgateHeight(ByteObject sizer, ILayoutable layoutable) {
      return c.getRealHeight();
   }

   public int getSizeFromDeletgateWidth() {
      return c.getRealWidth();
   }

   public int getSizePreferredHeight() {
      return c.getRealPrefHeight();
   }

   public int getSizePreferredWidth() {
      return c.getRealPrefWidth();
   }

   public void repaintLayoutable() {
      c.repaint();
   }

   protected void setLocation(int x, int y) {
      // TODO Auto-generated method stub

   }


   protected void setSize(int width, int height) {
      // TODO Auto-generated method stub

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "LayoutableAdapterI2DReal");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LayoutableAdapterI2DReal");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   public String toStringName() {
      // TODO Auto-generated method stub
      return null;
   }

   private void toStringPrivate(Dctx dc) {

   }

   public ILayoutable getLayoutableEtalon(int etalonType) {
      // TODO Auto-generated method stub
      return null;
   }

   protected void setPreferredSize(int width, int height) {
      // TODO Auto-generated method stub
      
   }

   //#enddebug

}
