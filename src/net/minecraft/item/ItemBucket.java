package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBucket extends Item {
   private final Block isFull;

   public ItemBucket(Block block) {
      this.maxStackSize = 1;
      this.isFull = block;
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      boolean var7 = this.isFull == Blocks.air;
      MovingObjectPosition var8 = this.getMovingObjectPositionFromPlayer(world, entityPlayer, var7);
      if (var8 != null && var8.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
         BlockPos var9 = var8.getBlockPos();
         if (!world.isBlockModifiable(entityPlayer, var9)) {
            return itemStack;
         }

         if (var7) {
            if (!entityPlayer.canPlayerEdit(var9.offset(var8.sideHit), var8.sideHit, itemStack)) {
               return itemStack;
            }

            IBlockState var10 = world.getBlockState(var9);
            Material var11 = var10.getBlock().getMaterial();
            if (var11 == Material.water && var10.getValue(BlockLiquid.LEVEL) == 0) {
               world.setBlockToAir(var9);
               entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
               return this.fillBucket(itemStack, entityPlayer, Items.water_bucket);
            }

            if (var11 == Material.lava && var10.getValue(BlockLiquid.LEVEL) == 0) {
               world.setBlockToAir(var9);
               entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
               return this.fillBucket(itemStack, entityPlayer, Items.lava_bucket);
            }
         } else {
            if (this.isFull == Blocks.air) {
               return new ItemStack(Items.bucket);
            }

            BlockPos var12 = var9.offset(var8.sideHit);
            if (!entityPlayer.canPlayerEdit(var12, var8.sideHit, itemStack)) {
               return itemStack;
            }

            if (this.tryPlaceContainedLiquid(world, var12) && !entityPlayer.capabilities.isCreativeMode) {
               entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
               return new ItemStack(Items.bucket);
            }
         }
      }

      return itemStack;
   }

   private ItemStack fillBucket(ItemStack itemStack, EntityPlayer entityPlayer, Item item) {
      if (entityPlayer.capabilities.isCreativeMode) {
         return itemStack;
      }

      if (--itemStack.stackSize <= 0) {
         return new ItemStack(item);
      }

      if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(item))) {
         entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(item, 1, 0), false);
      }

      return itemStack;
   }

   public boolean tryPlaceContainedLiquid(World world, BlockPos blockPos) {
      if (this.isFull == Blocks.air) {
         return false;
      }

      Material var6 = world.getBlockState(blockPos).getBlock().getMaterial();
      boolean var7 = !var6.isSolid();
      if (!world.isAirBlock(blockPos) && !var7) {
         return false;
      }

      if (world.provider.doesWaterVaporize() && this.isFull == Blocks.flowing_water) {
         int var8 = blockPos.getX();
         int var9 = blockPos.getY();
         int var10 = blockPos.getZ();
         world.playSoundEffect(var8 + 0.5F, var9 + 0.5F, var10 + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

         for (int var11 = 0; var11 < 8; var11++) {
            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var8 + Math.random(), var9 + Math.random(), var10 + Math.random(), 0.0, 0.0, 0.0);
         }
      } else {
         if (!world.isRemote && var7 && !var6.isLiquid()) {
            world.Method329(blockPos, true);
         }

         world.setBlockState(blockPos, this.isFull.getDefaultState(), 3);
      }

      return true;
   }
}
