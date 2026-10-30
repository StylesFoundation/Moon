package net.optifine.override;

import java.util.Arrays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.DynamicLights;
import net.optifine.util.ArrayCache;

public class ChunkCacheOF implements IBlockAccess {
   private final ChunkCache chunkCache;
   private final int posX;
   private final int posY;
   private final int posZ;
   private final int sizeX;
   private final int sizeY;
   private final int sizeZ;
   private final int sizeXY;
   private int[] combinedLights;
   private IBlockState[] blockStates;
   private final int arraySize;
   private final boolean dynamicLights = Config.isDynamicLights();
   private static final ArrayCache cacheCombinedLights = new ArrayCache(int.class, 16);
   private static final ArrayCache cacheBlockStates = new ArrayCache(IBlockState.class, 16);

   public ChunkCacheOF(ChunkCache chunkCache, BlockPos blockPos2, BlockPos blockPos3, int integer) {
      this.chunkCache = chunkCache;
      int var8 = blockPos2.getX() - integer >> 4;
      int var9 = blockPos2.getY() - integer >> 4;
      int var10 = blockPos2.getZ() - integer >> 4;
      int var11 = blockPos3.getX() + integer >> 4;
      int var12 = blockPos3.getY() + integer >> 4;
      int var13 = blockPos3.getZ() + integer >> 4;
      this.sizeX = var11 - var8 + 1 << 4;
      this.sizeY = var12 - var9 + 1 << 4;
      this.sizeZ = var13 - var10 + 1 << 4;
      this.sizeXY = this.sizeX * this.sizeY;
      this.arraySize = this.sizeX * this.sizeY * this.sizeZ;
      this.posX = var8 << 4;
      this.posY = var9 << 4;
      this.posZ = var10 << 4;
   }

   private int getPositionIndex(BlockPos blockPos) {
      int var5 = blockPos.getX() - this.posX;
      if (var5 >= 0 && var5 < this.sizeX) {
         int var6 = blockPos.getY() - this.posY;
         if (var6 >= 0 && var6 < this.sizeY) {
            int var7 = blockPos.getZ() - this.posZ;
            return var7 >= 0 && var7 < this.sizeZ ? var7 * this.sizeXY + var6 * this.sizeX + var5 : -1;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   @Override
   public int getCombinedLight(BlockPos blockPos, int integer) {
      int var6 = this.getPositionIndex(blockPos);
      if (var6 >= 0 && var6 < this.arraySize && this.combinedLights != null) {
         int var7 = this.combinedLights[var6];
         if (var7 == -1) {
            var7 = this.getCombinedLightRaw(blockPos, integer);
            this.combinedLights[var6] = var7;
         }

         return var7;
      } else {
         return this.getCombinedLightRaw(blockPos, integer);
      }
   }

   private int getCombinedLightRaw(BlockPos blockPos, int integer) {
      int var6 = this.chunkCache.getCombinedLight(blockPos, integer);
      if (this.dynamicLights && !this.getBlockState(blockPos).getBlock().isOpaqueCube()) {
         var6 = DynamicLights.getCombinedLight(blockPos, var6);
      }

      return var6;
   }

   @Override
   public IBlockState getBlockState(BlockPos blockPos) {
      int var5 = this.getPositionIndex(blockPos);
      if (var5 >= 0 && var5 < this.arraySize && this.blockStates != null) {
         IBlockState var6 = this.blockStates[var5];
         if (var6 == null) {
            var6 = this.chunkCache.getBlockState(blockPos);
            this.blockStates[var5] = var6;
         }

         return var6;
      } else {
         return this.chunkCache.getBlockState(blockPos);
      }
   }

   public void renderStart() {
      if (this.combinedLights == null) {
         this.combinedLights = (int[])cacheCombinedLights.allocate(this.arraySize);
      }

      Arrays.fill(this.combinedLights, -1);
      if (this.blockStates == null) {
         this.blockStates = (IBlockState[])cacheBlockStates.allocate(this.arraySize);
      }

      Arrays.fill(this.blockStates, null);
   }

   public void renderFinish() {
      cacheCombinedLights.free(this.combinedLights);
      this.combinedLights = null;
      cacheBlockStates.free(this.blockStates);
      this.blockStates = null;
   }

   @Override
   public boolean extendedLevelsInChunkCache() {
      return this.chunkCache.extendedLevelsInChunkCache();
   }

   @Override
   public BiomeGenBase getBiomeGenForCoords(BlockPos blockPos) {
      return this.chunkCache.getBiomeGenForCoords(blockPos);
   }

   @Override
   public int getStrongPower(BlockPos blockPos, EnumFacing enumFacing) {
      return this.chunkCache.getStrongPower(blockPos, enumFacing);
   }

   @Override
   public TileEntity getTileEntity(BlockPos blockPos) {
      return this.chunkCache.getTileEntity(blockPos);
   }

   @Override
   public WorldType getWorldType() {
      return this.chunkCache.getWorldType();
   }

   @Override
   public boolean isAirBlock(BlockPos blockPos) {
      return this.chunkCache.isAirBlock(blockPos);
   }
}
