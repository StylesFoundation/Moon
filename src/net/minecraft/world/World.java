package net.minecraft.world;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.moon.Moon;
import com.moon.events.impl.player.BlockBBEvent;
import com.moon.viamcp.ViaMCP;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.realms.Tezzelator;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;

public abstract class World implements IBlockAccess {
   private int seaLevel = 63;
   protected boolean scheduledUpdatesAreImmediate;
   public final List<Entity> loadedEntityList = Lists.newArrayList();
   protected final List<Entity> unloadedEntityList = Lists.newArrayList();
   public final List<TileEntity> loadedTileEntityList = Lists.newArrayList();
   public final List<TileEntity> tickableTileEntities = Lists.newArrayList();
   private final List<TileEntity> addedTileEntityList = Lists.newArrayList();
   private final List<TileEntity> tileEntitiesToBeRemoved = Lists.newArrayList();
   public final List<EntityPlayer> playerEntities = Lists.newArrayList();
   public final List<Entity> weatherEffects = Lists.newArrayList();
   protected final IntHashMap<Entity> entitiesById = new IntHashMap<>();
   private final long l = 16777215L;
   private int skylightSubtracted;
   protected int updateLCG = new Random().nextInt();
   protected final int o = 1013904223;
   protected float prevRainingStrength;
   protected float rainingStrength;
   protected float prevThunderingStrength;
   protected float thunderingStrength;
   private int lastLightningBolt;
   public final Random rand = new Random();
   public final WorldProvider provider;
   protected List<IWorldAccess> worldAccesses = Lists.newArrayList();
   protected IChunkProvider chunkProvider;
   protected final ISaveHandler saveHandler;
   protected WorldInfo worldInfo;
   protected boolean findingSpawnPoint;
   protected MapStorage mapStorage;
   protected VillageCollection villageCollectionObj;
   public final Profiler theProfiler;
   private final Calendar theCalendar = Calendar.getInstance();
   protected Scoreboard worldScoreboard = new Scoreboard();
   public final boolean isRemote;
   protected Set<ChunkCoordIntPair> activeChunkSet = Sets.newHashSet();
   private int ambientTickCountdown;
   protected boolean spawnHostileMobs;
   protected boolean spawnPeacefulMobs;
   private boolean processingLoadedTiles;
   private final WorldBorder worldBorder;
   int[] lightUpdateBlockList;

   protected World(ISaveHandler iSaveHandler, WorldInfo worldInfo, WorldProvider worldProvider, Profiler profiler, boolean boolean5) {
      this.ambientTickCountdown = this.rand.nextInt(12000);
      this.spawnHostileMobs = true;
      this.spawnPeacefulMobs = true;
      this.lightUpdateBlockList = new int[32768];
      this.saveHandler = iSaveHandler;
      this.theProfiler = profiler;
      this.worldInfo = worldInfo;
      this.provider = worldProvider;
      this.isRemote = boolean5;
      this.worldBorder = worldProvider.getWorldBorder();
   }

   public World init() {
      return this;
   }

   @Override
   public BiomeGenBase getBiomeGenForCoords(BlockPos blockPos) {
      if (this.Method322(blockPos)) {
         Chunk var5 = this.getChunkFromBlockCoords(blockPos);

         try {
            return var5.getBiome(blockPos, this.provider.getWorldChunkManager());
         } catch (Throwable var8) {
            CrashReport var7 = CrashReport.makeCrashReport(var8, "Getting biome");
            var7.makeCategory("Coordinates of biome request").addCrashSectionCallable("Location", () -> CrashReportCategory.getCoordinateInfo(blockPos));
            throw new ReportedException(var7);
         }
      } else {
         return this.provider.getWorldChunkManager().getBiomeGenerator(blockPos, BiomeGenBase.plains);
      }
   }

   public WorldChunkManager getWorldChunkManager() {
      return this.provider.getWorldChunkManager();
   }

   protected abstract IChunkProvider createChunkProvider();

   public void initialize(WorldSettings worldSettings) {
      this.worldInfo.setServerInitialized(true);
   }

   public void setInitialSpawnLocation() {
      this.setSpawnPoint(new BlockPos(8, 64, 8));
   }

   public Block getGroundAboveSeaLevel(BlockPos blockPos) {
      BlockPos var5 = new BlockPos(blockPos.getX(), this.getSeaLevel(), blockPos.getZ());

      while (!this.isAirBlock(var5.up())) {
         var5 = var5.up();
      }

      return this.getBlockState(var5).getBlock();
   }

   private boolean Method321(BlockPos blockPos) {
      return blockPos.getX() >= -30000000
         && blockPos.getZ() >= -30000000
         && blockPos.getX() < 30000000
         && blockPos.getZ() < 30000000
         && blockPos.getY() >= 0
         && blockPos.getY() < 256;
   }

   @Override
   public boolean isAirBlock(BlockPos blockPos) {
      return this.getBlockState(blockPos).getBlock().getMaterial() == Material.air;
   }

   public boolean Method322(BlockPos blockPos) {
      return this.Method323(blockPos, true);
   }

   public boolean Method323(BlockPos blockPos, boolean boolean2) {
      return this.Method321(blockPos) && this.isChunkLoaded(blockPos.getX() >> 4, blockPos.getZ() >> 4, boolean2);
   }

   public boolean Method324(BlockPos blockPos, int integer) {
      return this.Method325(blockPos, integer, true);
   }

   public boolean Method325(BlockPos blockPos, int integer, boolean boolean3) {
      return this.isAreaLoaded(
         blockPos.getX() - integer,
         blockPos.getY() - integer,
         blockPos.getZ() - integer,
         blockPos.getX() + integer,
         blockPos.getY() + integer,
         blockPos.getZ() + integer,
         boolean3
      );
   }

   public boolean Method326(BlockPos blockPos1, BlockPos blockPos2) {
      return this.Method327(blockPos1, blockPos2, true);
   }

   public boolean Method327(BlockPos blockPos1, BlockPos blockPos2, boolean boolean3) {
      return this.isAreaLoaded(blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ(), boolean3);
   }

   public boolean Method328(StructureBoundingBox structureBoundingBox) {
      return this.isAreaLoaded(structureBoundingBox, true);
   }

   public boolean isAreaLoaded(StructureBoundingBox structureBoundingBox, boolean boolean2) {
      return this.isAreaLoaded(
         structureBoundingBox.minX,
         structureBoundingBox.minY,
         structureBoundingBox.minZ,
         structureBoundingBox.maxX,
         structureBoundingBox.maxY,
         structureBoundingBox.maxZ,
         boolean2
      );
   }

