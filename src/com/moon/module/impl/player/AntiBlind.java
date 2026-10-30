package com.moon.module.impl.player;

import com.moon.events.impl.render.RenderFogEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;

public class AntiBlind extends Module {
   public AntiBlind(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onRenderFog(RenderFogEvent event) {
      event.setCancelled(true);
   }
}
