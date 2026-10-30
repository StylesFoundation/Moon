package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.management.LowerStringMap;

public class ServersideAttributeMap extends BaseAttributeMap {
   private final Set<IAttributeInstance> attributeInstanceSet = Sets.newHashSet();
   protected final Map<String, IAttributeInstance> descriptionToAttributeInstanceMap = new LowerStringMap<>();

   public ModifiableAttributeInstance getAttributeInstance(IAttribute iAttribute) {
      return (ModifiableAttributeInstance)super.getAttributeInstance(iAttribute);
   }

   public ModifiableAttributeInstance getAttributeInstanceByName(String string) {
      IAttributeInstance var5 = super.getAttributeInstanceByName(string);
      if (var5 == null) {
         var5 = this.descriptionToAttributeInstanceMap.get(string);
      }

      return (ModifiableAttributeInstance)var5;
   }

   @Override
   public IAttributeInstance registerAttribute(IAttribute iAttribute) {
      IAttributeInstance var5 = super.registerAttribute(iAttribute);
      if (iAttribute instanceof RangedAttribute && ((RangedAttribute)iAttribute).getDescription() != null) {
         this.descriptionToAttributeInstanceMap.put(((RangedAttribute)iAttribute).getDescription(), var5);
      }

      return var5;
   }

   @Override
   protected IAttributeInstance func_180376_c(IAttribute iAttribute) {
      return new ModifiableAttributeInstance(this, iAttribute);
   }

   @Override
   public void func_180794_a(IAttributeInstance iAttributeInstance) {
      if (iAttributeInstance.getAttribute().getShouldWatch()) {
         this.attributeInstanceSet.add(iAttributeInstance);
      }

      for (IAttribute var6 : this.field_180377_c.get(iAttributeInstance.getAttribute())) {
         ModifiableAttributeInstance var7 = this.getAttributeInstance(var6);
         if (var7 != null) {
            var7.flagForUpdate();
         }
      }
   }

   public Set<IAttributeInstance> getAttributeInstanceSet() {
      return this.attributeInstanceSet;
   }

   public Collection<IAttributeInstance> getWatchedAttributes() {
      HashSet var4 = Sets.newHashSet();

      for (IAttributeInstance var6 : this.getAllAttributes()) {
         if (var6.getAttribute().getShouldWatch()) {
            var4.add(var6);
         }
      }

      return var4;
   }
}
