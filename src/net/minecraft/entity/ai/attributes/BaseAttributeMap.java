package net.minecraft.entity.ai.attributes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.management.LowerStringMap;

public abstract class BaseAttributeMap {
   protected final Map<IAttribute, IAttributeInstance> attributes = Maps.newHashMap();
   protected final Map<String, IAttributeInstance> attributesByName = new LowerStringMap<>();
   protected final Multimap<IAttribute, IAttribute> field_180377_c = HashMultimap.create();

   public IAttributeInstance getAttributeInstance(IAttribute iAttribute) {
      return this.attributes.get(iAttribute);
   }

   public IAttributeInstance getAttributeInstanceByName(String string) {
      return this.attributesByName.get(string);
   }

   public IAttributeInstance registerAttribute(IAttribute iAttribute) {
      if (this.attributesByName.containsKey(iAttribute.getAttributeUnlocalizedName())) {
         throw new IllegalArgumentException("Attribute is already registered!");
      }

      IAttributeInstance var5 = this.func_180376_c(iAttribute);
      this.attributesByName.put(iAttribute.getAttributeUnlocalizedName(), var5);
      this.attributes.put(iAttribute, var5);

      for (IAttribute var6 = iAttribute.func_180372_d(); var6 != null; var6 = var6.func_180372_d()) {
         this.field_180377_c.put(var6, iAttribute);
      }

      return var5;
   }

   protected abstract IAttributeInstance func_180376_c(IAttribute iAttribute);

   public Collection<IAttributeInstance> getAllAttributes() {
      return this.attributesByName.values();
   }

   public void func_180794_a(IAttributeInstance iAttributeInstance) {
   }

   public void removeAttributeModifiers(Multimap<String, AttributeModifier> multimap) {
      for (Entry var6 : multimap.entries()) {
         IAttributeInstance var7 = this.getAttributeInstanceByName((String)var6.getKey());
         if (var7 != null) {
            var7.removeModifier((AttributeModifier)var6.getValue());
         }
      }
   }

   public void applyAttributeModifiers(Multimap<String, AttributeModifier> multimap) {
      for (Entry var6 : multimap.entries()) {
         IAttributeInstance var7 = this.getAttributeInstanceByName((String)var6.getKey());
         if (var7 != null) {
            var7.removeModifier((AttributeModifier)var6.getValue());
            var7.applyModifier((AttributeModifier)var6.getValue());
         }
      }
   }
}
