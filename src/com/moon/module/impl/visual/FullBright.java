package com.moon.module.impl.visual;

import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FullBright extends Module {
   public FullBright(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Override
   public void onDisable() {
      if (mc.thePlayer != null && mc.thePlayer.isPotionActive(Potion.nightVision)) {
         mc.thePlayer.removePotionEffect(Potion.nightVision.id);
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      PotionEffect nightVisionEffect = new PotionEffect(Potion.nightVision.id, 16350, 1336);
      mc.thePlayer.addPotionEffect(nightVisionEffect);
   }
}
