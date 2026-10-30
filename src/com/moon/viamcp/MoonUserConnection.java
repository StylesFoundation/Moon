package com.moon.viamcp;

import com.moon.handlers.packet.impl.ViaPingPacket;
import com.moon.viamcp.protocols.ProtocolCollection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.exception.CancelException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;

public class MoonUserConnection extends UserConnectionImpl {
   public MoonUserConnection(Channel channel, boolean clientSide) {
      super(channel, clientSide);
   }

   @Override
   public void transformIncoming(ByteBuf byteBuf, Function<Throwable, Exception> function) throws Exception {
      if (byteBuf.isReadable() && ViaMCP.getInstance().getVersion() == ProtocolCollection.R_1_19_1.Method572().getVersion()) {
         PacketBuffer packetBuffer = new PacketBuffer(byteBuf.copy());
         long packetId = Type.VAR_INT.readPrimitive(packetBuffer);
         if (packetId == 47L) {
            int pingId = Type.INT.read(packetBuffer);
            this.setActive(false);
            Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().thePlayer.sendQueue.Method754(new ViaPingPacket(32, pingId)));
            this.setActive(true);
            packetBuffer.release();
            throw (Exception)function.apply(new CancelException());
         }

         packetBuffer.release();
      }

      super.transformIncoming(byteBuf, function);
   }

   @Override
   public void transformOutgoing(ByteBuf byteBuf, Function<Throwable, Exception> function) throws Exception {
      super.transformOutgoing(byteBuf, function);
   }
}
