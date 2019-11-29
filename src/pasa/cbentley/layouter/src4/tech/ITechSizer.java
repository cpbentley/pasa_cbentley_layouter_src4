package pasa.cbentley.layouter.src4.tech;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.tech.ITechByteObject;
import pasa.cbentley.layouter.src4.engine.SizerFactory;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * Sizers compute the width and height of a RDA.
 * <li> relative to an etalon, usually another RDA
 * <li> absolute unit
 * <br>
 * <br>
 * Q: What's a sizer who size is content size?
 * A: {@link SizerFactory#getSizerPrefLazy()}
 * <br>
 * <br>
 * Q: What's are the sizers for a Swing BorderLayout <br>
 * A: Left and Right height: {@link SizerFactory#getSizerMatchParentLazy()} Left and right width :{@link SizerFactory#getSizerPrefLazy()} with max Parent.<br>
 *    Top and Bottom height: {@link SizerFactory#getSizerPrefLazy()} with max Parent Height; Top Width = {@link SizerFactory#getSizerMatchParentLazy()} - LeftW -RightH
 *    Center Width - {@link SizerFactory#getSizerMatchParentLazy()} - LeftW - RightW.
 *    Center Height - {@link SizerFactory#getSizerMatchParentLazy()} - TopH - BottomH.
 * <br>
 * <br>
 * Base definition of a size in the context of drawing in a <b>R</b>ectangle<b>D</b>raw<b>A</b>rea or <b>RDA</b>
 * <br>
 * <li> Defines a Sizer {@link ByteObject} describing how to compute a "size" value in the context of a RDA.
 * <li> Defines a Pozer {@link ByteObject} for the relative position of a "size" relative to another "size".
 * 
 * @author Charles Bentley
 *
 */
public interface ITechSizer extends ITechByteObject {

   public static final int ET_PROP_4_SIZE_FONT_RATIO        = 4;

   /**
    * Can be computed on a long value
    */
   public static final int SIZER_BASIC_SIZE                 = A_OBJECT_BASIC_SIZE + 7;

   /**
    * The results of computations can be set to zero.
    * <br>
    * <br>
    * 
    * By default this flag is not set to prevent division by zero.
    * <br>
    * When a size can be zero, set this flag to true.
    * 
    */
   public static final int SIZER_FLAG_1_ALLOW_0             = 1;

   /**
    * Allows context to shrink this size.
    * <br>
    */
   public static final int SIZER_FLAG_2_ALLOW_SHRINK        = 1 << 1;

   /**
    * A minimum has been defined as a {@link ITechLayout}.
    */
   public static final int SIZER_FLAG_3_MINIMUM             = 1 << 2;

   /**
    * A maximum has been defined as a {@link ITechLayout}
    */
   public static final int SIZER_FLAG_4_MAXIMUM             = 1 << 3;

   /**
    * Flag telling this sizing is also the minimum.
    */
   public static final int SIZER_FLAG_5_IS_MIN              = 1 << 4;

   /**
    * Flag telling this sizing is also the maximum.
    * <br>
    * Flag set to all maximum sizers. This allows to flag
    * a max as max.
    * <br>
    * Thus, there can be a relation to the dependencies.
    * 
    */
   public static final int SIZER_FLAG_6_IS_MAX              = 1 << 5;

   /**
    * Does not requires context to compute.
    * <br>
    * Flag set at construction time
    */
   public static final int SIZER_FLAG_7_DEFINED             = 1 << 6;

   /**
    * When used for {@link ITechLayout#MODE_6_POZER_DISTANCE}, the second sizer is implicit
    */
   public static final int SIZER_FLAG_8_IMPLICIT            = 1 << 7;

   public static final int SIZER_OFFSET_01_FLAG             = A_OBJECT_BASIC_SIZE;

   /**
    * The mode for converting the {@link ITechSizer#SIZER_OFFSET_05_VALUE2} into a pixel value
    * <li> {@link ITechLayout#MODE_0_RAW_UNITS} raw value
    * <li> {@link ITechLayout#MODE_1_DELEGATE} 
    * <li> {@link ITechLayout#MODE_2_RATIO} value relative to an etalon
    * <li> {@link ITechLayout#MODE_3_SCALE} value according a given scale
    * <li> {@link ITechLayout#MODE_5_FUNCTION} complex function
    * <br>
    * <br>
    * 
    */
   public static final int SIZER_OFFSET_02_MODE1            = A_OBJECT_BASIC_SIZE + 1;

   /**
    * <br>
    * The base unit is computed from an etalon. A function is used.
    * <li> {@link ITechLayout#ETALON_0_SIZEE_CTX}  Depends on context
    * <li> {@link ITechLayout#ETALON_1_VIEWCONTEXT}  Depends on context
    * <li> {@link ITechLayout#ETALON_2_FONT}  Depends on context
    * <li> {@link ITechLayout#ETALON_3_RATIO}  Depends on context
    * <li> {@link ITechLayout#ETALON_4_PARENT}  Depends on context
    * <li> {@link ITechLayout#ETALON_5_LINK} 
    * <li> {@link ITechLayout#ETALON_6_POZER_BOX} 
    * <li> {@link ITechLayout#ETALON_7_DELEGATE} 
    * 
    * When mode is {@link ITechLayout#MODE_0_RAW_UNITS}
    */
   public static final int SIZER_OFFSET_03_ETALON1          = A_OBJECT_BASIC_SIZE + 2;

   /**
    * 
    * When etalon is 
    * {@link ITechLayout#ETALON_0_SIZEE_CTX}, 
    * {@link ITechLayout#ETALON_4_PARENT}, 
    * {@link ITechLayout#ETALON_5_LINK}
    * 
    * this field decides which dimension width/height to use and how to use them
    * 
    * When {@link ITechLayout#MODE_5_FUNCTION}, this defines the function ID on the etalon
    * <br>
    * <li> {@link ITechLayout#ET_FUN_0_CTX}
    * <li> {@link ITechLayout#ET_FUN_1_WIDTH}
    * <li> {@link ITechLayout#ET_FUN_2_HEIGHT}
    * <li> {@link ITechLayout#ET_FUN_3_MIN}
    * <li> {@link ITechLayout#ET_FUN_4_MAX}
    * <li> {@link ITechLayout#ET_FUN_5_ADD}
    * <li> {@link ITechLayout#ET_FUN_6_DIFF}
    * <li> {@link ITechLayout#ET_FUN_7_CTX_OP}
    * 
    * The sizer is a function
    * 
    */
   public static final int SIZER_OFFSET_04_FUNCTION1        = A_OBJECT_BASIC_SIZE + 3;

   public static final int SIZER_OFFSET_05_FRAC_BOT1        = A_OBJECT_BASIC_SIZE + 4 + 1;

   public static final int SIZER_OFFSET_05_FRAC_TOP1        = A_OBJECT_BASIC_SIZE + 4;

   /**
    * Interpreation of the value depends on the {@link ITechSizer#SIZER_OFFSET_02_MODE1}
    * <li> {@link ITechLayout#MODE_0_RAW_UNITS} pixel
    * <li> {@link ITechLayout#MODE_1_DELEGATE} dip
    * <li> {@link ITechLayout#MODE_2_RATIO} 2 bytes float with 4 bits above 0, 12 bits below
    * <li> {@link ITechLayout#MODE_3_SCALE} The pixel are predefined from an etalon function
    *   <ol>
    *   <li> {@link ITechLayout#SIZE_0_NONE} ...
    *   <li> {@link ITechLayout#SIZE_1_SMALLEST} ...
    *   <li> {@link ITechLayout#SIZE_2_SMALL} ...
    *   <li> {@link ITechLayout#SIZE_4_BIG} ...
    *   <li> {@link ITechLayout#SIZE_5_BIGGEST} ...
    *   </ol>
    * <li> {@link ITechLayout#MODE_5_FUNCTION} The pixel are predefined from an etalon function
    *  
    *  In Ratio mode, the first byte is the top of the fraction,
    *  the second byte is the bottom
    */
   public static final int SIZER_OFFSET_05_VALUE2           = A_OBJECT_BASIC_SIZE + 4;

   /**
    * Increase granularity on the etalon.
    * <br>
    * Type of Font, Type of ViewContext.
    * <br>
    * <li> {@link ITechLayout#E_VIEWCONTEXT_0_ROOT}
    * <li> {@link ITechLayout#E_VIEWCONTEXT_1_APPLI}
    * <br>
    * <br>
    * <p>
    * When etalon is a <b>regular full {@link ILayoutable}</b>
    * <li> {@link ITechSizer#SIZER_PROP_00_DRAWN}
    * <li> {@link ITechSizer#SIZER_PROP_01_PREFERRED}
    * <li> {@link ITechSizer#SIZER_PROP_02_UNIT_LOGIC}
    * <li> {@link ITechSizer#SIZER_PROP_03_FONT}
    * <li> {@link ITechSizer#SIZER_PROP_05_CONTENT}
    * <br>
    * </p>
    * 
    * <br>
    * <p>
    * When etalon is <b>{@link ITechLayout#ETALON_2_FONT}</b>
    * 
    * <li> {@link ITechLayout#E_FONT_0_DEFAULT}
    * <li> {@link ITechLayout#E_FONT_1_DEFINED}
    * <li> {@link ITechLayout#E_FONT_2_SMALL}
    * <li> {@link ITechLayout#E_FONT_3_MEDIUM}
    * <li> {@link ITechLayout#E_FONT_4_BIG}
    * </p>
    */
   public static final int SIZER_OFFSET_06_PROPERTY1        = A_OBJECT_BASIC_SIZE + 6;

   public static final int SIZER_OFFSET_07_ETALON_SUBTYPE1  = A_OBJECT_BASIC_SIZE + 7;

   public static final int SIZER_OFFSET_08_ETALON_DATA1     = A_OBJECT_BASIC_SIZE + 8;

   public static final int SIZER_OFFSET_09_ETALON_DATA2     = A_OBJECT_BASIC_SIZE + 9;

   /**
    * The 4 main are coded on 2 bits.
    * Size actually drawn
    */
   public static final int SIZER_PROP_00_DRAWN              = 0;

   /**
    * Preferred size includes content, padding, border and margin
    */
   public static final int SIZER_PROP_01_PREFERRED          = 1;

   /**
    * Size of a unit, contextual to {@link ILayoutable}
    */
   public static final int SIZER_PROP_02_UNIT_LOGIC         = 2;

   public static final int SIZER_PROP_03_FONT               = 3;

   /**
    * Size of content, padding and border. ie drawn size minus margin
    */
   public static final int SIZER_PROP_07_CONTENT_PAD_BORDER = 4;

   /**
    * Size of content. ie drawn size minus margin,border and padding
    */
   public static final int SIZER_PROP_05_CONTENT            = 5;

   /**
    * Size of content and padding. ie drawn size minus margin,border
    */
   public static final int SIZER_PROP_06_CONTENT_PAD        = 6;

   public static final int SIZER_PROP_10_PAD                = 10;

   public static final int SIZER_PROP_11_PAD_BORDER         = 11;

   public static final int SIZER_PROP_12_PAD_BORDER_MARGIN  = 12;

   public static final int SIZER_PROP_13_BORDER             = 13;

   public static final int SIZER_PROP_14_BORDER_MARGIN      = 14;

   public static final int SIZER_PROP_15_MARGIN             = 15;

}
