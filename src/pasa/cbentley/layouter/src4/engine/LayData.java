package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechPozer;

/**
 * Separates the data from layouting
 * 
 * Provides methods to setup the 2d area definition.
 * 
 * @author Charles Bentley
 *
 */
public class LayData implements IStringable {

   protected final LayouterCtx lac;

   private Zer2DArea           area;

   public LayData(LayouterCtx lac) {
      this.lac = lac;
      area = new Zer2DArea(lac);
   }

   private void layPoz(ILayoutable lay, ByteObject pozer) {
      pozer.set1(ITechPozer.POS_OFFSET_02_ETALON1, ITechPozer.POS_ETALON_6_LAYOUTABLE);
      ByteObjectLayoutable boLayoutable = new ByteObjectLayoutable(lac.getBOC(), lay);
      pozer.addByteObject(boLayoutable);
   }

   public Zer2DArea getArea() {
      return area;
   }

   public void layPozBotToBotOf(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerBotToBot();
      layPozYBot(lay, pozerY);
   }

   public void layPozBotToBotOfParent() {
      layPozBotToBotOf(null);
   }

   public void layPozBotToMidOf(ILayoutable lay) {
      //the size is the distance between 2 pozers, one of which is implicit
      ByteObject pozerY = lac.getPozerFactory().getPozerBotToMid();
      layPozYBot(lay, pozerY);
   }

   public void layPozBotToMidOfParent() {
      layPozBotToMidOf(null);
   }

   public void layPozBotToTopOf(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerBotToTop();
      layPozYBot(lay, pozerY);
   }

   public void layPozBotToTopOfParent() {
      layPozBotToTopOf(null);
   }

   public void layPozCorner1ToCorner1Of(ILayoutable lay) {
      layPozTopToTopOf(lay);
      layPozStartToStartOf(lay);
   }

   public void setPozerCorner1(Zer2DPozer pozerCorner1) {
      setPozerXStart(pozerCorner1.getPozerX());
      setPozerYTop(pozerCorner1.getPozerY());
   }

   /**
    * Link the top right corner to
    * @param pozerX
    * @param pozerY
    */
   public void setPozerCorner2(ByteObject pozerX, ByteObject pozerY) {
      setPozerXEnd(pozerX);
      setPozerYTop(pozerY);
   }

   public void setPozerCorner2(Zer2DPozer pozerCorner2) {
      setPozerXEnd(pozerCorner2.getPozerX());
      setPozerYTop(pozerCorner2.getPozerY());
   }

   public void setPozerCorner3(Zer2DPozer pozerCorner3) {
      setPozerXEnd(pozerCorner3.getPozerX());
      setPozerYBot(pozerCorner3.getPozerY());
   }

   public void setPozerCorner4(Zer2DPozer pozerCorner4) {
      setPozerXStart(pozerCorner4.getPozerX());
      setPozerYBot(pozerCorner4.getPozerY());
   }

   public void setPozerXEnd(ByteObject pozerX) {
      area.setPozerXEnd(pozerX);
   }

   public void setPozerXStart(ByteObject pozerX) {
      area.setPozerXStart(pozerX);
   }

   public void setPozerYBot(ByteObject pozerY) {
      area.setPozerYBot(pozerY);
   }

   public void setPozerYTop(ByteObject pozerY) {
      area.setPozerYTop(pozerY);
   }

   public void setSizer(Zer2DSizer sizer) {
      this.area.setSizer(sizer);
   }
   
   public void setArea(Zer2DArea area) {
      this.area = area;
   }

   public void setSizerH(ByteObject sizerH) {
      area.setSizerH(sizerH);
   }

   /**
    * 
    */
   public void layFullViewcxt() {
     ByteObject pozer0 = lac.getPozerFactory().getPozerAtPixel0();
     area.setPozerXStart(pozer0);
     area.setPozerYTop(pozer0);
     ByteObject sizerRatio100ViewCtx = lac.getSizerFactory().getSizerRatio100ViewCtx(100);
     area.setSizerWH(sizerRatio100ViewCtx,sizerRatio100ViewCtx);
   }

   public void setSizerW(ByteObject sizerW) {
      area.setSizerW(sizerW);
   }

   public void layPozCorner1ToCorner1OfParent() {
      layPozCorner1ToCorner1Of(null);
   }

   public void layPozCorner1ToCorner2Of(ILayoutable lay) {
      layPozTopToTopOf(lay);
      layPozStartToEndOf(lay);
   }

   public void layPozCorner1ToCorner2OfParent() {
      layPozCorner1ToCorner2Of(null);
   }

   public void layPozCorner1ToCorner3Of(ILayoutable lay) {
      layPozTopToBotOf(lay);
      layPozStartToEndOf(lay);
   }

   public void layPozCorner1ToCorner3OfParent() {
      layPozCorner1ToCorner3Of(null);
   }

   public void layPozCorner1ToCorner4Of(ILayoutable lay) {
      layPozTopToBotOf(lay);
      layPozStartToStartOf(lay);
   }

   public void layPozCorner1ToCorner4OfParent() {
      layPozCorner1ToCorner4Of(null);
   }

   public void layPozCorner3ToCorner1Of(ILayoutable lay) {
      layPozBotToTopOf(lay);
      layPozEndToStartOf(lay);
   }

