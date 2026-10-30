package com.moon.module.impl.movement;

import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;

public class NoJumpDelay extends Module {
   public NoJumpDelay(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      mc.thePlayer.jumpTicks = 0;
   }
}
