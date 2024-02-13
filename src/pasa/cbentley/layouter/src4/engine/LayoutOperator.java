/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.BOAbstractOperator;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.byteobjects.src4.objects.function.ITechRelation;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.layouter.src4.ctx.IBOTypesLayout;
import pasa.cbentley.layouter.src4.ctx.LayoutException;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ToStringStaticLayout;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegateMax;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.IBOPozer;
import pasa.cbentley.layouter.src4.tech.IBOSizer;
import pasa.cbentley.layouter.src4.tech.IBOTblr;
import pasa.cbentley.layouter.src4.tech.ITechCoded;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

/**
 * How to use molecular sizes?
 * <br>
 * <br>
 * CodedSizes on 4 bytes are compressed representation of {@link ISizer}.
 * <br>
 * <br>
 * They use an internal coding using flags. Usually, they are used to code Ratio relative to an 
 * etalon.
 * <br>
 * <br>
 * For more complex 
 * CodedSize of -1, means use the {@link ISizer} at index position.
 * <br>
 * <br>
 * A scrollbar width shall have
 * <br>
 * The goal is also to fit the screen in a category. A screen of 170*220 is very small. We want 1 pixel borders
 * a screen 500 * 800 will want to use bigger borders.
 * <br>
 * How do you compute that?
 * <br>
 * <br>
 * 
 * @author Charles-Philip
 *
 */
public class LayoutOperator extends BOAbstractOperator implements IBOTypesLayout, IBOPozer, IBOSizer, IBOTblr, ITechLayout, ITechCoded {

   public static boolean hasSubFlag(int value) {
      return ((value >> 30) & 0x3) == 2;
   }

   /**
    * True when 
    * <li>{@link ISizer#CODED_SIZE_FLAG_32_SIGN}
    * <li>{@link ISizer#CODED_SIZE_FLAG_31_SIGN_CODE}
    * 
    * @param value
    * @return
    */
   public static boolean isCoded(int value) {
      return ((value >> 30) & 0x3) == 2;
   }

   public static boolean isLinked(int value) {
      return (value & CODED_SIZE_FLAG_30_CODED) == CODED_SIZE_FLAG_30_CODED;
   }

   /**
    * 
    */
   private LayouterCtx lac;

   /**
    * 
    *
    * @param lac 
    */
   public LayoutOperator(LayouterCtx lac) {
      super(lac.getBOC());
      this.lac = lac;
   }

   /**
    * 
    *
    * @param s 
    * @param sc 
    * @param ctx 
    * @param result 
    * @return 
    */
   private int applyMinMax(ByteObject s, ILayoutable sc, int ctx, int result) {
      if (s.hasFlag(SIZER_OFFSET_01_FLAG, SIZER_FLAG_3_MINIMUM)) {
         int min = getMin(s, sc, ctx);
         if (min != -1) {
            if (result < min) {
               result = min;
            }
         }
      }
      if (s.hasFlag(SIZER_OFFSET_01_FLAG, SIZER_FLAG_4_MAXIMUM)) {
         int max = getMax(s, sc, ctx);
         if (max != -1) {
            if (result > max) {
               result = max;
            }
         }
      }
      if (result == 0) {
         if (!s.hasFlag(SIZER_OFFSET_01_FLAG, SIZER_FLAG_1_ALLOW_0)) {
            result = 1;
         }
      }
      return result;
   }

   /**
    * 
    *
    * @param ctx 
    */
   private void checkCtx(int ctx) {
      if (ctx != CTX_1_WIDTH && ctx != CTX_2_HEIGHT) {
         throw new IllegalArgumentException();
      }
   }

   /**
    * 
    *
    * @param layoutable 
    */
   private void checkNull(ILayoutable layoutable) {
      if (layoutable == null) {
         throw new NullPointerException();
      }
   }

   /**
    * Decodes anchor.
    * 
    * @param value
    * @return
    */
   public int codedAnchorDecode(int value) {
      return codedSizeDecode(value, lac.getDefaultSizeContext(), CTX_1_WIDTH);
   }

