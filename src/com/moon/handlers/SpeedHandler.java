package com.moon.handlers;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import java.util.LinkedList;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C0APacketAnimation;

public class SpeedHandler {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private float moveSpeed;
   private final LinkedList<Long> clickSamples = new LinkedList<>();

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      this.pollCPS();
      if (mc.thePlayer != null && mc.theWorld != null) {
         float distance = (float)Math.sqrt(
            (mc.thePlayer.posX - mc.thePlayer.prevPosX) * (mc.thePlayer.posX - mc.thePlayer.prevPosX)
               + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (mc.thePlayer.posZ - mc.thePlayer.prevPosZ)
         );
         this.moveSpeed = distance * 20.0F * mc.timer.timerSpeed;
      }
   }

   public int getCPS() {
      return this.clickSamples.size();
   }

   private void pollCPS() {
      long currentTime = System.currentTimeMillis();

      while (this.clickSamples.peek() != null && currentTime > this.clickSamples.peek() + 1000L) {
         this.clickSamples.poll();
      }
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (event.getPacket() instanceof C0APacketAnimation) {
         this.clickSamples.add(System.currentTimeMillis());
      }
   }

   public float getMoveSpeed() {
      return this.moveSpeed;
   }

   public LinkedList<Long> getClickSamples() {
      return this.clickSamples;
   }
}
