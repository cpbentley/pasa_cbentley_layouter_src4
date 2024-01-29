/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.ctx;

import pasa.cbentley.core.src4.logging.ToStringStaticBase;
import pasa.cbentley.layouter.src4.tech.IBOPozer;
import pasa.cbentley.layouter.src4.tech.IBOSizer;
import pasa.cbentley.layouter.src4.tech.ITechLayout;
import pasa.cbentley.layouter.src4.tech.ITechSizer;

/**
 * 
 */
public class ToStringStaticLayout extends ToStringStaticBase implements ITechLayout {

   public static String toStringCompute(int c) {
      switch (c) {
         case COMPUTE_0_INVALID:
            return "Invalid";
         case COMPUTE_1_NORMAL:
            return "Normal";
         case COMPUTE_2_INVERSE:
            return "Inverse";
         case COMPUTE_3_BOTH:
            return "Both";
         default:
            return "Unknown" + c;
      }
   }

   public static String toStringComputeSize(int c) {
      switch (c) {
         case COMPUTE_SIZE_0_NORMAL:
            return "Normal";
         case COMPUTE_SIZE_1_ONLY_W:
            return "OnlyW";
         case COMPUTE_SIZE_2_ONLY_H:
            return "OnlyH";
         case COMPUTE_SIZE_3_NONE:
            return "None";
         default:
            return "Unknown" + c;
      }
   }

   /**
    * 
    *
    * @param fun 
    * @return 
    */
   public static String toStringEtalonSizer(int fun) {
      switch (fun) {
         case ITechLayout.ETALON_0_SIZEE_CTX:
            return "Ctx";
         case ITechLayout.ETALON_1_VIEWCONTEXT:
            return "ViewContext";
         case ITechLayout.ETALON_2_FONT:
            return "Font";
         case ITechLayout.ETALON_3_RATIO:
            return "Ratio";
         case ITechLayout.ETALON_4_PARENT:
            return "Parent";
         case ITechLayout.ETALON_5_LINK:
            return ":ink";
         case ITechLayout.ETALON_6_POZER_BOX:
            return "Pozerbox";
         case ITechLayout.ETALON_7_DELEGATE:
            return "delegate";
         default:
            return "Unknown " + fun;
      }
   }

   public static String toStringTypes(int type) {
      String str = toStringTypesNull(type);
      if (str == null) {
         return "UnknownType " + type;
      }
      return str;
   }

   public static String toStringTypesNull(int type) {
      switch (type) {
         case IBOTypesLayout.FTYPE_3_SIZER:
            return "Sizer";
         case IBOTypesLayout.FTYPE_4_POSITION:
            return "Pozer";
         case IBOTypesLayout.FTYPE_6_LINK:
            return "Link";
         default:
            return null;
      }
   }

   public static String dependencies(int type) {
      String str = dependenciesNull(type);
      if (str == null) {
         return "Unknown dependency " + type;
      }
      return str;
   }

   public static String dependenciesNull(int type) {
      switch (type) {
         case ITechLayout.DEPENDENCY_0_NONE:
            return "None";
         case ITechLayout.DEPENDENCY_1_SIZE:
            return "Size";
         case ITechLayout.DEPENDENCY_2_POZE:
            return "Position";
         case ITechLayout.DEPENDENCY_3_BOTH:
            return "SizeAndPosition";
         case ITechLayout.DEPENDENCY_4_PARENT:
            return "Parent";
         case ITechLayout.DEPENDENCY_X_DELETE:
            return "Delete";
         default:
            return null;
      }
   }

   /**
    * 
    *
    * @param etalon 
    * @return 
    */
   public static String toStringEtalonPozer(int etalon) {
      switch (etalon) {
         case IBOPozer.POS_ETALON_0_POINT:
            return "none";
         case IBOPozer.POS_ETALON_1_PARENT:
            return "parent";
         case IBOPozer.POS_ETALON_2_VIEWCTX:
            return "viewctx";
         case IBOPozer.POS_ETALON_3_LINK:
            return "link";
         case IBOPozer.POS_ETALON_4_SIZER:
            return "sizer";
         default:
            return "Unknown " + etalon;
      }
   }

