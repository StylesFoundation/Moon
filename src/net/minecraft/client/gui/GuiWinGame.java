package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiWinGame extends GuiScreen {
   private static final Logger logger = LogManager.getLogger();
   private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
   private int field_146581_h;
   private List<String> field_146582_i;
   private int field_146579_r;
   private final float C = 0.5F;

   @Override
   public void onUpdateScreen() {
      MusicTicker var3 = this.mc.getMusicTicker();
      SoundHandler var4 = this.mc.getSoundHandler();
      if (this.field_146581_h == 0) {
         var3.func_181557_a();
         var3.func_181558_a(MusicTicker.MusicType.CREDITS);
         var4.resumeSounds();
      }

      var4.update();
      this.field_146581_h++;
      float var5 = (this.field_146579_r + this.height + this.height + 24) / 0.5F;
      if (this.field_146581_h > var5) {
         this.sendRespawnPacket();
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (keyCode == 1) {
         this.sendRespawnPacket();
      }
   }

   private void sendRespawnPacket() {
      this.mc.thePlayer.sendQueue.Method754(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
      this.mc.displayGuiScreen(null);
   }

   @Override
   public boolean doesGuiPauseGame() {
      return true;
   }

   @Override
   public void onInitGui() {
      if (this.field_146582_i == null) {
         this.field_146582_i = Lists.newArrayList();

         try {
            String var4 = "" + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + EnumChatFormatting.GREEN + EnumChatFormatting.AQUA;
            short var5 = 274;
            InputStream var6 = this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt")).getInputStream();
            BufferedReader var7 = new BufferedReader(new InputStreamReader(var6, StandardCharsets.UTF_8));
            Random var8 = new Random(8124371L);

            String var3;
            while ((var3 = var7.readLine()) != null) {
               var3 = var3.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());

               while (var3.contains(var4)) {
                  int var11 = var3.indexOf(var4);
                  String var9 = var3.substring(0, var11);
                  String var10 = var3.substring(var11 + var4.length());
                  var3 = var9 + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, var8.nextInt(4) + 3) + var10;
               }

               this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(var3, var5));
               this.field_146582_i.add("");
            }

            var6.close();

            for (int var18 = 0; var18 < 8; var18++) {
               this.field_146582_i.add("");
            }

            var6 = this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream();
            var7 = new BufferedReader(new InputStreamReader(var6, StandardCharsets.UTF_8));

            while ((var3 = var7.readLine()) != null) {
               var3 = var3.replaceAll("PLAYERNAME", this.mc.getSession().getUsername()).replaceAll("\t", "    ");
               this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(var3, var5));
               this.field_146582_i.add("");
            }

            var6.close();
            this.field_146579_r = this.field_146582_i.size() * 12;
         } catch (Exception var12) {
            logger.error("Couldn't load credits", var12);
         }
      }
   }

   private void drawWinGameScreen(int integer1, int integer2, float float3) {
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
      var7.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      int var8 = this.width;
      float var9 = 0.0F - (this.field_146581_h + float3) * 0.5F * 0.5F;
      float var10 = this.height - (this.field_146581_h + float3) * 0.5F * 0.5F;
      float var12 = (this.field_146581_h + float3 - 0.0F) * 0.02F;
      float var14 = ((this.field_146579_r + this.height + this.height + 24) / 0.5F - 20.0F - (this.field_146581_h + float3)) * 0.005F;
      if (var14 < var12) {
         var12 = var14;
      }

      if (var12 > 1.0F) {
         var12 = 1.0F;
      }

      var12 = var12 * var12 * 96.0F / 255.0F;
      var7.pos(0.0, this.height, this.zLevel).tex(0.0, var9 * 0.015625F).color(var12, var12, var12, 1.0F).endVertex();
      var7.pos(var8, this.height, this.zLevel).tex(var8 * 0.015625F, var9 * 0.015625F).color(var12, var12, var12, 1.0F).endVertex();
      var7.pos(var8, 0.0, this.zLevel).tex(var8 * 0.015625F, var10 * 0.015625F).color(var12, var12, var12, 1.0F).endVertex();
      var7.pos(0.0, 0.0, this.zLevel).tex(0.0, var10 * 0.015625F).color(var12, var12, var12, 1.0F).endVertex();
      var6.draw();
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawWinGameScreen(mouseX, mouseY, partialTicks);
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      short var8 = 274;
      int var9 = this.width / 2 - 137;
      int var10 = this.height + 50;
      float var11 = -(this.field_146581_h + partialTicks) * 0.5F;
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, var11, 0.0F);
      this.mc.getTextureManager().bindTexture(MINECRAFT_LOGO);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.drawTexturedModalRect(var9, var10, 0, 0, 155, 44);
      this.drawTexturedModalRect(var9 + 155, var10, 0, 45, 155, 44);
      int var12 = var10 + 200;

      for (int var13 = 0; var13 < this.field_146582_i.size(); var13++) {
         if (var13 == this.field_146582_i.size() - 1) {
            float var14 = var12 + var11 - (this.height / 2 - 6);
            if (var14 < 0.0F) {
               GlStateManager.translate(0.0F, -var14, 0.0F);
            }
         }

         if (var12 + var11 + 12.0F + 8.0F > 0.0F && var12 + var11 < this.height) {
            String var16 = this.field_146582_i.get(var13);
            if (var16.startsWith("[C]")) {
               this.fontRendererObj
                  .drawStringWithShadow(var16.substring(3), var9 + (var8 - this.fontRendererObj.getStringWidth(var16.substring(3))) / 2, var12, 16777215);
            } else {
               this.fontRendererObj.fontRandom.setSeed(var13 * 4238972211L + this.field_146581_h / 4);
               this.fontRendererObj.drawStringWithShadow(var16, var9, var12, 16777215);
            }
         }

         var12 += 12;
      }

      GlStateManager.popMatrix();
      this.mc.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(0, 769);
      int var15 = this.width;
      int var17 = this.height;
      var7.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var7.pos(0.0, var17, this.zLevel).tex(0.0, 1.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var7.pos(var15, var17, this.zLevel).tex(1.0, 1.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var7.pos(var15, 0.0, this.zLevel).tex(1.0, 0.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var7.pos(0.0, 0.0, this.zLevel).tex(0.0, 0.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var6.draw();
      GlStateManager.disableBlend();
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }
}
