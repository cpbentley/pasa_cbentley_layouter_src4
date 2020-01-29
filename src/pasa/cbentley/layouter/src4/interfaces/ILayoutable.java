/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.interfaces.ITechNav;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
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
    *
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
   public int getFontHeight();

   /**
    * The size in pixels of the widest letter.
    *
    * @return 
    */
   public int getFontWidth();

   /**
    * Return the etalon to be used for computing sizes.
    * 
    * Allows for dynamic etalons
    * 
    * {@link ITechLayout#ETALON_7_DELEGATE}
    * 
    * @param source
    * @return
    */
   public ILayoutable getLayoutableDelegate(ILayoutable source);

   /**
    * In the context of this {@link ILayoutable} which .
    *
    * @param id 
    * @return 
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
    * 
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
    *
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
    *
    * @return 
    */
   public int getLayoutID();

   /**
    * Can be null. then {@link LayouterCtx#getGlobalLayoutRequestListener()}
    * 
    * If both null, then an exception is thrown.
    * 
    * The width and/or height are computed by an {@link ILayoutRequestListener}
    * 
    * {@link ITechLayout#MODE_1_DELEGATE}
    * 
    * @return
    */
   public ILayoutRequestListener getLayoutRequestListener();

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
    * 
    *
    * @return 
    */
   public int getSizeBorderHeight();

   /**
    * 
    *
    * @return 
    */
   public int getSizeBorderWidth();

   /**
    * 
    *
    * @return 
    */
   public int getSizeContentHeight();

   /**
    * 
    *
    * @return 
    */
   public int getSizeContentWidth();

   /**
    * 
    * @return height drawn on screen if it would be drawn on this call
    */
   public int getSizeDrawnHeight();

   /**
    * 
    * @return
    */
   public int getSizeDrawnWidth();

   /**
    * This layoutable is a delegate and must compute the size of the {@link ILayoutable}
    * 
    * @param sizer TODO
    * @param layoutable TODO
    * @return 
    * @throws new {@link IllegalArgumentException} if layoutable is not know
    */
   public int getSizeFromDeletgateHeight(ByteObject sizer, ILayoutable layoutable);

   /**
    * 
    *
    * @return 
    */
   public int getSizeFromDeletgateWidth();

   /**
    * 
    *
    * @param layoutable 
    * @return 
    */
   public int getSizeMaxHeight(ILayoutable layoutable);

   /**
    * 
    *
    * @param layoutable 
    * @return 
    */
   public int getSizeMaxWidth(ILayoutable layoutable);

   /**
    * 
    *
    * @return 
    */
   public int getSizePaddingHeight();

   /**
    * 
    *
    * @return 
    */
   public int getSizePaddingWidth();

   /**
    * 
    *
    * @return 
    */
   public int getSizePreferredHeight();

   /**
    * 
    *
    * @return 
    */
   public int getSizePreferredWidth();

   /**
    * 
    *
    * @return 
    */
   public int getSizeUnitHeight();

   /**
    * This value depends on the type of content.
    * @return
    */
   public int getSizeUnitWidth();

   /**
    * 
    *
    * @return 
    */
   public int getWidthDelegate();

   /**
    * 
    *
    * @return 
    */
   public int getWidthDrawn();

   /**
    * 
    *
    * @return 
    */
   public int getWidthFont();

   /**
    * Sugar for {@link ILayoutable#getSizePreferredWidth()}.
    *
    * @return 
    */
   public int getWidthPreferred();

   /**
    * Sugar for {@link ILayoutable#getSizeUnitWidth()}.
    *
    * @return 
    */
   public int getWidthUnit();

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
   //public void layoutUpdateSize();

   /**
    * When an etalon is choosen, the engine must check
    * if it has been sized
    * Check if size, compute
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
