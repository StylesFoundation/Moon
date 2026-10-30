package net.optifine.shaders.config;

public class ScreenShaderOptions {
   private final String name;
   private final ShaderOption[] shaderOptions;
   private final int columns;

   public ScreenShaderOptions(String string, ShaderOption[] arr, int integer) {
      this.name = string;
      this.shaderOptions = arr;
      this.columns = integer;
   }

   public String getName() {
      return this.name;
   }

   public ShaderOption[] getShaderOptions() {
      return this.shaderOptions;
   }

   public int getColumns() {
      return this.columns;
   }
}
