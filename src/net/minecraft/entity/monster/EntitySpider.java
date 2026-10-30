package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySpider extends EntityMob {
   public EntitySpider(World world) {
      super(world);
      this.setSize(1.4F, 0.9F);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
      this.tasks.addTask(4, new EntitySpider.AISpiderAttack(EntityPlayer.class));
      this.tasks.addTask(4, new EntitySpider.AISpiderAttack(EntityIronGolem.class));
      this.tasks.addTask(5, new EntityAIWander(this, 0.8));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(6, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
      this.targetTasks.addTask(2, new EntitySpider.AISpiderTarget(EntityPlayer.class));
      this.targetTasks.addTask(3, new EntitySpider.AISpiderTarget(EntityIronGolem.class));
   }

   @Override
   public double getMountedYOffset() {
      return this.height * 0.5F;
   }

   @Override
   protected PathNavigate getNewNavigator(World world) {
      return new PathNavigateClimber(this, world);
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (byte)0);
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.worldObj.isRemote) {
         this.setBesideClimbableBlock(this.isCollidedHorizontally);
      }
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
   }

   @Override
   protected String getLivingSound() {
      return "mob.spider.say";
   }

   @Override
   protected String getHurtSound() {
      return "mob.spider.say";
   }

   @Override
   protected String getDeathSound() {
      return "mob.spider.death";
   }

   @Override
   protected void playStepSound(BlockPos blockPos, Block block) {
      this.playSound("mob.spider.step", 0.15F, 1.0F);
   }

   @Override
   protected Item getDropItem() {
      return Items.string;
   }

   @Override
   protected void dropFewItems(boolean boolean1, int integer) {
      super.dropFewItems(boolean1, integer);
      if (boolean1 && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + integer) > 0)) {
         this.dropItem(Items.spider_eye, 1);
      }
   }

   @Override
   public boolean isOnLadder() {
      return this.isBesideClimbableBlock();
   }

   @Override
   public void setInWeb() {
   }

   @Override
   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.ARTHROPOD;
   }

   @Override
   public boolean isPotionApplicable(PotionEffect potionEffect) {
      return potionEffect.getPotionID() != Potion.poison.id && super.isPotionApplicable(potionEffect);
   }

   public boolean isBesideClimbableBlock() {
      return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
   }

   public void setBesideClimbableBlock(boolean boolean1) {
      byte var5 = this.dataWatcher.getWatchableObjectByte(16);
      if (boolean1) {
         var5 = (byte)(var5 | 1);
      } else {
         var5 = (byte)(var5 & -2);
      }

      this.dataWatcher.updateObject(16, var5);
   }

   @Override
   public IEntityLivingData onInitialSpawn(DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
      iEntityLivingData = super.onInitialSpawn(difficultyInstance, iEntityLivingData);
      if (this.worldObj.rand.nextInt(100) == 0) {
         EntitySkeleton var6 = new EntitySkeleton(this.worldObj);
         var6.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
         var6.onInitialSpawn(difficultyInstance, null);
         this.worldObj.spawnEntityInWorld(var6);
         var6.mountEntity(this);
      }

      if (iEntityLivingData == null) {
         iEntityLivingData = new EntitySpider.GroupData();
         if (this.worldObj.getDifficulty() == EnumDifficulty.HARD
            && this.worldObj.rand.nextFloat() < 0.1F * difficultyInstance.getClampedAdditionalDifficulty()) {
            ((EntitySpider.GroupData)iEntityLivingData).func_111104_a(this.worldObj.rand);
         }
      }

      if (iEntityLivingData instanceof EntitySpider.GroupData) {
         int var8 = ((EntitySpider.GroupData)iEntityLivingData).potionEffectId;
         if (var8 > 0 && Potion.potionTypes[var8] != null) {
            this.addPotionEffect(new PotionEffect(var8, Integer.MAX_VALUE));
         }
      }

      return iEntityLivingData;
   }

   @Override
   public float getEyeHeight() {
      return 0.65F;
   }

   class AISpiderAttack extends EntityAIAttackOnCollide {
      public AISpiderAttack(Class<? extends Entity> class2) {
         super(EntitySpider.this, class2, 1.0, true);
      }

      @Override
      public boolean continueExecuting() {
         float var4 = this.attacker.getBrightness(1.0F);
         if (var4 >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
            this.attacker.setAttackTarget(null);
            return false;
         } else {
            return super.continueExecuting();
         }
      }

      @Override
      protected double func_179512_a(EntityLivingBase entityLivingBase) {
         return 4.0F + entityLivingBase.width;
      }
   }

   class AISpiderTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget {
      public AISpiderTarget(Class<T> class2) {
         super(EntitySpider.this, class2, true);
      }

      @Override
      public boolean shouldExecute() {
         float var4 = this.taskOwner.getBrightness(1.0F);
         return !(var4 >= 0.5F) && super.shouldExecute();
      }
   }

   public static class GroupData implements IEntityLivingData {
      public int potionEffectId;

      public void func_111104_a(Random random) {
         int var5 = random.nextInt(5);
         if (var5 <= 1) {
            this.potionEffectId = Potion.moveSpeed.id;
         } else if (var5 == 2) {
            this.potionEffectId = Potion.damageBoost.id;
         } else if (var5 == 3) {
            this.potionEffectId = Potion.regeneration.id;
         } else if (var5 <= 4) {
            this.potionEffectId = Potion.invisibility.id;
         }
      }
   }
}
