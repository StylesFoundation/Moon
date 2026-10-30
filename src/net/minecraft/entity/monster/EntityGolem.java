package net.minecraft.entity.monster;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.world.World;

public abstract class EntityGolem extends EntityCreature implements IAnimals {
   public EntityGolem(World world) {
      super(world);
   }

   @Override
   public void fall(float float1, float float2) {
   }

   @Override
   protected String getLivingSound() {
      return "none";
   }

   @Override
   protected String getHurtSound() {
      return "none";
   }

   @Override
   protected String getDeathSound() {
      return "none";
   }

   @Override
   public int getTalkInterval() {
      return 120;
   }

   @Override
   protected boolean canDespawn() {
      return false;
   }
}
