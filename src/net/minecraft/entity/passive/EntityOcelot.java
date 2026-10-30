package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIOcelotSit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityOcelot extends EntityTameable {
   private EntityAIAvoidEntity<EntityPlayer> avoidEntity;
   private final EntityAITempt aiTempt;

   public EntityOcelot(World world) {
      super(world);
      this.setSize(0.6F, 0.7F);
      ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, this.aiSit);
      this.tasks.addTask(3, this.aiTempt = new EntityAITempt(this, 0.6, Items.fish, true));
      this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0F, 5.0F));
      this.tasks.addTask(6, new EntityAIOcelotSit(this, 0.8));
      this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
      this.tasks.addTask(8, new EntityAIOcelotAttack(this));
      this.tasks.addTask(9, new EntityAIMate(this, 0.8));
      this.tasks.addTask(10, new EntityAIWander(this, 0.8));
      this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
      this.targetTasks.addTask(1, new EntityAITargetNonTamed<>(this, EntityChicken.class, false, null));
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(18, (byte)0);
   }

   @Override
   public void updateAITasks() {
      if (this.getMoveHelper().isUpdating()) {
         double var4 = this.getMoveHelper().getSpeed();
         if (var4 == 0.6) {
            this.setSneaking(true);
            this.setSprinting(false);
         } else if (var4 == 1.33) {
            this.setSneaking(false);
            this.setSprinting(true);
         } else {
            this.setSneaking(false);
            this.setSprinting(false);
         }
      } else {
         this.setSneaking(false);
         this.setSprinting(false);
      }
   }

   @Override
   protected boolean canDespawn() {
      return !this.isTamed() && this.ticksExisted > 2400;
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
   }

   @Override
   public void fall(float float1, float float2) {
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setInteger("CatType", this.getTameSkin());
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.setTameSkin(nBTTagCompound.getInteger("CatType"));
   }

   @Override
   protected String getLivingSound() {
      return this.isTamed() ? (this.isInLove() ? "mob.cat.purr" : (this.rand.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow")) : "";
   }

   @Override
   protected String getHurtSound() {
      return "mob.cat.hitt";
   }

   @Override
   protected String getDeathSound() {
      return "mob.cat.hitt";
   }

   @Override
   protected float getSoundVolume() {
      return 0.4F;
   }

   @Override
   protected Item getDropItem() {
      return Items.leather;
   }

   @Override
   public boolean attackEntityAsMob(Entity entity) {
      return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      this.aiSit.setSitting(false);
      return super.attackEntityFrom(damageSource, float2);
   }

   @Override
   protected void dropFewItems(boolean boolean1, int integer) {
   }

   @Override
   public boolean interact(EntityPlayer entityPlayer) {
      ItemStack var5 = entityPlayer.inventory.getCurrentItem();
      if (this.isTamed()) {
         if (this.isOwner(entityPlayer) && !this.worldObj.isRemote && !this.isBreedingItem(var5)) {
            this.aiSit.setSitting(!this.isSitting());
         }
      } else if (this.aiTempt.isRunning() && var5 != null && var5.getItem() == Items.fish && entityPlayer.getDistanceSqToEntity(this) < 9.0) {
         if (!entityPlayer.capabilities.isCreativeMode) {
            var5.stackSize--;
         }

         if (var5.stackSize <= 0) {
            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
         }

         if (!this.worldObj.isRemote) {
            if (this.rand.nextInt(3) == 0) {
               this.setTamed(true);
               this.setTameSkin(1 + this.worldObj.rand.nextInt(3));
               this.setOwnerId(entityPlayer.getUniqueID().toString());
               this.playTameEffect(true);
               this.aiSit.setSitting(true);
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

   public EntityOcelot createChild(EntityAgeable entityAgeable) {
      EntityOcelot var5 = new EntityOcelot(this.worldObj);
      if (this.isTamed()) {
         var5.setOwnerId(this.getOwnerId());
         var5.setTamed(true);
         var5.setTameSkin(this.getTameSkin());
      }

      return var5;
   }

   @Override
   public boolean isBreedingItem(ItemStack itemStack) {
      return itemStack != null && itemStack.getItem() == Items.fish;
   }

   @Override
   public boolean canMateWith(EntityAnimal entityAnimal) {
      if (entityAnimal == this) {
         return false;
      } else if (!this.isTamed()) {
         return false;
      } else {
         return !(entityAnimal instanceof EntityOcelot var5) ? false : var5.isTamed() && this.isInLove() && var5.isInLove();
      }
   }

   public int getTameSkin() {
      return this.dataWatcher.getWatchableObjectByte(18);
   }

   public void setTameSkin(int integer) {
      this.dataWatcher.updateObject(18, (byte)integer);
   }

   @Override
   public boolean getCanSpawnHere() {
      return this.worldObj.rand.nextInt(3) != 0;
   }

   @Override
   public boolean isNotColliding() {
      if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this)
         && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()
         && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox())) {
         BlockPos var4 = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
         if (var4.getY() < this.worldObj.getSeaLevel()) {
            return false;
         }

         Block var5 = this.worldObj.getBlockState(var4.down()).getBlock();
         return var5 == Blocks.grass || var5.getMaterial() == Material.leaves;
      } else {
         return false;
      }
   }

   @Override
   public String getName() {
      return this.hasCustomName() ? this.getCustomNameTag() : (this.isTamed() ? StatCollector.translateToLocal("entity.Cat.name") : super.getName());
   }

   @Override
   public void setTamed(boolean boolean1) {
      super.setTamed(boolean1);
   }

   @Override
   protected void setupTamedAI() {
      if (this.avoidEntity == null) {
         this.avoidEntity = new EntityAIAvoidEntity<>(this, EntityPlayer.class, 16.0F, 0.8, 1.33);
      }

      this.tasks.removeTask(this.avoidEntity);
      if (!this.isTamed()) {
         this.tasks.addTask(4, this.avoidEntity);
      }
   }

   @Override
   public IEntityLivingData onInitialSpawn(DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
      iEntityLivingData = super.onInitialSpawn(difficultyInstance, iEntityLivingData);
      if (this.worldObj.rand.nextInt(7) == 0) {
         for (int var6 = 0; var6 < 2; var6++) {
            EntityOcelot var7 = new EntityOcelot(this.worldObj);
            var7.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            var7.setGrowingAge(-24000);
            this.worldObj.spawnEntityInWorld(var7);
         }
      }

      return iEntityLivingData;
   }
}
