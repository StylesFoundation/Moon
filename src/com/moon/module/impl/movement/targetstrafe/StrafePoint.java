package com.moon.module.impl.movement.targetstrafe;

import com.moon.util.render.LegacyGlUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class StrafePoint {
   private final EntityLivingBase target;
   private final Vec3 offset;
   private final Vec3 position;
   private final boolean valid;

   public Vec3 getInterpolatedPosition() {
      float partialTicks = Minecraft.getMinecraft().timer.renderPartialTicks;
      double posX = LegacyGlUtil.lerp(this.target.posX, this.target.prevPosX, partialTicks);
      double posY = LegacyGlUtil.lerp(this.target.posY, this.target.prevPosY, partialTicks);
      double posZ = LegacyGlUtil.lerp(this.target.posZ, this.target.prevPosZ, partialTicks);
      Vec3 interpolatedPos = new Vec3(posX, posY, posZ);
      return interpolatedPos.add(this.offset);
   }

   public EntityLivingBase getTarget() {
      return this.target;
   }

   public Vec3 getOffset() {
      return this.offset;
   }

   public Vec3 getPosition() {
      return this.position;
   }

   public boolean isValid() {
      return this.valid;
   }

   public StrafePoint(EntityLivingBase target, Vec3 offset, Vec3 position, boolean valid) {
      this.target = target;
      this.offset = offset;
      this.position = position;
      this.valid = valid;
   }

   public static boolean alwaysFalse() {
      return false;
   }

   static {
      if (alwaysFalse()) {
      }
   }
}
