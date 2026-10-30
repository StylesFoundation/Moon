package net.optifine.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class PropertiesOrdered extends Properties {
   private final Set<Object> keysOrdered = new LinkedHashSet<>();

   @Override
   public synchronized Object put(Object object1, Object object2) {
      this.keysOrdered.add(object1);
      return super.put(object1, object2);
   }

   @Override
   public Set<Object> keySet() {
      Set var1 = super.keySet();
      this.keysOrdered.retainAll(var1);
      return Collections.unmodifiableSet(this.keysOrdered);
   }

   @Override
   public synchronized Enumeration<Object> keys() {
      return Collections.enumeration(this.keySet());
   }
}
