package com.moon.module.impl.combat;

import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class Teams extends Module {
   private final BooleanSetting checkName = BooleanSetting.builder("name").defaults("Name", true).build();
   private final BooleanSetting checkScoreboardTeam = BooleanSetting.builder("team").defaults("Scoreboard team", true).build();
   private final BooleanSetting checkWool = BooleanSetting.builder("wool").defaults("Check for wool", true).build();

   public Teams(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   public boolean isOnSameTeam(EntityPlayer player) {
      EnumChatFormatting localColor = this.getNameColor(mc.thePlayer);
      EnumChatFormatting targetColor = this.getNameColor(player);
      if (localColor != null && localColor == targetColor && this.checkName.getValue()) {
         return true;
      } else if (mc.thePlayer.getTeam() != null && this.checkScoreboardTeam.getValue() && player.isOnSameTeam(mc.thePlayer)) {
         return true;
      } else {
         ItemStack localHelmet = mc.thePlayer.inventory.armorInventory[3];
         if (localHelmet != null && this.checkWool.getValue()) {
            Item helmetItem = localHelmet.getItem();
            return helmetItem instanceof ItemBlock && ItemStack.areItemStacksEqual(localHelmet, player.inventory.armorInventory[3]);
         } else {
            return false;
         }
      }
   }

   private EnumChatFormatting getNameColor(EntityPlayer player) {
      if (player.getDisplayName() == null) {
         return null;
      }

      for (EnumChatFormatting formatting : EnumChatFormatting.values()) {
         if (formatting != EnumChatFormatting.RESET && player.getDisplayName().getFormattedText().substring(0, 2).contains(formatting.toString())) {
            return formatting;
         }
      }

      return null;
   }
}
