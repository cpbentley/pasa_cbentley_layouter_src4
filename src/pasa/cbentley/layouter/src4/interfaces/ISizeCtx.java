/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.layouter.src4.engine.LayoutOperator;
import pasa.cbentley.layouter.src4.tech.IBOSizer;

/**
 * Computes Etalon sizes for the {@link LayoutOperator}.
 * <br>
 * {@link LayoutOperator#getPixelSize(ByteObject, ISizeCtx, int)} is called when a {@link ISizeCtx} want to 
 * compute 
 * 
 * When computing 2D dynamic sizes, {@link ISizable} needs an etalon context
 * <br>
 * Able to compute {@link IBOSizer#ETALON_0_SIZEE_CTX}
 * 
 * <li> {@link IBOSizer#E_CTX_0_PREFERRED_SIZE}
 * @author Charles Bentley
 *
 */
public interface ISizeCtx {

   /**
    * Get the etalon H of the given type.
    * Provides the Height of the given etalon.
    * <br>
    * Read {@link IBOSizer#SIZER_OFFSET_06_PROPERTY1} and deal with the case
    * 
    * <li> {@link IBOSizer#E_CTX_0_PREFERRED_SIZE}
    * <li> {@link IBOSizer#E_CTX_1_LOGIC_SIZE}
    * <li> {@link IBOSizer#E_CTX_2_PARENT}
    * <br>
    * <br>
    *
    * @param sizer 
    * @return -1 if could not compute
    */
   public int getSizeEtalonH(ByteObject sizer);

   /**
    * Same as getSizeEtalonH but for the width.
    *
    * @param sizer 
    * @return -1 if could not compute
    */
   public int getSizeEtalonW(ByteObject sizer);

}
