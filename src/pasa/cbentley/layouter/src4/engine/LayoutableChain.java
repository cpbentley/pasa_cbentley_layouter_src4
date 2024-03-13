/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.BufferObject;
import pasa.cbentley.layouter.src4.ctx.LayouterCtx;
import pasa.cbentley.layouter.src4.interfaces.ILayoutable;

/**
 * The chain is a ghost
 * 
 * it is not a layout. It simply provides a convient API to organize componenents together.
 * 
 * Those {@link ILayoutable} have their own parents.
 * <br>
 * The chain is not a parent, is it a dependency to its elements? none. but it organizes the dependencies of the different elements.
 * 
 * 
 * The chain does not set any Sizer.
 * 
 * The only decision might be to poz Top and Bot in the align decision
 * 
 * @author Charles Bentley
 *
 */
public class LayoutableChain extends LayoutableGhost implements ILayoutable {

   protected int              alignLogic;

   protected BufferObject     chainData;

   private Area2DConfigurator configurator;


   protected int              type;

   /**
    * Will be set when the first element is added
    */
   protected ILayoutable      alignReference;

   public LayoutableChain(LayouterCtx lac) {
      super(lac);
      chainData = new BufferObject(lac.getUC());
      alignLogic = C.LOGIC_2_CENTER;
      type = C.LINE_0_HORIZONTAL;
      configurator = new Area2DConfigurator(lac);
   }

