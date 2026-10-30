package com.moon.module.impl.combat.killaura.components;

import com.moon.module.impl.combat.KillAura;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.util.game.RaytraceUtil;
import com.moon.util.math.MathUtil;
import com.moon.util.math.RotationUtil;
import com.moon.util.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.util.vector.Vector2f;

public class Rotation {
   public static float DEGREES_TO_RADIANS = (float) (Math.PI / 180.0);
   public static float RADIANS_TO_DEGREES = 180.0F / (float)Math.PI;
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final KillAura killAura;
   private Vector2f previousRotation;
   private Vector2f targetRotation;
   private final float[][] circularOffsets = new float[360][2];
   private int circularAngle;
   private boolean aimingAtTarget = false;

   public void init() {
      EntityPlayerSP player = mc.thePlayer;
      float yaw = player == null ? 0.0F : player.rotationYaw;
      float pitch = player == null ? 0.0F : player.rotationPitch;
      float prevYaw = player == null ? 0.0F : player.cN;
      float prevPitch = player == null ? 0.0F : player.cL;
      this.previousRotation = new Vector2f(prevYaw, prevPitch);
      this.targetRotation = new Vector2f(yaw, pitch);

      for (int i = 0; i < 360; i++) {
         float xOffset = (float)Math.sin(Math.toRadians(i)) * MathUtil.random(1.25F, 6.0F);
         float yOffset = (float)Math.cos(Math.toRadians(i)) * MathUtil.random(1.25F, 6.0F);
         this.circularOffsets[i] = new float[]{xOffset, yOffset};
      }
   }

   public void disable() {
   }

