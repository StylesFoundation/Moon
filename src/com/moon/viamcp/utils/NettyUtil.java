package com.moon.viamcp.utils;

import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;

public class NettyUtil {
   public static void decompress(ChannelHandlerContext ctx, ByteBuf byteBuf) throws InvocationTargetException {
      ChannelHandler handler = ctx.pipeline().get("decompress");
      ByteBuf decompressedBuf = handler instanceof MessageToMessageDecoder
         ? (ByteBuf)PipelineUtil.callDecode((MessageToMessageDecoder)handler, ctx, byteBuf).get(0)
         : (ByteBuf)PipelineUtil.callDecode((ByteToMessageDecoder)handler, ctx, byteBuf).get(0);

      try {
         byteBuf.clear().writeBytes(decompressedBuf);
      } finally {
         decompressedBuf.release();
      }
   }

   public static void compress(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
      ByteBuf compressedBuf = ctx.alloc().buffer();

      try {
         PipelineUtil.callEncode((MessageToByteEncoder)ctx.pipeline().get("compress"), ctx, byteBuf, compressedBuf);
         byteBuf.clear().writeBytes(compressedBuf);
      } finally {
         compressedBuf.release();
      }
   }
}
