package com.moon.module.impl.player.scaffold.components;

import com.moon.module.impl.player.ScaffoldWalk;
import com.moon.module.impl.player.scaffold.RotationHolder;
import com.moon.util.game.RaytraceUtil;
import com.moon.util.math.MathUtil;
import com.moon.util.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Rotation {
   private static final List<float[]> SPIRAL_OFFSETS = new ArrayList<>();
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final ScaffoldWalk scaffold;
   private final RotationHolder rotations = new RotationHolder();

   public void applyBackRotation() {
      this.rotations.setRotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
      boolean backOnStart = this.scaffold.getBackOnStart().getValue();
      this.rotations.setImmediate(backOnStart ? mc.thePlayer.rotationYaw - 180.0F : mc.thePlayer.rotationYaw, backOnStart ? 84.0F : mc.thePlayer.rotationPitch);
   }

   public void reset() {
   }

   public boolean isLookingAt(BlockPos blockPos, EnumFacing facing) {
      return this.isLookingAtWithRotation(blockPos, facing, this.rotations.getRotation().getX(), this.rotations.getRotation().getY(), 4.5F);
   }

   private boolean isLookingAtWithRotation(BlockPos blockPos, EnumFacing facing, float yaw, float pitch, float reach) {
      MovingObjectPosition raytraceResult = RaytraceUtil.raytraceWithEntities(yaw, pitch, reach, 1.0F);
      return raytraceResult != null && raytraceResult.getBlockPos() != null && raytraceResult.sideHit != null && raytraceResult.getBlockPos().equals(blockPos) && raytraceResult.sideHit == facing;
   }

   private void alignHitVec(EnumFacing facing, Vec3 hitVec, Vector3f target) {
      if (Math.abs(target.Method521() - hitVec.zCoord) <= 0.5 && facing.getAxis() == EnumFacing.Axis.X) {
         hitVec.zCoord = target.Method521();
      }

      if (Math.abs(target.Method519() - hitVec.xCoord) <= 0.5 && facing.getAxis() == EnumFacing.Axis.Z) {
         hitVec.xCoord = target.Method519();
      }
   }

   public void computeRotations(BlockPos blockPos, EnumFacing facing) {
      Vec3 eyesPos = mc.thePlayer.getPositionEyes(1.0F);
      float offsetY = facing.getFrontOffsetY() * 0.5F + 0.5F;
      Vector3f hitVector = Vector3f.of(blockPos).add(facing.getFrontOffsetX() * 0.5F, offsetY, facing.getFrontOffsetZ() * 0.5F);
      if (this.getScaffold().getMiddlePoint().getValue()) {
         this.alignHitVec(facing, eyesPos, hitVector);
      }

      Vector3f eyeVector = new Vector3f((float)eyesPos.xCoord, (float)eyesPos.yCoord, (float)eyesPos.zCoord);
      float[] rotations = MathUtil.rotationsBetween(eyeVector, hitVector);
      this.rotations.setTarget(rotations[0], rotations[1]);
      float smoothedYaw = this.rotations.getSmoothedRotation().getX();
      label150:
      switch ((ScaffoldWalk.RotationMode)this.scaffold.getRotationMode().getValue()) {
         case BRUTEFORCE:
            for (float[] offset : SPIRAL_OFFSETS) {
               float targetYaw = smoothedYaw + offset[0];
               float targetPitch = MathHelper.clamp_float(this.rotations.getSmoothedRotation().getY() + offset[1], -90.0F, 90.0F);
               boolean looking = this.isLookingAtWithRotation(blockPos, facing, targetYaw, targetPitch, 4.5F);
               if (looking) {
                  this.rotations.setTarget(targetYaw, targetPitch);
                  break;
               }
            }
            break;
         case BACKWARDS:
            this.rotations.setTargetYaw(mc.thePlayer.rotationYaw - 180.0F);

            for (float pitch = 65.0F; pitch <= 84.0F; pitch = (float)(pitch + 0.005)) {
               boolean looking = this.isLookingAtWithRotation(blockPos, facing, this.rotations.getTargetRotation().getX(), pitch, 4.5F);
               if (looking) {
                  this.rotations.setPitch(pitch);
                  break label150;
               }
            }
            break;
         case STATIC:
            float strafe = 0.0F;
            float forward = 0.0F;
            if (mc.gameSettings.keyBindForward.isKeyDown()) {
               forward = 1.0F;
            }

            if (mc.gameSettings.keyBindBack.isKeyDown()) {
               forward--;
            }

            if (mc.gameSettings.keyBindLeft.isKeyDown()) {
               strafe = 1.0F;
            }

            if (mc.gameSettings.keyBindRight.isKeyDown()) {
               strafe--;
            }

            float playerYaw = mc.thePlayer.rotationYaw;
            float calculatedYaw = playerYaw - 180.0F * (forward == 0.0F ? 1.0F : Math.max(forward, 0.0F)) + 90.0F * strafe / (forward != 0.0F ? 2.0F * forward : 1.0F);
            float normalizedYaw = mc.thePlayer.rotationYaw % 360.0F;
            float yawDiff = MathHelper.wrapAngleTo180_float(normalizedYaw - calculatedYaw);
            this.rotations.setTargetYaw(mc.thePlayer.rotationYaw + yawDiff);

            for (float pitch = 65.0F; pitch <= 84.0F; pitch = (float)(pitch + 0.005)) {
               boolean looking = this.isLookingAtWithRotation(blockPos, facing, this.rotations.getTargetRotation().getX(), pitch, 4.5F);
               if (looking) {
                  this.rotations.setPitch(pitch);
                  break label150;
               }
            }
            break;
         case ECONOMIC:
            float minYawDiff = Float.MAX_VALUE;
            float bestYaw = 0.0F;
            boolean foundYaw = false;

            for (float[] offset : SPIRAL_OFFSETS) {
               float testYaw = smoothedYaw + offset[0];
               float testPitch = MathHelper.clamp_float(this.rotations.getSmoothedRotation().getY() + offset[1], -90.0F, 90.0F);
               boolean looking = this.isLookingAtWithRotation(blockPos, facing, testYaw, testPitch, 4.5F);
               if (looking) {
                  float diff = Math.abs(MathHelper.wrapAngleTo180_float(testYaw - (mc.thePlayer.rotationYaw - 180.0F)));
                  if (diff < minYawDiff) {
                     foundYaw = true;
                     bestYaw = testYaw;
                     minYawDiff = diff;
                     if (diff <= 1.0F) {
                        break;
                     }
                  }
               }
            }

            if (minYawDiff > 5.0F) {
               for (float[] offset : SPIRAL_OFFSETS) {
                  float testYaw = smoothedYaw + offset[0];
                  float testPitch = MathHelper.clamp_float(this.rotations.getSmoothedRotation().getY() + offset[1], -90.0F, 90.0F);
                  boolean looking = this.isLookingAtWithRotation(blockPos, facing, testYaw, testPitch, 4.5F);
                  if (looking) {
                     float diff = Math.abs(MathHelper.wrapAngleTo180_float(testYaw - smoothedYaw));
                     if (diff < minYawDiff) {
                        foundYaw = true;
                        bestYaw = testYaw;
                        minYawDiff = diff;
                        if (diff <= 1.0F) {
                           break;
                        }
                     }
                  }
               }
            }

            if (foundYaw) {
               minYawDiff = Float.MAX_VALUE;
               float bestPitch = this.rotations.getSmoothedRotation().getY();
               boolean foundPitch = false;

               for (float[] offset : SPIRAL_OFFSETS) {
                  float testPitch = MathHelper.clamp_float(this.rotations.getSmoothedRotation().getY() + offset[1], -90.0F, 90.0F);
                  MovingObjectPosition mop = RaytraceUtil.raytraceWithEntities(bestYaw, testPitch, 4.5F, 1.0F);
                  if (mop != null
                     && mop.getBlockPos() != null
                     && mop.sideHit != null
                     && mop.getBlockPos().equals(blockPos)
                     && mop.sideHit == facing
                     && testPitch < minYawDiff) {
                     foundPitch = true;
                     bestPitch = testPitch;
                     minYawDiff = testPitch;
                     if (testPitch <= 81.0F) {
                        break;
                     }
                  }
               }

               if (foundPitch) {
                  this.rotations.setTarget(bestYaw, bestPitch);
               }
            }
      }

      boolean onlyRotateIfRequired = this.scaffold.getOnlyRotateIfRequired().getValue()
         && this.isLookingAtWithRotation(blockPos, facing, this.rotations.getRotation().getX(), this.rotations.getRotation().getY(), 4.5F);
      if (onlyRotateIfRequired) {
         this.rotations.setTarget(this.rotations.getRotation().getX(), this.rotations.getRotation().getY());
      }
   }

   private float approachAngle(float current, float target, float step) {
      float diff = MathHelper.wrapAngleTo180_float(target - current);
      if (diff > step) {
         diff = step;
      }

      if (diff < -step) {
         diff = -step;
      }

      return current + diff;
   }

   public ScaffoldWalk getScaffold() {
      return this.scaffold;
   }

   public RotationHolder getRotations() {
      return this.rotations;
   }

   public Rotation(ScaffoldWalk scaffold) {
      this.scaffold = scaffold;
   }

   static {
      float index = 0.0F;

      for (float radius = 0.0F; radius < 90.0F; radius = MathHelper.sqrt_float(++index)) {
         float angle = (float)(1.61803398875 * MathHelper.PI2 * index);
         float x = MathHelper.sin(angle) * radius;
         float y = MathHelper.cos(angle) * radius;
         SPIRAL_OFFSETS.add(new float[]{x, y});
      }
   }
}
