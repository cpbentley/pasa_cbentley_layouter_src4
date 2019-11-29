package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.layouter.src4.tech.ITechCoded;

/**
 * Functions that can be inlined.
 * <br>
 * @author Charles Bentley
 *
 */
public class CodedUtils implements ITechCoded {
   
   public static int codedIndexRefDecode(int ref) {
      return getCodedValue(ref);
   }
   public static boolean hasSubFlag(int value) {
      return ((value >> 30) & 0x3) == 2;
   }

   public static int codedIndexRefEncode(int ref) {
      int val = (ref << CODED_SIZE_SHIFT_0_VALUE) & CODED_BITS_0_VALUE;
      return val + CODED_SIZE_FLAG_32_SIGN;
   }

   public static int getCodedValue(int value) {
      int valueV = (value >> CODED_SIZE_SHIFT_0_VALUE) & CODED_MASK_0_VALUE;
      return valueV;
   }
   
   /**
    * True when 
    * <li>{@link ISizer#CODED_SIZE_FLAG_32_SIGN}
    * <li>{@link ISizer#CODED_SIZE_FLAG_31_SIGN_CODE}
    * 
    * @param value
    * @return
    */
   public static boolean isCoded(int value) {
      return ((value >> 30) & 0x3) == 2;
   }

   public static boolean isLinked(int value) {
      return (value & CODED_SIZE_FLAG_30_CODED) == CODED_SIZE_FLAG_30_CODED;
   }

}
