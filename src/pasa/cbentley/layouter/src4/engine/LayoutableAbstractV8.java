/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ObjectLC;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

public abstract class LayoutableAbstractV8 extends ObjectLC implements ILayoutable {

   private ILayoutDelegate    delegate;

   protected LayouterEngineV8 engine;

   private ILayoutable        parent;

   public LayoutableAbstractV8(LayouterCtx lac) {
      super(lac);
      engine = new LayouterEngineV8(lac, this);
   }

   public void setDependency(ILayoutable lay, int flags) {
      engine.setDependency(lay, flags);
   }

   public Zer2DArea getArea() {
      return engine.getArea();
   }

   public ILayoutable[] getDependencies() {
      return engine.getDependencies();
   }

   public void setSizerW(ByteObject sizer) {
      engine.getLay().setSizerW(sizer);
   }

   public void setSizerH(ByteObject sizer) {
      engine.getLay().setSizerH(sizer);
   }

   /**
    * Understanding that pozer goes on XStart
    * @param pozer
    */
   public void setPozerX(ByteObject pozer) {
      engine.getLay().setPozerXStart(pozer);
   }

   /**
    * Understanding that pozer goes on YTop
    * @param pozer
    */
   public void setPozerY(ByteObject pozer) {
      engine.getLay().setPozerYTop(pozer);
   }

   public int getSizeFontHeight() {
      return engine.getSizeFontHeight();
   }

   public int getSizeFontWidth() {
      return engine.getSizeFontWidth();
   }

   public void setSizeFontH(int sizeFontH) {
      engine.setSizeFontH(sizeFontH);
   }

   public void setSizeFontW(int sizeFontW) {
      engine.setSizeFontW(sizeFontW);
   }

   public void setSizeContentW(int contentW) {
      engine.getProperties().setContentW(contentW);
   }

   public void setSizeContentH(int contentH) {
      engine.getProperties().setContentH(contentH);
   }

   public Area2DConfigurator layout() {
      return engine.getLay();
   }

   public Area2DConfigurator lay() {
      return engine.getLay();
   }

   public Area2DConfigurator getLay() {
      return engine.getLay();
   }

   public ILayoutable getLayoutableDelegate(ILayoutable source) {
      // TODO Auto-generated method stub
      return null;
   }

   public ILayoutable getLayoutableEtalon(int etalonType) {
      // TODO Auto-generated method stub
      return null;
   }

   public ILayoutable getLayoutableID(int id) {
      // TODO Auto-generated method stub
      return null;
   }

   public ILayoutable getLayoutableNav(int dir) {
      // TODO Auto-generated method stub
      return null;
   }

   public ILayoutable getLayoutableParent() {
      return parent;
   }

   public ILayoutable getLayoutableViewContext() {
      // TODO Auto-generated method stub
      return null;
   }

   public ILayoutable getLayoutableViewPort() {
      // TODO Auto-generated method stub
      return null;
   }

   public ILayoutDelegate getLayoutDelegate() {
      return delegate;
   }

   public int getLayoutID() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getPozeX() {
      return engine.getPozeX();
   }

   public int getPozeXComputed() {
      return engine.getPozeXComputed();
   }

   public int getPozeY() {
      return engine.getPozeY();
   }

   public int getPozeYComputed() {
      return engine.getPozeYComputed();
   }

   public int getSizeDrawnHeight() {
      return engine.getSizeH();
   }

   public int getSizeDrawnWidth() {
      return engine.getSizeW();
   }

   public int getSizePreferredHeight() {
      return engine.getSizePH();
   }

   public int getSizePreferredWidth() {
      return engine.getSizePW();
   }

   public int getSizeContentHeight() {
      return engine.getProperties().getContentH();
   }

   public int getSizeContentWidth() {
      return engine.getProperties().getContentW();
   }

   public int getSizePropertyValueH(int property) {
      switch (property) {
         case ITechLayout.SIZER_PROP_00_DRAWN:
            return getSizeDrawnHeight();
         case ITechLayout.SIZER_PROP_01_PREFERRED:
            return getSizePreferredHeight();
         case ITechLayout.SIZER_PROP_03_FONT:
            return getSizeFontHeight();
         case ITechLayout.SIZER_PROP_05_CONTENT:
            return getSizeContentHeight();
         default:
            throw new IllegalArgumentException();
      }
   }

   public int getSizePropertyValueW(int property) {
      switch (property) {
         case ITechLayout.SIZER_PROP_00_DRAWN:
            return getSizeDrawnWidth();
         case ITechLayout.SIZER_PROP_01_PREFERRED:
            return getSizePreferredWidth();
         case ITechLayout.SIZER_PROP_03_FONT:
            return getSizeFontWidth();
         case ITechLayout.SIZER_PROP_05_CONTENT:
            return getSizeContentWidth();
         default:
            throw new IllegalArgumentException();
      }
   }

   public void setX(int x) {
      engine.setX(x);
      engine.setManualOverrideXTrue();
   }

   public void setY(int y) {
      engine.setY(y);
      engine.setManualOverrideYTrue();
   }

   public void setH(int h) {
      engine.setH(h);
      engine.setManualOverrideHTrue();
   }

   public void setPw(int pw) {
      engine.setPw(pw);
   }

   public void setPh(int ph) {
      engine.setPh(ph);
   }

   public void setW(int w) {
      engine.setW(w);
      engine.setManualOverrideWTrue();
   }

   public void layoutInvalidate() {
      engine.layoutInvalidate();
   }

   public void layoutInvalidatePosition() {
      engine.layoutInvalidatePosition();

   }

   public void layoutInvalidateSize() {
      engine.layoutInvalidateSize();
   }

   public boolean layoutIsValidPosition() {
      return engine.layoutIsValidPosition();
   }

   public boolean layoutIsValidSize() {
      return engine.layoutIsValidSize();
   }

   public void layoutUpdateDependencies(int type) {
      engine.layoutUpdateDependencies(type);
   }

   public void layoutUpdatePositionCheck() {
      engine.layoutUpdatePositionCheck();
   }

   public void layoutUpdatePositionXCheck() {
      engine.layoutUpdatePositionXCheck();

   }

   public void layoutUpdatePositionYCheck() {
      engine.layoutUpdatePositionYCheck();
   }

   public void layoutUpdateSizeCheck() {
      engine.layoutUpdateSizeCheck();

   }

   public void layoutUpdateSizeHCheck() {
      engine.layoutUpdateSizeHCheck();
   }

   public void layoutUpdateSizeWCheck() {
      engine.layoutUpdateSizeWCheck();
   }

   public void repaintLayoutable() {
      // TODO Auto-generated method stub

   }

   public void setArea(Zer2DArea area) {
      engine.getLay().setArea(area);
   }

   public void setLayoutDelegate(ILayoutDelegate delegate) {
      this.delegate = delegate;
   }

   public void setParent(ILayoutable parent) {
      this.parent = parent;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, LayoutableAbstractV8.class);
      toStringPrivate(dc);
      super.toString(dc);
      dc.nlLvl(engine);
      dc.nlLvl(delegate, "LayoutDelegate");
   }

   private void toStringPrivate(Dctx dc) {
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayoutableAbstractV8.class);
      toStringPrivate(dc);
   }

   //#enddebug

}
