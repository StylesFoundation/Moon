package net.minecraft.world.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import net.minecraft.world.biome.BiomeGenBase;

public class ChunkProviderSettings {
   public final float coordinateScale;
   public final float heightScale;
   public final float upperLimitScale;
   public final float lowerLimitScale;
   public final float depthNoiseScaleX;
   public final float depthNoiseScaleZ;
   public final float depthNoiseScaleExponent;
   public final float mainNoiseScaleX;
   public final float mainNoiseScaleY;
   public final float mainNoiseScaleZ;
   public final float baseSize;
   public final float stretchY;
   public final float biomeDepthWeight;
   public final float biomeDepthOffSet;
   public final float biomeScaleWeight;
   public final float biomeScaleOffset;
   public final int seaLevel;
   public final boolean useCaves;
   public final boolean useDungeons;
   public final int dungeonChance;
   public final boolean useStrongholds;
   public final boolean useVillages;
   public final boolean useMineShafts;
   public final boolean useTemples;
   public final boolean useMonuments;
   public final boolean useRavines;
   public final boolean useWaterLakes;
   public final int waterLakeChance;
   public final boolean useLavaLakes;
   public final int lavaLakeChance;
   public final boolean useLavaOceans;
   public final int fixedBiome;
   public final int biomeSize;
   public final int riverSize;
   public final int dirtSize;
   public final int dirtCount;
   public final int dirtMinHeight;
   public final int dirtMaxHeight;
   public final int gravelSize;
   public final int gravelCount;
   public final int gravelMinHeight;
   public final int gravelMaxHeight;
   public final int graniteSize;
   public final int graniteCount;
   public final int graniteMinHeight;
   public final int graniteMaxHeight;
   public final int dioriteSize;
   public final int dioriteCount;
   public final int dioriteMinHeight;
   public final int dioriteMaxHeight;
   public final int andesiteSize;
   public final int andesiteCount;
   public final int andesiteMinHeight;
   public final int andesiteMaxHeight;
   public final int coalSize;
   public final int coalCount;
   public final int coalMinHeight;
   public final int coalMaxHeight;
   public final int ironSize;
   public final int ironCount;
   public final int ironMinHeight;
   public final int ironMaxHeight;
   public final int goldSize;
   public final int goldCount;
   public final int goldMinHeight;
   public final int goldMaxHeight;
   public final int redstoneSize;
   public final int redstoneCount;
   public final int redstoneMinHeight;
   public final int redstoneMaxHeight;
   public final int diamondSize;
   public final int diamondCount;
   public final int diamondMinHeight;
   public final int diamondMaxHeight;
   public final int lapisSize;
   public final int lapisCount;
   public final int lapisCenterHeight;
   public final int lapisSpread;

   private ChunkProviderSettings(ChunkProviderSettings.Factory factory) {
      this.coordinateScale = factory.coordinateScale;
      this.heightScale = factory.heightScale;
      this.upperLimitScale = factory.upperLimitScale;
      this.lowerLimitScale = factory.lowerLimitScale;
      this.depthNoiseScaleX = factory.depthNoiseScaleX;
      this.depthNoiseScaleZ = factory.depthNoiseScaleZ;
      this.depthNoiseScaleExponent = factory.depthNoiseScaleExponent;
      this.mainNoiseScaleX = factory.mainNoiseScaleX;
      this.mainNoiseScaleY = factory.mainNoiseScaleY;
      this.mainNoiseScaleZ = factory.mainNoiseScaleZ;
      this.baseSize = factory.baseSize;
      this.stretchY = factory.stretchY;
      this.biomeDepthWeight = factory.biomeDepthWeight;
      this.biomeDepthOffSet = factory.biomeDepthOffset;
      this.biomeScaleWeight = factory.biomeScaleWeight;
      this.biomeScaleOffset = factory.biomeScaleOffset;
      this.seaLevel = factory.seaLevel;
      this.useCaves = factory.useCaves;
      this.useDungeons = factory.useDungeons;
      this.dungeonChance = factory.dungeonChance;
      this.useStrongholds = factory.useStrongholds;
      this.useVillages = factory.useVillages;
      this.useMineShafts = factory.useMineShafts;
      this.useTemples = factory.useTemples;
      this.useMonuments = factory.useMonuments;
      this.useRavines = factory.useRavines;
      this.useWaterLakes = factory.useWaterLakes;
      this.waterLakeChance = factory.waterLakeChance;
      this.useLavaLakes = factory.useLavaLakes;
      this.lavaLakeChance = factory.lavaLakeChance;
      this.useLavaOceans = factory.useLavaOceans;
      this.fixedBiome = factory.fixedBiome;
      this.biomeSize = factory.biomeSize;
      this.riverSize = factory.riverSize;
      this.dirtSize = factory.dirtSize;
      this.dirtCount = factory.dirtCount;
      this.dirtMinHeight = factory.dirtMinHeight;
      this.dirtMaxHeight = factory.dirtMaxHeight;
      this.gravelSize = factory.gravelSize;
      this.gravelCount = factory.gravelCount;
      this.gravelMinHeight = factory.gravelMinHeight;
      this.gravelMaxHeight = factory.gravelMaxHeight;
      this.graniteSize = factory.graniteSize;
      this.graniteCount = factory.graniteCount;
      this.graniteMinHeight = factory.graniteMinHeight;
      this.graniteMaxHeight = factory.graniteMaxHeight;
      this.dioriteSize = factory.dioriteSize;
      this.dioriteCount = factory.dioriteCount;
      this.dioriteMinHeight = factory.dioriteMinHeight;
      this.dioriteMaxHeight = factory.dioriteMaxHeight;
      this.andesiteSize = factory.andesiteSize;
      this.andesiteCount = factory.andesiteCount;
      this.andesiteMinHeight = factory.andesiteMinHeight;
      this.andesiteMaxHeight = factory.andesiteMaxHeight;
      this.coalSize = factory.coalSize;
      this.coalCount = factory.coalCount;
      this.coalMinHeight = factory.coalMinHeight;
      this.coalMaxHeight = factory.coalMaxHeight;
      this.ironSize = factory.ironSize;
      this.ironCount = factory.ironCount;
      this.ironMinHeight = factory.ironMinHeight;
      this.ironMaxHeight = factory.ironMaxHeight;
      this.goldSize = factory.goldSize;
      this.goldCount = factory.goldCount;
      this.goldMinHeight = factory.goldMinHeight;
      this.goldMaxHeight = factory.goldMaxHeight;
      this.redstoneSize = factory.redstoneSize;
      this.redstoneCount = factory.redstoneCount;
      this.redstoneMinHeight = factory.redstoneMinHeight;
      this.redstoneMaxHeight = factory.redstoneMaxHeight;
      this.diamondSize = factory.diamondSize;
      this.diamondCount = factory.diamondCount;
      this.diamondMinHeight = factory.diamondMinHeight;
      this.diamondMaxHeight = factory.diamondMaxHeight;
      this.lapisSize = factory.lapisSize;
      this.lapisCount = factory.lapisCount;
      this.lapisCenterHeight = factory.lapisCenterHeight;
      this.lapisSpread = factory.lapisSpread;
   }

