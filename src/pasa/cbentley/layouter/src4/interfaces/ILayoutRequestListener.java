/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;

/**
 * Sizes can be computed dynamically by this delegate listener.
 *
 * @author Charles Bentley
 */
public interface ILayoutRequestListener {

   /**
    * 
    *
    * @param layoutable 
    * @param sizer 
    * @param res 
    */
   public void computeSizeWidthFor(ILayoutable layoutable, ByteObject sizer, SizeResult res);

   /**
    * 
    *
    * @param layoutable 
    * @param sizer 
    * @param res 
    */
   public void computeSizeHeightFor(ILayoutable layoutable, ByteObject sizer, SizeResult res);
}
