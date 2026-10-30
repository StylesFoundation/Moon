package net.optifine.shaders;

import com.google.common.base.Joiner;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.src.Config;
import net.optifine.util.StrUtils;

public class ShaderPackZip implements IShaderPack {
   protected File packFile;
   protected ZipFile packZipFile;
   protected String baseFolder;

   public ShaderPackZip(String string, File file) {
      this.packFile = file;
      this.packZipFile = null;
      this.baseFolder = "";
   }

   @Override
   public void close() {
      if (this.packZipFile != null) {
         try {
            this.packZipFile.close();
         } catch (Exception var1) {
         }

         this.packZipFile = null;
      }
   }

   @Override
   public InputStream getResourceAsStream(String string) {
      try {
         if (this.packZipFile == null) {
            this.packZipFile = new ZipFile(this.packFile);
            this.baseFolder = this.detectBaseFolder(this.packZipFile);
         }

         String var5 = StrUtils.removePrefix(string, "/");
         if (var5.contains("..")) {
            var5 = this.resolveRelative(var5);
         }

         ZipEntry var6 = this.packZipFile.getEntry(this.baseFolder + var5);
         return var6 == null ? null : this.packZipFile.getInputStream(var6);
      } catch (Exception var7) {
         return null;
      }
   }

   private String resolveRelative(String string) {
      ArrayDeque var5 = new ArrayDeque();

      for (String var10 : Config.tokenize(string, "/")) {
         if (var10.equals("..")) {
            if (var5.isEmpty()) {
               return "";
            }

            var5.removeLast();
         } else {
            var5.add(var10);
         }
      }

      return Joiner.on('/').join(var5);
   }

   private String detectBaseFolder(ZipFile zipFile) {
      ZipEntry var5 = zipFile.getEntry("shaders/");
      if (var5 == null || !var5.isDirectory()) {
         Pattern var6 = Pattern.compile("([^/]+/)shaders/");
         Enumeration var7 = zipFile.entries();

         while (var7.hasMoreElements()) {
            String var9 = ((ZipEntry)var7.nextElement()).getName();
            Matcher var10 = var6.matcher(var9);
            if (var10.matches()) {
               String var11 = var10.group(1);
               if (var11 != null) {
                  if (var11.equals("shaders/")) {
                     return "";
                  }

                  return var11;
               }
            }
         }
      }

      return "";
   }

   @Override
   public boolean hasDirectory(String string) {
      try {
         if (this.packZipFile == null) {
            this.packZipFile = new ZipFile(this.packFile);
            this.baseFolder = this.detectBaseFolder(this.packZipFile);
         }

         String var5 = StrUtils.removePrefix(string, "/");
         ZipEntry var6 = this.packZipFile.getEntry(this.baseFolder + var5);
         return var6 != null;
      } catch (IOException var7) {
         return false;
      }
   }

   @Override
   public String getName() {
      return this.packFile.getName();
   }
}
