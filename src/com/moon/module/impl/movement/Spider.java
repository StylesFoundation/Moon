package com.moon.module.impl.movement;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.movement.spider.VulcanSpider;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;

public class Spider extends Module {
   private final ModeSetting<Spider.Mode> mode = ModeSetting.builder("mode", Spider.Mode.class, this).defaults("Mode", Spider.Mode.VULCAN).build();

   public Spider(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      this.setSuffix(this.mode.getValue().label());
   }

   public enum Mode implements LabelEnum, SubModuleRegistry<Spider> {
      VULCAN("Vulcan", new VulcanSpider());

      private final String label;
      private final SubModule<Spider> subModule;

      @Override
      public String label() {
         return this.label;
      }

      @Override
      public SubModule<Spider> getSubModule() {
         return this.subModule;
      }

      Mode(String label, SubModule<Spider> subModule) {
         this.label = label;
         this.subModule = subModule;
      }
   }
}
