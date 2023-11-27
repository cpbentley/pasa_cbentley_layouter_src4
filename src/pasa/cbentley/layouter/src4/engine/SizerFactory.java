/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.BOAbstractFactory;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ToStringStaticLayout;
import pasa.cbentley.layouter.src4.interfaces.IBOTypesLayout;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechCoded;
import pasa.cbentley.layouter.src4.tech.ITechLayout;
import pasa.cbentley.layouter.src4.tech.ITechLinker;
import pasa.cbentley.layouter.src4.tech.ITechPozer;
import pasa.cbentley.layouter.src4.tech.ITechSizer;

/**
 * 
 */
public class SizerFactory extends BOAbstractFactory implements ITechLinker, IBOTypesLayout, ITechCoded, ITechSizer, ITechPozer, ITechLayout {

   /**
    * 
    */
   private LayouterCtx lac;

   /**
    * 
    */
   private ByteObject  prefSizer;

   /**
    * 
    */
   private ByteObject  sizerMatchParentContentCtx;

   /**
    * 
    */
   private ByteObject  sizerMatchParentCtx;

   private ByteObject  sizerEmpty;

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
    * 
    *
    * @return 
    */
   public ByteObject getSingletonSizerPref() {
      return getSizerPrefLazy();
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
      bo.set2(SIZER_OFFSET_05_VALUE2, value);
      return bo;
   }

