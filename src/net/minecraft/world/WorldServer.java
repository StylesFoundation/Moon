package net.minecraft.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.INpc;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ReportedException;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldServer extends World implements IThreadListener {
   private static final Logger logger = LogManager.getLogger();
   private final MinecraftServer mcServer;
   private final EntityTracker theEntityTracker;
   private final PlayerManager thePlayerManager;
   private final Set<NextTickListEntry> pendingTickListEntriesHashSet = Sets.newHashSet();
   private final TreeSet<NextTickListEntry> pendingTickListEntriesTreeSet = new TreeSet<>();
   private final Map<UUID, Entity> entitiesByUuid = Maps.newHashMap();
   public ChunkProviderServer theChunkProviderServer;
   public boolean disableLevelSaving;
   private boolean allPlayersSleeping;
   private int updateEntityTick;
   private final Teleporter worldTeleporter;
   private final SpawnerAnimals mobSpawner = new SpawnerAnimals();
   protected final VillageSiege villageSiege = new VillageSiege(this);
   private final WorldServer.ServerBlockEventList[] blockEventQueue = new WorldServer.ServerBlockEventList[]{
      new WorldServer.ServerBlockEventList(), new WorldServer.ServerBlockEventList()
   };
   private int blockEventCacheIndex;
   private static final List<WeightedRandomChestContent> bonusChestContent = Lists.newArrayList(
      new WeightedRandomChestContent[]{
         new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10),
         new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10),
         new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10),
         new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3),
         new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3),
         new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5),
         new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3),
         new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10)
      }
   );
   private final List<NextTickListEntry> pendingTickListEntriesThisTick = Lists.newArrayList();

   public WorldServer(MinecraftServer minecraftServer, ISaveHandler iSaveHandler, WorldInfo worldInfo, int integer, Profiler profiler) {
      super(iSaveHandler, worldInfo, WorldProvider.getProviderForDimension(integer), profiler, false);
      this.mcServer = minecraftServer;
      this.theEntityTracker = new EntityTracker(this);
      this.thePlayerManager = new PlayerManager(this);
      this.provider.registerWorld(this);
      this.chunkProvider = this.createChunkProvider();
      this.worldTeleporter = new Teleporter(this);
      this.calculateInitialSkylight();
      this.calculateInitialWeather();
      this.getWorldBorder().setSize(minecraftServer.getMaxWorldSize());
   }

   @Override
   public World init() {
      this.mapStorage = new MapStorage(this.saveHandler);
      String var4 = VillageCollection.Method1033(this.provider);
      VillageCollection var5 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, var4);
      if (var5 == null) {
         this.villageCollectionObj = new VillageCollection(this);
         this.mapStorage.setData(var4, this.villageCollectionObj);
      } else {
         this.villageCollectionObj = var5;
         this.villageCollectionObj.setWorldsForAll(this);
      }

      this.worldScoreboard = new ServerScoreboard(this.mcServer);
      ScoreboardSaveData var6 = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
      if (var6 == null) {
         var6 = new ScoreboardSaveData();
         this.mapStorage.setData("scoreboard", var6);
      }

      var6.setScoreboard(this.worldScoreboard);
      ((ServerScoreboard)this.worldScoreboard).func_96547_a(var6);
      this.getWorldBorder().setCenter(this.worldInfo.getBorderCenterX(), this.worldInfo.getBorderCenterZ());
      this.getWorldBorder().setDamageAmount(this.worldInfo.getBorderDamagePerBlock());
      this.getWorldBorder().setDamageBuffer(this.worldInfo.getBorderSafeZone());
      this.getWorldBorder().setWarningDistance(this.worldInfo.getBorderWarningDistance());
      this.getWorldBorder().setWarningTime(this.worldInfo.getBorderWarningTime());
      if (this.worldInfo.getBorderLerpTime() > 0L) {
         this.getWorldBorder().setTransition(this.worldInfo.getBorderSize(), this.worldInfo.getBorderLerpTarget(), this.worldInfo.getBorderLerpTime());
      } else {
         this.getWorldBorder().setTransition(this.worldInfo.getBorderSize());
      }

      return this;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.getWorldInfo().isHardcoreModeEnabled() && this.getDifficulty() != EnumDifficulty.HARD) {
         this.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
      }

      this.provider.getWorldChunkManager().cleanupCache();
      if (this.areAllPlayersAsleep()) {
         if (this.getGameRules().getBoolean("doDaylightCycle")) {
            long var4 = this.worldInfo.getWorldTime() + 24000L;
            this.worldInfo.setWorldTime(var4 - var4 % 24000L);
         }

         this.wakeAllPlayers();
      }

      this.theProfiler.startSection("mobSpawner");
      if (this.getGameRules().getBoolean("doMobSpawning") && this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
         this.mobSpawner.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() % 400L == 0L);
      }

      this.theProfiler.endStartSection("chunkSource");
      this.chunkProvider.unloadQueuedChunks();
      int var6 = this.calculateSkylightSubtracted(1.0F);
      if (var6 != this.getSkylightSubtracted()) {
         this.setSkylightSubtracted(var6);
      }

      this.worldInfo.setWorldTotalTime(this.worldInfo.getWorldTotalTime() + 1L);
      if (this.getGameRules().getBoolean("doDaylightCycle")) {
         this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
      }

      this.theProfiler.endStartSection("tickPending");
      this.tickUpdates(false);
      this.theProfiler.endStartSection("tickBlocks");
      this.updateBlocks();
      this.theProfiler.endStartSection("chunkMap");
      this.thePlayerManager.updatePlayerInstances();
      this.theProfiler.endStartSection("village");
      this.villageCollectionObj.tick();
      this.villageSiege.tick();
      this.theProfiler.endStartSection("portalForcer");
      this.worldTeleporter.removeStalePortalLocations(this.getTotalWorldTime());
      this.theProfiler.endSection();
      this.sendQueuedBlockEvents();
   }

   public BiomeGenBase.SpawnListEntry getSpawnListEntryForTypeAt(EnumCreatureType enumCreatureType, BlockPos blockPos) {
      List<BiomeGenBase.SpawnListEntry> var6 = this.getChunkProvider().getPossibleCreatures(enumCreatureType, blockPos);
      return var6 != null && !var6.isEmpty() ? (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(this.rand, var6) : null;
   }

   public boolean canCreatureTypeSpawnHere(EnumCreatureType enumCreatureType, BiomeGenBase.SpawnListEntry spawnListEntry, BlockPos blockPos) {
      List<BiomeGenBase.SpawnListEntry> var7 = this.getChunkProvider().getPossibleCreatures(enumCreatureType, blockPos);
      return var7 != null && !var7.isEmpty() && var7.contains(spawnListEntry);
   }

   @Override
   public void updateAllPlayersSleepingFlag() {
      this.allPlayersSleeping = false;
      if (!this.playerEntities.isEmpty()) {
         int var4 = 0;
         int var5 = 0;

         for (EntityPlayer var7 : this.playerEntities) {
            if (var7.isSpectator()) {
               var4++;
            } else if (var7.isPlayerSleeping()) {
               var5++;
            }
         }

         this.allPlayersSleeping = var5 > 0 && var5 >= this.playerEntities.size() - var4;
      }
   }

   protected void wakeAllPlayers() {
      this.allPlayersSleeping = false;

      for (EntityPlayer var5 : this.playerEntities) {
         if (var5.isPlayerSleeping()) {
            var5.wakeUpPlayer(false, false, true);
         }
      }

      this.resetRainAndThunder();
   }

   private void resetRainAndThunder() {
      this.worldInfo.setRainTime(0);
      this.worldInfo.setRaining(false);
      this.worldInfo.setThunderTime(0);
      this.worldInfo.setThundering(false);
   }

   public boolean areAllPlayersAsleep() {
      if (this.allPlayersSleeping && !this.isRemote) {
         for (EntityPlayer var5 : this.playerEntities) {
            if (var5.isSpectator() || !var5.isPlayerFullyAsleep()) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void setInitialSpawnLocation() {
      if (this.worldInfo.getSpawnY() <= 0) {
         this.worldInfo.setSpawnY(this.getSeaLevel() + 1);
      }

      int var4 = this.worldInfo.getSpawnX();
      int var5 = this.worldInfo.getSpawnZ();
      int var6 = 0;

      while (this.getGroundAboveSeaLevel(new BlockPos(var4, 0, var5)).getMaterial() == Material.air) {
         var4 += this.rand.nextInt(8) - this.rand.nextInt(8);
         var5 += this.rand.nextInt(8) - this.rand.nextInt(8);
         if (++var6 == 10000) {
            break;
         }
      }

      this.worldInfo.setSpawnX(var4);
      this.worldInfo.setSpawnZ(var5);
   }

   @Override
   protected void updateBlocks() {
      super.updateBlocks();
      if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
         for (ChunkCoordIntPair var5 : this.activeChunkSet) {
            this.getChunkFromChunkCoords(var5.chunkXPos, var5.chunkZPos).func_150804_b(false);
         }
      } else {
         int var23 = 0;
         int var24 = 0;

         for (ChunkCoordIntPair var7 : this.activeChunkSet) {
            int var8 = var7.chunkXPos * 16;
            int var9 = var7.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            Chunk var10 = this.getChunkFromChunkCoords(var7.chunkXPos, var7.chunkZPos);
            this.playMoodSoundAndCheckLight(var8, var9, var10);
            this.theProfiler.endStartSection("tickChunk");
            var10.func_150804_b(false);
            this.theProfiler.endStartSection("thunder");
            if (this.rand.nextInt(100000) == 0 && this.isRaining() && this.isThundering()) {
               this.updateLCG = this.updateLCG * 3 + 1013904223;
               int var11 = this.updateLCG >> 2;
               BlockPos var12 = this.adjustPosToNearbyEntity(new BlockPos(var8 + (var11 & 15), 0, var9 + (var11 >> 8 & 15)));
               if (this.isRainingAt(var12)) {
                  this.addWeatherEffect(new EntityLightningBolt(this, var12.getX(), var12.getY(), var12.getZ()));
               }
            }

            this.theProfiler.endStartSection("iceandsnow");
            if (this.rand.nextInt(16) == 0) {
               this.updateLCG = this.updateLCG * 3 + 1013904223;
               int var25 = this.updateLCG >> 2;
               BlockPos var27 = this.getPrecipitationHeight(new BlockPos(var8 + (var25 & 15), 0, var9 + (var25 >> 8 & 15)));
               BlockPos var13 = var27.down();
               if (this.canBlockFreezeNoWater(var13)) {
                  this.setBlockState(var13, Blocks.ice.getDefaultState());
               }

               if (this.isRaining() && this.canSnowAt(var27, true)) {
                  this.setBlockState(var27, Blocks.snow_layer.getDefaultState());
               }

               if (this.isRaining() && this.getBiomeGenForCoords(var13).canRain()) {
                  this.getBlockState(var13).getBlock().fillWithRain(this, var13);
               }
            }

            this.theProfiler.endStartSection("tickBlocks");
            int var26 = this.getGameRules().getInt("randomTickSpeed");
            if (var26 > 0) {
               for (ExtendedBlockStorage var15 : var10.getBlockStorageArray()) {
                  if (var15 != null && var15.getNeedsRandomTick()) {
                     for (int var16 = 0; var16 < var26; var16++) {
                        this.updateLCG = this.updateLCG * 3 + 1013904223;
                        int var17 = this.updateLCG >> 2;
                        int var18 = var17 & 15;
                        int var19 = var17 >> 8 & 15;
                        int var20 = var17 >> 16 & 15;
                        var24++;
                        IBlockState var21 = var15.get(var18, var20, var19);
                        Block var22 = var21.getBlock();
                        if (var22.getTickRandomly()) {
                           var23++;
                           var22.randomTick(this, new BlockPos(var18 + var8, var20 + var15.getYLocation(), var19 + var9), var21, this.rand);
                        }
                     }
                  }
               }
            }

            this.theProfiler.endSection();
         }
      }
   }

   protected BlockPos adjustPosToNearbyEntity(BlockPos blockPos) {
      BlockPos var5 = this.getPrecipitationHeight(blockPos);
      AxisAlignedBB var6 = new AxisAlignedBB(var5, new BlockPos(var5.getX(), this.getHeight(), var5.getZ())).expand(3.0, 3.0, 3.0);
      List var7 = this.getEntitiesWithinAABB(
         EntityLivingBase.class,
         var6,
         entityLivingBase -> entityLivingBase != null && entityLivingBase.isEntityAlive() && this.canSeeSky(entityLivingBase.getPosition())
      );
      return !var7.isEmpty() ? ((EntityLivingBase)var7.get(this.rand.nextInt(var7.size()))).getPosition() : var5;
   }

   @Override
   public boolean isBlockTickPending(BlockPos blockPos, Block block) {
      NextTickListEntry var3 = new NextTickListEntry(blockPos, block);
      return this.pendingTickListEntriesThisTick.contains(var3);
   }

   @Override
   public void scheduleUpdate(BlockPos blockPos, Block block, int integer) {
      this.updateBlockTick(blockPos, block, integer, 0);
   }

   @Override
   public void updateBlockTick(BlockPos blockPos, Block block, int integer3, int integer4) {
      NextTickListEntry var8 = new NextTickListEntry(blockPos, block);
      if (this.scheduledUpdatesAreImmediate && block.getMaterial() != Material.air) {
         if (block.requiresUpdates()) {
            byte var9 = 8;
            if (this.Method326(var8.position.add(-8, -8, -8), var8.position.add(var9, var9, var9))) {
               IBlockState var10 = this.getBlockState(var8.position);
               if (var10.getBlock().getMaterial() != Material.air && var10.getBlock() == var8.getBlock()) {
                  var10.getBlock().updateTick(this, var8.position, var10, this.rand);
               }
            }

            return;
         }

         integer3 = 1;
      }

      if (this.Method326(blockPos.add(0, 0, 0), blockPos.add(0, 0, 0))) {
         if (block.getMaterial() != Material.air) {
            var8.setScheduledTime(integer3 + this.worldInfo.getWorldTotalTime());
            var8.setPriority(integer4);
         }

         if (!this.pendingTickListEntriesHashSet.contains(var8)) {
            this.pendingTickListEntriesHashSet.add(var8);
            this.pendingTickListEntriesTreeSet.add(var8);
         }
      }
   }

   @Override
   public void scheduleBlockUpdate(BlockPos blockPos, Block block, int integer3, int integer4) {
      NextTickListEntry var8 = new NextTickListEntry(blockPos, block);
      var8.setPriority(integer4);
      if (block.getMaterial() != Material.air) {
         var8.setScheduledTime(integer3 + this.worldInfo.getWorldTotalTime());
      }

      if (!this.pendingTickListEntriesHashSet.contains(var8)) {
         this.pendingTickListEntriesHashSet.add(var8);
         this.pendingTickListEntriesTreeSet.add(var8);
      }
   }

   @Override
   public void updateEntities() {
      if (this.playerEntities.isEmpty()) {
         if (this.updateEntityTick++ >= 1200) {
            return;
         }
      } else {
         this.resetUpdateEntityTick();
      }

      super.updateEntities();
   }

   public void resetUpdateEntityTick() {
      this.updateEntityTick = 0;
   }

   @Override
   public boolean tickUpdates(boolean boolean1) {
      if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
         return false;
      }

      int var5 = this.pendingTickListEntriesTreeSet.size();
      if (var5 != this.pendingTickListEntriesHashSet.size()) {
         throw new IllegalStateException("TickNextTick list out of synch");
      }

      if (var5 > 1000) {
         var5 = 1000;
      }

      this.theProfiler.startSection("cleaning");

      for (int var6 = 0; var6 < var5; var6++) {
         NextTickListEntry var7 = this.pendingTickListEntriesTreeSet.first();
         if (!boolean1 && var7.scheduledTime > this.worldInfo.getWorldTotalTime()) {
            break;
         }

         this.pendingTickListEntriesTreeSet.remove(var7);
         this.pendingTickListEntriesHashSet.remove(var7);
         this.pendingTickListEntriesThisTick.add(var7);
      }

      this.theProfiler.endSection();
      this.theProfiler.startSection("ticking");
      Iterator var14 = this.pendingTickListEntriesThisTick.iterator();

      while (var14.hasNext()) {
         NextTickListEntry var15 = (NextTickListEntry)var14.next();
         var14.remove();
         if (this.Method326(var15.position.add(0, 0, 0), var15.position.add(0, 0, 0))) {
            IBlockState var9 = this.getBlockState(var15.position);
            if (var9.getBlock().getMaterial() != Material.air && Block.isEqualTo(var9.getBlock(), var15.getBlock())) {
               try {
                  var9.getBlock().updateTick(this, var15.position, var9, this.rand);
               } catch (Throwable var13) {
                  CrashReport var11 = CrashReport.makeCrashReport(var13, "Exception while ticking a block");
                  CrashReportCategory var12 = var11.makeCategory("Block being ticked");
                  CrashReportCategory.addBlockInfo(var12, var15.position, var9);
                  throw new ReportedException(var11);
               }
            }
         } else {
            this.scheduleUpdate(var15.position, var15.getBlock(), 0);
         }
      }

      this.theProfiler.endSection();
      this.pendingTickListEntriesThisTick.clear();
      return !this.pendingTickListEntriesTreeSet.isEmpty();
   }

   @Override
   public List<NextTickListEntry> getPendingBlockUpdates(Chunk chunk, boolean boolean2) {
      ChunkCoordIntPair var5 = chunk.getChunkCoordIntPair();
      int var6 = (var5.chunkXPos << 4) - 2;
      int var7 = var6 + 16 + 2;
      int var8 = (var5.chunkZPos << 4) - 2;
      int var9 = var8 + 16 + 2;
      return this.func_175712_a(new StructureBoundingBox(var6, 0, var8, var7, 256, var9), boolean2);
   }

   @Override
   public List<NextTickListEntry> func_175712_a(StructureBoundingBox structureBoundingBox, boolean boolean2) {
      ArrayList var6 = null;

      for (int var7 = 0; var7 < 2; var7++) {
         Iterator var8;
         if (var7 == 0) {
            var8 = this.pendingTickListEntriesTreeSet.iterator();
         } else {
            var8 = this.pendingTickListEntriesThisTick.iterator();
         }

         while (var8.hasNext()) {
            NextTickListEntry var9 = (NextTickListEntry)var8.next();
            BlockPos var10 = var9.position;
            if (var10.getX() >= structureBoundingBox.minX
               && var10.getX() < structureBoundingBox.maxX
               && var10.getZ() >= structureBoundingBox.minZ
               && var10.getZ() < structureBoundingBox.maxZ) {
               if (boolean2) {
                  this.pendingTickListEntriesHashSet.remove(var9);
                  var8.remove();
               }

               if (var6 == null) {
                  var6 = Lists.newArrayList();
               }

               var6.add(var9);
            }
         }
      }

      return var6;
   }

   @Override
   public void updateEntityWithOptionalForce(Entity entity, boolean boolean2) {
      if (!this.canSpawnAnimals() && (entity instanceof EntityAnimal || entity instanceof EntityWaterMob)) {
         entity.setDead();
      }

      if (!this.canSpawnNPCs() && entity instanceof INpc) {
         entity.setDead();
      }

      super.updateEntityWithOptionalForce(entity, boolean2);
   }

   private boolean canSpawnNPCs() {
      return this.mcServer.getCanSpawnNPCs();
   }

   private boolean canSpawnAnimals() {
      return this.mcServer.getCanSpawnAnimals();
   }

   @Override
   protected IChunkProvider createChunkProvider() {
      IChunkLoader var1 = this.saveHandler.getChunkLoader(this.provider);
      this.theChunkProviderServer = new ChunkProviderServer(this, var1, this.provider.createChunkGenerator());
      return this.theChunkProviderServer;
   }

   public List<TileEntity> getTileEntitiesIn(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      ArrayList var10 = Lists.newArrayList();

      for (TileEntity var12 : this.loadedTileEntityList) {
         BlockPos var13 = var12.getPos();
         if (var13.getX() >= integer1
            && var13.getY() >= integer2
            && var13.getZ() >= integer3
            && var13.getX() < integer4
            && var13.getY() < integer5
            && var13.getZ() < integer6) {
            var10.add(var12);
         }
      }

      return var10;
   }

   @Override
   public boolean isBlockModifiable(EntityPlayer entityPlayer, BlockPos blockPos) {
      return !this.mcServer.isBlockProtected(this, blockPos, entityPlayer) && this.getWorldBorder().contains(blockPos);
   }

   @Override
   public void initialize(WorldSettings worldSettings) {
      if (!this.worldInfo.isInitialized()) {
         try {
            this.createSpawnPosition(worldSettings);
            if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
               this.setDebugWorldSettings();
            }

            super.initialize(worldSettings);
         } catch (Throwable var8) {
            CrashReport var6 = CrashReport.makeCrashReport(var8, "Exception initializing level");

            try {
               this.addWorldInfoToCrashReport(var6);
            } catch (Throwable var7) {
            }

            throw new ReportedException(var6);
         }

         this.worldInfo.setServerInitialized(true);
      }
   }

   private void setDebugWorldSettings() {
      this.worldInfo.setMapFeaturesEnabled(false);
      this.worldInfo.setAllowCommands(true);
      this.worldInfo.setRaining(false);
      this.worldInfo.setThundering(false);
      this.worldInfo.setCleanWeatherTime(1000000000);
      this.worldInfo.setWorldTime(6000L);
      this.worldInfo.setGameType(WorldSettings.GameType.SPECTATOR);
      this.worldInfo.setHardcore(false);
      this.worldInfo.setDifficulty(EnumDifficulty.PEACEFUL);
      this.worldInfo.setDifficultyLocked(true);
      this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
   }

   private void createSpawnPosition(WorldSettings worldSettings) {
      if (!this.provider.canRespawnHere()) {
         this.worldInfo.setSpawn(BlockPos.ORIGIN.up(this.provider.getAverageGroundLevel()));
      } else if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
         this.worldInfo.setSpawn(BlockPos.ORIGIN.up());
      } else {
         this.findingSpawnPoint = true;
         WorldChunkManager var5 = this.provider.getWorldChunkManager();
         List var6 = var5.getBiomesToSpawnIn();
         Random var7 = new Random(this.getSeed());
         BlockPos var8 = var5.findBiomePosition(0, 0, 256, var6, var7);
         int var9 = 0;
         int var10 = this.provider.getAverageGroundLevel();
         int var11 = 0;
         if (var8 != null) {
            var9 = var8.getX();
            var11 = var8.getZ();
         } else {
            logger.warn("Unable to find spawn biome");
         }

         int var12 = 0;

         while (!this.provider.canCoordinateBeSpawn(var9, var11)) {
            var9 += var7.nextInt(64) - var7.nextInt(64);
            var11 += var7.nextInt(64) - var7.nextInt(64);
            if (++var12 == 1000) {
               break;
            }
         }

         this.worldInfo.setSpawn(new BlockPos(var9, var10, var11));
         this.findingSpawnPoint = false;
         if (worldSettings.isBonusChestEnabled()) {
            this.createBonusChest();
         }
      }
   }

   protected void createBonusChest() {
      WorldGeneratorBonusChest var4 = new WorldGeneratorBonusChest(bonusChestContent, 10);

      for (int var5 = 0; var5 < 10; var5++) {
         int var6 = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
         int var7 = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6);
         BlockPos var8 = this.getTopSolidOrLiquidBlock(new BlockPos(var6, 0, var7)).up();
         if (var4.generate(this, this.rand, var8)) {
            break;
         }
      }
   }

   public BlockPos getSpawnCoordinate() {
      return this.provider.getSpawnCoordinate();
   }

   public void saveAllChunks(boolean boolean1, IProgressUpdate iProgressUpdate) throws MinecraftException {
      if (this.chunkProvider.canSave()) {
         if (iProgressUpdate != null) {
            iProgressUpdate.displaySavingString("Saving level");
         }

         this.saveLevel();
         if (iProgressUpdate != null) {
            iProgressUpdate.displayLoadingString("Saving chunks");
         }

         this.chunkProvider.saveChunks(boolean1, iProgressUpdate);

         for (Chunk var7 : Lists.newArrayList(this.theChunkProviderServer.func_152380_a())) {
            if (var7 != null && !this.thePlayerManager.hasPlayerInstance(var7.xPosition, var7.zPosition)) {
               this.theChunkProviderServer.dropChunk(var7.xPosition, var7.zPosition);
            }
         }
      }
   }

   public void saveChunkData() {
      if (this.chunkProvider.canSave()) {
         this.chunkProvider.saveExtraData();
      }
   }

   protected void saveLevel() throws MinecraftException {
      this.checkSessionLock();
      this.worldInfo.setBorderSize(this.getWorldBorder().getDiameter());
      this.worldInfo.getBorderCenterX(this.getWorldBorder().getCenterX());
      this.worldInfo.getBorderCenterZ(this.getWorldBorder().getCenterZ());
      this.worldInfo.setBorderSafeZone(this.getWorldBorder().getDamageBuffer());
      this.worldInfo.setBorderDamagePerBlock(this.getWorldBorder().getDamageAmount());
      this.worldInfo.setBorderWarningDistance(this.getWorldBorder().getWarningDistance());
      this.worldInfo.setBorderWarningTime(this.getWorldBorder().getWarningTime());
      this.worldInfo.setBorderLerpTarget(this.getWorldBorder().getTargetSize());
      this.worldInfo.setBorderLerpTime(this.getWorldBorder().getTimeUntilTarget());
      this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
      this.mapStorage.saveAllData();
   }

   @Override
   protected void onEntityAdded(Entity entity) {
      super.onEntityAdded(entity);
      this.entitiesById.addKey(entity.getEntityId(), entity);
      this.entitiesByUuid.put(entity.getUniqueID(), entity);
      Entity[] var5 = entity.getParts();
      if (var5 != null) {
         for (Entity var9 : var5) {
            this.entitiesById.addKey(var9.getEntityId(), var9);
         }
      }
   }

   @Override
   protected void onEntityRemoved(Entity entity) {
      super.onEntityRemoved(entity);
      this.entitiesById.removeObject(entity.getEntityId());
      this.entitiesByUuid.remove(entity.getUniqueID());
      Entity[] var5 = entity.getParts();
      if (var5 != null) {
         for (Entity var9 : var5) {
            this.entitiesById.removeObject(var9.getEntityId());
         }
      }
   }

   @Override
   public boolean addWeatherEffect(Entity entity) {
      if (super.addWeatherEffect(entity)) {
         this.mcServer
            .getConfigurationManager()
            .sendToAllNear(entity.posX, entity.posY, entity.posZ, 512.0, this.provider.getDimensionId(), new S2CPacketSpawnGlobalEntity(entity));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void setEntityState(Entity entity, byte byte2) {
      this.getEntityTracker().func_151248_b(entity, new S19PacketEntityStatus(entity, byte2));
   }

   @Override
   public Explosion newExplosion(Entity entity, double double2, double double3, double double4, float float5, boolean boolean6, boolean boolean7) {
      Explosion var14 = new Explosion(this, entity, double2, double3, double4, float5, boolean6, boolean7);
      var14.doExplosionA();
      var14.doExplosionB(false);
      if (!boolean7) {
         var14.clearAffectedBlockPositions();
      }

      for (EntityPlayer var16 : this.playerEntities) {
         if (var16.getDistanceSq(double2, double3, double4) < 4096.0) {
            ((EntityPlayerMP)var16)
               .playerNetServerHandler
               .sendPacket(
                  new S27PacketExplosion(double2, double3, double4, float5, var14.getAffectedBlockPositions(), var14.getPlayerKnockbackMap().get(var16))
               );
         }
      }

      return var14;
   }

   @Override
   public void addBlockEvent(BlockPos blockPos, Block block, int integer3, int integer4) {
      BlockEventData var8 = new BlockEventData(blockPos, block, integer3, integer4);

      for (BlockEventData var10 : this.blockEventQueue[this.blockEventCacheIndex]) {
         if (var10.equals(var8)) {
            return;
         }
      }

      this.blockEventQueue[this.blockEventCacheIndex].add(var8);
   }

   private void sendQueuedBlockEvents() {
      while (!this.blockEventQueue[this.blockEventCacheIndex].isEmpty()) {
         int var4 = this.blockEventCacheIndex;
         this.blockEventCacheIndex ^= 1;

         for (BlockEventData var6 : this.blockEventQueue[var4]) {
            if (this.fireBlockEvent(var6)) {
               this.mcServer
                  .getConfigurationManager()
                  .sendToAllNear(
                     var6.getPosition().getX(),
                     var6.getPosition().getY(),
                     var6.getPosition().getZ(),
                     64.0,
                     this.provider.getDimensionId(),
                     new S24PacketBlockAction(var6.getPosition(), var6.getBlock(), var6.getEventID(), var6.getEventParameter())
                  );
            }
         }

         this.blockEventQueue[var4].clear();
      }
   }

   private boolean fireBlockEvent(BlockEventData blockEventData) {
      IBlockState var5 = this.getBlockState(blockEventData.getPosition());
      return var5.getBlock() == blockEventData.getBlock()
         && var5.getBlock().onBlockEventReceived(this, blockEventData.getPosition(), var5, blockEventData.getEventID(), blockEventData.getEventParameter());
   }

   public void flush() {
      this.saveHandler.flush();
   }

   @Override
   protected void updateWeather() {
      boolean var4 = this.isRaining();
      super.updateWeather();
      if (this.prevRainingStrength != this.rainingStrength) {
         this.mcServer
            .getConfigurationManager()
            .sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(7, this.rainingStrength), this.provider.getDimensionId());
      }

      if (this.prevThunderingStrength != this.thunderingStrength) {
         this.mcServer
            .getConfigurationManager()
            .sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(8, this.thunderingStrength), this.provider.getDimensionId());
      }

      if (var4 != this.isRaining()) {
         if (var4) {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0F));
         } else {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(1, 0.0F));
         }

         this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(7, this.rainingStrength));
         this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(8, this.thunderingStrength));
      }
   }

   @Override
   protected int getRenderDistanceChunks() {
      return this.mcServer.getConfigurationManager().getViewDistance();
   }

   public MinecraftServer getMinecraftServer() {
      return this.mcServer;
   }

   public EntityTracker getEntityTracker() {
      return this.theEntityTracker;
   }

   public PlayerManager getPlayerManager() {
      return this.thePlayerManager;
   }

   public Teleporter getDefaultTeleporter() {
      return this.worldTeleporter;
   }

   public void spawnParticle(
      EnumParticleTypes enumParticleTypes,
      double double2,
      double double3,
      double double4,
      int integer,
      double double6,
      double double7,
      double double8,
      double double9,
      int... arr
   ) {
      this.spawnParticle(enumParticleTypes, false, double2, double3, double4, integer, double6, double7, double8, double9, arr);
   }

   public void spawnParticle(
      EnumParticleTypes enumParticleTypes,
      boolean boolean2,
      double double3,
      double double4,
      double double5,
      int integer,
      double double7,
      double double8,
      double double9,
      double double10,
      int... arr
   ) {
      S2APacketParticles var22 = new S2APacketParticles(
         enumParticleTypes,
         boolean2,
         (float)double3,
         (float)double4,
         (float)double5,
         (float)double7,
         (float)double8,
         (float)double9,
         (float)double10,
         integer,
         arr
      );

      for (EntityPlayer var25_player : this.playerEntities) {
         EntityPlayerMP var25 = (EntityPlayerMP)var25_player;
         double var27 = var25.getPosition().distanceSq(double3, double4, double5);
         if (var27 <= 256.0 || boolean2 && var27 <= 65536.0) {
            var25.playerNetServerHandler.sendPacket(var22);
         }
      }
   }

   public Entity getEntityFromUuid(UUID uUID) {
      return this.entitiesByUuid.get(uUID);
   }

   @Override
   public ListenableFuture<Object> addScheduledTask(Runnable runnable) {
      return this.mcServer.addScheduledTask(runnable);
   }

   @Override
   public boolean isCallingFromMinecraftThread() {
      return this.mcServer.isCallingFromMinecraftThread();
   }

   static class ServerBlockEventList extends ArrayList<BlockEventData> {
      private ServerBlockEventList() {
      }
   }
}
