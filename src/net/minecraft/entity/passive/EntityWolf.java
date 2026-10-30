package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWolf extends EntityTameable {
   private float headRotationCourse;
   private float headRotationCourseOld;
   private boolean isWet;
   private boolean isShaking;
   private float timeWolfIsShaking;
   private float prevTimeWolfIsShaking;

   public EntityWolf(World world) {
      super(world);
      this.setSize(0.6F, 0.8F);
      ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, this.aiSit);
      this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
      this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, true));
      this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0F, 2.0F));
      this.tasks.addTask(6, new EntityAIMate(this, 1.0));
      this.tasks.addTask(7, new EntityAIWander(this, 1.0));
      this.tasks.addTask(8, new EntityAIBeg(this, 8.0F));
      this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(9, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
      this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
      this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
      this.targetTasks
         .addTask(4, new EntityAITargetNonTamed<>(this, EntityAnimal.class, false, entity -> entity instanceof EntitySheep || entity instanceof EntityRabbit));
      this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<>(this, EntitySkeleton.class, false));
      this.setTamed(false);
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
      if (this.isTamed()) {
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
      } else {
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
      }

      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
   }

   @Override
   public void setAttackTarget(EntityLivingBase entityLivingBase) {
      super.setAttackTarget(entityLivingBase);
      if (entityLivingBase == null) {
         this.setAngry(false);
      } else if (!this.isTamed()) {
         this.setAngry(true);
      }
   }

   @Override
   protected void updateAITasks() {
      this.dataWatcher.updateObject(18, this.getHealth());
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(18, this.getHealth());
      this.dataWatcher.addObject(19, (byte)0);
      this.dataWatcher.addObject(20, (byte)EnumDyeColor.RED.getMetadata());
   }

   @Override
   protected void playStepSound(BlockPos blockPos, Block block) {
      this.playSound("mob.wolf.step", 0.15F, 1.0F);
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setBoolean("Angry", this.isAngry());
      nBTTagCompound.setByte("CollarColor", (byte)this.getCollarColor().getDyeDamage());
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.setAngry(nBTTagCompound.getBoolean("Angry"));
      if (nBTTagCompound.hasKey("CollarColor", 99)) {
         this.setCollarColor(EnumDyeColor.byDyeDamage(nBTTagCompound.getByte("CollarColor")));
      }
   }

   @Override
   protected String getLivingSound() {
      return this.isAngry()
         ? "mob.wolf.growl"
         : (
            this.rand.nextInt(3) == 0
               ? (this.isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0F ? "mob.wolf.whine" : "mob.wolf.panting")
               : "mob.wolf.bark"
         );
   }

   @Override
   protected String getHurtSound() {
      return "mob.wolf.hurt";
   }

   @Override
   protected String getDeathSound() {
      return "mob.wolf.death";
   }

   @Override
   protected float getSoundVolume() {
      return 0.4F;
   }

   @Override
   protected Item getDropItem() {
      return Item.getItemById(-1);
   }

   @Override
   public void onLivingUpdate() {
      super.onLivingUpdate();
      if (!this.worldObj.isRemote && this.isWet && !this.isShaking && !this.hasPath() && this.onGround) {
         this.isShaking = true;
         this.timeWolfIsShaking = 0.0F;
         this.prevTimeWolfIsShaking = 0.0F;
         this.worldObj.setEntityState(this, (byte)8);
      }

      if (!this.worldObj.isRemote && this.getAttackTarget() == null && this.isAngry()) {
         this.setAngry(false);
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      this.headRotationCourseOld = this.headRotationCourse;
      if (this.isBegging()) {
         this.headRotationCourse = this.headRotationCourse + (1.0F - this.headRotationCourse) * 0.4F;
      } else {
         this.headRotationCourse = this.headRotationCourse + (0.0F - this.headRotationCourse) * 0.4F;
      }

      if (this.isWet()) {
         this.isWet = true;
         this.isShaking = false;
         this.timeWolfIsShaking = 0.0F;
         this.prevTimeWolfIsShaking = 0.0F;
      } else if ((this.isWet || this.isShaking) && this.isShaking) {
         if (this.timeWolfIsShaking == 0.0F) {
            this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         }

         this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
         this.timeWolfIsShaking += 0.05F;
         if (this.prevTimeWolfIsShaking >= 2.0F) {
            this.isWet = false;
            this.isShaking = false;
            this.prevTimeWolfIsShaking = 0.0F;
            this.timeWolfIsShaking = 0.0F;
         }

         if (this.timeWolfIsShaking > 0.4F) {
            float var4 = (float)this.getEntityBoundingBox().minY;
            int var5 = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7.0F);

            for (int var6 = 0; var6 < var5; var6++) {
               float var7 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
               float var8 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
               this.worldObj
                  .spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + var7, var4 + 0.8F, this.posZ + var8, this.motionX, this.motionY, this.motionZ);
            }
         }
      }
   }

   public boolean isWolfWet() {
      return this.isWet;
   }

   public float getShadingWhileWet(float float1) {
      return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * float1) / 2.0F * 0.25F;
   }

   public float getShakeAngle(float float1, float float2) {
      float var6 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * float1 + float2) / 1.8F;
      if (var6 < 0.0F) {
         var6 = 0.0F;
      } else if (var6 > 1.0F) {
         var6 = 1.0F;
      }

      return MathHelper.sin(var6 * (float) Math.PI) * MathHelper.sin(var6 * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
   }

   public float getInterestedAngle(float float1) {
      return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * float1) * 0.15F * (float) Math.PI;
   }

   @Override
   public float getEyeHeight() {
      return this.height * 0.8F;
   }

   @Override
   public int getVerticalFaceSpeed() {
      return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      Entity var6 = damageSource.getEntity();
      this.aiSit.setSitting(false);
      if (var6 != null && !(var6 instanceof EntityPlayer) && !(var6 instanceof EntityArrow)) {
         float2 = (float2 + 1.0F) / 2.0F;
      }

      return super.attackEntityFrom(damageSource, float2);
   }

   @Override
   public boolean attackEntityAsMob(Entity entity) {
      boolean var5 = entity.attackEntityFrom(
         DamageSource.causeMobDamage(this), (int)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue()
      );
      if (var5) {
         this.applyEnchantments(this, entity);
      }

      return var5;
   }

   @Override
   public void setTamed(boolean boolean1) {
      super.setTamed(boolean1);
      if (boolean1) {
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
      } else {
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
      }

      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
   }

   @Override
   public boolean interact(EntityPlayer entityPlayer) {
      ItemStack var5 = entityPlayer.inventory.getCurrentItem();
      if (this.isTamed()) {
         if (var5 != null) {
            if (var5.getItem() instanceof ItemFood var6) {
               if (var6.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F) {
                  if (!entityPlayer.capabilities.isCreativeMode) {
                     var5.stackSize--;
                  }

                  this.heal(var6.getHealAmount(var5));
                  if (var5.stackSize <= 0) {
                     entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                  }

                  return true;
               }
            } else if (var5.getItem() == Items.dye) {
               EnumDyeColor var8 = EnumDyeColor.byDyeDamage(var5.getMetadata());
               if (var8 != this.getCollarColor()) {
                  this.setCollarColor(var8);
                  if (!entityPlayer.capabilities.isCreativeMode && --var5.stackSize <= 0) {
                     entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                  }

                  return true;
               }
            }
         }

         if (this.isOwner(entityPlayer) && !this.worldObj.isRemote && !this.isBreedingItem(var5)) {
            this.aiSit.setSitting(!this.isSitting());
            this.isJumping = false;
            this.navigator.clearPathEntity();
            this.setAttackTarget(null);
         }
      } else if (var5 != null && var5.getItem() == Items.bone && !this.isAngry()) {
         if (!entityPlayer.capabilities.isCreativeMode) {
            var5.stackSize--;
         }

         if (var5.stackSize <= 0) {
            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
         }

         if (!this.worldObj.isRemote) {
            if (this.rand.nextInt(3) == 0) {
               this.setTamed(true);
               this.navigator.clearPathEntity();
               this.setAttackTarget(null);
               this.aiSit.setSitting(true);
               this.setHealth(20.0F);
               this.setOwnerId(entityPlayer.getUniqueID().toString());
               this.playTameEffect(true);
               this.worldObj.setEntityState(this, (byte)7);
            } else {
               this.playTameEffect(false);
               this.worldObj.setEntityState(this, (byte)6);
            }
         }

         return true;
      }

      return super.interact(entityPlayer);
   }

   @Override
   public void handleStatusUpdate(byte byte1) {
      if (byte1 == 8) {
         this.isShaking = true;
         this.timeWolfIsShaking = 0.0F;
         this.prevTimeWolfIsShaking = 0.0F;
      } else {
         super.handleStatusUpdate(byte1);
      }
   }

   public float getTailRotation() {
      return this.isAngry()
         ? 1.5393804F
         : (this.isTamed() ? (0.55F - (20.0F - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02F) * (float) Math.PI : (float) (Math.PI / 5));
   }

   @Override
   public boolean isBreedingItem(ItemStack itemStack) {
      return itemStack != null && itemStack.getItem() instanceof ItemFood && ((ItemFood)itemStack.getItem()).isWolfsFavoriteMeat();
   }

   @Override
   public int getMaxSpawnedInChunk() {
      return 8;
   }

   public boolean isAngry() {
      return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
   }

   public void setAngry(boolean boolean1) {
      byte var5 = this.dataWatcher.getWatchableObjectByte(16);
      if (boolean1) {
         this.dataWatcher.updateObject(16, (byte)(var5 | 2));
      } else {
         this.dataWatcher.updateObject(16, (byte)(var5 & -3));
      }
   }

   public EnumDyeColor getCollarColor() {
      return EnumDyeColor.byDyeDamage(this.dataWatcher.getWatchableObjectByte(20) & 15);
   }

   public void setCollarColor(EnumDyeColor enumDyeColor) {
      this.dataWatcher.updateObject(20, (byte)(enumDyeColor.getDyeDamage() & 15));
   }

   public EntityWolf createChild(EntityAgeable entityAgeable) {
      EntityWolf var5 = new EntityWolf(this.worldObj);
      String var6 = this.getOwnerId();
      if (var6 != null && var6.trim().length() > 0) {
         var5.setOwnerId(var6);
         var5.setTamed(true);
      }

      return var5;
   }

   public void setBegging(boolean boolean1) {
      if (boolean1) {
         this.dataWatcher.updateObject(19, (byte)1);
      } else {
         this.dataWatcher.updateObject(19, (byte)0);
      }
   }

   @Override
   public boolean canMateWith(EntityAnimal entityAnimal) {
      if (entityAnimal == this) {
         return false;
      } else if (!this.isTamed()) {
         return false;
      } else {
         return !(entityAnimal instanceof EntityWolf var5) ? false : var5.isTamed() && !var5.isSitting() && this.isInLove() && var5.isInLove();
      }
   }

   public boolean isBegging() {
      return this.dataWatcher.getWatchableObjectByte(19) == 1;
   }

   @Override
   protected boolean canDespawn() {
      return !this.isTamed() && this.ticksExisted > 2400;
   }

   @Override
   public boolean shouldAttackEntity(EntityLivingBase entityLivingBase1, EntityLivingBase entityLivingBase2) {
      if (!(entityLivingBase1 instanceof EntityCreeper) && !(entityLivingBase1 instanceof EntityGhast)) {
         return entityLivingBase1 instanceof EntityWolf var6 && var6.isTamed() && var6.getOwner() == entityLivingBase2
            ? false
            : (
                  !(entityLivingBase1 instanceof EntityPlayer)
                     || !(entityLivingBase2 instanceof EntityPlayer)
                     || ((EntityPlayer)entityLivingBase2).canAttackPlayer((EntityPlayer)entityLivingBase1)
               )
               && (!(entityLivingBase1 instanceof EntityHorse) || !((EntityHorse)entityLivingBase1).isTame());
      } else {
         return false;
      }
   }

   @Override
   public boolean allowLeashing() {
      return !this.isAngry() && super.allowLeashing();
   }
}
