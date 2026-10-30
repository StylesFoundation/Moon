package net.optifine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.config.Matches;
import net.optifine.util.TextureUtils;

public class CustomColormap implements CustomColors.IColorizer {
   public String name = null;
   public String basePath = null;
   private int format = -1;
   private MatchBlock[] matchBlocks = null;
   private String source = null;
   private int color = -1;
   private int yVariance = 0;
   private int yOffset = 0;
   private int width = 0;
   private int height = 0;
   private int[] colors = null;
   private float[][] colorsRgb = null;
   public static final String[] FORMAT_STRINGS = new String[]{"vanilla", "grid", "fixed"};

   public CustomColormap(Properties properties, String string2, int integer3, int integer4, String string5) {
      ConnectedParser var8 = new ConnectedParser("Colormap");
      this.name = var8.parseName(string2);
      this.basePath = var8.parseBasePath(string2);
      this.format = this.parseFormat(properties.getProperty("format", string5));
      this.matchBlocks = var8.parseMatchBlocks(properties.getProperty("blocks"));
      this.source = parseTexture(properties.getProperty("source"), string2, this.basePath);
      this.color = ConnectedParser.parseColor(properties.getProperty("color"), -1);
      this.yVariance = var8.parseInt(properties.getProperty("yVariance"), 0);
      this.yOffset = var8.parseInt(properties.getProperty("yOffset"), 0);
      this.width = integer3;
      this.height = integer4;
   }

   private int parseFormat(String string) {
      if (string == null) {
         return 0;
      }

      string = string.trim();
      if (string.equals("vanilla")) {
         return 0;
      }

      if (string.equals("grid")) {
         return 1;
      }

      if (string.equals("fixed")) {
         return 2;
      }

      warn("Unknown format: " + string);
      return -1;
   }

   public boolean isValid(String string) {
      if (this.format != 0 && this.format != 1) {
         if (this.format != 2) {
            return false;
         }

         if (this.color < 0) {
            this.color = 16777215;
         }
      } else {
         if (this.source == null) {
            warn("Source not defined: " + string);
            return false;
         }

         this.readColors();
         if (this.colors == null) {
            return false;
         }

         if (this.color < 0) {
            if (this.format == 0) {
               this.color = this.getColor(127, 127);
            }

            if (this.format == 1) {
               this.color = this.getColorGrid(BiomeGenBase.plains, new BlockPos(0, 64, 0));
            }
         }
      }

      return true;
   }

   public boolean isValidMatchBlocks(String string) {
      if (this.matchBlocks == null) {
         this.matchBlocks = this.detectMatchBlocks();
         if (this.matchBlocks == null) {
            warn("Match blocks not defined: " + string);
            return false;
         }
      }

      return true;
   }

   private MatchBlock[] detectMatchBlocks() {
      Block var4 = Block.getBlockFromName(this.name);
      if (var4 != null) {
         return new MatchBlock[]{new MatchBlock(Block.getIdFromBlock(var4))};
      }

      Matcher var6 = Pattern.compile("^block([0-9]+).*$").matcher(this.name);
      if (var6.matches()) {
         int var8 = Config.parseInt(var6.group(1), -1);
         if (var8 >= 0) {
            return new MatchBlock[]{new MatchBlock(var8)};
         }
      }

      return new ConnectedParser("Colormap").parseMatchBlock(this.name);
   }

   private void readColors() {
      try {
         this.colors = null;
         if (this.source == null) {
            return;
         }

         String var4 = this.source + ".png";
         InputStream var6 = Config.getResourceStream(new ResourceLocation(var4));
         if (var6 == null) {
            return;
         }

         BufferedImage var7 = TextureUtil.readBufferedImage(var6);
         if (var7 == null) {
            return;
         }

         int var8 = var7.getWidth();
         int var9 = var7.getHeight();
         boolean var10 = this.width < 0 || this.width == var8;
         boolean var11 = this.height < 0 || this.height == var9;
         if (!var10 || !var11) {
            String var12 = var4;
            int var13 = this.height;
            int var14 = this.width;
            int var15 = var9;
            int var16 = var8;
            dbg("Non-standard palette size: " + var16 + "x" + var15 + ", should be: " + var14 + "x" + var13 + ", path: " + var12);
         }

         this.width = var8;
         this.height = var9;
         if (this.width <= 0 || this.height <= 0) {
            String var17 = var4;
            int var18 = var9;
            int var19 = var8;
            warn("Invalid palette size: " + var19 + "x" + var18 + ", path: " + var17);
            return;
         }

         this.colors = new int[var8 * var9];
         var7.getRGB(0, 0, var8, var9, this.colors, 0, var8);
      } catch (IOException var20) {
         var20.printStackTrace();
      }
   }

