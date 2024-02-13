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
 * <li> {@link ITechCoded#CODED_BITS_0_VALUE}
 * <li> {@link ITechCoded#CODED_BITS_1_MODE}
 * <li> {@link ITechCoded#CODED_BITS_2_ETALON}
 * <li> {@link ITechCoded#CODED_BITS_3_ETALON_TYPE}
 * <li> {@link ITechCoded#CODED_BITS_4_ETALON_FUN}
 * <li> {@link ITechCoded#CODED_BITS_5_FLAGS}
 * 
 * @author Charles Bentley
 *
 */
public interface ITechCoded extends ITech {

   /**
    * The etalon must be explicitely given.
    */
   public static final int CODED_SIZE_FLAG_ETALON_EXPLICIT = 1 << 20;

   /**
    * When coding a perten size, explicitely says that the etalon
    * should be Height of the font.
    */
   public static final int CODED_SIZE_FLAG_ETALON_FONT     = 1 << 21;

   /**
    * When coding a percentage size, explicitely says that the etalon
    * should be Height of the screen.
    */
   public static final int CODED_SIZE_FLAG_ETALON_H        = 1 << 22;

   /**
    * 
    */
   public static final int CODED_SIZE_FLAG_ETALON_W        = 1 << 23;

   /**
    * public static final intermediary data is a Maximum value.
    */
   public static final int CODED_SIZE_FLAG_MAX_DIM         = 1 << 28;

   /**
    * Flag for a 32 bits coded size that has min dimension in pixels.
    */
   public static final int CODED_SIZE_FLAG_MIN_DIM         = 1 << 29;

   /**
    * If flag is set, the coded size should be read as a percent.
    */
   public static final int CODED_SIZE_TYPE_PERCENT         = 1 << 26;

   /**
    * If flag is set, coded size should be read as a permille.
    */
   public static final int CODED_SIZE_TYPE_PERMILLE        = 1 << 27;

   /**
    * If flag is set, coded size should be read as a perten.
    */
   public static final int CODED_SIZE_TYPE_PERTEN          = 1 << 25;

   /**
    * 11 bits. Max is {@link BitUtils#BIT_MASK_11}
    * <br>
    * Codes for {@link IBOSizer#SIZER_OFFSET_08_VALUE2}
    */
   public static final int CODED_BITS_0_VALUE              = 12;

   /**
    * 3 bits for 8 modes 
    * <li> {@link ITechLayout#MODE_1_DELEGATE}
    * <li> {@link ITechLayout#MODE_3_SCALE}.
    */
   public static final int CODED_BITS_1_MODE               = 2;

   /**
    * 4 bits for 8 possibilities
    * <li> {@link ITechLayout#ETALON_0_SIZEE_CTX}
    * <li> {@link ITechLayout#ETALON_1_PARENT}
    * <li> {@link ITechLayout#ETALON_1_VIEWCONTEXT}
    * <li> {@link ITechLayout#ETALON_3_RATIO}
    * <li> {@link ITechLayout#ETALON_2_FONT}.
    */
   public static final int CODED_BITS_2_ETALON             = 4;

   /**
    * 4 Bits for 16 p
    * 
    * For Views 
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_0_ROOT}
    * <li> {@link ITechLayout#ET_FONT_1_DEFINED}
    * <li> {@link ITechLayout#ET_FONT_4_BIG}
    * 
    * For fonts
    * <li> {@link ITechLayout#ET_FONT_0_DEFAULT}
    * <li> {@link ITechLayout#ET_FONT_1_DEFINED}
    * <li> {@link ITechLayout#ET_FONT_2_SMALL}
    * <li> {@link ITechLayout#ET_FONT_3_MEDIUM}
    * <li> {@link ITechLayout#ET_FONT_4_BIG}.
    */
   public static final int CODED_BITS_3_ETALON_TYPE        = 4;

   /**
    * 4 bits for 7 modes 
    * <li> {@link ITechLayout#ET_FUN_0_CTX} Call must be made with {@link ISizable#codedSizeDecode(int,public static final int)}
    * <li> {@link ITechLayout#ET_FUN_1_WIDTH}
    * <li> {@link ITechLayout#ET_FUN_2_HEIGHT}
    * <li> {@link ITechLayout#ET_FUN_3_MIN}
    * <li> {@link ITechLayout#ET_FUN_4_MAX}
    * <li> {@link ITechLayout#ET_FUN_5_ADD}
    * <li> {@link ITechLayout#ET_FUN_6_DIFF}.
    */
   public static final int CODED_BITS_4_ETALON_FUN         = 4;

   /**
    * Number of bit flags at the top.
    * <li> {@link ITechCoded#CODED_SIZE_FLAG_30_CODED}
    * <li> {@link ITechCoded#CODED_SIZE_FLAG_31_SIGN_CODE}
    * <li> {@link ITechCoded#CODED_SIZE_FLAG_32_SIGN}
    */
   public static final int CODED_BITS_5_FLAGS              = 3;

   /**
    * 
    */
   public static final int CODED_MASK_0_VALUE              = 0xFFF;

   /**
    * 111 3 bits coding
    */
   public static final int CODED_MASK_1_MODE               = 0x7;

   /**
    * 1111 4 bits coding
    * {@link IBOSizer#SIZER_OFFSET_03_ETALON1}
    */
   public static final int CODED_MASK_2_ETALON             = 0xF;

   /**
    * 1111 bits coding
    * 
    *  {@link IBOSizer#SIZER_OFFSET_04_ET_SUBTYPE1}
    */
   public static final int CODED_MASK_3_ETALON_TYPE        = 0xF;

   /**
    * {@link IBOSizer#SIZER_OFFSET_05_ET_PROPERTY1}
    */
   public static final int CODED_MASK_4_ETALON_PROP        = 0xF;

   /**
    * {@link IBOSizer#SIZER_OFFSET_06_ET_FUN1}
    */
   public static final int CODED_MASK_4_ETALON_FUN         = 0xF;


   /**
    * {@link IBOSizer#SIZER_OFFSET_10_OP_FUN1}
    */
   public static final int CODED_MASK_6_OP_FUN             = 0xF;

   /**
    * When this bit is set, we have a coded value. Otherwise,
    * return value without modification.
    * <br>
    * The sign bit is avoided to allow negative values
    * <br>
    * Set whenpublic static final int value is coded for a mode such as
    * <li> {@link ITechLayout#MODE_1_DELEGATE}
    * <li> {@link ITechLayout#MODE_3_SCALE}
    * <br>
    * <br>
    * Otherwise, raw pixel value
    */
   public static final int CODED_SIZE_FLAG_30_CODED        = 1 << 29;

   /**
    * This is a Java specific representation. 
    * <br>
    * nearly all computer represent negative number with 2 complements 
    * http://stackoverflow.com/questions/30496810/the-way-c-sharp-represents-negative-integers-in-memory-and-casts-them-unchecked
    * 
    * <li>When bit 32 is 1 and bit 31 is 0, we have a coded value.
    * <br>
    * <li>When bit 32 is 1 and bit 31 is 1 and bit 30 is 1 we have a sub code
    * <li>When bit 32 is 1 and bit 31 is 1 and bit 30 is 0 we have a negativepublic static final integer
    * 
    */
   public static final int CODED_SIZE_FLAG_31_SIGN_CODE    = 1 << 30;

   /**
    * Use to code for negative values.
    * <br>
    * How to detect a coded? First bit is 1 and second is 0.
    * <br>
    * 
    */
   public static final int CODED_SIZE_FLAG_32_SIGN         = 1 << 31;

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

   /**
    * 
    */
   public static final int CODED_SIZE_SHIFT_4_ETALON_FUN   = CODED_SIZE_SHIFT_3_ETALON_TYPE + CODED_BITS_3_ETALON_TYPE;

}
