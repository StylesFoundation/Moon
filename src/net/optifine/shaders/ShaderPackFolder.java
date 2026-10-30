package net.optifine.shaders;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import net.optifine.util.StrUtils;

public class ShaderPackFolder implements IShaderPack {
   protected File packFile;

   public ShaderPackFolder(String string, File file) {
      this.packFile = file;
   }

   @Override
   public void close() {
   }

   @Override
   public InputStream getResourceAsStream(String string) {
      try {
         String var4 = StrUtils.removePrefixSuffix(string, "/", "/");
         File var5 = new File(this.packFile, var4);
         return !var5.exists() ? null : new BufferedInputStream(new FileInputStream(var5));
      } catch (Exception var6) {
         return null;
      }
   }

   @Override
   public boolean hasDirectory(String string) {
      File var5 = new File(this.packFile, string.substring(1));
      return var5.exists() && var5.isDirectory();
   }

   @Override
   public String getName() {
      return this.packFile.getName();
   }
}
