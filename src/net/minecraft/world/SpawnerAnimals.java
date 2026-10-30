package net.minecraft.world;

import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.optifine.BlockPosM;

public final class SpawnerAnimals {
   private static final int MOB_COUNT_DIV = (int)Math.pow(17.0, 2.0);
   private final Set<ChunkCoordIntPair> eligibleChunksForSpawning = Sets.newHashSet();
   private final Map<Class, EntityLiving> mapSampleEntitiesByClass = new HashMap<>();
   private int lastPlayerChunkX = Integer.MAX_VALUE;
   private int lastPlayerChunkZ = Integer.MAX_VALUE;
   private int countChunkPos;

   public int findChunksForSpawning(WorldServer worldServer, boolean boolean2, boolean boolean3, boolean boolean4) {
      if (!boolean2 && !boolean3) {
         return 0;
      }

      boolean var8 = true;
      EntityPlayer var9 = null;
      if (worldServer.playerEntities.size() == 1) {
         var9 = worldServer.playerEntities.get(0);
         if (this.eligibleChunksForSpawning.size() > 0 && var9 != null && var9.W == this.lastPlayerChunkX && var9.Y == this.lastPlayerChunkZ) {
            var8 = false;
         }
      }

      if (var8) {
         this.eligibleChunksForSpawning.clear();
         int var10 = 0;

         for (EntityPlayer var12 : worldServer.playerEntities) {
            if (!var12.isSpectator()) {
               int var13 = MathHelper.floor_double(var12.posX / 16.0);
               int var14 = MathHelper.floor_double(var12.posZ / 16.0);
               byte var15 = 8;

               for (int var16 = -8; var16 <= var15; var16++) {
                  for (int var17 = -8; var17 <= var15; var17++) {
                     boolean var18 = var16 == -8 || var16 == var15 || var17 == -8 || var17 == var15;
                     ChunkCoordIntPair var19 = new ChunkCoordIntPair(var16 + var13, var17 + var14);
                     if (!this.eligibleChunksForSpawning.contains(var19)) {
                        var10++;
                        if (!var18 && worldServer.getWorldBorder().contains(var19)) {
                           this.eligibleChunksForSpawning.add(var19);
                        }
                     }
                  }
               }
            }
         }

         this.countChunkPos = var10;
         if (var9 != null) {
            this.lastPlayerChunkX = var9.W;
            this.lastPlayerChunkZ = var9.Y;
         }
      }

      int var42 = 0;
      BlockPos var43 = worldServer.getSpawnPoint();
      BlockPosM var44 = new BlockPosM(0, 0, 0);
      new BlockPos.MutableBlockPos();

      for (EnumCreatureType var48 : EnumCreatureType.values()) {
         if ((!var48.getPeacefulCreature() || boolean3) && (var48.getPeacefulCreature() || boolean2) && (!var48.getAnimal() || boolean4)) {
            int var49 = worldServer.countEntities(var48.getCreatureClass());
            int var50 = var48.getMaxNumberOfCreature() * this.countChunkPos / MOB_COUNT_DIV;
            if (var49 <= var50) {
               label150:
               for (ChunkCoordIntPair var20 : this.eligibleChunksForSpawning) {
                  BlockPosM var21 = getRandomChunkPosition(worldServer, var20.chunkXPos, var20.chunkZPos, var44);
                  int var22 = var21.getX();
                  int var23 = var21.getY();
                  int var24 = var21.getZ();
                  if (!worldServer.getBlockState(var21).getBlock().isNormalCube()) {
                     int var26 = 0;

                     for (int var27 = 0; var27 < 3; var27++) {
                        int var28 = var22;
                        int var29 = var23;
                        int var30 = var24;
                        byte var31 = 6;
                        BiomeGenBase.SpawnListEntry var32 = null;
                        IEntityLivingData var33 = null;

                        for (int var34 = 0; var34 < 4; var34++) {
                           var28 += worldServer.rand.nextInt(var31) - worldServer.rand.nextInt(var31);
                           var29 += worldServer.rand.nextInt(1) - worldServer.rand.nextInt(1);
                           var30 += worldServer.rand.nextInt(var31) - worldServer.rand.nextInt(var31);
                           BlockPos var35 = new BlockPos(var28, var29, var30);
                           float var36 = var28 + 0.5F;
                           float var37 = var30 + 0.5F;
                           if (!worldServer.isAnyPlayerWithinRangeAt(var36, var29, var37, 24.0) && var43.distanceSq(var36, var29, var37) >= 576.0) {
                              if (var32 == null) {
                                 var32 = worldServer.getSpawnListEntryForTypeAt(var48, var35);
                                 if (var32 == null) {
                                    break;
                                 }
                              }

                              if (worldServer.canCreatureTypeSpawnHere(var48, var32, var35)
                                 && canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementForEntity(var32.entityClass), worldServer, var35)) {
                                 EntityLiving var38;
                                 try {
                                    var38 = this.mapSampleEntitiesByClass.get(var32.entityClass);
                                    if (var38 == null) {
                                       var38 = var32.entityClass.getConstructor(World.class).newInstance(worldServer);
                                       this.mapSampleEntitiesByClass.put(var32.entityClass, var38);
                                    }
                                 } catch (Exception var41) {
                                    var41.printStackTrace();
                                    return var42;
                                 }

                                 var38.setLocationAndAngles(var36, var29, var37, worldServer.rand.nextFloat() * 360.0F, 0.0F);
                                 boolean var39 = var38.getCanSpawnHere() && var38.isNotColliding();
                                 if (var39) {
                                    this.mapSampleEntitiesByClass.remove(var32.entityClass);
                                    var33 = var38.onInitialSpawn(worldServer.getDifficultyForLocation(new BlockPos(var38)), var33);
                                    if (var38.isNotColliding()) {
                                       var26++;
                                       worldServer.spawnEntityInWorld(var38);
                                    }

                                    int var40 = var38.getMaxSpawnedInChunk();
                                    if (var26 >= var40) {
                                       continue label150;
                                    }
                                 }

                                 var42 += var26;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var42;
   }

   private static BlockPos getRandomChunkPosition(World world, int integer2, int integer3) {
      Chunk var6 = world.getChunkFromChunkCoords(integer2, integer3);
      int var7 = integer2 * 16 + world.rand.nextInt(16);
      int var8 = integer3 * 16 + world.rand.nextInt(16);
      int var9 = MathHelper.roundUp(var6.getHeight(new BlockPos(var7, 0, var8)) + 1, 16);
      int var10 = world.rand.nextInt(var9 > 0 ? var9 : var6.getTopFilledSegment() + 16 - 1);
      return new BlockPos(var7, var10, var8);
   }

   private static BlockPosM getRandomChunkPosition(World world, int integer2, int integer3, BlockPosM blockPosM) {
      Chunk var7 = world.getChunkFromChunkCoords(integer2, integer3);
      int var8 = integer2 * 16 + world.rand.nextInt(16);
      int var9 = integer3 * 16 + world.rand.nextInt(16);
      int var10 = MathHelper.roundUp(var7.getHeightValue(var8 & 15, var9 & 15) + 1, 16);
      int var11 = world.rand.nextInt(var10 > 0 ? var10 : var7.getTopFilledSegment() + 16 - 1);
      blockPosM.setXyz(var8, var11, var9);
      return blockPosM;
   }

   public static boolean canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType spawnPlacementType, World world, BlockPos blockPos) {
      if (!world.getWorldBorder().contains(blockPos)) {
         return false;
      }

      if (spawnPlacementType == null) {
         return false;
      }

      Block var6 = world.getBlockState(blockPos).getBlock();
      if (spawnPlacementType == EntityLiving.SpawnPlacementType.IN_WATER) {
         return var6.getMaterial().isLiquid()
            && world.getBlockState(blockPos.down()).getBlock().getMaterial().isLiquid()
            && !world.getBlockState(blockPos.up()).getBlock().isNormalCube();
      }

      BlockPos var7 = blockPos.down();
      world.getBlockState(var7);
      boolean var9 = World.doesBlockHaveSolidTopSurface(world, var7);
      if (!var9) {
         return false;
      }

      Block var10 = world.getBlockState(var7).getBlock();
      boolean var11 = var10 != Blocks.bedrock && var10 != Blocks.barrier;
      return var11 && !var6.isNormalCube() && !var6.getMaterial().isLiquid() && !world.getBlockState(blockPos.up()).getBlock().isNormalCube();
   }

   public static void performWorldGenSpawning(World world, BiomeGenBase biomeGenBase, int integer3, int integer4, int integer5, int integer6, Random random) {
      List<BiomeGenBase.SpawnListEntry> var10 = biomeGenBase.getSpawnableList(EnumCreatureType.CREATURE);
      if (!var10.isEmpty()) {
         while (random.nextFloat() < biomeGenBase.getSpawningChance()) {
            BiomeGenBase.SpawnListEntry var11 = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(world.rand, var10);
            int var12 = var11.minGroupCount + random.nextInt(1 + var11.maxGroupCount - var11.minGroupCount);
            IEntityLivingData var13 = null;
            int var14 = integer3 + random.nextInt(integer5);
            int var15 = integer4 + random.nextInt(integer6);
            int var16 = var14;
            int var17 = var15;

            for (int var18 = 0; var18 < var12; var18++) {
               boolean var19 = false;

               for (int var20 = 0; !var19 && var20 < 4; var20++) {
                  BlockPos var21 = world.getTopSolidOrLiquidBlock(new BlockPos(var14, 0, var15));
                  if (canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, world, var21)) {
                     EntityLiving var22;
                     try {
                        var22 = var11.entityClass.getConstructor(World.class).newInstance(world);
                     } catch (Exception var23) {
                        var23.printStackTrace();
                        continue;
                     }

                     var22.setLocationAndAngles(var14 + 0.5F, var21.getY(), var15 + 0.5F, random.nextFloat() * 360.0F, 0.0F);
                     world.spawnEntityInWorld(var22);
                     var13 = var22.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(var22)), var13);
                     var19 = true;
                  }

                  var14 += random.nextInt(5) - random.nextInt(5);

                  for (var15 += random.nextInt(5) - random.nextInt(5);
                     var14 < integer3 || var14 >= integer3 + integer5 || var15 < integer4 || var15 >= integer4 + integer5;
                     var15 = var17 + random.nextInt(5) - random.nextInt(5)
                  ) {
                     var14 = var16 + random.nextInt(5) - random.nextInt(5);
                  }
               }
            }
         }
      }
   }
}
