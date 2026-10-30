package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.config.NbtTagValue;
import net.optifine.render.Blender;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;

public class CustomItems {
   private static CustomItemProperties[][] itemProperties = null;
   private static CustomItemProperties[][] enchantmentProperties = null;
   private static Map mapPotionIds = null;
   private static final ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
   private static boolean useGlint = true;
   private static final int[][] EMPTY_INT2_ARRAY = new int[0][];

   public static void update() {
      itemProperties = null;
      enchantmentProperties = null;
      useGlint = true;
      if (Config.isCustomItems()) {
         readCitProperties("mcpatcher/cit.properties");
         IResourcePack[] var3 = Config.getResourcePacks();

         for (int var4 = var3.length - 1; var4 >= 0; var4--) {
            IResourcePack var5 = var3[var4];
            update(var5);
         }

         update(Config.getDefaultResourcePack());
         if (itemProperties.length == 0) {
            itemProperties = null;
         }

         if (enchantmentProperties.length == 0) {
            enchantmentProperties = null;
         }
      }
   }

   private static void readCitProperties(String string) {
      try {
         InputStream var5 = Config.getResourceStream(new ResourceLocation(string));
         if (var5 == null) {
            return;
         }

         Config.Method251("CustomItems: Loading " + string);
         PropertiesOrdered var6 = new PropertiesOrdered();
         var6.load(var5);
         var5.close();
         useGlint = Config.parseBoolean(var6.getProperty("useGlint"), true);
      } catch (FileNotFoundException var7) {
      } catch (IOException var8) {
         var8.printStackTrace();
      }
   }

   private static void update(IResourcePack iResourcePack) {
      String[] var4 = ResUtils.collectFiles(iResourcePack, "mcpatcher/cit/", ".properties", null);
      Map var5 = makeAutoImageProperties(iResourcePack);
      if (var5.size() > 0) {
         String[] var7 = (String[])var5.keySet().toArray(new String[0]);
         var4 = (String[])Config.addObjectsToArray(var4, var7);
      }

      Arrays.sort(var4);
      List var6 = makePropertyList(itemProperties);
      List var18 = makePropertyList(enchantmentProperties);

      for (String var11 : var4) {
         Config.Method251("CustomItems: " + var11);

         try {
            CustomItemProperties var12 = null;
            if (var5.containsKey(var11)) {
               var12 = (CustomItemProperties)var5.get(var11);
            }

            if (var12 == null) {
               ResourceLocation var13 = new ResourceLocation(var11);
               InputStream var14 = iResourcePack.getInputStream(var13);
               if (var14 == null) {
                  Config.Method252("CustomItems file not found: " + var11);
                  continue;
               }

               PropertiesOrdered var15 = new PropertiesOrdered();
               var15.load(var14);
               var14.close();
               var12 = new CustomItemProperties(var15, var11);
            }

            if (var12.isValid(var11)) {
               addToItemList(var12, var6);
               addToEnchantmentList(var12, var18);
            }
         } catch (FileNotFoundException var16) {
            Config.Method252("CustomItems file not found: " + var11);
         } catch (Exception var17) {
            var17.printStackTrace();
         }
      }

      itemProperties = propertyListToArray(var6);
      enchantmentProperties = propertyListToArray(var18);
      Comparator var19 = getPropertiesComparator();

      for (CustomItemProperties[] var26 : itemProperties) {
         if (var26 != null) {
            Arrays.sort(var26, var19);
         }
      }

      for (CustomItemProperties[] var27 : enchantmentProperties) {
         if (var27 != null) {
            Arrays.sort(var27, var19);
         }
      }
   }

   private static Comparator getPropertiesComparator() {
      return (object1, object2) -> {
         CustomItemProperties var5 = (CustomItemProperties)object1;
         CustomItemProperties var6 = (CustomItemProperties)object2;
         return var5.t != var6.t
            ? var5.t - var6.t
            : (
               var5.weight != var6.weight
                  ? var6.weight - var5.weight
                  : (!var5.basePath.equals(var6.basePath) ? var5.basePath.compareTo(var6.basePath) : var5.name.compareTo(var6.name))
            );
      };
   }

