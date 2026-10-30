package com.moon.util.discord;

import com.moon.Moon;
import com.moon.module.Module;
import com.moon.module.ModuleManager;
import java.util.Collection;

public class EnabledModulesText implements DiscordRpcText {
   @Override
   public String getText() {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      Collection<Module> modules = moduleManager.getRegistry().getModules();
      int enabledCount = (int)modules.stream().filter(Module::isEnabled).count();
      return enabledCount + "/" + modules.size() + " modules enabled";
   }
}
