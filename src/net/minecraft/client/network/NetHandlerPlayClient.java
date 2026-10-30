package net.minecraft.client.network;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.authlib.GameProfile;
import com.moon.Moon;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.KnockbackEvent;
import com.moon.events.impl.player.PlayerPropertySyncEvent;
import com.moon.events.impl.player.ServerPositionEvent;
import com.moon.util.math.Position;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.GuardianSound;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.GuiScreenDemo;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityPickupFX;
import net.minecraft.client.player.inventory.ContainerLocalMenu;
import net.minecraft.client.player.inventory.LocalBlockIntercommunication;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.Explosion;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayClient implements INetHandlerPlayClient {
   private static final Logger logger = LogManager.getLogger();
   private final NetworkManager netManager;
   private final GameProfile profile;
   private final GuiScreen guiScreenServer;
   private final Map<UUID, NetworkPlayerInfo> playerInfoMap = Maps.newHashMap();
   private final Random avRandomizer = new Random();
   public int currentServerMaxPlayers = 20;
   private Minecraft gameController;
   private WorldClient clientWorldController;
   public boolean doneLoadingTerrain;
   private boolean field_147308_k = false;

   public NetHandlerPlayClient(Minecraft minecraft, GuiScreen guiScreen, NetworkManager networkManager, GameProfile gameProfile) {
      this.gameController = minecraft;
      this.guiScreenServer = guiScreen;
      this.netManager = networkManager;
      this.profile = gameProfile;
   }

   public void cleanup() {
      this.clientWorldController = null;
   }

   @Override
   public void handleJoinGame(S01PacketJoinGame s01PacketJoinGame) {
      PacketThreadUtil.checkThreadAndEnqueue(s01PacketJoinGame, this, this.gameController);
      this.gameController.playerController = new PlayerControllerMP(this.gameController, this);
      this.clientWorldController = new WorldClient(
         this,
         new WorldSettings(0L, s01PacketJoinGame.getGameType(), false, s01PacketJoinGame.isHardcoreMode(), s01PacketJoinGame.getWorldType()),
         s01PacketJoinGame.getDimension(),
         s01PacketJoinGame.getDifficulty(),
         this.gameController.mcProfiler
      );
      this.gameController.gameSettings.difficulty = s01PacketJoinGame.getDifficulty();
      this.gameController.loadWorld(this.clientWorldController);
      this.gameController.thePlayer.dimension = s01PacketJoinGame.getDimension();
      this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
      this.gameController.thePlayer.setEntityId(s01PacketJoinGame.getEntityId());
      this.currentServerMaxPlayers = s01PacketJoinGame.getMaxPlayers();
      this.gameController.thePlayer.setReducedDebug(s01PacketJoinGame.isReducedDebugInfo());
      this.gameController.playerController.setGameType(s01PacketJoinGame.getGameType());
      this.gameController.gameSettings.sendSettingsToServer();
      this.netManager
         .sendPacket(new C17PacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).writeString(ClientBrandRetriever.getClientModName())));
   }

   @Override
   public void handleSpawnObject(S0EPacketSpawnObject s0EPacketSpawnObject) {
      PacketThreadUtil.checkThreadAndEnqueue(s0EPacketSpawnObject, this, this.gameController);
      double var5 = s0EPacketSpawnObject.getX() / 32.0;
      double var7 = s0EPacketSpawnObject.getY() / 32.0;
      double var9 = s0EPacketSpawnObject.getZ() / 32.0;
      Entity var11 = null;
      if (s0EPacketSpawnObject.getType() == 10) {
         var11 = EntityMinecart.getMinecart(
            this.clientWorldController, var5, var7, var9, EntityMinecart.EnumMinecartType.byNetworkID(s0EPacketSpawnObject.func_149009_m())
         );
      } else if (s0EPacketSpawnObject.getType() == 90) {
         Entity var12 = this.clientWorldController.getEntityByID(s0EPacketSpawnObject.func_149009_m());
         if (var12 instanceof EntityPlayer) {
            var11 = new EntityFishHook(this.clientWorldController, var5, var7, var9, (EntityPlayer)var12);
         }

         s0EPacketSpawnObject.func_149002_g(0);
      } else if (s0EPacketSpawnObject.getType() == 60) {
         var11 = new EntityArrow(this.clientWorldController, var5, var7, var9);
      } else if (s0EPacketSpawnObject.getType() == 61) {
         var11 = new EntitySnowball(this.clientWorldController, var5, var7, var9);
      } else if (s0EPacketSpawnObject.getType() == 71) {
         var11 = new EntityItemFrame(
            this.clientWorldController,
            new BlockPos(MathHelper.floor_double(var5), MathHelper.floor_double(var7), MathHelper.floor_double(var9)),
            EnumFacing.getHorizontal(s0EPacketSpawnObject.func_149009_m())
         );
         s0EPacketSpawnObject.func_149002_g(0);
      } else if (s0EPacketSpawnObject.getType() == 77) {
         var11 = new EntityLeashKnot(
            this.clientWorldController, new BlockPos(MathHelper.floor_double(var5), MathHelper.floor_double(var7), MathHelper.floor_double(var9))
         );
         s0EPacketSpawnObject.func_149002_g(0);
      } else if (s0EPacketSpawnObject.getType() == 65) {
         var11 = new EntityEnderPearl(this.clientWorldController, var5, var7, var9);
      } else if (s0EPacketSpawnObject.getType() == 72) {
         var11 = new EntityEnderEye(this.clientWorldController, var5, var7, var9);
      } else if (s0EPacketSpawnObject.getType() == 76) {
         var11 = new EntityFireworkRocket(this.clientWorldController, var5, var7, var9, null);
      } else if (s0EPacketSpawnObject.getType() == 63) {
         var11 = new EntityLargeFireball(
            this.clientWorldController,
            var5,
            var7,
            var9,
            s0EPacketSpawnObject.getSpeedX() / 8000.0,
            s0EPacketSpawnObject.getSpeedY() / 8000.0,
            s0EPacketSpawnObject.getSpeedZ() / 8000.0
         );
         s0EPacketSpawnObject.func_149002_g(0);
      } else if (s0EPacketSpawnObject.getType() == 64) {
         var11 = new EntitySmallFireball(
            this.clientWorldController,
            var5,
            var7,
            var9,
            s0EPacketSpawnObject.getSpeedX() / 8000.0,
            s0EPacketSpawnObject.getSpeedY() / 8000.0,
            s0EPacketSpawnObject.getSpeedZ() / 8000.0
         );
         s0EPacketSpawnObject.func_149002_g(0);
      } else if (s0EPacketSpawnObject.getType() == 66) {
         var11 = new EntityWitherSkull(
            this.clientWorldController,
            var5,
            var7,
            var9,
            s0EPacketSpawnObject.getSpeedX() / 8000.0,
            s0EPacketSpawnObject.getSpeedY() / 8000.0,
            s0EPacketSpawnObject.getSpeedZ() / 8000.0
         );
         s0EPacketSpawnObject.func_149002_g(0);
      } else if (s0EPacketSpawnObject.getType() == 62) {
         var11 = new EntityEgg(this.clientWorldController, var5, var7, var9);
      } else if (s0EPacketSpawnObject.getType() == 73) {
         var11 = new EntityPotion(this.clientWorldController, var5, var7, var9, s0EPacketSpawnObject.func_149009_m());
         s0EPacketSpawnObject.func_149002_g(0);
      } else if (s0EPacketSpawnObject.getType() == 75) {
         var11 = new EntityExpBottle(this.clientWorldController, var5, var7, var9);
         s0EPacketSpawnObject.func_149002_g(0);
      } else if (s0EPacketSpawnObject.getType() == 1) {
         var11 = new EntityBoat(this.clientWorldController, var5, var7, var9);
      } else if (s0EPacketSpawnObject.getType() == 50) {
         var11 = new EntityTNTPrimed(this.clientWorldController, var5, var7, var9, null);
      } else if (s0EPacketSpawnObject.getType() == 78) {
         var11 = new EntityArmorStand(this.clientWorldController, var5, var7, var9);
      } else if (s0EPacketSpawnObject.getType() == 51) {
         var11 = new EntityEnderCrystal(this.clientWorldController, var5, var7, var9);
      } else if (s0EPacketSpawnObject.getType() == 2) {
         var11 = new EntityItem(this.clientWorldController, var5, var7, var9);
      } else if (s0EPacketSpawnObject.getType() == 70) {
         var11 = new EntityFallingBlock(this.clientWorldController, var5, var7, var9, Block.getStateById(s0EPacketSpawnObject.func_149009_m() & 65535));
         s0EPacketSpawnObject.func_149002_g(0);
      }

      if (var11 != null) {
         var11.serverPosX = s0EPacketSpawnObject.getX();
         var11.serverPosY = s0EPacketSpawnObject.getY();
         var11.serverPosZ = s0EPacketSpawnObject.getZ();
         var11.rotationPitch = s0EPacketSpawnObject.getPitch() * 360 / 256.0F;
         var11.rotationYaw = s0EPacketSpawnObject.getYaw() * 360 / 256.0F;
         Entity[] var18 = var11.getParts();
         if (var18 != null) {
            int var13 = s0EPacketSpawnObject.getEntityID() - var11.getEntityId();

            for (Entity var17 : var18) {
               var17.setEntityId(var17.getEntityId() + var13);
            }
         }

         var11.setEntityId(s0EPacketSpawnObject.getEntityID());
         this.clientWorldController.addEntityToWorld(s0EPacketSpawnObject.getEntityID(), var11);
         if (s0EPacketSpawnObject.func_149009_m() > 0) {
            if (s0EPacketSpawnObject.getType() == 60) {
               Entity var19 = this.clientWorldController.getEntityByID(s0EPacketSpawnObject.func_149009_m());
               if (var19 instanceof EntityLivingBase && var11 instanceof EntityArrow) {
                  ((EntityArrow)var11).shootingEntity = var19;
               }
            }

            var11.setVelocity(s0EPacketSpawnObject.getSpeedX() / 8000.0, s0EPacketSpawnObject.getSpeedY() / 8000.0, s0EPacketSpawnObject.getSpeedZ() / 8000.0);
         }
      }
   }

   @Override
   public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb s11PacketSpawnExperienceOrb) {
      PacketThreadUtil.checkThreadAndEnqueue(s11PacketSpawnExperienceOrb, this, this.gameController);
      EntityXPOrb var2 = new EntityXPOrb(
         this.clientWorldController,
         s11PacketSpawnExperienceOrb.getX() / 32.0,
         s11PacketSpawnExperienceOrb.getY() / 32.0,
         s11PacketSpawnExperienceOrb.getZ() / 32.0,
         s11PacketSpawnExperienceOrb.getXPValue()
      );
      var2.serverPosX = s11PacketSpawnExperienceOrb.getX();
      var2.serverPosY = s11PacketSpawnExperienceOrb.getY();
      var2.serverPosZ = s11PacketSpawnExperienceOrb.getZ();
      var2.rotationYaw = 0.0F;
      var2.rotationPitch = 0.0F;
      var2.setEntityId(s11PacketSpawnExperienceOrb.getEntityID());
      this.clientWorldController.addEntityToWorld(s11PacketSpawnExperienceOrb.getEntityID(), var2);
   }

   @Override
   public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity s2CPacketSpawnGlobalEntity) {
      PacketThreadUtil.checkThreadAndEnqueue(s2CPacketSpawnGlobalEntity, this, this.gameController);
      double var5 = s2CPacketSpawnGlobalEntity.func_149051_d() / 32.0;
      double var7 = s2CPacketSpawnGlobalEntity.func_149050_e() / 32.0;
      double var9 = s2CPacketSpawnGlobalEntity.func_149049_f() / 32.0;
      EntityLightningBolt var11 = null;
      if (s2CPacketSpawnGlobalEntity.func_149053_g() == 1) {
         var11 = new EntityLightningBolt(this.clientWorldController, var5, var7, var9);
      }

      if (var11 != null) {
         var11.serverPosX = s2CPacketSpawnGlobalEntity.func_149051_d();
         var11.serverPosY = s2CPacketSpawnGlobalEntity.func_149050_e();
         var11.serverPosZ = s2CPacketSpawnGlobalEntity.func_149049_f();
         var11.rotationYaw = 0.0F;
         var11.rotationPitch = 0.0F;
         var11.setEntityId(s2CPacketSpawnGlobalEntity.func_149052_c());
         this.clientWorldController.addWeatherEffect(var11);
      }
   }

   @Override
   public void handleSpawnPainting(S10PacketSpawnPainting s10PacketSpawnPainting) {
      PacketThreadUtil.checkThreadAndEnqueue(s10PacketSpawnPainting, this, this.gameController);
      EntityPainting var2 = new EntityPainting(
         this.clientWorldController, s10PacketSpawnPainting.getPosition(), s10PacketSpawnPainting.getFacing(), s10PacketSpawnPainting.getTitle()
      );
      this.clientWorldController.addEntityToWorld(s10PacketSpawnPainting.getEntityID(), var2);
   }

   @Override
   public void handleEntityVelocity(S12PacketEntityVelocity s12PacketEntityVelocity) {
      PacketThreadUtil.checkThreadAndEnqueue(s12PacketEntityVelocity, this, this.gameController);
      Entity var5 = this.clientWorldController.getEntityByID(s12PacketEntityVelocity.getEntityID());
      Position var6 = new Position(
         s12PacketEntityVelocity.getMotionZ() / 8000.0, s12PacketEntityVelocity.getMotionX() / 8000.0, s12PacketEntityVelocity.getMotionY() / 8000.0
      );
      KnockbackEvent var7 = new KnockbackEvent(var6, false, false, false);
      if (s12PacketEntityVelocity.getEntityID() == this.gameController.thePlayer.getEntityId()) {
         Moon.getInstance().getEventBus().publish(var7);
      }

      if (!var7.isCancelled() && var5 != null) {
         var5.setVelocity(
            var7.isIgnoreZ() ? var5.motionX : var6.getX(), var7.isIgnoreY() ? var5.motionY : var6.getY(), var7.isIgnoreZ() ? var5.motionZ : var6.getZ()
         );
      }
   }

   @Override
   public void handleEntityMetadata(S1CPacketEntityMetadata s1CPacketEntityMetadata) {
      PacketThreadUtil.checkThreadAndEnqueue(s1CPacketEntityMetadata, this, this.gameController);
      Entity var4 = this.clientWorldController.getEntityByID(s1CPacketEntityMetadata.getEntityId());
      if (var4 != null && s1CPacketEntityMetadata.func_149376_c() != null) {
         var4.getDataWatcher().updateWatchedObjectsFromList(s1CPacketEntityMetadata.func_149376_c());
      }
   }

   @Override
   public void handleSpawnPlayer(S0CPacketSpawnPlayer s0CPacketSpawnPlayer) {
      PacketThreadUtil.checkThreadAndEnqueue(s0CPacketSpawnPlayer, this, this.gameController);
      double var5 = s0CPacketSpawnPlayer.getX() / 32.0;
      double var7 = s0CPacketSpawnPlayer.getY() / 32.0;
      double var9 = s0CPacketSpawnPlayer.getZ() / 32.0;
      float var11 = s0CPacketSpawnPlayer.getYaw() * 360 / 256.0F;
      float var12 = s0CPacketSpawnPlayer.getPitch() * 360 / 256.0F;
      NetworkPlayerInfo var13 = this.getPlayerInfo(s0CPacketSpawnPlayer.getPlayer());
      Objects.requireNonNull(var13);
      GameProfile var14 = var13.getGameProfile();
      Objects.requireNonNull(var14);
      EntityOtherPlayerMP var15 = new EntityOtherPlayerMP(this.gameController.theWorld, var14);
      var15.prevPosX = var15.lastTickPosX = var15.serverPosX = s0CPacketSpawnPlayer.getX();
      var15.prevPosY = var15.lastTickPosY = var15.serverPosY = s0CPacketSpawnPlayer.getY();
      var15.prevPosZ = var15.lastTickPosZ = var15.serverPosZ = s0CPacketSpawnPlayer.getZ();
      int var16 = s0CPacketSpawnPlayer.getCurrentItemID();
      if (var16 == 0) {
         var15.inventory.mainInventory[var15.inventory.currentItem] = null;
      } else {
         var15.inventory.mainInventory[var15.inventory.currentItem] = new ItemStack(Item.getItemById(var16), 1, 0);
      }

      var15.setPositionAndRotation(var5, var7, var9, var11, var12);
      this.clientWorldController.addEntityToWorld(s0CPacketSpawnPlayer.getEntityID(), var15);
      List var17 = s0CPacketSpawnPlayer.func_148944_c();
      if (var17 != null) {
         var15.getDataWatcher().updateWatchedObjectsFromList(var17);
      }
   }

   @Override
   public void handleEntityTeleport(S18PacketEntityTeleport s18PacketEntityTeleport) {
      PacketThreadUtil.checkThreadAndEnqueue(s18PacketEntityTeleport, this, this.gameController);
      Entity var5 = this.clientWorldController.getEntityByID(s18PacketEntityTeleport.getEntityId());
      if (var5 != null) {
         var5.serverPosX = s18PacketEntityTeleport.getX();
         var5.serverPosY = s18PacketEntityTeleport.getY();
         var5.serverPosZ = s18PacketEntityTeleport.getZ();
         double var6 = var5.serverPosX / 32.0;
         double var8 = var5.serverPosY / 32.0;
         double var10 = var5.serverPosZ / 32.0;
         float var12 = s18PacketEntityTeleport.getYaw() * 360 / 256.0F;
         float var13 = s18PacketEntityTeleport.getPitch() * 360 / 256.0F;
         if (Math.abs(var5.posX - var6) < 0.03125 && Math.abs(var5.posY - var8) < 0.015625 && Math.abs(var5.posZ - var10) < 0.03125) {
            var5.setPositionAndRotation2(var5.posX, var5.posY, var5.posZ, var12, var13, 3, true);
         } else {
            var5.setPositionAndRotation2(var6, var8, var10, var12, var13, 3, true);
         }

         var5.onGround = s18PacketEntityTeleport.getOnGround();
      }
   }

   @Override
   public void handleHeldItemChange(S09PacketHeldItemChange s09PacketHeldItemChange) {
      PacketThreadUtil.checkThreadAndEnqueue(s09PacketHeldItemChange, this, this.gameController);
      if (s09PacketHeldItemChange.getHeldItemHotbarIndex() >= 0 && s09PacketHeldItemChange.getHeldItemHotbarIndex() < InventoryPlayer.getHotbarSize()) {
         this.gameController.thePlayer.inventory.currentItem = s09PacketHeldItemChange.getHeldItemHotbarIndex();
      }
   }

   @Override
   public void handleEntityMovement(S14PacketEntity s14PacketEntity) {
      PacketThreadUtil.checkThreadAndEnqueue(s14PacketEntity, this, this.gameController);
      Entity var5 = s14PacketEntity.getEntity(this.clientWorldController);
      if (var5 != null) {
         var5.serverPosX = var5.serverPosX + s14PacketEntity.func_149062_c();
         var5.serverPosY = var5.serverPosY + s14PacketEntity.func_149061_d();
         var5.serverPosZ = var5.serverPosZ + s14PacketEntity.func_149064_e();
         double var6 = var5.serverPosX / 32.0;
         double var8 = var5.serverPosY / 32.0;
         double var10 = var5.serverPosZ / 32.0;
         float var12 = s14PacketEntity.func_149060_h() ? s14PacketEntity.func_149066_f() * 360 / 256.0F : var5.rotationYaw;
         float var13 = s14PacketEntity.func_149060_h() ? s14PacketEntity.func_149063_g() * 360 / 256.0F : var5.rotationPitch;
         var5.setPositionAndRotation2(var6, var8, var10, var12, var13, 3, false);
         var5.onGround = s14PacketEntity.getOnGround();
      }
   }

   @Override
   public void handleEntityHeadLook(S19PacketEntityHeadLook s19PacketEntityHeadLook) {
      PacketThreadUtil.checkThreadAndEnqueue(s19PacketEntityHeadLook, this, this.gameController);
      Entity var4 = s19PacketEntityHeadLook.getEntity(this.clientWorldController);
      if (var4 != null) {
         float var5 = s19PacketEntityHeadLook.getYaw() * 360 / 256.0F;
         var4.setRotationYawHead(var5);
      }
   }

   @Override
   public void handleDestroyEntities(S13PacketDestroyEntities s13PacketDestroyEntities) {
      PacketThreadUtil.checkThreadAndEnqueue(s13PacketDestroyEntities, this, this.gameController);

      for (int var5 = 0; var5 < s13PacketDestroyEntities.getEntityIDs().length; var5++) {
         this.clientWorldController.removeEntityFromWorld(s13PacketDestroyEntities.getEntityIDs()[var5]);
      }
   }

   @Override
   public void handlePlayerPosLook(S08PacketPlayerPosLook s08PacketPlayerPosLook) {
      PacketThreadUtil.checkThreadAndEnqueue(s08PacketPlayerPosLook, this, this.gameController);
      EntityPlayerSP var5 = this.gameController.thePlayer;
      double var6 = s08PacketPlayerPosLook.getX();
      double var8 = s08PacketPlayerPosLook.getY();
      double var10 = s08PacketPlayerPosLook.getZ();
      float var12 = s08PacketPlayerPosLook.getYaw();
      float var13 = s08PacketPlayerPosLook.Method909();
      ServerPositionEvent var14 = new ServerPositionEvent();
      Moon.getInstance().getEventBus().publish(var14);
      if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
         var6 += var5.posX;
      } else if (!var14.hasPosition()) {
         var5.motionX = 0.0;
      }

      if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
         var8 += var5.posY;
      } else if (!var14.hasPosition()) {
         var5.motionY = 0.0;
      }

      if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
         var10 += var5.posZ;
      } else if (!var14.hasPosition()) {
         var5.motionZ = 0.0;
      }

      if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
         var13 += var5.rotationPitch;
      }

      if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
         var12 += var5.rotationYaw;
      }

      if (var14.hasRotation()) {
         var5.setPositionAndRotation(var6, var8, var10, var5.rotationYaw, var5.rotationPitch);
         C03PacketPlayer.C06PacketPlayerPosLook var15 = new C03PacketPlayer.C06PacketPlayerPosLook(
            var5.posX, var5.getEntityBoundingBox().minY, var5.posZ, var12, var13, false
         );
         var15.Method474(true);
         this.Method754(var15);
      } else {
         var5.setPositionAndRotation(var6, var8, var10, var12, var13);
         C03PacketPlayer.C06PacketPlayerPosLook var16 = new C03PacketPlayer.C06PacketPlayerPosLook(
            var5.posX, var5.getEntityBoundingBox().minY, var5.posZ, var5.rotationYaw, var5.rotationPitch, false
         );
         var16.Method474(true);
         this.Method754(var16);
      }

      if (!this.doneLoadingTerrain) {
         this.gameController.thePlayer.prevPosX = this.gameController.thePlayer.posX;
         this.gameController.thePlayer.prevPosY = this.gameController.thePlayer.posY;
         this.gameController.thePlayer.prevPosZ = this.gameController.thePlayer.posZ;
         this.doneLoadingTerrain = true;
         this.gameController.displayGuiScreen(null);
      }
   }

   @Override
   public void handleMultiBlockChange(S22PacketMultiBlockChange s22PacketMultiBlockChange) {
      PacketThreadUtil.checkThreadAndEnqueue(s22PacketMultiBlockChange, this, this.gameController);

      for (S22PacketMultiBlockChange.BlockUpdateData var8 : s22PacketMultiBlockChange.getChangedBlocks()) {
         this.clientWorldController.invalidateRegionAndSetBlock(var8.getPos(), var8.getBlockState());
      }
   }

   @Override
   public void handleChunkData(S21PacketChunkData s21PacketChunkData) {
      PacketThreadUtil.checkThreadAndEnqueue(s21PacketChunkData, this, this.gameController);
      if (s21PacketChunkData.func_149274_i()) {
         if (s21PacketChunkData.getExtractedSize() == 0) {
            this.clientWorldController.doPreChunk(s21PacketChunkData.getChunkX(), s21PacketChunkData.getChunkZ(), false);
            return;
         }

         this.clientWorldController.doPreChunk(s21PacketChunkData.getChunkX(), s21PacketChunkData.getChunkZ(), true);
      }

      this.clientWorldController
         .invalidateBlockReceiveRegion(
            s21PacketChunkData.getChunkX() << 4,
            0,
            s21PacketChunkData.getChunkZ() << 4,
            (s21PacketChunkData.getChunkX() << 4) + 15,
            256,
            (s21PacketChunkData.getChunkZ() << 4) + 15
         );
      Chunk var5 = this.clientWorldController.getChunkFromChunkCoords(s21PacketChunkData.getChunkX(), s21PacketChunkData.getChunkZ());
      var5.fillChunk(s21PacketChunkData.getExtractedDataBytes(), s21PacketChunkData.getExtractedSize(), s21PacketChunkData.func_149274_i());
      this.clientWorldController
         .markBlockRangeForRenderUpdate(
            s21PacketChunkData.getChunkX() << 4,
            0,
            s21PacketChunkData.getChunkZ() << 4,
            (s21PacketChunkData.getChunkX() << 4) + 15,
            256,
            (s21PacketChunkData.getChunkZ() << 4) + 15
         );
      if (!s21PacketChunkData.func_149274_i() || !(this.clientWorldController.provider instanceof WorldProviderSurface)) {
         var5.resetRelightChecks();
      }
   }

   @Override
   public void handleBlockChange(S23PacketBlockChange s23PacketBlockChange) {
      PacketThreadUtil.checkThreadAndEnqueue(s23PacketBlockChange, this, this.gameController);
      this.clientWorldController.invalidateRegionAndSetBlock(s23PacketBlockChange.getBlockPosition(), s23PacketBlockChange.getBlockState());
   }

   @Override
   public void handleDisconnect(S40PacketDisconnect s40PacketDisconnect) {
      this.netManager.closeChannel(s40PacketDisconnect.getReason());
   }

   @Override
   public void onDisconnect(IChatComponent iChatComponent) {
      this.gameController.loadWorld(null);
      this.gameController
         .displayGuiScreen(
            new GuiDisconnected(
               Objects.requireNonNullElseGet(this.guiScreenServer, () -> new GuiMultiplayer(new GuiMainMenu())), "disconnect.lost", iChatComponent
            )
         );
   }

   public void Method754(Packet packet) {
      PacketEvent var5 = new PacketEvent(true, packet);
      Moon.getInstance().getEventBus().publish(var5);
      if (!var5.isCancelled()) {
         if (packet instanceof C09PacketHeldItemChange var6) {
            this.gameController.thePlayer.bC.deprecatedNoOpForSlot(var6.getSlotId());
         }

         this.netManager.sendPacket(packet);
      }
   }

   public void Method755(Packet<?> packet) {
      this.netManager.sendPacket(packet);
   }

   @Override
   public void handleCollectItem(S0DPacketCollectItem s0DPacketCollectItem) {
      PacketThreadUtil.checkThreadAndEnqueue(s0DPacketCollectItem, this, this.gameController);
      Entity var5 = this.clientWorldController.getEntityByID(s0DPacketCollectItem.getCollectedItemEntityID());
      EntityLivingBase var6 = (EntityLivingBase)this.clientWorldController.getEntityByID(s0DPacketCollectItem.getEntityID());
      if (var6 == null) {
         var6 = this.gameController.thePlayer;
      }

      if (var5 != null) {
         if (var5 instanceof EntityXPOrb) {
            this.clientWorldController
               .playSoundAtEntity(var5, "random.orb", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         } else {
            this.clientWorldController
               .playSoundAtEntity(var5, "random.pop", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         }

         this.gameController.effectRenderer.addEffect(new EntityPickupFX(this.clientWorldController, var5, var6, 0.5F));
         this.clientWorldController.removeEntityFromWorld(s0DPacketCollectItem.getCollectedItemEntityID());
      }
   }

   @Override
   public void handleChat(S02PacketChat s02PacketChat) {
      PacketThreadUtil.checkThreadAndEnqueue(s02PacketChat, this, this.gameController);
      if (s02PacketChat.getType() == 2) {
         this.gameController.ingameGUI.setRecordPlaying(s02PacketChat.getChatComponent(), false);
      } else {
         this.gameController.ingameGUI.getChatGUI().printChatMessage(s02PacketChat.getChatComponent());
      }
   }

   @Override
   public void handleAnimation(S0BPacketAnimation s0BPacketAnimation) {
      PacketThreadUtil.checkThreadAndEnqueue(s0BPacketAnimation, this, this.gameController);
      Entity var5 = this.clientWorldController.getEntityByID(s0BPacketAnimation.getEntityID());
      if (var5 != null) {
         if (s0BPacketAnimation.getAnimationType() == 0) {
            EntityLivingBase var6 = (EntityLivingBase)var5;
            var6.swingItem();
         } else if (s0BPacketAnimation.getAnimationType() == 1) {
            var5.performHurtAnimation();
         } else if (s0BPacketAnimation.getAnimationType() == 2) {
            EntityPlayer var7 = (EntityPlayer)var5;
            var7.wakeUpPlayer(false, false, false);
         } else if (s0BPacketAnimation.getAnimationType() == 4) {
            this.gameController.effectRenderer.emitParticleAtEntity(var5, EnumParticleTypes.CRIT);
         } else if (s0BPacketAnimation.getAnimationType() == 5) {
            this.gameController.effectRenderer.emitParticleAtEntity(var5, EnumParticleTypes.CRIT_MAGIC);
         }
      }
   }

   @Override
   public void handleUseBed(S0APacketUseBed s0APacketUseBed) {
      PacketThreadUtil.checkThreadAndEnqueue(s0APacketUseBed, this, this.gameController);
      s0APacketUseBed.getPlayer(this.clientWorldController).trySleep(s0APacketUseBed.getBedPosition());
   }

   @Override
   public void handleSpawnMob(S0FPacketSpawnMob s0FPacketSpawnMob) {
      PacketThreadUtil.checkThreadAndEnqueue(s0FPacketSpawnMob, this, this.gameController);
      double var5 = s0FPacketSpawnMob.getX() / 32.0;
      double var7 = s0FPacketSpawnMob.getY() / 32.0;
      double var9 = s0FPacketSpawnMob.getZ() / 32.0;
      float var11 = s0FPacketSpawnMob.getYaw() * 360 / 256.0F;
      float var12 = s0FPacketSpawnMob.getPitch() * 360 / 256.0F;
      EntityLivingBase var13 = (EntityLivingBase)EntityList.Method512(s0FPacketSpawnMob.getEntityType(), this.gameController.theWorld);
      var13.serverPosX = s0FPacketSpawnMob.getX();
      var13.serverPosY = s0FPacketSpawnMob.getY();
      var13.serverPosZ = s0FPacketSpawnMob.getZ();
      var13.renderYawOffset = var13.rotationYawHead = s0FPacketSpawnMob.getHeadPitch() * 360 / 256.0F;
      Entity[] var14 = var13.getParts();
      if (var14 != null) {
         int var15 = s0FPacketSpawnMob.getEntityID() - var13.getEntityId();

         for (Entity var19 : var14) {
            var19.setEntityId(var19.getEntityId() + var15);
         }
      }

      var13.setEntityId(s0FPacketSpawnMob.getEntityID());
      var13.setPositionAndRotation(var5, var7, var9, var11, var12);
      var13.motionX = s0FPacketSpawnMob.getVelocityX() / 8000.0F;
      var13.motionY = s0FPacketSpawnMob.getVelocityY() / 8000.0F;
      var13.motionZ = s0FPacketSpawnMob.getVelocityZ() / 8000.0F;
      this.clientWorldController.addEntityToWorld(s0FPacketSpawnMob.getEntityID(), var13);
      List var20 = s0FPacketSpawnMob.func_149027_c();
      if (var20 != null) {
         var13.getDataWatcher().updateWatchedObjectsFromList(var20);
      }
   }

   @Override
   public void handleTimeUpdate(S03PacketTimeUpdate s03PacketTimeUpdate) {
      PacketThreadUtil.checkThreadAndEnqueue(s03PacketTimeUpdate, this, this.gameController);
      this.gameController.theWorld.setTotalWorldTime(s03PacketTimeUpdate.getTotalWorldTime());
      this.gameController.theWorld.setWorldTime(s03PacketTimeUpdate.getWorldTime());
   }

   @Override
   public void handleSpawnPosition(S05PacketSpawnPosition s05PacketSpawnPosition) {
      PacketThreadUtil.checkThreadAndEnqueue(s05PacketSpawnPosition, this, this.gameController);
      this.gameController.thePlayer.setSpawnPoint(s05PacketSpawnPosition.getSpawnPos(), true);
      this.gameController.theWorld.getWorldInfo().setSpawn(s05PacketSpawnPosition.getSpawnPos());
   }

   @Override
   public void handleEntityAttach(S1BPacketEntityAttach s1BPacketEntityAttach) {
      PacketThreadUtil.checkThreadAndEnqueue(s1BPacketEntityAttach, this, this.gameController);
      Entity var5 = this.clientWorldController.getEntityByID(s1BPacketEntityAttach.getEntityId());
      Entity var6 = this.clientWorldController.getEntityByID(s1BPacketEntityAttach.getVehicleEntityId());
      if (s1BPacketEntityAttach.getLeash() == 0) {
         boolean var7 = false;
         if (s1BPacketEntityAttach.getEntityId() == this.gameController.thePlayer.getEntityId()) {
            var5 = this.gameController.thePlayer;
            if (var6 instanceof EntityBoat) {
               ((EntityBoat)var6).setIsBoatEmpty(false);
            }

            var7 = var5.ridingEntity == null && var6 != null;
         } else if (var6 instanceof EntityBoat) {
            ((EntityBoat)var6).setIsBoatEmpty(true);
         }

         if (var5 == null) {
            return;
         }

         var5.mountEntity(var6);
         if (var7) {
            GameSettings var8 = this.gameController.gameSettings;
            this.gameController
               .ingameGUI
               .setRecordPlaying(I18n.format("mount.onboard", GameSettings.getKeyDisplayString(var8.keyBindSneak.getKeyCode())), false);
         }
      } else if (s1BPacketEntityAttach.getLeash() == 1 && var5 instanceof EntityLiving) {
         if (var6 != null) {
            ((EntityLiving)var5).setLeashedToEntity(var6, false);
         } else {
            ((EntityLiving)var5).clearLeashed(false, false);
         }
      }
   }

   @Override
   public void handleEntityStatus(S19PacketEntityStatus s19PacketEntityStatus) {
      PacketThreadUtil.checkThreadAndEnqueue(s19PacketEntityStatus, this, this.gameController);
      Entity var5 = s19PacketEntityStatus.getEntity(this.clientWorldController);
      if (var5 != null) {
         if (s19PacketEntityStatus.getOpCode() == 21) {
            this.gameController.getSoundHandler().playSound(new GuardianSound((EntityGuardian)var5));
         } else {
            var5.handleStatusUpdate(s19PacketEntityStatus.getOpCode());
         }
      }
   }

   @Override
   public void handleUpdateHealth(S06PacketUpdateHealth s06PacketUpdateHealth) {
      PacketThreadUtil.checkThreadAndEnqueue(s06PacketUpdateHealth, this, this.gameController);
      this.gameController.thePlayer.setPlayerSPHealth(s06PacketUpdateHealth.getHealth());
      this.gameController.thePlayer.getFoodStats().setFoodLevel(s06PacketUpdateHealth.getFoodLevel());
      this.gameController.thePlayer.getFoodStats().setFoodSaturationLevel(s06PacketUpdateHealth.getSaturationLevel());
   }

   @Override
   public void handleSetExperience(S1FPacketSetExperience s1FPacketSetExperience) {
      PacketThreadUtil.checkThreadAndEnqueue(s1FPacketSetExperience, this, this.gameController);
      this.gameController
         .thePlayer
         .setXPStats(s1FPacketSetExperience.func_149397_c(), s1FPacketSetExperience.getTotalExperience(), s1FPacketSetExperience.getLevel());
   }

   @Override
   public void handleRespawn(S07PacketRespawn s07PacketRespawn) {
      PacketThreadUtil.checkThreadAndEnqueue(s07PacketRespawn, this, this.gameController);
      if (s07PacketRespawn.getDimensionID() != this.gameController.thePlayer.dimension) {
         this.doneLoadingTerrain = false;
         Scoreboard var5 = this.clientWorldController.getScoreboard();
         this.clientWorldController = new WorldClient(
            this,
            new WorldSettings(
               0L, s07PacketRespawn.getGameType(), false, this.gameController.theWorld.getWorldInfo().isHardcoreModeEnabled(), s07PacketRespawn.getWorldType()
            ),
            s07PacketRespawn.getDimensionID(),
            s07PacketRespawn.getDifficulty(),
            this.gameController.mcProfiler
         );
         this.clientWorldController.setWorldScoreboard(var5);
         this.gameController.loadWorld(this.clientWorldController);
         this.gameController.thePlayer.dimension = s07PacketRespawn.getDimensionID();
         this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
      }

      this.gameController.setDimensionAndSpawnPlayer(s07PacketRespawn.getDimensionID());
      this.gameController.playerController.setGameType(s07PacketRespawn.getGameType());
   }

   @Override
   public void handleExplosion(S27PacketExplosion s27PacketExplosion) {
      if (!(s27PacketExplosion.getX() >= Double.POSITIVE_INFINITY)
         && !(s27PacketExplosion.getY() >= Double.POSITIVE_INFINITY)
         && !(s27PacketExplosion.getZ() >= Double.POSITIVE_INFINITY)
         && !(s27PacketExplosion.func_149149_c() >= Float.POSITIVE_INFINITY)
         && !(s27PacketExplosion.func_149144_d() >= Float.POSITIVE_INFINITY)
         && !(s27PacketExplosion.func_149147_e() >= Float.POSITIVE_INFINITY)
         && !(s27PacketExplosion.getStrength() >= Float.POSITIVE_INFINITY)) {
         PacketThreadUtil.checkThreadAndEnqueue(s27PacketExplosion, this, this.gameController);
         new Explosion(
               this.gameController.theWorld,
               null,
               s27PacketExplosion.getX(),
               s27PacketExplosion.getY(),
               s27PacketExplosion.getZ(),
               s27PacketExplosion.getStrength(),
               s27PacketExplosion.getAffectedBlockPositions()
            )
            .doExplosionB(true);
         Position var6 = new Position(s27PacketExplosion.func_149149_c(), s27PacketExplosion.func_149144_d(), s27PacketExplosion.func_149147_e());
         KnockbackEvent var7 = new KnockbackEvent(var6, true, false, false);
         if (var7.getPosition().getX() > 0.0 || var7.getPosition().getY() > 0.0 || var7.getPosition().getZ() > 0.0) {
            Moon.getInstance().getEventBus().publish(var7);
         }

         if (!var7.isCancelled()) {
            if (!var7.isIgnoreZ()) {
               this.gameController.thePlayer.motionX = this.gameController.thePlayer.motionX + var7.getPosition().getX();
               this.gameController.thePlayer.motionZ = this.gameController.thePlayer.motionZ + var7.getPosition().getZ();
            }

            if (!var7.isIgnoreY()) {
               this.gameController.thePlayer.motionY = this.gameController.thePlayer.motionY + var7.getPosition().getY();
            }
         }
      }
   }

   @Override
   public void handleOpenWindow(S2DPacketOpenWindow s2DPacketOpenWindow) {
      PacketThreadUtil.checkThreadAndEnqueue(s2DPacketOpenWindow, this, this.gameController);
      EntityPlayerSP var5 = this.gameController.thePlayer;
      if ("minecraft:container".equals(s2DPacketOpenWindow.getGuiId())) {
         var5.displayGUIChest(new InventoryBasic(s2DPacketOpenWindow.getWindowTitle(), s2DPacketOpenWindow.getSlotCount()));
         var5.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
      } else if ("minecraft:villager".equals(s2DPacketOpenWindow.getGuiId())) {
         var5.displayVillagerTradeGui(new NpcMerchant(var5, s2DPacketOpenWindow.getWindowTitle()));
         var5.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
      } else if ("EntityHorse".equals(s2DPacketOpenWindow.getGuiId())) {
         Entity var6 = this.clientWorldController.getEntityByID(s2DPacketOpenWindow.getEntityId());
         if (var6 instanceof EntityHorse) {
            var5.displayGUIHorse((EntityHorse)var6, new AnimalChest(s2DPacketOpenWindow.getWindowTitle(), s2DPacketOpenWindow.getSlotCount()));
            var5.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
         }
      } else if (!s2DPacketOpenWindow.hasSlots()) {
         var5.displayGui(new LocalBlockIntercommunication(s2DPacketOpenWindow.getGuiId(), s2DPacketOpenWindow.getWindowTitle()));
         var5.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
      } else {
         ContainerLocalMenu var7 = new ContainerLocalMenu(
            s2DPacketOpenWindow.getGuiId(), s2DPacketOpenWindow.getWindowTitle(), s2DPacketOpenWindow.getSlotCount()
         );
         var5.displayGUIChest(var7);
         var5.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
      }
   }

   @Override
   public void handleSetSlot(S2FPacketSetSlot s2FPacketSetSlot) {
      PacketThreadUtil.checkThreadAndEnqueue(s2FPacketSetSlot, this, this.gameController);
      EntityPlayerSP var5 = this.gameController.thePlayer;
      if (s2FPacketSetSlot.func_149175_c() == -1) {
         var5.inventory.setItemStack(s2FPacketSetSlot.func_149174_e());
      } else {
         boolean var6 = false;
         if (this.gameController.currentScreen instanceof GuiContainerCreative var7) {
            var6 = var7.getSelectedTabIndex() != CreativeTabs.tabInventory.getTabIndex();
         }

         if (s2FPacketSetSlot.func_149175_c() == 0 && s2FPacketSetSlot.func_149173_d() >= 36 && s2FPacketSetSlot.func_149173_d() < 45) {
            ItemStack var9 = var5.inventoryContainer.getSlot(s2FPacketSetSlot.func_149173_d()).getStack();
            if (s2FPacketSetSlot.func_149174_e() != null && (var9 == null || var9.stackSize < s2FPacketSetSlot.func_149174_e().stackSize)) {
               s2FPacketSetSlot.func_149174_e().animationsToGo = 5;
            }

            var5.inventoryContainer.putStackInSlot(s2FPacketSetSlot.func_149173_d(), s2FPacketSetSlot.func_149174_e());
         } else if (s2FPacketSetSlot.func_149175_c() == var5.openContainer.windowId && (s2FPacketSetSlot.func_149175_c() != 0 || !var6)) {
            var5.openContainer.putStackInSlot(s2FPacketSetSlot.func_149173_d(), s2FPacketSetSlot.func_149174_e());
         }
      }
   }

   @Override
   public void handleConfirmTransaction(S32PacketConfirmTransaction s32PacketConfirmTransaction) {
      PacketThreadUtil.checkThreadAndEnqueue(s32PacketConfirmTransaction, this, this.gameController);
      Container var5 = null;
      EntityPlayerSP var6 = this.gameController.thePlayer;
      if (s32PacketConfirmTransaction.getWindowId() == 0) {
         var5 = var6.inventoryContainer;
      } else if (s32PacketConfirmTransaction.getWindowId() == var6.openContainer.windowId) {
         var5 = var6.openContainer;
      }

      if (var5 != null && !s32PacketConfirmTransaction.func_148888_e()) {
         this.Method754(new C0FPacketConfirmTransaction(s32PacketConfirmTransaction.getWindowId(), s32PacketConfirmTransaction.getActionNumber(), true));
      }
   }

   @Override
   public void handleWindowItems(S30PacketWindowItems s30PacketWindowItems) {
      PacketThreadUtil.checkThreadAndEnqueue(s30PacketWindowItems, this, this.gameController);
      EntityPlayerSP var5 = this.gameController.thePlayer;
      if (s30PacketWindowItems.func_148911_c() == 0) {
         var5.inventoryContainer.putStacksInSlots(s30PacketWindowItems.getItemStacks());
      } else if (s30PacketWindowItems.func_148911_c() == var5.openContainer.windowId) {
         var5.openContainer.putStacksInSlots(s30PacketWindowItems.getItemStacks());
      }
   }

   @Override
   public void handleSignEditorOpen(S36PacketSignEditorOpen s36PacketSignEditorOpen) {
      PacketThreadUtil.checkThreadAndEnqueue(s36PacketSignEditorOpen, this, this.gameController);
      TileEntity var5 = this.clientWorldController.getTileEntity(s36PacketSignEditorOpen.getSignPosition());
      if (!(var5 instanceof TileEntitySign)) {
         var5 = new TileEntitySign();
         var5.setWorldObj(this.clientWorldController);
         var5.setPos(s36PacketSignEditorOpen.getSignPosition());
      }

      this.gameController.thePlayer.openEditSign((TileEntitySign)var5);
   }

   @Override
   public void handleUpdateSign(S33PacketUpdateSign s33PacketUpdateSign) {
      PacketThreadUtil.checkThreadAndEnqueue(s33PacketUpdateSign, this, this.gameController);
      boolean var5 = false;
      if (this.gameController.theWorld.Method322(s33PacketUpdateSign.getPos())
         && this.gameController.theWorld.getTileEntity(s33PacketUpdateSign.getPos()) instanceof TileEntitySign var7) {
         if (var7.getIsEditable()) {
            System.arraycopy(s33PacketUpdateSign.getLines(), 0, var7.signText, 0, 4);
            var7.markDirty();
         }

         var5 = true;
      }

      if (!var5 && this.gameController.thePlayer != null) {
         this.gameController
            .thePlayer
            .addChatMessage(
               new ChatComponentText(
                  "Unable to locate sign at "
                     + s33PacketUpdateSign.getPos().getX()
                     + ", "
                     + s33PacketUpdateSign.getPos().getY()
                     + ", "
                     + s33PacketUpdateSign.getPos().getZ()
               )
            );
      }
   }

   @Override
   public void handleUpdateTileEntity(S35PacketUpdateTileEntity s35PacketUpdateTileEntity) {
      PacketThreadUtil.checkThreadAndEnqueue(s35PacketUpdateTileEntity, this, this.gameController);
      if (this.gameController.theWorld.Method322(s35PacketUpdateTileEntity.getPos())) {
         TileEntity var5 = this.gameController.theWorld.getTileEntity(s35PacketUpdateTileEntity.getPos());
         int var6 = s35PacketUpdateTileEntity.getTileEntityType();
         if (var6 == 1 && var5 instanceof TileEntityMobSpawner
            || var6 == 2 && var5 instanceof TileEntityCommandBlock
            || var6 == 3 && var5 instanceof TileEntityBeacon
            || var6 == 4 && var5 instanceof TileEntitySkull
            || var6 == 5 && var5 instanceof TileEntityFlowerPot
            || var6 == 6 && var5 instanceof TileEntityBanner) {
            var5.readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
         }
      }
   }

   @Override
   public void handleWindowProperty(S31PacketWindowProperty s31PacketWindowProperty) {
      PacketThreadUtil.checkThreadAndEnqueue(s31PacketWindowProperty, this, this.gameController);
      EntityPlayerSP var5 = this.gameController.thePlayer;
      if (var5.openContainer != null && var5.openContainer.windowId == s31PacketWindowProperty.getWindowId()) {
         var5.openContainer.updateProgressBar(s31PacketWindowProperty.getVarIndex(), s31PacketWindowProperty.getVarValue());
      }
   }

   @Override
   public void handleEntityEquipment(S04PacketEntityEquipment s04PacketEntityEquipment) {
      PacketThreadUtil.checkThreadAndEnqueue(s04PacketEntityEquipment, this, this.gameController);
      Entity var5 = this.clientWorldController.getEntityByID(s04PacketEntityEquipment.getEntityID());
      if (var5 != null) {
         var5.setCurrentItemOrArmor(s04PacketEntityEquipment.getEquipmentSlot(), s04PacketEntityEquipment.getItemStack());
      }
   }

   @Override
   public void handleCloseWindow(S2EPacketCloseWindow s2EPacketCloseWindow) {
      PacketThreadUtil.checkThreadAndEnqueue(s2EPacketCloseWindow, this, this.gameController);
      this.gameController.thePlayer.closeScreenAndDropStack();
   }

   @Override
   public void handleBlockAction(S24PacketBlockAction s24PacketBlockAction) {
      PacketThreadUtil.checkThreadAndEnqueue(s24PacketBlockAction, this, this.gameController);
      this.gameController
         .theWorld
         .addBlockEvent(
            s24PacketBlockAction.getBlockPosition(), s24PacketBlockAction.getBlockType(), s24PacketBlockAction.getData1(), s24PacketBlockAction.getData2()
         );
   }

   @Override
   public void handleBlockBreakAnim(S25PacketBlockBreakAnim s25PacketBlockBreakAnim) {
      PacketThreadUtil.checkThreadAndEnqueue(s25PacketBlockBreakAnim, this, this.gameController);
      this.gameController
         .theWorld
         .sendBlockBreakProgress(s25PacketBlockBreakAnim.getBreakerId(), s25PacketBlockBreakAnim.getPosition(), s25PacketBlockBreakAnim.getProgress());
   }

   @Override
   public void handleMapChunkBulk(S26PacketMapChunkBulk s26PacketMapChunkBulk) {
      PacketThreadUtil.checkThreadAndEnqueue(s26PacketMapChunkBulk, this, this.gameController);

      for (int var5 = 0; var5 < s26PacketMapChunkBulk.getChunkCount(); var5++) {
         int var6 = s26PacketMapChunkBulk.getChunkX(var5);
         int var7 = s26PacketMapChunkBulk.getChunkZ(var5);
         this.clientWorldController.doPreChunk(var6, var7, true);
         this.clientWorldController.invalidateBlockReceiveRegion(var6 << 4, 0, var7 << 4, (var6 << 4) + 15, 256, (var7 << 4) + 15);
         Chunk var8 = this.clientWorldController.getChunkFromChunkCoords(var6, var7);
         var8.fillChunk(s26PacketMapChunkBulk.getChunkBytes(var5), s26PacketMapChunkBulk.getChunkSize(var5), true);
         this.clientWorldController.markBlockRangeForRenderUpdate(var6 << 4, 0, var7 << 4, (var6 << 4) + 15, 256, (var7 << 4) + 15);
         if (!(this.clientWorldController.provider instanceof WorldProviderSurface)) {
            var8.resetRelightChecks();
         }
      }
   }

   @Override
   public void handleChangeGameState(S2BPacketChangeGameState s2BPacketChangeGameState) {
      PacketThreadUtil.checkThreadAndEnqueue(s2BPacketChangeGameState, this, this.gameController);
      EntityPlayerSP var5 = this.gameController.thePlayer;
      int var6 = s2BPacketChangeGameState.getGameState();
      float var7 = s2BPacketChangeGameState.func_149137_d();
      int var8 = MathHelper.floor_float(var7 + 0.5F);
      if (var6 >= 0 && var6 < S2BPacketChangeGameState.MESSAGE_NAMES.length && S2BPacketChangeGameState.MESSAGE_NAMES[var6] != null) {
         var5.addChatComponentMessage(new ChatComponentTranslation(S2BPacketChangeGameState.MESSAGE_NAMES[var6]));
      }

      if (var6 == 1) {
         this.clientWorldController.getWorldInfo().setRaining(true);
         this.clientWorldController.setRainStrength(0.0F);
      } else if (var6 == 2) {
         this.clientWorldController.getWorldInfo().setRaining(false);
         this.clientWorldController.setRainStrength(1.0F);
      } else if (var6 == 3) {
         this.gameController.playerController.setGameType(WorldSettings.GameType.getByID(var8));
      } else if (var6 == 4) {
         this.gameController.displayGuiScreen(new GuiWinGame());
      } else if (var6 == 5) {
         GameSettings var9 = this.gameController.gameSettings;
         if (var7 == 0.0F) {
            this.gameController.displayGuiScreen(new GuiScreenDemo());
         } else if (var7 == 101.0F) {
            this.gameController
               .ingameGUI
               .getChatGUI()
               .printChatMessage(
                  new ChatComponentTranslation(
                     "demo.help.movement",
                     GameSettings.getKeyDisplayString(var9.keyBindForward.getKeyCode()),
                     GameSettings.getKeyDisplayString(var9.keyBindLeft.getKeyCode()),
                     GameSettings.getKeyDisplayString(var9.keyBindBack.getKeyCode()),
                     GameSettings.getKeyDisplayString(var9.keyBindRight.getKeyCode())
                  )
               );
         } else if (var7 == 102.0F) {
            this.gameController
               .ingameGUI
               .getChatGUI()
               .printChatMessage(new ChatComponentTranslation("demo.help.jump", GameSettings.getKeyDisplayString(var9.keyBindJump.getKeyCode())));
         } else if (var7 == 103.0F) {
            this.gameController
               .ingameGUI
               .getChatGUI()
               .printChatMessage(new ChatComponentTranslation("demo.help.inventory", GameSettings.getKeyDisplayString(var9.keyBindInventory.getKeyCode())));
         }
      } else if (var6 == 6) {
         this.clientWorldController.playSound(var5.posX, var5.posY + var5.getEyeHeight(), var5.posZ, "random.successful_hit", 0.18F, 0.45F, false);
      } else if (var6 == 7) {
         this.clientWorldController.setRainStrength(var7);
      } else if (var6 == 8) {
         this.clientWorldController.setThunderStrength(var7);
      } else if (var6 == 10) {
         this.clientWorldController.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, var5.posX, var5.posY, var5.posZ, 0.0, 0.0, 0.0);
         this.clientWorldController.playSound(var5.posX, var5.posY, var5.posZ, "mob.guardian.curse", 1.0F, 1.0F, false);
      }
   }

   @Override
   public void handleMaps(S34PacketMaps s34PacketMaps) {
      PacketThreadUtil.checkThreadAndEnqueue(s34PacketMaps, this, this.gameController);
      MapData var2 = ItemMap.Method528(s34PacketMaps.getMapId(), this.gameController.theWorld);
      s34PacketMaps.setMapdataTo(var2);
      this.gameController.entityRenderer.getMapItemRenderer().updateMapTexture(var2);
   }

   @Override
   public void handleEffect(S28PacketEffect s28PacketEffect) {
      PacketThreadUtil.checkThreadAndEnqueue(s28PacketEffect, this, this.gameController);
      if (s28PacketEffect.isSoundServerwide()) {
         this.gameController.theWorld.playBroadcastSound(s28PacketEffect.getSoundType(), s28PacketEffect.getSoundPos(), s28PacketEffect.getSoundData());
      } else {
         this.gameController.theWorld.playAuxSFX(s28PacketEffect.getSoundType(), s28PacketEffect.getSoundPos(), s28PacketEffect.getSoundData());
      }
   }

   @Override
   public void handleStatistics(S37PacketStatistics s37PacketStatistics) {
      PacketThreadUtil.checkThreadAndEnqueue(s37PacketStatistics, this, this.gameController);
      boolean var5 = false;

      for (Entry var7 : s37PacketStatistics.func_148974_c().entrySet()) {
         StatBase var8 = (StatBase)var7.getKey();
         int var9 = (Integer)var7.getValue();
         if (var8.isAchievement() && var9 > 0) {
            if (this.field_147308_k && this.gameController.thePlayer.getStatFileWriter().readStat(var8) == 0) {
               Achievement var10 = (Achievement)var8;
               this.gameController.guiAchievement.displayAchievement(var10);
               if (var8 == AchievementList.openInventory) {
                  this.gameController.gameSettings.showInventoryAchievementHint = false;
                  this.gameController.gameSettings.saveOptions();
               }
            }

            var5 = true;
         }

         this.gameController.thePlayer.getStatFileWriter().unlockAchievement(this.gameController.thePlayer, var8, var9);
      }

      if (!this.field_147308_k && !var5 && this.gameController.gameSettings.showInventoryAchievementHint) {
         this.gameController.guiAchievement.displayUnformattedAchievement(AchievementList.openInventory);
      }

      this.field_147308_k = true;
      if (this.gameController.currentScreen instanceof IProgressMeter) {
         ((IProgressMeter)this.gameController.currentScreen).doneLoading();
      }
   }

   @Override
   public void handleEntityEffect(S1DPacketEntityEffect s1DPacketEntityEffect) {
      PacketThreadUtil.checkThreadAndEnqueue(s1DPacketEntityEffect, this, this.gameController);
      Entity var4 = this.clientWorldController.getEntityByID(s1DPacketEntityEffect.getEntityId());
      if (var4 instanceof EntityLivingBase) {
         PotionEffect var5 = new PotionEffect(
            s1DPacketEntityEffect.getEffectId(),
            s1DPacketEntityEffect.getDuration(),
            s1DPacketEntityEffect.getAmplifier(),
            false,
            s1DPacketEntityEffect.func_179707_f()
         );
         var5.setPotionDurationMax(s1DPacketEntityEffect.func_149429_c());
         ((EntityLivingBase)var4).addPotionEffect(var5);
      }
   }

   @Override
   public void handleCombatEvent(S42PacketCombatEvent s42PacketCombatEvent) {
      PacketThreadUtil.checkThreadAndEnqueue(s42PacketCombatEvent, this, this.gameController);
   }

   @Override
   public void handleServerDifficulty(S41PacketServerDifficulty s41PacketServerDifficulty) {
      PacketThreadUtil.checkThreadAndEnqueue(s41PacketServerDifficulty, this, this.gameController);
      this.gameController.theWorld.getWorldInfo().setDifficulty(s41PacketServerDifficulty.getDifficulty());
      this.gameController.theWorld.getWorldInfo().setDifficultyLocked(s41PacketServerDifficulty.isDifficultyLocked());
   }

   @Override
   public void handleCamera(S43PacketCamera s43PacketCamera) {
      PacketThreadUtil.checkThreadAndEnqueue(s43PacketCamera, this, this.gameController);
      Entity var4 = s43PacketCamera.getEntity(this.clientWorldController);
      if (var4 != null) {
         this.gameController.setRenderViewEntity(var4);
      }
   }

   @Override
   public void handleWorldBorder(S44PacketWorldBorder s44PacketWorldBorder) {
      PacketThreadUtil.checkThreadAndEnqueue(s44PacketWorldBorder, this, this.gameController);
      s44PacketWorldBorder.func_179788_a(this.clientWorldController.getWorldBorder());
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void handleTitle(S45PacketTitle s45PacketTitle) {
      PacketThreadUtil.checkThreadAndEnqueue(s45PacketTitle, this, this.gameController);
      S45PacketTitle.Type var5 = s45PacketTitle.getType();
      String var6 = null;
      String var7 = null;
      String var8 = s45PacketTitle.getMessage() != null ? s45PacketTitle.getMessage().getFormattedText() : "";
      switch (var5) {
         case TITLE:
            var6 = var8;
            break;
         case SUBTITLE:
            var7 = var8;
            break;
         case RESET:
            this.gameController.ingameGUI.displayTitle("", "", -1, -1, -1);
            this.gameController.ingameGUI.setDefaultTitlesTimes();
            return;
      }

      this.gameController.ingameGUI.displayTitle(var6, var7, s45PacketTitle.getFadeInTime(), s45PacketTitle.getDisplayTime(), s45PacketTitle.getFadeOutTime());
   }

   @Override
   public void handleSetCompressionLevel(S46PacketSetCompressionLevel s46PacketSetCompressionLevel) {
      if (!this.netManager.isLocalChannel()) {
         this.netManager.setCompressionTreshold(s46PacketSetCompressionLevel.getThreshold());
      }
   }

   @Override
   public void handlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter s47PacketPlayerListHeaderFooter) {
      this.gameController
         .ingameGUI
         .getTabList()
         .setHeader(s47PacketPlayerListHeaderFooter.getHeader().getFormattedText().length() == 0 ? null : s47PacketPlayerListHeaderFooter.getHeader());
      this.gameController
         .ingameGUI
         .getTabList()
         .setFooter(s47PacketPlayerListHeaderFooter.getFooter().getFormattedText().length() == 0 ? null : s47PacketPlayerListHeaderFooter.getFooter());
   }

   @Override
   public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect s1EPacketRemoveEntityEffect) {
      PacketThreadUtil.checkThreadAndEnqueue(s1EPacketRemoveEntityEffect, this, this.gameController);
      Entity var5 = this.clientWorldController.getEntityByID(s1EPacketRemoveEntityEffect.getEntityId());
      if (var5 instanceof EntityLivingBase) {
         ((EntityLivingBase)var5).removePotionEffectClient(s1EPacketRemoveEntityEffect.getEffectId());
      }
   }


   @SuppressWarnings("incomplete-switch")
   public void handlePlayerListItem(S38PacketPlayerListItem packetIn)
   {
      PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.gameController);

      for (S38PacketPlayerListItem.AddPlayerData s38packetplayerlistitem$addplayerdata : packetIn.getEntries())
      {
         if (packetIn.getAction() == S38PacketPlayerListItem.Action.REMOVE_PLAYER)
         {
            this.playerInfoMap.remove(s38packetplayerlistitem$addplayerdata.getProfile().getId());
         }
         else
         {
            NetworkPlayerInfo networkplayerinfo = (NetworkPlayerInfo)this.playerInfoMap.get(s38packetplayerlistitem$addplayerdata.getProfile().getId());

            if (packetIn.getAction() == S38PacketPlayerListItem.Action.ADD_PLAYER)
            {
               networkplayerinfo = new NetworkPlayerInfo(s38packetplayerlistitem$addplayerdata);
               this.playerInfoMap.put(networkplayerinfo.getGameProfile().getId(), networkplayerinfo);
            }

            if (networkplayerinfo != null)
            {
               switch (packetIn.getAction())
               {
                  case ADD_PLAYER:
                     networkplayerinfo.setGameType(s38packetplayerlistitem$addplayerdata.getGameMode());
                     networkplayerinfo.setResponseTime(s38packetplayerlistitem$addplayerdata.getPing());
                     break;

                  case UPDATE_GAME_MODE:
                     networkplayerinfo.setGameType(s38packetplayerlistitem$addplayerdata.getGameMode());
                     break;

                  case UPDATE_LATENCY:
                     networkplayerinfo.setResponseTime(s38packetplayerlistitem$addplayerdata.getPing());
                     break;

                  case UPDATE_DISPLAY_NAME:
                     networkplayerinfo.setDisplayName(s38packetplayerlistitem$addplayerdata.getDisplayName());
               }
            }
         }
      }
   }

   @Override
   public void handleKeepAlive(S00PacketKeepAlive s00PacketKeepAlive) {
      this.Method754(new C00PacketKeepAlive(s00PacketKeepAlive.func_149134_c()));
   }

   @Override
   public void handlePlayerAbilities(S39PacketPlayerAbilities s39PacketPlayerAbilities) {
      PacketThreadUtil.checkThreadAndEnqueue(s39PacketPlayerAbilities, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.thePlayer;
      var2.capabilities.isFlying = s39PacketPlayerAbilities.isFlying();
      var2.capabilities.isCreativeMode = s39PacketPlayerAbilities.isCreativeMode();
      var2.capabilities.disableDamage = s39PacketPlayerAbilities.isInvulnerable();
      var2.capabilities.allowFlying = s39PacketPlayerAbilities.isAllowFlying();
      var2.capabilities.setFlySpeed(s39PacketPlayerAbilities.getFlySpeed());
      var2.capabilities.setPlayerWalkSpeed(s39PacketPlayerAbilities.getWalkSpeed());
   }

   @Override
   public void handleTabComplete(S3APacketTabComplete s3APacketTabComplete) {
      PacketThreadUtil.checkThreadAndEnqueue(s3APacketTabComplete, this, this.gameController);
      String[] var5 = s3APacketTabComplete.func_149630_c();
      if (this.gameController.currentScreen instanceof GuiChat var6) {
         var6.onAutocompleteResponse(var5);
      }
   }

   @Override
   public void handleSoundEffect(S29PacketSoundEffect s29PacketSoundEffect) {
      PacketThreadUtil.checkThreadAndEnqueue(s29PacketSoundEffect, this, this.gameController);
      this.gameController
         .theWorld
         .playSound(
            s29PacketSoundEffect.getX(),
            s29PacketSoundEffect.getY(),
            s29PacketSoundEffect.getZ(),
            s29PacketSoundEffect.getSoundName(),
            s29PacketSoundEffect.getVolume(),
            s29PacketSoundEffect.getPitch(),
            false
         );
   }

   @Override
   public void handleResourcePack(S48PacketResourcePackSend s48PacketResourcePackSend) {
      String var5 = s48PacketResourcePackSend.getURL();
      final String var6 = s48PacketResourcePackSend.getHash();
      if (var5.startsWith("level://")) {
         String var7 = var5.substring(8);
         File var8 = new File(this.gameController.mcDataDir, "saves");
         File var9 = new File(var8, var7);
         if (var9.isFile()) {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(var6, C19PacketResourcePackStatus.Action.ACCEPTED));
            Futures.addCallback(
               this.gameController.getResourcePackRepository().setResourcePackInstance(var9),
               new FutureCallback<Object>() {
                  @Override
                  public void onSuccess(Object object) {
                     NetHandlerPlayClient.this.netManager
                        .sendPacket(new C19PacketResourcePackStatus(var6, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                  }

                  @Override
                  public void onFailure(Throwable throwable) {
                     NetHandlerPlayClient.this.netManager.sendPacket(new C19PacketResourcePackStatus(var6, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                  }
               },
               MoreExecutors.directExecutor()
            );
         } else {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(var6, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
         }
      } else if (this.gameController.getCurrentServerData() != null
         && this.gameController.getCurrentServerData().getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
         this.netManager.sendPacket(new C19PacketResourcePackStatus(var6, C19PacketResourcePackStatus.Action.ACCEPTED));
         Futures.addCallback(this.gameController.getResourcePackRepository().downloadResourcePack(var5, var6), new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object object) {
               NetHandlerPlayClient.this.netManager.sendPacket(new C19PacketResourcePackStatus(var6, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
            }

            @Override
            public void onFailure(Throwable throwable) {
               NetHandlerPlayClient.this.netManager.sendPacket(new C19PacketResourcePackStatus(var6, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
            }
         }, MoreExecutors.directExecutor());
      } else if (this.gameController.getCurrentServerData() != null
         && this.gameController.getCurrentServerData().getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
         this.netManager.sendPacket(new C19PacketResourcePackStatus(var6, C19PacketResourcePackStatus.Action.DECLINED));
      } else {
         this.gameController
            .addScheduledTask(
               () -> this.gameController
                  .displayGuiScreen(
                     new GuiYesNo(
                        (boolean3, integer) -> {
                           this.gameController = Minecraft.getMinecraft();
                           if (boolean3) {
                              if (this.gameController.getCurrentServerData() != null) {
                                 this.gameController.getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.ENABLED);
                              }

                              this.netManager.sendPacket(new C19PacketResourcePackStatus(var6, C19PacketResourcePackStatus.Action.ACCEPTED));
                              Futures.addCallback(
                                 this.gameController.getResourcePackRepository().downloadResourcePack(var5, var6),
                                 new NetHandlerPlayClient$3$1$1(this, var6),
                                 MoreExecutors.directExecutor()
                              );
                           } else {
                              if (this.gameController.getCurrentServerData() != null) {
                                 this.gameController.getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.DISABLED);
                              }

                              this.netManager.sendPacket(new C19PacketResourcePackStatus(var6, C19PacketResourcePackStatus.Action.DECLINED));
                           }

                           ServerList.func_147414_b(this.gameController.getCurrentServerData());
                           this.gameController.displayGuiScreen(null);
                        },
                        I18n.format("multiplayer.texturePrompt.line1"),
                        I18n.format("multiplayer.texturePrompt.line2"),
                        0
                     )
                  )
            );
      }
   }

   @Override
   public void handleEntityNBT(S49PacketUpdateEntityNBT s49PacketUpdateEntityNBT) {
      PacketThreadUtil.checkThreadAndEnqueue(s49PacketUpdateEntityNBT, this, this.gameController);
      Entity var5 = s49PacketUpdateEntityNBT.getEntity(this.clientWorldController);
      if (var5 != null) {
         var5.clientUpdateEntityNBT(s49PacketUpdateEntityNBT.getTagCompound());
      }
   }

   @Override
   public void handleCustomPayload(S3FPacketCustomPayload s3FPacketCustomPayload) {
      PacketThreadUtil.checkThreadAndEnqueue(s3FPacketCustomPayload, this, this.gameController);
      if ("MC|TrList".equals(s3FPacketCustomPayload.getChannelName())) {
         PacketBuffer var5 = s3FPacketCustomPayload.getBufferData();

         try {
            int var6 = var5.readInt();
            GuiScreen var7 = this.gameController.currentScreen;
            if (var7 instanceof GuiMerchant && var6 == this.gameController.thePlayer.openContainer.windowId) {
               IMerchant var8 = ((GuiMerchant)var7).getMerchant();
               MerchantRecipeList var9 = MerchantRecipeList.readFromBuf(var5);
               var8.setRecipes(var9);
            }
         } catch (IOException var13) {
            logger.error("Couldn't load trade info", var13);
         } finally {
            var5.release();
         }
      } else if ("MC|Brand".equals(s3FPacketCustomPayload.getChannelName())) {
         this.gameController.thePlayer.setClientBrand(s3FPacketCustomPayload.getBufferData().readStringFromBuffer(32767));
      } else if ("MC|BOpen".equals(s3FPacketCustomPayload.getChannelName())) {
         ItemStack var15 = this.gameController.thePlayer.getCurrentEquippedItem();
         if (var15 != null && var15.getItem() == Items.written_book) {
            this.gameController.displayGuiScreen(new GuiScreenBook(this.gameController.thePlayer, var15, false));
         }
      }
   }

   @Override
   public void handleScoreboardObjective(S3BPacketScoreboardObjective s3BPacketScoreboardObjective) {
      PacketThreadUtil.checkThreadAndEnqueue(s3BPacketScoreboardObjective, this, this.gameController);
      Scoreboard var5 = this.clientWorldController.getScoreboard();
      if (s3BPacketScoreboardObjective.func_149338_e() == 0) {
         ScoreObjective var6 = var5.Method280(s3BPacketScoreboardObjective.func_149339_c(), IScoreObjectiveCriteria.DUMMY);
         var6.setDisplayName(s3BPacketScoreboardObjective.func_149337_d());
         var6.setRenderType(s3BPacketScoreboardObjective.func_179817_d());
      } else {
         ScoreObjective var7 = var5.getObjective(s3BPacketScoreboardObjective.func_149339_c());
         if (s3BPacketScoreboardObjective.func_149338_e() == 1) {
            var5.removeObjective(var7);
         } else if (s3BPacketScoreboardObjective.func_149338_e() == 2) {
            var7.setDisplayName(s3BPacketScoreboardObjective.func_149337_d());
            var7.setRenderType(s3BPacketScoreboardObjective.func_179817_d());
         }
      }
   }

   @Override
   public void handleUpdateScore(S3CPacketUpdateScore s3CPacketUpdateScore) {
      PacketThreadUtil.checkThreadAndEnqueue(s3CPacketUpdateScore, this, this.gameController);
      Scoreboard var5 = this.clientWorldController.getScoreboard();
      ScoreObjective var6 = var5.getObjective(s3CPacketUpdateScore.getObjectiveName());
      if (s3CPacketUpdateScore.getScoreAction() == S3CPacketUpdateScore.Action.CHANGE) {
         Score var7 = var5.Method281(s3CPacketUpdateScore.getPlayerName(), var6);
         var7.setScorePoints(s3CPacketUpdateScore.getScoreValue());
      } else if (s3CPacketUpdateScore.getScoreAction() == S3CPacketUpdateScore.Action.REMOVE) {
         if (StringUtils.isNullOrEmpty(s3CPacketUpdateScore.getObjectiveName())) {
            var5.removeObjectiveFromEntity(s3CPacketUpdateScore.getPlayerName(), null);
         } else if (var6 != null) {
            var5.removeObjectiveFromEntity(s3CPacketUpdateScore.getPlayerName(), var6);
         }
      }
   }

   @Override
   public void handleDisplayScoreboard(S3DPacketDisplayScoreboard s3DPacketDisplayScoreboard) {
      PacketThreadUtil.checkThreadAndEnqueue(s3DPacketDisplayScoreboard, this, this.gameController);
      Scoreboard var5 = this.clientWorldController.getScoreboard();
      if (s3DPacketDisplayScoreboard.func_149370_d().length() == 0) {
         var5.setObjectiveInDisplaySlot(s3DPacketDisplayScoreboard.func_149371_c(), null);
      } else {
         ScoreObjective var6 = var5.getObjective(s3DPacketDisplayScoreboard.func_149370_d());
         var5.setObjectiveInDisplaySlot(s3DPacketDisplayScoreboard.func_149371_c(), var6);
      }
   }

   @Override
   public void handleTeams(S3EPacketTeams s3EPacketTeams) {
      PacketThreadUtil.checkThreadAndEnqueue(s3EPacketTeams, this, this.gameController);
      Scoreboard var5 = this.clientWorldController.getScoreboard();
      ScorePlayerTeam var6;
      if (s3EPacketTeams.getAction() == 0) {
         var6 = var5.createTeam(s3EPacketTeams.getName());
      } else {
         var6 = var5.getTeam(s3EPacketTeams.getName());
      }

      if (s3EPacketTeams.getAction() == 0 || s3EPacketTeams.getAction() == 2) {
         var6.setTeamName(s3EPacketTeams.getDisplayName());
         var6.setNamePrefix(s3EPacketTeams.getPrefix());
         var6.setNameSuffix(s3EPacketTeams.getSuffix());
         var6.setChatFormat(EnumChatFormatting.func_175744_a(s3EPacketTeams.getColor()));
         var6.func_98298_a(s3EPacketTeams.getFriendlyFlags());
         Team.EnumVisible var7 = Team.EnumVisible.func_178824_a(s3EPacketTeams.getNameTagVisibility());
         if (var7 != null) {
            var6.setNameTagVisibility(var7);
         }
      }

      if (s3EPacketTeams.getAction() == 0 || s3EPacketTeams.getAction() == 3) {
         for (String var8 : s3EPacketTeams.getPlayers()) {
            var5.addPlayerToTeam(var8, s3EPacketTeams.getName());
         }
      }

      if (s3EPacketTeams.getAction() == 4) {
         for (String var11 : s3EPacketTeams.getPlayers()) {
            var5.removePlayerFromTeam(var11, var6);
         }
      }

      if (s3EPacketTeams.getAction() == 1) {
         var5.removeTeam(var6);
      }
   }

   @Override
   public void handleParticles(S2APacketParticles s2APacketParticles) {
      PacketThreadUtil.checkThreadAndEnqueue(s2APacketParticles, this, this.gameController);
      if (s2APacketParticles.getParticleCount() == 0) {
         double var5 = s2APacketParticles.getParticleSpeed() * s2APacketParticles.getXOffset();
         double var7 = s2APacketParticles.getParticleSpeed() * s2APacketParticles.getYOffset();
         double var9 = s2APacketParticles.getParticleSpeed() * s2APacketParticles.getZOffset();

         try {
            this.clientWorldController
               .spawnParticle(
                  s2APacketParticles.getParticleType(),
                  s2APacketParticles.isLongDistance(),
                  s2APacketParticles.getXCoordinate(),
                  s2APacketParticles.getYCoordinate(),
                  s2APacketParticles.getZCoordinate(),
                  var5,
                  var7,
                  var9,
                  s2APacketParticles.getParticleArgs()
               );
         } catch (Throwable var19) {
            logger.warn("Could not spawn particle effect " + s2APacketParticles.getParticleType());
         }
      } else {
         for (int var20 = 0; var20 < s2APacketParticles.getParticleCount(); var20++) {
            double var6 = this.avRandomizer.nextGaussian() * s2APacketParticles.getXOffset();
            double var8 = this.avRandomizer.nextGaussian() * s2APacketParticles.getYOffset();
            double var10 = this.avRandomizer.nextGaussian() * s2APacketParticles.getZOffset();
            double var12 = this.avRandomizer.nextGaussian() * s2APacketParticles.getParticleSpeed();
            double var14 = this.avRandomizer.nextGaussian() * s2APacketParticles.getParticleSpeed();
            double var16 = this.avRandomizer.nextGaussian() * s2APacketParticles.getParticleSpeed();

            try {
               this.clientWorldController
                  .spawnParticle(
                     s2APacketParticles.getParticleType(),
                     s2APacketParticles.isLongDistance(),
                     s2APacketParticles.getXCoordinate() + var6,
                     s2APacketParticles.getYCoordinate() + var8,
                     s2APacketParticles.getZCoordinate() + var10,
                     var12,
                     var14,
                     var16,
                     s2APacketParticles.getParticleArgs()
                  );
            } catch (Throwable var18) {
               logger.warn("Could not spawn particle effect " + s2APacketParticles.getParticleType());
               return;
            }
         }
      }
   }

   @Override
   public void handleEntityProperties(S20PacketEntityProperties s20PacketEntityProperties) {
      PacketThreadUtil.checkThreadAndEnqueue(s20PacketEntityProperties, this, this.gameController);
      Entity var5 = this.clientWorldController.getEntityByID(s20PacketEntityProperties.getEntityId());
      if (var5 != null) {
         if (!(var5 instanceof EntityLivingBase)) {
            throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + var5 + ")");
         }

         BaseAttributeMap var6 = ((EntityLivingBase)var5).getAttributeMap();

         for (S20PacketEntityProperties.Snapshot var8 : s20PacketEntityProperties.func_149441_d()) {
            IAttributeInstance var9 = var6.getAttributeInstanceByName(var8.func_151409_a());
            if (var9 == null) {
               var9 = var6.registerAttribute(new RangedAttribute(null, var8.func_151409_a(), 0.0, Double.MIN_NORMAL, Double.MAX_VALUE));
            }

            var9.setBaseValue(var8.func_151410_b());
            if (var5 instanceof EntityPlayerSP) {
               PlayerPropertySyncEvent var10 = new PlayerPropertySyncEvent();
               Moon.getInstance().getEventBus().publish(var10);
               var9.Method284(!var10.isCancelled(), var8.func_151408_c());
            } else {
               var9.removeAllModifiers();
            }

            for (AttributeModifier var11 : var8.func_151408_c()) {
               var9.applyModifier(var11);
            }
         }
      }
   }

   public NetworkManager getNetworkManager() {
      return this.netManager;
   }

   public Collection<NetworkPlayerInfo> getPlayerInfoMap() {
      return this.playerInfoMap.values();
   }

   public NetworkPlayerInfo getPlayerInfo(UUID uUID) {
      return this.playerInfoMap.get(uUID);
   }

   public NetworkPlayerInfo getPlayerInfo(String string) {
      for (NetworkPlayerInfo var6 : this.playerInfoMap.values()) {
         if (var6.getGameProfile().getName().equals(string)) {
            return var6;
         }
      }

      return null;
   }

   public GameProfile getGameProfile() {
      return this.profile;
   }
}
