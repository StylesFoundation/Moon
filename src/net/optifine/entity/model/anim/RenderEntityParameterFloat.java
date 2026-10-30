package net.optifine.entity.model.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpressionFloat;

public enum RenderEntityParameterFloat implements IExpressionFloat {
   LIMB_SWING("limb_swing"),
   LIMB_SWING_SPEED("limb_speed"),
   AGE("age"),
   HEAD_YAW("head_yaw"),
   HEAD_PITCH("head_pitch"),
   SCALE("scale"),
   HEALTH("health"),
   HURT_TIME("hurt_time"),
   IDLE_TIME("idle_time"),
   MAX_HEALTH("max_health"),
   MOVE_FORWARD("move_forward"),
   MOVE_STRAFING("move_strafing"),
   PARTIAL_TICKS("partial_ticks"),
   POS_X("pos_x"),
   POS_Y("pos_y"),
   POS_Z("pos_z"),
   REVENGE_TIME("revenge_time"),
   SWING_PROGRESS("swing_progress");

   private final String name;
   private final RenderManager renderManager;
   private static final RenderEntityParameterFloat[] VALUES = values();

   RenderEntityParameterFloat(String string3) {
      this.name = string3;
      this.renderManager = Minecraft.getMinecraft().getRenderManager();
   }

   public String getName() {
      return this.name;
   }

   @Override
   public ExpressionType getExpressionType() {
      return ExpressionType.FLOAT;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public float eval() {
      Render var4 = this.renderManager.renderRender;
      if (var4 != null && var4 instanceof RendererLivingEntity var5) {
         switch (this) {
            case LIMB_SWING:
               return var5.renderLimbSwing;
            case LIMB_SWING_SPEED:
               return var5.renderLimbSwingAmount;
            case AGE:
               return var5.renderAgeInTicks;
            case HEAD_YAW:
               return var5.renderHeadYaw;
            case HEAD_PITCH:
               return var5.renderHeadPitch;
            case SCALE:
               return var5.renderScaleFactor;
            default:
               EntityLivingBase var6 = var5.renderEntity;
               if (var6 == null) {
                  return 0.0F;
               }

               switch (this) {
                  case HEALTH:
                     return var6.getHealth();
                  case HURT_TIME:
                     return var6.hurtTime;
                  case IDLE_TIME:
                     return var6.getAge();
                  case MAX_HEALTH:
                     return var6.getMaxHealth();
                  case MOVE_FORWARD:
                     return var6.moveForward;
                  case MOVE_STRAFING:
                     return var6.moveStrafing;
                  case POS_X:
                     return (float)var6.posX;
                  case POS_Y:
                     return (float)var6.posY;
                  case POS_Z:
                     return (float)var6.posZ;
                  case REVENGE_TIME:
                     return var6.getRevengeTimer();
                  case SWING_PROGRESS:
                     return var6.getSwingProgress(var5.renderPartialTicks);
               }
         }
      }

      return 0.0F;
   }

   public static RenderEntityParameterFloat parse(String string) {
      if (string != null) {
         for (RenderEntityParameterFloat var7 : VALUES) {
            if (var7.getName().equals(string)) {
               return var7;
            }
         }
      }

      return null;
   }

   private static RenderEntityParameterFloat[] Method945() {
      return new RenderEntityParameterFloat[]{
         LIMB_SWING,
         LIMB_SWING_SPEED,
         AGE,
         HEAD_YAW,
         HEAD_PITCH,
         SCALE,
         HEALTH,
         HURT_TIME,
         IDLE_TIME,
         MAX_HEALTH,
         MOVE_FORWARD,
         MOVE_STRAFING,
         PARTIAL_TICKS,
         POS_X,
         POS_Y,
         POS_Z,
         REVENGE_TIME,
         SWING_PROGRESS
      };
   }
}
