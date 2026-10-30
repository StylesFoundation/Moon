package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.VertexBufferUploader;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class ChunkRenderDispatcher {
   private static final Logger logger = LogManager.getLogger();
   private static final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Chunk Batcher %d").setDaemon(true).build();
   private final List<ChunkRenderWorker> listThreadedWorkers = Lists.newArrayList();
   private final BlockingQueue<ChunkCompileTaskGenerator> queueChunkUpdates = Queues.newArrayBlockingQueue(100);
   private final BlockingQueue<RegionRenderCacheBuilder> queueFreeRenderBuilders;
   private final WorldVertexBufferUploader worldVertexUploader = new WorldVertexBufferUploader();
   private final VertexBufferUploader vertexUploader = new VertexBufferUploader();
   private final Queue<ListenableFutureTask<?>> queueChunkUploads = Queues.newArrayDeque();
   private final ChunkRenderWorker renderWorker;
   private final int countRenderBuilders;
   private final List<RegionRenderCacheBuilder> listPausedBuilders = new ArrayList<>();

   public ChunkRenderDispatcher() {
      this(-1);
   }

   public ChunkRenderDispatcher(int integer) {
      int var4 = Math.max(1, (int)(Runtime.getRuntime().maxMemory() * 0.3) / 10485760);
      int var5 = Math.max(1, MathHelper.clamp_int(Runtime.getRuntime().availableProcessors() - 2, 1, var4 / 5));
      if (integer < 0) {
         this.countRenderBuilders = MathHelper.clamp_int(var5 * 8, 1, var4);
      } else {
         this.countRenderBuilders = integer;
      }

      for (int var6 = 0; var6 < var5; var6++) {
         ChunkRenderWorker var7 = new ChunkRenderWorker(this);
         threadFactory.newThread(var7).start();
         this.listThreadedWorkers.add(var7);
      }

      this.queueFreeRenderBuilders = Queues.newArrayBlockingQueue(this.countRenderBuilders);

      for (int var8 = 0; var8 < this.countRenderBuilders; var8++) {
         this.queueFreeRenderBuilders.add(new RegionRenderCacheBuilder());
      }

      this.renderWorker = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
   }

   public String getDebugInfo() {
      return String.format("pC: %03d, pU: %1d, aB: %1d", this.queueChunkUpdates.size(), this.queueChunkUploads.size(), this.queueFreeRenderBuilders.size());
   }

   public boolean runChunkUploads(long long1) {
      boolean var3 = false;

      long var9;
      do {
         boolean var4 = false;
         ListenableFutureTask var5;
         synchronized (this.queueChunkUploads) {
            var5 = this.queueChunkUploads.poll();
         }

         if (var5 != null) {
            var5.run();
            var4 = true;
            var3 = true;
         }

         if (long1 == 0L || !var4) {
            break;
         }

         var9 = long1 - System.nanoTime();
      } while (var9 >= 0L);

      return var3;
   }

   public boolean updateChunkLater(RenderChunk renderChunk) {
      renderChunk.getLockCompileTask().lock();

      boolean var2;
      try {
         ChunkCompileTaskGenerator var3 = renderChunk.makeCompileTaskChunk();
         var3.addFinishRunnable(() -> this.queueChunkUpdates.remove(var3));
         boolean var4 = this.queueChunkUpdates.offer(var3);
         if (!var4) {
            var3.finish();
         }

         var2 = var4;
      } finally {
         renderChunk.getLockCompileTask().unlock();
      }

      return var2;
   }

   public boolean updateChunkNow(RenderChunk renderChunk) {
      renderChunk.getLockCompileTask().lock();

      try {
         ChunkCompileTaskGenerator var3 = renderChunk.makeCompileTaskChunk();

         try {
            this.renderWorker.processTask(var3);
         } catch (InterruptedException var8) {
         }
      } finally {
         renderChunk.getLockCompileTask().unlock();
      }

      return true;
   }

   public void stopChunkUpdates() {
      this.clearChunkUpdates();

      while (this.runChunkUploads(0L)) {
      }

      ArrayList var1 = Lists.newArrayList();

      while (var1.size() != this.countRenderBuilders) {
         try {
            var1.add(this.allocateRenderBuilder());
         } catch (InterruptedException var2) {
         }
      }

      this.queueFreeRenderBuilders.addAll(var1);
   }

   public void freeRenderBuilder(RegionRenderCacheBuilder regionRenderCacheBuilder) {
      this.queueFreeRenderBuilders.add(regionRenderCacheBuilder);
   }

   public RegionRenderCacheBuilder allocateRenderBuilder() throws InterruptedException {
      return this.queueFreeRenderBuilders.take();
   }

   public ChunkCompileTaskGenerator getNextChunkUpdate() throws InterruptedException {
      return this.queueChunkUpdates.take();
   }

   // $VF: Removed empty exception range
   public boolean updateTransparencyLater(RenderChunk renderChunk) {
      renderChunk.getLockCompileTask().lock();

      try {
         ChunkCompileTaskGenerator var3 = renderChunk.makeCompileTaskTransparency();
         if (var3 != null) {
            var3.addFinishRunnable(() -> this.queueChunkUpdates.remove(var3));
            return this.queueChunkUpdates.offer(var3);
         }
      } finally {
         renderChunk.getLockCompileTask().unlock();
      }

      return true;
   }

   public ListenableFuture<Object> uploadChunk(
      EnumWorldBlockLayer enumWorldBlockLayer, WorldRenderer worldRenderer, RenderChunk renderChunk, CompiledChunk compiledChunk
   ) {
      if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
         if (OpenGlHelper.useVbo()) {
            this.uploadVertexBuffer(worldRenderer, renderChunk.getVertexBufferByLayer(enumWorldBlockLayer.ordinal()));
         } else {
            this.uploadDisplayList(worldRenderer, ((ListedRenderChunk)renderChunk).getDisplayList(enumWorldBlockLayer, compiledChunk), renderChunk);
         }

         worldRenderer.setTranslation(0.0, 0.0, 0.0);
         return Futures.immediateFuture(null);
      } else {
         ListenableFutureTask var5 = ListenableFutureTask.create(() -> this.uploadChunk(enumWorldBlockLayer, worldRenderer, renderChunk, compiledChunk), null);
         synchronized (this.queueChunkUploads) {
            this.queueChunkUploads.add(var5);
            return var5;
         }
      }
   }

   private void uploadDisplayList(WorldRenderer worldRenderer, int integer, RenderChunk renderChunk) {
      GL11.glNewList(integer, GL11.GL_COMPILE);
      GlStateManager.pushMatrix();
      renderChunk.multModelviewMatrix();
      this.worldVertexUploader.draw(worldRenderer);
      GlStateManager.popMatrix();
      GL11.glEndList();
   }

   private void uploadVertexBuffer(WorldRenderer worldRenderer, VertexBuffer vertexBuffer) {
      this.vertexUploader.setVertexBuffer(vertexBuffer);
      this.vertexUploader.draw(worldRenderer);
   }

   public void clearChunkUpdates() {
      while (!this.queueChunkUpdates.isEmpty()) {
         ChunkCompileTaskGenerator var1 = this.queueChunkUpdates.poll();
         if (var1 != null) {
            var1.finish();
         }
      }
   }

   public boolean hasChunkUpdates() {
      return this.queueChunkUpdates.isEmpty() && this.queueChunkUploads.isEmpty();
   }

   public void pauseChunkUpdates() {
      while (this.listPausedBuilders.size() != this.countRenderBuilders) {
         try {
            this.runChunkUploads(Long.MAX_VALUE);
            RegionRenderCacheBuilder var3 = this.queueFreeRenderBuilders.poll(100L, TimeUnit.MILLISECONDS);
            if (var3 != null) {
               this.listPausedBuilders.add(var3);
            }
         } catch (InterruptedException var4) {
         }
      }
   }

   public void resumeChunkUpdates() {
      this.queueFreeRenderBuilders.addAll(this.listPausedBuilders);
      this.listPausedBuilders.clear();
   }
}
