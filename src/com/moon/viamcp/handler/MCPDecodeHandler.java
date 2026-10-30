package com.moon.viamcp.handler;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@Sharable
public class MCPDecodeHandler extends MessageToMessageDecoder<ByteBuf> {
   private final UserConnection info;

   public MCPDecodeHandler(UserConnection userConnection) {
      this.info = userConnection;
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
      if (!this.info.checkIncomingPacket()) {
         throw CancelDecoderException.generate(null);
      }

      if (!this.info.shouldTransformPacket()) {
         out.add(byteBuf.retain());
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(byteBuf);

         try {
            this.info.transformIncoming(transformedBuf, CancelDecoderException::generate);
            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }
      }
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (!PipelineUtil.containsCause(cause, CancelCodecException.class)) {
         if (PipelineUtil.containsCause(cause, InformativeException.class) && this.info.getProtocolInfo().getState() != State.HANDSHAKE) {
            cause.printStackTrace();
         }
      }
   }
}
