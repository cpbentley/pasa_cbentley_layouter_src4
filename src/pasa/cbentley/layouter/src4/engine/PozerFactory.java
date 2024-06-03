/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.BOAbstractFactory;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ToStringStaticC;
import pasa.cbentley.layouter.src4.ctx.IBOTypesLayout;
import pasa.cbentley.layouter.src4.ctx.LayoutException;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ToStringStaticLayout;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechCodedSizer;
import pasa.cbentley.layouter.src4.tech.ITechLayout;
import pasa.cbentley.layouter.src4.tech.IBOLinker;
import pasa.cbentley.layouter.src4.tech.IBOPozer;
import pasa.cbentley.layouter.src4.tech.IBOSizer;

/**
 * 
 */
public class PozerFactory extends BOAbstractFactory implements IBOLinker, IBOTypesLayout, ITechCodedSizer, IBOSizer, IBOPozer, ITechLayout {

   /**
    * 
    */
   private final LayouterCtx lc;

   private ByteObject        pozerAtPixel0Lazy;

   /**
    * often used pozer.
    */
   private ByteObject        pozerCenterLazy;

   /**
    * 
    *
    * @param lac 
    */
   public PozerFactory(LayouterCtx lac) {
      super(lac.getBOC());
      this.lc = lac;

   }

   /**
    * 
    *
    * @param pos 
    * @param sizer 
    * @return 
    */
   public ByteObject getPoserWithSizer(int pos, ByteObject sizer) {
      sizer.checkType(FTYPE_3_SIZER);
      ByteObject bo = getBOFactory().createByteObject(FTYPE_4_POSITION, SIZER_BASIC_SIZE);
      bo.set1(POS_OFFSET_10_SIZER_FUN1, pos);
      bo.setFlag(POS_OFFSET_01_FLAG, POS_FLAG_1_SIZER, true);
      bo.addSub(sizer);
      return bo;
   }

   /**
    * 
    * @param pozer
    * @param posFunction
    * @param sizer
    * @return
    */
   public ByteObject setPoserWithSizer(ByteObject pozer, int posFunction, ByteObject sizer) {
      pozer.checkType(FTYPE_4_POSITION);
      sizer.checkType(FTYPE_3_SIZER);
      pozer.set1(POS_OFFSET_10_SIZER_FUN1, posFunction);
      pozer.setFlag(POS_OFFSET_01_FLAG, POS_FLAG_1_SIZER, true);
      pozer.addSub(sizer);
      return pozer;
   }

