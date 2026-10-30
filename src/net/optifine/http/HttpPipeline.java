package net.optifine.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.Config;

public class HttpPipeline {
   private static final Map<String, HttpPipelineConnection> mapConnections = new HashMap<String, HttpPipelineConnection>();

   public static void addRequest(String string, HttpListener httpListener) throws IOException {
      addRequest(string, httpListener, Proxy.NO_PROXY);
   }

   public static void addRequest(String string, HttpListener httpListener, Proxy proxy) throws IOException {
      HttpRequest var3 = makeRequest(string, proxy);
      addRequest(new HttpPipelineRequest(var3, httpListener));
   }

   public static HttpRequest makeRequest(String string, Proxy proxy) throws IOException {
      URL var5 = new URL(string);
      if (!var5.getProtocol().equals("http")) {
         throw new IOException("Only protocol http is supported: " + var5);
      }

      String var6 = var5.getFile();
      String var7 = var5.getHost();
      int var8 = var5.getPort();
      if (var8 <= 0) {
         var8 = 80;
      }

      String var9 = "GET";
      String var10 = "HTTP/1.1";
      LinkedHashMap var11 = new LinkedHashMap();
      var11.put("User-Agent", "Java/" + System.getProperty("java.version"));
      var11.put("Host", var7);
      var11.put("Accept", "text/html, image/gif, image/png");
      var11.put("Connection", "keep-alive");
      byte[] var12 = new byte[0];
      return new HttpRequest(var7, var8, proxy, var9, var6, var10, var11, var12);
   }

   public static void addRequest(HttpPipelineRequest httpPipelineRequest) {
      HttpRequest var4 = httpPipelineRequest.getHttpRequest();

      for (HttpPipelineConnection var5 = getConnection(var4.getHost(), var4.getPort(), var4.getProxy());
         !var5.addRequest(httpPipelineRequest);
         var5 = getConnection(var4.getHost(), var4.getPort(), var4.getProxy())
      ) {
         removeConnection(var4.getHost(), var4.getPort(), var4.getProxy(), var5);
      }
   }

   private static synchronized HttpPipelineConnection getConnection(String string, int integer, Proxy proxy) {
      String var6 = makeConnectionKey(string, integer, proxy);
      HttpPipelineConnection var7 = (HttpPipelineConnection)mapConnections.get(var6);
      if (var7 == null) {
         var7 = new HttpPipelineConnection(string, integer, proxy);
         mapConnections.put(var6, var7);
      }

      return var7;
   }

   private static synchronized void removeConnection(String string, int integer, Proxy proxy, HttpPipelineConnection httpPipelineConnection) {
      String var7 = makeConnectionKey(string, integer, proxy);
      HttpPipelineConnection var8 = (HttpPipelineConnection)mapConnections.get(var7);
      if (var8 == httpPipelineConnection) {
         mapConnections.remove(var7);
      }
   }

   private static String makeConnectionKey(String string, int integer, Proxy proxy) {
      return string + ":" + integer + "-" + proxy;
   }

   public static byte[] get(String string) throws IOException {
      return get(string, Proxy.NO_PROXY);
   }

   public static byte[] get(String string, Proxy proxy) throws IOException {
      if (string.startsWith("file:")) {
         return Config.readAll(new URL(string).openStream());
      } else {
         HttpResponse var6 = executeRequest(makeRequest(string, proxy));
         if (var6.getStatus() / 100 != 2) {
            throw new IOException("HTTP response: " + var6.getStatus());
         } else {
            return var6.getBody();
         }
      }
   }

   public static HttpResponse executeRequest(HttpRequest httpRequest) throws IOException {
      final HashMap var4 = new HashMap();
      HttpListener var7 = new HttpListener() {
         @Override
         public void finished(HttpRequest httpRequest, HttpResponse httpResponse) {
            synchronized (var4) {
               var4.put("Response", httpResponse);
               var4.notifyAll();
            }
         }

         @Override
         public void failed(HttpRequest httpRequest, Exception exception) {
            synchronized (var4) {
               var4.put("Exception", exception);
               var4.notifyAll();
            }
         }
      };
      synchronized (var4) {
         addRequest(new HttpPipelineRequest(httpRequest, var7));

         try {
            var4.wait();
         } catch (InterruptedException var13) {
            throw new InterruptedIOException("Interrupted");
         }

         Exception var10 = (Exception)var4.get("Exception");
         if (var10 != null) {
            if (var10 instanceof IOException) {
               throw (IOException)var10;
            } else if (var10 instanceof RuntimeException) {
               throw (RuntimeException)var10;
            } else {
               throw new RuntimeException(var10.getMessage(), var10);
            }
         } else {
            HttpResponse var11 = (HttpResponse)var4.get("Response");
            if (var11 == null) {
               throw new IOException("Response is null");
            } else {
               return var11;
            }
         }
      }
   }

   public static boolean hasActiveRequests() {
      for (HttpPipelineConnection var4 : mapConnections.values()) {
         if (var4.hasActiveRequests()) {
            return true;
         }
      }

      return false;
   }
}
