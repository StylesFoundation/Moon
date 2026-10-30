package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;
import net.minecraft.network.PacketBuffer;

public class MessageDeserializer2 extends ByteToMessageDecoder {
   @Override
   protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
      byteBuf.markReaderIndex();
      byte[] var7 = new byte[3];

      for (int var8 = 0; var8 < var7.length; var8++) {
         if (!byteBuf.isReadable()) {
            byteBuf.resetReaderIndex();
            return;
         }

         var7[var8] = byteBuf.readByte();
         if (var7[var8] >= 0) {
            PacketBuffer var9 = new PacketBuffer(Unpooled.wrappedBuffer(var7));

            try {
               int var10 = var9.readVarIntFromBuffer();
               if (byteBuf.readableBytes() < var10) {
                  byteBuf.resetReaderIndex();
                  return;
               }

               list.add(byteBuf.readBytes(var10));
            } finally {
               var9.release();
            }

            return;
         }
      }

      throw new CorruptedFrameException("length wider than 21-bit");
   }
}