   /**
    * 
    *
    * @param ow 
    * @param bo 
    * @param index 
    * @param w 
    * @param h 
    * @param ctx 
    * @return 
    */
   public int codedPosDecode(int ow, ByteObject bo, int index, int w, int h, int ctx) {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @param bo 
    * @param index 
    * @param w 
    * @param h 
    * @param ctx 
    * @return 
    */
   public int codedSizeDecode(ByteObject bo, int index, int w, int h, int ctx) {
      //use a thread local
      //we are always on 4 bytes for those calls
      int value = bo.get4(index);
      if (CodedUtils.isCoded(value)) {
         LayoutableRect sc = new LayoutableRect(lac, w, h);
         //
         if (CodedUtils.isLinked(value)) {
            ByteObject si = bo.getSubFirst(FTYPE_3_SIZER);
            if (si != null) {
               return getPixelSize(si, sc, ctx);
            } else {
               //dev warn.. return value
               //bad
               throw new IllegalArgumentException();
            }
         } else {
            return codedSizeDecode(value, sc, ctx);
         }
      } else {
         return value;
      }
   }

   /**
    * Decode size using the default module {@link ILayoutable} and {@link ISizer#CTX_1_WIDTH}.
    *
    * @param value 
    * @return 
    */
   public int codedSizeDecode(int value) {
      return codedSizeDecode(value, lac.getDefaultSizeContext(), CTX_1_WIDTH);
   }

   /**
    * 
    *
    * @param value 
    * @param sc 
    * @param ctxType 
    * @return 
    */
   public int codedSizeDecode(int value, ILayoutable sc, int ctxType) {
      if (CodedUtils.isCoded(value)) {
         if (CodedUtils.isLinked(value)) {
            throw new IllegalArgumentException("Link needs a ByteObject");
         }
         int mode = (value >> CODED_SIZE_SHIFT_1_MODE) & CODED_MASK_1_MODE;
         int etalon = (value >> CODED_SIZE_SHIFT_2_ETALON) & CODED_MASK_2_ETALON;
         int esub = (value >> CODED_SIZE_SHIFT_3_ETALON_TYPE) & CODED_MASK_3_ETALON_TYPE;
         int eprop = (value >> CODED_SIZE_SHIFT_3_ETALON_TYPE) & CODED_MASK_3_ETALON_TYPE;
         int efun = (value >> CODED_SIZE_SHIFT_4_ETALON_FUN) & CODED_MASK_4_ETALON_FUN;
         int opfun = (value >> CODED_SIZE_SHIFT_4_ETALON_FUN) & CODED_MASK_6_OP_FUN;
         int v1 = 0;
         int v2 = 0;
         if (opfun == 0) {
            v1 = (value >> CODED_SIZE_SHIFT_0_VALUE) & CODED_MASK_0_VALUE;
         }

         int valueV = (value >> CODED_SIZE_SHIFT_0_VALUE) & CODED_MASK_0_VALUE;
         int modV = (value >> CODED_SIZE_SHIFT_1_MODE) & CODED_MASK_1_MODE;
         int etalonV = (value >> CODED_SIZE_SHIFT_2_ETALON) & CODED_MASK_2_ETALON;
         int etalonTypeV = (value >> CODED_SIZE_SHIFT_3_ETALON_TYPE) & CODED_MASK_3_ETALON_TYPE;
         int etalonFunV = (value >> CODED_SIZE_SHIFT_4_ETALON_FUN) & CODED_MASK_4_ETALON_FUN;
         //set invalid etalon
         //TODO create a thread local OR one sizable for each thread!
         //OR OPTIMIZE down to the bits. use this method for generic testing
         ByteObject sizer = lac.getSizerFactory().getSizer(modV, valueV, etalonV, etalonTypeV, etalonFunV);

         sizer = lac.getSizerFactory().getSizer(mode, etalon, esub, eprop, efun, opfun, v1, v2);
         int p = getPixelSize(sizer, sc, ctxType);
         return p;
      } else {
         return value;
      }
   }

   /**
    * 
    *
    * @param value 
    * @param w 
    * @param h 
    * @param ctx 
    * @return 
    */
   public int codedSizeDecode(int value, int w, int h, int ctx) {
      LayoutableRect sc = new LayoutableRect(lac, w, h);
      return codedSizeDecode(value, sc, ctx);
   }

   /**
    * 
    *
    * @param codedsize 
    * @param sc 
    * @return 
    */
   public int codedSizeDecodeH(int codedsize, ILayoutable sc) {
      return codedSizeDecode(codedsize, sc, CTX_2_HEIGHT);
   }

   /**
    * 
    *
    * @param codedsize 
    * @param sc 
    * @return 
    */
   public int codedSizeDecodeW(int codedsize, ILayoutable sc) {
      return codedSizeDecode(codedsize, sc, CTX_1_WIDTH);
   }

   /**
    * 
    * Coded value is mostly used for quick and light sizing definition for artifacts inside a drawing rectangle.
    * 
    * We have 32 bits to code most used 
    * <li> Value is coded on 16 bits. Each function can read those bits as required
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
    *  <li> {@link ITechLayout#FUNCTION_OP_06_X_FOR_Y}
    *  </ol>
    * @param mode 
    * @param value 
    * @param etalon 
    * @param etype 
    * @param efun 
    * @return 
    */
   public int codedSizeEncode(int mode, int value, int etalon, int etype, int efun) {
      if (value > 10000 || value < 0) {
         //value too big for our mask. coded as raw
         return value;
      }
      int code = CODED_SIZE_FLAG_32_SIGN;
      int valueV = (value & CODED_MASK_0_VALUE) << CODED_SIZE_SHIFT_0_VALUE;
      int modV = (mode & CODED_MASK_1_MODE) << CODED_SIZE_SHIFT_1_MODE;
      int etalonV = (etalon & CODED_MASK_2_ETALON) << CODED_SIZE_SHIFT_2_ETALON;
      int etalonTypeV = (etype & CODED_MASK_3_ETALON_TYPE) << CODED_SIZE_SHIFT_3_ETALON_TYPE;
      int etalonFunV = (efun & CODED_MASK_4_ETALON_FUN) << CODED_SIZE_SHIFT_4_ETALON_FUN;
      return code + modV + etalonV + valueV + etalonFunV + etalonTypeV;
   }

   public int codedSizeEncodeRatioDefEtalon(int ratio) {
      // TODO Auto-generated method stub
      return 0;
   }

   public String codedSizeToString1Line(int codedsize) {
      String s = null;
      if (!isCoded(codedsize)) {
         s = "Raw Size Value = " + codedsize;
      } else {
         //we have code
         if (isLinked(codedsize)) {
            s = "Index Link to " + getCodedValue(codedsize);
         } else {
            int value = getCodedValue(codedsize);
            int mode = getCodedMode(codedsize);
            int etalon = getCodedEtalon(codedsize);
            int etalonType = getCodedEtalonType(codedsize);
            int etalonFun = getCodedEtalonFun(codedsize);
            s = "v=" + value;
            s += "mo" + ToStringStaticLayout.toStringMod(mode);
         }
      }
      return s;
   }

   /**
    * 
    *
    * @param fun 
    * @param ctx 
    * @param sizer 
    * @param ctxType 
    * @return 
    */
   private int computeEtalonInPixels(ILayoutable ctx, ByteObject sizer, int ctxType) {
      int fun = sizer.get1(SIZER_OFFSET_06_ET_FUN1);
      if (fun == ET_FUN_0_CTX) {
         //context... caller knows what it is supposed to be
         if (ctxType == CTX_1_WIDTH) {
            return getSizeEtalonW(sizer, ctx);
         } else if (ctxType == CTX_2_HEIGHT) {
            return getSizeEtalonH(sizer, ctx);
         } else {
            toDLogMalformedSizer(sizer, "Unknown Sizer ctxType " + ctxType);
         }
      } else if (fun == ET_FUN_1_WIDTH) {
         return getSizeEtalonW(sizer, ctx);
      } else if (fun == ET_FUN_2_HEIGHT) {
         return getSizeEtalonH(sizer, ctx);
      } else if (fun == ET_FUN_3_MIN) {
         int v = Math.min(getSizeEtalonW(sizer, ctx), getSizeEtalonH(sizer, ctx));
         return v;
      } else if (fun == ET_FUN_4_MAX) {
         int v = Math.max(getSizeEtalonW(sizer, ctx), getSizeEtalonH(sizer, ctx));
         return v;
      } else if (fun == ET_FUN_5_ADD) {
         return getSizeEtalonW(sizer, ctx) + getSizeEtalonH(sizer, ctx);
      } else if (fun == ET_FUN_6_DIFF) {
         return Math.abs(getSizeEtalonW(sizer, ctx) - getSizeEtalonH(sizer, ctx));
      } else if (fun == ET_FUN_7_CTX_INVERSE) {
         if (ctxType == CTX_1_WIDTH) {
            return getSizeEtalonH(sizer, ctx);
         } else if (ctxType == CTX_2_HEIGHT) {
            return getSizeEtalonW(sizer, ctx);
         } else {
            toDLogMalformedSizer(sizer, "Unknown Sizer ctxType " + ctxType);
         }
      } else {
         toDLogMalformedSizer(sizer, "Unknown Sizer Etalon Function " + fun);
      }
      return getSizeEtalonW(sizer, ctx);
   }

   /**
    * 
    *
    * @param val {@link C#LOGIC_1_TOP_LEFT} /  {@link C#LOGIC_2_CENTER} /  {@link C#LOGIC_3_BOTTOM_RIGHT}
    * @param ref the value to modify
    * @param cfun {@link IBOPozer#POS_FUN_0_TOWARDS_CENTER} / {@link IBOPozer#POS_FUN_1_AWAY_CENTER}
    * @param extraSizing 
    * @return 
    */
   private int funPos(int val, int ref, int cfun, int extraSizing) {
      //modified by sizer function. sign defined by function
      if (cfun == POS_FUN_0_TOWARDS_CENTER) {
         if (val == C.LOGIC_2_CENTER) {
            //we are already center
         } else if (val == C.LOGIC_3_BOTTOM_RIGHT) {
            ref -= extraSizing;
         } else {
            ref += extraSizing;
         }
      } else if (cfun == POS_FUN_1_AWAY_CENTER) {
         if (val == C.LOGIC_2_CENTER) {
            //we are already center
         } else if (val == C.LOGIC_3_BOTTOM_RIGHT) {
            ref += extraSizing;
         } else {
            ref -= extraSizing;
         }
      }
      return ref;
   }

   /**
    * 
    *
    * @param value 
    * @return 
    */
   public int getCodedEtalon(int value) {
      int etalonV = (value >> CODED_SIZE_SHIFT_2_ETALON) & CODED_MASK_2_ETALON;
      return etalonV;
   }

   /**
    * 
    *
    * @param value 
    * @return 
    */
   public int getCodedEtalonFun(int value) {
      int etalonFunV = (value >> CODED_SIZE_SHIFT_4_ETALON_FUN) & CODED_MASK_4_ETALON_FUN;
      return etalonFunV;
   }

   /**
    * 
    *
    * @param value 
    * @return 
    */
   public int getCodedEtalonType(int value) {
      int etalonTypeV = (value >> CODED_SIZE_SHIFT_3_ETALON_TYPE) & CODED_MASK_3_ETALON_TYPE;
      return etalonTypeV;
   }

   /**
    * 
    *
    * @param value 
    * @return 
    */
   public int getCodedMode(int value) {
      int modV = (value >> CODED_SIZE_SHIFT_1_MODE) & CODED_MASK_1_MODE;
      return modV;
   }

   /**
    * 
    *
    * @param value 
    * @return 
    */
   public int getCodedValue(int value) {
      return CodedUtils.getCodedValue(value);
   }

   /**
    * 
    * @param sizer a sizer with a delegate
    * @return never null
    * @throws LayoutException if sizer malformed, its supposed to have one.
    */
   public ILayoutDelegate getDelegateFromSizer(ByteObject sizer) {
      ILayoutDelegate del = getDelegateFromSizerNull(sizer);
      if (del == null) {
         throw new LayoutException(lac, "malformed sizer. should have a delegate");
      }
      return del;
   }

   /**
    * 
    * @param sizer a sizer with a delegate
    * @return null if none
    */
   public ILayoutDelegate getDelegateFromSizerNull(ByteObject sizer) {
      ByteObject bo = sizer.getSubFirst(IBOTypesBOC.TYPE_017_REFERENCE_OBJECT);
      if (bo instanceof ByteObjectLayoutDelegate) {
         ByteObjectLayoutDelegate bol = (ByteObjectLayoutDelegate) bo;
         ILayoutDelegate delegate = bol.getDelegate();
         return delegate;
      }
      return null;
   }

   /**
    * 
    *
    * @param value 
    * @return 
    */
   public int getEncodedIndex(int value) {
      return getCodedValue(value);
   }

   /**
    * Apply the function to w and h.
    *
    * @param type 
    * @param w 
    * @param h 
    * @return 
    */
   private int getEtalonFun(int type, int w, int h) {
      if (type == ET_FUN_1_WIDTH) {
         return w;
      } else if (type == ET_FUN_2_HEIGHT) {
         return h;
      } else if (type == ET_FUN_3_MIN) {
         return Math.min(w, h);
      } else if (type == ET_FUN_4_MAX) {
         return Math.max(w, h);
      } else if (type == ET_FUN_5_ADD) {
         return w + h;
      } else if (type == ET_FUN_6_DIFF) {
         return Math.abs(w - h);
      } else {
         //contextual or otherwise.. return w
         return w;
      }
   }

   /**
    * 
    *
    * @param pozerSizer 
    * @param index 
    * @param id 
    * @param layoutable 
    * @return 
    */
   private ILayoutable getEtalonLink(ByteObject pozerSizer, int index, int id, ILayoutable layoutable) {
      ILayoutable layoutableEtalon = layoutable;
      if (index == ET_LINK_0_PARENT) {
         layoutableEtalon = layoutable.getLayoutableParent();
      } else if (index == ET_LINK_1_NAV) {
         layoutableEtalon = layoutable.getLayoutableNav(id);
      } else if (index == ET_LINK_0_ID) {
         layoutableEtalon = layoutable.getLayoutableID(id);
         //ID
      } else if (index == ET_LINK_3_LAYOUTABLE) {
         ByteObject bo = pozerSizer.getSubFirst(IBOTypesBOC.TYPE_017_REFERENCE_OBJECT);
         if (bo instanceof ByteObjectLayoutable) {
            ByteObjectLayoutable bol = (ByteObjectLayoutable) bo;
            layoutableEtalon = bol.getLayoutable();
         } else {
            throw new LayoutException(lac, "");
         }
      } else {
         //error fall back on parent emit a warning
         layoutableEtalon = layoutable.getLayoutableParent();
      }
      return layoutableEtalon;
   }

   /**
    * Return the {@link ILayoutable} that is used 
    *
    * @param pozer the {@link IBOPozer} object defining how to select the Etalon for computing the position of <code>layoutable</code>
    * @param layoutable the {@link ILayoutable} to get the position for
    * @param ctx define x or y for positioning
    * @return a not null {@link ILayoutable}
    * @throws LayoutException if etalon not found
    */
   public ILayoutable getEtalonPozer(ByteObject pozer, ILayoutable layoutable, int ctx) {
      int etalon = pozer.get1(POS_OFFSET_02_ETALON1);
      ILayoutable layoutableEtalon = null;
      if (etalon == POS_ETALON_0_POINT) {
         layoutableEtalon = getEtalonPozer0Point(pozer, ctx);
      } else if (etalon == IBOPozer.POS_ETALON_4_SIZER) {
         layoutableEtalon = getEtalonPozerSizer(pozer, ctx, layoutableEtalon);
      } else if (etalon == IBOPozer.POS_ETALON_2_VIEWCTX) {
         layoutableEtalon = layoutable.getLayoutableViewContext();
      } else if (etalon == IBOPozer.POS_ETALON_1_PARENT) {
         layoutableEtalon = layoutable.getLayoutableParent();
         if (layoutableEtalon == null) {
            //#debug
            toDLog().pNull("getLayoutableParent() is null", layoutable, LayoutOperator.class, "getEtalonPozer", LVL_10_SEVERE, false);
         }
         layoutableEtalon.setDependency(layoutable, ITechLayout.DEPENDENCY_2_POZE);
      } else if (etalon == IBOPozer.POS_ETALON_5_NAV_TOPOLOGY) {
         ByteObject litteral = pozer.getSubFirst(IBOTypesBOC.TYPE_002_LIT_INT);
         int idValue = lac.getBOC().getLitteralIntOperator().getIntValueFromBO(litteral);
         layoutableEtalon = layoutable.getLayoutableNav(idValue);
         //relation de dependance est actée
         layoutableEtalon.setDependency(layoutable, ITechLayout.DEPENDENCY_2_POZE);
      } else if (etalon == IBOPozer.POS_ETALON_6_LAYOUTABLE) {
         ByteObject bo = pozer.getSubFirst(IBOTypesBOC.TYPE_017_REFERENCE_OBJECT);
         if (bo instanceof ByteObjectLayoutable) {
            ByteObjectLayoutable bol = (ByteObjectLayoutable) bo;
            layoutableEtalon = bol.getLayoutable();
         } else {
            throw new LayoutException(lac, "");
         }
         layoutableEtalon.setDependency(layoutable, ITechLayout.DEPENDENCY_2_POZE);
      } else if (etalon == IBOPozer.POS_ETALON_3_LINK) {
         ByteObject litteral = pozer.getSubFirst(IBOTypesBOC.TYPE_002_LIT_INT);
         int idValue = lac.getBOC().getLitteralIntOperator().getIntValueFromBO(litteral);
         layoutableEtalon = layoutable.getLayoutableID(idValue);
         layoutableEtalon.setDependency(layoutable, ITechLayout.DEPENDENCY_2_POZE);
      } else {
         String message = "Etalon value is not valid for pozer ->" + etalon;
         //#debug
         toDLog().pNull(message + " \n" + lac.getFactoryPozer().toStringPozer(pozer, ctx), layoutable, LayoutOperator.class, "getEtalonPozer", LVL_05_FINE, false);

         //bad poser etalon.. throw exception dev warn
         throw new IllegalArgumentException(message);
      }
      if (layoutableEtalon == null) {
         String message = "null etalon (" + etalon + ") for pozer ->" + lac.getFactoryPozer().toStringPozer1Line(pozer, ctx);
         //#debug
         toDLog().pNull(message + " \n" + lac.getFactoryPozer().toStringPozer(pozer, ctx), layoutable, LayoutOperator.class, "getEtalonPozer", LVL_05_FINE, false);

         throw new LayoutException(lac, message);
      }
      return layoutableEtalon;
   }

   /**
    * 
    *
    * @param pozer 
    * @param ctx 
    * @return 
    */
   private ILayoutable getEtalonPozer0Point(ByteObject pozer, int ctx) {
      ILayoutable layoutableEtalon;
      //read value
      int unit = pozer.get1(POS_OFFSET_10_SIZER_FUN1);
      int val = pozer.get4(POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4);
      if (unit == RAW_UNIT_1_DIP) {
         val = lac.getDPI() * val / 160;
      }
      LayoutableRect lr = lac.getDefaultSizeContext();
      if (ctx == CTX_1_WIDTH) {
         lr.set(val, 0, 0, 0);
      } else {
         lr.set(0, val, 0, 0);
      }
      layoutableEtalon = lr;
      return layoutableEtalon;
   }

   /**
    * 
    *
    * @param pozer 
    * @param ctx 
    * @param layoutableEtalon 
    * @return 
    */
   private ILayoutable getEtalonPozerSizer(ByteObject pozer, int ctx, ILayoutable layoutableEtalon) {
      ByteObject sizer = pozer.getSubFirst(IBOTypesLayout.FTYPE_3_SIZER);
      if (sizer != null) {
         int val = getPixelSize(sizer, layoutableEtalon, ctx);
         LayoutableRect lr = lac.getDefaultSizeContext();
         if (ctx == CTX_1_WIDTH) {
            lr.set(val, 0, 0, 0);
         } else {
            lr.set(0, val, 0, 0);
         }
         layoutableEtalon = lr;
      } else {
         throw new IllegalArgumentException("Pozer does not define a sizer");
      }
      return layoutableEtalon;
   }

   /**
    * 
    *
    * @param sizer 
    * @param layoutable 
    * @return 
    */
   private ILayoutable getEtalonSizer(ByteObject sizer, ILayoutable layoutable, int ctx) {
      int etalon = sizer.get1(SIZER_OFFSET_03_ETALON1);
      //goal is to find the right layoutable on which to compute
      ILayoutable layoutableEtalon = layoutable; //to avoid NPE
      if (etalon == ETALON_0_SIZEE_CTX) {
         //nothing
         layoutableEtalon = layoutable;
      } else if (etalon == ETALON_1_VIEWCONTEXT) {
         layoutableEtalon = layoutable.getLayoutableViewContext();
      } else if (etalon == ETALON_2_FONT) {
         layoutableEtalon = layoutable;
      } else if (etalon == ETALON_7_DELEGATE) {
         //etalon is chosen dynamically by the sizer's delegate
         ByteObject bo = sizer.getSubFirst(IBOTypesBOC.TYPE_017_REFERENCE_OBJECT);
         if (bo instanceof ByteObjectLayoutDelegate) {
            ByteObjectLayoutDelegate bol = (ByteObjectLayoutDelegate) bo;
            ILayoutDelegate delegate = bol.getDelegate();
            layoutableEtalon = delegate.getDelegateEtaloneSizer(sizer, layoutable, ctx);
         } else if (bo instanceof ByteObjectLayoutable) {
            ByteObjectLayoutable bol = (ByteObjectLayoutable) bo;
            layoutableEtalon = bol.getLayoutable();
         } else {
            throw new LayoutException(lac, "");
         }
      } else if (etalon == ETALON_4_PARENT) {
         layoutableEtalon = layoutable.getLayoutableParent();
         //no need to add a dependency for parent
      } else if (etalon == ETALON_5_LINK) {
         int index = sizer.get1(SIZER_OFFSET_04_ET_SUBTYPE1); //type of linked defined in payload
         int param = sizer.get1(SIZER_OFFSET_07_ET_DATA2); //65k ids
         layoutableEtalon = getEtalonLink(sizer, index, param, layoutable);
         layoutableEtalon.setDependency(layoutable, ITechLayout.DEPENDENCY_1_SIZE);
      }
      return layoutableEtalon;
   }

   public LayouterCtx getLC() {
      return lac;
   }

   /**
    * Returns the maximum defined.
    *
    * @param sizer 
    * @param c 
    * @param ctx 
    * @return -1 if none
    */
   public int getMax(ByteObject sizer, ILayoutable c, int ctx) {
      return getMinMax(sizer, c, ctx, SIZER_FLAG_6_IS_MAX, SIZER_FLAG_4_MAXIMUM, IBOTypesLayout.RELATION_2_MAX);
   }

   /**
    * 
    *
    * @param sizer 
    * @param c 
    * @return 
    */
   public int getMaxH(ByteObject sizer, ILayoutable c) {
      return getMax(sizer, c, CTX_2_HEIGHT);
   }

   /**
    * 
    *
    * @param sizer 
    * @param c 
    * @return 
    */
   public int getMaxW(ByteObject sizer, ILayoutable c) {
      return getMax(sizer, c, CTX_1_WIDTH);
   }

   /**
    * Returns the maximum defined.
    *
    * @param sizer 
    * @param c 
    * @param ctx 
    * @return -1 if none
    */
   public int getMin(ByteObject sizer, ILayoutable c, int ctx) {
      return getMinMax(sizer, c, ctx, SIZER_FLAG_5_IS_MIN, SIZER_FLAG_3_MINIMUM, IBOTypesLayout.RELATION_1_MIN);
   }

   /**
    * 
    *
    * @param sizer 
    * @param c 
    * @param ctx 
    * @param isFlag 
    * @param hasFlag 
    * @param relationType 
    * @return -1 if not defined
    */
   public int getMinMax(ByteObject sizer, ILayoutable c, int ctx, int isFlag, int hasFlag, int relationType) {
      if (sizer.hasFlag(SIZER_OFFSET_01_FLAG, isFlag)) {
         return getPixelSize(sizer, c, ctx);
      }
      if (sizer.hasFlag(SIZER_OFFSET_01_FLAG, hasFlag)) {
         int typeByteObject = IBOTypesBOC.TYPE_019_RELATIONSHIP;
         ByteObject relationMin = sizer.getSubFirst(typeByteObject, ITechRelation.RELATION_OFFSET_02_TYPE1, 1, relationType);
         if (relationMin == null) {
            //#debug
            toDLogMalformedMinMax(sizer, relationType);
            return -1;
         }
         ByteObject minS = relationMin.getSubFirst(IBOTypesLayout.FTYPE_3_SIZER);
         if (minS == null) {
            //#debug
            toDLogMalformedMinMax(sizer, relationType);
            return -1;
         } else {
            int minSize = getPixelSize(minS, c, ctx);
            return minSize;
         }
      }
      return -1;
   }

   /**
    * Computes a pixel position value on the x axis for {@link ILayoutable}
    * 
    * When layoutable has 2 pozers, constraining the width
    *
    * @param pozerX 
    * @param layoutable 
    * @return 
    */
   public int getPixelPozXPure(ByteObject pozerX, ILayoutable layoutable) {
      //#debug
      checkNull(layoutable);

      //construct for each layoutable, its dependencies
      //own sort them in asc. it will first layout those without dependencies.
      //

      ILayoutable layoutableEtalon = getEtalonPozer(pozerX, layoutable, CTX_1_WIDTH);
      layoutableEtalon.getDependencies(); //TODO get depedencies for X
      layoutableEtalon.layoutUpdatePositionXCheck();

      layoutableEtalon.layoutUpdateSizeWCheck();

      int xEtalon = layoutableEtalon.getPozeX();
      int wEtalon = layoutableEtalon.getSizeDrawnWidth();
      int alignDest = pozerX.get1(POS_OFFSET_04_ANCHOR_ETALON1); //this decides the dependency
      int x = getPosPure(alignDest, wEtalon, xEtalon);

      //check for margin/padding
      if (pozerX.hasFlag(IBOPozer.POS_OFFSET_01_FLAG, POS_FLAG_1_SIZER)) {
         ByteObject sizer = pozerX.getSubFirst(IBOTypesLayout.FTYPE_3_SIZER);
         int sizerValue = getPixelSize(sizer, layoutable, CTX_1_WIDTH);
         int fun = pozerX.get1(POS_OFFSET_10_SIZER_FUN1);
         x = funPos(alignDest, x, fun, sizerValue);
      }

      return x;
   }

   /**
    * Computes the x position for {@link ILayoutable} using the given pozer.
    * 
    * @param pozerX
    * @param layoutable the {@link ILayoutable} to get the position for
    * @return
    */
   public int getPixelPozXWidth(ByteObject pozerX, ILayoutable layoutable) {

      //#debug
      checkNull(layoutable);

      //get a reference to our etalon
      ILayoutable layoutableEtalon = getEtalonPozer(pozerX, layoutable, CTX_1_WIDTH);
      //the pozer compute position relative to the box
      //compute relative or absolute?
      layoutableEtalon.layoutUpdatePositionXCheck(); //make sure the etalon has been sized and positioned
      layoutableEtalon.layoutUpdateSizeWCheck(); //make sure the etalon has been sized and positioned

      int etalonX = layoutableEtalon.getPozeX();
      int etalonW = layoutableEtalon.getSizeDrawnWidth();

      layoutable.layoutUpdateSizeWCheck(); //we need our width computed to be able locate endof
      int objectSize = layoutable.getSizeDrawnWidth();

      int x = getPos(pozerX, etalonX, etalonW, objectSize, layoutable, CTX_1_WIDTH);
      return x;
   }

   /**
    * The value is valid in the reference of the etalon.
    * @param pozerY
    * @param layoutable
    * @return
    */
   public int getPixelPozYHeight(ByteObject pozerY, ILayoutable layoutable) {
      ILayoutable layoutableEtalon = getEtalonPozer(pozerY, layoutable, CTX_2_HEIGHT);
      //the pozer compute position relative to the box
      //compute relative or absolute?

      layoutableEtalon.layoutUpdatePositionYCheck(); //make sure the etalon has been sized and positioned
      layoutableEtalon.layoutUpdateSizeHCheck(); //make sure the etalon has been sized and positioned

      int fx = layoutableEtalon.getPozeY();
      int fw = layoutableEtalon.getSizeDrawnHeight();

      layoutable.layoutUpdateSizeHCheck();
      int objectSize = layoutable.getSizeDrawnHeight();

      int y = getPos(pozerY, fx, fw, objectSize, layoutable, CTX_2_HEIGHT);
      return y;
   }

   /**
    * 
    *
    * @param pozerX 
    * @param layoutable 
    * @return 
    */
   public int getPixelPozYPure(ByteObject pozerX, ILayoutable layoutable) {
      //#debug
      checkNull(layoutable);

      ILayoutable layoutableEtalon = getEtalonPozer(pozerX, layoutable, CTX_2_HEIGHT);
      layoutableEtalon.layoutUpdatePositionYCheck();
      layoutableEtalon.layoutUpdateSizeHCheck();

      int fy = layoutableEtalon.getPozeY();
      int fh = layoutableEtalon.getSizeDrawnHeight();
      int alignDest = pozerX.get1(POS_OFFSET_04_ANCHOR_ETALON1);
      int x = getPosPure(alignDest, fh, fy);

      //check for margin/padding
      if (pozerX.hasFlag(IBOPozer.POS_OFFSET_01_FLAG, POS_FLAG_1_SIZER)) {
         ByteObject sizer = pozerX.getSubFirst(IBOTypesLayout.FTYPE_3_SIZER);
         int sizerValue = getPixelSize(sizer, layoutable, CTX_2_HEIGHT);
         int fun = pozerX.get1(POS_OFFSET_10_SIZER_FUN1);
         x = funPos(alignDest, x, fun, sizerValue);
      }

      return x;
   }

   /**
    * 
    * {@link LayouterCtx} provides global value such as DPI, Scaling etc.
    * 
    * @param sizer {@link IBOSizer}
    * @param layoutable the sizing context on which to compute etalon size of The Layoutable
    * @param ctx width or heigth
    * @return
    */
   public int getPixelSize(ByteObject sizer, ILayoutable layoutable, int ctx) {
      sizer.checkType(FTYPE_3_SIZER);

      //#debug
      checkCtx(ctx);

      int mode = sizer.get1(SIZER_OFFSET_02_MODE1);
      int result = 0;
      if (mode == MODE_0_RAW_UNITS) {
         result = getPixelSizeRawUnit(sizer);
      } else if (mode == MODE_1_DELEGATE) {
         result = getPixelSizeDelegate(sizer, layoutable, ctx);
      } else if (mode == MODE_2_FUNCTION) {
         result = getPixelSizeFunction(sizer, layoutable, ctx);
         if (result <= 0) {
            result = 1; //avoid problems
         }
      } else if (mode == MODE_3_SCALE) {
         int value = sizer.get2(SIZER_OFFSET_08_VALUE2);
         int fun = sizer.get1(SIZER_OFFSET_06_ET_FUN1);
         result = lac.getScalePixel(value, fun);
      } else if (mode == MODE_5_SIZERS) {
         //check no loop
         if (sizer.hasReferenceLoop()) {
            throw new IllegalArgumentException();
         }
         //when null what to do? LayoutException
         ByteObject sizer1 = sizer.getSubAtIndexNull(0);
         ByteObject sizer2 = sizer.getSubAtIndexNull(1);
         int val1 = getPixelSize(sizer1, layoutable, ctx);
         int val2 = getPixelSize(sizer2, layoutable, ctx);
         int fun = sizer.get1(SIZER_OFFSET_06_ET_FUN1);
         result = getEtalonFun(fun, val1, val2);
      } else if (mode == MODE_6_POZERS) {
         return getPixelSizePozerDistance(sizer, layoutable, ctx);
      }
      result = applyMinMax(sizer, layoutable, ctx, result);
      return result;
   }

   /**
    * Returns value if s is null.
    * <br>
    * 
    * @param sizer {@link ISizer} definition
    * @param value
    * @return
    */
   public int getPixelSize(ByteObject sizer, int value) {
      return getPixelSize(sizer, value, value, value);
   }

   /**
    * 
    *
    * @param s 
    * @param w 
    * @param h 
    * @param ctx 
    * @return 
    */
   public int getPixelSize(ByteObject s, int w, int h, int ctx) {
      LayoutableRect lr = lac.getDefaultSizeContext();
      lr.set(w, h);
      return getPixelSize(s, lr, ctx);
   }

   /**
    * Called when mode is {@link ITechLayout#MODE_1_DELEGATE}
    *
    * @param sizer 
    * @param layoutable 
    * @param ctx 
    * @return 
    */
   private int getPixelSizeDelegate(ByteObject sizer, ILayoutable layoutable, int ctx) {
      int delegateEtalon = sizer.get1(SIZER_OFFSET_03_ETALON1);
      if (delegateEtalon == DELEGATE_ETALON_1_PARENT_MAX) {
         ILayoutable layoutableParent = layoutable.getLayoutableParent();
         if (layoutableParent == null) {
            throw new IllegalArgumentException();
         }
         //
         if (layoutableParent instanceof ILayoutDelegateMax) {
            if (ctx == CTX_1_WIDTH) {
               return ((ILayoutDelegateMax) layoutableParent).getSizeMaxWidth(layoutable);
            } else {
               return ((ILayoutDelegateMax) layoutableParent).getSizeMaxHeight(layoutable);
            }
         } else {
            throw new IllegalArgumentException("Not an instance of " + ILayoutDelegateMax.class);
         }

      } else if (delegateEtalon == DELEGATE_ETALON_0_REFERENCE) {
         ByteObject bo = sizer.getSubFirst(IBOTypesBOC.TYPE_017_REFERENCE_OBJECT);
         if (bo instanceof ByteObjectLayoutDelegate) {
            ByteObjectLayoutDelegate bol = (ByteObjectLayoutDelegate) bo;
            ILayoutDelegate delegate = bol.getDelegate();
            if (ctx == CTX_1_WIDTH) {
               return delegate.getDelegateSizeWidth(sizer, layoutable);
            } else {
               return delegate.getDelegateSizeHeight(sizer, layoutable);
            }
         } else {
            throw new LayoutException(lac, "");
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   private int getPixelSizeFunction(ByteObject sizer, ILayoutable layoutable, int ctx) {
      int etalonValue = computeEtalonInPixels(layoutable, sizer, ctx);
      int funOp = sizer.get1(SIZER_OFFSET_10_OP_FUN1);

      switch (funOp) {
         case FUNCTION_OP_00_NONE:
            return etalonValue;
         case FUNCTION_OP_01_ADD:
            return etalonValue + sizer.get2(SIZER_OFFSET_08_VALUE2);
         case FUNCTION_OP_02_MINUS:
            return etalonValue - sizer.get2(SIZER_OFFSET_08_VALUE2);
         case FUNCTION_OP_03_MULTIPLY:
            return etalonValue * sizer.get2(SIZER_OFFSET_08_VALUE2);
         case FUNCTION_OP_04_DIVIDE:
            return etalonValue / sizer.get2(SIZER_OFFSET_08_VALUE2);
         case FUNCTION_OP_05_RATIO:
            return getPixelSizeRatio(sizer, etalonValue);
         case FUNCTION_OP_06_X_FOR_Y:
            return getPixelSizeXForY(sizer, etalonValue);

         default:
            throw new IllegalArgumentException("funOperator " + funOp);
      }
   }

   /**
    * Compute the height of the {@link ILayoutable} 
    *
    * @param sizer 
    * @param lay 
    * @return 
    */
   public int getPixelSizeH(ByteObject sizer, ILayoutable lay) {
      return getPixelSize(sizer, lay, CTX_2_HEIGHT);
   }

   /**
    * 
    *
    * @param s 
    * @param w 
    * @param h 
    * @return 
    */
   public int getPixelSizeH(ByteObject s, int w, int h) {
      LayoutableRect sc = new LayoutableRect(lac, w, h);
      return getPixelSize(s, sc, CTX_2_HEIGHT);
   }

   /**
    * 
    *
    * @param sizer 
    * @param layoutable 
    * @param ctx 
    * @return 
    */
   private int getPixelSizePozerDistance(ByteObject sizer, ILayoutable layoutable, int ctx) {
      //at least one
      int val1 = 0;
      int val2 = 0;
      ByteObject pozer1 = sizer.getSubAtIndexNull(0);
      if (pozer1 == null) {
         //malformed sizer
         throw new LayoutException(lac, "Mode Pozer Distance requires a pozer to be added");
      }
      if (sizer.hasFlag(SIZER_OFFSET_01_FLAG, SIZER_FLAG_8_IMPLICIT)) {
         //get the poser value
         val1 = getPosition(layoutable, ctx, pozer1);
         val2 = getPosition(layoutable, ctx); //implicit position

      } else {
         ByteObject pozer2 = sizer.getSubAtIndexNull(1);
         if (pozer2 == null) {
            throw new LayoutException(lac, "Mode Pozer Distance requires 2 pozers to be added");
         }
         //compute sizer of pozers
         val1 = getPosition(layoutable, ctx, pozer1);
         val2 = getPosition(layoutable, ctx, pozer2);
      }
      return Math.abs(val1 - val2);
   }

   private int getPixelSizeRatio(ByteObject sizer, int etalonValue) {
      int result = 0;
      int fractionTop = sizer.get2(SIZER_OFFSET_08_FRACTION_TOP2);
      int fractionBottom = sizer.get2(SIZER_OFFSET_09_FRACTION_BOT2);
      //because results precision is wrong when
      if (fractionTop == fractionBottom) {
         result = etalonValue;
      } else if (fractionBottom == 0 || fractionBottom == 1) {
         result = etalonValue * fractionTop;
      } else {
         int mul = etalonValue * fractionTop;
         result = mul / fractionBottom;
      }
      return result;
   }

   /**
    * 
    *
    * @param sizer 
    * @return 
    */
   private int getPixelSizeRawUnit(ByteObject sizer) {
      int rawUnit = sizer.get1(SIZER_OFFSET_03_ETALON1);
      int value = sizer.get2(SIZER_OFFSET_08_VALUE2);
      if (rawUnit == RAW_UNIT_0_PIXEL) {
         return value;
      } else if (rawUnit == RAW_UNIT_1_DIP) {
         return lac.getDPI() * value / 160;
      } else {
         throw new IllegalArgumentException();
      }
   }

   /**
    * Compute the sizer for the given 0,0,w,h root layoutable acting as parent/viewcontext
    * 
    * Can only be called in UI thread because it is using a shared object for performance.
    * @param s
    * @param w
    * @param h
    * @param ctx
    * @return
    */
   public int getPixelSizeUIThead(ByteObject s, int w, int h, int ctx) {
      LayoutableRect lr = lac.getDefaultSizeContext();
      lr.set(w, h);
      return getPixelSize(s, lr, ctx);
   }

   /**
    * 
    * @see LayoutOperator#getPixelSize(ByteObject, ILayoutable, int) 
    * 
    * @param sizer {@link IBOSizer}
    * @param layoutable the sizing context on which to compute etalon size of The Layoutable
    * @return 
    */
   public int getPixelSizeW(ByteObject sizer, ILayoutable sc) {
      return getPixelSize(sizer, sc, CTX_1_WIDTH);
   }

   /**
    * 
    *
    * @param s 
    * @param w 
    * @param h 
    * @return 
    */
   public int getPixelSizeW(ByteObject s, int w, int h) {
      LayoutableRect sc = new LayoutableRect(lac, w, h);
      return getPixelSize(s, sc, CTX_1_WIDTH);
   }

   private int getPixelSizeXForY(ByteObject sizer, int etalonValue) {
      int x = sizer.get2(SIZER_OFFSET_08_FUN_X2);
      int y = sizer.get2(SIZER_OFFSET_09_FUN_Y2);
      int result = 0;
      do {
         etalonValue -= y;
         result += x;
      } while (etalonValue > 0);
      return result;
   }

   /**
    * 
    * @param s pozer definition
    * @param etalonOrigin padding
    * @param etalonSize
    * @param objectSize
    * @param sc
    * @param ctx
    * @return
    */
   public int getPos(ByteObject s, int etalonOrigin, int etalonSize, int objectSize, ILayoutable sc, int ctx) {
      s.checkType(FTYPE_4_POSITION);

      int alignSrc = s.get1(POS_OFFSET_07_ANCHOR_POZEE1);
      int alignDest = s.get1(POS_OFFSET_04_ANCHOR_ETALON1);
      int res = 0;
      if (etalonSize == 0) {
         res = getPosAlign(alignSrc, objectSize, etalonOrigin);
      } else {
         res = getPosAlign(alignSrc, alignDest, etalonOrigin, etalonSize, objectSize);
      }
      if (s.hasFlag(POS_OFFSET_01_FLAG, POS_FLAG_1_SIZER)) {
         ByteObject sizer = s.getSubAtIndexNull(0);
         if (sizer == null) {
            throw new LayoutException(lac, "null sizer");
         } else {
            int size = getPixelSize(sizer, sc, ctx);
            int fun = s.get1(POS_OFFSET_10_SIZER_FUN1);
            res = funPos(alignSrc, res, fun, size);
         }
      }
      return res;
   }

   /**
    * Reads the alignement. if complex coded value, compute it.
    * 
    * Provide the context to compute sizing distance
    *
    * @param index 
    * @param bo 
    * @param topleftMost 
    * @param totalSize 
    * @param objectSize 
    * @param w 
    * @param h 
    * @param ctx 
    * @return 
    */
   public int getPosAlign(int index, ByteObject bo, int topleftMost, int totalSize, int objectSize, int w, int h, int ctx) {
      int val = bo.get4(index);
      if (CodedUtils.isCoded(val)) {
         // val is stored in the etalon type. Etalon type is NONE
         int align = getCodedEtalonType(val);
         int ref = getPosAlign(align, topleftMost, totalSize, objectSize);
         //compute sizer
         int cfun = getCodedEtalonFun(val);
         int extraSizing = 0;
         if (CodedUtils.isLinked(val)) {
            //sizer index
            int sindex = CodedUtils.codedIndexRefDecode(val);
            ByteObject sizer = bo.getSubAtIndexNull(sindex);
            extraSizing = getPixelSize(sizer, w, h, ctx);
         } else {
            extraSizing = codedSizeDecode(val, w, h, ctx);
         }
         ref = funPos(val, ref, cfun, extraSizing);
         return ref;
      } else {
         return getPosAlign(val, topleftMost, totalSize, objectSize);
      }
   }

   /**
    * 
    *
    * @param alignSrc 
    * @param objectSize 
    * @param topleftMost 
    * @return 
    */
   public int getPosAlign(int alignSrc, int objectSize, int topleftMost) {
      int val = topleftMost;
      if (alignSrc == C.LOGIC_2_CENTER) {
         val = topleftMost - (objectSize / 2);
      } else if (alignSrc == C.LOGIC_1_TOP_LEFT) {
         val = topleftMost;
      } else if (alignSrc == C.LOGIC_3_BOTTOM_RIGHT) {
         val = topleftMost - objectSize;
      }
      return val;
   }

   /**
    * 
    * @param ha
    * @param topleftMost
    * @param totalSize
    * @param objectSize
    * @return
    */
   public int getPosAlign(int ha, int topleftMost, int totalSize, int objectSize) {
      int val = 0;
      if (ha == C.LOGIC_2_CENTER) {
         val = (totalSize - objectSize) / 2;
      } else if (ha == C.LOGIC_3_BOTTOM_RIGHT) {
         val = (totalSize - objectSize);
      }
      return topleftMost + val;
   }

   /**
    * 
    *
    * @param alignSrc 
    * @param alignDest 
    * @param topleftMost 
    * @param totalSize 
    * @param objectSize 
    * @return 
    */
   public int getPosAlign(int alignSrc, int alignDest, int topleftMost, int totalSize, int objectSize) {
      int val = 0;
      if (alignSrc == C.LOGIC_2_CENTER) {
         if (alignDest == C.LOGIC_1_TOP_LEFT) {
            val = 0 - (objectSize / 2);
         } else if (alignDest == C.LOGIC_2_CENTER) {
            val = (totalSize - objectSize) / 2;
         } else if (alignDest == C.LOGIC_3_BOTTOM_RIGHT) {
            val = totalSize - (objectSize / 2);
         } else {
            //dev warn
         }
      } else if (alignSrc == C.LOGIC_3_BOTTOM_RIGHT) {
         if (alignDest == C.LOGIC_1_TOP_LEFT) {
            val = -objectSize;
         } else if (alignDest == C.LOGIC_2_CENTER) {
            val = (totalSize) / 2 - (objectSize);
         } else if (alignDest == C.LOGIC_3_BOTTOM_RIGHT) {
            val = (totalSize - objectSize);
         } else {
            //devWarn. no sizing
         }
      } else if (alignSrc == C.LOGIC_1_TOP_LEFT) {
         //src is left
         if (alignDest == C.LOGIC_1_TOP_LEFT) {
            val = 0;
         } else if (alignDest == C.LOGIC_2_CENTER) {
            val = (totalSize) / 2;
         } else if (alignDest == C.LOGIC_3_BOTTOM_RIGHT) {
            val = totalSize;
         } else {
            //devWarn. no sizing
         }
      } else {
         //#debug
         throw new IllegalArgumentException("" + alignSrc);
      }
      return topleftMost + val;
   }

   /**
    * 
    * @param pozer
    * @param sc 
    * @param ctx
    * @return
    */
   private int getPosition(ByteObject pozer, ILayoutable sc, int ctx) {

      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    *
    * @param layoutable 
    * @param ctx 
    * @return 
    */
   private int getPosition(ILayoutable layoutable, int ctx) {
      int val1;
      if (ctx == CTX_1_WIDTH) {
         //x has been computed
         val1 = layoutable.getPozeXComputed();
      } else {
         val1 = layoutable.getPozeYComputed();
      }
      return val1;
   }

   /**
    * 
    *
    * @param layoutable 
    * @param ctx 
    * @param pozer1 
    * @return 
    */
   private int getPosition(ILayoutable layoutable, int ctx, ByteObject pozer1) {
      int val2;
      if (ctx == CTX_1_WIDTH) {
         val2 = getPixelPozXWidth(pozer1, layoutable);
      } else {
         val2 = getPixelPozYHeight(pozer1, layoutable);
      }
      return val2;
   }

   /**
    * 
    *
    * @param align 
    * @param objectSize 
    * @param xyLeftTop 
    * @return 
    */
   public int getPosPure(int align, int objectSize, int xyLeftTop) {
      int val = xyLeftTop;
      if (align == C.LOGIC_2_CENTER) {
         val = xyLeftTop + (objectSize / 2);
      } else if (align == C.LOGIC_1_TOP_LEFT) {
         val = xyLeftTop;
      } else if (align == C.LOGIC_3_BOTTOM_RIGHT) {
         val = xyLeftTop + objectSize;
      }
      return val;
   }

   /**
    * Once etalon has been found, get one of its properties.
    *
    * @param sizer 
    * @param layoutable 
    * @return 
    */
   private int getSizeEtalonCtxH(ByteObject sizer, ILayoutable layoutable) {
      int type = sizer.get1(SIZER_OFFSET_05_ET_PROPERTY1);
      return layoutable.getSizePropertyValueH(type);
   }

   /**
    * Once etalon has been found, get one of its properties.
    *
    * @param sizer 
    * @param layoutable 
    * @return 
    */
   private int getSizeEtalonCtxW(ByteObject sizer, ILayoutable layoutable) {
      int type = sizer.get1(SIZER_OFFSET_05_ET_PROPERTY1);
      return layoutable.getSizePropertyValueW(type);
   }

   /**
    * 1st resolve the target {@link ILayoutable}
    * 
    * Of context.
    *
    * @param sizer 
    * @param layoutable 
    * @return 
    */
   public int getSizeEtalonH(ByteObject sizer, ILayoutable layoutable) {
      //goal is to find the right layoutable on which to compute
      ILayoutable layoutableEtalon = getEtalonSizer(sizer, layoutable, CTX_2_HEIGHT);
      if (layoutable != layoutableEtalon) {
         layoutableEtalon.layoutUpdateSizeCheck();
      }
      return getSizeEtalonCtxH(sizer, layoutableEtalon);
   }

   /**
    * 
    *
    * @param sizer 
    * @param layoutable 
    * @return 
    */
   public int getSizeEtalonW(ByteObject sizer, ILayoutable layoutable) {
      ILayoutable layoutableEtalon = getEtalonSizer(sizer, layoutable, CTX_1_WIDTH);
      if (layoutableEtalon == null) {
         //#debug
         toDLog().pNull("sizer=" + sizer.toString(), layoutable, LayoutOperator.class, "getSizeEtalonW");
      }
      //if etalon is yourself ?
      if (layoutable != layoutableEtalon) {
         layoutableEtalon.layoutUpdateSizeCheck();
      }
      return getSizeEtalonCtxW(sizer, layoutableEtalon);
   }

   /**
    * Get method for TBLR values given a position.
    * <br>
    * The ViewContext is the w and h
    * <br>
    * @param tblr
    * @param pos {@link C#POS_0_TOP} ...
    * @return
    */
   public int getTBLRValue(ByteObject tblr, int pos) {
      int flags = tblr.get1(TBLR_OFFSET_01_FLAG);
      if (BitUtils.hasFlag(flags, TBLR_FLAG_4_SAME_VALUE)) {
         return tblr.get4(IBOTblr.TBLR_OFFSET_03_DATA4);
      } else {
         //how to read the value
         if (BitUtils.hasFlag(flags, TBLR_FLAG_1_USING_ARRAY)) {
            ByteObject intarray = tblr.getSubFirst(IBOTypesBOC.TYPE_007_LIT_ARRAY_INT);
            return boc.getLitteralIntOperator().getLitteralArrayValueAt(intarray, pos);
         }
         if (pos == C.POS_0_TOP) {
            if (BitUtils.hasFlag(flags, TBLR_FLAG_5_TRANS_TOP)) {
               return 0;
            } else {
               return tblr.get1(TBLR_OFFSET_03_DATA4);
            }
         } else if (pos == C.POS_1_BOT) {
            if (BitUtils.hasFlag(flags, TBLR_FLAG_6_TRANS_BOT)) {
               return 0;
            } else {
               return tblr.get1(TBLR_OFFSET_03_DATA4 + 1);
            }
         } else if (pos == C.POS_2_LEFT) {
            if (BitUtils.hasFlag(flags, TBLR_FLAG_7_TRANS_LEFT)) {
               return 0;
            } else {
               return tblr.get1(TBLR_OFFSET_03_DATA4 + 2);
            }
         } else if (pos == C.POS_3_RIGHT) {
            if (BitUtils.hasFlag(flags, TBLR_FLAG_8_TRANS_RIGHT)) {
               return 0;
            } else {
               return tblr.get1(TBLR_OFFSET_03_DATA4 + 3);
            }
         } else {
            throw new IllegalArgumentException();
         }
      }
   }

   /**
    * Returns 0 if failure to compute
    * <br>
    * Logs a Dev BIP.
    * <br>
    * @param tblr
    * @param pos
    * @param sizer
    * @param sc
    * @return
    */
   public int getTBLRValue(ByteObject tblr, int pos, ILayoutable layoutable) {
      int type = tblr.get1(TBLR_OFFSET_02_TYPE1);
      int value = 0;
      if (type == TYPE_0_PIXEL_VALUE) {
         value = tblr.get4(TBLR_OFFSET_03_DATA4);
      } else if (type == TYPE_1_CODED_VALUE) {
         int codedsize = tblr.get4(TBLR_OFFSET_03_DATA4);
         if (pos == C.POS_0_TOP || pos == C.POS_1_BOT) {
            value = codedSizeDecodeH(codedsize, layoutable);
         } else {
            value = codedSizeDecodeW(codedsize, layoutable);
         }
      } else if (type == TYPE_2_SIZER) {
         boolean isOne = tblr.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_4_SAME_VALUE);
         int ctx = ITechLayout.CTX_1_WIDTH;
         if (pos == C.POS_0_TOP || pos == C.POS_1_BOT) {
            ctx = ITechLayout.CTX_2_HEIGHT;
         }
         int mod = 0;
         if (!isOne) {
            if (pos == C.POS_1_BOT) {
               mod = 1;
            } else if (pos == C.POS_2_LEFT) {
               mod = 2;
            } else if (pos == C.POS_3_RIGHT) {
               mod = 3;
            }
         }
         ByteObject sizer = tblr.getSubAtIndexNull(mod);
         if (sizer != null) {
            value = getPixelSize(sizer, layoutable, ctx);
         } else {
            //transparent sizer
            value = 0;
         }
      } else {
         throw new RuntimeException();
      }
      return value;
   }

   public int getTBLRValue(ByteObject tblr, int pos, int x, int y, int w, int h) {
      LayoutableRect layoutable = new LayoutableRect(lac, w, h);
      return getTBLRValue(tblr, pos, layoutable);
   }

   /**
    * 
    * @param tblr
    * @param x
    * @param y
    * @param w
    * @param h
    * @return
    */
   public int[] getTBLRValues(ByteObject tblr, int x, int y, int w, int h) {
      LayoutableRect layoutable = new LayoutableRect(lac, w, h);
      int[] vs = new int[4];
      vs[0] = getTBLRValue(tblr, C.POS_0_TOP, layoutable);
      vs[1] = getTBLRValue(tblr, C.POS_1_BOT, layoutable);
      vs[2] = getTBLRValue(tblr, C.POS_2_LEFT, layoutable);
      vs[3] = getTBLRValue(tblr, C.POS_3_RIGHT, layoutable);
      return vs;
   }

   /**
    * Check if both sizers use the same etalon reference.
    *
    * @param sizerW 
    * @param sizerH 
    * @return 
    */
   public boolean isSameEtalon(ByteObject sizerW, ByteObject sizerH) {
      if (sizerW.get1(SIZER_OFFSET_03_ETALON1) == sizerH.get1(SIZER_OFFSET_03_ETALON1)) {
         if (sizerW.get1(SIZER_OFFSET_05_ET_PROPERTY1) == sizerH.get1(SIZER_OFFSET_05_ET_PROPERTY1)) {
            return true;
         }
      }
      return false;
   }

   /**
    * 
    *
    * @param bo 
    * @param index 
    * @param sizer 
    */
   public void linkSizer(ByteObject bo, int index, ByteObject sizer) {
      sizer.checkType(FTYPE_3_SIZER);
      int in = bo.addByteObject(sizer);
      int encodedRef = CodedUtils.codedIndexRefEncode(in);
      bo.set4(index, encodedRef);
   }

   /**
    * Link sizer to the 4 byes reference.
    * <br>
    *
    * @param obj 
    * @param index 
    * @param sizer Type check {@link ITypesBentleyFw#FTYPE_3_SIZER}
    */
   public void linkSizer4(ByteObject obj, int index, ByteObject sizer) {
      sizer.checkType(FTYPE_3_SIZER);
      int in = obj.addByteObject(sizer);
      int val = getEncodedIndex(in);
      obj.set4(index, val);
   }

   //#mdebug
   /**
    * 
    *
    * @param sizer 
    * @param relationType 
    */
   private void toDLogMalformedMinMax(ByteObject sizer, int relationType) {
      if (relationType == IBOTypesLayout.RELATION_1_MIN) {
         toDLog().pNull("Malformed Sizer on Minium. Returning -1", sizer, LayoutOperator.class, "toDLogMalformedMinMax", LVL_05_FINE, true);
      } else {
         toDLog().pNull("Malformed Sizer on Maximum. Returning -1", sizer, LayoutOperator.class, "toDLogMalformedMinMax", LVL_05_FINE, true);
      }
   }

   /**
    * 
    *
    * @param sizer 
    * @param msg 
    */
   private void toDLogMalformedSizer(ByteObject sizer, String msg) {
      toDLog().pNull(msg, sizer, LayoutOperator.class, "toDLogMalformedSizer", LVL_05_FINE, true);
   }

   public void toString(Dctx dc) {
      dc.root(this, LayoutOperator.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayoutOperator.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
