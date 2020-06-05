/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.tech;

import pasa.cbentley.byteobjects.src4.tech.ITechByteObject;

/**
 * Linker provides a reference to an etalon.
 *
 * @author Charles Bentley
 */
public interface ITechLinker extends ITechByteObject {
   
   /**
    * 
    */
   public static final int LINKER_BASIC_SIZE      = A_OBJECT_BASIC_SIZE + 5;

   /**
    * 
    */
   public static final int LINKER_OFFSET_01_TYPE1 = A_OBJECT_BASIC_SIZE;

   /**
    * 
    */
   public static final int LINKER_OFFSET_02_DATA4 = A_OBJECT_BASIC_SIZE + 1;

}
