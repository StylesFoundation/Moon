package com.moon.module.impl.movement.speed;

import com.moon.events.impl.player.JumpDelayEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Speed;
import com.moon.module.sub.SubModule;
import com.moon.util.game.MovementUtil;
import net.minecraft.potion.Potion;

public class VulcanSpeed extends SubModule<Speed> {
   private int stage;
   private double currentSpeed = 0.0;

   public VulcanSpeed() {
      super(Speed.class);
   }

   @Override
   public void onDisable() {
      if (mc.thePlayer.onGround) {
         mc.thePlayer.motionY = 0.05F;
      }

      MovementUtil.setSpeed(0.0);
   }

   @Subscribe
   public void onJumpDelay(JumpDelayEvent event) {
      event.setCancelled(true);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         this.stage++;
         if (mc.thePlayer.onGround && mc.thePlayer.ae()) {
            event.setY(event.getY() + 0.0125F);
            mc.thePlayer.motionY = 0.0125F;
            this.currentSpeed = MovementUtil.getBaseSpeed(0.6) + 0.31;
            this.stage = 0;
         }
      }
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      if (mc.thePlayer.ae()) {
         event.setSpeed(this.currentSpeed);
         int speedLevel = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
         this.currentSpeed = MovementUtil.getBaseSpeed() - (speedLevel >= 2 ? 0.061F : 0.0F);
      } else {
         event.setSpeed(0.0);
      }
   }
}
