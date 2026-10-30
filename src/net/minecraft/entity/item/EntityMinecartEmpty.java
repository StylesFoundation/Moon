package net.minecraft.entity.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMinecartEmpty extends EntityMinecart {
   public EntityMinecartEmpty(World world) {
      super(world);
   }

   public EntityMinecartEmpty(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   public boolean interactFirst(EntityPlayer entityPlayer) {
      if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityPlayer) {
         return true;
      }

      if (this.riddenByEntity != null && this.riddenByEntity != entityPlayer) {
         return false;
      }

      if (!this.worldObj.isRemote) {
         entityPlayer.mountEntity(this);
      }

      return true;
   }

   @Override
   public void onActivatorRailPass(int integer1, int integer2, int integer3, boolean boolean4) {
      if (boolean4) {
         if (this.riddenByEntity != null) {
            this.riddenByEntity.mountEntity(null);
         }

         if (this.getRollingAmplitude() == 0) {
            this.setRollingDirection(-this.getRollingDirection());
            this.setRollingAmplitude(10);
            this.setDamage(50.0F);
            this.setBeenAttacked();
         }
      }
   }

   @Override
   public EntityMinecart.EnumMinecartType getMinecartType() {
      return EntityMinecart.EnumMinecartType.RIDEABLE;
   }
}
