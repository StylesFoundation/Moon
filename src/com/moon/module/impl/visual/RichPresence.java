package com.moon.module.impl.visual;

import com.moon.Moon;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;

public class RichPresence extends Module {
   public RichPresence(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Override
   public void onEnable() {
      Moon.getInstance().getDiscordRpc().display();
   }

   @Override
   public void onDisable() {
      Moon.getInstance().getDiscordRpc().shutdown();
   }
}
