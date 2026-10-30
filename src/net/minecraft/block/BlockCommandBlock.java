package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockCommandBlock extends BlockContainer {
   public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");

   public BlockCommandBlock() {
      super(Material.iron, MapColor.adobeColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(TRIGGERED, Boolean.FALSE));
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityCommandBlock();
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!world.isRemote) {
         boolean var8 = world.isBlockPowered(blockPos);
         boolean var9 = iBlockState.getValue(TRIGGERED);
         if (var8 && !var9) {
            world.setBlockState(blockPos, iBlockState.withProperty(TRIGGERED, Boolean.TRUE), 4);
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
         } else if (!var8 && var9) {
            world.setBlockState(blockPos, iBlockState.withProperty(TRIGGERED, Boolean.FALSE), 4);
         }
      }
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      TileEntity var8 = world.getTileEntity(blockPos);
      if (var8 instanceof TileEntityCommandBlock) {
         ((TileEntityCommandBlock)var8).getCommandBlockLogic().trigger(world);
         world.updateComparatorOutputLevel(blockPos, this);
      }
   }

   @Override
   public int tickRate(World world) {
      return 1;
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      TileEntity var12 = world.getTileEntity(blockPos);
      return var12 instanceof TileEntityCommandBlock && ((TileEntityCommandBlock)var12).getCommandBlockLogic().tryOpenEditCommandBlock(entityPlayer);
   }

   @Override
   public boolean hasComparatorInputOverride() {
      return true;
   }

   @Override
   public int getComparatorInputOverride(World world, BlockPos blockPos) {
      TileEntity var6 = world.getTileEntity(blockPos);
      return var6 instanceof TileEntityCommandBlock ? ((TileEntityCommandBlock)var6).getCommandBlockLogic().getSuccessCount() : 0;
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      TileEntity var9 = world.getTileEntity(blockPos);
      if (var9 instanceof TileEntityCommandBlock) {
         CommandBlockLogic var10 = ((TileEntityCommandBlock)var9).getCommandBlockLogic();
         if (itemStack.hasDisplayName()) {
            var10.setName(itemStack.getDisplayName());
         }

         if (!world.isRemote) {
            var10.setTrackOutput(world.getGameRules().getBoolean("sendCommandFeedback"));
         }
      }
   }

   @Override
   public int quantityDropped(Random random) {
      return 0;
   }

   @Override
   public int getRenderType() {
      return 3;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(TRIGGERED, (integer & 1) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      byte var5 = 0;
      if (iBlockState.getValue(TRIGGERED)) {
         var5 = 1;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, TRIGGERED);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState().withProperty(TRIGGERED, Boolean.FALSE);
   }
}
