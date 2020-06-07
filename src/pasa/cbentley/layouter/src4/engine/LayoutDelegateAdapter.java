package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

public class LayoutDelegateAdapter implements ILayoutDelegate {

   protected final LayouterCtx lac;

   public LayoutDelegateAdapter(LayouterCtx lac) {
      this.lac = lac;

   }

   public int getDelegateSizeHeight(ByteObject sizer, ILayoutable layoutable) {
      return 0;
   }

   public ILayoutable getDelegateEtalonePozer(ByteObject pozer, ILayoutable layoutable) {
      return null;
   }

   public ILayoutable getDelegateEtaloneSizer(ByteObject sizer, ILayoutable layoutable) {
      return null;
   }

   public int getDelegateSizeWidth(ByteObject sizer, ILayoutable layoutable) {
      return 0;
   }

   public ILayoutable getDelegateSizer(ByteObject sizer, ILayoutable layoutable, int ctx) {
      return null;
   }

   public int getDelegatePozerX(ByteObject pozer, ILayoutable layoutable) {
      return 0;
   }

   public int getDelegatePozerY(ByteObject pozer, ILayoutable layoutable) {
      return 0;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   /**
    * 
    *
    * @return 
    */
   public String toString() {
      return Dctx.toString(this);
   }

   /**
    * 
    *
    * @param dc 
    */
   public void toString(Dctx dc) {
      dc.root(this, LayoutDelegateAdapter.class, 69);
   }

   /**
    * 
    *
    * @return 
    */
   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   /**
    * 
    *
    * @param dc 
    */
   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayoutDelegateAdapter.class);
   }

   /**
    * 
    *
    * @return 
    */
   public UCtx toStringGetUCtx() {
      return lac.getUCtx();
   }

   //#enddebug

}