   /**
    * Align are 
    * <li> {@link C#LOGIC_1_TOP_LEFT}
    * <li> {@link C#LOGIC_2_CENTER}
    * <li> {@link C#LOGIC_3_BOTTOM_RIGHT}.
    *
    * @param alignSrc 
    * @param alignDest 
    * @param etalon 
    * @param type 
    * @return 
    */
   public ByteObject getPozer(int alignSrc, int alignDest, int etalon, int type) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_4_POSITION, POS_BASIC_SIZE);
      bo.set1(POS_OFFSET_07_ANCHOR_POZEE1, alignSrc);
      bo.set1(POS_OFFSET_04_ANCHOR_ETALON1, alignDest);
      bo.set1(POS_OFFSET_02_ETALON1, etalon);
      bo.set1(POS_OFFSET_08_ANCHOR_POZEE_STRUCT1, type);
      return bo;
   }

   /**
    * Align relative to given value.
    * <br>
    * Position will be dependent on object size.
    * @param alignSrc
    * @param cx The point
    * @return
    */
   public ByteObject getPozerAtPixel(int alignSrc, int cx) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_4_POSITION, POS_BASIC_SIZE);
      bo.set1(POS_OFFSET_02_ETALON1, POS_ETALON_0_POINT);
      bo.set1(POS_OFFSET_07_ANCHOR_POZEE1, alignSrc);
      bo.set4(POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4, cx);
      return bo;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerAtPixel0() {
      return getPozerAtPixel(C.LOGIC_1_TOP_LEFT, 0);
   }

   public ByteObject getPozerAtPixel0Lazy() {
      if (pozerAtPixel0Lazy == null) {
         pozerAtPixel0Lazy = getPozerAtPixel0();
      }
      return pozerAtPixel0Lazy;
   }

   /**
    * The delegate decides a point
    * @param delegate
    * @return
    */
   public ByteObject getPozerDelegatePoint(ILayoutDelegate delegate, int alignSrc) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_4_POSITION, POS_BASIC_SIZE);
      bo.set1(POS_OFFSET_02_ETALON1, POS_ETALON_0_POINT);
      bo.set1(POS_OFFSET_07_ANCHOR_POZEE1, alignSrc);
      //special value for delegate call
      bo.set4(POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4, ITechLayout.DELEGATE_POINT_VALUE);
      
      ByteObjectLayoutDelegate delegateBO = new ByteObjectLayoutDelegate(lc.getBOC(), delegate);
      bo.addByteObject(delegateBO);

      return bo;
   }
   /**
    * Position the top of the pozee at the pixel value
    * Position the start of the pozee at the pixel value.
    *
    * @param value 
    * @return 
    */
   public ByteObject getPozerAtTopStartForPixel(int value) {
      return getPozerAtPixel(C.LOGIC_1_TOP_LEFT, value);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerBotToBot() {
      return getPozerOnParentAt(C.LOGIC_3_BOTTOM_RIGHT, C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerBotToMid() {
      return getPozerOnParentAt(C.LOGIC_3_BOTTOM_RIGHT, C.LOGIC_2_CENTER, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * Place the right/bottom of object on the bottom/right of the parent.
    *
    * @return 
    */
   public ByteObject getPozerBottomRight() {
      return getPozerOnParentAt(C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerBottomRightBorder() {
      return getPozerOnParentAt(C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_01_VIEW_CONTENT_PAD_BORDER);
   }

   public ByteObject getPozerBottomRightVC() {
      return getPozerOnParentAt(C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerBotToTop() {
      return getPozerOnParentAt(C.LOGIC_3_BOTTOM_RIGHT, C.LOGIC_1_TOP_LEFT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerCenterToBotRight() {
      return getPozerOnParentAt(C.LOGIC_2_CENTER, C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * Center object relative to the parent.
    * <br>
    * What parents anchor? The full drawn size
    * @return
    */
   public ByteObject getPozerCenterToCenter() {
      return getPozerOnParentAt(C.LOGIC_2_CENTER, C.LOGIC_2_CENTER, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerCenterToCenterLazy() {
      if (pozerCenterLazy == null) {
         pozerCenterLazy = getPozerOnParentAt(C.LOGIC_2_CENTER, C.LOGIC_2_CENTER, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
      }
      return pozerCenterLazy;
   }

   /**
    * Position on the top/left on the parent's top/left.
    *
    * @return 
    */
   public ByteObject getPozerCenterToTopLeft() {
      return getPozerOnParentAt(C.LOGIC_2_CENTER, C.LOGIC_1_TOP_LEFT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * Position end of the end of a {@link ILayoutable} dynamically provided by the {@link ILayoutDelegate}
    * @param delegate
    * @return
    */
   public ByteObject getPozerEndToEndDelegate(ILayoutDelegate delegate) {
      ByteObject bo = getPozerOnDelegateAt(delegate, C.LOGIC_3_BOTTOM_RIGHT, C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
      return bo;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerEndToCenter() {
      return getPozerOnParentAt(C.LOGIC_3_BOTTOM_RIGHT, C.LOGIC_2_CENTER, ITechLayout.VIEW_STYLE_00_VIEW_FULL);

   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerEndToEnd() {
      return getPozerOnParentAt(C.LOGIC_3_BOTTOM_RIGHT, C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   public ByteObject getPozerEndToEndVC() {
      return getPozerOnVCAt(C.LOGIC_3_BOTTOM_RIGHT, C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerEndToStart() {
      return getPozerOnParentAt(C.LOGIC_3_BOTTOM_RIGHT, C.LOGIC_1_TOP_LEFT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   public ByteObject getPozerEndToStartVC() {
      return getPozerOnVCAt(C.LOGIC_3_BOTTOM_RIGHT, C.LOGIC_1_TOP_LEFT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * Align inside the {@link ViewContext}.
    *
    * @param alignSrc 
    * @return 
    */
   public ByteObject getPozerInsideVC(int alignSrc) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_4_POSITION, POS_BASIC_SIZE);
      bo.set1(POS_OFFSET_07_ANCHOR_POZEE1, alignSrc);
      bo.set1(POS_OFFSET_04_ANCHOR_ETALON1, alignSrc);
      bo.set1(POS_OFFSET_02_ETALON1, ETALON_1_VIEWCONTEXT);
      bo.set1(POS_OFFSET_08_ANCHOR_POZEE_STRUCT1, 0);
      return bo;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerMidToBot() {
      return getPozerMidToEnd();
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerMidToEnd() {
      return getPozerOnParentAt(C.LOGIC_2_CENTER, C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerMidToStart() {
      return getPozerOnParentAt(C.LOGIC_2_CENTER, C.LOGIC_1_TOP_LEFT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerMidToTop() {
      return getPozerMidToStart();
   }

   /**
    * Sub etalon?
    * TODO
    * <li>{@link ISizerDrawable#ET_DRW_0_DRAWN}
    * <li>{@link ISizerDrawable#ET_DRW_1_CONTENT}
    * <li>{@link ISizerDrawable#ET_DRW_2_VIEWPORT}
    * <br>
    * <br>
    * Align are 
    * <li> {@link C#LOGIC_1_TOP_LEFT}
    * <li> {@link C#LOGIC_2_CENTER}
    * <li> {@link C#LOGIC_3_BOTTOM_RIGHT}.
    *
    * @param alignSrc 
    * @param alignDest 
    * @param dir 
    * @return 
    */
   public ByteObject getPozerNav(int alignSrc, int alignDest, int dir) {
      ByteObject bo = getPozer(alignSrc, alignDest, ETALON_5_LINK, ET_LINK_1_NAV);
      ByteObject linker = lc.getLayoutFactory().createLink(ET_LINK_1_NAV, dir);
      bo.addByteObject(linker);
      return bo;
   }

   /**
    * At located where on the parent style element
    * <li> {@link ITechLayout#VIEW_STYLE_00_VIEW_FULL}
    * <li> {@link ITechLayout#ET_DRW_11_BORDER}
    * <li> {@link ITechLayout#ET_DRW_12_MARGIN}.
    *
    * @param anchorMatch 
    * @param at 
    * @return 
    */
   public ByteObject getPozerOnParentAt(int anchorMatch, int at) {
      return getPozerOnParentAt(anchorMatch, anchorMatch, at);
   }

   /**
    * <li> {@link ITechLayout#VIEW_STYLE_00_VIEW_FULL}
    * <li> {@link ITechLayout#VIEW_STYLE_01_VIEW_CONTENT_PAD_BORDER}
    * <li> {@link ITechLayout#VIEW_STYLE_02_VIEW_CONTENT_PAD}
    * <li> {@link ITechLayout#VIEW_STYLE_03_VIEW_CONTENT}.
    *
    * @param anchorSrc 
    * @param anchorDest 
    * @param atStyle which style property position of should be used
    * @return 
    */
   public ByteObject getPozerOnParentAt(int anchorSrc, int anchorDest, int atStyle) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_4_POSITION, POS_BASIC_SIZE);
      bo.set1(POS_OFFSET_02_ETALON1, IBOPozer.POS_ETALON_1_PARENT);
      bo.set1(POS_OFFSET_04_ANCHOR_ETALON1, anchorDest);
      bo.set1(POS_OFFSET_05_ANCHOR_ETALON_STRUCT1, ITechLayout.VIEW_STRUCT_00_ALL_VISIBLE);
      bo.set1(POS_OFFSET_06_ANCHOR_ETALON_STYLE1, atStyle);
      bo.set1(POS_OFFSET_07_ANCHOR_POZEE1, anchorSrc);
      bo.set1(POS_OFFSET_08_ANCHOR_POZEE_STRUCT1, ITechLayout.VIEW_STRUCT_00_ALL_VISIBLE);
      bo.set1(POS_OFFSET_09_ANCHOR_POZEE_STYLE1, atStyle);
      return bo;
   }

   /**
    * The anchor is decided dynamically by the {@link ILayoutDelegate}
    * @param delegate
    * @param anchorSrc
    * @param anchorDest
    * @param atStyle
    * @return
    */
   public ByteObject getPozerOnDelegateAt(ILayoutDelegate delegate, int anchorSrc, int anchorDest, int atStyle) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_4_POSITION, POS_BASIC_SIZE);
      bo.set1(POS_OFFSET_02_ETALON1, IBOPozer.POS_ETALON_7_DELEGATE);
      bo.set1(POS_OFFSET_04_ANCHOR_ETALON1, anchorDest);
      bo.set1(POS_OFFSET_05_ANCHOR_ETALON_STRUCT1, ITechLayout.VIEW_STRUCT_00_ALL_VISIBLE);
      bo.set1(POS_OFFSET_06_ANCHOR_ETALON_STYLE1, atStyle);
      bo.set1(POS_OFFSET_07_ANCHOR_POZEE1, anchorSrc);
      bo.set1(POS_OFFSET_08_ANCHOR_POZEE_STRUCT1, ITechLayout.VIEW_STRUCT_00_ALL_VISIBLE);
      bo.set1(POS_OFFSET_09_ANCHOR_POZEE_STYLE1, atStyle);

      ByteObjectLayoutDelegate delegateBO = new ByteObjectLayoutDelegate(lc.getBOC(), delegate);
      bo.addByteObject(delegateBO);

      return bo;
   }

   public ByteObject getPozerOnVCAt(int anchorMatch, int at) {
      return getPozerOnVCAt(anchorMatch, anchorMatch, at);
   }

   public ByteObject getPozerOnVCAt(int anchorSrc, int anchorDest, int atStyle) {
      ByteObject bo = getBOFactory().createByteObject(FTYPE_4_POSITION, POS_BASIC_SIZE);
      bo.set1(POS_OFFSET_02_ETALON1, IBOPozer.POS_ETALON_2_VIEWCTX);
      bo.set1(POS_OFFSET_04_ANCHOR_ETALON1, anchorDest);
      bo.set1(POS_OFFSET_05_ANCHOR_ETALON_STRUCT1, ITechLayout.VIEW_STRUCT_00_ALL_VISIBLE);
      bo.set1(POS_OFFSET_06_ANCHOR_ETALON_STYLE1, atStyle);
      bo.set1(POS_OFFSET_07_ANCHOR_POZEE1, anchorSrc);
      bo.set1(POS_OFFSET_08_ANCHOR_POZEE_STRUCT1, ITechLayout.VIEW_STRUCT_00_ALL_VISIBLE);
      bo.set1(POS_OFFSET_09_ANCHOR_POZEE_STYLE1, atStyle);
      return bo;
   }

   /**
    * 
    *
    * @param alignSrc 
    * @param alignDest 
    * @param nav 
    * @return 
    */
   public ByteObject getPozerParent(int alignSrc, int alignDest, int nav) {
      ByteObject bo = getPozer(alignSrc, alignDest, ETALON_5_LINK, ET_LINK_0_PARENT);
      ByteObject linker = lc.getLayoutFactory().createLink(ET_LINK_0_PARENT, 0);
      bo.addByteObject(linker);
      return bo;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerRightToRight() {
      return getPozerOnParentAt(C.LOGIC_3_BOTTOM_RIGHT, C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerRiteToLeft() {
      return getPozerOnParentAt(C.LOGIC_3_BOTTOM_RIGHT, C.LOGIC_1_TOP_LEFT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerStartToCenter() {
      return getPozerOnParentAt(C.LOGIC_1_TOP_LEFT, C.LOGIC_2_CENTER, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerStartToEnd() {
      return getPozerOnParentAt(C.LOGIC_1_TOP_LEFT, C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerStartToStart() {
      return getPozerOnParentAt(C.LOGIC_1_TOP_LEFT, C.LOGIC_1_TOP_LEFT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * Position on the top/left on the parent's top/left.
    *
    * @return 
    */
   public ByteObject getPozerTopLeft() {
      return getPozerOnParentAt(C.LOGIC_1_TOP_LEFT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerTopToBottom() {
      return getPozerOnParentAt(C.LOGIC_1_TOP_LEFT, C.LOGIC_3_BOTTOM_RIGHT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerTopToMid() {
      return getPozerOnParentAt(C.LOGIC_1_TOP_LEFT, C.LOGIC_2_CENTER, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * Positions Top of Pozee to the Top of parent.
    * <br>
    * The property of parent use for computing its top is {@link ITechLayout#VIEW_STYLE_00_VIEW_FULL}
    *
    * @return 
    */
   public ByteObject getPozerTopToTop() {
      return getPozerOnParentAt(C.LOGIC_1_TOP_LEFT, C.LOGIC_1_TOP_LEFT, ITechLayout.VIEW_STYLE_00_VIEW_FULL);
   }

   /**
    * A position
    * Param is 
    * <li>{@link C#LOGIC_1_LEFT}
    * <li>{@link C#LOGIC_2_CENTER}
    * <li>{@link C#LOGIC_3_RIGHT}
    * 
    * <br>
    * <br>
    * and 
    * <li>{@link C#LOGIC_1_TOP_LEFT}
    * <li>{@link C#LOGIC_2_CENTER}
    * <li>{@link C#LOGIC_3_BOTTOM_RIGHT}
    * 
    * Direction of distance from point? Away.
    * What is away from center? nothing
    * TODO Link with Anchor used for Style?
    *
    * @param value 
    * @param sizer distance function. Away
    * @param distFun 
    * @return 
    */
   public ByteObject getPozerViewCtx(int value, ByteObject sizer, int distFun) {
      sizer.checkType(FTYPE_3_SIZER);
      ByteObject bo = getBOFactory().createByteObject(FTYPE_4_POSITION, SIZER_BASIC_SIZE);
      bo.set1(SIZER_OFFSET_02_MODE1, MODE_0_RAW_UNITS);
      bo.set2(SIZER_OFFSET_08_VALUE2, value);
      bo.addByteObject(sizer);
      return bo;
   }

   /**
    * 
    *
    * @param object 
    * @param viewStruct 
    * @param viewStyle 
    * @return 
    */
   public String toStringAnchorStructStyle(String object, int viewStruct, int viewStyle) {
      if (viewStruct == ITechLayout.VIEW_STRUCT_00_ALL_VISIBLE && viewStyle == ITechLayout.VIEW_STYLE_00_VIEW_FULL) {
         return object;
      } else {
         String strStyle = ToStringStaticLayout.toStringViewStyle(viewStyle);
         String strStruct = ToStringStaticLayout.toStringViewStruct(viewStruct);
         return object + " " + strStruct + " " + strStyle;
      }
   }

   /**
    * 
    *
    * @param pozer 
    * @return 
    */
   public String toStringEtalon(ByteObject pozer) {
      return toStringEtalon(pozer, null);
   }

   /**
    * 
    *
    * @param pozer 
    * @param pozee 
    * @return 
    */
   public String toStringEtalon(ByteObject pozer, ILayoutable pozee) {
      int etalonType = pozer.get1(POS_OFFSET_02_ETALON1);
      String strEtalon = "etalon";
      if (etalonType == POS_ETALON_3_LINK) {
         ByteObject bo = pozer.getSubFirst(IBOTypesBOC.TYPE_017_REFERENCE_OBJECT);
         if (bo instanceof ByteObjectLayoutable) {
            ByteObjectLayoutable bol = (ByteObjectLayoutable) bo;
            strEtalon = "layoutable" + bol.getLayoutable().toStringName();
         } else {
            strEtalon = "wrong TYPE_017_REFERENCE_OBJECT";
         }
      } else if (etalonType == POS_ETALON_1_PARENT) {
         if (pozee != null) {
            ILayoutable layoutableParent = pozee.getLayoutableParent();
            if (layoutableParent != null) {
               strEtalon = layoutableParent.toStringName() + "(parent)";
            } else {
               strEtalon = "parent (error null)";
            }
         } else {
            strEtalon = "parent";
         }
      } else if (etalonType == POS_ETALON_2_VIEWCTX) {
         strEtalon = "viewcontext";
      } else if (etalonType == POS_ETALON_6_LAYOUTABLE) {
         strEtalon = "layoutable";
         ByteObject bo = pozer.getSubFirst(IBOTypesBOC.TYPE_017_REFERENCE_OBJECT);
         if (bo instanceof ByteObjectLayoutable) {
            ByteObjectLayoutable bol = (ByteObjectLayoutable) bo;
            ILayoutable lay = bol.getLayoutable();
            strEtalon += " ";
            strEtalon += lay.toString1Line();
         } else {
            strEtalon += "error";
         }
      } else {
         strEtalon = "unknown etalon " + etalonType;
      }
      int etalonViewStruct = pozer.get1(POS_OFFSET_05_ANCHOR_ETALON_STRUCT1);
      int etalonViewStyle = pozer.get1(POS_OFFSET_06_ANCHOR_ETALON_STYLE1);

      return toStringAnchorStructStyle(strEtalon, etalonViewStruct, etalonViewStyle);
   }

   /**
    * 
    *
    * @param pozer 
    * @return 
    */
   public String toStringPozee(ByteObject pozer) {
      return toStringPozee(pozer, "pozee");
   }

   /**
    * 
    *
    * @param pozer 
    * @param pozeeName 
    * @return 
    */
   public String toStringPozee(ByteObject pozer, String pozeeName) {
      int pozeeViewStruct = pozer.get1(POS_OFFSET_08_ANCHOR_POZEE_STRUCT1);
      int pozeeViewStyle = pozer.get1(POS_OFFSET_09_ANCHOR_POZEE_STYLE1);

      return toStringAnchorStructStyle(pozeeName, pozeeViewStruct, pozeeViewStyle);
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    */
   public void toStringPozer(ByteObject pozer, Dctx dc) {
      toStringPozer(pozer, dc, null);
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    * @param ctx 
    */
   public void toStringPozer(ByteObject pozer, Dctx dc, int ctx) {
      if (ctx == ITechLayout.CTX_1_WIDTH) {
         toStringPozerX(pozer, dc);
      } else if (ctx == ITechLayout.CTX_2_HEIGHT) {
         toStringPozerY(pozer, dc);
      } else {
         toStringPozer(pozer, dc);
      }
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    * @param title 
    */
   public void toStringPozer(ByteObject pozer, Dctx dc, String title) {
      dc.root(pozer, "Pozer");
      if (title != null) {
         dc.appendWithSpace(title);
      }
      if (pozer == null) {
         dc.appendWithSpace("is NULL");
         return;
      }
      int align = pozer.get4(POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4);
      int fun = pozer.get1(POS_OFFSET_10_SIZER_FUN1);
      int anchorSrc = pozer.get1(POS_OFFSET_07_ANCHOR_POZEE1);
      int anchorDst = pozer.get1(POS_OFFSET_04_ANCHOR_ETALON1);

      dc.appendVarWithSpace("anchor on pozee (src)", ToStringStaticC.toStringLogicAlign(anchorSrc));
      dc.appendVarWithSpace("anchor on etalon (dest)", ToStringStaticC.toStringLogicAlign(anchorDst));

      dc.nl();
      //extra pozer modifying
      dc.appendVar("fun", ToStringStaticLayout.toStringPozerFun(fun));
      dc.appendVarWithSpace("value", align);
   }

   /**
    * 
    *
    * @param pozer 
    * @param ctx 
    * @return 
    */
   public String toStringPozer(ByteObject pozer, int ctx) {
      Dctx dc = new Dctx(lc.getUC());
      toStringPozer(pozer, dc, ctx);
      return dc.toString();
   }

   /**
    * Any context
    * @param pozer
    * @param dc
    */
   public void toStringPozer1Line(ByteObject pozer, Dctx dc) {
      dc.root1Line(pozer, "Pozer");
      int align = pozer.get4(POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4);
      int fun = pozer.get1(POS_OFFSET_10_SIZER_FUN1);
      int etalon = pozer.get1(POS_OFFSET_02_ETALON1);
      dc.appendVarWithSpace("val", align);
      dc.appendVarWithSpace("etalon", etalon);
   }

   /**
    * 
    *
    * @param pozer 
    * @param ctx 
    * @return 
    */
   public String toStringPozer1Line(ByteObject pozer, int ctx) {
      Dctx dc = new Dctx(lc.getUC());
      toStringPozer1Line(pozer, ctx, dc);
      return dc.toString();
   }

   /**
    * 
    *
    * @param pozer 
    * @param ctx 
    * @param dc 
    */
   public void toStringPozer1Line(ByteObject pozer, int ctx, Dctx dc) {
      dc.root1Line(pozer, "Pozer");
      if (ctx == CTX_1_WIDTH) {
         dc.appendWithSpace("X");
      } else {
         dc.appendWithSpace("Y");
      }
      int align = pozer.get4(POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4);
      int fun = pozer.get1(POS_OFFSET_10_SIZER_FUN1);
      int etalon = pozer.get1(POS_OFFSET_02_ETALON1);
      dc.appendVarWithSpace("val", align);
      dc.appendVarWithSpace("etalon", etalon);
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    * @param layoutable 
    */
   public void toStringPozerBot(ByteObject pozer, Dctx dc, ILayoutable layoutable) {
      dc.rootN(pozer, "PozerBot");
      toStringPrivatePozerY(pozer, dc);
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    * @param layoutable 
    */
   public void toStringPozerEnd(ByteObject pozer, Dctx dc, ILayoutable layoutable) {
      dc.rootN(pozer, "PozerEnd");
      toStringPrivatePozerX(pozer, dc);
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    */
   public void toStringPozerEtalon(ByteObject pozer, Dctx dc) {
      dc.nl();
      int etalon = pozer.get1(POS_OFFSET_02_ETALON1);
      dc.appendVarWithSpace("etalon", ToStringStaticLayout.toStringEtalonPozer(etalon));
      int etalonType = pozer.get1(POS_OFFSET_08_ANCHOR_POZEE_STRUCT1);
      dc.appendVarWithSpace("etfun", ToStringStaticLayout.toStringEtalonShort(etalon));
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    */
   public void toStringPozerOther(ByteObject pozer, Dctx dc) {
      int align = pozer.get4(POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4);
      int fun = pozer.get1(POS_OFFSET_10_SIZER_FUN1);
      dc.appendVar("value", align);
      dc.appendVar("fun", ToStringStaticLayout.toStringPozerFun(fun));

      //defines 
      int etalon = pozer.get1(POS_OFFSET_02_ETALON1);
      int etalonType = pozer.get1(POS_OFFSET_08_ANCHOR_POZEE_STRUCT1);
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    * @param layoutable 
    */
   public void toStringPozerStart(ByteObject pozer, Dctx dc, ILayoutable layoutable) {
      dc.rootN(pozer, "PozerStart");
      toStringPrivatePozerX(pozer, dc);
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    * @param layoutable 
    */
   public void toStringPozerTop(ByteObject pozer, Dctx dc, ILayoutable layoutable) {
      dc.rootN(pozer, "PozerTop");
      toStringPrivatePozerY(pozer, dc);
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    */
   public void toStringPozerValue(ByteObject pozer, Dctx dc) {
      dc.nl();
      int align = pozer.get4(POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4);
      int fun = pozer.get1(POS_OFFSET_10_SIZER_FUN1);
      dc.appendVar("value", align);
      dc.appendVarWithSpace("fun", ToStringStaticLayout.toStringPozerFun(fun));
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    */
   public void toStringPozerX(ByteObject pozer, Dctx dc) {
      dc.root(pozer, "PozerX");
      toStringPrivatePozerX(pozer, dc);
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    */
   public void toStringPozerY(ByteObject pozer, Dctx dc) {
      dc.root(pozer, "PozerY");
      int anchorSrc = pozer.get1(POS_OFFSET_07_ANCHOR_POZEE1);
      int anchorDst = pozer.get1(POS_OFFSET_04_ANCHOR_ETALON1);
      dc.appendVarWithSpace("anchor on pozee (src)", ToStringStaticC.toStringLogicAlignY(anchorSrc));
      dc.appendVarWithSpace("anchor on etalon (dest)", ToStringStaticC.toStringLogicAlignY(anchorDst));
      toStringPozerValue(pozer, dc);
      toStringPozerEtalon(pozer, dc);
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    */
   public void toStringPozerYBot(ByteObject pozer, Dctx dc) {
      dc.root(pozer, "PozerY");
      int anchorSrc = pozer.get1(POS_OFFSET_07_ANCHOR_POZEE1);
      int anchorDst = pozer.get1(POS_OFFSET_04_ANCHOR_ETALON1);
      dc.appendVarWithSpace("anchor on pozee (src)", ToStringStaticC.toStringLogicAlignY(anchorSrc));
      dc.appendVarWithSpace("anchor on etalon (dest)", ToStringStaticC.toStringLogicAlignY(anchorDst));
      toStringPozerValue(pozer, dc);
      toStringPozerEtalon(pozer, dc);

   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    */
   private void toStringPrivatePozerX(ByteObject pozer, Dctx dc) {
      toStringPrivatePozerXY(pozer, dc, true);
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    * @param isX 
    */
   private void toStringPrivatePozerXY(ByteObject pozer, Dctx dc, boolean isX) {
      if (pozer == null) {
         dc.appendWithSpace("is null");
         return;
      }
      int anchorSrc = pozer.get1(POS_OFFSET_07_ANCHOR_POZEE1);
      String strPozee = toStringPozee(pozer);
      if (isX) {
         dc.appendWithSpace(ToStringStaticC.toStringLogicAlignXStartEnd(anchorSrc));
      } else {
         dc.appendWithSpace(ToStringStaticC.toStringLogicAlignY(anchorSrc));
      }
      dc.appendWithSpace("of");
      dc.appendWithSpace(strPozee);
      dc.appendWithSpace("to");

      int etalon = pozer.get1(POS_OFFSET_02_ETALON1);
      if (etalon == POS_ETALON_0_POINT) {
         int point = pozer.get3(POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4);
         dc.appendVarWithSpace("single point", point);
      } else {
         int anchorDst = pozer.get1(POS_OFFSET_04_ANCHOR_ETALON1);
         String strEtalon = toStringEtalon(pozer);
         if (isX) {
            dc.appendWithSpace(ToStringStaticC.toStringLogicAlignXStartEnd(anchorDst));
         } else {
            dc.appendWithSpace(ToStringStaticC.toStringLogicAlignY(anchorDst));
         }
         dc.appendWithSpace("of");
         dc.appendWithSpace(strEtalon);
      }
   }

   /**
    * 
    *
    * @param pozer 
    * @param pozee 
    * @param dc 
    * @param isX 
    */
   private void toStringPrivatePozerXY(ByteObject pozer, ILayoutable pozee, Dctx dc, boolean isX) {
      int anchorSrc = pozer.get1(POS_OFFSET_07_ANCHOR_POZEE1);
      String strPozee = toStringPozee(pozer, pozee.toStringName());
      if (isX) {
         dc.appendWithSpace(ToStringStaticC.toStringLogicAlignXStartEnd(anchorSrc));
      } else {
         dc.appendWithSpace(ToStringStaticC.toStringLogicAlignY(anchorSrc));
      }
      dc.appendWithSpace("of");
      dc.appendWithSpace(strPozee);
      dc.appendWithSpace("to");

      int etalon = pozer.get1(POS_OFFSET_02_ETALON1);
      if (etalon == POS_ETALON_0_POINT) {
         int point = pozer.get3(POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4);
         dc.appendVarWithSpace("single point", point);
      } else {
         int anchorDst = pozer.get1(POS_OFFSET_04_ANCHOR_ETALON1);
         String strEtalon = toStringEtalon(pozer);
         if (isX) {
            dc.appendWithSpace(ToStringStaticC.toStringLogicAlignXStartEnd(anchorDst));
         } else {
            dc.appendWithSpace(ToStringStaticC.toStringLogicAlignY(anchorDst));
         }
         dc.appendWithSpace("of");
         dc.appendWithSpace(strEtalon);
      }
   }

   /**
    * 
    *
    * @param pozer 
    * @param dc 
    */
   private void toStringPrivatePozerY(ByteObject pozer, Dctx dc) {
      toStringPrivatePozerXY(pozer, dc, false);
   }

}
