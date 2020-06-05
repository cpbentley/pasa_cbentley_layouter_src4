/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.ctx;

/**
 * 
 */
public class LayoutException extends RuntimeException {

   /**
    * 
    */
   protected final LayouterCtx lc;

   /**
    * 
    *
    * @param lc 
    * @param string 
    */
   public LayoutException(LayouterCtx lc, String string) {
      super(string);
      this.lc = lc;
   }

}
