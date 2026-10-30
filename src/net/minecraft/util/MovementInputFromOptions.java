package net.minecraft.util;

import com.moon.Moon;
import com.moon.events.impl.player.SlowdownEvent;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput {
   private final GameSettings gameSettings;

   public MovementInputFromOptions(GameSettings gameSettings) {
      this.gameSettings = gameSettings;
   }

   @Override
   public void updatePlayerMoveState() {
      this.moveStrafe = 0.0F;
      this.moveForward = 0.0F;
      if (this.gameSettings.keyBindForward.isKeyDown()) {
         this.moveForward++;
      }

      if (this.gameSettings.keyBindBack.isKeyDown()) {
         this.moveForward--;
      }

      if (this.gameSettings.keyBindLeft.isKeyDown()) {
         this.moveStrafe++;
      }

      if (this.gameSettings.keyBindRight.isKeyDown()) {
         this.moveStrafe--;
      }

      this.jump = this.gameSettings.keyBindJump.isKeyDown();
      this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
      if (this.sneak) {
         SlowdownEvent var4 = new SlowdownEvent(SlowdownEvent.Type.SNEAKING);
         Moon.getInstance().getEventBus().publish(var4);
         if (!var4.isCancelled()) {
            this.moveStrafe = (float)(this.moveStrafe * 0.3);
            this.moveForward = (float)(this.moveForward * 0.3);
         }
      }
   }
}
