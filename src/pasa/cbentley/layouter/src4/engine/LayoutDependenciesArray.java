/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ObjectLayouter;
import pasa.cbentley.layouter.src4.ctx.ToStringStaticLayout;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDependencies;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

/**
 * Helper class for implementing the {@link ILayoutable#setDependency(ILayoutable, int)} method.
 */
public class LayoutDependenciesArray extends ObjectLayouter implements ILayoutDependencies {

   /**
    * int is the dependency type.
    */
   private IntToObjects array;

   /**
    * 
    *
    * @param lc 
    */
   public LayoutDependenciesArray(LayouterCtx lc) {
      super(lc);
      array = new IntToObjects(lc.getUCtx());
   }

   /**
    * 
    *
    * @param lay 
    * @param flags 
    */
   public void setDependency(ILayoutable lay, int flags) {
      int index = array.getObjectIndex(lay);
      if (index == -1) {
         array.add(lay, flags);
      } else {
         int flag = array.getInt(index);
         if (BitUtils.hasFlag(flag, flag)) {
            array.setInt(flags, index);
         }
      }
   }

   /**
    * 
    *
    * @return 
    */
   public ILayoutable[] getDependencies() {
      if (array == null || array.getLength() == 0) {
         return null;
      }
      ILayoutable[] ar = new ILayoutable[array.getLength()];
      for (int i = 0; i < ar.length; i++) {
         ar[i] = (ILayoutable) array.getObjectAtIndex(i);
      }
      return ar;
   }

   /**
    * 
    * @param lay
    * @return 0 if no dependency
    */
   public int getDependency(ILayoutable lay) {
      int index = array.getObjectIndex(lay);
      if (index != -1) {
         return array.getInt(index);
      }
      return 0;
   }

   /**
    * 
    *
    * @param flags 
    * @return 
    */
   private boolean isMatchPosition(int flags) {
      return flags == ITechLayout.DEPENDENCY_2_POZE || flags == ITechLayout.DEPENDENCY_3_BOTH;
   }

   /**
    * 
    *
    * @param flags 
    * @return 
    */
   private boolean isMatchSize(int flags) {
      return flags == ITechLayout.DEPENDENCY_1_SIZE || flags == ITechLayout.DEPENDENCY_3_BOTH;
   }

   /**
    * 
    *
    * @param flags 
    */
   public void layoutUpdateDependencies(int flags) {
      for (int i = 0; i < array.nextempty; i++) {
         int flagsLayoutable = array.getInt(i);
         if (flagsLayoutable != 0) {
            ILayoutable layoutable = (ILayoutable) array.getObjectAtIndex(i);
            if (flags == ITechLayout.DEPENDENCY_3_BOTH) {
               layoutable.layoutInvalidate();
               layoutable.layoutUpdateSizeCheck();
               layoutable.layoutUpdatePositionCheck();
            } else if (flags == ITechLayout.DEPENDENCY_2_POZE) {
               if (isMatchPosition(flagsLayoutable)) {
                  layoutable.layoutInvalidatePosition();
                  layoutable.layoutUpdatePositionCheck();
               }
            } else if (flags == ITechLayout.DEPENDENCY_1_SIZE) {
               if (isMatchSize(flagsLayoutable)) {
                  layoutable.layoutInvalidateSize();
                  layoutable.layoutUpdatePositionCheck();
               }
            }
         }
      }
   }

   /**
    * 
    *
    * @param layout 
    * @param flags 
    */
   public void removeDependency(ILayoutable layout, int flags) {
      int index = array.getObjectIndex(layout);
      if (index != -1) {
         int flagsLayoutable = array.getInt(index);
         if (flags == ITechLayout.DEPENDENCY_X_DELETE) {
            array.delete(index);
         } else {
            if (flags == ITechLayout.DEPENDENCY_3_BOTH) {
               //remove both dependencies
               flagsLayoutable = ITechLayout.DEPENDENCY_0_NONE;
            } else if (flags == ITechLayout.DEPENDENCY_1_SIZE) {
               flagsLayoutable = BitUtils.setFlag(flagsLayoutable, ITechLayout.DEPENDENCY_1_SIZE, false);
            } else if (flags == ITechLayout.DEPENDENCY_2_POZE) {
               flagsLayoutable = BitUtils.setFlag(flagsLayoutable, ITechLayout.DEPENDENCY_2_POZE, false);
            }
            array.setInt(flagsLayoutable, index);
         }
      }

   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, LayoutDependenciesArray.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.appendVarWithNewLine("#dependencies", array.getLength());
      for (int index = 0; index < array.getLength(); index++) {
         ILayoutable o = (ILayoutable) array.getObjectAtIndex(index);
         int flags = array.getInt(index);
         dc.nl();
         dc.appendVar("Type", ToStringStaticLayout.dependencies(flags));
         dc.append("->");
         dc.oneLine(o);
      }
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayoutDependenciesArray.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
