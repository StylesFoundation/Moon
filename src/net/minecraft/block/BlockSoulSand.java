package net.minecraft.block;

import com.moon.Moon;
import com.moon.events.impl.player.SlowdownEvent;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockSoulSand extends Block {
   public BlockSoulSand() {
      super(Material.sand, MapColor.brownColor);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1 - 0.125F, blockPos.getZ() + 1);
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity) {
      SlowdownEvent var8 = new SlowdownEvent(SlowdownEvent.Type.BLOCKS);
      Moon.getInstance().getEventBus().publish(var8);
      if (!var8.isCancelled()) {
         entity.motionX *= 0.4;
         entity.motionZ *= 0.4;
      }
   }
}
