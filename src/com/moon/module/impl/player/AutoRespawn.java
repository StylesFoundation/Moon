package com.moon.module.impl.player;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;

public class AutoRespawn extends Module {
   public AutoRespawn(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.PRE && mc.thePlayer != null && (mc.thePlayer.isDead || mc.thePlayer.getHealth() <= 0.0F)) {
         mc.thePlayer.respawnPlayer();
         if (mc.currentScreen != null) {
            mc.displayGuiScreen(null);
         }
      }
   }
}
