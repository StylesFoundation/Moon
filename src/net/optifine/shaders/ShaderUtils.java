package net.optifine.shaders;

import net.minecraft.src.Config;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderProfile;

public class ShaderUtils {
   public static ShaderOption getShaderOption(String string, ShaderOption[] arr) {
      if (arr != null) {
         for (ShaderOption var8 : arr) {
            if (var8.getName().equals(string)) {
               return var8;
            }
         }
      }

      return null;
   }

   public static ShaderProfile detectProfile(ShaderProfile[] profiles, ShaderOption[] options, boolean boolean3) {
      if (profiles != null) {
         for (ShaderProfile var9 : profiles) {
            if (matchProfile(var9, options, boolean3)) {
               return var9;
            }
         }
      }

      return null;
   }

   public static boolean matchProfile(ShaderProfile shaderProfile, ShaderOption[] arr, boolean boolean3) {
      if (shaderProfile == null) {
         return false;
      }

      if (arr == null) {
         return false;
      }

      for (String var10 : shaderProfile.getOptions()) {
         ShaderOption var11 = getShaderOption(var10, arr);
         if (var11 != null) {
            String var12 = boolean3 ? var11.getValueDefault() : var11.getValue();
            String var13 = shaderProfile.getValue(var10);
            if (!Config.equals(var12, var13)) {
               return false;
            }
         }
      }

      return true;
   }
}