   private static void dbg(String string) {
      Config.Method251("CustomColors: " + string);
   }

   private static void warn(String string) {
      Config.Method252("CustomColors: " + string);
   }

   private static String parseTexture(String string1, String string2, String string3) {
      if (string1 != null) {
         string1 = string1.trim();
         String var10 = ".png";
         if (string1.endsWith(var10)) {
            string1 = string1.substring(0, string1.length() - 4);
         }

         return fixTextureName(string1, string3);
      } else {
         String var6 = string2;
         int var7 = string2.lastIndexOf(47);
         if (var7 >= 0) {
            var6 = string2.substring(var7 + 1);
         }

         int var8 = var6.lastIndexOf(46);
         if (var8 >= 0) {
            var6 = var6.substring(0, var8);
         }

         return fixTextureName(var6, string3);
      }
   }

   private static String fixTextureName(String string1, String string2) {
      string1 = TextureUtils.fixResourcePath(string1, string2);
      if (!string1.startsWith(string2) && !string1.startsWith("textures/") && !string1.startsWith("mcpatcher/")) {
         string1 = string2 + "/" + string1;
      }

      if (string1.endsWith(".png")) {
         string1 = string1.substring(0, string1.length() - 4);
      }

      String var5 = "textures/blocks/";
      if (string1.startsWith(var5)) {
         string1 = string1.substring(16);
      }

      if (string1.startsWith("/")) {
         string1 = string1.substring(1);
      }

      return string1;
   }

   public boolean matchesBlock(BlockStateBase blockStateBase) {
      return Matches.block(blockStateBase, this.matchBlocks);
   }

   public int getColorRandom() {
      if (this.format == 2) {
         return this.color;
      }

      int var4 = CustomColors.random.nextInt(this.colors.length);
      return this.colors[var4];
   }

   public int getColor(int integer) {
      integer = Config.Method255(integer, 0, this.colors.length - 1);
      return this.colors[integer] & 16777215;
   }

   public int getColor(int integer1, int integer2) {
      integer1 = Config.Method255(integer1, 0, this.width - 1);
      integer2 = Config.Method255(integer2, 0, this.height - 1);
      return this.colors[integer2 * this.width + integer1] & 16777215;
   }

   public float[][] getColorsRgb() {
      if (this.colorsRgb == null) {
         this.colorsRgb = toRgb(this.colors);
      }

      return this.colorsRgb;
   }

   @Override
   public int getColor(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      return this.getColor(iBlockAccess, blockPos);
   }

   public int getColor(IBlockAccess iBlockAccess, BlockPos blockPos) {
      BiomeGenBase var3 = CustomColors.getColorBiome(iBlockAccess, blockPos);
      return this.getColor(var3, blockPos);
   }

   @Override
   public boolean isColorConstant() {
      return this.format == 2;
   }

   public int getColor(BiomeGenBase biomeGenBase, BlockPos blockPos) {
      return this.format == 0 ? this.getColorVanilla(biomeGenBase, blockPos) : (this.format == 1 ? this.getColorGrid(biomeGenBase, blockPos) : this.color);
   }

