package net.optifine.util;

import java.util.Arrays;
import java.util.HashSet;
import net.minecraft.client.settings.KeyBinding;

public class KeyUtils {
   public static void fixKeyConflicts(KeyBinding[] arr1, KeyBinding[] arr2) {
      HashSet<Integer> var5 = new HashSet<Integer>();

      for (KeyBinding var9 : arr2) {
         var5.add(var9.getKeyCode());
      }

      HashSet<KeyBinding> var10 = new HashSet<KeyBinding>(Arrays.asList(arr1));
      Arrays.asList(arr2).forEach(var10::remove);

      for (KeyBinding var12 : var10) {
         Integer var13 = var12.getKeyCode();
         if (var5.contains(var13)) {
            var12.setKeyCode(0);
         }
      }
   }
}
