package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.layouter.src4.ctx.IBOTypesLayout;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ObjectLC;
import pasa.cbentley.layouter.src4.ctx.ToStringStaticLayout;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechCodedFigure;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

public class CodedCodePageFigure extends ObjectLC implements ITechCodedFigure, ITechLayout {

   public static final int CODE_PAGE_1 = 1;

   public CodedCodePageFigure(LayouterCtx lac) {
      super(lac);
   }

   /**
    * 
    * @param ratio
    * @return
    */
   public int encodeRatioMinWHSizee(int ratio) {
      int value = ratio;
      int fun = FUNCTION_FIG_05_RATIO;
      int funaux = RATIO_TYPE_0_FOR_100;
      int etalon = ETALON_0_SIZEE_CTX;
      int efun = ET_FUN_03_MIN;
      return encode(value, fun, funaux, etalon, efun);
   }

   public int encodeDivSizeWH(int divider) {
      int value = divider;
      int fun = FUNCTION_FIG_04_DIVIDE;
      int funaux = RATIO_TYPE_0_FOR_100;
      int etalon = ETALON_0_SIZEE_CTX;
      int efun = ET_FUN_00_CTX;
      return encode(value, fun, funaux, etalon, efun);
   }

   public int encodeRatioSizeWH(int ratio) {
      int value = ratio;
      int fun = FUNCTION_FIG_05_RATIO;
      int funaux = RATIO_TYPE_0_FOR_100;
      int etalon = ETALON_0_SIZEE_CTX;
      int efun = ET_FUN_00_CTX;
      return encode(value, fun, funaux, etalon, efun);
   }

   /**
    *  <li> Fun 3 bits
    *  <ol>
    *  <li>{@link ITechCodedFigure#FUNCTION_FIG_00_NONE}
    *  <li>{@link ITechCodedFigure#FUNCTION_FIG_01_ADD}
    *  <li>{@link ITechCodedFigure#FUNCTION_FIG_03_MULTIPLY}
    *  <li>{@link ITechCodedFigure#FUNCTION_FIG_04_DIVIDE}
    *  <li>{@link ITechCodedFigure#FUNCTION_FIG_05_RATIO}
    *  </ol>
    *  <li> Fun aux 2 bits
    *  <ol>
    *  <li>{@link ITechCodedFigure#RATIO_TYPE_0_FOR_100}
    *  <li>{@link ITechCodedFigure#RATIO_TYPE_1_FOR_1000}
    *  <li>{@link ITechCodedFigure#RATIO_TYPE_2_FOR_10}
    *  </ol>
    *   
    * 
    *  <li> Etalon Fun 3 bits
    *  <ol>
    *  <li>{@link ITechLayout#ET_FUN_00_CTX}
    *  <li>{@link ITechLayout#ET_FUN_01_WIDTH}
    *  <li>{@link ITechLayout#ET_FUN_02_HEIGHT}
    *  <li>{@link ITechLayout#ET_FUN_03_MIN}
    *  <li>{@link ITechLayout#ET_FUN_04_MAX}
    *  <li>{@link ITechLayout#ET_FUN_05_ADD}
    *  <li>{@link ITechLayout#ET_FUN_06_DIFF}
    *  <li> 
    *  </ol>
    * @param value
    * @param fun
    * @param funaux
    * @param etalon
    * @param efun
    * @return
    */
   public int encode(int value, int fun, int funaux, int etalon, int efun) {
      if (value > 10000 || value < 0) {
         throw new IllegalArgumentException();
      }
      int code = CODED_SIZE_FLAG_32_SIGN;

      int valueV = (value & CODED_MASK_0_VALUE) << CODED_SIZE_SHIFT_0_VALUE;
      int funV = (fun & CODED_MASK_1_VALUE_FUNCTION) << CODED_SIZE_SHIFT_1_VALUE_FUNCTION;
      int funVa = (funaux & CODED_MASK_2_VALUE_FUNCTION_AUX) << CODED_SIZE_SHIFT_2_VALUE_FUNCTION_AUX;
      int etalonV = (etalon & CODED_MASK_3_ETALON) << CODED_SIZE_SHIFT_3_ETALON;
      int etalonFunV = (efun & CODED_MASK_4_ETALON_FUN) << CODED_SIZE_SHIFT_4_ETALON_FUN;
      int codepageV = (CODE_PAGE_1 & CODED_MASK_5_CODE_PAGE) << CODED_SIZE_SHIFT_5_CODE_PAGE;
      return code + codepageV + valueV + funV + funVa + etalonV + etalonFunV;
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
      return (data >> CODED_SIZE_SHIFT_1_VALUE_FUNCTION) & CODED_MASK_1_VALUE_FUNCTION;
   }

   public int getFunctionAux(int data) {
      return (data >> CODED_SIZE_SHIFT_2_VALUE_FUNCTION_AUX) & CODED_MASK_2_VALUE_FUNCTION_AUX;
   }

   public int getEtalon(int data) {
      return (data >> CODED_SIZE_SHIFT_3_ETALON) & CODED_MASK_3_ETALON;
   }

   public int getEtalonFun(int data) {
      return (data >> CODED_SIZE_SHIFT_4_ETALON_FUN) & CODED_MASK_4_ETALON_FUN;
   }

   public int decode(int data, ILayoutable la, int ctx) {
      int v = getValue(data);
      int fun = getFunction(data);
      int ev = getEtalonValue(data, la, ctx);
      if (fun == FUNCTION_FIG_01_ADD) {
         return v + ev;
      } else if (fun == FUNCTION_FIG_03_MULTIPLY) {
         return v * ev;
      } else if (fun == FUNCTION_FIG_04_DIVIDE) {
         return ev / v;
      } else if (fun == FUNCTION_FIG_05_RATIO) {
         int aux = getFunctionAux(data);
         if (aux == RATIO_TYPE_0_FOR_100) {
            return (v * ev) / 100;
         } else if (aux == RATIO_TYPE_1_FOR_1000) {
            return (v * ev) / 1000;
         } else if (aux == RATIO_TYPE_2_FOR_10) {
            return (v * ev) / 10;
         }
         return ev / v;
      }
      return ev;
   }

   public int getEtalonValue(int data, ILayoutable la, int ctx) {
      int et = getEtalon(data);
      int w = la.getSizeDrawnWidth();
      int h = la.getSizeDrawnHeight();
      if (et == ETALON_0_RECT) {
         int ef = getEtalonFun(data);
         if (ef == ET_FUN_01_WIDTH) {
            return w;
         } else if (ef == ET_FUN_02_HEIGHT) {
            return h;
         } else if (ef == ET_FUN_03_MIN) {
            int v = Math.min(w, h);
            return v;
         } else if (ef == ET_FUN_04_MAX) {
            int v = Math.max(w, h);
            return v;
         } else if (ef == ET_FUN_05_ADD) {
            return w + h;
         } else if (ef == ET_FUN_06_DIFF) {
            return Math.abs(w - h);
         } 
      }
      if (ctx == CTX_1_WIDTH) {
         return w;
      } else {
         return h;
      }
   }

   public String codedSizeToString1Line(int codedsize) {
      int value = getValue(codedsize);
      int mode = ITechLayout.MODE_2_FUNCTION;
      int etalon = getEtalon(codedsize);
      int etalonFun = getEtalonFun(codedsize);
      String s = "v=" + value;
      s += "mo" + ToStringStaticLayout.toStringMod(mode);
      return s;
   }
}
