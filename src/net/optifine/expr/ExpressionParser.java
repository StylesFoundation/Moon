package net.optifine.expr;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.src.Config;

public class ExpressionParser {
   private final IExpressionResolver expressionResolver;

   public ExpressionParser(IExpressionResolver iExpressionResolver) {
      this.expressionResolver = iExpressionResolver;
   }

   public IExpressionFloat parseFloat(String string) throws ParseException {
      IExpression var5 = this.parse(string);
      if (!(var5 instanceof IExpressionFloat)) {
         throw new ParseException("Not a float expression: " + var5.getExpressionType());
      } else {
         return (IExpressionFloat)var5;
      }
   }

   public IExpressionBool parseBool(String string) throws ParseException {
      IExpression var5 = this.parse(string);
      if (!(var5 instanceof IExpressionBool)) {
         throw new ParseException("Not a boolean expression: " + var5.getExpressionType());
      } else {
         return (IExpressionBool)var5;
      }
   }

   public IExpression parse(String string) throws ParseException {
      try {
         Token[] var4 = TokenParser.parse(string);
         if (var4 == null) {
            return null;
         }

         ArrayDeque var5 = new ArrayDeque<>(Arrays.asList(var4));
         return this.parseInfix(var5);
      } catch (IOException var6) {
         throw new ParseException(var6.getMessage(), var6);
      }
   }

   private IExpression parseInfix(Deque<Token> deque) throws ParseException {
      if (deque.isEmpty()) {
         return null;
      }

      LinkedList var5 = new LinkedList();
      LinkedList var6 = new LinkedList();
      IExpression var7 = this.parseExpression(deque);
      checkNull(var7, "Missing expression");
      var5.add(var7);

      while (true) {
         Token var8 = (Token)deque.poll();
         if (var8 == null) {
            return this.makeInfix(var5, var6);
         }

         if (var8.getType() != TokenType.OPERATOR) {
            throw new ParseException("Invalid operator: " + var8);
         }

         IExpression var9 = this.parseExpression(deque);
         checkNull(var9, "Missing expression");
         var6.add(var8);
         var5.add(var9);
      }
   }

   private IExpression makeInfix(List<IExpression> list1, List<Token> list2) throws ParseException {
      LinkedList var6 = new LinkedList();

      for (Token var8 : list2) {
         FunctionType var9 = FunctionType.parse(var8.getText());
         checkNull(var9, "Invalid operator: " + var8);
         var6.add(var9);
      }

      return this.makeInfixFunc(list1, var6);
   }

   private IExpression makeInfixFunc(List<IExpression> list1, List<FunctionType> list2) throws ParseException {
      if (list1.size() != list2.size() + 1) {
         throw new ParseException("Invalid infix expression, expressions: " + list1.size() + ", operators: " + list2.size());
      }

      if (list1.size() == 1) {
         return (IExpression)list1.get(0);
      }

      int var6 = Integer.MAX_VALUE;
      int var7 = Integer.MIN_VALUE;

      for (FunctionType var9 : list2) {
         var6 = Math.min(var9.getPrecedence(), var6);
         var7 = Math.max(var9.getPrecedence(), var7);
      }

      if (var7 >= var6 && var7 - var6 <= 10) {
         for (int var10 = var7; var10 >= var6; var10--) {
            this.mergeOperators(list1, list2, var10);
         }

         if (list1.size() == 1 && list2.size() == 0) {
            return (IExpression)list1.get(0);
         } else {
            throw new ParseException("Error merging operators, expressions: " + list1.size() + ", operators: " + list2.size());
         }
      } else {
         throw new ParseException("Invalid infix precedence, min: " + var6 + ", max: " + var7);
      }
   }

   private void mergeOperators(List<IExpression> list1, List<FunctionType> list2, int integer) throws ParseException {
      for (int var7 = 0; var7 < list2.size(); var7++) {
         FunctionType var8 = (FunctionType)list2.get(var7);
         if (var8.getPrecedence() == integer) {
            list2.remove(var7);
            IExpression var9 = (IExpression)list1.remove(var7);
            IExpression var10 = (IExpression)list1.remove(var7);
            IExpression var11 = makeFunction(var8, new IExpression[]{var9, var10});
            list1.add(var7, var11);
            var7--;
         }
      }
   }

   private IExpression parseExpression(Deque<Token> deque) throws ParseException {
      Token var5 = (Token)deque.poll();
      checkNull(var5, "Missing expression");
      switch (var5.getType()) {
         case NUMBER:
            return makeConstantFloat(var5);
         case IDENTIFIER:
            FunctionType var6 = this.getFunctionType(var5, deque);
            if (var6 != null) {
               return this.makeFunction(var6, deque);
            }

            return this.makeVariable(var5);
         case BRACKET_OPEN:
            return this.makeBracketed(var5, deque);
         case OPERATOR:
            FunctionType var7 = FunctionType.parse(var5.getText());
            checkNull(var7, "Invalid operator: " + var5);
            if (var7 == FunctionType.PLUS) {
               return this.parseExpression(deque);
            } else if (var7 == FunctionType.MINUS) {
               IExpression var9 = this.parseExpression(deque);
               return makeFunction(FunctionType.NEG, new IExpression[]{var9});
            } else if (var7 == FunctionType.NOT) {
               IExpression var8 = this.parseExpression(deque);
               return makeFunction(FunctionType.NOT, new IExpression[]{var8});
            }
         default:
            throw new ParseException("Invalid expression: " + var5);
      }
   }