   private boolean isAreaLoaded(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, boolean boolean7) {
      if (integer5 >= 0 && integer2 < 256) {
         integer1 >>= 4;
         integer3 >>= 4;
         integer4 >>= 4;
         integer6 >>= 4;

         for (int var11 = integer1; var11 <= integer4; var11++) {
            for (int var12 = integer3; var12 <= integer6; var12++) {
               if (!this.isChunkLoaded(var11, var12, boolean7)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean isChunkLoaded(int integer1, int integer2, boolean boolean3) {
      return this.chunkProvider.chunkExists(integer1, integer2) && (boolean3 || !this.chunkProvider.provideChunk(integer1, integer2).isEmpty());
   }

   public Chunk getChunkFromBlockCoords(BlockPos blockPos) {
      return this.getChunkFromChunkCoords(blockPos.getX() >> 4, blockPos.getZ() >> 4);
   }

   public Chunk getChunkFromChunkCoords(int integer1, int integer2) {
      return this.chunkProvider.provideChunk(integer1, integer2);
   }

   public boolean setBlockState(BlockPos blockPos, IBlockState iBlockState, int integer) {
      if (!this.Method321(blockPos)) {
         return false;
      }

      if (!this.isRemote && this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
         return false;
      }

      Chunk var8 = this.getChunkFromBlockCoords(blockPos);
      Block var9 = iBlockState.getBlock();
      IBlockState var10 = var8.setBlockState(blockPos, iBlockState);
      if (var10 == null) {
         return false;
      }

      Block var11 = var10.getBlock();
      if (var9.getLightOpacity() != var11.getLightOpacity() || var9.getLightValue() != var11.getLightValue()) {
         this.theProfiler.startSection("checkLight");
         this.checkLight(blockPos);
         this.theProfiler.endSection();
      }

      if ((integer & 2) != 0 && (!this.isRemote || (integer & 4) == 0) && var8.isPopulated()) {
         this.markBlockForUpdate(blockPos);
      }

      if (!this.isRemote && (integer & 1) != 0) {
         this.notifyNeighborsRespectDebug(blockPos, var10.getBlock());
         if (var9.hasComparatorInputOverride()) {
            this.updateComparatorOutputLevel(blockPos, var9);
         }
      }

      return true;
   }

   public boolean setBlockToAir(BlockPos blockPos) {
      return this.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
   }

   public boolean Method329(BlockPos blockPos, boolean boolean2) {
      IBlockState var7 = this.getBlockState(blockPos);
      Block var8 = var7.getBlock();
      if (ViaMCP.getInstance().getVersion() != 47) {
         this.playAuxSFX(2001, blockPos, Block.getStateId(var7));
      }

      if (var8.getMaterial() == Material.air) {
         return false;
      }

      if (ViaMCP.getInstance().getVersion() == 47) {
         this.playAuxSFX(2001, blockPos, Block.getStateId(var7));
      }

      if (boolean2) {
         var8.dropBlockAsItem(this, blockPos, var7, 0);
      }

      return this.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
   }

   public boolean setBlockState(BlockPos blockPos, IBlockState iBlockState) {
      return this.setBlockState(blockPos, iBlockState, 3);
   }

   public void markBlockForUpdate(BlockPos blockPos) {
      Iterator var5 = this.worldAccesses.iterator();

      while (var5.hasNext()) {
         ((IWorldAccess)var5.next()).markBlockForUpdate(blockPos);
      }
   }

   public void notifyNeighborsRespectDebug(BlockPos blockPos, Block block) {
      if (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
         this.notifyNeighborsOfStateChange(blockPos, block);
      }
   }

   public void markBlocksDirtyVertical(int integer1, int integer2, int integer3, int integer4) {
      if (integer3 > integer4) {
         int var8 = integer4;
         integer4 = integer3;
         integer3 = var8;
      }

      if (!this.provider.getHasNoSky()) {
         for (int var9 = integer3; var9 <= integer4; var9++) {
            this.checkLightFor(EnumSkyBlock.SKY, new BlockPos(integer1, var9, integer2));
         }
      }

      this.markBlockRangeForRenderUpdate(integer1, integer3, integer2, integer1, integer4, integer2);
   }

   public void markBlockRangeForRenderUpdate(BlockPos blockPos1, BlockPos blockPos2) {
      this.markBlockRangeForRenderUpdate(blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
   }

   public void markBlockRangeForRenderUpdate(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      Iterator var10 = this.worldAccesses.iterator();

      while (var10.hasNext()) {
         ((IWorldAccess)var10.next()).markBlockRangeForRenderUpdate(integer1, integer2, integer3, integer4, integer5, integer6);
      }
   }

   public void notifyNeighborsOfStateChange(BlockPos blockPos, Block block) {
      this.notifyBlockOfStateChange(blockPos.west(), block);
      this.notifyBlockOfStateChange(blockPos.east(), block);
      this.notifyBlockOfStateChange(blockPos.down(), block);
      this.notifyBlockOfStateChange(blockPos.up(), block);
      this.notifyBlockOfStateChange(blockPos.north(), block);
      this.notifyBlockOfStateChange(blockPos.south(), block);
   }

   public void notifyNeighborsOfStateExcept(BlockPos blockPos, Block block, EnumFacing enumFacing) {
      if (enumFacing != EnumFacing.WEST) {
         this.notifyBlockOfStateChange(blockPos.west(), block);
      }

      if (enumFacing != EnumFacing.EAST) {
         this.notifyBlockOfStateChange(blockPos.east(), block);
      }

      if (enumFacing != EnumFacing.DOWN) {
         this.notifyBlockOfStateChange(blockPos.down(), block);
      }

      if (enumFacing != EnumFacing.UP) {
         this.notifyBlockOfStateChange(blockPos.up(), block);
      }

      if (enumFacing != EnumFacing.NORTH) {
         this.notifyBlockOfStateChange(blockPos.north(), block);
      }

      if (enumFacing != EnumFacing.SOUTH) {
         this.notifyBlockOfStateChange(blockPos.south(), block);
      }
   }

   public void notifyBlockOfStateChange(BlockPos blockPos, Block block) {
      if (!this.isRemote) {
         IBlockState var6 = this.getBlockState(blockPos);

         try {
            var6.getBlock().onNeighborBlockChange(this, blockPos, var6, block);
         } catch (Throwable var10) {
            CrashReport var8 = CrashReport.makeCrashReport(var10, "Exception while updating neighbours");
            CrashReportCategory var9 = var8.makeCategory("Block being updated");
            var9.addCrashSectionCallable("Source block type", () -> {
               try {
                  return String.format("ID #%d (%s // %s)", Block.getIdFromBlock(block), block.Method440(), block.getClass().getCanonicalName());
               } catch (Throwable var1) {
                  return "ID #" + Block.getIdFromBlock(block);
               }
            });
            CrashReportCategory.addBlockInfo(var9, blockPos, var6);
            throw new ReportedException(var8);
         }
      }
   }

   public boolean isBlockTickPending(BlockPos blockPos, Block block) {
      return false;
   }

   public boolean canSeeSky(BlockPos blockPos) {
      return this.getChunkFromBlockCoords(blockPos).canSeeSky(blockPos);
   }

   public boolean canBlockSeeSky(BlockPos blockPos) {
      if (blockPos.getY() >= this.getSeaLevel()) {
         return this.canSeeSky(blockPos);
      }

      BlockPos var5 = new BlockPos(blockPos.getX(), this.getSeaLevel(), blockPos.getZ());
      if (!this.canSeeSky(var5)) {
         return false;
      }

      for (BlockPos var7 = var5.down(); var7.getY() > blockPos.getY(); var7 = var7.down()) {
         Block var6 = this.getBlockState(var7).getBlock();
         if (var6.getLightOpacity() > 0 && !var6.getMaterial().isLiquid()) {
            return false;
         }
      }

      return true;
   }

   public int getLight(BlockPos blockPos) {
      if (blockPos.getY() < 0) {
         return 0;
      }

      if (blockPos.getY() >= 256) {
         blockPos = new BlockPos(blockPos.getX(), 255, blockPos.getZ());
      }

      return this.getChunkFromBlockCoords(blockPos).getLightSubtracted(blockPos, 0);
   }

   public int getLightFromNeighbors(BlockPos blockPos) {
      return this.getLight(blockPos, true);
   }

   public int getLight(BlockPos blockPos, boolean boolean2) {
      if (blockPos.getX() < -30000000 || blockPos.getZ() < -30000000 || blockPos.getX() >= 30000000 || blockPos.getZ() >= 30000000) {
         return 15;
      }

      if (boolean2 && this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
         int var11 = this.getLight(blockPos.up(), false);
         int var7 = this.getLight(blockPos.east(), false);
         int var8 = this.getLight(blockPos.west(), false);
         int var9 = this.getLight(blockPos.south(), false);
         int var10 = this.getLight(blockPos.north(), false);
         if (var7 > var11) {
            var11 = var7;
         }

         if (var8 > var11) {
            var11 = var8;
         }

         if (var9 > var11) {
            var11 = var9;
         }

         if (var10 > var11) {
            var11 = var10;
         }

         return var11;
      } else {
         if (blockPos.getY() < 0) {
            return 0;
         }

         if (blockPos.getY() >= 256) {
            blockPos = new BlockPos(blockPos.getX(), 255, blockPos.getZ());
         }

         Chunk var6 = this.getChunkFromBlockCoords(blockPos);
         return var6.getLightSubtracted(blockPos, this.skylightSubtracted);
      }
   }

   public BlockPos getHeight(BlockPos blockPos) {
      int var5;
      if (blockPos.getX() >= -30000000 && blockPos.getZ() >= -30000000 && blockPos.getX() < 30000000 && blockPos.getZ() < 30000000) {
         if (this.isChunkLoaded(blockPos.getX() >> 4, blockPos.getZ() >> 4, true)) {
            var5 = this.getChunkFromChunkCoords(blockPos.getX() >> 4, blockPos.getZ() >> 4).getHeightValue(blockPos.getX() & 15, blockPos.getZ() & 15);
         } else {
            var5 = 0;
         }
      } else {
         var5 = this.getSeaLevel() + 1;
      }

      return new BlockPos(blockPos.getX(), var5, blockPos.getZ());
   }

   public int getChunksLowestHorizon(int integer1, int integer2) {
      if (integer1 >= -30000000 && integer2 >= -30000000 && integer1 < 30000000 && integer2 < 30000000) {
         return !this.isChunkLoaded(integer1 >> 4, integer2 >> 4, true) ? 0 : this.getChunkFromChunkCoords(integer1 >> 4, integer2 >> 4).getLowestHeight();
      } else {
         return this.getSeaLevel() + 1;
      }
   }

   public int getLightFromNeighborsFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
      if (this.provider.getHasNoSky() && enumSkyBlock == EnumSkyBlock.SKY) {
         return 0;
      }

      if (blockPos.getY() < 0) {
         blockPos = new BlockPos(blockPos.getX(), 0, blockPos.getZ());
      }

      if (!this.Method321(blockPos)) {
         return enumSkyBlock.defaultLightValue;
      }

      if (!this.Method322(blockPos)) {
         return enumSkyBlock.defaultLightValue;
      }

      if (this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
         int var6 = this.getLightFor(enumSkyBlock, blockPos.up());
         int var7 = this.getLightFor(enumSkyBlock, blockPos.east());
         int var8 = this.getLightFor(enumSkyBlock, blockPos.west());
         int var9 = this.getLightFor(enumSkyBlock, blockPos.south());
         int var10 = this.getLightFor(enumSkyBlock, blockPos.north());
         if (var7 > var6) {
            var6 = var7;
         }

         if (var8 > var6) {
            var6 = var8;
         }

         if (var9 > var6) {
            var6 = var9;
         }

         if (var10 > var6) {
            var6 = var10;
         }

         return var6;
      } else {
         return this.getChunkFromBlockCoords(blockPos).getLightFor(enumSkyBlock, blockPos);
      }
   }

   public int getLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
      if (blockPos.getY() < 0) {
         blockPos = new BlockPos(blockPos.getX(), 0, blockPos.getZ());
      }

      if (!this.Method321(blockPos)) {
         return enumSkyBlock.defaultLightValue;
      } else {
         return !this.Method322(blockPos) ? enumSkyBlock.defaultLightValue : this.getChunkFromBlockCoords(blockPos).getLightFor(enumSkyBlock, blockPos);
      }
   }

   public void setLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos, int integer) {
      if (this.Method321(blockPos) && this.Method322(blockPos)) {
         this.getChunkFromBlockCoords(blockPos).setLightFor(enumSkyBlock, blockPos, integer);
         this.notifyLightSet(blockPos);
      }
   }

   public void notifyLightSet(BlockPos blockPos) {
      Iterator var5 = this.worldAccesses.iterator();

      while (var5.hasNext()) {
         ((IWorldAccess)var5.next()).notifyLightSet(blockPos);
      }
   }

   @Override
   public int getCombinedLight(BlockPos blockPos, int integer) {
      int var6 = this.getLightFromNeighborsFor(EnumSkyBlock.SKY, blockPos);
      int var7 = this.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, blockPos);
      if (var7 < integer) {
         var7 = integer;
      }

      return var6 << 20 | var7 << 4;
   }

   public float getLightBrightness(BlockPos blockPos) {
      return this.provider.getLightBrightnessTable()[this.getLightFromNeighbors(blockPos)];
   }

   @Override
   public IBlockState getBlockState(BlockPos blockPos) {
      return !this.Method321(blockPos) ? Blocks.air.getDefaultState() : this.getChunkFromBlockCoords(blockPos).getBlockState(blockPos);
   }

   public boolean isDaytime() {
      return this.skylightSubtracted < 4;
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 vec31, Vec3 vec32) {
      return this.rayTraceBlocks(vec31, vec32, false, false, false);
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 vec31, Vec3 vec32, boolean boolean3) {
      return this.rayTraceBlocks(vec31, vec32, boolean3, false, false);
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 vec31, Vec3 vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
      if (Double.isNaN(vec31.xCoord) || Double.isNaN(vec31.yCoord) || Double.isNaN(vec31.zCoord)) {
         return null;
      }

      if (!Double.isNaN(vec32.xCoord) && !Double.isNaN(vec32.yCoord) && !Double.isNaN(vec32.zCoord)) {
         int var9 = MathHelper.floor_double(vec32.xCoord);
         int var10 = MathHelper.floor_double(vec32.yCoord);
         int var11 = MathHelper.floor_double(vec32.zCoord);
         int var12 = MathHelper.floor_double(vec31.xCoord);
         int var13 = MathHelper.floor_double(vec31.yCoord);
         int var14 = MathHelper.floor_double(vec31.zCoord);
         BlockPos var15 = new BlockPos(var12, var13, var14);
         IBlockState var16 = this.getBlockState(var15);
         Block var17 = var16.getBlock();
         if ((!ignoreBlockWithoutBoundingBox || var17.getCollisionBoundingBox(this, var15, var16) != null) && var17.canCollideCheck(var16, stopOnLiquid)) {
            MovingObjectPosition var18 = var17.collisionRayTrace(this, var15, vec31, vec32);
            if (var18 != null) {
               return var18;
            }
         }

         MovingObjectPosition var46 = null;
         int var19 = 200;

         while (var19-- >= 0) {
            if (Double.isNaN(vec31.xCoord) || Double.isNaN(vec31.yCoord) || Double.isNaN(vec31.zCoord)) {
               return null;
            }

            if (var12 == var9 && var13 == var10 && var14 == var11) {
               return returnLastUncollidableBlock ? var46 : null;
            }

            boolean var20 = true;
            boolean var21 = true;
            boolean var22 = true;
            double var23 = 999.0;
            double var25 = 999.0;
            double var27 = 999.0;
            if (var9 > var12) {
               var23 = var12 + 1.0;
            } else if (var9 < var12) {
               var23 = var12 + 0.0;
            } else {
               var20 = false;
            }

            if (var10 > var13) {
               var25 = var13 + 1.0;
            } else if (var10 < var13) {
               var25 = var13 + 0.0;
            } else {
               var21 = false;
            }

            if (var11 > var14) {
               var27 = var14 + 1.0;
            } else if (var11 < var14) {
               var27 = var14 + 0.0;
            } else {
               var22 = false;
            }

            double var29 = 999.0;
            double var31 = 999.0;
            double var33 = 999.0;
            double var35 = vec32.xCoord - vec31.xCoord;
            double var37 = vec32.yCoord - vec31.yCoord;
            double var39 = vec32.zCoord - vec31.zCoord;
            if (var20) {
               var29 = (var23 - vec31.xCoord) / var35;
            }

            if (var21) {
               var31 = (var25 - vec31.yCoord) / var37;
            }

            if (var22) {
               var33 = (var27 - vec31.zCoord) / var39;
            }

            if (var29 == -0.0) {
               var29 = -1.0E-4;
            }

            if (var31 == -0.0) {
               var31 = -1.0E-4;
            }

            if (var33 == -0.0) {
               var33 = -1.0E-4;
            }

            EnumFacing var41;
            if (var29 < var31 && var29 < var33) {
               var41 = var9 > var12 ? EnumFacing.WEST : EnumFacing.EAST;
               vec31 = new Vec3(var23, vec31.yCoord + var37 * var29, vec31.zCoord + var39 * var29);
            } else if (var31 < var33) {
               var41 = var10 > var13 ? EnumFacing.DOWN : EnumFacing.UP;
               vec31 = new Vec3(vec31.xCoord + var35 * var31, var25, vec31.zCoord + var39 * var31);
            } else {
               var41 = var11 > var14 ? EnumFacing.NORTH : EnumFacing.SOUTH;
               vec31 = new Vec3(vec31.xCoord + var35 * var33, vec31.yCoord + var37 * var33, var27);
            }

            var12 = MathHelper.floor_double(vec31.xCoord) - (var41 == EnumFacing.EAST ? 1 : 0);
            var13 = MathHelper.floor_double(vec31.yCoord) - (var41 == EnumFacing.UP ? 1 : 0);
            var14 = MathHelper.floor_double(vec31.zCoord) - (var41 == EnumFacing.SOUTH ? 1 : 0);
            var15 = new BlockPos(var12, var13, var14);
            IBlockState var42 = this.getBlockState(var15);
            Block var43 = var42.getBlock();
            if (!ignoreBlockWithoutBoundingBox || var43.getCollisionBoundingBox(this, var15, var42) != null) {
               if (var43.canCollideCheck(var42, stopOnLiquid)) {
                  MovingObjectPosition var44 = var43.collisionRayTrace(this, var15, vec31, vec32);
                  if (var44 != null) {
                     return var44;
                  }
               } else {
                  var46 = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec31, var41, var15);
               }
            }
         }

         return returnLastUncollidableBlock ? var46 : null;
      } else {
         return null;
      }
   }

   public void playSoundAtEntity(Entity entity, String string, float float3, float float4) {
      Iterator var8 = this.worldAccesses.iterator();

      while (var8.hasNext()) {
         ((IWorldAccess)var8.next()).playSound(string, entity.posX, entity.posY, entity.posZ, float3, float4);
      }
   }

   public void playSoundToNearExcept(EntityPlayer entityPlayer, String string, float float3, float float4) {
      Iterator var8 = this.worldAccesses.iterator();

      while (var8.hasNext()) {
         ((IWorldAccess)var8.next()).playSoundToNearExcept(entityPlayer, string, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, float3, float4);
      }
   }

   public void playSoundEffect(double double1, double double2, double double3, String string, float float5, float float6) {
      Iterator var13 = this.worldAccesses.iterator();

      while (var13.hasNext()) {
         ((IWorldAccess)var13.next()).playSound(string, double1, double2, double3, float5, float6);
      }
   }

   public void playSound(double double1, double double2, double double3, String string, float float5, float float6, boolean boolean7) {
   }

   public void playRecord(BlockPos blockPos, String string) {
      Iterator var6 = this.worldAccesses.iterator();

      while (var6.hasNext()) {
         ((IWorldAccess)var6.next()).playRecord(string, blockPos);
      }
   }

   public void spawnParticle(
      EnumParticleTypes enumParticleTypes, double double2, double double3, double double4, double double5, double double6, double double7, int... arr
   ) {
      this.spawnParticle(enumParticleTypes.getParticleID(), enumParticleTypes.getShouldIgnoreRange(), double2, double3, double4, double5, double6, double7, arr);
   }

   public void spawnParticle(
      EnumParticleTypes enumParticleTypes,
      boolean boolean2,
      double double3,
      double double4,
      double double5,
      double double6,
      double double7,
      double double8,
      int... arr
   ) {
      this.spawnParticle(
         enumParticleTypes.getParticleID(), enumParticleTypes.getShouldIgnoreRange() | boolean2, double3, double4, double5, double6, double7, double8, arr
      );
   }

   private void spawnParticle(
      int integer, boolean boolean2, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
   ) {
      Iterator var19 = this.worldAccesses.iterator();

      while (var19.hasNext()) {
         ((IWorldAccess)var19.next()).spawnParticle(integer, boolean2, double3, double4, double5, double6, double7, double8, arr);
      }
   }

   public boolean addWeatherEffect(Entity entity) {
      this.weatherEffects.add(entity);
      return true;
   }

   public boolean spawnEntityInWorld(Entity entity) {
      int var5 = MathHelper.floor_double(entity.posX / 16.0);
      int var6 = MathHelper.floor_double(entity.posZ / 16.0);
      boolean var7 = entity.forceSpawn;
      if (entity instanceof EntityPlayer) {
         var7 = true;
      }

      if (!var7 && !this.isChunkLoaded(var5, var6, true)) {
         return false;
      }

      if (entity instanceof EntityPlayer var8) {
         this.playerEntities.add(var8);
         this.updateAllPlayersSleepingFlag();
      }

      this.getChunkFromChunkCoords(var5, var6).addEntity(entity);
      this.loadedEntityList.add(entity);
      this.onEntityAdded(entity);
      return true;
   }

   protected void onEntityAdded(Entity entity) {
      Iterator var5 = this.worldAccesses.iterator();

      while (var5.hasNext()) {
         ((IWorldAccess)var5.next()).onEntityAdded(entity);
      }
   }

   protected void onEntityRemoved(Entity entity) {
      Iterator var5 = this.worldAccesses.iterator();

      while (var5.hasNext()) {
         ((IWorldAccess)var5.next()).onEntityRemoved(entity);
      }
   }

   public void removeEntity(Entity entity) {
      if (entity.riddenByEntity != null) {
         entity.riddenByEntity.mountEntity(null);
      }

      if (entity.ridingEntity != null) {
         entity.mountEntity(null);
      }

      entity.setDead();
      if (entity instanceof EntityPlayer) {
         this.playerEntities.remove(entity);
         this.updateAllPlayersSleepingFlag();
         this.onEntityRemoved(entity);
      }
   }

   public void removePlayerEntityDangerously(Entity entity) {
      entity.setDead();
      if (entity instanceof EntityPlayer) {
         this.playerEntities.remove(entity);
         this.updateAllPlayersSleepingFlag();
      }

      int var5 = entity.W;
      int var6 = entity.Y;
      if (entity.addedToChunk && this.isChunkLoaded(var5, var6, true)) {
         this.getChunkFromChunkCoords(var5, var6).removeEntity(entity);
      }

      this.loadedEntityList.remove(entity);
      this.onEntityRemoved(entity);
   }

   public void addWorldAccess(IWorldAccess iWorldAccess) {
      this.worldAccesses.add(iWorldAccess);
   }

   public void removeWorldAccess(IWorldAccess iWorldAccess) {
      this.worldAccesses.remove(iWorldAccess);
   }

   public List<AxisAlignedBB> getCollidingBoundingBoxes(Entity entity, AxisAlignedBB axisAlignedBB) {
      List var6 = Lists.newArrayList();
      int var7 = MathHelper.floor_double(axisAlignedBB.minX);
      int var8 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
      int var9 = MathHelper.floor_double(axisAlignedBB.minY);
      int var10 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
      int var11 = MathHelper.floor_double(axisAlignedBB.minZ);
      int var12 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
      WorldBorder var13 = this.getWorldBorder();
      boolean var14 = entity.isOutsideBorder();
      boolean var15 = this.isInsideBorder(var13, entity);
      IBlockState var16 = Blocks.stone.getDefaultState();
      BlockPos.MutableBlockPos var17 = new BlockPos.MutableBlockPos();

      for (int var18 = var7; var18 < var8; var18++) {
         for (int var19 = var11; var19 < var12; var19++) {
            if (this.Method322(var17.set(var18, 64, var19))) {
               for (int var20 = var9 - 1; var20 < var10; var20++) {
                  var17.set(var18, var20, var19);
                  if (var14 && var15) {
                     entity.setOutsideBorder(false);
                  } else if (!var14 && !var15) {
                     entity.setOutsideBorder(true);
                  }

                  IBlockState var21 = var16;
                  if (var13.contains(var17) || !var15) {
                     var21 = this.getBlockState(var17);
                  }

                  if (entity instanceof EntityPlayerSP) {
                     BlockBBEvent var22 = new BlockBBEvent(var16.getBlock(), var17, var6);
                     Moon.getInstance().getEventBus().publish(var22);
                     var17 = var22.getBlockPos();
                     var6 = var22.getBoundingBox();
                  }

                  var21.getBlock().addCollisionBoxesToList(this, var17, var21, axisAlignedBB, var6, entity);
               }
            }
         }
      }

      List var23 = this.getEntitiesWithinAABBExcludingEntity(entity, axisAlignedBB.expand(0.25, 0.25, 0.25));

      for (int var24 = 0; var24 < var23.size(); var24++) {
         if (entity.riddenByEntity != var23 && entity.ridingEntity != var23) {
            AxisAlignedBB var25 = ((Entity)var23.get(var24)).getCollisionBoundingBox();
            if (var25 != null && var25.intersectsWith(axisAlignedBB)) {
               var6.add(var25);
            }

            var25 = entity.getCollisionBox((Entity)var23.get(var24));
            if (var25 != null && var25.intersectsWith(axisAlignedBB)) {
               var6.add(var25);
            }
         }
      }

      return var6;
   }

   public boolean isInsideBorder(WorldBorder worldBorder, Entity entity) {
      double var6 = worldBorder.minX();
      double var8 = worldBorder.minZ();
      double var10 = worldBorder.maxX();
      double var12 = worldBorder.maxZ();
      if (entity.isOutsideBorder()) {
         var6++;
         var8++;
         var10--;
         var12--;
      } else {
         var6--;
         var8--;
         var10++;
         var12++;
      }

      return entity.posX > var6 && entity.posX < var10 && entity.posZ > var8 && entity.posZ < var12;
   }

   public List<AxisAlignedBB> getCollisionBoxes(AxisAlignedBB axisAlignedBB) {
      ArrayList var5 = Lists.newArrayList();
      int var6 = MathHelper.floor_double(axisAlignedBB.minX);
      int var7 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
      int var8 = MathHelper.floor_double(axisAlignedBB.minY);
      int var9 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
      int var10 = MathHelper.floor_double(axisAlignedBB.minZ);
      int var11 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
      BlockPos.MutableBlockPos var12 = new BlockPos.MutableBlockPos();

      for (int var13 = var6; var13 < var7; var13++) {
         for (int var14 = var10; var14 < var11; var14++) {
            if (this.Method322(var12.set(var13, 64, var14))) {
               for (int var15 = var8 - 1; var15 < var9; var15++) {
                  var12.set(var13, var15, var14);
                  IBlockState var16;
                  if (var13 >= -30000000 && var13 < 30000000 && var14 >= -30000000 && var14 < 30000000) {
                     var16 = this.getBlockState(var12);
                  } else {
                     var16 = Blocks.bedrock.getDefaultState();
                  }

                  var16.getBlock().addCollisionBoxesToList(this, var12, var16, axisAlignedBB, var5, null);
               }
            }
         }
      }

      return var5;
   }

   public int calculateSkylightSubtracted(float float1) {
      float var2 = this.getCelestialAngle(float1);
      float var3 = MathHelper.clamp_float(1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.5F), 0.0F, 1.0F);
      var3 = (float)((float)((1.0F - var3) * (1.0 - this.getRainStrength(float1) * 5.0F / 16.0)) * (1.0 - this.getThunderStrength(float1) * 5.0F / 16.0));
      return (int)((1.0F - var3) * 11.0F);
   }

