/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.BOByteObjectModule;

/**
 * 
 */
public class BOLayouterModule extends BOByteObjectModule {

   /**
    * 
    */
   protected final LayouterCtx lc;

   /**
    * 
    *
    * @param lc 
    */
   public BOLayouterModule(LayouterCtx lc) {
      super(lc.getBOC());
      this.lc = lc;
   }
}
