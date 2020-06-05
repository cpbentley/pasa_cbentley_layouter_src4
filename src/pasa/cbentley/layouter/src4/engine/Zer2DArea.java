/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;
import pasa.cbentley.layouter.src4.tech.ITechLayout;
import pasa.cbentley.layouter.src4.tech.ITechPozer;
import pasa.cbentley.layouter.src4.tech.ITechSizer;

/**
 * Define a 2d rectangle using 2 {@link ITechSizer} and 4 {@link ITechPozer}.
 * <br>
 * <br>
 * 
 * When 2 pozers and 1 sizer are defined, the sizer becomes the preferred size
 * 
 * and the size of the component is solely defined by the 2 pozers.
 * 
 * @author Charles Bentley
 *
 */
public class Zer2DArea implements IStringable, ITechLayout {

   /**
    * 
    */
   protected final LayouterCtx lc;

   /**
    * Can be null
    */
   protected ByteObject        pozerXEnd;

   /**
    * Can be null
    */
   protected ByteObject        pozerXStart;

   /**
    * Can be null
    */
   protected ByteObject        pozerYBot;

   /**
    * Can be null
    */
   protected ByteObject        pozerYTop;

   /**
    * 
    */
   protected Zer2DSizer        sizer;

   /**
    * 
    *
    * @param lc 
    */
   public Zer2DArea(LayouterCtx lc) {
      this.lc = lc;
   }

   /**
    * 
    *
    * @return 
    */
   public Object clone() {
      Zer2DArea clone = new Zer2DArea(lc);
      if (this.pozerXEnd != null) {
         clone.pozerXEnd = this.pozerXEnd.cloneMe();
      }
      if (this.pozerXStart != null) {
         clone.pozerXStart = this.pozerXStart.cloneMe();
      }
      if (this.pozerYBot != null) {
         clone.pozerYBot = this.pozerYBot.cloneMe();
      }
      if (this.pozerYTop != null) {
         clone.pozerYTop = this.pozerYTop.cloneMe();
      }
      if (this.sizer != null) {
         clone.sizer = (Zer2DSizer) this.sizer.clone();
      }
      return clone;
   }

   /**
    * 
    *
    * @return 
    */
   public Zer2DArea cloneMe() {
      return (Zer2DArea) clone();
   }

   /**
    * <li> {@link ITechLayout#COMPUTE_0_INVALID} area is not defined
    * <li> {@link ITechLayout#COMPUTE_1_NORMAL} area is defined by a top
    * <li> {@link ITechLayout#COMPUTE_2_INVERSE} area is defined by a bottom
    * <li> {@link ITechLayout#COMPUTE_3_BOTH} area is defined by a top and a bottom
    *
    * @return 
    */
   public int getPositionComputeFlagX() {
      boolean isXNormal = pozerXStart != null;
      boolean isXEnd = pozerXEnd != null;

      if (isXNormal) {
         if (isXEnd) {
            return COMPUTE_3_BOTH;
         } else {
            return COMPUTE_1_NORMAL;
         }
      } else {
         if (isXEnd) {
            //compute based
            return COMPUTE_2_INVERSE;
         } else {
            return COMPUTE_0_INVALID;
         }
      }
   }

   /**
    * <li> {@link ITechLayout#COMPUTE_0_INVALID} area is not defined
    * <li> {@link ITechLayout#COMPUTE_1_NORMAL} area is defined by a top
    * <li> {@link ITechLayout#COMPUTE_2_INVERSE} area is defined by a bottom
    * <li> {@link ITechLayout#COMPUTE_3_BOTH} area is defined by a top and a bottom
    *
    * @return 
    */
   public int getPositionComputeFlagY() {
      boolean isYNormal = pozerYTop != null;
      boolean isYEnd = pozerYBot != null;

      if (isYNormal) {
         if (isYEnd) {
            return COMPUTE_3_BOTH;
         } else {
            return COMPUTE_1_NORMAL;
         }
      } else {
         if (isYEnd) {
            //compute based
            return COMPUTE_2_INVERSE;
         } else {
            return COMPUTE_0_INVALID;
         }
      }
   }

   /**
    * COMPUTE_3_BOTH if both pozers are defined.
    * 
    * @return
    */
   public int getSizeComputeFlagH() {
      if (pozerYTop != null && pozerYBot != null) {
         return COMPUTE_3_BOTH;
      } else if (sizer != null && sizer.getSizerH() != null) {
         return COMPUTE_1_NORMAL;
      } else {
         return COMPUTE_0_INVALID;
      }
   }

