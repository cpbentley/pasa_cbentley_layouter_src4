/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.tech;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.interfaces.ITech;
import pasa.cbentley.core.src4.interfaces.ITechNav;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ToStringStaticLayout;
import pasa.cbentley.layouter.src4.engine.ByteObjectLayoutDelegate;
import pasa.cbentley.layouter.src4.engine.LayouterEngine;
import pasa.cbentley.layouter.src4.engine.Zer2DArea;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegateMax;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * @author Charles-Philip Bentley
 *
 */
public interface ITechLayout extends ITech {

   /**
    * 
    */
   public static final int ATODO_ET_PROP_4_SIZE_FONT_RATIO       = 4;

   /**
    * 
    */
   public static final int COMPUTE_0_INVALID                     = 0;

   /**
    * 
    */
   public static final int COMPUTE_1_NORMAL                      = 1;

   /**
    * 
    */
   public static final int COMPUTE_2_INVERSE                     = 2;

   /**
    * 
    */
   public static final int COMPUTE_3_BOTH                        = 3;

   /**
    * 
    */
   public static final int COMPUTE_SIZE_0_NORMAL                 = 0;

   /**
    * 
    */
   public static final int COMPUTE_SIZE_1_ONLY_W                 = 1;

   /**
    * 
    */
   public static final int COMPUTE_SIZE_2_ONLY_H                 = 2;

   /**
    * 
    */
   public static final int COMPUTE_SIZE_3_NONE                   = 3;

   /**
    * implicit width or height.
    */
   public static final int CTX_0_NONE                            = 0;

   /**
    * Use the width of the etalon.
    */
   public static final int CTX_1_WIDTH                           = 1;

   /**
    * use the height.
    */
   public static final int CTX_2_HEIGHT                          = 2;

   public static final int CTX_3_BOTH                            = 3;

   /**
    * 
    */
   public static final int DELEGATE_ETALON_0_PREFERRED           = 0;

   /**
    * 
    * Looks for an {@link ILayoutDelegate} from a {@link ByteObjectLayoutDelegate} in the Sizer.
    * 
    * This gets you an {@link ILayoutDelegate}
    * 
    * For the mode {@link ITechLayout#MODE_1_DELEGATE}, 
    * 
    * <li> {@link ILayoutDelegate#getDelegateSizeWidth(ByteObject, ILayoutable)}
    * 
    * Used when value is a custom function
    */
   public static final int DELEGATE_ETALON_0_REFERENCE           = 0;

   /**
    * 
    * {@link ILayoutable#getLayoutableParent()}
    * 
    * This mode, takes the biggest possible value provided by the context
    * <br>
    * The parent is responsible to find that.. Delegate mode
    * 
    * Ask the Parent the maximum what? example? fill
    * 
    * {@link ILayoutDelegateMax}
    * 
    */
   public static final int DELEGATE_ETALON_1_PARENT_MAX          = 1;

   public static final int DELEGATE_POINT_VALUE                  = -1;

   /**
    * Keep in memory. Contrast with {@link ITechLayout#DEPENDENCY_X_DELETE}
    */
   public static final int DEPENDENCY_0_NONE                     = 0;

   /**
    * {@link ILayoutable} depends on the size of another {@link ILayoutable}.
    */
   public static final int DEPENDENCY_1_SIZE                     = 1 << 0;

   /**
    * 
    */
   public static final int DEPENDENCY_2_POZE                     = 1 << 1;

   /**
    * {@link ILayoutable} depends on the size anb position of the {@link ILayoutable}.
    */
   public static final int DEPENDENCY_3_BOTH                     = DEPENDENCY_1_SIZE | DEPENDENCY_2_POZE;

   /**
    * Flag is set the dependency is parental
    */
   public static final int DEPENDENCY_4_PARENT                   = 1 << 3;

   /**
    * 
    */
   public static final int DEPENDENCY_X_DELETE                   = -1;

   /**
    * Its usually medium
    */
   public static final int ET_FONT_0_DEFAULT                     = 0;

   /**
    * Font defined in the sub.
    */
   public static final int ET_FONT_1_DEFINED                     = 1;

   public static final int ET_FONT_2_DEBUG                       = 2;

   public static final int ET_FONT_3_                            = 3;

   public static final int ET_FONT_4_TINY                        = 4;

   /**
    * 
    */
   public static final int ET_FONT_5_SMALL                       = 5;

   /**
    * 
    */
   public static final int ET_FONT_6_MEDIUM                      = 6;

