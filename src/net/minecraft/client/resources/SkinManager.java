package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class SkinManager {
   private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
   private final TextureManager textureManager;
   private final File skinCacheDir;
   private final MinecraftSessionService sessionService;
   private final LoadingCache<GameProfile, Map<Type, MinecraftProfileTexture>> skinCacheLoader;

   public SkinManager(TextureManager textureManager, File file, MinecraftSessionService minecraftSessionService) {
      this.textureManager = textureManager;
      this.skinCacheDir = file;
      this.sessionService = minecraftSessionService;
      this.skinCacheLoader = CacheBuilder.newBuilder()
         .expireAfterAccess(15L, TimeUnit.SECONDS)
         .build(new CacheLoader<GameProfile, Map<Type, MinecraftProfileTexture>>() {
            public Map<Type, MinecraftProfileTexture> load(GameProfile gameProfile) throws Exception {
               return Minecraft.getMinecraft().getSessionService().getTextures(gameProfile, false);
            }
         });
   }

   public ResourceLocation loadSkin(MinecraftProfileTexture minecraftProfileTexture, Type type) {
      return this.loadSkin(minecraftProfileTexture, type, null);
   }

   public ResourceLocation loadSkin(MinecraftProfileTexture minecraftProfileTexture, Type type, SkinManager.SkinAvailableCallback skinAvailableCallback) {
      final ResourceLocation var7 = new ResourceLocation("skins/" + minecraftProfileTexture.getHash());
      ITextureObject var8 = this.textureManager.getTexture(var7);
      if (var8 != null) {
         if (skinAvailableCallback != null) {
            skinAvailableCallback.skinAvailable(type, var7, minecraftProfileTexture);
         }
      } else {
         File var9 = new File(this.skinCacheDir, minecraftProfileTexture.getHash().length() > 2 ? minecraftProfileTexture.getHash().substring(0, 2) : "xx");
         File var10 = new File(var9, minecraftProfileTexture.getHash());
         final ImageBufferDownload var11 = type == Type.SKIN ? new ImageBufferDownload() : null;
         ThreadDownloadImageData var12 = new ThreadDownloadImageData(
            var10, minecraftProfileTexture.getUrl(), DefaultPlayerSkin.getDefaultSkinLegacy(), new IImageBuffer() {
               @Override
               public BufferedImage parseUserSkin(BufferedImage bufferedImage) {
                  if (var11 != null) {
                     bufferedImage = var11.parseUserSkin(bufferedImage);
                  }

                  return bufferedImage;
               }

               @Override
               public void skinAvailable() {
                  if (var11 != null) {
                     var11.skinAvailable();
                  }

                  if (skinAvailableCallback != null) {
                     skinAvailableCallback.skinAvailable(type, var7, minecraftProfileTexture);
                  }
               }
            }
         );
         this.textureManager.loadTexture(var7, var12);
      }

      return var7;
   }

   public void loadProfileTextures(GameProfile gameProfile, SkinManager.SkinAvailableCallback skinAvailableCallback, boolean boolean3) {
      THREAD_POOL.submit(() -> {
         HashMap var5 = Maps.newHashMap();

         try {
            var5.putAll(this.sessionService.getTextures(gameProfile, boolean3));
         } catch (InsecureTextureException var6) {
         }

         if (var5.isEmpty() && gameProfile.getId().equals(Minecraft.getMinecraft().getSession().getProfile().getId())) {
            gameProfile.getProperties().clear();
            gameProfile.getProperties().putAll(Minecraft.getMinecraft().getProfileProperties());
            var5.putAll(this.sessionService.getTextures(gameProfile, false));
         }

         Minecraft.getMinecraft().addScheduledTask(() -> {
            if (var5.containsKey(Type.SKIN)) {
               this.loadSkin((MinecraftProfileTexture)var5.get(Type.SKIN), Type.SKIN, skinAvailableCallback);
            }

            if (var5.containsKey(Type.CAPE)) {
               this.loadSkin((MinecraftProfileTexture)var5.get(Type.CAPE), Type.CAPE, skinAvailableCallback);
            }
         });
      });
   }

   public Map<Type, MinecraftProfileTexture> loadSkinFromCache(GameProfile gameProfile) {
      return (Map<Type, MinecraftProfileTexture>)this.skinCacheLoader.getUnchecked(gameProfile);
   }

   public interface SkinAvailableCallback {
      void skinAvailable(Type type, ResourceLocation resourceLocation, MinecraftProfileTexture minecraftProfileTexture);
   }
}