   public static void updateIcons(TextureMap textureMap) {
      Iterator var4 = getAllProperties().iterator();

      while (var4.hasNext()) {
         ((CustomItemProperties)var4.next()).updateIcons(textureMap);
      }
   }

   public static void loadModels(ModelBakery modelBakery) {
      Iterator var4 = getAllProperties().iterator();

      while (var4.hasNext()) {
         ((CustomItemProperties)var4.next()).loadModels(modelBakery);
      }
   }

   public static void updateModels() {
      for (CustomItemProperties var4 : getAllProperties()) {
         if (var4.type == 1) {
            TextureMap var5 = Minecraft.getMinecraft().getTextureMapBlocks();
            var4.updateModelTexture(var5, itemModelGenerator);
            var4.updateModelsFull();
         }
      }
   }

   private static List<CustomItemProperties> getAllProperties() {
      ArrayList var0 = new ArrayList();
      addAll(itemProperties, var0);
      addAll(enchantmentProperties, var0);
      return var0;
   }

   private static void addAll(CustomItemProperties[][] arr, List<CustomItemProperties> list) {
      if (arr != null) {
         for (CustomItemProperties[] var8 : arr) {
            if (var8 != null) {
               for (CustomItemProperties var12 : var8) {
                  if (var12 != null) {
                     list.add(var12);
                  }
               }
            }
         }
      }
   }

   private static Map makeAutoImageProperties(IResourcePack iResourcePack) {
      HashMap var3 = new HashMap();
      var3.putAll(makePotionImageProperties(iResourcePack, "normal", Item.getIdFromItem(Items.potionitem)));
      var3.putAll(makePotionImageProperties(iResourcePack, "splash", Item.getIdFromItem(Items.potionitem)));
      var3.putAll(makePotionImageProperties(iResourcePack, "linger", Item.getIdFromItem(Items.potionitem)));
      return var3;
   }

   private static Map makePotionImageProperties(IResourcePack iResourcePack, String string, int integer) {
      HashMap var6 = new HashMap();
      String var7 = string + "/";
      String[] var8 = new String[]{"mcpatcher/cit/potion/" + var7, "mcpatcher/cit/Potion/" + var7};
      String[] var9 = new String[]{".png"};
      String[] var10 = ResUtils.collectFiles(iResourcePack, var8, var9);

      for (String var14 : var10) {
         Properties var16 = makePotionProperties(StrUtils.removePrefixSuffix(var14, var8, var9), string, integer, var14);
         if (var16 != null) {
            String var17 = StrUtils.removeSuffix(var14, var9) + ".properties";
            CustomItemProperties var18 = new CustomItemProperties(var16, var17);
            var6.put(var17, var18);
         }
      }

      return var6;
   }

   private static Properties makePotionProperties(String string1, String string2, int integer, String string4) {
      if (StrUtils.endsWith(string1, new String[]{"_n", "_s"})) {
         return null;
      }

      if (string1.equals("empty") && string2.equals("normal")) {
         integer = Item.getIdFromItem(Items.glass_bottle);
         PropertiesOrdered var12 = new PropertiesOrdered();
         var12.put("type", "item");
         var12.put("items", integer + "");
         return var12;
      }

      int[] var7 = (int[])getMapPotionIds().get(string1);
      if (var7 == null) {
         Config.Method252("Potion not found for image: " + string4);
         return null;
      }

      StringBuilder var8 = new StringBuilder();

      for (int var9 = 0; var9 < var7.length; var9++) {
         int var10 = var7[var9];
         if (string2.equals("splash")) {
            var10 |= 16384;
         }

         if (var9 > 0) {
            var8.append(" ");
         }

         var8.append(var10);
      }

      short var13 = 16447;
      if (string1.equals("water") || string1.equals("mundane")) {
         var13 = 16511;
      }

      PropertiesOrdered var14 = new PropertiesOrdered();
      var14.put("type", "item");
      var14.put("items", integer + "");
      var14.put("damage", var8 + "");
      var14.put("damageMask", var13 + "");
      if (string2.equals("splash")) {
         var14.put("texture.potion_bottle_splash", string1);
      } else {
         var14.put("texture.potion_bottle_drinkable", string1);
      }

      return var14;
   }

