package com.moon.viamcp;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.moon.viamcp.loader.MCPBackwardsLoader;
import com.moon.viamcp.loader.MCPRewindLoader;
import com.moon.viamcp.loader.MCPViaLoader;
import com.moon.viamcp.platform.MCPViaInjector;
import com.moon.viamcp.platform.MCPViaPlatform;
import com.moon.viamcp.utils.JLoggerToLog4j;
import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import io.netty.channel.EventLoop;
import io.netty.channel.local.LocalEventLoopGroup;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

public class ViaMCP {
   private static ViaMCP INSTANCE = new ViaMCP();
   private final int PROTOCOL_VERSION = 47;
   private final Logger jLogger = new JLoggerToLog4j(LogManager.getLogger("MoonProtocol"));
   private final CompletableFuture<Void> INIT_FUTURE = new CompletableFuture<>();
   private ExecutorService ASYNC_EXEC;
   private EventLoop EVENT_LOOP;
   private File file;
   private int version;

   public void start(File file) {
      ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Moon-Protocol-(%d)").build();
      this.ASYNC_EXEC = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), threadFactory);
      this.EVENT_LOOP = new LocalEventLoopGroup(1, threadFactory).next();
      this.EVENT_LOOP.submit(this.INIT_FUTURE::join);
      this.setVersion(47);
      this.file = new File(file, "Moon 4.0/protocol");
      if (!this.file.exists()) {
         this.file.mkdirs();
      }

      Via.init(ViaManagerImpl.builder().injector(new MCPViaInjector()).loader(new MCPViaLoader()).platform(new MCPViaPlatform(this.file)).build());
      MappingDataLoader.enableMappingsCache();
      ((ViaManagerImpl)Via.getManager()).init();
      new MCPBackwardsLoader(this.file);
      new MCPRewindLoader(this.file);
      this.INIT_FUTURE.complete(null);
   }

   public int getProtocolVersion() {
      return 47;
   }

   public Logger getjLogger() {
      return this.jLogger;
   }

   public CompletableFuture<Void> getInitFuture() {
      return this.INIT_FUTURE;
   }

   public ExecutorService getAsyncExecutor() {
      return this.ASYNC_EXEC;
   }

   public EventLoop getEventLoop() {
      return this.EVENT_LOOP;
   }

   public File getFile() {
      return this.file;
   }

   public int getVersion() {
      return this.version;
   }

   public void setAsyncExecutor(ExecutorService executorService) {
      this.ASYNC_EXEC = executorService;
   }

   public void setEventLoop(EventLoop eventLoop) {
      this.EVENT_LOOP = eventLoop;
   }

   public void setFile(File file) {
      this.file = file;
   }

   public void setVersion(int version) {
      this.version = version;
   }

   public static ViaMCP getInstance() {
      return INSTANCE;
   }
}
