package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ObjectLC;

public class LayoutablePropertyHolder extends ObjectLC {

   public LayoutablePropertyHolder(LayouterCtx lac) {
      super(lac);
   }

   private int contentH;

   private int contentW;

   private int contentX;

   private int contentY;

   public int getContentX() {
      return contentX;
   }

   public void setContentX(int contentX) {
      this.contentX = contentX;
   }

   public int getContentY() {
      return contentY;
   }

   public void setContentY(int contentY) {
      this.contentY = contentY;
   }

   private int fontW;

   private int fontH;

   public int getContentH() {
      return contentH;
   }

   public void setContentH(int contentH) {
      this.contentH = contentH;
   }

   public int getContentW() {
      return contentW;
   }

   public void setContentW(int contentW) {
      this.contentW = contentW;
   }

   public int getFontW() {
      return fontW;
   }

   public void setFontW(int fontW) {
      this.fontW = fontW;
   }

   public int getFontH() {
      return fontH;
   }

   public void setFontH(int fontH) {
      this.fontH = fontH;
   }

}
