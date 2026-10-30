package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityOtherPlayerMP extends AbstractClientPlayer {
   private boolean isItemInUse;
   private int otherPlayerMPPosRotationIncrements;
   private double otherPlayerMPX;
   private double otherPlayerMPY;
   private double otherPlayerMPZ;
   private double otherPlayerMPYaw;
   private double otherPlayerMPPitch;

   public EntityOtherPlayerMP(World world, GameProfile gameProfile) {
      super(world, gameProfile);
      this.stepHeight = 0.0F;
      this.noClip = true;
      this.renderOffsetY = 0.25F;
      this.renderDistanceWeight = 10.0;
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      return true;
   }

   @Override
   public void setPositionAndRotation2(double double1, double double2, double double3, float float4, float float5, int integer, boolean boolean7) {
      this.otherPlayerMPX = double1;
      this.otherPlayerMPY = double2;
      this.otherPlayerMPZ = double3;
      this.otherPlayerMPYaw = float4;
      this.otherPlayerMPPitch = float5;
      this.otherPlayerMPPosRotationIncrements = integer;
   }

   @Override
   public void onUpdate() {
      this.renderOffsetY = 0.0F;
      super.onUpdate();
      this.prevLimbSwingAmount = this.limbSwingAmount;
      double var4 = this.posX - this.prevPosX;
      double var6 = this.posZ - this.prevPosZ;
      float var8 = MathHelper.sqrt_double(var4 * var4 + var6 * var6) * 4.0F;
      if (var8 > 1.0F) {
         var8 = 1.0F;
      }

      this.limbSwingAmount = this.limbSwingAmount + (var8 - this.limbSwingAmount) * 0.4F;
      this.limbSwing = this.limbSwing + this.limbSwingAmount;
      if (!this.isItemInUse && this.isEating() && this.inventory.mainInventory[this.inventory.currentItem] != null) {
         ItemStack var9 = this.inventory.mainInventory[this.inventory.currentItem];
         this.setItemInUse(this.inventory.mainInventory[this.inventory.currentItem], var9.getItem().getMaxItemUseDuration(var9));
         this.isItemInUse = true;
      } else if (this.isItemInUse && !this.isEating()) {
         this.clearItemInUse();
         this.isItemInUse = false;
      }
   }

   @Override
   public void onLivingUpdate() {
      if (this.otherPlayerMPPosRotationIncrements > 0) {
         double var4 = this.posX + (this.otherPlayerMPX - this.posX) / this.otherPlayerMPPosRotationIncrements;
         double var6 = this.posY + (this.otherPlayerMPY - this.posY) / this.otherPlayerMPPosRotationIncrements;
         double var8 = this.posZ + (this.otherPlayerMPZ - this.posZ) / this.otherPlayerMPPosRotationIncrements;
         double var10 = this.otherPlayerMPYaw - this.rotationYaw;

         while (var10 < -180.0) {
            var10 += 360.0;
         }

         while (var10 >= 180.0) {
            var10 -= 360.0;
         }

         this.rotationYaw = (float)(this.rotationYaw + var10 / this.otherPlayerMPPosRotationIncrements);
         this.rotationPitch = (float)(this.rotationPitch + (this.otherPlayerMPPitch - this.rotationPitch) / this.otherPlayerMPPosRotationIncrements);
         this.otherPlayerMPPosRotationIncrements--;
         this.setPosition(var4, var6, var8);
         this.setRotation(this.rotationYaw, this.rotationPitch);
      }

      this.prevCameraYaw = this.cameraYaw;
      this.updateArmSwingProgress();
      float var12 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
      float var5 = (float)Math.atan(-this.motionY * 0.2F) * 15.0F;
      if (var12 > 0.1F) {
         var12 = 0.1F;
      }

      if (!this.onGround || this.getHealth() <= 0.0F) {
         var12 = 0.0F;
      }

      if (this.onGround || this.getHealth() <= 0.0F) {
         var5 = 0.0F;
      }

      this.cameraYaw = this.cameraYaw + (var12 - this.cameraYaw) * 0.4F;
      this.cameraPitch = this.cameraPitch + (var5 - this.cameraPitch) * 0.8F;
   }

   @Override
   public void setCurrentItemOrArmor(int integer, ItemStack itemStack) {
      if (integer == 0) {
         this.inventory.mainInventory[this.inventory.currentItem] = itemStack;
      } else {
         this.inventory.armorInventory[integer - 1] = itemStack;
      }
   }

   @Override
   public void addChatMessage(IChatComponent iChatComponent) {
      Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(iChatComponent);
   }

   @Override
   public boolean canCommandSenderUseCommand(int integer, String string) {
      return false;
   }

   @Override
   public BlockPos getPosition() {
      return new BlockPos(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5);
   }
}
