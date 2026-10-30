package com.moon.util.game;

import com.moon.util.math.MathUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.optifine.BlockPosM;

public class PredictedEntity {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final ArrayList<Vec3> trackedPositions = new ArrayList<>();
   private final EntityLivingBase entity;
   private float yaw;
   private float pitch;
   private float posX;
   private float posY;
   private float posZ;
   private float velX;
   private float velY;
   private float velZ;
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

   public PredictedEntity(EntityLivingBase entity) {
      this(entity, 0.0F, 0.0F);
      float speedMultiplier = 0.98F;
      if (this.sneaking) {
         speedMultiplier = 0.29400003F;
      }

      if (entity instanceof EntityPlayer && ((EntityPlayer)entity).isUsingItem()) {
         speedMultiplier *= 0.2F;
      }

      if (!this.sneaking && this.sprinting) {
         this.jumping = true;
      }

      float[] moveInput = MovementInputUtil.inferInput(this.yaw * MathUtil.TO_RADIANS, this.velX, this.velZ, 0.754F, speedMultiplier);
      float inputLength = MathUtil.length(moveInput);
      this.forward = speedMultiplier * moveInput[0] / inputLength;
      this.strafe = speedMultiplier * moveInput[1] / inputLength;
      this.onGround = this.posY % 0.015625 == 0.0;
   }

   public PredictedEntity(EntityLivingBase entity, float forward, float strafe) {
      this.entity = entity;
      this.posX = (float)entity.posX;
      this.posY = (float)entity.getEntityBoundingBox().minY;
      this.posZ = (float)entity.posZ;
      this.velX = (float)(entity.posX - entity.lastTickPosX);
      this.velY = (float)(entity.posY - entity.lastTickPosY);
      this.velZ = (float)(entity.posZ - entity.lastTickPosZ);
      this.width = entity.width;
      this.height = entity.height;
      this.yaw = entity.rotationYaw;
      this.sprinting = entity.isSprinting();
      this.sneaking = entity.isSneaking();
      this.forward = forward;
      this.strafe = strafe;
      if (this.forward < 0.0F) {
         this.sprinting = false;
      }

      this.stepHeight = 0.6F;
   }

   public void tick() {
      this.trackedPositions.add(new Vec3(this.posX, this.posY, this.posZ));
      if (this.sprinting && this.collidedHorizontally) {
         this.sprinting = false;
      }

      if (!this.sprinting && !this.collidedHorizontally && this.forward > 0.0F) {
         this.sprinting = true;
      }

      this.move();
   }

   private void move() {
      if (Math.abs(this.velX) < 0.005F) {
         this.velX = 0.0F;
      }

      if (Math.abs(this.velY) < 0.005F) {
         this.velY = 0.0F;
      }

      if (Math.abs(this.velZ) < 0.005F) {
         this.velZ = 0.0F;
      }

      this.jumpMovementFactor = 0.02F;
      if (this.sprinting) {
         this.jumpMovementFactor += 0.006F;
      }

      float strafeMovement = this.strafe * 0.98F;
      float forwardMovement = this.forward * 0.98F;
      if (this.sneaking) {
         forwardMovement *= 0.2F;
         strafeMovement *= 0.2F;
      }

      this.travel(strafeMovement, forwardMovement);
   }

   private void moveFlying(float strafe, float forward, float speed) {
      float factor = strafe * strafe + forward * forward;
      if (factor >= 1.0E-4F) {
         factor = Math.max(1.0F, MathHelper.sqrt_float(factor));
         factor = speed / factor;
         strafe *= factor;
         forward *= factor;
         float sin = MathHelper.sin(this.yaw * MathUtil.TO_RADIANS);
         float cos = MathHelper.cos(this.yaw * MathUtil.TO_RADIANS);
         this.velX += strafe * cos - forward * sin;
         this.velZ += forward * cos + strafe * sin;
      }
   }