   public static class Factory {
      static final Gson JSON_ADAPTER = new GsonBuilder()
         .registerTypeAdapter(ChunkProviderSettings.Factory.class, new ChunkProviderSettings.Serializer())
         .create();
      public float coordinateScale = 684.412F;
      public float heightScale = 684.412F;
      public float upperLimitScale = 512.0F;
      public float lowerLimitScale = 512.0F;
      public float depthNoiseScaleX = 200.0F;
      public float depthNoiseScaleZ = 200.0F;
      public float depthNoiseScaleExponent = 0.5F;
      public float mainNoiseScaleX = 80.0F;
      public float mainNoiseScaleY = 160.0F;
      public float mainNoiseScaleZ = 80.0F;
      public float baseSize = 8.5F;
      public float stretchY = 12.0F;
      public float biomeDepthWeight = 1.0F;
      public float biomeDepthOffset = 0.0F;
      public float biomeScaleWeight = 1.0F;
      public float biomeScaleOffset = 0.0F;
      public int seaLevel = 63;
      public boolean useCaves = true;
      public boolean useDungeons = true;
      public int dungeonChance = 8;
      public boolean useStrongholds = true;
      public boolean useVillages = true;
      public boolean useMineShafts = true;
      public boolean useTemples = true;
      public boolean useMonuments = true;
      public boolean useRavines = true;
      public boolean useWaterLakes = true;
      public int waterLakeChance = 4;
      public boolean useLavaLakes = true;
      public int lavaLakeChance = 80;
      public boolean useLavaOceans = false;
      public int fixedBiome = -1;
      public int biomeSize = 4;
      public int riverSize = 4;
      public int dirtSize = 33;
      public int dirtCount = 10;
      public int dirtMinHeight = 0;
      public int dirtMaxHeight = 256;
      public int gravelSize = 33;
      public int gravelCount = 8;
      public int gravelMinHeight = 0;
      public int gravelMaxHeight = 256;
      public int graniteSize = 33;
      public int graniteCount = 10;
      public int graniteMinHeight = 0;
      public int graniteMaxHeight = 80;
      public int dioriteSize = 33;
      public int dioriteCount = 10;
      public int dioriteMinHeight = 0;
      public int dioriteMaxHeight = 80;
      public int andesiteSize = 33;
      public int andesiteCount = 10;
      public int andesiteMinHeight = 0;
      public int andesiteMaxHeight = 80;
      public int coalSize = 17;
      public int coalCount = 20;
      public int coalMinHeight = 0;
      public int coalMaxHeight = 128;
      public int ironSize = 9;
      public int ironCount = 20;
      public int ironMinHeight = 0;
      public int ironMaxHeight = 64;
      public int goldSize = 9;
      public int goldCount = 2;
      public int goldMinHeight = 0;
      public int goldMaxHeight = 32;
      public int redstoneSize = 8;
      public int redstoneCount = 8;
      public int redstoneMinHeight = 0;
      public int redstoneMaxHeight = 16;
      public int diamondSize = 8;
      public int diamondCount = 1;
      public int diamondMinHeight = 0;
      public int diamondMaxHeight = 16;
      public int lapisSize = 7;
      public int lapisCount = 1;
      public int lapisCenterHeight = 16;
      public int lapisSpread = 16;

