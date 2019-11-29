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

/**
 * 
 * When 2 pozers and 1 sizer are defined, the sizer becomes the preferred size
 * 
 * and the size of the component is solely defined by the 2 pozers.
 * 
 * @author Charles Bentley
 *
 */
public class Zer2DArea implements IStringable, ITechLayout {

   protected final LayouterCtx lc;

   protected ByteObject        pozerXEnd;

   protected ByteObject        pozerXStart;

   protected ByteObject        pozerYBot;

   protected ByteObject        pozerYTop;

   protected Zer2DSizer        sizer;

   public Zer2DArea(LayouterCtx lc) {
      this.lc = lc;
   }

   public Zer2DSizer getSizer() {
      return sizer;
   }

   public Zer2DSizer getSizerLazy() {
      if (sizer == null) {
         sizer = new Zer2DSizer(lc, null, null);
      }
      return sizer;
   }

   /**
    * Is there enough data to compute the area's position and size
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
            if(pozerYBot == null && pozerYTop == null) {
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
   public Zer2DArea cloneMe() {
      return (Zer2DArea)clone();
   }
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

   public boolean isValidPosition() {
      //we need at least one x and one y
      if ((pozerXEnd == null && pozerXStart == null) || (pozerYTop == null && pozerYBot == null)) {
         return false;
      }
      return true;
   }

   public boolean isValidPositionX() {
      //we need at least one x and one y
      if ((pozerXEnd == null && pozerXStart == null)) {
         return false;
      }
      return true;
   }

   public boolean isValidPositionY() {
      //we need at least one x and one y
      if ((pozerYTop == null && pozerYBot == null)) {
         return false;
      }
      return true;
   }

   public boolean isValidSize() {
      return isValidWidth() && isValidHeight();
   }

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
    * Defines the X coordinate of the bottom line of this area
    * @param pozerY
    */
   public void setPozerXEnd(ByteObject pozerX) {
      pozerXEnd = pozerX;
   }

   /**
    * Defines the X coordinate of the top line of this area
    * @param pozerY
    */
   public void setPozerXStart(ByteObject pozerX) {
      pozerXStart = pozerX;
   }

   /**
    * Defines the Y coordinate of the bottom line of this area
    * @param pozerY
    */
   public void setPozerYBot(ByteObject pozerY) {
      pozerYBot = pozerY;
   }

   /**
    * Defines the Y coordinate of the top line of this area
    * @param pozerY
    */
   public void setPozerYTop(ByteObject pozerY) {
      pozerYTop = pozerY;
   }

   public void setSizer(Zer2DSizer sizer) {
      this.sizer = sizer;
   }

   public void setSizerH(ByteObject sizerH) {
      getSizerLazy().setSizerH(sizerH);
   }

   public void setSizerW(ByteObject sizerW) {
      getSizerLazy().setSizerW(sizerW);

   }

   public ByteObject getSizerH() {
      if (sizer == null) {
         return null;
      }
      return sizer.getSizerH();
   }

   public ByteObject getSizerW() {
      if (sizer == null) {
         return null;
      }
      return sizer.getSizerW();
   }

   public ByteObject getPozerYTop() {
      return pozerYTop;
   }

   public ByteObject getPozerXEnd() {
      return pozerXEnd;
   }

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

   public ByteObject getPozerYBot() {
      return pozerYBot;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "Zer2DArea");
      toStringPrivate(dc);

      dc.nlLvl(sizer, "Sizer");
      toStringPozerLayout(dc, null);
   }

   public void toString(Dctx dc, ILayoutable layoutable) {
      dc.root(this, "Zer2DArea");
      toStringPrivate(dc);

      dc.nlLvl(sizer, "Sizer");
      toStringPozerLayout(dc, layoutable);
   }

   private void toStringPozerLayout(Dctx dc, ILayoutable layoutable) {
      //TODO mode pozerX and 
      //in mode start-end, PozerStart and PozerEnd
      lc.getPozerFactory().toStringPozerStart(pozerXStart, dc.nLevel(), layoutable);
      lc.getPozerFactory().toStringPozerEnd(pozerXEnd, dc.nLevel(), layoutable);
      lc.getPozerFactory().toStringPozerTop(pozerYTop, dc.nLevel(), layoutable);
      lc.getPozerFactory().toStringPozerBot(pozerYBot, dc.nLevel(), layoutable);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "Zer2DArea");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return lc.getUCtx();
   }

   //#enddebug

}
