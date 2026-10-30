package com.moon.module.impl.combat.killaura.autoblock;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.handlers.packet.ViaUseItemPacket;
import com.moon.module.impl.combat.killaura.AutoBlock;
import com.moon.util.client.ChatUtil;
import com.moon.viamcp.ViaMCP;
import com.moon.viamcp.protocols.ProtocolCollection;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class WatchdogAutoBlock extends AutoBlock {
   private boolean blocking;
   private int initialSlot = Integer.MAX_VALUE;
   private int unblockDelay;

   @Override
   public void onPacket(PacketEvent event) {
      if (event.getPacket() instanceof C07PacketPlayerDigging || event.getPacket() instanceof C09PacketHeldItemChange) {
         this.blocking = false;
      }
   }

   @Override
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.BEFORE) {
         if (this.isHoldingSword() && this.killAura.getTargetSelector().getTarget() != null) {
            if (this.killAura.getWatchdogAlternative().getValue()) {
               client.thePlayer.sendQueue.Method754(new C08PacketPlayerBlockPlacement(null));
               this.blocking = true;
            } else if (ViaMCP.getInstance().getVersion() != ProtocolCollection.R1_12_2.Method572().getVersion()) {
               ChatUtil.sendMessage("Please use 1.12 for autoblock");
            } else if (!this.blocking && --this.unblockDelay <= 0) {
               client.thePlayer.sendQueue.Method755(new ViaUseItemPacket(32, 0));
               this.blocking = true;
               this.unblockDelay = 3;
            } else if (this.blocking && client.thePlayer.ticksExisted % 3 == 0) {
               client.thePlayer.sendQueue.Method755(new ViaUseItemPacket(32, 1));
               this.unblockDelay = 3;
            }
         } else if ((!this.isHoldingSword() || this.killAura.getTargetSelector().getTarget() == null) && this.blocking) {
            client.thePlayer.sendQueue.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.blocking = false;
            this.unblockDelay = 0;
         }
      }
   }

   @Override
   public void onEnable() {
      this.initialSlot = client.thePlayer.inventory.currentItem;
      this.blocking = false;
   }

   @Override
   public void onDisable() {
      if (this.blocking) {
         client.thePlayer.sendQueue.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
         this.blocking = false;
      }
   }

   @Override
   public void startBlocking() {
   }

   @Override
   public void stopBlocking() {
   }

   private boolean isValidSlot() {
      return this.initialSlot < 10;
   }
}
