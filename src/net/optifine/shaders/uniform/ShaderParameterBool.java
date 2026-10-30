package net.optifine.shaders.uniform;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.optifine.expr.IExpressionBool;

public enum ShaderParameterBool implements IExpressionBool {
   IS_ALIVE("is_alive"),
   IS_BURNING("is_burning"),
   IS_CHILD("is_child"),
   IS_HURT("is_hurt"),
   IS_IN_LAVA("is_in_lava"),
   IS_IN_WATER("is_in_water"),
   IS_INVISIBLE("is_invisible"),
   IS_ON_GROUND("is_on_ground"),
   IS_RIDDEN("is_ridden"),
   IS_RIDING("is_riding"),
   IS_SNEAKING("is_sneaking"),
   IS_SPRINTING("is_sprinting"),
   IS_WET("is_wet");

   private final String name;
   private final RenderManager renderManager;
   private static final ShaderParameterBool[] VALUES = values();

   ShaderParameterBool(String string3) {
      this.name = string3;
      this.renderManager = Minecraft.getMinecraft().getRenderManager();
   }

   public String getName() {
      return this.name;
   }

   @Override
   public boolean eval() {
      if (Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityLivingBase var5) {
         switch (this) {
            case IS_ALIVE:
               return var5.isEntityAlive();
            case IS_BURNING:
               return var5.isBurning();
            case IS_CHILD:
               return var5.isChild();
            case IS_HURT:
               return var5.hurtTime > 0;
            case IS_IN_LAVA:
               return var5.isInLava();
            case IS_IN_WATER:
               return var5.isInWater();
            case IS_INVISIBLE:
               return var5.isInvisible();
            case IS_ON_GROUND:
               return var5.onGround;
            case IS_RIDDEN:
               return var5.riddenByEntity != null;
            case IS_RIDING:
               return var5.isRiding();
            case IS_SNEAKING:
               return var5.isSneaking();
            case IS_SPRINTING:
               return var5.isSprinting();
            case IS_WET:
               return var5.isWet();
         }
      }

      return false;
   }

   public static ShaderParameterBool parse(String string) {
      if (string != null) {
         for (ShaderParameterBool var7 : VALUES) {
            if (var7.getName().equals(string)) {
               return var7;
            }
         }
      }

      return null;
   }

   private static ShaderParameterBool[] Method426() {
      return new ShaderParameterBool[]{
         IS_ALIVE, IS_BURNING, IS_CHILD, IS_HURT, IS_IN_LAVA, IS_IN_WATER, IS_INVISIBLE, IS_ON_GROUND, IS_RIDDEN, IS_RIDING, IS_SNEAKING, IS_SPRINTING, IS_WET
      };
   }
}
