package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.entity.Entity;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.optifine.override.PlayerControllerOF;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;

public class CustomGuis {
   private static final Minecraft mc = Config.getMinecraft();
   private static PlayerControllerOF b = null;
   private static CustomGuiProperties[][] guiProperties = null;
   public static boolean isChristmas = isChristmas();

   public static ResourceLocation getTextureLocation(ResourceLocation resourceLocation) {
      if (guiProperties == null) {
         return resourceLocation;
      }

      GuiScreen var4 = mc.currentScreen;
      if (!(var4 instanceof GuiContainer)) {
         return resourceLocation;
      }

      if (!resourceLocation.getResourceDomain().equals("minecraft") || !resourceLocation.getResourcePath().startsWith("textures/gui/")) {
         return resourceLocation;
      }

      if (b == null) {
         return resourceLocation;
      }

      WorldClient var5 = mc.theWorld;
      if (var5 == null) {
         return resourceLocation;
      }

      if (var4 instanceof GuiContainerCreative) {
         return getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, mc.thePlayer.getPosition(), var5, resourceLocation, var4);
      }

      if (var4 instanceof GuiInventory) {
         return getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, mc.thePlayer.getPosition(), var5, resourceLocation, var4);
      }

      BlockPos var6 = b.getLastClickBlockPos();
      if (var6 != null) {
         if (var4 instanceof GuiRepair) {
            return getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, var6, var5, resourceLocation, var4);
         }

         if (var4 instanceof GuiBeacon) {
            return getTexturePos(CustomGuiProperties.EnumContainer.BEACON, var6, var5, resourceLocation, var4);
         }

         if (var4 instanceof GuiBrewingStand) {
            return getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, var6, var5, resourceLocation, var4);
         }

         if (var4 instanceof GuiChest) {
            return getTexturePos(CustomGuiProperties.EnumContainer.CHEST, var6, var5, resourceLocation, var4);
         }

         if (var4 instanceof GuiCrafting) {
            return getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, var6, var5, resourceLocation, var4);
         }

         if (var4 instanceof GuiDispenser) {
            return getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, var6, var5, resourceLocation, var4);
         }

         if (var4 instanceof GuiEnchantment) {
            return getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, var6, var5, resourceLocation, var4);
         }

         if (var4 instanceof GuiFurnace) {
            return getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, var6, var5, resourceLocation, var4);
         }

         if (var4 instanceof GuiHopper) {
            return getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, var6, var5, resourceLocation, var4);
         }
      }

      Entity var7 = b.getLastClickEntity();
      if (var7 != null) {
         if (var4 instanceof GuiScreenHorseInventory) {
            return getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, var7, var5, resourceLocation);
         }

         if (var4 instanceof GuiMerchant) {
            return getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, var7, var5, resourceLocation);
         }
      }

      return resourceLocation;
   }

   private static ResourceLocation getTexturePos(
      CustomGuiProperties.EnumContainer enumContainer, BlockPos blockPos, IBlockAccess iBlockAccess, ResourceLocation resourceLocation, GuiScreen guiScreen
   ) {
      CustomGuiProperties[] var8 = guiProperties[enumContainer.ordinal()];
      if (var8 != null) {
         for (CustomGuiProperties var12 : var8) {
            if (var12.matchesPos(enumContainer, blockPos, iBlockAccess, guiScreen)) {
               return var12.getTextureLocation(resourceLocation);
            }
         }
      }

      return resourceLocation;
   }

   private static ResourceLocation getTextureEntity(
      CustomGuiProperties.EnumContainer enumContainer, Entity entity, IBlockAccess iBlockAccess, ResourceLocation resourceLocation
   ) {
      CustomGuiProperties[] var7 = guiProperties[enumContainer.ordinal()];
      if (var7 != null) {
         for (CustomGuiProperties var11 : var7) {
            if (var11.matchesEntity(enumContainer, entity, iBlockAccess)) {
               return var11.getTextureLocation(resourceLocation);
            }
         }
      }

      return resourceLocation;
   }

   public static void update() {
      guiProperties = null;
      if (Config.isCustomGuis()) {
         ArrayList var3 = new ArrayList();
         IResourcePack[] var4 = Config.getResourcePacks();

         for (int var5 = var4.length - 1; var5 >= 0; var5--) {
            IResourcePack var6 = var4[var5];
            update(var6, var3);
         }

         guiProperties = propertyListToArray(var3);
      }
   }

   private static CustomGuiProperties[][] propertyListToArray(List<List<CustomGuiProperties>> list) {
      if (list.isEmpty()) {
         return null;
      }

      CustomGuiProperties[][] var4 = new CustomGuiProperties[CustomGuiProperties.EnumContainer.VALUES.length][];

      for (int var5 = 0; var5 < var4.length; var5++) {
         if (list.size() > var5) {
            List<CustomGuiProperties> var6 = list.get(var5);
            if (var6 != null) {
               CustomGuiProperties[] var7 = var6.toArray(new CustomGuiProperties[0]);
               var4[var5] = var7;
            }
         }
      }

      return var4;
   }

   private static void update(IResourcePack iResourcePack, List<List<CustomGuiProperties>> list) {
      String[] var5 = ResUtils.collectFiles(iResourcePack, "optifine/gui/container/", ".properties", null);
      Arrays.sort(var5);

      for (String var9 : var5) {
         Config.Method251("CustomGuis: " + var9);

         try {
            ResourceLocation var10 = new ResourceLocation(var9);
            InputStream var11 = iResourcePack.getInputStream(var10);
            if (var11 == null) {
               Config.Method252("CustomGuis file not found: " + var9);
            } else {
               PropertiesOrdered var12 = new PropertiesOrdered();
               var12.load(var11);
               var11.close();
               CustomGuiProperties var13 = new CustomGuiProperties(var12, var9);
               if (var13.isValid(var9)) {
                  addToList(var13, list);
               }
            }
         } catch (FileNotFoundException var14) {
            Config.Method252("CustomGuis file not found: " + var9);
         } catch (Exception var15) {
            var15.printStackTrace();
         }
      }
   }

   private static void addToList(CustomGuiProperties customGuiProperties, List<List<CustomGuiProperties>> list) {
      if (customGuiProperties.getContainer() == null) {
         warn("Invalid container: " + customGuiProperties.getContainer());
      } else {
         int var5 = customGuiProperties.getContainer().ordinal();

         while (list.size() <= var5) {
            list.add(null);
         }

         List var6 = (List)list.get(var5);
         if (var6 == null) {
            var6 = new ArrayList();
            list.set(var5, var6);
         }

         var6.add(customGuiProperties);
      }
   }

   public static PlayerControllerOF getPlayerControllerOF() {
      return b;
   }

   public static void setPlayerControllerOF(PlayerControllerOF playerControllerOF) {
      b = playerControllerOF;
   }

   private static boolean isChristmas() {
      Calendar var2 = Calendar.getInstance();
      return var2.get(2) + 1 == 12 && var2.get(5) >= 24 && var2.get(5) <= 26;
   }

   private static void warn(String string) {
      Config.Method252("[CustomGuis] " + string);
   }
}
