package com.moon.module.impl.player;

import com.moon.events.impl.player.ServerPositionEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;

public class NoRotate extends Module {
   public NoRotate(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onServerPosition(ServerPositionEvent event) {
      event.ignoreRotation(true);
   }
}
