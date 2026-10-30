package net.optifine.shaders.config;

public class ShaderMacro {
   private final String name;
   private final String value;

   public ShaderMacro(String string1, String string2) {
      this.name = string1;
      this.value = string2;
   }

   public String getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }

   public String getSourceLine() {
      return "#define " + this.name + " " + this.value;
   }

   @Override
   public String toString() {
      return this.getSourceLine();
   }
}