   /**
    * 
    */
   public static final int ET_FONT_7_LARGE                       = 7;

   public static final int ET_FONT_8_HUGE                        = 8;

   public static final int ET_FONT_PROP_0_W                      = 0;

   public static final int ET_FONT_PROP_1_H                      = 1;

   public static final int ET_FONT_PROP_3_ASCENT                 = 3;

   /**
    * Function that returns the input context value.
    * <br>
    * <br>
    * <li> When computing a width size, returns  the width of the etalon
    * <li> When computing a height size, returns  the height of the etalon
    * 
    */
   public static final int ET_FUN_00_CTX                         = 0;

   /**
    * Take the width of the contextual etalon.
    * <br>
    * <br>
    * When etalon is {@link ITechLayout#ETALON_1_VIEWCONTEXT},
    * the etalon is the width of the View.
    */
   public static final int ET_FUN_01_WIDTH                       = 1;

   /**
    * 
    */
   public static final int ET_FUN_02_HEIGHT                      = 2;

   /**
    * Take the minimum between W and H of etalon.
    */
   public static final int ET_FUN_03_MIN                         = 3;

   /**
    * Take the maximum between W and H.
    */
   public static final int ET_FUN_04_MAX                         = 4;

   /**
    * Add w and h.
    */
   public static final int ET_FUN_05_ADD                         = 5;

   /**
    * Take the difference between w and h.
    */
   public static final int ET_FUN_06_DIFF                        = 6;

   /**
    * Inverse of context.
    */
   public static final int ET_FUN_07_CTX_INVERSE                 = 7;

   /**
    * things are computed by the delegate
    */
   public static final int ET_FUN_08_DELEGATE                    = 8;

   public static final int ET_FUN_10_MIN_HALF                    = 10;

   public static final int ET_FUN_11_MAX_HALF                    = 11;

   /**
    * Etalon link type {@link IBOSizer#SIZER_OFFSET_05_ET_PROPERTY1}.
    */
   public static final int ET_LINK_0_ID                          = 0;

   /**
    * Contextual value relative to the Parent of the caller....
    * {@link ISizer#POS_OFFSET_08_ANCHOR_POZEE_STRUCT1} defines which Drawable to get.
    * 
    * A sizer is loaded in sub.
    * <br>
    *  size of parent
    * 
    * Example:
    */
   public static final int ET_LINK_0_PARENT                      = 0;

   /**
    * Drawn size minus styles.
    *
    * @see ITechNav
    */
   public static final int ET_LINK_1_NAV                         = 1;

   /**
    * Provides by a 4 byte UIID.
    */
   public static final int ET_LINK_2_UIID                        = 2;

   /**
    * 
    */
   public static final int ET_LINK_3_LAYOUTABLE                  = 3;

   /**
    * The root view context of the application.
    * If the application has a menu and other artifacts around,
    * they are included. Its really the root.
    * We want the sizee to position/size relative to this reference.
    */
   public static final int ET_VIEWCONTEXT_0_ROOT                 = 0;

   /**
    * THe view context of the application.
    */
   public static final int ET_VIEWCONTEXT_1_APPLI                = 1;

   /**
    * Sizee position/size relative to its parent
    * To avoid creating a link. we allow etalon ViewContext with this type
    */
   public static final int ET_VIEWCONTEXT_2_PARENT               = 2;

   /**
    * 
    */
   public static final int ET_VIEWCONTEXT_3_SCREEN_MAIN          = 3;

   /**
    * The full multi screens view context.
    */
   public static final int ET_VIEWCONTEXT_4_SCREEN_ALL           = 4;

   /**
    * 
    */
   public static final int ET_VIEWCONTEXT_5_CLIP                 = 5;

   /**
    * Custom View context must be defined using a Link.
    */
   public static final int ET_VIEWCONTEXT_6_LINK                 = 6;

   /**
    * Etalon is implicit and decided from the sizee context. 
    * 
    * 
    * <li> A size is computed in the context of a clip rectangle. This is the etalon.
    * <li> For sizing height of text, it will be the height of the font used to display the text.
    * <li> For sizing styled text, it will be the height of the font used to display the text, plus the margin, padding and other artifacts.
    * <li> For sizing the width of figure in a RDA, it will be the width of the RDA
    * <li> For sizing the height of figure in a RDA, it will be the height of the RDA
    * <li> In a table, contextual width is the width of one column.
    * 
    * <p>
    * 
    * For this etalon, the {@link IBOSizer#SIZER_OFFSET_05_ET_PROPERTY1} is
    * 
    * Included in the {@link ITechCodedSizer#CODED_BITS_2_ETALON}
    * </p>
    */
   public static final int ETALON_0_SIZEE_CTX                    = 0;

