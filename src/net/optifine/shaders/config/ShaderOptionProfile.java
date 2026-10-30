package net.optifine.shaders.config;

import java.util.ArrayList;
import net.optifine.Lang;
import net.optifine.shaders.ShaderUtils;
import net.optifine.shaders.Shaders;

public class ShaderOptionProfile extends ShaderOption {
   private ShaderProfile[] profiles = null;
   private ShaderOption[] options = null;

   public ShaderOptionProfile(ShaderProfile[] profiles, ShaderOption[] options) {
      super("<profile>", "", detectProfileName(profiles, options), getProfileNames(profiles), detectProfileName(profiles, options, true), null);
      this.profiles = profiles;
      this.options = options;
   }

   @Override
   public void nextValue() {
      super.nextValue();
      if (this.getValue().equals("<custom>")) {
         super.nextValue();
      }

      this.applyProfileOptions();
   }

   public void updateProfile() {
      if (!ShaderUtils.matchProfile(this.getProfile(this.getValue()), this.options, false)) {
         String var4 = detectProfileName(this.profiles, this.options);
         this.setValue(var4);
      }
   }

   private void applyProfileOptions() {
      ShaderProfile var4 = this.getProfile(this.getValue());
      if (var4 != null) {
         for (String var9 : var4.getOptions()) {
            ShaderOption var10 = this.getOption(var9);
            if (var10 != null) {
               String var11 = var4.getValue(var9);
               var10.setValue(var11);
            }
         }
      }
   }

   private ShaderOption getOption(String string) {
      for (ShaderOption var8 : this.options) {
         if (var8.getName().equals(string)) {
            return var8;
         }
      }

      return null;
   }

   private ShaderProfile getProfile(String string) {
      for (ShaderProfile var8 : this.profiles) {
         if (var8.getName().equals(string)) {
            return var8;
         }
      }

      return null;
   }

   @Override
   public String getNameText() {
      return Lang.get("of.shaders.profile");
   }

   @Override
   public String getValueText(String string) {
      return string.equals("<custom>") ? Lang.get("of.general.custom", "<custom>") : Shaders.translate("profile." + string, string);
   }

   @Override
   public String getValueColor(String string) {
      return string.equals("<custom>") ? "§c" : "§a";
   }

   @Override
   public String getDescriptionText() {
      String var4 = Shaders.translate("profile.comment", null);
      if (var4 != null) {
         return var4;
      }

      StringBuilder var5 = new StringBuilder();
      ShaderProfile[] var6 = this.profiles;
      int var7 = var6.length;

      for (int var8 = 0; var8 < var7; var8++) {
         String var10 = var6[var8].getName();
         if (var10 != null) {
            String var11 = Shaders.translate("profile." + var10 + ".comment", null);
            if (var11 != null) {
               var5.append(var11);
               if (!var11.endsWith(". ")) {
                  var5.append(". ");
               }
            }
         }
      }

      return var5.toString();
   }

   private static String detectProfileName(ShaderProfile[] profiles, ShaderOption[] options) {
      return detectProfileName(profiles, options, false);
   }

   private static String detectProfileName(ShaderProfile[] profiles, ShaderOption[] options, boolean boolean3) {
      ShaderProfile var6 = ShaderUtils.detectProfile(profiles, options, boolean3);
      return var6 == null ? "<custom>" : var6.getName();
   }

   private static String[] getProfileNames(ShaderProfile[] arr) {
      ArrayList<String> var4 = new ArrayList<>();

      for (ShaderProfile var8 : arr) {
         var4.add(var8.getName());
      }

      var4.add("<custom>");
      return var4.toArray(new String[0]);
   }
}
