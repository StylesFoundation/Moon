package net.optifine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.config.Matches;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import net.optifine.util.MathUtils;
import net.optifine.util.TextureUtils;

public class ConnectedProperties {
   public String name = null;
   public String basePath = null;
   public MatchBlock[] matchBlocks = null;
   public int[] metadatas = null;
   public String[] matchTiles = null;
   public int method = 0;
   public String[] tiles = null;
   public int connect = 0;
   public int faces = 63;
   public BiomeGenBase[] biomes = null;
   public RangeListInt heights = null;
   public int renderPass = 0;
   public boolean innerSeams = false;
   public int[] ctmTileIndexes = null;
   public int width = 0;
   public int height = 0;
   public int[] weights = null;
   public int randomLoops = 0;
   public int symmetry = 1;
   public boolean linked = false;
   public NbtTagValue nbtName = null;
   public int[] sumWeights = null;
   public int sumAllWeights = 1;
   public TextureAtlasSprite[] matchTileIcons = null;
   public TextureAtlasSprite[] tileIcons = null;
   public MatchBlock[] connectBlocks = null;
   public String[] connectTiles = null;
   public TextureAtlasSprite[] connectTileIcons = null;
   public int tintIndex = -1;
   public IBlockState tintBlockState = Blocks.air.getDefaultState();
   public EnumWorldBlockLayer layer = null;

   public ConnectedProperties(Properties properties, String string) {
      ConnectedParser var6 = new ConnectedParser("ConnectedTextures");
      this.name = var6.parseName(string);
      this.basePath = var6.parseBasePath(string);
      this.matchBlocks = var6.parseMatchBlocks(properties.getProperty("matchBlocks"));
      this.metadatas = var6.parseIntList(properties.getProperty("metadata"));
      this.matchTiles = this.parseMatchTiles(properties.getProperty("matchTiles"));
      this.method = parseMethod(properties.getProperty("method"));
      this.tiles = this.parseTileNames(properties.getProperty("tiles"));
      this.connect = parseConnect(properties.getProperty("connect"));
      this.faces = parseFaces(properties.getProperty("faces"));
      this.biomes = var6.parseBiomes(properties.getProperty("biomes"));
      this.heights = var6.parseRangeListInt(properties.getProperty("heights"));
      if (this.heights == null) {
         int var7 = var6.parseInt(properties.getProperty("minHeight"), -1);
         int var8 = var6.parseInt(properties.getProperty("maxHeight"), 1024);
         if (var7 != -1 || var8 != 1024) {
            this.heights = new RangeListInt(new RangeInt(var7, var8));
         }
      }

      this.renderPass = var6.parseInt(properties.getProperty("renderPass"), -1);
      this.innerSeams = var6.parseBoolean(properties.getProperty("innerSeams"), false);
      this.ctmTileIndexes = this.parseCtmTileIndexes(properties);
      this.width = var6.parseInt(properties.getProperty("width"), -1);
      this.height = var6.parseInt(properties.getProperty("height"), -1);
      this.weights = var6.parseIntList(properties.getProperty("weights"));
      this.randomLoops = var6.parseInt(properties.getProperty("randomLoops"), 0);
      this.symmetry = parseSymmetry(properties.getProperty("symmetry"));
      this.linked = var6.parseBoolean(properties.getProperty("linked"), false);
      this.nbtName = var6.parseNbtTagValue("name", properties.getProperty("name"));
      this.connectBlocks = var6.parseMatchBlocks(properties.getProperty("connectBlocks"));
      this.connectTiles = this.parseMatchTiles(properties.getProperty("connectTiles"));
      this.tintIndex = var6.parseInt(properties.getProperty("tintIndex"), -1);
      this.tintBlockState = var6.parseBlockState(properties.getProperty("tintBlock"), Blocks.air.getDefaultState());
      this.layer = var6.parseBlockRenderLayer(properties.getProperty("layer"), EnumWorldBlockLayer.CUTOUT_MIPPED);
   }