   /**
    * The etalon is a ViewContext layoutable, i.e. a rectangular area in the layoutable hierarchy tagged as ViewContext.
    * 
    * It is not necessarily the parent if the parent is not tagged as a ViewContext.
    * Its the screen area given to the context of the object.
    * 
    * It is used for sizing and positions top level windows/frames.
    * 
    * The ViewContext is the rectangle in which the object resides.
    * <li> A frame resides in a screen. ViewContext is the screen
    * <li> A View/Drawable/Component 
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_0_ROOT}
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_1_APPLI}
    * <li> {@link ITechLayout#ET_VIEWCONTEXT_2_PARENT}
    * 
    * 
    * <p>
    * 
    * Examples of ViewContext that may be defined in the context of graphical application
    * 
    * ViewContext rectangle in which the value is computed.
    * <li> Screen Size provided by {@link IGraphics}
    * <li> Size of the Canvas.
    * <li> Clip?
    * </p>
    * 
    * <p>
    * Based on a view context, compute the number of times a Font Fits?
    * Every Draw is done in the context of Screen width and height.
    * </p>
    * 
    */
   public static final int ETALON_1_VIEWCONTEXT                  = 1;

   /**
    * Base id for font based computations.
    * 
    * The etalon value 
    * Which font is used in the Application context.
    * <br>
    * {@link ITechLayout#SIZER_OFFSET_06_ET_FUN1} defines which font
    * <li> {@link ITechLayout#ET_FONT_0_DEFAULT} means default font
    * <li> {@link ITechLayout#ET_FONT_1_DEFINED}
    * <li> {@link ITechLayout#ET_FONT_5_SMALL} 
    * <li> {@link ITechLayout#ET_FONT_6_MEDIUM}
    * <li> {@link ITechLayout#ET_FONT_7_LARGE} 
    * <br>
    * <br>
    * <li> you want a size of the word "Welcome"
    * <li> you want a height of the font used by {@link ILayoutable}
    * 
    * When etalon is a {@link ILayoutable}, the property font is also used
    * 
    * This mode is for the globally defined value at the level of the {@link LayouterCtx}.
    * 
    * Is it a property or a mode? It can be both.
    */
   public static final int ETALON_2_FONT                         = 2;

   /**
    * Etalon is a function value computed by the engine.
    * 
    * <p>
    * 
    * {@link IBOSizer#SIZER_OFFSET_04_ET_SUBTYPE1} contains 
    * 
    * <li> {@link ITechLayout#RATIO_01_SCREEN_FONT}
    * <li> {@link ITechLayout#RATIO_02_VIEWCTX_FONT}
    * <li> {@link ITechLayout#RATIO_03_PARENT_FONT}
    * </p>
    * 
    * <p>
    * Discussion about
    * 
    * Number of times Default Font Height fits the W and H of the View/Etalon.
    * 
    * Used as an etalon for the Unit mode. We want the size to be a multiple
    * of this
    * 
    * But which font are we talking about?
    * A small font will fit 40 times on a laptop screen
    * A big font might fit 4 times on a phone screen.
    * 
    * How do you use it as an etalon? In base unit like Font size
    * </p>
    */
   public static final int ETALON_3_RATIO                        = 3;

   /**
    * The etalon on which to fetch the property is {@link ILayoutable#getLayoutableParent()} on the input {@link ILayoutable}.
    * 
    * When Parent is null, uses the ViewContext
    */
   public static final int ETALON_4_PARENT                       = 4;

   /**
    * Etalon is defined as {@link IBOLinker} in this {@link ByteObject}.
    * 
    * <p>
    * 
    * Usually used when linking to a totally unrelated object.
    * <li> {@link ITechLayout#ET_LINK_0_PARENT}
    * <li> {@link ITechLayout#ET_LINK_1_NAV}
    * <li> {@link ITechLayout#ET_LINK_2_UIID}, kinda like Android viewIDs.
    * </p>
    * 
    * <p>
    * Links can be chained
    * Written to {@link ISizer#SIZER_OFFSET_03_ETALON1}
    * </p>
    */
   public static final int ETALON_5_LINK                         = 5;

