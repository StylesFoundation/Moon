package net.optifine.config;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.src.Config;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.ConnectedProperties;
import net.optifine.util.EntityUtils;

public class ConnectedParser {
   private String context = null;
   public static final VillagerProfession[] PROFESSIONS_INVALID = new VillagerProfession[0];
   public static final EnumDyeColor[] DYE_COLORS_INVALID = new EnumDyeColor[0];
   private static final INameGetter<Enum> d = Enum::name;
   private static final INameGetter<EnumDyeColor> e = EnumDyeColor::getName;

   public ConnectedParser(String string) {
      this.context = string;
   }

   public String parseName(String string) {
      String var5 = string;
      int var6 = string.lastIndexOf(47);
      if (var6 >= 0) {
         var5 = string.substring(var6 + 1);
      }

      int var7 = var5.lastIndexOf(46);
      if (var7 >= 0) {
         var5 = var5.substring(0, var7);
      }

      return var5;
   }

   public String parseBasePath(String string) {
      int var4 = string.lastIndexOf(47);
      return var4 < 0 ? "" : string.substring(0, var4);
   }

   public MatchBlock[] parseMatchBlocks(String string) {
      if (string == null) {
         return null;
      }

      ArrayList<MatchBlock> var5 = new ArrayList<MatchBlock>();

      for (String var10 : Config.tokenize(string, " ")) {
         MatchBlock[] var11 = this.parseMatchBlock(var10);
         if (var11 != null) {
            var5.addAll(Arrays.asList(var11));
         }
      }

      return var5.toArray(new MatchBlock[0]);
   }

   public IBlockState parseBlockState(String string, IBlockState iBlockState) {
      MatchBlock[] var6 = this.parseMatchBlock(string);
      if (var6 == null) {
         return iBlockState;
      } else {
         return var6.length != 1 ? iBlockState : Block.getBlockById(var6[0].getBlockId()).getDefaultState();
      }
   }

   public MatchBlock[] parseMatchBlock(String string) {
      if (string == null) {
         return null;
      }

      string = string.trim();
      if (string.length() == 0) {
         return null;
      }

      String[] var5 = Config.tokenize(string, ":");
      String var6;
      byte var7;
      if (var5.length > 1 && this.isFullBlockName(var5)) {
         var6 = var5[0];
         var7 = 1;
      } else {
         var6 = "minecraft";
         var7 = 0;
      }

      String var8 = var5[var7];
      String[] var9 = Arrays.copyOfRange(var5, var7 + 1, var5.length);
      Block[] var10 = this.parseBlockPart(var6, var8);
      if (var10 == null) {
         return null;
      }

      MatchBlock[] var11 = new MatchBlock[var10.length];

      for (int var12 = 0; var12 < var10.length; var12++) {
         Block var13 = var10[var12];
         int var14 = Block.getIdFromBlock(var13);
         int[] var15 = null;
         if (var9.length > 0) {
            var15 = this.parseBlockMetadatas(var13, var9);
            if (var15 == null) {
               return null;
            }
         }

         MatchBlock var16 = new MatchBlock(var14, var15);
         var11[var12] = var16;
      }

      return var11;
   }

   public boolean isFullBlockName(String[] arr) {
      if (arr.length < 2) {
         return false;
      }

      String var5 = arr[1];
      return var5.length() >= 1 && !this.startsWithDigit(var5) && !var5.contains("=");
   }

   public boolean startsWithDigit(String string) {
      if (string == null) {
         return false;
      } else {
         return string.length() < 1 ? false : Character.isDigit(string.charAt(0));
      }
   }

   public Block[] parseBlockPart(String string1, String string2) {
      if (this.startsWithDigit(string2)) {
         int[] var11 = this.parseIntList(string2);
         if (var11 == null) {
            return null;
         }

         Block[] var12 = new Block[var11.length];

         for (int var8 = 0; var8 < var11.length; var8++) {
            int var9 = var11[var8];
            Block var10 = Block.getBlockById(var9);
            if (var10 == null) {
               this.warn("Block not found for id: " + var9);
               return null;
            }

            var12[var8] = var10;
         }

         return var12;
      } else {
         String var6 = string1 + ":" + string2;
         Block var7 = Block.getBlockFromName(var6);
         if (var7 == null) {
            this.warn("Block not found for name: " + var6);
            return null;
         } else {
            return new Block[]{var7};
         }
      }
   }

