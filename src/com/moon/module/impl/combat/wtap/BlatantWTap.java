package com.moon.module.impl.combat.wtap;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.combat.WTap;
import com.moon.module.sub.SubModule;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class BlatantWTap extends SubModule<WTap> {
   public BlatantWTap() {
      super(WTap.class);
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @Override
   public void onDisable() {
      super.onDisable();
   }

   @Subscribe
   public void onPacketEvent(PacketEvent event) {
      this.getParent().setSuffix("Blatant");
      if (event.getPacket() instanceof C02PacketUseEntity useEntityPacket && useEntityPacket.getAction() == C02PacketUseEntity.Action.ATTACK && mc.thePlayer.ae()) {
         boolean wasSprinting = mc.thePlayer.isSprinting();
         if (wasSprinting) {
            mc.thePlayer.sendQueue.Method755(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
         }

         mc.thePlayer.sendQueue.Method755(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
         if (this.getParent().getDoublePacket().getValue()) {
            mc.thePlayer.sendQueue.Method755(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
            mc.thePlayer.sendQueue.Method755(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
         }

         mc.thePlayer.sendQueue.Method755(event.getPacket());
         if (!wasSprinting) {
            mc.thePlayer.sendQueue.Method755(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
         }

         event.setCancelled(true);
      }
   }
}
