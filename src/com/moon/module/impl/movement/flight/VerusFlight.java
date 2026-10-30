package com.moon.module.impl.movement.flight;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.BlockBBEvent;
import com.moon.events.impl.player.KillAuraEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Flight;
import com.moon.module.sub.SubModule;
import com.moon.util.game.MovementUtil;
import java.util.LinkedList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Tuple;

public class VerusFlight extends SubModule<Flight> {
   private final LinkedList<Tuple<Packet<?>, Long>> packetQueue = new LinkedList<>();
   private int stage;
   private int damageTicks;
   private boolean waitingForTransaction;
   private boolean receivedTransaction;
   private boolean wasDamaged;
   private boolean jumped;

   public VerusFlight() {
      super(Flight.class);
   }

   @Override
   public void onEnable() {
      this.receivedTransaction = false;
      this.waitingForTransaction = true;
      this.damageTicks = 0;
      this.jumped = false;
      this.releasePackets(0L);
   }

   @Override
   public void onDisable() {
      this.stage = 0;
      this.releasePackets(0L);
   }

   @Subscribe
   public void onKillAura(KillAuraEvent event) {
      if (this.getParent().getVerusDelayTransactions().getValue()) {
         event.setCancelled(true);
      }
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      boolean delayTransactions = this.getParent().getVerusDelayTransactions().getValue();
      if (delayTransactions) {
         Packet packet = event.getPacket();
         boolean isDelayPacket = packet instanceof S00PacketKeepAlive || packet instanceof S32PacketConfirmTransaction;
         if (isDelayPacket) {
            this.packetQueue.add(new Tuple<>(packet, System.currentTimeMillis()));
            event.setCancelled(true);
         } else if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
            this.receivedTransaction = true;
            this.waitingForTransaction = false;
         }
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         boolean delayTransactions = this.getParent().getVerusDelayTransactions().getValue();
         int delayTicks = this.getParent().getVerusDelayTicks().getValue();
         boolean waitForDelay = this.getParent().getVerusWaitForDelay().getValue();
         if (!this.packetQueue.isEmpty()) {
            this.releasePackets(delayTicks * 50L);
         }

         if (delayTransactions && !this.receivedTransaction) {
            waitForDelay = true;
         }

         if (!this.waitingForTransaction && waitForDelay && delayTransactions) {
            double posX = mc.thePlayer.posX;
            double posY = mc.thePlayer.posY;
            double posZ = mc.thePlayer.posZ;
            BlockPos blockPos = new BlockPos(posX, posY - 1.5, posZ);
            ItemStack itemStack = new ItemStack(Blocks.stone.getItem(mc.theWorld, blockPos));
            mc.thePlayer.sendQueue.Method755(new C08PacketPlayerBlockPlacement(blockPos, 1, itemStack, 0.0F, (float)(0.5 + Math.random() * 0.1F), 0.0F));
            this.stage++;
            if (this.stage < 4) {
               event.setCancelled(true);
               if (this.stage == 2) {
                  mc.thePlayer.sendQueue.Method755(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
               }
            } else if (this.stage == 4) {
               mc.thePlayer
                  .sendQueue
                  .Method755(
                     new C03PacketPlayer.C06PacketPlayerPosLook(
                        posX, posY + 5.0, posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false
                     )
                  );
               mc.thePlayer
                  .sendQueue
                  .Method755(
                     new C03PacketPlayer.C06PacketPlayerPosLook(posX, posY, posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false)
                  );
               mc.thePlayer
                  .sendQueue
                  .Method755(
                     new C03PacketPlayer.C06PacketPlayerPosLook(posX, posY, posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true)
                  );
            }

            if (mc.thePlayer.hurtResistantTime >= 15) {
               this.wasDamaged = true;
            }

            if (this.wasDamaged) {
               this.damageTicks++;
            }
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
   public void onMove(MoveEvent event) {
      if (this.stage >= 5 && this.wasDamaged) {
         if (mc.thePlayer.onGround && !this.jumped) {
            event.setY(0.42F);
            this.jumped = true;
         } else {
            event.setY(0.0);
         }

         int maxDamageTicks = 9 + this.getParent().getVerusDelayTicks().getValue() * 2;
         if (this.damageTicks < maxDamageTicks) {
            event.setSpeed(2.5);
         } else {
            event.setSpeed(MovementUtil.getBaseSpeed());
         }
      } else {
         event.setSpeed(0.0);
      }
   }

   @Subscribe
   public void onBlockBB(BlockBBEvent event) {
      event.getBoundingBox().add(new AxisAlignedBB(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ));
   }
}
