/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.BOAbstractFactory;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.interfaces.ITechNav;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.IBOTypesLayout;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ToStringStaticLayout;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.IBOLinker;
import pasa.cbentley.layouter.src4.tech.IBOPozer;
import pasa.cbentley.layouter.src4.tech.IBOSizer;
import pasa.cbentley.layouter.src4.tech.ITechCoded;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

/**
 * 
 */
public class SizerFactory extends BOAbstractFactory implements IBOLinker, IBOTypesLayout, ITechCoded, IBOSizer, IBOPozer, ITechLayout {

   /**
    * 
    */
   private LayouterCtx lac;

   /**
    * 
    */
   private ByteObject  prefSizer;

   private ByteObject  sizerEmpty;

   /**
    * 
    */
   private ByteObject  sizerMatchParentContentCtx;

   /**
    * 
    */
   private ByteObject  sizerMatchParentCtx;

   /**
    * 
    *
    * @param lac 
    */
   public SizerFactory(LayouterCtx lac) {
      super(lac.getBOC());
      this.lac = lac;
   }

   /**
    * 
    *
    * @param bo 
    * @param max 
    */
   private void addMax(ByteObject bo, ByteObject max) {
      //#mdebug
      if (max == null) {
         throw new NullPointerException();
      }
      //#enddebug
      //relationship of the 2 objects is defined?
      ByteObject relationshipMax = lac.getRelationMaxNew();
      relationshipMax.addByteObject(max);
      bo.addByteObject(relationshipMax);
      bo.setFlag(SIZER_OFFSET_01_FLAG, SIZER_FLAG_4_MAXIMUM, true);
   }

   /**
    * 
    *
    * @param bo 
    * @param min 
    */
   private void addMin(ByteObject bo, ByteObject min) {
      //#mdebug
      if (min == null) {
         throw new NullPointerException();
      }
      //#enddebug

      ByteObject relationshipMin = lac.getRelationMinNew();
      relationshipMin.addByteObject(min);
      bo.addByteObject(relationshipMin);
      bo.setFlag(SIZER_OFFSET_01_FLAG, SIZER_FLAG_3_MINIMUM, true);
   }

