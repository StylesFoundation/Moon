package net.optifine.util;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;

public class FrameEvent {
   private static final Map<String, Integer> mapEventFrames = new HashMap<>();

   public static boolean isActive(String string, int integer) {
      synchronized (mapEventFrames) {
         int var6 = Minecraft.getMinecraft().entityRenderer.frameCount;
         int var8 = mapEventFrames.computeIfAbsent(string, k -> var6);
         if (var6 > var8 && var6 < var8 + integer) {
            return false;
         }

         mapEventFrames.put(string, var6);
         return true;
      }
   }
}
