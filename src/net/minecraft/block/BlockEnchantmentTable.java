package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockEnchantmentTable extends BlockContainer {
   protected BlockEnchantmentTable() {
      super(Material.rock, MapColor.redColor);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
      this.setLightOpacity(0);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      super.randomDisplayTick(world, blockPos, iBlockState, random);

      for (int var8 = -2; var8 <= 2; var8++) {
         for (int var9 = -2; var9 <= 2; var9++) {
            if (var8 > -2 && var8 < 2 && var9 == -1) {
               var9 = 2;
            }

            if (random.nextInt(16) == 0) {
               for (int var10 = 0; var10 <= 1; var10++) {
                  BlockPos var11 = blockPos.add(var8, var10, var9);
                  if (world.getBlockState(var11).getBlock() == Blocks.bookshelf) {
                     if (!world.isAirBlock(blockPos.add(var8 / 2, 0, var9 / 2))) {
                        break;
                     }

                     world.spawnParticle(
                        EnumParticleTypes.ENCHANTMENT_TABLE,
                        blockPos.getX() + 0.5,
                        blockPos.getY() + 2.0,
                        blockPos.getZ() + 0.5,
                        var8 + random.nextFloat() - 0.5,
                        var10 - random.nextFloat() - 1.0F,
                        var9 + random.nextFloat() - 0.5
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public int getRenderType() {
      return 3;
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityEnchantmentTable();
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote) {
         TileEntity var12 = world.getTileEntity(blockPos);
         if (var12 instanceof TileEntityEnchantmentTable) {
            entityPlayer.displayGui((TileEntityEnchantmentTable)var12);
         }
      }

      return true;
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      super.onBlockPlacedBy(world, blockPos, iBlockState, entityLivingBase, itemStack);
      if (itemStack.hasDisplayName()) {
         TileEntity var9 = world.getTileEntity(blockPos);
         if (var9 instanceof TileEntityEnchantmentTable) {
            ((TileEntityEnchantmentTable)var9).setCustomName(itemStack.getDisplayName());
         }
      }
   }
}
