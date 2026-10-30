package com.moon.module.impl.combat.velocity;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.KnockbackEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.combat.Velocity;
import com.moon.module.sub.SubModule;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class GrimVelocity extends SubModule<Velocity> {
   private final Queue<C0FPacketConfirmTransaction> transactionQueue = new LinkedBlockingQueue<>();
   private boolean receivedTeleport = false;
   private boolean suppressKnockback = false;

   public GrimVelocity() {
      super(Velocity.class);
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (mc.thePlayer != null && mc.thePlayer.ticksExisted > 20) {
         if (event.getPacket() instanceof C0FPacketConfirmTransaction transaction) {
            if (!this.receivedTeleport && this.suppressKnockback) {
               event.setCancelled(true);
               this.transactionQueue.add(transaction);
            }

            this.receivedTeleport = false;
         } else if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            this.receivedTeleport = true;
         }
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.POST) {
         this.suppressKnockback = false;

         C0FPacketConfirmTransaction transaction;
         while ((transaction = this.transactionQueue.poll()) != null) {
            mc.thePlayer.sendQueue.Method755(transaction);
         }

         this.getParent().setSuffix("Grim (2.0)");
      }
   }

   @Subscribe
   public void onKnockback(KnockbackEvent event) {
      if (!this.receivedTeleport) {
         event.ignoreZ(true);
         event.ignoreY(true);
         this.suppressKnockback = true;
      }
   }
}
