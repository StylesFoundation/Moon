package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.moon.Moon;
import com.moon.module.impl.visual.Camera;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;

public class ModifiableAttributeInstance implements IAttributeInstance {
   private final BaseAttributeMap attributeMap;
   private final IAttribute genericAttribute;
   private final Map<Integer, Set<AttributeModifier>> mapByOperation = Maps.newHashMap();
   private final Map<String, Set<AttributeModifier>> mapByName = Maps.newHashMap();
   private final Map<UUID, AttributeModifier> mapByUUID = Maps.newHashMap();
   private double baseValue;
   private boolean needsUpdate = true;
   private boolean h = true;
   private double cachedValue;
   private double j;
   private Camera k;

   public ModifiableAttributeInstance(BaseAttributeMap baseAttributeMap, IAttribute iAttribute) {
      this.attributeMap = baseAttributeMap;
      this.genericAttribute = iAttribute;
      this.baseValue = iAttribute.getDefaultValue();

      for (int var6 = 0; var6 < 3; var6++) {
         this.mapByOperation.put(var6, Sets.newHashSet());
      }
   }

   @Override
   public IAttribute getAttribute() {
      return this.genericAttribute;
   }

   @Override
   public double getBaseValue() {
      return this.baseValue;
   }

   @Override
   public void setBaseValue(double double1) {
      if (double1 != this.getBaseValue()) {
         this.baseValue = double1;
         this.flagForUpdate();
      }
   }

   @Override
   public Collection<AttributeModifier> getModifiersByOperation(int integer) {
      return this.mapByOperation.get(integer);
   }

   @Override
   public Collection<AttributeModifier> func_111122_c() {
      HashSet var4 = Sets.newHashSet();

      for (int var5 = 0; var5 < 3; var5++) {
         var4.addAll(this.getModifiersByOperation(var5));
      }

      return var4;
   }

   @Override
   public AttributeModifier getModifier(UUID uUID) {
      return this.mapByUUID.get(uUID);
   }

   @Override
   public boolean hasModifier(AttributeModifier attributeModifier) {
      return this.mapByUUID.get(attributeModifier.getID()) != null;
   }

   @Override
   public void applyModifier(AttributeModifier attributeModifier) {
      if (this.getModifier(attributeModifier.getID()) != null) {
         throw new IllegalArgumentException("Modifier is already applied on this attribute!");
      }

      Set var5 = this.mapByName.computeIfAbsent(attributeModifier.getName(), string -> Sets.newHashSet());
      this.mapByOperation.get(attributeModifier.getOperation()).add(attributeModifier);
      var5.add(attributeModifier);
      this.mapByUUID.put(attributeModifier.getID(), attributeModifier);
      this.flagForUpdate();
   }

   protected void flagForUpdate() {
      this.needsUpdate = true;
      this.h = true;
      this.attributeMap.func_180794_a(this);
   }

   @Override
   public void removeModifier(AttributeModifier attributeModifier) {
      for (int var5 = 0; var5 < 3; var5++) {
         Set var6 = this.mapByOperation.get(var5);
         var6.remove(attributeModifier);
      }

      Set var7 = this.mapByName.get(attributeModifier.getName());
      if (var7 != null) {
         var7.remove(attributeModifier);
         if (var7.isEmpty()) {
            this.mapByName.remove(attributeModifier.getName());
         }
      }

      this.mapByUUID.remove(attributeModifier.getID());
      this.flagForUpdate();
   }

   @Override
   public void removeAllModifiers() {
      Collection<AttributeModifier> var4 = this.func_111122_c();
      if (var4 != null) {
         for (AttributeModifier var6 : Lists.newArrayList(var4)) {
            this.removeModifier(var6);
         }
      }
   }

   @Override
   public void Method284(boolean boolean1, Collection<AttributeModifier> collection) {
      Collection<AttributeModifier> var6 = this.func_111122_c();
      if (var6 != null) {
         for (AttributeModifier var8 : Lists.newArrayList(var6)) {
            if (var8.getID() != EntityLivingBase.sprintingSpeedBoostModifierUUID || boolean1 || this.Method285(collection)) {
               this.removeModifier(var8);
            }
         }
      }
   }

   private boolean Method285(Collection<AttributeModifier> collection) {
      for (AttributeModifier var6 : collection) {
         if (var6.getID().equals(EntityLivingBase.sprintingSpeedBoostModifierUUID)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public double getAttributeValue() {
      if (this.needsUpdate) {
         this.cachedValue = this.Method287(false);
         this.needsUpdate = false;
      }

      return this.cachedValue;
   }

   @Override
   public double Method286() {
      if (this.h) {
         this.j = this.Method287(true);
         this.h = false;
      }

      return this.j;
   }

   private double Method287(boolean boolean1) {
      double var5 = this.getBaseValue();

      for (AttributeModifier var8 : this.func_180375_b(0)) {
         var5 += var8.getAmount();
      }

      double var11 = var5;

      for (AttributeModifier var10 : this.func_180375_b(1)) {
         var11 += var5 * var10.getAmount();
      }

      for (AttributeModifier var13 : this.func_180375_b(2)) {
         if (var13.getAmount() % 0.2F != 0.0 || this.Method288() || !boolean1) {
            var11 *= 1.0 + var13.getAmount();
         }
      }

      return this.genericAttribute.clampValue(var11);
   }

   private Collection<AttributeModifier> func_180375_b(int integer) {
      HashSet var5 = Sets.newHashSet(this.getModifiersByOperation(integer));

      for (IAttribute var6 = this.genericAttribute.func_180372_d(); var6 != null; var6 = var6.func_180372_d()) {
         IAttributeInstance var7 = this.attributeMap.getAttributeInstance(var6);
         if (var7 != null) {
            var5.addAll(var7.getModifiersByOperation(integer));
         }
      }

      return var5;
   }

   private boolean Method288() {
      if (this.k == null) {
         this.k = Moon.getInstance().getModuleManager().getModule(Camera.class);
      }

      return !this.k.isEnabled() || !this.k.removeSpeedEffect.getValue();
   }
}
