package net.optifine;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;

public class RandomEntity implements IRandomEntity {
   private Entity entity;

   @Override
   public int getId() {
      return (int)(this.entity.getUniqueID().getLeastSignificantBits() & 2147483647L);
   }

   @Override
   public BlockPos getSpawnPosition() {
      return this.entity.getDataWatcher().spawnPosition;
   }

   @Override
   public BiomeGenBase getSpawnBiome() {
      return this.entity.getDataWatcher().spawnBiome;
   }

   @Override
   public String getName() {
      return this.entity.hasCustomName() ? this.entity.getCustomNameTag() : null;
   }

   @Override
   public int getHealth() {
      return this.entity instanceof EntityLiving var4 ? (int)var4.getHealth() : 0;
   }

   @Override
   public int getMaxHealth() {
      return this.entity instanceof EntityLiving var4 ? (int)var4.getMaxHealth() : 0;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public void setEntity(Entity entity) {
      this.entity = entity;
   }
}
