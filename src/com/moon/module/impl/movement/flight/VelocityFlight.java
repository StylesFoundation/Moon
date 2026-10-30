package com.moon.module.impl.movement.flight;

import com.moon.events.impl.lifecycle.LoopEvent;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.KnockbackEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Flight;
import com.moon.module.sub.SubModule;
import com.moon.util.math.Position;
import java.util.LinkedList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.util.Tuple;

public class VelocityFlight extends SubModule<Flight> {
   private final LinkedList<Tuple<Packet<?>, Long>> packetQueue = new LinkedList<>();
   private Position velocityPosition;

   public VelocityFlight() {
      super(Flight.class);
   }

   @Override
   public void onEnable() {
      this.releasePackets(0L);
   }

   @Override
   public void onDisable() {
      this.releasePackets(0L);
      this.velocityPosition = null;
   }

   @Subscribe
   public void onKnockback(KnockbackEvent event) {
      if (!event.isExplosion()) {
         ;
      }
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (!event.isSend()) {
         if (this.velocityPosition == null) {
            if (event.getPacket() instanceof S12PacketEntityVelocity velocityPacket && velocityPacket.getEntityID() == mc.thePlayer.getEntityId()) {
               Position pos = new Position(velocityPacket.getMotionZ() / 8000.0, velocityPacket.getMotionX() / 8000.0, velocityPacket.getMotionY() / 8000.0);
               double length = pos.length();
               if (length > 0.05) {
                  this.velocityPosition = pos;
               }
            }

            if (event.getPacket() instanceof S32PacketConfirmTransaction) {
            }
         } else {
            Packet packet = event.getPacket();
            long timestamp = System.currentTimeMillis();
            Tuple entry = new Tuple<>(packet, timestamp);
            this.packetQueue.add(entry);
            event.setCancelled(true);
         }
      }
   }

   @Subscribe
   public void onLoop(LoopEvent event) {
      if (event.getSequence() == LoopEvent.Sequence.PRE) {
         if (!this.packetQueue.isEmpty()) {
            if (mc.thePlayer == null || mc.thePlayer.ticksExisted <= 1 || mc.thePlayer.isDead) {
               this.getParent().toggle();
            }

            this.releasePackets(1000000L);
         }
      }
   }

   private void releasePackets(long delayMillis) {
      long currentTime = System.currentTimeMillis();

      Tuple entry;
      while ((entry = this.packetQueue.peek()) != null) {
         Packet packet = (Packet)entry.getFirst();
         long packetTime = (Long)entry.getSecond();
         if (currentTime - packetTime < delayMillis) {
            break;
         }

         if (mc.getNetHandler() != null) {
            mc.Method1013(packet);
         }

         this.packetQueue.poll();
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         if (this.velocityPosition != null) {
            S12PacketEntityVelocity velocityPacket = new S12PacketEntityVelocity(
               mc.thePlayer.getEntityId(), this.velocityPosition.getX(), this.velocityPosition.getY(), this.velocityPosition.getZ()
            );
            if (mc.getNetHandler() != null) {
               mc.Method1013(velocityPacket);
            }
         }
      }
   }

   @Subscribe
   public void onMove(MoveEvent event) {
   }
}
