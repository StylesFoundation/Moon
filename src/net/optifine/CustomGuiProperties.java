package net.optifine;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.config.ConnectedParser;
import net.optifine.config.Matches;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeListInt;
import net.optifine.config.VillagerProfession;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;

public class CustomGuiProperties {
   private String fileName = null;
   private String basePath = null;
   private CustomGuiProperties.EnumContainer container = null;
   private Map<ResourceLocation, ResourceLocation> textureLocations = null;
   private NbtTagValue nbtName = null;
   private BiomeGenBase[] biomes = null;
   private RangeListInt heights = null;
   private Boolean large = null;
   private Boolean trapped = null;
   private Boolean christmas = null;
   private Boolean ender = null;
   private RangeListInt levels = null;
   private VillagerProfession[] professions = null;
   private CustomGuiProperties.EnumVariant[] variants = null;
   private EnumDyeColor[] colors = null;
   private static final CustomGuiProperties.EnumVariant[] VARIANTS_HORSE = new CustomGuiProperties.EnumVariant[]{
      CustomGuiProperties.EnumVariant.HORSE,
      CustomGuiProperties.EnumVariant.DONKEY,
      CustomGuiProperties.EnumVariant.MULE,
      CustomGuiProperties.EnumVariant.LLAMA
   };
   private static final CustomGuiProperties.EnumVariant[] VARIANTS_DISPENSER = new CustomGuiProperties.EnumVariant[]{
      CustomGuiProperties.EnumVariant.DISPENSER, CustomGuiProperties.EnumVariant.DROPPER
   };
   private static final CustomGuiProperties.EnumVariant[] VARIANTS_INVALID = new CustomGuiProperties.EnumVariant[0];
   private static final EnumDyeColor[] COLORS_INVALID = new EnumDyeColor[0];
   private static final ResourceLocation ANVIL_GUI_TEXTURE = new ResourceLocation("textures/gui/container/anvil.png");
   private static final ResourceLocation BEACON_GUI_TEXTURE = new ResourceLocation("textures/gui/container/beacon.png");
   private static final ResourceLocation BREWING_STAND_GUI_TEXTURE = new ResourceLocation("textures/gui/container/brewing_stand.png");
   private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
   private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");
   private static final ResourceLocation HORSE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/horse.png");
   private static final ResourceLocation DISPENSER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");
   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
   private static final ResourceLocation FURNACE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
   private static final ResourceLocation HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
   private static final ResourceLocation INVENTORY_GUI_TEXTURE = new ResourceLocation("textures/gui/container/inventory.png");
   private static final ResourceLocation SHULKER_BOX_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
   private static final ResourceLocation VILLAGER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");

   public CustomGuiProperties(Properties properties, String string) {
      ConnectedParser var5 = new ConnectedParser("CustomGuis");
      this.fileName = var5.parseName(string);
      this.basePath = var5.parseBasePath(string);
      this.container = (CustomGuiProperties.EnumContainer)var5.parseEnum(
         properties.getProperty("container"), CustomGuiProperties.EnumContainer.values(), "container"
      );
      this.textureLocations = parseTextureLocations(properties, "texture", this.container, "textures/gui/", this.basePath);
      this.nbtName = var5.parseNbtTagValue("name", properties.getProperty("name"));
      this.biomes = var5.parseBiomes(properties.getProperty("biomes"));
      this.heights = var5.parseRangeListInt(properties.getProperty("heights"));
      this.large = var5.parseBooleanObject(properties.getProperty("large"));
      this.trapped = var5.parseBooleanObject(properties.getProperty("trapped"));
      this.christmas = var5.parseBooleanObject(properties.getProperty("christmas"));
      this.ender = var5.parseBooleanObject(properties.getProperty("ender"));
      this.levels = var5.parseRangeListInt(properties.getProperty("levels"));
      this.professions = var5.parseProfessions(properties.getProperty("professions"));
      CustomGuiProperties.EnumVariant[] var6 = getContainerVariants(this.container);
      this.variants = (CustomGuiProperties.EnumVariant[])var5.parseEnums(properties.getProperty("variants"), var6, "variants", VARIANTS_INVALID);
      this.colors = parseEnumDyeColors(properties.getProperty("colors"));
   }

   private static CustomGuiProperties.EnumVariant[] getContainerVariants(CustomGuiProperties.EnumContainer enumContainer) {
      return enumContainer == CustomGuiProperties.EnumContainer.HORSE
         ? VARIANTS_HORSE
         : (enumContainer == CustomGuiProperties.EnumContainer.DISPENSER ? VARIANTS_DISPENSER : new CustomGuiProperties.EnumVariant[0]);
   }

