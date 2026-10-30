package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.optifine.util.IntegratedServerUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;

public class RandomEntities {
   private static final Map<String, RandomEntityProperties> mapProperties = new HashMap<>();
   private static boolean active = false;
   private static RenderGlobal renderGlobal;
   private static final RandomEntity randomEntity = new RandomEntity();
   private static TileEntityRendererDispatcher tileEntityRendererDispatcher;
   private static final RandomTileEntity randomTileEntity = new RandomTileEntity();
   private static boolean working = false;
   private static final String[] DEPENDANT_SUFFIXES = new String[]{
      "_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar"
   };
   private static final String[] i = EntityHorse.horseTextures;
   private static final String[] j = EntityHorse.cj;

   public static void entityLoaded(Entity entity, World world) {
      if (world != null) {
         DataWatcher var4 = entity.getDataWatcher();
         var4.spawnPosition = entity.getPosition();
         var4.spawnBiome = world.getBiomeGenForCoords(var4.spawnPosition);
         UUID var5 = entity.getUniqueID();
         if (entity instanceof EntityVillager) {
            Method442(var5, (EntityVillager)entity);
         }
      }
   }

   public static void entityUnloaded(Entity entity, World world) {
   }

   private static void Method442(UUID uUID, EntityVillager entityVillager) {
      if (IntegratedServerUtils.getEntity(uUID) instanceof EntityVillager var6) {
         int var7 = var6.getProfession();
         entityVillager.setProfession(var7);
         entityVillager.careerId = var6.careerId;
         entityVillager.careerLevel = var6.careerLevel;
      }
   }

   public static void worldChanged(World world1, World world2) {
      if (world2 != null) {
         for (Entity var8 : world2.getLoadedEntityList()) {
            entityLoaded(var8, world2);
         }
      }

      randomEntity.setEntity(null);
      randomTileEntity.setTileEntity(null);
   }

   public static ResourceLocation getTextureLocation(ResourceLocation resourceLocation) {
      if (!active) {
         return resourceLocation;
      }

      if (working) {
         return resourceLocation;
      }

      ResourceLocation var4;
      try {
         working = true;
         IRandomEntity var5 = getRandomEntityRendered();
         if (var5 != null) {
            String var6 = resourceLocation.getResourcePath();
            if (var6.startsWith("horse/")) {
               var6 = getHorseTexturePath(var6, 6);
            }

            if (!var6.startsWith("textures/entity/") && !var6.startsWith("textures/painting/")) {
               return resourceLocation;
            }

            RandomEntityProperties var7 = mapProperties.get(var6);
            if (var7 == null) {
               return resourceLocation;
            }

            return var7.getTextureLocation(resourceLocation, var5);
         }

         var4 = resourceLocation;
      } finally {
         working = false;
      }

      return var4;
   }

   private static String getHorseTexturePath(String string, int integer) {
      if (i != null && j != null) {
         for (int var5 = 0; var5 < j.length; var5++) {
            String var6 = j[var5];
            if (string.startsWith(var6, integer)) {
               return i[var5];
            }
         }
      }

      return string;
   }

   private static IRandomEntity getRandomEntityRendered() {
      if (renderGlobal.renderedEntity != null) {
         randomEntity.setEntity(renderGlobal.renderedEntity);
         return randomEntity;
      }

      if (tileEntityRendererDispatcher.tileEntityRendered != null) {
         TileEntity var3 = tileEntityRendererDispatcher.tileEntityRendered;
         if (var3.getWorld() != null) {
            randomTileEntity.setTileEntity(var3);
            return randomTileEntity;
         }
      }

      return null;
   }

   private static RandomEntityProperties makeProperties(ResourceLocation resourceLocation, boolean boolean2) {
      String var5 = resourceLocation.getResourcePath();
      ResourceLocation var6 = getLocationProperties(resourceLocation, boolean2);
      if (var6 != null) {
         RandomEntityProperties var7 = parseProperties(var6, resourceLocation);
         if (var7 != null) {
            return var7;
         }
      }

      ResourceLocation[] var8 = getLocationsVariants(resourceLocation, boolean2);
      return var8 == null ? null : new RandomEntityProperties(var5, var8);
   }

   private static RandomEntityProperties parseProperties(ResourceLocation resourceLocation1, ResourceLocation resourceLocation2) {
      try {
         String var5 = resourceLocation1.getResourcePath();
         dbg(resourceLocation2.getResourcePath() + ", properties: " + var5);
         InputStream var6 = Config.getResourceStream(resourceLocation1);
         if (var6 == null) {
            warn("Properties not found: " + var5);
            return null;
         } else {
            PropertiesOrdered var7 = new PropertiesOrdered();
            var7.load(var6);
            var6.close();
            RandomEntityProperties var8 = new RandomEntityProperties(var7, var5, resourceLocation2);
            return !var8.isValid(var5) ? null : var8;
         }
      } catch (FileNotFoundException var9) {
         warn("File not found: " + resourceLocation2.getResourcePath());
         return null;
      } catch (IOException var10) {
         var10.printStackTrace();
         return null;
      }
   }