   /**
    * This etalon is valid for {@link IBOSizer} where sizee has a corresponding 
    * pozer defined. We use the etalon defined there.
    * 
    * <p>
    * Pozer defines a box to position that we use 
    * {@link IBOPozer#POS_OFFSET_02_ETALON1}.
    * Etalon is defined by 4 pozers -> 2 points creating a rectangle
    * a valid {@link Zer2DArea}
    * </p>
    */
   public static final int ETALON_6_POZER_BOX                    = 6;

   /**
    * The etalon choice is dynamic by a delegate object.
    * 
    * etalon is chosen dynamically by the sizer's delegate
    * 
    */
   public static final int ETALON_7_DELEGATE                     = 7;

   /**
    * 
    */
   public static final int ETALON_CK_MAX                         = 3;

   /**
    * Takes the distance between the 2 pozers.
    * 
    * When {@link IBOSizer#SIZER_FLAG_8_IMPLICIT}
    */
   public static final int FUN_POZER_0_DISTANCE                  = 0;

   /**
    * Returns the etalon value and ignores the provided value
    * 
    * Equivalent to ratio 100/100
    * 
    * {@link ToStringStaticLayout#toStringOpFun(int)}
    */
   public static final int FUNCTION_OP_00_NONE                   = 0;

   /**
    * Adds the etalon value to the provided value
    */
   public static final int FUNCTION_OP_01_ADD                    = 1;

   public static final int FUNCTION_OP_02_MINUS                  = 2;

   public static final int FUNCTION_OP_03_MULTIPLY               = 3;

   /**
    * Computed etalon value is divided by the provided value
    */
   public static final int FUNCTION_OP_04_DIVIDE                 = 4;

   /**
    * Interprets {@link IBOSizer#SIZER_OFFSET_08_VALUE2} as a Ratio of the etalon defined in {@link IBOSizer#SIZER_OFFSET_03_ETALON1}
    * 
    * <p>
    * Stored in {@link IBOSizer#SIZER_OFFSET_02_MODE1}
    * </p>
    * <p>
    * Q:How do you define a 16/9 ratio ? 
    * A:
    * <li>if width has a sizer. ratio is 9/16 and etalon for computing height is the sizer defining the Width
    * <li>if height has a sizer. ratio is 16/9 and etalon for computing width is the sizer defining the height
    * </p>
    */
   public static final int FUNCTION_OP_05_RATIO                  = 5;

   public static final int FUNCTION_OP_06_X_FOR_Y                = 6;

   public static final int FUNCTION_OP_07_                       = 7;

   public static final int FUNCTION_OP_08_                       = 8;

   public static final int FUNCTION_OP_09_                       = 9;

   public static final int FUNCTION_OP_10_                       = 10;

   public static final int FUNCTION_OP_11_                       = 11;

   public static final int FUNCTION_OP_12_                       = 12;

   public static final int FUNCTION_OP_CK_MAX                    = 6;

   /**
    * Interprets {@link IBOSizer#SIZER_OFFSET_08_VALUE2} as absolute raw value pixel. 
    * 
    * The unit is decided by  {@link IBOSizer#SIZER_OFFSET_03_ETALON1}
    * <br>
    * Included in the {@link ITechCodedSizer#CODED_BITS_1_MODE}
    * 
    * TODO and for the Pozer?
    */
   public static final int MODE_0_RAW_UNITS                      = 0;

   /**
    * This mode ignores the etalon, simple let the Layout
    * decides what its size should be with 
    * {@link ILayoutable#getSizePreferredHeight()}
    * 
    * <li> {@link ITechLayout#DELEGATE_ETALON_0_REFERENCE}
    * <li> {@link ITechLayout#DELEGATE_ETALON_1_PARENT_MAX}
    * 
    * Dynamic listener
    * 
    * When a function cannot be expressed, a delegate registers
    */
   public static final int MODE_1_DELEGATE                       = 1;

   /**
    * A function on the value of etalon computed from 
    * <li>{@link IBOSizer#SIZER_OFFSET_03_ETALON1} , 
    * <li>{@link IBOSizer#SIZER_OFFSET_06_ET_FUN1} 
    * <li>{@link IBOSizer#SIZER_OFFSET_05_ET_PROPERTY1} 
    * 
    * <li> {@link ITechLayout#FUNCTION_OP_0_NONE}
    * <li> {@link ITechLayout#FUNCTION_OP_1_ADD}
    * <li> {@link ITechLayout#FUNCTION_OP_2_MINUS}
    * <li> {@link ITechLayout#FUNCTION_OP_3_MULTIPLY}
    * <li> {@link ITechLayout#FUNCTION_OP_4_DIVIDE}
    * <li> {@link ITechLayout#FUNCTION_OP_05_RATIO}
    * 
    * This etalon value is modified by value {@link IBOSizer#SIZER_OFFSET_08_VALUE2} 
    * using operator 
    */
   public static final int MODE_2_FUNCTION                       = 4;