   /**
    * Changes the align reference.
    * @param obj
    * @throws IllegalArgumentException if {@link ILayoutable} is not part of the chain
    */
   public void setAlignReference(ILayoutable obj) {
      if (chainData.hasReference(obj)) {
         this.alignReference = obj;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public void appendFirst(ILayoutable element) {
      if (isEmpty()) {
         appendWhenEmpty(element);
      } else {
         ILayoutable first = (ILayoutable) chainData.getFirst();
         if (isHorizontal()) {
            configurator.setAreaOf(element);
            configurator.layPoz_EndToStart_Of(first);

            alignHorizontal();
         } else {
            configurator.setAreaOf(element);
            configurator.layPoz_BotToTop_Of(first);

            alignVertical();
         }
      }
      chainData.add(element);
   }

   public void appendLast(ILayoutable element) {
      if (isEmpty()) {
         appendWhenEmpty(element);
      } else {
         ILayoutable last = (ILayoutable) chainData.getLast();
         configurator.setAreaOf(last);
         if (isHorizontal()) {
            configurator.setAreaOf(element);
            configurator.layPoz_StartToEnd_Of(last);

            alignHorizontal();

         } else {
            configurator.setAreaOf(element);
            configurator.layPoz_TopToBot_Of(last);

            alignVertical();

         }
      }
      chainData.add(element);
   }

   public void alignUpdate() {
      for (int index = 0; index < chainData.getLength(); index++) {
         ILayoutable o = (ILayoutable) chainData.get(index);
         if (isHorizontal()) {
            alignHorizontalSet(o);
         } else {
            alignVerticalSet(o);
         }
      }
   }

   private void alignVerticalSet(ILayoutable element) {
      if (element != alignReference) {
         configurator.setAreaOf(element);
         alignVertical();
      }
   }

   private void alignHorizontalSet(ILayoutable element) {
      if (element != alignReference) {
         configurator.setAreaOf(element);
         alignHorizontal();
      }
   }

   private void alignHorizontal() {
      if (alignLogic == C.LOGIC_1_TOP_LEFT) {
         //top and preferred size
         configurator.layPoz_TopToTop_Of(alignReference);
         configurator.setPozerYBot(null); //force the use of pref size
      } else if (alignLogic == C.LOGIC_2_CENTER) {
         configurator.layPoz_MidYToMid_Of(alignReference);
         configurator.setPozerYBot(null); //force the use of pref size
      } else if (alignLogic == C.LOGIC_3_BOTTOM_RIGHT) {
         configurator.setPozerYTop(null);
         configurator.layPoz_BotToBot_Of(alignReference);
      } else {
         configurator.layPoz_TopToTop_Of(alignReference);
         configurator.layPoz_BotToBot_Of(alignReference);
      }
   }

   private void alignVertical() {
      if (alignLogic == C.LOGIC_1_TOP_LEFT) {
         //top and preferred size
         configurator.layPoz_StartToStart_Of(alignReference);
         configurator.setPozerXEnd(null);
      } else if (alignLogic == C.LOGIC_2_CENTER) {
         configurator.layPoz_MidXToMid_Of(alignReference);
         configurator.setPozerXEnd(null);
      } else if (alignLogic == C.LOGIC_3_BOTTOM_RIGHT) {
         configurator.setPozerXStart(null);
         configurator.layPoz_EndToEnd_Of(alignReference);
      } else {
         configurator.layPoz_StartToStart_Of(alignReference);
         configurator.layPoz_EndToEnd_Of(alignReference);
      }
   }

   private void appendWhenEmpty(ILayoutable element) {
      configurator.setAreaOf(element);
      configurator.layPoz_StartToStart_Of(this);
      configurator.layPoz_TopToTop_Of(this);
      alignReference = element;
   }

   public int getChainHeight() {
      if (isHorizontal()) {
         //vertical -> take the max
         if(alignLogic == C.LOGIC_0_UNDEFINED) {
            alignReference.layoutUpdateSizeHCheck();
            return alignReference.getSizeDrawnHeight();
         }
         int maxHeight = 0;
         for (int index = 0; index < chainData.getLength(); index++) {
            ILayoutable o = (ILayoutable) chainData.get(index);
            o.layoutUpdateSizeHCheck();
            int ow = o.getSizeDrawnHeight();
            if (ow > maxHeight) {
               maxHeight = ow;
            }
         }
         return maxHeight;
      } else {
         //add them all
         int height = 0;
         for (int index = 0; index < chainData.getLength(); index++) {
            ILayoutable o = (ILayoutable) chainData.get(index);
            //we need to make sure its computed
            o.layoutUpdateSizeHCheck();
            int oh = o.getSizeDrawnHeight();
            height += oh;
         }
         return height;
      }
   }

   /**
    * @return
    */
   public int getChainWidth() {
      if(isEmpty()) {
         return 0;
      }
      // Method must avoid loops depending 
      if (isHorizontal()) {
         //add them all
         int width = 0;
         for (int index = 0; index < chainData.getLength(); index++) {
            ILayoutable o = (ILayoutable) chainData.get(index);
            o.layoutUpdateSizeWCheck();
            int ow = o.getSizeDrawnWidth();
            width += ow;
         }
         return width;
      } else {
         //
         if(alignLogic == C.LOGIC_0_UNDEFINED) {
            alignReference.layoutUpdateSizeWCheck();
            return alignReference.getSizeDrawnWidth();
         }
         //vertical -> take the max
         int max = 0;
         for (int index = 0; index < chainData.getLength(); index++) {
            ILayoutable o = (ILayoutable) chainData.get(index);
            o.layoutUpdateSizeWCheck();
            int ow = o.getSizeDrawnWidth();
            if (ow > max) {
               max = ow;
            }
         }
         return max;
      }
   }

   public int getSizeChain() {
      return chainData.getSize();
   }

   /**
    * Override
    */
   public int getSizePreferredHeight() {
      return getChainHeight();
   }

   public int getSizePreferredWidth() {
      return getChainWidth();
   }

   public boolean isEmpty() {
      return getSizeChain() == 0;
   }

   private boolean isHorizontal() {
      return type == C.LINE_0_HORIZONTAL;
   }

   /**
    * <li> {@link C#LOGIC_1_TOP_LEFT}
    * <li> {@link C#LOGIC_2_CENTER}
    * <li> {@link C#LOGIC_3_BOTTOM_RIGHT}
    * @param align
    */
   public void setChainElementAlignment(int align) {
      this.alignLogic = align;
   }
   
   public int getChainElementAlignment() {
      return alignLogic;
   }

   /**
    * 
    * @param type
    */
   public void setChainType(int type) {
      this.type = type;
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, LayoutableChain.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LayoutableChain.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
