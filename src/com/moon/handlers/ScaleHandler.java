package com.moon.handlers;

import com.moon.events.impl.render.ResizeEvent;
import com.moon.events.impl.render.ScaleEvent;
import com.moon.events.subscription.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ScaleHandler {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static ScaledResolution scaledResolution = new ScaledResolution(mc);
   private static int lastDisplayWidth = -1;
   private static int lastDisplayHeight = -1;
   private static int lastGuiScale = -1;

   @Subscribe
   public void onResize(ResizeEvent event) {
      updateScaledResolution();
   }

   @Subscribe
   public void onScale(ScaleEvent event) {
      updateScaledResolution();
   }

   public static void updateScaledResolution() {
      scaledResolution = new ScaledResolution(mc);
      lastDisplayWidth = mc.displayWidth;
      lastDisplayHeight = mc.displayHeight;
      lastGuiScale = mc.gameSettings != null ? mc.gameSettings.guiScale : -1;
   }

   public static ScaledResolution getScaledResolution() {
      int currentScale = mc.gameSettings != null ? mc.gameSettings.guiScale : -1;
      if (scaledResolution == null 
            || mc.displayWidth != lastDisplayWidth 
            || mc.displayHeight != lastDisplayHeight 
            || currentScale != lastGuiScale) {
         updateScaledResolution();
      }
      return scaledResolution;
   }
}