   private static Map getMapPotionIds() {
      if (mapPotionIds == null) {
         mapPotionIds = new LinkedHashMap();
         mapPotionIds.put("water", getPotionId(0, 0));
         mapPotionIds.put("awkward", getPotionId(0, 1));
         mapPotionIds.put("thick", getPotionId(0, 2));
         mapPotionIds.put("potent", getPotionId(0, 3));
         mapPotionIds.put("regeneration", getPotionIds(1));
         mapPotionIds.put("movespeed", getPotionIds(2));
         mapPotionIds.put("fireresistance", getPotionIds(3));
         mapPotionIds.put("poison", getPotionIds(4));
         mapPotionIds.put("heal", getPotionIds(5));
         mapPotionIds.put("nightvision", getPotionIds(6));
         mapPotionIds.put("clear", getPotionId(7, 0));
         mapPotionIds.put("bungling", getPotionId(7, 1));
         mapPotionIds.put("charming", getPotionId(7, 2));
         mapPotionIds.put("rank", getPotionId(7, 3));
         mapPotionIds.put("weakness", getPotionIds(8));
         mapPotionIds.put("damageboost", getPotionIds(9));
         mapPotionIds.put("moveslowdown", getPotionIds(10));
         mapPotionIds.put("leaping", getPotionIds(11));
         mapPotionIds.put("harm", getPotionIds(12));
         mapPotionIds.put("waterbreathing", getPotionIds(13));
         mapPotionIds.put("invisibility", getPotionIds(14));
         mapPotionIds.put("thin", getPotionId(15, 0));
         mapPotionIds.put("debonair", getPotionId(15, 1));
         mapPotionIds.put("sparkling", getPotionId(15, 2));
         mapPotionIds.put("stinky", getPotionId(15, 3));
         mapPotionIds.put("mundane", getPotionId(0, 4));
         mapPotionIds.put("speed", mapPotionIds.get("movespeed"));
         mapPotionIds.put("fire_resistance", mapPotionIds.get("fireresistance"));
         mapPotionIds.put("instant_health", mapPotionIds.get("heal"));
         mapPotionIds.put("night_vision", mapPotionIds.get("nightvision"));
         mapPotionIds.put("strength", mapPotionIds.get("damageboost"));
         mapPotionIds.put("slowness", mapPotionIds.get("moveslowdown"));
         mapPotionIds.put("instant_damage", mapPotionIds.get("harm"));
         mapPotionIds.put("water_breathing", mapPotionIds.get("waterbreathing"));
      }

      return mapPotionIds;
   }

   private static int[] getPotionIds(int integer) {
      return new int[]{integer, integer + 16, integer + 32, integer + 48};
   }

   private static int[] getPotionId(int integer1, int integer2) {
      return new int[]{integer1 + integer2 * 16};
   }

   private static int getPotionNameDamage(String string) {
      String var4 = "potion." + string;

      for (Potion var9 : Potion.potionTypes) {
         if (var9 != null) {
            String var10 = var9.getName();
            if (var4.equals(var10)) {
               return var9.getId();
            }
         }
      }

      return -1;
   }

   private static List makePropertyList(CustomItemProperties[][] arr) {
      ArrayList var4 = new ArrayList();
      if (arr != null) {
         for (CustomItemProperties[] var8 : arr) {
            ArrayList var9 = null;
            if (var8 != null) {
               var9 = new ArrayList<>(Arrays.asList(var8));
            }

            var4.add(var9);
         }
      }

      return var4;
   }

