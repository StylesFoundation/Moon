package net.minecraft.world.storage;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class WorldInfo {
   public static final EnumDifficulty DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;
   private long randomSeed;
   private WorldType terrainType = WorldType.DEFAULT;
   private String generatorOptions = "";
   private int spawnX;
   private int spawnY;
   private int spawnZ;
   private long totalTime;
   private long worldTime;
   private long lastTimePlayed;
   private long sizeOnDisk;
   private NBTTagCompound playerTag;
   private int dimension;
   private String levelName;
   private int saveVersion;
   private int cleanWeatherTime;
   private boolean raining;
   private int rainTime;
   private boolean thundering;
   private int thunderTime;
   private WorldSettings.GameType theGameType;
   private boolean mapFeaturesEnabled;
   private boolean hardcore;
   private boolean allowCommands;
   private boolean initialized;
   private EnumDifficulty difficulty;
   private boolean difficultyLocked;
   private double borderCenterX = 0.0;
   private double borderCenterZ = 0.0;
   private double borderSize = 6.0E7;
   private long borderSizeLerpTime = 0L;
   private double borderSizeLerpTarget = 0.0;
   private double borderSafeZone = 5.0;
   private double borderDamagePerBlock = 0.2;
   private int borderWarningDistance = 5;
   private int borderWarningTime = 15;
   private GameRules theGameRules = new GameRules();

   protected WorldInfo() {
   }

   public WorldInfo(NBTTagCompound nBTTagCompound) {
      this.randomSeed = nBTTagCompound.getLong("RandomSeed");
      if (nBTTagCompound.hasKey("generatorName", 8)) {
         String var5 = nBTTagCompound.getString("generatorName");
         this.terrainType = WorldType.parseWorldType(var5);
         if (this.terrainType == null) {
            this.terrainType = WorldType.DEFAULT;
         } else if (this.terrainType.isVersioned()) {
            int var6 = 0;
            if (nBTTagCompound.hasKey("generatorVersion", 99)) {
               var6 = nBTTagCompound.getInteger("generatorVersion");
            }

            this.terrainType = this.terrainType.getWorldTypeForGeneratorVersion(var6);
         }

         if (nBTTagCompound.hasKey("generatorOptions", 8)) {
            this.generatorOptions = nBTTagCompound.getString("generatorOptions");
         }
      }

      this.theGameType = WorldSettings.GameType.getByID(nBTTagCompound.getInteger("GameType"));
      if (nBTTagCompound.hasKey("MapFeatures", 99)) {
         this.mapFeaturesEnabled = nBTTagCompound.getBoolean("MapFeatures");
      } else {
         this.mapFeaturesEnabled = true;
      }

      this.spawnX = nBTTagCompound.getInteger("SpawnX");
      this.spawnY = nBTTagCompound.getInteger("SpawnY");
      this.spawnZ = nBTTagCompound.getInteger("SpawnZ");
      this.totalTime = nBTTagCompound.getLong("Time");
      if (nBTTagCompound.hasKey("DayTime", 99)) {
         this.worldTime = nBTTagCompound.getLong("DayTime");
      } else {
         this.worldTime = this.totalTime;
      }

      this.lastTimePlayed = nBTTagCompound.getLong("LastPlayed");
      this.sizeOnDisk = nBTTagCompound.getLong("SizeOnDisk");
      this.levelName = nBTTagCompound.getString("LevelName");
      this.saveVersion = nBTTagCompound.getInteger("version");
      this.cleanWeatherTime = nBTTagCompound.getInteger("clearWeatherTime");
      this.rainTime = nBTTagCompound.getInteger("rainTime");
      this.raining = nBTTagCompound.getBoolean("raining");
      this.thunderTime = nBTTagCompound.getInteger("thunderTime");
      this.thundering = nBTTagCompound.getBoolean("thundering");
      this.hardcore = nBTTagCompound.getBoolean("hardcore");
      if (nBTTagCompound.hasKey("initialized", 99)) {
         this.initialized = nBTTagCompound.getBoolean("initialized");
      } else {
         this.initialized = true;
      }

      if (nBTTagCompound.hasKey("allowCommands", 99)) {
         this.allowCommands = nBTTagCompound.getBoolean("allowCommands");
      } else {
         this.allowCommands = this.theGameType == WorldSettings.GameType.CREATIVE;
      }

      if (nBTTagCompound.hasKey("Player", 10)) {
         this.playerTag = nBTTagCompound.getCompoundTag("Player");
         this.dimension = this.playerTag.getInteger("Dimension");
      }

      if (nBTTagCompound.hasKey("GameRules", 10)) {
         this.theGameRules.readFromNBT(nBTTagCompound.getCompoundTag("GameRules"));
      }

      if (nBTTagCompound.hasKey("Difficulty", 99)) {
         this.difficulty = EnumDifficulty.getDifficultyEnum(nBTTagCompound.getByte("Difficulty"));
      }

      if (nBTTagCompound.hasKey("DifficultyLocked", 1)) {
         this.difficultyLocked = nBTTagCompound.getBoolean("DifficultyLocked");
      }

      if (nBTTagCompound.hasKey("BorderCenterX", 99)) {
         this.borderCenterX = nBTTagCompound.getDouble("BorderCenterX");
      }

      if (nBTTagCompound.hasKey("BorderCenterZ", 99)) {
         this.borderCenterZ = nBTTagCompound.getDouble("BorderCenterZ");
      }

      if (nBTTagCompound.hasKey("BorderSize", 99)) {
         this.borderSize = nBTTagCompound.getDouble("BorderSize");
      }

      if (nBTTagCompound.hasKey("BorderSizeLerpTime", 99)) {
         this.borderSizeLerpTime = nBTTagCompound.getLong("BorderSizeLerpTime");
      }

      if (nBTTagCompound.hasKey("BorderSizeLerpTarget", 99)) {
         this.borderSizeLerpTarget = nBTTagCompound.getDouble("BorderSizeLerpTarget");
      }

      if (nBTTagCompound.hasKey("BorderSafeZone", 99)) {
         this.borderSafeZone = nBTTagCompound.getDouble("BorderSafeZone");
      }

      if (nBTTagCompound.hasKey("BorderDamagePerBlock", 99)) {
         this.borderDamagePerBlock = nBTTagCompound.getDouble("BorderDamagePerBlock");
      }

      if (nBTTagCompound.hasKey("BorderWarningBlocks", 99)) {
         this.borderWarningDistance = nBTTagCompound.getInteger("BorderWarningBlocks");
      }

      if (nBTTagCompound.hasKey("BorderWarningTime", 99)) {
         this.borderWarningTime = nBTTagCompound.getInteger("BorderWarningTime");
      }
   }

   public WorldInfo(WorldSettings worldSettings, String string) {
      this.populateFromWorldSettings(worldSettings);
      this.levelName = string;
      this.difficulty = DEFAULT_DIFFICULTY;
      this.initialized = false;
   }

   public void populateFromWorldSettings(WorldSettings worldSettings) {
      this.randomSeed = worldSettings.getSeed();
      this.theGameType = worldSettings.getGameType();
      this.mapFeaturesEnabled = worldSettings.isMapFeaturesEnabled();
      this.hardcore = worldSettings.getHardcoreEnabled();
      this.terrainType = worldSettings.getTerrainType();
      this.generatorOptions = worldSettings.getWorldName();
      this.allowCommands = worldSettings.areCommandsAllowed();
   }

   public WorldInfo(WorldInfo worldInfo) {
      this.randomSeed = worldInfo.randomSeed;
      this.terrainType = worldInfo.terrainType;
      this.generatorOptions = worldInfo.generatorOptions;
      this.theGameType = worldInfo.theGameType;
      this.mapFeaturesEnabled = worldInfo.mapFeaturesEnabled;
      this.spawnX = worldInfo.spawnX;
      this.spawnY = worldInfo.spawnY;
      this.spawnZ = worldInfo.spawnZ;
      this.totalTime = worldInfo.totalTime;
      this.worldTime = worldInfo.worldTime;
      this.lastTimePlayed = worldInfo.lastTimePlayed;
      this.sizeOnDisk = worldInfo.sizeOnDisk;
      this.playerTag = worldInfo.playerTag;
      this.dimension = worldInfo.dimension;
      this.levelName = worldInfo.levelName;
      this.saveVersion = worldInfo.saveVersion;
      this.rainTime = worldInfo.rainTime;
      this.raining = worldInfo.raining;
      this.thunderTime = worldInfo.thunderTime;
      this.thundering = worldInfo.thundering;
      this.hardcore = worldInfo.hardcore;
      this.allowCommands = worldInfo.allowCommands;
      this.initialized = worldInfo.initialized;
      this.theGameRules = worldInfo.theGameRules;
      this.difficulty = worldInfo.difficulty;
      this.difficultyLocked = worldInfo.difficultyLocked;
      this.borderCenterX = worldInfo.borderCenterX;
      this.borderCenterZ = worldInfo.borderCenterZ;
      this.borderSize = worldInfo.borderSize;
      this.borderSizeLerpTime = worldInfo.borderSizeLerpTime;
      this.borderSizeLerpTarget = worldInfo.borderSizeLerpTarget;
      this.borderSafeZone = worldInfo.borderSafeZone;
      this.borderDamagePerBlock = worldInfo.borderDamagePerBlock;
      this.borderWarningTime = worldInfo.borderWarningTime;
      this.borderWarningDistance = worldInfo.borderWarningDistance;
   }

   public NBTTagCompound getNBTTagCompound() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.updateTagCompound(var1, this.playerTag);
      return var1;
   }

   public NBTTagCompound cloneNBTCompound(NBTTagCompound nBTTagCompound) {
      NBTTagCompound var2 = new NBTTagCompound();
      this.updateTagCompound(var2, nBTTagCompound);
      return var2;
   }

   private void updateTagCompound(NBTTagCompound nBTTagCompound1, NBTTagCompound nBTTagCompound2) {
      nBTTagCompound1.setLong("RandomSeed", this.randomSeed);
      nBTTagCompound1.setString("generatorName", this.terrainType.getWorldTypeName());
      nBTTagCompound1.setInteger("generatorVersion", this.terrainType.getGeneratorVersion());
      nBTTagCompound1.setString("generatorOptions", this.generatorOptions);
      nBTTagCompound1.setInteger("GameType", this.theGameType.getID());
      nBTTagCompound1.setBoolean("MapFeatures", this.mapFeaturesEnabled);
      nBTTagCompound1.setInteger("SpawnX", this.spawnX);
      nBTTagCompound1.setInteger("SpawnY", this.spawnY);
      nBTTagCompound1.setInteger("SpawnZ", this.spawnZ);
      nBTTagCompound1.setLong("Time", this.totalTime);
      nBTTagCompound1.setLong("DayTime", this.worldTime);
      nBTTagCompound1.setLong("SizeOnDisk", this.sizeOnDisk);
      nBTTagCompound1.setLong("LastPlayed", MinecraftServer.getCurrentTimeMillis());
      nBTTagCompound1.setString("LevelName", this.levelName);
      nBTTagCompound1.setInteger("version", this.saveVersion);
      nBTTagCompound1.setInteger("clearWeatherTime", this.cleanWeatherTime);
      nBTTagCompound1.setInteger("rainTime", this.rainTime);
      nBTTagCompound1.setBoolean("raining", this.raining);
      nBTTagCompound1.setInteger("thunderTime", this.thunderTime);
      nBTTagCompound1.setBoolean("thundering", this.thundering);
      nBTTagCompound1.setBoolean("hardcore", this.hardcore);
      nBTTagCompound1.setBoolean("allowCommands", this.allowCommands);
      nBTTagCompound1.setBoolean("initialized", this.initialized);
      nBTTagCompound1.setDouble("BorderCenterX", this.borderCenterX);
      nBTTagCompound1.setDouble("BorderCenterZ", this.borderCenterZ);
      nBTTagCompound1.setDouble("BorderSize", this.borderSize);
      nBTTagCompound1.setLong("BorderSizeLerpTime", this.borderSizeLerpTime);
      nBTTagCompound1.setDouble("BorderSafeZone", this.borderSafeZone);
      nBTTagCompound1.setDouble("BorderDamagePerBlock", this.borderDamagePerBlock);
      nBTTagCompound1.setDouble("BorderSizeLerpTarget", this.borderSizeLerpTarget);
      nBTTagCompound1.setDouble("BorderWarningBlocks", this.borderWarningDistance);
      nBTTagCompound1.setDouble("BorderWarningTime", this.borderWarningTime);
      if (this.difficulty != null) {
         nBTTagCompound1.setByte("Difficulty", (byte)this.difficulty.getDifficultyId());
      }

      nBTTagCompound1.setBoolean("DifficultyLocked", this.difficultyLocked);
      nBTTagCompound1.setTag("GameRules", this.theGameRules.writeToNBT());
      if (nBTTagCompound2 != null) {
         nBTTagCompound1.setTag("Player", nBTTagCompound2);
      }
   }

   public long getSeed() {
      return this.randomSeed;
   }

   public int getSpawnX() {
      return this.spawnX;
   }

   public int getSpawnY() {
      return this.spawnY;
   }

   public int getSpawnZ() {
      return this.spawnZ;
   }

   public long getWorldTotalTime() {
      return this.totalTime;
   }

   public long getWorldTime() {
      return this.worldTime;
   }

   public long getSizeOnDisk() {
      return this.sizeOnDisk;
   }

   public NBTTagCompound getPlayerNBTTagCompound() {
      return this.playerTag;
   }

   public void setSpawnX(int integer) {
      this.spawnX = integer;
   }

   public void setSpawnY(int integer) {
      this.spawnY = integer;
   }

   public void setSpawnZ(int integer) {
      this.spawnZ = integer;
   }

   public void setWorldTotalTime(long long1) {
      this.totalTime = long1;
   }

   public void setWorldTime(long long1) {
      this.worldTime = long1;
   }

   public void setSpawn(BlockPos blockPos) {
      this.spawnX = blockPos.getX();
      this.spawnY = blockPos.getY();
      this.spawnZ = blockPos.getZ();
   }

   public String getWorldName() {
      return this.levelName;
   }

   public void setWorldName(String string) {
      this.levelName = string;
   }

   public int getSaveVersion() {
      return this.saveVersion;
   }

   public void setSaveVersion(int integer) {
      this.saveVersion = integer;
   }

   public long getLastTimePlayed() {
      return this.lastTimePlayed;
   }

   public int getCleanWeatherTime() {
      return this.cleanWeatherTime;
   }

   public void setCleanWeatherTime(int integer) {
      this.cleanWeatherTime = integer;
   }

   public boolean isThundering() {
      return this.thundering;
   }

   public void setThundering(boolean boolean1) {
      this.thundering = boolean1;
   }

   public int getThunderTime() {
      return this.thunderTime;
   }

   public void setThunderTime(int integer) {
      this.thunderTime = integer;
   }

   public boolean isRaining() {
      return this.raining;
   }

   public void setRaining(boolean boolean1) {
      this.raining = boolean1;
   }

   public int getRainTime() {
      return this.rainTime;
   }

   public void setRainTime(int integer) {
      this.rainTime = integer;
   }

   public WorldSettings.GameType getGameType() {
      return this.theGameType;
   }

   public boolean isMapFeaturesEnabled() {
      return this.mapFeaturesEnabled;
   }

   public void setMapFeaturesEnabled(boolean boolean1) {
      this.mapFeaturesEnabled = boolean1;
   }

   public void setGameType(WorldSettings.GameType gameType) {
      this.theGameType = gameType;
   }

   public boolean isHardcoreModeEnabled() {
      return this.hardcore;
   }

   public void setHardcore(boolean boolean1) {
      this.hardcore = boolean1;
   }

   public WorldType getTerrainType() {
      return this.terrainType;
   }

   public void setTerrainType(WorldType worldType) {
      this.terrainType = worldType;
   }

   public String getGeneratorOptions() {
      return this.generatorOptions;
   }

   public boolean areCommandsAllowed() {
      return this.allowCommands;
   }

   public void setAllowCommands(boolean boolean1) {
      this.allowCommands = boolean1;
   }

   public boolean isInitialized() {
      return this.initialized;
   }

   public void setServerInitialized(boolean boolean1) {
      this.initialized = boolean1;
   }

   public GameRules getGameRulesInstance() {
      return this.theGameRules;
   }

   public double getBorderCenterX() {
      return this.borderCenterX;
   }

   public double getBorderCenterZ() {
      return this.borderCenterZ;
   }

   public double getBorderSize() {
      return this.borderSize;
   }

   public void setBorderSize(double double1) {
      this.borderSize = double1;
   }

   public long getBorderLerpTime() {
      return this.borderSizeLerpTime;
   }

   public void setBorderLerpTime(long long1) {
      this.borderSizeLerpTime = long1;
   }

   public double getBorderLerpTarget() {
      return this.borderSizeLerpTarget;
   }

   public void setBorderLerpTarget(double double1) {
      this.borderSizeLerpTarget = double1;
   }

   public void getBorderCenterZ(double double1) {
      this.borderCenterZ = double1;
   }

   public void getBorderCenterX(double double1) {
      this.borderCenterX = double1;
   }

   public double getBorderSafeZone() {
      return this.borderSafeZone;
   }

   public void setBorderSafeZone(double double1) {
      this.borderSafeZone = double1;
   }

   public double getBorderDamagePerBlock() {
      return this.borderDamagePerBlock;
   }

   public void setBorderDamagePerBlock(double double1) {
      this.borderDamagePerBlock = double1;
   }

   public int getBorderWarningDistance() {
      return this.borderWarningDistance;
   }

   public int getBorderWarningTime() {
      return this.borderWarningTime;
   }

   public void setBorderWarningDistance(int integer) {
      this.borderWarningDistance = integer;
   }

   public void setBorderWarningTime(int integer) {
      this.borderWarningTime = integer;
   }

   public EnumDifficulty getDifficulty() {
      return this.difficulty;
   }

   public void setDifficulty(EnumDifficulty enumDifficulty) {
      this.difficulty = enumDifficulty;
   }

   public boolean isDifficultyLocked() {
      return this.difficultyLocked;
   }

   public void setDifficultyLocked(boolean boolean1) {
      this.difficultyLocked = boolean1;
   }

   public void addToCrashReport(CrashReportCategory crashReportCategory) {
      crashReportCategory.addCrashSectionCallable("Level seed", () -> String.valueOf(this.getSeed()));
      crashReportCategory.addCrashSectionCallable(
         "Level generator",
         () -> String.format(
            "ID %02d - %s, ver %d. Features enabled: %b",
            this.terrainType.getWorldTypeID(),
            this.terrainType.getWorldTypeName(),
            this.terrainType.getGeneratorVersion(),
            this.mapFeaturesEnabled
         )
      );
      crashReportCategory.addCrashSectionCallable("Level generator options", () -> this.generatorOptions);
      crashReportCategory.addCrashSectionCallable("Level spawn location", () -> CrashReportCategory.getCoordinateInfo(this.spawnX, this.spawnY, this.spawnZ));
      crashReportCategory.addCrashSectionCallable("Level time", () -> String.format("%d game time, %d day time", this.totalTime, this.worldTime));
      crashReportCategory.addCrashSectionCallable("Level dimension", () -> String.valueOf(this.dimension));
      crashReportCategory.addCrashSectionCallable("Level storage version", () -> {
         String var4 = "Unknown?";

         try {
            switch (this.saveVersion) {
               case 19132:
                  var4 = "McRegion";
                  break;
               case 19133:
                  var4 = "Anvil";
            }
         } catch (Throwable var5) {
         }

         return String.format("0x%05X - %s", this.saveVersion, var4);
      });
      crashReportCategory.addCrashSectionCallable(
         "Level weather",
         () -> String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", this.rainTime, this.raining, this.thunderTime, this.thundering)
      );
      crashReportCategory.addCrashSectionCallable(
         "Level game mode",
         () -> String.format(
            "Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", this.theGameType.getName(), this.theGameType.getID(), this.hardcore, this.allowCommands
         )
      );
   }
}
