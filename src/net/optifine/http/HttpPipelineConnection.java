package net.optifine.http;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.src.Config;

public class HttpPipelineConnection {
   private String host = null;
   private int port = 0;
   private Proxy proxy = Proxy.NO_PROXY;
   private final List<HttpPipelineRequest> listRequests = new LinkedList<>();
   private final List<HttpPipelineRequest> listRequestsSend = new LinkedList<>();
   private Socket socket = null;
   private InputStream inputStream = null;
   private OutputStream outputStream = null;
   private HttpPipelineSender httpPipelineSender = null;
   private HttpPipelineReceiver httpPipelineReceiver = null;
   private int countRequests = 0;
   private boolean responseReceived = false;
   private long keepaliveTimeoutMs = 5000L;
   private int keepaliveMaxCount = 1000;
   private long timeLastActivityMs = System.currentTimeMillis();
   private boolean terminated = false;
   private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");

   public HttpPipelineConnection(String string, int integer) {
      this(string, integer, Proxy.NO_PROXY);
   }

   public HttpPipelineConnection(String string, int integer, Proxy proxy) {
      this.host = string;
      this.port = integer;
      this.proxy = proxy;
      this.httpPipelineSender = new HttpPipelineSender(this);
      this.httpPipelineSender.start();
      this.httpPipelineReceiver = new HttpPipelineReceiver(this);
      this.httpPipelineReceiver.start();
   }

   public synchronized boolean addRequest(HttpPipelineRequest httpPipelineRequest) {
      if (this.isClosed()) {
         return false;
      }

      this.addRequest(httpPipelineRequest, this.listRequests);
      this.addRequest(httpPipelineRequest, this.listRequestsSend);
      this.countRequests++;
      return true;
   }

   private void addRequest(HttpPipelineRequest httpPipelineRequest, List<HttpPipelineRequest> list) {
      list.add(httpPipelineRequest);
      this.notifyAll();
   }

   public synchronized void setSocket(Socket socket) throws IOException {
      if (!this.terminated) {
         if (this.socket != null) {
            throw new IllegalArgumentException("Already connected");
         }

         this.socket = socket;
         this.socket.setTcpNoDelay(true);
         this.inputStream = this.socket.getInputStream();
         this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
         this.onActivity();
         this.notifyAll();
      }
   }

   public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
      while (this.outputStream == null) {
         this.checkTimeout();
         this.wait(1000L);
      }

