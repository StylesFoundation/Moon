package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySmallFireball extends EntityFireball {
   public EntitySmallFireball(World world) {
      super(world);
      this.setSize(0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World world, EntityLivingBase entityLivingBase, double double3, double double4, double double5) {
      super(world, entityLivingBase, double3, double4, double5);
      this.setSize(0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, double5, double6, double7);
      this.setSize(0.3125F, 0.3125F);
   }

   @Override
   protected void onImpact(MovingObjectPosition movingObjectPosition) {
      if (!this.worldObj.isRemote) {
         if (movingObjectPosition.entityHit != null) {
            boolean var5 = movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 5.0F);
            if (var5) {
               this.applyEnchantments(this.shootingEntity, movingObjectPosition.entityHit);
               if (!movingObjectPosition.entityHit.isImmuneToFire()) {
                  movingObjectPosition.entityHit.setFire(5);
               }
            }
         } else {
            boolean var7 = true;
            if (this.shootingEntity != null && this.shootingEntity instanceof EntityLiving) {
               var7 = this.worldObj.getGameRules().getBoolean("mobGriefing");
            }

            if (var7) {
               BlockPos var6 = movingObjectPosition.getBlockPos().offset(movingObjectPosition.sideHit);
               if (this.worldObj.isAirBlock(var6)) {
                  this.worldObj.setBlockState(var6, Blocks.fire.getDefaultState());
               }
            }
         }

         this.setDead();
      }
   }

   @Override
   public boolean canBeCollidedWith() {
      return false;
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      return false;
   }
}