      public static ChunkProviderSettings.Factory jsonToFactory(String string) {
         if (string.length() == 0) {
            return new ChunkProviderSettings.Factory();
         }

         try {
            return (ChunkProviderSettings.Factory)JSON_ADAPTER.fromJson(string, ChunkProviderSettings.Factory.class);
         } catch (Exception var1) {
            return new ChunkProviderSettings.Factory();
         }
      }

      @Override
      public String toString() {
         return JSON_ADAPTER.toJson(this);
      }

      public Factory() {
         this.func_177863_a();
      }

      public void func_177863_a() {
         this.coordinateScale = 684.412F;
         this.heightScale = 684.412F;
         this.upperLimitScale = 512.0F;
         this.lowerLimitScale = 512.0F;
         this.depthNoiseScaleX = 200.0F;
         this.depthNoiseScaleZ = 200.0F;
         this.depthNoiseScaleExponent = 0.5F;
         this.mainNoiseScaleX = 80.0F;
         this.mainNoiseScaleY = 160.0F;
         this.mainNoiseScaleZ = 80.0F;
         this.baseSize = 8.5F;
         this.stretchY = 12.0F;
         this.biomeDepthWeight = 1.0F;
         this.biomeDepthOffset = 0.0F;
         this.biomeScaleWeight = 1.0F;
         this.biomeScaleOffset = 0.0F;
         this.seaLevel = 63;
         this.useCaves = true;
         this.useDungeons = true;
         this.dungeonChance = 8;
         this.useStrongholds = true;
         this.useVillages = true;
         this.useMineShafts = true;
         this.useTemples = true;
         this.useMonuments = true;
         this.useRavines = true;
         this.useWaterLakes = true;
         this.waterLakeChance = 4;
         this.useLavaLakes = true;
         this.lavaLakeChance = 80;
         this.useLavaOceans = false;
         this.fixedBiome = -1;
         this.biomeSize = 4;
         this.riverSize = 4;
         this.dirtSize = 33;
         this.dirtCount = 10;
         this.dirtMinHeight = 0;
         this.dirtMaxHeight = 256;
         this.gravelSize = 33;
         this.gravelCount = 8;
         this.gravelMinHeight = 0;
         this.gravelMaxHeight = 256;
         this.graniteSize = 33;
         this.graniteCount = 10;
         this.graniteMinHeight = 0;
         this.graniteMaxHeight = 80;
         this.dioriteSize = 33;
         this.dioriteCount = 10;
         this.dioriteMinHeight = 0;
         this.dioriteMaxHeight = 80;
         this.andesiteSize = 33;
         this.andesiteCount = 10;
         this.andesiteMinHeight = 0;
         this.andesiteMaxHeight = 80;
         this.coalSize = 17;
         this.coalCount = 20;
         this.coalMinHeight = 0;
         this.coalMaxHeight = 128;
         this.ironSize = 9;
         this.ironCount = 20;
         this.ironMinHeight = 0;
         this.ironMaxHeight = 64;
         this.goldSize = 9;
         this.goldCount = 2;
         this.goldMinHeight = 0;
         this.goldMaxHeight = 32;
         this.redstoneSize = 8;
         this.redstoneCount = 8;
         this.redstoneMinHeight = 0;
         this.redstoneMaxHeight = 16;
         this.diamondSize = 8;
         this.diamondCount = 1;
         this.diamondMinHeight = 0;
         this.diamondMaxHeight = 16;
         this.lapisSize = 7;
         this.lapisCount = 1;
         this.lapisCenterHeight = 16;
         this.lapisSpread = 16;
      }

