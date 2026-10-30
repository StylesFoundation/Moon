package com.moon.module.impl.combat.killaura.autoblock;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.module.impl.combat.killaura.AutoBlock;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class InvalidAutoBlock extends AutoBlock {
   private boolean blocking;
   private int startTick;

   @Override
   public void onPacket(PacketEvent event) {
   }

   @Override
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         if (this.isHoldingSword() && this.killAura.getTargetSelector().getTarget() != null) {
            if (!this.blocking) {
               client.thePlayer.sendQueue.Method755(new C08PacketPlayerBlockPlacement(null));
               this.startTick = client.thePlayer.ticksExisted;
            }

            this.blocking = true;
         } else if ((!this.isHoldingSword() || this.killAura.getTargetSelector().getTarget() == null) && this.blocking) {
            client.thePlayer.sendQueue.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.blocking = false;
         }
      }
   }

   @Override
   public void onEnable() {
      this.blocking = false;
   }

   @Override
   public void onDisable() {
      if (this.blocking) {
         int currentSlot = client.thePlayer.inventory.currentItem;
         client.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(currentSlot % 8 + 1));
         client.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(currentSlot));
      }
   }

   @Override
   public void startBlocking() {
      this.blocking = false;
   }

   @Override
   public void stopBlocking() {
      if (!this.blocking && this.startTick != client.thePlayer.ticksExisted) {
         client.thePlayer.sendQueue.Method755(new C08PacketPlayerBlockPlacement(null));
      }
   }
}
