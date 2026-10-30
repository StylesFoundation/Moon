package net.minecraft.block.properties;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;

public class PropertyBool extends PropertyHelper<Boolean> {
   private final ImmutableSet<Boolean> allowedValues = ImmutableSet.of(Boolean.TRUE, Boolean.FALSE);

   protected PropertyBool(String string) {
      super(string, Boolean.class);
   }

   @Override
   public Collection<Boolean> getAllowedValues() {
      return this.allowedValues;
   }

   public static PropertyBool create(String string) {
      return new PropertyBool(string);
   }

   public String getName(Boolean boolean1) {
      return boolean1.toString();
   }
}
