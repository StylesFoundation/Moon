package net.optifine;

import java.util.Properties;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.config.ConnectedParser;
import net.optifine.config.Matches;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import net.optifine.config.VillagerProfession;
import net.optifine.config.Weather;
import net.optifine.util.ArrayUtils;
import net.optifine.util.MathUtils;

public class RandomEntityRule {
   private String pathProps = null;
   private ResourceLocation baseResLoc = null;
   private final int index;
   private int[] textures = null;
   private ResourceLocation[] resourceLocations = null;
   private int[] weights = null;
   private BiomeGenBase[] biomes = null;
   private RangeListInt heights = null;
   private RangeListInt healthRange = null;
   private boolean healthPercent = false;
   private NbtTagValue nbtName = null;
   public int[] sumWeights = null;
   public int sumAllWeights = 1;
   private VillagerProfession[] professions = null;
   private EnumDyeColor[] collarColors = null;
   private Boolean baby = null;
   private RangeListInt moonPhases = null;
   private RangeListInt dayTimes = null;
   private Weather[] weatherList = null;

   public RandomEntityRule(
      Properties properties, String string2, ResourceLocation resourceLocation, int integer, String string5, ConnectedParser connectedParser
   ) {
      this.pathProps = string2;
      this.baseResLoc = resourceLocation;
      this.index = integer;
      this.textures = connectedParser.parseIntList(string5);
      this.weights = connectedParser.parseIntList(properties.getProperty("weights." + integer));
      this.biomes = connectedParser.parseBiomes(properties.getProperty("biomes." + integer));
      this.heights = connectedParser.parseRangeListInt(properties.getProperty("heights." + integer));
      if (this.heights == null) {
         this.heights = this.parseMinMaxHeight(properties, integer);
      }

      String var10 = properties.getProperty("health." + integer);
      if (var10 != null) {
         this.healthPercent = var10.contains("%");
         var10 = var10.replace("%", "");
         this.healthRange = connectedParser.parseRangeListInt(var10);
      }

      this.nbtName = connectedParser.parseNbtTagValue("name", properties.getProperty("name." + integer));
      this.professions = connectedParser.parseProfessions(properties.getProperty("professions." + integer));
      this.collarColors = connectedParser.parseDyeColors(properties.getProperty("collarColors." + integer), "collar color", ConnectedParser.DYE_COLORS_INVALID);
      this.baby = connectedParser.parseBooleanObject(properties.getProperty("baby." + integer));
      this.moonPhases = connectedParser.parseRangeListInt(properties.getProperty("moonPhase." + integer));
      this.dayTimes = connectedParser.parseRangeListInt(properties.getProperty("dayTime." + integer));
      this.weatherList = connectedParser.parseWeather(properties.getProperty("weather." + integer), "weather." + integer, null);
   }

   private RangeListInt parseMinMaxHeight(Properties properties, int integer) {
      String var6 = properties.getProperty("minHeight." + integer);
      String var7 = properties.getProperty("maxHeight." + integer);
      if (var6 == null && var7 == null) {
         return null;
      }

      int var8 = 0;
      if (var6 != null) {
         var8 = Config.parseInt(var6, -1);
         if (var8 < 0) {
            Config.Method252("Invalid minHeight: " + var6);
            return null;
         }
      }

      int var9 = 256;
      if (var7 != null) {
         var9 = Config.parseInt(var7, -1);
         if (var9 < 0) {
            Config.Method252("Invalid maxHeight: " + var7);
            return null;
         }
      }

      if (var9 < 0) {
         Config.Method252("Invalid minHeight, maxHeight: " + var6 + ", " + var7);
         return null;
      } else {
         RangeListInt var10 = new RangeListInt();
         var10.addRange(new RangeInt(var8, var9));
         return var10;
      }
   }

