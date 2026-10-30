package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityLargeFireball extends EntityFireball {
   public int explosionPower = 1;

   public EntityLargeFireball(World world) {
      super(world);
   }

   public EntityLargeFireball(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, double5, double6, double7);
   }

   public EntityLargeFireball(World world, EntityLivingBase entityLivingBase, double double3, double double4, double double5) {
      super(world, entityLivingBase, double3, double4, double5);
   }

   @Override
   protected void onImpact(MovingObjectPosition movingObjectPosition) {
      if (!this.worldObj.isRemote) {
         if (movingObjectPosition.entityHit != null) {
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0F);
            this.applyEnchantments(this.shootingEntity, movingObjectPosition.entityHit);
         }

         boolean var5 = this.worldObj.getGameRules().getBoolean("mobGriefing");
         this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, this.explosionPower, var5, var5);
         this.setDead();
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setInteger("ExplosionPower", this.explosionPower);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      if (nBTTagCompound.hasKey("ExplosionPower", 99)) {
         this.explosionPower = nBTTagCompound.getInteger("ExplosionPower");
      }
   }
}