   private static EnumDyeColor[] parseEnumDyeColors(String string) {
      if (string == null) {
         return null;
      }

      String[] var4 = Config.tokenize(string.toLowerCase(), " ");
      EnumDyeColor[] var5 = new EnumDyeColor[var4.length];

      for (int var6 = 0; var6 < var4.length; var6++) {
         String var7 = var4[var6];
         EnumDyeColor var8 = parseEnumDyeColor(var7);
         if (var8 == null) {
            warn("Invalid color: " + var7);
            return COLORS_INVALID;
         }

         var5[var6] = var8;
      }

      return var5;
   }

   private static EnumDyeColor parseEnumDyeColor(String string) {
      if (string != null) {
         for (EnumDyeColor var8 : EnumDyeColor.values()) {
            if (var8.getName().equals(string)) {
               return var8;
            }

            if (var8.getUnlocalizedName().equals(string)) {
               return var8;
            }
         }
      }

      return null;
   }

   private static ResourceLocation parseTextureLocation(String string1, String string2) {
      if (string1 == null) {
         return null;
      }

      String var5 = TextureUtils.fixResourcePath(string1.trim(), string2);
      if (!var5.endsWith(".png")) {
         var5 = var5 + ".png";
      }

      return new ResourceLocation(string2 + "/" + var5);
   }

   private static Map<ResourceLocation, ResourceLocation> parseTextureLocations(
      Properties properties, String string2, CustomGuiProperties.EnumContainer enumContainer, String string4, String string5
   ) {
      HashMap var8 = new HashMap();
      String var9 = properties.getProperty(string2);
      if (var9 != null) {
         ResourceLocation var10 = getGuiTextureLocation(enumContainer);
         ResourceLocation var11 = parseTextureLocation(var9, string5);
         if (var10 != null && var11 != null) {
            var8.put(var10, var11);
         }
      }

      String var18 = string2 + ".";

      for (String var12 : properties.stringPropertyNames()) {
         if (var12.startsWith(var18)) {
            String var13 = StrUtils.removePrefixSuffix(var12.substring(var18.length()).replace('\\', '/'), "/", ".png");
            String var14 = string4 + var13 + ".png";
            String var15 = properties.getProperty(var12);
            ResourceLocation var16 = new ResourceLocation(var14);
            ResourceLocation var17 = parseTextureLocation(var15, string5);
            var8.put(var16, var17);
         }
      }

      return var8;
   }

   private static ResourceLocation getGuiTextureLocation(CustomGuiProperties.EnumContainer enumContainer) {
      if (enumContainer == null) {
         return null;
      }

      switch (enumContainer) {
         case ANVIL:
            return ANVIL_GUI_TEXTURE;
         case BEACON:
            return BEACON_GUI_TEXTURE;
         case BREWING_STAND:
            return BREWING_STAND_GUI_TEXTURE;
         case CHEST:
            return CHEST_GUI_TEXTURE;
         case CRAFTING:
            return CRAFTING_TABLE_GUI_TEXTURE;
         case DISPENSER:
            return DISPENSER_GUI_TEXTURE;
         case ENCHANTMENT:
            return ENCHANTMENT_TABLE_GUI_TEXTURE;
         case FURNACE:
            return FURNACE_GUI_TEXTURE;
         case HOPPER:
            return HOPPER_GUI_TEXTURE;
         case HORSE:
            return HORSE_GUI_TEXTURE;
         case INVENTORY:
            return INVENTORY_GUI_TEXTURE;
         case SHULKER_BOX:
            return SHULKER_BOX_GUI_TEXTURE;
         case VILLAGER:
            return VILLAGER_GUI_TEXTURE;
         default:
            return null;
      }
   }

