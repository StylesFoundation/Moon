package net.minecraft.client.gui;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import com.moon.Moon;
import com.moon.module.impl.visual.Camera;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;

public class GuiPlayerTabOverlay extends Gui {
   private static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new GuiPlayerTabOverlay.PlayerComparator());
   private final Minecraft mc;
   private final GuiIngame guiIngame;
   private IChatComponent footer;
   private IChatComponent header;
   private long lastTimeOpened;
   private boolean isBeingRendered;
   private Camera m;

   public GuiPlayerTabOverlay(Minecraft minecraft, GuiIngame guiIngame) {
      this.mc = minecraft;
      this.guiIngame = guiIngame;
   }

   public String getPlayerName(NetworkPlayerInfo networkPlayerInfo) {
      String var2 = networkPlayerInfo.getDisplayName() != null
         ? networkPlayerInfo.getDisplayName().getFormattedText()
         : ScorePlayerTeam.formatPlayerName(networkPlayerInfo.getPlayerTeam(), networkPlayerInfo.getGameProfile().getName());
      return var2;
   }

   public void updatePlayerList(boolean boolean1) {
      if (boolean1 && !this.isBeingRendered) {
         this.lastTimeOpened = Minecraft.getSystemTime();
      }

      this.isBeingRendered = boolean1;
   }

   public void renderPlayerlist(int integer, Scoreboard scoreboard, ScoreObjective scoreObjective) {
      if (this.m == null) {
         this.m = Moon.getInstance().getModuleManager().getModule(Camera.class);
      }

      NetHandlerPlayClient var6 = this.mc.thePlayer.sendQueue;
      List<NetworkPlayerInfo> var7 = field_175252_a.sortedCopy(var6.getPlayerInfoMap());
      int var8 = 0;
      int var9 = 0;

      for (NetworkPlayerInfo var11 : var7) {
         int var12 = this.mc.fontRendererObj.getStringWidth(this.getPlayerName(var11));
         var8 = Math.max(var8, var12);
         if (scoreObjective != null && scoreObjective.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
            var12 = this.mc.fontRendererObj.getStringWidth(" " + scoreboard.Method281(var11.getGameProfile().getName(), scoreObjective).getScorePoints());
            var9 = Math.max(var9, var12);
         }
      }

      var7 = var7.subList(0, Math.min(var7.size(), 80));
      int var36 = var7.size();
      int var37 = var36;

      int var39;
      for (var39 = 1; var37 > 20; var37 = (var36 + var39 - 1) / var39) {
         var39++;
      }

      boolean var13 = this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted();
      int var14;
      if (scoreObjective != null) {
         if (scoreObjective.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
            var14 = 90;
         } else {
            var14 = var9;
         }
      } else {
         var14 = 0;
      }

      int var15 = Math.min(var39 * ((var13 ? 9 : 0) + var8 + var14 + 13), integer - 50) / var39;
      int var16 = integer / 2 - (var15 * var39 + (var39 - 1) * 5) / 2;
      int var17 = 10;
      int var18 = var15 * var39 + (var39 - 1) * 5;
      List<String> var19 = null;
      List<String> var20 = null;
      if (this.header != null) {
         var19 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), integer - 50);

         for (String var22 : var19) {
            var18 = Math.max(var18, this.mc.fontRendererObj.getStringWidth(var22));
         }
      }

      if (this.footer != null) {
         var20 = this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), integer - 50);

         for (String var45 : var20) {
            var18 = Math.max(var18, this.mc.fontRendererObj.getStringWidth(var45));
         }
      }

      if (var19 != null) {
         drawRect(integer / 2 - var18 / 2 - 1, 9, integer / 2 + var18 / 2 + 1, var17 + var19.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

         for (String var46 : var19) {
            int var23 = this.mc.fontRendererObj.getStringWidth(var46);
            this.mc.fontRendererObj.drawStringWithShadow(var46, integer / 2 - var23 / 2, var17, -1);
            var17 += this.mc.fontRendererObj.FONT_HEIGHT;
         }

         var17++;
      }

      drawRect(integer / 2 - var18 / 2 - 1, var17 - 1, integer / 2 + var18 / 2 + 1, var17 + var37 * 9, Integer.MIN_VALUE);

      for (int var43 = 0; var43 < var36; var43++) {
         int var47 = var43 / var37;
         int var49 = var43 % var37;
         int var24 = var16 + var47 * var15 + var47 * 5;
         int var25 = var17 + var49 * 9;
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         drawRect(var24, var25, var24 + var15, var25 + 8, this.m.limitHudFps.getValue() ? -1602980748 : 553648127);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         if (var43 < var7.size()) {
            NetworkPlayerInfo var26 = (NetworkPlayerInfo)var7.get(var43);
            String var27 = this.getPlayerName(var26);
            GameProfile var28 = var26.getGameProfile();
            if (var13) {
               EntityPlayer var29 = this.mc.theWorld.getPlayerEntityByUUID(var28.getId());
               boolean var30 = var29 != null
                  && var29.isWearing(EnumPlayerModelParts.CAPE)
                  && (var28.getName().equals("Dinnerbone") || var28.getName().equals("Grumm"));
               this.mc.getTextureManager().bindTexture(var26.getLocationSkin());
               int var31 = 8 + (var30 ? 8 : 0);
               int var32 = 8 * (var30 ? -1 : 1);
               Gui.drawScaledCustomSizeModalRect(var24, var25, 8.0F, var31, 8, var32, 8, 8, 64.0F, 64.0F);
               if (var29 != null && var29.isWearing(EnumPlayerModelParts.HAT)) {
                  int var33 = 8 + (var30 ? 8 : 0);
                  int var34 = 8 * (var30 ? -1 : 1);
                  Gui.drawScaledCustomSizeModalRect(var24, var25, 40.0F, var33, 8, var34, 8, 8, 64.0F, 64.0F);
               }

               var24 += 9;
            }

            if (var26.getGameType() == WorldSettings.GameType.SPECTATOR) {
               var27 = EnumChatFormatting.ITALIC + var27;
               this.mc.fontRendererObj.drawStringWithShadow(var27, var24, var25, -1862270977);
            } else {
               this.mc.fontRendererObj.drawStringWithShadow(var27, var24, var25, -1);
            }

            if (scoreObjective != null && var26.getGameType() != WorldSettings.GameType.SPECTATOR) {
               int var52 = var24 + var8 + 1;
               int var53 = var52 + var14;
               if (var53 - var52 > 5) {
                  this.drawScoreboardValues(scoreObjective, var25, var28.getName(), var52, var53, var26);
               }
            }

            this.drawPing(var15, var24 - (var13 ? 9 : 0), var25, var26);
         }
      }

      if (var20 != null) {
         var17 = var17 + var37 * 9 + 1;
         drawRect(
            integer / 2 - var18 / 2 - 1, var17 - 1, integer / 2 + var18 / 2 + 1, var17 + var20.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE
         );

         for (String var48 : var20) {
            int var50 = this.mc.fontRendererObj.getStringWidth(var48);
            this.mc.fontRendererObj.drawStringWithShadow(var48, integer / 2 - var50 / 2, var17, -1);
            var17 += this.mc.fontRendererObj.FONT_HEIGHT;
         }
      }
   }

   protected void drawPing(int integer1, int integer2, int integer3, NetworkPlayerInfo networkPlayerInfo) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(icons);
      byte var8;
      if (networkPlayerInfo.getResponseTime() < 0) {
         var8 = 5;
      } else if (networkPlayerInfo.getResponseTime() < 150) {
         var8 = 0;
      } else if (networkPlayerInfo.getResponseTime() < 300) {
         var8 = 1;
      } else if (networkPlayerInfo.getResponseTime() < 600) {
         var8 = 2;
      } else if (networkPlayerInfo.getResponseTime() < 1000) {
         var8 = 3;
      } else {
         var8 = 4;
      }

      this.zLevel += 100.0F;
      this.drawTexturedModalRect(integer2 + integer1 - 11, integer3, 0, 176 + var8 * 8, 10, 8);
      this.zLevel -= 100.0F;
   }

   private void drawScoreboardValues(
      ScoreObjective scoreObjective, int integer2, String string, int integer4, int integer5, NetworkPlayerInfo networkPlayerInfo
   ) {
      int var9 = scoreObjective.getScoreboard().Method281(string, scoreObjective).getScorePoints();
      if (scoreObjective.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
         this.mc.getTextureManager().bindTexture(icons);
         if (this.lastTimeOpened == networkPlayerInfo.func_178855_p()) {
            if (var9 < networkPlayerInfo.func_178835_l()) {
               networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
               networkPlayerInfo.func_178844_b(this.guiIngame.getUpdateCounter() + 20);
            } else if (var9 > networkPlayerInfo.func_178835_l()) {
               networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
               networkPlayerInfo.func_178844_b(this.guiIngame.getUpdateCounter() + 10);
            }
         }

         if (Minecraft.getSystemTime() - networkPlayerInfo.func_178847_n() > 1000L || this.lastTimeOpened != networkPlayerInfo.func_178855_p()) {
            networkPlayerInfo.func_178836_b(var9);
            networkPlayerInfo.func_178857_c(var9);
            networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
         }

         networkPlayerInfo.func_178843_c(this.lastTimeOpened);
         networkPlayerInfo.func_178836_b(var9);
         int var10 = MathHelper.ceiling_float_int(Math.max(var9, networkPlayerInfo.func_178860_m()) / 2.0F);
         int var11 = Math.max(MathHelper.ceiling_float_int(var9 / 2), Math.max(MathHelper.ceiling_float_int(networkPlayerInfo.func_178860_m() / 2), 10));
         boolean var12 = networkPlayerInfo.func_178858_o() > this.guiIngame.getUpdateCounter()
            && (networkPlayerInfo.func_178858_o() - this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L;
         if (var10 > 0) {
            float var13 = Math.min((float)(integer5 - integer4 - 4) / var11, 9.0F);
            if (var13 > 3.0F) {
               for (int var14 = var10; var14 < var11; var14++) {
                  this.drawTexturedModalRect(integer4 + var14 * var13, integer2, var12 ? 25 : 16, 0, 9, 9);
               }

               for (int var18 = 0; var18 < var10; var18++) {
                  this.drawTexturedModalRect(integer4 + var18 * var13, integer2, var12 ? 25 : 16, 0, 9, 9);
                  if (var12) {
                     if (var18 * 2 + 1 < networkPlayerInfo.func_178860_m()) {
                        this.drawTexturedModalRect(integer4 + var18 * var13, integer2, 70, 0, 9, 9);
                     }

                     if (var18 * 2 + 1 == networkPlayerInfo.func_178860_m()) {
                        this.drawTexturedModalRect(integer4 + var18 * var13, integer2, 79, 0, 9, 9);
                     }
                  }

                  if (var18 * 2 + 1 < var9) {
                     this.drawTexturedModalRect(integer4 + var18 * var13, integer2, var18 >= 10 ? 160 : 52, 0, 9, 9);
                  }

                  if (var18 * 2 + 1 == var9) {
                     this.drawTexturedModalRect(integer4 + var18 * var13, integer2, var18 >= 10 ? 169 : 61, 0, 9, 9);
                  }
               }
            } else {
               float var19 = MathHelper.clamp_float(var9 / 20.0F, 0.0F, 1.0F);
               int var15 = (int)((1.0F - var19) * 255.0F) << 16 | (int)(var19 * 255.0F) << 8;
               String var16 = var9 / 2.0F + "";
               if (integer5 - this.mc.fontRendererObj.getStringWidth(var16 + "hp") >= integer4) {
                  var16 = var16 + "hp";
               }

               this.mc
                  .fontRendererObj
                  .drawStringWithShadow(var16, (integer5 + integer4) / 2 - this.mc.fontRendererObj.getStringWidth(var16) / 2, integer2, var15);
            }
         }
      } else {
         String var17 = "" + EnumChatFormatting.YELLOW + var9;
         this.mc.fontRendererObj.drawStringWithShadow(var17, integer5 - this.mc.fontRendererObj.getStringWidth(var17), integer2, 16777215);
      }
   }

   public void setFooter(IChatComponent iChatComponent) {
      this.footer = iChatComponent;
   }

   public void setHeader(IChatComponent iChatComponent) {
      this.header = iChatComponent;
   }

   public void resetFooterHeader() {
      this.header = null;
      this.footer = null;
   }

   static class PlayerComparator implements Comparator<NetworkPlayerInfo> {
      private PlayerComparator() {
      }

      public int compare(NetworkPlayerInfo networkPlayerInfo1, NetworkPlayerInfo networkPlayerInfo2) {
         ScorePlayerTeam var3 = networkPlayerInfo1.getPlayerTeam();
         ScorePlayerTeam var4 = networkPlayerInfo2.getPlayerTeam();
         return ComparisonChain.start()
            .compareTrueFirst(
               networkPlayerInfo1.getGameType() != WorldSettings.GameType.SPECTATOR, networkPlayerInfo2.getGameType() != WorldSettings.GameType.SPECTATOR
            )
            .compare(var3 != null ? var3.getRegisteredName() : "", var4 != null ? var4.getRegisteredName() : "")
            .compare(networkPlayerInfo1.getGameProfile().getName(), networkPlayerInfo2.getGameProfile().getName())
            .result();
      }
   }
}
