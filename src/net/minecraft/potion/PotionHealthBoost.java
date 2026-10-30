package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.util.ResourceLocation;

public class PotionHealthBoost extends Potion {
   public PotionHealthBoost(int integer1, ResourceLocation resourceLocation, boolean boolean3, int integer4) {
      super(integer1, resourceLocation, boolean3, integer4);
   }

   @Override
   public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBase, BaseAttributeMap baseAttributeMap, int integer) {
      super.removeAttributesModifiersFromEntity(entityLivingBase, baseAttributeMap, integer);
      if (entityLivingBase.getHealth() > entityLivingBase.getMaxHealth()) {
         entityLivingBase.setHealth(entityLivingBase.getMaxHealth());
      }
   }
}
