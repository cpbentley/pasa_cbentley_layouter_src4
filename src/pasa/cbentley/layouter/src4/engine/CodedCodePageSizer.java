package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.layouter.src4.ctx.IBOTypesLayout;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ObjectLC;
import pasa.cbentley.layouter.src4.ctx.ToStringStaticLayout;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechCodedFigure;
import pasa.cbentley.layouter.src4.tech.ITechCodedSizer;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

public class CodedCodePageSizer extends ObjectLC implements ITechCodedSizer, ITechLayout {
   public static final int CODE_PAGE_2 = 2;

   public CodedCodePageSizer(LayouterCtx lac) {
      super(lac);
   }

   /**
    * 
    * Coded value is mostly used for quick and light sizing definition for artifacts inside a drawing rectangle.
    * 
    * We have 32 bits to code most used 
    * <li> Value is coded on 12 bits. Each function can read those bits as required
    * 
    * <li> mode is coded in 2 bits for the first 4 values
    *  <ol>
    *  <li> {@link ITechLayout#MODE_0_RAW_UNITS}
    *  <li> {@link ITechLayout#MODE_1_DELEGATE}
    *  <li> {@link ITechLayout#MODE_2_FUNCTION}
    *  <li> {@link ITechLayout#MODE_3_SCALE}
    *  </ol>
    *  
    *  <li> Etalon is coded on 3 bits for 6 values
    *  <ol>
    *  <li>{@link ITechLayout#ETALON_0_SIZEE_CTX}
    *  <li>{@link ITechLayout#ETALON_1_VIEWCONTEXT}
    *  <li>{@link ITechLayout#ETALON_2_FONT}
    *  <li>{@link ITechLayout#ETALON_3_RATIO}
    *  <li>{@link ITechLayout#ETALON_4_PARENT}
    *  <li>{@link ITechLayout#ETALON_5_LINK}
    *  </ol>
    *  <li> Etalon Prop 2 bits
    *  <ol>
    *  <li>{@link ITechLayout#SIZER_PROP_00_DRAWN}
    *  <li>{@link ITechLayout#SIZER_PROP_01_PREFERRED}
    *  <li>{@link ITechLayout#SIZER_PROP_02_UNIT_LOGIC}
    *  <li>{@link ITechLayout#SIZER_PROP_03_FONT}
    *  <li> 
    *  </ol>
    *  <li> Etalon Fun 3 bits
    *  <ol>
    *  <li>{@link ITechLayout#ET_FUN_0_CTX}
    *  <li>{@link ITechLayout#ET_FUN_1_WIDTH}
    *  <li>{@link ITechLayout#ET_FUN_2_HEIGHT}
    *  <li>{@link ITechLayout#ET_FUN_3_MIN}
    *  <li>{@link ITechLayout#ET_FUN_4_MAX}
    *  <li>{@link ITechLayout#ET_FUN_5_ADD}
    *  <li>{@link ITechLayout#ET_FUN_6_DIFF}
    *  <li> 
    *  </ol>
    *  <li> 4 Bits for Function Function
    *  <ol>
    *  <li> {@link ITechLayout#FUNCTION_OP_00_NONE}
    *  <li> {@link ITechLayout#FUNCTION_OP_01_ADD}
    *  <li> {@link ITechLayout#FUNCTION_OP_02_MINUS}
    *  <li> {@link ITechLayout#FUNCTION_OP_03_MULTIPLY}
    *  <li> {@link ITechLayout#FUNCTION_OP_04_DIVIDE}
    *  <li> {@link ITechLayout#FUNCTION_OP_05_RATIO}
    *  <li> {@link ITechLayout#FUNCTION_OP_06_X_FOR_Y}
    *  <li> {@link ITechLayout#FUNCTION_OP_07_}
    *  </ol>
    * @param mode 
    * @param value 
    * @param etalon 
    * @param etype 
    * @param efun which value of the etalon to use.
    * @return 
    */
   public int codedSizeEncode(int value, int etalon, int etype, int eprop, int efun, int fun, int funaux) {
      if (value > 10000 || value < 0) {
         //value too big for our mask. coded as raw
         return value;
      }
      int mode = ITechLayout.MODE_2_FUNCTION;
      int code = CODED_SIZE_FLAG_32_SIGN;
      int valueV = (value & CODED_MASK_0_VALUE) << CODED_SIZE_SHIFT_0_VALUE;
      int etalonV = (etalon & CODED_MASK_2_ETALON) << CODED_SIZE_SHIFT_2_ETALON;
      int etalonTypeV = (etype & CODED_MASK_3_ETALON_TYPE) << CODED_SIZE_SHIFT_3_ETALON_TYPE;
      int etalonPropV = (eprop & CODED_MASK_4_ETALON_PROP) << CODED_SIZE_SHIFT_4_ETALON_PROP;
      int etalonFunV = (efun & CODED_MASK_5_ETALON_FUN) << CODED_SIZE_SHIFT_5_ETALON_FUN;
      int funV = (fun & CODED_MASK_6_FUNCTION) << CODED_SIZE_SHIFT_6_FUNCTION;
      int funauxV = (funaux & CODED_MASK_7_FUNCTION_AUX) << CODED_SIZE_SHIFT_7_FUNCTION_AUX;
      int codepageV = (CODE_PAGE_2 & CODED_MASK_5_CODE_PAGE) << CODED_SIZE_SHIFT_5_CODE_PAGE;
      return code + codepageV + valueV + etalonV + etalonTypeV + etalonPropV + etalonFunV + funV + funauxV;
   }

