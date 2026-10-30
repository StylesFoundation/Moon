package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;

public class NettyCompressionDecoder extends ByteToMessageDecoder {
   private final Inflater inflater;
   private int treshold;

   public NettyCompressionDecoder(int integer) {
      this.treshold = integer;
      this.inflater = new Inflater();
   }

   @Override
   protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
      if (byteBuf.readableBytes() != 0) {
         PacketBuffer var7 = new PacketBuffer(byteBuf);
         int var8 = var7.readVarIntFromBuffer();
         if (var8 == 0) {
            list.add(var7.readBytes(var7.readableBytes()));
         } else {
            if (var8 < this.treshold) {
               throw new DecoderException("Badly compressed packet - size of " + var8 + " is below server threshold of " + this.treshold);
            }

            if (var8 > 2097152) {
               throw new DecoderException("Badly compressed packet - size of " + var8 + " is larger than protocol maximum of 2097152");
            }

            byte[] var9 = new byte[var7.readableBytes()];
            var7.readBytes(var9);
            this.inflater.setInput(var9);
            byte[] var10 = new byte[var8];
            this.inflater.inflate(var10);
            list.add(Unpooled.wrappedBuffer(var10));
            this.inflater.reset();
         }
      }
   }

   public void setCompressionTreshold(int integer) {
      this.treshold = integer;
   }
}