   public boolean isValid(String string) {
      if (this.textures == null || this.textures.length == 0) {
         Config.Method252("Invalid skins for rule: " + this.index);
         return false;
      }

      if (this.resourceLocations != null) {
         return true;
      }

      this.resourceLocations = new ResourceLocation[this.textures.length];
      boolean var5 = this.pathProps.startsWith("mcpatcher/mob/");
      ResourceLocation var6 = RandomEntities.getLocationRandom(this.baseResLoc, var5);
      if (var6 == null) {
         Config.Method252("Invalid path: " + this.baseResLoc.getResourcePath());
         return false;
      }

      for (int var7 = 0; var7 < this.resourceLocations.length; var7++) {
         int var8 = this.textures[var7];
         if (var8 <= 1) {
            this.resourceLocations[var7] = this.baseResLoc;
         } else {
            ResourceLocation var9 = RandomEntities.getLocationIndexed(var6, var8);
            if (var9 == null) {
               Config.Method252("Invalid path: " + this.baseResLoc.getResourcePath());
               return false;
            }

            if (!Config.hasResource(var9)) {
               Config.Method252("Texture not found: " + var9.getResourcePath());
               return false;
            }

            this.resourceLocations[var7] = var9;
         }
      }

      if (this.weights != null) {
         if (this.weights.length > this.resourceLocations.length) {
            Config.Method252("More weights defined than skins, trimming weights: " + string);
            int[] var10 = new int[this.resourceLocations.length];
            System.arraycopy(this.weights, 0, var10, 0, var10.length);
            this.weights = var10;
         }

         if (this.weights.length < this.resourceLocations.length) {
            Config.Method252("Less weights defined than skins, expanding weights: " + string);
            int[] var11 = new int[this.resourceLocations.length];
            System.arraycopy(this.weights, 0, var11, 0, this.weights.length);
            int var13 = MathUtils.getAverage(this.weights);

            for (int var15 = this.weights.length; var15 < var11.length; var15++) {
               var11[var15] = var13;
            }

            this.weights = var11;
         }

         this.sumWeights = new int[this.weights.length];
         int var12 = 0;

         for (int var14 = 0; var14 < this.weights.length; var14++) {
            if (this.weights[var14] < 0) {
               Config.Method252("Invalid weight: " + this.weights[var14]);
               return false;
            }

            var12 += this.weights[var14];
            this.sumWeights[var14] = var12;
         }

         this.sumAllWeights = var12;
         if (this.sumAllWeights <= 0) {
            Config.Method252("Invalid sum of all weights: " + var12);
            this.sumAllWeights = 1;
         }
      }

      if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
         Config.Method252("Invalid professions or careers: " + string);
         return false;
      } else if (this.collarColors == ConnectedParser.DYE_COLORS_INVALID) {
         Config.Method252("Invalid collar colors: " + string);
         return false;
      } else {
         return true;
      }
   }

   public boolean matches(IRandomEntity iRandomEntity) {
      if (this.biomes != null && !Matches.biome(iRandomEntity.getSpawnBiome(), this.biomes)) {
         return false;
      }

      if (this.heights != null) {
         BlockPos var5 = iRandomEntity.getSpawnPosition();
         if (var5 != null && !this.heights.isInRange(var5.getY())) {
            return false;
         }
      }

      if (this.healthRange != null) {
         int var15 = iRandomEntity.getHealth();
         if (this.healthPercent) {
            int var6 = iRandomEntity.getMaxHealth();
            if (var6 > 0) {
               var15 = (int)((double)(var15 * 100) / var6);
            }
         }

         if (!this.healthRange.isInRange(var15)) {
            return false;
         }
      }

      if (this.nbtName != null) {
         String var16 = iRandomEntity.getName();
         if (!this.nbtName.matchesValue(var16)) {
            return false;
         }
      }

      if (this.professions != null && iRandomEntity instanceof RandomEntity && ((RandomEntity)iRandomEntity).getEntity() instanceof EntityVillager var7) {
         int var8 = var7.getProfession();
         int var9 = var7.careerId;
         if (var8 < 0 || var9 < 0) {
            return false;
         }

         boolean var10 = false;

         for (VillagerProfession var14 : this.professions) {
            if (var14.matches(var8, var9)) {
               var10 = true;
               break;
            }
         }

         if (!var10) {
            return false;
         }
      }

      if (this.collarColors != null && iRandomEntity instanceof RandomEntity && ((RandomEntity)iRandomEntity).getEntity() instanceof EntityWolf var26) {
         if (!var26.isTamed()) {
            return false;
         }

         EnumDyeColor var28 = var26.getCollarColor();
         if (!Config.equalsOne(var28, this.collarColors)) {
            return false;
         }
      }

      if (this.baby != null
         && iRandomEntity instanceof RandomEntity
         && ((RandomEntity)iRandomEntity).getEntity() instanceof EntityLiving var27
         && var27.isChild() != this.baby) {
         return false;
      }

      if (this.moonPhases != null) {
         WorldClient var17 = Config.getMinecraft().theWorld;
         if (var17 != null) {
            int var23 = var17.getMoonPhase();
            if (!this.moonPhases.isInRange(var23)) {
               return false;
            }
         }
      }

      if (this.dayTimes != null) {
         WorldClient var18 = Config.getMinecraft().theWorld;
         if (var18 != null) {
            int var24 = (int)var18.getWorldInfo().getWorldTime();
            if (!this.dayTimes.isInRange(var24)) {
               return false;
            }
         }
      }

      if (this.weatherList != null) {
         WorldClient var19 = Config.getMinecraft().theWorld;
         if (var19 != null) {
            Weather var25 = Weather.getWeather(var19, 0.0F);
            return ArrayUtils.contains(this.weatherList, var25);
         }
      }

      return true;
   }

   public ResourceLocation getTextureLocation(ResourceLocation resourceLocation, int integer) {
      if (this.resourceLocations != null && this.resourceLocations.length != 0) {
         int var6 = 0;
         if (this.weights == null) {
            var6 = integer % this.resourceLocations.length;
         } else {
            int var7 = integer % this.sumAllWeights;

            for (int var8 = 0; var8 < this.sumWeights.length; var8++) {
               if (this.sumWeights[var8] > var7) {
                  var6 = var8;
                  break;
               }
            }
         }

         return this.resourceLocations[var6];
      } else {
         return resourceLocation;
      }
   }
}
