package net.minecraft.item;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemEnderEye extends Item {
   public ItemEnderEye() {
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      IBlockState var12 = world.getBlockState(blockPos);
      if (entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)
         && var12.getBlock() == Blocks.end_portal_frame
         && !var12.getValue(BlockEndPortalFrame.EYE)) {
         if (!world.isRemote) {
            world.setBlockState(blockPos, var12.withProperty(BlockEndPortalFrame.EYE, Boolean.TRUE), 2);
            world.updateComparatorOutputLevel(blockPos, Blocks.end_portal_frame);
            itemStack.stackSize--;

            for (int var13 = 0; var13 < 16; var13++) {
               double var14 = blockPos.getX() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F;
               double var16 = blockPos.getY() + 0.8125F;
               double var18 = blockPos.getZ() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F;
               world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var14, var16, var18, 0.0, 0.0, 0.0);
            }

            EnumFacing var24 = var12.getValue(BlockEndPortalFrame.FACING);
            int var25 = 0;
            int var15 = 0;
            boolean var26 = false;
            boolean var17 = true;
            EnumFacing var27 = var24.rotateY();

            for (int var19 = -2; var19 <= 2; var19++) {
               BlockPos var20 = blockPos.offset(var27, var19);
               IBlockState var21 = world.getBlockState(var20);
               if (var21.getBlock() == Blocks.end_portal_frame) {
                  if (!var21.getValue(BlockEndPortalFrame.EYE)) {
                     var17 = false;
                     break;
                  }

                  var15 = var19;
                  if (!var26) {
                     var25 = var19;
                     var26 = true;
                  }
               }
            }

            if (var17 && var15 == var25 + 2) {
               BlockPos var28 = blockPos.offset(var24, 4);

               for (int var31 = var25; var31 <= var15; var31++) {
                  BlockPos var34 = var28.offset(var27, var31);
                  IBlockState var22 = world.getBlockState(var34);
                  if (var22.getBlock() != Blocks.end_portal_frame || !var22.getValue(BlockEndPortalFrame.EYE)) {
                     var17 = false;
                     break;
                  }
               }

               for (int var32 = var25 - 1; var32 <= var15 + 1; var32 += 4) {
                  var28 = blockPos.offset(var27, var32);

                  for (int var35 = 1; var35 <= 3; var35++) {
                     BlockPos var37 = var28.offset(var24, var35);
                     IBlockState var23 = world.getBlockState(var37);
                     if (var23.getBlock() != Blocks.end_portal_frame || !var23.getValue(BlockEndPortalFrame.EYE)) {
                        var17 = false;
                        break;
                     }
                  }
               }

               if (var17) {
                  for (int var33 = var25; var33 <= var15; var33++) {
                     var28 = blockPos.offset(var27, var33);

                     for (int var36 = 1; var36 <= 3; var36++) {
                        BlockPos var38 = var28.offset(var24, var36);
                        world.setBlockState(var38, Blocks.end_portal.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      MovingObjectPosition var7 = this.getMovingObjectPositionFromPlayer(world, entityPlayer, false);
      if (var7 != null
         && var7.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
         && world.getBlockState(var7.getBlockPos()).getBlock() == Blocks.end_portal_frame) {
         return itemStack;
      }

      if (!world.isRemote) {
         BlockPos var8 = world.getStrongholdPos("Stronghold", new BlockPos(entityPlayer));
         if (var8 != null) {
            EntityEnderEye var9 = new EntityEnderEye(world, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
            var9.moveTowards(var8);
            world.spawnEntityInWorld(var9);
            world.playSoundAtEntity(entityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            world.playAuxSFXAtEntity(null, 1002, new BlockPos(entityPlayer), 0);
            if (!entityPlayer.capabilities.isCreativeMode) {
               itemStack.stackSize--;
            }

            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
         }
      }

      return itemStack;
   }
}
