package net.minecraft.server;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.imageio.ImageIO;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Util;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer implements Runnable, ICommandSender, IThreadListener, IPlayerUsage {
   private static final Logger logger = LogManager.getLogger();
   public static final File USER_CACHE_FILE = new File("usercache.json");
   private static MinecraftServer mcServer;
   private final ISaveFormat anvilConverterForAnvilFile;
   private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
   private final File anvilFile;
   private final List<ITickable> playersOnline = Lists.newArrayList();
   protected final ICommandManager commandManager;
   public final Profiler theProfiler = new Profiler();
   private final NetworkSystem networkSystem;
   private final ServerStatusResponse statusResponse = new ServerStatusResponse();
   private final Random random = new Random();
   private final int m = -1;
   public WorldServer[] worldServers;
   private ServerConfigurationManager serverConfigManager;
   private boolean serverRunning = true;
   private boolean serverStopped;
   private int tickCounter;
   protected final Proxy serverProxy;
   public String currentTask;
   public int percentDone;
   private boolean onlineMode;
   private boolean canSpawnAnimals;
   private boolean canSpawnNPCs;
   private boolean pvpEnabled;
   private boolean allowFlight;
   private String motd;
   private int buildLimit;
   private int maxPlayerIdleMinutes = 0;
   public final long[] tickTimeArray = new long[100];
   public long[][] timeOfLastDimensionTick;
   private KeyPair serverKeyPair;
   private String serverOwner;
   private String folderName;
   private String worldName;
   private boolean isDemo;
   private boolean enableBonusChest;
   private boolean worldIsBeingDeleted;
   private String resourcePackUrl = "";
   private String resourcePackHash = "";
   private boolean serverIsRunning;
   private long timeOfLastWarning;
   private String userMessage;
   private boolean startProfiling;
   private boolean isGamemodeForced;
   private final YggdrasilAuthenticationService authService;
   private final MinecraftSessionService sessionService;
   private long nanoTimeSinceStatusRefresh = 0L;
   private final GameProfileRepository profileRepo;
   private final PlayerProfileCache profileCache;
   protected final Queue<FutureTask<?>> futureTaskQueue = Queues.newArrayDeque();
   private Thread serverThread;
   private long currentTime = getCurrentTimeMillis();

   public MinecraftServer(Proxy proxy, File file) {
      this.serverProxy = proxy;
      mcServer = this;
      this.anvilFile = null;
      this.networkSystem = null;
      this.profileCache = new PlayerProfileCache(this, file);
      this.commandManager = null;
      this.anvilConverterForAnvilFile = null;
      this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
      this.sessionService = this.authService.createMinecraftSessionService();
      this.profileRepo = this.authService.createProfileRepository();
   }

   public MinecraftServer(File file1, Proxy proxy, File file3) {
      this.serverProxy = proxy;
      mcServer = this;
      this.anvilFile = file1;
      this.networkSystem = new NetworkSystem(this);
      this.profileCache = new PlayerProfileCache(this, file3);
      this.commandManager = this.createNewCommandManager();
      this.anvilConverterForAnvilFile = new AnvilSaveConverter(file1);
      this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
      this.sessionService = this.authService.createMinecraftSessionService();
      this.profileRepo = this.authService.createProfileRepository();
   }

   protected ServerCommandManager createNewCommandManager() {
      return new ServerCommandManager();
   }

   protected abstract boolean startServer() throws IOException;

   protected void convertMapIfNeeded(String string) {
      if (this.getActiveAnvilConverter().isOldMapFormat(string)) {
         logger.info("Converting map!");
         this.setUserMessage("menu.convertingLevel");
         this.getActiveAnvilConverter().convertMapFormat(string, new IProgressUpdate() {
            private long startTime = System.currentTimeMillis();

            @Override
            public void displaySavingString(String string) {
            }

            @Override
            public void resetProgressAndMessage(String string) {
            }

            @Override
            public void setLoadingProgress(int integer) {
               if (System.currentTimeMillis() - this.startTime >= 1000L) {
                  this.startTime = System.currentTimeMillis();
                  MinecraftServer.logger.info("Converting... " + integer + "%");
               }
            }

            @Override
            public void setDoneWorking() {
            }

            @Override
            public void displayLoadingString(String string) {
            }
         });
      }
   }

   protected synchronized void setUserMessage(String string) {
      this.userMessage = string;
   }

   public synchronized String getUserMessage() {
      return this.userMessage;
   }

   protected void loadAllWorlds(String string1, String string2, long long3, WorldType worldType, String string5) {
      this.convertMapIfNeeded(string1);
      this.setUserMessage("menu.loadingLevel");
      this.worldServers = new WorldServer[3];
      this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
      ISaveHandler var10 = this.anvilConverterForAnvilFile.getSaveLoader(string1, true);
      this.setResourcePackFromWorld(this.getFolderName(), var10);
      WorldInfo var11 = var10.loadWorldInfo();
      WorldSettings var12;
      if (var11 == null) {
         if (this.isDemo()) {
            var12 = DemoWorldServer.demoWorldSettings;
         } else {
            var12 = new WorldSettings(long3, this.getGameType(), this.canStructuresSpawn(), this.isHardcore(), worldType);
            var12.setWorldName(string5);
            if (this.enableBonusChest) {
               var12.enableBonusChest();
            }
         }

         var11 = new WorldInfo(var12, string2);
      } else {
         var11.setWorldName(string2);
         var12 = new WorldSettings(var11);
      }

      for (int var13 = 0; var13 < this.worldServers.length; var13++) {
         byte var14 = 0;
         if (var13 == 1) {
            var14 = -1;
         }

         if (var13 == 2) {
            var14 = 1;
         }

         if (var13 == 0) {
            if (this.isDemo()) {
               this.worldServers[var13] = (WorldServer)new DemoWorldServer(this, var10, var11, var14, this.theProfiler).init();
            } else {
               this.worldServers[var13] = (WorldServer)new WorldServer(this, var10, var11, var14, this.theProfiler).init();
            }

            this.worldServers[var13].initialize(var12);
         } else {
            this.worldServers[var13] = (WorldServer)new WorldServerMulti(this, var10, var14, this.worldServers[0], this.theProfiler).init();
         }

         this.worldServers[var13].addWorldAccess(new WorldManager(this, this.worldServers[var13]));
         if (!this.isSinglePlayer()) {
            this.worldServers[var13].getWorldInfo().setGameType(this.getGameType());
         }
      }

      this.serverConfigManager.setPlayerManager(this.worldServers);
      this.setDifficultyForAllWorlds(this.getDifficulty());
      this.initialWorldChunkLoad();
   }

   protected void initialWorldChunkLoad() {
      int var8 = 0;
      this.setUserMessage("menu.generatingTerrain");
      logger.info("Preparing start region for level " + 0);
      WorldServer var10 = this.worldServers[0];
      BlockPos var11 = var10.getSpawnPoint();
      long var12 = getCurrentTimeMillis();

      for (short var14 = -192; var14 <= 192 && this.isServerRunning(); var14 += 16) {
         for (short var15 = -192; var15 <= 192 && this.isServerRunning(); var15 += 16) {
            long var16 = getCurrentTimeMillis();
            if (var16 - var12 > 1000L) {
               this.Method243("Preparing spawn area", var8 * 100 / 625);
               var12 = var16;
            }

            var8++;
            var10.theChunkProviderServer.loadChunk(var11.getX() + var14 >> 4, var11.getZ() + var15 >> 4);
         }
      }

      this.clearCurrentTask();
   }

   protected void setResourcePackFromWorld(String string, ISaveHandler iSaveHandler) {
      File var5 = new File(iSaveHandler.getWorldDirectory(), "resources.zip");
      if (var5.isFile()) {
         this.setResourcePack("level://" + string + "/" + var5.getName(), "");
      }
   }

   public abstract boolean canStructuresSpawn();

   public abstract WorldSettings.GameType getGameType();

   public abstract EnumDifficulty getDifficulty();

   public abstract boolean isHardcore();

   public abstract int getOpPermissionLevel();

   public abstract boolean shouldBroadcastRconToOps();

   public abstract boolean shouldBroadcastConsoleToOps();

   protected void Method243(String string, int integer) {
      this.currentTask = string;
      this.percentDone = integer;
      logger.info(string + ": " + integer + "%");
   }

   protected void clearCurrentTask() {
      this.currentTask = null;
      this.percentDone = 0;
   }

   protected void saveAllWorlds(boolean boolean1) {
      if (!this.worldIsBeingDeleted) {
         for (WorldServer var8 : this.worldServers) {
            if (var8 != null) {
               if (!boolean1) {
                  logger.info("Saving chunks for level '" + var8.getWorldInfo().getWorldName() + "'/" + var8.provider.getDimensionName());
               }

               try {
                  var8.saveAllChunks(true, null);
               } catch (MinecraftException var10) {
                  logger.warn(var10.getMessage());
               }
            }
         }
      }
   }

   public void stopServer() {
      if (!this.worldIsBeingDeleted) {
         logger.info("Stopping server");
         if (this.getNetworkSystem() != null) {
            this.getNetworkSystem().terminateEndpoints();
         }

         if (this.serverConfigManager != null) {
            logger.info("Saving players");
            this.serverConfigManager.saveAllPlayerData();
            this.serverConfigManager.removeAllPlayers();
         }

         if (this.worldServers != null) {
            logger.info("Saving worlds");
            this.saveAllWorlds(false);

            for (WorldServer var7 : this.worldServers) {
               var7.flush();
            }
         }

         if (this.usageSnooper.isSnooperRunning()) {
            this.usageSnooper.stopSnooper();
         }
      }
   }

   public boolean isServerRunning() {
      return this.serverRunning;
   }

   public void initiateShutdown() {
      this.serverRunning = false;
   }

   protected void setInstance() {
      mcServer = this;
   }

   @Override
   public void run() {
      try {
         if (this.startServer()) {
            this.currentTime = getCurrentTimeMillis();
            long var4 = 0L;
            this.statusResponse.setServerDescription(new ChatComponentText(this.motd));
            this.statusResponse.setProtocolVersionInfo(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.8.9", 47));
            this.addFaviconToStatusResponse(this.statusResponse);

            while (this.serverRunning) {
               long var51 = getCurrentTimeMillis();
               long var8 = var51 - this.currentTime;
               if (var8 > 2000L && this.currentTime - this.timeOfLastWarning >= 15000L) {
                  logger.warn(
                     "Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", var8, var8 / 50L
                  );
                  var8 = 2000L;
                  this.timeOfLastWarning = this.currentTime;
               }

               if (var8 < 0L) {
                  logger.warn("Time ran backwards! Did the system time change?");
                  var8 = 0L;
               }

               var4 += var8;
               this.currentTime = var51;
               if (this.worldServers[0].areAllPlayersAsleep()) {
                  this.tick();
                  var4 = 0L;
               } else {
                  while (var4 > 50L) {
                     var4 -= 50L;
                     this.tick();
                  }
               }

               Thread.sleep(Math.max(1L, 50L - var4));
               this.serverIsRunning = true;
            }
         } else {
            this.finalTick(null);
         }
      } catch (Throwable var49) {
         logger.error("Encountered an unexpected exception", var49);
         CrashReport var5;
         if (var49 instanceof ReportedException) {
            var5 = this.addServerInfoToCrashReport(((ReportedException)var49).getCrashReport());
         } else {
            var5 = this.addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", var49));
         }

         File var6 = new File(
            new File(this.getDataDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt"
         );
         if (var5.saveToFile(var6)) {
            logger.error("This crash report has been saved to: " + var6.getAbsolutePath());
         } else {
            logger.error("We were unable to save this crash report to disk.");
         }

         this.finalTick(var5);
      } finally {
         try {
            this.serverStopped = true;
            this.stopServer();
         } catch (Throwable var47) {
            logger.error("Exception stopping the server", var47);
         } finally {
            this.systemExitNow();
         }
      }
   }

   private void addFaviconToStatusResponse(ServerStatusResponse serverStatusResponse) {
      File var5 = this.getFile("server-icon.png");
      if (var5.isFile()) {
         ByteBuf var6 = Unpooled.buffer();

         try {
            BufferedImage var7 = ImageIO.read(var5);
            Validate.validState(var7.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
            Validate.validState(var7.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
            ImageIO.write(var7, "PNG", new ByteBufOutputStream(var6));
            ByteBuf var8 = Base64.encode(var6);
            serverStatusResponse.setFavicon("data:image/png;base64," + var8.toString(Charsets.UTF_8));
         } catch (Exception var12) {
            logger.error("Couldn't load server icon", var12);
         } finally {
            var6.release();
         }
      }
   }

   public File getDataDirectory() {
      return new File(".");
   }

   protected void finalTick(CrashReport crashReport) {
   }

   protected void systemExitNow() {
   }

   public void tick() {
      long var4 = System.nanoTime();
      this.tickCounter++;
      if (this.startProfiling) {
         this.startProfiling = false;
         this.theProfiler.profilingEnabled = true;
         this.theProfiler.clearProfiling();
      }

      this.theProfiler.startSection("root");
      this.updateTimeLightAndEntities();
      if (var4 - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
         this.nanoTimeSinceStatusRefresh = var4;
         this.statusResponse.setPlayerCountData(new ServerStatusResponse.PlayerCountData(this.getMaxPlayers(), this.getCurrentPlayerCount()));
         GameProfile[] var6 = new GameProfile[Math.min(this.getCurrentPlayerCount(), 12)];
         int var7 = MathHelper.getRandomIntegerInRange(this.random, 0, this.getCurrentPlayerCount() - var6.length);

         for (int var8 = 0; var8 < var6.length; var8++) {
            var6[var8] = this.serverConfigManager.getPlayerList().get(var7 + var8).getGameProfile();
         }

         Collections.shuffle(Arrays.asList(var6));
         this.statusResponse.getPlayerCountData().setPlayers(var6);
      }

      if (this.tickCounter % 900 == 0) {
         this.theProfiler.startSection("save");
         this.serverConfigManager.saveAllPlayerData();
         this.saveAllWorlds(true);
         this.theProfiler.endSection();
      }

      this.theProfiler.startSection("tallying");
      this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - var4;
      this.theProfiler.endSection();
      this.theProfiler.startSection("snooper");
      if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100) {
         this.usageSnooper.startSnooper();
      }

      if (this.tickCounter % 6000 == 0) {
         this.usageSnooper.addMemoryStatsToSnooper();
      }

      this.theProfiler.endSection();
      this.theProfiler.endSection();
   }

   public void updateTimeLightAndEntities() {
      this.theProfiler.startSection("jobs");
      synchronized (this.futureTaskQueue) {
         while (!this.futureTaskQueue.isEmpty()) {
            Util.runTask(this.futureTaskQueue.poll(), logger);
         }
      }

      this.theProfiler.endStartSection("levels");

      for (int var13 = 0; var13 < this.worldServers.length; var13++) {
         long var5 = System.nanoTime();
         if (var13 == 0 || this.getAllowNether()) {
            WorldServer var7 = this.worldServers[var13];
            this.theProfiler.startSection(var7.getWorldInfo().getWorldName());
            if (this.tickCounter % 20 == 0) {
               this.theProfiler.startSection("timeSync");
               this.serverConfigManager
                  .sendPacketToAllPlayersInDimension(
                     new S03PacketTimeUpdate(var7.getTotalWorldTime(), var7.getWorldTime(), var7.getGameRules().getBoolean("doDaylightCycle")),
                     var7.provider.getDimensionId()
                  );
               this.theProfiler.endSection();
            }

            this.theProfiler.startSection("tick");

            try {
               var7.tick();
            } catch (Throwable var11) {
               CrashReport var9 = CrashReport.makeCrashReport(var11, "Exception ticking world");
               var7.addWorldInfoToCrashReport(var9);
               throw new ReportedException(var9);
            }

            try {
               var7.updateEntities();
            } catch (Throwable var10) {
               CrashReport var16 = CrashReport.makeCrashReport(var10, "Exception ticking world entities");
               var7.addWorldInfoToCrashReport(var16);
               throw new ReportedException(var16);
            }

            this.theProfiler.endSection();
            this.theProfiler.startSection("tracker");
            var7.getEntityTracker().updateTrackedEntities();
            this.theProfiler.endSection();
            this.theProfiler.endSection();
         }

         this.timeOfLastDimensionTick[var13][this.tickCounter % 100] = System.nanoTime() - var5;
      }

      this.theProfiler.endStartSection("connection");
      this.getNetworkSystem().networkTick();
      this.theProfiler.endStartSection("players");
      this.serverConfigManager.onTick();
      this.theProfiler.endStartSection("tickables");

      for (ITickable var15 : this.playersOnline) {
         var15.update();
      }

      this.theProfiler.endSection();
   }

   public boolean getAllowNether() {
      return true;
   }

   public void startServerThread() {
      this.serverThread = new Thread(this, "Server thread");
      this.serverThread.start();
   }

   public File getFile(String string) {
      return new File(this.getDataDirectory(), string);
   }

   public void logWarning(String string) {
      logger.warn(string);
   }

   public WorldServer worldServerForDimension(int integer) {
      return integer == -1 ? this.worldServers[1] : (integer == 1 ? this.worldServers[2] : this.worldServers[0]);
   }

   public String getMinecraftVersion() {
      return "1.8.9";
   }

   public int getCurrentPlayerCount() {
      return this.serverConfigManager.getCurrentPlayerCount();
   }

   public int getMaxPlayers() {
      return this.serverConfigManager.getMaxPlayers();
   }

   public String[] getAllUsernames() {
      return this.serverConfigManager.getAllUsernames();
   }

   public GameProfile[] getGameProfiles() {
      return this.serverConfigManager.getAllProfiles();
   }

   public String getServerModName() {
      return "vanilla";
   }

   public CrashReport addServerInfoToCrashReport(CrashReport crashReport) {
      crashReport.getCategory()
         .addCrashSectionCallable("Profiler Position", () -> this.theProfiler.profilingEnabled ? this.theProfiler.getNameOfLastSection() : "N/A (disabled)");
      if (this.serverConfigManager != null) {
         crashReport.getCategory()
            .addCrashSectionCallable(
               "Player Count",
               () -> this.serverConfigManager.getCurrentPlayerCount()
                  + " / "
                  + this.serverConfigManager.getMaxPlayers()
                  + "; "
                  + this.serverConfigManager.getPlayerList()
            );
      }

      return crashReport;
   }

   public List<String> getTabCompletions(ICommandSender iCommandSender, String string, BlockPos blockPos) {
      List<String> var7 = Lists.newArrayList();
      if (string.startsWith("/")) {
         string = string.substring(1);
         boolean var8 = !string.contains(" ");
         List<String> var9 = this.commandManager.getTabCompletionOptions(iCommandSender, string, blockPos);
         if (var9 != null) {
            for (String var11 : var9) {
               if (var8) {
                  var7.add("/" + var11);
               } else {
                  var7.add(var11);
               }
            }
         }
      } else {
         String[] var15 = string.split(" ", -1);
         String var16 = var15[var15.length - 1];

         for (String var13 : this.serverConfigManager.getAllUsernames()) {
            if (CommandBase.doesStringStartWith(var16, var13)) {
               var7.add(var13);
            }
         }
      }

      return var7;
   }

   public static MinecraftServer getServer() {
      return mcServer;
   }

   public boolean isAnvilFileSet() {
      return this.anvilFile != null;
   }

   @Override
   public String getName() {
      return "Server";
   }

   @Override
   public void addChatMessage(IChatComponent iChatComponent) {
      logger.info(iChatComponent.getUnformattedText());
   }

   @Override
   public boolean canCommandSenderUseCommand(int integer, String string) {
      return true;
   }

   public ICommandManager getCommandManager() {
      return this.commandManager;
   }

   public KeyPair getKeyPair() {
      return this.serverKeyPair;
   }

   public String getServerOwner() {
      return this.serverOwner;
   }

   public void setServerOwner(String string) {
      this.serverOwner = string;
   }

   public boolean isSinglePlayer() {
      return this.serverOwner != null;
   }

   public String getFolderName() {
      return this.folderName;
   }

   public void setFolderName(String string) {
      this.folderName = string;
   }

   public void setWorldName(String string) {
      this.worldName = string;
   }

   public String getWorldName() {
      return this.worldName;
   }

   public void setKeyPair(KeyPair keyPair) {
      this.serverKeyPair = keyPair;
   }

   public void setDifficultyForAllWorlds(EnumDifficulty enumDifficulty) {
      for (WorldServer var8 : this.worldServers) {
         if (var8 != null) {
            if (var8.getWorldInfo().isHardcoreModeEnabled()) {
               var8.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
               var8.setAllowedSpawnTypes(true, true);
            } else if (this.isSinglePlayer()) {
               var8.getWorldInfo().setDifficulty(enumDifficulty);
               var8.setAllowedSpawnTypes(var8.getDifficulty() != EnumDifficulty.PEACEFUL, true);
            } else {
               var8.getWorldInfo().setDifficulty(enumDifficulty);
               var8.setAllowedSpawnTypes(this.allowSpawnMonsters(), this.canSpawnAnimals);
            }
         }
      }
   }

   protected boolean allowSpawnMonsters() {
      return true;
   }

   public boolean isDemo() {
      return this.isDemo;
   }

   public void setDemo(boolean boolean1) {
      this.isDemo = boolean1;
   }

   public void canCreateBonusChest(boolean boolean1) {
      this.enableBonusChest = boolean1;
   }

   public ISaveFormat getActiveAnvilConverter() {
      return this.anvilConverterForAnvilFile;
   }

   public void deleteWorldAndStopServer() {
      this.worldIsBeingDeleted = true;
      this.getActiveAnvilConverter().flushCache();

      for (WorldServer var7 : this.worldServers) {
         if (var7 != null) {
            var7.flush();
         }
      }

      this.getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
      this.initiateShutdown();
   }

   public String getResourcePackUrl() {
      return this.resourcePackUrl;
   }

   public String getResourcePackHash() {
      return this.resourcePackHash;
   }

   public void setResourcePack(String string1, String string2) {
      this.resourcePackUrl = string1;
      this.resourcePackHash = string2;
   }

   @Override
   public void addServerStatsToSnooper(PlayerUsageSnooper playerUsageSnooper) {
      playerUsageSnooper.addClientStat("whitelist_enabled", Boolean.FALSE);
      playerUsageSnooper.addClientStat("whitelist_count", 0);
      if (this.serverConfigManager != null) {
         playerUsageSnooper.addClientStat("players_current", this.getCurrentPlayerCount());
         playerUsageSnooper.addClientStat("players_max", this.getMaxPlayers());
         playerUsageSnooper.addClientStat("players_seen", this.serverConfigManager.getAvailablePlayerDat().length);
      }

      playerUsageSnooper.addClientStat("uses_auth", this.onlineMode);
      playerUsageSnooper.addClientStat("gui_state", this.getGuiEnabled() ? "enabled" : "disabled");
      playerUsageSnooper.addClientStat("run_time", (getCurrentTimeMillis() - playerUsageSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
      playerUsageSnooper.addClientStat("avg_tick_ms", (int)(MathHelper.average(this.tickTimeArray) * 1.0E-6));
      int var5 = 0;
      if (this.worldServers != null) {
         for (WorldServer var9 : this.worldServers) {
            if (var9 != null) {
               WorldInfo var10 = var9.getWorldInfo();
               playerUsageSnooper.addClientStat("world[" + var5 + "][dimension]", var9.provider.getDimensionId());
               playerUsageSnooper.addClientStat("world[" + var5 + "][mode]", var10.getGameType());
               playerUsageSnooper.addClientStat("world[" + var5 + "][difficulty]", var9.getDifficulty());
               playerUsageSnooper.addClientStat("world[" + var5 + "][hardcore]", var10.isHardcoreModeEnabled());
               playerUsageSnooper.addClientStat("world[" + var5 + "][generator_name]", var10.getTerrainType().getWorldTypeName());
               playerUsageSnooper.addClientStat("world[" + var5 + "][generator_version]", var10.getTerrainType().getGeneratorVersion());
               playerUsageSnooper.addClientStat("world[" + var5 + "][height]", this.buildLimit);
               playerUsageSnooper.addClientStat("world[" + var5 + "][chunks_loaded]", var9.getChunkProvider().getLoadedChunkCount());
               var5++;
            }
         }
      }

      playerUsageSnooper.addClientStat("worlds", var5);
   }

   @Override
   public void addServerTypeToSnooper(PlayerUsageSnooper playerUsageSnooper) {
      playerUsageSnooper.addStatToSnooper("singleplayer", this.isSinglePlayer());
      playerUsageSnooper.addStatToSnooper("server_brand", this.getServerModName());
      playerUsageSnooper.addStatToSnooper("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
      playerUsageSnooper.addStatToSnooper("dedicated", this.isDedicatedServer());
   }

   @Override
   public boolean isSnooperEnabled() {
      return true;
   }

   public abstract boolean isDedicatedServer();

   public boolean isServerInOnlineMode() {
      return this.onlineMode;
   }

   public void setOnlineMode(boolean boolean1) {
      this.onlineMode = boolean1;
   }

   public boolean getCanSpawnAnimals() {
      return this.canSpawnAnimals;
   }

   public void setCanSpawnAnimals(boolean boolean1) {
      this.canSpawnAnimals = boolean1;
   }

   public boolean getCanSpawnNPCs() {
      return this.canSpawnNPCs;
   }

   public abstract boolean shouldUseNativeTransport();

   public void setCanSpawnNPCs(boolean boolean1) {
      this.canSpawnNPCs = boolean1;
   }

   public boolean isPVPEnabled() {
      return this.pvpEnabled;
   }

   public void setAllowPvp(boolean boolean1) {
      this.pvpEnabled = boolean1;
   }

   public boolean isFlightAllowed() {
      return this.allowFlight;
   }

   public void setAllowFlight(boolean boolean1) {
      this.allowFlight = boolean1;
   }

   public abstract boolean isCommandBlockEnabled();

   public String getMOTD() {
      return this.motd;
   }

   public void setMOTD(String string) {
      this.motd = string;
   }

   public int getBuildLimit() {
      return this.buildLimit;
   }

   public void setBuildLimit(int integer) {
      this.buildLimit = integer;
   }

   public boolean isServerStopped() {
      return this.serverStopped;
   }

   public ServerConfigurationManager getConfigurationManager() {
      return this.serverConfigManager;
   }

   public void setConfigManager(ServerConfigurationManager serverConfigurationManager) {
      this.serverConfigManager = serverConfigurationManager;
   }

   public void setGameType(WorldSettings.GameType gameType) {
      for (int var5 = 0; var5 < this.worldServers.length; var5++) {
         getServer().worldServers[var5].getWorldInfo().setGameType(gameType);
      }
   }

   public NetworkSystem getNetworkSystem() {
      return this.networkSystem;
   }

   public boolean serverIsInRunLoop() {
      return this.serverIsRunning;
   }

   public boolean getGuiEnabled() {
      return false;
   }

   public abstract String shareToLAN(WorldSettings.GameType gameType, boolean boolean2);

   public int getTickCounter() {
      return this.tickCounter;
   }

   public void enableProfiling() {
      this.startProfiling = true;
   }

   public PlayerUsageSnooper getPlayerUsageSnooper() {
      return this.usageSnooper;
   }

   @Override
   public BlockPos getPosition() {
      return BlockPos.ORIGIN;
   }

   @Override
   public Vec3 getPositionVector() {
      return new Vec3(0.0, 0.0, 0.0);
   }

   @Override
   public World getEntityWorld() {
      return this.worldServers[0];
   }

   @Override
   public Entity getCommandSenderEntity() {
      return null;
   }

   public int getSpawnProtectionSize() {
      return 16;
   }

   public boolean isBlockProtected(World world, BlockPos blockPos, EntityPlayer entityPlayer) {
      return false;
   }

   public boolean getForceGamemode() {
      return this.isGamemodeForced;
   }

   public Proxy getServerProxy() {
      return this.serverProxy;
   }

   public static long getCurrentTimeMillis() {
      return System.currentTimeMillis();
   }

   public int getMaxPlayerIdleMinutes() {
      return this.maxPlayerIdleMinutes;
   }

   public void setPlayerIdleTimeout(int integer) {
      this.maxPlayerIdleMinutes = integer;
   }

   @Override
   public IChatComponent getDisplayName() {
      return new ChatComponentText(this.getName());
   }

   public boolean isAnnouncingPlayerAchievements() {
      return true;
   }

   public MinecraftSessionService getMinecraftSessionService() {
      return this.sessionService;
   }

   public GameProfileRepository getGameProfileRepository() {
      return this.profileRepo;
   }

   public PlayerProfileCache getPlayerProfileCache() {
      return this.profileCache;
   }

   public ServerStatusResponse getServerStatusResponse() {
      return this.statusResponse;
   }

   public void refreshStatusNextTick() {
      this.nanoTimeSinceStatusRefresh = 0L;
   }

   public Entity getEntityFromUuid(UUID uUID) {
      for (WorldServer var8 : this.worldServers) {
         if (var8 != null) {
            Entity var9 = var8.getEntityFromUuid(uUID);
            if (var9 != null) {
               return var9;
            }
         }
      }

      return null;
   }

   @Override
   public boolean sendCommandFeedback() {
      return getServer().worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
   }

   @Override
   public void setCommandStat(CommandResultStats.Type type, int integer) {
   }

   public int getMaxWorldSize() {
      return 29999984;
   }

   public <V> ListenableFuture<V> callFromMainThread(Callable<V> callable) {
      Validate.notNull(callable);
      if (!this.isCallingFromMinecraftThread() && !this.isServerStopped()) {
         ListenableFutureTask var5 = ListenableFutureTask.create(callable);
         synchronized (this.futureTaskQueue) {
            this.futureTaskQueue.add(var5);
            return var5;
         }
      } else {
         try {
            return Futures.immediateFuture(callable.call());
         } catch (Exception var9) {
            return Futures.immediateFailedFuture(var9);
         }
      }
   }

   @Override
   public ListenableFuture<Object> addScheduledTask(Runnable runnable) {
      Validate.notNull(runnable);
      return this.callFromMainThread(Executors.callable(runnable));
   }

   @Override
   public boolean isCallingFromMinecraftThread() {
      return Thread.currentThread() == this.serverThread;
   }

   public int getNetworkCompressionTreshold() {
      return 256;
   }
}
