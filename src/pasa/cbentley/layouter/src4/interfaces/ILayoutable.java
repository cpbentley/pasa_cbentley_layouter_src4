/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

import pasa.cbentley.core.src4.interfaces.ITechNav;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.engine.ByteObjectLayoutDelegate;
import pasa.cbentley.layouter.src4.engine.LayouterEngine;
import pasa.cbentley.layouter.src4.engine.Zer2DArea;
import pasa.cbentley.layouter.src4.tech.IBOPozer;
import pasa.cbentley.layouter.src4.tech.IBOSizer;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

/**
 * 2D UI object injectable in the Layouter engine.
 * 
 * <p>
 * 
 * It has a preferred size
 * 
 * <li>The drawn size, pixels used on screen
 * <li>The preferred size, the pixel object wished to use to display all of its content.
 * <li>The pixel size of 1 unit of its content.
 * <li>The font pixel size used by this object
 * </p>
 * 
 * <p>
 * 
 * All sizes are expressed in pixels.
 * 
 * Sugar methods are to be pruned and optimized away.
 * 
 * </p>
 * @author Charles Bentley
 *
 */
public interface ILayoutable extends IStringable {

   /**
    * Returns the {@link Zer2DArea} that hosts the sizers and pozers of this {@link ILayoutable}.
    *
    * @return 
    */
   public Zer2DArea getArea();

   /**
    * Return the {@link ILayoutable}s that depends on this {@link ILayoutable} for their wh sizes and/or xy positions.
    * <br>
    * This set is modified by {@link ILayoutable#setDependency(ILayoutable, int)}
    * @return null if none
    */
   public ILayoutable[] getDependencies();

   /**
    * Return the reference to be used for computing a size and/or a position.
    * 
    * The Delegate uses the source reference to return the Etalon.
    * 
    * 2 indirection.
    * Allows for dynamic etalons
    * 
    * Similar to {@link ByteObjectLayoutDelegate} but the control of the delegate
    * is on the {@link ILayoutable} instead of the sizer/pozer definition
    * 
    * {@link ITechLayout#ETALON_7_DELEGATE}
    * 
    * @param source
    * @return null if {@link ILayoutable} is unaware of a delegate
    */
   public ILayoutable getLayoutableDelegate(ILayoutable source);

   /**
    * Enables custom etalon types for
    * 
    * {@link IBOSizer#SIZER_OFFSET_04_ET_SUBTYPE1}
    * {@link IBOPozer#POS_OFFSET_02_ETALON1}
    * 
    * TODO add the layout context to help choose the etalon, provides others parameteres
    * the target layoutable etc
    * @param etalonType
    * @return
    */
   public ILayoutable getLayoutableEtalon(int etalonType);

   /**
    * In the context of this {@link ILayoutable} which .
    *
    * @param id 
    * @return null if id could not be found
    */
   public ILayoutable getLayoutableID(int id);

   /**
    * Which {@link ILayoutable} is positioned in the nav graph
    *  
    * null if no object positioned on top, {@link ITechNav#NAV_1_UP}.
    *
    * @param dir code from {@link ITechNav}
    * @return {@link ILayoutable}
    * @see ITechNav
    */
   public ILayoutable getLayoutableNav(int dir);

   /**
    * The parent, null if root.
    *
    * @return {@link ILayoutable}
    */
   public ILayoutable getLayoutableParent();

   /**
    * {@link ITechLayout#ETALON_1_VIEWCONTEXT}.
    * If the {@link ILayoutable} does not have the concept of a ViewContext,
    * return the parent or the root {@link ILayoutable}
    * 
    * @return {@link ILayoutable}
    */
   public ILayoutable getLayoutableViewContext();

   /**
    * When only a portion of the object is visible, this method
    * returns the {@link ILayoutable} controlling that area.
    * 
    * If none, return null
    * @return
    */
   public ILayoutable getLayoutableViewPort();

   /**
    * {@link ILayoutDelegate}
    * @return
    */
   public ILayoutDelegate getLayoutDelegate();

