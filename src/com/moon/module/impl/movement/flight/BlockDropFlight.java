package com.moon.module.impl.movement.flight;

import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Flight;
import com.moon.module.sub.SubModule;
import com.moon.util.game.MovementUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class BlockDropFlight extends SubModule<Flight> {
   public BlockDropFlight() {
      super(Flight.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.POST) {
         double offsetX = -Math.sin(MovementUtil.getMovementYaw()) * 24.0;
         double offsetY = mc.gameSettings.keyBindSneak.isKeyDown() ? -4.0 : (mc.gameSettings.keyBindJump.isKeyDown() ? 4.0 : 0.0);
         double offsetZ = Math.cos(MovementUtil.getMovementYaw()) * 24.0;
         mc.thePlayer
            .sendQueue
            .Method755(
               new C03PacketPlayer.C04PacketPlayerPosition(
                  mc.thePlayer.posX + offsetX, mc.thePlayer.posY + offsetY, mc.thePlayer.posZ + offsetZ, false
               )
            );
      } else if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         for (int i = 0; i < 3; i++) {
            mc.thePlayer
               .sendQueue
               .Method755(
                  new C03PacketPlayer.C06PacketPlayerPosLook(
                     mc.thePlayer.posX,
                     mc.thePlayer.posY,
                     mc.thePlayer.posZ,
                     mc.thePlayer.rotationYaw,
                     mc.thePlayer.rotationPitch,
                     false
                  )
               );
         }

         mc.thePlayer.motionY = 0.0;
      }
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      event.setSpeed(0.0);
   }
}