   /**
    * 
    *
    * @param style 
    * @return 
    */
   public static String toStringViewStyle(int style) {
      switch (style) {
         case ITechLayout.VIEW_STYLE_00_VIEW_FULL:
            return "full";
         case ITechLayout.VIEW_STYLE_01_VIEW_CONTENT_PAD_BORDER:
            return "contentpadborder";
         case ITechLayout.VIEW_STYLE_02_VIEW_CONTENT_PAD:
            return "contentpad";
         case ITechLayout.VIEW_STYLE_03_VIEW_CONTENT:
            return "content";
         default:
            return "Unknown " + style;
      }
   }

   /**
    * 
    *
    * @param struct 
    * @return 
    */
   public static String toStringViewStruct(int struct) {
      switch (struct) {
         case ITechLayout.VIEW_STRUCT_00_ALL_VISIBLE:
            return "full";
         case ITechLayout.VIEW_STRUCT_01_INNER_CONTENT:
            return "content";
         case ITechLayout.VIEW_STRUCT_02_VIEW_PORT:
            return "viewport";
         case ITechLayout.VIEW_STRUCT_03_VIEW_PORT_ARTIFACTS:
            return "viewportartifact";
         default:
            return "Unknown " + struct;
      }
   }

   /**
    * 
    *
    * @param fun 
    * @return 
    */
   public static String toStringEtalonShort(int fun) {
      switch (fun) {
         case ITechLayout.ETALON_0_SIZEE_CTX:
            return "ctx";
         case ITechLayout.ETALON_1_VIEWCONTEXT:
            return "viewctx";
         case ITechLayout.ETALON_3_RATIO:
            return "ratioscreen";
         case ITechLayout.ETALON_2_FONT:
            return "font";
         default:
            return "Unknown " + fun;
      }
   }

   /**
    * 
    *
    * @param offset 
    * @return 
    */
   public static String toStringSizerField(int offset) {
      switch (offset) {
         case IBOSizer.SIZER_OFFSET_01_FLAG:
            return "flag";
         case IBOSizer.SIZER_OFFSET_02_MODE1:
            return "mode";
         case IBOSizer.SIZER_OFFSET_03_ETALON1:
            return "etalon";
         case IBOSizer.SIZER_OFFSET_04_FUNCTION1:
            return "func";
         case IBOSizer.SIZER_OFFSET_05_VALUE2:
            return "value";
         default:
            return "Unknown offset " + offset;
      }
   }

   /**
    * 
    *
    * @param fun 
    * @return 
    */
   public static String toStringFun(int fun) {
      switch (fun) {
         case ITechLayout.ET_FUN_0_CTX:
            return "Ctx";
         case ITechLayout.ET_FUN_1_WIDTH:
            return "Width";
         case ITechLayout.ET_FUN_2_HEIGHT:
            return "Height";
         case ITechLayout.ET_FUN_3_MIN:
            return "Min";
         case ITechLayout.ET_FUN_4_MAX:
            return "Max";
         case ITechLayout.ET_FUN_5_ADD:
            return "Add";
         case ITechLayout.ET_FUN_6_DIFF:
            return "Diff";
         case ITechLayout.ET_FUN_7_CTX_OP:
            return "CtxInverse";
         default:
            return "Unknown " + fun;
      }
   }

   /**
    * 
    *
    * @param unit 
    * @return 
    */
   public static String toStringRawUnit(int unit) {
      switch (unit) {
         case ITechLayout.RAW_UNIT_0_PIXEL:
            return "Pixel";
         case ITechLayout.RAW_UNIT_1_DIP:
            return "DIP";
         default:
            return "Unknown " + unit;
      }
   }

