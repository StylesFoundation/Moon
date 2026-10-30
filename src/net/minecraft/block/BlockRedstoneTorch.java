package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneTorch extends BlockTorch {
   private static final Map<World, List<BlockRedstoneTorch.Toggle>> toggles = Maps.newHashMap();
   private final boolean isOn;

   private boolean isBurnedOut(World world, BlockPos blockPos, boolean boolean3) {
      if (!toggles.containsKey(world)) {
         toggles.put(world, Lists.newArrayList());
      }

      List<BlockRedstoneTorch.Toggle> var7 = toggles.get(world);
      if (boolean3) {
         var7.add(new BlockRedstoneTorch.Toggle(blockPos, world.getTotalWorldTime()));
      }

      int var8 = 0;

      for (BlockRedstoneTorch.Toggle var10 : var7) {
         if (var10.pos.equals(blockPos)) {
            if (++var8 >= 8) {
               return true;
            }
         }
      }

      return false;
   }

   protected BlockRedstoneTorch(boolean boolean1) {
      this.isOn = boolean1;
      this.setTickRandomly(true);
      this.setCreativeTab(null);
   }

   @Override
   public int tickRate(World world) {
      return 2;
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (this.isOn) {
         for (EnumFacing var10 : EnumFacing.values()) {
            world.notifyNeighborsOfStateChange(blockPos.offset(var10), this);
         }
      }
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (this.isOn) {
         for (EnumFacing var10 : EnumFacing.values()) {
            world.notifyNeighborsOfStateChange(blockPos.offset(var10), this);
         }
      }
   }

   @Override
   public int getWeakPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return this.isOn && iBlockState.getValue(FACING) != enumFacing ? 15 : 0;
   }

   private boolean shouldBeOff(World world, BlockPos blockPos, IBlockState iBlockState) {
      EnumFacing var4 = iBlockState.getValue(FACING).getOpposite();
      return world.isSidePowered(blockPos.offset(var4), var4);
   }

   @Override
   public void randomTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      boolean var8 = this.shouldBeOff(world, blockPos, iBlockState);
      List var9 = toggles.get(world);

      while (var9 != null && !var9.isEmpty() && world.getTotalWorldTime() - ((BlockRedstoneTorch.Toggle)var9.get(0)).time > 60L) {
         var9.remove(0);
      }

      if (this.isOn) {
         if (var8) {
            world.setBlockState(blockPos, Blocks.unlit_redstone_torch.getDefaultState().withProperty(FACING, iBlockState.getValue(FACING)), 3);
            if (this.isBurnedOut(world, blockPos, true)) {
               world.playSoundEffect(
                  blockPos.getX() + 0.5F,
                  blockPos.getY() + 0.5F,
                  blockPos.getZ() + 0.5F,
                  "random.fizz",
                  0.5F,
                  2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F
               );

               for (int var10 = 0; var10 < 5; var10++) {
                  double var11 = blockPos.getX() + random.nextDouble() * 0.6 + 0.2;
                  double var13 = blockPos.getY() + random.nextDouble() * 0.6 + 0.2;
                  double var15 = blockPos.getZ() + random.nextDouble() * 0.6 + 0.2;
                  world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var11, var13, var15, 0.0, 0.0, 0.0);
               }

               world.scheduleUpdate(blockPos, world.getBlockState(blockPos).getBlock(), 160);
            }
         }
      } else if (!var8 && !this.isBurnedOut(world, blockPos, false)) {
         world.setBlockState(blockPos, Blocks.redstone_torch.getDefaultState().withProperty(FACING, iBlockState.getValue(FACING)), 3);
      }
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!this.onNeighborChangeInternal(world, blockPos, iBlockState) && this.isOn == this.shouldBeOff(world, blockPos, iBlockState)) {
         world.scheduleUpdate(blockPos, this, this.tickRate(world));
      }
   }

   @Override
   public int getStrongPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return enumFacing == EnumFacing.DOWN ? this.getWeakPower(iBlockAccess, blockPos, iBlockState, enumFacing) : 0;
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Item.getItemFromBlock(Blocks.redstone_torch);
   }

   @Override
   public boolean canProvidePower() {
      return true;
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (this.isOn) {
         double var8 = blockPos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
         double var10 = blockPos.getY() + 0.7 + (random.nextDouble() - 0.5) * 0.2;
         double var12 = blockPos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
         EnumFacing var14 = iBlockState.getValue(FACING);
         if (var14.getAxis().isHorizontal()) {
            EnumFacing var15 = var14.getOpposite();
            var8 += 0.27 * var15.getFrontOffsetX();
            var10 += 0.22;
            var12 += 0.27 * var15.getFrontOffsetZ();
         }

         world.spawnParticle(EnumParticleTypes.REDSTONE, var8, var10, var12, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Item.getItemFromBlock(Blocks.redstone_torch);
   }

   @Override
   public boolean isAssociatedBlock(Block block) {
      return block == Blocks.unlit_redstone_torch || block == Blocks.redstone_torch;
   }

   static class Toggle {
      BlockPos pos;
      long time;

      public Toggle(BlockPos blockPos, long long2) {
         this.pos = blockPos;
         this.time = long2;
      }
   }
}
