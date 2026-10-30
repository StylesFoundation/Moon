package net.optifine.shaders;

import java.io.InputStream;

public class ShaderPackDefault implements IShaderPack {
   @Override
   public void close() {
   }

   @Override
   public InputStream getResourceAsStream(String string) {
      return ShaderPackDefault.class.getResourceAsStream(string);
   }

   @Override
   public String getName() {
      return "(internal)";
   }

   @Override
   public boolean hasDirectory(String string) {
      return false;
   }
}
