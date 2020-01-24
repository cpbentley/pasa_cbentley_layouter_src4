package pasa.cbentley.layouter.src4.ctx;

import pasa.cbentley.byteobjects.src4.tech.ITechCtxSettings;

public interface ITechCtxSettingsLayouter extends ITechCtxSettings {

   public static final int CTX_LAY_BASIC_SIZE      = MODSET_BASIC_SIZE + 5;

   public static final int CTX_LAY_OFFSET_01_FLAG1 = MODSET_BASIC_SIZE;

   /**
    * Visual theme ID used by the view. convenience spot. Model is not supposed to know about theming. Why not anyways?
    * <br>
    * <br>
    * 
    */
   public static final int CTX_LAY_OFFSET_02_     = MODSET_BASIC_SIZE + 1;

}
