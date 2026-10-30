package net.minecraft.client.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerDetector$ThreadLanServerFind extends Thread {
   private static final AtomicInteger field_148551_a = new AtomicInteger(0);
   private static final Logger logger = LogManager.getLogger();
   private final LanServerDetector$LanServerList localServerList;
   private final InetAddress broadcastAddress;
   private final MulticastSocket socket;

   public LanServerDetector$ThreadLanServerFind(LanServerDetector$LanServerList lanServerList) throws IOException {
      super("LanServerDetector #" + field_148551_a.incrementAndGet());
      this.localServerList = lanServerList;
      this.setDaemon(true);
      this.socket = new MulticastSocket(4445);
      this.broadcastAddress = InetAddress.getByName("224.0.2.60");
      this.socket.setSoTimeout(5000);
      this.socket.joinGroup(this.broadcastAddress);
   }

   @Override
   public void run() {
      byte[] var4 = new byte[1024];

      while (!this.isInterrupted()) {
         DatagramPacket var5 = new DatagramPacket(var4, var4.length);

         try {
            this.socket.receive(var5);
         } catch (SocketTimeoutException var8) {
            continue;
         } catch (IOException var9) {
            logger.error("Couldn't ping server", var9);
            break;
         }

         String var6 = new String(var5.getData(), var5.getOffset(), var5.getLength());
         logger.debug(var5.getAddress() + ": " + var6);
         this.localServerList.func_77551_a(var6, var5.getAddress());
      }

      try {
         this.socket.leaveGroup(this.broadcastAddress);
      } catch (IOException var7) {
      }

      this.socket.close();
   }
}
