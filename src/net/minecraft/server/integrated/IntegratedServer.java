package net.minecraft.server.integrated;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.moon.Moon;
import com.moon.events.impl.network.ConnectServerEvent;
import java.io.File;
import java.io.IOException;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.ClearWater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer extends MinecraftServer {
   private static final Logger logger = LogManager.getLogger();
   private final Minecraft mc;
   private final WorldSettings theWorldSettings;
   private boolean isGamePaused;
   private boolean isPublic;
   private ThreadLanServerPing lanServerPing;
   private long ticksSaveLast = 0L;
   public World difficultyUpdateWorld = null;
   public BlockPos difficultyUpdatePos = null;
   public DifficultyInstance difficultyLast = null;

   public IntegratedServer(Minecraft minecraft) {
      super(minecraft.getProxy(), new File(minecraft.mcDataDir, USER_CACHE_FILE.getName()));
      this.mc = minecraft;
      this.theWorldSettings = null;
   }

   public IntegratedServer(Minecraft minecraft, String string2, String string3, WorldSettings worldSettings) {
      super(new File(minecraft.mcDataDir, "saves"), minecraft.getProxy(), new File(minecraft.mcDataDir, USER_CACHE_FILE.getName()));
      this.setServerOwner(minecraft.getSession().getUsername());
      this.setFolderName(string2);
      this.setWorldName(string3);
      this.setDemo(minecraft.isDemo());
      this.canCreateBonusChest(worldSettings.isBonusChestEnabled());
      this.setBuildLimit(256);
      this.setConfigManager(new IntegratedPlayerList(this));
      this.mc = minecraft;
      this.theWorldSettings = this.isDemo() ? DemoWorldServer.demoWorldSettings : worldSettings;
      WorldInfo var9 = this.getActiveAnvilConverter().getSaveLoader(string2, false).loadWorldInfo();
      if (var9 != null) {
         NBTTagCompound var10 = var9.getPlayerNBTTagCompound();
         if (var10 != null && var10.hasKey("Dimension")) {
            PacketThreadUtil.lastDimensionId = var10.getInteger("Dimension");
            this.mc.loadingScreen.setLoadingProgress(-1);
         }
      }
   }

   @Override
   protected ServerCommandManager createNewCommandManager() {
      return new IntegratedServerCommandManager();
   }

   @Override
   protected void loadAllWorlds(String string1, String string2, long long3, WorldType worldType, String string5) {
      this.convertMapIfNeeded(string1);
      this.worldServers = new WorldServer[3];
      this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
      ISaveHandler var10 = this.getActiveAnvilConverter().getSaveLoader(string1, true);
      this.setResourcePackFromWorld(this.getFolderName(), var10);
      WorldInfo var11 = var10.loadWorldInfo();
      if (var11 == null) {
         var11 = new WorldInfo(this.theWorldSettings, string2);
      } else {
         var11.setWorldName(string2);
      }

      for (int var12 = 0; var12 < this.worldServers.length; var12++) {
         byte var13 = 0;
         if (var12 == 1) {
            var13 = -1;
         }

         if (var12 == 2) {
            var13 = 1;
         }

         if (var12 == 0) {
            if (this.isDemo()) {
               this.worldServers[var12] = (WorldServer)new DemoWorldServer(this, var10, var11, var13, this.theProfiler).init();
            } else {
               this.worldServers[var12] = (WorldServer)new WorldServer(this, var10, var11, var13, this.theProfiler).init();
            }

            this.worldServers[var12].initialize(this.theWorldSettings);
         } else {
            this.worldServers[var12] = (WorldServer)new WorldServerMulti(this, var10, var13, this.worldServers[0], this.theProfiler).init();
         }

         this.worldServers[var12].addWorldAccess(new WorldManager(this, this.worldServers[var12]));
      }

      this.getConfigurationManager().setPlayerManager(this.worldServers);
      if (this.worldServers[0].getWorldInfo().getDifficulty() == null) {
         this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
      }

      this.initialWorldChunkLoad();
   }

   @Override
   protected boolean startServer() throws IOException {
      Moon.getInstance().getEventBus().publish(new ConnectServerEvent("Singleplayer", true));
      logger.info("Starting integrated minecraft server version 1.9");
      this.setOnlineMode(true);
      this.setCanSpawnAnimals(true);
      this.setCanSpawnNPCs(true);
      this.setAllowPvp(true);
      this.setAllowFlight(true);
      logger.info("Generating keypair");
      this.setKeyPair(CryptManager.generateKeyPair());
      this.loadAllWorlds(
         this.getFolderName(),
         this.getWorldName(),
         this.theWorldSettings.getSeed(),
         this.theWorldSettings.getTerrainType(),
         this.theWorldSettings.getWorldName()
      );
      this.setMOTD(this.getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
      return true;
   }

   @Override
   public void tick() {
      this.onTick();
      boolean var4 = this.isGamePaused;
      this.isGamePaused = Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused();
      if (!var4 && this.isGamePaused) {
         logger.info("Saving and pausing game...");
         this.getConfigurationManager().saveAllPlayerData();
         this.saveAllWorlds(false);
      }

      if (this.isGamePaused) {
         synchronized (this.futureTaskQueue) {
            while (!this.futureTaskQueue.isEmpty()) {
               Util.runTask(this.futureTaskQueue.poll(), logger);
            }
         }
      } else {
         super.tick();
         if (this.mc.gameSettings.renderDistanceChunks != this.getConfigurationManager().getViewDistance()) {
            logger.info("Changing view distance to {}, from {}", this.mc.gameSettings.renderDistanceChunks, this.getConfigurationManager().getViewDistance());
            this.getConfigurationManager().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
         }

         if (this.mc.theWorld != null) {
            WorldInfo var12 = this.worldServers[0].getWorldInfo();
            WorldInfo var6 = this.mc.theWorld.getWorldInfo();
            if (!var12.isDifficultyLocked() && var6.getDifficulty() != var12.getDifficulty()) {
               logger.info("Changing difficulty to {}, from {}", var6.getDifficulty(), var12.getDifficulty());
               this.setDifficultyForAllWorlds(var6.getDifficulty());
            } else if (var6.isDifficultyLocked() && !var12.isDifficultyLocked()) {
               logger.info("Locking difficulty to {}", new Object[]{var6.getDifficulty()});

               for (WorldServer var10 : this.worldServers) {
                  if (var10 != null) {
                     var10.getWorldInfo().setDifficultyLocked(true);
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean canStructuresSpawn() {
      return false;
   }

   @Override
   public WorldSettings.GameType getGameType() {
      return this.theWorldSettings.getGameType();
   }

   @Override
   public EnumDifficulty getDifficulty() {
      return this.mc.theWorld == null ? this.mc.gameSettings.difficulty : this.mc.theWorld.getWorldInfo().getDifficulty();
   }

   @Override
   public boolean isHardcore() {
      return this.theWorldSettings.getHardcoreEnabled();
   }

   @Override
   public boolean shouldBroadcastRconToOps() {
      return true;
   }

   @Override
   public boolean shouldBroadcastConsoleToOps() {
      return true;
   }

   @Override
   public void saveAllWorlds(boolean boolean1) {
      if (boolean1) {
         int var5 = this.getTickCounter();
         int var6 = this.mc.gameSettings.ofAutoSaveTicks;
         if (var5 < this.ticksSaveLast + var6) {
            return;
         }

         this.ticksSaveLast = var5;
      }

      super.saveAllWorlds(boolean1);
   }

   @Override
   public File getDataDirectory() {
      return this.mc.mcDataDir;
   }

   @Override
   public boolean isDedicatedServer() {
      return false;
   }

   @Override
   public boolean shouldUseNativeTransport() {
      return false;
   }

   @Override
   protected void finalTick(CrashReport crashReport) {
      this.mc.crashed(crashReport);
   }

   @Override
   public CrashReport addServerInfoToCrashReport(CrashReport crashReport) {
      crashReport = super.addServerInfoToCrashReport(crashReport);
      crashReport.getCategory().addCrashSectionCallable("Type", () -> "Integrated Server (map_client.txt)");
      crashReport.getCategory()
         .addCrashSectionCallable(
            "Is Modded",
            () -> {
               String var4 = ClientBrandRetriever.getClientModName();
               if (!var4.equals("vanilla")) {
                  return "Definitely; Client brand changed to '" + var4 + "'";
               }

               var4 = this.getServerModName();
               return !var4.equals("vanilla")
                  ? "Definitely; Server brand changed to '" + var4 + "'"
                  : (
                     Minecraft.class.getSigners() == null
                        ? "Very likely; Jar signature invalidated"
                        : "Probably not. Jar signature remains and both client + server brands are untouched."
                  );
            }
         );
      return crashReport;
   }

   @Override
   public void setDifficultyForAllWorlds(EnumDifficulty enumDifficulty) {
      super.setDifficultyForAllWorlds(enumDifficulty);
      if (this.mc.theWorld != null) {
         this.mc.theWorld.getWorldInfo().setDifficulty(enumDifficulty);
      }
   }

   @Override
   public void addServerStatsToSnooper(PlayerUsageSnooper playerUsageSnooper) {
      super.addServerStatsToSnooper(playerUsageSnooper);
      playerUsageSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
   }

   @Override
   public boolean isSnooperEnabled() {
      return Minecraft.getMinecraft().isSnooperEnabled();
   }

   @Override
   public String shareToLAN(WorldSettings.GameType gameType, boolean boolean2) {
      try {
         int var6 = -1;

         try {
            var6 = HttpUtil.getSuitableLanPort();
         } catch (IOException var7) {
         }

         if (var6 <= 0) {
            var6 = 25564;
         }

         this.getNetworkSystem().addLanEndpoint(null, var6);
         logger.info("Started on " + var6);
         this.isPublic = true;
         this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), var6 + "");
         this.lanServerPing.start();
         this.getConfigurationManager().setGameType(gameType);
         this.getConfigurationManager().setCommandsAllowedForAll(boolean2);
         return var6 + "";
      } catch (IOException var8) {
         return null;
      }
   }

   @Override
   public void stopServer() {
      super.stopServer();
      if (this.lanServerPing != null) {
         this.lanServerPing.interrupt();
         this.lanServerPing = null;
      }
   }

   @Override
   public void initiateShutdown() {
      if (this.isServerRunning()) {
         Futures.getUnchecked(this.addScheduledTask(() -> {
            for (EntityPlayerMP var5 : Lists.newArrayList(this.getConfigurationManager().getPlayerList())) {
               this.getConfigurationManager().playerLoggedOut(var5);
            }
         }));
      }

      super.initiateShutdown();
      if (this.lanServerPing != null) {
         this.lanServerPing.interrupt();
         this.lanServerPing = null;
      }
   }

   public void setStaticInstance() {
      this.setInstance();
   }

   public boolean getPublic() {
      return this.isPublic;
   }

   @Override
   public void setGameType(WorldSettings.GameType gameType) {
      this.getConfigurationManager().setGameType(gameType);
   }

   @Override
   public boolean isCommandBlockEnabled() {
      return true;
   }

   @Override
   public int getOpPermissionLevel() {
      return 4;
   }

   private void onTick() {
      for (WorldServer var7 : this.worldServers) {
         this.onTick(var7);
      }
   }

   public DifficultyInstance getDifficultyAsync(World world, BlockPos blockPos) {
      this.difficultyUpdateWorld = world;
      this.difficultyUpdatePos = blockPos;
      return this.difficultyLast;
   }

   private void onTick(WorldServer worldServer) {
      if (!Config.Method270()) {
         this.fixWorldTime(worldServer);
      }

      if (!Config.Method268()) {
         this.fixWorldWeather(worldServer);
      }

      if (Config.waterOpacityChanged) {
         Config.waterOpacityChanged = false;
         ClearWater.updateWaterOpacity(Config.getGameSettings(), worldServer);
      }

      if (this.difficultyUpdateWorld == worldServer && this.difficultyUpdatePos != null) {
         this.difficultyLast = worldServer.getDifficultyForLocation(this.difficultyUpdatePos);
         this.difficultyUpdateWorld = null;
         this.difficultyUpdatePos = null;
      }
   }

   private void fixWorldWeather(WorldServer worldServer) {
      WorldInfo var5 = worldServer.getWorldInfo();
      if (var5.isRaining() || var5.isThundering()) {
         var5.setRainTime(0);
         var5.setRaining(false);
         worldServer.setRainStrength(0.0F);
         var5.setThunderTime(0);
         var5.setThundering(false);
         worldServer.setThunderStrength(0.0F);
         this.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0F));
         this.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(7, 0.0F));
         this.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(8, 0.0F));
      }
   }

   private void fixWorldTime(WorldServer worldServer) {
      WorldInfo var5 = worldServer.getWorldInfo();
      if (var5.getGameType().getID() == 1) {
         long var6 = worldServer.getWorldTime();
         long var8 = var6 % 24000L;
         if (Config.Method269()) {
            if (var8 <= 1000L) {
               worldServer.setWorldTime(var6 - var8 + 1001L);
            }

            if (var8 >= 11000L) {
               worldServer.setWorldTime(var6 - var8 + 24001L);
            }
         }

         if (Config.Method271()) {
            if (var8 <= 14000L) {
               worldServer.setWorldTime(var6 - var8 + 14001L);
            }

            if (var8 >= 22000L) {
               worldServer.setWorldTime(var6 - var8 + 24000L + 14001L);
            }
         }
      }
   }
}
