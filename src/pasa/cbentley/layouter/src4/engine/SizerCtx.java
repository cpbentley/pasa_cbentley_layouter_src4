/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.layouter.src4.interfaces.ISizeCtx;

/**
 * Simple Context with just w and h.
 * <br>
 * More complex SizeCtx is a {@link Drawable}, a SwingSizer, an AndroidSizer 
 * <br>
 * @author Charles Bentley
 *
 */
public class SizerCtx implements ISizeCtx {

   /**
    * 
    */
   public int        h;

   /**
    * 
    */
   public int        w;


   /**
    * 
    *
    * @param w 
    * @param h 
    */
   public SizerCtx(int w, int h) {
      this.w = w;
      this.h = h;
   }

   /**
    * 
    *
    * @param sizer 
    * @return 
    */
   public int getSizeEtalonH(ByteObject sizer) {
      return h;
   }

   /**
    * 
    *
    * @param sizer 
    * @return 
    */
   public int getSizeEtalonW(ByteObject sizer) {
      return w;
   }

   /**
    * 
    *
    * @return 
    */
   public int getStart() {
      return 0;
   }

   /**
    * 
    *
    * @return 
    */
   public int getTop() {
      return 0;
   }

}