      @Override
      public boolean equals(Object object) {
         if (this == object) {
            return true;
         } else if (object != null && this.getClass() == object.getClass()) {
            ChunkProviderSettings.Factory var5 = (ChunkProviderSettings.Factory)object;
            return this.andesiteCount == var5.andesiteCount
               && this.andesiteMaxHeight == var5.andesiteMaxHeight
               && this.andesiteMinHeight == var5.andesiteMinHeight
               && this.andesiteSize == var5.andesiteSize
               && Float.compare(var5.baseSize, this.baseSize) == 0
               && Float.compare(var5.biomeDepthOffset, this.biomeDepthOffset) == 0
               && Float.compare(var5.biomeDepthWeight, this.biomeDepthWeight) == 0
               && Float.compare(var5.biomeScaleOffset, this.biomeScaleOffset) == 0
               && Float.compare(var5.biomeScaleWeight, this.biomeScaleWeight) == 0
               && this.biomeSize == var5.biomeSize
               && this.coalCount == var5.coalCount
               && this.coalMaxHeight == var5.coalMaxHeight
               && this.coalMinHeight == var5.coalMinHeight
               && this.coalSize == var5.coalSize
               && Float.compare(var5.coordinateScale, this.coordinateScale) == 0
               && Float.compare(var5.depthNoiseScaleExponent, this.depthNoiseScaleExponent) == 0
               && Float.compare(var5.depthNoiseScaleX, this.depthNoiseScaleX) == 0
               && Float.compare(var5.depthNoiseScaleZ, this.depthNoiseScaleZ) == 0
               && this.diamondCount == var5.diamondCount
               && this.diamondMaxHeight == var5.diamondMaxHeight
               && this.diamondMinHeight == var5.diamondMinHeight
               && this.diamondSize == var5.diamondSize
               && this.dioriteCount == var5.dioriteCount
               && this.dioriteMaxHeight == var5.dioriteMaxHeight
               && this.dioriteMinHeight == var5.dioriteMinHeight
               && this.dioriteSize == var5.dioriteSize
               && this.dirtCount == var5.dirtCount
               && this.dirtMaxHeight == var5.dirtMaxHeight
               && this.dirtMinHeight == var5.dirtMinHeight
               && this.dirtSize == var5.dirtSize
               && this.dungeonChance == var5.dungeonChance
               && this.fixedBiome == var5.fixedBiome
               && this.goldCount == var5.goldCount
               && this.goldMaxHeight == var5.goldMaxHeight
               && this.goldMinHeight == var5.goldMinHeight
               && this.goldSize == var5.goldSize
               && this.graniteCount == var5.graniteCount
               && this.graniteMaxHeight == var5.graniteMaxHeight
               && this.graniteMinHeight == var5.graniteMinHeight
               && this.graniteSize == var5.graniteSize
               && this.gravelCount == var5.gravelCount
               && this.gravelMaxHeight == var5.gravelMaxHeight
               && this.gravelMinHeight == var5.gravelMinHeight
               && this.gravelSize == var5.gravelSize
               && Float.compare(var5.heightScale, this.heightScale) == 0
               && this.ironCount == var5.ironCount
               && this.ironMaxHeight == var5.ironMaxHeight
               && this.ironMinHeight == var5.ironMinHeight
               && this.ironSize == var5.ironSize
               && this.lapisCenterHeight == var5.lapisCenterHeight
               && this.lapisCount == var5.lapisCount
               && this.lapisSize == var5.lapisSize
               && this.lapisSpread == var5.lapisSpread
               && this.lavaLakeChance == var5.lavaLakeChance
               && Float.compare(var5.lowerLimitScale, this.lowerLimitScale) == 0
               && Float.compare(var5.mainNoiseScaleX, this.mainNoiseScaleX) == 0
               && Float.compare(var5.mainNoiseScaleY, this.mainNoiseScaleY) == 0
               && Float.compare(var5.mainNoiseScaleZ, this.mainNoiseScaleZ) == 0
               && this.redstoneCount == var5.redstoneCount
               && this.redstoneMaxHeight == var5.redstoneMaxHeight
               && this.redstoneMinHeight == var5.redstoneMinHeight
               && this.redstoneSize == var5.redstoneSize
               && this.riverSize == var5.riverSize
               && this.seaLevel == var5.seaLevel
               && Float.compare(var5.stretchY, this.stretchY) == 0
               && Float.compare(var5.upperLimitScale, this.upperLimitScale) == 0
               && this.useCaves == var5.useCaves
               && this.useDungeons == var5.useDungeons
               && this.useLavaLakes == var5.useLavaLakes
               && this.useLavaOceans == var5.useLavaOceans
               && this.useMineShafts == var5.useMineShafts
               && this.useRavines == var5.useRavines
               && this.useStrongholds == var5.useStrongholds
               && this.useTemples == var5.useTemples
               && this.useMonuments == var5.useMonuments
               && this.useVillages == var5.useVillages
               && this.useWaterLakes == var5.useWaterLakes
               && this.waterLakeChance == var5.waterLakeChance;
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int var4 = this.coordinateScale != 0.0F ? Float.floatToIntBits(this.coordinateScale) : 0;
         var4 = 31 * var4 + (this.heightScale != 0.0F ? Float.floatToIntBits(this.heightScale) : 0);
         var4 = 31 * var4 + (this.upperLimitScale != 0.0F ? Float.floatToIntBits(this.upperLimitScale) : 0);
         var4 = 31 * var4 + (this.lowerLimitScale != 0.0F ? Float.floatToIntBits(this.lowerLimitScale) : 0);
         var4 = 31 * var4 + (this.depthNoiseScaleX != 0.0F ? Float.floatToIntBits(this.depthNoiseScaleX) : 0);
         var4 = 31 * var4 + (this.depthNoiseScaleZ != 0.0F ? Float.floatToIntBits(this.depthNoiseScaleZ) : 0);
         var4 = 31 * var4 + (this.depthNoiseScaleExponent != 0.0F ? Float.floatToIntBits(this.depthNoiseScaleExponent) : 0);
         var4 = 31 * var4 + (this.mainNoiseScaleX != 0.0F ? Float.floatToIntBits(this.mainNoiseScaleX) : 0);
         var4 = 31 * var4 + (this.mainNoiseScaleY != 0.0F ? Float.floatToIntBits(this.mainNoiseScaleY) : 0);
         var4 = 31 * var4 + (this.mainNoiseScaleZ != 0.0F ? Float.floatToIntBits(this.mainNoiseScaleZ) : 0);
         var4 = 31 * var4 + (this.baseSize != 0.0F ? Float.floatToIntBits(this.baseSize) : 0);
         var4 = 31 * var4 + (this.stretchY != 0.0F ? Float.floatToIntBits(this.stretchY) : 0);
         var4 = 31 * var4 + (this.biomeDepthWeight != 0.0F ? Float.floatToIntBits(this.biomeDepthWeight) : 0);
         var4 = 31 * var4 + (this.biomeDepthOffset != 0.0F ? Float.floatToIntBits(this.biomeDepthOffset) : 0);
         var4 = 31 * var4 + (this.biomeScaleWeight != 0.0F ? Float.floatToIntBits(this.biomeScaleWeight) : 0);
         var4 = 31 * var4 + (this.biomeScaleOffset != 0.0F ? Float.floatToIntBits(this.biomeScaleOffset) : 0);
         var4 = 31 * var4 + this.seaLevel;
         var4 = 31 * var4 + (this.useCaves ? 1 : 0);
         var4 = 31 * var4 + (this.useDungeons ? 1 : 0);
         var4 = 31 * var4 + this.dungeonChance;
         var4 = 31 * var4 + (this.useStrongholds ? 1 : 0);
         var4 = 31 * var4 + (this.useVillages ? 1 : 0);
         var4 = 31 * var4 + (this.useMineShafts ? 1 : 0);
         var4 = 31 * var4 + (this.useTemples ? 1 : 0);
         var4 = 31 * var4 + (this.useMonuments ? 1 : 0);
         var4 = 31 * var4 + (this.useRavines ? 1 : 0);
         var4 = 31 * var4 + (this.useWaterLakes ? 1 : 0);
         var4 = 31 * var4 + this.waterLakeChance;
         var4 = 31 * var4 + (this.useLavaLakes ? 1 : 0);
         var4 = 31 * var4 + this.lavaLakeChance;
         var4 = 31 * var4 + (this.useLavaOceans ? 1 : 0);
         var4 = 31 * var4 + this.fixedBiome;
         var4 = 31 * var4 + this.biomeSize;
         var4 = 31 * var4 + this.riverSize;
         var4 = 31 * var4 + this.dirtSize;
         var4 = 31 * var4 + this.dirtCount;
         var4 = 31 * var4 + this.dirtMinHeight;
         var4 = 31 * var4 + this.dirtMaxHeight;
         var4 = 31 * var4 + this.gravelSize;
         var4 = 31 * var4 + this.gravelCount;
         var4 = 31 * var4 + this.gravelMinHeight;
         var4 = 31 * var4 + this.gravelMaxHeight;
         var4 = 31 * var4 + this.graniteSize;
         var4 = 31 * var4 + this.graniteCount;
         var4 = 31 * var4 + this.graniteMinHeight;
         var4 = 31 * var4 + this.graniteMaxHeight;
         var4 = 31 * var4 + this.dioriteSize;
         var4 = 31 * var4 + this.dioriteCount;
         var4 = 31 * var4 + this.dioriteMinHeight;
         var4 = 31 * var4 + this.dioriteMaxHeight;
         var4 = 31 * var4 + this.andesiteSize;
         var4 = 31 * var4 + this.andesiteCount;
         var4 = 31 * var4 + this.andesiteMinHeight;
         var4 = 31 * var4 + this.andesiteMaxHeight;
         var4 = 31 * var4 + this.coalSize;
         var4 = 31 * var4 + this.coalCount;
         var4 = 31 * var4 + this.coalMinHeight;
         var4 = 31 * var4 + this.coalMaxHeight;
         var4 = 31 * var4 + this.ironSize;
         var4 = 31 * var4 + this.ironCount;
         var4 = 31 * var4 + this.ironMinHeight;
         var4 = 31 * var4 + this.ironMaxHeight;
         var4 = 31 * var4 + this.goldSize;
         var4 = 31 * var4 + this.goldCount;
         var4 = 31 * var4 + this.goldMinHeight;
         var4 = 31 * var4 + this.goldMaxHeight;
         var4 = 31 * var4 + this.redstoneSize;
         var4 = 31 * var4 + this.redstoneCount;
         var4 = 31 * var4 + this.redstoneMinHeight;
         var4 = 31 * var4 + this.redstoneMaxHeight;
         var4 = 31 * var4 + this.diamondSize;
         var4 = 31 * var4 + this.diamondCount;
         var4 = 31 * var4 + this.diamondMinHeight;
         var4 = 31 * var4 + this.diamondMaxHeight;
         var4 = 31 * var4 + this.lapisSize;
         var4 = 31 * var4 + this.lapisCount;
         var4 = 31 * var4 + this.lapisCenterHeight;
         return 31 * var4 + this.lapisSpread;
      }

      public ChunkProviderSettings func_177864_b() {
         return new ChunkProviderSettings(this);
      }
   }