   private static IExpression makeConstantFloat(Token token) throws ParseException {
      float var3 = Config.parseFloat(token.getText(), Float.NaN);
      if (Float.isNaN(var3)) {
         throw new ParseException("Invalid float value: " + token);
      } else {
         return new ConstantFloat(var3);
      }
   }

   private FunctionType getFunctionType(Token token, Deque<Token> deque) throws ParseException {
      Token var6 = (Token)deque.peek();
      if (var6 != null && var6.getType() == TokenType.BRACKET_OPEN) {
         FunctionType var8 = FunctionType.parse(var6.getText());
         checkNull(var8, "Unknown function: " + var6.getType());
         return var8;
      } else {
         FunctionType var7 = FunctionType.parse(token.getText());
         if (var7 == null) {
            return null;
         } else if (var7.getParameterCount(new IExpression[0]) > 0) {
            throw new ParseException("Missing arguments: " + var7);
         } else {
            return var7;
         }
      }
   }

   private IExpression makeFunction(FunctionType functionType, Deque<Token> deque) throws ParseException {
      if (functionType.getParameterCount(new IExpression[0]) == 0) {
         Token var6 = (Token)deque.peek();
         if (var6 == null || var6.getType() != TokenType.BRACKET_OPEN) {
            return makeFunction(functionType, new IExpression[0]);
         }
      }

      Token var10000 = (Token)deque.poll();
      Deque var7 = getGroup(deque, TokenType.BRACKET_CLOSE, true);
      IExpression[] var8 = this.parseExpressions(var7);
      return makeFunction(functionType, var8);
   }

   private IExpression[] parseExpressions(Deque<Token> deque) throws ParseException {
      List<IExpression> var5 = new ArrayList<>();

      while (true) {
         Deque var6 = getGroup(deque, TokenType.COMMA, false);
         IExpression var7 = this.parseInfix(var6);
         if (var7 == null) {
            return var5.toArray(new IExpression[0]);
         }

         var5.add(var7);
      }
   }

   private static IExpression makeFunction(FunctionType functionType, IExpression[] arr) throws ParseException {
      ExpressionType[] var5 = functionType.getParameterTypes(arr);
      if (arr.length != var5.length) {
         String var17 = functionType.getName();
         int var10003 = arr.length;
         int var10 = var5.length;
         int var11 = var10003;
         String var12 = var17;
         throw new ParseException("Invalid number of arguments, function: \"" + var12 + "\", count arguments: " + var11 + ", should be: " + var10);
      }

      for (int var6 = 0; var6 < arr.length; var6++) {
         ExpressionType var8 = arr[var6].getExpressionType();
         ExpressionType var9 = var5[var6];
         if (var8 != var9) {
            String var10002 = functionType.getName();
            ExpressionType var13 = var9;
            ExpressionType var14 = var8;
            int var15 = var6;
            String var16 = var10002;
            throw new ParseException("Invalid argument type, function: \"" + var16 + "\", index: " + var15 + ", type: " + var14 + ", should be: " + var13);
         }
      }

      if (functionType.getExpressionType() == ExpressionType.FLOAT) {
         return new FunctionFloat(functionType, arr);
      } else if (functionType.getExpressionType() == ExpressionType.BOOL) {
         return new FunctionBool(functionType, arr);
      } else if (functionType.getExpressionType() == ExpressionType.FLOAT_ARRAY) {
         return new FunctionFloatArray(functionType, arr);
      } else {
         throw new ParseException("Unknown function type: " + functionType.getExpressionType() + ", function: " + functionType.getName());
      }
   }

   private IExpression makeVariable(Token token) throws ParseException {
      if (this.expressionResolver == null) {
         throw new ParseException("Model variable not found: " + token);
      } else {
         IExpression var5 = this.expressionResolver.getExpression(token.getText());
         if (var5 == null) {
            throw new ParseException("Model variable not found: " + token);
         } else {
            return var5;
         }
      }
   }

   private IExpression makeBracketed(Token token, Deque<Token> deque) throws ParseException {
      Deque var3 = getGroup(deque, TokenType.BRACKET_CLOSE, true);
      return this.parseInfix(var3);
   }

   private static Deque<Token> getGroup(Deque<Token> deque, TokenType tokenType, boolean boolean3) throws ParseException {
      ArrayDeque var6 = new ArrayDeque();
      int var7 = 0;
      Iterator var8 = deque.iterator();

      while (var8.hasNext()) {
         Token var9 = (Token)var8.next();
         var8.remove();
         if (var7 == 0 && var9.getType() == tokenType) {
            return var6;
         }

         var6.add(var9);
         if (var9.getType() == TokenType.BRACKET_OPEN) {
            var7++;
         }

         if (var9.getType() == TokenType.BRACKET_CLOSE) {
            var7--;
         }
      }

      if (boolean3) {
         throw new ParseException("Missing end token: " + tokenType);
      } else {
         return var6;
      }
   }

   private static void checkNull(Object object, String string) throws ParseException {
      if (object == null) {
         throw new ParseException(string);
      }
   }
}
