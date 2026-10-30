package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TeleportToTeam implements ISpectatorMenuView, ISpectatorMenuObject {
   private final List<ISpectatorMenuObject> field_178672_a = Lists.newArrayList();

   public TeleportToTeam() {
      for (ScorePlayerTeam var3 : Minecraft.getMinecraft().theWorld.getScoreboard().getTeams()) {
         this.field_178672_a.add(new TeleportToTeam.TeamSelectionObject(var3));
      }
   }

   @Override
   public List<ISpectatorMenuObject> func_178669_a() {
      return this.field_178672_a;
   }

   @Override
   public IChatComponent func_178670_b() {
      return new ChatComponentText("Select a team to teleport to");
   }

   @Override
   public void func_178661_a(SpectatorMenu spectatorMenu) {
      spectatorMenu.func_178647_a(this);
   }

   @Override
   public IChatComponent getSpectatorName() {
      return new ChatComponentText("Teleport to team member");
   }

   @Override
   public void func_178663_a(float float1, int integer) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
      Gui.drawModalRectWithCustomSizedTexture(0, 0, 16.0F, 0.0F, 16, 16, 256.0F, 256.0F);
   }

   @Override
   public boolean func_178662_A_() {
      for (ISpectatorMenuObject var2 : this.field_178672_a) {
         if (var2.func_178662_A_()) {
            return true;
         }
      }

      return false;
   }

   static class TeamSelectionObject implements ISpectatorMenuObject {
      private final ScorePlayerTeam field_178676_b;
      private final ResourceLocation field_178677_c;
      private final List<NetworkPlayerInfo> field_178675_d;

      public TeamSelectionObject(ScorePlayerTeam scorePlayerTeam) {
         this.field_178676_b = scorePlayerTeam;
         this.field_178675_d = Lists.newArrayList();

         for (String var3 : scorePlayerTeam.getMembershipCollection()) {
            NetworkPlayerInfo var4 = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(var3);
            if (var4 != null) {
               this.field_178675_d.add(var4);
            }
         }

         if (!this.field_178675_d.isEmpty()) {
            String var5 = this.field_178675_d.get(new Random().nextInt(this.field_178675_d.size())).getGameProfile().getName();
            this.field_178677_c = AbstractClientPlayer.getLocationSkin(var5);
            AbstractClientPlayer.Method886(this.field_178677_c, var5);
         } else {
            this.field_178677_c = DefaultPlayerSkin.getDefaultSkinLegacy();
         }
      }

      @Override
      public void func_178661_a(SpectatorMenu spectatorMenu) {
         spectatorMenu.func_178647_a(new TeleportToPlayer(this.field_178675_d));
      }

      @Override
      public IChatComponent getSpectatorName() {
         return new ChatComponentText(this.field_178676_b.getTeamName());
      }

      @Override
      public void func_178663_a(float float1, int integer) {
         int var5 = -1;
         String var6 = FontRenderer.getFormatFromString(this.field_178676_b.getColorPrefix());
         if (var6.length() >= 2) {
            var5 = Minecraft.getMinecraft().fontRendererObj.getColorCode(var6.charAt(1));
         }

         if (var5 >= 0) {
            float var7 = (var5 >> 16 & 0xFF) / 255.0F;
            float var8 = (var5 >> 8 & 0xFF) / 255.0F;
            float var9 = (var5 & 0xFF) / 255.0F;
            Gui.drawRect(1, 1, 15, 15, MathHelper.func_180183_b(var7 * float1, var8 * float1, var9 * float1) | integer << 24);
         }

         Minecraft.getMinecraft().getTextureManager().bindTexture(this.field_178677_c);
         GlStateManager.color(float1, float1, float1, integer / 255.0F);
         Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
         Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
      }

      @Override
      public boolean func_178662_A_() {
         return !this.field_178675_d.isEmpty();
      }
   }
}
