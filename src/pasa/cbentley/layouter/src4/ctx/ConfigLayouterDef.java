/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.ctx;

import pasa.cbentley.core.src4.ctx.ConfigAbstract;
import pasa.cbentley.core.src4.ctx.UCtx;

public class ConfigLayouterDef extends ConfigAbstract implements IConfigLayouter {

   public ConfigLayouterDef(UCtx uc) {
      super(uc);
   }

   public boolean isSizePrefferedDefault() {
      return false;
   }

}
