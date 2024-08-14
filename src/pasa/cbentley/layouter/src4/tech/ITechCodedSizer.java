/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.tech;

import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * Int Coded sizers are used simple figure cases where we want to code a size efficiently on 32bits.
 * The most important modes and etalons are supported.
 * <li> {@link ITechCodedSizer#CODED_BITS_0_VALUE}
 * <li> {@link ITechCodedSizer#CODED_BITS_1_MODE}
 * <li> {@link ITechCodedSizer#CODED_BITS_2_ETALON}
 * <li> {@link ITechCodedSizer#CODED_BITS_3_ETALON_TYPE}
 * <li> {@link ITechCodedSizer#CODED_BITS_5_ETALON_FUN}
 * <li> {@link ITechCodedSizer#CODED_BITS_5_FLAGS}
 * 
 * @author Charles Bentley
 *
 */
public interface ITechCodedSizer extends ITechCodedHeader {

   /**
    * 12 bits. Max is {@link BitUtils#BIT_MASK_11}
    * <br>
    * Codes for {@link IBOSizer#SIZER_OFFSET_08_VALUE2}
    */
   public static final int CODED_BITS_0_VALUE              = 12;

   /**
    * 0 bits for 1 default mode which is {@link ITechLayout#MODE_2_FUNCTION} 
    */
   public static final int CODED_BITS_1_MODE               = 0;

   /**
    * 3 bits for 8 possibilities
    * <li> {@link ITechLayout#ETALON_0_SIZEE_CTX}
    * <li> {@link ITechLayout#ETALON_1_VIEWCONTEXT}
    * <li> {@link ITechLayout#ETALON_2_FONT}.
    * <li> {@link ITechLayout#ETALON_3_RATIO}
    * <li> {@link ITechLayout#ETALON_4_PARENT}
    */
   public static final int CODED_BITS_2_ETALON             = 3;

   /**
    * 2 Bits for 4 etalon sub types
    * 
    * For Views 
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_0_ROOT}
    * <li> {@link ITechLayout#ET_FONT_1_DEFINED}
    * <li> {@link ITechLayout#ET_FONT_7_LARGE}
    * 
    * For ViewContext
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_0_ROOT} 
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_1_APPLI} 
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_2_PARENT} 
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_3_SCREEN_MAIN} 
    * 
    * For fonts
    * <li> {@link ITechLayout#ET_FONT_0_DEFAULT}
    * <li> {@link ITechLayout#ET_FONT_1_DEFINED}
    * <li> {@link ITechLayout#ET_FONT_5_SMALL}
    * <li> {@link ITechLayout#ET_FONT_6_MEDIUM}
    * 
    * For Layoutable etalons 
    * <li> {@link ITechLayout#SIZER_PROP_00_DRAWN}
    * <li> {@link ITechLayout#SIZER_PROP_01_PREFERRED}
    * <li> {@link ITechLayout#SIZER_PROP_02_UNIT_LOGIC}
    * <li> {@link ITechLayout#SIZER_PROP_03_FONT}
    */
   public static final int CODED_BITS_3_ETALON_TYPE        = 2;

   /**
    * 
    * 2 bits for 4 different properties.
    * 
    * When etalon is a regular <b> {@link ILayoutable}</b>
    * 
    * <li> {@link ITechLayout#SIZER_PROP_00_DRAWN}
    * <li> {@link ITechLayout#SIZER_PROP_01_PREFERRED}
    * <li> {@link ITechLayout#SIZER_PROP_02_UNIT_LOGIC}
    * <li> {@link ITechLayout#SIZER_PROP_03_FONT}
    */
   public static final int CODED_BITS_4_ETALON_PROP        = 2;

   /**
    * 2 bits for 4 modes 
    * <li> {@link ITechLayout#ET_FUN_00_CTX} Call must be made with {@link ISizable#codedSizeDecode(int,public static final int)}
    * <li> {@link ITechLayout#ET_FUN_01_WIDTH}
    * <li> {@link ITechLayout#ET_FUN_02_HEIGHT}
    * <li> {@link ITechLayout#ET_FUN_03_MIN}
    */
   public static final int CODED_BITS_5_ETALON_FUN         = 2;

   /**
    * 3 bits
    * 
    * Function between the computed etalon value and the provided value
    * <li> {@link ITechLayout#FUNCTION_OP_00_NONE}
    * <li> {@link ITechLayout#FUNCTION_OP_01_ADD}
    * <li> {@link ITechLayout#FUNCTION_OP_02_MINUS}
    * <li> {@link ITechLayout#FUNCTION_OP_03_MULTIPLY}
    * <li> {@link ITechLayout#FUNCTION_OP_04_DIVIDE}
    * <li> {@link ITechLayout#FUNCTION_OP_05_RATIO}
    * <li> {@link ITechLayout#FUNCTION_OP_06_X_FOR_Y}
    */
   public static final int CODED_BITS_6_FUNCTION           = 3;

   /**
    * 2 bits for 4 types
    * <li> {@link ITechCodedFigure#RATIO_TYPE_0_FOR_100}
    * <li> {@link ITechCodedFigure#RATIO_TYPE_1_FOR_1000}
    * <li> {@link ITechCodedFigure#RATIO_TYPE_2_FOR_10}
    */
   public static final int CODED_BITS_7_FUN_AUX            = 2;

   /**
    * 12 bits for coding the 
    */
   public static final int CODED_MASK_0_VALUE              = 0xFFF;

   /**
    * 0 bits coding
    * 
    * <li> {@link ITechLayout#MODE_2_FUNCTION}
    */
   public static final int CODED_MASK_1_MODE               = 0;

   /**
    * 3 bits coding
    */
   public static final int CODED_MASK_2_ETALON             = 0x7;

   /**
    * 2 bits coding
    */
   public static final int CODED_MASK_3_ETALON_TYPE        = 0x3;

   /**
    * 2 bits coding
    */
   public static final int CODED_MASK_4_ETALON_PROP        = 0x3;

   /**
    * 2 bits
    */
   public static final int CODED_MASK_5_ETALON_FUN         = 0x3;

   /**
    * 3 bits
    */
   public static final int CODED_MASK_6_FUNCTION           = 0x7;

   /**
    * 2 bits
    */
   public static final int CODED_MASK_7_FUNCTION_AUX       = 0x3;

   /**
    * 
    */
   public static final int CODED_SIZE_SHIFT_0_VALUE        = 0;

   /**
    * Four modes
    * <li> {@link ITechLayout#MODE_1_DELEGATE}
    * <li> {@link ITechLayout#MODE_3_SCALE}.
    */
   public static final int CODED_SIZE_SHIFT_1_MODE         = CODED_SIZE_SHIFT_0_VALUE + CODED_BITS_0_VALUE;

   /**
    * 
    */
   public static final int CODED_SIZE_SHIFT_2_ETALON       = CODED_SIZE_SHIFT_1_MODE + CODED_BITS_1_MODE;

   /**
    * 
    */
   public static final int CODED_SIZE_SHIFT_3_ETALON_TYPE  = CODED_SIZE_SHIFT_2_ETALON + CODED_BITS_2_ETALON;

   public static final int CODED_SIZE_SHIFT_4_ETALON_PROP  = CODED_SIZE_SHIFT_3_ETALON_TYPE + CODED_BITS_3_ETALON_TYPE;

   /**
    * 
    */
   public static final int CODED_SIZE_SHIFT_5_ETALON_FUN   = CODED_SIZE_SHIFT_4_ETALON_PROP + CODED_BITS_4_ETALON_PROP;

   public static final int CODED_SIZE_SHIFT_6_FUNCTION     = CODED_SIZE_SHIFT_5_ETALON_FUN + CODED_BITS_5_ETALON_FUN;

   public static final int CODED_SIZE_SHIFT_7_FUNCTION_AUX = CODED_SIZE_SHIFT_6_FUNCTION + CODED_BITS_6_FUNCTION;

   public static final int RATIO_TYPE_0_FOR_100            = 0;

   public static final int RATIO_TYPE_1_FOR_1000           = 1;

   public static final int RATIO_TYPE_2_FOR_10             = 2;
}
