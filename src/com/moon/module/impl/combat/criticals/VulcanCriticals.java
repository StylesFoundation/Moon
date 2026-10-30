package com.moon.module.impl.combat.criticals;

import com.moon.events.impl.player.InteractEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.combat.Criticals;
import com.moon.module.sub.SubModule;
import com.moon.util.client.Stopwatch;

public class VulcanCriticals extends SubModule<Criticals> {
   private final Stopwatch stopwatch = new Stopwatch();
   private boolean criticalQueued = false;
   private int hopStage = 0;

   public VulcanCriticals() {
      super(Criticals.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         if (mc.thePlayer.onGround && this.criticalQueued) {
            this.hopStage++;
            switch (this.hopStage) {
               case 1:
                  event.setY(event.getY() + 0.07);
                  break;
               case 2:
                  event.setY(event.getY() + 0.04);
                  break;
               case 3:
                  event.setY(event.getY() + 0.03);
                  this.criticalQueued = false;
                  break;
               default:
                  this.criticalQueued = false;
            }

            event.setOnGround(false);
         } else {
            this.criticalQueued = false;
            this.hopStage = 0;
         }
      }
   }

   @Subscribe
   public void onInteract(InteractEvent event) {
      if (mc.thePlayer.onGround
         && !mc.thePlayer.isOnLadder()
         && this.stopwatch.hasElapsedAndReset(this.getParent().getDelay().getValue().intValue())) {
         if (event.getEntity().isDead) {
            return;
         }

         this.criticalQueued = true;
         mc.thePlayer.onCriticalHit(event.getEntity());
      }
   }
}
