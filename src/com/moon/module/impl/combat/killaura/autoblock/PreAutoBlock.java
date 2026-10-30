package com.moon.module.impl.combat.killaura.autoblock;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.module.impl.combat.killaura.AutoBlock;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.EnumFacing;

public class PreAutoBlock extends AutoBlock {
   private boolean blocking;

   @Override
   public void onPacket(PacketEvent event) {
   }

   @Override
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE && this.killAura.getTargetSelector().hasTarget() && this.isHoldingSword()) {
         client.playerController.sendUseItem(client.thePlayer, client.theWorld, client.thePlayer.getHeldItem());
         this.blocking = true;
      }
   }

   @Override
   public void onEnable() {
      this.blocking = false;
   }

   @Override
   public void onDisable() {
      if (this.blocking) {
         client.thePlayer
            .sendQueue
            .Method754(
               new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, C08PacketPlayerBlockPlacement.field_179726_a, EnumFacing.DOWN)
            );
      }
   }

   @Override
   public void startBlocking() {
      if (this.killAura.getUnblockOnAttack().getValue() && this.blocking) {
         client.playerController.syncCurrentPlayItem();
         client.thePlayer
            .sendQueue
            .Method754(
               new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, C08PacketPlayerBlockPlacement.field_179726_a, EnumFacing.DOWN)
            );
         this.blocking = false;
      }
   }

   @Override
   public void stopBlocking() {
      if (this.killAura.getUnblockOnAttack().getValue() && !this.blocking && this.isHoldingSword()) {
         client.playerController.sendUseItem(client.thePlayer, client.theWorld, client.thePlayer.getHeldItem());
         this.blocking = true;
      }
   }
}
