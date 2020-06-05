/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;


/**
 * A Styler activates/desactivates drawing capabilities.
 * 
 * November 2019: The styler should actively configure the Drawer.
 * 
 * The Drawer shouldn't have a reference to the Styler
 * 
 * @author Charles Bentley
 *
 */
public interface IStyler extends IStringable {

   /**
    * 
    *
    * @param b 
    * @return 
    */
   public boolean isUseGradient(boolean b);

}
