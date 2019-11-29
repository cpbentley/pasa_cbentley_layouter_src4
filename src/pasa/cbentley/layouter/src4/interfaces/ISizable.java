/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;

/**
 * Converts size definitions into pixels.
 * <br>
 * <br>
 * Used in the context of a Graphic application.
 * <br>
 * <br>
 * 
 * @author Charles-Philip
 *
 */
public interface ISizable {
   /**
    * 
    * @param sizer cannot be null
    * @param c cannot be null
    * @param ctx
    * @return -1 if none defined
    */
   public int getMin(ByteObject sizer, ISizeCtx c, int ctx);

   /**
    * 
    * @param sizer
    * @param c
    * @param ctx
    * @return -1 if none defined
    */
   public int getMax(ByteObject sizer, ISizeCtx c, int ctx);

   /**
    * 
    *
    * @param sizer 
    * @param c 
    * @return 
    */
   public int getMaxW(ByteObject sizer, ISizeCtx c);

   /**
    * 
    *
    * @param sizer 
    * @param c 
    * @return 
    */
   public int getMaxH(ByteObject sizer, ISizeCtx c);

   /**
    * 
    *
    * @param codedsize 
    * @return 
    */
   public String codedSizeToString1Line(int codedsize);

   /**
    * Reads the 4 bytes at index offset.
    * <br>
    * If coded for int, decode it, if coded for defined sub read it from 
    * {@link ITypesBentleyFw#FTYPE_3_SIZER}.
    * <br>
    * Sizer reads the size
    * @param bo {@link ByteObject} from which to read index and sub
    * @param index
    * @param w simple context width
    * @param h simple context height
    * @param ctx cue for current context
    * @return
    */
   public int codedSizeDecode(ByteObject bo, int index, int w, int h, int ctx);

   /**
    * 
    *
    * @param index 
    * @param bo 
    * @param topleftMost 
    * @param totalSize 
    * @param objectSize 
    * @param w 
    * @param h 
    * @param ctx 
    * @return 
    */
   public int getPosAlign(int index, ByteObject bo, int topleftMost, int totalSize, int objectSize, int w, int h, int ctx);

   /**
    * 
    * @param ow
    * @param bo
    * @param index
    * @param w
    * @param h
    * @param ctx
    * @return
    */
   public int codedPosDecode(int ow, ByteObject bo, int index, int w, int h, int ctx);

   /**
    * Decode with the context.
    *
    * @param codedsize 
    * @param sc 
    * @param ctxType 
    * @return 
    */
   public int codedSizeDecode(int codedsize, ISizeCtx sc, int ctxType);

   /**
    * Shortcut for {@link ISizer#CTX_1_WIDTH}.
    *
    * @param codedsize 
    * @param sc 
    * @return 
    */
   public int codedSizeDecodeH(int codedsize, ISizeCtx sc);

   /**
    * Shortcut for {@link ISizer#CTX_1_WIDTH}.
    *
    * @param codedsize 
    * @param sc 
    * @return 
    */
   public int codedSizeDecodeW(int codedsize, ISizeCtx sc);

   /**
    * Decode the coded size with the given 2D context.
    *
    * @param codedsize 
    * @param w 
    * @param h 
    * @param ctx value for {@link ISizer#ETALON_0_SIZEE_CTX}
    * @return 
    */
   public int codedSizeDecode(int codedsize, int w, int h, int ctx);

   /**
    * 
    *
    * @param mode 
    * @param value 
    * @param etalon 
    * @param etype 
    * @param efun 
    * @return 
    */
   public int codedSizeEncode(int mode, int value, int etalon, int etype, int efun);

   /**
    * The etalon is a single value.
    *
    * @param sizer 
    * @param etalon 
    * @return 
    */
   public int getPixelSize(ByteObject sizer, int etalon);

   /**
    * Overrides the value in the sizer... Used
    * Get the value based on the w/h.
    * 
    * Provides the etalon rectangle.
    * <br>
    *
    * @param sizer 
    * @param w width of the etalon
    * @param h height of the etlon
    * @param ctx 
    * @return 
    */
   public int getPixelSize(ByteObject sizer, int w, int h, int ctx);

   /**
    * 
    *
    * @param s 
    * @param w 
    * @param h 
    * @return 
    */
   public int getPixelSizeW(ByteObject s, int w, int h);

   /**
    * 
    *
    * @param s 
    * @param w 
    * @param h 
    * @return 
    */
   public int getPixelSizeH(ByteObject s, int w, int h);

   /**
    * Size the sizer using the {@link ISizeCtx} provider and the ctx Type.
    *
    * @param s 
    * @param sc 
    * @param ctx {@link ISizer#CTX_1_WIDTH} or {@link ISizer#CTX_2_HEIGHT}
    * @return 
    */
   public int getPixelSize(ByteObject s, ISizeCtx sc, int ctx);

   /**
    * 
    *
    * @param s 
    * @param sc 
    * @return 
    */
   public int getPixelSizeW(ByteObject s, ISizeCtx sc);

   /**
    * 
    * @param s {@link IPozer}
    * @param xy the position of the box
    * @param totalSize size of the box
    * @param objectSize size of the object to align
    * @param sc to compute the Sizer if one is defined
    * @param ctx
    * @return
    */
   public int getPos(ByteObject s, int xy, int totalSize, int objectSize, ISizeCtx sc, int ctx);

   /**
    * -1 if pixel could not be computed due to lack of context.
    *
    * @param s 
    * @param sc 
    * @return 
    */
   public int getPixelSizeH(ByteObject s, ISizeCtx sc);

   /**
    * Creates a new Sizer.
    *
    * @param mode 
    * @param value 
    * @param etalon 
    * @param etype 
    * @param efun 
    * @return 
    */
   public ByteObject getSizerNew(int mode, int value, int etalon, int etype, int efun);

   /**
    * 
    *
    * @param dc 
    * @param sizer 
    */
   public void toString(Dctx dc, ByteObject sizer);

   /**
    * 
    *
    * @param sizer 
    * @return 
    */
   public String toString1Line(ByteObject sizer);


}
