package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntitySplashFX extends EntityRainFX {
   protected EntitySplashFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4);
      this.particleGravity = 0.04F;
      this.nextTextureIndexX();
      if (double6 == 0.0 && (double5 != 0.0 || double7 != 0.0)) {
         this.motionX = double5;
         this.motionY = double6 + 0.1;
         this.motionZ = double7;
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntitySplashFX(world, double3, double4, double5, double6, double7, double8);
      }
   }
}
