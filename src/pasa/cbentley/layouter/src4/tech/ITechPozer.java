/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.tech;

import pasa.cbentley.byteobjects.src4.tech.ITechByteObject;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * Pozers compute the position of a <b>RDA</b> (<b>R</b>ectangle<b>D</b>raw<b>A</b>rea) relative to another <b>RDA</b>.
 *
 * @author Charles Bentley
 */
public interface ITechPozer extends ITechByteObject {

   /**
    * 
    */
   public static final int POS_BASIC_SIZE                           = A_OBJECT_BASIC_SIZE + 10;

   /**
    * Value {@link ITechPozer#POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4} is taken literally.
    * 
    * {@link ITechPozer#POS_OFFSET_07_ANCHOR_POZEE1} is used.
    * 
    * It can be seen as a point reference and mostly used in dynamic pozers used
    * to move a {@link ILayoutable} on a pixel path.
    * 
    * {@link ITechPozer#POS_OFFSET_10_SIZER_FUN1} holds the unit.
    * 
    * This is a shortcut for a Sizer defined value. 
    */
   public static final int POS_ETALON_0_POINT                       = 0;

   /**
    * Pozer anchor values interpreted relative to parent.
    */
   public static final int POS_ETALON_1_PARENT                      = 1;

   /**
    * Full view.
    */
   public static final int POS_ETALON_2_VIEWCTX                     = 2;

   /**
    * ID to a {@link ILayoutable}.
    */
   public static final int POS_ETALON_3_LINK                        = 3;

   /**
    * A sizer is defined and will be used instead of {@link ITechPozer#POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4}.
    */
   public static final int POS_ETALON_4_SIZER                       = 4;

   /**
    * 
    */
   public static final int POS_ETALON_5_NAV_TOPOLOGY                = 5;

   /**
    * 
    */
   public static final int POS_ETALON_6_LAYOUTABLE                  = 6;

   /**
    * Positive sizer that modifies (used for positive margin).
    * 
    * The relationship of the sizer is defined by
    */
   public static final int POS_FLAG_1_SIZER                         = 1;

   /**
    * Size value pulls value towards the center.
    */
   public static final int POS_FUN_0_TOWARDS_CENTER                 = 0;

   /**
    * 
    */
   public static final int POS_FUN_1_AWAY_CENTER                    = 1;

   /**
    * 
    */
   public static final int POS_FUN_2_TO_LEFT_TOP                    = 2;

   /**
    * 
    */
   public static final int POS_FUN_3_TO_RIGHT_BOT                   = 3;

   /**
    * 
    */
   public static final int POS_OFFSET_01_FLAG                       = A_OBJECT_BASIC_SIZE;

   /**
    * Etalon used to compute this x,w or y,h of the box
    * 
    * <li> {@link ITechPozer#POS_ETALON_0_POINT}
    * <li> {@link ITechPozer#POS_ETALON_1_PARENT}
    * 
    * <li> {@link ITechLayout#ETALON_0_SIZEE_CTX}
    * <li> {@link ITechLayout#ETALON_1_VIEWCONTEXT}
    * <li> {@link ITechLayout#ETALON_2_FONT}
    * <li> {@link ITechLayout#ETALON_3_RATIO}
    * <li> {@link ITechLayout#ETALON_4_DRAWABLE}
    * <li> {@link ITechLayout#ETALON_5_LINK}
    * <li> {@link ITechLayout#ETALON_6_POZER_BOX}
    * <li> {@link ITechLayout#ETALON_7_DELEGATE}
    * <li> {@link ITechLayout#ETALON_4_PARENT}.
    */
   public static final int POS_OFFSET_02_ETALON1                    = A_OBJECT_BASIC_SIZE + 1;

   /**
    * Coding data only valid when etalon is {@link ITechPozer#POS_ETALON_0_POINT}.
    * 
    * Signed 4 bytes
    * 
    * This field uses the pozee anchor is located on that point
    * <li> {@link ITechPozer#POS_OFFSET_04_ANCHOR_ETALON1}
    * <li> {@link ITechPozer#POS_OFFSET_05_ANCHOR_ETALON_STRUCT1}
    * <li> {@link ITechPozer#POS_OFFSET_06_ANCHOR_ETALON_STYLE1}
    */
   public static final int POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4 = A_OBJECT_BASIC_SIZE + 2;

