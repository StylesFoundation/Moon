package net.optifine.config;

import net.minecraft.world.World;

public enum Weather {
   CLEAR,
   RAIN,
   THUNDER;

   public static Weather getWeather(World world, float float2) {
      float var5 = world.getThunderStrength(float2);
      if (var5 > 0.5F) {
         return THUNDER;
      }

      float var6 = world.getRainStrength(float2);
      return var6 > 0.5F ? RAIN : CLEAR;
   }
}