   private int[] parseCtmTileIndexes(Properties properties) {
      if (this.tiles == null) {
         return null;
      }

      HashMap var5 = new HashMap();

      for (Object var7 : properties.keySet()) {
         if (var7 instanceof String var8) {
            String var9 = "ctm.";
            if (var8.startsWith(var9)) {
               String var10 = var8.substring(4);
               String var11 = properties.getProperty(var8);
               if (var11 != null) {
                  var11 = var11.trim();
                  int var12 = Config.parseInt(var10, -1);
                  if (var12 >= 0 && var12 <= 46) {
                     int var13 = Config.parseInt(var11, -1);
                     if (var13 >= 0 && var13 < this.tiles.length) {
                        var5.put(var12, var13);
                     } else {
                        Config.Method252("Invalid CTM tile index: " + var11);
                     }
                  } else {
                     Config.Method252("Invalid CTM index: " + var10);
                  }
               }
            }
         }
      }

      if (var5.isEmpty()) {
         return null;
      }

      int[] var14 = new int[47];

      for (int var15 = 0; var15 < var14.length; var15++) {
         var14[var15] = -1;
         if (var5.containsKey(var15)) {
            var14[var15] = (Integer)var5.get(var15);
         }
      }

      return var14;
   }

   private String[] parseMatchTiles(String string) {
      if (string == null) {
         return null;
      }

      String[] var5 = Config.tokenize(string, " ");

      for (int var6 = 0; var6 < var5.length; var6++) {
         String var7 = var5[var6];
         if (var7.endsWith(".png")) {
            var7 = var7.substring(0, var7.length() - 4);
         }

         var7 = TextureUtils.fixResourcePath(var7, this.basePath);
         var5[var6] = var7;
      }

      return var5;
   }

   private static String parseName(String string) {
      String var4 = string;
      int var5 = string.lastIndexOf(47);
      if (var5 >= 0) {
         var4 = string.substring(var5 + 1);
      }

      int var6 = var4.lastIndexOf(46);
      if (var6 >= 0) {
         var4 = var4.substring(0, var6);
      }

      return var4;
   }

   private static String parseBasePath(String string) {
      int var3 = string.lastIndexOf(47);
      return var3 < 0 ? "" : string.substring(0, var3);
   }

   private String[] parseTileNames(String string) {
      if (string == null) {
         return null;
      }

      ArrayList<String> var5 = new ArrayList<>();

      for (String var10 : Config.tokenize(string, " ,")) {
         if (var10.contains("-")) {
            String[] var11 = Config.tokenize(var10, "-");
            if (var11.length == 2) {
               int var12 = Config.parseInt(var11[0], -1);
               int var13 = Config.parseInt(var11[1], -1);
               if (var12 >= 0 && var13 >= 0) {
                  if (var12 > var13) {
                     Config.Method252("Invalid interval: " + var10 + ", when parsing: " + string);
                     continue;
                  }

                  for (int var14 = var12; var14 <= var13; var14++) {
                     var5.add(String.valueOf(var14));
                  }
                  continue;
               }
            }
         }

         var5.add(var10);
      }

      String[] var15 = var5.toArray(new String[0]);

      for (int var16 = 0; var16 < var15.length; var16++) {
         String var17 = TextureUtils.fixResourcePath(var15[var16], this.basePath);
         if (!var17.startsWith(this.basePath) && !var17.startsWith("textures/") && !var17.startsWith("mcpatcher/")) {
            var17 = this.basePath + "/" + var17;
         }

         if (var17.endsWith(".png")) {
            var17 = var17.substring(0, var17.length() - 4);
         }

         if (var17.startsWith("/")) {
            var17 = var17.substring(1);
         }

         var15[var16] = var17;
      }

      return var15;
   }

   private static int parseSymmetry(String string) {
      if (string == null) {
         return 1;
      }

      string = string.trim();
      if (string.equals("opposite")) {
         return 2;
      }

      if (string.equals("all")) {
         return 6;
      }

      Config.Method252("Unknown symmetry: " + string);
      return 1;
   }

