package net.minecraft.client.network;

import net.minecraft.client.Minecraft;

public class LanServerDetector$LanServer {
   private final String lanServerMotd;
   private final String lanServerIpPort;
   private long timeLastSeen;

   public LanServerDetector$LanServer(String string1, String string2) {
      this.lanServerMotd = string1;
      this.lanServerIpPort = string2;
      this.timeLastSeen = Minecraft.getSystemTime();
   }

   public String getServerMotd() {
      return this.lanServerMotd;
   }

   public String getServerIpPort() {
      return this.lanServerIpPort;
   }

   public void updateLastSeen() {
      this.timeLastSeen = Minecraft.getSystemTime();
   }
}