   /**
    *  Interprets {@link IBOSizer#SIZER_OFFSET_08_VALUE2} as belonging to the set
    * <li> {@link ITechLayout#SIZE_0_NONE}
    * <li> {@link ITechLayout#SIZE_1_SMALLEST}
    * <li> {@link ITechLayout#SIZE_2_SMALL}
    * <li> {@link ITechLayout#SIZE_3_MEDIUM}
    * <li> {@link ITechLayout#SIZE_4_BIG}
    * <li> {@link ITechLayout#SIZE_5_BIGGEST}
    * <br>
    * 
    * {@link IBOSizer#SIZER_OFFSET_03_ETALON1} provides the scale of things.
    * <li> {@link ITechLayout#SCALE_0_PADDING}
    * <li> {@link ITechLayout#SCALE_1_EXPO}
    * <li> {@link ITechLayout#SCALE_2_FONT}
    * <br>
    * {@link IBOSizer#SIZER_OFFSET_05_ET_PROPERTY1} provides ?
    * {@link IBOSizer#SIZER_OFFSET_06_ET_FUN1} provides ?
    * <br>
    * Usually it will be related to the font size
    * <br>
    * <br>
    * 
    * Type of scale is defined by {@link IBOSizer#SIZER_OFFSET_03_ETALON1}.
    * 
    * The idea of this mode is to define margins as small/medium/big.
    * Those values are centralized
    */
   public static final int MODE_3_SCALE                          = 3;

   /**
    * The sizer is a function of 2 sizers.
    * <br>
    * The 2 sizers used are the 2 first found.
    * 
    * The only relevant field is {@link IBOSizer#SIZER_OFFSET_06_ET_FUN1} that defines the operator
    * to use between the 2 sizers.
    */
   public static final int MODE_5_SIZERS                         = 5;

   /**
    * The size is the distance between 2 pozers.
    * 
    * Often times, {@link LayouterEngine} will be used in this mode 
    */
   public static final int MODE_6_POZERS                         = 6;

   /**
    * Mode is coded in 1 byte, potentially a total of 255 modes.
    */
   public static final int MODE_CK_MAX                           = 6;

   /**
    * 
    */
   public static final int PARENT_LINK_BYTE                      = 255;

   /**
    * Main screen with default font.
    */
   public static final int RATIO_01_SCREEN_FONT                  = 1;

   /**
    * 
    */
   public static final int RATIO_02_VIEWCTX_FONT                 = 1;

   /**
    * 
    */
   public static final int RATIO_03_PARENT_FONT                  = 1;

   /**
    * 
    */
   public static final int RAW_UNIT_0_PIXEL                      = 0;

   /**
    * Interprets {@link IBOSizer#SIZER_OFFSET_08_VALUE2} as DPI (Density Independent Pixel)
    * <br>
    * When a value is express in DIPs, the engine computes the number of pixels using
    * 
    * <br>
    * A dp is equal to one physical pixel on a screen with a density of 160. To calculate dp:
    * dp = (width in pixels * 160) /  screen density
    * <br>
    * To Calculate Pixel = dp * screen density / 160;
    * <br>
    * Screen density is a {@link IFramework} property. It depends on the physical properties of the screen.
    * IFrame
    * <br>
    */
   public static final int RAW_UNIT_1_DIP                        = 1;

   /**
    * {@link IFontImgCreator#getScalePixel(int, int)} requires a scale paremeter.
    * <br>
    * This scale is used to defined small padding and margin to huge padding and margin.
    * On a small screen, a small padding is 1 pixel, on a big screen it will be 8.
    * <br>
    * It kinda related to DIP... but works on laptop screen as well.
    */
   public static final int SCALE_0_PADDING                       = 0;

   /**
    * {@link IFontImgCreator#getScalePixel(int, int)} requires a scale paremeter.
    * This is an exponential scale
    * 
    * Scale start at 1 for smallest than goes to ViewPort Max for biggest
    */
   public static final int SCALE_1_EXPO                          = 1;

