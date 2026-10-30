package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BiomeColorHelper {
   private static final BiomeColorHelper.ColorResolver GRASS_COLOR = BiomeGenBase::getGrassColorAtPos;
   private static final BiomeColorHelper.ColorResolver FOLIAGE_COLOR = BiomeGenBase::getFoliageColorAtPos;
   private static final BiomeColorHelper.ColorResolver WATER_COLOR_MULTIPLIER = (biomeGenBase, blockPos) -> biomeGenBase.waterColorMultiplier;

   private static int getColorAtPos(IBlockAccess iBlockAccess, BlockPos blockPos, BiomeColorHelper.ColorResolver colorResolver) {
      int var6 = 0;
      int var7 = 0;
      int var8 = 0;

      for (BlockPos.MutableBlockPos var10 : BlockPos.getAllInBoxMutable(blockPos.add(-1, 0, -1), blockPos.add(1, 0, 1))) {
         int var11 = colorResolver.getColorAtPos(iBlockAccess.getBiomeGenForCoords(var10), var10);
         var6 += (var11 & 0xFF0000) >> 16;
         var7 += (var11 & 0xFF00) >> 8;
         var8 += var11 & 0xFF;
      }

      return (var6 / 9 & 0xFF) << 16 | (var7 / 9 & 0xFF) << 8 | var8 / 9 & 0xFF;
   }

   public static int getGrassColorAtPos(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return getColorAtPos(iBlockAccess, blockPos, GRASS_COLOR);
   }

   public static int getFoliageColorAtPos(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return getColorAtPos(iBlockAccess, blockPos, FOLIAGE_COLOR);
   }

   public static int getWaterColorAtPos(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return getColorAtPos(iBlockAccess, blockPos, WATER_COLOR_MULTIPLIER);
   }

   interface ColorResolver {
      int getColorAtPos(BiomeGenBase biomeGenBase, BlockPos blockPos);
   }
}