   private static CustomItemProperties[][] propertyListToArray(List list) {
      CustomItemProperties[][] var4 = new CustomItemProperties[list.size()][];

      for (int var5 = 0; var5 < list.size(); var5++) {
         List var6 = (List)list.get(var5);
         if (var6 != null) {
            CustomItemProperties[] var7 = (CustomItemProperties[])var6.toArray(new CustomItemProperties[0]);
            Arrays.sort(var7, new CustomItemsComparator());
            var4[var5] = var7;
         }
      }

      return var4;
   }

   private static void addToItemList(CustomItemProperties customItemProperties, List list) {
      if (customItemProperties.items != null) {
         for (int var5 = 0; var5 < customItemProperties.items.length; var5++) {
            int var6 = customItemProperties.items[var5];
            if (var6 <= 0) {
               Config.Method252("Invalid item ID: " + var6);
            } else {
               addToList(customItemProperties, list, var6);
            }
         }
      }
   }

   private static void addToEnchantmentList(CustomItemProperties customItemProperties, List list) {
      if (customItemProperties.type == 2 && customItemProperties.enchantmentIds != null) {
         for (int var5 = 0; var5 < 256; var5++) {
            if (customItemProperties.enchantmentIds.isInRange(var5)) {
               addToList(customItemProperties, list, var5);
            }
         }
      }
   }

   private static void addToList(CustomItemProperties customItemProperties, List list, int integer) {
      while (integer >= list.size()) {
         list.add(null);
      }

      List var6 = (List)list.get(integer);
      if (var6 == null) {
         var6 = new ArrayList();
         list.set(integer, var6);
      }

      var6.add(customItemProperties);
   }

   public static IBakedModel getCustomItemModel(ItemStack itemStack, IBakedModel iBakedModel, ResourceLocation resourceLocation, boolean boolean4) {
      if (!boolean4 && iBakedModel.isGui3d()) {
         return iBakedModel;
      }

      if (itemProperties == null) {
         return iBakedModel;
      }

      CustomItemProperties var7 = getCustomItemProperties(itemStack, 1);
      if (var7 == null) {
         return iBakedModel;
      }

      IBakedModel var8 = var7.getBakedModel(resourceLocation, boolean4);
      return var8 != null ? var8 : iBakedModel;
   }

   public static boolean bindCustomArmorTexture(ItemStack itemStack, int integer, String string) {
      if (itemProperties == null) {
         return false;
      }

      ResourceLocation var6 = getCustomArmorLocation(itemStack, integer, string);
      if (var6 == null) {
         return false;
      }

      Config.getTextureManager().bindTexture(var6);
      return true;
   }

   private static ResourceLocation getCustomArmorLocation(ItemStack itemStack, int integer, String string) {
      CustomItemProperties var6 = getCustomItemProperties(itemStack, 3);
      if (var6 == null) {
         return null;
      }

      if (var6.mapTextureLocations == null) {
         return var6.textureLocation;
      }

      if (itemStack.getItem() instanceof ItemArmor var8) {
         String var9 = var8.getArmorMaterial().getName();
         StringBuilder var10 = new StringBuilder();
         var10.append("texture.");
         var10.append(var9);
         var10.append("_layer_");
         var10.append(integer);
         if (string != null) {
            var10.append("_");
            var10.append(string);
         }

         String var11 = var10.toString();
         ResourceLocation var12 = (ResourceLocation)var6.mapTextureLocations.get(var11);
         return var12 == null ? var6.textureLocation : var12;
      } else {
         return null;
      }
   }

   private static CustomItemProperties getCustomItemProperties(ItemStack itemStack, int integer) {
      if (itemProperties == null) {
         return null;
      }

      if (itemStack == null) {
         return null;
      }

      int var6 = Item.getIdFromItem(itemStack.getItem());
      if (var6 >= 0 && var6 < itemProperties.length) {
         CustomItemProperties[] var7 = itemProperties[var6];
         if (var7 != null) {
            for (CustomItemProperties var11 : var7) {
               if (var11.type == integer && matchesProperties(var11, itemStack, null)) {
                  return var11;
               }
            }
         }
      }

      return null;
   }

