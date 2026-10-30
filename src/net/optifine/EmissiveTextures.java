package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.util.PropertiesOrdered;

public class EmissiveTextures {
   private static String suffixEmissive = null;
   private static String suffixEmissivePng = null;
   private static boolean active = false;
   private static boolean render = false;
   private static boolean hasEmissive = false;
   private static boolean renderEmissive = false;
   private static float lightMapX;
   private static float lightMapY;
   private static final ResourceLocation LOCATION_EMPTY = new ResourceLocation("mcpatcher/ctm/default/empty.png");

   public static boolean isActive() {
      return active;
   }

   public static String getSuffixEmissive() {
      return suffixEmissive;
   }

   public static void beginRender() {
      render = true;
      hasEmissive = false;
   }

   public static ITextureObject getEmissiveTexture(ITextureObject iTextureObject, Map<ResourceLocation, ITextureObject> map) {
      if (!render) {
         return iTextureObject;
      }

      if (iTextureObject instanceof SimpleTexture var5) {
         ResourceLocation var6 = var5.locationEmissive;
         if (!renderEmissive) {
            if (var6 != null) {
               hasEmissive = true;
            }

            return iTextureObject;
         } else {
            if (var6 == null) {
               var6 = LOCATION_EMPTY;
            }

            ITextureObject var7 = (ITextureObject)map.get(var6);
            if (var7 == null) {
               var7 = new SimpleTexture(var6);
               Config.getTextureManager().loadTexture(var6, var7);
            }

            return var7;
         }
      } else {
         return iTextureObject;
      }
   }

   public static boolean hasEmissive() {
      return hasEmissive;
   }

   public static void beginRenderEmissive() {
      lightMapX = OpenGlHelper.lastBrightnessX;
      lightMapY = OpenGlHelper.lastBrightnessY;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, lightMapY);
      renderEmissive = true;
   }

   public static void endRenderEmissive() {
      renderEmissive = false;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightMapX, lightMapY);
   }

   public static void endRender() {
      render = false;
      hasEmissive = false;
   }

   public static void update() {
      active = false;
      suffixEmissive = null;
      suffixEmissivePng = null;
      if (Config.isEmissiveTextures()) {
         try {
            String var3 = "optifine/emissive.properties";
            InputStream var5 = Config.getResourceStream(new ResourceLocation(var3));
            if (var5 == null) {
               return;
            }

            dbg("Loading " + "optifine/emissive.properties");
            PropertiesOrdered var6 = new PropertiesOrdered();
            var6.load(var5);
            var5.close();
            suffixEmissive = var6.getProperty("suffix.emissive");
            if (suffixEmissive != null) {
               suffixEmissivePng = suffixEmissive + ".png";
            }

            active = suffixEmissive != null;
         } catch (FileNotFoundException var7) {
         } catch (IOException var8) {
            var8.printStackTrace();
         }
      }
   }

   private static void dbg(String string) {
      Config.Method251("EmissiveTextures: " + string);
   }

   private static void warn(String string) {
      Config.Method252("EmissiveTextures: " + string);
   }

   public static boolean isEmissive(ResourceLocation resourceLocation) {
      return suffixEmissivePng != null && resourceLocation.getResourcePath().endsWith(suffixEmissivePng);
   }

   public static void loadTexture(ResourceLocation resourceLocation, SimpleTexture simpleTexture) {
      if (resourceLocation != null && simpleTexture != null) {
         simpleTexture.isEmissive = false;
         simpleTexture.locationEmissive = null;
         if (suffixEmissivePng != null) {
            String var5 = resourceLocation.getResourcePath();
            if (var5.endsWith(".png")) {
               if (var5.endsWith(suffixEmissivePng)) {
                  simpleTexture.isEmissive = true;
               } else {
                  String var6 = var5.substring(0, var5.length() - 4) + suffixEmissivePng;
                  ResourceLocation var7 = new ResourceLocation(resourceLocation.getResourceDomain(), var6);
                  if (Config.hasResource(var7)) {
                     simpleTexture.locationEmissive = var7;
                  }
               }
            }
         }
      }
   }
}
