package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class MessageDeserializer extends ByteToMessageDecoder {
   private static final Logger logger = LogManager.getLogger();
   private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.logMarkerPackets);
   private final EnumPacketDirection direction;

   public MessageDeserializer(EnumPacketDirection enumPacketDirection) {
      this.direction = enumPacketDirection;
   }

   @Override
   protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
      if (byteBuf.readableBytes() != 0) {
         PacketBuffer var7 = new PacketBuffer(byteBuf);
         int var8 = var7.readVarIntFromBuffer();
         Packet var9 = ((EnumConnectionState)channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacket(this.direction, var8);
         if (var9 == null) {
            throw new IOException("Bad packet id " + var8);
         }

         var9.readPacketData(var7);
         if (var7.readableBytes() > 0) {
            throw new IOException(
               "Packet "
                  + ((EnumConnectionState)channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get()).getId()
                  + "/"
                  + var8
                  + " ("
                  + var9.getClass().getSimpleName()
                  + ") was larger than I expected, found "
                  + var7.readableBytes()
                  + " bytes extra whilst reading packet "
                  + var8
            );
         }

         list.add(var9);
         if (logger.isDebugEnabled()) {
            logger.debug(
               RECEIVED_PACKET_MARKER,
               " IN: [{}:{}] {}",
               channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get(),
               var8,
               var9.getClass().getName()
            );
         }
      }
   }
}
