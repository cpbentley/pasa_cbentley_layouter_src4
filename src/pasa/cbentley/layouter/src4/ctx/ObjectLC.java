package pasa.cbentley.layouter.src4.ctx;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * 
 * @author Charles Bentley
 *
 */
public class ObjectLC implements IStringable {

   protected final LayouterCtx lac;

   //#debug
   private String              toStringDebugName;

   public ObjectLC(LayouterCtx lac) {
      this.lac = lac;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, ObjectLC.class, 32);
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      if (toStringDebugName != null) {
         dc.appendBracketedWithSpace(toStringDebugName);
      }
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ObjectLC.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return lac.getUC();
   }

   public String toStringName() {
      return toStringDebugName;
   }

   public void toStringSetDebugName(String name) {
      toStringDebugName = name;
   }

   //#enddebug

}
