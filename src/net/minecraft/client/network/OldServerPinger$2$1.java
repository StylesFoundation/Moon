package net.minecraft.client.network;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import moom.l;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class OldServerPinger$2$1 extends SimpleChannelInboundHandler<ByteBuf> {
   public final l this$1;

   public OldServerPinger$2$1(l l) {
      this.this$1 = l;
   }

   @Override
   public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
      super.channelActive(channelHandlerContext);
      ByteBuf var5 = Unpooled.buffer();

      try {
         var5.writeByte(254);
         var5.writeByte(1);
         var5.writeByte(250);
         char[] var6 = "MC|PingHost".toCharArray();
         var5.writeShort(var6.length);

         for (char var10 : var6) {
            var5.writeChar(var10);
         }

         var5.writeShort(7 + 2 * this.this$1.a.getIP().length());
         var5.writeByte(127);
         var6 = this.this$1.a.getIP().toCharArray();
         var5.writeShort(var6.length);

         for (char var18 : var6) {
            var5.writeChar(var18);
         }

         var5.writeInt(this.this$1.a.getPort());
         channelHandlerContext.channel().writeAndFlush(var5).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
      } finally {
         var5.release();
      }
   }

   protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
      short var6 = byteBuf.readUnsignedByte();
      if (var6 == 255) {
         String var7 = new String(byteBuf.readBytes(byteBuf.readShort() * 2).array(), Charsets.UTF_16BE);
         String[] var8 = (String[])Iterables.toArray(Splitter.on('\u0000').limit(6).split(var7), String.class);
         if ("§1".equals(var8[0])) {
            MathHelper.parseIntWithDefault(var8[1], 0);
            String var10 = var8[2];
            String var11 = var8[3];
            int var12 = MathHelper.parseIntWithDefault(var8[4], -1);
            int var13 = MathHelper.parseIntWithDefault(var8[5], -1);
            this.this$1.b.version = -1;
            this.this$1.b.g = var10;
            this.this$1.b.d = var11;
            this.this$1.b.c = "" + EnumChatFormatting.GRAY + var12 + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var13;
         }
      }

      channelHandlerContext.close();
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
      channelHandlerContext.close();
   }
}