   public int getColorSmooth(IBlockAccess iBlockAccess, double double2, double double3, double double4, int integer) {
      if (this.format == 2) {
         return this.color;
      }

      int var12 = MathHelper.floor_double(double2);
      int var13 = MathHelper.floor_double(double3);
      int var14 = MathHelper.floor_double(double4);
      int var15 = 0;
      int var16 = 0;
      int var17 = 0;
      int var18 = 0;
      BlockPosM var19 = new BlockPosM(0, 0, 0);

      for (int var20 = var12 - integer; var20 <= var12 + integer; var20++) {
         for (int var21 = var14 - integer; var21 <= var14 + integer; var21++) {
            var19.setXyz(var20, var13, var21);
            int var22 = this.getColor(iBlockAccess, var19);
            var15 += var22 >> 16 & 0xFF;
            var16 += var22 >> 8 & 0xFF;
            var17 += var22 & 0xFF;
            var18++;
         }
      }

      int var23 = var15 / var18;
      int var24 = var16 / var18;
      int var25 = var17 / var18;
      return var23 << 16 | var24 << 8 | var25;
   }

   private int getColorVanilla(BiomeGenBase biomeGenBase, BlockPos blockPos) {
      double var3 = MathHelper.clamp_float(biomeGenBase.getFloatTemperature(blockPos), 0.0F, 1.0F);
      double var5 = MathHelper.clamp_float(biomeGenBase.getFloatRainfall(), 0.0F, 1.0F);
      var5 *= var3;
      int var7 = (int)((1.0 - var3) * (this.width - 1));
      int var8 = (int)((1.0 - var5) * (this.height - 1));
      return this.getColor(var7, var8);
   }

   private int getColorGrid(BiomeGenBase biomeGenBase, BlockPos blockPos) {
      int var6 = biomeGenBase.biomeID;
      int var7 = blockPos.getY() - this.yOffset;
      if (this.yVariance > 0) {
         int var9 = Config.intHash(blockPos.getX() << 16 + blockPos.getZ());
         int var10 = this.yVariance * 2 + 1;
         int var11 = (var9 & 0xFF) % var10 - this.yVariance;
         var7 += var11;
      }

      return this.getColor(var6, var7);
   }

   public int getLength() {
      return this.format == 2 ? 1 : this.colors.length;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   private static float[][] toRgb(int[] arr) {
      float[][] var4 = new float[arr.length][3];

      for (int var5 = 0; var5 < arr.length; var5++) {
         int var6 = arr[var5];
         float var7 = (var6 >> 16 & 0xFF) / 255.0F;
         float var8 = (var6 >> 8 & 0xFF) / 255.0F;
         float var9 = (var6 & 0xFF) / 255.0F;
         float[] var10 = var4[var5];
         var10[0] = var7;
         var10[1] = var8;
         var10[2] = var9;
      }

      return var4;
   }

   public void addMatchBlock(MatchBlock matchBlock) {
      if (this.matchBlocks == null) {
         this.matchBlocks = new MatchBlock[0];
      }

      this.matchBlocks = (MatchBlock[])Config.addObjectToArray(this.matchBlocks, matchBlock);
   }

   public void addMatchBlock(int integer1, int integer2) {
      MatchBlock var6 = this.getMatchBlock(integer1);
      if (var6 != null) {
         if (integer2 >= 0) {
            var6.addMetadata(integer2);
         }
      } else {
         this.addMatchBlock(new MatchBlock(integer1, integer2));
      }
   }

   private MatchBlock getMatchBlock(int integer) {
      if (this.matchBlocks != null) {
         for (MatchBlock var8 : this.matchBlocks) {
            if (var8.getBlockId() == integer) {
               return var8;
            }
         }
      }

      return null;
   }

   public int[] getMatchBlockIds() {
      if (this.matchBlocks == null) {
         return null;
      }

      HashSet<Integer> var4 = new HashSet<Integer>();

      for (MatchBlock var8 : this.matchBlocks) {
         if (var8.getBlockId() >= 0) {
            var4.add(var8.getBlockId());
         }
      }

      Integer[] var9 = var4.toArray(new Integer[0]);
      int[] var10 = new int[var9.length];

      for (int var11 = 0; var11 < var9.length; var11++) {
         var10[var11] = var9[var11];
      }

      return var10;
   }

   @Override
   public String toString() {
      String var10000 = this.basePath;
      String var10001 = this.name;
      String var10002 = Config.arrayToString(this.matchBlocks);
      String var3 = this.source;
      String var4 = var10002;
      String var5 = var10001;
      return var10000 + "/" + var5 + ", blocks: " + var4 + ", source: " + var3;
   }
}
