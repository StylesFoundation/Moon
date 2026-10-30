package com.moon.util.game;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class DamageUtil {
   private static final Minecraft a = Minecraft.getMinecraft();

   public static void selfDamage() {
      a.thePlayer.sendQueue.Method754(new C03PacketPlayer.C04PacketPlayerPosition(a.thePlayer.posX, a.thePlayer.posY + 4.0, a.thePlayer.posZ, false));
      a.thePlayer.sendQueue.Method754(new C03PacketPlayer.C04PacketPlayerPosition(a.thePlayer.posX, a.thePlayer.posY, a.thePlayer.posZ, false));
      a.thePlayer.sendQueue.Method754(new C03PacketPlayer.C04PacketPlayerPosition(a.thePlayer.posX, a.thePlayer.posY, a.thePlayer.posZ, true));
      a.thePlayer.sendQueue.Method754(new C03PacketPlayer.C04PacketPlayerPosition(a.thePlayer.posX, a.thePlayer.posY + 0.42F, a.thePlayer.posZ, true));
   }
}
