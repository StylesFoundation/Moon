package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemLead extends Item {
   public ItemLead() {
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      Block var12 = world.getBlockState(blockPos).getBlock();
      if (var12 instanceof BlockFence) {
         if (!world.isRemote) {
            attachToFence(entityPlayer, world, blockPos);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean attachToFence(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
      EntityLeashKnot var6 = EntityLeashKnot.getKnotForPosition(world, blockPos);
      boolean var7 = false;
      int var10 = blockPos.getX();
      int var11 = blockPos.getY();
      int var12 = blockPos.getZ();

      for (EntityLiving var14 : world.getEntitiesWithinAABB(
         EntityLiving.class, new AxisAlignedBB(var10 - 7.0, var11 - 7.0, var12 - 7.0, var10 + 7.0, var11 + 7.0, var12 + 7.0)
      )) {
         if (var14.getLeashed() && var14.getLeashedToEntity() == entityPlayer) {
            if (var6 == null) {
               var6 = EntityLeashKnot.createKnot(world, blockPos);
            }

            var14.setLeashedToEntity(var6, true);
            var7 = true;
         }
      }

      return var7;
   }
}
