/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.layouter.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.BOModuleAbstract;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.layouter.src4.interfaces.IBOTypesLayout;

/**
 * 
 */
public class BOModuleLayouter extends BOModuleAbstract implements IBOTypesLayout {

   /**
    * 
    */
   protected final LayouterCtx lc;

   /**
    * 
    *
    * @param lc 
    */
   public BOModuleLayouter(LayouterCtx lc) {
      super(lc.getBOC());
      this.lc = lc;
   }

   public ByteObject getFlagOrdered(ByteObject bo, int offset, int flag) {
      // TODO Auto-generated method stub
      return null;
   }

   public String getIDString(int did, int value) {
      // TODO Auto-generated method stub
      return null;
   }

   public ByteObject merge(ByteObject root, ByteObject merge) {
      // TODO Auto-generated method stub
      return null;
   }

   public String subToStringOffset(ByteObject o, int offset) {
      int type = o.getType();
      String strType = ToStringStaticLayout.toStringTypesNull(type);
      if (strType == null) {
         return null;
      } else {
         return "" + offset;
      }
   }

   public String subToStringType(int type) {
      return ToStringStaticLayout.toStringTypesNull(type);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "BOModuleLayouter");
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.appendVarWithNewLine(toStringType(FTYPE_3_SIZER), FTYPE_3_SIZER);
      dc.appendVarWithNewLine(toStringType(FTYPE_4_POSITION), FTYPE_4_POSITION);
      dc.appendVarWithNewLine(toStringType(FTYPE_6_LINK), FTYPE_6_LINK);

   }

   public boolean toString(Dctx dc, ByteObject bo) {
      final int type = bo.getType();
      switch (type) {
         case FTYPE_3_SIZER:
            lc.getSizerFactory().toStringSizer(bo, dc, "");
            break;
         case FTYPE_4_POSITION:
            lc.getPozerFactory().toStringPozer(bo, dc);
            break;
         case FTYPE_6_LINK:
            dc.append("Link");
            break;
         default:
            return false;
      }
      return true;
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BOModuleLayouter");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   public boolean toString1Line(Dctx dc, ByteObject bo) {
      final int type = bo.getType();
      switch (type) {
         case FTYPE_3_SIZER:
            lc.getSizerFactory().toString1Line(bo, dc);
            break;
         case FTYPE_4_POSITION:
            lc.getPozerFactory().toStringPozer1Line(bo, dc);
            break;
         case FTYPE_6_LINK:
            dc.append("Link");
            break;
         default:
            return false;
      }
      return true;
   }

   public String toStringOffset(ByteObject o, int offset) {
      // TODO Auto-generated method stub
      return null;
   }

   private void toStringPrivate(Dctx dc) {

   }

   public String toStringType(int type) {
      return ToStringStaticLayout.toStringTypesNull(type);
   }

   //#enddebug

}
