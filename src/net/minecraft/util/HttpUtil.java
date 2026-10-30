package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtil {
   public static final ListeningExecutorService field_180193_a = MoreExecutors.listeningDecorator(
      Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Downloader %d").build())
   );
   private static final AtomicInteger downloadThreadsStarted = new AtomicInteger(0);
   private static final Logger logger = LogManager.getLogger();

   public static String buildPostString(Map<String, Object> map) {
      StringBuilder var4 = new StringBuilder();

      for (Entry var6 : map.entrySet()) {
         if (var4.length() > 0) {
            var4.append('&');
         }

         var4.append(URLEncoder.encode((String)var6.getKey(), StandardCharsets.UTF_8));
         if (var6.getValue() != null) {
            var4.append('=');
            var4.append(URLEncoder.encode(var6.getValue().toString(), StandardCharsets.UTF_8));
         }
      }

      return var4.toString();
   }

   public static String postMap(URL uRL, Map<String, Object> map, boolean boolean3) {
      return post(uRL, buildPostString(map), boolean3);
   }

   private static String post(URL uRL, String string, boolean boolean3) {
      try {
         Proxy var6 = MinecraftServer.getServer() == null ? null : MinecraftServer.getServer().getServerProxy();
         if (var6 == null) {
            var6 = Proxy.NO_PROXY;
         }

         HttpURLConnection var7 = (HttpURLConnection)uRL.openConnection(var6);
         var7.setRequestMethod("POST");
         var7.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         var7.setRequestProperty("Content-Length", string.getBytes().length + "");
         var7.setRequestProperty("Content-Language", "en-US");
         var7.setUseCaches(false);
         var7.setDoInput(true);
         var7.setDoOutput(true);
         DataOutputStream var8 = new DataOutputStream(var7.getOutputStream());
         var8.writeBytes(string);
         var8.flush();
         var8.close();
         BufferedReader var9 = new BufferedReader(new InputStreamReader(var7.getInputStream()));
         StringBuilder var10 = new StringBuilder();

         String var11;
         while ((var11 = var9.readLine()) != null) {
            var10.append(var11);
            var10.append('\r');
         }

         var9.close();
         return var10.toString();
      } catch (Exception var12) {
         if (!boolean3) {
            logger.error("Could not post to " + uRL, var12);
         }

         return "";
      }
   }

   public static ListenableFuture<Object> downloadResourcePack(
      File file, String string, Map<String, String> map, int integer, IProgressUpdate iProgressUpdate, Proxy proxy
   ) {
      return (ListenableFuture<Object>)field_180193_a.submit(() -> {
         HttpURLConnection var9 = null;
         InputStream var10 = null;
         DataOutputStream var11 = null;
         if (iProgressUpdate != null) {
            iProgressUpdate.resetProgressAndMessage("Downloading Resource Pack");
            iProgressUpdate.displayLoadingString("Making Request...");
         }

         boolean var23 = false /* VF: Semaphore variable */;

         label276: {
            label275: {
               try {
                  var23 = true;
                  byte[] var12 = new byte[4096];
                  var9 = (HttpURLConnection)new URL(string).openConnection(proxy);
                  float var14 = 0.0F;
                  float var15 = map.entrySet().size();

                  for (Entry var17 : map.entrySet()) {
                     var9.setRequestProperty((String)var17.getKey(), (String)var17.getValue());
                     if (iProgressUpdate != null) {
                        iProgressUpdate.setLoadingProgress((int)(++var14 / var15 * 100.0F));
                     }
                  }

                  var10 = var9.getInputStream();
                  var15 = var9.getContentLength();
                  int var28 = var9.getContentLength();
                  if (iProgressUpdate != null) {
                     iProgressUpdate.displayLoadingString(String.format("Downloading file (%.2f MB)...", var15 / 1000.0F / 1000.0F));
                  }

                  if (file.exists()) {
                     long var29 = file.length();
                     if (var29 == var28) {
                        if (iProgressUpdate != null) {
                           iProgressUpdate.setDoneWorking();
                           var23 = false;
                        } else {
                           var23 = false;
                        }
                        break label276;
                     }

                     logger.warn("Deleting " + file + " as it does not match what we currently have (" + var28 + " vs our " + var29 + ").");
                     FileUtils.deleteQuietly(file);
                  } else if (file.getParentFile() != null) {
                     file.getParentFile().mkdirs();
                  }

                  var11 = new DataOutputStream(new FileOutputStream(file));
                  if (integer > 0 && var15 > integer) {
                     if (iProgressUpdate != null) {
                        iProgressUpdate.setDoneWorking();
                     }

                     throw new IOException("Filesize is bigger than maximum allowed (file is " + var14 + ", limit is " + integer + ")");
                  }

                  int var30;
                  while ((var30 = var10.read(var12)) >= 0) {
                     var14 += var30;
                     if (iProgressUpdate != null) {
                        iProgressUpdate.setLoadingProgress((int)(var14 / var15 * 100.0F));
                     }

                     if (integer > 0 && var14 > integer) {
                        if (iProgressUpdate != null) {
                           iProgressUpdate.setDoneWorking();
                        }

                        throw new IOException("Filesize was bigger than maximum allowed (got >= " + var14 + ", limit was " + integer + ")");
                     }

                     if (Thread.interrupted()) {
                        logger.error("INTERRUPTED");
                        if (iProgressUpdate != null) {
                           iProgressUpdate.setDoneWorking();
                           var23 = false;
                        } else {
                           var23 = false;
                        }
                        break label275;
                     }

                     var11.write(var12, 0, var30);
                  }

                  if (iProgressUpdate != null) {
                     iProgressUpdate.setDoneWorking();
                     var23 = false;
                  } else {
                     var23 = false;
                  }
               } catch (Throwable var25) {
                  var25.printStackTrace();
                  if (var9 != null) {
                     InputStream var13 = var9.getErrorStream();

                     try {
                        logger.error(IOUtils.toString(var13));
                     } catch (IOException var24) {
                        var24.printStackTrace();
                     }
                  }

                  if (iProgressUpdate != null) {
                     iProgressUpdate.setDoneWorking();
                     var23 = false;
                  } else {
                     var23 = false;
                  }
               } finally {
                  if (var23) {
                     IOUtils.closeQuietly(var10);
                     IOUtils.closeQuietly(var11);
                  }
               }

               IOUtils.closeQuietly(var10);
               IOUtils.closeQuietly(var11);
               return;
            }

            IOUtils.closeQuietly(var10);
            IOUtils.closeQuietly(var11);
            return;
         }

         IOUtils.closeQuietly(var10);
         IOUtils.closeQuietly((OutputStream)null);
      });
   }

   public static int getSuitableLanPort() throws IOException {
      ServerSocket var3 = null;

      int var4;
      try {
         var3 = new ServerSocket(0);
         var4 = var3.getLocalPort();
      } finally {
         try {
            if (var3 != null) {
               var3.close();
            }
         } catch (IOException var10) {
         }
      }

      return var4;
   }

   public static String get(URL uRL) throws IOException {
      HttpURLConnection var4 = (HttpURLConnection)uRL.openConnection();
      var4.setRequestMethod("GET");
      BufferedReader var5 = new BufferedReader(new InputStreamReader(var4.getInputStream()));
      StringBuilder var6 = new StringBuilder();

      String var7;
      while ((var7 = var5.readLine()) != null) {
         var6.append(var7);
         var6.append('\r');
      }

      var5.close();
      return var6.toString();
   }
}
