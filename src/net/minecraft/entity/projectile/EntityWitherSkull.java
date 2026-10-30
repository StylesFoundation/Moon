package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityWitherSkull extends EntityFireball {
   public EntityWitherSkull(World world) {
      super(world);
      this.setSize(0.3125F, 0.3125F);
   }

   public EntityWitherSkull(World world, EntityLivingBase entityLivingBase, double double3, double double4, double double5) {
      super(world, entityLivingBase, double3, double4, double5);
      this.setSize(0.3125F, 0.3125F);
   }

   @Override
   protected float getMotionFactor() {
      return this.isInvulnerable() ? 0.73F : super.getMotionFactor();
   }

   public EntityWitherSkull(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, double5, double6, double7);
      this.setSize(0.3125F, 0.3125F);
   }

   @Override
   public boolean isBurning() {
      return false;
   }

   @Override
   public float getExplosionResistance(Explosion explosion, World world, BlockPos blockPos, IBlockState iBlockState) {
      float var8 = super.getExplosionResistance(explosion, world, blockPos, iBlockState);
      Block var9 = iBlockState.getBlock();
      if (this.isInvulnerable() && EntityWither.canDestroyBlock(var9)) {
         var8 = Math.min(0.8F, var8);
      }

      return var8;
   }

   @Override
   protected void onImpact(MovingObjectPosition movingObjectPosition) {
      if (!this.worldObj.isRemote) {
         if (movingObjectPosition.entityHit != null) {
            if (this.shootingEntity != null) {
               if (movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0F)) {
                  if (!movingObjectPosition.entityHit.isEntityAlive()) {
                     this.shootingEntity.heal(5.0F);
                  } else {
                     this.applyEnchantments(this.shootingEntity, movingObjectPosition.entityHit);
                  }
               }
            } else {
               movingObjectPosition.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
            }

            if (movingObjectPosition.entityHit instanceof EntityLivingBase) {
               byte var5 = 0;
               if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
                  var5 = 10;
               } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                  var5 = 40;
               }

               if (var5 > 0) {
                  ((EntityLivingBase)movingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * var5, 1));
               }
            }
         }

         this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, false, this.worldObj.getGameRules().getBoolean("mobGriefing"));
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

   @Override
   protected void entityInit() {
      this.dataWatcher.addObject(10, (byte)0);
   }

   public boolean isInvulnerable() {
      return this.dataWatcher.getWatchableObjectByte(10) == 1;
   }

   public void setInvulnerable(boolean boolean1) {
      this.dataWatcher.updateObject(10, (byte)(boolean1 ? 1 : 0));
   }
}