   /**
    * Unique ID given by the underlying {@link LayouterCtx}
    * 
    * 
    * 0 if no id.
    * It allows to indirectly reference another {@link ILayoutable} that hasn't been created yet.
    * 
    * Some layout id are statically defined, if none, it is created by {@link LayouterCtx#getNewLayoutID()}
    * 
    * @return 
    */
   public int getLayoutID();

   /**
    * Simply returns current x/start value.
    *
    * @return 
    */
   public int getPozeX();

   /**
    * If not computed, compute only the X.
    *
    * @return 
    */
   public int getPozeXComputed();

   /**
    * Simply returns current y/top value.
    *
    * @return 
    */
   public int getPozeY();

   /**
    * If not computed, compute only the Y.
    *
    * @return 
    */
   public int getPozeYComputed();

   /**
    * Method is a simple get and does not computes anything.
    * <br>
    * Caller is responsible to check with {@link ILayoutable#layoutIsValidSize()} or
    * {@link ILayoutable#layoutUpdateSizeHCheck()}
    * @return pixel height drawn on screen if it would be drawn on this call
    */
   public int getSizeDrawnHeight();

   /**
    * Method is a simple get and does not computes anything.
    * <br>
    * Caller is responsible to check with {@link ILayoutable#layoutIsValidSize()}
    * @return pixel width drawn on screen if it would be drawn on this call
    */
   public int getSizeDrawnWidth();

   /**
    * The height in pixels.
    *
    * @return 
    */
   public int getSizeFontHeight();

   /**
    * The size in pixels of the widest letter.
    *
    * @return 
    */
   public int getSizeFontWidth();

   /**
    * Returns the preferred height of this {@link ILayoutable}
    *
    * @return 
    */
   public int getSizePreferredHeight();

   /**
    * Returns the preferred height of this {@link ILayoutable}
    * 
    * The {@link ILayoutable} computes this value based on its content
    * @return 
    */
   public int getSizePreferredWidth();

   /**
    * Same as {@link ILayoutable#getSizePropertyValueW(int)}
    * 
    * @param property
    * @return
    */
   public int getSizePropertyValueH(int property);

   /**
    * Returns the current non computed width property.
    * Some often use properties have their own methods
    * <li> {@link ITechLayout#SIZER_PROP_00_DRAWN} -> {@link ILayoutable#getSizeDrawnWidth()}
    * <li> {@link ITechLayout#SIZER_PROP_01_PREFERRED} -> {@link ILayoutable#getSizePreferredWidth()}
    * <li> {@link ITechLayout#SIZER_PROP_02_UNIT_LOGIC}
    * <li> {@link ITechLayout#SIZER_PROP_03_FONT} -> {@link ILayoutable#getSizeFontWidth()}
    * <li> {@link ITechLayout#SIZER_PROP_05_CONTENT}
    * <li> {@link ITechLayout#SIZER_PROP_06_CONTENT_PAD}
    * <li> {@link ITechLayout#SIZER_PROP_07_CONTENT_PAD_BORDER}
    * <li> {@link ITechLayout#SIZER_PROP_10_PAD}
    * 
    * <p>
    * When {@link ILayoutable} is free to send a similar value when it has no understanding of the concept.
    * </p>
    * 
    * <p>
    * The {@link LayouterEngine} will call this method on an Etalon {@link ILayoutable} to compute sizes for other {@link ILayoutable} .
    * 
    * </p>
    * @param property
    * @return
    */
   public int getSizePropertyValueW(int property);

   /**
    * The layout is invalid so that 
    * 
    * {@link ILayoutable#layoutIsValidSize()} returns false
    * {@link ILayoutable#layoutIsValidPosition()} returns false
    * 
    * It will also invalidates dependencies.
    */
   public void layoutInvalidate();

   /**
    * Invalidating the position may invalidates the size if size is dependant of the position.
    * <br>
    * It will also invalidates the positions of dependencies
    */
   public void layoutInvalidatePosition();

