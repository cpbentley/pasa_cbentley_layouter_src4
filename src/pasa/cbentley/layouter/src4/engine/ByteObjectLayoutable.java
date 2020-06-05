/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * Reference type. Can be serialized?
 * @author Charles Bentley
 *
 */
public class ByteObjectLayoutable extends ByteObject {

   /**
    * 
    */
   private ILayoutable layoutable;

   /**
    * 
    *
    * @param mod 
    * @param layoutable 
    */
   public ByteObjectLayoutable(BOCtx mod, ILayoutable layoutable) {
      super(mod,IBOTypesBOC.TYPE_017_REFERENCE_OBJECT, 10);
      this.layoutable = layoutable;
   }

   /**
    * 
    *
    * @return 
    */
   public ILayoutable getLayoutable() {
      return layoutable;
   }
}
