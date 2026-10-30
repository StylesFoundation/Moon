package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIDefendVillage;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityIronGolem extends EntityGolem {
   private int homeCheckTimer;
   Village villageObj;
   private int attackTimer;
   private int holdRoseTick;

   public EntityIronGolem(World world) {
      super(world);
      this.setSize(1.4F, 2.9F);
      ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
      this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0, true));
      this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9, 32.0F));
      this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6, true));
      this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0));
      this.tasks.addTask(5, new EntityAILookAtVillager(this));
      this.tasks.addTask(6, new EntityAIWander(this, 0.6));
      this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(8, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIDefendVillage(this));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
      this.targetTasks
         .addTask(3, new EntityIronGolem.AINearestAttackableTargetNonCreeper<>(this, EntityLiving.class, 10, false, true, IMob.VISIBLE_MOB_SELECTOR));
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (byte)0);
   }

   @Override
   protected void updateAITasks() {
      if (--this.homeCheckTimer <= 0) {
         this.homeCheckTimer = 70 + this.rand.nextInt(50);
         this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this), 32);
         if (this.villageObj == null) {
            this.detachHome();
         } else {
            BlockPos var4 = this.villageObj.getCenter();
            this.setHomePosAndDistance(var4, (int)(this.villageObj.getVillageRadius() * 0.6F));
         }
      }

      super.updateAITasks();
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
   }

   @Override
   protected int decreaseAirSupply(int integer) {
      return integer;
   }

   @Override
   protected void collideWithEntity(Entity entity) {
      if (entity instanceof IMob && !(entity instanceof EntityCreeper) && this.getRNG().nextInt(20) == 0) {
         this.setAttackTarget((EntityLivingBase)entity);
      }

      super.collideWithEntity(entity);
   }

   @Override
   public void onLivingUpdate() {
      super.onLivingUpdate();
      if (this.attackTimer > 0) {
         this.attackTimer--;
      }

      if (this.holdRoseTick > 0) {
         this.holdRoseTick--;
      }

      if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.5000003E-7F && this.rand.nextInt(5) == 0) {
         int var4 = MathHelper.floor_double(this.posX);
         int var5 = MathHelper.floor_double(this.posY - 0.2F);
         int var6 = MathHelper.floor_double(this.posZ);
         IBlockState var7 = this.worldObj.getBlockState(new BlockPos(var4, var5, var6));
         Block var8 = var7.getBlock();
         if (var8.getMaterial() != Material.air) {
            this.worldObj
               .spawnParticle(
                  EnumParticleTypes.BLOCK_CRACK,
                  this.posX + (this.rand.nextFloat() - 0.5) * this.width,
                  this.getEntityBoundingBox().minY + 0.1,
                  this.posZ + (this.rand.nextFloat() - 0.5) * this.width,
                  4.0 * (this.rand.nextFloat() - 0.5),
                  0.5,
                  (this.rand.nextFloat() - 0.5) * 4.0,
                  Block.getStateId(var7)
               );
         }
      }
   }

   @Override
   public boolean canAttackClass(Class<? extends EntityLivingBase> class1) {
      return (!this.isPlayerCreated() || !EntityPlayer.class.isAssignableFrom(class1)) && class1 != EntityCreeper.class && super.canAttackClass(class1);
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setBoolean("PlayerCreated", this.isPlayerCreated());
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.setPlayerCreated(nBTTagCompound.getBoolean("PlayerCreated"));
   }

   @Override
   public boolean attackEntityAsMob(Entity entity) {
      this.attackTimer = 10;
      this.worldObj.setEntityState(this, (byte)4);
      boolean var5 = entity.attackEntityFrom(DamageSource.causeMobDamage(this), 7 + this.rand.nextInt(15));
      if (var5) {
         entity.motionY += 0.4F;
         this.applyEnchantments(this, entity);
      }

      this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
      return var5;
   }

   @Override
   public void handleStatusUpdate(byte byte1) {
      if (byte1 == 4) {
         this.attackTimer = 10;
         this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
      } else if (byte1 == 11) {
         this.holdRoseTick = 400;
      } else {
         super.handleStatusUpdate(byte1);
      }
   }

   public Village getVillage() {
      return this.villageObj;
   }

   public int getAttackTimer() {
      return this.attackTimer;
   }

   public void setHoldingRose(boolean boolean1) {
      this.holdRoseTick = boolean1 ? 400 : 0;
      this.worldObj.setEntityState(this, (byte)11);
   }

   @Override
   protected String getHurtSound() {
      return "mob.irongolem.hit";
   }

   @Override
   protected String getDeathSound() {
      return "mob.irongolem.death";
   }

   @Override
   protected void playStepSound(BlockPos blockPos, Block block) {
      this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
   }

   @Override
   protected void dropFewItems(boolean boolean1, int integer) {
      int var6 = this.rand.nextInt(3);

      for (int var7 = 0; var7 < var6; var7++) {
         this.dropItemWithOffset(Item.getItemFromBlock(Blocks.red_flower), 1, BlockFlower.EnumFlowerType.POPPY.getMeta());
      }

      int var9 = 3 + this.rand.nextInt(3);

      for (int var8 = 0; var8 < var9; var8++) {
         this.dropItem(Items.iron_ingot, 1);
      }
   }

   public int getHoldRoseTick() {
      return this.holdRoseTick;
   }

   public boolean isPlayerCreated() {
      return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
   }

   public void setPlayerCreated(boolean boolean1) {
      byte var5 = this.dataWatcher.getWatchableObjectByte(16);
      if (boolean1) {
         this.dataWatcher.updateObject(16, (byte)(var5 | 1));
      } else {
         this.dataWatcher.updateObject(16, (byte)(var5 & -2));
      }
   }

   @Override
   public void onDeath(DamageSource damageSource) {
      if (!this.isPlayerCreated() && this.attackingPlayer != null && this.villageObj != null) {
         this.villageObj.setReputationForPlayer(this.attackingPlayer.getName(), -5);
      }

      super.onDeath(damageSource);
   }

   static class AINearestAttackableTargetNonCreeper<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
      public AINearestAttackableTargetNonCreeper(
         EntityCreature entityCreature, Class<T> class2, int integer, boolean boolean4, boolean boolean5, Predicate<? super T> predicate
      ) {
         super(entityCreature, class2, integer, boolean4, boolean5, predicate);
         this.targetEntitySelector = entityLivingBase -> {
            if (predicate != null && !predicate.apply((T)entityLivingBase)) {
               return false;
            }

            if (entityLivingBase instanceof EntityCreeper) {
               return false;
            }

            if (entityLivingBase instanceof EntityPlayer) {
               double var7 = this.getTargetDistance();
               if (entityLivingBase.isSneaking()) {
                  var7 *= 0.8F;
               }

               if (entityLivingBase.isInvisible()) {
                  float var9 = ((EntityPlayer)entityLivingBase).getArmorVisibility();
                  if (var9 < 0.1F) {
                     var9 = 0.1F;
                  }

                  var7 *= 0.7F * var9;
               }

               if (entityLivingBase.getDistanceToEntity(entityCreature) > var7) {
                  return false;
               }
            }

            return this.isSuitableTarget(entityLivingBase, false);
         };
      }
   }
}
