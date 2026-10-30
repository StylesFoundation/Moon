package com.moon.viamcp.handler;

import com.viaversion.viaversion.api.connection.UserConnection;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class PipelineReorderHandler extends ChannelInboundHandlerAdapter {
   private final UserConnection userConnection;

   public PipelineReorderHandler(UserConnection userConnection) {
      this.userConnection = userConnection;
   }

   @Override
   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      super.handlerAdded(ctx);
      ctx.pipeline().addBefore(this.getDecoderInjectPoint(), "via-decoder", new MCPDecodeHandler(this.userConnection));
      ctx.pipeline().addBefore(this.getEncoderInjectPoint(), "via-encoder", new MCPEncodeHandler(this.userConnection));
   }

   @Override
   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
      super.userEventTriggered(ctx, evt);
      if (evt instanceof CompressionReorderEvent) {
         int anchorIndex = ctx.pipeline().names().indexOf(this.getDecoderReorderAnchor());
         if (anchorIndex == -1) {
            return;
         }

         if (anchorIndex > ctx.pipeline().names().indexOf("via-decoder")) {
            ChannelHandler decoder = ctx.pipeline().get("via-decoder");
            ChannelHandler encoder = ctx.pipeline().get("via-encoder");
            ctx.pipeline().remove(decoder);
            ctx.pipeline().remove(encoder);
            ctx.pipeline().addAfter(this.getDecoderReorderAnchor(), "via-decoder", decoder);
            ctx.pipeline().addAfter(this.getEncoderReorderAnchor(), "via-encoder", encoder);
         }
      }
   }

   public abstract String getDecoderInjectPoint();

   public abstract String getEncoderInjectPoint();

   public abstract String getDecoderReorderAnchor();

   public abstract String getEncoderReorderAnchor();

   public UserConnection getUserConnection() {
      return this.userConnection;
   }
}
