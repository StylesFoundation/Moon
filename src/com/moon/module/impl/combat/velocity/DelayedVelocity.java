package com.moon.module.impl.combat.velocity;

import com.moon.events.impl.player.KnockbackEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.combat.Velocity;
import com.moon.module.sub.SubModule;
import com.moon.util.math.Position;
import java.util.LinkedList;
import net.minecraft.util.Tuple;

public class DelayedVelocity extends SubModule<Velocity> {
   private final LinkedList<Tuple<KnockbackEvent, Integer>> pendingKnockbacks = new LinkedList<>();

   public DelayedVelocity() {
      super(Velocity.class);
   }

   @Subscribe
   public void onKnockback(KnockbackEvent event) {
      if (!event.isExplosion() || this.getParent().modifyExplosions.getValue()) {
         Tuple<KnockbackEvent, Integer> existing = null;
         if (this.getParent().stackVelocities.getValue()) {
            existing = this.pendingKnockbacks.peek();
            this.pendingKnockbacks.clear();
         }

         int ticks = existing != null ? (Integer)existing.getSecond() : mc.thePlayer.ticksExisted;
         this.pendingKnockbacks.add(new Tuple<>(event, ticks));
         event.setCancelled(true);
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         Tuple<KnockbackEvent, Integer> entry;
         while ((entry = this.pendingKnockbacks.peek()) != null) {
            KnockbackEvent knockbackEvent = (KnockbackEvent)entry.getFirst();
            Position position = knockbackEvent.getPosition();
            int ticks = (Integer)entry.getSecond();
            if (mc.thePlayer.ticksExisted - ticks < this.getParent().timeoutTicks.getValue()) {
               break;
            }

            if (knockbackEvent.isExplosion()) {
               mc.thePlayer.Method936(position);
            } else {
               mc.thePlayer.Method937(position);
            }

            this.pendingKnockbacks.poll();
         }

         this.getParent().setSuffix("Delayed");
      }
   }
}
