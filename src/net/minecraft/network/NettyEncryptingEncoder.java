package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;

public class NettyEncryptingEncoder extends MessageToByteEncoder<ByteBuf> {
   private final NettyEncryptionTranslator encryptionCodec;

   public NettyEncryptingEncoder(Cipher cipher) {
      this.encryptionCodec = new NettyEncryptionTranslator(cipher);
   }

   protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf2, ByteBuf byteBuf3) throws Exception {
      this.encryptionCodec.cipher(byteBuf2, byteBuf3);
   }
}