   /**
    * 
    *
    * @param mode 
    * @return 
    */
   public static String toStringMod(int mode) {
      switch (mode) {
         case ITechLayout.MODE_0_RAW_UNITS:
            return "RawUnits";
         case ITechLayout.MODE_1_DELEGATE:
            return "DIP";
         case ITechLayout.MODE_2_RATIO:
            return "Ratio";
         case ITechLayout.MODE_3_SCALE:
            return "Scale";
         case ITechLayout.MODE_5_FUNCTION:
            return "Function";
         default:
            return "Unknown " + mode;
      }
   }

   /**
    * 
    *
    * @param fun 
    * @return 
    */
   public static String toStringFunShort(int fun) {
      switch (fun) {
         case ITechLayout.ET_FUN_0_CTX:
            return "ctx";
         case ITechLayout.ET_FUN_1_WIDTH:
            return "w";
         case ITechLayout.ET_FUN_2_HEIGHT:
            return "h";
         case ITechLayout.ET_FUN_3_MIN:
            return "min";
         case ITechLayout.ET_FUN_4_MAX:
            return "max";
         case ITechLayout.ET_FUN_5_ADD:
            return "+";
         case ITechLayout.ET_FUN_6_DIFF:
            return "-";
         default:
            return "Unknown " + fun;
      }
   }

   /**
    * 
    *
    * @param fun 
    * @return 
    */
   public static String toStringSizerProp(int fun) {
      switch (fun) {
         case ITechSizer.SIZER_PROP_00_DRAWN:
            return "drawn";
         case ITechSizer.SIZER_PROP_01_PREFERRED:
            return "preferred";
         case ITechSizer.SIZER_PROP_02_UNIT_LOGIC:
            return "unit";
         case ITechSizer.SIZER_PROP_03_FONT:
            return "font";
         case ITechSizer.SIZER_PROP_05_CONTENT:
            return "content";
         default:
            return "Unknown " + fun;
      }
   }

   /**
    * 
    *
    * @param offset 
    * @return 
    */
   public static String toStringPozerField(int offset) {
      switch (offset) {
         case IBOPozer.POS_OFFSET_01_FLAG:
            return "flag";
         case IBOPozer.POS_OFFSET_02_ETALON1:
            return "etalon";
         case IBOPozer.POS_OFFSET_03_ANCHOR_ETALON_POINT_VALUE4:
            return "point";
         case IBOPozer.POS_OFFSET_04_ANCHOR_ETALON1:
            return "anchorEtalon";
         case IBOPozer.POS_OFFSET_07_ANCHOR_POZEE1:
            return "anchorPozee";
         case IBOPozer.POS_OFFSET_08_ANCHOR_POZEE_STRUCT1:
            return "anchorPozeeStruct";
         case IBOPozer.POS_OFFSET_09_ANCHOR_POZEE_STYLE1:
            return "anchorPozeeStyle";
         case IBOPozer.POS_OFFSET_10_SIZER_FUN1:
            return "fun";
         default:
            return "Unknown offset " + offset;
      }
   }

   /**
    * 
    *
    * @param fun 
    * @return 
    */
   public static String toStringPozerFun(int fun) {
      switch (fun) {
         case IBOPozer.POS_FUN_0_TOWARDS_CENTER:
            return "Towards center";
         case IBOPozer.POS_FUN_1_AWAY_CENTER:
            return "Away from center";
         default:
            return "Unknown " + fun;
      }
   }

   /**
    * 
    *
    * @param prop 
    * @return 
    */
   public static String toStringFontProp(int prop) {
      switch (prop) {
         case ITechLayout.E_FONT_0_DEFAULT:
            return "default";
         case ITechLayout.E_FONT_1_DEFINED:
            return "defined";
         case ITechLayout.E_FONT_2_SMALL:
            return "small";
         default:
            return "Unknown " + prop;
      }
   }
}