   public void update(EntityLivingBase target) {
      if (target == null) {
         if (this.killAura.getPreventBackSnap().getValue() && this.targetRotation != null && this.previousRotation != null) {
            Vector2f limitedRotation = new Vector2f(
               this.limitAngle(this.previousRotation.x, mc.thePlayer.rotationYaw, this.killAura.getRotationSpeed().getValue()),
               this.limitAngle(this.previousRotation.y, mc.thePlayer.rotationPitch, this.killAura.getRotationSpeed().getValue())
            );
            if (this.aimingAtTarget) {
               if (limitedRotation.getX() == mc.thePlayer.rotationYaw && mc.thePlayer.rotationPitch == mc.thePlayer.rotationPitch) {
                  this.aimingAtTarget = false;
               }
            } else {
               limitedRotation.set(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
            }

            this.previousRotation = this.targetRotation;
            this.targetRotation = limitedRotation;
         } else {
            this.previousRotation = new Vector2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
            this.targetRotation = new Vector2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
         }
      } else {
         if (this.killAura.getOnlyRotateWhenNeeded().getValue()) {
            MovingObjectPosition raytraceResult = RaytraceUtil.raytraceWithEntities(this.targetRotation.getX(), this.targetRotation.getY(), 6.0F, 1.0F);
            if (raytraceResult != null && raytraceResult.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && raytraceResult.entityHit == target) {
               this.previousRotation = new Vector2f(this.targetRotation.x, this.targetRotation.y);
               return;
            }
         }

         if (this.killAura.getDontRotateInHitbox().getValue()) {
            Vec3 eyePos = mc.thePlayer.getPositionEyes(1.0F);
            AxisAlignedBB targetBox = target.getEntityBoundingBox();
            if (targetBox.isVecInside(eyePos)) {
               this.previousRotation = new Vector2f(this.targetRotation.x, this.targetRotation.y);
               return;
            }
         }

         EntityPlayerSP player = mc.thePlayer;
         Vector3f playerEyePos = new Vector3f((float)player.posX, (float)player.posY + player.getEyeHeight(), (float)player.posZ);
         Vec3 aimVec = mc.thePlayer.getPositionEyes(1.0F);
         AxisAlignedBB targetBounds = new AxisAlignedBB(
            target.posX - 0.4F, target.posY, target.posZ - 0.4F, target.posX + 0.4F, target.posY + target.height, target.posZ + 0.4F
         );
         if (aimVec.xCoord > targetBounds.maxX) {
            aimVec.xCoord = targetBounds.maxX;
         } else {
            aimVec.xCoord = Math.max(aimVec.xCoord, targetBounds.minX);
         }

         if (aimVec.yCoord > targetBounds.maxY) {
            aimVec.yCoord = targetBounds.maxY;
         } else {
            aimVec.yCoord = Math.max(aimVec.yCoord, targetBounds.minY);
         }

         if (aimVec.zCoord > targetBounds.maxZ) {
            aimVec.zCoord = targetBounds.maxZ;
         } else {
            aimVec.zCoord = Math.max(aimVec.zCoord, targetBounds.minZ);
         }

         this.previousRotation = this.targetRotation;
         this.targetRotation = this.calculateRotations(playerEyePos, new Vector3f((float)aimVec.xCoord, (float)aimVec.yCoord, (float)aimVec.zCoord));
         this.aimingAtTarget = true;
      }
   }

   private Vector2f calculateRotations(Vector3f from, Vector3f to) {
      MultipleChoiceSetting randomizationTypes = this.killAura.getRandomizationTypes();
      if (randomizationTypes.hasChecked(KillAura.RandomizationType.ATTACK_POINT)) {
         to = to.add(new Vector3f((float)MathUtil.random(-0.1, 0.1), (float)MathUtil.random(-0.25, 0.25), (float)MathUtil.random(-0.1, 0.1)));
      }

      Vector3f diff = to.sub(from);
      float distanceXZ = MathHelper.sqrt_float(diff.Method519() * diff.Method519() + diff.Method521() * diff.Method521());
      float yaw = MathHelper.Method565(diff.Method521(), diff.Method519()) * RADIANS_TO_DEGREES - 90.0F;
      float pitch = -MathHelper.Method565(diff.Method520(), distanceXZ) * RADIANS_TO_DEGREES;
      yaw = this.limitAngle(this.previousRotation.x, yaw, this.killAura.getRotationSpeed().getValue());
      pitch = this.limitAngle(this.previousRotation.y, pitch, this.killAura.getRotationSpeed().getValue());
      if (randomizationTypes.hasChecked(KillAura.RandomizationType.NORMAL)) {
         FloatSetting randomizationAmount = this.killAura.getRandomizationAmount();
         yaw += MathUtil.random(-randomizationAmount.getValue(), randomizationAmount.getValue());
         pitch += MathUtil.random(-randomizationAmount.getValue(), randomizationAmount.getValue());
      }

      if (randomizationTypes.hasChecked(KillAura.RandomizationType.CIRCULAR)) {
         this.circularAngle = (int)(this.circularAngle + 36.0F * this.killAura.getCircularSpeed().getValue());
         int offsetIndex = this.circularAngle % 360;
         if (this.circularAngle > 2147483287) {
            this.circularAngle = 0;
         }

         float[] offset = this.circularOffsets[offsetIndex];
         yaw += offset[0];
         pitch += offset[1];
      }

      float prevYaw = this.previousRotation.getX();
      float prevPitch = this.previousRotation.getY();
      float deltaYaw = MathHelper.wrapAngleTo180_float(yaw - prevYaw);
      float deltaPitch = MathHelper.wrapAngleTo180_float(pitch - prevPitch);
      float sensitivityMultiplier = RotationUtil.getSensitivityMultiplier();
      int stepYaw = (int)(deltaYaw / 0.15);
      int stepPitch = (int)(deltaPitch / 0.15);
      float fixedDeltaYaw = stepYaw * sensitivityMultiplier;
      float fixedDeltaPitch = stepPitch * sensitivityMultiplier;
      yaw = (float)(prevYaw + fixedDeltaYaw * 0.15 / sensitivityMultiplier);
      pitch = MathHelper.clamp_float((float)(prevPitch + fixedDeltaPitch * 0.15 / sensitivityMultiplier), -90.0F, 90.0F);
      return new Vector2f(yaw, pitch);
   }

   private float limitAngle(float currentAngle, float targetAngle, float maxTurnSpeed) {
      float angleDelta = MathHelper.wrapAngleTo180_float(targetAngle - currentAngle);
      if (angleDelta > maxTurnSpeed) {
         angleDelta = maxTurnSpeed;
      }

      if (angleDelta < -maxTurnSpeed) {
         angleDelta = -maxTurnSpeed;
      }

      return currentAngle + angleDelta;
   }

   public boolean hasTargetRotation() {
      return this.targetRotation != null;
   }

   public boolean hasPreviousRotation() {
      return this.previousRotation != null;
   }

   public KillAura getKillAura() {
      return this.killAura;
   }

   public Vector2f getPreviousRotation() {
      return this.previousRotation;
   }

   public Vector2f getTargetRotation() {
      return this.targetRotation;
   }

   public float[][] getCircularOffsets() {
      return this.circularOffsets;
   }

   public int getCircularAngle() {
      return this.circularAngle;
   }

   public boolean isAimingAtTarget() {
      return this.aimingAtTarget;
   }

   public Rotation(KillAura killAura) {
      this.killAura = killAura;
   }
}