   public int getSizeComputeFlagW() {
      if (pozerXStart != null && pozerXEnd != null) {
         return COMPUTE_3_BOTH;
      } else if (sizer != null && sizer.getSizerW() != null) {
         return COMPUTE_1_NORMAL;
      } else {
         return COMPUTE_0_INVALID;
      }
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerXEnd() {
      return pozerXEnd;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerXMain() {
      if (pozerXStart == null) {
         return pozerXEnd;
      }
      return pozerXStart;
   }

   /**
    * 
    * @return
    */
   public ByteObject getPozerXStart() {
      return pozerXStart;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerYBot() {
      return pozerYBot;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getPozerYTop() {
      return pozerYTop;
   }

   /**
    * 
    *
    * @return 
    */
   public int getSizeComputeFlag() {
      if (sizer == null) {
         return COMPUTE_SIZE_3_NONE;
      } else {
         ByteObject sizerW = sizer.getSizerW();
         ByteObject sizerH = sizer.getSizerH();
         boolean isWOK = sizerW != null;
         boolean isHOK = sizerH != null;
         if (isWOK) {
            if (isHOK) {
               return COMPUTE_SIZE_0_NORMAL;
            } else {
               return COMPUTE_SIZE_1_ONLY_W;
            }
         } else {
            if (isHOK) {
               return COMPUTE_SIZE_2_ONLY_H;
            } else {
               return COMPUTE_SIZE_3_NONE;
            }
         }
      }
   }

   /**
    * 
    * {@link Zer2DSizer}
    * 
    * @return can be null
    * 
    */
   public Zer2DSizer getSizer() {
      return sizer;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getSizerH() {
      if (sizer == null) {
         return null;
      }
      return sizer.getSizerH();
   }

   /**
    * 
    *
    * @return never null
    */
   public Zer2DSizer getSizerLazy() {
      if (sizer == null) {
         sizer = new Zer2DSizer(lc, null, null);
      }
      return sizer;
   }

   /**
    * 
    *
    * @return 
    */
   public ByteObject getSizerW() {
      if (sizer == null) {
         return null;
      }
      return sizer.getSizerW();
   }

   /**
    * 
    *
    * @return 
    */
   public boolean isInvalidArea() {
      boolean isValid1 = isValidArea();
      boolean isValid2 = isValidAreaOptimized();
      if (isValid1 != isValid2) {
         boolean isValid2ForBreak = isValidAreaOptimized();
         //#debug
         toDLog().pNull("" + toStringInvalidAreaMessage(), this, Zer2DArea.class, "isInvalidArea", LVL_05_FINE, false);
         throw new RuntimeException("isValidArea=" + isValid1 + " isValidAreaOptimized=" + isValid2);
      }
      return !isValid1;
   }

   /**
    * Is there enough data to compute the area's position and size.
    *
    * @return 
    */
   public boolean isValidArea() {
      if (isValidHeight()) {
         if (isValidWidth()) {
            if (isValidPosition()) {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * 
    *
    * @return 
    */
   public boolean isValidAreaOptimized() {
      if (sizer == null || (sizer.getSizerH() == null && sizer.getSizerW() == null)) {
         //then we need all pozer
         if (pozerXEnd != null && pozerXStart != null && pozerYBot != null && pozerYTop != null) {
            return true;
         } else {
            return false;
         }
      } else {
         //assert at least one sizer is not null
         if (sizer.getSizerH() == null) {
            //we need
            if (pozerYBot != null && pozerYTop != null) {
               return true;
            } else {
               return false;
            }
         } else {
            //do we have a Y coordinate ?
            if (pozerYBot == null && pozerYTop == null) {
               return false;
            }
         }
         if (sizer.getSizerW() == null) {
            //we need
            if (pozerXStart != null && pozerXEnd != null) {
               return true;
            } else {
               return false;
            }
         } else {
            if (pozerXStart == null && pozerXEnd == null) {
               return false;
            }
         }
         return true;
      }
   }

   /**
    * 
    *
    * @return 
    */
   public boolean isValidHeight() {
      if (sizer == null || sizer.getSizerH() == null) {
         //then we need all pozer
         if (pozerYBot != null && pozerYTop != null) {
            return true;
         }
         return false;
      } else {
         return sizer.getSizerH() != null;
      }
   }

   /**
    * 
    *
    * @return 
    */
   public boolean isValidPosition() {
      //we need at least one x and one y
      if ((pozerXEnd == null && pozerXStart == null) || (pozerYTop == null && pozerYBot == null)) {
         return false;
      }
      return true;
   }

   /**
    * 
    *
    * @return 
    */
   public boolean isValidPositionX() {
      //we need at least one x and one y
      if ((pozerXEnd == null && pozerXStart == null)) {
         return false;
      }
      return true;
   }

   /**
    * 
    *
    * @return 
    */
   public boolean isValidPositionY() {
      //we need at least one x and one y
      if ((pozerYTop == null && pozerYBot == null)) {
         return false;
      }
      return true;
   }

   /**
    * 
    *
    * @return 
    */
   public boolean isValidSize() {
      return isValidWidth() && isValidHeight();
   }

   /**
    * 
    *
    * @return 
    */
   public boolean isValidWidth() {
      if (sizer == null || sizer.getSizerW() == null) {
         //then we need all pozer
         if (pozerXEnd != null && pozerXStart != null) {
            return true;
         }
         return false;
      } else {
         return sizer.getSizerW() != null;
      }
   }

   /**
    * Defines the X coordinate of the bottom line of this area.
    *
    * @param pozerX 
    */
   public void setPozerXEnd(ByteObject pozerX) {
      pozerXEnd = pozerX;
   }

   /**
    * Defines the X coordinate of the top line of this area.
    *
    * @param pozerX 
    */
   public void setPozerXStart(ByteObject pozerX) {
      pozerXStart = pozerX;
   }

   /**
    * Defines the Y coordinate of the bottom line of this area.
    *
    * @param pozerY 
    */
   public void setPozerYBot(ByteObject pozerY) {
      pozerYBot = pozerY;
   }

   /**
    * Defines the Y coordinate of the top line of this area.
    *
    * @param pozerY 
    */
   public void setPozerYTop(ByteObject pozerY) {
      pozerYTop = pozerY;
   }

   /**
    * 
    *
    * @param sizer 
    */
   public void setSizer(Zer2DSizer sizer) {
      this.sizer = sizer;
   }

   /**
    * 
    *
    * @param sizerH 
    */
   public void setSizerH(ByteObject sizerH) {
      getSizerLazy().setSizerH(sizerH);
   }

   /**
    * 
    *
    * @param sizerW 
    */
   public void setSizerW(ByteObject sizerW) {
      getSizerLazy().setSizerW(sizerW);

   }

   public void setSizerWH(ByteObject sizerW, ByteObject sizerH) {
      getSizerLazy().setSizerWH(sizerW, sizerH);
   }

   /**
    * 
    *
    * @return 
    */
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   /**
    * 
    *
    * @return 
    */
   public String toString() {
      return Dctx.toString(this);
   }

   /**
    * 
    *
    * @param dc 
    */
   public void toString(Dctx dc) {
      dc.root(this, "Zer2DArea");
      toStringPrivate(dc);

      dc.appendVarWithSpace("isValidArea", isValidArea());
      dc.appendVarWithSpace("isValidSize", isValidSize());
      dc.appendVarWithSpace("isValidPosition", isValidPosition());
      dc.appendVarWithSpace("isValidPositionX", isValidPositionX());
      dc.appendVarWithSpace("isValidPositionY", isValidPositionY());
      dc.nl();

      dc.appendVarWithSpace("getSizeComputeFlagW", getSizeComputeFlagW());
      dc.appendVarWithSpace("getSizeComputeFlagH", getSizeComputeFlagH());
      
      dc.nlLvl(sizer, "Sizer");
      toStringPozerLayout(dc, null);
   }

   /**
    * 
    *
    * @param dc 
    * @param layoutable 
    */
   public void toString(Dctx dc, ILayoutable layoutable) {
      dc.root(this, "Zer2DArea");
      toStringPrivate(dc);

      dc.nlLvl(sizer, "Sizer");
      toStringPozerLayout(dc, layoutable);
   }

   /**
    * 
    *
    * @return 
    */
   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   /**
    * 
    *
    * @param dc 
    */
   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "Zer2DArea");
      toStringPrivate(dc);
   }

   /**
    * 
    *
    * @return 
    */
   public UCtx toStringGetUCtx() {
      return lc.getUCtx();
   }

   /**
    * 
    *
    * @return 
    */
   public String toStringInvalidAreaMessage() {
      StringBBuilder sb = new StringBBuilder(lc.getUCtx());
      sb.append("Area Invalid");
      if (!isValidHeight()) {
         sb.append(";Height is not defined");
      }
      if (!isValidWidth()) {
         sb.append(";Width is not defined");
      }
      if (!isValidPositionX()) {
         sb.append(";X coordinate is not defined");
      }
      if (!isValidPositionY()) {
         sb.append(";Y coordinate is not defined");
      }
      return sb.toString();
   }

   /**
    * 
    *
    * @param dc 
    * @param layoutable 
    */
   private void toStringPozerLayout(Dctx dc, ILayoutable layoutable) {
      //TODO mode pozerX and 
      //in mode start-end, PozerStart and PozerEnd
      lc.getPozerFactory().toStringPozerStart(pozerXStart, dc.newLevel(), layoutable);
      lc.getPozerFactory().toStringPozerEnd(pozerXEnd, dc.newLevel(), layoutable);
      lc.getPozerFactory().toStringPozerTop(pozerYTop, dc.newLevel(), layoutable);
      lc.getPozerFactory().toStringPozerBot(pozerYBot, dc.newLevel(), layoutable);
   }

   /**
    * 
    *
    * @param dc 
    */
   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
