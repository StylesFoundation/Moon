package net.minecraft.potion;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.ResourceLocation;

public class PotionAttackDamage extends Potion {
   protected PotionAttackDamage(int integer1, ResourceLocation resourceLocation, boolean boolean3, int integer4) {
      super(integer1, resourceLocation, boolean3, integer4);
   }

   @Override
   public double getAttributeModifierAmount(int integer, AttributeModifier attributeModifier) {
      return this.id == Potion.weakness.id ? -0.5F * (integer + 1) : 1.3 * (integer + 1);
   }
}
