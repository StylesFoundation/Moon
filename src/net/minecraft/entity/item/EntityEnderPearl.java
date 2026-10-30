package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEnderPearl extends EntityThrowable {
   private EntityLivingBase field_181555_c;

   public EntityEnderPearl(World world) {
      super(world);
   }

   public EntityEnderPearl(World world, EntityLivingBase entityLivingBase) {
      super(world, entityLivingBase);
      this.field_181555_c = entityLivingBase;
   }

   public EntityEnderPearl(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   protected void onImpact(MovingObjectPosition movingObjectPosition) {
      EntityLivingBase var5 = this.getThrower();
      if (movingObjectPosition.entityHit != null) {
         if (movingObjectPosition.entityHit == this.field_181555_c) {
            return;
         }

         movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, var5), 0.0F);
      }

      for (int var6 = 0; var6 < 32; var6++) {
         this.worldObj
            .spawnParticle(
               EnumParticleTypes.PORTAL,
               this.posX,
               this.posY + this.rand.nextDouble() * 2.0,
               this.posZ,
               this.rand.nextGaussian(),
               0.0,
               this.rand.nextGaussian()
            );
      }

      if (!this.worldObj.isRemote) {
         if (var5 instanceof EntityPlayerMP var8) {
            if (var8.playerNetServerHandler.getNetworkManager().isChannelOpen() && var8.worldObj == this.worldObj && !var8.isPlayerSleeping()) {
               if (this.rand.nextFloat() < 0.05F && this.worldObj.getGameRules().getBoolean("doMobSpawning")) {
                  EntityEndermite var7 = new EntityEndermite(this.worldObj);
                  var7.setSpawnedByPlayer(true);
                  var7.setLocationAndAngles(var5.posX, var5.posY, var5.posZ, var5.rotationYaw, var5.rotationPitch);
                  this.worldObj.spawnEntityInWorld(var7);
               }

               if (var5.isRiding()) {
                  var5.mountEntity(null);
               }

               var5.setPositionAndUpdate(this.posX, this.posY, this.posZ);
               var5.fallDistance = 0.0F;
               var5.attackEntityFrom(DamageSource.fall, 5.0F);
            }
         } else if (var5 != null) {
            var5.setPositionAndUpdate(this.posX, this.posY, this.posZ);
            var5.fallDistance = 0.0F;
         }

         this.setDead();
      }
   }

   @Override
   public void onUpdate() {
      EntityLivingBase var4 = this.getThrower();
      if (var4 instanceof EntityPlayer && !var4.isEntityAlive()) {
         this.setDead();
      } else {
         super.onUpdate();
      }
   }
}
