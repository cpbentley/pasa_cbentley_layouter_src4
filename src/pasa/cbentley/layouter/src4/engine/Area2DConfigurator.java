/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
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
 * Configurator for the {@link Zer2DArea} class.
 * 
 * Separates the data from layouting
 * 
 * Provides methods to setup the 2d area definition.
 * 
 * @author Charles Bentley
 *
 */
public class Area2DConfigurator implements IStringable {

   private Zer2DArea           area;

   protected final LayouterCtx lac;

   public Area2DConfigurator(LayouterCtx lac) {
      this.lac = lac;
      area = new Zer2DArea(lac);
   }

   public Area2DConfigurator(LayouterCtx lac, Zer2DArea area) {
      this.lac = lac;
      this.area = area;
   }

   public Zer2DArea getArea() {
      return area;
   }

   /**
    * Takes the whole area defined by its ViewContext
    */
   public void layFullViewContext() {
      ByteObject pozer0 = lac.getPozerFactory().getPozerAtPixel0();
      area.setPozerXStart(pozer0);
      area.setPozerYTop(pozer0);
      ByteObject sizerRatio100ViewCtx = lac.getSizerFactory().getSizerRatio100ViewCtx(100);
      area.setSizerWH(sizerRatio100ViewCtx, sizerRatio100ViewCtx);
   }

   /**
    * TODO move to factory
    * @param lay
    * @param pozer
    */
   private void layPoz(ILayoutable lay, ByteObject pozer) {
      pozer.set1(ITechPozer.POS_OFFSET_02_ETALON1, ITechPozer.POS_ETALON_6_LAYOUTABLE);
      ByteObjectLayoutable boLayoutable = new ByteObjectLayoutable(lac.getBOC(), lay);
      pozer.addByteObject(boLayoutable);
   }

   public void layPoz_BotToBot_Of(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerBotToBot();
      layPozYBot(lay, pozerY);
   }

   public void layPoz_BotToBot_Of_Margin(ILayoutable lay, ByteObject sizer) {
      layPoz_BotToBot_Of_With(lay, ITechPozer.POS_FUN_1_AWAY_CENTER, sizer);
   }

   public void layPoz_BotToBot_Of_Padding(ILayoutable lay, ByteObject sizer) {
      layPoz_BotToBot_Of_With(lay, ITechPozer.POS_FUN_0_TOWARDS_CENTER, sizer);
   }

   public void layPoz_BotToBot_Of_With(ILayoutable lay, int fun, ByteObject sizer) {
      ByteObject pozerX = lac.getPozerFactory().getPozerBotToBot();
      if (sizer != null) {
         lac.getPozerFactory().setPoserWithSizer(pozerX, fun, sizer);
      }
      layPozYBot(lay, pozerX);
   }

   public void layPoz_BotToBot_OfParent() {
      layPoz_BotToBot_Of(null);
   }

   public void layPoz_BotToMid_Of(ILayoutable lay) {
      //the size is the distance between 2 pozers, one of which is implicit
      ByteObject pozerY = lac.getPozerFactory().getPozerBotToMid();
      layPozYBot(lay, pozerY);
   }

   public void layPoz_BotToMid_OfParent() {
      layPoz_BotToMid_Of(null);
   }

   public void layPoz_BotToTop_Of(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerBotToTop();
      layPozYBot(lay, pozerY);
   }

   public void layPoz_BotToTop_OfParent() {
      layPoz_BotToTop_Of(null);
   }

   /**
    * Anchors Corner1 of configured {@link ILayoutable} with Corner1 of parameter {@link ILayoutable} .
    * <li>Corner1=TopLeft
    * <li>Corner2=TopRight
    * <li>Corner3=BotRight
    * <li>Corner4=BotLeft
    * 
    * @param lay
    */
   public void layPoz_Corner_1To1_Of(ILayoutable lay) {
      layPoz_TopToTop_Of(lay);
      layPoz_StartToStart_Of(lay);
   }

   public void layPoz_Corner_1To1_OfParent() {
      layPoz_Corner_1To1_Of(null);
   }

   public void layPoz_Corner_1To2_Of(ILayoutable lay) {
      layPoz_TopToTop_Of(lay);
      layPoz_StartToEnd_Of(lay);
   }

   public void layPoz_Corner_1To2_OfParent() {
      layPoz_Corner_1To2_Of(null);
   }

