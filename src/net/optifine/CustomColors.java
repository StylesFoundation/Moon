package net.optifine;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.render.RenderEnv;
import net.optifine.util.EntityUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomColors {
   private static String paletteFormatDefault = "vanilla";
   private static CustomColormap waterColors = null;
   private static CustomColormap c = null;
   private static CustomColormap d = null;
   private static CustomColormap swampFoliageColors = null;
   private static CustomColormap swampGrassColors = null;
   private static CustomColormap[] colorsBlockColormaps = null;
   private static CustomColormap[][] blockColormaps = null;
   private static CustomColormap skyColors = null;
   private static final CustomColorFader skyColorFader = new CustomColorFader();
   private static CustomColormap fogColors = null;
   private static final CustomColorFader fogColorFader = new CustomColorFader();
   private static CustomColormap underwaterColors = null;
   private static final CustomColorFader underwaterColorFader = new CustomColorFader();
   private static CustomColormap underlavaColors = null;
   private static final CustomColorFader underlavaColorFader = new CustomColorFader();
   private static LightMapPack[] lightMapPacks = null;
   private static int lightmapMinDimensionId = 0;
   private static CustomColormap redstoneColors = null;
   private static CustomColormap xpOrbColors = null;
   private static int xpOrbTime = -1;
   private static CustomColormap durabilityColors = null;
   private static CustomColormap stemColors = null;
   private static CustomColormap stemMelonColors = null;
   private static CustomColormap stemPumpkinColors = null;
   private static CustomColormap myceliumParticleColors = null;
   private static boolean useDefaultGrassFoliageColors = true;
   private static int particleWaterColor = -1;
   private static int particlePortalColor = -1;
   private static int lilyPadColor = -1;
   private static int expBarTextColor = -1;
   private static int bossTextColor = -1;
   private static int signTextColor = -1;
   private static Vec3 fogColorNether = null;
   private static Vec3 fogColorEnd = null;
   private static Vec3 skyColorEnd = null;
   private static int[] spawnEggPrimaryColors = null;
   private static int[] spawnEggSecondaryColors = null;
   private static float[][] wolfCollarColors = null;
   private static float[][] sheepColors = null;
   private static int[] textColors = null;
   private static int[] mapColorsOriginal = null;
   private static int[] potionColors = null;
   private static final IBlockState BLOCK_STATE_DIRT = Blocks.dirt.getDefaultState();
   private static final IBlockState BLOCK_STATE_WATER = Blocks.water.getDefaultState();
   public static Random random = new Random();
   private static final CustomColors.IColorizer COLORIZER_GRASS = new CustomColors.IColorizer() {
      @Override
      public int getColor(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
         BiomeGenBase var7 = CustomColors.getColorBiome(iBlockAccess, blockPos);
         return CustomColors.swampGrassColors != null && var7 == BiomeGenBase.swampland
            ? CustomColors.swampGrassColors.getColor(var7, blockPos)
            : var7.getGrassColorAtPos(blockPos);
      }

      @Override
      public boolean isColorConstant() {
         return false;
      }
   };
   private static final CustomColors.IColorizer COLORIZER_FOLIAGE = new CustomColors.IColorizer() {
      @Override
      public int getColor(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
         BiomeGenBase var7 = CustomColors.getColorBiome(iBlockAccess, blockPos);
         return CustomColors.swampFoliageColors != null && var7 == BiomeGenBase.swampland
            ? CustomColors.swampFoliageColors.getColor(var7, blockPos)
            : var7.getFoliageColorAtPos(blockPos);
      }

      @Override
      public boolean isColorConstant() {
         return false;
      }
   };
   private static final CustomColors.IColorizer COLORIZER_FOLIAGE_PINE = new CustomColors.IColorizer() {
      @Override
      public int getColor(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
         return CustomColors.c != null ? CustomColors.c.getColor(iBlockAccess, blockPos) : ColorizerFoliage.getFoliageColorPine();
      }

      @Override
      public boolean isColorConstant() {
         return CustomColors.c == null;
      }
   };
   private static final CustomColors.IColorizer COLORIZER_FOLIAGE_BIRCH = new CustomColors.IColorizer() {
      @Override
      public int getColor(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
         return CustomColors.d != null ? CustomColors.d.getColor(iBlockAccess, blockPos) : ColorizerFoliage.getFoliageColorBirch();
      }

      @Override
      public boolean isColorConstant() {
         return CustomColors.d == null;
      }
   };
   private static final CustomColors.IColorizer COLORIZER_WATER = new CustomColors.IColorizer() {
      @Override
      public int getColor(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
         BiomeGenBase var7 = CustomColors.getColorBiome(iBlockAccess, blockPos);
         return CustomColors.waterColors != null ? CustomColors.waterColors.getColor(var7, blockPos) : var7.waterColorMultiplier;
      }

      @Override
      public boolean isColorConstant() {
         return false;
      }
   };

   public static void update() {
      paletteFormatDefault = "vanilla";
      waterColors = null;
      d = null;
      c = null;
      swampGrassColors = null;
      swampFoliageColors = null;
      skyColors = null;
      fogColors = null;
      underwaterColors = null;
      underlavaColors = null;
      redstoneColors = null;
      xpOrbColors = null;
      xpOrbTime = -1;
      durabilityColors = null;
      stemColors = null;
      myceliumParticleColors = null;
      lightMapPacks = null;
      particleWaterColor = -1;
      particlePortalColor = -1;
      lilyPadColor = -1;
      expBarTextColor = -1;
      bossTextColor = -1;
      signTextColor = -1;
      fogColorNether = null;
      fogColorEnd = null;
      skyColorEnd = null;
      colorsBlockColormaps = null;
      blockColormaps = null;
      useDefaultGrassFoliageColors = true;
      spawnEggPrimaryColors = null;
      spawnEggSecondaryColors = null;
      wolfCollarColors = null;
      sheepColors = null;
      textColors = null;
      setMapColors(mapColorsOriginal);
      potionColors = null;
      paletteFormatDefault = getValidProperty("mcpatcher/color.properties", "palette.format", CustomColormap.FORMAT_STRINGS, "vanilla");
      String var3 = "mcpatcher/colormap/";
      String[] var4 = new String[]{"water.png", "watercolorX.png"};
      waterColors = getCustomColors(var3, var4, 256, 256);
      updateUseDefaultGrassFoliageColors();
      if (Config.isCustomColors()) {
         String[] var5 = new String[]{"pine.png", "pinecolor.png"};
         c = getCustomColors(var3, var5, 256, 256);
         String[] var6 = new String[]{"birch.png", "birchcolor.png"};
         d = getCustomColors(var3, var6, 256, 256);
         String[] var7 = new String[]{"swampgrass.png", "swampgrasscolor.png"};
         swampGrassColors = getCustomColors(var3, var7, 256, 256);
         String[] var8 = new String[]{"swampfoliage.png", "swampfoliagecolor.png"};
         swampFoliageColors = getCustomColors(var3, var8, 256, 256);
         String[] var9 = new String[]{"sky0.png", "skycolor0.png"};
         skyColors = getCustomColors(var3, var9, 256, 256);
         String[] var10 = new String[]{"fog0.png", "fogcolor0.png"};
         fogColors = getCustomColors(var3, var10, 256, 256);
         String[] var11 = new String[]{"underwater.png", "underwatercolor.png"};
         underwaterColors = getCustomColors(var3, var11, 256, 256);
         String[] var12 = new String[]{"underlava.png", "underlavacolor.png"};
         underlavaColors = getCustomColors(var3, var12, 256, 256);
         String[] var13 = new String[]{"redstone.png", "redstonecolor.png"};
         redstoneColors = getCustomColors(var3, var13, 16, 1);
         xpOrbColors = getCustomColors(var3 + "xporb.png", -1, -1);
         durabilityColors = getCustomColors(var3 + "durability.png", -1, -1);
         String[] var14 = new String[]{"stem.png", "stemcolor.png"};
         stemColors = getCustomColors(var3, var14, 8, 1);
         stemPumpkinColors = getCustomColors(var3 + "pumpkinstem.png", 8, 1);
         stemMelonColors = getCustomColors(var3 + "melonstem.png", 8, 1);
         String[] var15 = new String[]{"myceliumparticle.png", "myceliumparticlecolor.png"};
         myceliumParticleColors = getCustomColors(var3, var15, -1, -1);
         Pair var16 = parseLightMapPacks();
         lightMapPacks = (LightMapPack[])var16.getLeft();
         lightmapMinDimensionId = (Integer)var16.getRight();
         readColorProperties("mcpatcher/color.properties");
         blockColormaps = readBlockColormaps(new String[]{var3 + "custom/", var3 + "blocks/"}, colorsBlockColormaps, 256, 256);
         updateUseDefaultGrassFoliageColors();
      }
   }

   private static String getValidProperty(String string1, String string2, String[] arr, String string4) {
      try {
         InputStream var8 = Config.getResourceStream(new ResourceLocation(string1));
         if (var8 == null) {
            return string4;
         } else {
            PropertiesOrdered var9 = new PropertiesOrdered();
            var9.load(var8);
            var8.close();
            String var10 = var9.getProperty(string2);
            if (var10 == null) {
               return string4;
            } else {
               List var11 = Arrays.asList(arr);
               if (!var11.contains(var10)) {
                  warn("Invalid value: " + string2 + "=" + var10);
                  warn("Expected values: " + Config.arrayToString(arr));
                  return string4;
               } else {
                  dbg(string2 + "=" + var10);
                  return var10;
               }
            }
         }
      } catch (FileNotFoundException var12) {
         return string4;
      } catch (IOException var13) {
         var13.printStackTrace();
         return string4;
      }
   }

   private static Pair<LightMapPack[], Integer> parseLightMapPacks() {
      String var3 = "mcpatcher/lightmap/world";
      String var4 = ".png";
      String[] var5 = ResUtils.collectFiles(var3, var4);
      HashMap<Integer, String> var6 = new HashMap<Integer, String>();

      for (String var10 : var5) {
         String var11 = StrUtils.removePrefixSuffix(var10, var3, var4);
         int var12 = Config.parseInt(var11, Integer.MIN_VALUE);
         if (var12 == Integer.MIN_VALUE) {
            warn("Invalid dimension ID: " + var11 + ", path: " + var10);
         } else {
            var6.put(var12, var10);
         }
      }

      Integer[] var24 = var6.keySet().toArray(new Integer[0]);
      Arrays.sort(var24);
      if (var24.length == 0) {
         return new ImmutablePair(null, 0);
      }

      int var25 = var24[0];
      CustomColormap[] var26 = new CustomColormap[var24[var24.length - 1] - var25 + 1];

      for (Integer var16 : var24) {
         String var17 = (String)var6.get(var16);
         CustomColormap var18 = getCustomColors(var17, -1, -1);
         if (var18 != null) {
            if (var18.getWidth() < 16) {
               warn("Invalid lightmap width: " + var18.getWidth() + ", path: " + var17);
            } else {
               int var19 = var16 - var25;
               var26[var19] = var18;
            }
         }
      }

      LightMapPack[] var27 = new LightMapPack[var26.length];

      for (int var28 = 0; var28 < var26.length; var28++) {
         CustomColormap var29 = var26[var28];
         if (var29 != null) {
            String var30 = var29.name;
            String var31 = var29.basePath;
            CustomColormap var32 = getCustomColors(var31 + "/" + var30 + "_rain.png", -1, -1);
            CustomColormap var33 = getCustomColors(var31 + "/" + var30 + "_thunder.png", -1, -1);
            LightMap var20 = new LightMap(var29);
            LightMap var21 = var32 != null ? new LightMap(var32) : null;
            LightMap var22 = var33 != null ? new LightMap(var33) : null;
            LightMapPack var23 = new LightMapPack(var20, var21, var22);
            var27[var28] = var23;
         }
      }

      return new ImmutablePair(var27, var25);
   }

   private static int getTextureHeight(String string, int integer) {
      try {
         InputStream var5 = Config.getResourceStream(new ResourceLocation(string));
         if (var5 == null) {
            return integer;
         }

         BufferedImage var6 = ImageIO.read(var5);
         var5.close();
         return var6 == null ? integer : var6.getHeight();
      } catch (IOException var7) {
         return integer;
      }
   }

   private static void readColorProperties(String string) {
      try {
         InputStream var5 = Config.getResourceStream(new ResourceLocation(string));
         if (var5 == null) {
            return;
         }

         dbg("Loading " + string);
         PropertiesOrdered var6 = new PropertiesOrdered();
         var6.load(var5);
         var5.close();
         particleWaterColor = readColor(var6, new String[]{"particle.water", "drop.water"});
         particlePortalColor = readColor(var6, "particle.portal");
         lilyPadColor = readColor(var6, "lilypad");
         expBarTextColor = readColor(var6, "text.xpbar");
         bossTextColor = readColor(var6, "text.boss");
         signTextColor = readColor(var6, "text.sign");
         fogColorNether = readColorVec3(var6, "fog.nether");
         fogColorEnd = readColorVec3(var6, "fog.end");
         skyColorEnd = readColorVec3(var6, "sky.end");
         colorsBlockColormaps = readCustomColormaps(var6, string);
         spawnEggPrimaryColors = readSpawnEggColors(var6, string, "egg.shell.", "Spawn egg shell");
         spawnEggSecondaryColors = readSpawnEggColors(var6, string, "egg.spots.", "Spawn egg spot");
         wolfCollarColors = readDyeColors(var6, string, "collar.", "Wolf collar");
         sheepColors = readDyeColors(var6, string, "sheep.", "Sheep");
         textColors = readTextColors(var6, string, "text.code.", "Text");
         int[] var7 = readMapColors(var6, string, "map.", "Map");
         if (var7 != null) {
            if (mapColorsOriginal == null) {
               mapColorsOriginal = getMapColors();
            }

            setMapColors(var7);
         }

         potionColors = readPotionColors(var6, string, "potion.", "Potion");
         xpOrbTime = Config.parseInt(var6.getProperty("xporb.time"), -1);
      } catch (FileNotFoundException var8) {
      } catch (IOException var9) {
         var9.printStackTrace();
      }
   }

   private static CustomColormap[] readCustomColormaps(Properties properties, String string) {
      ArrayList<CustomColormap> var5 = new ArrayList<CustomColormap>();
      String var6 = "palette.block.";
      HashMap<String, String> var7 = new HashMap<String, String>();

      for (String var9 : properties.stringPropertyNames()) {
         String var10 = properties.getProperty(var9);
         if (var9.startsWith(var6)) {
            var7.put(var9, var10);
         }
      }

      for (String var12 : var7.keySet().toArray(new String[0])) {
         String var13 = properties.getProperty(var12);
         dbg("Block palette: " + var12 + " = " + var13);
         String var14 = var12.substring(14);
         String var15 = TextureUtils.getBasePath(string);
         var14 = TextureUtils.fixResourcePath(var14, var15);
         CustomColormap var16 = getCustomColors(var14, 256, 256);
         if (var16 == null) {
            warn("Colormap not found: " + var14);
         } else {
            ConnectedParser var17 = new ConnectedParser("CustomColors");
            MatchBlock[] var18 = var17.parseMatchBlocks(var13);
            if (var18 != null && var18.length > 0) {
               for (MatchBlock var22 : var18) {
                  var16.addMatchBlock(var22);
               }

               var5.add(var16);
            } else {
               warn("Invalid match blocks: " + var13);
            }
         }
      }

      return var5.size() == 0 ? null : var5.toArray(new CustomColormap[0]);
   }

   private static CustomColormap[][] readBlockColormaps(String[] basePaths, CustomColormap[] basePalettes, int integer3, int integer4) {
      String[] var7 = ResUtils.collectFiles(basePaths, new String[]{".properties"});
      Arrays.sort(var7);
      ArrayList var8 = new ArrayList();

      for (String var12 : var7) {
         dbg("Block colormap: " + var12);

         try {
            InputStream var14 = Config.getResourceStream(new ResourceLocation("minecraft", var12));
            if (var14 == null) {
               warn("File not found: " + var12);
            } else {
               PropertiesOrdered var15 = new PropertiesOrdered();
               var15.load(var14);
               var14.close();
               CustomColormap var16 = new CustomColormap(var15, var12, integer3, integer4, paletteFormatDefault);
               if (var16.isValid(var12) && var16.isValidMatchBlocks(var12)) {
                  addToBlockList(var16, var8);
               }
            }
         } catch (FileNotFoundException var17) {
            warn("File not found: " + var12);
         } catch (Exception var18) {
            var18.printStackTrace();
         }
      }

      if (basePalettes != null) {
         for (CustomColormap var22 : basePalettes) {
            addToBlockList(var22, var8);
         }
      }

      return var8.size() == 0 ? null : blockListToArray(var8);
   }

   private static void addToBlockList(CustomColormap customColormap, List list) {
      int[] var5 = customColormap.getMatchBlockIds();
      if (var5 != null && var5.length > 0) {
         for (int var9 : var5) {
            if (var9 < 0) {
               warn("Invalid block ID: " + var9);
            } else {
               addToList(customColormap, list, var9);
            }
         }
      } else {
         warn("No match blocks: " + Config.arrayToString(var5));
      }
   }

   private static void addToList(CustomColormap customColormap, List list, int integer) {
      while (integer >= list.size()) {
         list.add(null);
      }

      List var6 = (List)list.get(integer);
      if (var6 == null) {
         var6 = new ArrayList();
         list.set(integer, var6);
      }

      var6.add(customColormap);
   }

   private static CustomColormap[][] blockListToArray(List list) {
      CustomColormap[][] var4 = new CustomColormap[list.size()][];

      for (int var5 = 0; var5 < list.size(); var5++) {
         List var6 = (List)list.get(var5);
         if (var6 != null) {
            CustomColormap[] var7 = (CustomColormap[])var6.toArray(new CustomColormap[0]);
            var4[var5] = var7;
         }
      }

      return var4;
   }

   private static int readColor(Properties properties, String[] arr) {
      for (String var8 : arr) {
         int var9 = readColor(properties, var8);
         if (var9 >= 0) {
            return var9;
         }
      }

      return -1;
   }

   private static int readColor(Properties properties, String string) {
      String var5 = properties.getProperty(string);
      if (var5 == null) {
         return -1;
      }

      var5 = var5.trim();
      int var6 = parseColor(var5);
      if (var6 < 0) {
         warn("Invalid color: " + string + " = " + var5);
      } else {
         dbg(string + " = " + var5);
      }

      return var6;
   }

   private static int parseColor(String string) {
      if (string == null) {
         return -1;
      }

      string = string.trim();

      try {
         return Integer.parseInt(string, 16) & 16777215;
      } catch (NumberFormatException var1) {
         return -1;
      }
   }

   private static Vec3 readColorVec3(Properties properties, String string) {
      int var5 = readColor(properties, string);
      if (var5 < 0) {
         return null;
      }

      int var6 = var5 >> 16 & 0xFF;
      int var7 = var5 >> 8 & 0xFF;
      int var8 = var5 & 0xFF;
      float var9 = var6 / 255.0F;
      float var10 = var7 / 255.0F;
      float var11 = var8 / 255.0F;
      return new Vec3(var9, var10, var11);
   }

   private static CustomColormap getCustomColors(String string, String[] arr, int integer3, int integer4) {
      for (String var11 : arr) {
         CustomColormap var12 = getCustomColors(string + var11, integer3, integer4);
         if (var12 != null) {
            return var12;
         }
      }

      return null;
   }

   public static CustomColormap getCustomColors(String string, int integer2, int integer3) {
      try {
         ResourceLocation var6 = new ResourceLocation(string);
         if (!Config.hasResource(var6)) {
            return null;
         }

         dbg("Colormap " + string);
         PropertiesOrdered var7 = new PropertiesOrdered();
         String var8 = StrUtils.replaceSuffix(string, ".png", ".properties");
         ResourceLocation var9 = new ResourceLocation(var8);
         if (Config.hasResource(var9)) {
            InputStream var10 = Config.getResourceStream(var9);
            var7.load(var10);
            var10.close();
            dbg("Colormap properties: " + var8);
         } else {
            var7.put("format", paletteFormatDefault);
            var7.put("source", string);
            var8 = string;
         }

         CustomColormap var12 = new CustomColormap(var7, var8, integer2, integer3, paletteFormatDefault);
         return !var12.isValid(var8) ? null : var12;
      } catch (Exception var11) {
         var11.printStackTrace();
         return null;
      }
   }

   public static void updateUseDefaultGrassFoliageColors() {
      useDefaultGrassFoliageColors = d == null
         && c == null
         && swampGrassColors == null
         && swampFoliageColors == null
         && Config.isSwampColors()
         && Config.isSmoothBiomes();
   }

   public static int getColorMultiplier(BakedQuad bakedQuad, IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos, RenderEnv renderEnv) {
      Block var8 = iBlockState.getBlock();
      IBlockState var9 = renderEnv.getBlockState();
      if (blockColormaps != null) {
         if (!bakedQuad.hasTintIndex()) {
            if (var8 == Blocks.grass) {
               var9 = BLOCK_STATE_DIRT;
            }

            if (var8 == Blocks.redstone_wire) {
               return -1;
            }
         }

         if (var8 == Blocks.double_plant && renderEnv.getMetadata() >= 8) {
            blockPos = blockPos.down();
            var9 = iBlockAccess.getBlockState(blockPos);
         }

         CustomColormap var10 = getBlockColormap(var9);
         if (var10 != null) {
            if (Config.isSmoothBiomes() && !var10.isColorConstant()) {
               return getSmoothColorMultiplier(iBlockState, iBlockAccess, blockPos, var10, renderEnv.getColorizerBlockPosM());
            }

            return var10.getColor(iBlockAccess, blockPos);
         }
      }

      if (!bakedQuad.hasTintIndex()) {
         return -1;
      }

      if (var8 == Blocks.waterlily) {
         return getLilypadColorMultiplier(iBlockAccess, blockPos);
      }

      if (var8 == Blocks.redstone_wire) {
         return getRedstoneColor(renderEnv.getBlockState());
      }

      if (var8 instanceof BlockStem) {
         return getStemColorMultiplier(var8, iBlockAccess, blockPos, renderEnv);
      }

      if (useDefaultGrassFoliageColors) {
         return -1;
      }

      int var12 = renderEnv.getMetadata();
      CustomColors.IColorizer var11;
      if (var8 == Blocks.grass || var8 == Blocks.tallgrass || var8 == Blocks.double_plant) {
         var11 = COLORIZER_GRASS;
      } else if (var8 == Blocks.double_plant) {
         var11 = COLORIZER_GRASS;
         if (var12 >= 8) {
            blockPos = blockPos.down();
         }
      } else if (var8 == Blocks.leaves) {
         switch (var12 & 3) {
            case 1:
               var11 = COLORIZER_FOLIAGE_PINE;
               break;
            case 2:
               var11 = COLORIZER_FOLIAGE_BIRCH;
               break;
            default:
               var11 = COLORIZER_FOLIAGE;
         }
      } else if (var8 == Blocks.leaves2) {
         var11 = COLORIZER_FOLIAGE;
      } else {
         if (var8 != Blocks.vine) {
            return -1;
         }

         var11 = COLORIZER_FOLIAGE;
      }

      return Config.isSmoothBiomes() && !var11.isColorConstant()
         ? getSmoothColorMultiplier(iBlockState, iBlockAccess, blockPos, var11, renderEnv.getColorizerBlockPosM())
         : var11.getColor(var9, iBlockAccess, blockPos);
   }

   protected static BiomeGenBase getColorBiome(IBlockAccess iBlockAccess, BlockPos blockPos) {
      BiomeGenBase var5 = iBlockAccess.getBiomeGenForCoords(blockPos);
      if (var5 == BiomeGenBase.swampland && !Config.isSwampColors()) {
         var5 = BiomeGenBase.plains;
      }

      return var5;
   }

   private static CustomColormap getBlockColormap(IBlockState iBlockState) {
      if (blockColormaps == null) {
         return null;
      } else if (!(iBlockState instanceof BlockStateBase var4)) {
         return null;
      } else {
         int var5 = var4.getBlockId();
         if (var5 >= 0 && var5 < blockColormaps.length) {
            CustomColormap[] var6 = blockColormaps[var5];
            if (var6 != null) {
               for (CustomColormap var10 : var6) {
                  if (var10.matchesBlock(var4)) {
                     return var10;
                  }
               }
            }
         }

         return null;
      }
   }

   private static int getSmoothColorMultiplier(
      IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos, CustomColors.IColorizer iColorizer, BlockPosM blockPosM
   ) {
      int var8 = 0;
      int var9 = 0;
      int var10 = 0;
      int var11 = blockPos.getX();
      int var12 = blockPos.getY();
      int var13 = blockPos.getZ();

      for (int var14 = var11 - 1; var14 <= var11 + 1; var14++) {
         for (int var15 = var13 - 1; var15 <= var13 + 1; var15++) {
            blockPosM.setXyz(var14, var12, var15);
            int var16 = iColorizer.getColor(iBlockState, iBlockAccess, blockPosM);
            var8 += var16 >> 16 & 0xFF;
            var9 += var16 >> 8 & 0xFF;
            var10 += var16 & 0xFF;
         }
      }

      int var17 = var8 / 9;
      int var18 = var9 / 9;
      int var19 = var10 / 9;
      return var17 << 16 | var18 << 8 | var19;
   }

   public static int getFluidColor(IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos, RenderEnv renderEnv) {
      Block var7 = iBlockState.getBlock();
      CustomColors.IColorizer var8 = getBlockColormap(iBlockState);
      if (var8 == null && iBlockState.getBlock().getMaterial() == Material.water) {
         var8 = COLORIZER_WATER;
      }

      return var8 == null
         ? var7.colorMultiplier(iBlockAccess, blockPos, 0)
         : (
            Config.isSmoothBiomes() && !var8.isColorConstant()
               ? getSmoothColorMultiplier(iBlockState, iBlockAccess, blockPos, var8, renderEnv.getColorizerBlockPosM())
               : var8.getColor(iBlockState, iBlockAccess, blockPos)
         );
   }

   public static void updatePortalFX(EntityFX entityFX) {
      if (particlePortalColor >= 0) {
         int var4 = particlePortalColor;
         int var5 = var4 >> 16 & 0xFF;
         int var6 = var4 >> 8 & 0xFF;
         int var7 = var4 & 0xFF;
         float var8 = var5 / 255.0F;
         float var9 = var6 / 255.0F;
         float var10 = var7 / 255.0F;
         entityFX.setRBGColorF(var8, var9, var10);
      }
   }

   public static void updateMyceliumFX(EntityFX entityFX) {
      if (myceliumParticleColors != null) {
         int var4 = myceliumParticleColors.getColorRandom();
         int var5 = var4 >> 16 & 0xFF;
         int var6 = var4 >> 8 & 0xFF;
         int var7 = var4 & 0xFF;
         float var8 = var5 / 255.0F;
         float var9 = var6 / 255.0F;
         float var10 = var7 / 255.0F;
         entityFX.setRBGColorF(var8, var9, var10);
      }
   }

   private static int getRedstoneColor(IBlockState iBlockState) {
      if (redstoneColors == null) {
         return -1;
      }

      int var3 = getRedstoneLevel(iBlockState, 15);
      return redstoneColors.getColor(var3);
   }

   public static void updateReddustFX(EntityFX entityFX, IBlockAccess iBlockAccess, double double3, double double4, double double5) {
      if (redstoneColors != null) {
         int var11 = getRedstoneLevel(iBlockAccess.getBlockState(new BlockPos(double3, double4, double5)), 15);
         int var12 = redstoneColors.getColor(var11);
         int var13 = var12 >> 16 & 0xFF;
         int var14 = var12 >> 8 & 0xFF;
         int var15 = var12 & 0xFF;
         float var16 = var13 / 255.0F;
         float var17 = var14 / 255.0F;
         float var18 = var15 / 255.0F;
         entityFX.setRBGColorF(var16, var17, var18);
      }
   }

   private static int getRedstoneLevel(IBlockState iBlockState, int integer) {
      Block var5 = iBlockState.getBlock();
      if (!(var5 instanceof BlockRedstoneWire)) {
         return integer;
      } else {
         Integer var7 = (Integer)iBlockState.getValue(BlockRedstoneWire.POWER);
         return var7 != null ? var7 : integer;
      }
   }

   public static float getXpOrbTimer(float float1) {
      if (xpOrbTime <= 0) {
         return float1;
      }

      float var3 = 628.0F / xpOrbTime;
      return float1 * var3;
   }

   public static int getXpOrbColor(float float1) {
      if (xpOrbColors == null) {
         return -1;
      }

      int var3 = (int)Math.round((MathHelper.sin(float1) + 1.0F) * (xpOrbColors.getLength() - 1) / 2.0);
      return xpOrbColors.getColor(var3);
   }

   public static int getDurabilityColor(int integer) {
      if (durabilityColors == null) {
         return -1;
      }

      int var3 = integer * durabilityColors.getLength() / 255;
      return durabilityColors.getColor(var3);
   }

   public static void updateWaterFX(EntityFX entityFX, IBlockAccess iBlockAccess, double double3, double double4, double double5, RenderEnv renderEnv) {
      if (waterColors != null || blockColormaps != null || particleWaterColor >= 0) {
         BlockPos var12 = new BlockPos(double3, double4, double5);
         renderEnv.reset(BLOCK_STATE_WATER, var12);
         int var13 = getFluidColor(iBlockAccess, BLOCK_STATE_WATER, var12, renderEnv);
         int var14 = var13 >> 16 & 0xFF;
         int var15 = var13 >> 8 & 0xFF;
         int var16 = var13 & 0xFF;
         float var17 = var14 / 255.0F;
         float var18 = var15 / 255.0F;
         float var19 = var16 / 255.0F;
         if (particleWaterColor >= 0) {
            int var20 = particleWaterColor >> 16 & 0xFF;
            int var21 = particleWaterColor >> 8 & 0xFF;
            int var22 = particleWaterColor & 0xFF;
            var17 *= var20 / 255.0F;
            var18 *= var21 / 255.0F;
            var19 *= var22 / 255.0F;
         }

         entityFX.setRBGColorF(var17, var18, var19);
      }
   }

   private static int getLilypadColorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return lilyPadColor < 0 ? Blocks.waterlily.colorMultiplier(iBlockAccess, blockPos) : lilyPadColor;
   }

   private static Vec3 getFogColorNether(Vec3 vec3) {
      return fogColorNether == null ? vec3 : fogColorNether;
   }

   private static Vec3 getFogColorEnd(Vec3 vec3) {
      return fogColorEnd == null ? vec3 : fogColorEnd;
   }

   private static Vec3 getSkyColorEnd(Vec3 vec3) {
      return skyColorEnd == null ? vec3 : skyColorEnd;
   }

   public static Vec3 getSkyColor(Vec3 vec3, IBlockAccess iBlockAccess, double double3, double double4, double double5) {
      if (skyColors == null) {
         return vec3;
      }

      int var11 = skyColors.getColorSmooth(iBlockAccess, double3, double4, double5, 3);
      int var12 = var11 >> 16 & 0xFF;
      int var13 = var11 >> 8 & 0xFF;
      int var14 = var11 & 0xFF;
      float var15 = var12 / 255.0F;
      float var16 = var13 / 255.0F;
      float var17 = var14 / 255.0F;
      float var18 = (float)vec3.xCoord / 0.5F;
      float var19 = (float)vec3.yCoord / 0.66275F;
      float var20 = (float)vec3.zCoord;
      var15 *= var18;
      var16 *= var19;
      var17 *= var20;
      return skyColorFader.getColor(var15, var16, var17);
   }

   private static Vec3 getFogColor(Vec3 vec3, IBlockAccess iBlockAccess, double double3, double double4, double double5) {
      if (fogColors == null) {
         return vec3;
      }

      int var11 = fogColors.getColorSmooth(iBlockAccess, double3, double4, double5, 3);
      int var12 = var11 >> 16 & 0xFF;
      int var13 = var11 >> 8 & 0xFF;
      int var14 = var11 & 0xFF;
      float var15 = var12 / 255.0F;
      float var16 = var13 / 255.0F;
      float var17 = var14 / 255.0F;
      float var18 = (float)vec3.xCoord / 0.753F;
      float var19 = (float)vec3.yCoord / 0.8471F;
      float var20 = (float)vec3.zCoord;
      var15 *= var18;
      var16 *= var19;
      var17 *= var20;
      return fogColorFader.getColor(var15, var16, var17);
   }

   public static Vec3 getUnderwaterColor(IBlockAccess iBlockAccess, double double2, double double3, double double4) {
      return getUnderFluidColor(iBlockAccess, double2, double3, double4, underwaterColors, underwaterColorFader);
   }

   public static Vec3 getUnderlavaColor(IBlockAccess iBlockAccess, double double2, double double3, double double4) {
      return getUnderFluidColor(iBlockAccess, double2, double3, double4, underlavaColors, underlavaColorFader);
   }

   public static Vec3 getUnderFluidColor(
      IBlockAccess iBlockAccess, double double2, double double3, double double4, CustomColormap customColormap, CustomColorFader customColorFader
   ) {
      if (customColormap == null) {
         return null;
      }

      int var12 = customColormap.getColorSmooth(iBlockAccess, double2, double3, double4, 3);
      int var13 = var12 >> 16 & 0xFF;
      int var14 = var12 >> 8 & 0xFF;
      int var15 = var12 & 0xFF;
      float var16 = var13 / 255.0F;
      float var17 = var14 / 255.0F;
      float var18 = var15 / 255.0F;
      return customColorFader.getColor(var16, var17, var18);
   }

   private static int getStemColorMultiplier(Block block, IBlockAccess iBlockAccess, BlockPos blockPos, RenderEnv renderEnv) {
      CustomColormap var7 = stemColors;
      if (block == Blocks.pumpkin_stem && stemPumpkinColors != null) {
         var7 = stemPumpkinColors;
      }

      if (block == Blocks.melon_stem && stemMelonColors != null) {
         var7 = stemMelonColors;
      }

      if (var7 == null) {
         return -1;
      }

      int var8 = renderEnv.getMetadata();
      return var7.getColor(var8);
   }

   public static boolean updateLightmap(World world, float float2, int[] arr, boolean boolean4, float float5) {
      if (world == null) {
         return false;
      } else if (lightMapPacks == null) {
         return false;
      } else {
         int var9 = world.provider.getDimensionId() - lightmapMinDimensionId;
         if (var9 >= 0 && var9 < lightMapPacks.length) {
            LightMapPack var10 = lightMapPacks[var9];
            return var10 != null && var10.updateLightmap(world, float2, arr, boolean4, float5);
         } else {
            return false;
         }
      }
   }

   public static Vec3 getWorldFogColor(Vec3 vec3, World world, Entity entity, float float4) {
      int var7 = world.provider.getDimensionId();
      switch (var7) {
         case -1:
            vec3 = getFogColorNether(vec3);
            break;
         case 0:
            Minecraft var8 = Minecraft.getMinecraft();
            vec3 = getFogColor(vec3, var8.theWorld, entity.posX, entity.posY + 1.0, entity.posZ);
            break;
         case 1:
            vec3 = getFogColorEnd(vec3);
      }

      return vec3;
   }

   public static Vec3 getWorldSkyColor(Vec3 vec3, World world, Entity entity, float float4) {
      int var7 = world.provider.getDimensionId();
      switch (var7) {
         case 0:
            Minecraft var8 = Minecraft.getMinecraft();
            vec3 = getSkyColor(vec3, var8.theWorld, entity.posX, entity.posY + 1.0, entity.posZ);
            break;
         case 1:
            vec3 = getSkyColorEnd(vec3);
      }

      return vec3;
   }

   private static int[] readSpawnEggColors(Properties properties, String string2, String string3, String string4) {
      ArrayList var7 = new ArrayList();
      Set<String> var8 = properties.stringPropertyNames();
      int var9 = 0;

      for (String var11 : var8) {
         String var12 = properties.getProperty(var11);
         if (var11.startsWith(string3)) {
            int var14 = EntityUtils.getEntityIdByName(StrUtils.removePrefix(var11, string3));
            if (var14 < 0) {
               warn("Invalid spawn egg name: " + var11);
            } else {
               int var15 = parseColor(var12);
               if (var15 < 0) {
                  warn("Invalid spawn egg color: " + var11 + " = " + var12);
               } else {
                  while (var7.size() <= var14) {
                     var7.add(-1);
                  }

                  var7.set(var14, var15);
                  var9++;
               }
            }
         }
      }

      if (var9 <= 0) {
         return null;
      }

      dbg(string4 + " colors: " + var9);
      int[] var16 = new int[var7.size()];

      for (int var17 = 0; var17 < var16.length; var17++) {
         var16[var17] = (Integer)var7.get(var17);
      }

      return var16;
   }

   private static int getSpawnEggColor(ItemMonsterPlacer itemMonsterPlacer, ItemStack itemStack, int integer3, int integer4) {
      int var7 = itemStack.getMetadata();
      int[] var8 = integer3 == 0 ? spawnEggPrimaryColors : spawnEggSecondaryColors;
      if (var8 == null) {
         return integer4;
      } else if (var7 >= 0 && var7 < var8.length) {
         int var9 = var8[var7];
         return var9 < 0 ? integer4 : var9;
      } else {
         return integer4;
      }
   }

   public static int getColorFromItemStack(ItemStack itemStack, int integer2, int integer3) {
      if (itemStack == null) {
         return integer3;
      }

      Item var6 = itemStack.getItem();
      return var6 == null
         ? integer3
         : (var6 instanceof ItemMonsterPlacer ? getSpawnEggColor((ItemMonsterPlacer)var6, itemStack, integer2, integer3) : integer3);
   }

   private static float[][] readDyeColors(Properties properties, String string2, String string3, String string4) {
      EnumDyeColor[] var7 = EnumDyeColor.values();
      HashMap var8 = new HashMap();

      for (EnumDyeColor var12 : var7) {
         var8.put(var12.getName(), var12);
      }

      float[][] var18 = new float[var7.length][];
      int var19 = 0;

      for (String var21 : properties.stringPropertyNames()) {
         String var13 = properties.getProperty(var21);
         if (var21.startsWith(string3)) {
            String var14 = StrUtils.removePrefix(var21, string3);
            if (var14.equals("lightBlue")) {
               var14 = "light_blue";
            }

            EnumDyeColor var15 = (EnumDyeColor)var8.get(var14);
            int var16 = parseColor(var13);
            if (var15 != null && var16 >= 0) {
               float[] var17 = new float[]{(var16 >> 16 & 0xFF) / 255.0F, (var16 >> 8 & 0xFF) / 255.0F, (var16 & 0xFF) / 255.0F};
               var18[var15.ordinal()] = var17;
               var19++;
            } else {
               warn("Invalid color: " + var21 + " = " + var13);
            }
         }
      }

      if (var19 <= 0) {
         return null;
      }

      dbg(string4 + " colors: " + var19);
      return var18;
   }

   private static float[] getDyeColors(EnumDyeColor enumDyeColor, float[][] dyeColors, float[] defaultColors) {
      if (dyeColors == null) {
         return defaultColors;
      }

      if (enumDyeColor == null) {
         return defaultColors;
      }

      float[] var6 = dyeColors[enumDyeColor.ordinal()];
      return var6 == null ? defaultColors : var6;
   }

   public static float[] getWolfCollarColors(EnumDyeColor enumDyeColor, float[] arr) {
      return getDyeColors(enumDyeColor, wolfCollarColors, arr);
   }

   public static float[] getSheepColors(EnumDyeColor enumDyeColor, float[] arr) {
      return getDyeColors(enumDyeColor, sheepColors, arr);
   }

   private static int[] readTextColors(Properties properties, String string2, String string3, String string4) {
      int[] var7 = new int[32];
      Arrays.fill(var7, -1);
      int var8 = 0;

      for (String var10 : properties.stringPropertyNames()) {
         String var11 = properties.getProperty(var10);
         if (var10.startsWith(string3)) {
            int var13 = Config.parseInt(StrUtils.removePrefix(var10, string3), -1);
            int var14 = parseColor(var11);
            if (var13 >= 0 && var13 < var7.length && var14 >= 0) {
               var7[var13] = var14;
               var8++;
            } else {
               warn("Invalid color: " + var10 + " = " + var11);
            }
         }
      }

      if (var8 <= 0) {
         return null;
      }

      dbg(string4 + " colors: " + var8);
      return var7;
   }

   public static int getTextColor(int integer1, int integer2) {
      if (textColors == null) {
         return integer2;
      } else if (integer1 >= 0 && integer1 < textColors.length) {
         int var5 = textColors[integer1];
         return var5 < 0 ? integer2 : var5;
      } else {
         return integer2;
      }
   }

   private static int[] readMapColors(Properties properties, String string2, String string3, String string4) {
      int[] var7 = new int[MapColor.mapColorArray.length];
      Arrays.fill(var7, -1);
      int var8 = 0;

      for (String var10 : properties.stringPropertyNames()) {
         String var11 = properties.getProperty(var10);
         if (var10.startsWith(string3)) {
            int var13 = getMapColorIndex(StrUtils.removePrefix(var10, string3));
            int var14 = parseColor(var11);
            if (var13 >= 0 && var13 < var7.length && var14 >= 0) {
               var7[var13] = var14;
               var8++;
            } else {
               warn("Invalid color: " + var10 + " = " + var11);
            }
         }
      }

      if (var8 <= 0) {
         return null;
      }

      dbg(string4 + " colors: " + var8);
      return var7;
   }

   private static int[] readPotionColors(Properties properties, String string2, String string3, String string4) {
      int[] var7 = new int[Potion.potionTypes.length];
      Arrays.fill(var7, -1);
      int var8 = 0;

      for (String var10 : properties.stringPropertyNames()) {
         String var11 = properties.getProperty(var10);
         if (var10.startsWith(string3)) {
            int var12 = getPotionId(var10);
            int var13 = parseColor(var11);
            if (var12 >= 0 && var12 < var7.length && var13 >= 0) {
               var7[var12] = var13;
               var8++;
            } else {
               warn("Invalid color: " + var10 + " = " + var11);
            }
         }
      }

      if (var8 <= 0) {
         return null;
      }

      dbg(string4 + " colors: " + var8);
      return var7;
   }

   private static int getPotionId(String string) {
      if (string.equals("potion.water")) {
         return 0;
      }

      for (Potion var8 : Potion.potionTypes) {
         if (var8 != null && var8.getName().equals(string)) {
            return var8.getId();
         }
      }

      return -1;
   }

   public static int getPotionColor(int integer1, int integer2) {
      if (potionColors == null) {
         return integer2;
      } else if (integer1 >= 0 && integer1 < potionColors.length) {
         int var5 = potionColors[integer1];
         return var5 < 0 ? integer2 : var5;
      } else {
         return integer2;
      }
   }

   private static int getMapColorIndex(String string) {
      return string == null
         ? -1
         : (
            string.equals("air")
               ? MapColor.airColor.colorIndex
               : (
                  string.equals("grass")
                     ? MapColor.grassColor.colorIndex
                     : (
                        string.equals("sand")
                           ? MapColor.sandColor.colorIndex
                           : (
                              string.equals("cloth")
                                 ? MapColor.clothColor.colorIndex
                                 : (
                                    string.equals("tnt")
                                       ? MapColor.tntColor.colorIndex
                                       : (
                                          string.equals("ice")
                                             ? MapColor.iceColor.colorIndex
                                             : (
                                                string.equals("iron")
                                                   ? MapColor.ironColor.colorIndex
                                                   : (
                                                      string.equals("foliage")
                                                         ? MapColor.foliageColor.colorIndex
                                                         : (
                                                            string.equals("clay")
                                                               ? MapColor.clayColor.colorIndex
                                                               : (
                                                                  string.equals("dirt")
                                                                     ? MapColor.dirtColor.colorIndex
                                                                     : (
                                                                        string.equals("stone")
                                                                           ? MapColor.stoneColor.colorIndex
                                                                           : (
                                                                              string.equals("water")
                                                                                 ? MapColor.waterColor.colorIndex
                                                                                 : (
                                                                                    string.equals("wood")
                                                                                       ? MapColor.woodColor.colorIndex
                                                                                       : (
                                                                                          string.equals("quartz")
                                                                                             ? MapColor.quartzColor.colorIndex
                                                                                             : (
                                                                                                string.equals("gold")
                                                                                                   ? MapColor.goldColor.colorIndex
                                                                                                   : (
                                                                                                      string.equals("diamond")
                                                                                                         ? MapColor.diamondColor.colorIndex
                                                                                                         : (
                                                                                                            string.equals("lapis")
                                                                                                               ? MapColor.lapisColor.colorIndex
                                                                                                               : (
                                                                                                                  string.equals("emerald")
                                                                                                                     ? MapColor.emeraldColor.colorIndex
                                                                                                                     : (
                                                                                                                        string.equals("podzol")
                                                                                                                           ? MapColor.obsidianColor.colorIndex
                                                                                                                           : (
                                                                                                                              string.equals("netherrack")
                                                                                                                                 ? MapColor.netherrackColor.colorIndex
                                                                                                                                 : (
                                                                                                                                    string.equals("snow")
                                                                                                                                          || string.equals(
                                                                                                                                             "white"
                                                                                                                                          )
                                                                                                                                       ? MapColor.snowColor.colorIndex
                                                                                                                                       : (
                                                                                                                                          string.equals("adobe")
                                                                                                                                                || string.equals(
                                                                                                                                                   "orange"
                                                                                                                                                )
                                                                                                                                             ? MapColor.adobeColor
                                                                                                                                                .colorIndex
                                                                                                                                             : (
                                                                                                                                                string.equals(
                                                                                                                                                      "magenta"
                                                                                                                                                   )
                                                                                                                                                   ? MapColor.magentaColor
                                                                                                                                                      .colorIndex
                                                                                                                                                   : (
                                                                                                                                                      !string.equals(
                                                                                                                                                               "light_blue"
                                                                                                                                                            )
                                                                                                                                                            && !string.equals(
                                                                                                                                                               "lightBlue"
                                                                                                                                                            )
                                                                                                                                                         ? (
                                                                                                                                                            string.equals(
                                                                                                                                                                  "yellow"
                                                                                                                                                               )
                                                                                                                                                               ? MapColor.yellowColor
                                                                                                                                                                  .colorIndex
                                                                                                                                                               : (
                                                                                                                                                                  string.equals(
                                                                                                                                                                        "lime"
                                                                                                                                                                     )
                                                                                                                                                                     ? MapColor.limeColor
                                                                                                                                                                        .colorIndex
                                                                                                                                                                     : (
                                                                                                                                                                        string.equals(
                                                                                                                                                                              "pink"
                                                                                                                                                                           )
                                                                                                                                                                           ? MapColor.pinkColor
                                                                                                                                                                              .colorIndex
                                                                                                                                                                           : (
                                                                                                                                                                              string.equals(
                                                                                                                                                                                    "gray"
                                                                                                                                                                                 )
                                                                                                                                                                                 ? MapColor.grayColor
                                                                                                                                                                                    .colorIndex
                                                                                                                                                                                 : (
                                                                                                                                                                                    string.equals(
                                                                                                                                                                                          "silver"
                                                                                                                                                                                       )
                                                                                                                                                                                       ? MapColor.silverColor
                                                                                                                                                                                          .colorIndex
                                                                                                                                                                                       : (
                                                                                                                                                                                          string.equals(
                                                                                                                                                                                                "cyan"
                                                                                                                                                                                             )
                                                                                                                                                                                             ? MapColor.cyanColor
                                                                                                                                                                                                .colorIndex
                                                                                                                                                                                             : (
                                                                                                                                                                                                string.equals(
                                                                                                                                                                                                      "purple"
                                                                                                                                                                                                   )
                                                                                                                                                                                                   ? MapColor.purpleColor
                                                                                                                                                                                                      .colorIndex
                                                                                                                                                                                                   : (
                                                                                                                                                                                                      string.equals(
                                                                                                                                                                                                            "blue"
                                                                                                                                                                                                         )
                                                                                                                                                                                                         ? MapColor.blueColor
                                                                                                                                                                                                            .colorIndex
                                                                                                                                                                                                         : (
                                                                                                                                                                                                            string.equals(
                                                                                                                                                                                                                  "brown"
                                                                                                                                                                                                               )
                                                                                                                                                                                                               ? MapColor.brownColor
                                                                                                                                                                                                                  .colorIndex
                                                                                                                                                                                                               : (
                                                                                                                                                                                                                  string.equals(
                                                                                                                                                                                                                        "green"
                                                                                                                                                                                                                     )
                                                                                                                                                                                                                     ? MapColor.greenColor
                                                                                                                                                                                                                        .colorIndex
                                                                                                                                                                                                                     : (
                                                                                                                                                                                                                        string.equals(
                                                                                                                                                                                                                              "red"
                                                                                                                                                                                                                           )
                                                                                                                                                                                                                           ? MapColor.redColor
                                                                                                                                                                                                                              .colorIndex
                                                                                                                                                                                                                           : (
                                                                                                                                                                                                                              string.equals(
                                                                                                                                                                                                                                    "black"
                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                 ? MapColor.blackColor
                                                                                                                                                                                                                                    .colorIndex
                                                                                                                                                                                                                                 : -1
                                                                                                                                                                                                                           )
                                                                                                                                                                                                                     )
                                                                                                                                                                                                               )
                                                                                                                                                                                                         )
                                                                                                                                                                                                   )
                                                                                                                                                                                             )
                                                                                                                                                                                       )
                                                                                                                                                                                 )
                                                                                                                                                                           )
                                                                                                                                                                     )
                                                                                                                                                               )
                                                                                                                                                         )
                                                                                                                                                         : MapColor.lightBlueColor
                                                                                                                                                            .colorIndex
                                                                                                                                                   )
                                                                                                                                             )
                                                                                                                                       )
                                                                                                                                 )
                                                                                                                           )
                                                                                                                     )
                                                                                                               )
                                                                                                         )
                                                                                                   )
                                                                                             )
                                                                                       )
                                                                                 )
                                                                           )
                                                                     )
                                                               )
                                                         )
                                                   )
                                             )
                                       )
                                 )
                           )
                     )
               )
         );
   }

   private static int[] getMapColors() {
      MapColor[] var3 = MapColor.mapColorArray;
      int[] var4 = new int[var3.length];
      Arrays.fill(var4, -1);

      for (int var5 = 0; var5 < var3.length && var5 < var4.length; var5++) {
         MapColor var6 = var3[var5];
         if (var6 != null) {
            var4[var5] = var6.colorValue;
         }
      }

      return var4;
   }

   private static void setMapColors(int[] arr) {
      if (arr != null) {
         MapColor[] var4 = MapColor.mapColorArray;
         boolean var5 = false;

         for (int var6 = 0; var6 < var4.length && var6 < arr.length; var6++) {
            MapColor var7 = var4[var6];
            if (var7 != null) {
               int var8 = arr[var6];
               if (var8 >= 0 && var7.colorValue != var8) {
                  var7.colorValue = var8;
                  var5 = true;
               }
            }
         }

         if (var5) {
            Minecraft.getMinecraft().getTextureManager().reloadBannerTextures();
         }
      }
   }

   private static void dbg(String string) {
      Config.Method251("CustomColors: " + string);
   }

   private static void warn(String string) {
      Config.Method252("CustomColors: " + string);
   }

   public static int getExpBarTextColor(int integer) {
      return expBarTextColor < 0 ? integer : expBarTextColor;
   }

   public static int getBossTextColor(int integer) {
      return bossTextColor < 0 ? integer : bossTextColor;
   }

   public static int getSignTextColor(int integer) {
      return signTextColor < 0 ? integer : signTextColor;
   }

   public interface IColorizer {
      int getColor(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos);

      boolean isColorConstant();
   }
}
