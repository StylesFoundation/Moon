package net.minecraft.block.properties;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.Collection;
import net.minecraft.util.EnumFacing;

public class PropertyDirection extends PropertyEnum<EnumFacing> {
   protected PropertyDirection(String string, Collection<EnumFacing> collection) {
      super(string, EnumFacing.class, collection);
   }

   public static PropertyDirection create(String string) {
      return create(string, Predicates.alwaysTrue());
   }

   public static PropertyDirection create(String string, Predicate<EnumFacing> predicate) {
      return create(string, Collections2.filter(Lists.newArrayList(EnumFacing.values()), predicate));
   }

   public static PropertyDirection create(String string, Collection<EnumFacing> collection) {
      return new PropertyDirection(string, collection);
   }
}