   public int[] parseBlockMetadatas(Block block, String[] arr) {
      if (arr.length == 0) {
         return null;
      }

      String var6 = arr[0];
      if (this.startsWithDigit(var6)) {
         return this.parseIntList(var6);
      }

      Collection var8 = block.getDefaultState().getPropertyNames();
      HashMap var9 = new HashMap();

      for (String var13 : arr) {
         if (var13.length() > 0) {
            String[] var14 = Config.tokenize(var13, "=");
            if (var14.length != 2) {
               this.warn("Invalid block property: " + var13);
               return null;
            }

            String var15 = var14[0];
            String var16 = var14[1];
            IProperty var17 = ConnectedProperties.getProperty(var15, var8);
            if (var17 == null) {
               this.warn("Property not found: " + var15 + ", block: " + block);
               return null;
            }

            List var18 = (List)var9.get(var15);
            if (var18 == null) {
               var18 = new ArrayList();
               var9.put(var17, var18);
            }

            for (String var23 : Config.tokenize(var16, ",")) {
               Comparable var24 = parsePropertyValue(var17, var23);
               if (var24 == null) {
                  Block var25 = block;
                  String var26 = var15;
                  String var27 = var23;
                  this.warn("Property value not found: " + var27 + ", property: " + var26 + ", block: " + var25);
                  return null;
               }

               var18.add(var24);
            }
         }
      }

      if (var9.isEmpty()) {
         return null;
      }

      ArrayList var29 = new ArrayList();

      for (int var30 = 0; var30 < 16; var30++) {
         try {
            IBlockState var32 = this.getStateFromMeta(block, var30);
            if (this.matchState(var32, var9)) {
               var29.add(var30);
            }
         } catch (IllegalArgumentException var28) {
         }
      }

      if (var29.size() == 16) {
         return null;
      }

      int[] var31 = new int[var29.size()];

      for (int var33 = 0; var33 < var31.length; var33++) {
         var31[var33] = (Integer)var29.get(var33);
      }

      return var31;
   }

   private IBlockState getStateFromMeta(Block block, int integer) {
      try {
         IBlockState var5 = block.getStateFromMeta(integer);
         if (block == Blocks.double_plant && integer > 7) {
            IBlockState var6 = block.getStateFromMeta(integer & 7);
            var5 = var5.withProperty(BlockDoublePlant.VARIANT, var6.getValue(BlockDoublePlant.VARIANT));
         }

         return var5;
      } catch (IllegalArgumentException var7) {
         return block.getDefaultState();
      }
   }

   public static Comparable parsePropertyValue(IProperty iProperty, String string) {
      Class var5 = iProperty.getValueClass();
      Comparable var6 = Method234(string, var5);
      if (var6 == null) {
         Collection var7 = iProperty.getAllowedValues();
         var6 = getPropertyValue(string, var7);
      }

      return var6;
   }

   public static Comparable getPropertyValue(String string, Collection<Comparable> collection) {
      for (Comparable var6 : collection) {
         if (getValueName(var6).equals(string)) {
            return var6;
         }
      }

      return null;
   }

   private static Object getValueName(Comparable comparable) {
      return comparable instanceof IStringSerializable ? ((IStringSerializable)comparable).getName() : comparable.toString();
   }

   public static Comparable Method234(String string, Class class2) {
      return class2 == String.class
         ? string
         : (Comparable)(
            class2 == Boolean.class
               ? Boolean.valueOf(string)
               : class2 == Float.class
                  ? Float.parseFloat(string)
                  : (
                     class2 == Double.class
                        ? Double.parseDouble(string)
                        : (class2 == Integer.class ? Integer.parseInt(string) : class2 == Long.class ? Long.valueOf(string) : null)
                  )
         );
   }

   public boolean matchState(IBlockState iBlockState, Map<IProperty, List<Comparable>> map) {
      for (IProperty var7 : map.keySet()) {
         List var8 = (List)map.get(var7);
         Comparable var9 = iBlockState.getValue(var7);
         if (var9 == null) {
            return false;
         }

         if (!var8.contains(var9)) {
            return false;
         }
      }

      return true;
   }

   public BiomeGenBase[] parseBiomes(String string) {
      if (string == null) {
         return null;
      }

      string = string.trim();
      boolean var5 = false;
      if (string.startsWith("!")) {
         var5 = true;
         string = string.substring(1);
      }

      String[] var6 = Config.tokenize(string, " ");
      ArrayList<BiomeGenBase> var7 = new ArrayList<BiomeGenBase>();

      for (String var11 : var6) {
         BiomeGenBase var12 = this.findBiome(var11);
         if (var12 == null) {
            this.warn("Biome not found: " + var11);
         } else {
            var7.add(var12);
         }
      }

      if (var5) {
         ArrayList<BiomeGenBase> var14 = new ArrayList<BiomeGenBase>(Arrays.asList(BiomeGenBase.getBiomeGenArray()));
         var14.removeAll(var7);
         var7 = var14;
      }

      return var7.toArray(new BiomeGenBase[0]);
   }

