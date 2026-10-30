package com.moon.viamcp.handler;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@Sharable
public class MCPEncodeHandler extends MessageToMessageEncoder<ByteBuf> {
   private final UserConnection info;

   public MCPEncodeHandler(UserConnection userConnection) {
      this.info = userConnection;
   }

   protected void encode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
      if (!this.info.checkOutgoingPacket()) {
         throw CancelEncoderException.generate(null);
      }

      if (!this.info.shouldTransformPacket()) {
         out.add(byteBuf.retain());
      } else {
         Type.VAR_INT.readPrimitive(byteBuf);
         byteBuf = byteBuf.readerIndex(0);
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(byteBuf);

         try {
            this.info.transformOutgoing(transformedBuf, CancelEncoderException::generate);
            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }
      }
   }

   @Override
   public boolean acceptOutboundMessage(Object msg) throws Exception {
      return super.acceptOutboundMessage(msg);
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (!PipelineUtil.containsCause(cause, CancelCodecException.class)) {
         super.exceptionCaught(ctx, cause);
      }
   }
}
