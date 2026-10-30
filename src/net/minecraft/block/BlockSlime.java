package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockSlime extends BlockBreakable {
   public BlockSlime() {
      super(Material.clay, false, MapColor.grassColor);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.slipperiness = 0.8F;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.TRANSLUCENT;
   }

   @Override
   public void onFallenUpon(World world, BlockPos blockPos, Entity entity, float float4) {
      if (entity.isSneaking()) {
         super.onFallenUpon(world, blockPos, entity, float4);
      } else {
         entity.fall(float4, 0.0F);
      }
   }

   @Override
   public void onLanded(World world, Entity entity) {
      if (entity.isSneaking()) {
         super.onLanded(world, entity);
      } else if (entity.motionY < 0.0) {
         entity.motionY = -entity.motionY;
      }
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, Entity entity) {
      if (Math.abs(entity.motionY) < 0.1 && !entity.isSneaking()) {
         double var7 = 0.4 + Math.abs(entity.motionY) * 0.2;
         entity.motionX *= var7;
         entity.motionZ *= var7;
      }

      super.onEntityCollidedWithBlock(world, blockPos, entity);
   }
}
