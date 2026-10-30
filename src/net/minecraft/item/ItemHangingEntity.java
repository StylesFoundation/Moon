package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemHangingEntity extends Item {
   private final Class<? extends EntityHanging> hangingEntityClass;

   public ItemHangingEntity(Class<? extends EntityHanging> class1) {
      this.hangingEntityClass = class1;
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (enumFacing == EnumFacing.DOWN) {
         return false;
      }

      if (enumFacing == EnumFacing.UP) {
         return false;
      }

      BlockPos var12 = blockPos.offset(enumFacing);
      if (!entityPlayer.canPlayerEdit(var12, enumFacing, itemStack)) {
         return false;
      }

      EntityHanging var13 = this.createEntity(world, var12, enumFacing);
      if (var13 != null && var13.onValidSurface()) {
         if (!world.isRemote) {
            world.spawnEntityInWorld(var13);
         }

         itemStack.stackSize--;
      }

      return true;
   }

   private EntityHanging createEntity(World world, BlockPos blockPos, EnumFacing enumFacing) {
      return this.hangingEntityClass == EntityPainting.class
         ? new EntityPainting(world, blockPos, enumFacing)
         : (this.hangingEntityClass == EntityItemFrame.class ? new EntityItemFrame(world, blockPos, enumFacing) : null);
   }
}
