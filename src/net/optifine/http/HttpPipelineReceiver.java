package net.optifine.http;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import net.minecraft.src.Config;

public class HttpPipelineReceiver extends Thread {
   private HttpPipelineConnection httpPipelineConnection = null;
   private static final Charset b = StandardCharsets.US_ASCII;

   public HttpPipelineReceiver(HttpPipelineConnection httpPipelineConnection) {
      super("HttpPipelineReceiver");
      this.httpPipelineConnection = httpPipelineConnection;
   }

   @Override
   public void run() {
      while (!Thread.interrupted()) {
         HttpPipelineRequest var4 = null;

         try {
            var4 = this.httpPipelineConnection.getNextRequestReceive();
            InputStream var5 = this.httpPipelineConnection.getInputStream();
            HttpResponse var6 = this.readResponse(var5);
            this.httpPipelineConnection.onResponseReceived(var4, var6);
         } catch (InterruptedException var7) {
            return;
         } catch (Exception var8) {
            this.httpPipelineConnection.onExceptionReceive(var4, var8);
         }
      }
   }

   private HttpResponse readResponse(InputStream inputStream) throws IOException {
      String var5 = this.readLine(inputStream);
      String[] var6 = Config.tokenize(var5, " ");
      if (var6.length < 3) {
         throw new IOException("Invalid status line: " + var5);
      }

      String var10000 = var6[0];
      int var8 = Config.parseInt(var6[1], 0);
      var10000 = var6[2];
      LinkedHashMap var10 = new LinkedHashMap();

      while (true) {
         String var11 = this.readLine(inputStream);
         if (var11.length() == 0) {
            byte[] var15 = null;
            String var16 = (String)var10.get("Content-Length");
            if (var16 != null) {
               int var17 = Config.parseInt(var16, -1);
               if (var17 > 0) {
                  var15 = new byte[var17];
                  this.readFull(var15, inputStream);
               }
            } else if (Config.equals((String)var10.get("Transfer-Encoding"), "chunked")) {
               var15 = this.readContentChunked(inputStream);
            }

            return new HttpResponse(var8, var5, var10, var15);
         }

         int var12 = var11.indexOf(":");
         if (var12 > 0) {
            String var13 = var11.substring(0, var12).trim();
            String var14 = var11.substring(var12 + 1).trim();
            var10.put(var13, var14);
         }
      }
   }

   private byte[] readContentChunked(InputStream inputStream) throws IOException {
      ByteArrayOutputStream var5 = new ByteArrayOutputStream();

      int var8;
      do {
         var8 = Integer.parseInt(Config.tokenize(this.readLine(inputStream), "; ")[0], 16);
         byte[] var9 = new byte[var8];
         this.readFull(var9, inputStream);
         var5.write(var9);
         this.readLine(inputStream);
      } while (var8 != 0);

      return var5.toByteArray();
   }

   private void readFull(byte[] arr, InputStream inputStream) throws IOException {
      int var7 = 0;

      while (var7 < arr.length) {
         int var6 = inputStream.read(arr, var7, arr.length - var7);
         if (var6 < 0) {
            throw new EOFException();
         }

         var7 += var6;
      }
   }

   private String readLine(InputStream inputStream) throws IOException {
      ByteArrayOutputStream var5 = new ByteArrayOutputStream();
      int var6 = -1;
      boolean var7 = false;

      while (true) {
         int var8 = inputStream.read();
         if (var8 < 0) {
            break;
         }

         var5.write(var8);
         if (var6 == 13 && var8 == 10) {
            var7 = true;
            break;
         }

         var6 = var8;
      }

      String var9 = var5.toString(b);
      if (var7) {
         var9 = var9.substring(0, var9.length() - 2);
      }

      return var9;
   }
}
