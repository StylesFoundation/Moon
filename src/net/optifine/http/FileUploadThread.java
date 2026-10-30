package net.optifine.http;

import java.util.Map;

public class FileUploadThread extends Thread {
   private final String urlString;
   private final Map headers;
   private final byte[] content;
   private final IFileUploadListener listener;

   public FileUploadThread(String string, Map map, byte[] arr, IFileUploadListener iFileUploadListener) {
      this.urlString = string;
      this.headers = map;
      this.content = arr;
      this.listener = iFileUploadListener;
   }

   @Override
   public void run() {
      try {
         HttpUtils.Method553(this.urlString, this.headers, this.content);
         this.listener.fileUploadFinished(this.urlString, this.content, null);
      } catch (Exception var2) {
         this.listener.fileUploadFinished(this.urlString, this.content, var2);
      }
   }

   public String getUrlString() {
      return this.urlString;
   }

   public byte[] getContent() {
      return this.content;
   }

   public IFileUploadListener getListener() {
      return this.listener;
   }
}
