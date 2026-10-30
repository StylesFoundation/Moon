package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;

public class ThreadedFileIOBase implements Runnable {
   private static final ThreadedFileIOBase threadedIOInstance = new ThreadedFileIOBase();
   private final List<IThreadedFileIO> threadedIOQueue = Collections.synchronizedList(Lists.newArrayList());
   private volatile long writeQueuedCounter;
   private volatile long savedIOCounter;
   private volatile boolean isThreadWaiting;

   private ThreadedFileIOBase() {
      Thread var1 = new Thread(this, "File IO Thread");
      var1.setPriority(1);
      var1.start();
   }

   public static ThreadedFileIOBase getThreadedIOInstance() {
      return threadedIOInstance;
   }

   @Override
   public void run() {
      while (true) {
         this.processQueue();
      }
   }

   private void processQueue() {
      for (int var4 = 0; var4 < this.threadedIOQueue.size(); var4++) {
         boolean var6 = this.threadedIOQueue.get(var4).writeNextIO();
         if (!var6) {
            this.threadedIOQueue.remove(var4--);
            this.savedIOCounter++;
         }

         try {
            Thread.sleep(this.isThreadWaiting ? 0L : 10L);
         } catch (InterruptedException var8) {
            var8.printStackTrace();
         }
      }

      if (this.threadedIOQueue.isEmpty()) {
         try {
            Thread.sleep(25L);
         } catch (InterruptedException var7) {
            var7.printStackTrace();
         }
      }
   }

   public void queueIO(IThreadedFileIO iThreadedFileIO) {
      if (!this.threadedIOQueue.contains(iThreadedFileIO)) {
         this.writeQueuedCounter++;
         this.threadedIOQueue.add(iThreadedFileIO);
      }
   }

   public void waitForFinish() throws InterruptedException {
      this.isThreadWaiting = true;

      while (this.writeQueuedCounter != this.savedIOCounter) {
         Thread.sleep(10L);
      }

      this.isThreadWaiting = false;
   }
}
