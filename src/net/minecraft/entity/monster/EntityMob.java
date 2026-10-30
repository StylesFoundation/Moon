package net.minecraft.entity.monster;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public abstract class EntityMob extends EntityCreature implements IMob {
   public EntityMob(World world) {
      super(world);
      this.experienceValue = 5;
   }

   @Override
   public void onLivingUpdate() {
      this.updateArmSwingProgress();
      float var4 = this.getBrightness(1.0F);
      if (var4 > 0.5F) {
         this.entityAge += 2;
      }

      super.onLivingUpdate();
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
         this.setDead();
      }
   }

   @Override
   protected String getSwimSound() {
      return "game.hostile.swim";
   }

   @Override
   protected String getSplashSound() {
      return "game.hostile.swim.splash";
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      } else if (super.attackEntityFrom(damageSource, float2)) {
         damageSource.getEntity();
         return true;
      } else {
         return false;
      }
   }

   @Override
   protected String getHurtSound() {
      return "game.hostile.hurt";
   }

   @Override
   protected String getDeathSound() {
      return "game.hostile.die";
   }

   @Override
   protected String getFallSoundString(int integer) {
      return integer > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
   }

   @Override
   public boolean attackEntityAsMob(Entity entity) {
      float var5 = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
      int var6 = 0;
      if (entity instanceof EntityLivingBase) {
         var5 += EnchantmentHelper.getModifierForCreature(this.getHeldItem(), ((EntityLivingBase)entity).getCreatureAttribute());
         var6 = 0 + EnchantmentHelper.getKnockbackModifier(this);
      }

      boolean var7 = entity.attackEntityFrom(DamageSource.causeMobDamage(this), var5);
      if (var7) {
         if (var6 > 0) {
            entity.addVelocity(
               -MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * var6 * 0.5F,
               0.1,
               MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * var6 * 0.5F
            );
            this.motionX *= 0.6;
            this.motionZ *= 0.6;
         }

         int var8 = EnchantmentHelper.getFireAspectModifier(this);
         if (var8 > 0) {
            entity.setFire(var8 * 4);
         }

         this.applyEnchantments(this, entity);
      }

      return var7;
   }

   @Override
   public float getBlockPathWeight(BlockPos blockPos) {
      return 0.5F - this.worldObj.getLightBrightness(blockPos);
   }

   protected boolean isValidLightLevel() {
      BlockPos var4 = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
      if (this.worldObj.getLightFor(EnumSkyBlock.SKY, var4) > this.rand.nextInt(32)) {
         return false;
      }

      int var5 = this.worldObj.getLightFromNeighbors(var4);
      if (this.worldObj.isThundering()) {
         int var6 = this.worldObj.getSkylightSubtracted();
         this.worldObj.setSkylightSubtracted(10);
         var5 = this.worldObj.getLightFromNeighbors(var4);
         this.worldObj.setSkylightSubtracted(var6);
      }

      return var5 <= this.rand.nextInt(8);
   }

   @Override
   public boolean getCanSpawnHere() {
      return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere();
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
   }

   @Override
   protected boolean canDropLoot() {
      return true;
   }

   public static int Method447() {
      return 0;
   }
}
