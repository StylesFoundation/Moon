package net.optifine.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.optifine.Lang;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.gui.GuiButtonShaderOption;
import net.optifine.util.StrUtils;

public class TooltipProviderShaderOptions extends TooltipProviderOptions {
   @Override
   public String[] getTooltipLines(GuiButton guiButton, int integer) {
      if (guiButton instanceof GuiButtonShaderOption var6) {
         ShaderOption var7 = var6.getShaderOption();
         return this.makeTooltipLines(var7, integer);
      } else {
         return null;
      }
   }

   private String[] makeTooltipLines(ShaderOption shaderOption, int integer) {
      String var6 = shaderOption.getNameText();
      String var7 = Config.normalize(shaderOption.getDescriptionText()).trim();
      String[] var8 = this.splitDescription(var7);
      GameSettings var9 = Config.getGameSettings();
      String var10 = null;
      if (!var6.equals(shaderOption.getName()) && var9.advancedItemTooltips) {
         var10 = "§8" + Lang.get("of.general.id") + ": " + shaderOption.getName();
      }

      String var11 = null;
      if (shaderOption.getPaths() != null && var9.advancedItemTooltips) {
         var11 = "§8" + Lang.get("of.general.from") + ": " + Config.arrayToString(shaderOption.getPaths());
      }

      String var12 = null;
      if (shaderOption.getValueDefault() != null && var9.advancedItemTooltips) {
         String var13 = shaderOption.isEnabled() ? shaderOption.getValueText(shaderOption.getValueDefault()) : Lang.get("of.general.ambiguous");
         var12 = "§8" + Lang.getDefault() + ": " + var13;
      }

      ArrayList var14 = new ArrayList();
      var14.add(var6);
      var14.addAll(Arrays.asList(var8));
      if (var10 != null) {
         var14.add(var10);
      }

      if (var11 != null) {
         var14.add(var11);
      }

      if (var12 != null) {
         var14.add(var12);
      }

      return this.makeTooltipLines(integer, var14);
   }

   private String[] splitDescription(String string) {
      if (string.length() == 0) {
         return new String[0];
      }

      String[] var5 = StrUtils.removePrefix(string, "//").split("\\. ");

      for (int var6 = 0; var6 < var5.length; var6++) {
         var5[var6] = "- " + var5[var6].trim();
         var5[var6] = StrUtils.removeSuffix(var5[var6], ".");
      }

      return var5;
   }

   private String[] makeTooltipLines(int integer, List<String> list) {
      FontRenderer var6 = Config.getMinecraft().fontRendererObj;
      ArrayList<String> var7 = new ArrayList<String>();

      for (String var9 : list) {
         if (var9 != null && var9.length() > 0) {
            var7.addAll(var6.listFormattedStringToWidth(var9, integer));
         }
      }

      return var7.toArray(new String[0]);
   }
}