   public boolean isValid(String string) {
      if (this.fileName == null || this.fileName.length() <= 0) {
         warn("No name found: " + string);
         return false;
      } else if (this.basePath == null) {
         warn("No base path found: " + string);
         return false;
      } else if (this.container == null) {
         warn("No container found: " + string);
         return false;
      } else if (this.textureLocations.isEmpty()) {
         warn("No texture found: " + string);
         return false;
      } else if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
         warn("Invalid professions or careers: " + string);
         return false;
      } else if (this.variants == VARIANTS_INVALID) {
         warn("Invalid variants: " + string);
         return false;
      } else if (this.colors == COLORS_INVALID) {
         warn("Invalid colors: " + string);
         return false;
      } else {
         return true;
      }
   }

   private static void warn(String string) {
      Config.Method252("[CustomGuis] " + string);
   }

   private boolean matchesGeneral(CustomGuiProperties.EnumContainer enumContainer, BlockPos blockPos, IBlockAccess iBlockAccess) {
      if (this.container != enumContainer) {
         return false;
      }

      if (this.biomes != null) {
         BiomeGenBase var7 = iBlockAccess.getBiomeGenForCoords(blockPos);
         if (!Matches.biome(var7, this.biomes)) {
            return false;
         }
      }

      return this.heights == null || this.heights.isInRange(blockPos.getY());
   }

   public boolean matchesPos(CustomGuiProperties.EnumContainer enumContainer, BlockPos blockPos, IBlockAccess iBlockAccess, GuiScreen guiScreen) {
      if (!this.matchesGeneral(enumContainer, blockPos, iBlockAccess)) {
         return false;
      }

      if (this.nbtName != null) {
         String var8 = getName(guiScreen);
         if (!this.nbtName.matchesValue(var8)) {
            return false;
         }
      }

      switch (enumContainer) {
         case BEACON:
            return this.matchesBeacon(blockPos, iBlockAccess);
         case BREWING_STAND:
         case CRAFTING:
         default:
            return true;
         case CHEST:
            return this.matchesChest(blockPos, iBlockAccess);
         case DISPENSER:
            return this.matchesDispenser(blockPos, iBlockAccess);
      }
   }

   public static String getName(GuiScreen guiScreen) {
      IWorldNameable var4 = Method359(guiScreen);
      return var4 == null ? null : var4.getDisplayName().getUnformattedText();
   }

   private static IWorldNameable Method359(GuiScreen guiScreen) {
      return guiScreen instanceof GuiBeacon
         ? ((GuiBeacon)guiScreen).tileBeacon
         : (
            guiScreen instanceof GuiBrewingStand
               ? ((GuiBrewingStand)guiScreen).tileBrewingStand
               : (
                  guiScreen instanceof GuiChest
                     ? ((GuiChest)guiScreen).lowerChestInventory
                     : (
                        guiScreen instanceof GuiDispenser
                           ? ((GuiDispenser)guiScreen).dispenserInventory
                           : (
                              guiScreen instanceof GuiEnchantment
                                 ? ((GuiEnchantment)guiScreen).field_175380_I
                                 : (
                                    guiScreen instanceof GuiFurnace
                                       ? ((GuiFurnace)guiScreen).tileFurnace
                                       : (guiScreen instanceof GuiHopper ? ((GuiHopper)guiScreen).hopperInventory : null)
                                 )
                           )
                     )
               )
         );
   }

   private boolean matchesBeacon(BlockPos blockPos, IBlockAccess iBlockAccess) {
      if (iBlockAccess.getTileEntity(blockPos) instanceof TileEntityBeacon var7) {
         if (this.levels != null) {
            NBTTagCompound var8 = new NBTTagCompound();
            var7.writeToNBT(var8);
            int var9 = var8.getInteger("Levels");
            return this.levels.isInRange(var9);
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private boolean matchesChest(BlockPos blockPos, IBlockAccess iBlockAccess) {
      TileEntity var6 = iBlockAccess.getTileEntity(blockPos);
      if (var6 instanceof TileEntityChest var7) {
         return this.matchesChest(var7, blockPos, iBlockAccess);
      } else {
         return var6 instanceof TileEntityEnderChest var8 ? this.matchesEnderChest(var8, blockPos, iBlockAccess) : false;
      }
   }

   private boolean matchesChest(TileEntityChest tileEntityChest, BlockPos blockPos, IBlockAccess iBlockAccess) {
      boolean var7 = tileEntityChest.adjacentChestXNeg != null
         || tileEntityChest.adjacentChestXPos != null
         || tileEntityChest.adjacentChestZNeg != null
         || tileEntityChest.adjacentChestZPos != null;
      boolean var8 = tileEntityChest.getChestType() == 1;
      boolean var9 = CustomGuis.isChristmas;
      return this.matchesChest(var7, var8, var9, false);
   }

   private boolean matchesEnderChest(TileEntityEnderChest tileEntityEnderChest, BlockPos blockPos, IBlockAccess iBlockAccess) {
      return this.matchesChest(false, false, false, true);
   }

   private boolean matchesChest(boolean boolean1, boolean boolean2, boolean boolean3, boolean boolean4) {
      return (this.large == null || this.large == boolean1)
         && (this.trapped == null || this.trapped == boolean2)
         && (this.christmas == null || this.christmas == boolean3)
         && (this.ender == null || this.ender == boolean4);
   }

   private boolean matchesDispenser(BlockPos blockPos, IBlockAccess iBlockAccess) {
      if (iBlockAccess.getTileEntity(blockPos) instanceof TileEntityDispenser var7) {
         return this.variants != null ? Config.equalsOne(this.getDispenserVariant(var7), this.variants) : true;
      } else {
         return false;
      }
   }

   private CustomGuiProperties.EnumVariant getDispenserVariant(TileEntityDispenser tileEntityDispenser) {
      return tileEntityDispenser instanceof TileEntityDropper ? CustomGuiProperties.EnumVariant.DROPPER : CustomGuiProperties.EnumVariant.DISPENSER;
   }

   public boolean matchesEntity(CustomGuiProperties.EnumContainer enumContainer, Entity entity, IBlockAccess iBlockAccess) {
      if (!this.matchesGeneral(enumContainer, entity.getPosition(), iBlockAccess)) {
         return false;
      }

      if (this.nbtName != null) {
         String var7 = entity.getName();
         if (!this.nbtName.matchesValue(var7)) {
            return false;
         }
      }

      switch (enumContainer) {
         case HORSE:
            return this.matchesHorse(entity, iBlockAccess);
         case VILLAGER:
            return this.matchesVillager(entity, iBlockAccess);
         default:
            return true;
      }
   }

   private boolean matchesVillager(Entity entity, IBlockAccess iBlockAccess) {
      if (!(entity instanceof EntityVillager var6)) {
         return false;
      } else {
         if (this.professions == null) {
            return true;
         }

         int var7 = var6.getProfession();
         int var8 = ((EntityVillager)entity).careerId;
         if (var8 < 0) {
            return false;
         }

         boolean var9 = false;

         for (VillagerProfession var13 : this.professions) {
            if (var13.matches(var7, var8)) {
               var9 = true;
               break;
            }
         }

         return var9;
      }
   }

   private boolean matchesHorse(Entity entity, IBlockAccess iBlockAccess) {
      if (entity instanceof EntityHorse var6) {
         return this.variants != null ? Config.equalsOne(this.getHorseVariant(var6), this.variants) : true;
      } else {
         return false;
      }
   }

   private CustomGuiProperties.EnumVariant getHorseVariant(EntityHorse entityHorse) {
      int var4 = entityHorse.getHorseType();
      switch (var4) {
         case 0:
            return CustomGuiProperties.EnumVariant.HORSE;
         case 1:
            return CustomGuiProperties.EnumVariant.DONKEY;
         case 2:
            return CustomGuiProperties.EnumVariant.MULE;
         default:
            return null;
      }
   }

   public CustomGuiProperties.EnumContainer getContainer() {
      return this.container;
   }

   public ResourceLocation getTextureLocation(ResourceLocation resourceLocation) {
      ResourceLocation var5 = this.textureLocations.get(resourceLocation);
      return var5 == null ? resourceLocation : var5;
   }

   @Override
   public String toString() {
      Map var3 = this.textureLocations;
      CustomGuiProperties.EnumContainer var4 = this.container;
      String var5 = this.fileName;
      return "name: " + var5 + ", container: " + var4 + ", textures: " + var3;
   }

   public enum EnumContainer {
      ANVIL,
      BEACON,
      BREWING_STAND,
      CHEST,
      CRAFTING,
      DISPENSER,
      ENCHANTMENT,
      FURNACE,
      HOPPER,
      HORSE,
      VILLAGER,
      SHULKER_BOX,
      CREATIVE,
      INVENTORY;

      public static final CustomGuiProperties.EnumContainer[] VALUES = values();

      private static CustomGuiProperties.EnumContainer[] Method901() {
         return new CustomGuiProperties.EnumContainer[]{
            ANVIL, BEACON, BREWING_STAND, CHEST, CRAFTING, DISPENSER, ENCHANTMENT, FURNACE, HOPPER, HORSE, VILLAGER, SHULKER_BOX, CREATIVE, INVENTORY
         };
      }
   }

   enum EnumVariant {
      HORSE,
      DONKEY,
      MULE,
      LLAMA,
      DISPENSER,
      DROPPER;

      private static CustomGuiProperties.EnumVariant[] Method534() {
         return new CustomGuiProperties.EnumVariant[]{HORSE, DONKEY, MULE, LLAMA, DISPENSER, DROPPER};
      }
   }
}
