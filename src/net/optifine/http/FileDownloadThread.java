package net.optifine.http;

import net.minecraft.client.Minecraft;

public class FileDownloadThread extends Thread {
   private String urlString = null;
   private IFileDownloadListener listener = null;

   public FileDownloadThread(String string, IFileDownloadListener iFileDownloadListener) {
      this.urlString = string;
      this.listener = iFileDownloadListener;
   }

   @Override
   public void run() {
      try {
         byte[] var1 = HttpPipeline.get(this.urlString, Minecraft.getMinecraft().getProxy());
         this.listener.fileDownloadFinished(this.urlString, var1, null);
      } catch (Exception var2) {
         this.listener.fileDownloadFinished(this.urlString, null, var2);
      }
   }

   public String getUrlString() {
      return this.urlString;
   }

   public IFileDownloadListener getListener() {
      return this.listener;
   }
}
