package com.moon.viamcp.platform;

import com.moon.viamcp.ViaMCP;
import com.moon.viamcp.utils.FutureTaskId;
import com.moon.viamcp.utils.JLoggerToLog4j;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.libs.gson.JsonObject;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

public class MCPViaPlatform implements ViaPlatform<UUID> {
   private final Logger logger = new JLoggerToLog4j(LogManager.getLogger("ViaVersion"));
   private MCPViaConfig config;
   private final File file;
   private final MCPViaAPI api;

   public MCPViaPlatform(File file) {
      Path viaVersionPath = file.toPath().resolve("ViaVersion");
      this.config = new MCPViaConfig(viaVersionPath.resolve("viaversion.yml").toFile());
      this.file = viaVersionPath.toFile();
      this.api = new MCPViaAPI();
   }

   @Override
   public Logger getLogger() {
      return this.logger;
   }

   @Override
   public String getPlatformName() {
      return "MoonProtocol";
   }

   @Override
   public String getPlatformVersion() {
      return String.valueOf(ViaMCP.getInstance().getProtocolVersion());
   }

   @Override
   public boolean isProxy() {
      return ViaPlatform.super.isProxy();
   }

   @Override
   public String getPluginVersion() {
      return "4.1.1";
   }

   public FutureTaskId runAsync(Runnable runnable) {
      return new FutureTaskId(CompletableFuture.runAsync(runnable, ViaMCP.getInstance().getAsyncExecutor()).exceptionally(throwable -> {
         if (!(throwable instanceof CancellationException)) {
            throwable.printStackTrace();
         }

         return null;
      }));
   }

   public FutureTaskId runSync(Runnable runnable) {
      return new FutureTaskId(ViaMCP.getInstance().getEventLoop().submit(runnable).addListener(this.Method320()));
   }

   @Override
   public PlatformTask runSync(Runnable runnable, long ticks) {
      return new FutureTaskId(
         ViaMCP.getInstance().getEventLoop().schedule(runnable, ticks * 50L, TimeUnit.MILLISECONDS).addListener(this.Method320())
      );
   }

   @Override
   public PlatformTask runRepeatingSync(Runnable runnable, long ticks) {
      return new FutureTaskId(
         ViaMCP.getInstance().getEventLoop().scheduleAtFixedRate(runnable, 0L, ticks * 50L, TimeUnit.MILLISECONDS).addListener(this.Method320())
      );
   }

   private <T extends Future<?>> GenericFutureListener<T> Method320() {
      return future -> {
         if (!future.isCancelled() && future.cause() != null) {
            future.cause().printStackTrace();
         }
      };
   }

   @Override
   public ViaCommandSender[] getOnlinePlayers() {
      return new ViaCommandSender[10000];
   }

   @Override
   public void sendMessage(UUID uuid, String message) {
   }

   @Override
   public boolean kickPlayer(UUID uuid, String message) {
      return false;
   }

   @Override
   public boolean disconnect(UserConnection connection, String message) {
      return ViaPlatform.super.disconnect(connection, message);
   }

   @Override
   public boolean isPluginEnabled() {
      return true;
   }

   @Override
   public ViaAPI<UUID> getApi() {
      return this.api;
   }

   @Override
   public ViaVersionConfig getConf() {
      return this.config;
   }

   @Override
   public ConfigurationProvider getConfigurationProvider() {
      return this.config;
   }

   @Override
   public File getDataFolder() {
      return this.file;
   }

   @Override
   public void onReload() {
   }

   @Override
   public JsonObject getDump() {
      return new JsonObject();
   }

   @Override
   public boolean isOldClientsAllowed() {
      return true;
   }

   @Override
   public Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
      return ViaPlatform.super.getUnsupportedSoftwareClasses();
   }

   @Override
   public boolean hasPlugin(String name) {
      return false;
   }
}
