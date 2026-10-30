package net.minecraft.util;

import com.moon.handlers.packet.ViaUseItemPacket;
import com.moon.handlers.packet.impl.ViaPingPacket;
import com.moon.viamcp.handler.MCPEncodeHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class MessageSerializer extends MessageToByteEncoder<Packet> {
   private static final Logger logger = LogManager.getLogger();
   private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
   private final EnumPacketDirection direction;

   public MessageSerializer(EnumPacketDirection enumPacketDirection) {
      this.direction = enumPacketDirection;
   }

   protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
      if (!(packet instanceof ViaPingPacket) && !(packet instanceof ViaUseItemPacket)) {
         Integer var12 = ((EnumConnectionState)channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get())
            .getPacketId(this.direction, packet);
         if (logger.isDebugEnabled()) {
            logger.debug(
               RECEIVED_PACKET_MARKER,
               "OUT: [{}:{}] {}",
               channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get(),
               var12,
               packet.getClass().getName()
            );
         }

         if (var12 == null) {
            throw new IOException("Can't serialize unregistered packet");
         }

         PacketBuffer var8 = new PacketBuffer(byteBuf);
         var8.writeVarIntToBuffer(var12);

         try {
            packet.writePacketData(var8);
         } catch (Throwable var10) {
            logger.error(var10);
         }
      } else {
         PacketBuffer var7 = new PacketBuffer(Unpooled.buffer());

         try {
            packet.writePacketData(var7);
         } catch (Throwable var11) {
            logger.error(var11);
         }

         channelHandlerContext.channel().pipeline().context(MCPEncodeHandler.class).writeAndFlush(var7);
      }
   }
}
