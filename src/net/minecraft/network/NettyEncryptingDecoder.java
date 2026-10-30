package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;

public class NettyEncryptingDecoder extends MessageToMessageDecoder<ByteBuf> {
   private final NettyEncryptionTranslator decryptionCodec;

   public NettyEncryptingDecoder(Cipher cipher) {
      this.decryptionCodec = new NettyEncryptionTranslator(cipher);
   }

   protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
      list.add(this.decryptionCodec.decipher(channelHandlerContext, byteBuf));
   }
}
