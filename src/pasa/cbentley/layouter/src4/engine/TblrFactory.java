/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.BOAbstractFactory;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.byteobjects.src4.objects.function.Function;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.layouter.src4.ctx.IBOTypesLayout;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.tech.IBOTblr;

/**
 * 3 ways to encode a TBLR
 * <li> 1 raw value
 * <li> 1 sizer
 * <li> 4 sizers
 * 
 * The operator 
 * @author Charles Bentley
 *
 */
public class TblrFactory extends BOAbstractFactory implements IBOTblr, IBOTypesLayout {

   private LayouterCtx lac;

   public TblrFactory(LayouterCtx lac) {
      super(lac.getBOC());
      this.lac = lac;
   }

   private ByteObject createTBLR() {
      return getBOFactory().createByteObject(FTYPE_2_TBLR, TBLR_BASIC_SIZE);
   }

   public ByteObject getTBLR_T_Function(ByteObject funDef) {
      ByteObject p = createTBLR();
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_3_TRANS_FUN, true);
      
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_5_TRANS_TOP, true);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_6_TRANS_BOT, true);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_7_TRANS_LEFT, true);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_8_TRANS_RIGHT, true);
      
      p.addByteObject(funDef);
      
      return p;
   }

   /**
    * During a merge operation,
    * This TBLR will imprint take the value of the root in a reverse merge.
    * 
    * The merged will keep its original TBLR reference
    * @return
    */
   public ByteObject getTBLRCarbon() {
      ByteObject p = createTBLR();
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_5_TRANS_TOP, true);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_6_TRANS_BOT, true);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_7_TRANS_LEFT, true);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_8_TRANS_RIGHT, true);
      return p;
   }

   /**
    * TBLR is typically reused many times over. Immutable 
    * A reference consumed 4 bytes.
    * A same value
    * @param samevalue
    * @return
    */
   public ByteObject getTBLRCoded(int samevalue) {
      ByteObject p = createTBLR();
      p.set1(TBLR_OFFSET_02_TYPE1, TYPE_1_CODED_VALUE);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_4_SAME_VALUE, true);
      p.set4(TBLR_OFFSET_03_DATA4, samevalue);
      return p;
   }

   public ByteObject getTBLRIncomplete() {
      ByteObject p = createTBLR();
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_5_TRANS_TOP, true);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_6_TRANS_BOT, true);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_7_TRANS_LEFT, true);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_8_TRANS_RIGHT, true);
      return p;
   }

   public ByteObject getTBLRPixel(int samevalue) {
      ByteObject p = createTBLR();
      p.set4(TBLR_OFFSET_03_DATA4, samevalue);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_4_SAME_VALUE, true);
      p.set1(TBLR_OFFSET_02_TYPE1, TYPE_0_PIXEL_VALUE);
      return p;
   }

   /**
    * Looks up the pooling
    * Immutable TBLR value
    * @param top -1 or negative for undefined
    * @param bot
    * @param left
    * @param right
    * @return
    */
   public ByteObject getTBLRPixel(int top, int bot, int left, int right) {
      if (top == bot && top == left && top == right) {
         return getTBLRCoded(top);
      }

      ByteObject p = createTBLR();
      p.set1(TBLR_OFFSET_02_TYPE1, TYPE_0_PIXEL_VALUE);
      int flag = 0;
      boolean useArray = false;
      boolean isDefTop = true;
      boolean isDefBot = true;
      boolean isDefLeft = true;
      boolean isDefRight = true;
      if (top > 255) {
         useArray = true;
      } else if (top < 0) {
         isDefTop = false;
      }
      if (bot > 255) {
         useArray = true;
      } else if (bot < 0) {
         isDefBot = false;
      }
      if (left > 255) {
         useArray = true;
      } else if (left < 0) {
         isDefLeft = false;
      }
      if (right > 255) {
         useArray = true;
      } else if (right < 0) {
         isDefRight = false;
      }
      flag = BitUtils.setFlag(flag, TBLR_FLAG_5_TRANS_TOP, !isDefTop);
      flag = BitUtils.setFlag(flag, TBLR_FLAG_6_TRANS_BOT, !isDefBot);
      flag = BitUtils.setFlag(flag, TBLR_FLAG_7_TRANS_LEFT, !isDefLeft);
      flag = BitUtils.setFlag(flag, TBLR_FLAG_8_TRANS_RIGHT, !isDefRight);
      if (useArray) {
         int[] ar = new int[] { top, bot, left, right };
         ByteObject arrayBo = boc.getLitteralIntFactory().getLitteralArray(ar);
         p.addSub(arrayBo);
         flag = BitUtils.setFlag(flag, TBLR_FLAG_1_USING_ARRAY, true);
      } else {
         int values = 0;
         values = BitUtils.setByte1(values, top);
         values = BitUtils.setByte2(values, bot);
         values = BitUtils.setByte3(values, left);
         values = BitUtils.setByte4(values, right);
         p.set4(TBLR_OFFSET_03_DATA4, values);
      }
      p.set1(TBLR_OFFSET_01_FLAG, flag);
      return p;
   }

   /**
    * a TBLR with a unique sizer for all the elements.
    * <br>
    * 
    * @param mod
    * @param sizer defines the unit. when null. sero
    * @return
    */
   public ByteObject getTBLRSizer(ByteObject sizer) {
      ByteObject bo = createTBLR();
      bo.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_4_SAME_VALUE, true);
      bo.set1(TBLR_OFFSET_02_TYPE1, TYPE_2_SIZER);
      bo.addSub(sizer);
      return bo;
   }

   public ByteObject getTBLRSizers(ByteObject sizerTop, ByteObject sizerBot, ByteObject sizerLeft, ByteObject sizerRight) {
      ByteObject p = createTBLR();
      p.set1(TBLR_OFFSET_02_TYPE1, TYPE_2_SIZER);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_5_TRANS_TOP, sizerTop == null);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_6_TRANS_BOT, sizerBot == null);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_7_TRANS_LEFT, sizerLeft == null);
      p.setFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_8_TRANS_RIGHT, sizerRight == null);

      ByteObject[] ar = new ByteObject[] { sizerTop, sizerBot, sizerLeft, sizerRight };
      p.setSubs(ar);
      return p;
   }

   /**
    * Called by {@link BOModuleDrawx#merge(ByteObject, ByteObject)}
    * 
    * Merging can change the type of tblr
    * @param root
    * @param merge
    * @return
    */
   public ByteObject mergeTBLR(ByteObject root, ByteObject merge) {
      LayoutOperator layoutOperator = lac.getLayoutOperator();
      if (merge.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_4_SAME_VALUE)) {
         return merge;
      } else {
         Function f = null;
         if (merge.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_3_TRANS_FUN)) {
            ByteObject fun = merge.getSubFirst(IBOTypesBOC.TYPE_021_FUNCTION);
            f = boc.getFunctionFactory().createFunction(fun);
         }
         int mergeType = merge.get1(TBLR_OFFSET_02_TYPE1);
         if (mergeType == TYPE_0_PIXEL_VALUE) {
            int top = -1;
            if (!merge.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_5_TRANS_TOP)) {
               top = layoutOperator.getTBLRValue(merge, C.POS_0_TOP);
            } else {
               top = layoutOperator.getTBLRValue(root, C.POS_0_TOP);
               if (f != null) {
                  top = f.fx(top);
               }
            }
            int bot = -1;
            if (!merge.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_6_TRANS_BOT)) {
               bot = layoutOperator.getTBLRValue(merge, C.POS_1_BOT);
            } else {
               bot = layoutOperator.getTBLRValue(root, C.POS_1_BOT);
               if (f != null) {
                  bot = f.fx(bot);
               }
            }
            int left = -1;
            if (!merge.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_7_TRANS_LEFT)) {
               left = layoutOperator.getTBLRValue(merge, C.POS_2_LEFT);
            } else {
               left = layoutOperator.getTBLRValue(root, C.POS_2_LEFT);
               if (f != null) {
                  left = f.fx(left);
               }
            }
            int right = -1;
            if (!merge.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_8_TRANS_RIGHT)) {
               right = layoutOperator.getTBLRValue(merge, C.POS_3_RIGHT);
            } else {
               right = layoutOperator.getTBLRValue(root, C.POS_3_RIGHT);
               if (f != null) {
                  right = f.fx(right);
               }
            }
            ByteObject nt = getTBLRPixel(top, bot, left, right);
            return nt;
         } else if (mergeType == TYPE_2_SIZER) {
            //depends on root
            ByteObject sizerTop = null;
            ByteObject sizerBot = null;
            ByteObject sizerLeft = null;
            ByteObject sizerRight = null;
            if (root.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_4_SAME_VALUE)) {
               ByteObject sizer = root.getSubFirst(IBOTypesLayout.FTYPE_3_SIZER);
               sizerTop = sizer;
               sizerBot = sizer;
               sizerLeft = sizer;
               sizerRight = sizer;
            } else {
               sizerTop = root.getSubAtIndexNull(0);
               sizerBot = root.getSubAtIndexNull(1);
               sizerLeft = root.getSubAtIndexNull(2);
               sizerRight = root.getSubAtIndexNull(3);

            }
            if (!merge.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_5_TRANS_TOP)) {
               sizerTop = merge.getSubAtIndexNull(0);
            }
            if (!merge.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_6_TRANS_BOT)) {
               sizerBot = merge.getSubAtIndexNull(1);
            }
            if (!merge.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_7_TRANS_LEFT)) {
               sizerLeft = merge.getSubAtIndexNull(2);
            }
            if (!merge.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_8_TRANS_RIGHT)) {
               sizerRight = merge.getSubAtIndexNull(3);
            }
            ByteObject result = getTBLRSizers(sizerTop, sizerBot, sizerLeft, sizerRight);
            return result;
         } else {
            //other tblr don't have
            return merge;
         }
      }
   }

   //#mdebug
   /**
    * Descriptive. no context needed.
    * @param bo
    * @param dc
    */
   public void toStringTBLR(ByteObject bo, Dctx dc) {
      dc.rootN(this, "TBLR", TblrFactory.class, 274);
      LayoutOperator operator = lac.getLayoutOperator();
      dc.appendVarWithSpace("hasArray", bo.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_1_USING_ARRAY));
      dc.appendVarWithSpace("SameValue", bo.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_4_SAME_VALUE));
      if (bo.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_4_SAME_VALUE)) {
         dc.append(" Value = " + operator.getTBLRValue(bo, C.POS_0_TOP));
      } else {

         dc.nl();
         dc.appendVarWithSpace("top", bo.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_5_TRANS_TOP));
         dc.appendVarWithSpace("bot", bo.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_6_TRANS_BOT));
         dc.appendVarWithSpace("left", bo.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_7_TRANS_LEFT));
         dc.appendVarWithSpace("right", bo.hasFlag(TBLR_OFFSET_01_FLAG, TBLR_FLAG_8_TRANS_RIGHT));
         dc.nl();
         dc.append(" Top=" + operator.getTBLRValue(bo, C.POS_0_TOP));
         dc.append(" Bot=" + operator.getTBLRValue(bo, C.POS_1_BOT));
         dc.append(" Left=" + operator.getTBLRValue(bo, C.POS_2_LEFT));
         dc.append(" Right=" + operator.getTBLRValue(bo, C.POS_3_RIGHT));
      }
   }
//#enddebug
}
