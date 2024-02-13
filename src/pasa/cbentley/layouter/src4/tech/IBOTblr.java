/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.tech;

import pasa.cbentley.byteobjects.src4.core.interfaces.IByteObject;
import pasa.cbentley.byteobjects.src4.objects.pointer.MergeMaskFactory;
import pasa.cbentley.core.src4.interfaces.C;

public interface IBOTblr extends IByteObject {

   public static final int TBLR_UNDEF              = -1;

   /**
    * 1 byte for flag
    * 1 byte for type
    */
   public static final int TBLR_BASIC_SIZE         = A_OBJECT_BASIC_SIZE + 6;

   public static final int TBLR_FLAG_1_USING_ARRAY = 1 << 0;

   /**
    * Same when all TBLR have the same value
    */
   public static final int TBLR_FLAG_4_SAME_VALUE  = 1 << 3;

   /**
    * 4 bits mask to tell {@link MergeMaskFactory} if Top is transparent/not defined. So no need of a merge mask
    */
   public static final int TBLR_FLAG_5_TRANS_TOP     = 1 << 4;

   /**
    * 1 bit to tell {@link MergeMaskFactory} that a {@link C#POS_1_BOT} is defined.
    */
   public static final int TBLR_FLAG_6_TRANS_BOT     = 1 << 5;

   public static final int TBLR_FLAG_7_TRANS_LEFT    = 1 << 6;

   public static final int TBLR_FLAG_8_TRANS_RIGHT   = 1 << 7;

   public static final int TBLR_OFFSET_01_FLAG     = A_OBJECT_BASIC_SIZE;

   /**
    * Type of encoding for {@link IBOTblr#TBLR_OFFSET_03_DATA4}
    * <li> {@link IBOTblr#TYPE_0_PIXEL_VALUE} // 4 bytes coded sizer
    * <li> {@link IBOTblr#TYPE_2_SIZER} //full sizer ByteObject
    */
   public static final int TBLR_OFFSET_02_TYPE1    = A_OBJECT_BASIC_SIZE + 1;

   /**
    * Start of data. for same size value.
    * <br>
    * When using a {@link ITypesBentleyFw#FTYPE_3_SIZER}, the value is a pointer
    * <br>
    * Sometimes the figure using the TBLR will use its own SIZER to interpret TBLR values
    */
   public static final int TBLR_OFFSET_03_DATA4    = A_OBJECT_BASIC_SIZE + 2;

   /**
    * Single pixel value for all TopBotLeftRight.
    */
   public static final int TYPE_0_PIXEL_VALUE      = 0;

   public static final int TYPE_1_CODED_VALUE      = 1;

   /**
    * Sizers are defined with a 1 byte pointer. When flag samevalue is set,
    * one sizer definition, one pointer
    */
   public static final int TYPE_2_SIZER            = 2;

}
