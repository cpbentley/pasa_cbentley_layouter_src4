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

   public int        h;

   public int        w;


   public SizerCtx(int w, int h) {
      this.w = w;
      this.h = h;
   }

   public int getSizeEtalonH(ByteObject sizer) {
      return h;
   }

   public int getSizeEtalonW(ByteObject sizer) {
      return w;
   }

   public int getStart() {
      return 0;
   }

   public int getTop() {
      return 0;
   }

}
