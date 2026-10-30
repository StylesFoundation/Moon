package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.optifine.config.ConnectedParser;
import net.optifine.config.EntityClassLocator;
import net.optifine.config.IObjectLocator;
import net.optifine.config.ItemLocator;
import net.optifine.util.PropertiesOrdered;

public class DynamicLights {
   private static final DynamicLightsMap mapDynamicLights = new DynamicLightsMap();
   private static final Map<Class, Integer> mapEntityLightLevels = new HashMap<>();
   private static final Map<Item, Integer> mapItemLightLevels = new HashMap<>();
   private static long timeUpdateMs = 0L;
   private static boolean initialized;

   public static void entityAdded(Entity entity, RenderGlobal renderGlobal) {
   }

   public static void entityRemoved(Entity entity, RenderGlobal renderGlobal) {
      synchronized (mapDynamicLights) {
         DynamicLight var6 = mapDynamicLights.remove(entity.getEntityId());
         if (var6 != null) {
            var6.updateLitChunks(renderGlobal);
         }
      }
   }

   public static void update(RenderGlobal renderGlobal) {
      long var4 = System.currentTimeMillis();
      if (var4 >= timeUpdateMs + 50L) {
         timeUpdateMs = var4;
         if (!initialized) {
            initialize();
         }

         synchronized (mapDynamicLights) {
            updateMapDynamicLights(renderGlobal);
            if (mapDynamicLights.size() > 0) {
               for (DynamicLight var9 : mapDynamicLights.valueList()) {
                  var9.update(renderGlobal);
               }
            }
         }
      }
   }

   private static void initialize() {
      initialized = true;
      mapEntityLightLevels.clear();
      mapItemLightLevels.clear();
      if (mapEntityLightLevels.size() > 0) {
         Config.Method251("DynamicLights entities: " + mapEntityLightLevels.size());
      }

      if (mapItemLightLevels.size() > 0) {
         Config.Method251("DynamicLights items: " + mapItemLightLevels.size());
      }
   }

   private static void Method522(InputStream inputStream, String string2, String string3) {
      if (inputStream != null) {
         try {
            PropertiesOrdered var5 = new PropertiesOrdered();
            var5.load(inputStream);
            inputStream.close();
            Config.Method251("DynamicLights: Parsing " + string2);
            ConnectedParser var6 = new ConnectedParser("DynamicLights");
            loadModLightLevels(var5.getProperty("entities"), mapEntityLightLevels, new EntityClassLocator(), var6, string2, string3);
            loadModLightLevels(var5.getProperty("items"), mapItemLightLevels, new ItemLocator(), var6, string2, string3);
         } catch (IOException var7) {
            Config.Method252("DynamicLights: Error reading " + string2);
         }
      }
   }

   private static void loadModLightLevels(
      String string1, Map map, IObjectLocator iObjectLocator, ConnectedParser connectedParser, String string5, String string6
   ) {
      if (string1 != null) {
         for (String var13 : Config.tokenize(string1, " ")) {
            String[] var14 = Config.tokenize(var13, ":");
            if (var14.length != 2) {
               connectedParser.warn("Invalid entry: " + var13 + ", in:" + string5);
            } else {
               String var15 = var14[0];
               String var16 = var14[1];
               String var17 = string6 + ":" + var15;
               ResourceLocation var18 = new ResourceLocation(var17);
               Object var19 = iObjectLocator.getObject(var18);
               if (var19 == null) {
                  connectedParser.warn("Object not found: " + var17);
               } else {
                  int var20 = connectedParser.parseInt(var16, -1);
                  if (var20 >= 0 && var20 <= 15) {
                     map.put(var19, var20);
                  } else {
                     connectedParser.warn("Invalid light level: " + var13);
                  }
               }
            }
         }
      }
   }

   private static void updateMapDynamicLights(RenderGlobal renderGlobal) {
      WorldClient var4 = renderGlobal.getWorld();
      if (var4 != null) {
         for (Entity var6 : var4.getLoadedEntityList()) {
            int var7 = getLightLevel(var6);
            if (var7 > 0) {
               int var8 = var6.getEntityId();
               DynamicLight var9 = mapDynamicLights.get(var8);
               if (var9 == null) {
                  var9 = new DynamicLight(var6);
                  mapDynamicLights.put(var8, var9);
               }
            } else {
               int var10 = var6.getEntityId();
               DynamicLight var12 = mapDynamicLights.remove(var10);
               if (var12 != null) {
                  var12.updateLitChunks(renderGlobal);
               }
            }
         }
      }
   }

   public static int getCombinedLight(BlockPos blockPos, int integer) {
      return getCombinedLight(getLightLevel(blockPos), integer);
   }

