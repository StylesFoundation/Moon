package net.optifine;

import java.util.Comparator;
import net.minecraft.src.Config;

public class CustomItemsComparator implements Comparator {
   @Override
   public int compare(Object object1, Object object2) {
      CustomItemProperties var6 = (CustomItemProperties)object1;
      CustomItemProperties var7 = (CustomItemProperties)object2;
      return var6.weight != var7.weight
         ? var7.weight - var6.weight
         : (!Config.equals(var6.basePath, var7.basePath) ? var6.basePath.compareTo(var7.basePath) : var6.name.compareTo(var7.name));
   }
}
