package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.IStyler;

/**
 * Adapt the style based on screen densities and screen size
 * <br>
 * <br>
 * A Swing app can have several style adapter depending on the size of the screen.
 * <br>
 * {@link ISizer}
 * {@link ISizerC}
 * 
 * <br>
 * @author Charles-Philip
 *
 */
public class StyleAdapter implements IStyler {

   protected int    z_refSize       = -1;

   protected float  scaleA          = 1;

   public boolean   gradientPlusOne = false;

   public boolean   useGradient     = false;

   public boolean   useFocusStyle   = false;

   public int       gradientIncr    = 1;

   private LayouterCtx lac;

   public StyleAdapter(LayouterCtx lac, float scaleA) {
      this.lac = lac;
      this.scaleA = scaleA;
   }

   /**
    * This will depends on the Graphical Context.
    * <br>
    * Standard (Etalon) pixel value for adjusting dimensions
    * @return
    */
   public int getRefSize() {
      if (z_refSize > 0)
         return z_refSize;
      return lac.getReferenceFontHeightPixels();
   }

   /**
    * Gradient step just increase one unit after  a given threshold
    * @param size
    * @return
    */
   public int getScaledGradientStep(int size) {
      if (gradientPlusOne) {
         return size + gradientIncr;
      }
      return size;
   }

   public int getScaledMargin(int size) {
      return scaleIt(size);
   }

   public int getScaledPadding(int size) {
      return scaleIt(size);
   }

   public int getScaledSBBGBlockSize(int size) {
      return scaleIt(size);
   }

   public int getScaledSBBlockSize(int size) {
      return scaleIt(size);
   }

   public int getScaledSBBlockWrapper(int size) {
      return scaleIt(size);
   }

   public boolean haveFocusStyles() {
      if (useFocusStyle) {
      }
      return useFocusStyle;
   }

   public boolean isUseGradient(boolean val) {
      if (useGradient) {
         return val;
      }
      return false;
   }

   /**
    * Driver may override this method.
    * 
    * TODO in 
    * @param size
    * @return
    */
   public int getScaledSize(int size) {
      return scaleIt(size);
   }

   public int getScaledArc(int size) {
      return scaleIt(size);
   }

   public int getScaledGridH(int size) {
      return scaleIt(size);
   }

   protected int scaleIt(int size) {
      return (int) Math.floor((double) size + 0.5d);
      //return Math.round(((float) size * scaleA));
   }

   public int getCaretWidth() {
      return scaleIt(1);
   }

   public String toString1Line() {
      // TODO Auto-generated method stub
      return null;
   }

   public void toString(Dctx dc) {
      // TODO Auto-generated method stub
      
   }

   public void toString1Line(Dctx dc) {
      // TODO Auto-generated method stub
      
   }

   public UCtx toStringGetUCtx() {
      // TODO Auto-generated method stub
      return null;
   }

}
