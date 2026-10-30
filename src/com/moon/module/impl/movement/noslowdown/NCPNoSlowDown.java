package com.moon.module.impl.movement.noslowdown;

import com.moon.Moon;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.movement.NoSlowDown;
import com.moon.module.sub.SubModule;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.EnumFacing;

public class NCPNoSlowDown extends SubModule<NoSlowDown> {
   private KillAura killAura;

   public NCPNoSlowDown() {
      super(NoSlowDown.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      this.initKillAura();
      EntityLivingBase target = this.killAura.getTargetSelector().getTarget();
      if (mc.thePlayer.ae() && mc.thePlayer.isUsingItem() && (target == null || !this.killAura.isEnabled())) {
         if (event.getSequence() == UpdateEvent.Sequence.PRE) {
            mc.thePlayer
               .sendQueue
               .Method754(
                  new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, C08PacketPlayerBlockPlacement.field_179726_a, EnumFacing.DOWN)
               );
         } else if (event.getSequence() == UpdateEvent.Sequence.POST) {
            mc.thePlayer.sendQueue.Method754(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
         }
      }
   }

   private void initKillAura() {
      if (this.killAura == null) {
         this.killAura = Moon.getInstance().getModuleManager().getModule(KillAura.class);
      }
   }
}
