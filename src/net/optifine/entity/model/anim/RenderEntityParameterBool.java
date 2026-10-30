package net.optifine.entity.model.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpressionBool;

public enum RenderEntityParameterBool implements IExpressionBool {
   IS_ALIVE("is_alive"),
   IS_BURNING("is_burning"),
   IS_CHILD("is_child"),
   IS_GLOWING("is_glowing"),
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
   private static final RenderEntityParameterBool[] VALUES = values();

   RenderEntityParameterBool(String string3) {
      this.name = string3;
      this.renderManager = Minecraft.getMinecraft().getRenderManager();
   }

   public String getName() {
      return this.name;
   }

   @Override
   public ExpressionType getExpressionType() {
      return ExpressionType.BOOL;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public boolean eval() {
      Render var4 = this.renderManager.renderRender;
      if (var4 != null && var4 instanceof RendererLivingEntity) {
         RendererLivingEntity var5 = (RendererLivingEntity)var4;
         EntityLivingBase var6 = var5.renderEntity;
         if (var6 == null) {
            return false;
         }

         switch (this) {
            case IS_ALIVE:
               return var6.isEntityAlive();
            case IS_BURNING:
               return var6.isBurning();
            case IS_CHILD:
               return var6.isChild();
            case IS_HURT:
               return var6.hurtTime > 0;
            case IS_IN_LAVA:
               return var6.isInLava();
            case IS_IN_WATER:
               return var6.isInWater();
            case IS_INVISIBLE:
               return var6.isInvisible();
            case IS_ON_GROUND:
               return var6.onGround;
            case IS_RIDDEN:
               return var6.riddenByEntity != null;
            case IS_RIDING:
               return var6.isRiding();
            case IS_SNEAKING:
               return var6.isSneaking();
            case IS_SPRINTING:
               return var6.isSprinting();
            case IS_WET:
               return var6.isWet();
         }
      }

      return false;
   }

   public static RenderEntityParameterBool parse(String string) {
      if (string != null) {
         for (RenderEntityParameterBool var7 : VALUES) {
            if (var7.getName().equals(string)) {
               return var7;
            }
         }
      }

      return null;
   }

   private static RenderEntityParameterBool[] Method1028() {
      return new RenderEntityParameterBool[]{
         IS_ALIVE,
         IS_BURNING,
         IS_CHILD,
         IS_GLOWING,
         IS_HURT,
         IS_IN_LAVA,
         IS_IN_WATER,
         IS_INVISIBLE,
         IS_ON_GROUND,
         IS_RIDDEN,
         IS_RIDING,
         IS_SNEAKING,
         IS_SPRINTING,
         IS_WET
      };
   }
}