   private static boolean matchesProperties(CustomItemProperties customItemProperties, ItemStack itemStack, int[][] arr) {
      Item var6 = itemStack.getItem();
      if (customItemProperties.damage != null) {
         int var7 = itemStack.getItemDamage();
         if (customItemProperties.damageMask != 0) {
            var7 &= customItemProperties.damageMask;
         }

         if (customItemProperties.damagePercent) {
            int var8 = var6.getMaxDamage();
            var7 = (int)((double)(var7 * 100) / var8);
         }

         if (!customItemProperties.damage.isInRange(var7)) {
            return false;
         }
      }

      if (customItemProperties.stackSize != null && !customItemProperties.stackSize.isInRange(itemStack.stackSize)) {
         return false;
      }

      int[][] var14 = arr;
      if (customItemProperties.enchantmentIds != null) {
         if (arr == null) {
            var14 = getEnchantmentIdLevels(itemStack);
         }

         boolean var15 = false;
         int[][] var9 = var14;
         int var10 = var9.length;

         for (int var11 = 0; var11 < var10; var11++) {
            int var13 = var9[var11][0];
            if (customItemProperties.enchantmentIds.isInRange(var13)) {
               var15 = true;
               break;
            }
         }

         if (!var15) {
            return false;
         }
      }

      if (customItemProperties.enchantmentLevels != null) {
         if (var14 == null) {
            var14 = getEnchantmentIdLevels(itemStack);
         }

         boolean var16 = false;
         int[][] var18 = var14;
         int var20 = var18.length;

         for (int var22 = 0; var22 < var20; var22++) {
            int var23 = var18[var22][1];
            if (customItemProperties.enchantmentLevels.isInRange(var23)) {
               var16 = true;
               break;
            }
         }

         if (!var16) {
            return false;
         }
      }

      if (customItemProperties.nbtTagValues != null) {
         NBTTagCompound var17 = itemStack.getTagCompound();

         for (int var19 = 0; var19 < customItemProperties.nbtTagValues.length; var19++) {
            NbtTagValue var21 = customItemProperties.nbtTagValues[var19];
            if (!var21.matches(var17)) {
               return false;
            }
         }
      }

      if (customItemProperties.hand != 0) {
         if (customItemProperties.hand == 1) {
         }

         return customItemProperties.hand != 2;
      } else {
         return true;
      }
   }

   private static int[][] getEnchantmentIdLevels(ItemStack itemStack) {
      Item var4 = itemStack.getItem();
      NBTTagList var5 = var4 == Items.enchanted_book ? Items.enchanted_book.getEnchantments(itemStack) : itemStack.getEnchantmentTagList();
      if (var5 != null && var5.tagCount() > 0) {
         int[][] var6 = new int[var5.tagCount()][2];

         for (int var7 = 0; var7 < var5.tagCount(); var7++) {
            NBTTagCompound var8 = var5.getCompoundTagAt(var7);
            short var9 = var8.getShort("id");
            short var10 = var8.getShort("lvl");
            var6[var7][0] = var9;
            var6[var7][1] = var10;
         }

         return var6;
      } else {
         return EMPTY_INT2_ARRAY;
      }
   }

