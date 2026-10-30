package com.moon.module.impl.movement.antivoid.impl;

import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.antivoid.AntiVoidMode;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PacketAntiVoid extends AntiVoidMode {
   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE
         && mc.thePlayer.fallDistance > this.getParent().getFallDistance().getValue()
         && !this.hasGroundBelow()) {
         mc.thePlayer
            .sendQueue
            .Method755(
               new C03PacketPlayer.C04PacketPlayerPosition(
                  mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.fallDistance, mc.thePlayer.posZ, false
               )
            );
      }
   }
}