   public int decode(ByteObject bo, int index, int w, int h, int ctx) {
      LayoutableRect lr = new LayoutableRect(lac, w, h);
      return decode(bo, index, lr, ctx);
   }

   public int decode(ByteObject bo, int index, ILayoutable la, int ctx) {
      int value = bo.get4(index);
      if (CodedUtils.isCoded(value)) {
         //
         if (CodedUtils.isLinked(value)) {
            ByteObject si = bo.getSubFirst(IBOTypesLayout.FTYPE_3_SIZER);
            if (si != null) {
               LayoutOperator op = lac.getLayoutOperator();
               return op.getPixelSize(si, la, ctx);
            } else {
               //dev warn.. return value
               //bad
               throw new IllegalArgumentException();
            }
         } else {
            return decode(value, la, ctx);
         }
      } else {
         return value;
      }
   }

   public int getValue(int data) {
      return (data >> CODED_SIZE_SHIFT_0_VALUE) & CODED_MASK_0_VALUE;
   }

   public int getFunction(int data) {
      return (data >> CODED_SIZE_SHIFT_6_FUNCTION) & CODED_MASK_6_FUNCTION;
   }

   public int getFunctionAux(int data) {
      return (data >> CODED_SIZE_SHIFT_7_FUNCTION_AUX) & CODED_MASK_7_FUNCTION_AUX;
   }

   public int getEtalon(int data) {
      return (data >> CODED_SIZE_SHIFT_2_ETALON) & CODED_MASK_2_ETALON;
   }

   public int getEtalonType(int data) {
      return (data >> CODED_SIZE_SHIFT_3_ETALON_TYPE) & CODED_MASK_3_ETALON_TYPE;
   }

   public int getEtalonProp(int data) {
      return (data >> CODED_SIZE_SHIFT_4_ETALON_PROP) & CODED_MASK_4_ETALON_PROP;
   }

   public int getEtalonFun(int data) {
      return (data >> CODED_SIZE_SHIFT_5_ETALON_FUN) & CODED_MASK_5_ETALON_FUN;
   }

   public int decode(int value, ILayoutable la, int ctx) {
      if (CodedUtils.isCoded(value)) {
         if (CodedUtils.isLinked(value)) {
            throw new IllegalArgumentException("Link needs a ByteObject");
         }
         int etalon = (value >> CODED_SIZE_SHIFT_2_ETALON) & CODED_MASK_2_ETALON;
         int etype = (value >> CODED_SIZE_SHIFT_3_ETALON_TYPE) & CODED_MASK_3_ETALON_TYPE;
         int eprop = (value >> CODED_SIZE_SHIFT_4_ETALON_PROP) & CODED_MASK_4_ETALON_PROP;
         int efun = (value >> CODED_SIZE_SHIFT_5_ETALON_FUN) & CODED_MASK_5_ETALON_FUN;
         int fun = (value >> CODED_SIZE_SHIFT_6_FUNCTION) & CODED_MASK_6_FUNCTION;
         int funaux = (value >> CODED_SIZE_SHIFT_7_FUNCTION_AUX) & CODED_MASK_7_FUNCTION_AUX;
         int modV = MODE_2_FUNCTION;

         int v1 = (value >> CODED_SIZE_SHIFT_0_VALUE) & CODED_MASK_0_VALUE;
         int v2 = 100;
         if (fun == ITechLayout.FUNCTION_OP_05_RATIO) {
            if (funaux == RATIO_TYPE_1_FOR_1000) {
               v2 = 1000;
            } else if (funaux == RATIO_TYPE_2_FOR_10) {
               v2 = 10;
            }
         }
         ByteObject sizer = lac.getSizerFactory().getSizer(modV, etalon, etype, eprop, efun, fun, v1, v2);
         int p = lac.getLayoutOperator().getPixelSize(sizer, la, ctx);
         return p;
      } else {
         return value;
      }
   }

   public String codedSizeToString1Line(int codedsize) {
      int value = getValue(codedsize);
      int mode = ITechLayout.MODE_2_FUNCTION;
      int etalon = getEtalon(codedsize);
      int etalonType = getEtalonType(codedsize);
      int etalonFun = getEtalonFun(codedsize);
      int etalonProp = getEtalonProp(codedsize);
      String s = "v=" + value;
      s += "mo" + ToStringStaticLayout.toStringMod(mode);
      return s;
   }
}
