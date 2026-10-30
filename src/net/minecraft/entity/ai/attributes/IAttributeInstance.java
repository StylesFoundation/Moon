package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.UUID;

public interface IAttributeInstance {
   IAttribute getAttribute();

   double getBaseValue();

   void setBaseValue(double double1);

   Collection<AttributeModifier> getModifiersByOperation(int integer);

   Collection<AttributeModifier> func_111122_c();

   boolean hasModifier(AttributeModifier attributeModifier);

   AttributeModifier getModifier(UUID uUID);

   void applyModifier(AttributeModifier attributeModifier);

   void removeModifier(AttributeModifier attributeModifier);

   void removeAllModifiers();

   void Method284(boolean boolean1, Collection<AttributeModifier> collection);

   double getAttributeValue();

   double Method286();
}
