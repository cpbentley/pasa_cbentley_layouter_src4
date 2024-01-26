/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.engine.LayoutOperator;
import pasa.cbentley.layouter.src4.tech.ITechLayout;
import pasa.cbentley.layouter.src4.tech.IBOSizer;

/**
 * When the function is too complex or dynamic to be expressed by {@link ITechLayout}
 * 
 * @author Charles Bentley
 *
 */
public interface ILayoutDelegate extends IStringable {

   /**
    * Returns the {@link ILayoutable} that should be used as etalon for computing the contextual position of {@link ILayoutable} in parameter
    * 
    * @param sizer
    * @param layoutable
    * @param ctx the contextual position being computed, {@link ITechLayout#CTX_1_WIDTH} = X / {@link ITechLayout#CTX_2_HEIGHT} = Y
    * @return
    */
   public ILayoutable getDelegateEtalonePozer(ByteObject pozer, ILayoutable layoutable, int ctx);

   /**
    * Returns the {@link ILayoutable} that should be used as etalon for computing the contextual size of {@link ILayoutable} in parameter
    * 
    * @param sizer
    * @param layoutable
    * @param ctx the contextual size being computed, {@link ITechLayout#CTX_1_WIDTH} / {@link ITechLayout#CTX_2_HEIGHT}
    * @return
    */
   public ILayoutable getDelegateEtaloneSizer(ByteObject sizer, ILayoutable layoutable, int ctx);

   /**
    * 
    * @param pozer
    * @param layoutable
    * @return
    */
   public int getDelegatePozerX(ByteObject pozer, ILayoutable layoutable);

   /**
    * 
    * @param pozer
    * @param layoutable
    * @return
    */
   public int getDelegatePozerY(ByteObject pozer, ILayoutable layoutable);

   /**
    * The delegate computes the size height of the {@link ILayoutable}.
    * <br>
    * <br>
    * The sizer {@link ByteObject} that generated this call in the {@link LayoutOperator} is provided 
    * <br>
    * Used when the function for computing the height is too complex or dynamic to be expressed using the {@link IBOSizer}
    * @param sizer 
    * @param layoutable
    * @return 
    * @throws new {@link IllegalArgumentException} if layoutable is not know
    */
   public int getDelegateSizeHeight(ByteObject sizer, ILayoutable layoutable);


   /**
    * Delegate computes the size width of {@link ILayoutDelegate}.
    * <br>
    * <br>
    * The sizer {@link ByteObject} that generated this call in the {@link LayoutOperator} is provided 
    * @param sizer
    * @param layoutable 
    * @return 
    */
   public int getDelegateSizeWidth(ByteObject sizer, ILayoutable layoutable);
}
