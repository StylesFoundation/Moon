package com.moon.module.impl.player;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import net.minecraft.item.ItemBlock;

public class FastPlace extends Module {
   public final BooleanSetting blockOnly = BooleanSetting.builder("block-only").defaults("Block only", true).build();

   public FastPlace(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (mc.thePlayer != null) {
         boolean canFastPlace = !this.blockOnly.getValue() || mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock;
         if (event.getSequence() == TickEvent.Sequence.PRE && canFastPlace) {
            mc.rightClickDelayTimer = 0;
         }
      }
   }
}
