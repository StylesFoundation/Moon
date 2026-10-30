package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityPig extends EntityAnimal {
   private final EntityAIControlledByPlayer aiControlledByPlayer;

   public EntityPig(World world) {
      super(world);
      this.setSize(0.9F, 0.9F);
      ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIPanic(this, 1.25));
      this.tasks.addTask(2, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3F));
      this.tasks.addTask(3, new EntityAIMate(this, 1.0));
      this.tasks.addTask(4, new EntityAITempt(this, 1.2, Items.carrot_on_a_stick, false));
      this.tasks.addTask(4, new EntityAITempt(this, 1.2, Items.carrot, false));
      this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1));
      this.tasks.addTask(6, new EntityAIWander(this, 1.0));
      this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(8, new EntityAILookIdle(this));
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
   }

   @Override
   public boolean canBeSteered() {
      ItemStack var4 = ((EntityPlayer)this.riddenByEntity).getHeldItem();
      return var4 != null && var4.getItem() == Items.carrot_on_a_stick;
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (byte)0);
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setBoolean("Saddle", this.getSaddled());
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.setSaddled(nBTTagCompound.getBoolean("Saddle"));
   }

   @Override
   protected String getLivingSound() {
      return "mob.pig.say";
   }

   @Override
   protected String getHurtSound() {
      return "mob.pig.say";
   }

   @Override
   protected String getDeathSound() {
      return "mob.pig.death";
   }

   @Override
   protected void playStepSound(BlockPos blockPos, Block block) {
      this.playSound("mob.pig.step", 0.15F, 1.0F);
   }

   @Override
   public boolean interact(EntityPlayer entityPlayer) {
      if (super.interact(entityPlayer)) {
         return true;
      } else if (this.getSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == entityPlayer)) {
         entityPlayer.mountEntity(this);
         return true;
      } else {
         return false;
      }
   }

   @Override
   protected Item getDropItem() {
      return this.isBurning() ? Items.cooked_porkchop : Items.porkchop;
   }

   @Override
   protected void dropFewItems(boolean boolean1, int integer) {
      int var6 = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + integer);

      for (int var7 = 0; var7 < var6; var7++) {
         if (this.isBurning()) {
            this.dropItem(Items.cooked_porkchop, 1);
         } else {
            this.dropItem(Items.porkchop, 1);
         }
      }

      if (this.getSaddled()) {
         this.dropItem(Items.saddle, 1);
      }
   }

   public boolean getSaddled() {
      return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
   }

   public void setSaddled(boolean boolean1) {
      if (boolean1) {
         this.dataWatcher.updateObject(16, (byte)1);
      } else {
         this.dataWatcher.updateObject(16, (byte)0);
      }
   }

   @Override
   public void onStruckByLightning(EntityLightningBolt entityLightningBolt) {
      if (!this.worldObj.isRemote && !this.isDead) {
         EntityPigZombie var5 = new EntityPigZombie(this.worldObj);
         var5.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
         var5.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
         var5.setNoAI(this.isAIDisabled());
         if (this.hasCustomName()) {
            var5.setCustomNameTag(this.getCustomNameTag());
            var5.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
         }

         this.worldObj.spawnEntityInWorld(var5);
         this.setDead();
      }
   }

   @Override
   public void fall(float float1, float float2) {
      super.fall(float1, float2);
      if (float1 > 5.0F && this.riddenByEntity instanceof EntityPlayer) {
         ((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
      }
   }

   public EntityPig createChild(EntityAgeable entityAgeable) {
      return new EntityPig(this.worldObj);
   }

   @Override
   public boolean isBreedingItem(ItemStack itemStack) {
      return itemStack != null && itemStack.getItem() == Items.carrot;
   }

   public EntityAIControlledByPlayer getAIControlledByPlayer() {
      return this.aiControlledByPlayer;
   }
}
