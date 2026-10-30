package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySnowball extends EntityThrowable {
   public EntitySnowball(World world) {
      super(world);
   }

   public EntitySnowball(World world, EntityLivingBase entityLivingBase) {
      super(world, entityLivingBase);
   }

   public EntitySnowball(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   protected void onImpact(MovingObjectPosition movingObjectPosition) {
      if (movingObjectPosition.entityHit != null) {
         byte var5 = 0;
         if (movingObjectPosition.entityHit instanceof EntityBlaze) {
            var5 = 3;
         }

         movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), var5);
      }

      for (int var6 = 0; var6 < 8; var6++) {
         this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
      }

      if (!this.worldObj.isRemote) {
         this.setDead();
      }
   }
}
