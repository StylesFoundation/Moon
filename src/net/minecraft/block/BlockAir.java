package net.minecraft.block;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockAir extends Block {
   private static final Map mapOriginalOpacity = new IdentityHashMap();

   protected BlockAir() {
      super(Material.air);
   }

   @Override
   public int getRenderType() {
      return -1;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return null;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean canCollideCheck(IBlockState iBlockState, boolean boolean2) {
      return false;
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
   }

   @Override
   public boolean isReplaceable(World world, BlockPos blockPos) {
      return true;
   }

   public static void setLightOpacity(Block block, int integer) {
      if (!mapOriginalOpacity.containsKey(block)) {
         mapOriginalOpacity.put(block, block.lightOpacity);
      }

      block.lightOpacity = integer;
   }

   public static void restoreLightOpacity(Block block) {
      if (mapOriginalOpacity.containsKey(block)) {
         int var4 = (Integer)mapOriginalOpacity.get(block);
         setLightOpacity(block, var4);
      }
   }
}
