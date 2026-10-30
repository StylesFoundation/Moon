package com.moon.module.impl.player.scaffold.sprint;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.module.impl.player.scaffold.SprintScaffold;
import com.moon.util.game.MovementUtil;
import com.moon.util.math.MathUtil;
import com.moon.viamcp.ViaMCP;
import com.moon.viamcp.protocols.ProtocolCollection;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;

public class HypixelSprint extends SprintScaffold {
   private final List<Packet> packetQueue = new LinkedList<>();
   private int packetCounter;
   private double teleportCooldown;

   @Override
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.BEFORE) {
         if (minecraft.gameSettings.keyBindJump.isKeyDown() && minecraft.thePlayer.ae()) {
            KeyBinding.setKeyBindState(minecraft.gameSettings.keyBindSprint.getKeyCode(), false);
            minecraft.thePlayer.setSprinting(false);
         }

         if (MovementUtil.getBaseSpeed() > 0.216
            && minecraft.thePlayer.ae()
            && minecraft.thePlayer.onGround
            && minecraft.thePlayer.isPotionActive(Potion.moveSpeed.id)) {
            MovementUtil.setSpeed(
               this.scaffoldWalk.getHypixelSlowSprint().getValue()
                  ? 0.108
                  : (ViaMCP.getInstance().getVersion() > ProtocolCollection.R1_12_2.Method572().getVersion() ? 0.176 : 0.126)
            );
         } else if (minecraft.thePlayer.ae() && this.scaffoldWalk.getHypixelSlowSprint().getValue()) {
            MovementUtil.setSpeed(0.122);
         }
      }
   }

   @Override
   public void onMove(MoveEvent event) {
   }

   @Override
   public void onPacket(PacketEvent event) {
      boolean canSprint = !minecraft.gameSettings.keyBindJump.isKeyDown() || !minecraft.thePlayer.ae();
      if (event.getPacket() instanceof S08PacketPlayerPosLook) {
         this.teleportCooldown = 20.0;
      }

      if (canSprint && this.teleportCooldown == 0.0) {
         if (!(event.getPacket() instanceof C0APacketAnimation) && !(event.getPacket() instanceof C08PacketPlayerBlockPlacement)) {
            if (event.getPacket() instanceof C03PacketPlayer playerPacket && !playerPacket.Method473()) {
               double moveDistance = Math.hypot(minecraft.thePlayer.posX - minecraft.thePlayer.prevPosX, minecraft.thePlayer.posZ - minecraft.thePlayer.prevPosZ);
               if (playerPacket.Method471() && minecraft.thePlayer.onGround) {
                  double yaw = MovementUtil.getMovementYaw();
                  double sin = -Math.sin(yaw);
                  double cos = Math.cos(yaw);
                  double offset = this.scaffoldWalk.getHypixelSlowSprint().getValue()
                     ? Math.abs(moveDistance - (minecraft.thePlayer.isPotionActive(Potion.moveSpeed.id) ? MathUtil.random(0.16, 0.18) : MathUtil.random(0.08, 0.1)))
                     : Math.max(moveDistance - MathUtil.random(0.12, 0.125), 0.0);
                  this.packetCounter++;
                  if (this.packetCounter == 1) {
                     playerPacket.Method482(playerPacket.getPositionZ() - sin * offset);
                     playerPacket.Method484(playerPacket.Method476() - cos * offset);
                  } else {
                     this.packetQueue.forEach(packet -> minecraft.thePlayer.sendQueue.Method755((Packet<?>)packet));
                     this.packetQueue.clear();
                     event.setCancelled(true);
                     minecraft.thePlayer.sendQueue.Method755(event.getPacket());
                     this.packetCounter = 0;
                  }
               } else {
                  this.packetQueue.forEach(packet -> minecraft.thePlayer.sendQueue.Method755((Packet<?>)packet));
                  this.packetQueue.clear();
                  event.setCancelled(true);
                  minecraft.thePlayer.sendQueue.Method755(event.getPacket());
                  this.packetCounter = 0;
               }
            }
         } else {
            event.setCancelled(true);
            this.packetQueue.add(event.getPacket());
         }
      } else if (event.isSend()) {
         if (this.teleportCooldown > 0.0) {
            this.teleportCooldown--;
         }

         KeyBinding.setKeyBindState(minecraft.gameSettings.keyBindSprint.getKeyCode(), false);
         minecraft.thePlayer.setSprinting(false);
         this.packetQueue.forEach(packet -> minecraft.thePlayer.sendQueue.Method755((Packet<?>)packet));
         this.packetQueue.clear();
         event.setCancelled(true);
         minecraft.thePlayer.sendQueue.Method755(event.getPacket());
         this.packetCounter = 0;
      }
   }

   @Override
   public void onUpdateState() {
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onEnable() {
      this.packetCounter = 0;
   }
}
