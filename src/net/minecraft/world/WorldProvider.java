package net.minecraft.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderDebug;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.FlatGeneratorInfo;

public abstract class WorldProvider {
   public static final float[] moonPhaseFactors = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   protected World worldObj;
   private WorldType terrainType;
   private String generatorSettings;
   protected WorldChunkManager worldChunkMgr;
   protected boolean isHellWorld;
   protected boolean hasNoSky;
   protected final float[] lightBrightnessTable = new float[16];
   protected int dimensionId;
   private final float[] colorsSunriseSunset = new float[4];

   public final void registerWorld(World world) {
      this.worldObj = world;
      this.terrainType = world.getWorldInfo().getTerrainType();
      this.generatorSettings = world.getWorldInfo().getGeneratorOptions();
      this.registerWorldChunkManager();
      this.generateLightBrightnessTable();
   }

   protected void generateLightBrightnessTable() {
      for (int var5 = 0; var5 <= 15; var5++) {
         float var6 = 1.0F - var5 / 15.0F;
         this.lightBrightnessTable[var5] = (1.0F - var6) / (var6 * 3.0F + 1.0F) * 1.0F + 0.0F;
      }
   }

   protected void registerWorldChunkManager() {
      WorldType var4 = this.worldObj.getWorldInfo().getTerrainType();
      if (var4 == WorldType.FLAT) {
         FlatGeneratorInfo var5 = FlatGeneratorInfo.createFlatGeneratorFromString(this.worldObj.getWorldInfo().getGeneratorOptions());
         this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.Method289(var5.getBiome(), BiomeGenBase.field_180279_ad), 0.5F);
      } else if (var4 == WorldType.DEBUG_WORLD) {
         this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.plains, 0.0F);
      } else {
         this.worldChunkMgr = new WorldChunkManager(this.worldObj);
      }
   }

   public IChunkProvider createChunkGenerator() {
      return this.terrainType == WorldType.FLAT
         ? new ChunkProviderFlat(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings)
         : (
            this.terrainType == WorldType.DEBUG_WORLD
               ? new ChunkProviderDebug(this.worldObj)
               : new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings)
         );
   }

   public boolean canCoordinateBeSpawn(int integer1, int integer2) {
      return this.worldObj.getGroundAboveSeaLevel(new BlockPos(integer1, 0, integer2)) == Blocks.grass;
   }

   public float calculateCelestialAngle(long long1, float float2) {
      int var7 = (int)(long1 % 24000L);
      float var8 = (var7 + float2) / 24000.0F - 0.25F;
      if (var8 < 0.0F) {
         var8++;
      }

      if (var8 > 1.0F) {
         var8--;
      }

      return 1.0F - (float)((Math.cos(var8 * Math.PI) + 1.0) / 2.0) + 0.0F;
   }

   public int getMoonPhase(long long1) {
      return (int)(long1 / 24000L % 8L + 8L) % 8;
   }

   public boolean isSurfaceWorld() {
      return true;
   }

   public float[] calcSunriseSunsetColors(float float1, float float2) {
      float var7 = MathHelper.cos(float1 * (float) Math.PI * 2.0F) - 0.0F;
      if (var7 >= -0.4F && var7 <= 0.4F) {
         float var9 = (var7 - -0.0F) / 0.4F * 0.5F + 0.5F;
         float var10 = 1.0F - (1.0F - MathHelper.sin(var9 * (float) Math.PI)) * 0.99F;
         var10 *= var10;
         this.colorsSunriseSunset[0] = var9 * 0.3F + 0.7F;
         this.colorsSunriseSunset[1] = var9 * var9 * 0.7F + 0.2F;
         this.colorsSunriseSunset[2] = var9 * var9 * 0.0F + 0.2F;
         this.colorsSunriseSunset[3] = var10;
         return this.colorsSunriseSunset;
      } else {
         return null;
      }
   }

   public Vec3 getFogColor(float float1, float float2) {
      float var3 = MathHelper.clamp_float(MathHelper.cos(float1 * (float) Math.PI * 2.0F) * 2.0F + 0.5F, 0.0F, 1.0F);
      float var4 = 0.7529412F * (var3 * 0.94F + 0.06F);
      float var5 = 0.84705883F * (var3 * 0.94F + 0.06F);
      float var6 = 1.0F * (var3 * 0.91F + 0.09F);
      return new Vec3(var4, var5, var6);
   }

   public boolean canRespawnHere() {
      return true;
   }

   public static WorldProvider getProviderForDimension(int integer) {
      return integer == -1 ? new WorldProviderHell() : (integer == 0 ? new WorldProviderSurface() : (integer == 1 ? new WorldProviderEnd() : null));
   }

   public float getCloudHeight() {
      return 128.0F;
   }

   public boolean isSkyColored() {
      return true;
   }

   public BlockPos getSpawnCoordinate() {
      return null;
   }

   public int getAverageGroundLevel() {
      return this.terrainType == WorldType.FLAT ? 4 : this.worldObj.getSeaLevel() + 1;
   }

   public double getVoidFogYFactor() {
      return this.terrainType == WorldType.FLAT ? 1.0 : 0.03125;
   }

   public boolean doesXZShowFog(int integer1, int integer2) {
      return false;
   }

   public abstract String getDimensionName();

   public abstract String getInternalNameSuffix();

   public WorldChunkManager getWorldChunkManager() {
      return this.worldChunkMgr;
   }

   public boolean doesWaterVaporize() {
      return this.isHellWorld;
   }

   public boolean getHasNoSky() {
      return this.hasNoSky;
   }

   public float[] getLightBrightnessTable() {
      return this.lightBrightnessTable;
   }

   public int getDimensionId() {
      return this.dimensionId;
   }

   public WorldBorder getWorldBorder() {
      return new WorldBorder();
   }
}
