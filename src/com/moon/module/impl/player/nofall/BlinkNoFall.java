package com.moon.module.impl.player.nofall;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.player.NoFall;
import com.moon.module.sub.SubModule;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;

public class BlinkNoFall extends SubModule<NoFall> {
   private final List<Packet<?>> queuedPackets = new ArrayList<>();

   public BlinkNoFall() {
      super(NoFall.class);
   }

   @Override
   public void onDisable() {
      this.queuedPackets.forEach(mc.thePlayer.sendQueue::Method755);
      this.queuedPackets.clear();
   }

   @Subscribe
   public void onPacketEvent(PacketEvent event) {
      if (event.isSend()) {
         if (mc.thePlayer.fallDistance >= 2.5
            && !mc.thePlayer.capabilities.isFlying
            && !mc.thePlayer.capabilities.allowFlying
            && this.hasGroundBelow()) {
            this.queuedPackets.add(event.getPacket());
            event.setCancelled(true);
         } else if (mc.thePlayer.onGround
            || mc.thePlayer.fallDistance <= 2.0F
            || mc.thePlayer.posY < 0.0
            || mc.thePlayer.isDead
            || mc.playerController.isSpectator()) {
            this.queuedPackets.forEach(mc.thePlayer.sendQueue::Method755);
            this.queuedPackets.clear();
         }
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE && mc.thePlayer.fallDistance >= 2.5 && this.hasGroundBelow()) {
         event.setOnGround(true);
      }
   }

   public boolean hasGroundBelow() {
      if (mc.thePlayer == null) {
         return true;
      }

      for (int offsetY = 0; offsetY < mc.thePlayer.posY + mc.thePlayer.getEyeHeight(); offsetY++) {
         AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox().offset(0.0, -offsetY, 0.0);
         if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, boundingBox).isEmpty()) {
            return true;
         }
      }

      return false;
   }
}
