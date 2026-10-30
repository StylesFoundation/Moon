package com.moon.util.game;

import com.moon.util.math.MathUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class PredictedPlayer {
   private static final Minecraft a = Minecraft.getMinecraft();
   private final ArrayList<Vec3> trackedPositions = new ArrayList<>();
   private final EntityLivingBase entity;
   private float yaw;
   private float pitch;
   private double posX;
   private double posY;
   private double posZ;
   private double velX;
   private double velY;
   private double velZ;
   private float width;
   private float height;
   private final float stepHeight;
   private float jumpMovementFactor;
   private float forward;
   private float strafe;
   private boolean collidedVertically;
   private boolean collidedHorizontally;
   private boolean onGround;
   private boolean sprinting;
   private boolean sneaking;
   private boolean jumping;

   public PredictedPlayer(EntityLivingBase entityLivingBase) {
      this(entityLivingBase, 0.0F, 0.0F);
      float moveSpeed = 0.98F;
      if (this.sneaking) {
         moveSpeed = 0.29400003F;
      }

      if (entityLivingBase instanceof EntityPlayer && ((EntityPlayer)entityLivingBase).isUsingItem()) {
         moveSpeed *= 0.2F;
      }

      if (!this.sneaking && this.sprinting) {
         this.jumping = true;
      }

      float[] inputs = MovementInputUtil.inferInput(this.yaw * MathUtil.TO_RADIANS, (float)this.velX, (float)this.velZ, 0.754F, moveSpeed);
      float inputLength = MathUtil.length(inputs);
      this.forward = (float)MathUtil.round(moveSpeed * inputs[0] / inputLength, 2);
      this.strafe = (float)MathUtil.round(moveSpeed * inputs[1] / inputLength, 2);
      if (Math.abs(this.forward) < 0.1) {
         this.forward = 0.0F;
      }

      if (Math.abs(this.strafe) < 0.1) {
         this.strafe = 0.0F;
      }

      this.onGround = this.posY % 0.015625 == 0.0;
      if (this.onGround) {
         this.velY = (this.velY - 0.08F) * 0.98F;
      }
   }

   public PredictedPlayer(EntityLivingBase entityLivingBase, float forward, float strafe) {
      this.entity = entityLivingBase;
      if (entityLivingBase instanceof EntityPlayerSP) {
         this.posX = entityLivingBase.posX;
         this.posY = entityLivingBase.getEntityBoundingBox().minY;
         this.posZ = entityLivingBase.posZ;
      } else {
         this.posX = entityLivingBase.posX;
         this.posY = entityLivingBase.posY;
         this.posZ = entityLivingBase.posZ;
      }

      if (entityLivingBase instanceof EntityPlayerSP) {
         this.velX = entityLivingBase.motionX;
         this.velY = entityLivingBase.motionY;
         this.velZ = entityLivingBase.motionZ;
      } else {
         this.velX = entityLivingBase.posX - entityLivingBase.lastTickPosX;
         this.velY = entityLivingBase.posY - entityLivingBase.lastTickPosY;
         this.velZ = entityLivingBase.posZ - entityLivingBase.lastTickPosZ;
      }

      this.width = entityLivingBase.width;
      this.height = entityLivingBase.height;
      if (entityLivingBase instanceof EntityPlayerSP) {
         this.yaw = ((EntityPlayerSP)entityLivingBase).cw;
      } else {
         this.yaw = entityLivingBase.rotationYaw;
      }

      this.sprinting = entityLivingBase.isSprinting();
      this.sneaking = entityLivingBase.isSneaking();
      this.forward = forward;
      this.strafe = strafe;
      this.onGround = entityLivingBase.onGround;
      if (this.forward < 0.0F) {
         this.sprinting = false;
      }

      this.stepHeight = 0.6F;
   }

   public void tick() {
      this.trackedPositions.add(new Vec3(this.posX, this.posY, this.posZ));
      this.move();
   }

   private void move() {
      if (Math.abs(this.velX) < 0.005F) {
         this.velX = 0.0;
      }

      if (Math.abs(this.velY) < 0.005F) {
         this.velY = 0.0;
      }

      if (Math.abs(this.velZ) < 0.005F) {
         this.velZ = 0.0;
      }

      this.jumpMovementFactor = 0.02F;
      if (this.sprinting) {
         this.jumpMovementFactor += 0.006F;
      }

      float moveStrafe = this.strafe;
      float moveForward = this.forward;
      if (this.sneaking) {
         moveForward *= 0.2F;
         moveStrafe *= 0.2F;
      }

      this.travel(moveStrafe, moveForward);
   }

   private void moveFlying(float strafe, float forward, float speed) {
      float dist = strafe * strafe + forward * forward;
      if (dist >= 1.0E-4F) {
         dist = MathHelper.sqrt_float(dist);
         if (dist < 1.0F) {
            dist = 1.0F;
         }

         dist = speed / dist;
         strafe *= dist;
         forward *= dist;
         float sinYaw = MathHelper.sin(this.yaw * (float) Math.PI / 180.0F);
         float cosYaw = MathHelper.cos(this.yaw * (float) Math.PI / 180.0F);
         this.velX += strafe * cosYaw - forward * sinYaw;
         this.velZ += forward * cosYaw + strafe * sinYaw;
      }
   }

   private void travel(float strafe, float forward) {
      if (this.isInWater() && !this.hasNoGravity()) {
         this.moveFlying(strafe, forward, 0.02F);
         this.moveEntity(this.velX, this.velY, this.velZ);
         this.velX *= 0.8F;
         this.velZ *= 0.8F;
         this.velY = (this.velY - 0.02F) * 0.8F;
      } else if (this.isInLava() && !this.hasNoGravity()) {
         this.moveFlying(strafe, forward, 0.02F);
         this.moveEntity(this.velX, this.velY, this.velZ);
         this.velX *= 0.5;
         this.velZ *= 0.5;
         this.velY = (this.velY - 0.02F) * 0.5;
      } else {
         float friction = 0.91F;
         if (this.onGround) {
            friction = a.theWorld
                  .getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)))
                  .getBlock()
                  .slipperiness
               * 0.91F;
         }

         float moveFactor = 0.1F;
         if (forward > 0.1 && this.sprinting) {
            moveFactor = 0.13F;
         }

         float effectiveFactor = moveFactor;
         float moveSpeed = this.onGround ? effectiveFactor * 0.16277136F / (friction * friction * friction) : this.jumpMovementFactor;
         this.moveFlying(strafe, forward, moveSpeed);
         if (this.isOnLadder()) {
            this.velX = MathHelper.clamp_double(this.velX, -0.15F, 0.15F);
            this.velZ = MathHelper.clamp_double(this.velZ, -0.15F, 0.15F);
            if (this.sneaking) {
               this.velY = 0.0;
            } else if (this.velY < -0.15F) {
               this.velY = 0.15F;
            }
         }

         this.moveEntity(this.velX, this.velY, this.velZ);
         if (this.isOnLadder() && this.collidedHorizontally) {
            this.velY = 0.2F;
         }

         this.velY = (this.velY - 0.08F) * 0.98F;
         this.velX *= friction;
         this.velZ *= friction;
      }
   }

   private void moveEntity(double x, double y, double z) {
      AxisAlignedBB entityBB = this.getBoundingBox();
      double origX = x;
      double origY = y;
      double origZ = z;
      if (this.onGround && this.sneaking) {
         for (; x != 0.0 && this.getCollisionBoxes(entityBB.offset(x, -1.0, 0.0)).isEmpty(); origX = x) {
            if (x < 0.05 && x >= -0.05) {
               x = 0.0;
            } else if (x > 0.0) {
               x -= 0.05;
            } else {
               x += 0.05;
            }
         }

         for (; z != 0.0 && this.getCollisionBoxes(entityBB.offset(0.0, -1.0, z)).isEmpty(); origZ = z) {
            if (z < 0.05 && z >= -0.05) {
               z = 0.0;
            } else if (z > 0.0) {
               z -= 0.05;
            } else {
               z += 0.05;
            }
         }

         for (; x != 0.0 && z != 0.0 && this.getCollisionBoxes(entityBB.offset(x, -1.0, z)).isEmpty(); origZ = z) {
            if (x < 0.05 && x >= -0.05) {
               x = 0.0;
            } else if (x > 0.0) {
               x -= 0.05;
            } else {
               x += 0.05;
            }

            origX = x;
            if (z < 0.05 && z >= -0.05) {
               z = 0.0;
            } else if (z > 0.0) {
               z -= 0.05;
            } else {
               z += 0.05;
            }
         }
      }

      List<AxisAlignedBB> collisionBoxes = this.getCollisionBoxes(entityBB.addCoord(x, y, z));
      AxisAlignedBB originalBB = entityBB;

      for (AxisAlignedBB box : collisionBoxes) {
         y = box.calculateYOffset(entityBB, y);
      }

      entityBB = entityBB.offset(0.0, y, 0.0);
      boolean onGroundOrFalling = this.onGround || origY != y && origY < 0.0;
      Iterator boxIt = collisionBoxes.iterator();

      while (boxIt.hasNext()) {
         x = ((AxisAlignedBB)boxIt.next()).calculateXOffset(entityBB, x);
      }

      entityBB = entityBB.offset(x, 0.0, 0.0);
      boxIt = collisionBoxes.iterator();

      while (boxIt.hasNext()) {
         z = ((AxisAlignedBB)boxIt.next()).calculateZOffset(entityBB, z);
      }

      entityBB = entityBB.offset(0.0, 0.0, z);
      if (this.stepHeight > 0.0F && onGroundOrFalling && (origX != x || origZ != z)) {
         double steplessX = x;
         double steplessY = y;
         double steplessZ = z;
         AxisAlignedBB steplessBB = entityBB;
         entityBB = originalBB;
         y = this.stepHeight;
         List<AxisAlignedBB> stepBoxes = this.getCollisionBoxes(entityBB.addCoord(origX, y, origZ));
         AxisAlignedBB stepBB1 = entityBB;
         AxisAlignedBB stepBB1XZ = stepBB1.addCoord(origX, 0.0, origZ);
         double stepY1 = y;

         for (AxisAlignedBB box : stepBoxes) {
            stepY1 = box.calculateYOffset(stepBB1XZ, stepY1);
         }

         stepBB1 = stepBB1.offset(0.0, stepY1, 0.0);
         double stepX1 = origX;
         Iterator boxIt1 = stepBoxes.iterator();

         while (boxIt1.hasNext()) {
            stepX1 = ((AxisAlignedBB)boxIt1.next()).calculateXOffset(stepBB1, stepX1);
         }

         stepBB1 = stepBB1.offset(stepX1, 0.0, 0.0);
         double stepZ1 = origZ;
         Iterator boxIt2 = stepBoxes.iterator();

         while (boxIt2.hasNext()) {
            stepZ1 = ((AxisAlignedBB)boxIt2.next()).calculateZOffset(stepBB1, stepZ1);
         }

         stepBB1 = stepBB1.offset(0.0, 0.0, stepZ1);
         AxisAlignedBB stepBB2 = entityBB;
         double stepY2 = y;

         for (AxisAlignedBB box : stepBoxes) {
            stepY2 = box.calculateYOffset(stepBB2, stepY2);
         }

         AxisAlignedBB stepBB2Y = stepBB2.offset(0.0, stepY2, 0.0);
         double stepX2 = origX;
         Iterator boxIt3 = stepBoxes.iterator();

         while (boxIt3.hasNext()) {
            stepX2 = ((AxisAlignedBB)boxIt3.next()).calculateXOffset(stepBB2Y, stepX2);
         }

         AxisAlignedBB stepBB2X = stepBB2Y.offset(stepX2, 0.0, 0.0);
         double stepZ2 = origZ;
         Iterator boxIt4 = stepBoxes.iterator();

         while (boxIt4.hasNext()) {
            stepZ2 = ((AxisAlignedBB)boxIt4.next()).calculateZOffset(stepBB2X, stepZ2);
         }

         AxisAlignedBB stepBB2Z = stepBB2X.offset(0.0, 0.0, stepZ2);
         double distSq1 = stepX1 * stepX1 + stepZ1 * stepZ1;
         double distSq2 = stepX2 * stepX2 + stepZ2 * stepZ2;
         if (distSq1 > distSq2) {
            x = stepX1;
            z = stepZ1;
            y = -stepY1;
            entityBB = stepBB1;
         } else {
            x = stepX2;
            z = stepZ2;
            y = -stepY2;
            entityBB = stepBB2Z;
         }

         Iterator boxItFinal = stepBoxes.iterator();

         while (boxItFinal.hasNext()) {
            y = ((AxisAlignedBB)boxItFinal.next()).calculateYOffset(entityBB, y);
         }

         entityBB = entityBB.offset(0.0, y, 0.0);
         if (steplessX * steplessX + steplessZ * steplessZ >= x * x + z * z) {
            x = steplessX;
            y = steplessY;
            z = steplessZ;
            entityBB = steplessBB;
         }
      }

      this.collidedHorizontally = origX != x || origZ != z;
      this.collidedVertically = origY != y;
      this.onGround = this.collidedVertically && this.velY < 0.0;
      if (origX != x) {
         this.velX = 0.0;
      }

      if (origY != y) {
         this.velY = 0.0;
      }

      if (origZ != z) {
         this.velZ = 0.0;
      }

      this.posX = (entityBB.minX + entityBB.maxX) / 2.0;
      this.posY = entityBB.minY;
      this.posZ = (entityBB.minZ + entityBB.maxZ) / 2.0;
   }

   private void jump() {
      if (this.sprinting) {
         float yawRad = this.yaw * MathUtil.TO_RADIANS;
         float jumpX = -MathHelper.sin(yawRad) * 0.2F;
         float jumpZ = MathHelper.cos(yawRad) * 0.2F;
         this.velX += jumpX;
         this.velZ += jumpZ;
      }

      this.velY = 0.42F;
   }

   private List<AxisAlignedBB> getCollisionBoxes(AxisAlignedBB boundingBox) {
      return a.theWorld.getCollisionBoxes(boundingBox);
   }

   private boolean isInWater() {
      AxisAlignedBB bb = this.getBoundingBox();
      return a.theWorld.isMaterialInBB(bb.expand(-0.1F, -0.4F, -0.1F), Material.water);
   }

   private boolean isInLava() {
      AxisAlignedBB bb = this.getBoundingBox();
      return a.theWorld.isMaterialInBB(bb.expand(-0.1F, -0.4F, -0.1F), Material.lava);
   }

   private boolean isOnLadder() {
      BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
      Block block = a.theWorld.getBlockState(pos).getBlock();
      return block == Blocks.ladder || block == Blocks.vine;
   }

   private boolean hasNoGravity() {
      return false;
   }

   public Vec3 predictPosition(int ticks) {
      for (int i = 0; i < ticks; i++) {
         this.tick();
      }

      return new Vec3(this.posX, this.posY, this.posZ);
   }

   public Vec3 getTrackedPositionAt(int index) {
      for (int i = this.trackedPositions.size(); i <= index + 1; i++) {
         this.tick();
      }

      return this.trackedPositions.get(index);
   }

   public AxisAlignedBB getBoundingBox() {
      return new AxisAlignedBB(
         this.posX - this.width / 2.0F,
         this.posY,
         this.posZ - this.width / 2.0F,
         this.posX + this.width / 2.0F,
         this.posY + this.height,
         this.posZ + this.width / 2.0F
      );
   }

   public ArrayList<Vec3> getTrackedPositions() {
      return this.trackedPositions;
   }

   public EntityLivingBase getEntity() {
      return this.entity;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public double getPosX() {
      return this.posX;
   }

   public double getPosY() {
      return this.posY;
   }

   public double getPosZ() {
      return this.posZ;
   }

   public double getVelX() {
      return this.velX;
   }

   public double getVelY() {
      return this.velY;
   }

   public double getVelZ() {
      return this.velZ;
   }

   public float getWidth() {
      return this.width;
   }

   public float getHeight() {
      return this.height;
   }

   public float getStepHeight() {
      return this.stepHeight;
   }

   public float getJumpMovementFactor() {
      return this.jumpMovementFactor;
   }

   public float getForward() {
      return this.forward;
   }

   public float getStrafe() {
      return this.strafe;
   }

   public boolean isCollidedVertically() {
      return this.collidedVertically;
   }

   public boolean isCollidedHorizontally() {
      return this.collidedHorizontally;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public boolean isSprinting() {
      return this.sprinting;
   }

   public boolean isSneaking() {
      return this.sneaking;
   }

   public boolean isJumping() {
      return this.jumping;
   }
}
