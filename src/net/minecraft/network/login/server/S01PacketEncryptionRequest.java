package net.minecraft.network.login.server;

import java.io.IOException;
import java.security.PublicKey;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.CryptManager;

public class S01PacketEncryptionRequest implements Packet<INetHandlerLoginClient> {
   private String hashedServerId;
   private PublicKey publicKey;
   private byte[] verifyToken;

   public S01PacketEncryptionRequest() {
   }

   public S01PacketEncryptionRequest(String string, PublicKey publicKey, byte[] arr) {
      this.hashedServerId = string;
      this.publicKey = publicKey;
      this.verifyToken = arr;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.hashedServerId = buffer.readStringFromBuffer(20);
      this.publicKey = CryptManager.decodePublicKey(buffer.readByteArray());
      this.verifyToken = buffer.readByteArray();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.hashedServerId);
      buffer.writeByteArray(this.publicKey.getEncoded());
      buffer.writeByteArray(this.verifyToken);
   }

   public void processPacket(INetHandlerLoginClient handler) {
      handler.handleEncryptionRequest(this);
   }

   public String getServerId() {
      return this.hashedServerId;
   }

   public PublicKey getPublicKey() {
      return this.publicKey;
   }

   public byte[] getVerifyToken() {
      return this.verifyToken;
   }
}
