package pasa.cbentley.layouter.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;

/**
 * Sizes can be computed dynamically by this delegate listener
 * 
 * @author Charles Bentley
 *
 */
public interface ILayoutRequestListener {

   public void computeSizeWidthFor(ILayoutable layoutable, ByteObject sizer, SizeResult res);

   public void computeSizeHeightFor(ILayoutable layoutable, ByteObject sizer, SizeResult res);
}
