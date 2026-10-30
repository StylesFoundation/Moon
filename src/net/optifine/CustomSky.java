package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.optifine.render.Blender;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.TextureUtils;

public class CustomSky {
   private static CustomSkyLayer[][] worldSkyLayers = null;

   public static void reset() {
      worldSkyLayers = null;
   }

   public static void update() {
      reset();
      if (Config.isCustomSky()) {
         worldSkyLayers = readCustomSkies();
      }
   }

   private static CustomSkyLayer[][] readCustomSkies() {
      CustomSkyLayer[][] var3 = new CustomSkyLayer[10][0];
      String var4 = "mcpatcher/sky/world";
      int var5 = -1;

      for (int var6 = 0; var6 < var3.length; var6++) {
         String var7 = var4 + var6 + "/sky";
         ArrayList<CustomSkyLayer> var8 = new ArrayList<CustomSkyLayer>();

         for (int var9 = 1; var9 < 1000; var9++) {
            String var10 = var7 + var9 + ".properties";

            try {
               InputStream var12 = Config.getResourceStream(new ResourceLocation(var10));
               if (var12 == null) {
                  break;
               }

               PropertiesOrdered var13 = new PropertiesOrdered();
               var13.load(var12);
               var12.close();
               Config.Method251("CustomSky properties: " + var10);
               String var14 = var7 + var9 + ".png";
               CustomSkyLayer var15 = new CustomSkyLayer(var13, var14);
               if (var15.isValid(var10)) {
                  ResourceLocation var16 = new ResourceLocation(var15.source);
                  ITextureObject var17 = TextureUtils.getTexture(var16);
                  if (var17 == null) {
                     Config.log("CustomSky: Texture not found: " + var16);
                  } else {
                     var15.textureId = var17.getGlTextureId();
                     var8.add(var15);
                     var12.close();
                  }
               }
            } catch (FileNotFoundException var18) {
               break;
            } catch (IOException var19) {
               var19.printStackTrace();
            }
         }

         if (var8.size() > 0) {
            CustomSkyLayer[] var21 = var8.toArray(new CustomSkyLayer[0]);
            var3[var6] = var21;
            var5 = var6;
         }
      }

      if (var5 < 0) {
         return null;
      }

      CustomSkyLayer[][] var20 = new CustomSkyLayer[var5 + 1][0];
      System.arraycopy(var3, 0, var20, 0, var20.length);
      return var20;
   }

   public static void renderSky(World world, TextureManager textureManager, float float3) {
      if (worldSkyLayers != null) {
         int var6 = world.provider.getDimensionId();
         if (var6 >= 0 && var6 < worldSkyLayers.length) {
            CustomSkyLayer[] var7 = worldSkyLayers[var6];
            if (var7 != null) {
               int var10 = (int)(world.getWorldTime() % 24000L);
               float var11 = world.getCelestialAngle(float3);
               float var12 = world.getRainStrength(float3);
               float var13 = world.getThunderStrength(float3);
               if (var12 > 0.0F) {
                  var13 /= var12;
               }

               for (CustomSkyLayer var17 : var7) {
                  if (var17.isActive(world, var10)) {
                     var17.render(world, var10, var11, var12, var13);
                  }
               }

               Blender.clearBlend(1.0F - var12);
            }
         }
      }
   }

   public static boolean hasSkyLayers(World world) {
      if (worldSkyLayers == null) {
         return false;
      } else {
         int var4 = world.provider.getDimensionId();
         if (var4 >= 0 && var4 < worldSkyLayers.length) {
            CustomSkyLayer[] var5 = worldSkyLayers[var4];
            return var5 != null && var5.length > 0;
         } else {
            return false;
         }
      }
   }
}
