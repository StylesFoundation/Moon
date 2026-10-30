package com.moon.module.impl.movement.antivoid.impl;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.antivoid.AntiVoidMode;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class WatchdogAntiVoid extends AntiVoidMode {
   private final List<Packet> queuedPackets = new ArrayList<>();
   private Vec3 lastSafePosition;
   private BlockPos lastSafeBlockPos;
   private boolean teleportedBack;
   private int delayCounter;

   @Subscribe
   public void onPacketEvent(PacketEvent event) {
      if (event.isSend()
         && (
            event.getPacket() instanceof C02PacketUseEntity
               || event.getPacket() instanceof C07PacketPlayerDigging
               || event.getPacket() instanceof C08PacketPlayerBlockPlacement
               || event.getPacket() instanceof C0APacketAnimation
         )) {
         if (mc.getNetHandler().doneLoadingTerrain
            && !mc.thePlayer.capabilities.allowFlying
            && !mc.thePlayer.capabilities.isFlying
            && !this.hasGroundBelow()
            && this.lastSafePosition != null) {
            if (mc.thePlayer.fallDistance > 20.0F) {
               return;
            }

            if (this.lastSafeBlockPos == null) {
               return;
            }

            Block safeBlock = mc.theWorld.getBlockState(this.lastSafeBlockPos).getBlock();
            if (safeBlock.getMaterial() == Material.air) {
               return;
            }

            this.queuedPackets.add(event.getPacket());
            event.setCancelled(true);
         }
      } else {
         Packet packet = event.getPacket();
         if (packet instanceof C03PacketPlayer) {
            if (mc.thePlayer.ticksExisted <= 100) {
               this.queuedPackets.clear();
               return;
            }

            if (mc.getNetHandler().doneLoadingTerrain && !mc.thePlayer.capabilities.allowFlying && !mc.thePlayer.capabilities.isFlying) {
               if (!this.hasGroundBelow()) {
                  if (this.lastSafePosition != null) {
                     if (mc.thePlayer.fallDistance > 20.0F) {
                        return;
                     }

                     if (this.lastSafeBlockPos == null) {
                        return;
                     }

                     Block safeBlock = mc.theWorld.getBlockState(this.lastSafeBlockPos).getBlock();
                     if (safeBlock.getMaterial() == Material.air) {
                        return;
                     }

                     this.queuedPackets.add(event.getPacket());
                     event.setCancelled(true);
                     if (mc.thePlayer.fallDistance > this.getParent().getFallDistance().getValue() && ++this.delayCounter >= 20) {
                        mc.thePlayer
                           .sendQueue
                           .Method755(
                              new C03PacketPlayer.C04PacketPlayerPosition(
                                 this.lastSafePosition.xCoord, this.lastSafePosition.yCoord - 1.0, this.lastSafePosition.zCoord, false
                              )
                           );
                        this.delayCounter = 0;
                        this.teleportedBack = true;
                     }
                  }
               } else {
                  if (mc.thePlayer.onGround) {
                     this.lastSafePosition = mc.thePlayer.getPositionVector();
                     BlockPos groundPos = new BlockPos(this.lastSafePosition.xCoord, this.lastSafePosition.yCoord - 1.0, this.lastSafePosition.zCoord);
                     Block groundBlock = mc.theWorld.getBlockState(groundPos).getBlock();
                     if (groundBlock.getMaterial() != Material.air) {
                        this.lastSafeBlockPos = groundPos;
                     }

                     this.delayCounter = 20;
                  }

                  if (!this.queuedPackets.isEmpty()) {
                     this.queuedPackets.forEach(mc.thePlayer.sendQueue::Method755);
                     this.queuedPackets.clear();
                  }
               }
            }
         } else if (event.getPacket() instanceof S08PacketPlayerPosLook posLookPacket) {
            this.queuedPackets.clear();
            this.teleportedBack = false;
            if (this.lastSafePosition != null) {
               double yDiff = posLookPacket.getY() - this.lastSafePosition.yCoord;
               if (Math.abs(yDiff) > 2.0) {
                  this.lastSafePosition = null;
               }
            }

            this.delayCounter = 0;
         }
      }
   }
}
