package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ObjectLC;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * 
 * @author Charles Bentley
 *
 */
public class LayoutDelegateAdapter extends ObjectLC implements ILayoutDelegate {

   public LayoutDelegateAdapter(LayouterCtx lac) {
      super(lac);
   }

   public ILayoutable getDelegateEtalonePozer(ByteObject pozer, ILayoutable layoutable, int ctx) {
      return null;
   }

   public ILayoutable getDelegateEtaloneSizer(ByteObject sizer, ILayoutable layoutable, int ctx) {
      return null;
   }

   public int getDelegateEtalonFun(ByteObject sizer, ILayoutable layoutable, int ew, int eh) {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getDelegatePozerX(ByteObject pozer, ILayoutable layoutable) {
      return 0;
   }

   public int getDelegatePozerY(ByteObject pozer, ILayoutable layoutable) {
      return 0;
   }

   public int getDelegateSizeHeight(ByteObject sizer, ILayoutable layoutable) {
      return 0;
   }

   public int getDelegateSizeWidth(ByteObject sizer, ILayoutable layoutable) {
      return 0;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, LayoutDelegateAdapter.class, 55);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayoutDelegateAdapter.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
