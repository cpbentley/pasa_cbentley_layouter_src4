/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.tech;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.core.interfaces.IByteObject;
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
 * A: Left and Right height: {@link SizerFactory#getSizerFitParentLazy()} Left and right width :{@link SizerFactory#getSizerPrefLazy()} with max Parent.<br>
 *    Top and Bottom height: {@link SizerFactory#getSizerPrefLazy()} with max Parent Height; Top Width = {@link SizerFactory#getSizerFitParentLazy()} - LeftW -RightH
 *    Center Width - {@link SizerFactory#getSizerFitParentLazy()} - LeftW - RightW.
 *    Center Height - {@link SizerFactory#getSizerFitParentLazy()} - TopH - BottomH.
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
public interface IBOSizer extends IByteObject {

   /**
    * Can be computed on a long value.
    */
   public static final int SIZER_BASIC_SIZE              = A_OBJECT_BASIC_SIZE + 13;

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
   public static final int SIZER_FLAG_1_ALLOW_0          = 1;

   /**
    * Allows context to shrink this size.
    * <br>
    * When contextual content is smaller than provided size.
    * 
    * Shrink to preferred size.
    */
   public static final int SIZER_FLAG_2_ALLOW_SHRINK     = 1 << 1;

   /**
    * A minimum has been defined as a {@link ITechLayout}.
    */
   public static final int SIZER_FLAG_3_MINIMUM          = 1 << 2;

   /**
    * A maximum has been defined as a {@link ITechLayout}.
    */
   public static final int SIZER_FLAG_4_MAXIMUM          = 1 << 3;

   /**
    * Flag telling this sizing is also the minimum.
    */
   public static final int SIZER_FLAG_5_IS_MIN           = 1 << 4;

   /**
    * Flag telling this sizing is also the maximum.
    * <br>
    * Flag set to all maximum sizers. This allows to flag
    * a max as max.
    * <br>
    * Thus, there can be a relation to the dependencies.
    * 
    */
   public static final int SIZER_FLAG_6_IS_MAX           = 1 << 5;

   /**
    * Flag set at construction time telling engine that this size does not require context to be computed.
    */
   public static final int SIZER_FLAG_7_DEFINED          = 1 << 6;

   /**
    * When used for {@link ITechLayout#MODE_6_POZERS}, the second sizer is implicit.
    * 
    * Otherwise, the Layouter expects 2 Pozers
    */
   public static final int SIZER_FLAG_8_IMPLICIT         = 1 << 7;

   /**
    * <li> {@link IBOSizer#SIZER_FLAG_1_ALLOW_0}
    * <li> {@link IBOSizer#SIZER_FLAG_2_ALLOW_SHRINK}
    * <li> {@link IBOSizer#SIZER_FLAG_3_MINIMUM}
    * <li> {@link IBOSizer#SIZER_FLAG_4_MAXIMUM}
    * <li> {@link IBOSizer#SIZER_FLAG_5_IS_MIN}
    * <li> {@link IBOSizer#SIZER_FLAG_6_IS_MAX}
    * <li> {@link IBOSizer#SIZER_FLAG_7_DEFINED}
    * <li> {@link IBOSizer#SIZER_FLAG_8_IMPLICIT}
    */
   public static final int SIZER_OFFSET_01_FLAG          = A_OBJECT_BASIC_SIZE;

   /**
    * The mode for converting the {@link IBOSizer#SIZER_OFFSET_08_VALUE2} into a pixel value
    * <li> {@link ITechLayout#MODE_0_RAW_UNITS} raw value
    * <li> {@link ITechLayout#MODE_1_DELEGATE} a delegate will decide how to interpret this {@link ByteObject}
    * <li> {@link ITechLayout#MODE_3_SCALE} value according a given scale
    * <li> {@link ITechLayout#MODE_2_FUNCTION} 
    * <li> {@link ITechLayout#MODE_5_SIZERS} complex function
    * <li> {@link ITechLayout#MODE_6_POZERS} 
    * <br>
    * <br>.
    */
   public static final int SIZER_OFFSET_02_MODE1         = A_OBJECT_BASIC_SIZE + 1;

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
    * <li> {@link ITechLayout#RAW_UNIT_0_PIXEL}
    * <li> {@link ITechLayout#RAW_UNIT_1_DIP}
    */
   public static final int SIZER_OFFSET_03_ETALON1       = A_OBJECT_BASIC_SIZE + 2;

   /**
    * Sub type for the etalon.
    * 
    * When parent is used {@link IBOSizer#SIZER_OFFSET_03_ETALON1} this value can reference
    * 
    * yet another value such as parent, parent of parent, scrollbar, viewport, viewcontext
    * 
    * The specifics like scrollbar/viewport are left to the generic getter
    * 
    * {@link ILayoutable}
    * 
    * <p>
    * When {@link IBOSizer#SIZER_OFFSET_03_ETALON1} is {@link ITechLayout#ETALON_1_VIEWCONTEXT}
    * </p>
    * 
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_0_ROOT}
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_1_APPLI}
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_2_PARENT}
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_6_LINK}
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_5_CLIP}
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_3_SCREEN_MAIN}
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_4_SCREEN_ALL}
    * 
    * For Font
    * <li> {@link ITechLayout#ET_FONT_0_DEFAULT}
    * <li> {@link ITechLayout#ET_FONT_1_DEFINED}
    * <li> {@link ITechLayout#ET_FONT_5_SMALL}
    * <li> {@link ITechLayout#ET_FONT_6_MEDIUM}
    * 
    */
   public static final int SIZER_OFFSET_04_ET_SUBTYPE1   = A_OBJECT_BASIC_SIZE + 3;

   /**
    * Increase the granularity on the etalon width/height.
    * 
    * <p>
    * 
    * When etalon is <b>{@link ITechLayout#ETALON_1_VIEWCONTEXT}</b> :
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_0_ROOT}
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_1_APPLI}
    * 
    * </p>
    * 
    * <p>
    * When etalon is a regular <b> {@link ILayoutable}</b>
    * 
    * <li> {@link ITechLayout#SIZER_PROP_00_DRAWN}
    * <li> {@link ITechLayout#SIZER_PROP_01_PREFERRED}
    * <li> {@link ITechLayout#SIZER_PROP_02_UNIT_LOGIC}
    * <li> {@link ITechLayout#SIZER_PROP_03_FONT}
    * <li> {@link ITechLayout#SIZER_PROP_05_CONTENT}
    * </p>
    * <p>
    * Those values are provided by the 2 following method
    * <li>{@link ILayoutable#getSizePropertyValueW(int)}
    * <li>{@link ILayoutable#getSizePropertyValueH(int)}
    * </p>
    * 
    * <p>
    * When etalon is <b>{@link ITechLayout#ETALON_2_FONT}</b>
    * 
    * <li> {@link ITechLayout#ET_FONT_0_DEFAULT}
    * <li> {@link ITechLayout#ET_FONT_1_DEFINED}
    * <li> {@link ITechLayout#ET_FONT_5_SMALL}
    * <li> {@link ITechLayout#ET_FONT_6_MEDIUM}
    * <li> {@link ITechLayout#ET_FONT_7_LARGE}
    * </p>
    */
   public static final int SIZER_OFFSET_05_ET_PROPERTY1  = A_OBJECT_BASIC_SIZE + 4;

   /**
    * Once the etalon has been selected and the property to be used,
    * this operator decides which dimension width/height to use and how to use them
    * 
    * <p>
    * It will be used when etalon is one of those values
    * <li>{@link ITechLayout#ETALON_0_SIZEE_CTX}, 
    * <li>{@link ITechLayout#ETALON_4_PARENT}, 
    * <li>{@link ITechLayout#ETALON_5_LINK}
    * </p>
    * 
    * <p>
    * 
    * <br>
    * <li> {@link ITechLayout#ET_FUN_00_CTX}
    * <li> {@link ITechLayout#ET_FUN_01_WIDTH}
    * <li> {@link ITechLayout#ET_FUN_02_HEIGHT}
    * <li> {@link ITechLayout#ET_FUN_03_MIN}
    * <li> {@link ITechLayout#ET_FUN_04_MAX}
    * <li> {@link ITechLayout#ET_FUN_05_ADD}
    * <li> {@link ITechLayout#ET_FUN_06_DIFF}
    * <li> {@link ITechLayout#ET_FUN_07_CTX_INVERSE}
    * 
    * </p>
    * The sizer is a function.
    * 
    * <p>
    * When {@link ITechLayout#MODE_5_SIZERS}, this defines the function ID on the etalon
    * 
    * </p>
    * 
    */
   public static final int SIZER_OFFSET_06_ET_FUN1       = A_OBJECT_BASIC_SIZE + 5;

   /**
    * 
    */
   public static final int SIZER_OFFSET_07_ET_DATA2      = A_OBJECT_BASIC_SIZE + 6;

   /**
    * Instead of a value, a ratio gets a 8bits/8bits ratio.
    * <p>
    * See {@link IBOSizer#SIZER_OFFSET_08_VALUE2} for more details
    * </p>
    */
   public static final int SIZER_OFFSET_08_FRACTION_TOP2 = A_OBJECT_BASIC_SIZE + 8;

   /**
    * 
    */
   public static final int SIZER_OFFSET_08_FUN_X2        = A_OBJECT_BASIC_SIZE + 8;

   /**
    * Interpreation of the value depends on the {@link IBOSizer#SIZER_OFFSET_02_MODE1}
    * <li> {@link ITechLayout#MODE_0_RAW_UNITS} pixel
    * <li> {@link ITechLayout#MODE_1_DELEGATE} dip
    * <li> {@link ITechLayout#MODE_3_SCALE} The pixel are predefined from an etalon function
    *   <ol>
    *   <li> {@link ITechLayout#SIZE_0_NONE} ...
    *   <li> {@link ITechLayout#SIZE_1_SMALLEST} ...
    *   <li> {@link ITechLayout#SIZE_2_SMALL} ...
    *   <li> {@link ITechLayout#SIZE_4_BIG} ...
    *   <li> {@link ITechLayout#SIZE_5_BIGGEST} ...
    *   </ol>
    * <li> {@link ITechLayout#MODE_5_SIZERS} The pixel are predefined from an etalon function
    *  
    *  In Ratio mode, the first byte is the top of the fraction,
    *  the second byte is the bottom
    */
   public static final int SIZER_OFFSET_08_VALUE2        = A_OBJECT_BASIC_SIZE + 8;

   public static final int SIZER_OFFSET_09_DATA_EXTRA2   = A_OBJECT_BASIC_SIZE + 10;

   /**
    * When zero, engine will assume its 1.. to avoid division by zero.
    * <p>
    * See {@link IBOSizer#SIZER_OFFSET_08_VALUE2} for more details
    * </p>
    */
   public static final int SIZER_OFFSET_09_FRACTION_BOT2 = A_OBJECT_BASIC_SIZE + 10;

   public static final int SIZER_OFFSET_09_FUN_Y2        = A_OBJECT_BASIC_SIZE + 10;

   public static final int SIZER_OFFSET_10_OP_FUN1       = A_OBJECT_BASIC_SIZE + 12;

}
