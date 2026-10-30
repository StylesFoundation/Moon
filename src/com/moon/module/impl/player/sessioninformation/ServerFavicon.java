package com.moon.module.impl.player.sessioninformation;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.image.BufferedImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;

public class ServerFavicon {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final ResourceLocation iconLocation;
   private final ServerData serverData;
   private DynamicTexture dynamicTexture;
   private boolean hasIcon = false;

   public ServerFavicon(ServerData serverData) {
      this.serverData = serverData;
      this.iconLocation = new ResourceLocation("servers/" + serverData.serverIP + "/icon");
      ITextureObject texture = mc.getTextureManager().getTexture(this.iconLocation);
      if (texture != null) {
         this.dynamicTexture = (DynamicTexture)texture;
      }

      this.loadIcon();
   }

   private void loadIcon() {
      if (this.serverData.getBase64EncodedIconData() == null) {
         mc.getTextureManager().deleteTexture(this.iconLocation);
      } else {
         ByteBuf byteBuf = Unpooled.copiedBuffer(this.serverData.getBase64EncodedIconData(), Charsets.UTF_8);
         ByteBuf decodedBuffer = Base64.decode(byteBuf);

         BufferedImage image;
         label80: {
            try {
               image = TextureUtil.readBufferedImage(new ByteBufInputStream(decodedBuffer));
               Validate.validState(image.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
               Validate.validState(image.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
               break label80;
            } catch (Throwable throwable) {
               this.hasIcon = false;
               this.serverData.setBase64EncodedIconData(null);
            } finally {
               byteBuf.release();
               decodedBuffer.release();
            }

            return;
         }

         if (this.dynamicTexture == null) {
            DynamicTexture newTexture = new DynamicTexture(image.getWidth(), image.getHeight());
            mc.getTextureManager().loadTexture(this.iconLocation, newTexture);
         }

         if (this.dynamicTexture == null) {
            return;
         }

         image.getRGB(0, 0, image.getWidth(), image.getHeight(), this.dynamicTexture.getTextureData(), 0, image.getWidth());
         this.dynamicTexture.updateDynamicTexture();
         this.hasIcon = true;
      }
   }

   public ResourceLocation getIconLocation() {
      return this.iconLocation;
   }

   public ServerData getServerData() {
      return this.serverData;
   }

   public DynamicTexture getDynamicTexture() {
      return this.dynamicTexture;
   }

   public boolean hasIcon() {
      return this.hasIcon;
   }
}
