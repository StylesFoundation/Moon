package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityParticleEmitter extends EntityFX {
   private final Entity attachedEntity;
   private int age;
   private final int lifetime;
   private final EnumParticleTypes particleTypes;

   public EntityParticleEmitter(World world, Entity entity, EnumParticleTypes enumParticleTypes) {
      super(world, entity.posX, entity.getEntityBoundingBox().minY + entity.height / 2.0F, entity.posZ, entity.motionX, entity.motionY, entity.motionZ);
      this.attachedEntity = entity;
      this.lifetime = 3;
      this.particleTypes = enumParticleTypes;
      this.onUpdate();
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
   }

   @Override
   public void onUpdate() {
      for (int var3 = 0; var3 < 16; var3++) {
         double var4 = this.rand.nextFloat() * 2.0F - 1.0F;
         double var6 = this.rand.nextFloat() * 2.0F - 1.0F;
         double var8 = this.rand.nextFloat() * 2.0F - 1.0F;
         if (var4 * var4 + var6 * var6 + var8 * var8 <= 1.0) {
            double var10 = this.attachedEntity.posX + var4 * this.attachedEntity.width / 4.0;
            double var12 = this.attachedEntity.getEntityBoundingBox().minY + this.attachedEntity.height / 2.0F + var6 * this.attachedEntity.height / 4.0;
            double var14 = this.attachedEntity.posZ + var8 * this.attachedEntity.width / 4.0;
            this.worldObj.spawnParticle(this.particleTypes, false, var10, var12, var14, var4, var6 + 0.2, var8);
         }
      }

      this.age++;
      if (this.age >= this.lifetime) {
         this.setDead();
      }
   }

   @Override
   public int getFXLayer() {
      return 3;
   }
}
