package com.moon.module.impl.movement.noslowdown;

import com.moon.Moon;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.movement.NoSlowDown;
import com.moon.module.sub.SubModule;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class UpdatedNCPNoSlowDown extends SubModule<NoSlowDown> {
   private KillAura killAura;
   private final List<Packet> queuedPackets = new CopyOnWriteArrayList<>();
   private boolean blocking;

   public UpdatedNCPNoSlowDown() {
      super(NoSlowDown.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      this.initKillAura();
      if (mc.thePlayer.ae() && mc.thePlayer.isUsingItem() && !mc.thePlayer.isBlocking() && event.getSequence() == UpdateEvent.Sequence.PRE
         )
       {
         mc.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem + 1));
         mc.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
      }
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      this.initKillAura();
      EntityLivingBase target = this.killAura.getTargetSelector().getTarget();
      if (event.getPacket() instanceof C03PacketPlayer && (target == null || !this.killAura.isEnabled())) {
         if (mc.thePlayer.isBlocking()) {
            this.queuedPackets.add(event.getPacket());
            event.setCancelled(true);
            if (this.queuedPackets.size() > 5) {
               mc.thePlayer.sendQueue.Method755(this.queuedPackets.get(0));
               mc.thePlayer
                  .sendQueue
                  .Method755(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));

               for (int i = 1; i < this.queuedPackets.size(); i++) {
                  Packet packet = this.queuedPackets.get(i);
                  mc.thePlayer.sendQueue.Method755(packet);
               }

               this.queuedPackets.clear();
               mc.thePlayer.sendQueue.Method755(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
            }

            this.blocking = true;
         } else if (this.blocking && this.queuedPackets.size() > 3) {
            mc.thePlayer.sendQueue.Method755(this.queuedPackets.get(0));
            mc.thePlayer
               .sendQueue
               .Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            if (this.queuedPackets.size() > 1) {
               for (int i = 1; i < this.queuedPackets.size(); i++) {
                  Packet packet = this.queuedPackets.get(i);
                  mc.thePlayer.sendQueue.Method755(packet);
               }

               this.queuedPackets.clear();
            }

            this.blocking = false;
         } else if (this.blocking) {
            this.queuedPackets.add(event.getPacket());
            event.setCancelled(true);
         }
      }
   }

   private void initKillAura() {
      if (this.killAura == null) {
         this.killAura = Moon.getInstance().getModuleManager().getModule(KillAura.class);
      }
   }
}
