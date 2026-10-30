package com.moon.module.impl.movement;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class Sprint extends Module {
   private final BooleanSetting omni = BooleanSetting.builder("omni").defaults("Multi-directional (Omni)", false).build();
   private final BooleanSetting noPackets = BooleanSetting.builder("no-packets").defaults("No Packets", false).build();

   public Sprint(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Override
   public void onDisable() {
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
   }

   @Subscribe(priority = Priority.LOWEST)
   public void onTick(TickEvent event) {
      if (mc.theWorld != null && mc.thePlayer != null) {
         if (event.getSequence() == TickEvent.Sequence.PRE) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
         }
      }
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (event.getPacket() instanceof C0BPacketEntityAction actionPacket
         && this.noPackets.getValue()
         && (actionPacket.getAction() == C0BPacketEntityAction.Action.START_SPRINTING || actionPacket.getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING)) {
         event.setCancelled(true);
      }
   }

   public BooleanSetting getOmni() {
      return this.omni;
   }

   public BooleanSetting getNoPackets() {
      return this.noPackets;
   }
}
