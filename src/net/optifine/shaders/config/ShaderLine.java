package net.optifine.shaders.config;

import net.minecraft.src.Config;
import net.optifine.util.StrUtils;
import org.lwjgl.util.vector.Vector4f;

public class ShaderLine {
   private final int type;
   private final String name;
   private final String value;
   private final String line;

   public ShaderLine(int integer, String string2, String string3, String string4) {
      this.type = integer;
      this.name = string2;
      this.value = string3;
      this.line = string4;
   }

   public int getType() {
      return this.type;
   }

   public String getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }

   public boolean isUniform() {
      return this.type == 1;
   }

   public boolean isUniform(String string) {
      return this.isUniform() && string.equals(this.name);
   }

   public boolean isAttribute() {
      return this.type == 2;
   }

   public boolean isAttribute(String string) {
      return this.isAttribute() && string.equals(this.name);
   }

   public boolean isProperty() {
      return this.type == 6;
   }

   public boolean isConstInt() {
      return this.type == 3;
   }

   public boolean isConstFloat() {
      return this.type == 4;
   }

   public boolean isConstBool() {
      return this.type == 5;
   }

   public boolean isExtension() {
      return this.type == 7;
   }

   public boolean isConstVec4() {
      return this.type == 8;
   }

   public boolean isProperty(String string) {
      return this.isProperty() && string.equals(this.name);
   }

   public boolean isProperty(String string1, String string2) {
      return this.isProperty(string1) && string2.equals(this.value);
   }

   public boolean isConstInt(String string) {
      return this.isConstInt() && string.equals(this.name);
   }

   public boolean isConstIntSuffix(String string) {
      return this.isConstInt() && this.name.endsWith(string);
   }

   public boolean isConstFloat(String string) {
      return this.isConstFloat() && string.equals(this.name);
   }

   public boolean isConstBool(String string) {
      return this.isConstBool() && string.equals(this.name);
   }

   public boolean isExtension(String string) {
      return this.isExtension() && string.equals(this.name);
   }

   public boolean isConstBoolSuffix(String string) {
      return this.isConstBool() && this.name.endsWith(string);
   }

   public boolean isConstBoolSuffix(String string, boolean boolean2) {
      return this.isConstBoolSuffix(string) && this.getValueBool() == boolean2;
   }

   public boolean isConstBool(String string1, String string2) {
      return this.isConstBool(string1) || this.isConstBool(string2);
   }

   public boolean isConstBool(String string1, String string2, String string3) {
      return this.isConstBool(string1) || this.isConstBool(string2) || this.isConstBool(string3);
   }

   public boolean isConstBool(String string, boolean boolean2) {
      return this.isConstBool(string) && this.getValueBool() == boolean2;
   }

   public boolean isConstBool(String string1, String string2, boolean boolean3) {
      return this.isConstBool(string1, string2) && this.getValueBool() == boolean3;
   }

   public boolean isConstBool(String string1, String string2, String string3, boolean boolean4) {
      return this.isConstBool(string1, string2, string3) && this.getValueBool() == boolean4;
   }

   public boolean isConstVec4Suffix(String string) {
      return this.isConstVec4() && this.name.endsWith(string);
   }

   public int getValueInt() {
      try {
         return Integer.parseInt(this.value);
      } catch (NumberFormatException var1) {
         throw new NumberFormatException("Invalid integer: " + this.value + ", line: " + this.line);
      }
   }

   public float getValueFloat() {
      try {
         return Float.parseFloat(this.value);
      } catch (NumberFormatException var1) {
         throw new NumberFormatException("Invalid float: " + this.value + ", line: " + this.line);
      }
   }

   public Vector4f getValueVec4() {
      if (this.value == null) {
         return null;
      }

      String[] var5 = Config.tokenize(StrUtils.trim(StrUtils.removePrefix(this.value.trim(), "vec4"), " ()"), ", ");
      if (var5.length != 4) {
         return null;
      }

      float[] var6 = new float[4];

      for (int var7 = 0; var7 < var5.length; var7++) {
         float var9 = Config.parseFloat(StrUtils.removeSuffix(var5[var7], new String[]{"F", "f"}), Float.MAX_VALUE);
         if (var9 == Float.MAX_VALUE) {
            return null;
         }

         var6[var7] = var9;
      }

      return new Vector4f(var6[0], var6[1], var6[2], var6[3]);
   }

   public boolean getValueBool() {
      String var4 = this.value.toLowerCase();
      if (!var4.equals("true") && !var4.equals("false")) {
         throw new RuntimeException("Invalid boolean: " + this.value + ", line: " + this.line);
      } else {
         return Boolean.parseBoolean(this.value);
      }
   }
}
