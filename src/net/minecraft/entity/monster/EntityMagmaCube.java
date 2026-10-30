package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMagmaCube extends EntitySlime {
   public EntityMagmaCube(World world) {
      super(world);
      this.isImmuneToFire = true;
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2F);
   }

   @Override
   public boolean getCanSpawnHere() {
      return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
   }

   @Override
   public boolean isNotColliding() {
      return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this)
         && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()
         && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
   }

   @Override
   public int getTotalArmorValue() {
      return this.getSlimeSize() * 3;
   }

   @Override
   public int getBrightnessForRender(float float1) {
      return 15728880;
   }

   @Override
   public float getBrightness(float float1) {
      return 1.0F;
   }

   @Override
   protected EnumParticleTypes getParticleType() {
      return EnumParticleTypes.FLAME;
   }

   @Override
   protected EntitySlime createInstance() {
      return new EntityMagmaCube(this.worldObj);
   }

   @Override
   protected Item getDropItem() {
      return Items.magma_cream;
   }

   @Override
   protected void dropFewItems(boolean boolean1, int integer) {
      Item var6 = this.getDropItem();
      if (var6 != null && this.getSlimeSize() > 1) {
         int var7 = this.rand.nextInt(4) - 2;
         if (integer > 0) {
            var7 += this.rand.nextInt(integer + 1);
         }

         for (int var8 = 0; var8 < var7; var8++) {
            this.dropItem(var6, 1);
         }
      }
   }

   @Override
   public boolean isBurning() {
      return false;
   }

   @Override
   protected int getJumpDelay() {
      return super.getJumpDelay() * 4;
   }

   @Override
   protected void alterSquishAmount() {
      this.squishAmount *= 0.9F;
   }

   @Override
   protected void jump() {
      this.motionY = 0.42F + this.getSlimeSize() * 0.1F;
      this.isAirBorne = true;
   }

   @Override
   protected void handleJumpLava() {
      this.motionY = 0.22F + this.getSlimeSize() * 0.05F;
      this.isAirBorne = true;
   }

   @Override
   public void fall(float float1, float float2) {
   }

   @Override
   protected boolean canDamagePlayer() {
      return true;
   }

   @Override
   protected int getAttackStrength() {
      return super.getAttackStrength() + 2;
   }

   @Override
   protected String getJumpSound() {
      return this.getSlimeSize() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
   }

   @Override
   protected boolean makesSoundOnLand() {
      return true;
   }
}
