package com.moon.connection.client;

import com.aayushatharva.brotli4j.decoder.Decoder;
import com.aayushatharva.brotli4j.decoder.DirectDecompress;
import com.aayushatharva.brotli4j.decoder.DecoderJNI.Status;
import com.google.protobuf.GeneratedMessageV3;
import com.moon.connection.Gateway;
import com.moon.connection.cipher.GatewayCipher;
import com.moon.connection.packet.PacketCodec;
import com.moon.connection.protobuf.authentication.ServerboundAuthenticationRequest;
import java.net.URI;
import java.nio.ByteBuffer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class GatewayClient extends WebSocketClient {
   private final TaggedLogger logger = Logger.tag("gateway/web-socket");
   private final Gateway gateway;

   public GatewayClient(Gateway gateway, String serverUri) {
      super(URI.create(serverUri));
      this.gateway = gateway;
   }

   @Override
   public void onOpen(ServerHandshake serverHandshake) {
      this.logger.info("Successfully connected to gateway proxy");
      this.send(PacketCodec.encode(ServerboundAuthenticationRequest.newBuilder().setSecret(this.gateway.getConfig().getSecret()).build()));
   }

   @Override
   public void onMessage(String message) {
   }

   @Override
   public void onMessage(ByteBuffer buffer) {
      try {
         GatewayCipher cipher = this.gateway.getCipher();
         ByteBuffer payload = buffer;
         if (cipher != null) {
            payload = ByteBuffer.wrap(cipher.decrypt(buffer.array()));
         }

         DirectDecompress decompressResult = Decoder.decompress(payload.array());
         if (decompressResult.getResultStatus() == Status.DONE) {
            payload = ByteBuffer.wrap(decompressResult.getDecompressedData());
            this.gateway.getPacketManager().getHandler(payload.get()).handle(payload);
         } else {
            this.logger.error("Failed to decompress incoming message (%s)", new Object[]{decompressResult.getResultStatus()});
         }
      } catch (Throwable t) {
         this.logger.error("Error processing incoming message", t);
      }
   }

   @Override
   public void onClose(int code, String reason, boolean remote) {
      this.gateway.queueReconnect();
      this.gateway.cipher(null);
   }

   @Override
   public void onError(Exception exception) {
   }

   public void send(GeneratedMessageV3 message) {
      ByteBuffer encoded = PacketCodec.encode(message);
      if (this.gateway.getCipher() != null && this.isOpen()) {
         this.send(this.gateway.getCipher().encrypt(encoded.array()));
      }
   }
}
