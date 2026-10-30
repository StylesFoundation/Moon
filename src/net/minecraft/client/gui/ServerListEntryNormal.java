package net.minecraft.client.gui;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.image.BufferedImage;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry {
   private static final Logger logger = LogManager.getLogger();
   private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(
      5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build()
   );
   public static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_server.png");
   private static final ResourceLocation SERVER_SELECTION_BUTTONS = new ResourceLocation("textures/gui/server_selection.png");
   private final GuiMultiplayer owner;
   private final Minecraft mc;
   private final ServerData server;
   private final ResourceLocation serverIcon;
   private String field_148299_g;
   private DynamicTexture field_148305_h;
   private long field_148298_f;

   protected ServerListEntryNormal(GuiMultiplayer guiMultiplayer, ServerData serverData) {
      this.owner = guiMultiplayer;
      this.server = serverData;
      this.mc = Minecraft.getMinecraft();
      this.serverIcon = new ResourceLocation("servers/" + serverData.serverIP + "/icon");
      this.field_148305_h = (DynamicTexture)this.mc.getTextureManager().getTexture(this.serverIcon);
   }

   @Override
   public void drawEntry(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, boolean boolean8) {
      if (!this.server.field_78841_f) {
         this.server.field_78841_f = true;
         this.server.pingToServer = -2L;
         this.server.d = "";
         this.server.c = "";
         field_148302_b.submit(() -> {
            try {
               this.owner.getOldServerPinger().ping(this.server);
            } catch (UnknownHostException var1) {
               this.server.pingToServer = -1L;
               this.server.d = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
            } catch (Exception var2) {
               this.server.pingToServer = -1L;
               this.server.d = EnumChatFormatting.DARK_RED + "Can't connect to server.";
            }
         });
      }

      boolean var11 = this.server.version > 47;
      boolean var12 = this.server.version < 47;
      boolean var13 = var11 || var12;
      this.mc.fontRendererObj.drawString(this.server.serverName, integer2 + 32 + 3, integer3 + 1, 16777215);
      List var14 = this.mc.fontRendererObj.listFormattedStringToWidth(this.server.d, integer4 - 32 - 2);

      for (int var15 = 0; var15 < Math.min(var14.size(), 2); var15++) {
         this.mc.fontRendererObj.drawString((String)var14.get(var15), integer2 + 32 + 3, integer3 + 12 + this.mc.fontRendererObj.FONT_HEIGHT * var15, 8421504);
      }

      String var25 = var13 ? EnumChatFormatting.DARK_RED + this.server.g : this.server.c;
      int var16 = this.mc.fontRendererObj.getStringWidth(var25);
      this.mc.fontRendererObj.drawString(var25, integer2 + integer4 - var16 - 15 - 2, integer3 + 1, 8421504);
      byte var17 = 0;
      String var18 = null;
      int var19;
      String var20;
      if (var13) {
         var19 = 5;
         var20 = var11 ? "Client out of date!" : "Server out of date!";
         var18 = this.server.i;
      } else if (this.server.field_78841_f && this.server.pingToServer != -2L) {
         if (this.server.pingToServer < 0L) {
            var19 = 5;
         } else if (this.server.pingToServer < 150L) {
            var19 = 0;
         } else if (this.server.pingToServer < 300L) {
            var19 = 1;
         } else if (this.server.pingToServer < 600L) {
            var19 = 2;
         } else if (this.server.pingToServer < 1000L) {
            var19 = 3;
         } else {
            var19 = 4;
         }

         if (this.server.pingToServer < 0L) {
            var20 = "(no connection)";
         } else {
            var20 = this.server.pingToServer + "ms";
            var18 = this.server.i;
         }
      } else {
         var17 = 1;
         var19 = (int)(Minecraft.getSystemTime() / 100L + integer1 * 2L & 7L);
         if (var19 > 4) {
            var19 = 8 - var19;
         }

         var20 = "Pinging...";
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(Gui.icons);
      Gui.drawModalRectWithCustomSizedTexture(integer2 + integer4 - 15, integer3, var17 * 10, 176 + var19 * 8, 10, 8, 256.0F, 256.0F);
      if (this.server.getBase64EncodedIconData() != null && !this.server.getBase64EncodedIconData().equals(this.field_148299_g)) {
         this.field_148299_g = this.server.getBase64EncodedIconData();
         this.prepareServerIcon();
         this.owner.getServerList().saveServerList();
      }

      if (this.field_148305_h != null) {
         this.drawTextureAt(integer2, integer3, this.serverIcon);
      } else {
         this.drawTextureAt(integer2, integer3, UNKNOWN_SERVER);
      }

      int var21 = integer6 - integer2;
      int var22 = integer7 - integer3;
      if (var21 >= integer4 - 15 && var21 <= integer4 - 5 && var22 >= 0 && var22 <= 8) {
         this.owner.setHoveringText(var20);
      } else if (var21 >= integer4 - var16 - 15 - 2 && var21 <= integer4 - 15 - 2 && var22 >= 0 && var22 <= 8) {
         this.owner.setHoveringText(var18);
      }

      if (this.mc.gameSettings.touchscreen || boolean8) {
         this.mc.getTextureManager().bindTexture(SERVER_SELECTION_BUTTONS);
         Gui.drawRect(integer2, integer3, integer2 + 32, integer3 + 32, -1601138544);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         int var23 = integer6 - integer2;
         int var24 = integer7 - integer3;
         if (this.func_178013_b()) {
            if (var23 < 32 && var23 > 16) {
               Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.owner.func_175392_a(this, integer1)) {
            if (var23 < 16 && var24 < 16) {
               Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.owner.func_175394_b(this, integer1)) {
            if (var23 < 16 && var24 > 16) {
               Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }
      }
   }

   protected void drawTextureAt(int integer1, int integer2, ResourceLocation resourceLocation) {
      this.mc.getTextureManager().bindTexture(resourceLocation);
      GlStateManager.enableBlend();
      Gui.drawModalRectWithCustomSizedTexture(integer1, integer2, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      GlStateManager.disableBlend();
   }

   private boolean func_178013_b() {
      return true;
   }

   private void prepareServerIcon() {
      if (this.server.getBase64EncodedIconData() == null) {
         this.mc.getTextureManager().deleteTexture(this.serverIcon);
         this.field_148305_h = null;
      } else {
         ByteBuf var3 = Unpooled.copiedBuffer(this.server.getBase64EncodedIconData(), Charsets.UTF_8);
         ByteBuf var4 = Base64.decode(var3);

         BufferedImage var5;
         label67: {
            try {
               var5 = TextureUtil.readBufferedImage(new ByteBufInputStream(var4));
               Validate.validState(var5.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
               Validate.validState(var5.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
               break label67;
            } catch (Throwable var10) {
               logger.error("Invalid icon for server " + this.server.serverName + " (" + this.server.serverIP + ")", var10);
               this.server.setBase64EncodedIconData(null);
            } finally {
               var3.release();
               var4.release();
            }

            return;
         }

         if (this.field_148305_h == null) {
            this.field_148305_h = new DynamicTexture(var5.getWidth(), var5.getHeight());
            this.mc.getTextureManager().loadTexture(this.serverIcon, this.field_148305_h);
         }

         var5.getRGB(0, 0, var5.getWidth(), var5.getHeight(), this.field_148305_h.getTextureData(), 0, var5.getWidth());
         this.field_148305_h.updateDynamicTexture();
      }
   }

   @Override
   public boolean mousePressed(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      if (integer5 <= 32) {
         if (integer5 < 32 && integer5 > 16 && this.func_178013_b()) {
            this.owner.selectServer(integer1);
            this.owner.connectToSelected();
            return true;
         }

         if (integer5 < 16 && integer6 < 16 && this.owner.func_175392_a(this, integer1)) {
            this.owner.func_175391_a(this, integer1, GuiScreen.isShiftKeyDown());
            return true;
         }

         if (integer5 < 16 && integer6 > 16 && this.owner.func_175394_b(this, integer1)) {
            this.owner.func_175393_b(this, integer1, GuiScreen.isShiftKeyDown());
            return true;
         }
      }

      this.owner.selectServer(integer1);
      if (Minecraft.getSystemTime() - this.field_148298_f < 250L) {
         this.owner.connectToSelected();
      }

      this.field_148298_f = Minecraft.getSystemTime();
      return false;
   }

   @Override
   public void setSelected(int integer1, int integer2, int integer3) {
   }

   @Override
   public void mouseReleased(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
   }

   public ServerData getServerData() {
      return this.server;
   }
}
