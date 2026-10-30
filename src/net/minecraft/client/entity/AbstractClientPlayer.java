package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import com.moon.Moon;
import com.moon.events.impl.player.LookEvent;
import com.moon.module.impl.visual.Capes;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.optifine.player.CapeUtils;
import net.optifine.player.PlayerConfigurations;

public abstract class AbstractClientPlayer extends EntityPlayer {
   private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/moon/cape/cape.png");
   private NetworkPlayerInfo playerInfo;
   private ResourceLocation locationOfCape = null;
   private long reloadCapeTimeMs = 0L;
   private boolean elytraOfCape = false;
   private String nameClear = null;
   private Capes ck;

   public AbstractClientPlayer(World world, GameProfile gameProfile) {
      super(world, gameProfile);
      this.nameClear = gameProfile.getName();
      if (this.nameClear != null && !this.nameClear.isEmpty()) {
         this.nameClear = StringUtils.stripControlCodes(this.nameClear);
      }

      CapeUtils.downloadCape(this);
      PlayerConfigurations.getPlayerConfiguration(this);
   }

   public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocation, String string) {
      TextureManager var5 = Minecraft.getMinecraft().getTextureManager();
      ITextureObject var6 = var5.getTexture(resourceLocation);
      if (var6 == null) {
         var6 = new ThreadDownloadImageData(
            (File)null, String.format("http://minotar.net/skin/%s.png", string), DefaultPlayerSkin.getDefaultSkin(Method549(string)), new ImageBufferDownload()
         );
         var5.loadTexture(resourceLocation, var6);
      }

      return (ThreadDownloadImageData)var6;
   }

   private static String Method885(String string) {
      StringBuilder var4 = new StringBuilder();

      try {
         HttpURLConnection var5 = (HttpURLConnection)new URL(string).openConnection();
         var5.setRequestProperty(
            "User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36 OPR/73.0.3856.284 (Edition avira-2)"
         );
         var5.connect();
         BufferedReader var6 = new BufferedReader(new InputStreamReader(var5.getInputStream()));

         String var7;
         while ((var7 = var6.readLine()) != null) {
            var4.append(var7).append(System.lineSeparator());
         }

         var6.close();
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      return var4.toString();
   }

   public static ThreadDownloadImageData Method886(ResourceLocation resourceLocation, String string) {
      TextureManager var5 = Minecraft.getMinecraft().getTextureManager();
      ITextureObject var6 = var5.getTexture(resourceLocation);
      if (var6 == null) {
         var6 = new ThreadDownloadImageData(
            null,
            String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(string)),
            DefaultPlayerSkin.getDefaultSkin(Method549(string)),
            new ImageBufferDownload()
         );
         var5.loadTexture(resourceLocation, var6);
      }

      return (ThreadDownloadImageData)var6;
   }

   public static ResourceLocation getLocationSkin(String string) {
      return new ResourceLocation("skins/" + StringUtils.stripControlCodes(string));
   }

   @Override
   public boolean isSpectator() {
      NetworkPlayerInfo var4 = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getGameProfile().getId());
      return var4 != null && var4.getGameType() == WorldSettings.GameType.SPECTATOR;
   }

   public boolean hasPlayerInfo() {
      return this.getPlayerInfo() != null;
   }

   protected NetworkPlayerInfo getPlayerInfo() {
      if (this.playerInfo == null) {
         this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getUniqueID());
      }

      return this.playerInfo;
   }

   public boolean hasSkin() {
      NetworkPlayerInfo var4 = this.getPlayerInfo();
      return var4 != null && var4.hasLocationSkin();
   }

   public ResourceLocation getLocationSkin() {
      NetworkPlayerInfo var4 = this.getPlayerInfo();
      return var4 == null ? DefaultPlayerSkin.getDefaultSkin(this.getUniqueID()) : var4.getLocationSkin();
   }

   public ResourceLocation getLocationCape() {
      if (this.ck == null) {
         this.ck = Moon.getInstance().getModuleManager().getModule(Capes.class);
      }

      if (this.ck.isEnabled() && this.ck.shouldRenderCape(this)) {
         return TEXTURE_ELYTRA;
      }

      if (!Config.isShowCapes()) {
         return null;
      }

      if (this.reloadCapeTimeMs != 0L && System.currentTimeMillis() > this.reloadCapeTimeMs) {
         CapeUtils.reloadCape(this);
         this.reloadCapeTimeMs = 0L;
      }

      if (this.locationOfCape != null) {
         return this.locationOfCape;
      }

      NetworkPlayerInfo var4 = this.getPlayerInfo();
      return var4 == null ? null : var4.getLocationCape();
   }

   public String getSkinType() {
      NetworkPlayerInfo var4 = this.getPlayerInfo();
      return var4 == null ? DefaultPlayerSkin.getSkinType(this.getUniqueID()) : var4.getSkinType();
   }

   public float getFovModifier() {
      float var4 = 1.0F;
      if (this.capabilities.isFlying) {
         var4 = 1.1F;
      }

      double var6 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).Method286();
      var4 = (float)(var4 * ((var6 / this.capabilities.getWalkSpeed() + 1.0) / 2.0));
      if (this.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(var4) || Float.isInfinite(var4)) {
         var4 = 1.0F;
      }

      if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
         float var9 = this.getItemInUseDuration() / 20.0F;
         if (var9 > 1.0F) {
            var9 = 1.0F;
         } else {
            var9 *= var9;
         }

         var4 *= 1.0F - var9 * 0.15F;
      }

      return var4;
   }

   public String getNameClear() {
      return this.nameClear;
   }

   public ResourceLocation getLocationOfCape() {
      return this.locationOfCape;
   }

   public void setLocationOfCape(ResourceLocation resourceLocation) {
      this.locationOfCape = resourceLocation;
   }

   public boolean hasElytraCape() {
      ResourceLocation var4 = this.getLocationCape();
      return var4 != null && (var4 != this.locationOfCape || this.elytraOfCape);
   }

   public boolean isElytraOfCape() {
      return this.elytraOfCape;
   }

   public void setElytraOfCape(boolean boolean1) {
      this.elytraOfCape = boolean1;
   }

   public long getReloadCapeTimeMs() {
      return this.reloadCapeTimeMs;
   }

   public void setReloadCapeTimeMs(long long1) {
      this.reloadCapeTimeMs = long1;
   }

   @Override
   public Vec3 getLook(float float1) {
      if (this instanceof EntityPlayerSP var5) {
         float var11 = var5.rotationPitch;
         float var7 = var5.rotationYaw;
         LookEvent var8 = new LookEvent(var7, var11);
         Moon.getInstance().getEventBus().publish(var8);
         if (float1 == 1.0F) {
            return this.getVectorForRotation(var8.getPitch(), var8.getYaw());
         }

         float var9 = var5.cx + (var8.getPitch() - var5.cx) * float1;
         float var10 = var5.cw + (var8.getYaw() - var5.cw) * float1;
         return this.getVectorForRotation(var9, var10);
      } else {
         return this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
      }
   }
}
