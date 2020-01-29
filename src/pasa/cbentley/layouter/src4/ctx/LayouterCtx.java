/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.byteobjects.src4.tech.ITechRelation;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.layouter.src4.engine.DebugBreakAdapter;
import pasa.cbentley.layouter.src4.engine.LayoutFactory;
import pasa.cbentley.layouter.src4.engine.LayoutOperator;
import pasa.cbentley.layouter.src4.engine.LayoutableRect;
import pasa.cbentley.layouter.src4.engine.PozerFactory;
import pasa.cbentley.layouter.src4.engine.SizerFactory;
import pasa.cbentley.layouter.src4.interfaces.IBOTypesLayout;
import pasa.cbentley.layouter.src4.interfaces.IDebugBreaks;
import pasa.cbentley.layouter.src4.interfaces.ILayoutRequestListener;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

/**
 * Needs to know reference font pizel size, hdpi scale, Compact/Regular IOS
 * <br>.
 *
 * @author Charles Bentley
 */
public class LayouterCtx extends ABOCtx {

   public static final int          CTX_ID = 60;

   /**
    * 
    */
   //#debug
   private IDebugBreaks             debugBreaks;

   /**
    * Value is given by the outisde and changed by an event.
    */
   private int                      dpi;

   /**
    * 
    */
   private LayoutFactory            layoutFactory;

   /**
    * 
    */
   private LayoutOperator           layoutOperator;

   /**
    * 
    */
   private ILayoutRequestListener   layoutRequestListener;

   /**
    * 
    */
   protected final BOModuleLayouter module;

   /**
    * 
    */
   private PozerFactory             pozerFactory;

   /**
    * 
    */
   protected final LayoutableRect   sc;

   /**
    * 
    */
   private SizerFactory             sizerFactory;

   /**
    * 
    *
    * @param uc 
    * @param boc 
    */
   public LayouterCtx(UCtx uc, BOCtx boc) {
      super(boc);
      sc = new LayoutableRect(this);
      module = new BOModuleLayouter(this);
   }

   /**
    * 
    *
    * @return 
    */
   public BOCtx getBOC() {
      return boc;
   }

   /**
    * 
    *
    * @return 
    */
   public BOModuleLayouter getBOModule() {
      return module;
   }

   public int getBOSettingsCtxSize() {
      return ITechCtxSettingsLayouter.CTX_LAY_BASIC_SIZE;
   }

   /**
    * 
    *
    * @return 
    */
   //#mdebug
   public IDebugBreaks getDebugBreaks() {
      if (debugBreaks == null) {
         debugBreaks = new DebugBreakAdapter();
      }
      return debugBreaks;
   }
   //#enddebug

   /**
    * 
    *
    * @return 
    */
   public LayoutableRect getDefaultSizeContext() {
      return sc;
   }

   /**
    * 
    *
    * @return 
    */
   public int getDPI() {
      return dpi;
   }

   /**
    * 
    *
    * @return 
    */
   public PozerFactory getFactoryPozer() {
      return getPozerFactory();
   }

   /**
    * 
    *
    * @return 
    */
   public SizerFactory getFactorySizer() {
      return getSizerFactory();
   }

   /**
    * Ctx unique computer to dynamica layout values.
    * Used when
    * @return
    */
   public ILayoutRequestListener getGlobalLayoutRequestListener() {
      return layoutRequestListener;
   }

   /**
    * 
    * @param id
    * @return
    */
   public ILayoutable getLayoutableID(int id) {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * 
    *
    * @return 
    */
   public LayoutFactory getLayoutFactory() {
      if (layoutFactory == null) {
         layoutFactory = new LayoutFactory(this);
      }
      return layoutFactory;
   }

   /**
    * 
    *
    * @return 
    */
   public LayoutOperator getLayoutOperator() {
      if (layoutOperator == null) {
         layoutOperator = new LayoutOperator(this);
      }
      return layoutOperator;
   }

   public int getCtxID() {
      return CTX_ID;
   }

   /**
    * 
    *
    * @return 
    */
   public PozerFactory getPozerFactory() {
      if (pozerFactory == null) {
         pozerFactory = new PozerFactory(this);
      }
      return pozerFactory;
   }

   /**
    * 
    *
    * @return 
    */
   public int getReferenceFontHeightPixels() {
      return 10;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getRelationMaxNew() {
      ByteObject bo = new ByteObject(boc, IBOTypesBOC.TYPE_019_RELATIONSHIP, ITechRelation.RELATION_BASIC_SIZE);
      bo.set1(ITechRelation.RELATION_OFFSET_02_TYPE1, IBOTypesLayout.RELATION_2_MAX);
      return bo;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getRelationMinNew() {
      ByteObject bo = new ByteObject(boc, IBOTypesBOC.TYPE_019_RELATIONSHIP, ITechRelation.RELATION_BASIC_SIZE);
      bo.set1(ITechRelation.RELATION_OFFSET_02_TYPE1, IBOTypesLayout.RELATION_2_MAX);
      return bo;
   }

   /**
    * Scale is 
    * <li>{@link ITechLayout#SIZE_1_SMALLEST}
    * <li>{@link ITechLayout#SIZE_3_MEDIUM}
    * <li>{@link ITechLayout#SIZE_5_BIGGEST}.
    *
    * @param scale 
    * @param fun 
    * @return 
    */
   public int getScalePixel(int scale, int fun) {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public SizerFactory getSizerFactory() {
      if (sizerFactory == null) {
         sizerFactory = new SizerFactory(this);
      }
      return sizerFactory;
   }

   /**
    * 
    *
    * @param debugBreaks 
    */
   //#mdebug
   public void setDebugBreaks(IDebugBreaks debugBreaks) {
      this.debugBreaks = debugBreaks;
   }
   //#enddebug


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "LayouterCtx");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LayouterCtx");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
