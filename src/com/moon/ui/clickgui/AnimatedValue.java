package com.moon.ui.clickgui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class AnimatedValue {
   private float value = 0.0F;

   private double lerp(float start, float end, double factor) {
      factor = MathHelper.clamp_double(factor, 0.0, 1.0);
      return end + (start - end) * factor;
   }

   public void animateTo(float target, double factor) {
      this.value = (float)this.lerp(target, this.value, factor * getFrameTimeScale());
   }

   public void set(float value) {
      this.value = value;
   }

   public static float getFrameTimeScale() {
      int fps = Math.max(Minecraft.getDebugFPS(), 60);
      return 60.0F / fps;
   }

   public float get() {
      return this.value;
   }
}
