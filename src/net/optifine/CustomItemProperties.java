package net.optifine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.src.Config;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.optifine.config.IParserInt;
import net.optifine.config.NbtTagValue;
import net.optifine.config.ParserEnchantmentId;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import net.optifine.render.Blender;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;

public class CustomItemProperties {
   public String name = null;
   public String basePath = null;
   public int type = 1;
   public int[] items = null;
   public String texture = null;
   public Map<String, String> mapTextures = null;
   public String model = null;
   public Map<String, String> mapModels = null;
   public RangeListInt damage = null;
   public boolean damagePercent = false;
   public int damageMask = 0;
   public RangeListInt stackSize = null;
   public RangeListInt enchantmentIds = null;
   public RangeListInt enchantmentLevels = null;
   public NbtTagValue[] nbtTagValues = null;
   public int hand = 0;
   public int blend = 1;
   public float speed = 0.0F;
   public float rotation = 0.0F;
   public int t = 0;
   public float duration = 1.0F;
   public int weight = 0;
   public ResourceLocation textureLocation = null;
   public Map mapTextureLocations = null;
   public TextureAtlasSprite sprite = null;
   public Map mapSprites = null;
   public IBakedModel bakedModelTexture = null;
   public Map<String, IBakedModel> mapBakedModelsTexture = null;
   public IBakedModel bakedModelFull = null;
   public Map<String, IBakedModel> mapBakedModelsFull = null;
   private int textureWidth = 0;
   private int textureHeight = 0;

