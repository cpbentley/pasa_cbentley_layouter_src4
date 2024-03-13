/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.ctx;

import pasa.cbentley.core.src4.ctx.IConfig;

public interface IConfigLayouter extends IConfig {

   
   /**
    * Sets preferred sizes as default instead of leaving them as null.
    * @return
    */
   public boolean isSizePrefferedDefault();
}
