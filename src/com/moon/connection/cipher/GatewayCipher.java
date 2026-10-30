package com.moon.connection.cipher;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class GatewayCipher {
   private final SecureRandom secureRandom = new SecureRandom();
   private final byte[] salt = this.generateSalt();
   private final String password;
   private final String iv;
   private Cipher encryptCipher;
   private Cipher decryptCipher;

   public byte[] encrypt(byte[] data) {
      this.encryptCipher = this.createCipher(1, this.salt, this.iv.getBytes());

      try {
         byte[] encrypted = this.encryptCipher.doFinal(data);
         return ByteBuffer.allocate(this.salt.length + encrypted.length).put(this.salt).put(encrypted).array();
      } catch (IllegalBlockSizeException | BadPaddingException e) {
         return new byte[0];
      }
   }

   public byte[] decrypt(byte[] data) {
      ByteBuffer buffer = ByteBuffer.wrap(data);
      byte[] salt = new byte[16];
      buffer.get(salt);
      byte[] encryptedBytes = new byte[buffer.remaining()];
      buffer.get(encryptedBytes);
      this.decryptCipher = this.createCipher(2, salt, this.iv.getBytes());

      try {
         return this.decryptCipher.doFinal(encryptedBytes);
      } catch (IllegalBlockSizeException | BadPaddingException e) {
         return new byte[0];
      }
   }

   public Cipher createCipher(int mode, byte[] key, byte[] iv) {
      try {
         Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
         cipher.init(mode, this.deriveKey(key), new GCMParameterSpec(128, iv));
         return cipher;
      } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
         e.printStackTrace();
         return null;
      }
   }

   public SecretKey deriveKey(byte[] salt) {
      try {
         SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
         PBEKeySpec spec = new PBEKeySpec(this.password.toCharArray(), salt, 25536, 256);
         return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
      } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
         e.printStackTrace();
         return null;
      }
   }

   public byte[] generateSalt() {
      byte[] salt = new byte[16];
      this.secureRandom.nextBytes(salt);
      return salt;
   }

   public SecureRandom getSecureRandom() {
      return this.secureRandom;
   }

   public byte[] getSalt() {
      return this.salt;
   }

   public String getPassword() {
      return this.password;
   }

   public String getIv() {
      return this.iv;
   }

   public Cipher getEncryptCipher() {
      return this.encryptCipher;
   }

   public Cipher getDecryptCipher() {
      return this.decryptCipher;
   }

   public GatewayCipher(String password, String iv) {
      this.password = password;
      this.iv = iv;
   }
}
