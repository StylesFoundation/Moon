package net.minecraft.entity.player;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerCapabilities {
   public boolean disableDamage;
   public boolean isFlying;
   public boolean allowFlying;
   public boolean isCreativeMode;
   public boolean allowEdit = true;
   private float flySpeed = 0.05F;
   private float walkSpeed = 0.1F;

   public void writeCapabilitiesToNBT(NBTTagCompound nBTTagCompound) {
      NBTTagCompound var2 = new NBTTagCompound();
      var2.setBoolean("invulnerable", this.disableDamage);
      var2.setBoolean("flying", this.isFlying);
      var2.setBoolean("mayfly", this.allowFlying);
      var2.setBoolean("instabuild", this.isCreativeMode);
      var2.setBoolean("mayBuild", this.allowEdit);
      var2.setFloat("flySpeed", this.flySpeed);
      var2.setFloat("walkSpeed", this.walkSpeed);
      nBTTagCompound.setTag("abilities", var2);
   }

   public void readCapabilitiesFromNBT(NBTTagCompound nBTTagCompound) {
      if (nBTTagCompound.hasKey("abilities", 10)) {
         NBTTagCompound var5 = nBTTagCompound.getCompoundTag("abilities");
         this.disableDamage = var5.getBoolean("invulnerable");
         this.isFlying = var5.getBoolean("flying");
         this.allowFlying = var5.getBoolean("mayfly");
         this.isCreativeMode = var5.getBoolean("instabuild");
         if (var5.hasKey("flySpeed", 99)) {
            this.flySpeed = var5.getFloat("flySpeed");
            this.walkSpeed = var5.getFloat("walkSpeed");
         }

         if (var5.hasKey("mayBuild", 1)) {
            this.allowEdit = var5.getBoolean("mayBuild");
         }
      }
   }

   public float getFlySpeed() {
      return this.flySpeed;
   }

   public void setFlySpeed(float float1) {
      this.flySpeed = float1;
   }

   public float getWalkSpeed() {
      return this.walkSpeed;
   }

   public void setPlayerWalkSpeed(float float1) {
      this.walkSpeed = float1;
   }
}
