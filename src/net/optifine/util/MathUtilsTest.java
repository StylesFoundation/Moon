package net.optifine.util;

import net.minecraft.util.MathHelper;

public class MathUtilsTest {
   public static void main(String[] arr) throws Exception {
      for (MathUtilsTest.OPER var8 : MathUtilsTest.OPER.values()) {
         dbg("******** " + var8 + " ***********");
         test(var8, false);
      }
   }

   private static void test(MathUtilsTest.OPER oPER, boolean boolean2) {
      MathHelper.fastMath = boolean2;
      double var5;
      double var7;
      switch (oPER) {
         case SIN:
         case COS:
            var5 = -MathHelper.PI;
            var7 = MathHelper.PI;
            break;
         case ASIN:
         case ACOS:
            var5 = -1.0;
            var7 = 1.0;
            break;
         default:
            return;
      }

      byte var9 = 10;

      for (int var10 = 0; var10 <= var9; var10++) {
         double var11 = var5 + var10 * (var7 - var5) / 10.0;
         float var13;
         float var14;
         switch (oPER) {
            case SIN:
               var13 = (float)Math.sin(var11);
               var14 = MathHelper.sin((float)var11);
               break;
            case COS:
               var13 = (float)Math.cos(var11);
               var14 = MathHelper.cos((float)var11);
               break;
            case ASIN:
               var13 = (float)Math.asin(var11);
               var14 = MathUtils.asin((float)var11);
               break;
            case ACOS:
               var13 = (float)Math.acos(var11);
               var14 = MathUtils.acos((float)var11);
               break;
            default:
               return;
         }

         dbg(String.format("%.2f, Math: %f, Helper: %f, diff: %f", var11, var13, var14, Math.abs(var13 - var14)));
      }
   }

   public static void dbg(String string) {
      System.out.println(string);
   }

   enum OPER {
      SIN,
      COS,
      ASIN,
      ACOS;

      private static MathUtilsTest.OPER[] Method692() {
         return new MathUtilsTest.OPER[]{SIN, COS, ASIN, ACOS};
      }
   }
}
