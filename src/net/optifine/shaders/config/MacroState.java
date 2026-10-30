package net.optifine.shaders.config;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.src.Config;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.ParseException;

public class MacroState {
   private boolean active = true;
   private final Deque<Boolean> dequeState = new ArrayDeque<>();
   private final Deque<Boolean> dequeResolved = new ArrayDeque<>();
   private final Map<String, String> mapMacroValues = new HashMap<>();
   private static final Pattern PATTERN_DIRECTIVE = Pattern.compile("\\s*#\\s*(\\w+)\\s*(.*)");
   private static final Pattern PATTERN_DEFINED = Pattern.compile("defined\\s+(\\w+)");
   private static final Pattern PATTERN_DEFINED_FUNC = Pattern.compile("defined\\s*\\(\\s*(\\w+)\\s*\\)");
   private static final Pattern PATTERN_MACRO = Pattern.compile("(\\w+)");
   private static final List<String> MACRO_NAMES = Arrays.asList("define", "undef", "ifdef", "ifndef", "if", "else", "elif", "endif");

   public boolean processLine(String string) {
      Matcher var5 = PATTERN_DIRECTIVE.matcher(string);
      if (!var5.matches()) {
         return this.active;
      }

      String var6 = var5.group(1);
      String var7 = var5.group(2);
      int var8 = var7.indexOf("//");
      if (var8 >= 0) {
         var7 = var7.substring(0, var8);
      }

      boolean var9 = this.active;
      this.processMacro(var6, var7);
      this.active = !this.dequeState.contains(Boolean.FALSE);
      return this.active || var9;
   }

   public static boolean isMacroLine(String string) {
      Matcher var4 = PATTERN_DIRECTIVE.matcher(string);
      if (!var4.matches()) {
         return false;
      }

      String var5 = var4.group(1);
      return MACRO_NAMES.contains(var5);
   }

   private void processMacro(String string1, String string2) {
      StringTokenizer var6 = new StringTokenizer(string2, " \t");
      String var7 = var6.hasMoreTokens() ? var6.nextToken() : "";
      String var8 = var6.hasMoreTokens() ? var6.nextToken("").trim() : "";
      if (string1.equals("define")) {
         this.mapMacroValues.put(var7, var8);
      } else if (string1.equals("undef")) {
         this.mapMacroValues.remove(var7);
      } else if (string1.equals("ifdef")) {
         boolean var9 = this.mapMacroValues.containsKey(var7);
         this.dequeState.add(var9);
         this.dequeResolved.add(var9);
      } else if (string1.equals("ifndef")) {
         boolean var12 = !this.mapMacroValues.containsKey(var7);
         this.dequeState.add(var12);
         this.dequeResolved.add(var12);
      } else if (string1.equals("if")) {
         boolean var13 = this.eval(string2);
         this.dequeState.add(var13);
         this.dequeResolved.add(var13);
      } else if (!this.dequeState.isEmpty()) {
         if (string1.equals("elif")) {
            this.dequeState.removeLast();
            boolean var10 = this.dequeResolved.removeLast();
            if (var10) {
               this.dequeState.add(Boolean.FALSE);
               this.dequeResolved.add(var10);
            } else {
               boolean var11 = this.eval(string2);
               this.dequeState.add(var11);
               this.dequeResolved.add(var11);
            }
         } else if (string1.equals("else")) {
            this.dequeState.removeLast();
            boolean var14 = this.dequeResolved.removeLast();
            boolean var15 = !var14;
            this.dequeState.add(var15);
            this.dequeResolved.add(Boolean.TRUE);
         } else if (string1.equals("endif")) {
            this.dequeState.removeLast();
            this.dequeResolved.removeLast();
         }
      }
   }

   private boolean eval(String string) {
      Matcher var5 = PATTERN_DEFINED.matcher(string);
      string = var5.replaceAll("defined_$1");
      string = PATTERN_DEFINED_FUNC.matcher(string).replaceAll("defined_$1");
      int var8 = 0;

      boolean var7;
      do {
         var7 = false;
         Matcher var9 = PATTERN_MACRO.matcher(string);

         while (var9.find()) {
            String var10 = var9.group();
            if (var10.length() > 0) {
               char var11 = var10.charAt(0);
               if ((Character.isLetter(var11) || var11 == '_') && this.mapMacroValues.containsKey(var10)) {
                  String var12 = this.mapMacroValues.get(var10);
                  if (var12 == null) {
                     var12 = "1";
                  }

                  int var13 = var9.start();
                  int var14 = var9.end();
                  string = string.substring(0, var13) + " " + var12 + " " + string.substring(var14);
                  var7 = true;
                  var8++;
                  break;
               }
            }
         }
      } while (var7 && var8 < 100);

      if (var8 == 100) {
         Config.Method252("Too many iterations: " + var8 + ", when resolving: " + string);
         return true;
      }

      try {
         MacroExpressionResolver var18 = new MacroExpressionResolver(this.mapMacroValues);
         IExpression var19 = new ExpressionParser(var18).parse(string);
         if (var19.getExpressionType() == ExpressionType.BOOL) {
            return ((IExpressionBool)var19).eval();
         } else if (var19.getExpressionType() == ExpressionType.FLOAT) {
            float var20 = ((IExpressionFloat)var19).eval();
            return var20 != 0.0F;
         } else {
            throw new ParseException("Not a boolean or float expression: " + var19.getExpressionType());
         }
      } catch (ParseException var15) {
         Config.Method252("Invalid macro expression: " + string);
         Config.Method252("Error: " + var15.getMessage());
         return false;
      }
   }
}