   /**
    * Anchor at the Etalon RDA.
    * <li> {@link C#LOGIC_1_TOP_LEFT}
    * <li> {@link C#LOGIC_2_CENTER}
    * <li> {@link C#LOGIC_3_BOTTOM_RIGHT}
    * <br>
    * <br>
    * For example
    * <li> Width a Destination Anchor of Left and a Source Anchor of Right
    * <li> Height Destination Anchor of Top and a Source Anchor of Bot
    * 
    * will draw the RDA right bottom corner next to the top left corner of
    * destination
    * 
    */
   public static final int POS_OFFSET_04_ANCHOR_ETALON1             = A_OBJECT_BASIC_SIZE + 3; //+1 only use 1 byte

   /**
    * Relative to which sub element to compute the width
    * 
    *  <li> {@link ITechLayout#VIEW_STRUCT_00_ALL_VISIBLE}
    *  <li> {@link ITechLayout#VIEW_STRUCT_01_INNER_CONTENT}
    *  <li> {@link ITechLayout#VIEW_STRUCT_02_VIEW_PORT}
    *  <li> {@link ITechLayout#VIEW_STRUCT_03_VIEW_PORT_ARTIFACTS}.
    */
   public static final int POS_OFFSET_05_ANCHOR_ETALON_STRUCT1      = A_OBJECT_BASIC_SIZE + 4;

   /**
    * Relative to which sub element to compute the width
    * 
    * <li> {@link ITechLayout#VIEW_STYLE_00_VIEW_FULL}
    * <li> {@link ITechLayout#VIEW_STYLE_01_VIEW_CONTENT_PAD_BORDER}
    * <li> {@link ITechLayout#VIEW_STYLE_02_VIEW_CONTENT_PAD}
    * <li> {@link ITechLayout#VIEW_STYLE_03_VIEW_CONTENT}.
    */
   public static final int POS_OFFSET_06_ANCHOR_ETALON_STYLE1       = A_OBJECT_BASIC_SIZE + 5;

   /**
    * Anchor of the source RDA, i.e. the child RDA
    * <li> {@link C#LOGIC_1_TOP_LEFT}
    * <li> {@link C#LOGIC_2_CENTER}
    * <li> {@link C#LOGIC_3_BOTTOM_RIGHT}
    */
   public static final int POS_OFFSET_07_ANCHOR_POZEE1              = A_OBJECT_BASIC_SIZE + 6;

   /**
    * Relative to which sub element to compute the width
    * 
    *  <li> {@link ITechLayout#VIEW_STRUCT_00_ALL_VISIBLE}
    *  <li> {@link ITechLayout#VIEW_STRUCT_01_INNER_CONTENT}
    *  <li> {@link ITechLayout#VIEW_STRUCT_02_VIEW_PORT}
    *  <li> {@link ITechLayout#VIEW_STRUCT_03_VIEW_PORT_ARTIFACTS}.
    */
   public static final int POS_OFFSET_08_ANCHOR_POZEE_STRUCT1       = A_OBJECT_BASIC_SIZE + 7;

   /**
    * Relative to which sub element to compute the width
    * 
    * <li> {@link ITechLayout#VIEW_STYLE_00_VIEW_FULL}
    * <li> {@link ITechLayout#VIEW_STYLE_01_VIEW_CONTENT_PAD_BORDER}
    * <li> {@link ITechLayout#VIEW_STYLE_02_VIEW_CONTENT_PAD}
    * <li> {@link ITechLayout#VIEW_STYLE_03_VIEW_CONTENT}.
    */
   public static final int POS_OFFSET_09_ANCHOR_POZEE_STYLE1        = A_OBJECT_BASIC_SIZE + 8;

   /**
    * When sizer is defined, this field is a function that modifies the anchor on pozee.
    * 
    * <li> {@link ITechPozer#POS_FUN_0_TOWARDS_CENTER} when outside of center, x is towards the center
    * <li> {@link ITechPozer#POS_FUN_1_AWAY_CENTER}
    * <li> {@link ITechPozer#POS_FUN_2_TO_LEFT_TOP}
    * <li> {@link ITechPozer#POS_FUN_3_TO_RIGHT_BOT}
    * 
    * When etalon is {@link ITechPozer#POS_ETALON_0_POINT};
    * this field holds the unit
    * <li> {@link ITechLayout#RAW_UNIT_0_PIXEL}
    * <li> {@link ITechLayout#RAW_UNIT_1_DIP}
    */
   public static final int POS_OFFSET_10_SIZER_FUN1                 = A_OBJECT_BASIC_SIZE + 9;
}