   public void layPoz_Corner_1To3_Of(ILayoutable lay) {
      layPoz_TopToBot_Of(lay);
      layPoz_StartToEnd_Of(lay);
   }

   public void layPoz_Corner_1To3_OfParent() {
      layPoz_Corner_1To3_Of(null);
   }

   public void layPoz_Corner_1To4_Of(ILayoutable lay) {
      layPoz_TopToBot_Of(lay);
      layPoz_StartToStart_Of(lay);
   }

   public void layPoz_Corner_1To4_OfParent() {
      layPoz_Corner_2To4_Of(null);
   }

   /**
    * TopRight of Pozee to TopLeft of {@link ILayoutable} parameter.
    * @param lay
    */
   public void layPoz_Corner_2To1_Of(ILayoutable lay) {
      layPoz_TopToTop_Of(lay);
      layPoz_EndToStart_Of(lay);
   }

   public void layPoz_Corner_2To1_OfParent() {
      layPoz_Corner_2To1_Of(null);
   }

   /**
    * TopRight of Pozee to TopRight of {@link ILayoutable} parameter.
    * @param lay
    */
   public void layPoz_Corner_2To2_Of(ILayoutable lay) {
      layPoz_TopToTop_Of(lay);
      layPoz_EndToEnd_Of(lay);
   }

   public void layPoz_Corner_2To2_OfParent() {
      layPoz_Corner_2To2_Of(null);
   }

   /**
    * TopRight of Pozee to BotRight of {@link ILayoutable} parameter.
    * @param lay
    */
   public void layPoz_Corner_2To3_Of(ILayoutable lay) {
      layPoz_TopToBot_Of(lay);
      layPoz_EndToEnd_Of(lay);
   }

   public void layPoz_Corner_2To3_OfParent() {
      layPoz_Corner_2To3_Of(null);
   }

   /**
    * TopRight of Pozee to BotLeft of {@link ILayoutable} parameter.
    * @param lay
    */
   public void layPoz_Corner_2To4_Of(ILayoutable lay) {
      layPoz_TopToBot_Of(lay);
      layPoz_EndToStart_Of(lay);
   }

   public void layPoz_Corner_2To4_OfParent() {
      layPoz_Corner_2To4_Of(null);
   }

   public void layPoz_Corner_3To1_Of(ILayoutable lay) {
      layPoz_BotToTop_Of(lay);
      layPoz_EndToStart_Of(lay);
   }

   public void layPoz_Corner_3To1_OfParent() {
      layPoz_Corner_3To1_Of(null);
   }

   public void layPoz_Corner_3To2_Of(ILayoutable lay) {
      layPoz_BotToTop_Of(lay);
      layPoz_EndToEnd_Of(lay);
   }

   public void layPoz_Corner_3To2_OfParent() {
      layPoz_Corner_3To2_Of(null);
   }

   public void layPoz_Corner_3To3_Of(ILayoutable lay) {
      layPoz_BotToBot_Of(lay);
      layPoz_EndToEnd_Of(lay);
   }

   public void layPoz_Corner_3To3_OfParent() {
      layPoz_Corner_3To3_Of(null);
   }

   public void layPoz_Corner_3To4_Of(ILayoutable lay) {
      layPoz_BotToBot_Of(lay);
      layPoz_EndToStart_Of(lay);
   }

   public void layPoz_Corner_3To4_OfParent() {
      layPoz_Corner_3To4_Of(null);
   }

   /**
    * BotLeft of Pozee to TopLeft of {@link ILayoutable} parameter.
    * @param lay
    */
   public void layPoz_Corner_4To1_Of(ILayoutable lay) {
      layPoz_BotToTop_Of(lay);
      layPoz_EndToStart_Of(lay);
   }

   public void layPoz_Corner_4To1_OfParent() {
      layPoz_Corner_4To1_Of(null);
   }

   /**
    * BotLeft of Pozee to TopRight of {@link ILayoutable} parameter.
    * @param lay
    */
   public void layPoz_Corner_4To2_Of(ILayoutable lay) {
      layPoz_BotToTop_Of(lay);
      layPoz_StartToEnd_Of(lay);
   }

   public void layPoz_Corner_4To2_OfParent() {
      layPoz_Corner_4To2_Of(null);
   }

