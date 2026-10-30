package net.minecraft.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.IAnimals;

public enum EnumCreatureType {
   MONSTER(IMob.class, 70, Material.air, false, false),
   CREATURE(EntityAnimal.class, 10, Material.air, true, true),
   AMBIENT(EntityAmbientCreature.class, 15, Material.air, true, false),
   WATER_CREATURE(EntityWaterMob.class, 5, Material.water, true, false);

   private final Class<? extends IAnimals> creatureClass;
   private final int maxNumberOfCreature;
   private final Material creatureMaterial;
   private final boolean isPeacefulCreature;
   private final boolean isAnimal;

   EnumCreatureType(Class<? extends IAnimals> class3, int integer4, Material material, boolean boolean6, boolean boolean7) {
      this.creatureClass = class3;
      this.maxNumberOfCreature = integer4;
      this.creatureMaterial = material;
      this.isPeacefulCreature = boolean6;
      this.isAnimal = boolean7;
   }

   public Class<? extends IAnimals> getCreatureClass() {
      return this.creatureClass;
   }

   public int getMaxNumberOfCreature() {
      return this.maxNumberOfCreature;
   }

   public boolean getPeacefulCreature() {
      return this.isPeacefulCreature;
   }

   public boolean getAnimal() {
      return this.isAnimal;
   }
}
