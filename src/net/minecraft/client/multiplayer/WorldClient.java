package net.minecraft.client.multiplayer;

import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecart;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EntityFirework;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveDataMemoryStorage;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.CustomGuis;
import net.optifine.DynamicLights;
import net.optifine.override.PlayerControllerOF;

public class WorldClient extends World {
   private final NetHandlerPlayClient sendQueue;
   private ChunkProviderClient clientChunkProvider;
   private final Set<Entity> entityList = Sets.newHashSet();
   private final Set<Entity> entitySpawnQueue = Sets.newHashSet();
   private final Minecraft mc = Minecraft.getMinecraft();
   private final Set<ChunkCoordIntPair> previousActiveChunkSet = Sets.newHashSet();
   private boolean playerUpdate = false;

   public WorldClient(NetHandlerPlayClient netHandlerPlayClient, WorldSettings worldSettings, int integer, EnumDifficulty enumDifficulty, Profiler profiler) {
      super(new SaveHandlerMP(), new WorldInfo(worldSettings, "MpServer"), WorldProvider.getProviderForDimension(integer), profiler, true);
      this.sendQueue = netHandlerPlayClient;
      this.getWorldInfo().setDifficulty(enumDifficulty);
      this.provider.registerWorld(this);
      this.setSpawnPoint(new BlockPos(8, 64, 8));
      this.chunkProvider = this.createChunkProvider();
      this.mapStorage = new SaveDataMemoryStorage();
      this.calculateInitialSkylight();
      this.calculateInitialWeather();
      if (this.mc.playerController != null && this.mc.playerController.getClass() == PlayerControllerMP.class) {
         this.mc.playerController = new PlayerControllerOF(this.mc, netHandlerPlayClient);
         CustomGuis.setPlayerControllerOF((PlayerControllerOF)this.mc.playerController);
      }
   }

   @Override
   public void tick() {
      super.tick();
      this.setTotalWorldTime(this.getTotalWorldTime() + 1L);
      if (this.getGameRules().getBoolean("doDaylightCycle")) {
         this.setWorldTime(this.getWorldTime() + 1L);
      }

      this.theProfiler.startSection("reEntryProcessing");

      for (int var4 = 0; var4 < 10 && !this.entitySpawnQueue.isEmpty(); var4++) {
         Entity var5 = this.entitySpawnQueue.iterator().next();
         this.entitySpawnQueue.remove(var5);
         if (!this.loadedEntityList.contains(var5)) {
            this.spawnEntityInWorld(var5);
         }
      }

      this.theProfiler.endStartSection("chunkCache");
      this.clientChunkProvider.unloadQueuedChunks();
      this.theProfiler.endStartSection("blocks");
      this.updateBlocks();
      this.theProfiler.endSection();
   }

   public void invalidateBlockReceiveRegion(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
   }

   @Override
   protected IChunkProvider createChunkProvider() {
      this.clientChunkProvider = new ChunkProviderClient(this);
      return this.clientChunkProvider;
   }

   @Override
   protected void updateBlocks() {
      super.updateBlocks();
      this.previousActiveChunkSet.retainAll(this.activeChunkSet);
      if (this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
         this.previousActiveChunkSet.clear();
      }

      int var4 = 0;

      for (ChunkCoordIntPair var6 : this.activeChunkSet) {
         if (!this.previousActiveChunkSet.contains(var6)) {
            int var7 = var6.chunkXPos * 16;
            int var8 = var6.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            Chunk var9 = this.getChunkFromChunkCoords(var6.chunkXPos, var6.chunkZPos);
            this.playMoodSoundAndCheckLight(var7, var8, var9);
            this.theProfiler.endSection();
            this.previousActiveChunkSet.add(var6);
            if (++var4 >= 10) {
               return;
            }
         }
      }
   }

   public void doPreChunk(int integer1, int integer2, boolean boolean3) {
      if (boolean3) {
         this.clientChunkProvider.loadChunk(integer1, integer2);
      } else {
         this.clientChunkProvider.unloadChunk(integer1, integer2);
      }

      if (!boolean3) {
         this.markBlockRangeForRenderUpdate(integer1 * 16, 0, integer2 * 16, integer1 * 16 + 15, 256, integer2 * 16 + 15);
      }
   }

