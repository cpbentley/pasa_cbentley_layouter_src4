/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.BOAbstractFactory;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ToStringStaticLayout;
import pasa.cbentley.layouter.src4.interfaces.IBOTypesLayout;
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
      bo.set2(SIZER_OFFSET_05_VALUE2, value);
      return bo;
   }

   /**
    * 
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
    * 
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
    * 
    *
    * @return 
    */
   public ByteObject getSingletonSizerPref() {
      return getSizerPrefLazy();
   }

   /**
    * A ration definition with the etalon ID and 
    * 
    * {@link ITechSizer#SIZER_OFFSET_02_MODE1} is {@link ITechLayout#MODE_2_RATIO}
    * 
    * Etalon .
    *
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
    * 
    *
    * @param ratioValue 
    * @return 
    */
   public ByteObject getSizerRatio100Parent(int ratioValue) {
      return getSizerRatio100(ETALON_4_PARENT, ratioValue);
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
      if (mode == MODE_0_RAW_UNITS || mode == MODE_1_DELEGATE) {
         return value + " " + ToStringStaticLayout.toStringMod(mode);
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
      dc.root(sizer, "Sizer");
      if (title != null) {
         dc.appendWithSpace(title);
      }
      if (sizer == null) {
         dc.appendWithSpace("is NULL");
         return;
      }
      if (sizer == lac.getSizerFactory().getSingletonSizerPref()) {
         dc.append("Singleton Preferred Size");
         toString1LineContentShort(sizer);
         return;
      }
      int mode = sizer.get1(SIZER_OFFSET_02_MODE1);
      dc.nlVar("Mode", ToStringStaticLayout.toStringMod(mode));
      if (mode == MODE_2_RATIO) {
         int fracTop = sizer.get1(SIZER_OFFSET_05_FRAC_TOP1);
         int fracBot = sizer.get1(SIZER_OFFSET_05_FRAC_TOP1);
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
