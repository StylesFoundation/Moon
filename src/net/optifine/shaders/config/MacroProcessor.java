package net.optifine.shaders.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.src.Config;
import net.optifine.shaders.Shaders;

public class MacroProcessor {
   public static InputStream process(InputStream inputStream, String string) throws IOException {
      String var5 = Config.readInputStream(inputStream, "ASCII");
      String var6 = getMacroHeader(var5);
      if (!var6.isEmpty()) {
         var5 = var6 + var5;
         if (Shaders.saveFinalShaders) {
            String var7 = string.replace(':', '/') + ".pre";
            Shaders.saveShader(var7, var5);
         }

         var5 = process(var5);
      }

      if (Shaders.saveFinalShaders) {
         Shaders.saveShader(string.replace(':', '/'), var5);
      }

      byte[] var9 = var5.getBytes(StandardCharsets.US_ASCII);
      return new ByteArrayInputStream(var9);
   }

   public static String process(String string) throws IOException {
      StringReader var4 = new StringReader(string);
      BufferedReader var5 = new BufferedReader(var4);
      MacroState var6 = new MacroState();
      StringBuilder var7 = new StringBuilder();

      while (true) {
         String var8 = var5.readLine();
         if (var8 == null) {
            return var7.toString();
         }

         if (var6.processLine(var8) && !MacroState.isMacroLine(var8)) {
            var7.append(var8);
            var7.append("\n");
         }
      }
   }

   private static String getMacroHeader(String string) throws IOException {
      StringBuilder var4 = new StringBuilder();
      ArrayList var6 = null;
      StringReader var7 = new StringReader(string);
      BufferedReader var8 = new BufferedReader(var7);

      while (true) {
         String var9 = var8.readLine();
         if (var9 == null) {
            return var4.toString();
         }

         if (MacroState.isMacroLine(var9)) {
            if (var4.length() == 0) {
               var4.append(ShaderMacros.getFixedMacroLines());
            }

            if (var6 == null) {
               var6 = new ArrayList<>(Arrays.asList(ShaderMacros.getExtensions()));
            }

            Iterator var10 = var6.iterator();

            while (var10.hasNext()) {
               ShaderMacro var11 = (ShaderMacro)var10.next();
               if (var9.contains(var11.getName())) {
                  var4.append(var11.getSourceLine());
                  var4.append("\n");
                  var10.remove();
               }
            }
         }
      }
   }

   private static List<ShaderOption> getMacroOptions() {
      ArrayList var3 = new ArrayList();

      for (ShaderOption var8 : Shaders.getShaderPackOptions()) {
         String var9 = var8.getSourceLine();
         if (var9 != null && var9.startsWith("#")) {
            var3.add(var8);
         }
      }

      return var3;
   }
}