   @Override
   public boolean spawnEntityInWorld(Entity entity) {
      boolean var5 = super.spawnEntityInWorld(entity);
      this.entityList.add(entity);
      if (!var5) {
         this.entitySpawnQueue.add(entity);
      } else if (entity instanceof EntityMinecart) {
         this.mc.getSoundHandler().playSound(new MovingSoundMinecart((EntityMinecart)entity));
      }

      return var5;
   }

   @Override
   public void removeEntity(Entity entity) {
      super.removeEntity(entity);
      this.entityList.remove(entity);
   }

   @Override
   protected void onEntityAdded(Entity entity) {
      super.onEntityAdded(entity);
      this.entitySpawnQueue.remove(entity);
   }

   @Override
   protected void onEntityRemoved(Entity entity) {
      super.onEntityRemoved(entity);
      if (this.entityList.contains(entity)) {
         if (entity.isEntityAlive()) {
            this.entitySpawnQueue.add(entity);
         } else {
            this.entityList.remove(entity);
         }
      }
   }

   public void addEntityToWorld(int integer, Entity entity) {
      Entity var6 = this.getEntityByID(integer);
      if (var6 != null) {
         this.removeEntity(var6);
      }

      this.entityList.add(entity);
      entity.setEntityId(integer);
      if (!this.spawnEntityInWorld(entity)) {
         this.entitySpawnQueue.add(entity);
      }

      this.entitiesById.addKey(integer, entity);
   }

   @Override
   public Entity getEntityByID(int integer) {
      return integer == this.mc.thePlayer.getEntityId() ? this.mc.thePlayer : super.getEntityByID(integer);
   }

   public Entity removeEntityFromWorld(int integer) {
      Entity var5 = this.entitiesById.removeObject(integer);
      if (var5 != null) {
         this.entityList.remove(var5);
         this.removeEntity(var5);
      }

      return var5;
   }

   public boolean invalidateRegionAndSetBlock(BlockPos blockPos, IBlockState iBlockState) {
      int var3 = blockPos.getX();
      int var4 = blockPos.getY();
      int var5 = blockPos.getZ();
      this.invalidateBlockReceiveRegion(var3, var4, var5, var3, var4, var5);
      return super.setBlockState(blockPos, iBlockState, 3);
   }

   @Override
   public void sendQuittingDisconnectingPacket() {
      this.sendQueue.getNetworkManager().closeChannel(new ChatComponentText("Quitting"));
   }

   @Override
   protected void updateWeather() {
   }

   @Override
   protected int getRenderDistanceChunks() {
      return this.mc.gameSettings.renderDistanceChunks;
   }

   public void doVoidFogParticles(int integer1, int integer2, int integer3) {
      byte var7 = 16;
      Random var8 = new Random();
      ItemStack var9 = this.mc.thePlayer.getHeldItem();
      boolean var10 = this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE
         && var9 != null
         && Block.getBlockFromItem(var9.getItem()) == Blocks.barrier;
      BlockPos.MutableBlockPos var11 = new BlockPos.MutableBlockPos();

      for (int var12 = 0; var12 < 1000; var12++) {
         int var13 = integer1 + this.rand.nextInt(var7) - this.rand.nextInt(var7);
         int var14 = integer2 + this.rand.nextInt(var7) - this.rand.nextInt(var7);
         int var15 = integer3 + this.rand.nextInt(var7) - this.rand.nextInt(var7);
         var11.set(var13, var14, var15);
         IBlockState var16 = this.getBlockState(var11);
         var16.getBlock().randomDisplayTick(this, var11, var16, var8);
         if (var10 && var16.getBlock() == Blocks.barrier) {
            this.spawnParticle(EnumParticleTypes.BARRIER, var13 + 0.5F, var14 + 0.5F, var15 + 0.5F, 0.0, 0.0, 0.0);
         }
      }
   }

