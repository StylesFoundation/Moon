package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class BlockBeacon extends BlockContainer {
   public BlockBeacon() {
      super(Material.glass, MapColor.diamondColor);
      this.setHardness(3.0F);
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityBeacon();
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote) {
         TileEntity var12 = world.getTileEntity(blockPos);
         if (var12 instanceof TileEntityBeacon) {
            entityPlayer.displayGUIChest((TileEntityBeacon)var12);
            entityPlayer.triggerAchievement(StatList.field_181730_N);
         }
      }

      return true;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public int getRenderType() {
      return 3;
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      super.onBlockPlacedBy(world, blockPos, iBlockState, entityLivingBase, itemStack);
      if (itemStack.hasDisplayName()) {
         TileEntity var9 = world.getTileEntity(blockPos);
         if (var9 instanceof TileEntityBeacon) {
            ((TileEntityBeacon)var9).setName(itemStack.getDisplayName());
         }
      }
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      TileEntity var8 = world.getTileEntity(blockPos);
      if (var8 instanceof TileEntityBeacon) {
         ((TileEntityBeacon)var8).updateBeacon();
         world.addBlockEvent(blockPos, this, 1, 0);
      }
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public static void updateColorAsync(World world, BlockPos blockPos) {
      HttpUtil.field_180193_a.submit(() -> {
         Chunk var5 = world.getChunkFromBlockCoords(blockPos);

         for (int var6 = blockPos.getY() - 1; var6 >= 0; var6--) {
            BlockPos var7 = new BlockPos(blockPos.getX(), var6, blockPos.getZ());
            if (!var5.canSeeSky(var7)) {
               break;
            }

            IBlockState var8 = world.getBlockState(var7);
            if (var8.getBlock() == Blocks.beacon) {
               ((WorldServer)world).addScheduledTask(() -> {
                  TileEntity tileEntity = world.getTileEntity(var7);
                  if (tileEntity instanceof TileEntityBeacon) {
                     ((TileEntityBeacon)tileEntity).updateBeacon();
                     world.addBlockEvent(var7, Blocks.beacon, 1, 0);
                  }
               });
            }
         }
      });
   }
}