      return this.outputStream;
   }

   public synchronized InputStream getInputStream() throws IOException, InterruptedException {
      while (this.inputStream == null) {
         this.checkTimeout();
         this.wait(1000L);
      }

      return this.inputStream;
   }

   public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException {
      if (this.listRequestsSend.size() == 0 && this.outputStream != null) {
         this.outputStream.flush();
      }

      return this.getNextRequest(this.listRequestsSend, true);
   }

   public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
      return this.getNextRequest(this.listRequests, false);
   }

   private HttpPipelineRequest getNextRequest(List<HttpPipelineRequest> list, boolean boolean2) throws InterruptedException {
      while (list.size() == 0) {
         this.checkTimeout();
         this.wait(1000L);
      }

      this.onActivity();
      return boolean2 ? (HttpPipelineRequest)list.remove(0) : (HttpPipelineRequest)list.get(0);
   }

   private void checkTimeout() {
      if (this.socket != null) {
         long var4 = this.keepaliveTimeoutMs;
         if (this.listRequests.size() > 0) {
            var4 = 5000L;
         }

         long var6 = System.currentTimeMillis();
         if (var6 > this.timeLastActivityMs + var4) {
            this.terminate(new InterruptedException("Timeout " + var4));
         }
      }
   }

   private void onActivity() {
      this.timeLastActivityMs = System.currentTimeMillis();
   }

   public synchronized void onRequestSent(HttpPipelineRequest httpPipelineRequest) {
      if (!this.terminated) {
         this.onActivity();
      }
   }

   public synchronized void onResponseReceived(HttpPipelineRequest httpPipelineRequest, HttpResponse httpResponse) {
      if (!this.terminated) {
         this.responseReceived = true;
         this.onActivity();
         if (this.listRequests.size() <= 0 || this.listRequests.get(0) != httpPipelineRequest) {
            throw new IllegalArgumentException("Response out of order: " + httpPipelineRequest);
         }

         this.listRequests.remove(0);
         httpPipelineRequest.setClosed(true);
         String var6 = httpResponse.getHeader("Location");
         if (httpResponse.getStatus() / 100 == 3 && var6 != null && httpPipelineRequest.getHttpRequest().getRedirects() < 5) {
            try {
               HttpRequest var7 = HttpPipeline.makeRequest(
                  this.normalizeUrl(var6, httpPipelineRequest.getHttpRequest()), httpPipelineRequest.getHttpRequest().getProxy()
               );
               var7.setRedirects(httpPipelineRequest.getHttpRequest().getRedirects() + 1);
               HttpPipeline.addRequest(new HttpPipelineRequest(var7, httpPipelineRequest.getHttpListener()));
            } catch (IOException var8) {
               httpPipelineRequest.getHttpListener().failed(httpPipelineRequest.getHttpRequest(), var8);
            }
         } else {
            httpPipelineRequest.getHttpListener().finished(httpPipelineRequest.getHttpRequest(), httpResponse);
         }

         this.checkResponseHeader(httpResponse);
      }
   }

   private String normalizeUrl(String string, HttpRequest httpRequest) {
      if (patternFullUrl.matcher(string).matches()) {
         return string;
      }

      if (string.startsWith("//")) {
         return "http:" + string;
      }

      String var6 = httpRequest.getHost();
      if (httpRequest.getPort() != 80) {
         var6 = var6 + ":" + httpRequest.getPort();
      }

      if (string.startsWith("/")) {
         String var9 = string;
         String var10 = var6;
         return "http://" + var10 + var9;
      }

      String var7 = httpRequest.getFile();
      int var8 = var7.lastIndexOf("/");
      String var10000;
      if (var8 >= 0) {
         String var10001 = var7.substring(0, var8 + 1);
         String var11 = string;
         String var12 = var10001;
         String var13 = var6;
         var10000 = "http://" + var13 + var12 + var11;
      } else {
         var10000 = "http://" + var6 + "/" + string;
      }

      return var10000;
   }

   private void checkResponseHeader(HttpResponse httpResponse) {
      String var5 = httpResponse.getHeader("Connection");
      if (var5 != null && !var5.equalsIgnoreCase("keep-alive")) {
         this.terminate(new EOFException("Connection not keep-alive"));
      }

      String var6 = httpResponse.getHeader("Keep-Alive");
      if (var6 != null) {
         for (String var11 : Config.tokenize(var6, ",;")) {
            String[] var12 = this.split(var11, '=');
            if (var12.length >= 2) {
               if (var12[0].equals("timeout")) {
                  int var13 = Config.parseInt(var12[1], -1);
                  if (var13 > 0) {
                     this.keepaliveTimeoutMs = var13 * 1000L;
                  }
               }

               if (var12[0].equals("max")) {
                  int var14 = Config.parseInt(var12[1], -1);
                  if (var14 > 0) {
                     this.keepaliveMaxCount = var14;
                  }
               }
            }
         }
      }
   }

   private String[] split(String string, char character) {
      int var6 = string.indexOf(character);
      if (var6 < 0) {
         return new String[]{string};
      }

      String var7 = string.substring(0, var6);
      String var8 = string.substring(var6 + 1);
      return new String[]{var7, var8};
   }

   public synchronized void onExceptionSend(HttpPipelineRequest httpPipelineRequest, Exception exception) {
      this.terminate(exception);
   }

   public synchronized void onExceptionReceive(HttpPipelineRequest httpPipelineRequest, Exception exception) {
      this.terminate(exception);
   }

   private synchronized void terminate(Exception exception) {
      if (!this.terminated) {
         this.terminated = true;
         this.terminateRequests(exception);
         if (this.httpPipelineSender != null) {
            this.httpPipelineSender.interrupt();
         }

         if (this.httpPipelineReceiver != null) {
            this.httpPipelineReceiver.interrupt();
         }

         try {
            if (this.socket != null) {
               this.socket.close();
            }
         } catch (IOException var2) {
         }

         this.socket = null;
         this.inputStream = null;
         this.outputStream = null;
      }
   }

   private void terminateRequests(Exception exception) {
      if (this.listRequests.size() > 0) {
         if (!this.responseReceived) {
            HttpPipelineRequest var5 = this.listRequests.remove(0);
            var5.getHttpListener().failed(var5.getHttpRequest(), exception);
            var5.setClosed(true);
         }

         while (this.listRequests.size() > 0) {
            HttpPipeline.addRequest(this.listRequests.remove(0));
         }
      }
   }

   public synchronized boolean isClosed() {
      return this.terminated || this.countRequests >= this.keepaliveMaxCount;
   }

   public int getCountRequests() {
      return this.countRequests;
   }

   public synchronized boolean hasActiveRequests() {
      return this.listRequests.size() > 0;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public Proxy getProxy() {
      return this.proxy;
   }
}