   public void removeAllEntities() {
      this.loadedEntityList.removeAll(this.unloadedEntityList);

      for (Entity var5 : this.unloadedEntityList) {
         int var6 = var5.W;
         int var7 = var5.Y;
         if (var5.addedToChunk && this.isChunkLoaded(var6, var7, true)) {
            this.getChunkFromChunkCoords(var6, var7).removeEntity(var5);
         }
      }

      for (Entity var10 : this.unloadedEntityList) {
         this.onEntityRemoved(var10);
      }

      this.unloadedEntityList.clear();

      for (int var9 = 0; var9 < this.loadedEntityList.size(); var9++) {
         Entity var11 = this.loadedEntityList.get(var9);
         if (var11.ridingEntity != null) {
            if (!var11.ridingEntity.isDead && var11.ridingEntity.riddenByEntity == var11) {
               continue;
            }

            var11.ridingEntity.riddenByEntity = null;
            var11.ridingEntity = null;
         }

         if (var11.isDead) {
            int var12 = var11.W;
            int var13 = var11.Y;
            if (var11.addedToChunk && this.isChunkLoaded(var12, var13, true)) {
               this.getChunkFromChunkCoords(var12, var13).removeEntity(var11);
            }

            this.loadedEntityList.remove(var9--);
            this.onEntityRemoved(var11);
         }
      }
   }

   @Override
   public CrashReportCategory addWorldInfoToCrashReport(CrashReport crashReport) {
      CrashReportCategory var2 = super.addWorldInfoToCrashReport(crashReport);
      var2.addCrashSectionCallable("Forced entities", () -> this.entityList.size() + " total; " + this.entityList);
      var2.addCrashSectionCallable("Retry entities", () -> this.entitySpawnQueue.size() + " total; " + this.entitySpawnQueue);
      var2.addCrashSectionCallable("Server brand", () -> this.mc.thePlayer.getClientBrand());
      var2.addCrashSectionCallable(
         "Server type", () -> this.mc.getIntegratedServer() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server"
      );
      return var2;
   }

   public void playSoundAtPos(BlockPos blockPos, String string, float float3, float float4, boolean boolean5) {
      this.playSound(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, string, float3, float4, boolean5);
   }

   @Override
   public void playSound(double double1, double double2, double double3, String string, float float5, float float6, boolean boolean7) {
      double var14 = this.mc.getRenderViewEntity().getDistanceSq(double1, double2, double3);
      PositionedSoundRecord var16 = new PositionedSoundRecord(new ResourceLocation(string), float5, float6, (float)double1, (float)double2, (float)double3);
      if (boolean7 && var14 > 100.0) {
         double var17 = Math.sqrt(var14) / 40.0;
         this.mc.getSoundHandler().playDelayedSound(var16, (int)(var17 * 20.0));
      } else {
         this.mc.getSoundHandler().playSound(var16);
      }
   }

   @Override
   public void makeFireworks(double double1, double double2, double double3, double double4, double double5, double double6, NBTTagCompound nBTTagCompound) {
      this.mc
         .effectRenderer
         .addEffect(new EntityFirework.StarterFX(this, double1, double2, double3, double4, double5, double6, this.mc.effectRenderer, nBTTagCompound));
   }

   public void setWorldScoreboard(Scoreboard scoreboard) {
      this.worldScoreboard = scoreboard;
   }

   @Override
   public void setWorldTime(long long1) {
      if (long1 < 0L) {
         long1 = -long1;
         this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
      } else {
         this.getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
      }

      super.setWorldTime(long1);
   }

   @Override
   public int getCombinedLight(BlockPos blockPos, int integer) {
      int var6 = super.getCombinedLight(blockPos, integer);
      if (Config.isDynamicLights()) {
         var6 = DynamicLights.getCombinedLight(blockPos, var6);
      }

      return var6;
   }

   @Override
   public boolean setBlockState(BlockPos blockPos, IBlockState iBlockState, int integer) {
      this.playerUpdate = this.isPlayerActing();
      boolean var4 = super.setBlockState(blockPos, iBlockState, integer);
      this.playerUpdate = false;
      return var4;
   }

   private boolean isPlayerActing() {
      PlayerControllerMP var5 = this.mc.playerController;
      return var5 instanceof PlayerControllerOF ? ((PlayerControllerOF)var5).isActing() : false;
   }

   public boolean isPlayerUpdate() {
      return this.playerUpdate;
   }
}
