package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.network.PacketBuffer;

public class MessageSerializer2 extends MessageToByteEncoder<ByteBuf> {
   protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf2, ByteBuf byteBuf3) throws Exception {
      int var6 = byteBuf2.readableBytes();
      int var7 = PacketBuffer.getVarIntSize(var6);
      if (var7 > 3) {
         throw new IllegalArgumentException("unable to fit " + var6 + " into 3");
      }

      PacketBuffer var8 = new PacketBuffer(byteBuf3);
      var8.ensureWritable(var7 + var6);
      var8.writeVarIntToBuffer(var6);
      var8.writeBytes(byteBuf2, byteBuf2.readerIndex(), var6);
   }
}
