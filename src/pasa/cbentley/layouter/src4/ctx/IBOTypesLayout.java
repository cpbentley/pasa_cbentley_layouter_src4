/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.ctx;

/**
 * 
 */
public interface IBOTypesLayout {

   /**
    * 
    */
   public static final int AZ_BOTYPE_FW_A   = 41;

   /**
    * 
    */
   public static final int AZ_BOTYPE_FW_Z   = 49;

   /**
    * Type which stores X bytes of Top/Bottom,Left, Right data
    * 
    * X is defined by byte #2 
    * <br>
    * 
    * In Swing, java.awt.Insets
    */
   public static final int FTYPE_2_TBLR     = AZ_BOTYPE_FW_A + 2;

   /**
    * Defines how to convert an input value based on reference values.
    * <br>
    * Used to compute actual pixel sizes of shapes, figures, gradients.
    * Adds some vectorial capabilities to figure definitions.
    */
   public static final int FTYPE_3_SIZER    = AZ_BOTYPE_FW_A + 3;

   /**
    * Encodes a relative position in a 2D rectangle.
    * The 2D rectangle is context dependant
    * <li> Provided by the call
    * <li> Defined explicitely as Parent.
    * <li> A function
    * 
    */
   public static final int FTYPE_4_POSITION = AZ_BOTYPE_FW_A + 4;

   /**
    * 
    */
   public static final int FTYPE_6_LINK     = AZ_BOTYPE_FW_A + 6;

   /**
    * 
    */
   public static final int RELATION_1_MIN   = 1;

   /**
    * 
    */
   public static final int RELATION_2_MAX   = 2;

}