   private void moveEntity(double x, double y, double z) {
      AxisAlignedBB entityBoundingBox = this.getBoundingBox();
      double origX = x;
      double origY = y;
      double origZ = z;
      if (this.onGround && this.sneaking) {
         for (; x != 0.0 && getCollisionBoxes(entityBoundingBox.offset(x, -1.0, 0.0)).isEmpty(); origX = x) {
            if (x < 0.05 && x >= -0.05) {
               x = 0.0;
            } else if (x > 0.0) {
               x -= 0.05;
            } else {
               x += 0.05;
            }
         }

         for (; z != 0.0 && getCollisionBoxes(entityBoundingBox.offset(0.0, -1.0, z)).isEmpty(); origZ = z) {
            if (z < 0.05 && z >= -0.05) {
               z = 0.0;
            } else if (z > 0.0) {
               z -= 0.05;
            } else {
               z += 0.05;
            }
         }

         for (; x != 0.0 && z != 0.0 && getCollisionBoxes(entityBoundingBox.offset(x, -1.0, z)).isEmpty(); origZ = z) {
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

      List<AxisAlignedBB> collidingBoxes = getCollisionBoxes(entityBoundingBox.addCoord(x, y, z));
      AxisAlignedBB originalBB = entityBoundingBox;

      for (AxisAlignedBB box : collidingBoxes) {
         y = box.calculateYOffset(entityBoundingBox, y);
      }

      entityBoundingBox = entityBoundingBox.offset(0.0, y, 0.0);
      boolean fallingFlag = this.onGround || origY != y && origY < 0.0;
      Iterator it = collidingBoxes.iterator();

      while (it.hasNext()) {
         x = ((AxisAlignedBB)it.next()).calculateXOffset(entityBoundingBox, x);
      }

      entityBoundingBox = entityBoundingBox.offset(x, 0.0, 0.0);
      it = collidingBoxes.iterator();

      while (it.hasNext()) {
         z = ((AxisAlignedBB)it.next()).calculateZOffset(entityBoundingBox, z);
      }

      entityBoundingBox = entityBoundingBox.offset(0.0, 0.0, z);
      if (this.stepHeight > 0.0F && fallingFlag && (origX != x || origZ != z)) {
         double savedX = x;
         double savedY = y;
         double savedZ = z;
         AxisAlignedBB savedBB = entityBoundingBox;
         entityBoundingBox = originalBB;
         y = this.stepHeight;
         List<AxisAlignedBB> stepCollidingBoxes = getCollisionBoxes(entityBoundingBox.addCoord(origX, y, origZ));
         AxisAlignedBB stepBB1 = entityBoundingBox;
         AxisAlignedBB stepBBAxis = stepBB1.addCoord(origX, 0.0, origZ);
         double stepY1 = y;

         for (AxisAlignedBB box : stepCollidingBoxes) {
            stepY1 = box.calculateYOffset(stepBBAxis, stepY1);
         }

         stepBB1 = stepBB1.offset(0.0, stepY1, 0.0);
         double stepX1 = origX;
         Iterator itStep1 = stepCollidingBoxes.iterator();

         while (itStep1.hasNext()) {
            stepX1 = ((AxisAlignedBB)itStep1.next()).calculateXOffset(stepBB1, stepX1);
         }

         stepBB1 = stepBB1.offset(stepX1, 0.0, 0.0);
         double stepZ1 = origZ;
         Iterator itStep2 = stepCollidingBoxes.iterator();

         while (itStep2.hasNext()) {
            stepZ1 = ((AxisAlignedBB)itStep2.next()).calculateZOffset(stepBB1, stepZ1);
         }

         stepBB1 = stepBB1.offset(0.0, 0.0, stepZ1);
         AxisAlignedBB stepBB2 = entityBoundingBox;
         double stepY2 = y;

         for (AxisAlignedBB box : stepCollidingBoxes) {
            stepY2 = box.calculateYOffset(stepBB2, stepY2);
         }

         AxisAlignedBB stepBB2Y = stepBB2.offset(0.0, stepY2, 0.0);
         double stepX2 = origX;
         Iterator itStep3 = stepCollidingBoxes.iterator();

         while (itStep3.hasNext()) {
            stepX2 = ((AxisAlignedBB)itStep3.next()).calculateXOffset(stepBB2Y, stepX2);
         }

         AxisAlignedBB stepBB2X = stepBB2Y.offset(stepX2, 0.0, 0.0);
         double stepZ2 = origZ;
         Iterator itStep4 = stepCollidingBoxes.iterator();

         while (itStep4.hasNext()) {
            stepZ2 = ((AxisAlignedBB)itStep4.next()).calculateZOffset(stepBB2X, stepZ2);
         }

         AxisAlignedBB stepBB2Z = stepBB2X.offset(0.0, 0.0, stepZ2);
         double distSq1 = stepX1 * stepX1 + stepZ1 * stepZ1;
         double distSq2 = stepX2 * stepX2 + stepZ2 * stepZ2;
         if (distSq1 > distSq2) {
            x = stepX1;
            z = stepZ1;
            y = -stepY1;
            entityBoundingBox = stepBB1;
         } else {
            x = stepX2;
            z = stepZ2;
            y = -stepY2;
            entityBoundingBox = stepBB2Z;
         }

         Iterator itFinal = stepCollidingBoxes.iterator();

         while (itFinal.hasNext()) {
            y = ((AxisAlignedBB)itFinal.next()).calculateYOffset(entityBoundingBox, y);
         }

         entityBoundingBox = entityBoundingBox.offset(0.0, y, 0.0);
         if (savedX * savedX + savedZ * savedZ >= x * x + z * z) {
            x = savedX;
            y = savedY;
            z = savedZ;
            entityBoundingBox = savedBB;
         }
      }

      this.collidedHorizontally = origX != x || origZ != z;
      this.collidedVertically = origY != y;
      this.onGround = this.collidedVertically && this.velY < 0.0;
      if (origX != x) {
         this.velX = 0.0F;
      }

      if (origY != y) {
         this.velY = 0.0F;
      }

      if (origZ != z) {
         this.velZ = 0.0F;
      }

      this.posX = (float)(entityBoundingBox.minX + entityBoundingBox.maxX) / 2.0F;
      this.posY = (float)entityBoundingBox.minY;
      this.posZ = (float)(entityBoundingBox.minZ + entityBoundingBox.maxZ) / 2.0F;
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
         this.velX *= 0.5F;
         this.velZ *= 0.5F;
         this.velY = (this.velY - 0.02F) * 0.5F;
      } else {
         float friction = 0.91F;
         if (this.onGround) {
            BlockPos groundPos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 1.0F), MathHelper.floor_double(this.posZ));
            IBlockState groundState = mc.theWorld.getBlockState(groundPos);
            friction = 0.91F * groundState.getBlock().slipperiness;
         }

         float speed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
         float factor = this.onGround ? speed * 0.16277136F / (friction * friction * friction) : this.jumpMovementFactor;
         this.moveFlying(strafe, forward, factor);
         if (this.isOnLadder()) {
            this.velX = MathHelper.clamp_float(this.velX, -0.15F, 0.15F);
            this.velZ = MathHelper.clamp_float(this.velZ, -0.15F, 0.15F);
            if (this.sneaking) {
               this.velY = 0.0F;
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

   private void jump() {
      if (this.sprinting) {
         float yawRadians = this.yaw * MathUtil.TO_RADIANS;
         float motionX = -MathHelper.sin(yawRadians) * 0.2F;
         float motionZ = MathHelper.cos(yawRadians) * 0.2F;
         this.velX += motionX;
         this.velZ += motionZ;
      }

      this.velY = 0.42F;
   }

   private static List<AxisAlignedBB> getCollisionBoxes(AxisAlignedBB boundingBox) {
      ArrayList<AxisAlignedBB> collisionBoxes = new ArrayList<>();
      int minX = MathHelper.floor_double(boundingBox.minX);
      int minY = MathHelper.floor_double(boundingBox.minY);
      int minZ = MathHelper.floor_double(boundingBox.minZ);
      int maxX = MathHelper.floor_double(boundingBox.maxX + 1.0);
      int maxY = MathHelper.floor_double(boundingBox.maxY + 1.0);
      int maxZ = MathHelper.floor_double(boundingBox.maxZ + 1.0);
      BlockPosM blockPos = new BlockPosM(0, 0, 0);

      for (int x = minX; x < maxX; x++) {
         for (int z = minZ; z < maxZ; z++) {
            for (int y = minY - 1; y < maxY; y++) {
               blockPos.set(x, y, z);
               IBlockState blockState = mc.theWorld.getBlockState(blockPos);
               blockState.getBlock().addCollisionBoxesToList(mc.theWorld, blockPos, blockState, boundingBox, collisionBoxes, mc.thePlayer);
            }
         }
      }

      return collisionBoxes;
   }

   private boolean isInWater() {
      AxisAlignedBB boundingBox = this.getBoundingBox();
      return mc.theWorld.isMaterialInBB(boundingBox.expand(-0.1F, -0.4F, -0.1F), Material.water);
   }

   private boolean isInLava() {
      AxisAlignedBB boundingBox = this.getBoundingBox();
      return mc.theWorld.isMaterialInBB(boundingBox.expand(-0.1F, -0.4F, -0.1F), Material.lava);
   }

   private boolean isOnLadder() {
      BlockPos pos = new BlockPos(MathHelper.floor_float(this.posX), MathHelper.floor_float(this.posY), MathHelper.floor_float(this.posZ));
      Block block = mc.theWorld.getBlockState(pos).getBlock();
      return block == Blocks.ladder || block == Blocks.vine;
   }

   private boolean hasNoGravity() {
      return false;
   }

   public Vec3 getPositionAt(int index) {
      for (int i = this.getTrackedPositions().size(); i <= index + 1; i++) {
         this.tick();
      }

      return this.trackedPositions.get(index);
   }

   private AxisAlignedBB getBoundingBox() {
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

   public float getPosX() {
      return this.posX;
   }

   public float getPosY() {
      return this.posY;
   }

   public float getPosZ() {
      return this.posZ;
   }

   public float getVelX() {
      return this.velX;
   }

   public float getVelY() {
      return this.velY;
   }

   public float getVelZ() {
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

   public PredictedEntity setYaw(float yaw) {
      this.yaw = yaw;
      return this;
   }

   public PredictedEntity setPitch(float pitch) {
      this.pitch = pitch;
      return this;
   }

   public PredictedEntity setPosX(float posX) {
      this.posX = posX;
      return this;
   }

   public PredictedEntity setPosY(float posY) {
      this.posY = posY;
      return this;
   }

   public PredictedEntity setPosZ(float posZ) {
      this.posZ = posZ;
      return this;
   }

   public PredictedEntity setVelX(float velX) {
      this.velX = velX;
      return this;
   }

   public PredictedEntity setVelY(float velY) {
      this.velY = velY;
      return this;
   }

   public PredictedEntity setVelZ(float velZ) {
      this.velZ = velZ;
      return this;
   }

   public PredictedEntity setWidth(float width) {
      this.width = width;
      return this;
   }

   public PredictedEntity setHeight(float height) {
      this.height = height;
      return this;
   }

   public PredictedEntity setJumpMovementFactor(float jumpMovementFactor) {
      this.jumpMovementFactor = jumpMovementFactor;
      return this;
   }

   public PredictedEntity setForward(float forward) {
      this.forward = forward;
      return this;
   }

   public PredictedEntity setStrafe(float strafe) {
      this.strafe = strafe;
      return this;
   }

   public PredictedEntity setCollidedVertically(boolean collidedVertically) {
      this.collidedVertically = collidedVertically;
      return this;
   }

   public PredictedEntity setCollidedHorizontally(boolean collidedHorizontally) {
      this.collidedHorizontally = collidedHorizontally;
      return this;
   }

   public PredictedEntity setOnGround(boolean onGround) {
      this.onGround = onGround;
      return this;
   }

   public PredictedEntity setSprinting(boolean sprinting) {
      this.sprinting = sprinting;
      return this;
   }

   public PredictedEntity setSneaking(boolean sneaking) {
      this.sneaking = sneaking;
      return this;
   }

   public PredictedEntity setJumping(boolean jumping) {
      this.jumping = jumping;
      return this;
   }

   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof PredictedEntity other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (Float.compare(this.getYaw(), other.getYaw()) != 0) {
         return false;
      } else if (Float.compare(this.getPitch(), other.getPitch()) != 0) {
         return false;
      } else if (Float.compare(this.getPosX(), other.getPosX()) != 0) {
         return false;
      } else if (Float.compare(this.getPosY(), other.getPosY()) != 0) {
         return false;
      } else if (Float.compare(this.getPosZ(), other.getPosZ()) != 0) {
         return false;
      } else if (Float.compare(this.getVelX(), other.getVelX()) != 0) {
         return false;
      } else if (Float.compare(this.getVelY(), other.getVelY()) != 0) {
         return false;
      } else if (Float.compare(this.getVelZ(), other.getVelZ()) != 0) {
         return false;
      } else if (Float.compare(this.getWidth(), other.getWidth()) != 0) {
         return false;
      } else if (Float.compare(this.getHeight(), other.getHeight()) != 0) {
         return false;
      } else if (Float.compare(this.getStepHeight(), other.getStepHeight()) != 0) {
         return false;
      } else if (Float.compare(this.getJumpMovementFactor(), other.getJumpMovementFactor()) != 0) {
         return false;
      } else if (Float.compare(this.getForward(), other.getForward()) != 0) {
         return false;
      } else if (Float.compare(this.getStrafe(), other.getStrafe()) != 0) {
         return false;
      } else if (this.isCollidedVertically() != other.isCollidedVertically()) {
         return false;
      } else if (this.isCollidedHorizontally() != other.isCollidedHorizontally()) {
         return false;
      } else if (this.isOnGround() != other.isOnGround()) {
         return false;
      } else if (this.isSprinting() != other.isSprinting()) {
         return false;
      } else if (this.isSneaking() != other.isSneaking()) {
         return false;
      } else if (this.isJumping() != other.isJumping()) {
         return false;
      } else {
         ArrayList<Vec3> thisPositions = this.getTrackedPositions();
         ArrayList<Vec3> otherPositions = other.getTrackedPositions();
         if (thisPositions == null ? otherPositions == null : thisPositions.equals(otherPositions)) {
            EntityLivingBase thisEntity = this.getEntity();
            EntityLivingBase otherEntity = other.getEntity();
            return thisEntity == null ? otherEntity == null : thisEntity.equals(otherEntity);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof PredictedEntity;
   }

   @Override
   public int hashCode() {
      int result = (59 + Float.floatToIntBits(this.getYaw())) * 59 + Float.floatToIntBits(this.getPitch());
      result = result * 59 + Float.floatToIntBits(this.getPosX());
      result = (
               (
                        (
                                 (
                                          (
                                                   (
                                                            (
                                                                     (
                                                                              (
                                                                                       (result * 59 + Float.floatToIntBits(this.getPosY())) * 59
                                                                                          + Float.floatToIntBits(this.getPosZ())
                                                                                    )
                                                                                    * 59
                                                                                 + Float.floatToIntBits(this.getVelX())
                                                                           )
                                                                           * 59
                                                                        + Float.floatToIntBits(this.getVelY())
                                                                  )
                                                                  * 59
                                                               + Float.floatToIntBits(this.getVelZ())
                                                         )
                                                         * 59
                                                      + Float.floatToIntBits(this.getWidth())
                                                )
                                                * 59
                                             + Float.floatToIntBits(this.getHeight())
                                       )
                                       * 59
                                    + Float.floatToIntBits(this.getStepHeight())
                              )
                              * 59
                           + Float.floatToIntBits(this.getJumpMovementFactor())
                     )
                     * 59
                  + Float.floatToIntBits(this.getForward())
            )
            * 59
         + Float.floatToIntBits(this.getStrafe());
      result = result * 59 + (this.isCollidedVertically() ? 79 : 97);
      result = result * 59 + (this.isCollidedHorizontally() ? 79 : 97);
      result = result * 59 + (this.isOnGround() ? 79 : 97);
      result = result * 59 + (this.isSprinting() ? 79 : 97);
      result = result * 59 + (this.isSneaking() ? 79 : 97);
      result = result * 59 + (this.isJumping() ? 79 : 97);
      ArrayList<Vec3> positions = this.getTrackedPositions();
      result = result * 59 + (positions == null ? 43 : positions.hashCode());
      EntityLivingBase entity = this.getEntity();
      return result * 59 + (entity == null ? 43 : entity.hashCode());
   }

   @Override
   public String toString() {
      return "PredictedEntity(trackedPositions="
         + this.getTrackedPositions()
         + ", entity="
         + this.getEntity()
         + ", yaw="
         + this.getYaw()
         + ", pitch="
         + this.getPitch()
         + ", posX="
         + this.getPosX()
         + ", posY="
         + this.getPosY()
         + ", posZ="
         + this.getPosZ()
         + ", velX="
         + this.getVelX()
         + ", velY="
         + this.getVelY()
         + ", velZ="
         + this.getVelZ()
         + ", width="
         + this.getWidth()
         + ", height="
         + this.getHeight()
         + ", stepHeight="
         + this.getStepHeight()
         + ", jumpMovementFactor="
         + this.getJumpMovementFactor()
         + ", forward="
         + this.getForward()
         + ", strafe="
         + this.getStrafe()
         + ", collidedVertically="
         + this.isCollidedVertically()
         + ", collidedHorizontally="
         + this.isCollidedHorizontally()
         + ", onGround="
         + this.isOnGround()
         + ", sprinting="
         + this.isSprinting()
         + ", sneaking="
         + this.isSneaking()
         + ", jumping="
         + this.isJumping()
         + ")";
   }
}
