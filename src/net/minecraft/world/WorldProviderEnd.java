package net.minecraft.world;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;

public class WorldProviderEnd extends WorldProvider {
   @Override
   public void registerWorldChunkManager() {
      this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F);
      this.dimensionId = 1;
      this.hasNoSky = true;
   }

   @Override
   public IChunkProvider createChunkGenerator() {
      return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
   }

   @Override
   public float calculateCelestialAngle(long long1, float float2) {
      return 0.0F;
   }

   @Override
   public float[] calcSunriseSunsetColors(float float1, float float2) {
      return null;
   }

   @Override
   public Vec3 getFogColor(float float1, float float2) {
      int var5 = 10518688;
      float var6 = MathHelper.clamp_float(MathHelper.cos(float1 * (float) Math.PI * 2.0F) * 2.0F + 0.5F, 0.0F, 1.0F);
      float var7 = 0.627451F;
      float var8 = 0.5019608F;
      float var9 = 0.627451F;
      var7 *= var6 * 0.0F + 0.15F;
      var8 *= var6 * 0.0F + 0.15F;
      var9 *= var6 * 0.0F + 0.15F;
      return new Vec3(var7, var8, var9);
   }

   @Override
   public boolean isSkyColored() {
      return false;
   }

   @Override
   public boolean canRespawnHere() {
      return false;
   }

   @Override
   public boolean isSurfaceWorld() {
      return false;
   }

   @Override
   public float getCloudHeight() {
      return 8.0F;
   }

   @Override
   public boolean canCoordinateBeSpawn(int integer1, int integer2) {
      return this.worldObj.getGroundAboveSeaLevel(new BlockPos(integer1, 0, integer2)).getMaterial().blocksMovement();
   }

   @Override
   public BlockPos getSpawnCoordinate() {
      return new BlockPos(100, 50, 0);
   }

   @Override
   public int getAverageGroundLevel() {
      return 50;
   }

   @Override
   public boolean doesXZShowFog(int integer1, int integer2) {
      return true;
   }

   @Override
   public String getDimensionName() {
      return "The End";
   }

   @Override
   public String getInternalNameSuffix() {
      return "_end";
   }
}
