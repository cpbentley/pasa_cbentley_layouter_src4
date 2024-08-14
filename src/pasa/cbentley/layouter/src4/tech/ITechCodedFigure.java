/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.tech;

import pasa.cbentley.core.src4.interfaces.ITech;
import pasa.cbentley.core.src4.utils.BitUtils;

/**
 * Int Coded sizers are used simple figure cases where we want to code a size efficiently on 32bits.
 * The most important modes and etalons are supported.
 * <li> {@link ITechCodedFigure#CODED_BITS_0_VALUE}
 * <li> {@link ITechCodedFigure#CODED_BITS_1_MODE}
 * <li> {@link ITechCodedFigure#CODED_BITS_3_ETALON}
 * <li> {@link ITechCodedFigure#CODED_BITS_3_ETALON_TYPE}
 * <li> {@link ITechCodedFigure#CODED_BITS_4_ETALON_FUN}
 * <li> {@link ITechCodedFigure#CODED_BITS_6_FLAGS}
 * 
 * @author Charles Bentley
 *
 */
public interface ITechCodedFigure extends ITechCodedHeader {

   /**
    * 11 bits. Max is {@link BitUtils#BIT_MASK_11}
    * <br>
    * Codes for {@link IBOSizer#SIZER_OFFSET_08_VALUE2}
    */
   public static final int CODED_BITS_0_VALUE                    = 12;

   /**
    * Function between the computed etalon value and the provided value
    * 4bits
    * <li> {@link ITechCodedFigure#FUNCTION_OP_00_NONE}
    * <li> {@link ITechLayout#FUNCTION_OP_01_ADD}
    * <li> {@link ITechLayout#FUNCTION_OP_02_MINUS}
    * <li> {@link ITechLayout#FUNCTION_OP_03_MULTIPLY}
    * <li> {@link ITechLayout#FUNCTION_OP_04_DIVIDE}
    * <li> {@link ITechLayout#FUNCTION_OP_05_RATIO}
    * <li> {@link ITechLayout#FUNCTION_OP_06_X_FOR_Y}
    */
   public static final int CODED_BITS_1_VALUE_FUNCTION           = 4;

   /**
    * <li> {@link ITechCodedFigure#RATIO_TYPE_0_FOR_100}
    * <li> {@link ITechCodedFigure#RATIO_TYPE_1_FOR_1000}
    * <li> {@link ITechCodedFigure#RATIO_TYPE_2_FOR_10}
    */
   public static final int CODED_BITS_2_VALUE_FUNCTION_AUX       = 4;

   /**
    * 2 bit for 2 possibilities
    * <li> Figure Rectangle
    * <li> Font Active
    */
   public static final int CODED_BITS_3_ETALON                   = 2;

   /**
    * Used to compute a value from the 2D etalon.
    * 
    * 4 bits for 7 modes 
    * <li> {@link ITechLayout#ET_FUN_00_CTX} Call must be made with {@link ISizable#codedSizeDecode(int,public static final int)}
    * <li> {@link ITechLayout#ET_FUN_01_WIDTH}
    * <li> {@link ITechLayout#ET_FUN_02_HEIGHT}
    * <li> {@link ITechLayout#ET_FUN_03_MIN}
    * <li> {@link ITechLayout#ET_FUN_04_MAX}
    * <li> {@link ITechLayout#ET_FUN_05_ADD}
    * <li> {@link ITechLayout#ET_FUN_06_DIFF}.
    */
   public static final int CODED_BITS_4_ETALON_FUN               = 4;

   /**
    * Decides which code page to use to interpret the remaining 26 bits.
    */
   public static final int CODED_BITS_5_CODE_PAGE                = 3;

   /**
    * Number of bit flags at the top.
    * <li> {@link ITechCodedFigure#CODED_SIZE_FLAG_30_CODED}
    * <li> {@link ITechCodedFigure#CODED_SIZE_FLAG_31_SIGN_CODE}
    * <li> {@link ITechCodedFigure#CODED_SIZE_FLAG_32_SIGN}
    */
   public static final int CODED_BITS_6_FLAGS                    = 3;

   /**
    * 12 bits for coding the 
    */
   public static final int CODED_MASK_0_VALUE                    = 0xFFF;

   public static final int CODED_MASK_1_VALUE_FUNCTION           = 0xF;

   public static final int CODED_MASK_2_VALUE_FUNCTION_AUX       = 0xF;

   public static final int CODED_MASK_3_ETALON                   = 0x3;

   public static final int CODED_MASK_4_ETALON_FUN               = 0xF;

 
   /**
    * Right Shift 
    */
   public static final int CODED_SIZE_SHIFT_0_VALUE              = 0;

   /**
    */
   public static final int CODED_SIZE_SHIFT_1_VALUE_FUNCTION     = CODED_SIZE_SHIFT_0_VALUE + CODED_BITS_0_VALUE;

   /**
    * 
    */
   public static final int CODED_SIZE_SHIFT_2_VALUE_FUNCTION_AUX = CODED_SIZE_SHIFT_1_VALUE_FUNCTION + CODED_BITS_1_VALUE_FUNCTION;

   public static final int CODED_SIZE_SHIFT_3_ETALON             = CODED_SIZE_SHIFT_2_VALUE_FUNCTION_AUX + CODED_BITS_2_VALUE_FUNCTION_AUX;

   public static final int CODED_SIZE_SHIFT_4_ETALON_FUN         = CODED_SIZE_SHIFT_3_ETALON + CODED_BITS_3_ETALON;

 
   /**
    * Returns the etalon value and ignores the provided value
    * 
    */
   public static final int FUNCTION_FIG_00_NONE                   = 0;

   public static final int FUNCTION_FIG_01_ADD                    = 1;

   public static final int FUNCTION_FIG_03_MULTIPLY               = 3;

   public static final int FUNCTION_FIG_04_DIVIDE                 = 4;

   public static final int FUNCTION_FIG_05_RATIO                  = 5;

   public static final int RATIO_TYPE_0_FOR_100                  = 0;

   public static final int RATIO_TYPE_1_FOR_1000                 = 1;

   public static final int RATIO_TYPE_2_FOR_10                   = 2;

   public static final int ETALON_0_RECT                         = 0;

}
