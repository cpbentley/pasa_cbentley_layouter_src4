/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.utils.BitUtils;

public class Anchor32Bits {
   public static final int  ALIGN_BOTTOM                  = 2;

   public static final int  ALIGN_CENTER                  = 1;

   public static final int  ALIGN_FILL                    = -1;

   public static final int  ALIGN_LEFT                    = 0;

   public static final int  ALIGN_RIGHT                   = 2;

   public static final int  ALIGN_TOP                     = 0;

   public static final int  ALIGN_TOPLEFT                 = 0;

   /**
    * When this flag is set, the anchor is undefined.
    * No Anchor. Fill. W and H are implicitely coded pixel values on 14 bits
    * 
    */
   public static final int  ANCHOR_32BITS_FLAG_FILL       = 1 << 30;

   /**
    * Flag telling H and W are pixel values and not percentages
    */
   public static final int  ANCHOR_32BITS_FLAG_PIXELS     = 1 << 31;

   private static final int ANCHOR_32BITS_PIXELS_BIT_SIZE = 14;

   private static final int ANCHOR_32BITS_PIXELS_MASK     = BitUtils.getMask(14);

   public static int alignDecodeH(int coded) {
      return coded & 0x3;
   }

   public static int alignDecodeV(int coded) {
      return (coded >> 2) & 0x3;
   }

   /**
    * 4 bits encoding of vertical and horizontal alignment values 
    * @param ha horizontal value
    * @param va
    * @return int with 4 least significant bits of data
    */
   public static int alignEncode(int ha, int va) {
      return ha + (va << 2);
   }

   /**
    * Default. TOP, LEFT anchor
    * @return
    */
   public static int alignGetDefaultCode() {
      return 0;
   }

   /**
    * Decodes height value of anchor
    * @param anchorInt
    * @return
    */
   public static int anchorDecodeH(int anchorInt) {
      if ((anchorInt & ANCHOR_32BITS_FLAG_FILL) == ANCHOR_32BITS_FLAG_FILL) {
         return anchorInt & ANCHOR_32BITS_PIXELS_MASK;
      } else {
         return anchorInt & 0xFF;
      }
   }

   public static int anchorDecodeH(int anchorInt, int h) {
      if ((anchorInt & ANCHOR_32BITS_FLAG_FILL) == ANCHOR_32BITS_FLAG_FILL) {
         return anchorInt & ANCHOR_32BITS_PIXELS_MASK;
      } else {
         return h * (anchorInt & 0xFF) / 100;
      }
   }

   public static int anchorDecodeHA(int anchorInt) {
      if ((anchorInt & ANCHOR_32BITS_FLAG_FILL) == ANCHOR_32BITS_FLAG_FILL) {
         return ALIGN_FILL;
      }
      return alignDecodeH((anchorInt >> 16) & 0xFF);
   }

   public static int anchorDecodeVA(int anchorInt) {
      if ((anchorInt & ANCHOR_32BITS_FLAG_FILL) == ANCHOR_32BITS_FLAG_FILL) {
         return ALIGN_FILL;
      }
      return alignDecodeV((anchorInt >> 16) & 0xFF);
   }

   public static int anchorDecodeW(int anchorInt) {
      if ((anchorInt & ANCHOR_32BITS_FLAG_FILL) == ANCHOR_32BITS_FLAG_FILL) {
         return (anchorInt >> ANCHOR_32BITS_PIXELS_BIT_SIZE) & ANCHOR_32BITS_PIXELS_MASK;
      } else {
         return ((anchorInt >> 8) & 0xFF);
      }
   }

   public static int anchorDecodeW(int anchorInt, int w) {
      if ((anchorInt & ANCHOR_32BITS_FLAG_FILL) == ANCHOR_32BITS_FLAG_FILL) {
         return (anchorInt >> ANCHOR_32BITS_PIXELS_BIT_SIZE) & ANCHOR_32BITS_PIXELS_MASK;
      } else {
         return w * ((anchorInt >> 8) & 0xFF) / 100;
      }
   }

   /**
    * 32bits anchor 
    * h and w are byte percentage with no minimums
    * Percentage of explicitely given width and height of drawing figure
    * 1 byte flag 1 byte align, 1 byte w, 1 byte h
    * @param ha
    * @param hv
    * @param percent
    * @return
    */
   public static int anchorEncode(int ha, int hv, int wpercent, int hpercent) {
      return (alignEncode(ha, hv) << 16) + ((wpercent & 0xFF) << 8) + (hpercent & 0xFF);
   }

   /**
    * Encodes a W,H anchor pixel dimension with an implicit FILL positioning
    * @param w
    * @param h
    * @return
    */
   public static int anchorEncodePixel(int w, int h) {
      int val = ((w & ANCHOR_32BITS_PIXELS_MASK) << ANCHOR_32BITS_PIXELS_BIT_SIZE) + (h & ANCHOR_32BITS_PIXELS_MASK);
      val |= ANCHOR_32BITS_FLAG_FILL;
      return val;
   }

   /**
    * Fill Anchor with an explicit pixel Height given.
    * Width is unknown and set to zero. Will be explicity given
    * @param h
    * @return
    */
   public static int anchorEncodePixelH(int h) {
      return anchorEncodePixel(0, h);
   }

   public static int anchorEncodePixelW(int w) {
      return anchorEncodePixel(w, 0);
   }
}
