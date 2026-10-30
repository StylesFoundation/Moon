package net.optifine.expr;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestExpressions {
   public static void main(String[] arr) throws Exception {
      ExpressionParser var4 = new ExpressionParser(null);

      while (true) {
         try {
            InputStreamReader var5 = new InputStreamReader(System.in);
            String var7 = new BufferedReader(var5).readLine();
            if (var7.length() == 0) {
               return;
            }

            IExpression var8 = var4.parse(var7);
            if (var8 instanceof IExpressionFloat) {
               float var10 = ((IExpressionFloat)var8).eval();
               System.out.println(var10 + "");
            }

            if (var8 instanceof IExpressionBool) {
               boolean var12 = ((IExpressionBool)var8).eval();
               System.out.println(var12 + "");
            }
         } catch (Exception var11) {
            var11.printStackTrace();
         }
      }
   }
}
