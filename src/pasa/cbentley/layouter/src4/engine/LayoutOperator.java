/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.BOAbstractOperator;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.byteobjects.src4.tech.ITechRelation;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.layouter.src4.ctx.LayoutException;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.IBOTypesLayout;
import pasa.cbentley.layouter.src4.interfaces.ILayoutRequestListener;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.interfaces.SizeResult;
import pasa.cbentley.layouter.src4.tech.ITechCoded;
import pasa.cbentley.layouter.src4.tech.ITechLayout;
import pasa.cbentley.layouter.src4.tech.ITechPozer;
import pasa.cbentley.layouter.src4.tech.ITechSizer;

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
public class LayoutOperator extends BOAbstractOperator implements IBOTypesLayout, ITechPozer, ITechSizer, ITechLayout, ITechCoded {

   /**
    * 
    */
   private LayouterCtx lc;

   /**
    * 
    *
    * @param lac 
    */
   public LayoutOperator(LayouterCtx lac) {
      super(lac.getBOC());
      this.lc = lac;
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
      return codedSizeDecode(value, lc.getDefaultSizeContext(), CTX_1_WIDTH);
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
         LayoutableRect sc = new LayoutableRect(lc, w, h);
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
      return codedSizeDecode(value, lc.getDefaultSizeContext(), CTX_1_WIDTH);
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
         int valueV = (value >> CODED_SIZE_SHIFT_0_VALUE) & CODED_MASK_0_VALUE;
         int modV = (value >> CODED_SIZE_SHIFT_1_MODE) & CODED_MASK_1_MODE;
         int etalonV = (value >> CODED_SIZE_SHIFT_2_ETALON) & CODED_MASK_2_ETALON;
         int etalonTypeV = (value >> CODED_SIZE_SHIFT_3_ETALON_TYPE) & CODED_MASK_3_ETALON_TYPE;
         int etalonFunV = (value >> CODED_SIZE_SHIFT_4_ETALON_FUN) & CODED_MASK_4_ETALON_FUN;
         //set invalid etalon
         //TODO create a thread local OR one sizable for each thread!
         //OR OPTIMIZE down to the bits. use this method for generic testing
         ByteObject sizer = lc.getSizerFactory().getSizer(modV, valueV, etalonV, etalonTypeV, etalonFunV);

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
      LayoutableRect sc = new LayoutableRect(lc, w, h);
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
    * When value is above {@link ISizer#CODED_BITS_0_VALUE},
    * returns.
    *
    * @param value 
    * @param etalon 
    * @return 
    */
   public int codedSizeEncode(int value, int etalon) {
      int code = CODED_SIZE_FLAG_30_CODED;
      int etalonV = (etalon & CODED_BITS_2_ETALON) << CODED_SIZE_SHIFT_2_ETALON;
      int valueV = (value & CODED_BITS_0_VALUE) << CODED_SIZE_SHIFT_0_VALUE;
      return code + etalonV + valueV;
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

   /**
    * 
    *
    * @param fun 
    * @param ctx 
    * @param sizer 
    * @param ctxType 
    * @return 
    */
   private int computeEtalonInPixels(int fun, ILayoutable ctx, ByteObject sizer, int ctxType) {
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
      } else if (fun == ET_FUN_7_CTX_OP) {
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
    * @param val 
    * @param ref 
    * @param cfun 
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
      if (index == ET_LINK_2_PARENT) {
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
            throw new LayoutException(lc, "");
         }
      } else {
         //error fall back on parent emit a warning
         layoutableEtalon = layoutable.getLayoutableParent();
      }
      return layoutableEtalon;
   }

   /**
    * 
    *
    * @param pozer 
    * @param layoutable the {@link ILayoutable} to get the position for
    * @param ctx 
    * @return null if etalon not found
    */
   public ILayoutable getEtalonPozer(ByteObject pozer, ILayoutable layoutable, int ctx) {
      int etalon = pozer.get1(POS_OFFSET_02_ETALON1);
      ILayoutable layoutableEtalon = null;
      if (etalon == POS_ETALON_0_POINT) {
         layoutableEtalon = getEtalonPozer0Point(pozer, ctx);
      } else if (etalon == ITechPozer.POS_ETALON_4_SIZER) {
         layoutableEtalon = getEtalonPozerSizer(pozer, ctx, layoutableEtalon);
      } else if (etalon == ITechPozer.POS_ETALON_2_VIEWCTX) {
         layoutableEtalon = layoutable.getLayoutableViewContext();
      } else if (etalon == ITechPozer.POS_ETALON_1_PARENT) {
         layoutableEtalon = layoutable.getLayoutableParent();
         layoutableEtalon.addDependency(layoutable, ITechLayout.DEPENDENCY_2_POZE);
      } else if (etalon == ITechPozer.POS_ETALON_5_NAV_TOPOLOGY) {
         ByteObject litteral = pozer.getSubFirst(IBOTypesBOC.TYPE_002_LIT_INT);
         int idValue = lc.getBOC().getLitteralIntOperator().getIntValueFromBO(litteral);
         layoutableEtalon = layoutable.getLayoutableNav(idValue);
         //relation de dependance est actée
         layoutableEtalon.addDependency(layoutable, ITechLayout.DEPENDENCY_2_POZE);
      } else if (etalon == ITechPozer.POS_ETALON_6_LAYOUTABLE) {
         ByteObject bo = pozer.getSubFirst(IBOTypesBOC.TYPE_017_REFERENCE_OBJECT);
         if (bo instanceof ByteObjectLayoutable) {
            ByteObjectLayoutable bol = (ByteObjectLayoutable) bo;
            layoutableEtalon = bol.getLayoutable();
         } else {
            throw new LayoutException(lc, "");
         }
         layoutableEtalon.addDependency(layoutable, ITechLayout.DEPENDENCY_2_POZE);
      } else if (etalon == ITechPozer.POS_ETALON_3_LINK) {
         ByteObject litteral = pozer.getSubFirst(IBOTypesBOC.TYPE_002_LIT_INT);
         int idValue = lc.getBOC().getLitteralIntOperator().getIntValueFromBO(litteral);
         layoutableEtalon = layoutable.getLayoutableID(idValue);
         layoutableEtalon.addDependency(layoutable, ITechLayout.DEPENDENCY_2_POZE);
      } else {
         String message = "Etalon value is not valid for pozer ->" + etalon;
         //#debug
         toDLog().pNull(message + " \n" + lc.getFactoryPozer().toStringPozer(pozer, ctx), layoutable, LayoutOperator.class, "getEtalonPozer", LVL_05_FINE, false);

         //bad poser etalon.. throw exception dev warn
         throw new IllegalArgumentException(message);
      }
      if (layoutableEtalon == null) {
         String message = "null etalon (" + etalon + ") for pozer ->" + lc.getFactoryPozer().toStringPozer1Line(pozer, ctx);
         //#debug
         toDLog().pNull(message + " \n" + lc.getFactoryPozer().toStringPozer(pozer, ctx), layoutable, LayoutOperator.class, "getEtalonPozer", LVL_05_FINE, false);

         throw new LayoutException(lc, message);
      }
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
         LayoutableRect lr = lc.getDefaultSizeContext();
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
    * @param pozer 
    * @param ctx 
    * @return 
    */
   private ILayoutable getEtalonPozer0Point(ByteObject pozer, int ctx) {
      ILayoutable layoutableEtalon;
      //read value
      int unit = pozer.get1(POS_OFFSET_10_SIZER_FUN1);
      int val = pozer.get4(POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4);
      if(unit == RAW_UNIT_1_DIP) {
         val = lc.getDPI() * val / 160;
      }
      LayoutableRect lr = lc.getDefaultSizeContext();
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
    * @param sizer 
    * @param layoutable 
    * @return 
    */
   public ILayoutable getEtalonSizer(ByteObject sizer, ILayoutable layoutable) {
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
         layoutableEtalon = layoutable.getLayoutableDelegate(layoutable);
      } else if (etalon == ETALON_4_PARENT) {
         layoutableEtalon = layoutable.getLayoutableParent();
         //no need to add a dependency for parent
      } else if (etalon == ETALON_5_LINK) {
         int index = sizer.get1(SIZER_OFFSET_07_ETALON_SUBTYPE1); //type of linked defined in payload
         int param = sizer.get1(SIZER_OFFSET_09_ETALON_DATA2); //65k ids
         layoutableEtalon = getEtalonLink(sizer, index, param, layoutable);
         layoutableEtalon.addDependency(layoutable, ITechLayout.DEPENDENCY_1_SIZE);
      }
      return layoutableEtalon;
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
    * @return 
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
    * 
    *
    * @param sizer 
    * @return 
    */
   private int getPixelSizeRawUnit(ByteObject sizer) {
      int rawUnit = sizer.get1(SIZER_OFFSET_03_ETALON1);
      int value = sizer.get2(SIZER_OFFSET_05_VALUE2);
      if (rawUnit == RAW_UNIT_0_PIXEL) {
         return value;
      } else if (rawUnit == RAW_UNIT_1_DIP) {
         return lc.getDPI() * value / 160;
      } else {
         throw new IllegalArgumentException();
      }
   }

   /**
    * 
    *
    * @param sizer 
    * @param layoutable 
    * @param ctx 
    * @return 
    */
   private int getPixelSizeRatioEtalon(ByteObject sizer, ILayoutable layoutable, int ctx) {
      int result = 0;
      int fun = sizer.get1(SIZER_OFFSET_04_FUNCTION1);
      int vl = computeEtalonInPixels(fun, layoutable, sizer, ctx);
      int fractionTop = sizer.get1(SIZER_OFFSET_05_VALUE2);
      int fractionBottom = sizer.get1(SIZER_OFFSET_05_VALUE2 + 1);
      //because results precision is wrong when
      if (fractionTop == fractionBottom) {
         result = vl;
      } else {
         result = (int) (((float) ((vl) * fractionTop)) / (float) fractionBottom);
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
    * @param sizer 
    * @param layoutable 
    * @param ctx 
    * @return 
    */
   private int getPixelSizeDelegate(ByteObject sizer, ILayoutable layoutable, int ctx) {
      int delegateEtalon = sizer.get1(SIZER_OFFSET_03_ETALON1);
      if (delegateEtalon == DELEGATE_ETALON_0_PREFERRED) {
         if (ctx == CTX_1_WIDTH) {
            return layoutable.getSizePreferredWidth();
         } else {
            return layoutable.getSizePreferredHeight();
         }
      } else if (delegateEtalon == DELEGATE_ETALON_1_PARENT_MAX) {
         ILayoutable layoutableParent = layoutable.getLayoutableParent();
         if (layoutableParent == null) {
            throw new IllegalArgumentException();
         }
         if (ctx == CTX_1_WIDTH) {
            return layoutableParent.getSizeMaxWidth(layoutable);
         } else {
            return layoutableParent.getSizeMaxHeight(layoutable);
         }
      } else if (delegateEtalon == DELEGATE_ETALON_2_CALL) {
         if (ctx == CTX_1_WIDTH) {
            return layoutable.getSizeFromDeletgateWidth();
         } else {
            return layoutable.getSizeFromDeletgateHeight();
         }
      } else if (delegateEtalon == DELEGATE_ETALON_3_OBJECT) {
         ILayoutRequestListener delegate = layoutable.getLayoutRequestListener();
         SizeResult sr = new SizeResult();
         if (ctx == CTX_1_WIDTH) {
            delegate.computeSizeWidthFor(layoutable, sizer, sr);
         } else {
            delegate.computeSizeHeightFor(layoutable, sizer, sr);
         }
         return sr.getInt();
      } else {
         throw new IllegalArgumentException();
      }
   }

   /**
    * 
    * {@link LayouterCtx} provides global value such as DPI, Scaling etc.
    * 
    * @param sizer {@link ITechSizer}
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
      } else if (mode == MODE_2_RATIO) {
         result = getPixelSizeRatioEtalon(sizer, layoutable, ctx);
      } else if (mode == MODE_3_SCALE) {
         int value = sizer.get2(SIZER_OFFSET_05_VALUE2);
         int fun = sizer.get1(SIZER_OFFSET_04_FUNCTION1);
         result = lc.getScalePixel(value, fun);
      } else if (mode == MODE_5_FUNCTION) {
         //check no loop
         if (sizer.hasReferenceLoop()) {
            throw new IllegalArgumentException();
         }
         //when null what to do? LayoutException
         ByteObject sizer1 = sizer.getSubAtIndexNull(0);
         ByteObject sizer2 = sizer.getSubAtIndexNull(1);
         int val1 = getPixelSize(sizer1, layoutable, ctx);
         int val2 = getPixelSize(sizer2, layoutable, ctx);
         int fun = sizer.get1(SIZER_OFFSET_04_FUNCTION1);
         result = getEtalonFun(fun, val1, val2);
      } else if (mode == MODE_6_POZER_DISTANCE) {
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
      LayoutableRect lr = lc.getDefaultSizeContext();
      lr.set(w, h);
      return getPixelSize(s, lr, ctx);
   }

   /**
    * 
    *
    * @param s 
    * @param sc 
    * @return 
    */
   public int getPixelSizeH(ByteObject s, ILayoutable sc) {
      return getPixelSize(s, sc, CTX_2_HEIGHT);
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
      LayoutableRect sc = new LayoutableRect(lc, w, h);
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
         throw new LayoutException(lc, "Mode Pozer Distance requires a pozer to be added");
      }
      if (sizer.hasFlag(SIZER_OFFSET_01_FLAG, SIZER_FLAG_8_IMPLICIT)) {
         //get the poser value
         val1 = getPosition(layoutable, ctx, pozer1);
         val2 = getPosition(layoutable, ctx);

      } else {
         ByteObject pozer2 = sizer.getSubAtIndexNull(1);
         if (pozer2 == null) {
            throw new LayoutException(lc, "Mode Pozer Distance requires 2 pozers to be added");
         }
         //compute sizer of pozers
         val1 = getPosition(layoutable, ctx, pozer1);
         val2 = getPosition(layoutable, ctx, pozer2);
      }
      return Math.abs(val1 - val2);
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
      LayoutableRect lr = lc.getDefaultSizeContext();
      lr.set(w, h);
      return getPixelSize(s, lr, ctx);
   }

   /**
    * 
    *
    * @param s 
    * @param sc 
    * @return 
    */
   public int getPixelSizeW(ByteObject s, ILayoutable sc) {
      return getPixelSize(s, sc, CTX_1_WIDTH);
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
      LayoutableRect sc = new LayoutableRect(lc, w, h);
      return getPixelSize(s, sc, CTX_1_WIDTH);
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
            throw new LayoutException(lc, "null sizer");
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
         val2 = getPozXWidth(pozer1, layoutable);
      } else {
         val2 = getPozYHeight(pozer1, layoutable);
      }
      return val2;
   }

   /**
    * Computes the x position for {@link ILayoutable} using the given pozer.
    * @param pozerX
    * @param layoutable the {@link ILayoutable} to get the position for
    * @return
    */
   public int getPozXWidth(ByteObject pozerX, ILayoutable layoutable) {

      //#debug
      checkNull(layoutable);

      ILayoutable layoutableEtalon = getEtalonPozer(pozerX, layoutable, CTX_1_WIDTH);
      //the pozer compute position relative to the box
      //compute relative or absolute?
      layoutableEtalon.layoutUpdatePositionXCheck(); //make sure the etalon has been sized and positioned
      layoutableEtalon.layoutUpdateSizeWCheck(); //make sure the etalon has been sized and positioned

      int fx = layoutableEtalon.getPozeX();
      int fw = layoutableEtalon.getSizeDrawnWidth();

      layoutable.layoutUpdateSizeWCheck();
      int objectSize = layoutable.getSizeDrawnWidth();

      int x = getPos(pozerX, fx, fw, objectSize, layoutable, CTX_1_WIDTH);
      return x;
   }

   /**
    * 
    *
    * @param pozerX 
    * @param layoutable 
    * @return 
    */
   public int getPozXPure(ByteObject pozerX, ILayoutable layoutable) {
      //#debug
      checkNull(layoutable);

      ILayoutable layoutableEtalon = getEtalonPozer(pozerX, layoutable, CTX_1_WIDTH);
      layoutableEtalon.layoutUpdatePositionXCheck(); 
      layoutableEtalon.layoutUpdateSizeWCheck(); 

      int fx = layoutableEtalon.getPozeX();
      int fw = layoutableEtalon.getSizeDrawnWidth();
      int alignDest = pozerX.get1(POS_OFFSET_04_ANCHOR_ETALON1);
      int x = getPosPure(alignDest, fw, fx);
      return x;
   }
   
   /**
    * 
    *
    * @param pozerX 
    * @param layoutable 
    * @return 
    */
   public int getPozYPure(ByteObject pozerX, ILayoutable layoutable) {
      //#debug
      checkNull(layoutable);

      ILayoutable layoutableEtalon = getEtalonPozer(pozerX, layoutable, CTX_1_WIDTH);
      layoutableEtalon.layoutUpdatePositionYCheck(); 
      layoutableEtalon.layoutUpdateSizeHCheck(); 

      int fy = layoutableEtalon.getPozeY();
      int fh = layoutableEtalon.getSizeDrawnHeight();
      int alignDest = pozerX.get1(POS_OFFSET_04_ANCHOR_ETALON1);
      int x = getPosPure(alignDest, fh, fy);
      return x;
   }

   /**
    * The value is valid in the reference of the etalon.
    * @param pozerY
    * @param layoutable
    * @return
    */
   public int getPozYHeight(ByteObject pozerY, ILayoutable layoutable) {
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
    * Once etalon has been found, get one of its properties.
    *
    * @param sizer 
    * @param layoutable 
    * @return 
    */
   private int getSizeEtalonCtxH(ByteObject sizer, ILayoutable layoutable) {
      int type = sizer.get1(SIZER_OFFSET_06_PROPERTY1);
      switch (type) {
         case ITechSizer.SIZER_PROP_00_DRAWN:
            return layoutable.getSizeDrawnHeight();
         case SIZER_PROP_01_PREFERRED:
            return layoutable.getSizePreferredHeight();
         case SIZER_PROP_02_UNIT_LOGIC:
            return layoutable.getSizeUnitHeight();
         default:
            return layoutable.getSizeDrawnHeight();
      }
   }

   /**
    * Once etalon has been found, get one of its properties.
    *
    * @param sizer 
    * @param layoutable 
    * @return 
    */
   private int getSizeEtalonCtxW(ByteObject sizer, ILayoutable layoutable) {
      int type = sizer.get1(SIZER_OFFSET_06_PROPERTY1);
      switch (type) {
         case ITechSizer.SIZER_PROP_00_DRAWN:
            return layoutable.getSizeDrawnWidth();
         case SIZER_PROP_01_PREFERRED:
            return layoutable.getSizePreferredWidth();
         case SIZER_PROP_02_UNIT_LOGIC:
            return layoutable.getSizeUnitWidth();
         default:
            return layoutable.getSizeDrawnWidth();
      }
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
      ILayoutable layoutableEtalon = getEtalonSizer(sizer, layoutable);
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
      ILayoutable layoutableEtalon = getEtalonSizer(sizer, layoutable);
      //if etalon is yourself ?
      if (layoutable != layoutableEtalon) {
         layoutableEtalon.layoutUpdateSizeCheck();
      }
      return getSizeEtalonCtxW(sizer, layoutableEtalon);
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
         if (sizerW.get1(SIZER_OFFSET_06_PROPERTY1) == sizerH.get1(SIZER_OFFSET_06_PROPERTY1)) {
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

   /**
    * 
    *
    * @return 
    */
   //#mdebug
   public IDLog toDLog() {
      return lc.toDLog();
   }

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
      dc.root(this, "BasicSizer");
      toStringPrivate(dc);
   }

   /**
    * 
    *
    * @param dc 
    * @param sizer 
    */
   public void toString(Dctx dc, ByteObject sizer) {
      sizer.toString(dc);
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
      dc.root1Line(this, "BasicSizer");
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
