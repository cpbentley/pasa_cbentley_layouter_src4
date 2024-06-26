/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.layouter.src4.tech.ITechCodedSizer;

/**
 * Functions that can be inlined.
 * <br>
 * @author Charles Bentley
 *
 */
public class CodedUtils implements ITechCodedSizer {

   /**
    * 
    *
    * @param ref 
    * @return 
    */
   public static int codedIndexRefDecode(int ref) {
      return getCodedValue(ref);
   }

   /**
    * 
    *
    * @param value 
    * @return 
    */
   public static boolean hasSubFlag(int value) {
      return ((value >> 30) & 0x3) == 2;
   }

   /**
    * 
    *
    * @param ref 
    * @return 
    */
   public static int codedIndexRefEncode(int ref) {
      int val = (ref << CODED_SIZE_SHIFT_0_VALUE) & CODED_BITS_0_VALUE;
      return val + CODED_SIZE_FLAG_32_SIGN;
   }

   /**
    * 
    *
    * @param value 
    * @return 
    */
   public static int getCodedValue(int value) {
      int valueV = (value >> CODED_SIZE_SHIFT_0_VALUE) & CODED_MASK_0_VALUE;
      return valueV;
   }

   /**
    * True when 
    * <li>{@link ITechCodedSizer#CODED_SIZE_FLAG_32_SIGN}
    * <li>{@link ITechCodedSizer#CODED_SIZE_FLAG_31_SIGN_CODE}.
    *
    * @param value 
    * @return 
    */
   public static boolean isCoded(int value) {
      return ((value >> 30) & 0x3) == 2;
   }

   /**
    * 
    *
    * @param value 
    * @return 
    */
   public static boolean isLinked(int value) {
      return BitUtils.hasFlag(value, CODED_SIZE_FLAG_30_CODED);
   }

   public static int getCodePage(int value) {
      return ((value >> CODED_SIZE_SHIFT_5_CODE_PAGE) & CODED_MASK_5_CODE_PAGE);
   }

}
