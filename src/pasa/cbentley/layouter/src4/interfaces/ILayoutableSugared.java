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
 * 
 * @author Charles Bentley
 *
 */
public interface ILayoutableSugared extends ILayoutable {

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

   public int getHeightDrawn();

   public int getHeightPreferred();

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

}