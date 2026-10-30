package com.moon.module.impl.movement.noslowdown;

import com.moon.Moon;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.movement.NoSlowDown;
import com.moon.module.sub.SubModule;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class InvalidNoSlowDown extends SubModule<NoSlowDown> {
   private KillAura killAura;

   public InvalidNoSlowDown() {
      super(NoSlowDown.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (mc.thePlayer != null) {
         this.initKillAura();
         EntityLivingBase target = this.killAura.getTargetSelector().getTarget();
         boolean notAuraTargeting = target == null || !this.killAura.isEnabled();
         boolean isUsingItem = mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking();
         if (mc.thePlayer.ae() && isUsingItem && notAuraTargeting && event.getSequence() == UpdateEvent.Sequence.BEFORE && mc.thePlayer.getHeldItem() != null) {
            Item heldItem = mc.thePlayer.getHeldItem().getItem();
            if (heldItem instanceof ItemSword) {
               mc.thePlayer.sendQueue.Method754(new C08PacketPlayerBlockPlacement(null));
               return;
            }

            boolean featureEnabled;
            if (heldItem instanceof ItemBow) {
               featureEnabled = this.getParent().features.hasChecked(NoSlowDown.Features.BOW);
            } else {
               featureEnabled = this.getParent().features.hasChecked(NoSlowDown.Features.CONSUMABLE);
            }

            if (featureEnabled) {
               int currentSlot = mc.thePlayer.inventory.currentItem;
               mc.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(currentSlot % 8 + 1));
               mc.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(currentSlot));
            }
         }
      }
   }

   private void initKillAura() {
      if (this.killAura == null) {
         this.killAura = Moon.getInstance().getModuleManager().getModule(KillAura.class);
      }
   }
}
