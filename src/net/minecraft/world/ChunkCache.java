package net.minecraft.world;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class ChunkCache implements IBlockAccess {
   protected int chunkX;
   protected int chunkZ;
   protected Chunk[][] chunkArray;
   protected boolean hasExtendedLevels;
   protected World worldObj;

   public ChunkCache(World world, BlockPos blockPos2, BlockPos blockPos3, int integer) {
      this.worldObj = world;
      this.chunkX = blockPos2.getX() - integer >> 4;
      this.chunkZ = blockPos2.getZ() - integer >> 4;
      int var8 = blockPos3.getX() + integer >> 4;
      int var9 = blockPos3.getZ() + integer >> 4;
      this.chunkArray = new Chunk[var8 - this.chunkX + 1][var9 - this.chunkZ + 1];
      this.hasExtendedLevels = true;

      for (int var10 = this.chunkX; var10 <= var8; var10++) {
         for (int var11 = this.chunkZ; var11 <= var9; var11++) {
            this.chunkArray[var10 - this.chunkX][var11 - this.chunkZ] = world.getChunkFromChunkCoords(var10, var11);
         }
      }

      for (int var13 = blockPos2.getX() >> 4; var13 <= blockPos3.getX() >> 4; var13++) {
         for (int var14 = blockPos2.getZ() >> 4; var14 <= blockPos3.getZ() >> 4; var14++) {
            Chunk var12 = this.chunkArray[var13 - this.chunkX][var14 - this.chunkZ];
            if (var12 != null && !var12.getAreLevelsEmpty(blockPos2.getY(), blockPos3.getY())) {
               this.hasExtendedLevels = false;
            }
         }
      }
   }

   @Override
   public boolean extendedLevelsInChunkCache() {
      return this.hasExtendedLevels;
   }

   @Override
   public TileEntity getTileEntity(BlockPos blockPos) {
      int var2 = (blockPos.getX() >> 4) - this.chunkX;
      int var3 = (blockPos.getZ() >> 4) - this.chunkZ;
      return this.chunkArray[var2][var3].getTileEntity(blockPos, Chunk.EnumCreateEntityType.IMMEDIATE);
   }

   @Override
   public int getCombinedLight(BlockPos blockPos, int integer) {
      int var6 = this.getLightForExt(EnumSkyBlock.SKY, blockPos);
      int var7 = this.getLightForExt(EnumSkyBlock.BLOCK, blockPos);
      if (var7 < integer) {
         var7 = integer;
      }

      return var6 << 20 | var7 << 4;
   }

   @Override
   public IBlockState getBlockState(BlockPos blockPos) {
      if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
         int var5 = (blockPos.getX() >> 4) - this.chunkX;
         int var6 = (blockPos.getZ() >> 4) - this.chunkZ;
         if (var5 >= 0 && var5 < this.chunkArray.length && var6 >= 0 && var6 < this.chunkArray[var5].length) {
            Chunk var7 = this.chunkArray[var5][var6];
            if (var7 != null) {
               return var7.getBlockState(blockPos);
            }
         }
      }

      return Blocks.air.getDefaultState();
   }

   @Override
   public BiomeGenBase getBiomeGenForCoords(BlockPos blockPos) {
      return this.worldObj.getBiomeGenForCoords(blockPos);
   }

   private int getLightForExt(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
      if (enumSkyBlock == EnumSkyBlock.SKY && this.worldObj.provider.getHasNoSky()) {
         return 0;
      }

      if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
         if (this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            int var12 = 0;

            for (EnumFacing var10 : EnumFacing.values()) {
               int var11 = this.getLightFor(enumSkyBlock, blockPos.offset(var10));
               if (var11 > var12) {
                  var12 = var11;
               }

               if (var12 >= 15) {
                  return var12;
               }
            }

            return var12;
         } else {
            int var6 = (blockPos.getX() >> 4) - this.chunkX;
            int var7 = (blockPos.getZ() >> 4) - this.chunkZ;
            return this.chunkArray[var6][var7].getLightFor(enumSkyBlock, blockPos);
         }
      } else {
         return enumSkyBlock.defaultLightValue;
      }
   }

   @Override
   public boolean isAirBlock(BlockPos blockPos) {
      return this.getBlockState(blockPos).getBlock().getMaterial() == Material.air;
   }

   public int getLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
      if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
         int var6 = (blockPos.getX() >> 4) - this.chunkX;
         int var7 = (blockPos.getZ() >> 4) - this.chunkZ;
         return this.chunkArray[var6][var7].getLightFor(enumSkyBlock, blockPos);
      } else {
         return enumSkyBlock.defaultLightValue;
      }
   }

   @Override
   public int getStrongPower(BlockPos blockPos, EnumFacing enumFacing) {
      IBlockState var3 = this.getBlockState(blockPos);
      return var3.getBlock().getStrongPower(this, blockPos, var3, enumFacing);
   }

   @Override
   public WorldType getWorldType() {
      return this.worldObj.getWorldType();
   }
}
