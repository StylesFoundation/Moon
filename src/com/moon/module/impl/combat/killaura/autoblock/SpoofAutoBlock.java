package com.moon.module.impl.combat.killaura.autoblock;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.module.impl.combat.killaura.AutoBlock;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class SpoofAutoBlock extends AutoBlock {
   private boolean blocking;

   @Override
   public void onPacket(PacketEvent event) {
   }

   @Override
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         if (this.blocking) {
            int currentSlot = client.thePlayer.inventory.currentItem;
            client.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(currentSlot % 8 + 1));
            client.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(currentSlot));
            this.blocking = false;
         }

         if (this.isHoldingSword() && this.killAura.getTargetSelector().getTarget() != null) {
            client.thePlayer.sendQueue.Method754(new C08PacketPlayerBlockPlacement(client.thePlayer.getCurrentEquippedItem()));
            this.blocking = true;
         }
      }
   }

   @Override
   public void onEnable() {
      this.blocking = false;
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void startBlocking() {
      if (this.blocking) {
         int currentSlot = client.thePlayer.inventory.currentItem;
         client.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(currentSlot % 8 + 1));
         client.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(currentSlot));
         this.blocking = false;
      }
   }

   @Override
   public void stopBlocking() {
   }
}
