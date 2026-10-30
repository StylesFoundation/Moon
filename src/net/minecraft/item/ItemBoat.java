package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemBoat extends Item {
   public ItemBoat() {
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.tabTransport);
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      float var8 = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * 1.0F;
      float var9 = entityPlayer.prevRotationYaw + (entityPlayer.rotationYaw - entityPlayer.prevRotationYaw) * 1.0F;
      double var10 = entityPlayer.prevPosX + (entityPlayer.posX - entityPlayer.prevPosX) * 1.0;
      double var12 = entityPlayer.prevPosY + (entityPlayer.posY - entityPlayer.prevPosY) * 1.0 + entityPlayer.getEyeHeight();
      double var14 = entityPlayer.prevPosZ + (entityPlayer.posZ - entityPlayer.prevPosZ) * 1.0;
      Vec3 var16 = new Vec3(var10, var12, var14);
      float var17 = MathHelper.cos(-var9 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var18 = MathHelper.sin(-var9 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var19 = -MathHelper.cos(-var8 * (float) (Math.PI / 180.0));
      float var20 = MathHelper.sin(-var8 * (float) (Math.PI / 180.0));
      float var21 = var18 * var19;
      float var22 = var17 * var19;
      Vec3 var25 = var16.addVector(var21 * 5.0, var20 * 5.0, var22 * 5.0);
      MovingObjectPosition var26 = world.rayTraceBlocks(var16, var25, true);
      if (var26 != null) {
         Vec3 var27 = entityPlayer.getLook(1.0F);
         boolean var28 = false;

         for (Entity var32 : world.getEntitiesWithinAABBExcludingEntity(
            entityPlayer, entityPlayer.getEntityBoundingBox().addCoord(var27.xCoord * 5.0, var27.yCoord * 5.0, var27.zCoord * 5.0).expand(1.0, 1.0, 1.0)
         )) {
            if (var32.canBeCollidedWith()) {
               float var33 = var32.getCollisionBorderSize();
               AxisAlignedBB var34 = var32.getEntityBoundingBox().expand(var33, var33, var33);
               if (var34.isVecInside(var16)) {
                  var28 = true;
               }
            }
         }

         if (!var28 && var26.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos var35 = var26.getBlockPos();
            if (world.getBlockState(var35).getBlock() == Blocks.snow_layer) {
               var35 = var35.down();
            }

            EntityBoat var36 = new EntityBoat(world, var35.getX() + 0.5F, var35.getY() + 1.0F, var35.getZ() + 0.5F);
            var36.rotationYaw = ((MathHelper.floor_double(entityPlayer.rotationYaw * 4.0F / 360.0F + 0.5) & 3) - 1) * 90;
            if (!world.getCollidingBoundingBoxes(var36, var36.getEntityBoundingBox().expand(-0.1, -0.1, -0.1)).isEmpty()) {
               return itemStack;
            }

            if (!world.isRemote) {
               world.spawnEntityInWorld(var36);
            }

            if (!entityPlayer.capabilities.isCreativeMode) {
               itemStack.stackSize--;
            }

            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
         }
      }

      return itemStack;
   }
}
