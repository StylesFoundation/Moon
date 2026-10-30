package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityHugeExplodeFX extends EntityFX {
   private int timeSinceStart;
   private final int maximumTime = 8;

   protected EntityHugeExplodeFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, 0.0, 0.0, 0.0);
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
   }

   @Override
   public void onUpdate() {
      for (int var3 = 0; var3 < 6; var3++) {
         double var4 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
         double var6 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
         double var8 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, var4, var6, var8, this.timeSinceStart / 8.0F, 0.0, 0.0);
      }

      this.timeSinceStart++;
      if (this.timeSinceStart == 8) {
         this.setDead();
      }
   }

   @Override
   public int getFXLayer() {
      return 1;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityHugeExplodeFX(world, double3, double4, double5, double6, double7, double8);
      }
   }
}
