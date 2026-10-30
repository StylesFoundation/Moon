package net.optifine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicLightsMap {
   private final Map<Integer, DynamicLight> map = new HashMap<>();
   private final List<DynamicLight> list = new ArrayList<>();
   private boolean dirty = false;

   public DynamicLight put(int integer, DynamicLight dynamicLight) {
      DynamicLight var3 = this.map.put(integer, dynamicLight);
      this.setDirty();
      return var3;
   }

   public DynamicLight get(int integer) {
      return this.map.get(integer);
   }

   public int size() {
      return this.map.size();
   }

   public DynamicLight remove(int integer) {
      DynamicLight var5 = this.map.remove(integer);
      if (var5 != null) {
         this.setDirty();
      }

      return var5;
   }

   public void clear() {
      this.map.clear();
      this.list.clear();
      this.setDirty();
   }

   private void setDirty() {
      this.dirty = true;
   }

   public List<DynamicLight> valueList() {
      if (this.dirty) {
         this.list.clear();
         this.list.addAll(this.map.values());
         this.dirty = false;
      }

      return this.list;
   }
}