   public float getSunBrightness(float float1) {
      float var2 = this.getCelestialAngle(float1);
      float var3 = MathHelper.clamp_float(1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.2F), 0.0F, 1.0F);
      return (float)((float)((1.0F - var3) * (1.0 - this.getRainStrength(float1) * 5.0F / 16.0)) * (1.0 - this.getThunderStrength(float1) * 5.0F / 16.0))
            * 0.8F
         + 0.2F;
   }

   public Vec3 getSkyColor(Entity entity, float float2) {
      float var7 = MathHelper.clamp_float(MathHelper.cos(this.getCelestialAngle(float2) * (float) Math.PI * 2.0F) * 2.0F + 0.5F, 0.0F, 1.0F);
      int var8 = MathHelper.floor_double(entity.posX);
      int var9 = MathHelper.floor_double(entity.posY);
      int var10 = MathHelper.floor_double(entity.posZ);
      BlockPos var11 = new BlockPos(var8, var9, var10);
      BiomeGenBase var12 = this.getBiomeGenForCoords(var11);
      float var13 = var12.getFloatTemperature(var11);
      int var14 = var12.getSkyColorByTemp(var13);
      float var15 = (var14 >> 16 & 0xFF) / 255.0F;
      float var16 = (var14 >> 8 & 0xFF) / 255.0F;
      float var17 = (var14 & 0xFF) / 255.0F;
      var15 *= var7;
      var16 *= var7;
      var17 *= var7;
      float var18 = this.getRainStrength(float2);
      if (var18 > 0.0F) {
         float var19 = (var15 * 0.3F + var16 * 0.59F + var17 * 0.11F) * 0.6F;
         float var20 = 1.0F - var18 * 0.75F;
         var15 = var15 * var20 + var19 * (1.0F - var20);
         var16 = var16 * var20 + var19 * (1.0F - var20);
         var17 = var17 * var20 + var19 * (1.0F - var20);
      }

      float var25 = this.getThunderStrength(float2);
      if (var25 > 0.0F) {
         float var26 = (var15 * 0.3F + var16 * 0.59F + var17 * 0.11F) * 0.2F;
         float var21 = 1.0F - var25 * 0.75F;
         var15 = var15 * var21 + var26 * (1.0F - var21);
         var16 = var16 * var21 + var26 * (1.0F - var21);
         var17 = var17 * var21 + var26 * (1.0F - var21);
      }

      if (this.lastLightningBolt > 0) {
         float var27 = this.lastLightningBolt - float2;
         if (var27 > 1.0F) {
            var27 = 1.0F;
         }

         var27 *= 0.45F;
         var15 = var15 * (1.0F - var27) + 0.8F * var27;
         var16 = var16 * (1.0F - var27) + 0.8F * var27;
         var17 = var17 * (1.0F - var27) + var27;
      }

      return new Vec3(var15, var16, var17);
   }

   public float getCelestialAngle(float float1) {
      return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), float1);
   }

   public int getMoonPhase() {
      return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
   }

   public float getCurrentMoonPhaseFactor() {
      return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
   }

   public float getCelestialAngleRadians(float float1) {
      return this.getCelestialAngle(float1) * (float) Math.PI * 2.0F;
   }

   public Vec3 Method330(float float1) {
      float var5 = this.getCelestialAngle(float1);
      float var6 = MathHelper.clamp_float(MathHelper.cos(var5 * (float) Math.PI * 2.0F) * 2.0F + 0.5F, 0.0F, 1.0F);
      float var7 = 1.0F;
      float var8 = 1.0F;
      float var9 = 1.0F;
      float var10 = this.getRainStrength(float1);
      if (var10 > 0.0F) {
         float var11 = 0.6F;
         float var12 = 1.0F - var10 * 0.95F;
         var7 = var7 * var12 + var11 * (1.0F - var12);
         var8 = var8 * var12 + var11 * (1.0F - var12);
         var9 = var9 * var12 + var11 * (1.0F - var12);
      }

      var7 *= var6 * 0.9F + 0.1F;
      var8 *= var6 * 0.9F + 0.1F;
      var9 *= var6 * 0.85F + 0.15F;
      float var17 = this.getThunderStrength(float1);
      if (var17 > 0.0F) {
         float var18 = (var7 * 0.3F + var8 * 0.59F + var9 * 0.11F) * 0.2F;
         float var13 = 1.0F - var17 * 0.95F;
         var7 = var7 * var13 + var18 * (1.0F - var13);
         var8 = var8 * var13 + var18 * (1.0F - var13);
         var9 = var9 * var13 + var18 * (1.0F - var13);
      }

      return new Vec3(var7, var8, var9);
   }

   public Vec3 getFogColor(float float1) {
      float var2 = this.getCelestialAngle(float1);
      return this.provider.getFogColor(var2, float1);
   }

   public BlockPos getPrecipitationHeight(BlockPos blockPos) {
      return this.getChunkFromBlockCoords(blockPos).getPrecipitationHeight(blockPos);
   }

   public BlockPos getTopSolidOrLiquidBlock(BlockPos blockPos) {
      Chunk var5 = this.getChunkFromBlockCoords(blockPos);
      BlockPos var6 = new BlockPos(blockPos.getX(), var5.getTopFilledSegment() + 16, blockPos.getZ());

      while (var6.getY() >= 0) {
         BlockPos var7 = var6.down();
         Material var8 = var5.getBlock(var7).getMaterial();
         if (var8.blocksMovement() && var8 != Material.leaves) {
            break;
         }

         var6 = var7;
      }

      return var6;
   }

   public float getStarBrightness(float float1) {
      float var2 = this.getCelestialAngle(float1);
      float var3 = MathHelper.clamp_float(1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F), 0.0F, 1.0F);
      return var3 * var3 * 0.5F;
   }

   public void scheduleUpdate(BlockPos blockPos, Block block, int integer) {
   }

   public void updateBlockTick(BlockPos blockPos, Block block, int integer3, int integer4) {
   }

   public void scheduleBlockUpdate(BlockPos blockPos, Block block, int integer3, int integer4) {
   }

   public void updateEntities() {
      this.theProfiler.startSection("entities");
      this.theProfiler.startSection("global");

      for (int var4 = 0; var4 < this.weatherEffects.size(); var4++) {
         Entity var5 = this.weatherEffects.get(var4);

         try {
            var5.ticksExisted++;
            var5.onUpdate();
         } catch (Throwable var12) {
            CrashReport var7 = CrashReport.makeCrashReport(var12, "Ticking entity");
            CrashReportCategory var8 = var7.makeCategory("Entity being ticked");
            if (var5 == null) {
               var8.addCrashSection("Entity", "~~NULL~~");
            } else {
               var5.addEntityCrashInfo(var8);
            }

            throw new ReportedException(var7);
         }

         if (var5.isDead) {
            this.weatherEffects.remove(var4--);
         }
      }

      this.theProfiler.endStartSection("remove");
      this.loadedEntityList.removeAll(this.unloadedEntityList);

      for (Entity var17 : this.unloadedEntityList) {
         int var6 = var17.W;
         int var25 = var17.Y;
         if (var17.addedToChunk && this.isChunkLoaded(var6, var25, true)) {
            this.getChunkFromChunkCoords(var6, var25).removeEntity(var17);
         }
      }

      for (Entity var18 : this.unloadedEntityList) {
         this.onEntityRemoved(var18);
      }

      this.unloadedEntityList.clear();
      this.theProfiler.endStartSection("regular");

      for (int var15 = 0; var15 < this.loadedEntityList.size(); var15++) {
         Entity var19 = this.loadedEntityList.get(var15);
         if (var19.ridingEntity != null) {
            if (!var19.ridingEntity.isDead && var19.ridingEntity.riddenByEntity == var19) {
               continue;
            }

            var19.ridingEntity.riddenByEntity = null;
            var19.ridingEntity = null;
         }

         this.theProfiler.startSection("tick");
         if (!var19.isDead) {
            try {
               this.updateEntity(var19);
            } catch (Throwable var11) {
               CrashReport var26 = CrashReport.makeCrashReport(var11, "Ticking entity");
               CrashReportCategory var28 = var26.makeCategory("Entity being ticked");
               var19.addEntityCrashInfo(var28);
               throw new ReportedException(var26);
            }
         }

         this.theProfiler.endSection();
         this.theProfiler.startSection("remove");
         if (var19.isDead) {
            int var22 = var19.W;
            int var27 = var19.Y;
            if (var19.addedToChunk && this.isChunkLoaded(var22, var27, true)) {
               this.getChunkFromChunkCoords(var22, var27).removeEntity(var19);
            }

            this.loadedEntityList.remove(var15--);
            this.onEntityRemoved(var19);
         }

         this.theProfiler.endSection();
      }

      this.theProfiler.endStartSection("blockEntities");
      this.processingLoadedTiles = true;
      Iterator var16 = this.tickableTileEntities.iterator();

      while (var16.hasNext()) {
         TileEntity var20 = (TileEntity)var16.next();
         if (!var20.isInvalid() && var20.hasWorldObj()) {
            BlockPos var23 = var20.getPos();
            if (this.Method322(var23) && this.worldBorder.contains(var23)) {
               try {
                  ((ITickable)var20).update();
               } catch (Throwable var10) {
                  CrashReport var29 = CrashReport.makeCrashReport(var10, "Ticking block entity");
                  CrashReportCategory var9 = var29.makeCategory("Block entity being ticked");
                  var20.addInfoToCrashReport(var9);
                  throw new ReportedException(var29);
               }
            }
         }

         if (var20.isInvalid()) {
            var16.remove();
            this.loadedTileEntityList.remove(var20);
            if (this.Method322(var20.getPos())) {
               this.getChunkFromBlockCoords(var20.getPos()).removeTileEntity(var20.getPos());
            }
         }
      }

      this.processingLoadedTiles = false;
      if (!this.tileEntitiesToBeRemoved.isEmpty()) {
         this.tickableTileEntities.removeAll(this.tileEntitiesToBeRemoved);
         this.loadedTileEntityList.removeAll(this.tileEntitiesToBeRemoved);
         this.tileEntitiesToBeRemoved.clear();
      }

      this.theProfiler.endStartSection("pendingBlockEntities");
      if (!this.addedTileEntityList.isEmpty()) {
         for (TileEntity var24 : this.addedTileEntityList) {
            if (!var24.isInvalid()) {
               if (!this.loadedTileEntityList.contains(var24)) {
                  this.addTileEntity(var24);
               }

               if (this.Method322(var24.getPos())) {
                  this.getChunkFromBlockCoords(var24.getPos()).addTileEntity(var24.getPos(), var24);
               }

               this.markBlockForUpdate(var24.getPos());
            }
         }

         this.addedTileEntityList.clear();
      }

      this.theProfiler.endSection();
      this.theProfiler.endSection();
   }

   public boolean addTileEntity(TileEntity tileEntity) {
      boolean var5 = this.loadedTileEntityList.add(tileEntity);
      if (var5 && tileEntity instanceof ITickable) {
         this.tickableTileEntities.add(tileEntity);
      }

      return var5;
   }

   public void addTileEntities(Collection<TileEntity> collection) {
      if (this.processingLoadedTiles) {
         this.addedTileEntityList.addAll(collection);
      } else {
         for (TileEntity var6 : collection) {
            this.loadedTileEntityList.add(var6);
            if (var6 instanceof ITickable) {
               this.tickableTileEntities.add(var6);
            }
         }
      }
   }

   public void updateEntity(Entity entity) {
      this.updateEntityWithOptionalForce(entity, true);
   }

   public void updateEntityWithOptionalForce(Entity entity, boolean boolean2) {
      int var6 = MathHelper.floor_double(entity.posX);
      int var7 = MathHelper.floor_double(entity.posZ);
      byte var8 = 32;
      if (!boolean2 || this.isAreaLoaded(var6 - var8, 0, var7 - var8, var6 + var8, 0, var7 + var8, true)) {
         entity.lastTickPosX = entity.posX;
         entity.lastTickPosY = entity.posY;
         entity.lastTickPosZ = entity.posZ;
         entity.prevRotationYaw = entity.rotationYaw;
         entity.prevRotationPitch = entity.rotationPitch;
         if (boolean2 && entity.addedToChunk) {
            entity.ticksExisted++;
            if (entity.ridingEntity != null) {
               entity.updateRidden();
            } else {
               entity.onUpdate();
            }
         }

         this.theProfiler.startSection("chunkCheck");
         if (Double.isNaN(entity.posX) || Double.isInfinite(entity.posX)) {
            entity.posX = entity.lastTickPosX;
         }

         if (Double.isNaN(entity.posY) || Double.isInfinite(entity.posY)) {
            entity.posY = entity.lastTickPosY;
         }

         if (Double.isNaN(entity.posZ) || Double.isInfinite(entity.posZ)) {
            entity.posZ = entity.lastTickPosZ;
         }

         if (Double.isNaN(entity.rotationPitch) || Double.isInfinite(entity.rotationPitch)) {
            entity.rotationPitch = entity.prevRotationPitch;
         }

         if (Double.isNaN(entity.rotationYaw) || Double.isInfinite(entity.rotationYaw)) {
            entity.rotationYaw = entity.prevRotationYaw;
         }

         int var9 = MathHelper.floor_double(entity.posX / 16.0);
         int var10 = MathHelper.floor_double(entity.posY / 16.0);
         int var11 = MathHelper.floor_double(entity.posZ / 16.0);
         if (!entity.addedToChunk || entity.W != var9 || entity.chunkCoordY != var10 || entity.Y != var11) {
            if (entity.addedToChunk && this.isChunkLoaded(entity.W, entity.Y, true)) {
               this.getChunkFromChunkCoords(entity.W, entity.Y).removeEntityAtIndex(entity, entity.chunkCoordY);
            }

            if (this.isChunkLoaded(var9, var11, true)) {
               entity.addedToChunk = true;
               this.getChunkFromChunkCoords(var9, var11).addEntity(entity);
            } else {
               entity.addedToChunk = false;
            }
         }

         this.theProfiler.endSection();
         if (boolean2 && entity.addedToChunk && entity.riddenByEntity != null) {
            if (!entity.riddenByEntity.isDead && entity.riddenByEntity.ridingEntity == entity) {
               this.updateEntity(entity.riddenByEntity);
            } else {
               entity.riddenByEntity.ridingEntity = null;
               entity.riddenByEntity = null;
            }
         }
      }
   }

   public boolean checkNoEntityCollision(AxisAlignedBB axisAlignedBB) {
      return this.checkNoEntityCollision(axisAlignedBB, null);
   }

   public boolean checkNoEntityCollision(AxisAlignedBB axisAlignedBB, Entity entity) {
      for (Entity var8 : this.getEntitiesWithinAABBExcludingEntity(null, axisAlignedBB)) {
         if (!var8.isDead && var8.preventEntitySpawning && var8 != entity && (entity == null || entity.ridingEntity != var8 && entity.riddenByEntity != var8)) {
            return false;
         }
      }

      return true;
   }

   public boolean checkBlockCollision(AxisAlignedBB axisAlignedBB) {
      int var5 = MathHelper.floor_double(axisAlignedBB.minX);
      int var6 = MathHelper.floor_double(axisAlignedBB.maxX);
      int var7 = MathHelper.floor_double(axisAlignedBB.minY);
      int var8 = MathHelper.floor_double(axisAlignedBB.maxY);
      int var9 = MathHelper.floor_double(axisAlignedBB.minZ);
      int var10 = MathHelper.floor_double(axisAlignedBB.maxZ);
      BlockPos.MutableBlockPos var11 = new BlockPos.MutableBlockPos();

      for (int var12 = var5; var12 <= var6; var12++) {
         for (int var13 = var7; var13 <= var8; var13++) {
            for (int var14 = var9; var14 <= var10; var14++) {
               Block var15 = this.getBlockState(var11.set(var12, var13, var14)).getBlock();
               if (var15.getMaterial() != Material.air) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean isAnyLiquid(AxisAlignedBB axisAlignedBB) {
      int var5 = MathHelper.floor_double(axisAlignedBB.minX);
      int var6 = MathHelper.floor_double(axisAlignedBB.maxX);
      int var7 = MathHelper.floor_double(axisAlignedBB.minY);
      int var8 = MathHelper.floor_double(axisAlignedBB.maxY);
      int var9 = MathHelper.floor_double(axisAlignedBB.minZ);
      int var10 = MathHelper.floor_double(axisAlignedBB.maxZ);
      BlockPos.MutableBlockPos var11 = new BlockPos.MutableBlockPos();

      for (int var12 = var5; var12 <= var6; var12++) {
         for (int var13 = var7; var13 <= var8; var13++) {
            for (int var14 = var9; var14 <= var10; var14++) {
               Block var15 = this.getBlockState(var11.set(var12, var13, var14)).getBlock();
               if (var15.getMaterial().isLiquid()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean isFlammableWithin(AxisAlignedBB axisAlignedBB) {
      int var5 = MathHelper.floor_double(axisAlignedBB.minX);
      int var6 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
      int var7 = MathHelper.floor_double(axisAlignedBB.minY);
      int var8 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
      int var9 = MathHelper.floor_double(axisAlignedBB.minZ);
      int var10 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
      if (this.isAreaLoaded(var5, var7, var9, var6, var8, var10, true)) {
         BlockPos.MutableBlockPos var11 = new BlockPos.MutableBlockPos();

         for (int var12 = var5; var12 < var6; var12++) {
            for (int var13 = var7; var13 < var8; var13++) {
               for (int var14 = var9; var14 < var10; var14++) {
                  Block var15 = this.getBlockState(var11.set(var12, var13, var14)).getBlock();
                  if (var15 == Blocks.fire || var15 == Blocks.flowing_lava || var15 == Blocks.lava) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public boolean handleMaterialAcceleration(AxisAlignedBB axisAlignedBB, Material material, Entity entity) {
      int var7 = MathHelper.floor_double(axisAlignedBB.minX);
      int var8 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
      int var9 = MathHelper.floor_double(axisAlignedBB.minY);
      int var10 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
      int var11 = MathHelper.floor_double(axisAlignedBB.minZ);
      int var12 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
      if (!this.isAreaLoaded(var7, var9, var11, var8, var10, var12, true)) {
         return false;
      }

      boolean var13 = false;
      Vec3 var14 = new Vec3(0.0, 0.0, 0.0);
      BlockPos.MutableBlockPos var15 = new BlockPos.MutableBlockPos();

      for (int var16 = var7; var16 < var8; var16++) {
         for (int var17 = var9; var17 < var10; var17++) {
            for (int var18 = var11; var18 < var12; var18++) {
               var15.set(var16, var17, var18);
               IBlockState var19 = this.getBlockState(var15);
               Block var20 = var19.getBlock();
               if (var20.getMaterial() == material) {
                  double var21 = var17 + 1 - BlockLiquid.getLiquidHeightPercent(var19.getValue(BlockLiquid.LEVEL));
                  if (var10 >= var21) {
                     var13 = true;
                     var14 = var20.modifyAcceleration(this, var15, entity, var14);
                  }
               }
            }
         }
      }

      if (var14.lengthVector() > 0.0 && entity.isPushedByWater()) {
         var14 = var14.normalize();
         entity.motionX = entity.motionX + var14.xCoord * 0.014;
         entity.motionY = entity.motionY + var14.yCoord * 0.014;
         entity.motionZ = entity.motionZ + var14.zCoord * 0.014;
      }

      return var13;
   }

   public boolean isMaterialInBB(AxisAlignedBB axisAlignedBB, Material material) {
      int var6 = MathHelper.floor_double(axisAlignedBB.minX);
      int var7 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
      int var8 = MathHelper.floor_double(axisAlignedBB.minY);
      int var9 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
      int var10 = MathHelper.floor_double(axisAlignedBB.minZ);
      int var11 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
      BlockPos.MutableBlockPos var12 = new BlockPos.MutableBlockPos();

      for (int var13 = var6; var13 < var7; var13++) {
         for (int var14 = var8; var14 < var9; var14++) {
            for (int var15 = var10; var15 < var11; var15++) {
               if (this.getBlockState(var12.set(var13, var14, var15)).getBlock().getMaterial() == material) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean isAABBInMaterial(AxisAlignedBB axisAlignedBB, Material material) {
      int var6 = MathHelper.floor_double(axisAlignedBB.minX);
      int var7 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
      int var8 = MathHelper.floor_double(axisAlignedBB.minY);
      int var9 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
      int var10 = MathHelper.floor_double(axisAlignedBB.minZ);
      int var11 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
      BlockPos.MutableBlockPos var12 = new BlockPos.MutableBlockPos();

      for (int var13 = var6; var13 < var7; var13++) {
         for (int var14 = var8; var14 < var9; var14++) {
            for (int var15 = var10; var15 < var11; var15++) {
               IBlockState var16 = this.getBlockState(var12.set(var13, var14, var15));
               Block var17 = var16.getBlock();
               if (var17.getMaterial() == material) {
                  int var18 = var16.getValue(BlockLiquid.LEVEL);
                  double var19 = var14 + 1;
                  if (var18 < 8) {
                     var19 = var14 + 1 - var18 / 8.0;
                  }

                  if (var19 >= axisAlignedBB.minY) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public Explosion createExplosion(Entity entity, double double2, double double3, double double4, float float5, boolean boolean6) {
      return this.newExplosion(entity, double2, double3, double4, float5, false, boolean6);
   }

   public Explosion newExplosion(Entity entity, double double2, double double3, double double4, float float5, boolean boolean6, boolean boolean7) {
      Explosion var11 = new Explosion(this, entity, double2, double3, double4, float5, boolean6, boolean7);
      var11.doExplosionA();
      var11.doExplosionB(true);
      return var11;
   }

   public float getBlockDensity(Vec3 vec3, AxisAlignedBB axisAlignedBB) {
      double var6 = 1.0 / ((axisAlignedBB.maxX - axisAlignedBB.minX) * 2.0 + 1.0);
      double var8 = 1.0 / ((axisAlignedBB.maxY - axisAlignedBB.minY) * 2.0 + 1.0);
      double var10 = 1.0 / ((axisAlignedBB.maxZ - axisAlignedBB.minZ) * 2.0 + 1.0);
      double var12 = (1.0 - Math.floor(1.0 / var6) * var6) / 2.0;
      double var14 = (1.0 - Math.floor(1.0 / var10) * var10) / 2.0;
      if (var6 >= 0.0 && var8 >= 0.0 && var10 >= 0.0) {
         int var16 = 0;
         int var17 = 0;

         for (float var18 = 0.0F; var18 <= 1.0F; var18 = (float)(var18 + var6)) {
            for (float var19 = 0.0F; var19 <= 1.0F; var19 = (float)(var19 + var8)) {
               for (float var20 = 0.0F; var20 <= 1.0F; var20 = (float)(var20 + var10)) {
                  double var21 = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * var18;
                  double var23 = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * var19;
                  double var25 = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * var20;
                  if (this.rayTraceBlocks(new Vec3(var21 + var12, var23, var25 + var14), vec3) == null) {
                     var16++;
                  }

                  var17++;
               }
            }
         }

         return (float)var16 / var17;
      } else {
         return 0.0F;
      }
   }

   public boolean extinguishFire(EntityPlayer entityPlayer, BlockPos blockPos, EnumFacing enumFacing) {
      blockPos = blockPos.offset(enumFacing);
      if (this.getBlockState(blockPos).getBlock() == Blocks.fire) {
         this.playAuxSFXAtEntity(entityPlayer, 1004, blockPos, 0);
         this.setBlockToAir(blockPos);
         return true;
      } else {
         return false;
      }
   }

   public String Method331() {
      return "All: " + this.loadedEntityList.size();
   }

   public String getProviderName() {
      return this.chunkProvider.makeString();
   }

   @Override
   public TileEntity getTileEntity(BlockPos blockPos) {
      if (!this.Method321(blockPos)) {
         return null;
      }

      TileEntity var5 = null;
      if (this.processingLoadedTiles) {
         for (TileEntity var7 : this.addedTileEntityList) {
            if (!var7.isInvalid() && var7.getPos().equals(blockPos)) {
               var5 = var7;
               break;
            }
         }
      }

      if (var5 == null) {
         var5 = this.getChunkFromBlockCoords(blockPos).getTileEntity(blockPos, Chunk.EnumCreateEntityType.IMMEDIATE);
      }

      if (var5 == null) {
         for (TileEntity var9 : this.addedTileEntityList) {
            if (!var9.isInvalid() && var9.getPos().equals(blockPos)) {
               var5 = var9;
               break;
            }
         }
      }

      return var5;
   }

   public void setTileEntity(BlockPos blockPos, TileEntity tileEntity) {
      if (tileEntity != null && !tileEntity.isInvalid()) {
         if (this.processingLoadedTiles) {
            tileEntity.setPos(blockPos);
            Iterator var6 = this.addedTileEntityList.iterator();

            while (var6.hasNext()) {
               TileEntity var7 = (TileEntity)var6.next();
               if (var7.getPos().equals(blockPos)) {
                  var7.invalidate();
                  var6.remove();
               }
            }

            this.addedTileEntityList.add(tileEntity);
         } else {
            this.addTileEntity(tileEntity);
            this.getChunkFromBlockCoords(blockPos).addTileEntity(blockPos, tileEntity);
         }
      }
   }

   public void removeTileEntity(BlockPos blockPos) {
      TileEntity var5 = this.getTileEntity(blockPos);
      if (var5 != null && this.processingLoadedTiles) {
         var5.invalidate();
         this.addedTileEntityList.remove(var5);
      } else {
         if (var5 != null) {
            this.addedTileEntityList.remove(var5);
            this.loadedTileEntityList.remove(var5);
            this.tickableTileEntities.remove(var5);
         }

         this.getChunkFromBlockCoords(blockPos).removeTileEntity(blockPos);
      }
   }

   public void markTileEntityForRemoval(TileEntity tileEntity) {
      this.tileEntitiesToBeRemoved.add(tileEntity);
   }

   public boolean isBlockFullCube(BlockPos blockPos) {
      IBlockState var5 = this.getBlockState(blockPos);
      AxisAlignedBB var6 = var5.getBlock().getCollisionBoundingBox(this, blockPos, var5);
      return var6 != null && var6.getAverageEdgeLength() >= 1.0;
   }

   public static boolean doesBlockHaveSolidTopSurface(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var5 = iBlockAccess.getBlockState(blockPos);
      Block var6 = var5.getBlock();
      return var6.getMaterial().isOpaque() && var6.isFullCube()
         || (
            var6 instanceof BlockStairs
               ? var5.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP
               : (
                  var6 instanceof BlockSlab
                     ? var5.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP
                     : var6 instanceof BlockHopper || var6 instanceof BlockSnow && var5.getValue(BlockSnow.LAYERS) == 7
               )
         );
   }

   public boolean isBlockNormalCube(BlockPos blockPos, boolean boolean2) {
      if (!this.Method321(blockPos)) {
         return boolean2;
      }

      Chunk var6 = this.chunkProvider.provideChunk(blockPos);
      if (var6.isEmpty()) {
         return boolean2;
      }

      Block var7 = this.getBlockState(blockPos).getBlock();
      return var7.getMaterial().isOpaque() && var7.isFullCube();
   }

   public void calculateInitialSkylight() {
      int var3 = this.calculateSkylightSubtracted(1.0F);
      if (var3 != this.skylightSubtracted) {
         this.skylightSubtracted = var3;
      }
   }

   public void setAllowedSpawnTypes(boolean boolean1, boolean boolean2) {
      this.spawnHostileMobs = boolean1;
      this.spawnPeacefulMobs = boolean2;
   }

   public void tick() {
      this.updateWeather();
   }

   protected void calculateInitialWeather() {
      if (this.worldInfo.isRaining()) {
         this.rainingStrength = 1.0F;
         if (this.worldInfo.isThundering()) {
            this.thunderingStrength = 1.0F;
         }
      }
   }

   protected void updateWeather() {
      if (!this.provider.getHasNoSky() && !this.isRemote) {
         int var4 = this.worldInfo.getCleanWeatherTime();
         if (var4 > 0) {
            this.worldInfo.setCleanWeatherTime(--var4);
            this.worldInfo.setThunderTime(this.worldInfo.isThundering() ? 1 : 2);
            this.worldInfo.setRainTime(this.worldInfo.isRaining() ? 1 : 2);
         }

         int var5 = this.worldInfo.getThunderTime();
         if (var5 <= 0) {
            if (this.worldInfo.isThundering()) {
               this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
            } else {
               this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
            }
         } else {
            this.worldInfo.setThunderTime(--var5);
            if (var5 <= 0) {
               this.worldInfo.setThundering(!this.worldInfo.isThundering());
            }
         }

         this.prevThunderingStrength = this.thunderingStrength;
         if (this.worldInfo.isThundering()) {
            this.thunderingStrength = (float)(this.thunderingStrength + 0.01);
         } else {
            this.thunderingStrength = (float)(this.thunderingStrength - 0.01);
         }

         this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0F, 1.0F);
         int var6 = this.worldInfo.getRainTime();
         if (var6 <= 0) {
            if (this.worldInfo.isRaining()) {
               this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
            } else {
               this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
            }
         } else {
            this.worldInfo.setRainTime(--var6);
            if (var6 <= 0) {
               this.worldInfo.setRaining(!this.worldInfo.isRaining());
            }
         }

         this.prevRainingStrength = this.rainingStrength;
         if (this.worldInfo.isRaining()) {
            this.rainingStrength = (float)(this.rainingStrength + 0.01);
         } else {
            this.rainingStrength = (float)(this.rainingStrength - 0.01);
         }

         this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0F, 1.0F);
      }
   }

   protected void setActivePlayerChunksAndCheckLight() {
      this.activeChunkSet.clear();
      this.theProfiler.startSection("buildList");

      for (EntityPlayer var5 : this.playerEntities) {
         int var6 = MathHelper.floor_double(var5.posX / 16.0);
         int var7 = MathHelper.floor_double(var5.posZ / 16.0);
         int var8 = this.getRenderDistanceChunks();

         for (int var9 = -var8; var9 <= var8; var9++) {
            for (int var10 = -var8; var10 <= var8; var10++) {
               this.activeChunkSet.add(new ChunkCoordIntPair(var9 + var6, var10 + var7));
            }
         }
      }

      this.theProfiler.endSection();
      if (this.ambientTickCountdown > 0) {
         this.ambientTickCountdown--;
      }

      this.theProfiler.startSection("playerCheckLight");
      if (!this.playerEntities.isEmpty()) {
         int var11 = this.rand.nextInt(this.playerEntities.size());
         EntityPlayer var12 = this.playerEntities.get(var11);
         int var13 = MathHelper.floor_double(var12.posX) + this.rand.nextInt(11) - 5;
         int var14 = MathHelper.floor_double(var12.posY) + this.rand.nextInt(11) - 5;
         int var15 = MathHelper.floor_double(var12.posZ) + this.rand.nextInt(11) - 5;
         this.checkLight(new BlockPos(var13, var14, var15));
      }

      this.theProfiler.endSection();
   }

   protected abstract int getRenderDistanceChunks();

   protected void playMoodSoundAndCheckLight(int integer1, int integer2, Chunk chunk) {
      this.theProfiler.endStartSection("moodSound");
      if (this.ambientTickCountdown == 0 && !this.isRemote) {
         this.updateLCG = this.updateLCG * 3 + 1013904223;
         int var7 = this.updateLCG >> 2;
         int var8 = var7 & 15;
         int var9 = var7 >> 8 & 15;
         int var10 = var7 >> 16 & 0xFF;
         BlockPos var11 = new BlockPos(var8, var10, var9);
         Block var12 = chunk.getBlock(var11);
         var8 += integer1;
         var9 += integer2;
         if (var12.getMaterial() == Material.air && this.getLight(var11) <= this.rand.nextInt(8) && this.getLightFor(EnumSkyBlock.SKY, var11) <= 0) {
            EntityPlayer var13 = this.getClosestPlayer(var8 + 0.5, var10 + 0.5, var9 + 0.5, 8.0);
            if (var13 != null && var13.getDistanceSq(var8 + 0.5, var10 + 0.5, var9 + 0.5) > 4.0) {
               this.playSoundEffect(var8 + 0.5, var10 + 0.5, var9 + 0.5, "ambient.cave.cave", 0.7F, 0.8F + this.rand.nextFloat() * 0.2F);
               this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
            }
         }
      }

      this.theProfiler.endStartSection("checkLight");
      chunk.enqueueRelightChecks();
   }

   protected void updateBlocks() {
      this.setActivePlayerChunksAndCheckLight();
   }

   public void forceBlockUpdateTick(Block block, BlockPos blockPos, Random random) {
      this.scheduledUpdatesAreImmediate = true;
      block.updateTick(this, blockPos, this.getBlockState(blockPos), random);
      this.scheduledUpdatesAreImmediate = false;
   }

   public boolean canBlockFreezeWater(BlockPos blockPos) {
      return this.canBlockFreeze(blockPos, false);
   }

   public boolean canBlockFreezeNoWater(BlockPos blockPos) {
      return this.canBlockFreeze(blockPos, true);
   }

   public boolean canBlockFreeze(BlockPos blockPos, boolean boolean2) {
      BiomeGenBase var6 = this.getBiomeGenForCoords(blockPos);
      float var7 = var6.getFloatTemperature(blockPos);
      if (!(var7 > 0.15F) && blockPos.getY() >= 0 && blockPos.getY() < 256 && this.getLightFor(EnumSkyBlock.BLOCK, blockPos) < 10) {
         IBlockState var8 = this.getBlockState(blockPos);
         Block var9 = var8.getBlock();
         if ((var9 == Blocks.water || var9 == Blocks.flowing_water) && var8.getValue(BlockLiquid.LEVEL) == 0) {
            if (!boolean2) {
               return true;
            }

            boolean var10 = this.isWater(blockPos.west()) && this.isWater(blockPos.east()) && this.isWater(blockPos.north()) && this.isWater(blockPos.south());
            return !var10;
         }
      }

      return false;
   }

   private boolean isWater(BlockPos blockPos) {
      return this.getBlockState(blockPos).getBlock().getMaterial() == Material.water;
   }

   public boolean canSnowAt(BlockPos blockPos, boolean boolean2) {
      float var7 = this.getBiomeGenForCoords(blockPos).getFloatTemperature(blockPos);
      if (var7 > 0.15F) {
         return false;
      } else if (!boolean2) {
         return true;
      } else if (blockPos.getY() >= 0 && blockPos.getY() < 256 && this.getLightFor(EnumSkyBlock.BLOCK, blockPos) < 10) {
         Block var8 = this.getBlockState(blockPos).getBlock();
         return var8.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this, blockPos);
      } else {
         return false;
      }
   }

   public boolean checkLight(BlockPos blockPos) {
      boolean var5 = false;
      if (!this.provider.getHasNoSky()) {
         var5 = false | this.checkLightFor(EnumSkyBlock.SKY, blockPos);
      }

      return var5 | this.checkLightFor(EnumSkyBlock.BLOCK, blockPos);
   }

   private int getRawLight(BlockPos blockPos, EnumSkyBlock enumSkyBlock) {
      if (enumSkyBlock == EnumSkyBlock.SKY && this.canSeeSky(blockPos)) {
         return 15;
      }

      Block var6 = this.getBlockState(blockPos).getBlock();
      int var7 = enumSkyBlock == EnumSkyBlock.SKY ? 0 : var6.getLightValue();
      int var8 = var6.getLightOpacity();
      if (var8 >= 15 && var6.getLightValue() > 0) {
         var8 = 1;
      }

      if (var8 < 1) {
         var8 = 1;
      }

      if (var8 >= 15) {
         return 0;
      }

      if (var7 >= 14) {
         return var7;
      }

      for (EnumFacing var12 : EnumFacing.values()) {
         BlockPos var13 = blockPos.offset(var12);
         int var14 = this.getLightFor(enumSkyBlock, var13) - var8;
         if (var14 > var7) {
            var7 = var14;
         }

         if (var7 >= 14) {
            return var7;
         }
      }

      return var7;
   }

   public boolean checkLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
      if (!this.Method325(blockPos, 17, false)) {
         return false;
      }

      int var6 = 0;
      int var7 = 0;
      this.theProfiler.startSection("getBrightness");
      int var8 = this.getLightFor(enumSkyBlock, blockPos);
      int var9 = this.getRawLight(blockPos, enumSkyBlock);
      int var10 = blockPos.getX();
      int var11 = blockPos.getY();
      int var12 = blockPos.getZ();
      if (var9 > var8) {
         var7++;
         this.lightUpdateBlockList[0] = 133152;
      } else if (var9 < var8) {
         this.lightUpdateBlockList[var7++] = 133152 | var8 << 18;

         while (var6 < var7) {
            int var13 = this.lightUpdateBlockList[var6++];
            int var14 = (var13 & 63) - 32 + var10;
            int var15 = (var13 >> 6 & 63) - 32 + var11;
            int var16 = (var13 >> 12 & 63) - 32 + var12;
            int var17 = var13 >> 18 & 15;
            BlockPos var18 = new BlockPos(var14, var15, var16);
            int var19 = this.getLightFor(enumSkyBlock, var18);
            if (var19 == var17) {
               this.setLightFor(enumSkyBlock, var18, 0);
               if (var17 > 0) {
                  int var20 = MathHelper.abs_int(var14 - var10);
                  int var21 = MathHelper.abs_int(var15 - var11);
                  int var22 = MathHelper.abs_int(var16 - var12);
                  if (var20 + var21 + var22 < 17) {
                     BlockPos.MutableBlockPos var23 = new BlockPos.MutableBlockPos();

                     for (EnumFacing var27 : EnumFacing.values()) {
                        int var28 = var14 + var27.getFrontOffsetX();
                        int var29 = var15 + var27.getFrontOffsetY();
                        int var30 = var16 + var27.getFrontOffsetZ();
                        var23.set(var28, var29, var30);
                        int var31 = Math.max(1, this.getBlockState(var23).getBlock().getLightOpacity());
                        var19 = this.getLightFor(enumSkyBlock, var23);
                        if (var19 == var17 - var31 && var7 < this.lightUpdateBlockList.length) {
                           this.lightUpdateBlockList[var7++] = var28 - var10 + 32 | var29 - var11 + 32 << 6 | var30 - var12 + 32 << 12 | var17 - var31 << 18;
                        }
                     }
                  }
               }
            }
         }

         var6 = 0;
      }

      this.theProfiler.endSection();
      this.theProfiler.startSection("checkedPosition < toCheckCount");

      while (var6 < var7) {
         int var32 = this.lightUpdateBlockList[var6++];
         int var33 = (var32 & 63) - 32 + var10;
         int var34 = (var32 >> 6 & 63) - 32 + var11;
         int var35 = (var32 >> 12 & 63) - 32 + var12;
         BlockPos var36 = new BlockPos(var33, var34, var35);
         int var37 = this.getLightFor(enumSkyBlock, var36);
         int var39 = this.getRawLight(var36, enumSkyBlock);
         if (var39 != var37) {
            this.setLightFor(enumSkyBlock, var36, var39);
            if (var39 > var37) {
               int var40 = Math.abs(var33 - var10);
               int var41 = Math.abs(var34 - var11);
               int var42 = Math.abs(var35 - var12);
               boolean var43 = var7 < this.lightUpdateBlockList.length - 6;
               if (var40 + var41 + var42 < 17 && var43) {
                  if (this.getLightFor(enumSkyBlock, var36.west()) < var39) {
                     this.lightUpdateBlockList[var7++] = var33 - 1 - var10 + 32 + (var34 - var11 + 32 << 6) + (var35 - var12 + 32 << 12);
                  }

                  if (this.getLightFor(enumSkyBlock, var36.east()) < var39) {
                     this.lightUpdateBlockList[var7++] = var33 + 1 - var10 + 32 + (var34 - var11 + 32 << 6) + (var35 - var12 + 32 << 12);
                  }

                  if (this.getLightFor(enumSkyBlock, var36.down()) < var39) {
                     this.lightUpdateBlockList[var7++] = var33 - var10 + 32 + (var34 - 1 - var11 + 32 << 6) + (var35 - var12 + 32 << 12);
                  }

                  if (this.getLightFor(enumSkyBlock, var36.up()) < var39) {
                     this.lightUpdateBlockList[var7++] = var33 - var10 + 32 + (var34 + 1 - var11 + 32 << 6) + (var35 - var12 + 32 << 12);
                  }

                  if (this.getLightFor(enumSkyBlock, var36.north()) < var39) {
                     this.lightUpdateBlockList[var7++] = var33 - var10 + 32 + (var34 - var11 + 32 << 6) + (var35 - 1 - var12 + 32 << 12);
                  }

                  if (this.getLightFor(enumSkyBlock, var36.south()) < var39) {
                     this.lightUpdateBlockList[var7++] = var33 - var10 + 32 + (var34 - var11 + 32 << 6) + (var35 + 1 - var12 + 32 << 12);
                  }
               }
            }
         }
      }

      this.theProfiler.endSection();
      return true;
   }

   public boolean tickUpdates(boolean boolean1) {
      return false;
   }

   public List<NextTickListEntry> getPendingBlockUpdates(Chunk chunk, boolean boolean2) {
      return null;
   }

   public List<NextTickListEntry> func_175712_a(StructureBoundingBox structureBoundingBox, boolean boolean2) {
      return null;
   }

   public List<Entity> getEntitiesWithinAABBExcludingEntity(Entity entity, AxisAlignedBB axisAlignedBB) {
      return this.getEntitiesInAABBexcluding(entity, axisAlignedBB, Tezzelator.d);
   }

   public List<Entity> getEntitiesInAABBexcluding(Entity entity, AxisAlignedBB axisAlignedBB, Predicate<? super Entity> predicate) {
      ArrayList var7 = Lists.newArrayList();
      int var8 = MathHelper.floor_double((axisAlignedBB.minX - 2.0) / 16.0);
      int var9 = MathHelper.floor_double((axisAlignedBB.maxX + 2.0) / 16.0);
      int var10 = MathHelper.floor_double((axisAlignedBB.minZ - 2.0) / 16.0);
      int var11 = MathHelper.floor_double((axisAlignedBB.maxZ + 2.0) / 16.0);

      for (int var12 = var8; var12 <= var9; var12++) {
         for (int var13 = var10; var13 <= var11; var13++) {
            if (this.isChunkLoaded(var12, var13, true)) {
               this.getChunkFromChunkCoords(var12, var13).getEntitiesWithinAABBForEntity(entity, axisAlignedBB, var7, predicate);
            }
         }
      }

      return var7;
   }

   public <T extends Entity> List<T> getEntities(Class<? extends T> class1, Predicate<? super T> predicate) {
      ArrayList<T> var6 = Lists.newArrayList();

      for (Entity var8 : this.loadedEntityList) {
         if (class1.isAssignableFrom(var8.getClass()) && predicate.apply((T)var8)) {
            var6.add((T)var8);
         }
      }

      return var6;
   }

   public <T extends Entity> List<T> getPlayers(Class<? extends T> class1, Predicate<? super T> predicate) {
      ArrayList<T> var6 = Lists.newArrayList();

      for (Entity var8 : this.playerEntities) {
         if (class1.isAssignableFrom(var8.getClass()) && predicate.apply((T)var8)) {
            var6.add((T)var8);
         }
      }

      return var6;
   }

   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> class1, AxisAlignedBB axisAlignedBB) {
      return this.getEntitiesWithinAABB(class1, axisAlignedBB, Tezzelator.d);
   }

   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> class1, AxisAlignedBB axisAlignedBB, Predicate<? super T> predicate) {
      int var7 = MathHelper.floor_double((axisAlignedBB.minX - 2.0) / 16.0);
      int var8 = MathHelper.floor_double((axisAlignedBB.maxX + 2.0) / 16.0);
      int var9 = MathHelper.floor_double((axisAlignedBB.minZ - 2.0) / 16.0);
      int var10 = MathHelper.floor_double((axisAlignedBB.maxZ + 2.0) / 16.0);
      ArrayList<T> var11 = Lists.newArrayList();

      for (int var12 = var7; var12 <= var8; var12++) {
         for (int var13 = var9; var13 <= var10; var13++) {
            if (this.isChunkLoaded(var12, var13, true)) {
               this.getChunkFromChunkCoords(var12, var13).getEntitiesOfTypeWithinAAAB(class1, axisAlignedBB, var11, predicate);
            }
         }
      }

      return var11;
   }

   public <T extends Entity> T findNearestEntityWithinAABB(Class<? extends T> class1, AxisAlignedBB axisAlignedBB, T entity) {
      List<T> var7 = this.getEntitiesWithinAABB(class1, axisAlignedBB);
      T var8 = null;
      double var9 = Double.MAX_VALUE;

      for (T var12 : var7) {
         if (var12 != entity && Tezzelator.d.apply(var12)) {
            double var13 = entity.getDistanceSqToEntity(var12);
            if (var13 <= var9) {
               var8 = var12;
               var9 = var13;
            }
         }
      }

      return var8;
   }

   public Entity getEntityByID(int integer) {
      return this.entitiesById.lookup(integer);
   }

   public List<Entity> getLoadedEntityList() {
      return this.loadedEntityList;
   }

   public void markChunkDirty(BlockPos blockPos, TileEntity tileEntity) {
      if (this.Method322(blockPos)) {
         this.getChunkFromBlockCoords(blockPos).setChunkModified();
      }
   }

   public int countEntities(Class<?> class1) {
      int var5 = 0;

      for (Entity var7 : this.loadedEntityList) {
         if ((!(var7 instanceof EntityLiving) || !((EntityLiving)var7).isNoDespawnRequired()) && class1.isAssignableFrom(var7.getClass())) {
            var5++;
         }
      }

      return var5;
   }

   public void loadEntities(Collection<Entity> collection) {
      this.loadedEntityList.addAll(collection);

      for (Entity var6 : collection) {
         this.onEntityAdded(var6);
      }
   }

   public void unloadEntities(Collection<Entity> collection) {
      this.unloadedEntityList.addAll(collection);
   }

   public boolean canBlockBePlaced(Block block, BlockPos blockPos, boolean boolean3, EnumFacing enumFacing, Entity entity, ItemStack itemStack) {
      Block var10 = this.getBlockState(blockPos).getBlock();
      AxisAlignedBB var11 = boolean3 ? null : block.getCollisionBoundingBox(this, blockPos, block.getDefaultState());
      return (var11 == null || this.checkNoEntityCollision(var11, entity))
         && (
            var10.getMaterial() == Material.circuits && block == Blocks.anvil
               || var10.getMaterial().isReplaceable() && block.canReplace(this, blockPos, enumFacing, itemStack)
         );
   }

   public int getSeaLevel() {
      return this.seaLevel;
   }

   public void setSeaLevel(int integer) {
      this.seaLevel = integer;
   }

   @Override
   public int getStrongPower(BlockPos blockPos, EnumFacing enumFacing) {
      IBlockState var3 = this.getBlockState(blockPos);
      return var3.getBlock().getStrongPower(this, blockPos, var3, enumFacing);
   }

   @Override
   public WorldType getWorldType() {
      return this.worldInfo.getTerrainType();
   }

   public int getStrongPower(BlockPos blockPos) {
      int var5 = Math.max(0, this.getStrongPower(blockPos.down(), EnumFacing.DOWN));
      if (var5 >= 15) {
         return var5;
      }

      var5 = Math.max(var5, this.getStrongPower(blockPos.up(), EnumFacing.UP));
      if (var5 >= 15) {
         return var5;
      }

      var5 = Math.max(var5, this.getStrongPower(blockPos.north(), EnumFacing.NORTH));
      if (var5 >= 15) {
         return var5;
      }

      var5 = Math.max(var5, this.getStrongPower(blockPos.south(), EnumFacing.SOUTH));
      if (var5 < 15) {
         var5 = Math.max(var5, this.getStrongPower(blockPos.west(), EnumFacing.WEST));
         if (var5 < 15) {
            var5 = Math.max(var5, this.getStrongPower(blockPos.east(), EnumFacing.EAST));
         }
      }

      return var5;
   }

   public boolean isSidePowered(BlockPos blockPos, EnumFacing enumFacing) {
      return this.getRedstonePower(blockPos, enumFacing) > 0;
   }

   public int getRedstonePower(BlockPos blockPos, EnumFacing enumFacing) {
      IBlockState var6 = this.getBlockState(blockPos);
      Block var7 = var6.getBlock();
      return var7.isNormalCube() ? this.getStrongPower(blockPos) : var7.getWeakPower(this, blockPos, var6, enumFacing);
   }

   public boolean isBlockPowered(BlockPos blockPos) {
      return this.getRedstonePower(blockPos.down(), EnumFacing.DOWN) > 0
         || this.getRedstonePower(blockPos.up(), EnumFacing.UP) > 0
         || this.getRedstonePower(blockPos.north(), EnumFacing.NORTH) > 0
         || this.getRedstonePower(blockPos.south(), EnumFacing.SOUTH) > 0
         || this.getRedstonePower(blockPos.west(), EnumFacing.WEST) > 0
         || this.getRedstonePower(blockPos.east(), EnumFacing.EAST) > 0;
   }

   public int isBlockIndirectlyGettingPowered(BlockPos blockPos) {
      int var5 = 0;

      for (EnumFacing var9 : EnumFacing.values()) {
         int var10 = this.getRedstonePower(blockPos.offset(var9), var9);
         if (var10 >= 15) {
            return 15;
         }

         if (var10 > var5) {
            var5 = var10;
         }
      }

      return var5;
   }

   public EntityPlayer getClosestPlayerToEntity(Entity entity, double double2) {
      return this.getClosestPlayer(entity.posX, entity.posY, entity.posZ, double2);
   }

   public EntityPlayer getClosestPlayer(double double1, double double2, double double3, double double4) {
      double var12 = -1.0;
      EntityPlayer var14 = null;

      for (EntityPlayer var16 : this.playerEntities) {
         if (Tezzelator.d.apply(var16)) {
            double var17 = var16.getDistanceSq(double1, double2, double3);
            if ((double4 < 0.0 || var17 < double4 * double4) && (var12 == -1.0 || var17 < var12)) {
               var12 = var17;
               var14 = var16;
            }
         }
      }

      return var14;
   }

   public boolean isAnyPlayerWithinRangeAt(double double1, double double2, double double3, double double4) {
      for (EntityPlayer var13 : this.playerEntities) {
         if (Tezzelator.d.apply(var13)) {
            double var14 = var13.getDistanceSq(double1, double2, double3);
            if (double4 < 0.0 || var14 < double4 * double4) {
               return true;
            }
         }
      }

      return false;
   }

   public EntityPlayer getPlayerEntityByName(String string) {
      for (EntityPlayer var6 : this.playerEntities) {
         if (string.equals(var6.getName())) {
            return var6;
         }
      }

      return null;
   }

   public EntityPlayer getPlayerEntityByUUID(UUID uUID) {
      for (EntityPlayer var6 : this.playerEntities) {
         if (uUID.equals(var6.getUniqueID())) {
            return var6;
         }
      }

      return null;
   }

   public void sendQuittingDisconnectingPacket() {
   }

   public void checkSessionLock() throws MinecraftException {
      this.saveHandler.checkSessionLock();
   }

   public void setTotalWorldTime(long long1) {
      this.worldInfo.setWorldTotalTime(long1);
   }

   public long getSeed() {
      return this.worldInfo.getSeed();
   }

   public long getTotalWorldTime() {
      return this.worldInfo.getWorldTotalTime();
   }

   public long getWorldTime() {
      return this.worldInfo.getWorldTime();
   }

   public void setWorldTime(long long1) {
      this.worldInfo.setWorldTime(long1);
   }

   public BlockPos getSpawnPoint() {
      BlockPos var4 = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
      if (!this.getWorldBorder().contains(var4)) {
         var4 = this.getHeight(new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
      }

      return var4;
   }

   public void setSpawnPoint(BlockPos blockPos) {
      this.worldInfo.setSpawn(blockPos);
   }

   public void joinEntityInSurroundings(Entity entity) {
      int var5 = MathHelper.floor_double(entity.posX / 16.0);
      int var6 = MathHelper.floor_double(entity.posZ / 16.0);

      for (int var8 = var5 - 2; var8 <= var5 + 2; var8++) {
         for (int var9 = var6 - 2; var9 <= var6 + 2; var9++) {
            this.getChunkFromChunkCoords(var8, var9);
         }
      }

      if (!this.loadedEntityList.contains(entity)) {
         this.loadedEntityList.add(entity);
      }
   }

   public boolean isBlockModifiable(EntityPlayer entityPlayer, BlockPos blockPos) {
      return true;
   }

   public void setEntityState(Entity entity, byte byte2) {
   }

   public IChunkProvider getChunkProvider() {
      return this.chunkProvider;
   }

   public void addBlockEvent(BlockPos blockPos, Block block, int integer3, int integer4) {
      block.onBlockEventReceived(this, blockPos, this.getBlockState(blockPos), integer3, integer4);
   }

   public ISaveHandler getSaveHandler() {
      return this.saveHandler;
   }

   public WorldInfo getWorldInfo() {
      return this.worldInfo;
   }

   public GameRules getGameRules() {
      return this.worldInfo.getGameRulesInstance();
   }

   public void updateAllPlayersSleepingFlag() {
   }

   public float getThunderStrength(float float1) {
      return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * float1) * this.getRainStrength(float1);
   }

   public void setThunderStrength(float float1) {
      this.prevThunderingStrength = float1;
      this.thunderingStrength = float1;
   }

   public float getRainStrength(float float1) {
      return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * float1;
   }

   public void setRainStrength(float float1) {
      this.prevRainingStrength = float1;
      this.rainingStrength = float1;
   }

   public boolean isThundering() {
      return this.getThunderStrength(1.0F) > 0.9;
   }

   public boolean isRaining() {
      return this.getRainStrength(1.0F) > 0.2;
   }

   public boolean isRainingAt(BlockPos blockPos) {
      if (!this.isRaining()) {
         return false;
      }

      if (!this.canSeeSky(blockPos)) {
         return false;
      }

      if (this.getPrecipitationHeight(blockPos).getY() > blockPos.getY()) {
         return false;
      }

      BiomeGenBase var5 = this.getBiomeGenForCoords(blockPos);
      return !var5.getEnableSnow() && !this.canSnowAt(blockPos, false) && var5.canRain();
   }

   public boolean isBlockinHighHumidity(BlockPos blockPos) {
      return this.getBiomeGenForCoords(blockPos).isHighHumidity();
   }

   public MapStorage getMapStorage() {
      return this.mapStorage;
   }

   public void setItemData(String string, WorldSavedData worldSavedData) {
      this.mapStorage.setData(string, worldSavedData);
   }

   public WorldSavedData loadItemData(Class<? extends WorldSavedData> class1, String string) {
      return this.mapStorage.loadData(class1, string);
   }

   public int getUniqueDataId(String string) {
      return this.mapStorage.getUniqueDataId(string);
   }

   public void playBroadcastSound(int integer1, BlockPos blockPos, int integer3) {
      Iterator var7 = this.worldAccesses.iterator();

      while (var7.hasNext()) {
         ((IWorldAccess)var7.next()).broadcastSound(integer1, blockPos, integer3);
      }
   }

   public void playAuxSFX(int integer1, BlockPos blockPos, int integer3) {
      this.playAuxSFXAtEntity(null, integer1, blockPos, integer3);
   }

   public void playAuxSFXAtEntity(EntityPlayer entityPlayer, int integer2, BlockPos blockPos, int integer4) {
      try {
         for (IWorldAccess var12 : this.worldAccesses) {
            var12.playAuxSFX(entityPlayer, integer2, blockPos, integer4);
         }
      } catch (Throwable var11) {
         CrashReport var9 = CrashReport.makeCrashReport(var11, "Playing level event");
         CrashReportCategory var10 = var9.makeCategory("Level event being played");
         var10.addCrashSection("Block coordinates", CrashReportCategory.getCoordinateInfo(blockPos));
         var10.addCrashSection("Event source", entityPlayer);
         var10.addCrashSection("Event type", integer2);
         var10.addCrashSection("Event data", integer4);
         throw new ReportedException(var9);
      }
   }

   public int getHeight() {
      return 256;
   }

   public int getActualHeight() {
      return this.provider.getHasNoSky() ? 128 : 256;
   }

   public Random setRandomSeed(int integer1, int integer2, int integer3) {
      long var6 = integer1 * 341873128712L + integer2 * 132897987541L + this.getWorldInfo().getSeed() + integer3;
      this.rand.setSeed(var6);
      return this.rand;
   }

   public BlockPos getStrongholdPos(String string, BlockPos blockPos) {
      return this.getChunkProvider().getStrongholdGen(this, string, blockPos);
   }

   @Override
   public boolean extendedLevelsInChunkCache() {
      return false;
   }

   public double getHorizon() {
      return this.worldInfo.getTerrainType() == WorldType.FLAT ? 0.0 : 63.0;
   }

   public CrashReportCategory addWorldInfoToCrashReport(CrashReport crashReport) {
      CrashReportCategory var5 = crashReport.makeCategoryDepth("Affected level", 1);
      var5.addCrashSection("Level name", this.worldInfo == null ? "????" : this.worldInfo.getWorldName());
      var5.addCrashSectionCallable("All players", () -> this.playerEntities.size() + " total; " + this.playerEntities);
      var5.addCrashSectionCallable("Chunk stats", () -> this.chunkProvider.makeString());

      try {
         this.worldInfo.addToCrashReport(var5);
      } catch (Throwable var7) {
         var5.addCrashSectionThrowable("Level Data Unobtainable", var7);
      }

      return var5;
   }

   public void sendBlockBreakProgress(int integer1, BlockPos blockPos, int integer3) {
      Iterator var7 = this.worldAccesses.iterator();

      while (var7.hasNext()) {
         ((IWorldAccess)var7.next()).sendBlockBreakProgress(integer1, blockPos, integer3);
      }
   }

   public Calendar getCurrentDate() {
      if (this.getTotalWorldTime() % 600L == 0L) {
         this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
      }

      return this.theCalendar;
   }

   public void makeFireworks(double double1, double double2, double double3, double double4, double double5, double double6, NBTTagCompound nBTTagCompound) {
   }

   public Scoreboard getScoreboard() {
      return this.worldScoreboard;
   }

   public void updateComparatorOutputLevel(BlockPos blockPos, Block block) {
      for (EnumFacing var7 : EnumFacing.Plane.HORIZONTAL) {
         BlockPos var8 = blockPos.offset(var7);
         if (this.Method322(var8)) {
            IBlockState var9 = this.getBlockState(var8);
            if (Blocks.unpowered_comparator.isAssociated(var9.getBlock())) {
               var9.getBlock().onNeighborBlockChange(this, var8, var9, block);
            } else if (var9.getBlock().isNormalCube()) {
               var8 = var8.offset(var7);
               var9 = this.getBlockState(var8);
               if (Blocks.unpowered_comparator.isAssociated(var9.getBlock())) {
                  var9.getBlock().onNeighborBlockChange(this, var8, var9, block);
               }
            }
         }
      }
   }

   public DifficultyInstance getDifficultyForLocation(BlockPos blockPos) {
      long var5 = 0L;
      float var7 = 0.0F;
      if (this.Method322(blockPos)) {
         var7 = this.getCurrentMoonPhaseFactor();
         var5 = this.getChunkFromBlockCoords(blockPos).getInhabitedTime();
      }

      return new DifficultyInstance(this.getDifficulty(), this.getWorldTime(), var5, var7);
   }

   public EnumDifficulty getDifficulty() {
      return this.getWorldInfo().getDifficulty();
   }

   public int getSkylightSubtracted() {
      return this.skylightSubtracted;
   }

   public void setSkylightSubtracted(int integer) {
      this.skylightSubtracted = integer;
   }

   public int getLastLightningBolt() {
      return this.lastLightningBolt;
   }

   public void setLastLightningBolt(int integer) {
      this.lastLightningBolt = integer;
   }

   public boolean isFindingSpawnPoint() {
      return this.findingSpawnPoint;
   }

   public VillageCollection getVillageCollection() {
      return this.villageCollectionObj;
   }

   public WorldBorder getWorldBorder() {
      return this.worldBorder;
   }

   public boolean isSpawnChunk(int integer1, int integer2) {
      BlockPos var6 = this.getSpawnPoint();
      int var7 = integer1 * 16 + 8 - var6.getX();
      int var8 = integer2 * 16 + 8 - var6.getZ();
      short var9 = 128;
      return var7 >= -128 && var7 <= var9 && var8 >= -128 && var8 <= var9;
   }
}
