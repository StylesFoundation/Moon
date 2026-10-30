package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.Proxy.Type;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.http.HttpPipeline;
import net.optifine.http.HttpResponse;
import net.optifine.player.CapeImageBuffer;
import net.optifine.shaders.ShadersTex;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadDownloadImageData extends SimpleTexture {
   private static final Logger logger = LogManager.getLogger();
   private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
   private final File cacheFile;
   private final String imageUrl;
   private final IImageBuffer imageBuffer;
   private BufferedImage bufferedImage;
   private Thread imageThread;
   private boolean textureUploaded;
   public Boolean imageFound = null;
   public boolean pipeline = false;

   public ThreadDownloadImageData(File file, String string, ResourceLocation resourceLocation, IImageBuffer iImageBuffer) {
      super(resourceLocation);
      this.cacheFile = file;
      this.imageUrl = string;
      this.imageBuffer = iImageBuffer;
   }

   private void checkTextureUploaded() {
      if (!this.textureUploaded && this.bufferedImage != null) {
         this.textureUploaded = true;
         if (this.textureLocation != null) {
            this.deleteGlTexture();
         }

         if (Config.isShaders()) {
            ShadersTex.loadSimpleTexture(
               super.getGlTextureId(), this.bufferedImage, false, false, Config.getResourceManager(), this.textureLocation, this.getMultiTexID()
            );
         } else {
            TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
         }
      }
   }

   @Override
   public int getGlTextureId() {
      this.checkTextureUploaded();
      return super.getGlTextureId();
   }

   public void setBufferedImage(BufferedImage bufferedImage) {
      this.bufferedImage = bufferedImage;
      if (this.imageBuffer != null) {
         this.imageBuffer.skinAvailable();
      }

      this.imageFound = this.bufferedImage != null;
   }

   @Override
   public void loadTexture(IResourceManager iResourceManager) throws IOException {
      if (this.bufferedImage == null && this.textureLocation != null) {
         super.loadTexture(iResourceManager);
      }

      if (this.imageThread == null) {
         if (this.cacheFile != null && this.cacheFile.isFile()) {
            logger.debug("Loading http texture from local cache ({})", new Object[]{this.cacheFile});

            try {
               this.bufferedImage = ImageIO.read(this.cacheFile);
               if (this.imageBuffer != null) {
                  this.setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
               }

               this.loadingFinished();
            } catch (IOException var3) {
               logger.error("Couldn't load skin " + this.cacheFile, var3);
               this.loadTextureFromServer();
            }
         } else {
            this.loadTextureFromServer();
         }
      }
   }

   protected void loadTextureFromServer() {
      this.imageThread = new Thread("Texture Downloader #" + threadDownloadCounter.incrementAndGet()) {
         @Override
         public void run() {
            HttpURLConnection var3 = null;
            ThreadDownloadImageData.logger
               .debug("Downloading http texture from {} to {}", ThreadDownloadImageData.this.imageUrl, ThreadDownloadImageData.this.cacheFile);
            if (ThreadDownloadImageData.this.shouldPipeline()) {
               ThreadDownloadImageData.this.loadPipelined();
            } else {
               try {
                  var3 = (HttpURLConnection)new URL(ThreadDownloadImageData.this.imageUrl).openConnection(Minecraft.getMinecraft().getProxy());
                  var3.setDoInput(true);
                  var3.setDoOutput(false);
                  var3.connect();
                  if (var3.getResponseCode() / 100 == 2) {
                     BufferedImage var4;
                     if (ThreadDownloadImageData.this.cacheFile != null) {
                        FileUtils.copyInputStreamToFile(var3.getInputStream(), ThreadDownloadImageData.this.cacheFile);
                        var4 = ImageIO.read(ThreadDownloadImageData.this.cacheFile);
                     } else {
                        var4 = TextureUtil.readBufferedImage(var3.getInputStream());
                     }

                     if (ThreadDownloadImageData.this.imageBuffer != null) {
                        var4 = ThreadDownloadImageData.this.imageBuffer.parseUserSkin(var4);
                     }

                     ThreadDownloadImageData.this.setBufferedImage(var4);
                     return;
                  }

                  if (var3.getErrorStream() != null) {
                     Config.readAll(var3.getErrorStream());
                  }
               } catch (Exception var8) {
                  ThreadDownloadImageData.logger.error("Couldn't download http texture: " + var8.getClass().getName() + ": " + var8.getMessage());
                  return;
               } finally {
                  if (var3 != null) {
                     var3.disconnect();
                  }

                  ThreadDownloadImageData.this.loadingFinished();
               }
            }
         }
      };
      this.imageThread.setDaemon(true);
      this.imageThread.start();
   }

   private boolean shouldPipeline() {
      if (!this.pipeline) {
         return false;
      }

      Proxy var1 = Minecraft.getMinecraft().getProxy();
      return (var1.type() == Type.DIRECT || var1.type() == Type.SOCKS) && this.imageUrl.startsWith("http://");
   }

   private void loadPipelined() {
      try {
         HttpResponse var4 = HttpPipeline.executeRequest(HttpPipeline.makeRequest(this.imageUrl, Minecraft.getMinecraft().getProxy()));
         if (var4.getStatus() / 100 == 2) {
            byte[] var5 = var4.getBody();
            ByteArrayInputStream var6 = new ByteArrayInputStream(var5);
            BufferedImage var7;
            if (this.cacheFile != null) {
               FileUtils.copyInputStreamToFile(var6, this.cacheFile);
               var7 = ImageIO.read(this.cacheFile);
            } else {
               var7 = TextureUtil.readBufferedImage(var6);
            }

            if (this.imageBuffer != null) {
               var7 = this.imageBuffer.parseUserSkin(var7);
            }

            this.setBufferedImage(var7);
            return;
         }
      } catch (Exception var11) {
         logger.error("Couldn't download http texture: " + var11.getClass().getName() + ": " + var11.getMessage());
         return;
      } finally {
         this.loadingFinished();
      }
   }

   private void loadingFinished() {
      this.imageFound = this.bufferedImage != null;
      IImageBuffer var2 = this.imageBuffer;
      if (var2 instanceof CapeImageBuffer) {
         ((CapeImageBuffer)var2).cleanup();
      }
   }

   public IImageBuffer getImageBuffer() {
      return this.imageBuffer;
   }
}
