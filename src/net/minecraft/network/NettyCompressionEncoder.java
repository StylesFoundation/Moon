package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;

public class NettyCompressionEncoder extends MessageToByteEncoder<ByteBuf> {
   private final byte[] buffer = new byte[8192];
   private final Deflater deflater;
   private int treshold;

   public NettyCompressionEncoder(int integer) {
      this.treshold = integer;
      this.deflater = new Deflater();
   }

   protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf2, ByteBuf byteBuf3) throws Exception {
      int var7 = byteBuf2.readableBytes();
      PacketBuffer var8 = new PacketBuffer(byteBuf3);
      if (var7 < this.treshold) {
         var8.writeVarIntToBuffer(0);
         var8.writeBytes(byteBuf2);
      } else {
         byte[] var9 = new byte[var7];
         byteBuf2.readBytes(var9);
         var8.writeVarIntToBuffer(var9.length);
         this.deflater.setInput(var9, 0, var7);
         this.deflater.finish();

         while (!this.deflater.finished()) {
            int var10 = this.deflater.deflate(this.buffer);
            var8.writeBytes(this.buffer, 0, var10);
         }

         this.deflater.reset();
      }
   }

   public void setCompressionTreshold(int integer) {
      this.treshold = integer;
   }
}
