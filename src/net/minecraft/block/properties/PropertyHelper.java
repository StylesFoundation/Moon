package net.minecraft.block.properties;

import com.google.common.base.MoreObjects;

public abstract class PropertyHelper<T extends Comparable<T>> implements IProperty<T> {
   private final Class<T> valueClass;
   private final String name;

   protected PropertyHelper(String string, Class<T> class2) {
      this.valueClass = class2;
      this.name = string;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public Class<T> getValueClass() {
      return this.valueClass;
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("name", this.name).add("clazz", this.valueClass).add("values", this.getAllowedValues()).toString();
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         PropertyHelper var5 = (PropertyHelper)object;
         return this.valueClass.equals(var5.valueClass) && this.name.equals(var5.name);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * this.valueClass.hashCode() + this.name.hashCode();
   }
}