   /**
    * Create a basic sizer with mode and value.
    * <br>
    * Example
    * <li> 20dp => {@link ITechLayout#MODE_1_DELEGATE}, 8 
    * <li> 100pixels => {@link ITechLayout#MODE_0_RAW_UNITS}, 8 
    *
    * @param mode 
    * @param value 
    * @return 
    */
   public ByteObject getSizer(int mode, int value) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, mode);
      bo.set2(SIZER_OFFSET_08_VALUE2, value);
      return bo;
   }

   /**
    * If mode is ratio, value is assumed to be value/100
    *
    * @param mode 
    * @param value any value
    * @param etalon {@link IBOSizer#ETALON_1_VIEWCONTEXT}
    * @param etype {@link IBOSizer#ET_VIEWCONTEXT_1_APPLI}
    * @param efun {@link IBOSizer#ET_FUN_3_MIN}
    * @return 
    */
   public ByteObject getSizer(int mode, int value, int etalon, int etype, int efun) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_2_FUNCTION);
      bo.set1(SIZER_OFFSET_10_OP_FUN1, FUNCTION_OP_05_RATIO);
      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, etype);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, efun);
      //assume 100
      bo.set2(SIZER_OFFSET_08_FRACTION_TOP2, value);
      bo.set2(SIZER_OFFSET_09_FRACTION_BOT2, 100);
      bo.set2(SIZER_OFFSET_08_VALUE2, value);
      return bo;
   }

   public ByteObject getSizer(int mode, int etalon, int esub, int eprop, int efun, int opfun, int v1, int v2) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, mode);
      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_04_ET_SUBTYPE1, esub);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, eprop);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, efun);
      //assume 100
      bo.set2(SIZER_OFFSET_08_FRACTION_TOP2, v1);
      bo.set2(SIZER_OFFSET_09_FRACTION_BOT2, v2);
      bo.set1(SIZER_OFFSET_10_OP_FUN1, opfun);
      return bo;
   }
   
   public void setEtalonData(ByteObject bo, int etalon, int esub, int etype, int efun) {
      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_04_ET_SUBTYPE1, esub);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, etype);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, efun);
   }

   public void setEtalonData(ByteObject bo, int etalon, int esub, int etype, int efun, int edata) {
      setEtalonData(bo, etalon, esub, etype, efun);
      bo.set2(SIZER_OFFSET_07_ET_DATA2, edata);
   }

   public ByteObject getSizer(int mode, int op, int value, int etalon, int esub, int etype, int efun) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setEtalonData(bo, etalon, esub, etype, efun);

      bo.set1(SIZER_OFFSET_02_MODE1, mode);
      bo.set1(SIZER_OFFSET_10_OP_FUN1, op);

      bo.set2(SIZER_OFFSET_08_FRACTION_TOP2, value);
      bo.set2(SIZER_OFFSET_09_FRACTION_BOT2, 100);
      return bo;
   }

   /**
    * Mode is {@link ITechLayout#MODE_1_DELEGATE}
    * 
    * Etalon might be ignored.
    * @param delegate
    * @return
    */
   public ByteObject getSizerDelegate(ILayoutDelegate delegate) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_1_DELEGATE);
      bo.set1(SIZER_OFFSET_03_ETALON1, DELEGATE_ETALON_0_REFERENCE);
      ByteObjectLayoutDelegate boLayoutable = new ByteObjectLayoutDelegate(lac.getBOC(), delegate);
      bo.addByteObject(boLayoutable);
      return bo;
   }

   /**
    * Raw sizer whose value is 0
    * @return
    */
   public ByteObject getSizerEmptyLazy() {
      if (sizerEmpty == null) {
         sizerEmpty = getSizerRaw(0);
      }
      return sizerEmpty;
   }

   /**
    * 
    * @return
    */
   public ByteObject getSizerFontCtx() {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio1(bo, 1);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_0_SIZEE_CTX);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_0_CTX);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_03_FONT);
      return bo;
   }

   /**
    * Sizer is the height of the default font.
    *
    * @return 
    */
   public ByteObject getSizerFontH() {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio1(bo, 1);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_2_FONT);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_2_HEIGHT);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_03_FONT);
      return bo;
   }

   /**
    * Function is
    * <li> {@link IBOSizer#ET_FUN_6_DIFF}
    * <li> {@link IBOSizer#ET_FUN_5_ADD}.
    * @param sizer1 
    * @param sizer2 
    * @param function 
    *
    * @return 
    */
   public ByteObject getSizerFromFunctionOfSizer1Sizer2(ByteObject sizer1, ByteObject sizer2, int function) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_5_SIZERS);
      bo.set1(SIZER_OFFSET_03_ETALON1, 0);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, 0);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, function);
      bo.set2(SIZER_OFFSET_08_VALUE2, 0);
      bo.addSub(sizer1);
      bo.addSub(sizer2);
      return bo;
   }

   /**
    * Create a Sizer whose value is decided by the distance of the 2 {@link IBOTypesLayout#FTYPE_4_POSITION}
    *
    * @param pozer1 
    * @param pozer2 
    * @return 
    */
   public ByteObject getSizerFromPozer1Pozer2(ByteObject pozer1, ByteObject pozer2) {
      pozer1.checkType(IBOTypesLayout.FTYPE_4_POSITION);
      pozer2.checkType(IBOTypesLayout.FTYPE_4_POSITION);
      ByteObject bo = getSizer(MODE_6_POZERS, 0);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, FUN_POZER_0_DISTANCE);
      bo.addByteObject(pozer1);
      bo.addByteObject(pozer2);
      return bo;
   }

   /**
    * Implicit sizer, where the first pozer will be decided from the context.
    * 
    * i.e. the Layoutable that is being sized. It will have at least one pozer.
    *
    * @param pozer 
    * @return 
    */
   public ByteObject getSizerFromPozer1Pozer2Implicit(ByteObject pozer) {
      ByteObject bo = getSizer(MODE_6_POZERS, 0);
      bo.setFlag(SIZER_OFFSET_01_FLAG, SIZER_FLAG_8_IMPLICIT, true);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, FUN_POZER_0_DISTANCE);
      bo.addByteObject(pozer);
      return bo;
   }

   /**
    * Default etalon {@link ITechLayout#ETALON_0_SIZEE_CTX} with logic property {@link ITechLayout#SIZER_PROP_02_UNIT_LOGIC}
    * 
    * @param times number of times that unit
    * @return
    */
   public ByteObject getSizerLogicUnit(int times) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_2_FUNCTION);
      bo.set1(SIZER_OFFSET_10_OP_FUN1, FUNCTION_OP_05_RATIO);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_0_SIZEE_CTX);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_02_UNIT_LOGIC);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_0_CTX);
      bo.set2(SIZER_OFFSET_08_FRACTION_TOP2, times);
      bo.set2(SIZER_OFFSET_09_FRACTION_BOT2, 1);
      return bo;
   }

   /**
    * 
    * @return
    */
   public ByteObject getSizerFitParentLazy() {
      if (sizerMatchParentCtx == null) {
         ByteObject bo = getSizerFitParentProp(SIZER_PROP_00_DRAWN);
         sizerMatchParentCtx = bo;
         sizerMatchParentCtx.setImmutable();
      }
      return sizerMatchParentCtx;
   }

   public ByteObject getSizerFitParentProp(int prop) {
      ByteObject bo = getSizerFunEtalonDef(FUNCTION_OP_00_NONE);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_4_PARENT);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, prop);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_0_CTX); //when computing W, take parent's W, 
      return bo;
   }

   /**
    * Size is the full size minus margin, border and padding.
    *
    * @return 
    */
   public ByteObject getSizerFitParentContentLazy() {
      if (sizerMatchParentContentCtx == null) {
         ByteObject bo = getSizerFitParentProp(SIZER_PROP_05_CONTENT);
         sizerMatchParentContentCtx = bo;
         sizerMatchParentContentCtx.setImmutable();
      }
      return sizerMatchParentContentCtx;
   }

   /**
    * When the size is defined by an external topology of navigation.
    * 
    * Direction being
    * <li> {@link ITechNav#NAV_1_UP}
    * <li> {@link ITechNav#NAV_2_DOWN}
    * <li> {@link ITechNav#NAV_3_LEFT}
    * <li> {@link ITechNav#NAV8_5_BOT_BOT}
    * 
    * @param ratio
    * @param dir
    * @return
    */
   public ByteObject getSizerNav(int ratio, int dir) {
      ByteObject bo = getSizerRatio100(ratio, ETALON_5_LINK, ET_LINK_1_NAV, 0);

      ByteObject linker = lac.getLayoutFactory().createLink(ET_LINK_1_NAV, dir);
      bo.addByteObject(linker);
      return bo;
   }

   /**
    * 
    *
    * @param mode 
    * @param value 
    * @param etalon 
    * @param etype 
    * @param efun 
    * @return 
    */
   public ByteObject getSizerNew(int mode, int value, int etalon, int etype, int efun) {
      return getSizer(mode, value, etalon, etype, efun);
   }

   /**
    * Preferred size with a maximum of size of parent's content.
    *
    * @return 
    */
   public ByteObject getSizerPackParentContent() {
      ByteObject bo = getSizerFunEtalonDef(FUNCTION_OP_00_NONE);

      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_4_PARENT);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_0_CTX);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_05_CONTENT);

      ByteObject sizerPref = getSizerPref(bo);

      return sizerPref;
   }

   /**
    * Cue size is ViewContext
    * This sizer is used to pack/shrink
    * 
    * PACK means. preferred size with maximum size of parent,
    *
    * @return 
    */
   public ByteObject getSizerPackViewContext() {
      ByteObject bo = getSizerFunEtalonDef(FUNCTION_OP_00_NONE);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_1_VIEWCONTEXT);
      bo.set1(SIZER_OFFSET_04_ET_SUBTYPE1, ET_VIEWCONTEXT_0_ROOT);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_0_CTX);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_00_DRAWN);
      return bo;
   }

   /**
    * 
    *
    * @param value 
    * @return 
    */
   public ByteObject getSizerPixel(int value) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_0_RAW_UNITS);
      bo.set2(SIZER_OFFSET_08_VALUE2, value);
      return bo;
   }

   /**
    * Sizer returns the size value of property  {@link ITechLayout#SIZER_PROP_01_PREFERRED}.
    * 
    * @return {@link ByteObject} of type {@link IBOTypesLayout#FTYPE_3_SIZER}
    */
   public ByteObject getSizerPref() {
      ByteObject bo = getSizerFunEtalonDef(FUNCTION_OP_00_NONE);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_01_PREFERRED);
      return bo;
   }

   /**
    * When sizing an {@link ILayoutable} as container to its preferred size, you often need
    * to specificy a maximum size.
    * 
    * This is not necessary when sizing leaves such as Buttons.
    *
    * @param maximuSizer 
    * @return 
    */
   public ByteObject getSizerPref(ByteObject maximuSizer) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, 100);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_0_SIZEE_CTX);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_01_PREFERRED);
      addMax(bo, maximuSizer);
      return bo;
   }

   /**
    * Contextual Sizer to preferred size.
    * <br>
    * Object will take the size based on its inner dimension
    * 
    * Lazy get of a cached size for the preferred size.
    *  
    *
    * @return 
    */
   public ByteObject getSizerPrefLazy() {
      if (prefSizer == null) {
         prefSizer = getSizerPref();
      }
      return prefSizer;
   }

   /**
    * Leaks the reference into the {@link ByteObject}
    * @param etalon
    * @param ratioValue
    * @return
    */
   public ByteObject getSizerRatio100(ILayoutable etalon, int ratioValue) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, ratioValue);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_7_DELEGATE);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_0_CTX);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_00_DRAWN);
      ByteObjectLayoutable boLayoutable = new ByteObjectLayoutable(lac.getBOC(), etalon);
      bo.addByteObject(boLayoutable);
      return bo;
   }

   /**
    * @param ratio {@link IBOSizer#SIZER_OFFSET_08_VALUE2}
    * @param etalon {@link IBOSizer#SIZER_OFFSET_03_ETALON1}
    * @return 
    */
   public ByteObject getSizerRatio100(int ratio, int etalon) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, ratio);
      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_00_DRAWN);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_0_CTX);
      return bo;
   }

   public ByteObject getSizerRatio100(int ratio) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, ratio);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_0_SIZEE_CTX);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_00_DRAWN);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_0_CTX);
      return bo;
   }

   /**
    * 
    * @param ratioValue
    * @param etalon
    * @param efun {@link IBOSizer#SIZER_OFFSET_06_ET_FUN1} {@link ITechLayout#ET_FUN_0_CTX}
    * @param etalonType
    * @return
    */
   public ByteObject getSizerRatio100(int ratioValue, int etalon, int efun, int etalonType) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, ratioValue);
      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, etalonType);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, efun);
      return bo;
   }

   public ByteObject getSizerRatio100(int ratioValue, int etalon, int esub, int eprop, int efun) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, ratioValue);
      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_04_ET_SUBTYPE1, esub);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, eprop);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, efun);
      return bo;
   }

   /**
    * Size value is a ratio of the preferred size
    * @return
    */
   public ByteObject getSizerRatio100OwnPreferred(int percent) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_0_RAW_UNITS);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_0_SIZEE_CTX);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_0_CTX); //when computing w, use pw
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_01_PREFERRED); //when computing w, use pw
      setSizerRatio100(bo, percent);
      return bo;
   }

   /**
    *
    * @param ratioValue 0-100 percent value
    * @return 
    */
   public ByteObject getSizerRatio100Parent(int ratioValue) {
      return getSizerRatio100(ratioValue, ETALON_4_PARENT);
   }

   /**
    * Used when we want a component to take more space if there is available in the ratio
    * but if ratio gives a smaller value than minimum, the size give is the minimum.
    *
    * @param ratio100 
    * @param minimumSizer 
    * @return 
    */
   public ByteObject getSizerRatio100ParentMin(int ratio100, ByteObject minimumSizer) {
      ByteObject bo = getSizerRatio100Parent(ratio100);
      addMin(bo, minimumSizer);
      return bo;
   }

   /**
    * with {@link ITechLayout#ET_FUN_0_CTX}
    * so that we computing width, it takes the width
    * @param ratioValue
    * @return
    */
   public ByteObject getSizerRatio100ViewCtx(int ratioValue) {
      return getSizerRatio100(ratioValue, ETALON_1_VIEWCONTEXT);
   }

   public ByteObject getSizerRatio100W(int etalon, int ratioValue) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, ratioValue);
      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_1_WIDTH);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_00_DRAWN);
      return bo;
   }

   /**
    * Fraction of the other size .
    *
    * @param fracTop 
    * @param fracBot 
    * @return 
    */
   public ByteObject getSizerRatioFraction(int fracTop, int fracBot) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_2_FUNCTION);
      bo.set1(SIZER_OFFSET_10_OP_FUN1, FUNCTION_OP_05_RATIO);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_0_SIZEE_CTX);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_7_CTX_INVERSE);
      bo.set2(SIZER_OFFSET_08_FRACTION_TOP2, fracTop);
      bo.set2(SIZER_OFFSET_09_FRACTION_BOT2, fracBot);
      return bo;
   }

   public ByteObject getSizerRaw(int value) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_0_RAW_UNITS);
      bo.set2(SIZER_OFFSET_08_VALUE2, value);
      return bo;
   }

   /**
    * {@link ITechLayout#ET_VIEWCONTEXT_1_APPLI}
    * @param ratio
    * @return
    */
   public ByteObject getSizerViewContextAppliRatio(int ratio) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, ratio);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_1_VIEWCONTEXT);
      bo.set1(SIZER_OFFSET_04_ET_SUBTYPE1, ET_VIEWCONTEXT_1_APPLI);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, SIZER_PROP_00_DRAWN);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_0_CTX);
      return bo;
   }

   public ByteObject getSizerXPixelForYPixel(int x, int y, int efun) {
      return getSizerXPixelForYPixel(x, y, ETALON_0_SIZEE_CTX, SIZER_PROP_00_DRAWN, efun);
   }

   public ByteObject getSizerFunAddMax(int add) {
      ByteObject bo = getSizerFunEtalonDef(FUNCTION_OP_01_ADD, add);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_4_MAX);
      return bo;
   }

   public ByteObject getSizerFunAddDiff(int add) {
      ByteObject bo = getSizerFunEtalonDef(FUNCTION_OP_01_ADD, add);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, ET_FUN_6_DIFF);
      return bo;
   }

   public ByteObject getSizerFunAdd(int add) {
      return getSizerFunEtalonDef(FUNCTION_OP_01_ADD, add);
   }

   public ByteObject getSizerFunMinus(int minus) {
      return getSizerFunEtalonDef(FUNCTION_OP_02_MINUS, minus);
   }

   public ByteObject getSizerFunMultiply(int x) {
      return getSizerFunEtalonDef(FUNCTION_OP_03_MULTIPLY, x);
   }

   public ByteObject getSizerFunXforY(int x, int y) {
      return getSizerFunEtalonDef(FUNCTION_OP_06_X_FOR_Y, x, y);
   }

   public ByteObject getSizerFunEtalonDef(int funOp) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_2_FUNCTION);
      bo.set1(SIZER_OFFSET_10_OP_FUN1, funOp);
      return bo;
   }

   public ByteObject getSizerFunEtalonDef(int funOp, int value) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_2_FUNCTION);
      bo.set1(SIZER_OFFSET_10_OP_FUN1, funOp);
      bo.set2(SIZER_OFFSET_08_VALUE2, value);
      return bo;
   }

   public ByteObject getSizerFunEtalonDef(int funOp, int value1, int value2) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_2_FUNCTION);
      bo.set1(SIZER_OFFSET_10_OP_FUN1, funOp);
      bo.set2(SIZER_OFFSET_08_VALUE2, value1);
      bo.set2(SIZER_OFFSET_09_DATA_EXTRA2, value2);
      return bo;
   }

   public ByteObject getSizerFunDivide(int divideBy) {
      return getSizerFunEtalonDef(FUNCTION_OP_04_DIVIDE, divideBy);
   }

   public ByteObject getSizerXPixelForYPixel(int x, int y, int etalon, int eprop, int efun) {
      ByteObject bo = getSizerFunEtalonDef(FUNCTION_OP_06_X_FOR_Y, x, y);

      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_05_ET_PROPERTY1, eprop);
      bo.set1(SIZER_OFFSET_06_ET_FUN1, efun);

      return bo;
   }

   /**
    * 
    *
    * @param sizerHeight 
    * @return 
    */
   public Zer2DSizer getZer16_9_Height(ByteObject sizerHeight) {
      ByteObject bo = getSizerRatioFraction(16, 9);
      Zer2DSizer zer = new Zer2DSizer(lac);
      zer.setSizerW(bo);
      zer.setSizerH(sizerHeight);
      return zer;
   }

   /**
    * Sizer given gives the width of the area.
    * The heigh is 9/16 of it
    *
    * @param sizerWidth 
    * @return 
    */
   public Zer2DSizer getZer16_9_Width(ByteObject sizerWidth) {
      ByteObject bo = getSizerRatioFraction(9, 16);
      Zer2DSizer zer = new Zer2DSizer(lac);
      zer.setSizerH(bo);
      zer.setSizerW(sizerWidth);
      return zer;
   }

   /**
    * 
    *
    * @param sizer 
    * @param cent 
    */
   public void setSizerRatio1(ByteObject sizer, int cent) {
      sizer.set1(SIZER_OFFSET_02_MODE1, MODE_2_FUNCTION);
      sizer.set1(SIZER_OFFSET_10_OP_FUN1, FUNCTION_OP_05_RATIO);
      sizer.set2(SIZER_OFFSET_08_FRACTION_TOP2, cent);
      sizer.set2(SIZER_OFFSET_09_FRACTION_BOT2, 1);
   }

   /**
    * 
    *
    * @param sizer 
    * @param cent 
    */
   public void setSizerRatio100(ByteObject sizer, int cent) {
      sizer.set1(SIZER_OFFSET_02_MODE1, MODE_2_FUNCTION);
      sizer.set1(SIZER_OFFSET_10_OP_FUN1, FUNCTION_OP_05_RATIO);
      sizer.set2(SIZER_OFFSET_08_FRACTION_TOP2, cent);
      sizer.set2(SIZER_OFFSET_09_FRACTION_BOT2, 100);
   }

   /**
    * 
    *
    * @param sizer 
    * @param dc 
    */
   public void toString1Line(ByteObject sizer, Dctx dc) {
      dc.root(sizer, "Sizer");
      if (dc.isCompact()) {
         dc.appendWithSpace(toString1LineContentShort(sizer));
      } else {
         dc.appendWithSpace(toString1LineContentShort(sizer));
      }
   }

   /**
    * 
    *
    * @param sizer 
    * @return 
    */
   public String toString1LineContentShort(ByteObject sizer) {
      int value = sizer.get2(SIZER_OFFSET_08_VALUE2);
      int mode = sizer.get1(SIZER_OFFSET_02_MODE1);
      int op = sizer.get1(SIZER_OFFSET_10_OP_FUN1);
      int etalon = sizer.get1(SIZER_OFFSET_03_ETALON1);
      int prop = sizer.get1(SIZER_OFFSET_05_ET_PROPERTY1);
      int fun = sizer.get1(SIZER_OFFSET_06_ET_FUN1);
      if (mode == MODE_0_RAW_UNITS) {
         return value + " " + ToStringStaticLayout.toStringMod(mode);
      } else if (mode == MODE_1_DELEGATE) {
         return "delegate =>" + lac.getLayoutOperator().getDelegateFromSizerNull(sizer);
      } else {
         String strFun = ToStringStaticLayout.toStringFunShort(fun);
         String strEtalon = ToStringStaticLayout.toStringEtalonShort(etalon);
         if (op == FUNCTION_OP_05_RATIO) {
            String str = value + "% " + strFun + " of " + strEtalon;
            return str;
         } else if (mode == MODE_3_SCALE) {
            String str = value + "* " + strFun + " of " + strEtalon;
            return str;
         } else {
            //
            String str = "Function";
            return str;
         }
      }
   }

   /**
    * 
    *
    * @param dc 
    * @param sizer 
    */
   public void toString1LineContentShort(Dctx dc, ByteObject sizer) {
      dc.append(toString1LineContentShort(sizer));
   }

   /**
    * 
    *
    * @param sizer 
    * @param dc 
    * @param title 
    */
   public void toStringSizer(ByteObject sizer, Dctx dc, String title) {
      dc.rootN(sizer, "Sizer");
      if (title != null) {
         dc.appendWithSpace(title);
      }
      if (sizer == null) {
         dc.appendWithSpace("is NULL");
         return;
      }
      if (sizer == lac.getSizerFactory().getSizerPrefLazy()) {
         dc.appendWithSpace("Singleton Preferred Size");
         toString1LineContentShort(sizer);
         return;
      }
      int mode = sizer.get1(SIZER_OFFSET_02_MODE1);
      dc.nlVar("Mode", ToStringStaticLayout.toStringMod(mode));
      int op = sizer.get1(SIZER_OFFSET_10_OP_FUN1);
      dc.nlVar("Operator", ToStringStaticLayout.toStringOpFun(op));
      if (op == FUNCTION_OP_05_RATIO) {
         int fracTop = sizer.get2(SIZER_OFFSET_08_FRACTION_TOP2);
         int fracBot = sizer.get2(SIZER_OFFSET_09_FRACTION_BOT2);
         dc.append(' ');
         if (fracBot == 100) {
            dc.append(fracTop);
            dc.append('%');
         } else {
            dc.append(fracTop);
            dc.append('/');
            dc.append(fracBot);
         }
      } else if (op == FUNCTION_OP_05_RATIO) {
         dc.nlVar("X", sizer.get2(SIZER_OFFSET_08_FUN_X2));
         dc.nlVar("Y", sizer.get2(SIZER_OFFSET_09_FUN_Y2));
      } else {
         dc.nlVar("ValueA", sizer.get2(SIZER_OFFSET_08_VALUE2));
         dc.nlVar("ValueB", sizer.get2(SIZER_OFFSET_09_DATA_EXTRA2));
      }
      int etalon = sizer.get1(SIZER_OFFSET_03_ETALON1);
      int prop = sizer.get1(SIZER_OFFSET_05_ET_PROPERTY1);
      int fun = sizer.get1(SIZER_OFFSET_06_ET_FUN1);
      String strProp = "";
      dc.nlVar("Etalon", ToStringStaticLayout.toStringEtalonSizer(etalon));
      switch (etalon) {
         case ETALON_0_SIZEE_CTX:
            strProp = ToStringStaticLayout.toStringSizerProp(prop);
            break;
         case ETALON_2_FONT:
            strProp = ToStringStaticLayout.toStringFontProp(prop);
            break;
         case ETALON_4_PARENT:
            strProp = ToStringStaticLayout.toStringSizerProp(prop);
            break;

         default:
            break;
      }
      dc.nlVar("prop", strProp);
      String strFun = ToStringStaticLayout.toStringFun(fun);
      dc.nlVar("fun", strFun);
   }

   /**
    * Def etalon
    * <li> {@link ITechLayout#ETALON_0_SIZEE_CTX}
    * <li> {@link ITechLayout#ET_FUN_0_CTX}
    * <li> {@link ITechLayout#SIZER_PROP_00_DRAWN}
    * @param ratio100
    * @return
    */
   public ByteObject getSizerRatio100Def(int ratio100) {
      return null;
   }

   /**
    * 
    * @param ratio
    * @param esub
    * @param efun
    * @return
    */
   public ByteObject getSizerFontHeightRatio(int ratio, int esub) {
      return getSizerRatio100(ratio, ETALON_2_FONT, esub, SIZER_PROP_03_FONT, ET_FUN_2_HEIGHT);
   }

}
