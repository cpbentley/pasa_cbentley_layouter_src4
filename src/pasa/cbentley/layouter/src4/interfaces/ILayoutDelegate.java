package pasa.cbentley.layouter.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

/**
 * When the function is too complex or dynamic to be expressed by {@link ITechLayout}
 * 
 * @author Charles Bentley
 *
 */
public interface ILayoutDelegate {

   /**
    * The delegate computes the size of the {@link ILayoutable}
    * When the function is too complex or dynamic to be expressed
    * @param sizer TODO
    * @param layoutable TODO
    * @return 
    * @throws new {@link IllegalArgumentException} if layoutable is not know
    */
   public int getDelegateSizeHeight(ByteObject sizer, ILayoutable layoutable);

   /**
    * 
    *
    * @return 
    */
   public int getDelegateSizeWidth(ByteObject sizer, ILayoutable layoutable);

   public ILayoutable getDelegateSizer(ByteObject sizer, ILayoutable layoutable, int ctx);
   
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
}
