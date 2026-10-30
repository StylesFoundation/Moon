package net.minecraft.entity.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonSerializableSet;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPlayerMP extends EntityPlayer implements ICrafting {
   private static final Logger logger = LogManager.getLogger();
   private String translator = "en_US";
   public NetHandlerPlayServer playerNetServerHandler;
   public final MinecraftServer mcServer;
   public final ItemInWorldManager theItemInWorldManager;
   public double managedPosX;
   public double managedPosZ;
   public final List<ChunkCoordIntPair> loadedChunks = Lists.newLinkedList();
   private final List<Integer> destroyedItemsNetCache = Lists.newLinkedList();
   private final StatisticsFile statsFile;
   private float combinedHealth = Float.MIN_VALUE;
   private float lastHealth = -1.0E8F;
   private int lastFoodLevel = -99999999;
   private boolean wasHungry = true;
   private int lastExperience = -99999999;
   private int respawnInvulnerabilityTicks = 60;
   private EntityPlayer.EnumChatVisibility chatVisibility;
   private boolean chatColours = true;
   private long playerLastActiveTime = System.currentTimeMillis();
   private Entity spectatingEntity = null;
   private int currentWindowId;
   public boolean isChangingQuantityOnly;
   public int ping;
   public boolean playerConqueredTheEnd;

   public EntityPlayerMP(MinecraftServer minecraftServer, WorldServer worldServer, GameProfile gameProfile, ItemInWorldManager itemInWorldManager) {
      super(worldServer, gameProfile);
      itemInWorldManager.thisPlayerMP = this;
      this.theItemInWorldManager = itemInWorldManager;
      BlockPos var8 = worldServer.getSpawnPoint();
      if (!worldServer.provider.getHasNoSky() && worldServer.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE) {
         int var9 = Math.max(5, minecraftServer.getSpawnProtectionSize() - 6);
         int var10 = MathHelper.floor_double(worldServer.getWorldBorder().getClosestDistance(var8.getX(), var8.getZ()));
         if (var10 < var9) {
            var9 = var10;
         }

         if (var10 <= 1) {
            var9 = 1;
         }

         var8 = worldServer.getTopSolidOrLiquidBlock(var8.add(this.rand.nextInt(var9 * 2) - var9, 0, this.rand.nextInt(var9 * 2) - var9));
      }

      this.mcServer = minecraftServer;
      this.statsFile = minecraftServer.getConfigurationManager().getPlayerStatsFile(this);
      this.stepHeight = 0.0F;
      this.moveToBlockPosAndAngles(var8, 0.0F, 0.0F);

      while (!worldServer.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && this.posY < 255.0) {
         this.setPosition(this.posX, this.posY + 1.0, this.posZ);
      }
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      if (nBTTagCompound.hasKey("playerGameType", 99)) {
         if (MinecraftServer.getServer().getForceGamemode()) {
            this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
         } else {
            this.theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(nBTTagCompound.getInteger("playerGameType")));
         }
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
   }

   @Override
   public void addExperienceLevel(int integer) {
      super.addExperienceLevel(integer);
      this.lastExperience = -1;
   }

   @Override
   public void removeExperienceLevel(int integer) {
      super.removeExperienceLevel(integer);
      this.lastExperience = -1;
   }

   public void addSelfToInternalCraftingInventory() {
      this.openContainer.onCraftGuiOpened(this);
   }

   @Override
   public void sendEnterCombat() {
      super.sendEnterCombat();
      this.playerNetServerHandler.sendPacket(new S42PacketCombatEvent(this.getCombatTracker(), S42PacketCombatEvent.Event.ENTER_COMBAT));
   }

   @Override
   public void sendEndCombat() {
      super.sendEndCombat();
      this.playerNetServerHandler.sendPacket(new S42PacketCombatEvent(this.getCombatTracker(), S42PacketCombatEvent.Event.END_COMBAT));
   }

   @Override
   public void onUpdate() {
      this.theItemInWorldManager.updateBlockRemoving();
      this.respawnInvulnerabilityTicks--;
      if (this.hurtResistantTime > 0) {
         this.hurtResistantTime--;
      }

      this.openContainer.detectAndSendChanges();
      if (!this.worldObj.isRemote && !this.openContainer.canInteractWith(this)) {
         this.closeScreen();
         this.openContainer = this.inventoryContainer;
      }

      while (!this.destroyedItemsNetCache.isEmpty()) {
         int var4 = Math.min(this.destroyedItemsNetCache.size(), Integer.MAX_VALUE);
         int[] var5 = new int[var4];
         Iterator var6 = this.destroyedItemsNetCache.iterator();
         int var7 = 0;

         while (var6.hasNext() && var7 < var4) {
            var5[var7++] = (Integer)var6.next();
            var6.remove();
         }

         this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(var5));
      }

      if (!this.loadedChunks.isEmpty()) {
         ArrayList<Chunk> var9 = Lists.newArrayList();
         Iterator var11 = this.loadedChunks.iterator();
         ArrayList<TileEntity> var12 = Lists.newArrayList();

         while (var11.hasNext() && var9.size() < 10) {
            ChunkCoordIntPair var13 = (ChunkCoordIntPair)var11.next();
            if (var13 != null) {
               if (this.worldObj.Method322(new BlockPos(var13.chunkXPos << 4, 0, var13.chunkZPos << 4))) {
                  Chunk var8 = this.worldObj.getChunkFromChunkCoords(var13.chunkXPos, var13.chunkZPos);
                  if (var8.isPopulated()) {
                     var9.add(var8);
                     var12.addAll(
                        ((WorldServer)this.worldObj)
                           .getTileEntitiesIn(var13.chunkXPos * 16, 0, var13.chunkZPos * 16, var13.chunkXPos * 16 + 16, 256, var13.chunkZPos * 16 + 16)
                     );
                     var11.remove();
                  }
               }
            } else {
               var11.remove();
            }
         }

         if (!var9.isEmpty()) {
            if (var9.size() == 1) {
               this.playerNetServerHandler.sendPacket(new S21PacketChunkData((Chunk)var9.get(0), true, 65535));
            } else {
               this.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(var9));
            }

            for (TileEntity var16 : var12) {
               this.sendTileEntityUpdate(var16);
            }

            for (Chunk var17 : var9) {
               this.getServerForPlayer().getEntityTracker().func_85172_a(this, var17);
            }
         }
      }

      Entity var10 = this.getSpectatingEntity();
      if (var10 != this) {
         if (!var10.isEntityAlive()) {
            this.setSpectatingEntity(this);
         } else {
            this.setPositionAndRotation(var10.posX, var10.posY, var10.posZ, var10.rotationYaw, var10.rotationPitch);
            this.mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this);
            if (this.isSneaking()) {
               this.setSpectatingEntity(this);
            }
         }
      }
   }

   public void onUpdateEntity() {
      try {
         super.onUpdate();

         for (int var4 = 0; var4 < this.inventory.getSizeInventory(); var4++) {
            ItemStack var9 = this.inventory.getStackInSlot(var4);
            if (var9 != null && var9.getItem().isMap()) {
               Packet var11 = ((ItemMapBase)var9.getItem()).createMapDataPacket(var9, this.worldObj, this);
               if (var11 != null) {
                  this.playerNetServerHandler.sendPacket(var11);
               }
            }
         }

         if (this.getHealth() != this.lastHealth
            || this.lastFoodLevel != this.foodStats.getFoodLevel()
            || this.foodStats.getSaturationLevel() == 0.0F != this.wasHungry) {
            this.playerNetServerHandler
               .sendPacket(new S06PacketUpdateHealth(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
            this.lastHealth = this.getHealth();
            this.lastFoodLevel = this.foodStats.getFoodLevel();
            this.wasHungry = this.foodStats.getSaturationLevel() == 0.0F;
         }

         if (this.getHealth() + this.getAbsorptionAmount() != this.combinedHealth) {
            this.combinedHealth = this.getHealth() + this.getAbsorptionAmount();

            for (ScoreObjective var10 : this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.health)) {
               this.getWorldScoreboard().Method281(this.getName(), var10).func_96651_a(List.of(this));
            }
         }

         if (this.experienceTotal != this.lastExperience) {
            this.lastExperience = this.experienceTotal;
            this.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
         }

         if (this.ticksExisted % 20 * 5 == 0 && !this.getStatFile().hasAchievementUnlocked(AchievementList.exploreAllBiomes)) {
            this.updateBiomesExplored();
         }
      } catch (Throwable var7) {
         CrashReport var5 = CrashReport.makeCrashReport(var7, "Ticking player");
         CrashReportCategory var6 = var5.makeCategory("Player being ticked");
         this.addEntityCrashInfo(var6);
         throw new ReportedException(var5);
      }
   }

   protected void updateBiomesExplored() {
      BiomeGenBase var4 = this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)));
      String var5 = var4.biomeName;
      JsonSerializableSet var6 = this.getStatFile().func_150870_b(AchievementList.exploreAllBiomes);
      if (var6 == null) {
         var6 = this.getStatFile().func_150872_a(AchievementList.exploreAllBiomes, new JsonSerializableSet());
      }

      var6.add(var5);
      if (this.getStatFile().canUnlockAchievement(AchievementList.exploreAllBiomes) && var6.size() >= BiomeGenBase.explorationBiomesList.size()) {
         HashSet<BiomeGenBase> var7 = Sets.newHashSet(BiomeGenBase.explorationBiomesList);

         for (String var9 : var6) {
            var7.removeIf(biomeGenBase -> biomeGenBase.biomeName.equals(var9));
            if (var7.isEmpty()) {
               break;
            }
         }

         if (var7.isEmpty()) {
            this.triggerAchievement(AchievementList.exploreAllBiomes);
         }
      }
   }

   @Override
   public void onDeath(DamageSource damageSource) {
      if (this.worldObj.getGameRules().getBoolean("showDeathMessages")) {
         Team var5 = this.getTeam();
         if (var5 == null || var5.getDeathMessageVisibility() == Team.EnumVisible.ALWAYS) {
            this.mcServer.getConfigurationManager().sendChatMsg(this.getCombatTracker().getDeathMessage());
         } else if (var5.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
            this.mcServer.getConfigurationManager().sendMessageToAllTeamMembers(this, this.getCombatTracker().getDeathMessage());
         } else if (var5.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OWN_TEAM) {
            this.mcServer.getConfigurationManager().sendMessageToTeamOrEvryPlayer(this, this.getCombatTracker().getDeathMessage());
         }
      }

      if (!this.worldObj.getGameRules().getBoolean("keepInventory")) {
         this.inventory.dropAllItems();
      }

      for (ScoreObjective var6 : this.worldObj.getScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.deathCount)) {
         this.getWorldScoreboard().Method281(this.getName(), var6).func_96648_a();
      }

      EntityLivingBase var8 = this.getAttackingEntity();
      if (var8 != null) {
         EntityList.EntityEggInfo var9 = EntityList.entityEggs.get(EntityList.getEntityID(var8));
         if (var9 != null) {
            this.triggerAchievement(var9.field_151513_e);
         }

         var8.addToPlayerScore(this, this.scoreValue);
      }

      this.triggerAchievement(StatList.deathsStat);
      this.func_175145_a(StatList.timeSinceDeathStat);
      this.getCombatTracker().reset();
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      boolean var6 = this.mcServer.isDedicatedServer() && this.canPlayersAttack() && "fall".equals(damageSource.damageType);
      if (!var6 && this.respawnInvulnerabilityTicks > 0 && damageSource != DamageSource.outOfWorld) {
         return false;
      }

      if (damageSource instanceof EntityDamageSource) {
         Entity var7 = damageSource.getEntity();
         if (var7 instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)var7)) {
            return false;
         }

         if (var7 instanceof EntityArrow var8 && var8.shootingEntity instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)var8.shootingEntity)) {
            return false;
         }
      }

      return super.attackEntityFrom(damageSource, float2);
   }

   @Override
   public boolean canAttackPlayer(EntityPlayer entityPlayer) {
      return this.canPlayersAttack() && super.canAttackPlayer(entityPlayer);
   }

   private boolean canPlayersAttack() {
      return this.mcServer.isPVPEnabled();
   }

   @Override
   public void travelToDimension(int integer) {
      if (this.dimension == 1 && integer == 1) {
         this.triggerAchievement(AchievementList.theEnd2);
         this.worldObj.removeEntity(this);
         this.playerConqueredTheEnd = true;
         this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(4, 0.0F));
      } else {
         if (this.dimension == 0 && integer == 1) {
            this.triggerAchievement(AchievementList.theEnd);
            BlockPos var5 = this.mcServer.worldServerForDimension(integer).getSpawnCoordinate();
            if (var5 != null) {
               this.playerNetServerHandler.setPlayerLocation(var5.getX(), var5.getY(), var5.getZ(), 0.0F, 0.0F);
            }

            integer = 1;
         } else {
            this.triggerAchievement(AchievementList.portal);
         }

         this.mcServer.getConfigurationManager().transferPlayerToDimension(this, integer);
         this.lastExperience = -1;
         this.lastHealth = -1.0F;
         this.lastFoodLevel = -1;
      }
   }

   @Override
   public boolean isSpectatedByPlayer(EntityPlayerMP entityPlayerMP) {
      return entityPlayerMP.isSpectator() ? this.getSpectatingEntity() == this : !this.isSpectator() && super.isSpectatedByPlayer(entityPlayerMP);
   }

   private void sendTileEntityUpdate(TileEntity tileEntity) {
      if (tileEntity != null) {
         Packet var5 = tileEntity.getDescriptionPacket();
         if (var5 != null) {
            this.playerNetServerHandler.sendPacket(var5);
         }
      }
   }

   @Override
   public void onItemPickup(Entity entity, int integer) {
      super.onItemPickup(entity, integer);
      this.openContainer.detectAndSendChanges();
   }

   @Override
   public EntityPlayer.EnumStatus trySleep(BlockPos blockPos) {
      EntityPlayer.EnumStatus var5 = super.trySleep(blockPos);
      if (var5 == EntityPlayer.EnumStatus.OK) {
         S0APacketUseBed var6 = new S0APacketUseBed(this, blockPos);
         this.getServerForPlayer().getEntityTracker().sendToAllTrackingEntity(this, var6);
         this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
         this.playerNetServerHandler.sendPacket(var6);
      }

      return var5;
   }

   @Override
   public void wakeUpPlayer(boolean boolean1, boolean boolean2, boolean boolean3) {
      if (this.isPlayerSleeping()) {
         this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 2));
      }

      super.wakeUpPlayer(boolean1, boolean2, boolean3);
      if (this.playerNetServerHandler != null) {
         this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
      }
   }

   @Override
   public void mountEntity(Entity entity) {
      Entity var5 = this.ridingEntity;
      super.mountEntity(entity);
      if (entity != var5) {
         this.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this, this.ridingEntity));
         this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
      }
   }

   @Override
   protected void updateFallState(double double1, boolean boolean2, Block block, BlockPos blockPos) {
   }

   public void handleFalling(double double1, boolean boolean2) {
      int var7 = MathHelper.floor_double(this.posX);
      int var8 = MathHelper.floor_double(this.posY - 0.2F);
      int var9 = MathHelper.floor_double(this.posZ);
      BlockPos var10 = new BlockPos(var7, var8, var9);
      Block var11 = this.worldObj.getBlockState(var10).getBlock();
      if (var11.getMaterial() == Material.air) {
         Block var12 = this.worldObj.getBlockState(var10.down()).getBlock();
         if (var12 instanceof BlockFence || var12 instanceof BlockWall || var12 instanceof BlockFenceGate) {
            var10 = var10.down();
            var11 = this.worldObj.getBlockState(var10).getBlock();
         }
      }

      super.updateFallState(double1, boolean2, var11, var10);
   }

   @Override
   public void openEditSign(TileEntitySign tileEntitySign) {
      tileEntitySign.setPlayer(this);
      this.playerNetServerHandler.sendPacket(new S36PacketSignEditorOpen(tileEntitySign.getPos()));
   }

   private void getNextWindowId() {
      this.currentWindowId = this.currentWindowId % 100 + 1;
   }

   @Override
   public void displayGui(IInteractionObject iInteractionObject) {
      this.getNextWindowId();
      this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, iInteractionObject.getGuiID(), iInteractionObject.getDisplayName()));
      this.openContainer = iInteractionObject.createContainer(this.inventory, this);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.onCraftGuiOpened(this);
   }

   @Override
   public void displayGUIChest(IInventory iInventory) {
      if (this.openContainer != this.inventoryContainer) {
         this.closeScreen();
      }

      if (iInventory instanceof ILockableContainer var5 && var5.isLocked() && !this.canOpen(var5.getLockCode()) && !this.isSpectator()) {
         this.playerNetServerHandler.sendPacket(new S02PacketChat(new ChatComponentTranslation("container.isLocked", iInventory.getDisplayName()), (byte)2));
         this.playerNetServerHandler.sendPacket(new S29PacketSoundEffect("random.door_close", this.posX, this.posY, this.posZ, 1.0F, 1.0F));
      } else {
         this.getNextWindowId();
         if (iInventory instanceof IInteractionObject) {
            this.playerNetServerHandler
               .sendPacket(
                  new S2DPacketOpenWindow(
                     this.currentWindowId, ((IInteractionObject)iInventory).getGuiID(), iInventory.getDisplayName(), iInventory.getSizeInventory()
                  )
               );
            this.openContainer = ((IInteractionObject)iInventory).createContainer(this.inventory, this);
         } else {
            this.playerNetServerHandler
               .sendPacket(new S2DPacketOpenWindow(this.currentWindowId, "minecraft:container", iInventory.getDisplayName(), iInventory.getSizeInventory()));
            this.openContainer = new ContainerChest(this.inventory, iInventory, this);
         }

         this.openContainer.windowId = this.currentWindowId;
         this.openContainer.onCraftGuiOpened(this);
      }
   }

   @Override
   public void displayVillagerTradeGui(IMerchant iMerchant) {
      this.getNextWindowId();
      this.openContainer = new ContainerMerchant(this.inventory, iMerchant, this.worldObj);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.onCraftGuiOpened(this);
      InventoryMerchant var4 = ((ContainerMerchant)this.openContainer).getMerchantInventory();
      IChatComponent var5 = iMerchant.getDisplayName();
      this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, "minecraft:villager", var5, var4.getSizeInventory()));
      MerchantRecipeList var6 = iMerchant.getRecipes(this);
      if (var6 != null) {
         PacketBuffer var7 = new PacketBuffer(Unpooled.buffer());
         var7.writeInt(this.currentWindowId);
         var6.writeToBuf(var7);
         this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|TrList", var7));
      }
   }

   @Override
   public void displayGUIHorse(EntityHorse entityHorse, IInventory iInventory) {
      if (this.openContainer != this.inventoryContainer) {
         this.closeScreen();
      }

      this.getNextWindowId();
      this.playerNetServerHandler
         .sendPacket(
            new S2DPacketOpenWindow(this.currentWindowId, "EntityHorse", iInventory.getDisplayName(), iInventory.getSizeInventory(), entityHorse.getEntityId())
         );
      this.openContainer = new ContainerHorseInventory(this.inventory, iInventory, entityHorse, this);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.onCraftGuiOpened(this);
   }

   @Override
   public void displayGUIBook(ItemStack itemStack) {
      Item var4 = itemStack.getItem();
      if (var4 == Items.written_book) {
         this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
      }
   }

   @Override
   public void sendSlotContents(Container container, int integer, ItemStack itemStack) {
      if (!(container.getSlot(integer) instanceof SlotCrafting) && !this.isChangingQuantityOnly) {
         this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(container.windowId, integer, itemStack));
      }
   }

   public void sendContainerToPlayer(Container container) {
      this.updateCraftingInventory(container, container.getInventory());
   }

   @Override
   public void updateCraftingInventory(Container container, List<ItemStack> list) {
      this.playerNetServerHandler.sendPacket(new S30PacketWindowItems(container.windowId, list));
      this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
   }

   @Override
   public void sendProgressBarUpdate(Container container, int integer2, int integer3) {
      this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(container.windowId, integer2, integer3));
   }

   @Override
   public void sendAllWindowProperties(Container container, IInventory iInventory) {
      for (int var6 = 0; var6 < iInventory.getFieldCount(); var6++) {
         this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(container.windowId, var6, iInventory.getField(var6)));
      }
   }

   @Override
   public void closeScreen() {
      this.playerNetServerHandler.sendPacket(new S2EPacketCloseWindow(this.openContainer.windowId));
      this.closeContainer();
   }

   public void updateHeldItem() {
      if (!this.isChangingQuantityOnly) {
         this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
      }
   }

   public void closeContainer() {
      this.openContainer.onContainerClosed(this);
      this.openContainer = this.inventoryContainer;
   }

   public void setEntityActionState(float float1, float float2, boolean boolean3, boolean boolean4) {
      if (this.ridingEntity != null) {
         if (float1 >= -1.0F && float1 <= 1.0F) {
            this.moveStrafing = float1;
         }

         if (float2 >= -1.0F && float2 <= 1.0F) {
            this.moveForward = float2;
         }

         this.isJumping = boolean3;
         this.setSneaking(boolean4);
      }
   }

   @Override
   public void addStat(StatBase statBase, int integer) {
      if (statBase != null) {
         this.statsFile.increaseStat(this, statBase, integer);

         for (ScoreObjective var7 : this.getWorldScoreboard().getObjectivesFromCriteria(statBase.getCriteria())) {
            this.getWorldScoreboard().Method281(this.getName(), var7).increseScore(integer);
         }

         if (this.statsFile.func_150879_e()) {
            this.statsFile.func_150876_a(this);
         }
      }
   }

   @Override
   public void func_175145_a(StatBase statBase) {
      if (statBase != null) {
         this.statsFile.unlockAchievement(this, statBase, 0);

         for (ScoreObjective var6 : this.getWorldScoreboard().getObjectivesFromCriteria(statBase.getCriteria())) {
            this.getWorldScoreboard().Method281(this.getName(), var6).setScorePoints(0);
         }

         if (this.statsFile.func_150879_e()) {
            this.statsFile.func_150876_a(this);
         }
      }
   }

   public void mountEntityAndWakeUp() {
      if (this.riddenByEntity != null) {
         this.riddenByEntity.mountEntity(this);
      }

      if (this.sleeping) {
         this.wakeUpPlayer(true, false, false);
      }
   }

   public void setPlayerHealthUpdated() {
      this.lastHealth = -1.0E8F;
   }

   @Override
   public void addChatComponentMessage(IChatComponent iChatComponent) {
      this.playerNetServerHandler.sendPacket(new S02PacketChat(iChatComponent));
   }

   @Override
   protected void onItemUseFinish() {
      this.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(this, (byte)9));
      super.onItemUseFinish();
   }

   @Override
   public void setItemInUse(ItemStack itemStack, int integer) {
      super.setItemInUse(itemStack, integer);
      if (itemStack != null && itemStack.getItem() != null && itemStack.getItem().getItemUseAction(itemStack) == EnumAction.EAT) {
         this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 3));
      }
   }

   @Override
   public void clonePlayer(EntityPlayer entityPlayer, boolean boolean2) {
      super.clonePlayer(entityPlayer, boolean2);
      this.lastExperience = -1;
      this.lastHealth = -1.0F;
      this.lastFoodLevel = -1;
      this.destroyedItemsNetCache.addAll(((EntityPlayerMP)entityPlayer).destroyedItemsNetCache);
   }

   @Override
   protected void onNewPotionEffect(PotionEffect potionEffect) {
      super.onNewPotionEffect(potionEffect);
      this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), potionEffect));
   }

   @Override
   protected void onChangedPotionEffect(PotionEffect potionEffect, boolean boolean2) {
      super.onChangedPotionEffect(potionEffect, boolean2);
      this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), potionEffect));
   }

   @Override
   protected void onFinishedPotionEffect(PotionEffect potionEffect) {
      super.onFinishedPotionEffect(potionEffect);
      this.playerNetServerHandler.sendPacket(new S1EPacketRemoveEntityEffect(this.getEntityId(), potionEffect));
   }

   @Override
   public void setPositionAndUpdate(double double1, double double2, double double3) {
      this.playerNetServerHandler.setPlayerLocation(double1, double2, double3, this.rotationYaw, this.rotationPitch);
   }

   @Override
   public void onCriticalHit(Entity entity) {
      this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(entity, 4));
   }

   @Override
   public void onEnchantmentCritical(Entity entity) {
      this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(entity, 5));
   }

   @Override
   public void sendPlayerAbilities() {
      if (this.playerNetServerHandler != null) {
         this.playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(this.capabilities));
         this.updatePotionMetadata();
      }
   }

   public WorldServer getServerForPlayer() {
      return (WorldServer)this.worldObj;
   }

   @Override
   public void setGameType(WorldSettings.GameType gameType) {
      this.theItemInWorldManager.setGameType(gameType);
      this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(3, gameType.getID()));
      if (gameType == WorldSettings.GameType.SPECTATOR) {
         this.mountEntity(null);
      } else {
         this.setSpectatingEntity(this);
      }

      this.sendPlayerAbilities();
      this.markPotionsDirty();
   }

   @Override
   public boolean isSpectator() {
      return this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR;
   }

   @Override
   public void addChatMessage(IChatComponent iChatComponent) {
      this.playerNetServerHandler.sendPacket(new S02PacketChat(iChatComponent));
   }

   @Override
   public boolean canCommandSenderUseCommand(int integer, String string) {
      if ("seed".equals(string) && !this.mcServer.isDedicatedServer()) {
         return true;
      }

      if (!"tell".equals(string) && !"help".equals(string) && !"me".equals(string) && !"trigger".equals(string)) {
         if (this.mcServer.getConfigurationManager().canSendCommands(this.getGameProfile())) {
            UserListOpsEntry var6 = this.mcServer.getConfigurationManager().getOppedPlayers().getEntry(this.getGameProfile());
            return var6 != null ? var6.getPermissionLevel() >= integer : this.mcServer.getOpPermissionLevel() >= integer;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public String getPlayerIP() {
      String var1 = this.playerNetServerHandler.netManager.getRemoteAddress().toString();
      var1 = var1.substring(var1.indexOf("/") + 1);
      return var1.substring(0, var1.indexOf(":"));
   }

   public void handleClientSettings(C15PacketClientSettings c15PacketClientSettings) {
      this.translator = c15PacketClientSettings.getLang();
      this.chatVisibility = c15PacketClientSettings.getChatVisibility();
      this.chatColours = c15PacketClientSettings.isColorsEnabled();
      this.getDataWatcher().updateObject(10, (byte)c15PacketClientSettings.getModelPartFlags());
   }

   public EntityPlayer.EnumChatVisibility getChatVisibility() {
      return this.chatVisibility;
   }

   public void loadResourcePack(String string1, String string2) {
      this.playerNetServerHandler.sendPacket(new S48PacketResourcePackSend(string1, string2));
   }

   @Override
   public BlockPos getPosition() {
      return new BlockPos(this.posX, this.posY + 0.5, this.posZ);
   }

   public void markPlayerActive() {
      this.playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
   }

   public StatisticsFile getStatFile() {
      return this.statsFile;
   }

   public void removeEntity(Entity entity) {
      if (entity instanceof EntityPlayer) {
         this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(entity.getEntityId()));
      } else {
         this.destroyedItemsNetCache.add(entity.getEntityId());
      }
   }

   @Override
   protected void updatePotionMetadata() {
      if (this.isSpectator()) {
         this.resetPotionEffectMetadata();
         this.setInvisible(true);
      } else {
         super.updatePotionMetadata();
      }

      this.getServerForPlayer().getEntityTracker().func_180245_a(this);
   }

   public Entity getSpectatingEntity() {
      return this.spectatingEntity == null ? this : this.spectatingEntity;
   }

   public void setSpectatingEntity(Entity entity) {
      Entity var5 = this.getSpectatingEntity();
      this.spectatingEntity = entity == null ? this : entity;
      if (var5 != this.spectatingEntity) {
         this.playerNetServerHandler.sendPacket(new S43PacketCamera(this.spectatingEntity));
         this.setPositionAndUpdate(this.spectatingEntity.posX, this.spectatingEntity.posY, this.spectatingEntity.posZ);
      }
   }

   @Override
   public void attackTargetEntityWithCurrentItem(Entity entity) {
      if (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR) {
         this.setSpectatingEntity(entity);
      } else {
         super.attackTargetEntityWithCurrentItem(entity);
      }
   }

   public long getLastActiveTime() {
      return this.playerLastActiveTime;
   }

   public IChatComponent getTabListDisplayName() {
      return null;
   }
}
