package pasa.cbentley.layouter.src4.interfaces;

import java.awt.Container;
import java.awt.Dimension;

import pasa.cbentley.core.src4.logging.IStringable;

public interface I2DReal extends IStringable {

   public int getRealWidth();

   public int getRealHeight();

   public int getRealPrefHeight();

   public int getRealPrefWidth();

   public void setSize(int width, int height);

   public void setPreferredSize(Dimension preferredSize);

   public void setLocation(int x, int y);

   public I2DReal getParent2D();

   public void repaint();

   public String getName();

   public int getFontHeight();

   public int getFontWidth();
}
