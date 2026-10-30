package net.minecraft.world.gen.structure;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

public abstract class MapGenStructure extends MapGenBase {
   private MapGenStructureData structureData;
   protected Map<Long, StructureStart> structureMap = Maps.newHashMap();

   public abstract String getStructureName();

   @Override
   protected final void recursiveGenerate(World world, int integer2, int integer3, int integer4, int integer5, ChunkPrimer chunkPrimer) {
      this.initializeStructureData(world);
      if (!this.structureMap.containsKey(ChunkCoordIntPair.chunkXZ2Int(integer2, integer3))) {
         this.rand.nextInt();

         try {
            if (this.canSpawnStructureAtCoords(integer2, integer3)) {
               StructureStart var10 = this.getStructureStart(integer2, integer3);
               this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(integer2, integer3), var10);
               this.setStructureStart(integer2, integer3, var10);
            }
         } catch (Throwable var13) {
            CrashReport var11 = CrashReport.makeCrashReport(var13, "Exception preparing structure feature");
            CrashReportCategory var12 = var11.makeCategory("Feature being prepared");
            var12.addCrashSectionCallable("Is feature chunk", () -> this.canSpawnStructureAtCoords(integer2, integer3) ? "True" : "False");
            var12.addCrashSection("Chunk location", String.format("%d,%d", integer2, integer3));
            var12.addCrashSectionCallable("Chunk pos hash", () -> String.valueOf(ChunkCoordIntPair.chunkXZ2Int(integer2, integer3)));
            var12.addCrashSectionCallable("Structure type", () -> this.getClass().getCanonicalName());
            throw new ReportedException(var11);
         }
      }
   }

   public boolean generateStructure(World world, Random random, ChunkCoordIntPair chunkCoordIntPair) {
      this.initializeStructureData(world);
      int var7 = (chunkCoordIntPair.chunkXPos << 4) + 8;
      int var8 = (chunkCoordIntPair.chunkZPos << 4) + 8;
      boolean var9 = false;

      for (StructureStart var11 : this.structureMap.values()) {
         if (var11.isSizeableStructure() && var11.func_175788_a(chunkCoordIntPair) && var11.getBoundingBox().intersectsWith(var7, var8, var7 + 15, var8 + 15)) {
            var11.generateStructure(world, random, new StructureBoundingBox(var7, var8, var7 + 15, var8 + 15));
            var11.func_175787_b(chunkCoordIntPair);
            var9 = true;
            this.setStructureStart(var11.getChunkPosX(), var11.getChunkPosZ(), var11);
         }
      }

      return var9;
   }

   public boolean func_175795_b(BlockPos blockPos) {
      this.initializeStructureData(this.worldObj);
      return this.func_175797_c(blockPos) != null;
   }

   protected StructureStart func_175797_c(BlockPos blockPos) {
      for (StructureStart var6 : this.structureMap.values()) {
         if (var6.isSizeableStructure() && var6.getBoundingBox().isVecInside(blockPos)) {
            for (StructureComponent var8 : var6.getComponents()) {
               if (var8.getBoundingBox().isVecInside(blockPos)) {
                  return var6;
               }
            }
         }
      }

      return null;
   }

   public boolean isPositionInStructure(World world, BlockPos blockPos) {
      this.initializeStructureData(world);

      for (StructureStart var7 : this.structureMap.values()) {
         if (var7.isSizeableStructure() && var7.getBoundingBox().isVecInside(blockPos)) {
            return true;
         }
      }

      return false;
   }

   public BlockPos getClosestStrongholdPos(World world, BlockPos blockPos) {
      this.worldObj = world;
      this.initializeStructureData(world);
      this.rand.setSeed(world.getSeed());
      long var6 = this.rand.nextLong();
      long var8 = this.rand.nextLong();
      long var10 = (blockPos.getX() >> 4) * var6;
      long var12 = (blockPos.getZ() >> 4) * var8;
      this.rand.setSeed(var10 ^ var12 ^ world.getSeed());
      this.recursiveGenerate(world, blockPos.getX() >> 4, blockPos.getZ() >> 4, 0, 0, null);
      double var14 = Double.MAX_VALUE;
      BlockPos var16 = null;

      for (StructureStart var18 : this.structureMap.values()) {
         if (var18.isSizeableStructure()) {
            BlockPos var20 = var18.getComponents().get(0).getBoundingBoxCenter();
            double var21 = var20.distanceSq(blockPos);
            if (var21 < var14) {
               var14 = var21;
               var16 = var20;
            }
         }
      }

      if (var16 != null) {
         return var16;
      }

      List<BlockPos> var23 = this.getCoordList();
      if (var23 != null) {
         BlockPos var24 = null;

         for (BlockPos var25 : var23) {
            double var26 = var25.distanceSq(blockPos);
            if (var26 < var14) {
               var14 = var26;
               var24 = var25;
            }
         }

         return var24;
      } else {
         return null;
      }
   }

   protected List<BlockPos> getCoordList() {
      return null;
   }

   private void initializeStructureData(World world) {
      if (this.structureData == null) {
         this.structureData = (MapGenStructureData)world.loadItemData(MapGenStructureData.class, this.getStructureName());
         if (this.structureData == null) {
            this.structureData = new MapGenStructureData(this.getStructureName());
            world.setItemData(this.getStructureName(), this.structureData);
         } else {
            NBTTagCompound var5 = this.structureData.getTagCompound();

            for (String var7 : var5.getKeySet()) {
               NBTBase var8 = var5.getTag(var7);
               if (var8.getId() == 10) {
                  NBTTagCompound var9 = (NBTTagCompound)var8;
                  if (var9.hasKey("ChunkX") && var9.hasKey("ChunkZ")) {
                     int var10 = var9.getInteger("ChunkX");
                     int var11 = var9.getInteger("ChunkZ");
                     StructureStart var12 = MapGenStructureIO.getStructureStart(var9, world);
                     if (var12 != null) {
                        this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(var10, var11), var12);
                     }
                  }
               }
            }
         }
      }
   }

   private void setStructureStart(int integer1, int integer2, StructureStart structureStart) {
      this.structureData.writeInstance(structureStart.writeStructureComponentsToNBT(integer1, integer2), integer1, integer2);
      this.structureData.markDirty();
   }

   protected abstract boolean canSpawnStructureAtCoords(int integer1, int integer2);

   protected abstract StructureStart getStructureStart(int integer1, int integer2);
}