   public static int getCombinedLight(Entity entity, int integer) {
      return getCombinedLight(getLightLevel(entity), integer);
   }

   public static int getCombinedLight(double double1, int integer) {
      if (double1 > 0.0) {
         int var6 = (int)(double1 * 16.0);
         int var7 = integer & 0xFF;
         if (var6 > var7) {
            integer &= -256;
            integer |= var6;
         }
      }

      return integer;
   }

   public static double getLightLevel(BlockPos blockPos) {
      double var4 = 0.0;
      synchronized (mapDynamicLights) {
         List<DynamicLight> var7 = mapDynamicLights.valueList();
         var7.size();

         for (DynamicLight var10 : var7) {
            int var11 = var10.getLastLightLevel();
            if (var11 > 0) {
               double var12 = var10.getLastPosX();
               double var14 = var10.getLastPosY();
               double var16 = var10.getLastPosZ();
               double var18 = blockPos.getX() - var12;
               double var20 = blockPos.getY() - var14;
               double var22 = blockPos.getZ() - var16;
               double var24 = var18 * var18 + var20 * var20 + var22 * var22;
               if (var10.isUnderwater() && !Config.isClearWater()) {
                  var11 = Config.Method255(var11 - 2, 0, 15);
                  var24 *= 2.0;
               }

               if (var24 <= 56.25) {
                  double var26 = Math.sqrt(var24);
                  double var30 = (1.0 - var26 / 7.5) * var11;
                  if (var30 > var4) {
                     var4 = var30;
                  }
               }
            }
         }
      }

      return Config.Method257(var4, 0.0, 15.0);
   }

   public static int getLightLevel(ItemStack itemStack) {
      if (itemStack == null) {
         return 0;
      }

      Item var4 = itemStack.getItem();
      if (var4 instanceof ItemBlock var5) {
         Block var6 = var5.getBlock();
         if (var6 != null) {
            return var6.getLightValue();
         }
      }

      if (var4 == Items.lava_bucket) {
         return Blocks.lava.getLightValue();
      }

      if (var4 == Items.blaze_rod || var4 == Items.blaze_powder) {
         return 10;
      }

      if (var4 == Items.glowstone_dust) {
         return 8;
      }

      if (var4 == Items.prismarine_crystals) {
         return 8;
      }

      if (var4 == Items.magma_cream) {
         return 8;
      }

      if (var4 == Items.nether_star) {
         return Blocks.beacon.getLightValue() / 2;
      }

      if (!mapItemLightLevels.isEmpty()) {
         Integer var7 = mapItemLightLevels.get(var4);
         if (var7 != null) {
            return var7;
         }
      }

      return 0;
   }

   public static int getLightLevel(Entity entity) {
      if (entity == Config.getMinecraft().getRenderViewEntity() && !Config.isDynamicHandLight()) {
         return 0;
      } else if (entity instanceof EntityPlayer var4 && var4.isSpectator()) {
         return 0;
      } else {
         if (entity.isBurning()) {
            return 15;
         }

         if (!mapEntityLightLevels.isEmpty()) {
            Integer var12 = mapEntityLightLevels.get(entity.getClass());
            if (var12 != null) {
               return var12;
            }
         }

         if (entity instanceof EntityFireball) {
            return 15;
         } else if (entity instanceof EntityTNTPrimed) {
            return 15;
         } else if (entity instanceof EntityBlaze var13) {
            return var13.func_70845_n() ? 15 : 10;
         } else if (entity instanceof EntityMagmaCube var5) {
            return var5.squishFactor > 0.6 ? 13 : 8;
         } else if (entity instanceof EntityCreeper var6 && var6.getCreeperFlashIntensity(0.0F) > 0.001) {
            return 15;
         } else if (entity instanceof EntityLivingBase var14) {
            int var9 = getLightLevel(var14.getHeldItem());
            int var11 = getLightLevel(var14.getEquipmentInSlot(4));
            return Math.max(var9, var11);
         } else {
            return entity instanceof EntityItem ? getLightLevel(getItemStack((EntityItem)entity)) : 0;
         }
      }
   }

   public static void removeLights(RenderGlobal renderGlobal) {
      synchronized (mapDynamicLights) {
         for (DynamicLight var7 : mapDynamicLights.valueList()) {
            var7.updateLitChunks(renderGlobal);
         }

         mapDynamicLights.clear();
      }
   }

   public static void clear() {
      synchronized (mapDynamicLights) {
         mapDynamicLights.clear();
      }
   }

   public static int getCount() {
      synchronized (mapDynamicLights) {
         return mapDynamicLights.size();
      }
   }

   public static ItemStack getItemStack(EntityItem entityItem) {
      return entityItem.getDataWatcher().getWatchableObjectItemStack(10);
   }
}
