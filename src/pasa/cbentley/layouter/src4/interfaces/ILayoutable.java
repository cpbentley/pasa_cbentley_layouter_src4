/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.interfaces.ITechNav;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.engine.ByteObjectLayoutDelegate;
import pasa.cbentley.layouter.src4.engine.LayEngine;
import pasa.cbentley.layouter.src4.engine.Zer2DArea;
import pasa.cbentley.layouter.src4.tech.ITechLayout;
import pasa.cbentley.layouter.src4.tech.ITechPozer;
import pasa.cbentley.layouter.src4.tech.ITechSizer;

/**
 * 2D UI object able to be used in the Layouter engine.
 * 
 * 
 * It has a preferred size
 * 
 * <li>The drawn size, pixels used on screen
 * <li>The preferred size, the pixel object wished to use to display all of its content.
 * <li>The pixel size of 1 unit of its content.
 * <li>The font pixel size used by this object
 * 
 * All sizes are expressed in pixels.
 * 
 * Sugar methods are to be pruned and optimized away.
 * 
 * @author Charles Bentley
 *
 */
public interface ILayoutable extends IStringable {

   /**
    * Adding a dependency
    * 
    * A {@link ILayoutable} can find its children by looking at.
    * <li>{@link ITechLayout#DEPENDENCY_0_NONE}
    * <li>{@link ITechLayout#DEPENDENCY_1_SIZE}
    * <li>{@link ITechLayout#DEPENDENCY_2_POZE}
    * <li>{@link ITechLayout#DEPENDENCY_3_BOTH}
    * <li>{@link ITechLayout#DEPENDENCY_4_PARENT}
    * @param lay 
    * @param flags  
    */
   public void addDependency(ILayoutable lay, int flags);

   /**
    * Returns the {@link Zer2DArea} that hosts the sizers and pozers of this {@link ILayoutable}.
    *
    * @return 
    */
   public Zer2DArea getArea();

   /**
    * Return the {@link ILayoutable} that depends on this.
    *
    * @return null if none
    */
   public ILayoutable[] getDependencies();

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
    * @return 
    * @see ITechNav
    */
   public ILayoutable getLayoutableNav(int dir);

   /**
    * Enables custom etalon types for
    * 
    * {@link ITechSizer#SIZER_OFFSET_07_ETALON_SUBTYPE1}
    * {@link ITechPozer#POS_OFFSET_02_ETALON1}
    * 
    * TODO add the layout context to help choose the etalon, provides others parameteres
    * the target layoutable etc
    * @param etalonType
    * @return
    */
   public ILayoutable getLayoutableEtalon(int etalonType);

   /**
    * The parent, null if root.
    *
    * @return 
    */
   public ILayoutable getLayoutableParent();

   /**
    * {@link ITechLayout#ETALON_1_VIEWCONTEXT}.
    * If the {@link ILayoutable} does not have the concept of a ViewContext,
    * return the parent or the root
    * @return 
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
    * {@link ILayoutDelegate}
    * @return
    */
   public ILayoutDelegate getLayoutDelegate();

   /**
    * Returns the current non computed width property.
    * Some often use properties have their own methods
    * <li> {@link ITechSizer#SIZER_PROP_00_DRAWN} -> {@link ILayoutable#getSizeDrawnWidth()}
    * <li> {@link ITechSizer#SIZER_PROP_01_PREFERRED} -> {@link ILayoutable#getSizePreferredWidth()}
    * <li> {@link ITechSizer#SIZER_PROP_02_UNIT_LOGIC}
    * <li> {@link ITechSizer#SIZER_PROP_03_FONT} -> {@link ILayoutable#getSizeFontWidth()}
    * <li> {@link ITechSizer#SIZER_PROP_05_CONTENT}
    * <li> {@link ITechSizer#SIZER_PROP_06_CONTENT_PAD}
    * <li> {@link ITechSizer#SIZER_PROP_07_CONTENT_PAD_BORDER}
    * <li> {@link ITechSizer#SIZER_PROP_10_PAD}
    * 
    * When {@link ILayoutable} is free to send a similar value when it has no understanding of the concept.
    * 
    * The {@link LayEngine} will call this method on an Etalon {@link ILayoutable} to compute sizes for other {@link ILayoutable} .
    * @param property
    * @return
    */
   public int getSizePropertyValueW(int property);

   /**
    * 
    * @param property
    * @return
    */
   public int getSizePropertyValueH(int property);

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
    * 
    * It will also invalidates the positions of dependencies
    */
   public void layoutInvalidatePosition();

   /**
    * 
    */
   public void layoutInvalidateSize();

   /**
    * 
    *
    * @return 
    */
   public boolean layoutIsValidPosition();

   /**
    * 
    *
    * @return 
    */
   public boolean layoutIsValidSize();

   /**
    * 
    *
    * @param type 
    */
   public void layoutUpdateDependencies(int type);

   /**
    * Updates only the position.
    * 
    * It might need to compute the size
    */
   //public void layoutUpdatePosition();

   /**
    * Check if position, also check the size
    */
   public void layoutUpdatePositionCheck();

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
    * 
    */
   public void layoutUpdateSizeHCheck();

   /**
    * 
    */
   public void layoutUpdateSizeWCheck();

   //#enddebug

   /**
    * Completely change the area of this {@link ILayoutable}
    * 
    * This method invalidates positions and sizes.
    *
    * @param area 
    */
   public void setArea(Zer2DArea area);

   //#mdebug
   /**
    * Debug short name.
    *
    * @return 
    */
   public String toStringName();

   /**
    * 
    */
   public void repaintLayoutable();
}