   public static class Serializer implements JsonDeserializer<ChunkProviderSettings.Factory>, JsonSerializer<ChunkProviderSettings.Factory> {
      public ChunkProviderSettings.Factory deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject var7 = jsonElement.getAsJsonObject();
         ChunkProviderSettings.Factory var8 = new ChunkProviderSettings.Factory();

         try {
            var8.coordinateScale = JsonUtils.getFloat(var7, "coordinateScale", var8.coordinateScale);
            var8.heightScale = JsonUtils.getFloat(var7, "heightScale", var8.heightScale);
            var8.lowerLimitScale = JsonUtils.getFloat(var7, "lowerLimitScale", var8.lowerLimitScale);
            var8.upperLimitScale = JsonUtils.getFloat(var7, "upperLimitScale", var8.upperLimitScale);
            var8.depthNoiseScaleX = JsonUtils.getFloat(var7, "depthNoiseScaleX", var8.depthNoiseScaleX);
            var8.depthNoiseScaleZ = JsonUtils.getFloat(var7, "depthNoiseScaleZ", var8.depthNoiseScaleZ);
            var8.depthNoiseScaleExponent = JsonUtils.getFloat(var7, "depthNoiseScaleExponent", var8.depthNoiseScaleExponent);
            var8.mainNoiseScaleX = JsonUtils.getFloat(var7, "mainNoiseScaleX", var8.mainNoiseScaleX);
            var8.mainNoiseScaleY = JsonUtils.getFloat(var7, "mainNoiseScaleY", var8.mainNoiseScaleY);
            var8.mainNoiseScaleZ = JsonUtils.getFloat(var7, "mainNoiseScaleZ", var8.mainNoiseScaleZ);
            var8.baseSize = JsonUtils.getFloat(var7, "baseSize", var8.baseSize);
            var8.stretchY = JsonUtils.getFloat(var7, "stretchY", var8.stretchY);
            var8.biomeDepthWeight = JsonUtils.getFloat(var7, "biomeDepthWeight", var8.biomeDepthWeight);
            var8.biomeDepthOffset = JsonUtils.getFloat(var7, "biomeDepthOffset", var8.biomeDepthOffset);
            var8.biomeScaleWeight = JsonUtils.getFloat(var7, "biomeScaleWeight", var8.biomeScaleWeight);
            var8.biomeScaleOffset = JsonUtils.getFloat(var7, "biomeScaleOffset", var8.biomeScaleOffset);
            var8.seaLevel = JsonUtils.getInt(var7, "seaLevel", var8.seaLevel);
            var8.useCaves = JsonUtils.getBoolean(var7, "useCaves", var8.useCaves);
            var8.useDungeons = JsonUtils.getBoolean(var7, "useDungeons", var8.useDungeons);
            var8.dungeonChance = JsonUtils.getInt(var7, "dungeonChance", var8.dungeonChance);
            var8.useStrongholds = JsonUtils.getBoolean(var7, "useStrongholds", var8.useStrongholds);
            var8.useVillages = JsonUtils.getBoolean(var7, "useVillages", var8.useVillages);
            var8.useMineShafts = JsonUtils.getBoolean(var7, "useMineShafts", var8.useMineShafts);
            var8.useTemples = JsonUtils.getBoolean(var7, "useTemples", var8.useTemples);
            var8.useMonuments = JsonUtils.getBoolean(var7, "useMonuments", var8.useMonuments);
            var8.useRavines = JsonUtils.getBoolean(var7, "useRavines", var8.useRavines);
            var8.useWaterLakes = JsonUtils.getBoolean(var7, "useWaterLakes", var8.useWaterLakes);
            var8.waterLakeChance = JsonUtils.getInt(var7, "waterLakeChance", var8.waterLakeChance);
            var8.useLavaLakes = JsonUtils.getBoolean(var7, "useLavaLakes", var8.useLavaLakes);
            var8.lavaLakeChance = JsonUtils.getInt(var7, "lavaLakeChance", var8.lavaLakeChance);
            var8.useLavaOceans = JsonUtils.getBoolean(var7, "useLavaOceans", var8.useLavaOceans);
            var8.fixedBiome = JsonUtils.getInt(var7, "fixedBiome", var8.fixedBiome);
            if (var8.fixedBiome >= 38 || var8.fixedBiome < -1) {
               var8.fixedBiome = -1;
            } else if (var8.fixedBiome >= BiomeGenBase.hell.biomeID) {
               var8.fixedBiome += 2;
            }

            var8.biomeSize = JsonUtils.getInt(var7, "biomeSize", var8.biomeSize);
            var8.riverSize = JsonUtils.getInt(var7, "riverSize", var8.riverSize);
            var8.dirtSize = JsonUtils.getInt(var7, "dirtSize", var8.dirtSize);
            var8.dirtCount = JsonUtils.getInt(var7, "dirtCount", var8.dirtCount);
            var8.dirtMinHeight = JsonUtils.getInt(var7, "dirtMinHeight", var8.dirtMinHeight);
            var8.dirtMaxHeight = JsonUtils.getInt(var7, "dirtMaxHeight", var8.dirtMaxHeight);
            var8.gravelSize = JsonUtils.getInt(var7, "gravelSize", var8.gravelSize);
            var8.gravelCount = JsonUtils.getInt(var7, "gravelCount", var8.gravelCount);
            var8.gravelMinHeight = JsonUtils.getInt(var7, "gravelMinHeight", var8.gravelMinHeight);
            var8.gravelMaxHeight = JsonUtils.getInt(var7, "gravelMaxHeight", var8.gravelMaxHeight);
            var8.graniteSize = JsonUtils.getInt(var7, "graniteSize", var8.graniteSize);
            var8.graniteCount = JsonUtils.getInt(var7, "graniteCount", var8.graniteCount);
            var8.graniteMinHeight = JsonUtils.getInt(var7, "graniteMinHeight", var8.graniteMinHeight);
            var8.graniteMaxHeight = JsonUtils.getInt(var7, "graniteMaxHeight", var8.graniteMaxHeight);
            var8.dioriteSize = JsonUtils.getInt(var7, "dioriteSize", var8.dioriteSize);
            var8.dioriteCount = JsonUtils.getInt(var7, "dioriteCount", var8.dioriteCount);
            var8.dioriteMinHeight = JsonUtils.getInt(var7, "dioriteMinHeight", var8.dioriteMinHeight);
            var8.dioriteMaxHeight = JsonUtils.getInt(var7, "dioriteMaxHeight", var8.dioriteMaxHeight);
            var8.andesiteSize = JsonUtils.getInt(var7, "andesiteSize", var8.andesiteSize);
            var8.andesiteCount = JsonUtils.getInt(var7, "andesiteCount", var8.andesiteCount);
            var8.andesiteMinHeight = JsonUtils.getInt(var7, "andesiteMinHeight", var8.andesiteMinHeight);
            var8.andesiteMaxHeight = JsonUtils.getInt(var7, "andesiteMaxHeight", var8.andesiteMaxHeight);
            var8.coalSize = JsonUtils.getInt(var7, "coalSize", var8.coalSize);
            var8.coalCount = JsonUtils.getInt(var7, "coalCount", var8.coalCount);
            var8.coalMinHeight = JsonUtils.getInt(var7, "coalMinHeight", var8.coalMinHeight);
            var8.coalMaxHeight = JsonUtils.getInt(var7, "coalMaxHeight", var8.coalMaxHeight);
            var8.ironSize = JsonUtils.getInt(var7, "ironSize", var8.ironSize);
            var8.ironCount = JsonUtils.getInt(var7, "ironCount", var8.ironCount);
            var8.ironMinHeight = JsonUtils.getInt(var7, "ironMinHeight", var8.ironMinHeight);
            var8.ironMaxHeight = JsonUtils.getInt(var7, "ironMaxHeight", var8.ironMaxHeight);
            var8.goldSize = JsonUtils.getInt(var7, "goldSize", var8.goldSize);
            var8.goldCount = JsonUtils.getInt(var7, "goldCount", var8.goldCount);
            var8.goldMinHeight = JsonUtils.getInt(var7, "goldMinHeight", var8.goldMinHeight);
            var8.goldMaxHeight = JsonUtils.getInt(var7, "goldMaxHeight", var8.goldMaxHeight);
            var8.redstoneSize = JsonUtils.getInt(var7, "redstoneSize", var8.redstoneSize);
            var8.redstoneCount = JsonUtils.getInt(var7, "redstoneCount", var8.redstoneCount);
            var8.redstoneMinHeight = JsonUtils.getInt(var7, "redstoneMinHeight", var8.redstoneMinHeight);
            var8.redstoneMaxHeight = JsonUtils.getInt(var7, "redstoneMaxHeight", var8.redstoneMaxHeight);
            var8.diamondSize = JsonUtils.getInt(var7, "diamondSize", var8.diamondSize);
            var8.diamondCount = JsonUtils.getInt(var7, "diamondCount", var8.diamondCount);
            var8.diamondMinHeight = JsonUtils.getInt(var7, "diamondMinHeight", var8.diamondMinHeight);
            var8.diamondMaxHeight = JsonUtils.getInt(var7, "diamondMaxHeight", var8.diamondMaxHeight);
            var8.lapisSize = JsonUtils.getInt(var7, "lapisSize", var8.lapisSize);
            var8.lapisCount = JsonUtils.getInt(var7, "lapisCount", var8.lapisCount);
            var8.lapisCenterHeight = JsonUtils.getInt(var7, "lapisCenterHeight", var8.lapisCenterHeight);
            var8.lapisSpread = JsonUtils.getInt(var7, "lapisSpread", var8.lapisSpread);
         } catch (Exception var9) {
         }

