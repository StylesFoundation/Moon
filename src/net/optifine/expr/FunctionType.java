package net.optifine.expr;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Config;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.optifine.shaders.uniform.Smoother;
import net.optifine.util.MathUtils;

public enum FunctionType {
   PLUS(10, ExpressionType.FLOAT, "+", ExpressionType.FLOAT, ExpressionType.FLOAT),
   MINUS(10, ExpressionType.FLOAT, "-", ExpressionType.FLOAT, ExpressionType.FLOAT),
   MUL(11, ExpressionType.FLOAT, "*", ExpressionType.FLOAT, ExpressionType.FLOAT),
   DIV(11, ExpressionType.FLOAT, "/", ExpressionType.FLOAT, ExpressionType.FLOAT),
   MOD(11, ExpressionType.FLOAT, "%", ExpressionType.FLOAT, ExpressionType.FLOAT),
   NEG(12, ExpressionType.FLOAT, "neg", ExpressionType.FLOAT),
   PI(ExpressionType.FLOAT, "pi"),
   SIN(ExpressionType.FLOAT, "sin", ExpressionType.FLOAT),
   COS(ExpressionType.FLOAT, "cos", ExpressionType.FLOAT),
   ASIN(ExpressionType.FLOAT, "asin", ExpressionType.FLOAT),
   ACOS(ExpressionType.FLOAT, "acos", ExpressionType.FLOAT),
   TAN(ExpressionType.FLOAT, "tan", ExpressionType.FLOAT),
   ATAN(ExpressionType.FLOAT, "atan", ExpressionType.FLOAT),
   ATAN2(ExpressionType.FLOAT, "atan2", ExpressionType.FLOAT, ExpressionType.FLOAT),
   TORAD(ExpressionType.FLOAT, "torad", ExpressionType.FLOAT),
   TODEG(ExpressionType.FLOAT, "todeg", ExpressionType.FLOAT),
   MIN(ExpressionType.FLOAT, "min", new ParametersVariable().first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT)),
   MAX(ExpressionType.FLOAT, "max", new ParametersVariable().first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT)),
   CLAMP(ExpressionType.FLOAT, "clamp", ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT),
   ABS(ExpressionType.FLOAT, "abs", ExpressionType.FLOAT),
   FLOOR(ExpressionType.FLOAT, "floor", ExpressionType.FLOAT),
   CEIL(ExpressionType.FLOAT, "ceil", ExpressionType.FLOAT),
   EXP(ExpressionType.FLOAT, "exp", ExpressionType.FLOAT),
   FRAC(ExpressionType.FLOAT, "frac", ExpressionType.FLOAT),
   LOG(ExpressionType.FLOAT, "log", ExpressionType.FLOAT),
   POW(ExpressionType.FLOAT, "pow", ExpressionType.FLOAT, ExpressionType.FLOAT),
   RANDOM(ExpressionType.FLOAT, "random"),
   ROUND(ExpressionType.FLOAT, "round", ExpressionType.FLOAT),
   SIGNUM(ExpressionType.FLOAT, "signum", ExpressionType.FLOAT),
   SQRT(ExpressionType.FLOAT, "sqrt", ExpressionType.FLOAT),
   FMOD(ExpressionType.FLOAT, "fmod", ExpressionType.FLOAT, ExpressionType.FLOAT),
   TIME(ExpressionType.FLOAT, "time"),
   IF(
      ExpressionType.FLOAT,
      "if",
      new ParametersVariable().first(ExpressionType.BOOL, ExpressionType.FLOAT).repeat(ExpressionType.BOOL, ExpressionType.FLOAT).last(ExpressionType.FLOAT)
   ),
   NOT(12, ExpressionType.BOOL, "!", ExpressionType.BOOL),
   AND(3, ExpressionType.BOOL, "&&", ExpressionType.BOOL, ExpressionType.BOOL),
   OR(2, ExpressionType.BOOL, "||", ExpressionType.BOOL, ExpressionType.BOOL),
   GREATER(8, ExpressionType.BOOL, ">", ExpressionType.FLOAT, ExpressionType.FLOAT),
   GREATER_OR_EQUAL(8, ExpressionType.BOOL, ">=", ExpressionType.FLOAT, ExpressionType.FLOAT),
   SMALLER(8, ExpressionType.BOOL, "<", ExpressionType.FLOAT, ExpressionType.FLOAT),
   SMALLER_OR_EQUAL(8, ExpressionType.BOOL, "<=", ExpressionType.FLOAT, ExpressionType.FLOAT),
   EQUAL(7, ExpressionType.BOOL, "==", ExpressionType.FLOAT, ExpressionType.FLOAT),
   NOT_EQUAL(7, ExpressionType.BOOL, "!=", ExpressionType.FLOAT, ExpressionType.FLOAT),
   BETWEEN(7, ExpressionType.BOOL, "between", ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT),
   EQUALS(7, ExpressionType.BOOL, "equals", ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT),
   IN(ExpressionType.BOOL, "in", new ParametersVariable().first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT).last(ExpressionType.FLOAT)),
   SMOOTH(ExpressionType.FLOAT, "smooth", new ParametersVariable().first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT).maxCount(4)),
   TRUE(ExpressionType.BOOL, "true"),
   FALSE(ExpressionType.BOOL, "false"),
   VEC2(ExpressionType.FLOAT_ARRAY, "vec2", ExpressionType.FLOAT, ExpressionType.FLOAT),
   VEC3(ExpressionType.FLOAT_ARRAY, "vec3", ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT),
   VEC4(ExpressionType.FLOAT_ARRAY, "vec4", ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT);

   private final int precedence;
   private final ExpressionType expressionType;
   private final String name;
   private final IParameters parameters;
   public static final FunctionType[] VALUES = values();
   private static final Map<Integer, Float> mapSmooth = new HashMap<>();

   FunctionType(ExpressionType expressionType, String string4, ExpressionType... arr) {
      this(0, expressionType, string4, arr);
   }

   FunctionType(int integer3, ExpressionType expressionType, String string5, ExpressionType... arr) {
      this(integer3, expressionType, string5, new Parameters(arr));
   }

   FunctionType(ExpressionType expressionType, String string4, IParameters iParameters) {
      this(0, expressionType, string4, iParameters);
   }

   FunctionType(int integer3, ExpressionType expressionType, String string5, IParameters iParameters) {
      this.precedence = integer3;
      this.expressionType = expressionType;
      this.name = string5;
      this.parameters = iParameters;
   }

   public String getName() {
      return this.name;
   }

   public int getPrecedence() {
      return this.precedence;
   }

   public ExpressionType getExpressionType() {
      return this.expressionType;
   }

   public IParameters getParameters() {
      return this.parameters;
   }

   public int getParameterCount(IExpression[] arr) {
      return this.parameters.getParameterTypes(arr).length;
   }

   public ExpressionType[] getParameterTypes(IExpression[] arr) {
      return this.parameters.getParameterTypes(arr);
   }

   public float evalFloat(IExpression[] arr) {
      switch (this) {
         case PLUS:
            return evalFloat(arr, 0) + evalFloat(arr, 1);
         case MINUS:
            return evalFloat(arr, 0) - evalFloat(arr, 1);
         case MUL:
            return evalFloat(arr, 0) * evalFloat(arr, 1);
         case DIV:
            return evalFloat(arr, 0) / evalFloat(arr, 1);
         case MOD:
            float f = evalFloat(arr, 0);
            float f1 = evalFloat(arr, 1);
            return f - f1 * (float)((int)(f / f1));
         case NEG:
            return -evalFloat(arr, 0);
         case PI:
            return MathHelper.PI;
         case SIN:
            return MathHelper.sin(evalFloat(arr, 0));
         case COS:
            return MathHelper.cos(evalFloat(arr, 0));
         case ASIN:
            return MathUtils.asin(evalFloat(arr, 0));
         case ACOS:
            return MathUtils.acos(evalFloat(arr, 0));
         case TAN:
            return (float)Math.tan((double)evalFloat(arr, 0));
         case ATAN:
            return (float)Math.atan((double)evalFloat(arr, 0));
         case ATAN2:
            return MathHelper.Method565(evalFloat(arr, 0), evalFloat(arr, 1));
         case TORAD:
            return MathUtils.toRad(evalFloat(arr, 0));
         case TODEG:
            return MathUtils.toDeg(evalFloat(arr, 0));
         case MIN:
            return this.getMin(arr);
         case MAX:
            return this.getMax(arr);
         case CLAMP:
            return MathHelper.clamp_float(evalFloat(arr, 0), evalFloat(arr, 1), evalFloat(arr, 2));
         case ABS:
            return MathHelper.abs(evalFloat(arr, 0));
         case EXP:
            return (float)Math.exp((double)evalFloat(arr, 0));
         case FLOOR:
            return (float)MathHelper.floor_float(evalFloat(arr, 0));
         case CEIL:
            return (float)MathHelper.ceiling_float_int(evalFloat(arr, 0));
         case FRAC:
            return (float)MathHelper.func_181162_h((double)evalFloat(arr, 0));
         case LOG:
            return (float)Math.log((double)evalFloat(arr, 0));
         case POW:
            return (float)Math.pow((double)evalFloat(arr, 0), (double)evalFloat(arr, 1));
         case RANDOM:
            return (float)Math.random();
         case ROUND:
            return (float)Math.round(evalFloat(arr, 0));
         case SIGNUM:
            return Math.signum(evalFloat(arr, 0));
         case SQRT:
            return MathHelper.sqrt_float(evalFloat(arr, 0));
         case FMOD:
            float f2 = evalFloat(arr, 0);
            float f3 = evalFloat(arr, 1);
            return f2 - f3 * (float)MathHelper.floor_float(f2 / f3);
         case TIME:
            Minecraft minecraft = Minecraft.getMinecraft();
            World world = minecraft.theWorld;
            if (world == null) {
               return 0.0F;
            }

            return (float)(world.getTotalWorldTime() % 24000L) + Config.renderPartialTicks;
         case IF:
            int i = (arr.length - 1) / 2;

            for (int k = 0; k < i; k++) {
               int l = k * 2;
               if (evalBool(arr, l)) {
                  return evalFloat(arr, l + 1);
               }
            }

            return evalFloat(arr, i * 2);
         case SMOOTH:
            int j = (int)evalFloat(arr, 0);
            float f4 = evalFloat(arr, 1);
            float f5 = arr.length > 2 ? evalFloat(arr, 2) : 1.0F;
            float f6 = arr.length > 3 ? evalFloat(arr, 3) : f5;
            return Smoother.getSmoothValue(j, f4, f5, f6);
         default:
            Config.Method252("Unknown function type: " + this);
            return 0.0F;
      }
   }

   private float getMin(IExpression[] arr) {
      if (arr.length == 2) {
         return Math.min(evalFloat(arr, 0), evalFloat(arr, 1));
      }

      float var5 = evalFloat(arr, 0);

      for (int var6 = 1; var6 < arr.length; var6++) {
         float var7 = evalFloat(arr, var6);
         if (var7 < var5) {
            var5 = var7;
         }
      }

      return var5;
   }

   private float getMax(IExpression[] arr) {
      if (arr.length == 2) {
         return Math.max(evalFloat(arr, 0), evalFloat(arr, 1));
      }

      float var5 = evalFloat(arr, 0);

      for (int var6 = 1; var6 < arr.length; var6++) {
         float var7 = evalFloat(arr, var6);
         if (var7 > var5) {
            var5 = var7;
         }
      }

      return var5;
   }

   private static float evalFloat(IExpression[] arr, int integer) {
      return ((IExpressionFloat)arr[integer]).eval();
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public boolean evalBool(IExpression[] arr) {
      switch (this) {
         case TRUE:
            return true;
         case FALSE:
            return false;
         case NOT:
            return !evalBool(arr, 0);
         case AND:
            return evalBool(arr, 0) && evalBool(arr, 1);
         case OR:
            return evalBool(arr, 0) || evalBool(arr, 1);
         case GREATER:
            return evalFloat(arr, 0) > evalFloat(arr, 1);
         case GREATER_OR_EQUAL:
            return evalFloat(arr, 0) >= evalFloat(arr, 1);
         case SMALLER:
            return evalFloat(arr, 0) < evalFloat(arr, 1);
         case SMALLER_OR_EQUAL:
            return evalFloat(arr, 0) <= evalFloat(arr, 1);
         case EQUAL:
            return evalFloat(arr, 0) == evalFloat(arr, 1);
         case NOT_EQUAL:
            return evalFloat(arr, 0) != evalFloat(arr, 1);
         case BETWEEN:
            float var5 = evalFloat(arr, 0);
            return var5 >= evalFloat(arr, 1) && var5 <= evalFloat(arr, 2);
         case EQUALS:
            float var6 = evalFloat(arr, 0) - evalFloat(arr, 1);
            float var7 = evalFloat(arr, 2);
            return Math.abs(var6) <= var7;
         case IN:
            float var8 = evalFloat(arr, 0);

            for (int var9 = 1; var9 < arr.length; var9++) {
               float var10 = evalFloat(arr, var9);
               if (var8 == var10) {
                  return true;
               }
            }

            return false;
         default:
            Config.Method252("Unknown function type: " + this);
            return false;
      }
   }

   private static boolean evalBool(IExpression[] arr, int integer) {
      return ((IExpressionBool)arr[integer]).eval();
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public float[] evalFloatArray(IExpression[] arr) {
      switch (this) {
         case VEC2:
            return new float[]{evalFloat(arr, 0), evalFloat(arr, 1)};
         case VEC3:
            return new float[]{evalFloat(arr, 0), evalFloat(arr, 1), evalFloat(arr, 2)};
         case VEC4:
            return new float[]{evalFloat(arr, 0), evalFloat(arr, 1), evalFloat(arr, 2), evalFloat(arr, 3)};
         default:
            Config.Method252("Unknown function type: " + this);
            return null;
      }
   }

   public static FunctionType parse(String string) {
      for (FunctionType var7 : VALUES) {
         if (var7.getName().equals(string)) {
            return var7;
         }
      }

      return null;
   }

   private static FunctionType[] Method903() {
      return new FunctionType[]{
         PLUS,
         MINUS,
         MUL,
         DIV,
         MOD,
         NEG,
         PI,
         SIN,
         COS,
         ASIN,
         ACOS,
         TAN,
         ATAN,
         ATAN2,
         TORAD,
         TODEG,
         MIN,
         MAX,
         CLAMP,
         ABS,
         FLOOR,
         CEIL,
         EXP,
         FRAC,
         LOG,
         POW,
         RANDOM,
         ROUND,
         SIGNUM,
         SQRT,
         FMOD,
         TIME,
         IF,
         NOT,
         AND,
         OR,
         GREATER,
         GREATER_OR_EQUAL,
         SMALLER,
         SMALLER_OR_EQUAL,
         EQUAL,
         NOT_EQUAL,
         BETWEEN,
         EQUALS,
         IN,
         SMOOTH,
         TRUE,
         FALSE,
         VEC2,
         VEC3,
         VEC4
      };
   }
}