   public CustomItemProperties(Properties properties, String string) {
      this.name = parseName(string);
      this.basePath = parseBasePath(string);
      this.type = this.parseType(properties.getProperty("type"));
      this.items = this.parseItems(properties.getProperty("items"), properties.getProperty("matchItems"));
      this.mapModels = parseModels(properties, this.basePath);
      this.model = parseModel(properties.getProperty("model"), string, this.basePath, this.type, this.mapModels);
      this.mapTextures = parseTextures(properties, this.basePath);
      boolean var6 = this.mapModels == null && this.model == null;
      this.texture = parseTexture(
         properties.getProperty("texture"),
         properties.getProperty("tile"),
         properties.getProperty("source"),
         string,
         this.basePath,
         this.type,
         this.mapTextures,
         var6
      );
      String var7 = properties.getProperty("damage");
      if (var7 != null) {
         this.damagePercent = var7.contains("%");
         var7 = var7.replace("%", "");
         this.damage = this.parseRangeListInt(var7);
         this.damageMask = this.parseInt(properties.getProperty("damageMask"), 0);
      }

      this.stackSize = this.parseRangeListInt(properties.getProperty("stackSize"));
      this.enchantmentIds = this.parseRangeListInt(properties.getProperty("enchantmentIDs"), new ParserEnchantmentId());
      this.enchantmentLevels = this.parseRangeListInt(properties.getProperty("enchantmentLevels"));
      this.nbtTagValues = this.parseNbtTagValues(properties);
      this.hand = this.parseHand(properties.getProperty("hand"));
      this.blend = Blender.parseBlend(properties.getProperty("blend"));
      this.speed = this.parseFloat(properties.getProperty("speed"), 0.0F);
      this.rotation = this.parseFloat(properties.getProperty("rotation"), 0.0F);
      this.t = this.parseInt(properties.getProperty("layer"), 0);
      this.weight = this.parseInt(properties.getProperty("weight"), 0);
      this.duration = this.parseFloat(properties.getProperty("duration"), 1.0F);
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

   private int parseType(String string) {
      if (string == null) {
         return 1;
      }

      if (string.equals("item")) {
         return 1;
      }

      if (string.equals("enchantment")) {
         return 2;
      }

      if (string.equals("armor")) {
         return 3;
      }

      Config.Method252("Unknown method: " + string);
      return 0;
   }

   private int[] parseItems(String string1, String string2) {
      if (string1 == null) {
         string1 = string2;
      }

      if (string1 == null) {
         return null;
      }

      string1 = string1.trim();
      TreeSet<Integer> var6 = new TreeSet<Integer>();

      for (String var11 : Config.tokenize(string1, " ")) {
         int var12 = Config.parseInt(var11, -1);
         if (var12 >= 0) {
            var6.add(var12);
         } else {
            if (var11.contains("-")) {
               String[] var13 = Config.tokenize(var11, "-");
               if (var13.length == 2) {
                  int var14 = Config.parseInt(var13[0], -1);
                  int var15 = Config.parseInt(var13[1], -1);
                  if (var14 >= 0 && var15 >= 0) {
                     int var16 = Math.min(var14, var15);
                     int var17 = Math.max(var14, var15);

                     for (int var18 = var16; var18 <= var17; var18++) {
                        var6.add(var18);
                     }
                     continue;
                  }
               }
            }

            Item var23 = Item.getByNameOrId(var11);
            if (var23 == null) {
               Config.Method252("Item not found: " + var11);
            } else {
               int var24 = Item.getIdFromItem(var23);
               if (var24 <= 0) {
                  Config.Method252("Item not found: " + var11);
               } else {
                  var6.add(var24);
               }
            }
         }
      }

      Integer[] var20 = var6.toArray(new Integer[0]);
      int[] var21 = new int[var20.length];

      for (int var22 = 0; var22 < var21.length; var22++) {
         var21[var22] = var20[var22];
      }

      return var21;
   }

   private static String parseTexture(
      String string1, String string2, String string3, String string4, String string5, int integer, Map<String, String> map, boolean boolean8
   ) {
      if (string1 == null) {
         string1 = string2;
      }

      if (string1 == null) {
         string1 = string3;
      }

      if (string1 != null) {
         String var15 = ".png";
         if (string1.endsWith(var15)) {
            string1 = string1.substring(0, string1.length() - 4);
         }

         return fixTextureName(string1, string5);
      } else {
         if (integer == 3) {
            return null;
         }

         if (map != null) {
            String var11 = (String)map.get("texture.bow_standby");
            if (var11 != null) {
               return var11;
            }
         }

         if (!boolean8) {
            return null;
         }

         String var14 = string4;
         int var12 = string4.lastIndexOf(47);
         if (var12 >= 0) {
            var14 = string4.substring(var12 + 1);
         }

         int var13 = var14.lastIndexOf(46);
         if (var13 >= 0) {
            var14 = var14.substring(0, var13);
         }

         return fixTextureName(var14, string5);
      }
   }

   private static Map parseTextures(Properties properties, String string) {
      String var5 = "texture.";
      Map<String, String> var6 = getMatchingProperties(properties, var5);
      if (var6.size() == 0) {
         return null;
      }

      Set<String> var7 = var6.keySet();
      LinkedHashMap<String, String> var8 = new LinkedHashMap<String, String>();

      for (String var10 : var7) {
         String var11 = fixTextureName((String)var6.get(var10), string);
         var8.put(var10, var11);
      }

      return var8;
   }

   private static String fixTextureName(String string1, String string2) {
      string1 = TextureUtils.fixResourcePath(string1, string2);
      if (!string1.startsWith(string2) && !string1.startsWith("textures/") && !string1.startsWith("mcpatcher/")) {
         string1 = string2 + "/" + string1;
      }

      if (string1.endsWith(".png")) {
         string1 = string1.substring(0, string1.length() - 4);
      }

      if (string1.startsWith("/")) {
         string1 = string1.substring(1);
      }

      return string1;
   }

   private static String parseModel(String string1, String string2, String string3, int integer, Map<String, String> map) {
      if (string1 != null) {
         String var9 = ".json";
         if (string1.endsWith(var9)) {
            string1 = string1.substring(0, string1.length() - 5);
         }

         return fixModelName(string1, string3);
      } else {
         if (integer == 3) {
            return null;
         }

         if (map != null) {
            String var8 = (String)map.get("model.bow_standby");
            if (var8 != null) {
               return var8;
            }
         }

         return string1;
      }
   }

   private static Map parseModels(Properties properties, String string) {
      String var5 = "model.";
      Map<String, String> var6 = getMatchingProperties(properties, var5);
      if (var6.size() == 0) {
         return null;
      }

      Set<String> var7 = var6.keySet();
      LinkedHashMap<String, String> var8 = new LinkedHashMap<String, String>();

      for (String var10 : var7) {
         String var11 = fixModelName((String)var6.get(var10), string);
         var8.put(var10, var11);
      }

      return var8;
   }

   private static String fixModelName(String string1, String string2) {
      string1 = TextureUtils.fixResourcePath(string1, string2);
      boolean var5 = string1.startsWith("block/") || string1.startsWith("item/");
      if (!string1.startsWith(string2) && !var5 && !string1.startsWith("mcpatcher/")) {
         string1 = string2 + "/" + string1;
      }

      String var6 = ".json";
      if (string1.endsWith(var6)) {
         string1 = string1.substring(0, string1.length() - 5);
      }

      if (string1.startsWith("/")) {
         string1 = string1.substring(1);
      }

      return string1;
   }

   private int parseInt(String string, int integer) {
      if (string == null) {
         return integer;
      } else {
         string = string.trim();
         int var6 = Config.parseInt(string, Integer.MIN_VALUE);
         if (var6 == Integer.MIN_VALUE) {
            Config.Method252("Invalid integer: " + string);
            return integer;
         } else {
            return var6;
         }
      }
   }

   private float parseFloat(String string, float float2) {
      if (string == null) {
         return float2;
      } else {
         string = string.trim();
         float var6 = Config.parseFloat(string, Float.MIN_VALUE);
         if (var6 == Float.MIN_VALUE) {
            Config.Method252("Invalid float: " + string);
            return float2;
         } else {
            return var6;
         }
      }
   }

   private RangeListInt parseRangeListInt(String string) {
      return this.parseRangeListInt(string, null);
   }

   private RangeListInt parseRangeListInt(String string, IParserInt iParserInt) {
      if (string == null) {
         return null;
      }

      String[] var6 = Config.tokenize(string, " ");
      RangeListInt var7 = new RangeListInt();

      for (String var11 : var6) {
         if (iParserInt != null) {
            int var12 = iParserInt.parse(var11, Integer.MIN_VALUE);
            if (var12 != Integer.MIN_VALUE) {
               var7.addRange(new RangeInt(var12, var12));
               continue;
            }
         }

         RangeInt var13 = this.parseRangeInt(var11);
         if (var13 == null) {
            Config.Method252("Invalid range list: " + string);
            return null;
         }

         var7.addRange(var13);
      }

      return var7;
   }

   private RangeInt parseRangeInt(String string) {
      if (string == null) {
         return null;
      }

      string = string.trim();
      int var5 = string.length() - string.replace("-", "").length();
      if (var5 > 1) {
         Config.Method252("Invalid range: " + string);
         return null;
      }

      String[] var6 = Config.tokenize(string, "- ");
      int[] var7 = new int[var6.length];

      for (int var8 = 0; var8 < var6.length; var8++) {
         int var10 = Config.parseInt(var6[var8], -1);
         if (var10 < 0) {
            Config.Method252("Invalid range: " + string);
            return null;
         }

         var7[var8] = var10;
      }

      if (var7.length == 1) {
         int var13 = var7[0];
         if (string.startsWith("-")) {
            return new RangeInt(0, var13);
         } else {
            return string.endsWith("-") ? new RangeInt(var13, 65535) : new RangeInt(var13, var13);
         }
      } else if (var7.length == 2) {
         int var12 = Math.min(var7[0], var7[1]);
         int var9 = Math.max(var7[0], var7[1]);
         return new RangeInt(var12, var9);
      } else {
         Config.Method252("Invalid range: " + string);
         return null;
      }
   }

   private NbtTagValue[] parseNbtTagValues(Properties properties) {
      String var5 = "nbt.";
      Map<String, String> var6 = getMatchingProperties(properties, var5);
      if (var6.size() == 0) {
         return null;
      }

      ArrayList<NbtTagValue> var7 = new ArrayList<NbtTagValue>();

      for (String var9 : var6.keySet()) {
         String var10 = (String)var6.get(var9);
         String var11 = var9.substring(4);
         NbtTagValue var12 = new NbtTagValue(var11, var10);
         var7.add(var12);
      }

      return var7.toArray(new NbtTagValue[0]);
   }

   private static Map<String, String> getMatchingProperties(Properties properties, String string) {
      LinkedHashMap<String, String> var5 = new LinkedHashMap<String, String>();

      for (String var7 : properties.stringPropertyNames()) {
         String var8 = properties.getProperty(var7);
         if (var7.startsWith(string)) {
            var5.put(var7, var8);
         }
      }

      return var5;
   }

   private int parseHand(String string) {
      if (string == null) {
         return 0;
      }

      string = string.toLowerCase();
      if (string.equals("any")) {
         return 0;
      }

      if (string.equals("main")) {
         return 1;
      }

      if (string.equals("off")) {
         return 2;
      }

      Config.Method252("Invalid hand: " + string);
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

      if (this.type == 0) {
         Config.Method252("No type defined: " + string);
         return false;
      }

      if (this.type == 1 || this.type == 3) {
         if (this.items == null) {
            this.items = this.detectItems();
         }

         if (this.items == null) {
            Config.Method252("No items defined: " + string);
            return false;
         }
      }

      if (this.texture == null && this.mapTextures == null && this.model == null && this.mapModels == null) {
         Config.Method252("No texture or model specified: " + string);
         return false;
      } else if (this.type == 2 && this.enchantmentIds == null) {
         Config.Method252("No enchantmentIDs specified: " + string);
         return false;
      } else {
         return true;
      }
   }

   private int[] detectItems() {
      Item var4 = Item.getByNameOrId(this.name);
      if (var4 == null) {
         return null;
      }

      int var5 = Item.getIdFromItem(var4);
      return var5 <= 0 ? null : new int[]{var5};
   }

   public void updateIcons(TextureMap textureMap) {
      if (this.texture != null) {
         this.textureLocation = this.getTextureLocation(this.texture);
         if (this.type == 1) {
            ResourceLocation var5 = this.getSpriteLocation(this.textureLocation);
            this.sprite = textureMap.registerSprite(var5);
         }
      }

      if (this.mapTextures != null) {
         this.mapTextureLocations = new HashMap();
         this.mapSprites = new HashMap();

         for (String var6 : this.mapTextures.keySet()) {
            String var7 = this.mapTextures.get(var6);
            ResourceLocation var8 = this.getTextureLocation(var7);
            this.mapTextureLocations.put(var6, var8);
            if (this.type == 1) {
               ResourceLocation var9 = this.getSpriteLocation(var8);
               TextureAtlasSprite var10 = textureMap.registerSprite(var9);
               this.mapSprites.put(var6, var10);
            }
         }
      }
   }

   private ResourceLocation getTextureLocation(String string) {
      if (string == null) {
         return null;
      }

      ResourceLocation var5 = new ResourceLocation(string);
      String var6 = var5.getResourceDomain();
      String var7 = var5.getResourcePath();
      if (!var7.contains("/")) {
         var7 = "textures/items/" + var7;
      }

      String var8 = var7 + ".png";
      ResourceLocation var9 = new ResourceLocation(var6, var8);
      boolean var10 = Config.hasResource(var9);
      if (!var10) {
         Config.Method252("File not found: " + var8);
      }

      return var9;
   }

   private ResourceLocation getSpriteLocation(ResourceLocation resourceLocation) {
      String var4 = StrUtils.removeSuffix(StrUtils.removePrefix(resourceLocation.getResourcePath(), "textures/"), ".png");
      return new ResourceLocation(resourceLocation.getResourceDomain(), var4);
   }

   public void updateModelTexture(TextureMap textureMap, ItemModelGenerator itemModelGenerator) {
      if (this.texture != null || this.mapTextures != null) {
         String[] var6 = this.getModelTextures();
         boolean var7 = this.isUseTint();
         this.bakedModelTexture = makeBakedModel(textureMap, itemModelGenerator, var6, var7);
         if (this.type == 1 && this.mapTextures != null) {
            for (String var9 : this.mapTextures.keySet()) {
               String var10 = this.mapTextures.get(var9);
               String var11 = StrUtils.removePrefix(var9, "texture.");
               if (var11.startsWith("bow") || var11.startsWith("fishing_rod") || var11.startsWith("shield")) {
                  String[] var12 = new String[]{var10};
                  IBakedModel var13 = makeBakedModel(textureMap, itemModelGenerator, var12, var7);
                  if (this.mapBakedModelsTexture == null) {
                     this.mapBakedModelsTexture = new HashMap<>();
                  }

                  this.mapBakedModelsTexture.put(var11, var13);
               }
            }
         }
      }
   }

   private boolean isUseTint() {
      return true;
   }

   private static IBakedModel makeBakedModel(TextureMap textureMap, ItemModelGenerator itemModelGenerator, String[] arr, boolean boolean4) {
      String[] var7 = new String[arr.length];

      for (int var8 = 0; var8 < var7.length; var8++) {
         String var9 = arr[var8];
         var7[var8] = StrUtils.removePrefix(var9, "textures/");
      }

      ModelBlock var10 = makeModelBlock(var7);
      ModelBlock var11 = itemModelGenerator.makeItemModel(textureMap, var10);
      return bakeModel(textureMap, var11, boolean4);
   }

   private String[] getModelTextures() {
      if (this.type == 1 && this.items.length == 1) {
         Item var4 = Item.getItemById(this.items[0]);
         if (var4 == Items.potionitem && this.damage != null && this.damage.getCountRanges() > 0) {
            int var11 = this.damage.getRange(0).getMin();
            boolean var12 = (var11 & 16384) != 0;
            String var13 = this.getMapTexture(this.mapTextures, "texture.potion_overlay", "items/potion_overlay");
            String var14;
            if (var12) {
               var14 = this.getMapTexture(this.mapTextures, "texture.potion_bottle_splash", "items/potion_bottle_splash");
            } else {
               var14 = this.getMapTexture(this.mapTextures, "texture.potion_bottle_drinkable", "items/potion_bottle_drinkable");
            }

            return new String[]{var13, var14};
         }

         if (var4 instanceof ItemArmor var5 && var5.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
            String var6 = "leather";
            String var7 = "helmet";
            if (var5.armorType == 0) {
               var7 = "helmet";
            }

            if (var5.armorType == 1) {
               var7 = "chestplate";
            }

            if (var5.armorType == 2) {
               var7 = "leggings";
            }

            if (var5.armorType == 3) {
               var7 = "boots";
            }

            String var8 = var6 + "_" + var7;
            String var9 = this.getMapTexture(this.mapTextures, "texture." + var8, "items/" + var8);
            String var10 = this.getMapTexture(this.mapTextures, "texture." + var8 + "_overlay", "items/" + var8 + "_overlay");
            return new String[]{var9, var10};
         }
      }

      return new String[]{this.texture};
   }

   private String getMapTexture(Map<String, String> map, String string2, String string3) {
      if (map == null) {
         return string3;
      }

      String var7 = (String)map.get(string2);
      return var7 == null ? string3 : var7;
   }

   private static ModelBlock makeModelBlock(String[] arr) {
      StringBuilder var4 = new StringBuilder();
      var4.append("{\"parent\": \"builtin/generated\",\"textures\": {");

      for (int var5 = 0; var5 < arr.length; var5++) {
         String var6 = arr[var5];
         if (var5 > 0) {
            var4.append(", ");
         }

         var4.append("\"layer").append(var5).append("\": \"").append(var6).append("\"");
      }

      var4.append("}}");
      return ModelBlock.deserialize(var4.toString());
   }

   private static IBakedModel bakeModel(TextureMap textureMap, ModelBlock modelBlock, boolean boolean3) {
      ModelRotation var6 = ModelRotation.X0_Y0;
      String var8 = modelBlock.resolveTextureName("particle");
      TextureAtlasSprite var9 = textureMap.getAtlasSprite(new ResourceLocation(var8).toString());
      SimpleBakedModel.Builder var10 = new SimpleBakedModel.Builder(modelBlock).setTexture(var9);

      for (BlockPart var12 : modelBlock.getElements()) {
         for (EnumFacing var14 : var12.mapFaces.keySet()) {
            BlockPartFace var15 = var12.mapFaces.get(var14);
            if (!boolean3) {
               var15 = new BlockPartFace(var15.cullFace, -1, var15.texture, var15.blockFaceUV);
            }

            String var16 = modelBlock.resolveTextureName(var15.texture);
            TextureAtlasSprite var17 = textureMap.getAtlasSprite(new ResourceLocation(var16).toString());
            BakedQuad var18 = makeBakedQuad(var12, var15, var17, var14, var6, false);
            if (var15.cullFace == null) {
               var10.addGeneralQuad(var18);
            } else {
               var10.addFaceQuad(var6.rotateFace(var15.cullFace), var18);
            }
         }
      }

      return var10.makeBakedModel();
   }

   private static BakedQuad makeBakedQuad(
      BlockPart blockPart,
      BlockPartFace blockPartFace,
      TextureAtlasSprite textureAtlasSprite,
      EnumFacing enumFacing,
      ModelRotation modelRotation,
      boolean boolean6
   ) {
      return new FaceBakery()
         .makeBakedQuad(
            blockPart.positionFrom,
            blockPart.positionTo,
            blockPartFace,
            textureAtlasSprite,
            enumFacing,
            modelRotation,
            blockPart.partRotation,
            boolean6,
            blockPart.shade
         );
   }

   @Override
   public String toString() {
      String var10000 = this.basePath;
      String var10001 = this.name;
      int var10002 = this.type;
      String var10003 = Config.arrayToString(this.items);
      String var3 = this.texture;
      String var4 = var10003;
      int var5 = var10002;
      String var6 = var10001;
      return var10000 + "/" + var6 + ", type: " + var5 + ", items: [" + var4 + "], textture: " + var3;
   }

   public float getTextureWidth(TextureManager textureManager) {
      if (this.textureWidth <= 0) {
         if (this.textureLocation != null) {
            int var6 = textureManager.getTexture(this.textureLocation).getGlTextureId();
            int var7 = GlStateManager.getBoundTexture();
            GlStateManager.bindTexture(var6);
            this.textureWidth = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
            GlStateManager.bindTexture(var7);
         }

         if (this.textureWidth <= 0) {
            this.textureWidth = 16;
         }
      }

      return this.textureWidth;
   }

   public float getTextureHeight(TextureManager textureManager) {
      if (this.textureHeight <= 0) {
         if (this.textureLocation != null) {
            int var6 = textureManager.getTexture(this.textureLocation).getGlTextureId();
            int var7 = GlStateManager.getBoundTexture();
            GlStateManager.bindTexture(var6);
            this.textureHeight = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
            GlStateManager.bindTexture(var7);
         }

         if (this.textureHeight <= 0) {
            this.textureHeight = 16;
         }
      }

      return this.textureHeight;
   }

   public IBakedModel getBakedModel(ResourceLocation resourceLocation, boolean boolean2) {
      IBakedModel var6;
      Map var7;
      if (boolean2) {
         var6 = this.bakedModelFull;
         var7 = this.mapBakedModelsFull;
      } else {
         var6 = this.bakedModelTexture;
         var7 = this.mapBakedModelsTexture;
      }

      if (resourceLocation != null && var7 != null) {
         String var8 = resourceLocation.getResourcePath();
         IBakedModel var9 = (IBakedModel)var7.get(var8);
         if (var9 != null) {
            return var9;
         }
      }

      return var6;
   }

   public void loadModels(ModelBakery modelBakery) {
      if (this.model != null) {
         Method212(modelBakery, this.model);
      }

      if (this.type == 1 && this.mapModels != null) {
         for (String var6 : this.mapModels.keySet()) {
            String var7 = this.mapModels.get(var6);
            String var8 = StrUtils.removePrefix(var6, "model.");
            if (var8.startsWith("bow") || var8.startsWith("fishing_rod") || var8.startsWith("shield")) {
               Method212(modelBakery, var7);
            }
         }
      }
   }

   public void updateModelsFull() {
      ModelManager var4 = Config.getModelManager();
      IBakedModel var5 = var4.getMissingModel();
      if (this.model != null) {
         ResourceLocation var6 = Method213(this.model);
         ModelResourceLocation var7 = new ModelResourceLocation(var6, "inventory");
         this.bakedModelFull = var4.getModel(var7);
         if (this.bakedModelFull == var5) {
            Config.Method252("Custom Items: Model not found " + var7.getResourcePath());
            this.bakedModelFull = null;
         }
      }

      if (this.type == 1 && this.mapModels != null) {
         for (String var14 : this.mapModels.keySet()) {
            String var8 = this.mapModels.get(var14);
            String var9 = StrUtils.removePrefix(var14, "model.");
            if (var9.startsWith("bow") || var9.startsWith("fishing_rod") || var9.startsWith("shield")) {
               ResourceLocation var10 = Method213(var8);
               ModelResourceLocation var11 = new ModelResourceLocation(var10, "inventory");
               IBakedModel var12 = var4.getModel(var11);
               if (var12 == var5) {
                  Config.Method252("Custom Items: Model not found " + var11.getResourcePath());
               } else {
                  if (this.mapBakedModelsFull == null) {
                     this.mapBakedModelsFull = new HashMap<>();
                  }

                  this.mapBakedModelsFull.put(var9, var12);
               }
            }
         }
      }
   }

   private static void Method212(ModelBakery modelBakery, String string) {
      ResourceLocation var4 = Method213(string);
      ModelResourceLocation var5 = new ModelResourceLocation(var4, "inventory");
      modelBakery.loadItemModel(var4.toString(), var5, var4);
   }

   private static void checkNull(Object object, String string) throws NullPointerException {
      if (object == null) {
         throw new NullPointerException(string);
      }
   }

   private static ResourceLocation Method213(String string) {
      return new ResourceLocation(string);
   }
}
