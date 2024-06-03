package pasa.cbentley.layouter.src4.tech;

public interface ITechCodedHeader {

   /**
    * When this bit is set, we have a coded value. Otherwise, return value without modification.
    * 
    * The sign bit is avoided to allow negative values
    * <br>
    * Set whenpublic static final int value is coded for a mode such as
    * <li> {@link ITechLayout#MODE_1_DELEGATE}
    * <li> {@link ITechLayout#MODE_3_SCALE}
    * <br>
    * <br>
    * Otherwise, raw pixel value
    */
   public static final int CODED_SIZE_FLAG_30_CODED              = 1 << 29;

   /**
    * This is a Java specific representation. 
    * <br>
    * nearly all computer represent negative number with 2 complements 
    * http://stackoverflow.com/questions/30496810/the-way-c-sharp-represents-negative-integers-in-memory-and-casts-them-unchecked
    * 
    * <li>When bit 32 is 1 and bit 31 is 0 and bit 30 is 0,     we have a coded value.
    * <li>When bit 32 is 1 and bit 31 is 1 and bit 30 is 1,     we have a sub code
    * <li>When bit 32 is 1 and bit 31 is 1 and bit 30 is 0,     we have a negative constant public static final integer
    * <li>When bit 32 is 1 and bit 31 is 1 and bit 30 is 1,     we have a negative constant public static final integer
    * 
    */
   public static final int CODED_SIZE_FLAG_31_SIGN_CODE          = 1 << 30;

   /**
    * Reserved since this bit is being used by Java to code for negative values.
    * 
    * It is not used to code values. It is part of the bits for uncoded raw values.
    * 
    */
   public static final int CODED_SIZE_FLAG_32_SIGN               = 1 << 31;
   
   public static final int CODED_MASK_5_CODE_PAGE                = 0x7;

   public static final int CODED_MASK_6_FLAGS                    = 0x7;
   
   public static final int CODED_SIZE_SHIFT_5_CODE_PAGE          = 27;

}
