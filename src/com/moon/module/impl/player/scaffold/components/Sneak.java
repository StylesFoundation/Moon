package com.moon.module.impl.player.scaffold.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Sneak {
   static final Minecraft mc = Minecraft.getMinecraft();
   private int sneakTicks;
   private double distance;

   public void update() {
      int keyCode = mc.gameSettings.keyBindSneak.getKeyCode();
      boolean sneaking = Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && mc.currentScreen == null || this.sneakTicks-- > 0;
      if (sneaking) {
         this.distance = 0.0;
      } else {
         this.distance = this.distance + mc.thePlayer.j;
      }

      KeyBinding.setKeyBindState(keyCode, sneaking);
   }

   public void extend(int ticks) {
      this.sneakTicks = Math.max(this.sneakTicks, ticks);
   }

   public void reset() {
      this.sneakTicks = 0;
      this.distance = 0.0;
      this.update();
   }

   public double getDistance() {
      return this.distance;
   }
}