   public static boolean renderCustomEffect(RenderItem renderItem, ItemStack itemStack, IBakedModel iBakedModel) {
      if (enchantmentProperties == null) {
         return false;
      }

      if (itemStack == null) {
         return false;
      }

      int[][] var6 = getEnchantmentIdLevels(itemStack);
      if (var6.length == 0) {
         return false;
      }

      HashSet var7 = null;
      boolean var8 = false;
      TextureManager var9 = Config.getTextureManager();
      int[][] var10 = var6;
      int var11 = var10.length;

      for (int var12 = 0; var12 < var11; var12++) {
         int var14 = var10[var12][0];
         if (var14 >= 0 && var14 < enchantmentProperties.length) {
            CustomItemProperties[] var15 = enchantmentProperties[var14];
            if (var15 != null) {
               for (CustomItemProperties var19 : var15) {
                  if (var7 == null) {
                     var7 = new HashSet();
                  }

                  if (var7.add(var14) && matchesProperties(var19, itemStack, var6) && var19.textureLocation != null) {
                     var9.bindTexture(var19.textureLocation);
                     float var20 = var19.getTextureWidth(var9);
                     if (!var8) {
                        var8 = true;
                        GlStateManager.depthMask(false);
                        GlStateManager.depthFunc(514);
                        GlStateManager.disableLighting();
                        GlStateManager.matrixMode(5890);
                     }

                     Blender.setupBlend(var19.blend, 1.0F);
                     GlStateManager.pushMatrix();
                     GlStateManager.scale(var20 / 2.0F, var20 / 2.0F, var20 / 2.0F);
                     GlStateManager.translate(var19.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F, 0.0F, 0.0F);
                     GlStateManager.rotate(var19.rotation, 0.0F, 0.0F, 1.0F);
                     renderItem.renderModel(iBakedModel, -1);
                     GlStateManager.popMatrix();
                  }
               }
            }
         }
      }

      if (var8) {
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.enableLighting();
         GlStateManager.depthFunc(515);
         GlStateManager.depthMask(true);
         var9.bindTexture(TextureMap.locationBlocksTexture);
      }

      return var8;
   }

   public static boolean renderCustomArmorEffect(
      EntityLivingBase entityLivingBase,
      ItemStack itemStack,
      ModelBase modelBase,
      float float4,
      float float5,
      float float6,
      float float7,
      float float8,
      float float9,
      float float10
   ) {
      if (enchantmentProperties == null) {
         return false;
      }

      if (Config.isShaders() && Shaders.isShadowPass) {
         return false;
      }

      if (itemStack == null) {
         return false;
      }

      int[][] var13 = getEnchantmentIdLevels(itemStack);
      if (var13.length == 0) {
         return false;
      }

      HashSet var14 = null;
      boolean var15 = false;
      TextureManager var16 = Config.getTextureManager();
      int[][] var17 = var13;
      int var18 = var17.length;

      for (int var19 = 0; var19 < var18; var19++) {
         int var21 = var17[var19][0];
         if (var21 >= 0 && var21 < enchantmentProperties.length) {
            CustomItemProperties[] var22 = enchantmentProperties[var21];
            if (var22 != null) {
               for (CustomItemProperties var26 : var22) {
                  if (var14 == null) {
                     var14 = new HashSet();
                  }

                  if (var14.add(var21) && matchesProperties(var26, itemStack, var13) && var26.textureLocation != null) {
                     var16.bindTexture(var26.textureLocation);
                     float var27 = var26.getTextureWidth(var16);
                     if (!var15) {
                        var15 = true;
                        if (Config.isShaders()) {
                           ShadersRender.renderEnchantedGlintBegin();
                        }

                        GlStateManager.enableBlend();
                        GlStateManager.depthFunc(514);
                        GlStateManager.depthMask(false);
                     }

                     Blender.setupBlend(var26.blend, 1.0F);
                     GlStateManager.disableLighting();
                     GlStateManager.matrixMode(5890);
                     GlStateManager.loadIdentity();
                     GlStateManager.rotate(var26.rotation, 0.0F, 0.0F, 1.0F);
                     float var28 = var27 / 8.0F;
                     GlStateManager.scale(var28, var28 / 2.0F, var28);
                     float var29 = var26.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
                     GlStateManager.translate(0.0F, var29, 0.0F);
                     GlStateManager.matrixMode(5888);
                     modelBase.render(entityLivingBase, float4, float5, float7, float8, float9, float10);
                  }
               }
            }
         }
      }

      if (var15) {
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         GlStateManager.matrixMode(5888);
         GlStateManager.enableLighting();
         GlStateManager.depthMask(true);
         GlStateManager.depthFunc(515);
         GlStateManager.disableBlend();
         if (Config.isShaders()) {
            ShadersRender.renderEnchantedGlintEnd();
         }
      }

      return var15;
   }

   public static boolean isUseGlint() {
      return useGlint;
   }
}
