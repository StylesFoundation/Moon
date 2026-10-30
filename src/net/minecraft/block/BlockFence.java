package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemLead;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFence extends Block {
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");

   public BlockFence(Material material) {
      this(material, material.getMaterialMapColor());
   }

   public BlockFence(Material material, MapColor mapColor) {
      super(material, mapColor);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(NORTH, Boolean.FALSE)
            .withProperty(EAST, Boolean.FALSE)
            .withProperty(SOUTH, Boolean.FALSE)
            .withProperty(WEST, Boolean.FALSE)
      );
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      boolean var10 = this.canConnectTo(world, blockPos.north());
      boolean var11 = this.canConnectTo(world, blockPos.south());
      boolean var12 = this.canConnectTo(world, blockPos.west());
      boolean var13 = this.canConnectTo(world, blockPos.east());
      float var14 = 0.375F;
      float var15 = 0.625F;
      float var16 = 0.375F;
      float var17 = 0.625F;
      if (var10) {
         var16 = 0.0F;
      }

      if (var11) {
         var17 = 1.0F;
      }

      if (var10 || var11) {
         this.setBlockBounds(0.375F, 0.0F, var16, 0.625F, 1.5F, var17);
         super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      }

      var16 = 0.375F;
      var17 = 0.625F;
      if (var12) {
         var14 = 0.0F;
      }

      if (var13) {
         var15 = 1.0F;
      }

      if (var12 || var13 || !var10 && !var11) {
         this.setBlockBounds(var14, 0.0F, var16, var15, 1.5F, var17);
         super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      }

      if (var10) {
         var16 = 0.0F;
      }

      if (var11) {
         var17 = 1.0F;
      }

      this.setBlockBounds(var14, 0.0F, var16, var15, 1.0F, var17);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      boolean var6 = this.canConnectTo(iBlockAccess, blockPos.north());
      boolean var7 = this.canConnectTo(iBlockAccess, blockPos.south());
      boolean var8 = this.canConnectTo(iBlockAccess, blockPos.west());
      boolean var9 = this.canConnectTo(iBlockAccess, blockPos.east());
      float var10 = 0.375F;
      float var11 = 0.625F;
      float var12 = 0.375F;
      float var13 = 0.625F;
      if (var6) {
         var12 = 0.0F;
      }

      if (var7) {
         var13 = 1.0F;
      }

      if (var8) {
         var10 = 0.0F;
      }

      if (var9) {
         var11 = 1.0F;
      }

      this.setBlockBounds(var10, 0.0F, var12, var11, 1.0F, var13);
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
   public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return false;
   }

   public boolean canConnectTo(IBlockAccess iBlockAccess, BlockPos blockPos) {
      Block var6 = iBlockAccess.getBlockState(blockPos).getBlock();
      return var6 != Blocks.barrier
         && (
            var6 instanceof BlockFence && var6.blockMaterial == this.blockMaterial
               || var6 instanceof BlockFenceGate
               || var6.blockMaterial.isOpaque() && var6.isFullCube() && var6.blockMaterial != Material.gourd
         );
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return true;
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      return world.isRemote || ItemLead.attachToFence(entityPlayer, world, blockPos);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return 0;
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockState.withProperty(NORTH, this.canConnectTo(iBlockAccess, blockPos.north()))
         .withProperty(EAST, this.canConnectTo(iBlockAccess, blockPos.east()))
         .withProperty(SOUTH, this.canConnectTo(iBlockAccess, blockPos.south()))
         .withProperty(WEST, this.canConnectTo(iBlockAccess, blockPos.west()));
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, NORTH, EAST, WEST, SOUTH);
   }
}
