package pasa.cbentley.layouter.src4.ctx;

public class LayoutException extends RuntimeException {

   protected final LayouterCtx lc;

   public LayoutException(LayouterCtx lc, String string) {
      super(string);
      this.lc = lc;
   }

}