   /**
    * BotLeft of Pozee to BotRight of {@link ILayoutable} parameter.
    * @param lay
    */
   public void layPoz_Corner_4To3_Of(ILayoutable lay) {
      layPoz_BotToBot_Of(lay);
      layPoz_StartToEnd_Of(lay);
   }

   public void layPoz_Corner_4To3_OfParent() {
      layPoz_Corner_4To3_Of(null);
   }

   /**
    * BotLeft of Pozee to BotLeft of {@link ILayoutable} parameter.
    * @param lay
    */
   public void layPoz_Corner_4To4_Of(ILayoutable lay) {
      layPoz_BotToBot_Of(lay);
      layPoz_StartToStart_Of(lay);
   }

   public void layPoz_Corner_4To4_OfParent() {
      layPoz_Corner_4To4_Of(null);
   }

   public void layPoz_Corner_Of(int cornerPozee, int cornerLay, ILayoutable lay) {
      if (cornerPozee == 1) {
         if (cornerLay == 1) {
            layPoz_Corner_1To1_Of(lay);
         } else if (cornerLay == 2) {
            layPoz_Corner_1To2_Of(lay);
         } else if (cornerLay == 3) {
            layPoz_Corner_1To3_Of(lay);
         } else if (cornerLay == 4) {
            layPoz_Corner_1To4_Of(lay);
         } else {
            throw new IllegalArgumentException();
         }
      } else if (cornerPozee == 2) {
         if (cornerLay == 1) {
            layPoz_Corner_2To1_Of(lay);
         } else if (cornerLay == 2) {
            layPoz_Corner_2To2_Of(lay);
         } else if (cornerLay == 3) {
            layPoz_Corner_2To3_Of(lay);
         } else if (cornerLay == 4) {
            layPoz_Corner_2To4_Of(lay);
         } else {
            throw new IllegalArgumentException();
         }
      } else if (cornerPozee == 3) {
         if (cornerLay == 1) {
            layPoz_Corner_3To1_Of(lay);
         } else if (cornerLay == 2) {
            layPoz_Corner_3To2_Of(lay);
         } else if (cornerLay == 3) {
            layPoz_Corner_3To3_Of(lay);
         } else if (cornerLay == 4) {
            layPoz_Corner_3To4_Of(lay);
         } else {
            throw new IllegalArgumentException();
         }
      } else if (cornerPozee == 4) {
         if (cornerLay == 1) {
            layPoz_Corner_4To1_Of(lay);
         } else if (cornerLay == 2) {
            layPoz_Corner_4To2_Of(lay);
         } else if (cornerLay == 3) {
            layPoz_Corner_4To3_Of(lay);
         } else if (cornerLay == 4) {
            layPoz_Corner_4To4_Of(lay);
         } else {
            throw new IllegalArgumentException();
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public void layPoz_Corner_OfParent(int cornerPozee, int cornerLay) {
      layPoz_Corner_Of(cornerPozee, cornerLay, null);
   }

   public void layPoz_EndToCenter_Of(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerEndToCenter();
      layPozXEnd(lay, pozerX);
   }

   public void layPoz_EndToEnd_Of(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerEndToEnd();
      layPozXEnd(lay, pozerX);
   }

   public void layPoz_EndToEnd_Of_Margin(ILayoutable lay, ByteObject sizer) {
      layPoz_EndToEnd_Of_With(lay, ITechPozer.POS_FUN_1_AWAY_CENTER, sizer);
   }

   public void layPoz_EndToEnd_Of_Padding(ILayoutable lay, ByteObject sizer) {
      layPoz_EndToEnd_Of_With(lay, ITechPozer.POS_FUN_0_TOWARDS_CENTER, sizer);
   }

   /**
    * <li>{@link ITechPozer#POS_FUN_0_TOWARDS_CENTER} XX towards the center of this object, in effect negative margin
    * <li>{@link ITechPozer#POS_FUN_1_AWAY_CENTER} XX away the center of this object, in effect postive margin
    * @param lay
    * @param fun {@link ITechPozer#POS_OFFSET_10_SIZER_FUN1}
    * @param sizer
    */
   public void layPoz_EndToEnd_Of_With(ILayoutable lay, int fun, ByteObject sizer) {
      ByteObject pozerX = lac.getPozerFactory().getPozerEndToEnd();
      if (sizer != null) {
         lac.getPozerFactory().setPoserWithSizer(pozerX, fun, sizer);
      }
      layPozXEnd(lay, pozerX);
   }

   public void layPoz_EndToEnd_Parent() {
      layPoz_EndToEnd_Of(null);
   }

   public void layPoz_EndToStart_Of(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerEndToStart();
      layPozXEnd(lay, pozerX);
   }

   public void layPoz_EndToStart_OfParent() {
      layPoz_EndToStart_Of(null);
   }

   public void layPoz_Inside_OfParent() {
      layPoz_StartToStart_OfParent();
      layPoz_EndToEnd_Parent();
      layPoz_TopToTop_OfParent();
      layPoz_BotToBot_OfParent();
   }

   public void layPoz_Inside_StartBotEnd_OfParent() {
      layPoz_StartToStart_OfParent();
      layPoz_BotToBot_OfParent();
      layPoz_EndToEnd_Parent();
   }

   /**
    * Lay {@link ILayoutable} inside the parent at start top and end
    * Height will be set to preferred Height.
    */
   public void layPoz_Inside_StartTopEnd_OfParent_PrefH() {
      layPoz_Inside_StartTopEnd_OfParent_WithH(lac.getSizerFactory().getSizerPrefLazy());
   }

   /**
    * Lay {@link ILayoutable} inside the parent at start top and end
    * @param sizerH how to size the Height of the {@link ILayoutable}
    */
   public void layPoz_Inside_StartTopEnd_OfParent_WithH(ByteObject sizerH) {
      layPoz_StartToStart_OfParent();
      layPoz_TopToTop_OfParent();
      layPoz_EndToEnd_Parent();
      area.setSizerH(sizerH);
   }

   public void layPoz_MidXToEnd_Of(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerMidToEnd();
      layPozX(lay, pozerX);
   }

   public void layPoz_MidXToMid_Of(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerCenterToCenter();
      layPozX(lay, pozerX);
   }

   public void layPoz_MidXToMid_OfParent() {
      layPoz_MidXToMid_Of(null);
   }

   public void layPoz_MidXToStart_Of(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerMidToStart();
      layPozX(lay, pozerX);
   }

   public void layPoz_MidYToBot_Of(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerMidToEnd();
      layPozY(lay, pozerY);
   }

   public void layPoz_MidYToMid_Of(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerCenterToCenter();
      layPozY(lay, pozerY);
   }

   public void layPoz_MidYToMid_OfParent() {
      layPoz_MidYToMid_Of(null);
   }

   public void layPoz_MidYToTop_Of(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerMidToTop();
      layPozY(lay, pozerY);
   }

   public void layPoz_StartToCenter_Of(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerStartToCenter();
      layPozXEnd(lay, pozerX);
   }

   public void layPoz_StartToEnd_Of(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerStartToEnd();
      layPozXStart(lay, pozerX);
   }

   public void layPoz_StartToEnd_ofParent() {
      layPoz_StartToEnd_Of(null);
   }

   public void layPoz_StartToStart_Of(ILayoutable lay) {
      ByteObject pozerX = lac.getPozerFactory().getPozerStartToStart();
      layPozXStart(lay, pozerX);
   }

   /**
    * For margins used by many {@link ILayoutable}, consider create a ghost line and
    * layout relative to that line. 
    * @param lay
    * @param sizer
    */
   public void layPoz_StartToStart_Of_Margin(ILayoutable lay, ByteObject sizer) {
      layPoz_StartToStart_Of_With(lay, ITechPozer.POS_FUN_1_AWAY_CENTER, sizer);
   }

   public void layPoz_StartToStart_Of_Padding(ILayoutable lay, ByteObject sizer) {
      layPoz_StartToStart_Of_With(lay, ITechPozer.POS_FUN_0_TOWARDS_CENTER, sizer);
   }

   /**
    * <li>{@link ITechPozer#POS_FUN_0_TOWARDS_CENTER} XX towards the center of this object, in effect negative margin
    * <li>{@link ITechPozer#POS_FUN_1_AWAY_CENTER} XX away the center of this object, in effect postive margin
    * @param lay
    * @param fun {@link ITechPozer#POS_OFFSET_10_SIZER_FUN1}
    * @param sizer
    */
   public void layPoz_StartToStart_Of_With(ILayoutable lay, int fun, ByteObject sizer) {
      ByteObject pozerX = lac.getPozerFactory().getPozerStartToStart();
      if (sizer != null) {
         lac.getPozerFactory().setPoserWithSizer(pozerX, fun, sizer);
      }
      layPozXStart(lay, pozerX);
   }

   public void layPoz_StartToStart_OfParent() {
      layPoz_StartToStart_Of(null);
   }

   public void layPoz_TopToBot_Of(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerTopToBottom();
      layPozYTop(lay, pozerY);
   }

   public void layPoz_TopToBot_OfParent() {
      layPoz_TopToBot_Of(null);
   }

   public void layPoz_TopToMid_Of(ILayoutable lay) {
      //define the y pos
      ByteObject pozerY = lac.getPozerFactory().getPozerTopToMid();
      layPozYTop(lay, pozerY);
   }

   public void layPoz_TopToMid_OfParent() {
      layPoz_TopToMid_Of(null);
   }

   public void layPoz_TopToTop_Of(ILayoutable lay) {
      ByteObject pozerY = lac.getPozerFactory().getPozerTopToTop();
      layPozYTop(lay, pozerY);
   }

   public void layPoz_TopToTop_Of_Margin(ILayoutable lay, ByteObject sizer) {
      layPoz_TopToTop_Of_With(lay, ITechPozer.POS_FUN_1_AWAY_CENTER, sizer);
   }

   public void layPoz_TopToTop_Of_Padding(ILayoutable lay, ByteObject sizer) {
      layPoz_TopToTop_Of_With(lay, ITechPozer.POS_FUN_0_TOWARDS_CENTER, sizer);
   }

   public void layPoz_TopToTop_Of_With(ILayoutable lay, int fun, ByteObject sizer) {
      ByteObject pozerX = lac.getPozerFactory().getPozerTopToTop();
      if (sizer != null) {
         lac.getPozerFactory().setPoserWithSizer(pozerX, fun, sizer);
      }
      layPozYTop(lay, pozerX);
   }

   public void layPoz_TopToTop_OfParent() {
      layPoz_TopToTop_Of(null);
   }

   private void layPozParent(ByteObject pozer) {
      pozer.set1(ITechPozer.POS_OFFSET_02_ETALON1, ITechPozer.POS_ETALON_1_PARENT);
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

   public void laySiz_H_Ratio100Of(ILayoutable lay, int value) {
      if (lay == null) {
         //use parent
         laySiz_W_Ratio100OfParent(value);
      } else {
         ByteObject sizerH = lac.getSizerFactory().getSizerRatio100(lay, value);
         area.setSizerH(sizerH);
      }
   }

   public void laySiz_H_Ratio100OfParent(int value) {
      ByteObject sizerH = lac.getSizerFactory().getSizerRatio100Parent(value);
      area.setSizerH(sizerH);
   }

   public void laySiz_Preferred() {
      area.setSizerH(lac.getFactorySizer().getSizerPrefLazy());
      area.setSizerW(lac.getFactorySizer().getSizerPrefLazy());
   }

   /**
    * Contextual ratio. 
    * @param lay
    * @param value
    */
   public void laySiz_W_Ratio100Of(ILayoutable lay, int value) {
      if (lay == null) {
         //use parent
         laySiz_W_Ratio100OfParent(value);
      } else {
         ByteObject sizerW = lac.getSizerFactory().getSizerRatio100(lay, value);
         area.setSizerW(sizerW);
      }

   }

   public void laySiz_W_Ratio100OfParent(int value) {
      ByteObject sizerW = lac.getSizerFactory().getSizerRatio100Parent(value);
      area.setSizerW(sizerW);
   }

   public void laySiz_WH_Ratio100OfParent(int w, int h) {
      laySiz_W_Ratio100OfParent(w);
      laySiz_H_Ratio100OfParent(h);
   }

   public void setSizePixel(int w, int h) {
      setSizerW(lac.getSizerFactory().getSizerPix(w));
      setSizerH(lac.getSizerFactory().getSizerPix(h));
   }
   
   public void setArea(Zer2DArea area) {
      this.area = area;
   }

   public void setAreaOf(ILayoutable layoutable) {
      this.area = layoutable.getArea();
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

   public void setSizerH(ByteObject sizerH) {
      area.setSizerH(sizerH);
   }

   public void setSizerW(ByteObject sizerW) {
      area.setSizerW(sizerW);
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
      dc.nlLvl(area, "area");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LayData");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return lac.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
