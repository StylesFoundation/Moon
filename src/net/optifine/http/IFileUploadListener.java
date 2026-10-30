package net.optifine.http;

public interface IFileUploadListener {
   void fileUploadFinished(String string, byte[] arr, Throwable throwable);
}
