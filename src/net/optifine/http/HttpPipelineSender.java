package net.optifine.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpPipelineSender extends Thread {
   private HttpPipelineConnection httpPipelineConnection = null;
   private static final Charset b = StandardCharsets.US_ASCII;

   public HttpPipelineSender(HttpPipelineConnection httpPipelineConnection) {
      super("HttpPipelineSender");
      this.httpPipelineConnection = httpPipelineConnection;
   }

   @Override
   public void run() {
      HttpPipelineRequest var4 = null;

      try {
         this.connect();

         while (!Thread.interrupted()) {
            var4 = this.httpPipelineConnection.getNextRequestSend();
            HttpRequest var5 = var4.getHttpRequest();
            OutputStream var6 = this.httpPipelineConnection.getOutputStream();
            this.writeRequest(var5, var6);
            this.httpPipelineConnection.onRequestSent(var4);
         }
      } catch (InterruptedException var7) {
      } catch (Exception var8) {
         this.httpPipelineConnection.onExceptionSend(var4, var8);
      }
   }

   private void connect() throws IOException {
      String var3 = this.httpPipelineConnection.getHost();
      int var4 = this.httpPipelineConnection.getPort();
      Proxy var5 = this.httpPipelineConnection.getProxy();
      Socket var6 = new Socket(var5);
      var6.connect(new InetSocketAddress(var3, var4), 5000);
      this.httpPipelineConnection.setSocket(var6);
   }

   private void writeRequest(HttpRequest httpRequest, OutputStream outputStream) throws IOException {
      this.write(outputStream, httpRequest.getMethod() + " " + httpRequest.getFile() + " " + httpRequest.Method529() + "\r\n");
      Map<String, String> var6 = httpRequest.getHeaders();

      for (String var8 : var6.keySet()) {
         String var9 = httpRequest.getHeaders().get(var8);
         this.write(outputStream, var8 + ": " + var9 + "\r\n");
      }

      this.write(outputStream, "\r\n");
   }

   private void write(OutputStream outputStream, String string) throws IOException {
      byte[] var3 = string.getBytes(b);
      outputStream.write(var3);
   }
}
