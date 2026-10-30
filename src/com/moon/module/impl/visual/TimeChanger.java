package com.moon.module.impl.visual;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.IntegerSetting;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class TimeChanger extends Module {
   private final IntegerSetting time = IntegerSetting.builder("time").defaults("Time", 8000, 0, 24000, 500).build();

   public TimeChanger(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (mc.theWorld != null) {
         mc.theWorld.setWorldTime(this.time.getValue().intValue());
      }
   }

   @Subscribe
   public void onPacketEvent(PacketEvent event) {
      if (event.getPacket() instanceof S03PacketTimeUpdate) {
         event.setCancelled(true);
      }
   }
}
