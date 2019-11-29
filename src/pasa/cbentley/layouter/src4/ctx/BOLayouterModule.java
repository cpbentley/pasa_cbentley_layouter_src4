package pasa.cbentley.layouter.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.BOByteObjectModule;

public class BOLayouterModule extends BOByteObjectModule {

   protected final LayouterCtx lc;

   public BOLayouterModule(LayouterCtx lc) {
      super(lc.getBOC());
      this.lc = lc;
   }
}
