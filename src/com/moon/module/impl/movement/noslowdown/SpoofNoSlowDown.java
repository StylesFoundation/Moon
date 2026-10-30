package com.moon.module.impl.movement.noslowdown;

import com.moon.Moon;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.movement.NoSlowDown;
import com.moon.module.sub.SubModule;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class SpoofNoSlowDown extends SubModule<NoSlowDown> {
   private KillAura killAura;

   public SpoofNoSlowDown() {
      super(NoSlowDown.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      this.initKillAura();
      EntityLivingBase target = this.killAura.getTargetSelector().getTarget();
      boolean notAuraTargeting = target == null || !this.killAura.isEnabled();
      boolean isUsingItem = mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking();
      if (mc.thePlayer.ae() && isUsingItem && notAuraTargeting && event.getSequence() == UpdateEvent.Sequence.PRE) {
         int currentSlot = mc.thePlayer.inventory.currentItem;
         mc.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(currentSlot % 8 + 1));
         mc.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(currentSlot));
         if (this.getParent().reBlock.getValue()) {
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
