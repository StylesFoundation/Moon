package net.minecraft.server.management;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.optifine.ChunkPosComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerManager {
   private static final Logger pmLogger = LogManager.getLogger();
   private final WorldServer theWorldServer;
   private final List<EntityPlayerMP> players = Lists.newArrayList();
   private final LongHashMap<PlayerManager.PlayerInstance> playerInstances = new LongHashMap<>();
   private final List<PlayerManager.PlayerInstance> playerInstancesToUpdate = Lists.newArrayList();
   private final List<PlayerManager.PlayerInstance> playerInstanceList = Lists.newArrayList();
   private int playerViewRadius;
   private long previousTotalWorldTime;
   private final int[][] xzDirectionsConst = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
   private final Map<EntityPlayerMP, Set<ChunkCoordIntPair>> mapPlayerPendingEntries = new HashMap<>();

   public PlayerManager(WorldServer worldServer) {
      this.theWorldServer = worldServer;
      this.setPlayerViewRadius(worldServer.getMinecraftServer().getConfigurationManager().getViewDistance());
   }

   public WorldServer getWorldServer() {
      return this.theWorldServer;
   }

   public void updatePlayerInstances() {
      Set<Entry<EntityPlayerMP, Set<ChunkCoordIntPair>>> var4 = this.mapPlayerPendingEntries.entrySet();
      Iterator<Entry<EntityPlayerMP, Set<ChunkCoordIntPair>>> var5 = var4.iterator();

      while (var5.hasNext()) {
         Entry<EntityPlayerMP, Set<ChunkCoordIntPair>> var6 = var5.next();
         Set<ChunkCoordIntPair> var7 = var6.getValue();
         if (!var7.isEmpty()) {
            EntityPlayerMP var8 = var6.getKey();
            if (var8.worldObj != this.theWorldServer) {
               var5.remove();
            } else {
               int var9 = this.playerViewRadius / 3 + 1;
               if (!Config.as()) {
                  var9 = this.playerViewRadius * 2 + 1;
               }

               for (ChunkCoordIntPair var11 : this.getNearest(var7, var8, var9)) {
                  PlayerManager.PlayerInstance var12 = this.getPlayerInstance(var11.chunkXPos, var11.chunkZPos, true);
                  var12.addPlayer(var8);
                  var7.remove(var11);
               }
            }
         }
      }

      long var13 = this.theWorldServer.getTotalWorldTime();
      if (var13 - this.previousTotalWorldTime > 8000L) {
         this.previousTotalWorldTime = var13;

         for (PlayerManager.PlayerInstance var17 : this.playerInstanceList) {
            var17.onUpdate();
            var17.processChunk();
         }
      } else {
         for (PlayerManager.PlayerInstance var18 : this.playerInstancesToUpdate) {
            var18.onUpdate();
         }
      }

      this.playerInstancesToUpdate.clear();
      if (this.players.isEmpty()) {
         WorldProvider var16 = this.theWorldServer.provider;
         if (!var16.canRespawnHere()) {
            this.theWorldServer.theChunkProviderServer.unloadAllChunks();
         }
      }
   }

   public boolean hasPlayerInstance(int integer1, int integer2) {
      long var5 = integer1 + 2147483647L | integer2 + 2147483647L << 32;
      return this.playerInstances.getValueByKey(var5) != null;
   }

   private PlayerManager.PlayerInstance getPlayerInstance(int integer1, int integer2, boolean boolean3) {
      long var7 = integer1 + 2147483647L | integer2 + 2147483647L << 32;
      PlayerManager.PlayerInstance var9 = this.playerInstances.getValueByKey(var7);
      if (var9 == null && boolean3) {
         var9 = new PlayerManager.PlayerInstance(integer1, integer2);
         this.playerInstances.add(var7, var9);
         this.playerInstanceList.add(var9);
      }

      return var9;
   }

   public void markBlockForUpdate(BlockPos blockPos) {
      int var5 = blockPos.getX() >> 4;
      int var6 = blockPos.getZ() >> 4;
      PlayerManager.PlayerInstance var7 = this.getPlayerInstance(var5, var6, false);
      if (var7 != null) {
         var7.flagChunkForUpdate(blockPos.getX() & 15, blockPos.getY(), blockPos.getZ() & 15);
      }
   }

   public void addPlayer(EntityPlayerMP entityPlayerMP) {
      int var5 = (int)entityPlayerMP.posX >> 4;
      int var6 = (int)entityPlayerMP.posZ >> 4;
      entityPlayerMP.managedPosX = entityPlayerMP.posX;
      entityPlayerMP.managedPosZ = entityPlayerMP.posZ;
      int var7 = Math.min(this.playerViewRadius, 8);
      int var8 = var5 - var7;
      int var9 = var5 + var7;
      int var10 = var6 - var7;
      int var11 = var6 + var7;
      Set var12 = this.getPendingEntriesSafe(entityPlayerMP);

      for (int var13 = var5 - this.playerViewRadius; var13 <= var5 + this.playerViewRadius; var13++) {
         for (int var14 = var6 - this.playerViewRadius; var14 <= var6 + this.playerViewRadius; var14++) {
            if (var13 >= var8 && var13 <= var9 && var14 >= var10 && var14 <= var11) {
               this.getPlayerInstance(var13, var14, true).addPlayer(entityPlayerMP);
            } else {
               var12.add(new ChunkCoordIntPair(var13, var14));
            }
         }
      }

      this.players.add(entityPlayerMP);
      this.filterChunkLoadQueue(entityPlayerMP);
   }

   public void filterChunkLoadQueue(EntityPlayerMP entityPlayerMP) {
      ArrayList var5 = Lists.newArrayList(entityPlayerMP.loadedChunks);
      int var6 = 0;
      int var7 = this.playerViewRadius;
      int var8 = (int)entityPlayerMP.posX >> 4;
      int var9 = (int)entityPlayerMP.posZ >> 4;
      int var10 = 0;
      int var11 = 0;
      ChunkCoordIntPair var12 = this.getPlayerInstance(var8, var9, true).chunkCoords;
      entityPlayerMP.loadedChunks.clear();
      if (var5.contains(var12)) {
         entityPlayerMP.loadedChunks.add(var12);
      }

      for (int var13 = 1; var13 <= var7 * 2; var13++) {
         for (int var14 = 0; var14 < 2; var14++) {
            int[] var15 = this.xzDirectionsConst[var6++ % 4];

            for (int var16 = 0; var16 < var13; var16++) {
               var10 += var15[0];
               var11 += var15[1];
               var12 = this.getPlayerInstance(var8 + var10, var9 + var11, true).chunkCoords;
               if (var5.contains(var12)) {
                  entityPlayerMP.loadedChunks.add(var12);
               }
            }
         }
      }

      var6 %= 4;

      for (int var20 = 0; var20 < var7 * 2; var20++) {
         var10 += this.xzDirectionsConst[var6][0];
         var11 += this.xzDirectionsConst[var6][1];
         var12 = this.getPlayerInstance(var8 + var10, var9 + var11, true).chunkCoords;
         if (var5.contains(var12)) {
            entityPlayerMP.loadedChunks.add(var12);
         }
      }
   }

   public void removePlayer(EntityPlayerMP entityPlayerMP) {
      this.mapPlayerPendingEntries.remove(entityPlayerMP);
      int var5 = (int)entityPlayerMP.managedPosX >> 4;
      int var6 = (int)entityPlayerMP.managedPosZ >> 4;

      for (int var7 = var5 - this.playerViewRadius; var7 <= var5 + this.playerViewRadius; var7++) {
         for (int var8 = var6 - this.playerViewRadius; var8 <= var6 + this.playerViewRadius; var8++) {
            PlayerManager.PlayerInstance var9 = this.getPlayerInstance(var7, var8, false);
            if (var9 != null) {
               var9.removePlayer(entityPlayerMP);
            }
         }
      }

      this.players.remove(entityPlayerMP);
   }

   private boolean overlaps(int integer1, int integer2, int integer3, int integer4, int integer5) {
      int var9 = integer1 - integer3;
      int var10 = integer2 - integer4;
      return var9 >= -integer5 && var9 <= integer5 && var10 >= -integer5 && var10 <= integer5;
   }

   public void updateMountedMovingPlayer(EntityPlayerMP entityPlayerMP) {
      int var5 = (int)entityPlayerMP.posX >> 4;
      int var6 = (int)entityPlayerMP.posZ >> 4;
      double var7 = entityPlayerMP.managedPosX - entityPlayerMP.posX;
      double var9 = entityPlayerMP.managedPosZ - entityPlayerMP.posZ;
      double var11 = var7 * var7 + var9 * var9;
      if (var11 >= 64.0) {
         int var13 = (int)entityPlayerMP.managedPosX >> 4;
         int var14 = (int)entityPlayerMP.managedPosZ >> 4;
         int var15 = this.playerViewRadius;
         int var16 = var5 - var13;
         int var17 = var6 - var14;
         if (var16 != 0 || var17 != 0) {
            Set var18 = this.getPendingEntriesSafe(entityPlayerMP);

            for (int var19 = var5 - var15; var19 <= var5 + var15; var19++) {
               for (int var20 = var6 - var15; var20 <= var6 + var15; var20++) {
                  if (!this.overlaps(var19, var20, var13, var14, var15)) {
                     if (Config.as()) {
                        var18.add(new ChunkCoordIntPair(var19, var20));
                     } else {
                        this.getPlayerInstance(var19, var20, true).addPlayer(entityPlayerMP);
                     }
                  }

                  if (!this.overlaps(var19 - var16, var20 - var17, var5, var6, var15)) {
                     var18.remove(new ChunkCoordIntPair(var19 - var16, var20 - var17));
                     PlayerManager.PlayerInstance var21 = this.getPlayerInstance(var19 - var16, var20 - var17, false);
                     if (var21 != null) {
                        var21.removePlayer(entityPlayerMP);
                     }
                  }
               }
            }

            this.filterChunkLoadQueue(entityPlayerMP);
            entityPlayerMP.managedPosX = entityPlayerMP.posX;
            entityPlayerMP.managedPosZ = entityPlayerMP.posZ;
         }
      }
   }

   public boolean isPlayerWatchingChunk(EntityPlayerMP entityPlayerMP, int integer2, int integer3) {
      PlayerManager.PlayerInstance var7 = this.getPlayerInstance(integer2, integer3, false);
      return var7 != null && var7.playersWatchingChunk.contains(entityPlayerMP) && !entityPlayerMP.loadedChunks.contains(var7.chunkCoords);
   }

   public void setPlayerViewRadius(int integer) {
      integer = MathHelper.clamp_int(integer, 3, 64);
      if (integer != this.playerViewRadius) {
         int var5 = integer - this.playerViewRadius;

         for (EntityPlayerMP var7 : Lists.newArrayList(this.players)) {
            int var8 = (int)var7.posX >> 4;
            int var9 = (int)var7.posZ >> 4;
            Set var10 = this.getPendingEntriesSafe(var7);
            if (var5 > 0) {
               for (int var15 = var8 - integer; var15 <= var8 + integer; var15++) {
                  for (int var16 = var9 - integer; var16 <= var9 + integer; var16++) {
                     if (Config.as()) {
                        var10.add(new ChunkCoordIntPair(var15, var16));
                     } else {
                        PlayerManager.PlayerInstance var17 = this.getPlayerInstance(var15, var16, true);
                        if (!var17.playersWatchingChunk.contains(var7)) {
                           var17.addPlayer(var7);
                        }
                     }
                  }
               }
            } else {
               for (int var11 = var8 - this.playerViewRadius; var11 <= var8 + this.playerViewRadius; var11++) {
                  for (int var12 = var9 - this.playerViewRadius; var12 <= var9 + this.playerViewRadius; var12++) {
                     if (!this.overlaps(var11, var12, var8, var9, integer)) {
                        var10.remove(new ChunkCoordIntPair(var11, var12));
                        PlayerManager.PlayerInstance var13 = this.getPlayerInstance(var11, var12, true);
                        if (var13 != null) {
                           var13.removePlayer(var7);
                        }
                     }
                  }
               }
            }
         }

         this.playerViewRadius = integer;
      }
   }

   public static int getFurthestViewableBlock(int integer) {
      return integer * 16 - 16;
   }

   private PriorityQueue<ChunkCoordIntPair> getNearest(Set<ChunkCoordIntPair> set, EntityPlayerMP entityPlayerMP, int integer) {
      float var7 = entityPlayerMP.rotationYaw + 90.0F;

      while (var7 <= -180.0F) {
         var7 += 360.0F;
      }

      while (var7 > 180.0F) {
         var7 -= 360.0F;
      }

      double var8 = var7 * (Math.PI / 180.0);
      double var12 = entityPlayerMP.rotationPitch * (Math.PI / 180.0);
      ChunkPosComparator var14 = new ChunkPosComparator(entityPlayerMP.W, entityPlayerMP.Y, var8, var12);
      Comparator var15 = Collections.reverseOrder(var14);
      PriorityQueue var16 = new PriorityQueue(integer, var15);

      for (ChunkCoordIntPair var18 : set) {
         if (var16.size() < integer) {
            var16.add(var18);
         } else {
            ChunkCoordIntPair var19 = (ChunkCoordIntPair)var16.peek();
            if (var14.compare(var18, var19) < 0) {
               var16.remove();
               var16.add(var18);
            }
         }
      }

      return var16;
   }

   private Set<ChunkCoordIntPair> getPendingEntriesSafe(EntityPlayerMP entityPlayerMP) {
      Set var5 = this.mapPlayerPendingEntries.get(entityPlayerMP);
      if (var5 != null) {
         return var5;
      }

      int var6 = Math.min(this.playerViewRadius, 8);
      int var7 = this.playerViewRadius * 2 + 1;
      int var8 = var6 * 2 + 1;
      int var9 = Math.max(var7 * var7 - var8 * var8, 16);
      HashSet var10 = new HashSet(var9);
      this.mapPlayerPendingEntries.put(entityPlayerMP, var10);
      return var10;
   }

   class PlayerInstance {
      private final List<EntityPlayerMP> playersWatchingChunk = Lists.newArrayList();
      private final ChunkCoordIntPair chunkCoords;
      private final short[] locationOfBlockChange = new short[64];
      private int numBlocksToUpdate;
      private int flagsYAreasToUpdate;
      private long previousWorldTime;

      public PlayerInstance(int integer2, int integer3) {
         this.chunkCoords = new ChunkCoordIntPair(integer2, integer3);
         PlayerManager.this.getWorldServer().theChunkProviderServer.loadChunk(integer2, integer3);
      }

      public void addPlayer(EntityPlayerMP entityPlayerMP) {
         if (this.playersWatchingChunk.contains(entityPlayerMP)) {
            PlayerManager.pmLogger
               .debug("Failed to add player. {} already is in chunk {}, {}", entityPlayerMP, this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
         } else {
            if (this.playersWatchingChunk.isEmpty()) {
               this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
            }

            this.playersWatchingChunk.add(entityPlayerMP);
            entityPlayerMP.loadedChunks.add(this.chunkCoords);
         }
      }

      public void removePlayer(EntityPlayerMP entityPlayerMP) {
         if (this.playersWatchingChunk.contains(entityPlayerMP)) {
            Chunk var5 = PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
            if (var5.isPopulated()) {
               entityPlayerMP.playerNetServerHandler.sendPacket(new S21PacketChunkData(var5, true, 0));
            }

            this.playersWatchingChunk.remove(entityPlayerMP);
            entityPlayerMP.loadedChunks.remove(this.chunkCoords);
            if (this.playersWatchingChunk.isEmpty()) {
               long var6 = this.chunkCoords.chunkXPos + 2147483647L | this.chunkCoords.chunkZPos + 2147483647L << 32;
               this.increaseInhabitedTime(var5);
               PlayerManager.this.playerInstances.remove(var6);
               PlayerManager.this.playerInstanceList.remove(this);
               if (this.numBlocksToUpdate > 0) {
                  PlayerManager.this.playerInstancesToUpdate.remove(this);
               }

               PlayerManager.this.getWorldServer().theChunkProviderServer.dropChunk(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
            }
         }
      }

      public void processChunk() {
         this.increaseInhabitedTime(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos));
      }

      private void increaseInhabitedTime(Chunk chunk) {
         chunk.setInhabitedTime(chunk.getInhabitedTime() + PlayerManager.this.theWorldServer.getTotalWorldTime() - this.previousWorldTime);
         this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
      }

      public void flagChunkForUpdate(int integer1, int integer2, int integer3) {
         if (this.numBlocksToUpdate == 0) {
            PlayerManager.this.playerInstancesToUpdate.add(this);
         }

         this.flagsYAreasToUpdate |= 1 << (integer2 >> 4);
         if (this.numBlocksToUpdate < 64) {
            short var7 = (short)(integer1 << 12 | integer3 << 8 | integer2);

            for (int var8 = 0; var8 < this.numBlocksToUpdate; var8++) {
               if (this.locationOfBlockChange[var8] == var7) {
                  return;
               }
            }

            this.locationOfBlockChange[this.numBlocksToUpdate++] = var7;
         }
      }

      public void sendToAllPlayersWatchingChunk(Packet packet) {
         for (EntityPlayerMP var6 : this.playersWatchingChunk) {
            if (!var6.loadedChunks.contains(this.chunkCoords)) {
               var6.playerNetServerHandler.sendPacket(packet);
            }
         }
      }

      public void onUpdate() {
         if (this.numBlocksToUpdate != 0) {
            if (this.numBlocksToUpdate == 1) {
               int var12 = (this.locationOfBlockChange[0] >> 12 & 15) + this.chunkCoords.chunkXPos * 16;
               int var14 = this.locationOfBlockChange[0] & 255;
               int var16 = (this.locationOfBlockChange[0] >> 8 & 15) + this.chunkCoords.chunkZPos * 16;
               BlockPos var18 = new BlockPos(var12, var14, var16);
               this.sendToAllPlayersWatchingChunk(new S23PacketBlockChange(PlayerManager.this.theWorldServer, var18));
               if (PlayerManager.this.theWorldServer.getBlockState(var18).getBlock().hasTileEntity()) {
                  this.sendTileToAllPlayersWatchingChunk(PlayerManager.this.theWorldServer.getTileEntity(var18));
               }
            } else if (this.numBlocksToUpdate != 64) {
               this.sendToAllPlayersWatchingChunk(
                  new S22PacketMultiBlockChange(
                     this.numBlocksToUpdate,
                     this.locationOfBlockChange,
                     PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos)
                  )
               );

               for (int var11 = 0; var11 < this.numBlocksToUpdate; var11++) {
                  int var13 = (this.locationOfBlockChange[var11] >> 12 & 15) + this.chunkCoords.chunkXPos * 16;
                  int var15 = this.locationOfBlockChange[var11] & 255;
                  int var17 = (this.locationOfBlockChange[var11] >> 8 & 15) + this.chunkCoords.chunkZPos * 16;
                  BlockPos var8 = new BlockPos(var13, var15, var17);
                  if (PlayerManager.this.theWorldServer.getBlockState(var8).getBlock().hasTileEntity()) {
                     this.sendTileToAllPlayersWatchingChunk(PlayerManager.this.theWorldServer.getTileEntity(var8));
                  }
               }
            } else {
               int var4 = this.chunkCoords.chunkXPos * 16;
               int var5 = this.chunkCoords.chunkZPos * 16;
               this.sendToAllPlayersWatchingChunk(
                  new S21PacketChunkData(
                     PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos),
                     false,
                     this.flagsYAreasToUpdate
                  )
               );

               for (int var6 = 0; var6 < 16; var6++) {
                  if ((this.flagsYAreasToUpdate & 1 << var6) != 0) {
                     int var7 = var6 << 4;

                     for (TileEntity var10 : PlayerManager.this.theWorldServer.getTileEntitiesIn(var4, var7, var5, var4 + 16, var7 + 16, var5 + 16)) {
                        this.sendTileToAllPlayersWatchingChunk(var10);
                     }
                  }
               }
            }

            this.numBlocksToUpdate = 0;
            this.flagsYAreasToUpdate = 0;
         }
      }

      private void sendTileToAllPlayersWatchingChunk(TileEntity tileEntity) {
         if (tileEntity != null) {
            Packet var5 = tileEntity.getDescriptionPacket();
            if (var5 != null) {
               this.sendToAllPlayersWatchingChunk(var5);
            }
         }
      }
   }
}
