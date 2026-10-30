package moom;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.spi.CleanableThreadContextMap;
import org.apache.logging.log4j.util.SortedArrayStringMap;
import org.apache.logging.log4j.util.StringMap;
import org.tinylog.provider.ContextProvider;
import org.tinylog.provider.ProviderRegistry;

public class Og implements CleanableThreadContextMap {
   private static final ContextProvider a = ProviderRegistry.getLoggingProvider().getContextProvider();
   private static final StringMap b = new SortedArrayStringMap(1);

   @Override
   public void removeAll(Iterable<String> iterable) {
      for (String var6 : iterable) {
         a.remove(var6);
      }
   }

   @Override
   public void putAll(Map<String, String> map) {
      for (Entry<String, String> var6 : map.entrySet()) {
         a.put(var6.getKey(), var6.getValue());
      }
   }

   @Override
   public StringMap getReadOnlyContextData() {
      Map<String, String> var4 = this.getCopy();
      if (var4.isEmpty()) {
         return b;
      }

      SortedArrayStringMap var5 = new SortedArrayStringMap();

      for (Entry<String, String> var7 : var4.entrySet()) {
         var5.putValue(var7.getKey(), var7.getValue());
      }

      return var5;
   }

   @Override
   public void clear() {
      a.clear();
   }

   @Override
   public boolean containsKey(String string) {
      return a.get(string) != null;
   }

   @Override
   public String get(String string) {
      return a.get(string);
   }

   @Override
   public Map<String, String> getCopy() {
      Map var4 = a.getMapping();
      return var4 == null ? new HashMap<>() : new HashMap<>(var4);
   }

   @Override
   public Map<String, String> getImmutableMapOrNull() {
      Map var3 = a.getMapping();
      return var3 == null ? null : new HashMap<>(var3);
   }

   @Override
   public boolean isEmpty() {
      return a.getMapping().isEmpty();
   }

   @Override
   public void put(String string1, String string2) {
      a.put(string1, string2);
   }

   @Override
   public void remove(String string) {
      a.remove(string);
   }

   static {
      b.freeze();
   }
}
