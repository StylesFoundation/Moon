package net.minecraft.network;

import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.src.Config;
import net.minecraft.util.IThreadListener;

public class PacketThreadUtil {
   public static int lastDimensionId = Integer.MIN_VALUE;

   public static <T extends INetHandler> void checkThreadAndEnqueue(Packet<T> packet, T iNetHandler, IThreadListener iThreadListener) throws ThreadQuickExitException {
      if (!iThreadListener.isCallingFromMinecraftThread()) {
         iThreadListener.addScheduledTask(() -> {
            clientPreProcessPacket(packet);
            packet.processPacket(iNetHandler);
         });
         throw ThreadQuickExitException.INSTANCE;
      }

      clientPreProcessPacket(packet);
   }

   protected static void clientPreProcessPacket(Packet packet) {
      if (packet instanceof S08PacketPlayerPosLook) {
         Config.getRenderGlobal().onPlayerPositionSet();
      }

      if (packet instanceof S07PacketRespawn var4) {
         lastDimensionId = var4.getDimensionID();
      } else if (packet instanceof S01PacketJoinGame var5) {
         lastDimensionId = var5.getDimension();
      } else {
         lastDimensionId = Integer.MIN_VALUE;
      }
   }
}