   private static int parseFaces(String string) {
      if (string == null) {
         return 63;
      }

      String[] var4 = Config.tokenize(string, " ,");
      int var5 = 0;
      String[] var6 = var4;
      int var7 = var6.length;

      for (int var8 = 0; var8 < var7; var8++) {
         int var10 = parseFace(var6[var8]);
         var5 |= var10;
      }

      return var5;
   }

   private static int parseFace(String string) {
      string = string.toLowerCase();
      if (string.equals("bottom") || string.equals("down")) {
         return 1;
      }

      if (string.equals("top") || string.equals("up")) {
         return 2;
      }

      if (string.equals("north")) {
         return 4;
      }

      if (string.equals("south")) {
         return 8;
      }

      if (string.equals("east")) {
         return 32;
      }

      if (string.equals("west")) {
         return 16;
      }

      if (string.equals("sides")) {
         return 60;
      }

      if (string.equals("all")) {
         return 63;
      }

      Config.Method252("Unknown face: " + string);
      return 128;
   }

   private static int parseConnect(String string) {
      if (string == null) {
         return 0;
      }

      string = string.trim();
      if (string.equals("block")) {
         return 1;
      }

      if (string.equals("tile")) {
         return 2;
      }

      if (string.equals("material")) {
         return 3;
      }

      Config.Method252("Unknown connect: " + string);
      return 128;
   }

   public static IProperty getProperty(String string, Collection<IProperty> collection) {
      for (IProperty var6 : collection) {
         if (string.equals(var6.getName())) {
            return var6;
         }
      }

      return null;
   }

   private static int parseMethod(String string) {
      if (string == null) {
         return 1;
      }

      string = string.trim();
      if (string.equals("ctm") || string.equals("glass")) {
         return 1;
      }

      if (string.equals("ctm_compact")) {
         return 10;
      }

      if (string.equals("horizontal") || string.equals("bookshelf")) {
         return 2;
      }

      if (string.equals("vertical")) {
         return 6;
      }

      if (string.equals("top")) {
         return 3;
      }

      if (string.equals("random")) {
         return 4;
      }

      if (string.equals("repeat")) {
         return 5;
      }

      if (string.equals("fixed")) {
         return 7;
      }

      if (string.equals("horizontal+vertical") || string.equals("h+v")) {
         return 8;
      }

      if (string.equals("vertical+horizontal") || string.equals("v+h")) {
         return 9;
      }

      if (string.equals("overlay")) {
         return 11;
      }

      if (string.equals("overlay_fixed")) {
         return 12;
      }

      if (string.equals("overlay_random")) {
         return 13;
      }

      if (string.equals("overlay_repeat")) {
         return 14;
      }

      if (string.equals("overlay_ctm")) {
         return 15;
      }

      Config.Method252("Unknown method: " + string);
      return 0;
   }

