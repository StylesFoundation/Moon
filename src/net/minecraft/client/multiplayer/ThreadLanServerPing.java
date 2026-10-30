package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadLanServerPing extends Thread {
   private static final AtomicInteger field_148658_a = new AtomicInteger(0);
   private static final Logger logger = LogManager.getLogger();
   private final String motd;
   private final DatagramSocket socket;
   private boolean isStopping = true;
   private final String address;

   public ThreadLanServerPing(String string1, String string2) throws IOException {
      super("LanServerPinger #" + field_148658_a.incrementAndGet());
      this.motd = string1;
      this.address = string2;
      this.setDaemon(true);
      this.socket = new DatagramSocket();
   }

   @Override
   public void run() {
      String var4 = Method724(this.motd, this.address);
      byte[] var5 = var4.getBytes();

      while (!this.isInterrupted() && this.isStopping) {
         try {
            InetAddress var6 = InetAddress.getByName("224.0.2.60");
            DatagramPacket var7 = new DatagramPacket(var5, var5.length, var6, 4445);
            this.socket.send(var7);
         } catch (IOException var9) {
            logger.warn("LanServerPinger: " + var9.getMessage());
            break;
         }

         try {
            sleep(1500L);
         } catch (InterruptedException var8) {
         }
      }
   }

   @Override
   public void interrupt() {
      super.interrupt();
      this.isStopping = false;
   }

   public static String Method724(String string1, String string2) {
      return "[MOTD]" + string1 + "[/MOTD][AD]" + string2 + "[/AD]";
   }

   public static String getMotdFromPingResponse(String string) {
      int var4 = string.indexOf("[MOTD]");
      if (var4 < 0) {
         return "missing no";
      }

      int var5 = string.indexOf("[/MOTD]", var4 + 6);
      return var5 < var4 ? "missing no" : string.substring(var4 + 6, var5);
   }

   public static String getAdFromPingResponse(String string) {
      int var4 = string.indexOf("[/MOTD]");
      if (var4 < 0) {
         return null;
      }

      int var5 = string.indexOf("[/MOTD]", var4 + 7);
      if (var5 >= 0) {
         return null;
      }

      int var6 = string.indexOf("[AD]", var4 + 7);
      if (var6 < 0) {
         return null;
      }

      int var7 = string.indexOf("[/AD]", var6 + 4);
      return var7 < var6 ? null : string.substring(var6 + 4, var7);
   }
}
