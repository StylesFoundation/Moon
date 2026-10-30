package com.moon.util.render;

import com.moon.Moon;
import com.moon.module.impl.visual.Camera;
import net.minecraft.client.particle.EntityFX;

public class ParticleCullingUtil {
   private static Camera cameraModule = Moon.getInstance().getModuleManager().getModule(Camera.class);

   public static boolean isParticleVisible(EntityFX particle) {
      return FrustumUtil.isVisible(particle.getEntityBoundingBox()) || !cameraModule.particleCulling.getValue();
   }
}
