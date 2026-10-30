package com.moon.module.impl.combat.killaura;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.module.impl.combat.KillAura;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;

public abstract class AutoBlock {
   protected static final Minecraft client = Minecraft.getMinecraft();
   protected KillAura killAura;

   public abstract void onPacket(PacketEvent event);

   public abstract void onUpdate(UpdateEvent event);

   public abstract void onEnable();

   public abstract void onDisable();

   public abstract void startBlocking();

   public abstract void stopBlocking();

   public boolean isHoldingSword() {
      return client.thePlayer != null && client.thePlayer.getHeldItem() != null && client.thePlayer.getHeldItem().getItem() instanceof ItemSword;
   }

   protected void pressUseKey() {
      KeyBinding.setKeyBindState(client.gameSettings.keyBindUseItem.getKeyCode(), true);
   }

   protected void releaseUseKey() {
      KeyBinding.setKeyBindState(client.gameSettings.keyBindUseItem.getKeyCode(), false);
   }

   public KillAura getKillAura() {
      return this.killAura;
   }

   public AutoBlock setKillAura(KillAura killAura) {
      this.killAura = killAura;
      return this;
   }
}