   private static ResourceLocation getLocationProperties(ResourceLocation resourceLocation, boolean boolean2) {
      ResourceLocation var5 = getLocationRandom(resourceLocation, boolean2);
      if (var5 == null) {
         return null;
      }

      String var6 = var5.getResourceDomain();
      String var8 = StrUtils.removeSuffix(var5.getResourcePath(), ".png");
      String var9 = var8 + ".properties";
      ResourceLocation var10 = new ResourceLocation(var6, var9);
      if (Config.hasResource(var10)) {
         return var10;
      }

      String var11 = getParentTexturePath(var8);
      if (var11 == null) {
         return null;
      }

      ResourceLocation var12 = new ResourceLocation(var6, var11 + ".properties");
      return Config.hasResource(var12) ? var12 : null;
   }

   protected static ResourceLocation getLocationRandom(ResourceLocation resourceLocation, boolean boolean2) {
      String var5 = resourceLocation.getResourceDomain();
      String var6 = resourceLocation.getResourcePath();
      String var7 = "textures/";
      String var8 = "optifine/random/";
      if (boolean2) {
         var7 = "textures/entity/";
         var8 = "mcpatcher/mob/";
      }

      if (!var6.startsWith(var7)) {
         return null;
      }

      String var9 = StrUtils.replacePrefix(var6, var7, var8);
      return new ResourceLocation(var5, var9);
   }

   private static String getPathBase(String string) {
      return string.startsWith("optifine/random/")
         ? StrUtils.replacePrefix(string, "optifine/random/", "textures/")
         : (string.startsWith("mcpatcher/mob/") ? StrUtils.replacePrefix(string, "mcpatcher/mob/", "textures/entity/") : null);
   }

   protected static ResourceLocation getLocationIndexed(ResourceLocation resourceLocation, int integer) {
      if (resourceLocation == null) {
         return null;
      }

      String var5 = resourceLocation.getResourcePath();
      int var6 = var5.lastIndexOf(46);
      if (var6 < 0) {
         return null;
      }

      String var7 = var5.substring(0, var6);
      String var8 = var5.substring(var6);
      String var9 = var7 + integer + var8;
      return new ResourceLocation(resourceLocation.getResourceDomain(), var9);
   }

   private static String getParentTexturePath(String string) {
      for (String var7 : DEPENDANT_SUFFIXES) {
         if (string.endsWith(var7)) {
            return StrUtils.removeSuffix(string, var7);
         }
      }

      return null;
   }

   private static ResourceLocation[] getLocationsVariants(ResourceLocation resourceLocation, boolean boolean2) {
      ArrayList<ResourceLocation> var5 = new ArrayList<ResourceLocation>();
      var5.add(resourceLocation);
      ResourceLocation var6 = getLocationRandom(resourceLocation, boolean2);
      if (var6 == null) {
         return null;
      }

      for (int var7 = 1; var7 < var5.size() + 10; var7++) {
         int var8 = var7 + 1;
         ResourceLocation var9 = getLocationIndexed(var6, var8);
         if (Config.hasResource(var9)) {
            var5.add(var9);
         }
      }

      if (var5.size() <= 1) {
         return null;
      }

      ResourceLocation[] var10 = var5.toArray(new ResourceLocation[0]);
      dbg(resourceLocation.getResourcePath() + ", variants: " + var10.length);
      return var10;
   }

   public static void update() {
      mapProperties.clear();
      active = false;
      if (Config.isRandomEntities()) {
         initialize();
      }
   }

   private static void initialize() {
      renderGlobal = Config.getRenderGlobal();
      tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
      String[] var3 = new String[]{"optifine/random/", "mcpatcher/mob/"};
      String[] var4 = new String[]{".png", ".properties"};
      String[] var5 = ResUtils.collectFiles(var3, var4);
      HashSet var6 = new HashSet();

      for (String var11 : var5) {
         String var12 = getPathBase(StrUtils.trimTrailing(StrUtils.removeSuffix(var11, var4), "0123456789") + ".png");
         if (!var6.contains(var12)) {
            var6.add(var12);
            ResourceLocation var13 = new ResourceLocation(var12);
            if (Config.hasResource(var13)) {
               RandomEntityProperties var14 = mapProperties.get(var12);
               if (var14 == null) {
                  var14 = makeProperties(var13, false);
                  if (var14 == null) {
                     var14 = makeProperties(var13, true);
                  }

                  if (var14 != null) {
                     mapProperties.put(var12, var14);
                  }
               }
            }
         }
      }

      active = !mapProperties.isEmpty();
   }

   public static void dbg(String string) {
      Config.Method251("RandomEntities: " + string);
   }

   public static void warn(String string) {
      Config.Method252("RandomEntities: " + string);
   }
}
