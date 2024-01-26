/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.tech;

import pasa.cbentley.byteobjects.src4.core.interfaces.IByteObject;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**

 * @author Charles Bentley
 *
 */
public interface ITechSizer extends IByteObject {

   /**
    * 
    */
   public static final int ET_PROP_4_SIZE_FONT_RATIO        = 4;

   /**
    * The 4 main are coded on 2 bits.
    * Size actually drawn
    */
   public static final int SIZER_PROP_00_DRAWN              = 0;

   /**
    * Preferred size includes content, padding, border and margin.
    */
   public static final int SIZER_PROP_01_PREFERRED          = 1;

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
   public static final int SIZER_PROP_02_UNIT_LOGIC         = 2;

   /**
    * 
    */
   public static final int SIZER_PROP_03_FONT               = 3;

   /**
    * Size of content. ie drawn size minus margin,border and padding
    */
   public static final int SIZER_PROP_05_CONTENT            = 5;

   /**
    * Size of content and padding. ie drawn size minus margin,border
    */
   public static final int SIZER_PROP_06_CONTENT_PAD        = 6;

   /**
    * Size of content, padding and border. ie drawn size minus margin
    */
   public static final int SIZER_PROP_07_CONTENT_PAD_BORDER = 4;

   /**
    * 
    */
   public static final int SIZER_PROP_10_PAD                = 10;

   /**
    * 
    */
   public static final int SIZER_PROP_11_PAD_BORDER         = 11;

   /**
    * 
    */
   public static final int SIZER_PROP_12_PAD_BORDER_MARGIN  = 12;

   /**
    * 
    */
   public static final int SIZER_PROP_13_BORDER             = 13;

   /**
    * 
    */
   public static final int SIZER_PROP_14_BORDER_MARGIN      = 14;

   /**
    * 
    */
   public static final int SIZER_PROP_15_MARGIN             = 15;

   int DELEGATE_POINT_VALUE = -1;

}
