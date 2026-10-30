package com.moon.module.impl.combat.velocity;

import com.moon.events.impl.player.KnockbackEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.combat.Velocity;
import com.moon.module.sub.SubModule;
import com.moon.util.math.Position;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class JumpVelocity extends SubModule<Velocity> {
   private boolean pendingJump = false;

   public JumpVelocity() {
      super(Velocity.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      int jumpKeyCode = mc.gameSettings.keyBindJump.getKeyCode();
      if (event.getSequence() == UpdateEvent.Sequence.BEFORE) {
         if (mc.thePlayer.ticksExisted <= 2) {
            this.pendingJump = false;
         }

         if (mc.thePlayer.isSprinting() && mc.thePlayer.onGround && this.pendingJump) {
            KeyBinding.setKeyBindState(jumpKeyCode, true);
         }

         this.pendingJump = false;
      }

      if (event.getSequence() == UpdateEvent.Sequence.POST && mc.currentScreen == null) {
         KeyBinding.setKeyBindState(jumpKeyCode, Keyboard.isKeyDown(jumpKeyCode));
      }

      this.getParent().setSuffix("Jump");
   }

   @Subscribe
   public void onKnockback(KnockbackEvent event) {
      if (this.pendingJump) {
         this.pendingJump = false;
      } else {
         Position position = event.getPosition();
         double motionX = position.getX();
         double motionZ = position.getZ();
         double horizontalSpeed = Math.hypot(motionX, motionZ);
         if (horizontalSpeed > 0.15 && !event.isExplosion() && mc.thePlayer.onGround) {
            mc.gameSettings.keyBindJump.getKeyCode();
            this.pendingJump = true;
         }
      }
   }
}