   public BiomeGenBase findBiome(String string) {
      string = string.toLowerCase();
      if (string.equals("nether")) {
         return BiomeGenBase.hell;
      }

      for (BiomeGenBase var9 : BiomeGenBase.getBiomeGenArray()) {
         if (var9 != null) {
            String var10 = var9.biomeName.replace(" ", "").toLowerCase();
            if (var10.equals(string)) {
               return var9;
            }
         }
      }

      return null;
   }

   public int parseInt(String string, int integer) {
      if (string == null) {
         return integer;
      } else {
         string = string.trim();
         int var6 = Config.parseInt(string, -1);
         if (var6 < 0) {
            this.warn("Invalid number: " + string);
            return integer;
         } else {
            return var6;
         }
      }
   }

   public int[] parseIntList(String string) {
      if (string == null) {
         return null;
      }

      ArrayList var5 = new ArrayList();

      for (String var10 : Config.tokenize(string, " ,")) {
         if (var10.contains("-")) {
            String[] var17 = Config.tokenize(var10, "-");
            if (var17.length != 2) {
               this.warn("Invalid interval: " + var10 + ", when parsing: " + string);
            } else {
               int var12 = Config.parseInt(var17[0], -1);
               int var13 = Config.parseInt(var17[1], -1);
               if (var12 >= 0 && var13 >= 0 && var12 <= var13) {
                  for (int var14 = var12; var14 <= var13; var14++) {
                     var5.add(var14);
                  }
               } else {
                  this.warn("Invalid interval: " + var10 + ", when parsing: " + string);
               }
            }
         } else {
            int var11 = Config.parseInt(var10, -1);
            if (var11 < 0) {
               this.warn("Invalid number: " + var10 + ", when parsing: " + string);
            } else {
               var5.add(var11);
            }
         }
      }

      int[] var15 = new int[var5.size()];

      for (int var16 = 0; var16 < var15.length; var16++) {
         var15[var16] = (Integer)var5.get(var16);
      }

      return var15;
   }

   public boolean[] parseFaces(String string, boolean[] arr) {
      if (string == null) {
         return arr;
      }

      EnumSet var6 = EnumSet.allOf(EnumFacing.class);

      for (String var11 : Config.tokenize(string, " ,")) {
         if (var11.equals("sides")) {
            var6.add(EnumFacing.NORTH);
            var6.add(EnumFacing.SOUTH);
            var6.add(EnumFacing.WEST);
            var6.add(EnumFacing.EAST);
         } else if (var11.equals("all")) {
            var6.addAll(Arrays.asList(EnumFacing.VALUES));
         } else {
            EnumFacing var12 = this.parseFace(var11);
            if (var12 != null) {
               var6.add(var12);
            }
         }
      }

      boolean[] var13 = new boolean[EnumFacing.VALUES.length];

      for (int var14 = 0; var14 < var13.length; var14++) {
         var13[var14] = var6.contains(EnumFacing.VALUES[var14]);
      }

      return var13;
   }

   public EnumFacing parseFace(String string) {
      string = string.toLowerCase();
      if (string.equals("bottom") || string.equals("down")) {
         return EnumFacing.DOWN;
      }

      if (string.equals("top") || string.equals("up")) {
         return EnumFacing.UP;
      }

      if (string.equals("north")) {
         return EnumFacing.NORTH;
      }

      if (string.equals("south")) {
         return EnumFacing.SOUTH;
      }

      if (string.equals("east")) {
         return EnumFacing.EAST;
      }

      if (string.equals("west")) {
         return EnumFacing.WEST;
      }

      Config.Method252("Unknown face: " + string);
      return null;
   }

   public void dbg(String string) {
      Config.Method251(this.context + ": " + string);
   }

   public void warn(String string) {
      Config.Method252(this.context + ": " + string);
   }

   public RangeListInt parseRangeListInt(String string) {
      if (string == null) {
         return null;
      }

      RangeListInt var5 = new RangeListInt();

      for (String var10 : Config.tokenize(string, " ,")) {
         RangeInt var11 = this.parseRangeInt(var10);
         if (var11 == null) {
            return null;
         }

         var5.addRange(var11);
      }

      return var5;
   }

