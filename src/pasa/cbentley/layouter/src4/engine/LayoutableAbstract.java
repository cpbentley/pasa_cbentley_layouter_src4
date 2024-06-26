/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.ctx.ObjectLC;
import pasa.cbentley.layouter.src4.interfaces.ILayoutDelegate;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechLayout;

public abstract class LayoutableAbstract extends ObjectLC implements ILayoutable {

   private ILayoutDelegate      delegate;

   protected LayouterEngineRead engine;

   private ILayoutable          parent;

   public LayoutableAbstract(LayouterCtx lac) {
      super(lac);
      engine = new LayouterEngineRead(lac, this);
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

   public int getSizeFontHeight() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getSizeFontWidth() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getSizePreferredHeight() {
      return engine.getSizePH();
   }

   public int getSizePreferredWidth() {
      return engine.getSizePW();
   }

   public int getSizePropertyValueH(int property) {
      switch (property) {
         case ITechLayout.SIZER_PROP_00_DRAWN:
            return getSizeDrawnHeight();
         case ITechLayout.SIZER_PROP_01_PREFERRED:
            return getSizePreferredHeight();
         case ITechLayout.SIZER_PROP_03_FONT:
            return getSizeFontHeight();
         default:
            break;
      }
      return 0;
   }

   public int getSizePropertyValueW(int property) {
      switch (property) {
         case ITechLayout.SIZER_PROP_00_DRAWN:
            return getSizeDrawnWidth();
         case ITechLayout.SIZER_PROP_01_PREFERRED:
            return getSizePreferredWidth();
         case ITechLayout.SIZER_PROP_03_FONT:
            return getSizeFontWidth();
         default:
            break;
      }
      return 0;
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
      dc.root(this, LayoutableAbstract.class, 237);
      toStringPrivate(dc);
      super.toString(dc);
      dc.nlLvl(engine,"engine of LayoutableAbstract");
      dc.nlLvl(delegate, "delegate of LayoutableAbstract");
   }

   private void toStringPrivate(Dctx dc) {
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayoutableAbstract.class);
      toStringPrivate(dc);
   }

   //#enddebug

}
