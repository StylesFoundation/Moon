package net.minecraft.entity.passive;

import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public abstract class EntityTameable extends EntityAnimal implements IEntityOwnable {
   protected EntityAISit aiSit = new EntityAISit(this);

   public EntityTameable(World world) {
      super(world);
      this.setupTamedAI();
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (byte)0);
      this.dataWatcher.addObject(17, "");
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      if (this.getOwnerId() == null) {
         nBTTagCompound.setString("OwnerUUID", "");
      } else {
         nBTTagCompound.setString("OwnerUUID", this.getOwnerId());
      }

      nBTTagCompound.setBoolean("Sitting", this.isSitting());
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      String var5;
      if (nBTTagCompound.hasKey("OwnerUUID", 8)) {
         var5 = nBTTagCompound.getString("OwnerUUID");
      } else {
         var5 = PreYggdrasilConverter.getStringUUIDFromName(nBTTagCompound.getString("Owner"));
      }

      if (var5.length() > 0) {
         this.setOwnerId(var5);
         this.setTamed(true);
      }

      this.aiSit.setSitting(nBTTagCompound.getBoolean("Sitting"));
      this.setSitting(nBTTagCompound.getBoolean("Sitting"));
   }

   protected void playTameEffect(boolean boolean1) {
      EnumParticleTypes var5 = EnumParticleTypes.HEART;
      if (!boolean1) {
         var5 = EnumParticleTypes.SMOKE_NORMAL;
      }

      for (int var6 = 0; var6 < 7; var6++) {
         double var7 = this.rand.nextGaussian() * 0.02;
         double var9 = this.rand.nextGaussian() * 0.02;
         double var11 = this.rand.nextGaussian() * 0.02;
         this.worldObj
            .spawnParticle(
               var5,
               this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
               this.posY + 0.5 + this.rand.nextFloat() * this.height,
               this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
               var7,
               var9,
               var11
            );
      }
   }

   @Override
   public void handleStatusUpdate(byte byte1) {
      if (byte1 == 7) {
         this.playTameEffect(true);
      } else if (byte1 == 6) {
         this.playTameEffect(false);
      } else {
         super.handleStatusUpdate(byte1);
      }
   }

   public boolean isTamed() {
      return (this.dataWatcher.getWatchableObjectByte(16) & 4) != 0;
   }

   public void setTamed(boolean boolean1) {
      byte var5 = this.dataWatcher.getWatchableObjectByte(16);
      if (boolean1) {
         this.dataWatcher.updateObject(16, (byte)(var5 | 4));
      } else {
         this.dataWatcher.updateObject(16, (byte)(var5 & -5));
      }

      this.setupTamedAI();
   }

   protected void setupTamedAI() {
   }

   public boolean isSitting() {
      return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
   }

   public void setSitting(boolean boolean1) {
      byte var5 = this.dataWatcher.getWatchableObjectByte(16);
      if (boolean1) {
         this.dataWatcher.updateObject(16, (byte)(var5 | 1));
      } else {
         this.dataWatcher.updateObject(16, (byte)(var5 & -2));
      }
   }

   @Override
   public String getOwnerId() {
      return this.dataWatcher.getWatchableObjectString(17);
   }

   public void setOwnerId(String string) {
      this.dataWatcher.updateObject(17, string);
   }

   public EntityLivingBase getOwner() {
      try {
         UUID var3 = UUID.fromString(this.getOwnerId());
         return var3 == null ? null : this.worldObj.getPlayerEntityByUUID(var3);
      } catch (IllegalArgumentException var4) {
         return null;
      }
   }

   public boolean isOwner(EntityLivingBase entityLivingBase) {
      return entityLivingBase == this.getOwner();
   }

   public EntityAISit getAISit() {
      return this.aiSit;
   }

   public boolean shouldAttackEntity(EntityLivingBase entityLivingBase1, EntityLivingBase entityLivingBase2) {
      return true;
   }

   @Override
   public Team getTeam() {
      if (this.isTamed()) {
         EntityLivingBase var4 = this.getOwner();
         if (var4 != null) {
            return var4.getTeam();
         }
      }

      return super.getTeam();
   }

   @Override
   public boolean isOnSameTeam(EntityLivingBase entityLivingBase) {
      if (this.isTamed()) {
         EntityLivingBase var5 = this.getOwner();
         if (entityLivingBase == var5) {
            return true;
         }

         if (var5 != null) {
            return var5.isOnSameTeam(entityLivingBase);
         }
      }

      return super.isOnSameTeam(entityLivingBase);
   }

   @Override
   public void onDeath(DamageSource damageSource) {
      if (!this.worldObj.isRemote
         && this.worldObj.getGameRules().getBoolean("showDeathMessages")
         && this.hasCustomName()
         && this.getOwner() instanceof EntityPlayerMP) {
         this.getOwner().addChatMessage(this.getCombatTracker().getDeathMessage());
      }

      super.onDeath(damageSource);
   }
}