   /**
    * 
    */
   public static final int SCALE_2_FONT                          = 2;

   /**
    * No margin.
    */
   public static final int SIZE_0_NONE                           = 0;

   /**
    * For some hosts, very small equals small
    * <br>
    * Smallest possible in this scale.
    */
   public static final int SIZE_1_SMALLEST                       = 1;

   /**
    * 
    */
   public static final int SIZE_2_SMALL                          = 2;

   /**
    * 
    */
   public static final int SIZE_3_MEDIUM                         = 3;

   /**
    * 
    */
   public static final int SIZE_4_BIG                            = 4;

   /**
    * 
    */
   public static final int SIZE_5_BIGGEST                        = 5;

   /**
    * Size actually drawn on screen.
    * 
    * <p>
    * Located in {@link IBOSizer#SIZER_OFFSET_05_ET_PROPERTY1}
    * </p>
    * 
    * <p>
    * Modules creates as many properties as they wish to suit their structure.
    * 
    * </p>
    */
   public static final int SIZER_PROP_00_DRAWN                   = 0;

   /**
    * Preferred size includes content, padding, border and margin.
    */
   public static final int SIZER_PROP_01_PREFERRED               = 1;

   /**
    * Size of a unit, contextual to {@link ILayoutable}.
    * The pixel size of the height of one unit. 
    * {@link ILayoutable} is responsible to understand one unit in its context
    * 
    * a for Table, it will be a default row 
    * 
    * Tries its best
    *  Every component has a logic unit for W and H. A String is the height of a line, W might be the size of the biggest word 
    */
   public static final int SIZER_PROP_02_UNIT_LOGIC              = 2;

   /**
    * 
    */
   public static final int SIZER_PROP_03_FONT                    = 3;

   /**
    * Size which was used as the starting point for the layout
    */
   public static final int SIZER_PROP_04_INIT                    = 4;

   /**
    * Size of content. ie drawn size minus margin,border and padding
    */
   public static final int SIZER_PROP_05_CONTENT                 = 5;

   /**
    * Size of content and padding. ie drawn size minus margin,border
    */
   public static final int SIZER_PROP_06_CONTENT_PAD             = 6;

   /**
    * Size of content, padding and border. ie drawn size minus margin
    */
   public static final int SIZER_PROP_07_CONTENT_PAD_BORDER      = 4;

   public static final int SIZER_PROP_08                         = 8;

   public static final int SIZER_PROP_09                         = 9;

   /**
    * 
    */
   public static final int SIZER_PROP_10_PAD                     = 10;

   /**
    * 
    */
   public static final int SIZER_PROP_11_PAD_BORDER              = 11;

   /**
    * 
    */
   public static final int SIZER_PROP_12_PAD_BORDER_MARGIN       = 12;

   /**
    * 
    */
   public static final int SIZER_PROP_13_BORDER                  = 13;

   /**
    * 
    */
   public static final int SIZER_PROP_14_BORDER_MARGIN           = 14;

   /**
    * 
    */
   public static final int SIZER_PROP_15_MARGIN                  = 15;

   /**
    * Uses the whole visible object.
    */
   public static final int VIEW_STRUCT_00_ALL_VISIBLE            = 0;

   /**
    * Inner content values.
    */
   public static final int VIEW_STRUCT_01_INNER_CONTENT          = 1;

   /**
    * Uses the windows inner.
    */
   public static final int VIEW_STRUCT_02_VIEW_PORT              = 2;

   /**
    * Uses the window and the scrollbar artifacts.
    */
   public static final int VIEW_STRUCT_03_VIEW_PORT_ARTIFACTS    = 3;

   /**
    * Uses the area defined by content, padding, border and margin.
    */
   public static final int VIEW_STYLE_00_VIEW_FULL               = 0;

   /**
    * 
    */
   public static final int VIEW_STYLE_01_VIEW_CONTENT_PAD_BORDER = 1;

   /**
    * 
    */
   public static final int VIEW_STYLE_02_VIEW_CONTENT_PAD        = 2;

   /**
    * 
    */
   public static final int VIEW_STYLE_03_VIEW_CONTENT            = 3;

   /**
    * 
    */
   public static final int Z_SIZE_FILL                           = ITechCodedSizer.CODED_SIZE_FLAG_32_SIGN + (DELEGATE_ETALON_1_PARENT_MAX << ITechCodedSizer.CODED_SIZE_SHIFT_1_MODE);

}
