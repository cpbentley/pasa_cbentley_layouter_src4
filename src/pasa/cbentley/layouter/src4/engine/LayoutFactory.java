/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.BOAbstractFactory;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.IBOTypesLayout;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ToStringStaticLayout;
import pasa.cbentley.layouter.src4.tech.ITechCodedSizer;
import pasa.cbentley.layouter.src4.tech.ITechLayout;
import pasa.cbentley.layouter.src4.tech.IBOLinker;
import pasa.cbentley.layouter.src4.tech.IBOPozer;
import pasa.cbentley.layouter.src4.tech.IBOSizer;

/**
 * 
 */
public class LayoutFactory extends BOAbstractFactory implements IBOLinker, IBOTypesLayout, ITechCodedSizer, IBOSizer, IBOPozer, ITechLayout {

   /**
    * 
    */
   private final LayouterCtx lac;

   /**
    * 
    *
    * @param lac 
    */
   public LayoutFactory(LayouterCtx lac) {
      super(lac.getBOC());
      this.lac = lac;

   }

   /**
    * 
    *
    * @param type 
    * @param v 
    * @return 
    */
   public ByteObject createLink(int type, int v) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_5_LINK, LINKER_BASIC_SIZE);
      bo.set1(LINKER_OFFSET_01_TYPE1, type);
      bo.set4(LINKER_OFFSET_02_DATA4, v);
      return bo;
   }

   //#mdebug

   //#enddebug

   /**
    * 
    *
    * @param coded 
    * @return 
    */
   public String toString1Line(int coded) {
      if (CodedUtils.isCoded(coded)) {
         int value = coded;
         int valueV = (value >> CODED_SIZE_SHIFT_0_VALUE) & CODED_MASK_0_VALUE;
         int modV = (value >> CODED_SIZE_SHIFT_1_MODE) & CODED_MASK_1_MODE;
         int etalonV = (value >> CODED_SIZE_SHIFT_2_ETALON) & CODED_MASK_2_ETALON;
         int etalonTypeV = (value >> CODED_SIZE_SHIFT_3_ETALON_TYPE) & CODED_MASK_3_ETALON_TYPE;
         int etalonFunV = (value >> CODED_SIZE_SHIFT_5_ETALON_FUN) & CODED_MASK_5_ETALON_FUN;
         return "val=" + valueV + " mod=" + modV + " etalon=" + etalonV + " etype=" + etalonTypeV + " efun=" + etalonFunV;
      } else {
         return "Not a coded value " + coded;
      }
   }

   /**
    * 
    *
    * @param codedsize 
    * @return 
    */
   public String codedSizeToString1Line(int codedsize) {
      String s = null;
      LayoutOperator lo = lac.getLayoutOperator();
      if (!CodedUtils.isCoded(codedsize)) {
         s = "Raw Size Value = " + codedsize;
      } else {
         //we have code
         if (CodedUtils.isLinked(codedsize)) {
            s = "Index Link to " + lo.getCodedValue(codedsize);
         } else {
            int value = lo.getCodedValue(codedsize);
            int mode = lo.getCodedMode(codedsize);
            int etalon = lo.getCodedEtalon(codedsize);
            int etalonType = lo.getCodedEtalonType(codedsize);
            int etalonFun = lo.getCodedEtalonFun(codedsize);
            s = "v=" + value;
            s += "mo" + ToStringStaticLayout.toStringMod(mode);
         }
      }
      return s;
   }
   
   /**
    * 
    *
    * @param coded 
    * @param bo 
    * @return 
    */
   public String toString1Line(int coded, ByteObject bo) {
      if (CodedUtils.isCoded(coded)) {
         int value = coded;
         int valueV = (value >> CODED_SIZE_SHIFT_0_VALUE) & CODED_MASK_0_VALUE;
         int modV = (value >> CODED_SIZE_SHIFT_1_MODE) & CODED_MASK_1_MODE;
         int etalonV = (value >> CODED_SIZE_SHIFT_2_ETALON) & CODED_MASK_2_ETALON;
         int etalonTypeV = (value >> CODED_SIZE_SHIFT_3_ETALON_TYPE) & CODED_MASK_3_ETALON_TYPE;
         int etalonFunV = (value >> CODED_SIZE_SHIFT_5_ETALON_FUN) & CODED_MASK_5_ETALON_FUN;
         return "val=" + valueV + " mod=" + modV + " etalon=" + etalonV + " etype=" + etalonTypeV + " efun=" + etalonFunV;
      } else {
         if (CodedUtils.isLinked(coded)) {
            int index = lac.getLayoutOperator().getCodedValue(coded);
            ByteObject sizer = bo.getSubAtIndexNull(index);
            if (sizer != null) {
               if (sizer.getType() == FTYPE_3_SIZER) {
                  return lac.getSizerFactory().toString1LineContentShort(sizer);
               } else {
                  return "Wrong Type at " + coded;
               }
            } else {
               return "Error Null Sizer at " + coded;
            }
         } else {
            return "Raw " + coded;
         }
      }
   }
}
