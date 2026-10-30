package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSlab extends ItemBlock {
   private final BlockSlab singleSlab;
   private final BlockSlab doubleSlab;

   public ItemSlab(Block block, BlockSlab blockSlab2, BlockSlab blockSlab3) {
      super(block);
      this.singleSlab = blockSlab2;
      this.doubleSlab = blockSlab3;
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   @Override
   public int getMetadata(int integer) {
      return integer;
   }

   @Override
   public String getUnlocalizedName(ItemStack itemStack) {
      return this.singleSlab.getUnlocalizedName(itemStack.getMetadata());
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (itemStack.stackSize == 0) {
         return false;
      }

      if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
         return false;
      }

      Object var12 = this.singleSlab.getVariant(itemStack);
      IBlockState var13 = world.getBlockState(blockPos);
      if (var13.getBlock() == this.singleSlab) {
         IProperty var14 = this.singleSlab.getVariantProperty();
         Comparable var15 = var13.getValue(var14);
         BlockSlab.EnumBlockHalf var16 = var13.getValue(BlockSlab.HALF);
         if ((enumFacing == EnumFacing.UP && var16 == BlockSlab.EnumBlockHalf.BOTTOM || enumFacing == EnumFacing.DOWN && var16 == BlockSlab.EnumBlockHalf.TOP)
            && var15 == var12) {
            IBlockState var17 = this.doubleSlab.getDefaultState().withProperty(var14, var15);
            if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(world, blockPos, var17)) && world.setBlockState(blockPos, var17, 3)) {
               world.playSoundEffect(
                  blockPos.getX() + 0.5F,
                  blockPos.getY() + 0.5F,
                  blockPos.getZ() + 0.5F,
                  this.doubleSlab.stepSound.getPlaceSound(),
                  (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F,
                  this.doubleSlab.stepSound.getFrequency() * 0.8F
               );
               itemStack.stackSize--;
            }

            return true;
         }
      }

      return this.tryPlace(itemStack, world, blockPos.offset(enumFacing), var12)
         || super.onItemUse(itemStack, entityPlayer, world, blockPos, enumFacing, float6, float7, float8);
   }

   @Override
   public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, EnumFacing enumFacing, EntityPlayer entityPlayer, ItemStack itemStack) {
      BlockPos var9 = blockPos;
      IProperty var10 = this.singleSlab.getVariantProperty();
      Object var11 = this.singleSlab.getVariant(itemStack);
      IBlockState var12 = world.getBlockState(blockPos);
      if (var12.getBlock() == this.singleSlab) {
         boolean var13 = var12.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP;
         if ((enumFacing == EnumFacing.UP && !var13 || enumFacing == EnumFacing.DOWN && var13) && var11 == var12.getValue(var10)) {
            return true;
         }
      }

      blockPos = blockPos.offset(enumFacing);
      IBlockState var15 = world.getBlockState(blockPos);
      return var15.getBlock() == this.singleSlab && var11 == var15.getValue(var10)
         || super.canPlaceBlockOnSide(world, var9, enumFacing, entityPlayer, itemStack);
   }

   private boolean tryPlace(ItemStack itemStack, World world, BlockPos blockPos, Object object) {
      IBlockState var8 = world.getBlockState(blockPos);
      if (var8.getBlock() == this.singleSlab) {
         Comparable var9 = var8.getValue(this.singleSlab.getVariantProperty());
         if (var9 == object) {
            IBlockState var10 = this.doubleSlab.getDefaultState().withProperty((IProperty)this.singleSlab.getVariantProperty(), var9);
            if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(world, blockPos, var10)) && world.setBlockState(blockPos, var10, 3)) {
               world.playSoundEffect(
                  blockPos.getX() + 0.5F,
                  blockPos.getY() + 0.5F,
                  blockPos.getZ() + 0.5F,
                  this.doubleSlab.stepSound.getPlaceSound(),
                  (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F,
                  this.doubleSlab.stepSound.getFrequency() * 0.8F
               );
               itemStack.stackSize--;
            }

            return true;
         }
      }

      return false;
   }
}
