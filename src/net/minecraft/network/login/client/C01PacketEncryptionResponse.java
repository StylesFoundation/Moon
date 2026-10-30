package net.minecraft.network.login.client;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.util.CryptManager;

public class C01PacketEncryptionResponse implements Packet<INetHandlerLoginServer> {
   private byte[] secretKeyEncrypted = new byte[0];
   private byte[] verifyTokenEncrypted = new byte[0];

   public C01PacketEncryptionResponse() {
   }

   public C01PacketEncryptionResponse(SecretKey secretKey, PublicKey publicKey, byte[] arr) {
      this.secretKeyEncrypted = CryptManager.encryptData(publicKey, secretKey.getEncoded());
      this.verifyTokenEncrypted = CryptManager.encryptData(publicKey, arr);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.secretKeyEncrypted = buffer.readByteArray();
      this.verifyTokenEncrypted = buffer.readByteArray();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByteArray(this.secretKeyEncrypted);
      buffer.writeByteArray(this.verifyTokenEncrypted);
   }

   public void processPacket(INetHandlerLoginServer handler) {
      handler.processEncryptionResponse(this);
   }

   public SecretKey getSecretKey(PrivateKey privateKey) {
      return CryptManager.decryptSharedKey(privateKey, this.secretKeyEncrypted);
   }

   public byte[] getVerifyToken(PrivateKey privateKey) {
      return privateKey == null ? this.verifyTokenEncrypted : CryptManager.decryptData(privateKey, this.verifyTokenEncrypted);
   }
}