   /**
    * 
    * @param times
    * @return
    */
   public ByteObject getSizerLogicUnit(int times) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_2_RATIO);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_0_SIZEE_CTX);
      bo.set1(SIZER_OFFSET_06_PROPERTY1, SIZER_PROP_02_UNIT_LOGIC);
      bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_0_CTX);
      bo.set1(SIZER_OFFSET_05A_FRACTION_TOP1, times);
      bo.set1(SIZER_OFFSET_05B_FRACTION_BOT1, 1);
      return bo;
   }

   /**
    * If mode is ratio, value is assumed to be value/100
    *
    * @param mode {@link ITechSizer#MODE_2_RATIO}
    * @param value any value
    * @param etalon {@link ITechSizer#ETALON_1_VIEWCONTEXT}
    * @param etype {@link ITechSizer#E_VIEWCONTEXT_1_APPLI}
    * @param efun {@link ITechSizer#ET_FUN_3_MIN}
    * @return 
    */
   public ByteObject getSizer(int mode, int value, int etalon, int etype, int efun) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, mode);
      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_06_PROPERTY1, etype);
      bo.set1(SIZER_OFFSET_04_FUNCTION1, efun);
      if(mode == MODE_2_RATIO) {
         //assume 100
         bo.set1(SIZER_OFFSET_05A_FRACTION_TOP1, value);
         bo.set1(SIZER_OFFSET_05B_FRACTION_BOT1, 100);
      } else {
         bo.set2(SIZER_OFFSET_05_VALUE2, value);
      }
      return bo;
   }

   public ByteObject getSizerRaw(int value) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_0_RAW_UNITS);
      bo.set2(SIZER_OFFSET_05_VALUE2, value);
      return bo;
   }

   /**
    * When size is Preferred.
    *
    * @return 
    */
   public ByteObject getSizerCtx() {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_0_RAW_UNITS);
      bo.set2(SIZER_OFFSET_05_VALUE2, 0);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_0_SIZEE_CTX);
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
      bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_0_CTX); //when computing w, use pw
      bo.set1(SIZER_OFFSET_06_PROPERTY1, SIZER_PROP_01_PREFERRED); //when computing w, use pw
      setSizerRatio100(bo, percent);
      return bo;
   }
   
   /**
    * 
    * @return
    */
   public ByteObject getSizerFontCtx() {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio1(bo, 1);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_0_SIZEE_CTX);
      bo.set1(SIZER_OFFSET_06_PROPERTY1, SIZER_PROP_03_FONT);
      bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_0_CTX);
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
      bo.set1(SIZER_OFFSET_06_PROPERTY1, E_FONT_0_DEFAULT);
      bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_2_HEIGHT);
      return bo;
   }

   /**
    * 3 different interpretations exist for the width and height values in the {@link IDrawable#init(int, int)} method.
    * <ol>
    * <li> <b>Positve</b>. The pixel size is fully known. It must be that value. Axis is fully constrained.
    * <li> <b>0</b>. The pixel size is fully computed based on content, capped to screen size. Axis is free.
    * <li> <b>Negative</b>. The size is logical. -2 means 2 lines for a StringDrawable. Axis is constrained.
    * </ol>
    * </p>
    * @param value Old dimension API defined by {@link IDrawable#init(int, int)}
    * @return
    */
   public ByteObject getSizerFromOldValue(int value) {
      ByteObject sizer = null;
      if (value == 0) {
         int etalonType = SIZER_PROP_01_PREFERRED;
         int etalon = ETALON_0_SIZEE_CTX;
         //0 value equal preferred size
         sizer = getSizer(MODE_2_RATIO, 0, etalon, etalonType, 0);
      } else if (value < 0) {
         //might be a coded size
         int val = -value;
         int etalon = ETALON_0_SIZEE_CTX;
         sizer = getSizer(MODE_2_RATIO, val, etalon, 0, 0);
      } else {
         sizer = getSizerPix(value);
      }
      return sizer;
   }

   /**
    * Function is
    * <li> {@link ITechSizer#ET_FUN_6_DIFF}
    * <li> {@link ITechSizer#ET_FUN_5_ADD}.
    *
    * @param function 
    * @param sizer1 
    * @param sizer2 
    * @return 
    */
   public ByteObject getSizerFun(int function, ByteObject sizer1, ByteObject sizer2) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_5_FUNCTION);
      bo.set1(SIZER_OFFSET_03_ETALON1, 0);
      bo.set1(SIZER_OFFSET_06_PROPERTY1, 0);
      bo.set1(SIZER_OFFSET_04_FUNCTION1, function);
      bo.set2(SIZER_OFFSET_05_VALUE2, 0);
      bo.addSub(sizer1);
      bo.addSub(sizer2);
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
    * 
    * @return
    */
   public ByteObject getSizerMatchParentLazy() {
      if (sizerMatchParentCtx == null) {
         ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
         bo.set1(SIZER_OFFSET_02_MODE1, MODE_2_RATIO);
         bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_0_CTX); //when computing W, take parent's W, 
         bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_4_PARENT);
         bo.set1(SIZER_OFFSET_06_PROPERTY1, ITechSizer.SIZER_PROP_00_DRAWN);
         setSizerRatio100(bo, 100);
         sizerMatchParentCtx = bo;
         sizerMatchParentCtx.setImmutable();
      }
      return sizerMatchParentCtx;
   }

   /**
    * Size is the full size minus margin, border and padding.
    *
    * @return 
    */
   public ByteObject getSizerMatchParentLazyContent() {
      if (sizerMatchParentContentCtx == null) {
         ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
         bo.set1(SIZER_OFFSET_02_MODE1, MODE_2_RATIO);
         bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_4_PARENT);
         bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_0_CTX); //when computing W, take parent's W, 
         bo.set1(SIZER_OFFSET_06_PROPERTY1, ITechSizer.SIZER_PROP_05_CONTENT);
         setSizerRatio100(bo, 100);
         sizerMatchParentContentCtx = bo;
         sizerMatchParentContentCtx.setImmutable();
      }
      return sizerMatchParentContentCtx;
   }

   /**
    * When the size is defined by an external topology.
    * 
    * @param ratio
    * @param dir
    * @return
    */
   public ByteObject getSizerNav(int ratio, int dir) {
      ByteObject bo = getSizerNew(MODE_2_RATIO, ratio, ETALON_5_LINK, LINK_1_NAV, 0);

      ByteObject linker = lac.getLayoutFactory().createLink(LINK_1_NAV, dir);
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

      ByteObject max = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      max.set1(SIZER_OFFSET_02_MODE1, MODE_2_RATIO);
      setSizerRatio100(max, 100);
      max.set1(SIZER_OFFSET_03_ETALON1, ETALON_4_PARENT);
      max.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_0_CTX);
      max.set1(SIZER_OFFSET_06_PROPERTY1, ITechSizer.SIZER_PROP_05_CONTENT);

      ByteObject sizerPref = getSizerPref(max);

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
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, 100);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_1_VIEWCONTEXT);
      bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_0_CTX);
      bo.set1(SIZER_OFFSET_06_PROPERTY1, ITechSizer.SIZER_PROP_00_DRAWN);
      return bo;
   }

   /**
    * 
    *
    * @param value 
    * @return 
    */
   public ByteObject getSizerPix(int value) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_0_RAW_UNITS);
      bo.set2(SIZER_OFFSET_05_VALUE2, value);
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
   public ByteObject getSizerPozers(ByteObject pozer) {
      ByteObject bo = getSizer(MODE_6_POZER_DISTANCE, 0);
      bo.setFlag(SIZER_OFFSET_01_FLAG, SIZER_FLAG_8_IMPLICIT, true);
      bo.addByteObject(pozer);
      return bo;
   }

   /**
    * 
    *
    * @param pozer1 
    * @param pozer2 
    * @return 
    */
   public ByteObject getSizerPozers(ByteObject pozer1, ByteObject pozer2) {
      ByteObject bo = getSizer(MODE_6_POZER_DISTANCE, 0);
      bo.addByteObject(pozer1);
      bo.addByteObject(pozer2);
      return bo;
   }

   /**
    * Creates a new contextual Sizer for preferred size.
    * <br>
    * Object will take the size based on its inner dimension
    * {@link ITechSizer#SIZER_PROP_01_PREFERRED}
    * 
    * @return {@link ByteObject} of type {@link IBOTypesLayout#FTYPE_3_SIZER}
    */
   public ByteObject getSizerPref() {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, 100);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_0_SIZEE_CTX);
      bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_0_CTX);
      bo.set1(SIZER_OFFSET_06_PROPERTY1, SIZER_PROP_01_PREFERRED);
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
      bo.set1(SIZER_OFFSET_06_PROPERTY1, SIZER_PROP_01_PREFERRED);
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
    * A ration definition with the etalon ID and 
    * 
    * {@link ITechSizer#SIZER_OFFSET_02_MODE1} is {@link ITechLayout#MODE_2_RATIO}
    * 
    * Etalon .
    * @param etalon {@link ITechSizer#SIZER_OFFSET_03_ETALON1}
    * @param ratioValue {@link ITechSizer#SIZER_OFFSET_05_VALUE2}
    * @return 
    */
   public ByteObject getSizerRatio100(int etalon, int ratioValue) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, ratioValue);
      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_0_CTX);
      bo.set1(SIZER_OFFSET_06_PROPERTY1, ITechSizer.SIZER_PROP_00_DRAWN);
      return bo;
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
      bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_0_CTX);
      bo.set1(SIZER_OFFSET_06_PROPERTY1, ITechSizer.SIZER_PROP_00_DRAWN);
      ByteObjectLayoutable boLayoutable = new ByteObjectLayoutable(lac.getBOC(), etalon);
      bo.addByteObject(boLayoutable);
      return bo;
   }

   public ByteObject getSizerRatio100W(int etalon, int ratioValue) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, ratioValue);
      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_1_WIDTH);
      bo.set1(SIZER_OFFSET_06_PROPERTY1, SIZER_PROP_00_DRAWN);
      return bo;
   }

   /**
    * 
    * @param ratioValue
    * @param etalon
    * @param etalonFun {@link ITechSizer#SIZER_OFFSET_04_FUNCTION1} {@link ITechLayout#ET_FUN_0_CTX}
    * @param etalonType
    * @return
    */
   public ByteObject getSizerRatio100(int ratioValue, int etalon, int etalonFun, int etalonType) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_3_SIZER, SIZER_BASIC_SIZE);
      setSizerRatio100(bo, ratioValue);
      bo.set1(SIZER_OFFSET_03_ETALON1, etalon);
      bo.set1(SIZER_OFFSET_04_FUNCTION1, etalonFun);
      bo.set1(SIZER_OFFSET_06_PROPERTY1, etalonType);
      return bo;
   }

   /**
    *
    * @param ratioValue 0-100 percent value
    * @return 
    */
   public ByteObject getSizerRatio100Parent(int ratioValue) {
      return getSizerRatio100(ETALON_4_PARENT, ratioValue);
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
      return getSizerRatio100(ETALON_1_VIEWCONTEXT, ratioValue);
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
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_2_RATIO);
      bo.set1(SIZER_OFFSET_03_ETALON1, ETALON_0_SIZEE_CTX);
      bo.set1(SIZER_OFFSET_04_FUNCTION1, ET_FUN_7_CTX_OP);
      bo.set1(SIZER_OFFSET_05_VALUE2, fracTop);
      bo.set1(SIZER_OFFSET_05_VALUE2 + 1, fracBot);
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
      sizer.set1(SIZER_OFFSET_02_MODE1, MODE_2_RATIO);
      sizer.set1(SIZER_OFFSET_05_VALUE2, cent);
      sizer.set1(SIZER_OFFSET_05_VALUE2 + 1, 1);
   }

   /**
    * 
    *
    * @param sizer 
    * @param cent 
    */
   public void setSizerRatio100(ByteObject sizer, int cent) {
      sizer.set1(SIZER_OFFSET_02_MODE1, MODE_2_RATIO);
      sizer.set1(SIZER_OFFSET_05_VALUE2, cent);
      sizer.set1(SIZER_OFFSET_05_VALUE2 + 1, 100);
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
      int value = sizer.get2(SIZER_OFFSET_05_VALUE2);
      int mode = sizer.get1(SIZER_OFFSET_02_MODE1);
      int etalon = sizer.get1(SIZER_OFFSET_03_ETALON1);
      int type = sizer.get1(SIZER_OFFSET_06_PROPERTY1);
      int fun = sizer.get1(SIZER_OFFSET_04_FUNCTION1);
      if (mode == MODE_0_RAW_UNITS) {
         return value + " " + ToStringStaticLayout.toStringMod(mode);
      } else if( mode == MODE_1_DELEGATE) {
         return "delegate =>"+ lac.getLayoutOperator().getDelegateFromSizerNull(sizer);
      } else {
         String strFun = ToStringStaticLayout.toStringFunShort(fun);
         String strEtalon = ToStringStaticLayout.toStringEtalonShort(etalon);
         if (mode == MODE_2_RATIO) {
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
      if (sizer == lac.getSizerFactory().getSingletonSizerPref()) {
         dc.appendWithSpace("Singleton Preferred Size");
         toString1LineContentShort(sizer);
         return;
      }
      int mode = sizer.get1(SIZER_OFFSET_02_MODE1);
      dc.nlVar("Mode", ToStringStaticLayout.toStringMod(mode));
      if (mode == MODE_2_RATIO) {
         int fracTop = sizer.get1(SIZER_OFFSET_05A_FRACTION_TOP1);
         int fracBot = sizer.get1(SIZER_OFFSET_05B_FRACTION_BOT1);
         dc.append(' ');
         if (fracBot == 100) {
            dc.append(fracTop);
            dc.append('%');
         } else {
            dc.append(fracTop);
            dc.append('/');
            dc.append(fracBot);
         }
      } else {
         dc.nlVar("Value", sizer.get2(SIZER_OFFSET_05_VALUE2));
      }
      int etalon = sizer.get1(SIZER_OFFSET_03_ETALON1);
      int prop = sizer.get1(SIZER_OFFSET_06_PROPERTY1);
      int fun = sizer.get1(SIZER_OFFSET_04_FUNCTION1);
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

}
