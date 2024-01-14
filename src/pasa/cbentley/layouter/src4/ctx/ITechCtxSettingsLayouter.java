/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.interfaces.IBOCtxSettings;

public interface ITechCtxSettingsLayouter extends IBOCtxSettings {

   public static final int CTX_LAY_BASIC_SIZE      = CTX_BASIC_SIZE + 5;

   public static final int CTX_LAY_OFFSET_01_FLAG1 = CTX_BASIC_SIZE;

   /**
    * Visual theme ID used by the view. convenience spot. Model is not supposed to know about theming. Why not anyways?
    * <br>
    * <br>
    * 
    */
   public static final int CTX_LAY_OFFSET_02_     = CTX_BASIC_SIZE + 1;

}
