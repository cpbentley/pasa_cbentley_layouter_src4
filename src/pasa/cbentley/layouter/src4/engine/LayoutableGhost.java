/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;

/**
 * 
 * @author Charles Bentley
 *
 */
public class LayoutableGhost extends LayoutableAbstract {

   public LayoutableGhost(LayouterCtx lac) {
      super(lac);
   }

 
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, LayoutableGhost.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayoutableGhost.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
