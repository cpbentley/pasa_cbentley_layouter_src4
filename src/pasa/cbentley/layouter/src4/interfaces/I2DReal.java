/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.interfaces;


import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Interface to the real component.
 * @author Charles Bentley
 *
 */
public interface I2DReal extends IStringable {

   public int getRealWidth();

   public int getRealHeight();

   public int getRealPrefHeight();

   public int getRealPrefWidth();

   public void setSize(int width, int height);

   public void setPreferredSize(int width, int height);

   public void setLocation(int x, int y);

   public I2DReal getParent2D();

   public void repaint();

   public String getName();

   public int getFontHeight();

   public int getFontWidth();
}
