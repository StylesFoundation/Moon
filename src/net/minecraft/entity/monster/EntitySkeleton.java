package net.minecraft.entity.monster;

import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

public class EntitySkeleton extends EntityMob implements IRangedAttackMob {
   private final EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0, 20, 60, 15.0F);
   private final EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2, false);

   public EntitySkeleton(World world) {
      super(world);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIRestrictSun(this));
      this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0));
      this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityWolf.class, 6.0F, 1.0, 1.2));
      this.tasks.addTask(4, new EntityAIWander(this, 1.0));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(6, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
      this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
      if (world != null && !world.isRemote) {
         this.setCombatTask();
      }
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(13, (byte)0);
   }

   @Override
   protected String getLivingSound() {
      return "mob.skeleton.say";
   }

   @Override
   protected String getHurtSound() {
      return "mob.skeleton.hurt";
   }

   @Override
   protected String getDeathSound() {
      return "mob.skeleton.death";
   }

   @Override
   protected void playStepSound(BlockPos blockPos, Block block) {
      this.playSound("mob.skeleton.step", 0.15F, 1.0F);
   }

   @Override
   public boolean attackEntityAsMob(Entity entity) {
      if (super.attackEntityAsMob(entity)) {
         if (this.getSkeletonType() == 1 && entity instanceof EntityLivingBase) {
            ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEAD;
   }

   @Override
   public void onLivingUpdate() {
      if (this.worldObj.isDaytime() && !this.worldObj.isRemote) {
         float var4 = this.getBrightness(1.0F);
         BlockPos var5 = new BlockPos(this.posX, Math.round(this.posY), this.posZ);
         if (var4 > 0.5F && this.rand.nextFloat() * 30.0F < (var4 - 0.4F) * 2.0F && this.worldObj.canSeeSky(var5)) {
            boolean var6 = true;
            ItemStack var7 = this.getEquipmentInSlot(4);
            if (var7 != null) {
               if (var7.isItemStackDamageable()) {
                  var7.setItemDamage(var7.getItemDamage() + this.rand.nextInt(2));
                  if (var7.getItemDamage() >= var7.getMaxDamage()) {
                     this.renderBrokenItemStack(var7);
                     this.setCurrentItemOrArmor(4, null);
                  }
               }

               var6 = false;
            }

            if (var6) {
               this.setFire(8);
            }
         }
      }

      if (this.worldObj.isRemote && this.getSkeletonType() == 1) {
         this.setSize(0.72F, 2.535F);
      }

      super.onLivingUpdate();
   }

   @Override
   public void updateRidden() {
      super.updateRidden();
      if (this.ridingEntity instanceof EntityCreature var4) {
         this.renderYawOffset = var4.renderYawOffset;
      }
   }

   @Override
   public void onDeath(DamageSource damageSource) {
      super.onDeath(damageSource);
      if (damageSource.getSourceOfDamage() instanceof EntityArrow && damageSource.getEntity() instanceof EntityPlayer var5) {
         double var10 = var5.posX - this.posX;
         double var8 = var5.posZ - this.posZ;
         if (var10 * var10 + var8 * var8 >= 2500.0) {
            var5.triggerAchievement(AchievementList.snipeSkeleton);
         }
      } else if (damageSource.getEntity() instanceof EntityCreeper
         && ((EntityCreeper)damageSource.getEntity()).getPowered()
         && ((EntityCreeper)damageSource.getEntity()).isAIEnabled()) {
         ((EntityCreeper)damageSource.getEntity()).func_175493_co();
         this.entityDropItem(new ItemStack(Items.skull, 1, this.getSkeletonType() == 1 ? 1 : 0), 0.0F);
      }
   }

   @Override
   protected Item getDropItem() {
      return Items.arrow;
   }

   @Override
   protected void dropFewItems(boolean boolean1, int integer) {
      if (this.getSkeletonType() == 1) {
         int var6 = this.rand.nextInt(3 + integer) - 1;

         for (int var7 = 0; var7 < var6; var7++) {
            this.dropItem(Items.coal, 1);
         }
      } else {
         int var8 = this.rand.nextInt(3 + integer);

         for (int var10 = 0; var10 < var8; var10++) {
            this.dropItem(Items.arrow, 1);
         }
      }

      int var9 = this.rand.nextInt(3 + integer);

      for (int var11 = 0; var11 < var9; var11++) {
         this.dropItem(Items.bone, 1);
      }
   }

   @Override
   protected void addRandomDrop() {
      if (this.getSkeletonType() == 1) {
         this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
      }
   }

   @Override
   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
      super.setEquipmentBasedOnDifficulty(difficultyInstance);
      this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
   }

   @Override
   public IEntityLivingData onInitialSpawn(DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
      iEntityLivingData = super.onInitialSpawn(difficultyInstance, iEntityLivingData);
      if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0) {
         this.tasks.addTask(4, this.aiAttackOnCollide);
         this.setSkeletonType(1);
         this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
         this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
      } else {
         this.tasks.addTask(4, this.aiArrowAttack);
         this.setEquipmentBasedOnDifficulty(difficultyInstance);
         this.setEnchantmentBasedOnDifficulty(difficultyInstance);
      }

      this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficultyInstance.getClampedAdditionalDifficulty());
      if (this.getEquipmentInSlot(4) == null) {
         Calendar var6 = this.worldObj.getCurrentDate();
         if (var6.get(2) + 1 == 10 && var6.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
            this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
            this.equipmentDropChances[4] = 0.0F;
         }
      }

      return iEntityLivingData;
   }

   public void setCombatTask() {
      this.tasks.removeTask(this.aiAttackOnCollide);
      this.tasks.removeTask(this.aiArrowAttack);
      ItemStack var4 = this.getHeldItem();
      if (var4 != null && var4.getItem() == Items.bow) {
         this.tasks.addTask(4, this.aiArrowAttack);
      } else {
         this.tasks.addTask(4, this.aiAttackOnCollide);
      }
   }

   @Override
   public void attackEntityWithRangedAttack(EntityLivingBase entityLivingBase, float float2) {
      EntityArrow var6 = new EntityArrow(this.worldObj, this, entityLivingBase, 1.6F, 14 - this.worldObj.getDifficulty().getDifficultyId() * 4);
      int var7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
      int var8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
      var6.setDamage(float2 * 2.0F + this.rand.nextGaussian() * 0.25 + this.worldObj.getDifficulty().getDifficultyId() * 0.11F);
      if (var7 > 0) {
         var6.setDamage(var6.getDamage() + var7 * 0.5 + 0.5);
      }

      if (var8 > 0) {
         var6.setKnockbackStrength(var8);
      }

      if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1) {
         var6.setFire(100);
      }

      this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
      this.worldObj.spawnEntityInWorld(var6);
   }

   public int getSkeletonType() {
      return this.dataWatcher.getWatchableObjectByte(13);
   }

   public void setSkeletonType(int integer) {
      this.dataWatcher.updateObject(13, (byte)integer);
      this.isImmuneToFire = integer == 1;
      if (integer == 1) {
         this.setSize(0.72F, 2.535F);
      } else {
         this.setSize(0.6F, 1.95F);
      }
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      if (nBTTagCompound.hasKey("SkeletonType", 99)) {
         byte var5 = nBTTagCompound.getByte("SkeletonType");
         this.setSkeletonType(var5);
      }

      this.setCombatTask();
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setByte("SkeletonType", (byte)this.getSkeletonType());
   }

   @Override
   public void setCurrentItemOrArmor(int integer, ItemStack itemStack) {
      super.setCurrentItemOrArmor(integer, itemStack);
      if (!this.worldObj.isRemote && integer == 0) {
         this.setCombatTask();
      }
   }

   @Override
   public float getEyeHeight() {
      return this.getSkeletonType() == 1 ? super.getEyeHeight() : 1.74F;
   }

   @Override
   public double getYOffset() {
      return this.isChild() ? 0.0 : -0.35;
   }
}
