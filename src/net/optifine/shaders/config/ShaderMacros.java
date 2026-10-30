package net.optifine.shaders.config;

import net.minecraft.src.Config;
import net.minecraft.util.Util;
import net.optifine.shaders.Shaders;

public class ShaderMacros {
   private static ShaderMacro[] extensionMacros;

   public static String getOs() {
      Util.EnumOS var2 = Util.getOSType();
      switch (var2) {
         case WINDOWS:
            return "MC_OS_WINDOWS";
         case OSX:
            return "MC_OS_MAC";
         case LINUX:
            return "MC_OS_LINUX";
         default:
            return "MC_OS_OTHER";
      }
   }

   public static String getVendor() {
      String var3 = Config.f;
      if (var3 == null) {
         return "MC_GL_VENDOR_OTHER";
      }

      var3 = var3.toLowerCase();
      return var3.startsWith("ati")
         ? "MC_GL_VENDOR_ATI"
         : (
            var3.startsWith("intel")
               ? "MC_GL_VENDOR_INTEL"
               : (var3.startsWith("nvidia") ? "MC_GL_VENDOR_NVIDIA" : (var3.startsWith("x.org") ? "MC_GL_VENDOR_XORG" : "MC_GL_VENDOR_OTHER"))
         );
   }

   public static String getRenderer() {
      String var3 = Config.e;
      if (var3 == null) {
         return "MC_GL_RENDERER_OTHER";
      }

      var3 = var3.toLowerCase();
      return var3.startsWith("amd")
         ? "MC_GL_RENDERER_RADEON"
         : (
            var3.startsWith("ati")
               ? "MC_GL_RENDERER_RADEON"
               : (
                  var3.startsWith("radeon")
                     ? "MC_GL_RENDERER_RADEON"
                     : (
                        var3.startsWith("gallium")
                           ? "MC_GL_RENDERER_GALLIUM"
                           : (
                              var3.startsWith("intel")
                                 ? "MC_GL_RENDERER_INTEL"
                                 : (
                                    var3.startsWith("geforce")
                                       ? "MC_GL_RENDERER_GEFORCE"
                                       : (
                                          var3.startsWith("nvidia")
                                             ? "MC_GL_RENDERER_GEFORCE"
                                             : (
                                                var3.startsWith("quadro")
                                                   ? "MC_GL_RENDERER_QUADRO"
                                                   : (
                                                      var3.startsWith("nvs")
                                                         ? "MC_GL_RENDERER_QUADRO"
                                                         : (var3.startsWith("mesa") ? "MC_GL_RENDERER_MESA" : "MC_GL_RENDERER_OTHER")
                                                   )
                                             )
                                       )
                                 )
                           )
                     )
               )
         );
   }

   public static String getPrefixMacro() {
      return "MC_";
   }

   public static ShaderMacro[] getExtensions() {
      if (extensionMacros == null) {
         String[] var3 = Config.getOpenGlExtensions();
         ShaderMacro[] var4 = new ShaderMacro[var3.length];

         for (int var5 = 0; var5 < var3.length; var5++) {
            var4[var5] = new ShaderMacro("MC_" + var3[var5], "");
         }

         extensionMacros = var4;
      }

      return extensionMacros;
   }

   public static String getFixedMacroLines() {
      StringBuilder var2 = new StringBuilder();
      addMacroLine(var2, "MC_VERSION", Config.getMinecraftVersionInt());
      addMacroLine(var2, "MC_GL_VERSION " + Config.getGlVersion().toInt());
      addMacroLine(var2, "MC_GLSL_VERSION " + Config.getGlslVersion().toInt());
      addMacroLine(var2, getOs());
      addMacroLine(var2, getVendor());
      addMacroLine(var2, getRenderer());
      return var2.toString();
   }

   public static String getOptionMacroLines() {
      StringBuilder var3 = new StringBuilder();
      if (Shaders.configAntialiasingLevel > 0) {
         addMacroLine(var3, "MC_FXAA_LEVEL", Shaders.configAntialiasingLevel);
      }

      if (Shaders.configNormalMap) {
         addMacroLine(var3, "MC_NORMAL_MAP");
      }

      if (Shaders.configSpecularMap) {
         addMacroLine(var3, "MC_SPECULAR_MAP");
      }

      addMacroLine(var3, "MC_RENDER_QUALITY", Shaders.configRenderResMul);
      addMacroLine(var3, "MC_SHADOW_QUALITY", Shaders.configShadowResMul);
      addMacroLine(var3, "MC_HAND_DEPTH", Shaders.configHandDepthMul);
      if (Shaders.isOldHandLight()) {
         addMacroLine(var3, "MC_OLD_HAND_LIGHT");
      }

      if (Shaders.isOldLighting()) {
         addMacroLine(var3, "MC_OLD_LIGHTING");
      }

      return var3.toString();
   }

   private static void addMacroLine(StringBuilder stringBuilder, String string, int integer) {
      stringBuilder.append("#define ");
      stringBuilder.append(string);
      stringBuilder.append(" ");
      stringBuilder.append(integer);
      stringBuilder.append("\n");
   }

   private static void addMacroLine(StringBuilder stringBuilder, String string, float float3) {
      stringBuilder.append("#define ");
      stringBuilder.append(string);
      stringBuilder.append(" ");
      stringBuilder.append(float3);
      stringBuilder.append("\n");
   }

   private static void addMacroLine(StringBuilder stringBuilder, String string) {
      stringBuilder.append("#define ");
      stringBuilder.append(string);
      stringBuilder.append("\n");
   }
}