   /**
    * Tags the <b>width</b> and <b>height</b> of this object as stale/invalid.
    * <br>
    * They will be computed again next time they are requested
    */
   public void layoutInvalidateSize();

   /**
    * True if x and y position values are tagged as valid
    *
    * @return 
    */
   public boolean layoutIsValidPosition();

   /**
    * True if width and height size values are tagged as valid
    *
    * @return 
    */
   public boolean layoutIsValidSize();

   /**
    * Called when {@link ILayoutable} has been modified and the {@link ILayoutable} that depends 
    * on it for their layout have to update.
    * <br>
    * <br>
    * The caller knows the context of the change and is responsible for choosing the right type
    *
    * <li> {@link ITechLayout#DEPENDENCY_0_NONE}
    * <li> {@link ITechLayout#DEPENDENCY_1_SIZE} when size has been changed
    * <li> {@link ITechLayout#DEPENDENCY_2_POZE} when position has been changed
    * <li> {@link ITechLayout#DEPENDENCY_3_BOTH} when both position and size have been modified
    * <li> {@link ITechLayout#DEPENDENCY_4_PARENT}
    * <li> {@link ITechLayout#DEPENDENCY_X_DELETE} 
    *
    * @param type 
    */
   public void layoutUpdateDependencies(int type);

   /**
    * Check if position, also check the size
    */
   public void layoutUpdatePositionCheck();

   /**
    * Updates only the position.
    * 
    * It might need to compute the size
    */
   //public void layoutUpdatePosition();

   /**
    * 
    */
   public void layoutUpdatePositionXCheck();

   /**
    * 
    */
   public void layoutUpdatePositionYCheck();

   /**
    * Computes the size, whatever the state.
    * 
    * When no sizer has been defined, the sizer preferred size is assumed
    * for both the width and the height
    * 
    * This bombs if valid TODO
    */
   public void layoutUpdateSizeCheck();

   /**
    * Computes the Height size.
    * <p>
    * If no sizerH is set, the sizer the preferred Height size is assumed.
    * </p>
    */
   public void layoutUpdateSizeHCheck();

   /**
    * Computes the Width size, if check says the width is invalid.
    * <p>
    * If no sizerW is set, the sizer the preferred Width size is assumed.
    * </p>
    * Check means, the method checks the cached value. It does not force the computation.
    */
   public void layoutUpdateSizeWCheck();

   /**
    * 
    */
   public void repaintLayoutable();

   /**
    * Completely change the area of this {@link ILayoutable}
    * 
    * This method invalidates positions and sizes.
    *
    * @param area 
    */
   public void setArea(Zer2DArea area);

   /**
    * 
    * Modifies the dependency flags. <code>lay</code> is dependant of <b><code>this</code></b>. 
    * <br>
    * <br>
    * ie. the {@link ILayoutable} parameter <code>lay</code> is using <b><code>this</code></b> {@link ILayoutable} for computing its sizes and/or positions.
    * <br>
    * <br>
    * 
    * A {@link ILayoutable} can find its dependents by calling {@link ILayoutable#getDependencies()} .
    * <br>
    * The operator
    * 
    * <li>{@link ITechLayout#DEPENDENCY_0_NONE}
    * <li>{@link ITechLayout#DEPENDENCY_1_SIZE}
    * <li>{@link ITechLayout#DEPENDENCY_2_POZE}
    * <li>{@link ITechLayout#DEPENDENCY_3_BOTH}
    * <li>{@link ITechLayout#DEPENDENCY_4_PARENT} flag set when {@link ILayoutable} is a child of this.
    * <li>{@link ITechLayout#DEPENDENCY_X_DELETE}
    * <br>
    * 
    * When an local event that modifies your XY positions or WH sizes, the engine can request
    * the computation of those {@link ILayoutable}s that depend on the changed values.
    * @param lay 
    * @param operator  
    */
   public void setDependency(ILayoutable lay, int operator);

   //#mdebug
   /**
    * Debug short name.
    *
    * @return 
    */
   public String toStringName();
   //#enddebug
}
