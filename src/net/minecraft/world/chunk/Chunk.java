package net.minecraft.world.chunk;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderDebug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chunk {
   private static final Logger logger = LogManager.getLogger();
   private final ExtendedBlockStorage[] storageArrays = new ExtendedBlockStorage[16];
   private final byte[] blockBiomeArray = new byte[256];
   public final int[] precipitationHeightMap = new int[256];
   private final boolean[] updateSkylightColumns = new boolean[256];
   private boolean isChunkLoaded;
   private final World worldObj;
   private final int[] heightMap;
   public final int xPosition;
   public final int zPosition;
   private boolean isGapLightingUpdated;
   private final Map<BlockPos, TileEntity> chunkTileEntityMap = Maps.newHashMap();
   private final ClassInheritanceMultiMap<Entity>[] entityLists;
   private boolean isTerrainPopulated;
   private boolean isLightPopulated;
   private boolean field_150815_m;
   private boolean isModified;
   public boolean hasEntities;
   private long lastSaveTime;
   private int heightMapMinimum;
   private long inhabitedTime;
   private int queuedLightChecks = 4096;
   private final ConcurrentLinkedQueue<BlockPos> tileEntityPosQueue = Queues.newConcurrentLinkedQueue();

   public Chunk(World world, int integer2, int integer3) {
      this.entityLists = new ClassInheritanceMultiMap[16];
      this.worldObj = world;
      this.xPosition = integer2;
      this.zPosition = integer3;
      this.heightMap = new int[256];

      for (int var7 = 0; var7 < this.entityLists.length; var7++) {
         this.entityLists[var7] = new ClassInheritanceMultiMap<>(Entity.class);
      }

      Arrays.fill(this.precipitationHeightMap, -999);
      Arrays.fill(this.blockBiomeArray, (byte)-1);
   }

   public Chunk(World world, ChunkPrimer chunkPrimer, int integer3, int integer4) {
      this(world, integer3, integer4);
      short var8 = 256;
      boolean var9 = !world.provider.getHasNoSky();

      for (int var10 = 0; var10 < 16; var10++) {
         for (int var11 = 0; var11 < 16; var11++) {
            for (int var12 = 0; var12 < var8; var12++) {
               int var13 = var10 * var8 * 16 | var11 * var8 | var12;
               IBlockState var14 = chunkPrimer.getBlockState(var13);
               if (var14.getBlock().getMaterial() != Material.air) {
                  int var15 = var12 >> 4;
                  if (this.storageArrays[var15] == null) {
                     this.storageArrays[var15] = new ExtendedBlockStorage(var15 << 4, var9);
                  }

                  this.storageArrays[var15].set(var10, var12 & 15, var11, var14);
               }
            }
         }
      }
   }

   public boolean isAtLocation(int integer1, int integer2) {
      return integer1 == this.xPosition && integer2 == this.zPosition;
   }

   public int getHeight(BlockPos blockPos) {
      return this.getHeightValue(blockPos.getX() & 15, blockPos.getZ() & 15);
   }

   public int getHeightValue(int integer1, int integer2) {
      return this.heightMap[integer2 << 4 | integer1];
   }

   public int getTopFilledSegment() {
      for (int var4 = this.storageArrays.length - 1; var4 >= 0; var4--) {
         if (this.storageArrays[var4] != null) {
            return this.storageArrays[var4].getYLocation();
         }
      }

      return 0;
   }

   public ExtendedBlockStorage[] getBlockStorageArray() {
      return this.storageArrays;
   }

   protected void generateHeightMap() {
      int var4 = this.getTopFilledSegment();
      this.heightMapMinimum = Integer.MAX_VALUE;

      for (int var5 = 0; var5 < 16; var5++) {
         for (int var6 = 0; var6 < 16; var6++) {
            this.precipitationHeightMap[var5 + (var6 << 4)] = -999;

            for (int var7 = var4 + 16; var7 > 0; var7--) {
               Block var8 = this.getBlock0(var5, var7 - 1, var6);
               if (var8.getLightOpacity() != 0) {
                  this.heightMap[var6 << 4 | var5] = var7;
                  if (var7 < this.heightMapMinimum) {
                     this.heightMapMinimum = var7;
                  }
                  break;
               }
            }
         }
      }

      this.isModified = true;
   }

   public void generateSkylightMap() {
      int var4 = this.getTopFilledSegment();
      this.heightMapMinimum = Integer.MAX_VALUE;

      for (int var5 = 0; var5 < 16; var5++) {
         for (int var6 = 0; var6 < 16; var6++) {
            this.precipitationHeightMap[var5 + (var6 << 4)] = -999;

            for (int var7 = var4 + 16; var7 > 0; var7--) {
               if (this.getBlockLightOpacity(var5, var7 - 1, var6) != 0) {
                  this.heightMap[var6 << 4 | var5] = var7;
                  if (var7 < this.heightMapMinimum) {
                     this.heightMapMinimum = var7;
                  }
                  break;
               }
            }

            if (!this.worldObj.provider.getHasNoSky()) {
               int var11 = 15;
               int var8 = var4 + 16 - 1;

               while (true) {
                  int var9 = this.getBlockLightOpacity(var5, var8, var6);
                  if (var9 == 0 && var11 != 15) {
                     var9 = 1;
                  }

                  var11 -= var9;
                  if (var11 > 0) {
                     ExtendedBlockStorage var10 = this.storageArrays[var8 >> 4];
                     if (var10 != null) {
                        var10.setExtSkylightValue(var5, var8 & 15, var6, var11);
                        this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + var5, var8, (this.zPosition << 4) + var6));
                     }
                  }

                  if (--var8 <= 0 || var11 <= 0) {
                     break;
                  }
               }
            }
         }
      }

      this.isModified = true;
   }

   private void propagateSkylightOcclusion(int integer1, int integer2) {
      this.updateSkylightColumns[integer1 + integer2 * 16] = true;
      this.isGapLightingUpdated = true;
   }

   private void recheckGaps(boolean boolean1) {
      this.worldObj.theProfiler.startSection("recheckGaps");
      if (this.worldObj.Method324(new BlockPos(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8), 16)) {
         for (int var5 = 0; var5 < 16; var5++) {
            for (int var6 = 0; var6 < 16; var6++) {
               if (this.updateSkylightColumns[var5 + var6 * 16]) {
                  this.updateSkylightColumns[var5 + var6 * 16] = false;
                  int var7 = this.getHeightValue(var5, var6);
                  int var8 = this.xPosition * 16 + var5;
                  int var9 = this.zPosition * 16 + var6;
                  int var10 = Integer.MAX_VALUE;

                  for (EnumFacing var12 : EnumFacing.Plane.HORIZONTAL) {
                     var10 = Math.min(var10, this.worldObj.getChunksLowestHorizon(var8 + var12.getFrontOffsetX(), var9 + var12.getFrontOffsetZ()));
                  }

                  this.checkSkylightNeighborHeight(var8, var9, var10);

                  for (EnumFacing var14 : EnumFacing.Plane.HORIZONTAL) {
                     this.checkSkylightNeighborHeight(var8 + var14.getFrontOffsetX(), var9 + var14.getFrontOffsetZ(), var7);
                  }

                  if (boolean1) {
                     this.worldObj.theProfiler.endSection();
                     return;
                  }
               }
            }
         }

         this.isGapLightingUpdated = false;
      }

      this.worldObj.theProfiler.endSection();
   }

   private void checkSkylightNeighborHeight(int integer1, int integer2, int integer3) {
      int var7 = this.worldObj.getHeight(new BlockPos(integer1, 0, integer2)).getY();
      if (var7 > integer3) {
         this.updateSkylightNeighborHeight(integer1, integer2, integer3, var7 + 1);
      } else if (var7 < integer3) {
         this.updateSkylightNeighborHeight(integer1, integer2, var7, integer3 + 1);
      }
   }

   private void updateSkylightNeighborHeight(int integer1, int integer2, int integer3, int integer4) {
      if (integer4 > integer3 && this.worldObj.Method324(new BlockPos(integer1, 0, integer2), 16)) {
         for (int var8 = integer3; var8 < integer4; var8++) {
            this.worldObj.checkLightFor(EnumSkyBlock.SKY, new BlockPos(integer1, var8, integer2));
         }

         this.isModified = true;
      }
   }

   private void relightBlock(int integer1, int integer2, int integer3) {
      int var7 = this.heightMap[integer3 << 4 | integer1] & 0xFF;
      int var8 = Math.max(integer2, var7);

      while (var8 > 0 && this.getBlockLightOpacity(integer1, var8 - 1, integer3) == 0) {
         var8--;
      }

      if (var8 != var7) {
         this.worldObj.markBlocksDirtyVertical(integer1 + this.xPosition * 16, integer3 + this.zPosition * 16, var8, var7);
         this.heightMap[integer3 << 4 | integer1] = var8;
         int var9 = this.xPosition * 16 + integer1;
         int var10 = this.zPosition * 16 + integer3;
         if (!this.worldObj.provider.getHasNoSky()) {
            if (var8 < var7) {
               for (int var11 = var8; var11 < var7; var11++) {
                  ExtendedBlockStorage var12 = this.storageArrays[var11 >> 4];
                  if (var12 != null) {
                     var12.setExtSkylightValue(integer1, var11 & 15, integer3, 15);
                     this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + integer1, var11, (this.zPosition << 4) + integer3));
                  }
               }
            } else {
               for (int var16 = var7; var16 < var8; var16++) {
                  ExtendedBlockStorage var19 = this.storageArrays[var16 >> 4];
                  if (var19 != null) {
                     var19.setExtSkylightValue(integer1, var16 & 15, integer3, 0);
                     this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + integer1, var16, (this.zPosition << 4) + integer3));
                  }
               }
            }

            int var17 = 15;

            while (var8 > 0 && var17 > 0) {
               int var20 = this.getBlockLightOpacity(integer1, --var8, integer3);
               if (var20 == 0) {
                  var20 = 1;
               }

               var17 -= var20;
               if (var17 < 0) {
                  var17 = 0;
               }

               ExtendedBlockStorage var13 = this.storageArrays[var8 >> 4];
               if (var13 != null) {
                  var13.setExtSkylightValue(integer1, var8 & 15, integer3, var17);
               }
            }
         }

         int var18 = this.heightMap[integer3 << 4 | integer1];
         int var21 = var7;
         int var22 = var18;
         if (var18 < var7) {
            var21 = var18;
            var22 = var7;
         }

         if (var18 < this.heightMapMinimum) {
            this.heightMapMinimum = var18;
         }

         if (!this.worldObj.provider.getHasNoSky()) {
            for (EnumFacing var15 : EnumFacing.Plane.HORIZONTAL) {
               this.updateSkylightNeighborHeight(var9 + var15.getFrontOffsetX(), var10 + var15.getFrontOffsetZ(), var21, var22);
            }

            this.updateSkylightNeighborHeight(var9, var10, var21, var22);
         }

         this.isModified = true;
      }
   }

   public int getBlockLightOpacity(BlockPos blockPos) {
      return this.getBlock(blockPos).getLightOpacity();
   }

   private int getBlockLightOpacity(int integer1, int integer2, int integer3) {
      return this.getBlock0(integer1, integer2, integer3).getLightOpacity();
   }

   private Block getBlock0(int integer1, int integer2, int integer3) {
      Block var7 = Blocks.air;
      if (integer2 >= 0 && integer2 >> 4 < this.storageArrays.length) {
         ExtendedBlockStorage var8 = this.storageArrays[integer2 >> 4];
         if (var8 != null) {
            try {
               var7 = var8.getBlockByExtId(integer1, integer2 & 15, integer3);
            } catch (Throwable var11) {
               CrashReport var10 = CrashReport.makeCrashReport(var11, "Getting block");
               throw new ReportedException(var10);
            }
         }
      }

      return var7;
   }

   public Block getBlock(int integer1, int integer2, int integer3) {
      try {
         return this.getBlock0(integer1 & 15, integer2, integer3 & 15);
      } catch (ReportedException var7) {
         var7.getCrashReport()
            .makeCategory("Block being got")
            .addCrashSectionCallable(
               "Location", () -> CrashReportCategory.getCoordinateInfo(new BlockPos(this.xPosition * 16 + integer1, integer2, this.zPosition * 16 + integer3))
            );
         throw var7;
      }
   }

   public Block getBlock(BlockPos blockPos) {
      try {
         return this.getBlock0(blockPos.getX() & 15, blockPos.getY(), blockPos.getZ() & 15);
      } catch (ReportedException var5) {
         var5.getCrashReport().makeCategory("Block being got").addCrashSectionCallable("Location", () -> CrashReportCategory.getCoordinateInfo(blockPos));
         throw var5;
      }
   }

   public IBlockState getBlockState(BlockPos blockPos) {
      if (this.worldObj.getWorldType() == WorldType.DEBUG_WORLD) {
         IBlockState var10 = null;
         if (blockPos.getY() == 60) {
            var10 = Blocks.barrier.getDefaultState();
         }

         if (blockPos.getY() == 70) {
            var10 = ChunkProviderDebug.func_177461_b(blockPos.getX(), blockPos.getZ());
         }

         return var10 == null ? Blocks.air.getDefaultState() : var10;
      } else {
         try {
            if (blockPos.getY() >= 0 && blockPos.getY() >> 4 < this.storageArrays.length) {
               ExtendedBlockStorage var5 = this.storageArrays[blockPos.getY() >> 4];
               if (var5 != null) {
                  int var11 = blockPos.getX() & 15;
                  int var7 = blockPos.getY() & 15;
                  int var8 = blockPos.getZ() & 15;
                  return var5.get(var11, var7, var8);
               }
            }

            return Blocks.air.getDefaultState();
         } catch (Throwable var9) {
            CrashReport var6 = CrashReport.makeCrashReport(var9, "Getting block state");
            var6.makeCategory("Block being got").addCrashSectionCallable("Location", () -> CrashReportCategory.getCoordinateInfo(blockPos));
            throw new ReportedException(var6);
         }
      }
   }

   private int getBlockMetadata(int integer1, int integer2, int integer3) {
      if (integer2 >> 4 >= this.storageArrays.length) {
         return 0;
      }

      ExtendedBlockStorage var7 = this.storageArrays[integer2 >> 4];
      return var7 != null ? var7.getExtBlockMetadata(integer1, integer2 & 15, integer3) : 0;
   }

   public int getBlockMetadata(BlockPos blockPos) {
      return this.getBlockMetadata(blockPos.getX() & 15, blockPos.getY(), blockPos.getZ() & 15);
   }

   public IBlockState setBlockState(BlockPos blockPos, IBlockState iBlockState) {
      int var6 = blockPos.getX() & 15;
      int var7 = blockPos.getY();
      int var8 = blockPos.getZ() & 15;
      int var9 = var8 << 4 | var6;
      if (var7 >= this.precipitationHeightMap[var9] - 1) {
         this.precipitationHeightMap[var9] = -999;
      }

      int var10 = this.heightMap[var9];
      IBlockState var11 = this.getBlockState(blockPos);
      if (var11 == iBlockState) {
         return null;
      }

      Block var12 = iBlockState.getBlock();
      Block var13 = var11.getBlock();
      ExtendedBlockStorage var14 = this.storageArrays[var7 >> 4];
      boolean var15 = false;
      if (var14 == null) {
         if (var12 == Blocks.air) {
            return null;
         }

         var14 = this.storageArrays[var7 >> 4] = new ExtendedBlockStorage(var7 >> 4 << 4, !this.worldObj.provider.getHasNoSky());
         var15 = var7 >= var10;
      }

      var14.set(var6, var7 & 15, var8, iBlockState);
      if (var13 != var12) {
         if (!this.worldObj.isRemote) {
            var13.breakBlock(this.worldObj, blockPos, var11);
         } else if (var13 instanceof ITileEntityProvider) {
            this.worldObj.removeTileEntity(blockPos);
         }
      }

      if (var14.getBlockByExtId(var6, var7 & 15, var8) != var12) {
         return null;
      }

      if (var15) {
         this.generateSkylightMap();
      } else {
         int var16 = var12.getLightOpacity();
         int var17 = var13.getLightOpacity();
         if (var16 > 0) {
            if (var7 >= var10) {
               this.relightBlock(var6, var7 + 1, var8);
            }
         } else if (var7 == var10 - 1) {
            this.relightBlock(var6, var7, var8);
         }

         if (var16 != var17 && (var16 < var17 || this.getLightFor(EnumSkyBlock.SKY, blockPos) > 0 || this.getLightFor(EnumSkyBlock.BLOCK, blockPos) > 0)) {
            this.propagateSkylightOcclusion(var6, var8);
         }
      }

      if (var13 instanceof ITileEntityProvider) {
         TileEntity var18 = this.getTileEntity(blockPos, Chunk.EnumCreateEntityType.CHECK);
         if (var18 != null) {
            var18.updateContainingBlockInfo();
         }
      }

      if (!this.worldObj.isRemote && var13 != var12) {
         var12.onBlockAdded(this.worldObj, blockPos, iBlockState);
      }

      if (var12 instanceof ITileEntityProvider) {
         TileEntity var19 = this.getTileEntity(blockPos, Chunk.EnumCreateEntityType.CHECK);
         if (var19 == null) {
            var19 = ((ITileEntityProvider)var12).createNewTileEntity(this.worldObj, var12.getMetaFromState(iBlockState));
            this.worldObj.setTileEntity(blockPos, var19);
         }

         if (var19 != null) {
            var19.updateContainingBlockInfo();
         }
      }

      this.isModified = true;
      return var11;
   }

   public int getLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
      int var6 = blockPos.getX() & 15;
      int var7 = blockPos.getY();
      int var8 = blockPos.getZ() & 15;
      ExtendedBlockStorage var9 = this.storageArrays[var7 >> 4];
      return var9 == null
         ? (this.canSeeSky(blockPos) ? enumSkyBlock.defaultLightValue : 0)
         : (
            enumSkyBlock == EnumSkyBlock.SKY
               ? (this.worldObj.provider.getHasNoSky() ? 0 : var9.getExtSkylightValue(var6, var7 & 15, var8))
               : (enumSkyBlock == EnumSkyBlock.BLOCK ? var9.getExtBlocklightValue(var6, var7 & 15, var8) : enumSkyBlock.defaultLightValue)
         );
   }

   public void setLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos, int integer) {
      int var7 = blockPos.getX() & 15;
      int var8 = blockPos.getY();
      int var9 = blockPos.getZ() & 15;
      ExtendedBlockStorage var10 = this.storageArrays[var8 >> 4];
      if (var10 == null) {
         var10 = this.storageArrays[var8 >> 4] = new ExtendedBlockStorage(var8 >> 4 << 4, !this.worldObj.provider.getHasNoSky());
         this.generateSkylightMap();
      }

      this.isModified = true;
      if (enumSkyBlock == EnumSkyBlock.SKY) {
         if (!this.worldObj.provider.getHasNoSky()) {
            var10.setExtSkylightValue(var7, var8 & 15, var9, integer);
         }
      } else if (enumSkyBlock == EnumSkyBlock.BLOCK) {
         var10.setExtBlocklightValue(var7, var8 & 15, var9, integer);
      }
   }

   public int getLightSubtracted(BlockPos blockPos, int integer) {
      int var6 = blockPos.getX() & 15;
      int var7 = blockPos.getY();
      int var8 = blockPos.getZ() & 15;
      ExtendedBlockStorage var9 = this.storageArrays[var7 >> 4];
      if (var9 != null) {
         int var10 = (this.worldObj.provider.getHasNoSky() ? 0 : var9.getExtSkylightValue(var6, var7 & 15, var8)) - integer;
         int var11 = var9.getExtBlocklightValue(var6, var7 & 15, var8);
         if (var11 > var10) {
            var10 = var11;
         }

         return var10;
      } else {
         return !this.worldObj.provider.getHasNoSky() && integer < EnumSkyBlock.SKY.defaultLightValue ? EnumSkyBlock.SKY.defaultLightValue - integer : 0;
      }
   }

   public void addEntity(Entity entity) {
      this.hasEntities = true;
      int var5 = MathHelper.floor_double(entity.posX / 16.0);
      int var6 = MathHelper.floor_double(entity.posZ / 16.0);
      if (var5 != this.xPosition || var6 != this.zPosition) {
         logger.warn("Wrong location! (" + var5 + ", " + var6 + ") should be (" + this.xPosition + ", " + this.zPosition + "), " + entity, new Object[]{entity});
         entity.setDead();
      }

      int var7 = MathHelper.floor_double(entity.posY / 16.0);
      if (var7 < 0) {
         var7 = 0;
      }

      if (var7 >= this.entityLists.length) {
         var7 = this.entityLists.length - 1;
      }

      entity.addedToChunk = true;
      entity.W = this.xPosition;
      entity.chunkCoordY = var7;
      entity.Y = this.zPosition;
      this.entityLists[var7].add(entity);
   }

   public void removeEntity(Entity entity) {
      this.removeEntityAtIndex(entity, entity.chunkCoordY);
   }

   public void removeEntityAtIndex(Entity entity, int integer) {
      if (integer < 0) {
         integer = 0;
      }

      if (integer >= this.entityLists.length) {
         integer = this.entityLists.length - 1;
      }

      this.entityLists[integer].remove(entity);
   }

   public boolean canSeeSky(BlockPos blockPos) {
      int var5 = blockPos.getX() & 15;
      int var6 = blockPos.getY();
      int var7 = blockPos.getZ() & 15;
      return var6 >= this.heightMap[var7 << 4 | var5];
   }

   private TileEntity createNewTileEntity(BlockPos blockPos) {
      Block var5 = this.getBlock(blockPos);
      return !var5.hasTileEntity() ? null : ((ITileEntityProvider)var5).createNewTileEntity(this.worldObj, this.getBlockMetadata(blockPos));
   }

   public TileEntity getTileEntity(BlockPos blockPos, Chunk.EnumCreateEntityType enumCreateEntityType) {
      TileEntity var6 = this.chunkTileEntityMap.get(blockPos);
      if (var6 == null) {
         if (enumCreateEntityType == Chunk.EnumCreateEntityType.IMMEDIATE) {
            var6 = this.createNewTileEntity(blockPos);
            this.worldObj.setTileEntity(blockPos, var6);
         } else if (enumCreateEntityType == Chunk.EnumCreateEntityType.QUEUED) {
            this.tileEntityPosQueue.add(blockPos);
         }
      } else if (var6.isInvalid()) {
         this.chunkTileEntityMap.remove(blockPos);
         return null;
      }

      return var6;
   }

   public void addTileEntity(TileEntity tileEntity) {
      this.addTileEntity(tileEntity.getPos(), tileEntity);
      if (this.isChunkLoaded) {
         this.worldObj.addTileEntity(tileEntity);
      }
   }

   public void addTileEntity(BlockPos blockPos, TileEntity tileEntity) {
      tileEntity.setWorldObj(this.worldObj);
      tileEntity.setPos(blockPos);
      if (this.getBlock(blockPos) instanceof ITileEntityProvider) {
         if (this.chunkTileEntityMap.containsKey(blockPos)) {
            this.chunkTileEntityMap.get(blockPos).invalidate();
         }

         tileEntity.validate();
         this.chunkTileEntityMap.put(blockPos, tileEntity);
      }
   }

   public void removeTileEntity(BlockPos blockPos) {
      if (this.isChunkLoaded) {
         TileEntity var5 = this.chunkTileEntityMap.remove(blockPos);
         if (var5 != null) {
            var5.invalidate();
         }
      }
   }

   public void onChunkLoad() {
      this.isChunkLoaded = true;
      this.worldObj.addTileEntities(this.chunkTileEntityMap.values());

      for (ClassInheritanceMultiMap<Entity> var7 : this.entityLists) {
         for (Entity var9 : var7) {
            var9.onChunkLoad();
         }

         this.worldObj.loadEntities(var7);
      }
   }

   public void onChunkUnload() {
      this.isChunkLoaded = false;

      for (TileEntity var5 : this.chunkTileEntityMap.values()) {
         this.worldObj.markTileEntityForRemoval(var5);
      }

      for (ClassInheritanceMultiMap<Entity> var7 : this.entityLists) {
         this.worldObj.unloadEntities(var7);
      }
   }

   public void setChunkModified() {
      this.isModified = true;
   }

   public void getEntitiesWithinAABBForEntity(Entity entity, AxisAlignedBB axisAlignedBB, List<Entity> list, Predicate<? super Entity> predicate) {
      int var8 = MathHelper.floor_double((axisAlignedBB.minY - 2.0) / 16.0);
      int var9 = MathHelper.floor_double((axisAlignedBB.maxY + 2.0) / 16.0);
      var8 = MathHelper.clamp_int(var8, 0, this.entityLists.length - 1);
      var9 = MathHelper.clamp_int(var9, 0, this.entityLists.length - 1);

      for (int var10 = var8; var10 <= var9; var10++) {
         if (!this.entityLists[var10].isEmpty()) {
            for (Entity var12 : this.entityLists[var10]) {
               if (var12.getEntityBoundingBox().intersectsWith(axisAlignedBB) && var12 != entity) {
                  if (predicate == null || predicate.apply(var12)) {
                     list.add(var12);
                  }

                  Entity[] var13 = var12.getParts();
                  if (var13 != null) {
                     for (Entity var19 : var13) {
                        if (var19 != entity && var19.getEntityBoundingBox().intersectsWith(axisAlignedBB) && (predicate == null || predicate.apply(var19))) {
                           list.add(var19);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public <T extends Entity> void getEntitiesOfTypeWithinAAAB(
      Class<? extends T> class1, AxisAlignedBB axisAlignedBB, List<T> list, Predicate<? super T> predicate
   ) {
      int var8 = MathHelper.floor_double((axisAlignedBB.minY - 2.0) / 16.0);
      int var9 = MathHelper.floor_double((axisAlignedBB.maxY + 2.0) / 16.0);
      var8 = MathHelper.clamp_int(var8, 0, this.entityLists.length - 1);
      var9 = MathHelper.clamp_int(var9, 0, this.entityLists.length - 1);

      for (int var10 = var8; var10 <= var9; var10++) {
         for (T var12 : this.entityLists[var10].getByClass(class1)) {
            if (var12.getEntityBoundingBox().intersectsWith(axisAlignedBB) && (predicate == null || predicate.apply(var12))) {
               list.add(var12);
            }
         }
      }
   }

   public boolean needsSaving(boolean boolean1) {
      if (boolean1) {
         if (this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime || this.isModified) {
            return true;
         }
      } else if (this.hasEntities && this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600L) {
         return true;
      }

      return this.isModified;
   }

   public Random getRandomWithSeed(long long1) {
      return new Random(
         this.worldObj.getSeed()
               + (long)this.xPosition * this.xPosition * 4987142L
               + this.xPosition * 5947611L
               + (long)this.zPosition * this.zPosition * 4392871L
               + this.zPosition * 389711L
            ^ long1
      );
   }

   public boolean isEmpty() {
      return false;
   }

   public void populateChunk(IChunkProvider iChunkProvider1, IChunkProvider iChunkProvider2, int integer3, int integer4) {
      boolean var8 = iChunkProvider1.chunkExists(integer3, integer4 - 1);
      boolean var9 = iChunkProvider1.chunkExists(integer3 + 1, integer4);
      boolean var10 = iChunkProvider1.chunkExists(integer3, integer4 + 1);
      boolean var11 = iChunkProvider1.chunkExists(integer3 - 1, integer4);
      boolean var12 = iChunkProvider1.chunkExists(integer3 - 1, integer4 - 1);
      boolean var13 = iChunkProvider1.chunkExists(integer3 + 1, integer4 + 1);
      boolean var14 = iChunkProvider1.chunkExists(integer3 - 1, integer4 + 1);
      boolean var15 = iChunkProvider1.chunkExists(integer3 + 1, integer4 - 1);
      if (var9 && var10 && var13) {
         if (!this.isTerrainPopulated) {
            iChunkProvider1.populate(iChunkProvider2, integer3, integer4);
         } else {
            iChunkProvider1.populateChunk(iChunkProvider2, this, integer3, integer4);
         }
      }

      if (var11 && var10 && var14) {
         Chunk var16 = iChunkProvider1.provideChunk(integer3 - 1, integer4);
         if (!var16.isTerrainPopulated) {
            iChunkProvider1.populate(iChunkProvider2, integer3 - 1, integer4);
         } else {
            iChunkProvider1.populateChunk(iChunkProvider2, var16, integer3 - 1, integer4);
         }
      }

      if (var8 && var9 && var15) {
         Chunk var17 = iChunkProvider1.provideChunk(integer3, integer4 - 1);
         if (!var17.isTerrainPopulated) {
            iChunkProvider1.populate(iChunkProvider2, integer3, integer4 - 1);
         } else {
            iChunkProvider1.populateChunk(iChunkProvider2, var17, integer3, integer4 - 1);
         }
      }

      if (var12 && var8 && var11) {
         Chunk var18 = iChunkProvider1.provideChunk(integer3 - 1, integer4 - 1);
         if (!var18.isTerrainPopulated) {
            iChunkProvider1.populate(iChunkProvider2, integer3 - 1, integer4 - 1);
         } else {
            iChunkProvider1.populateChunk(iChunkProvider2, var18, integer3 - 1, integer4 - 1);
         }
      }
   }

   public BlockPos getPrecipitationHeight(BlockPos blockPos) {
      int var5 = blockPos.getX() & 15;
      int var6 = blockPos.getZ() & 15;
      int var7 = var5 | var6 << 4;
      BlockPos var8 = new BlockPos(blockPos.getX(), this.precipitationHeightMap[var7], blockPos.getZ());
      if (var8.getY() == -999) {
         int var9 = this.getTopFilledSegment() + 15;
         var8 = new BlockPos(blockPos.getX(), var9, blockPos.getZ());
         int var10 = -1;

         while (var8.getY() > 0 && var10 == -1) {
            Material var12 = this.getBlock(var8).getMaterial();
            if (!var12.blocksMovement() && !var12.isLiquid()) {
               var8 = var8.down();
            } else {
               var10 = var8.getY() + 1;
            }
         }

         this.precipitationHeightMap[var7] = var10;
      }

      return new BlockPos(blockPos.getX(), this.precipitationHeightMap[var7], blockPos.getZ());
   }

   public void func_150804_b(boolean boolean1) {
      if (this.isGapLightingUpdated && !this.worldObj.provider.getHasNoSky() && !boolean1) {
         this.recheckGaps(this.worldObj.isRemote);
      }

      this.field_150815_m = true;
      if (!this.isLightPopulated && this.isTerrainPopulated) {
         this.func_150809_p();
      }

      while (!this.tileEntityPosQueue.isEmpty()) {
         BlockPos var5 = this.tileEntityPosQueue.poll();
         if (this.getTileEntity(var5, Chunk.EnumCreateEntityType.CHECK) == null && this.getBlock(var5).hasTileEntity()) {
            TileEntity var6 = this.createNewTileEntity(var5);
            this.worldObj.setTileEntity(var5, var6);
            this.worldObj.markBlockRangeForRenderUpdate(var5, var5);
         }
      }
   }

   public boolean isPopulated() {
      return this.field_150815_m && this.isTerrainPopulated && this.isLightPopulated;
   }

   public ChunkCoordIntPair getChunkCoordIntPair() {
      return new ChunkCoordIntPair(this.xPosition, this.zPosition);
   }

   public boolean getAreLevelsEmpty(int integer1, int integer2) {
      if (integer1 < 0) {
         integer1 = 0;
      }

      if (integer2 >= 256) {
         integer2 = 255;
      }

      for (int var6 = integer1; var6 <= integer2; var6 += 16) {
         ExtendedBlockStorage var7 = this.storageArrays[var6 >> 4];
         if (var7 != null && !var7.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public void setStorageArrays(ExtendedBlockStorage[] arr) {
      if (this.storageArrays.length != arr.length) {
         logger.warn("Could not set level chunk sections, array length is " + arr.length + " instead of " + this.storageArrays.length);
      } else {
         System.arraycopy(arr, 0, this.storageArrays, 0, this.storageArrays.length);
      }
   }

   public void fillChunk(byte[] arr, int integer, boolean boolean3) {
      int var7 = 0;
      boolean var8 = !this.worldObj.provider.getHasNoSky();

      for (int var9 = 0; var9 < this.storageArrays.length; var9++) {
         if ((integer & 1 << var9) != 0) {
            if (this.storageArrays[var9] == null) {
               this.storageArrays[var9] = new ExtendedBlockStorage(var9 << 4, var8);
            }

            char[] var10 = this.storageArrays[var9].getData();

            for (int var11 = 0; var11 < var10.length; var11++) {
               var10[var11] = (char)((arr[var7 + 1] & 255) << 8 | arr[var7] & 0xFF);
               var7 += 2;
            }
         } else if (boolean3 && this.storageArrays[var9] != null) {
            this.storageArrays[var9] = null;
         }
      }

      for (int var12 = 0; var12 < this.storageArrays.length; var12++) {
         if ((integer & 1 << var12) != 0 && this.storageArrays[var12] != null) {
            NibbleArray var16 = this.storageArrays[var12].getBlocklightArray();
            System.arraycopy(arr, var7, var16.getData(), 0, var16.getData().length);
            var7 += var16.getData().length;
         }
      }

      if (var8) {
         for (int var13 = 0; var13 < this.storageArrays.length; var13++) {
            if ((integer & 1 << var13) != 0 && this.storageArrays[var13] != null) {
               NibbleArray var17 = this.storageArrays[var13].getSkylightArray();
               System.arraycopy(arr, var7, var17.getData(), 0, var17.getData().length);
               var7 += var17.getData().length;
            }
         }
      }

      if (boolean3) {
         System.arraycopy(arr, var7, this.blockBiomeArray, 0, this.blockBiomeArray.length);
         int var10000 = var7 + this.blockBiomeArray.length;
      }

      for (int var14 = 0; var14 < this.storageArrays.length; var14++) {
         if (this.storageArrays[var14] != null && (integer & 1 << var14) != 0) {
            this.storageArrays[var14].removeInvalidBlocks();
         }
      }

      this.isLightPopulated = true;
      this.isTerrainPopulated = true;
      this.generateHeightMap();
      Iterator var15 = this.chunkTileEntityMap.values().iterator();

      while (var15.hasNext()) {
         ((TileEntity)var15.next()).updateContainingBlockInfo();
      }
   }

   public BiomeGenBase getBiome(BlockPos blockPos, WorldChunkManager worldChunkManager) {
      int var6 = blockPos.getX() & 15;
      int var7 = blockPos.getZ() & 15;
      int var8 = this.blockBiomeArray[var7 << 4 | var6] & 255;
      if (var8 == 255) {
         var8 = worldChunkManager.getBiomeGenerator(blockPos, BiomeGenBase.plains).biomeID;
         this.blockBiomeArray[var7 << 4 | var6] = (byte)(var8 & 0xFF);
      }

      BiomeGenBase var9 = BiomeGenBase.getBiome(var8);
      return var9 == null ? BiomeGenBase.plains : var9;
   }

   public byte[] getBiomeArray() {
      return this.blockBiomeArray;
   }

   public void setBiomeArray(byte[] arr) {
      if (this.blockBiomeArray.length != arr.length) {
         logger.warn("Could not set level chunk biomes, array length is " + arr.length + " instead of " + this.blockBiomeArray.length);
      } else {
         System.arraycopy(arr, 0, this.blockBiomeArray, 0, this.blockBiomeArray.length);
      }
   }

   public void resetRelightChecks() {
      this.queuedLightChecks = 0;
   }

   public void enqueueRelightChecks() {
      BlockPos var4 = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);

      for (int var5 = 0; var5 < 8; var5++) {
         if (this.queuedLightChecks >= 4096) {
            return;
         }

         int var6 = this.queuedLightChecks % 16;
         int var7 = this.queuedLightChecks / 16 % 16;
         int var8 = this.queuedLightChecks / 256;
         this.queuedLightChecks++;

         for (int var9 = 0; var9 < 16; var9++) {
            BlockPos var10 = var4.add(var7, (var6 << 4) + var9, var8);
            boolean var11 = var9 == 0 || var9 == 15 || var7 == 0 || var7 == 15 || var8 == 0 || var8 == 15;
            if (this.storageArrays[var6] == null && var11
               || this.storageArrays[var6] != null && this.storageArrays[var6].getBlockByExtId(var7, var9, var8).getMaterial() == Material.air) {
               for (EnumFacing var15 : EnumFacing.values()) {
                  BlockPos var16 = var10.offset(var15);
                  if (this.worldObj.getBlockState(var16).getBlock().getLightValue() > 0) {
                     this.worldObj.checkLight(var16);
                  }
               }

               this.worldObj.checkLight(var10);
            }
         }
      }
   }

   public void func_150809_p() {
      this.isTerrainPopulated = true;
      this.isLightPopulated = true;
      BlockPos var4 = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
      if (!this.worldObj.provider.getHasNoSky()) {
         if (this.worldObj.Method326(var4.add(-1, 0, -1), var4.add(16, this.worldObj.getSeaLevel(), 16))) {
            label44:
            for (int var5 = 0; var5 < 16; var5++) {
               for (int var6 = 0; var6 < 16; var6++) {
                  if (!this.func_150811_f(var5, var6)) {
                     this.isLightPopulated = false;
                     break label44;
                  }
               }
            }

            if (this.isLightPopulated) {
               for (EnumFacing var9 : EnumFacing.Plane.HORIZONTAL) {
                  int var7 = var9.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE ? 16 : 1;
                  this.worldObj.getChunkFromBlockCoords(var4.offset(var9, var7)).func_180700_a(var9.getOpposite());
               }

               this.func_177441_y();
            }
         } else {
            this.isLightPopulated = false;
         }
      }
   }

   private void func_177441_y() {
      Arrays.fill(this.updateSkylightColumns, true);
      this.recheckGaps(false);
   }

   private void func_180700_a(EnumFacing enumFacing) {
      if (this.isTerrainPopulated) {
         if (enumFacing == EnumFacing.EAST) {
            for (int var5 = 0; var5 < 16; var5++) {
               this.func_150811_f(15, var5);
            }
         } else if (enumFacing == EnumFacing.WEST) {
            for (int var6 = 0; var6 < 16; var6++) {
               this.func_150811_f(0, var6);
            }
         } else if (enumFacing == EnumFacing.SOUTH) {
            for (int var7 = 0; var7 < 16; var7++) {
               this.func_150811_f(var7, 15);
            }
         } else if (enumFacing == EnumFacing.NORTH) {
            for (int var8 = 0; var8 < 16; var8++) {
               this.func_150811_f(var8, 0);
            }
         }
      }
   }

   private boolean func_150811_f(int integer1, int integer2) {
      int var6 = this.getTopFilledSegment();
      boolean var7 = false;
      boolean var8 = false;
      BlockPos.MutableBlockPos var9 = new BlockPos.MutableBlockPos((this.xPosition << 4) + integer1, 0, (this.zPosition << 4) + integer2);

      for (int var10 = var6 + 16 - 1; var10 > this.worldObj.getSeaLevel() || var10 > 0 && !var8; var10--) {
         var9.set(var9.getX(), var10, var9.getZ());
         int var11 = this.getBlockLightOpacity(var9);
         if (var11 == 255 && var9.getY() < this.worldObj.getSeaLevel()) {
            var8 = true;
         }

         if (!var7 && var11 > 0) {
            var7 = true;
         } else if (var7 && var11 == 0 && !this.worldObj.checkLight(var9)) {
            return false;
         }
      }

      for (int var12 = var9.getY(); var12 > 0; var12--) {
         var9.set(var9.getX(), var12, var9.getZ());
         if (this.getBlock(var9).getLightValue() > 0) {
            this.worldObj.checkLight(var9);
         }
      }

      return true;
   }

   public boolean isLoaded() {
      return this.isChunkLoaded;
   }

   public void setChunkLoaded(boolean boolean1) {
      this.isChunkLoaded = boolean1;
   }

   public World getWorld() {
      return this.worldObj;
   }

   public int[] getHeightMap() {
      return this.heightMap;
   }

   public void setHeightMap(int[] arr) {
      if (this.heightMap.length != arr.length) {
         logger.warn("Could not set level chunk heightmap, array length is " + arr.length + " instead of " + this.heightMap.length);
      } else {
         System.arraycopy(arr, 0, this.heightMap, 0, this.heightMap.length);
      }
   }

   public Map<BlockPos, TileEntity> getTileEntityMap() {
      return this.chunkTileEntityMap;
   }

   public ClassInheritanceMultiMap<Entity>[] getEntityLists() {
      return this.entityLists;
   }

   public boolean isTerrainPopulated() {
      return this.isTerrainPopulated;
   }

   public void setTerrainPopulated(boolean boolean1) {
      this.isTerrainPopulated = boolean1;
   }

   public boolean isLightPopulated() {
      return this.isLightPopulated;
   }

   public void setLightPopulated(boolean boolean1) {
      this.isLightPopulated = boolean1;
   }

   public void setModified(boolean boolean1) {
      this.isModified = boolean1;
   }

   public void setHasEntities(boolean boolean1) {
      this.hasEntities = boolean1;
   }

   public void setLastSaveTime(long long1) {
      this.lastSaveTime = long1;
   }

   public int getLowestHeight() {
      return this.heightMapMinimum;
   }

   public long getInhabitedTime() {
      return this.inhabitedTime;
   }

   public void setInhabitedTime(long long1) {
      this.inhabitedTime = long1;
   }

   public enum EnumCreateEntityType {
      IMMEDIATE,
      QUEUED,
      CHECK;

      private static Chunk.EnumCreateEntityType[] Method760() {
         return new Chunk.EnumCreateEntityType[]{IMMEDIATE, QUEUED, CHECK};
      }
   }
}