   private RangeInt parseRangeInt(String string) {
      if (string == null) {
         return null;
      }

      if (string.indexOf(45) >= 0) {
         String[] var8 = Config.tokenize(string, "-");
         if (var8.length != 2) {
            this.warn("Invalid range: " + string);
            return null;
         }

         int var6 = Config.parseInt(var8[0], -1);
         int var7 = Config.parseInt(var8[1], -1);
         if (var6 >= 0 && var7 >= 0) {
            return new RangeInt(var6, var7);
         }

         this.warn("Invalid range: " + string);
         return null;
      } else {
         int var5 = Config.parseInt(string, -1);
         if (var5 < 0) {
            this.warn("Invalid integer: " + string);
            return null;
         } else {
            return new RangeInt(var5, var5);
         }
      }
   }

   public boolean parseBoolean(String string, boolean boolean2) {
      if (string == null) {
         return boolean2;
      }

      String var6 = string.toLowerCase().trim();
      if (var6.equals("true")) {
         return true;
      }

      if (var6.equals("false")) {
         return false;
      }

      this.warn("Invalid boolean: " + string);
      return boolean2;
   }

   public Boolean parseBooleanObject(String string) {
      if (string == null) {
         return null;
      }

      String var5 = string.toLowerCase().trim();
      if (var5.equals("true")) {
         return Boolean.TRUE;
      }

      if (var5.equals("false")) {
         return Boolean.FALSE;
      }

      this.warn("Invalid boolean: " + string);
      return null;
   }

   public static int parseColor(String string, int integer) {
      if (string == null) {
         return integer;
      }

      string = string.trim();

      try {
         return Integer.parseInt(string, 16) & 16777215;
      } catch (NumberFormatException var2) {
         return integer;
      }
   }

   public static int parseColor4(String string, int integer) {
      if (string == null) {
         return integer;
      }

      string = string.trim();

      try {
         return (int)Long.parseLong(string, 16);
      } catch (NumberFormatException var2) {
         return integer;
      }
   }

   public EnumWorldBlockLayer parseBlockRenderLayer(String string, EnumWorldBlockLayer enumWorldBlockLayer) {
      if (string != null) {
         string = string.toLowerCase().trim();

         for (EnumWorldBlockLayer var10 : EnumWorldBlockLayer.values()) {
            if (string.equals(var10.name().toLowerCase())) {
               return var10;
            }
         }
      }

      return enumWorldBlockLayer;
   }

   public <T> T parseObject(String string1, T[] arr, INameGetter iNameGetter, String string4) {
      if (string1 != null) {
         String var8 = string1.toLowerCase().trim();

         for (Object var12 : arr) {
            String var13 = iNameGetter.getName(var12);
            if (var13 != null && var13.toLowerCase().equals(var8)) {
               return (T)var12;
            }
         }

         this.warn("Invalid " + string4 + ": " + string1);
      }

      return null;
   }

   public <T> T[] parseObjects(String string1, T[] arr2, INameGetter iNameGetter, String string4, T[] arr5) {
      if (string1 == null) {
         return null;
      }

      String[] var9 = Config.tokenize(string1.toLowerCase().trim(), " ");
      Object[] var10 = (Object[])Array.newInstance(arr2.getClass().getComponentType(), var9.length);

      for (int var11 = 0; var11 < var9.length; var11++) {
         String var12 = var9[var11];
         Object var13 = this.parseObject(var12, arr2, iNameGetter, string4);
         if (var13 == null) {
            return (T[])arr5;
         }

         var10[var11] = var13;
      }

      return (T[])var10;
   }

   public Enum parseEnum(String string1, Enum[] arr, String string3) {
      return this.parseObject(string1, arr, d, string3);
   }

   public Enum[] parseEnums(String string1, Enum[] arr2, String string3, Enum[] arr4) {
      return this.parseObjects(string1, arr2, d, string3, arr4);
   }

   public EnumDyeColor[] parseDyeColors(String string1, String string2, EnumDyeColor[] arr) {
      return this.parseObjects(string1, EnumDyeColor.values(), e, string2, arr);
   }

   public Weather[] parseWeather(String string1, String string2, Weather[] arr) {
      return this.parseObjects(string1, Weather.values(), d, string2, arr);
   }

   public NbtTagValue parseNbtTagValue(String string1, String string2) {
      return string1 != null && string2 != null ? new NbtTagValue(string1, string2) : null;
   }

