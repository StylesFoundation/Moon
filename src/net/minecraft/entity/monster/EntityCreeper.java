package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCreeper extends EntityMob {
   private int lastActiveTime;
   private int timeSinceIgnited;
   private int fuseTime = 30;
   private int explosionRadius = 3;
   private int field_175494_bm = 0;

   public EntityCreeper(World world) {
      super(world);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAICreeperSwell(this));
      this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityOcelot.class, 6.0F, 1.0, 1.2));
      this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, false));
      this.tasks.addTask(5, new EntityAIWander(this, 0.8));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(6, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
   }

   @Override
   public int getMaxFallHeight() {
      return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
   }

   @Override
   public void fall(float float1, float float2) {
      super.fall(float1, float2);
      this.timeSinceIgnited = (int)(this.timeSinceIgnited + float1 * 1.5F);
      if (this.timeSinceIgnited > this.fuseTime - 5) {
         this.timeSinceIgnited = this.fuseTime - 5;
      }
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (byte)-1);
      this.dataWatcher.addObject(17, (byte)0);
      this.dataWatcher.addObject(18, (byte)0);
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
         nBTTagCompound.setBoolean("powered", true);
      }

      nBTTagCompound.setShort("Fuse", (short)this.fuseTime);
      nBTTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
      nBTTagCompound.setBoolean("ignited", this.hasIgnited());
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.dataWatcher.updateObject(17, (byte)(nBTTagCompound.getBoolean("powered") ? 1 : 0));
      if (nBTTagCompound.hasKey("Fuse", 99)) {
         this.fuseTime = nBTTagCompound.getShort("Fuse");
      }

      if (nBTTagCompound.hasKey("ExplosionRadius", 99)) {
         this.explosionRadius = nBTTagCompound.getByte("ExplosionRadius");
      }

      if (nBTTagCompound.getBoolean("ignited")) {
         this.ignite();
      }
   }

   @Override
   public void onUpdate() {
      if (this.isEntityAlive()) {
         this.lastActiveTime = this.timeSinceIgnited;
         if (this.hasIgnited()) {
            this.setCreeperState(1);
         }

         int var4 = this.getCreeperState();
         if (var4 > 0 && this.timeSinceIgnited == 0) {
            this.playSound("creeper.primed", 1.0F, 0.5F);
         }

         this.timeSinceIgnited += var4;
         if (this.timeSinceIgnited < 0) {
            this.timeSinceIgnited = 0;
         }

         if (this.timeSinceIgnited >= this.fuseTime) {
            this.timeSinceIgnited = this.fuseTime;
            this.explode();
         }
      }

      super.onUpdate();
   }

   @Override
   protected String getHurtSound() {
      return "mob.creeper.say";
   }

   @Override
   protected String getDeathSound() {
      return "mob.creeper.death";
   }

   @Override
   public void onDeath(DamageSource damageSource) {
      super.onDeath(damageSource);
      if (damageSource.getEntity() instanceof EntitySkeleton) {
         int var5 = Item.getIdFromItem(Items.record_13);
         int var6 = Item.getIdFromItem(Items.record_wait);
         int var7 = var5 + this.rand.nextInt(var6 - var5 + 1);
         this.dropItem(Item.getItemById(var7), 1);
      } else if (damageSource.getEntity() instanceof EntityCreeper
         && damageSource.getEntity() != this
         && ((EntityCreeper)damageSource.getEntity()).getPowered()
         && ((EntityCreeper)damageSource.getEntity()).isAIEnabled()) {
         ((EntityCreeper)damageSource.getEntity()).func_175493_co();
         this.entityDropItem(new ItemStack(Items.skull, 1, 4), 0.0F);
      }
   }

   @Override
   public boolean attackEntityAsMob(Entity entity) {
      return true;
   }

   public boolean getPowered() {
      return this.dataWatcher.getWatchableObjectByte(17) == 1;
   }

   public float getCreeperFlashIntensity(float float1) {
      return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * float1) / (this.fuseTime - 2);
   }

   @Override
   protected Item getDropItem() {
      return Items.gunpowder;
   }

   public int getCreeperState() {
      return this.dataWatcher.getWatchableObjectByte(16);
   }

   public void setCreeperState(int integer) {
      this.dataWatcher.updateObject(16, (byte)integer);
   }

   @Override
   public void onStruckByLightning(EntityLightningBolt entityLightningBolt) {
      super.onStruckByLightning(entityLightningBolt);
      this.dataWatcher.updateObject(17, (byte)1);
   }

   @Override
   protected boolean interact(EntityPlayer entityPlayer) {
      ItemStack var5 = entityPlayer.inventory.getCurrentItem();
      if (var5 != null && var5.getItem() == Items.flint_and_steel) {
         this.worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
         entityPlayer.swingItem();
         if (!this.worldObj.isRemote) {
            this.ignite();
            var5.damageItem(1, entityPlayer);
            return true;
         }
      }

      return super.interact(entityPlayer);
   }

   private void explode() {
      if (!this.worldObj.isRemote) {
         boolean var4 = this.worldObj.getGameRules().getBoolean("mobGriefing");
         float var5 = this.getPowered() ? 2.0F : 1.0F;
         this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * var5, var4);
         this.setDead();
      }
   }

   public boolean hasIgnited() {
      return this.dataWatcher.getWatchableObjectByte(18) != 0;
   }

   public void ignite() {
      this.dataWatcher.updateObject(18, (byte)1);
   }

   public boolean isAIEnabled() {
      return this.field_175494_bm < 1 && this.worldObj.getGameRules().getBoolean("doMobLoot");
   }

   public void func_175493_co() {
      this.field_175494_bm++;
   }
}
