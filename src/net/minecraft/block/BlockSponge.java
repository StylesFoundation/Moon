package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;

public class BlockSponge extends Block {
   public static final PropertyBool WET = PropertyBool.create("wet");

   protected BlockSponge() {
      super(Material.sponge);
      this.setDefaultState(this.blockState.getBaseState().withProperty(WET, Boolean.FALSE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.Method440() + ".dry.name");
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(WET) ? 1 : 0;
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.tryAbsorb(world, blockPos, iBlockState);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      this.tryAbsorb(world, blockPos, iBlockState);
      super.onNeighborBlockChange(world, blockPos, iBlockState, block);
   }

   protected void tryAbsorb(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!iBlockState.getValue(WET) && this.absorb(world, blockPos)) {
         world.setBlockState(blockPos, iBlockState.withProperty(WET, Boolean.TRUE), 2);
         world.playAuxSFX(2001, blockPos, Block.getIdFromBlock(Blocks.water));
      }
   }

   private boolean absorb(World world, BlockPos blockPos) {
      LinkedList var6 = Lists.newLinkedList();
      ArrayList<BlockPos> var7 = Lists.newArrayList();
      var6.add(new Tuple<>(blockPos, 0));
      int var8 = 0;

      while (!var6.isEmpty()) {
         Tuple var9 = (Tuple)var6.poll();
         BlockPos var10 = (BlockPos)var9.getFirst();
         int var11 = (Integer)var9.getSecond();

         for (EnumFacing var15 : EnumFacing.values()) {
            BlockPos var16 = var10.offset(var15);
            if (world.getBlockState(var16).getBlock().getMaterial() == Material.water) {
               world.setBlockState(var16, Blocks.air.getDefaultState(), 2);
               var7.add(var16);
               var8++;
               if (var11 < 6) {
                  var6.add(new Tuple<>(var16, var11 + 1));
               }
            }
         }

         if (var8 > 64) {
            break;
         }
      }

      for (BlockPos var18 : var7) {
         world.notifyNeighborsOfStateChange(var18, Blocks.air);
      }

      return var8 > 0;
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      list.add(new ItemStack(item, 1, 0));
      list.add(new ItemStack(item, 1, 1));
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(WET, (integer & 1) == 1);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(WET) ? 1 : 0;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, WET);
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (iBlockState.getValue(WET)) {
         EnumFacing var8 = EnumFacing.random(random);
         if (var8 != EnumFacing.UP && !World.doesBlockHaveSolidTopSurface(world, blockPos.offset(var8))) {
            double var9 = blockPos.getX();
            double var11 = blockPos.getY();
            double var13 = blockPos.getZ();
            if (var8 == EnumFacing.DOWN) {
               var11 -= 0.05;
               var9 += random.nextDouble();
               var13 += random.nextDouble();
            } else {
               var11 += random.nextDouble() * 0.8;
               if (var8.getAxis() == EnumFacing.Axis.X) {
                  var13 += random.nextDouble();
                  if (var8 == EnumFacing.EAST) {
                     var9++;
                  } else {
                     var9 += 0.05;
                  }
               } else {
                  var9 += random.nextDouble();
                  if (var8 == EnumFacing.SOUTH) {
                     var13++;
                  } else {
                     var13 += 0.05;
                  }
               }
            }

            world.spawnParticle(EnumParticleTypes.DRIP_WATER, var9, var11, var13, 0.0, 0.0, 0.0);
         }
      }
   }
}