   public VillagerProfession[] parseProfessions(String string) {
      if (string == null) {
         return null;
      }

      ArrayList<VillagerProfession> var5 = new ArrayList<VillagerProfession>();

      for (String var10 : Config.tokenize(string, " ")) {
         VillagerProfession var11 = this.parseProfession(var10);
         if (var11 == null) {
            this.warn("Invalid profession: " + var10);
            return PROFESSIONS_INVALID;
         }

         var5.add(var11);
      }

      return var5.isEmpty() ? null : var5.toArray(new VillagerProfession[0]);
   }

   private VillagerProfession parseProfession(String string) {
      string = string.toLowerCase();
      String[] var5 = Config.tokenize(string, ":");
      if (var5.length > 2) {
         return null;
      }

      String var6 = var5[0];
      String var7 = null;
      if (var5.length > 1) {
         var7 = var5[1];
      }

      int var8 = parseProfessionId(var6);
      if (var8 < 0) {
         return null;
      }

      int[] var9 = null;
      if (var7 != null) {
         var9 = parseCareerIds(var8, var7);
         if (var9 == null) {
            return null;
         }
      }

      return new VillagerProfession(var8, var9);
   }

   private static int parseProfessionId(String string) {
      int var4 = Config.parseInt(string, -1);
      return var4 >= 0
         ? var4
         : (
            string.equals("farmer")
               ? 0
               : (
                  string.equals("librarian")
                     ? 1
                     : (string.equals("priest") ? 2 : (string.equals("blacksmith") ? 3 : (string.equals("butcher") ? 4 : (string.equals("nitwit") ? 5 : -1))))
               )
         );
   }

   private static int[] parseCareerIds(int integer, String string) {
      HashSet<Integer> var5 = new HashSet<Integer>();
      String[] var6 = Config.tokenize(string, ",");

      for (String var10 : var6) {
         int var11 = parseCareerId(integer, var10);
         if (var11 < 0) {
            return null;
         }

         var5.add(var11);
      }

      Integer[] var12 = var5.toArray(new Integer[0]);
      int[] var13 = new int[var12.length];

      for (int var14 = 0; var14 < var13.length; var14++) {
         var13[var14] = var12[var14];
      }

      return var13;
   }

   private static int parseCareerId(int integer, String string) {
      int var5 = Config.parseInt(string, -1);
      if (var5 >= 0) {
         return var5;
      }

      if (integer == 0) {
         if (string.equals("farmer")) {
            return 1;
         }

         if (string.equals("fisherman")) {
            return 2;
         }

         if (string.equals("shepherd")) {
            return 3;
         }

         if (string.equals("fletcher")) {
            return 4;
         }
      }

      if (integer == 1) {
         if (string.equals("librarian")) {
            return 1;
         }

         if (string.equals("cartographer")) {
            return 2;
         }
      }

      if (integer == 2 && string.equals("cleric")) {
         return 1;
      }

      if (integer == 3) {
         if (string.equals("armor")) {
            return 1;
         }

         if (string.equals("weapon")) {
            return 2;
         }

         if (string.equals("tool")) {
            return 3;
         }
      }

      if (integer == 4) {
         if (string.equals("butcher")) {
            return 1;
         }

         if (string.equals("leather")) {
            return 2;
         }
      }

      return integer == 5 && string.equals("nitwit") ? 1 : -1;
   }

   public int[] parseItems(String string) {
      string = string.trim();
      TreeSet<Integer> var5 = new TreeSet<Integer>();
      String[] var6 = Config.tokenize(string, " ");

      for (String var10 : var6) {
         ResourceLocation var11 = new ResourceLocation(var10);
         Item var12 = Item.itemRegistry.getObject(var11);
         if (var12 == null) {
            this.warn("Item not found: " + var10);
         } else {
            int var13 = Item.getIdFromItem(var12);
            if (var13 < 0) {
               this.warn("Item has no ID: " + var12 + ", name: " + var10);
            } else {
               var5.add(var13);
            }
         }
      }

      return Config.toPrimitive(var5.toArray(new Integer[0]));
   }

   public int[] parseEntities(String string) {
      string = string.trim();
      TreeSet<Integer> var5 = new TreeSet<Integer>();

      for (String var10 : Config.tokenize(string, " ")) {
         int var11 = EntityUtils.getEntityIdByName(var10);
         if (var11 < 0) {
            this.warn("Entity not found: " + var10);
         } else {
            var5.add(var11);
         }
      }

      return Config.toPrimitive(var5.toArray(new Integer[0]));
   }
}
