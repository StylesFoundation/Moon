package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.util.ResourceLocation;

public class PotionAbsorption extends Potion {
   protected PotionAbsorption(int integer1, ResourceLocation resourceLocation, boolean boolean3, int integer4) {
      super(integer1, resourceLocation, boolean3, integer4);
   }

   @Override
   public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBase, BaseAttributeMap baseAttributeMap, int integer) {
      entityLivingBase.setAbsorptionAmount(entityLivingBase.getAbsorptionAmount() - 4 * (integer + 1));
      super.removeAttributesModifiersFromEntity(entityLivingBase, baseAttributeMap, integer);
   }

   @Override
   public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBase, BaseAttributeMap baseAttributeMap, int integer) {
      entityLivingBase.setAbsorptionAmount(entityLivingBase.getAbsorptionAmount() + 4 * (integer + 1));
      super.applyAttributesModifiersToEntity(entityLivingBase, baseAttributeMap, integer);
   }
}