   public void layPozCorner3ToCorner1OfParent() {
      layPozCorner3ToCorner1Of(null);
   }

   public void layPozCorner3ToCorner2Of(ILayoutable lay) {
      layPozBotToTopOf(lay);
      layPozEndToEndOf(lay);
   }

   public void layPozCorner3ToCorner2OfParent() {
      layPozCorner3ToCorner2Of(null);
   }

   public void layPozCorner3ToCorner3Of(ILayoutable lay) {
      layPozBotToBotOf(lay);
      layPozEndToEndOf(lay);
   }

   public void layPozCorner3ToCorner3OfParent() {
      layPozCorner3ToCorner3Of(null);
   }

   public void layPozCorner3ToCorner4Of(ILayoutable lay) {
      layPozBotToBotOf(lay);
      layPozEndToStartOf(lay);
   }

   public void layPozCorner3ToCorner4OfParent() {
      layPozCorner3ToCorner4Of(null);
   }

   /**
    * Defines 2 pozers
    */
   public void layPozCornerTopLeftOfParent() {
      ByteObject pozerY = lac.getPozerFactory().getPozerTopToTop();
      setPozerYTop(pozerY);

      ByteObject pozerX = lac.getPozerFactory().getPozerStartToStart();
      setPozerXStart(pozerX);
   }

   public void layPozEndToEndOf(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerEndToEnd();
      layPozXEnd(lay, pozerX);
   }

   public void layPozEndToEndParent() {
      layPozEndToEndOf(null);
   }

   public void layPozStartToCenterOf(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerStartToCenter();
      layPozXEnd(lay, pozerX);
   }

   public void layPozEndToCenterOf(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerEndToCenter();
      layPozXEnd(lay, pozerX);
   }

   public void layPozEndToStartOf(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerEndToStart();
      layPozXEnd(lay, pozerX);
   }

   public void layPozEndToStartOfParent() {
      layPozEndToStartOf(null);
   }

   public void layPozMidXToEndOf(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerMidToEnd();
      layPozX(lay, pozerX);
   }

   public void layPozMidXToMidOf(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerCenterToCenter();
      layPozX(lay, pozerX);
   }

   public void layPozMidXToMidOfParent() {
      layPozMidXToMidOf(null);
   }

   public void layPozMidXToStartOf(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerMidToStart();
      layPozX(lay, pozerX);
   }

   public void layPozMidYToBotOf(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerMidToEnd();
      layPozY(lay, pozerX);
   }

   public void layPozMidYToTopOf(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerMidToTop();
      layPozY(lay, pozerY);
   }

   private void layPozParent(ByteObject pozer) {
      pozer.set1(ITechPozer.POS_OFFSET_02_ETALON1, ITechPozer.POS_ETALON_1_PARENT);
   }

   public void layPozStartToEndOf(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerStartToEnd();
      layPozXStart(lay, pozerX);
   }

   public void layPozStartToEndParent() {
      layPozStartToEndOf(null);
   }

   public void layPozStartToStartOf(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerStartToStart();
      layPozXStart(lay, pozerX);
   }

   public void layPozStartToStartOfParent() {
      layPozStartToStartOf(null);
   }

   public void layPozTopToBotOf(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerTopToBottom();
      layPozYTop(lay, pozerY);
   }

   public void layPozTopToBotOfParent() {
      layPozTopToBotOf(null);
   }

   public void layPozTopToMidOf(ILayoutable lay) {
      //define the y pos
      ByteObject pozerY = lac.getPozerFactory().getPozerTopToMid();
      layPozYTop(lay, pozerY);
   }

   public void layPozTopToTopOf(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerTopToTop();
      layPozYTop(lay, pozerY);
   }

   public void layPozTopToTopOfParent() {
      layPozTopToTopOf(null);
   }

   /**
    * 
    * @param lay
    * @param pozerX
    */
   private void layPozX(ILayoutable lay, ByteObject pozerX) {
      layPozXStart(lay, pozerX);
   }

   private void layPozXEnd(ILayoutable lay, ByteObject pozerX) {
      if (lay == null) {
         //use parent
         layPozParent(pozerX);
      } else {
         layPoz(lay, pozerX);
      }
      area.setPozerXEnd(pozerX);
   }

   private void layPozXStart(ILayoutable lay, ByteObject pozerX) {
      if (lay == null) {
         //use parent
         layPozParent(pozerX);
      } else {
         layPoz(lay, pozerX);
      }
      area.setPozerXStart(pozerX);
   }

   private void layPozY(ILayoutable lay, ByteObject pozerX) {
      layPozYTop(lay, pozerX);
   }

   private void layPozYBot(ILayoutable lay, ByteObject pozerY) {
      if (lay == null) {
         //use parent
         layPozParent(pozerY);
      } else {
         layPoz(lay, pozerY);
      }
      area.setPozerYBot(pozerY);
   }

   private void layPozYTop(ILayoutable lay, ByteObject pozerY) {
      if (lay == null) {
         //use parent
         layPozParent(pozerY);
      } else {
         layPoz(lay, pozerY);
      }
      area.setPozerYTop(pozerY);
   }

   public void laySizePreferred() {
      area.setSizerH(lac.getFactorySizer().getSizerPrefLazy());
      area.setSizerW(lac.getFactorySizer().getSizerPrefLazy());
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "LayData");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LayData");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return lac.getUCtx();
   }

   //#enddebug

}
