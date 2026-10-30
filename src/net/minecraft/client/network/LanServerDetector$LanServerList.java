package net.minecraft.client.network;

import com.google.common.collect.Lists;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.multiplayer.ThreadLanServerPing;

public class LanServerDetector$LanServerList {
   private final List<LanServerDetector$LanServer> listOfLanServers = Lists.newArrayList();
   boolean wasUpdated;

   public synchronized boolean getWasUpdated() {
      return this.wasUpdated;
   }

   public synchronized void setWasNotUpdated() {
      this.wasUpdated = false;
   }

   public synchronized List<LanServerDetector$LanServer> getLanServers() {
      return Collections.unmodifiableList(this.listOfLanServers);
   }

   public synchronized void func_77551_a(String string, InetAddress inetAddress) {
      String var6 = ThreadLanServerPing.getMotdFromPingResponse(string);
      String var7 = ThreadLanServerPing.getAdFromPingResponse(string);
      if (var7 != null) {
         var7 = inetAddress.getHostAddress() + ":" + var7;
         boolean var8 = false;

         for (LanServerDetector$LanServer var10 : this.listOfLanServers) {
            if (var10.getServerIpPort().equals(var7)) {
               var10.updateLastSeen();
               var8 = true;
               break;
            }
         }

         if (!var8) {
            this.listOfLanServers.add(new LanServerDetector$LanServer(var6, var7));
            this.wasUpdated = true;
         }
      }
   }
}
