/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;

/**
 * Reference type. Can be serialized?
 * @author Charles Bentley
 *
 */
public class ByteObjectLayoutDelegate extends ByteObject {

   /**
    * 
    */
   private ILayoutDelegate delegate;

   /**
    * 
    *
    * @param mod 
    * @param delegate 
    */
   public ByteObjectLayoutDelegate(BOCtx mod, ILayoutDelegate delegate) {
      super(mod,IBOTypesBOC.TYPE_017_REFERENCE_OBJECT, 10);
      this.delegate = delegate;
   }

   /**
    * 
    *
    * @return 
    */
   public ILayoutDelegate getDelegate() {
      return delegate;
   }
}
