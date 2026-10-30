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

public class WatchdogNoSlowDown extends SubModule<NoSlowDown> {
   private KillAura killAura;
   private int useTicks;

   public WatchdogNoSlowDown() {
      super(NoSlowDown.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      this.initKillAura();
      EntityLivingBase target = this.killAura.getTargetSelector().getTarget();
      if (mc.thePlayer.ae() && mc.thePlayer.isUsingItem() && (target == null || !this.killAura.isEnabled())) {
         if (mc.thePlayer.isBlocking() && this.getParent().features.hasChecked(NoSlowDown.Features.SWORDS)) {
            if (event.getSequence() == UpdateEvent.Sequence.POST) {
            }
         } else {
            Item heldItem = mc.thePlayer.getHeldItem().getItem();
            if (heldItem instanceof ItemSword) {
               return;
            }

            boolean featureEnabled;
            if (heldItem instanceof ItemBow) {
               featureEnabled = this.getParent().features.hasChecked(NoSlowDown.Features.BOW);
            } else {
               featureEnabled = this.getParent().features.hasChecked(NoSlowDown.Features.CONSUMABLE);
            }

            if (event.getSequence() == UpdateEvent.Sequence.POST && featureEnabled) {
               mc.thePlayer.sendQueue.Method754(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
            }
         }

         this.useTicks++;
      } else {
         this.useTicks = 0;
      }
   }

   private void initKillAura() {
      if (this.killAura == null) {
         this.killAura = Moon.getInstance().getModuleManager().getModule(KillAura.class);
      }
   }
}
