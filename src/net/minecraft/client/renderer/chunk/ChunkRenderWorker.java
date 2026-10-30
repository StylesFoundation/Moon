package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumWorldBlockLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRenderWorker implements Runnable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ChunkRenderDispatcher chunkRenderDispatcher;
   private final RegionRenderCacheBuilder regionRenderCacheBuilder;

   public ChunkRenderWorker(ChunkRenderDispatcher chunkRenderDispatcher) {
      this(chunkRenderDispatcher, null);
   }

   public ChunkRenderWorker(ChunkRenderDispatcher chunkRenderDispatcher, RegionRenderCacheBuilder regionRenderCacheBuilder) {
      this.chunkRenderDispatcher = chunkRenderDispatcher;
      this.regionRenderCacheBuilder = regionRenderCacheBuilder;
   }

   @Override
   public void run() {
      while (true) {
         try {
            this.processTask(this.chunkRenderDispatcher.getNextChunkUpdate());
         } catch (InterruptedException var2) {
            LOGGER.debug("Stopping due to interrupt");
            return;
         } catch (Throwable var3) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
               CrashReport var1 = CrashReport.makeCrashReport(var3, "Batching chunks");
               Minecraft.getMinecraft().crashed(Minecraft.getMinecraft().addGraphicsAndWorldToCrashReport(var1));
            });
            return;
         }
      }
   }

   protected void processTask(ChunkCompileTaskGenerator chunkCompileTaskGenerator) throws InterruptedException {
      chunkCompileTaskGenerator.getLock().lock();

      try {
         if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.PENDING) {
            if (!chunkCompileTaskGenerator.isFinished()) {
               LOGGER.warn("Chunk render task was " + chunkCompileTaskGenerator.getStatus() + " when I expected it to be pending; ignoring task");
            }

            return;
         }

         chunkCompileTaskGenerator.setStatus(ChunkCompileTaskGenerator.Status.COMPILING);
      } finally {
         chunkCompileTaskGenerator.getLock().unlock();
      }

      Entity var2 = Minecraft.getMinecraft().getRenderViewEntity();
      if (var2 == null) {
         chunkCompileTaskGenerator.finish();
      } else {
         chunkCompileTaskGenerator.setRegionRenderCacheBuilder(this.getRegionRenderCacheBuilder());
         float var3 = (float)var2.posX;
         float var4 = (float)var2.posY + var2.getEyeHeight();
         float var5 = (float)var2.posZ;
         ChunkCompileTaskGenerator.Type var6 = chunkCompileTaskGenerator.Method883();
         if (var6 == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
            chunkCompileTaskGenerator.Method882().rebuildChunk(var3, var4, var5, chunkCompileTaskGenerator);
         } else if (var6 == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
            chunkCompileTaskGenerator.Method882().resortTransparency(var3, var4, var5, chunkCompileTaskGenerator);
         }

         chunkCompileTaskGenerator.getLock().lock();

         try {
            if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
               if (!chunkCompileTaskGenerator.isFinished()) {
                  LOGGER.warn("Chunk render task was " + chunkCompileTaskGenerator.getStatus() + " when I expected it to be compiling; aborting task");
               }

               this.freeRenderBuilder(chunkCompileTaskGenerator);
               return;
            }

            chunkCompileTaskGenerator.setStatus(ChunkCompileTaskGenerator.Status.UPLOADING);
         } finally {
            chunkCompileTaskGenerator.getLock().unlock();
         }

         final CompiledChunk var7 = chunkCompileTaskGenerator.getCompiledChunk();
         ArrayList var8 = Lists.newArrayList();
         if (var6 == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
            for (EnumWorldBlockLayer var12 : EnumWorldBlockLayer.values()) {
               if (var7.isLayerStarted(var12)) {
                  var8.add(
                     this.chunkRenderDispatcher
                        .uploadChunk(
                           var12,
                           chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(var12),
                           chunkCompileTaskGenerator.Method882(),
                           var7
                        )
                  );
               }
            }
         } else if (var6 == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
            var8.add(
               this.chunkRenderDispatcher
                  .uploadChunk(
                     EnumWorldBlockLayer.TRANSLUCENT,
                     chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT),
                     chunkCompileTaskGenerator.Method882(),
                     var7
                  )
            );
         }

         ListenableFuture var19 = Futures.allAsList(var8);
         chunkCompileTaskGenerator.addFinishRunnable(() -> var19.cancel(false));
         Futures.addCallback(
            var19,
            new FutureCallback<List<Object>>() {
               public void onSuccess(List<Object> list) {
                  ChunkRenderWorker.this.freeRenderBuilder(chunkCompileTaskGenerator);
                  chunkCompileTaskGenerator.getLock().lock();

                  try {
                     if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.UPLOADING) {
                        if (!chunkCompileTaskGenerator.isFinished()) {
                           ChunkRenderWorker.LOGGER
                              .warn("Chunk render task was " + chunkCompileTaskGenerator.getStatus() + " when I expected it to be uploading; aborting task");
                        }

                        return;
                     }

                     chunkCompileTaskGenerator.setStatus(ChunkCompileTaskGenerator.Status.DONE);
                  } finally {
                     chunkCompileTaskGenerator.getLock().unlock();
                  }

                  chunkCompileTaskGenerator.Method882().setCompiledChunk(var7);
               }

               @Override
               public void onFailure(Throwable throwable) {
                  ChunkRenderWorker.this.freeRenderBuilder(chunkCompileTaskGenerator);
                  if (!(throwable instanceof CancellationException) && !(throwable instanceof InterruptedException)) {
                     Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(throwable, "Rendering chunk"));
                  }
               }
            },
            MoreExecutors.directExecutor()
         );
      }
   }

   private RegionRenderCacheBuilder getRegionRenderCacheBuilder() throws InterruptedException {
      return this.regionRenderCacheBuilder != null ? this.regionRenderCacheBuilder : this.chunkRenderDispatcher.allocateRenderBuilder();
   }

   private void freeRenderBuilder(ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
      if (this.regionRenderCacheBuilder == null) {
         this.chunkRenderDispatcher.freeRenderBuilder(chunkCompileTaskGenerator.getRegionRenderCacheBuilder());
      }
   }
}