   public boolean isValid(String string) {
      if (this.name == null || this.name.length() <= 0) {
         Config.Method252("No name found: " + string);
         return false;
      }

      if (this.basePath == null) {
         Config.Method252("No base path found: " + string);
         return false;
      }

      if (this.matchBlocks == null) {
         this.matchBlocks = this.detectMatchBlocks();
      }

      if (this.matchTiles == null && this.matchBlocks == null) {
         this.matchTiles = this.detectMatchTiles();
      }

      if (this.matchBlocks == null && this.matchTiles == null) {
         Config.Method252("No matchBlocks or matchTiles specified: " + string);
         return false;
      }

      if (this.method == 0) {
         Config.Method252("No method: " + string);
         return false;
      }

      if (this.tiles != null && this.tiles.length > 0) {
         if (this.connect == 0) {
            this.connect = this.detectConnect();
         }

         if (this.connect == 128) {
            Config.Method252("Invalid connect in: " + string);
            return false;
         }

         if (this.renderPass > 0) {
            Config.Method252("Render pass not supported: " + this.renderPass);
            return false;
         }

         if ((this.faces & 128) != 0) {
            Config.Method252("Invalid faces in: " + string);
            return false;
         }

         if ((this.symmetry & 128) != 0) {
            Config.Method252("Invalid symmetry in: " + string);
            return false;
         }

         switch (this.method) {
            case 1:
               return this.isValidCtm(string);
            case 2:
               return this.isValidHorizontal(string);
            case 3:
               return this.isValidTop(string);
            case 4:
               return this.isValidRandom(string);
            case 5:
               return this.isValidRepeat(string);
            case 6:
               return this.isValidVertical(string);
            case 7:
               return this.isValidFixed(string);
            case 8:
               return this.isValidHorizontalVertical(string);
            case 9:
               return this.isValidVerticalHorizontal(string);
            case 10:
               return this.isValidCtmCompact(string);
            case 11:
               return this.isValidOverlay(string);
            case 12:
               return this.Method203(string);
            case 13:
               return this.Method204(string);
            case 14:
               return this.Method205(string);
            case 15:
               return this.Method206(string);
            default:
               Config.Method252("Unknown method: " + string);
               return false;
         }
      } else {
         Config.Method252("No tiles specified: " + string);
         return false;
      }
   }

   private int detectConnect() {
      return this.matchBlocks != null ? 1 : (this.matchTiles != null ? 2 : 128);
   }

   private MatchBlock[] detectMatchBlocks() {
      int[] var4 = this.detectMatchBlockIds();
      if (var4 == null) {
         return null;
      }

      MatchBlock[] var5 = new MatchBlock[var4.length];

      for (int var6 = 0; var6 < var5.length; var6++) {
         var5[var6] = new MatchBlock(var4[var6]);
      }

      return var5;
   }

   private int[] detectMatchBlockIds() {
      if (!this.name.startsWith("block")) {
         return null;
      }

      byte var4 = 5;

      int var5;
      for (var5 = var4; var5 < this.name.length(); var5++) {
         char var6 = this.name.charAt(var5);
         if (var6 < '0' || var6 > '9') {
            break;
         }
      }

      if (var5 == var4) {
         return null;
      }

      int var7 = Config.parseInt(this.name.substring(var4, var5), -1);
      return var7 < 0 ? null : new int[]{var7};
   }

   private String[] detectMatchTiles() {
      TextureAtlasSprite var3 = getIcon(this.name);
      return var3 == null ? null : new String[]{this.name};
   }

   private static TextureAtlasSprite getIcon(String string) {
      TextureMap var4 = Minecraft.getMinecraft().getTextureMapBlocks();
      TextureAtlasSprite var5 = var4.getSpriteSafe(string);
      if (var5 == null) {
         var5 = var4.getSpriteSafe("blocks/" + string);
      }

      return var5;
   }

   private boolean isValidCtm(String string) {
      if (this.tiles == null) {
         this.tiles = this.parseTileNames("0-11 16-27 32-43 48-58");
      }

      if (this.tiles.length < 47) {
         Config.Method252("Invalid tiles, must be at least 47: " + string);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidCtmCompact(String string) {
      if (this.tiles == null) {
         this.tiles = this.parseTileNames("0-4");
      }

      if (this.tiles.length < 5) {
         Config.Method252("Invalid tiles, must be at least 5: " + string);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidOverlay(String string) {
      if (this.tiles == null) {
         this.tiles = this.parseTileNames("0-16");
      }

      if (this.tiles.length < 17) {
         Config.Method252("Invalid tiles, must be at least 17: " + string);
         return false;
      }

      if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID) {
         return true;
      }

      Config.Method252("Invalid overlay layer: " + this.layer);
      return false;
   }

   private boolean Method203(String string) {
      if (!this.isValidFixed(string)) {
         return false;
      }

      if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID) {
         return true;
      }

      Config.Method252("Invalid overlay layer: " + this.layer);
      return false;
   }

   private boolean Method204(String string) {
      if (!this.isValidRandom(string)) {
         return false;
      }

      if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID) {
         return true;
      }

      Config.Method252("Invalid overlay layer: " + this.layer);
      return false;
   }

   private boolean Method205(String string) {
      if (!this.isValidRepeat(string)) {
         return false;
      }

      if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID) {
         return true;
      }

      Config.Method252("Invalid overlay layer: " + this.layer);
      return false;
   }

