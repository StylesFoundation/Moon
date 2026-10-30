package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ServerConfigurationManager {
   public static final File FILE_PLAYERBANS = new File("banned-players.json");
   public static final File FILE_IPBANS = new File("banned-ips.json");
   public static final File FILE_OPS = new File("ops.json");
   public static final File FILE_WHITELIST = new File("whitelist.json");
   private static final Logger logger = LogManager.getLogger();
   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
   private final MinecraftServer mcServer;
   private final List<EntityPlayerMP> playerEntityList = Lists.newArrayList();
   private final Map<UUID, EntityPlayerMP> uuidToPlayerMap = Maps.newHashMap();
   private final UserListBans bannedPlayers = new UserListBans(FILE_PLAYERBANS);
   private final BanList bannedIPs = new BanList(FILE_IPBANS);
   private final UserListOps ops = new UserListOps(FILE_OPS);
   private final UserListWhitelist whiteListedPlayers = new UserListWhitelist(FILE_WHITELIST);
   private final Map<UUID, StatisticsFile> playerStatFiles = Maps.newHashMap();
   private IPlayerFileData playerNBTManagerObj;
   private boolean whiteListEnforced;
   protected int maxPlayers;
   private int viewDistance;
   private WorldSettings.GameType gameType;
   private boolean commandsAllowedForAll;
   private int playerPingIndex;

   public ServerConfigurationManager(MinecraftServer minecraftServer) {
      this.mcServer = minecraftServer;
      this.bannedPlayers.setLanServer(false);
      this.bannedIPs.setLanServer(false);
      this.maxPlayers = 8;
   }

   public void initializeConnectionToPlayer(NetworkManager networkManager, EntityPlayerMP entityPlayerMP) {
      GameProfile var6 = entityPlayerMP.getGameProfile();
      PlayerProfileCache var7 = this.mcServer.getPlayerProfileCache();
      GameProfile var8 = var7.getProfileByUUID(var6.getId());
      String var9 = var8 == null ? var6.getName() : var8.getName();
      var7.addEntry(var6);
      NBTTagCompound var10 = this.readPlayerDataFromFile(entityPlayerMP);
      entityPlayerMP.setWorld(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
      entityPlayerMP.theItemInWorldManager.setWorld((WorldServer)entityPlayerMP.worldObj);
      String var11 = "local";
      if (networkManager.getRemoteAddress() != null) {
         var11 = networkManager.getRemoteAddress().toString();
      }

      logger.info(
         entityPlayerMP.getName()
            + "["
            + var11
            + "] logged in with entity id "
            + entityPlayerMP.getEntityId()
            + " at ("
            + entityPlayerMP.posX
            + ", "
            + entityPlayerMP.posY
            + ", "
            + entityPlayerMP.posZ
            + ")"
      );
      WorldServer var12 = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
      WorldInfo var13 = var12.getWorldInfo();
      BlockPos var14 = var12.getSpawnPoint();
      this.setPlayerGameTypeBasedOnOther(entityPlayerMP, null, var12);
      NetHandlerPlayServer var15 = new NetHandlerPlayServer(this.mcServer, networkManager, entityPlayerMP);
      var15.sendPacket(
         new S01PacketJoinGame(
            entityPlayerMP.getEntityId(),
            entityPlayerMP.theItemInWorldManager.getGameType(),
            var13.isHardcoreModeEnabled(),
            var12.provider.getDimensionId(),
            var12.getDifficulty(),
            this.getMaxPlayers(),
            var13.getTerrainType(),
            var12.getGameRules().getBoolean("reducedDebugInfo")
         )
      );
      var15.sendPacket(new S3FPacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).writeString(this.getServerInstance().getServerModName())));
      var15.sendPacket(new S41PacketServerDifficulty(var13.getDifficulty(), var13.isDifficultyLocked()));
      var15.sendPacket(new S05PacketSpawnPosition(var14));
      var15.sendPacket(new S39PacketPlayerAbilities(entityPlayerMP.capabilities));
      var15.sendPacket(new S09PacketHeldItemChange(entityPlayerMP.inventory.currentItem));
      entityPlayerMP.getStatFile().func_150877_d();
      entityPlayerMP.getStatFile().sendAchievements(entityPlayerMP);
      this.sendScoreboard((ServerScoreboard)var12.getScoreboard(), entityPlayerMP);
      this.mcServer.refreshStatusNextTick();
      ChatComponentTranslation var16;
      if (!entityPlayerMP.getName().equalsIgnoreCase(var9)) {
         var16 = new ChatComponentTranslation("multiplayer.player.joined.renamed", entityPlayerMP.getDisplayName(), var9);
      } else {
         var16 = new ChatComponentTranslation("multiplayer.player.joined", entityPlayerMP.getDisplayName());
      }

      var16.getChatStyle().setColor(EnumChatFormatting.YELLOW);
      this.sendChatMsg(var16);
      this.playerLoggedIn(entityPlayerMP);
      var15.setPlayerLocation(entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.rotationYaw, entityPlayerMP.rotationPitch);
      this.updateTimeAndWeatherForPlayer(entityPlayerMP, var12);
      if (this.mcServer.getResourcePackUrl().length() > 0) {
         entityPlayerMP.loadResourcePack(this.mcServer.getResourcePackUrl(), this.mcServer.getResourcePackHash());
      }

      for (PotionEffect var18 : entityPlayerMP.getActivePotionEffects()) {
         var15.sendPacket(new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), var18));
      }

      entityPlayerMP.addSelfToInternalCraftingInventory();
      if (var10 != null && var10.hasKey("Riding", 10)) {
         Entity var19 = EntityList.createEntityFromNBT(var10.getCompoundTag("Riding"), var12);
         if (var19 != null) {
            var19.forceSpawn = true;
            var12.spawnEntityInWorld(var19);
            entityPlayerMP.mountEntity(var19);
            var19.forceSpawn = false;
         }
      }
   }

   protected void sendScoreboard(ServerScoreboard serverScoreboard, EntityPlayerMP entityPlayerMP) {
      HashSet var6 = Sets.newHashSet();

      for (ScorePlayerTeam var8 : serverScoreboard.getTeams()) {
         entityPlayerMP.playerNetServerHandler.sendPacket(new S3EPacketTeams(var8, 0));
      }

      for (int var11 = 0; var11 < 19; var11++) {
         ScoreObjective var12 = serverScoreboard.getObjectiveInDisplaySlot(var11);
         if (var12 != null && !var6.contains(var12)) {
            for (Packet var10 : serverScoreboard.func_96550_d(var12)) {
               entityPlayerMP.playerNetServerHandler.sendPacket(var10);
            }

            var6.add(var12);
         }
      }
   }

   public void setPlayerManager(WorldServer[] arr) {
      this.playerNBTManagerObj = arr[0].getSaveHandler().getPlayerNBTManager();
      arr[0].getWorldBorder().addListener(new IBorderListener() {
         @Override
         public void onSizeChanged(WorldBorder worldBorder, double double2) {
            ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_SIZE));
         }

         @Override
         public void onTransitionStarted(WorldBorder worldBorder, double double2, double double3, long long4) {
            ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.LERP_SIZE));
         }

         @Override
         public void onCenterChanged(WorldBorder worldBorder, double double2, double double3) {
            ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_CENTER));
         }

         @Override
         public void onWarningTimeChanged(WorldBorder worldBorder, int integer) {
            ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_WARNING_TIME));
         }

         @Override
         public void onWarningDistanceChanged(WorldBorder worldBorder, int integer) {
            ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS));
         }

         @Override
         public void onDamageAmountChanged(WorldBorder worldBorder, double double2) {
         }

         @Override
         public void onDamageBufferChanged(WorldBorder worldBorder, double double2) {
         }
      });
   }

   public void preparePlayer(EntityPlayerMP entityPlayerMP, WorldServer worldServer) {
      WorldServer var6 = entityPlayerMP.getServerForPlayer();
      if (worldServer != null) {
         worldServer.getPlayerManager().removePlayer(entityPlayerMP);
      }

      var6.getPlayerManager().addPlayer(entityPlayerMP);
      var6.theChunkProviderServer.loadChunk((int)entityPlayerMP.posX >> 4, (int)entityPlayerMP.posZ >> 4);
   }

   public int getEntityViewDistance() {
      return PlayerManager.getFurthestViewableBlock(this.getViewDistance());
   }

   public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP entityPlayerMP) {
      NBTTagCompound var5 = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
      NBTTagCompound var6;
      if (entityPlayerMP.getName().equals(this.mcServer.getServerOwner()) && var5 != null) {
         entityPlayerMP.readFromNBT(var5);
         var6 = var5;
         logger.debug("loading single player");
      } else {
         var6 = this.playerNBTManagerObj.readPlayerData(entityPlayerMP);
      }

      return var6;
   }

   protected void writePlayerData(EntityPlayerMP entityPlayerMP) {
      this.playerNBTManagerObj.writePlayerData(entityPlayerMP);
      StatisticsFile var5 = this.playerStatFiles.get(entityPlayerMP.getUniqueID());
      if (var5 != null) {
         var5.saveStatFile();
      }
   }

   public void playerLoggedIn(EntityPlayerMP entityPlayerMP) {
      this.playerEntityList.add(entityPlayerMP);
      this.uuidToPlayerMap.put(entityPlayerMP.getUniqueID(), entityPlayerMP);
      this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, entityPlayerMP));
      this.mcServer.worldServerForDimension(entityPlayerMP.dimension).spawnEntityInWorld(entityPlayerMP);
      this.preparePlayer(entityPlayerMP, null);

      for (EntityPlayerMP var7 : this.playerEntityList) {
         entityPlayerMP.playerNetServerHandler.sendPacket(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, var7));
      }
   }

   public void serverUpdateMountedMovingPlayer(EntityPlayerMP entityPlayerMP) {
      entityPlayerMP.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(entityPlayerMP);
   }

   public void playerLoggedOut(EntityPlayerMP entityPlayerMP) {
      entityPlayerMP.triggerAchievement(StatList.leaveGameStat);
      this.writePlayerData(entityPlayerMP);
      WorldServer var5 = entityPlayerMP.getServerForPlayer();
      if (entityPlayerMP.ridingEntity != null) {
         var5.removePlayerEntityDangerously(entityPlayerMP.ridingEntity);
         logger.debug("removing player mount");
      }

      var5.removeEntity(entityPlayerMP);
      var5.getPlayerManager().removePlayer(entityPlayerMP);
      this.playerEntityList.remove(entityPlayerMP);
      UUID var6 = entityPlayerMP.getUniqueID();
      EntityPlayerMP var7 = this.uuidToPlayerMap.get(var6);
      if (var7 == entityPlayerMP) {
         this.uuidToPlayerMap.remove(var6);
         this.playerStatFiles.remove(var6);
      }

      this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.REMOVE_PLAYER, entityPlayerMP));
   }

   public String allowUserToConnect(SocketAddress socketAddress, GameProfile gameProfile) {
      if (this.bannedPlayers.isBanned(gameProfile)) {
         UserListBansEntry var8 = this.bannedPlayers.getEntry(gameProfile);
         String var9 = "You are banned from this server!\nReason: " + var8.getBanReason();
         if (var8.getBanEndDate() != null) {
            var9 = var9 + "\nYour ban will be removed on " + dateFormat.format(var8.getBanEndDate());
         }

         return var9;
      } else {
         if (!this.canJoin(gameProfile)) {
            return "You are not white-listed on this server!";
         }

         if (this.bannedIPs.isBanned(socketAddress)) {
            IPBanEntry var6 = this.bannedIPs.getBanEntry(socketAddress);
            String var7 = "Your IP address is banned from this server!\nReason: " + var6.getBanReason();
            if (var6.getBanEndDate() != null) {
               var7 = var7 + "\nYour ban will be removed on " + dateFormat.format(var6.getBanEndDate());
            }

            return var7;
         } else {
            return this.playerEntityList.size() >= this.maxPlayers && !this.bypassesPlayerLimit(gameProfile) ? "The server is full!" : null;
         }
      }
   }

   public EntityPlayerMP createPlayerForUser(GameProfile gameProfile) {
      UUID var5 = EntityPlayer.getUUID(gameProfile);
      List<EntityPlayerMP> var6 = Lists.newArrayList();

      for (EntityPlayerMP var8 : this.playerEntityList) {
         if (var8.getUniqueID().equals(var5)) {
            var6.add(var8);
         }
      }

      EntityPlayerMP var10 = this.uuidToPlayerMap.get(gameProfile.getId());
      if (var10 != null && !var6.contains(var10)) {
         var6.add(var10);
      }

      for (EntityPlayerMP var9 : var6) {
         var9.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
      }

      ItemInWorldManager var12;
      if (this.mcServer.isDemo()) {
         var12 = new DemoWorldManager(this.mcServer.worldServerForDimension(0));
      } else {
         var12 = new ItemInWorldManager(this.mcServer.worldServerForDimension(0));
      }

      return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), gameProfile, var12);
   }

   public EntityPlayerMP recreatePlayerEntity(EntityPlayerMP entityPlayerMP, int integer, boolean boolean3) {
      entityPlayerMP.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(entityPlayerMP);
      entityPlayerMP.getServerForPlayer().getEntityTracker().untrackEntity(entityPlayerMP);
      entityPlayerMP.getServerForPlayer().getPlayerManager().removePlayer(entityPlayerMP);
      this.playerEntityList.remove(entityPlayerMP);
      this.mcServer.worldServerForDimension(entityPlayerMP.dimension).removePlayerEntityDangerously(entityPlayerMP);
      BlockPos var7 = entityPlayerMP.getBedLocation();
      boolean var8 = entityPlayerMP.isSpawnForced();
      entityPlayerMP.dimension = integer;
      ItemInWorldManager var9;
      if (this.mcServer.isDemo()) {
         var9 = new DemoWorldManager(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
      } else {
         var9 = new ItemInWorldManager(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
      }

      EntityPlayerMP var10 = new EntityPlayerMP(
         this.mcServer, this.mcServer.worldServerForDimension(entityPlayerMP.dimension), entityPlayerMP.getGameProfile(), var9
      );
      var10.playerNetServerHandler = entityPlayerMP.playerNetServerHandler;
      var10.clonePlayer(entityPlayerMP, boolean3);
      var10.setEntityId(entityPlayerMP.getEntityId());
      var10.setCommandStats(entityPlayerMP);
      WorldServer var11 = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
      this.setPlayerGameTypeBasedOnOther(var10, entityPlayerMP, var11);
      if (var7 != null) {
         BlockPos var12 = EntityPlayer.getBedSpawnLocation(this.mcServer.worldServerForDimension(entityPlayerMP.dimension), var7, var8);
         if (var12 != null) {
            var10.setLocationAndAngles(var12.getX() + 0.5F, var12.getY() + 0.1F, var12.getZ() + 0.5F, 0.0F, 0.0F);
            var10.setSpawnPoint(var7, var8);
         } else {
            var10.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0, 0.0F));
         }
      }

      var11.theChunkProviderServer.loadChunk((int)var10.posX >> 4, (int)var10.posZ >> 4);

      while (!var11.getCollidingBoundingBoxes(var10, var10.getEntityBoundingBox()).isEmpty() && var10.posY < 256.0) {
         var10.setPosition(var10.posX, var10.posY + 1.0, var10.posZ);
      }

      var10.playerNetServerHandler
         .sendPacket(
            new S07PacketRespawn(
               var10.dimension, var10.worldObj.getDifficulty(), var10.worldObj.getWorldInfo().getTerrainType(), var10.theItemInWorldManager.getGameType()
            )
         );
      BlockPos var13 = var11.getSpawnPoint();
      var10.playerNetServerHandler.setPlayerLocation(var10.posX, var10.posY, var10.posZ, var10.rotationYaw, var10.rotationPitch);
      var10.playerNetServerHandler.sendPacket(new S05PacketSpawnPosition(var13));
      var10.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(var10.experience, var10.experienceTotal, var10.experienceLevel));
      this.updateTimeAndWeatherForPlayer(var10, var11);
      var11.getPlayerManager().addPlayer(var10);
      var11.spawnEntityInWorld(var10);
      this.playerEntityList.add(var10);
      this.uuidToPlayerMap.put(var10.getUniqueID(), var10);
      var10.addSelfToInternalCraftingInventory();
      var10.setHealth(var10.getHealth());
      return var10;
   }

   public void transferPlayerToDimension(EntityPlayerMP entityPlayerMP, int integer) {
      int var6 = entityPlayerMP.dimension;
      WorldServer var7 = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
      entityPlayerMP.dimension = integer;
      WorldServer var8 = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
      entityPlayerMP.playerNetServerHandler
         .sendPacket(
            new S07PacketRespawn(
               entityPlayerMP.dimension,
               entityPlayerMP.worldObj.getDifficulty(),
               entityPlayerMP.worldObj.getWorldInfo().getTerrainType(),
               entityPlayerMP.theItemInWorldManager.getGameType()
            )
         );
      var7.removePlayerEntityDangerously(entityPlayerMP);
      entityPlayerMP.isDead = false;
      this.transferEntityToWorld(entityPlayerMP, var6, var7, var8);
      this.preparePlayer(entityPlayerMP, var7);
      entityPlayerMP.playerNetServerHandler
         .setPlayerLocation(entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.rotationYaw, entityPlayerMP.rotationPitch);
      entityPlayerMP.theItemInWorldManager.setWorld(var8);
      this.updateTimeAndWeatherForPlayer(entityPlayerMP, var8);
      this.syncPlayerInventory(entityPlayerMP);

      for (PotionEffect var10 : entityPlayerMP.getActivePotionEffects()) {
         entityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), var10));
      }
   }

   public void transferEntityToWorld(Entity entity, int integer, WorldServer worldServer3, WorldServer worldServer4) {
      double var8 = entity.posX;
      double var10 = entity.posZ;
      float var14 = entity.rotationYaw;
      worldServer3.theProfiler.startSection("moving");
      if (entity.dimension == -1) {
         var8 = MathHelper.clamp_double(var8 / 8.0, worldServer4.getWorldBorder().minX() + 16.0, worldServer4.getWorldBorder().maxX() - 16.0);
         var10 = MathHelper.clamp_double(var10 / 8.0, worldServer4.getWorldBorder().minZ() + 16.0, worldServer4.getWorldBorder().maxZ() - 16.0);
         entity.setLocationAndAngles(var8, entity.posY, var10, entity.rotationYaw, entity.rotationPitch);
         if (entity.isEntityAlive()) {
            worldServer3.updateEntityWithOptionalForce(entity, false);
         }
      } else if (entity.dimension == 0) {
         var8 = MathHelper.clamp_double(var8 * 8.0, worldServer4.getWorldBorder().minX() + 16.0, worldServer4.getWorldBorder().maxX() - 16.0);
         var10 = MathHelper.clamp_double(var10 * 8.0, worldServer4.getWorldBorder().minZ() + 16.0, worldServer4.getWorldBorder().maxZ() - 16.0);
         entity.setLocationAndAngles(var8, entity.posY, var10, entity.rotationYaw, entity.rotationPitch);
         if (entity.isEntityAlive()) {
            worldServer3.updateEntityWithOptionalForce(entity, false);
         }
      } else {
         BlockPos var15;
         if (integer == 1) {
            var15 = worldServer4.getSpawnPoint();
         } else {
            var15 = worldServer4.getSpawnCoordinate();
         }

         var8 = var15.getX();
         entity.posY = var15.getY();
         var10 = var15.getZ();
         entity.setLocationAndAngles(var8, entity.posY, var10, 90.0F, 0.0F);
         if (entity.isEntityAlive()) {
            worldServer3.updateEntityWithOptionalForce(entity, false);
         }
      }

      worldServer3.theProfiler.endSection();
      if (integer != 1) {
         worldServer3.theProfiler.startSection("placing");
         var8 = MathHelper.clamp_int((int)var8, -29999872, 29999872);
         var10 = MathHelper.clamp_int((int)var10, -29999872, 29999872);
         if (entity.isEntityAlive()) {
            entity.setLocationAndAngles(var8, entity.posY, var10, entity.rotationYaw, entity.rotationPitch);
            worldServer4.getDefaultTeleporter().placeInPortal(entity, var14);
            worldServer4.spawnEntityInWorld(entity);
            worldServer4.updateEntityWithOptionalForce(entity, false);
         }

         worldServer3.theProfiler.endSection();
      }

      entity.setWorld(worldServer4);
   }

   public void onTick() {
      if (++this.playerPingIndex > 600) {
         this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_LATENCY, this.playerEntityList));
         this.playerPingIndex = 0;
      }
   }

   public void sendPacketToAllPlayers(Packet packet) {
      Iterator var5 = this.playerEntityList.iterator();

      while (var5.hasNext()) {
         ((EntityPlayerMP)var5.next()).playerNetServerHandler.sendPacket(packet);
      }
   }

   public void sendPacketToAllPlayersInDimension(Packet packet, int integer) {
      for (EntityPlayerMP var7 : this.playerEntityList) {
         if (var7.dimension == integer) {
            var7.playerNetServerHandler.sendPacket(packet);
         }
      }
   }

   public void sendMessageToAllTeamMembers(EntityPlayer entityPlayer, IChatComponent iChatComponent) {
      Team var6 = entityPlayer.getTeam();
      if (var6 != null) {
         for (String var8 : var6.getMembershipCollection()) {
            EntityPlayerMP var9 = this.getPlayerByUsername(var8);
            if (var9 != null && var9 != entityPlayer) {
               var9.addChatMessage(iChatComponent);
            }
         }
      }
   }

   public void sendMessageToTeamOrEvryPlayer(EntityPlayer entityPlayer, IChatComponent iChatComponent) {
      Team var6 = entityPlayer.getTeam();
      if (var6 == null) {
         this.sendChatMsg(iChatComponent);
      } else {
         for (EntityPlayerMP var8 : this.playerEntityList) {
            if (var8.getTeam() != var6) {
               var8.addChatMessage(iChatComponent);
            }
         }
      }
   }

   public String func_181058_b(boolean boolean1) {
      StringBuilder var5 = new StringBuilder();
      ArrayList var6 = Lists.newArrayList(this.playerEntityList);

      for (int var7 = 0; var7 < var6.size(); var7++) {
         if (var7 > 0) {
            var5.append(", ");
         }

         var5.append(((EntityPlayerMP)var6.get(var7)).getName());
         if (boolean1) {
            var5.append(" (").append(((EntityPlayerMP)var6.get(var7)).getUniqueID().toString()).append(")");
         }
      }

      return var5.toString();
   }

   public String[] getAllUsernames() {
      String[] var4 = new String[this.playerEntityList.size()];

      for (int var5 = 0; var5 < this.playerEntityList.size(); var5++) {
         var4[var5] = this.playerEntityList.get(var5).getName();
      }

      return var4;
   }

   public GameProfile[] getAllProfiles() {
      GameProfile[] var4 = new GameProfile[this.playerEntityList.size()];

      for (int var5 = 0; var5 < this.playerEntityList.size(); var5++) {
         var4[var5] = this.playerEntityList.get(var5).getGameProfile();
      }

      return var4;
   }

   public UserListBans getBannedPlayers() {
      return this.bannedPlayers;
   }

   public BanList getBannedIPs() {
      return this.bannedIPs;
   }

   public void addOp(GameProfile gameProfile) {
      this.ops.addEntry(new UserListOpsEntry(gameProfile, this.mcServer.getOpPermissionLevel(), this.ops.bypassesPlayerLimit(gameProfile)));
   }

   public void removeOp(GameProfile gameProfile) {
      this.ops.removeEntry(gameProfile);
   }

   public boolean canJoin(GameProfile gameProfile) {
      return !this.whiteListEnforced || this.ops.hasEntry(gameProfile) || this.whiteListedPlayers.hasEntry(gameProfile);
   }

   public boolean canSendCommands(GameProfile gameProfile) {
      return this.ops.hasEntry(gameProfile)
         || this.mcServer.isSinglePlayer()
            && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed()
            && this.mcServer.getServerOwner().equalsIgnoreCase(gameProfile.getName())
         || this.commandsAllowedForAll;
   }

   public EntityPlayerMP getPlayerByUsername(String string) {
      for (EntityPlayerMP var6 : this.playerEntityList) {
         if (var6.getName().equalsIgnoreCase(string)) {
            return var6;
         }
      }

      return null;
   }

   public void sendToAllNear(double double1, double double2, double double3, double double4, int integer, Packet packet) {
      this.sendToAllNearExcept(null, double1, double2, double3, double4, integer, packet);
   }

   public void sendToAllNearExcept(EntityPlayer entityPlayer, double double2, double double3, double double4, double double5, int integer, Packet packet) {
      for (EntityPlayerMP var16 : this.playerEntityList) {
         if (var16 != entityPlayer && var16.dimension == integer) {
            double var17 = double2 - var16.posX;
            double var19 = double3 - var16.posY;
            double var21 = double4 - var16.posZ;
            if (var17 * var17 + var19 * var19 + var21 * var21 < double5 * double5) {
               var16.playerNetServerHandler.sendPacket(packet);
            }
         }
      }
   }

   public void saveAllPlayerData() {
      for (EntityPlayerMP var5 : this.playerEntityList) {
         this.writePlayerData(var5);
      }
   }

   public void addWhitelistedPlayer(GameProfile gameProfile) {
      this.whiteListedPlayers.addEntry(new UserListWhitelistEntry(gameProfile));
   }

   public void removePlayerFromWhitelist(GameProfile gameProfile) {
      this.whiteListedPlayers.removeEntry(gameProfile);
   }

   public UserListWhitelist getWhitelistedPlayers() {
      return this.whiteListedPlayers;
   }

   public String[] getWhitelistedPlayerNames() {
      return this.whiteListedPlayers.getKeys();
   }

   public UserListOps getOppedPlayers() {
      return this.ops;
   }

   public String[] getOppedPlayerNames() {
      return this.ops.getKeys();
   }

   public void loadWhiteList() {
   }

   public void updateTimeAndWeatherForPlayer(EntityPlayerMP entityPlayerMP, WorldServer worldServer) {
      WorldBorder var6 = this.mcServer.worldServers[0].getWorldBorder();
      entityPlayerMP.playerNetServerHandler.sendPacket(new S44PacketWorldBorder(var6, S44PacketWorldBorder.Action.INITIALIZE));
      entityPlayerMP.playerNetServerHandler
         .sendPacket(
            new S03PacketTimeUpdate(worldServer.getTotalWorldTime(), worldServer.getWorldTime(), worldServer.getGameRules().getBoolean("doDaylightCycle"))
         );
      if (worldServer.isRaining()) {
         entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(1, 0.0F));
         entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(7, worldServer.getRainStrength(1.0F)));
         entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(8, worldServer.getThunderStrength(1.0F)));
      }
   }

   public void syncPlayerInventory(EntityPlayerMP entityPlayerMP) {
      entityPlayerMP.sendContainerToPlayer(entityPlayerMP.inventoryContainer);
      entityPlayerMP.setPlayerHealthUpdated();
      entityPlayerMP.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(entityPlayerMP.inventory.currentItem));
   }

   public int getCurrentPlayerCount() {
      return this.playerEntityList.size();
   }

   public int getMaxPlayers() {
      return this.maxPlayers;
   }

   public String[] getAvailablePlayerDat() {
      return this.mcServer.worldServers[0].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
   }

   public void setWhiteListEnabled(boolean boolean1) {
      this.whiteListEnforced = boolean1;
   }

   public List<EntityPlayerMP> getPlayersMatchingAddress(String string) {
      ArrayList var5 = Lists.newArrayList();

      for (EntityPlayerMP var7 : this.playerEntityList) {
         if (var7.getPlayerIP().equals(string)) {
            var5.add(var7);
         }
      }

      return var5;
   }

   public int getViewDistance() {
      return this.viewDistance;
   }

   public MinecraftServer getServerInstance() {
      return this.mcServer;
   }

   public NBTTagCompound getHostPlayerData() {
      return null;
   }

   public void setGameType(WorldSettings.GameType gameType) {
      this.gameType = gameType;
   }

   private void setPlayerGameTypeBasedOnOther(EntityPlayerMP entityPlayerMP1, EntityPlayerMP entityPlayerMP2, World world) {
      if (entityPlayerMP2 != null) {
         entityPlayerMP1.theItemInWorldManager.setGameType(entityPlayerMP2.theItemInWorldManager.getGameType());
      } else if (this.gameType != null) {
         entityPlayerMP1.theItemInWorldManager.setGameType(this.gameType);
      }

      entityPlayerMP1.theItemInWorldManager.initializeGameType(world.getWorldInfo().getGameType());
   }

   public void setCommandsAllowedForAll(boolean boolean1) {
      this.commandsAllowedForAll = boolean1;
   }

   public void removeAllPlayers() {
      Iterator var4 = this.playerEntityList.iterator();

      while (var4.hasNext()) {
         ((EntityPlayerMP)var4.next()).playerNetServerHandler.kickPlayerFromServer("Server closed");
      }
   }

   public void sendChatMsgImpl(IChatComponent iChatComponent, boolean boolean2) {
      this.mcServer.addChatMessage(iChatComponent);
      byte var6 = (byte)(boolean2 ? 1 : 0);
      this.sendPacketToAllPlayers(new S02PacketChat(iChatComponent, var6));
   }

   public void sendChatMsg(IChatComponent iChatComponent) {
      this.sendChatMsgImpl(iChatComponent, true);
   }

   public StatisticsFile getPlayerStatsFile(EntityPlayer entityPlayer) {
      UUID var5 = entityPlayer.getUniqueID();
      StatisticsFile var6 = var5 == null ? null : this.playerStatFiles.get(var5);
      if (var6 == null) {
         File var7 = new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats");
         File var8 = new File(var7, var5.toString() + ".json");
         if (!var8.exists()) {
            File var9 = new File(var7, entityPlayer.getName() + ".json");
            if (var9.exists() && var9.isFile()) {
               var9.renameTo(var8);
            }
         }

         var6 = new StatisticsFile(this.mcServer, var8);
         var6.Method559();
         this.playerStatFiles.put(var5, var6);
      }

      return var6;
   }

   public void setViewDistance(int integer) {
      this.viewDistance = integer;
      if (this.mcServer.worldServers != null) {
         for (WorldServer var8 : this.mcServer.worldServers) {
            if (var8 != null) {
               var8.getPlayerManager().setPlayerViewRadius(integer);
            }
         }
      }
   }

   public List<EntityPlayerMP> getPlayerList() {
      return this.playerEntityList;
   }

   public EntityPlayerMP getPlayerByUUID(UUID uUID) {
      return this.uuidToPlayerMap.get(uUID);
   }

   public boolean bypassesPlayerLimit(GameProfile gameProfile) {
      return false;
   }
}
