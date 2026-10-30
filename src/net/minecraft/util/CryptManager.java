package net.minecraft.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CryptManager {
   private static final Logger LOGGER = LogManager.getLogger();

   public static SecretKey createNewSharedKey() {
      try {
         KeyGenerator var2 = KeyGenerator.getInstance("AES");
         var2.init(128);
         return var2.generateKey();
      } catch (NoSuchAlgorithmException var3) {
         throw new Error(var3);
      }
   }

   public static KeyPair generateKeyPair() {
      try {
         KeyPairGenerator var2 = KeyPairGenerator.getInstance("RSA");
         var2.initialize(1024);
         return var2.generateKeyPair();
      } catch (NoSuchAlgorithmException var3) {
         var3.printStackTrace();
         LOGGER.error("Key pair generation failed!");
         return null;
      }
   }

   public static byte[] getServerIdHash(String string, PublicKey publicKey, SecretKey secretKey) {
      try {
         return digestOperation("SHA-1", string.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
      } catch (UnsupportedEncodingException var3) {
         var3.printStackTrace();
         return null;
      }
   }

   private static byte[] digestOperation(String string, byte[]... arr) {
      try {
         MessageDigest var5 = MessageDigest.getInstance(string);

         for (byte[] var9 : arr) {
            var5.update(var9);
         }

         return var5.digest();
      } catch (NoSuchAlgorithmException var10) {
         var10.printStackTrace();
         return null;
      }
   }

   public static PublicKey decodePublicKey(byte[] arr) {
      try {
         X509EncodedKeySpec var1 = new X509EncodedKeySpec(arr);
         KeyFactory var2 = KeyFactory.getInstance("RSA");
         return var2.generatePublic(var1);
      } catch (NoSuchAlgorithmException | InvalidKeySpecException var3) {
         LOGGER.error("Public key reconstitute failed!");
         return null;
      }
   }

   public static SecretKey decryptSharedKey(PrivateKey privateKey, byte[] arr) {
      return new SecretKeySpec(decryptData(privateKey, arr), "AES");
   }

   public static byte[] encryptData(Key key, byte[] arr) {
      return cipherOperation(1, key, arr);
   }

   public static byte[] decryptData(Key key, byte[] arr) {
      return cipherOperation(2, key, arr);
   }

   private static byte[] cipherOperation(int integer, Key key, byte[] arr) {
      try {
         return createTheCipherInstance(integer, key.getAlgorithm(), key).doFinal(arr);
      } catch (IllegalBlockSizeException | BadPaddingException var3) {
         var3.printStackTrace();
         LOGGER.error("Cipher data failed!");
         return null;
      }
   }

   private static Cipher createTheCipherInstance(int integer, String string, Key key) {
      try {
         Cipher var3 = Cipher.getInstance(string);
         var3.init(integer, key);
         return var3;
      } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException var4) {
         var4.printStackTrace();
         LOGGER.error("Cipher creation failed!");
         return null;
      }
   }

   public static Cipher createNetCipherInstance(int integer, Key key) {
      try {
         Cipher var2 = Cipher.getInstance("AES/CFB8/NoPadding");
         var2.init(integer, key, new IvParameterSpec(key.getEncoded()));
         return var2;
      } catch (GeneralSecurityException var3) {
         throw new RuntimeException(var3);
      }
   }
}