   private boolean Method206(String string) {
      if (!this.isValidCtm(string)) {
         return false;
      }

      if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID) {
         return true;
      }

      Config.Method252("Invalid overlay layer: " + this.layer);
      return false;
   }

   private boolean isValidHorizontal(String string) {
      if (this.tiles == null) {
         this.tiles = this.parseTileNames("12-15");
      }

      if (this.tiles.length != 4) {
         Config.Method252("Invalid tiles, must be exactly 4: " + string);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidVertical(String string) {
      if (this.tiles == null) {
         Config.Method252("No tiles defined for vertical: " + string);
         return false;
      } else if (this.tiles.length != 4) {
         Config.Method252("Invalid tiles, must be exactly 4: " + string);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidHorizontalVertical(String string) {
      if (this.tiles == null) {
         Config.Method252("No tiles defined for horizontal+vertical: " + string);
         return false;
      } else if (this.tiles.length != 7) {
         Config.Method252("Invalid tiles, must be exactly 7: " + string);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidVerticalHorizontal(String string) {
      if (this.tiles == null) {
         Config.Method252("No tiles defined for vertical+horizontal: " + string);
         return false;
      } else if (this.tiles.length != 7) {
         Config.Method252("Invalid tiles, must be exactly 7: " + string);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidRandom(String string) {
      if (this.tiles != null && this.tiles.length > 0) {
         if (this.weights != null) {
            if (this.weights.length > this.tiles.length) {
               Config.Method252("More weights defined than tiles, trimming weights: " + string);
               int[] var5 = new int[this.tiles.length];
               System.arraycopy(this.weights, 0, var5, 0, var5.length);
               this.weights = var5;
            }

            if (this.weights.length < this.tiles.length) {
               Config.Method252("Less weights defined than tiles, expanding weights: " + string);
               int[] var8 = new int[this.tiles.length];
               System.arraycopy(this.weights, 0, var8, 0, this.weights.length);
               int var6 = MathUtils.getAverage(this.weights);

               for (int var7 = this.weights.length; var7 < var8.length; var7++) {
                  var8[var7] = var6;
               }

               this.weights = var8;
            }

            this.sumWeights = new int[this.weights.length];
            int var9 = 0;

            for (int var10 = 0; var10 < this.weights.length; var10++) {
               var9 += this.weights[var10];
               this.sumWeights[var10] = var9;
            }

            this.sumAllWeights = var9;
            if (this.sumAllWeights <= 0) {
               Config.Method252("Invalid sum of all weights: " + var9);
               this.sumAllWeights = 1;
            }
         }

         if (this.randomLoops >= 0 && this.randomLoops <= 9) {
            return true;
         }

         Config.Method252("Invalid randomLoops: " + this.randomLoops);
         return false;
      } else {
         Config.Method252("Tiles not defined: " + string);
         return false;
      }
   }

   private boolean isValidRepeat(String string) {
      if (this.tiles == null) {
         Config.Method252("Tiles not defined: " + string);
         return false;
      } else if (this.width <= 0) {
         Config.Method252("Invalid width: " + string);
         return false;
      } else if (this.height <= 0) {
         Config.Method252("Invalid height: " + string);
         return false;
      } else if (this.tiles.length != this.width * this.height) {
         Config.Method252("Number of tiles does not equal width x height: " + string);
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidFixed(String string) {
      if (this.tiles == null) {
         Config.Method252("Tiles not defined: " + string);
         return false;
      } else if (this.tiles.length != 1) {
         Config.Method252("Number of tiles should be 1 for method: fixed.");
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidTop(String string) {
      if (this.tiles == null) {
         this.tiles = this.parseTileNames("66");
      }

      if (this.tiles.length != 1) {
         Config.Method252("Invalid tiles, must be exactly 1: " + string);
         return false;
      } else {
         return true;
      }
   }

   public void updateIcons(TextureMap textureMap) {
      if (this.matchTiles != null) {
         this.matchTileIcons = registerIcons(this.matchTiles, textureMap, false, false);
      }

      if (this.connectTiles != null) {
         this.connectTileIcons = registerIcons(this.connectTiles, textureMap, false, false);
      }

      if (this.tiles != null) {
         this.tileIcons = registerIcons(this.tiles, textureMap, true, !isMethodOverlay(this.method));
      }
   }

   private static boolean isMethodOverlay(int integer) {
      switch (integer) {
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
            return true;
         default:
            return false;
      }
   }

   private static TextureAtlasSprite[] registerIcons(String[] arr, TextureMap textureMap, boolean boolean3, boolean boolean4) {
      if (arr == null) {
         return null;
      }

      ArrayList<TextureAtlasSprite> var7 = new ArrayList();

      for (String var11 : arr) {
         ResourceLocation var12 = new ResourceLocation(var11);
         String var13 = var12.getResourceDomain();
         String var14 = var12.getResourcePath();
         if (!var14.contains("/")) {
            var14 = "textures/blocks/" + var14;
         }

         String var15 = var14 + ".png";
         if (boolean3 && var15.endsWith("<skip>.png")) {
            var7.add(null);
         } else if (boolean4 && var15.endsWith("<default>.png")) {
            var7.add(ConnectedTextures.SPRITE_DEFAULT);
         } else {
            boolean var17 = Config.hasResource(new ResourceLocation(var13, var15));
            if (!var17) {
               Config.Method252("File not found: " + var15);
            }

            String var18 = "textures/";
            String var19 = var14;
            if (var14.startsWith(var18)) {
               var19 = var14.substring(9);
            }

            ResourceLocation var20 = new ResourceLocation(var13, var19);
            TextureAtlasSprite var21 = textureMap.registerSprite(var20);
            var7.add(var21);
         }
      }

      return var7.toArray(new TextureAtlasSprite[0]);
   }

   public boolean matchesBlockId(int integer) {
      return Matches.blockId(integer, this.matchBlocks);
   }

   public boolean matchesBlock(int integer1, int integer2) {
      return Matches.block(integer1, integer2, this.matchBlocks) && Matches.metadata(integer2, this.metadatas);
   }

   public boolean matchesIcon(TextureAtlasSprite textureAtlasSprite) {
      return Matches.sprite(textureAtlasSprite, this.matchTileIcons);
   }

   @Override
   public String toString() {
      String var10000 = this.name;
      String var10001 = this.basePath;
      String var10002 = Config.arrayToString(this.matchBlocks);
      String var3 = Config.arrayToString(this.matchTiles);
      String var4 = var10002;
      String var5 = var10001;
      String var6 = var10000;
      return "CTM name: " + var6 + ", basePath: " + var5 + ", matchBlocks: " + var4 + ", matchTiles: " + var3;
   }

   public boolean matchesBiome(BiomeGenBase biomeGenBase) {
      return Matches.biome(biomeGenBase, this.biomes);
   }

   public int getMetadataMax() {
      int var4 = this.getMax(this.metadatas, -1);
      if (this.matchBlocks != null) {
         for (MatchBlock var8 : this.matchBlocks) {
            var4 = this.getMax(var8.getMetadatas(), var4);
         }
      }

      return var4;
   }

   private int getMax(int[] arr, int integer) {
      if (arr != null) {
         for (int var9 : arr) {
            if (var9 > integer) {
               integer = var9;
            }
         }
      }

      return integer;
   }
}