         return var8;
      }

      public JsonElement serialize(ChunkProviderSettings.Factory factory, Type type, JsonSerializationContext jsonSerializationContext) {
         JsonObject var7 = new JsonObject();
         var7.addProperty("coordinateScale", factory.coordinateScale);
         var7.addProperty("heightScale", factory.heightScale);
         var7.addProperty("lowerLimitScale", factory.lowerLimitScale);
         var7.addProperty("upperLimitScale", factory.upperLimitScale);
         var7.addProperty("depthNoiseScaleX", factory.depthNoiseScaleX);
         var7.addProperty("depthNoiseScaleZ", factory.depthNoiseScaleZ);
         var7.addProperty("depthNoiseScaleExponent", factory.depthNoiseScaleExponent);
         var7.addProperty("mainNoiseScaleX", factory.mainNoiseScaleX);
         var7.addProperty("mainNoiseScaleY", factory.mainNoiseScaleY);
         var7.addProperty("mainNoiseScaleZ", factory.mainNoiseScaleZ);
         var7.addProperty("baseSize", factory.baseSize);
         var7.addProperty("stretchY", factory.stretchY);
         var7.addProperty("biomeDepthWeight", factory.biomeDepthWeight);
         var7.addProperty("biomeDepthOffset", factory.biomeDepthOffset);
         var7.addProperty("biomeScaleWeight", factory.biomeScaleWeight);
         var7.addProperty("biomeScaleOffset", factory.biomeScaleOffset);
         var7.addProperty("seaLevel", factory.seaLevel);
         var7.addProperty("useCaves", factory.useCaves);
         var7.addProperty("useDungeons", factory.useDungeons);
         var7.addProperty("dungeonChance", factory.dungeonChance);
         var7.addProperty("useStrongholds", factory.useStrongholds);
         var7.addProperty("useVillages", factory.useVillages);
         var7.addProperty("useMineShafts", factory.useMineShafts);
         var7.addProperty("useTemples", factory.useTemples);
         var7.addProperty("useMonuments", factory.useMonuments);
         var7.addProperty("useRavines", factory.useRavines);
         var7.addProperty("useWaterLakes", factory.useWaterLakes);
         var7.addProperty("waterLakeChance", factory.waterLakeChance);
         var7.addProperty("useLavaLakes", factory.useLavaLakes);
         var7.addProperty("lavaLakeChance", factory.lavaLakeChance);
         var7.addProperty("useLavaOceans", factory.useLavaOceans);
         var7.addProperty("fixedBiome", factory.fixedBiome);
         var7.addProperty("biomeSize", factory.biomeSize);
         var7.addProperty("riverSize", factory.riverSize);
         var7.addProperty("dirtSize", factory.dirtSize);
         var7.addProperty("dirtCount", factory.dirtCount);
         var7.addProperty("dirtMinHeight", factory.dirtMinHeight);
         var7.addProperty("dirtMaxHeight", factory.dirtMaxHeight);
         var7.addProperty("gravelSize", factory.gravelSize);
         var7.addProperty("gravelCount", factory.gravelCount);
         var7.addProperty("gravelMinHeight", factory.gravelMinHeight);
         var7.addProperty("gravelMaxHeight", factory.gravelMaxHeight);
         var7.addProperty("graniteSize", factory.graniteSize);
         var7.addProperty("graniteCount", factory.graniteCount);
         var7.addProperty("graniteMinHeight", factory.graniteMinHeight);
         var7.addProperty("graniteMaxHeight", factory.graniteMaxHeight);
         var7.addProperty("dioriteSize", factory.dioriteSize);
         var7.addProperty("dioriteCount", factory.dioriteCount);
         var7.addProperty("dioriteMinHeight", factory.dioriteMinHeight);
         var7.addProperty("dioriteMaxHeight", factory.dioriteMaxHeight);
         var7.addProperty("andesiteSize", factory.andesiteSize);
         var7.addProperty("andesiteCount", factory.andesiteCount);
         var7.addProperty("andesiteMinHeight", factory.andesiteMinHeight);
         var7.addProperty("andesiteMaxHeight", factory.andesiteMaxHeight);
         var7.addProperty("coalSize", factory.coalSize);
         var7.addProperty("coalCount", factory.coalCount);
         var7.addProperty("coalMinHeight", factory.coalMinHeight);
         var7.addProperty("coalMaxHeight", factory.coalMaxHeight);
         var7.addProperty("ironSize", factory.ironSize);
         var7.addProperty("ironCount", factory.ironCount);
         var7.addProperty("ironMinHeight", factory.ironMinHeight);
         var7.addProperty("ironMaxHeight", factory.ironMaxHeight);
         var7.addProperty("goldSize", factory.goldSize);
         var7.addProperty("goldCount", factory.goldCount);
         var7.addProperty("goldMinHeight", factory.goldMinHeight);
         var7.addProperty("goldMaxHeight", factory.goldMaxHeight);
         var7.addProperty("redstoneSize", factory.redstoneSize);
         var7.addProperty("redstoneCount", factory.redstoneCount);
         var7.addProperty("redstoneMinHeight", factory.redstoneMinHeight);
         var7.addProperty("redstoneMaxHeight", factory.redstoneMaxHeight);
         var7.addProperty("diamondSize", factory.diamondSize);
         var7.addProperty("diamondCount", factory.diamondCount);
         var7.addProperty("diamondMinHeight", factory.diamondMinHeight);
         var7.addProperty("diamondMaxHeight", factory.diamondMaxHeight);
         var7.addProperty("lapisSize", factory.lapisSize);
         var7.addProperty("lapisCount", factory.lapisCount);
         var7.addProperty("lapisCenterHeight", factory.lapisCenterHeight);
         var7.addProperty("lapisSpread", factory.lapisSpread);
         return var7;
      }
   }
}
