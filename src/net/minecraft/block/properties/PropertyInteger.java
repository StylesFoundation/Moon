package net.minecraft.block.properties;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;

public class PropertyInteger extends PropertyHelper<Integer> {
   private final ImmutableSet<Integer> allowedValues;

   protected PropertyInteger(String string, int integer2, int integer3) {
      super(string, Integer.class);
      if (integer2 < 0) {
         throw new IllegalArgumentException("Min value of " + string + " must be 0 or greater");
      }

      if (integer3 <= integer2) {
         throw new IllegalArgumentException("Max value of " + string + " must be greater than min (" + integer2 + ")");
      }

      HashSet var6 = Sets.newHashSet();

      for (int var7 = integer2; var7 <= integer3; var7++) {
         var6.add(var7);
      }

      this.allowedValues = ImmutableSet.copyOf(var6);
   }

   @Override
   public Collection<Integer> getAllowedValues() {
      return this.allowedValues;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }

      if (object == null || this.getClass() != object.getClass()) {
         return false;
      }

      if (!super.equals(object)) {
         return false;
      }

      PropertyInteger var5 = (PropertyInteger)object;
      return this.allowedValues.equals(var5.allowedValues);
   }

   @Override
   public int hashCode() {
      int var4 = super.hashCode();
      return 31 * var4 + this.allowedValues.hashCode();
   }

   public static PropertyInteger create(String string, int integer2, int integer3) {
      return new PropertyInteger(string, integer2, integer3);
   }

   public String getName(Integer integer) {
      return integer.toString();
   }
}
