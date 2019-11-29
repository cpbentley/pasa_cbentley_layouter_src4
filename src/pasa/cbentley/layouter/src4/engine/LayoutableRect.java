package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutRequestListener;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

public class LayoutableRect implements ILayoutable {

   private int                 h;

   protected final LayouterCtx lc;

   private int                 w;

   private int                 x;

   private int                 y;

   public LayoutableRect(LayouterCtx lc) {
      this.lc = lc;
   }

   public LayoutableRect(LayouterCtx lc, int w, int h) {
      this.lc = lc;
      set(w, h);
   }

   public void addDependency(ILayoutable lay, int flags) {

   }

   public void repaintLayoutable() {
   }

   public Zer2DArea getArea() {
      return null;
   }

   public ILayoutable[] getDependencies() {
      return null;
   }

   public int getFontHeight() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getFontWidth() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getH() {
      return h;
   }

   public ILayoutable getLayoutableDelegate(ILayoutable source) {
      // TODO Auto-generated method stub
      return null;
   }

   public ILayoutable getLayoutableID(int id) {
      // TODO Auto-generated method stub
      return null;
   }

   public ILayoutable getLayoutableNav(int dir) {
      return null;
   }

   public ILayoutable getLayoutableParent() {
      return null;
   }

   public ILayoutable getLayoutableViewContext() {
      return null;
   }

   public ILayoutable getLayoutableViewPort() {
      return null;
   }

   public int getLayoutID() {
      return 0;
   }

   public ILayoutRequestListener getLayoutRequestListener() {
      return null;
   }

   public int getPozeX() {
      return x;
   }

   public int getPozeXComputed() {
      return x;
   }

   public int getPozeY() {
      return y;
   }

   public int getPozeYComputed() {
      return y;
   }

   public int getSizeDrawnHeight() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getSizeDrawnWidth() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getSizeEtalonH(ByteObject sizer) {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getSizeEtalonW(ByteObject sizer) {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getSizePaddingWidth() {
      return w;
   }

   public int getSizePaddingHeight() {
      return h;
   }

   public int getSizeBorderWidth() {
      return w;
   }

   public int getSizeBorderHeight() {
      return h;
   }

   public int getSizeContentWidth() {
      return w;
   }

   public int getSizeContentHeight() {
      return h;
   }

   public int getSizeFromDeletgateHeight() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getSizeFromDeletgateWidth() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getSizePreferredHeight() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getSizePreferredWidth() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getSizeUnitHeight() {
      // TODO Auto-generated method stub
      return 0;
   }

   public int getSizeUnitWidth() {
      return w;
   }

   public int getW() {
      return w;
   }

   public int getWidthDelegate() {
      return w;
   }

   public int getWidthDrawn() {
      return w;
   }

   public int getWidthFont() {
      return 0;
   }

   public int getWidthPreferred() {
      return w;
   }

   public int getWidthUnit() {
      return w;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public void layoutInvalidate() {
   }

   public void layoutInvalidatePosition() {
   }

   public void layoutInvalidateSize() {
   }

   public boolean layoutIsValidPosition() {
      return true;
   }

   public boolean layoutIsValidSize() {
      return true;
   }

   public void layoutUpdateDependencies(int type) {

   }

   public void layoutUpdatePosition() {
   }

   public void layoutUpdatePositionCheck() {
   }

   public void layoutUpdateSize() {
   }

   public void layoutUpdateSizeCheck() {

   }

   public void set(int w, int h) {
      this.set(0, 0, w, h);
   }

   public void set(int x, int y, int w, int h) {
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
   }

   public void setArea(Zer2DArea area) {
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "LayoutableRect");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LayoutableRect");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return lc.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("x", x);
      dc.appendVarWithSpace("y", y);
      dc.appendVarWithSpace("w", w);
      dc.appendVarWithSpace("h", h);
   }

   public String toStringName() {
      return "rect " + x + "," + y + "|" + w + "," + h;
   }

   public void layoutUpdatePositionXCheck() {

   }

   public void layoutUpdatePositionYCheck() {

   }

   public void layoutUpdateSizeHCheck() {

   }

   public void layoutUpdateSizeWCheck() {

   }

   public int getSizeMaxHeight(ILayoutable layoutable) {
      return h;
   }

   public int getSizeMaxWidth(ILayoutable layoutable) {
      return w;
   }

   //#enddebug

}
