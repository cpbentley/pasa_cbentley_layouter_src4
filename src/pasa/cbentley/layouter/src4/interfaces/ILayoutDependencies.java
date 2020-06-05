/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

/**
 * 
 */
public interface ILayoutDependencies extends IStringable {

   /**
    * <p>
    * Call the 
    * <li>{@link ILayoutable#layoutUpdatePositionCheck()}
    * <li>{@link ILayoutable#layoutUpdateSizeCheck()}
    * </P>
    * 
    * Depending on the type of dependency asked
    * 
    * <p>
    * <li> {@link ITechLayout#DEPENDENCY_0_NONE}
    * <li> {@link ITechLayout#DEPENDENCY_1_SIZE}
    * <li> {@link ITechLayout#DEPENDENCY_2_POZE}
    * <li> {@link ITechLayout#DEPENDENCY_3_BOTH}
    * </P>.
    *
    * @param flags 
    */
   public void layoutUpdateDependencies(int flags);

   /**
    * 
    *
    * @param lay 
    * @param flags 
    */
   public void addDependency(ILayoutable lay, int flags);

   /**
    * Remove the dependency to {@link ILayoutable} for the flags.
    *
    * @param layout 
    * @param flags 
    */
   public void removeDependency(ILayoutable layout, int flags);
   
   /**
    * A copy of the array.
    *
    * @return null if none
    */
   public ILayoutable[] getDependencies();
}
