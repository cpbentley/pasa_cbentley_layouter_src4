/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ObjectLayouter;
import pasa.cbentley.layouter.src4.interfaces.ISizeCtx;
import pasa.cbentley.layouter.src4.tech.ITechLayout;
import pasa.cbentley.layouter.src4.tech.ITechPozer;
import pasa.cbentley.layouter.src4.tech.ITechSizer;

/**
 * Effectively computes the pixel sizes.
 *
 * @author Charles Bentley
 */
public abstract class SizerAbstract extends ObjectLayouter implements ISizeCtx, ITechLayout, ITechSizer, ITechPozer {

   /**
    * 
    *
    * @param lc 
    */
   public SizerAbstract(LayouterCtx lc) {
      super(lc);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, SizerAbstract.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, SizerAbstract.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
