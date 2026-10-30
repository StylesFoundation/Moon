package net.minecraft.block.properties;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import net.minecraft.util.IStringSerializable;

public class PropertyEnum<T extends Enum<T> & IStringSerializable> extends PropertyHelper<T> {
   private final ImmutableSet<T> allowedValues;
   private final Map<String, T> nameToValue = Maps.newHashMap();

   protected PropertyEnum(String string, Class<T> class2, Collection<T> collection) {
      super(string, class2);
      this.allowedValues = ImmutableSet.copyOf(collection);

      for (Enum var7 : collection) {
         String var8 = ((IStringSerializable)var7).getName();
         if (this.nameToValue.containsKey(var8)) {
            throw new IllegalArgumentException("Multiple values have the same name '" + var8 + "'");
         }

         this.nameToValue.put(var8, (T)var7);
      }
   }

   @Override
   public Collection<T> getAllowedValues() {
      return this.allowedValues;
   }

   public String getName(T enum1) {
      return ((IStringSerializable)enum1).getName();
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String string, Class<T> class2) {
      return create(string, class2, Predicates.alwaysTrue());
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String string, Class<T> class2, Predicate<T> predicate) {
      return create(string, class2, Collections2.filter(Lists.newArrayList(class2.getEnumConstants()), predicate));
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String string, Class<T> class2, T... arr) {
      return create(string, class2, Lists.newArrayList(arr));
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String string, Class<T> class2, Collection<T> collection) {
      return new PropertyEnum<>(string, class2, collection);
   }
}
