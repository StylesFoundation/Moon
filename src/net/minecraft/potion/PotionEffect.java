package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionEffect {
   private static final Logger LOGGER = LogManager.getLogger();
   private final int potionID;
   private int duration;
   private int amplifier;
   private boolean isSplashPotion;
   private boolean isAmbient;
   private boolean isPotionDurationMax;
   private boolean showParticles;

   public PotionEffect(int integer1, int integer2) {
      this(integer1, integer2, 0);
   }

   public PotionEffect(int integer1, int integer2, int integer3) {
      this(integer1, integer2, integer3, false, true);
   }

   public PotionEffect(int integer1, int integer2, int integer3, boolean boolean4, boolean boolean5) {
      this.potionID = integer1;
      this.duration = integer2;
      this.amplifier = integer3;
      this.isAmbient = boolean4;
      this.showParticles = boolean5;
   }

   public PotionEffect(PotionEffect potionEffect) {
      this.potionID = potionEffect.potionID;
      this.duration = potionEffect.duration;
      this.amplifier = potionEffect.amplifier;
      this.isAmbient = potionEffect.isAmbient;
      this.showParticles = potionEffect.showParticles;
   }

   public void combine(PotionEffect potionEffect) {
      if (this.potionID != potionEffect.potionID) {
         LOGGER.warn("This method should only be called for matching effects!");
      }

      if (potionEffect.amplifier > this.amplifier) {
         this.amplifier = potionEffect.amplifier;
         this.duration = potionEffect.duration;
      } else if (potionEffect.amplifier == this.amplifier && this.duration < potionEffect.duration) {
         this.duration = potionEffect.duration;
      } else if (!potionEffect.isAmbient && this.isAmbient) {
         this.isAmbient = potionEffect.isAmbient;
      }

      this.showParticles = potionEffect.showParticles;
   }

   public int getPotionID() {
      return this.potionID;
   }

   public int getDuration() {
      return this.duration;
   }

   public int getAmplifier() {
      return this.amplifier;
   }

   public void setSplashPotion(boolean boolean1) {
      this.isSplashPotion = boolean1;
   }

   public boolean getIsAmbient() {
      return this.isAmbient;
   }

   public boolean getIsShowParticles() {
      return this.showParticles;
   }

   public boolean onUpdate(EntityLivingBase entityLivingBase) {
      if (this.duration > 0) {
         if (Potion.potionTypes[this.potionID].isReady(this.duration, this.amplifier)) {
            this.performEffect(entityLivingBase);
         }

         this.deincrementDuration();
      }

      return this.duration > 0;
   }

   private int deincrementDuration() {
      return --this.duration;
   }

   public void performEffect(EntityLivingBase entityLivingBase) {
      if (this.duration > 0) {
         Potion.potionTypes[this.potionID].performEffect(entityLivingBase, this.amplifier);
      }
   }

   public String getEffectName() {
      return Potion.potionTypes[this.potionID].getName();
   }

   @Override
   public int hashCode() {
      return this.potionID;
   }

   @Override
   public String toString() {
      String var4;
      if (this.getAmplifier() > 0) {
         var4 = this.getEffectName() + " x " + (this.getAmplifier() + 1) + ", Duration: " + this.getDuration();
      } else {
         var4 = this.getEffectName() + ", Duration: " + this.getDuration();
      }

      if (this.isSplashPotion) {
         var4 = var4 + ", Splash: true";
      }

      if (!this.showParticles) {
         var4 = var4 + ", Particles: false";
      }

      return Potion.potionTypes[this.potionID].isUsable() ? "(" + var4 + ")" : var4;
   }

   @Override
   public boolean equals(Object object) {
      return !(object instanceof PotionEffect var5)
         ? false
         : this.potionID == var5.potionID
            && this.amplifier == var5.amplifier
            && this.duration == var5.duration
            && this.isSplashPotion == var5.isSplashPotion
            && this.isAmbient == var5.isAmbient;
   }

   public NBTTagCompound writeCustomPotionEffectToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setByte("Id", (byte)this.getPotionID());
      nBTTagCompound.setByte("Amplifier", (byte)this.getAmplifier());
      nBTTagCompound.setInteger("Duration", this.getDuration());
      nBTTagCompound.setBoolean("Ambient", this.getIsAmbient());
      nBTTagCompound.setBoolean("ShowParticles", this.getIsShowParticles());
      return nBTTagCompound;
   }

   public static PotionEffect readCustomPotionEffectFromNBT(NBTTagCompound nBTTagCompound) {
      byte var4 = nBTTagCompound.getByte("Id");
      if (var4 >= 0 && var4 < Potion.potionTypes.length && Potion.potionTypes[var4] != null) {
         byte var5 = nBTTagCompound.getByte("Amplifier");
         int var6 = nBTTagCompound.getInteger("Duration");
         boolean var7 = nBTTagCompound.getBoolean("Ambient");
         boolean var8 = true;
         if (nBTTagCompound.hasKey("ShowParticles", 1)) {
            var8 = nBTTagCompound.getBoolean("ShowParticles");
         }

         return new PotionEffect(var4, var6, var5, var7, var8);
      } else {
         return null;
      }
   }

   public void setPotionDurationMax(boolean boolean1) {
      this.isPotionDurationMax = boolean1;
   }

   public boolean getIsPotionDurationMax() {
      return this.isPotionDurationMax;
   }
}
