package net.optifine.util;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.src.Config;

public class EntityUtils {
   private static final Map<Class, Integer> mapIdByClass = new HashMap<>();
   private static final Map<String, Integer> mapIdByName = new HashMap<>();
   private static final Map<String, Class> mapClassByName = new HashMap<>();

   public static int getEntityIdByClass(Entity entity) {
      return entity == null ? -1 : getEntityIdByClass(entity.getClass());
   }

   public static int getEntityIdByClass(Class class1) {
      Integer var4 = mapIdByClass.get(class1);
      return var4 == null ? -1 : var4;
   }

   public static int getEntityIdByName(String string) {
      Integer var4 = mapIdByName.get(string);
      return var4 == null ? -1 : var4;
   }

   public static Class getEntityClassByName(String string) {
      return mapClassByName.get(string);
   }

   static {
      for (int var17 = 0; var17 < 1000; var17++) {
         Class var18 = EntityList.getClassFromID(var17);
         if (var18 != null) {
            String var19 = EntityList.getStringFromID(var17);
            if (var19 != null) {
               if (mapIdByClass.containsKey(var18)) {
                  Object var10001 = mapIdByClass.get(var18);
                  int var20 = var17;
                  Object var21 = var10001;
                  Class var22 = var18;
                  Config.Method252("Duplicate entity class: " + var22 + ", id1: " + var21 + ", id2: " + var20);
               }

               if (mapIdByName.containsKey(var19)) {
                  Object var29 = mapIdByName.get(var19);
                  int var23 = var17;
                  Object var24 = var29;
                  String var25 = var19;
                  Config.Method252("Duplicate entity name: " + var25 + ", id1: " + var24 + ", id2: " + var23);
               }

               if (mapClassByName.containsKey(var19)) {
                  Object var30 = mapClassByName.get(var19);
                  Class var26 = var18;
                  Object var27 = var30;
                  String var28 = var19;
                  Config.Method252("Duplicate entity name: " + var28 + ", class1: " + var27 + ", class2: " + var26);
               }

               mapIdByClass.put(var18, var17);
               mapIdByName.put(var19, var17);
               mapClassByName.put(var19, var18);
            }
         }
      }
   }
}
