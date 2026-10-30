package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.util.concurrent.Futures;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.ast.Str;

public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable {
   private static final Logger logger = LogManager.getLogger();
   public final NetworkManager netManager;
   private final MinecraftServer serverController;
   public EntityPlayerMP playerEntity;
   private int networkTickCount;
   private int field_175090_f;
   private int floatingTickCount;
   private boolean field_147366_g;
   private int field_147378_h;
   private long lastPingTime;
   private long lastSentPingPacket;
   private int chatSpamThresholdCount;
   private int itemDropThreshold;
   private final IntHashMap<Short> field_147372_n = new IntHashMap<>();
   private double lastPosX;
   private double lastPosY;
   private double lastPosZ;
   private boolean hasMoved = true;

   public NetHandlerPlayServer(MinecraftServer minecraftServer, NetworkManager networkManager, EntityPlayerMP entityPlayerMP) {
      this.serverController = minecraftServer;
      this.netManager = networkManager;
      networkManager.setNetHandler(this);
      this.playerEntity = entityPlayerMP;
      entityPlayerMP.playerNetServerHandler = this;
   }

   @Override
   public void update() {
      this.field_147366_g = false;
      this.networkTickCount++;
      this.serverController.theProfiler.startSection("keepAlive");
      if (this.networkTickCount - this.lastSentPingPacket > 40L) {
         this.lastSentPingPacket = this.networkTickCount;
         this.lastPingTime = this.currentTimeMillis();
         this.field_147378_h = (int)this.lastPingTime;
         this.sendPacket(new S00PacketKeepAlive(this.field_147378_h));
      }

      this.serverController.theProfiler.endSection();
      if (this.chatSpamThresholdCount > 0) {
         this.chatSpamThresholdCount--;
      }

      if (this.itemDropThreshold > 0) {
         this.itemDropThreshold--;
      }

      if (this.playerEntity.getLastActiveTime() > 0L
         && this.serverController.getMaxPlayerIdleMinutes() > 0
         && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime() > this.serverController.getMaxPlayerIdleMinutes() * 1000L * 60L) {
         this.kickPlayerFromServer("You have been idle for too long!");
      }
   }

   public NetworkManager getNetworkManager() {
      return this.netManager;
   }

   public void kickPlayerFromServer(String string) {
      ChatComponentText var2 = new ChatComponentText(string);
      this.netManager.sendPacket(new S40PacketDisconnect(var2), future -> this.netManager.closeChannel(var2));
      this.netManager.disableAutoRead();
      Futures.getUnchecked(this.serverController.addScheduledTask(this.netManager::checkDisconnected));
   }

   @Override
   public void processInput(C0CPacketInput c0CPacketInput) {
      PacketThreadUtil.checkThreadAndEnqueue(c0CPacketInput, this, this.playerEntity.getServerForPlayer());
      this.playerEntity
         .setEntityActionState(c0CPacketInput.getStrafeSpeed(), c0CPacketInput.getForwardSpeed(), c0CPacketInput.isJumping(), c0CPacketInput.isSneaking());
   }

   private boolean func_183006_b(C03PacketPlayer c03PacketPlayer) {
      return !Doubles.isFinite(c03PacketPlayer.getPositionX())
         || !Doubles.isFinite(c03PacketPlayer.Method469())
         || !Doubles.isFinite(c03PacketPlayer.getPositionY())
         || !Floats.isFinite(c03PacketPlayer.Method470())
         || !Floats.isFinite(c03PacketPlayer.getYaw());
   }

   @Override
   public void processPlayer(C03PacketPlayer c03PacketPlayer) {
      PacketThreadUtil.checkThreadAndEnqueue(c03PacketPlayer, this, this.playerEntity.getServerForPlayer());
      if (this.func_183006_b(c03PacketPlayer)) {
         this.kickPlayerFromServer("Invalid move packet received");
      } else {
         WorldServer var5 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
         this.field_147366_g = true;
         if (!this.playerEntity.playerConqueredTheEnd) {
            double var6 = this.playerEntity.posX;
            double var8 = this.playerEntity.posY;
            double var10 = this.playerEntity.posZ;
            double var12 = 0.0;
            double var14 = c03PacketPlayer.getPositionX() - this.lastPosX;
            double var16 = c03PacketPlayer.Method469() - this.lastPosY;
            double var18 = c03PacketPlayer.getPositionY() - this.lastPosZ;
            if (c03PacketPlayer.Method471()) {
               var12 = var14 * var14 + var16 * var16 + var18 * var18;
               if (!this.hasMoved && var12 < 0.25) {
                  this.hasMoved = true;
               }
            }

            if (this.hasMoved) {
               this.field_175090_f = this.networkTickCount;
               if (this.playerEntity.ridingEntity != null) {
                  float var44 = this.playerEntity.rotationYaw;
                  float var21 = this.playerEntity.rotationPitch;
                  this.playerEntity.ridingEntity.updateRiderPosition();
                  double var45 = this.playerEntity.posX;
                  double var46 = this.playerEntity.posY;
                  double var47 = this.playerEntity.posZ;
                  if (c03PacketPlayer.Method472()) {
                     var44 = c03PacketPlayer.getYaw();
                     var21 = c03PacketPlayer.Method470();
                  }

                  this.playerEntity.onGround = c03PacketPlayer.isOnGround();
                  this.playerEntity.onUpdateEntity();
                  this.playerEntity.setPositionAndRotation(var45, var46, var47, var44, var21);
                  if (this.playerEntity.ridingEntity != null) {
                     this.playerEntity.ridingEntity.updateRiderPosition();
                  }

                  this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                  if (this.playerEntity.ridingEntity != null) {
                     if (var12 > 4.0) {
                        Entity var48 = this.playerEntity.ridingEntity;
                        this.playerEntity.playerNetServerHandler.sendPacket(new S18PacketEntityTeleport(var48));
                        this.setPlayerLocation(
                           this.playerEntity.posX,
                           this.playerEntity.posY,
                           this.playerEntity.posZ,
                           this.playerEntity.rotationYaw,
                           this.playerEntity.rotationPitch
                        );
                     }

                     this.playerEntity.ridingEntity.isAirBorne = true;
                  }

                  if (this.hasMoved) {
                     this.lastPosX = this.playerEntity.posX;
                     this.lastPosY = this.playerEntity.posY;
                     this.lastPosZ = this.playerEntity.posZ;
                  }

                  var5.updateEntity(this.playerEntity);
                  return;
               }

               if (this.playerEntity.isPlayerSleeping()) {
                  this.playerEntity.onUpdateEntity();
                  this.playerEntity
                     .setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                  var5.updateEntity(this.playerEntity);
                  return;
               }

               double var20 = this.playerEntity.posY;
               this.lastPosX = this.playerEntity.posX;
               this.lastPosY = this.playerEntity.posY;
               this.lastPosZ = this.playerEntity.posZ;
               double var22 = this.playerEntity.posX;
               double var24 = this.playerEntity.posY;
               double var26 = this.playerEntity.posZ;
               float var28 = this.playerEntity.rotationYaw;
               float var29 = this.playerEntity.rotationPitch;
               if (c03PacketPlayer.Method471() && c03PacketPlayer.Method469() == -999.0) {
                  c03PacketPlayer.setMoving(false);
               }

               if (c03PacketPlayer.Method471()) {
                  var22 = c03PacketPlayer.getPositionX();
                  var24 = c03PacketPlayer.Method469();
                  var26 = c03PacketPlayer.getPositionY();
                  if (Math.abs(c03PacketPlayer.getPositionX()) > 3.0E7 || Math.abs(c03PacketPlayer.getPositionY()) > 3.0E7) {
                     this.kickPlayerFromServer("Illegal position");
                     return;
                  }
               }

               if (c03PacketPlayer.Method472()) {
                  var28 = c03PacketPlayer.getYaw();
                  var29 = c03PacketPlayer.Method470();
               }

               this.playerEntity.onUpdateEntity();
               this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, var28, var29);
               if (!this.hasMoved) {
                  return;
               }

               double var30 = var22 - this.playerEntity.posX;
               double var32 = var24 - this.playerEntity.posY;
               double var34 = var26 - this.playerEntity.posZ;
               double var36 = this.playerEntity.motionX * this.playerEntity.motionX
                  + this.playerEntity.motionY * this.playerEntity.motionY
                  + this.playerEntity.motionZ * this.playerEntity.motionZ;
               double var38 = var30 * var30 + var32 * var32 + var34 * var34;
               if (var38 - var36 > 100.0
                  && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getName()))) {
                  logger.warn(
                     this.playerEntity.getName()
                        + " moved too quickly! "
                        + var30
                        + ","
                        + var32
                        + ","
                        + var34
                        + " ("
                        + var30
                        + ", "
                        + var32
                        + ", "
                        + var34
                        + ")"
                  );
                  this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                  return;
               }

               boolean var41 = var5.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(0.0625, 0.0625, 0.0625))
                  .isEmpty();
               if (this.playerEntity.onGround && !c03PacketPlayer.isOnGround() && var32 > 0.0) {
                  this.playerEntity.jump();
               }

               this.playerEntity.moveEntity(var30, var32, var34);
               this.playerEntity.onGround = c03PacketPlayer.isOnGround();
               var30 = var22 - this.playerEntity.posX;
               var32 = var24 - this.playerEntity.posY;
               if (var32 > -0.5 || var32 < 0.5) {
                  var32 = 0.0;
               }

               var34 = var26 - this.playerEntity.posZ;
               var38 = var30 * var30 + var32 * var32 + var34 * var34;
               boolean var42 = false;
               if (var38 > 0.0625 && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
                  var42 = true;
                  logger.warn(this.playerEntity.getName() + " moved wrongly!");
               }

               this.playerEntity.setPositionAndRotation(var22, var24, var26, var28, var29);
               this.playerEntity.addMovementStat(this.playerEntity.posX - var6, this.playerEntity.posY - var8, this.playerEntity.posZ - var10);
               if (!this.playerEntity.noClip) {
                  boolean var43 = var5.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(0.0625, 0.0625, 0.0625))
                     .isEmpty();
                  if (var41 && (var42 || !var43) && !this.playerEntity.isPlayerSleeping()) {
                     this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, var28, var29);
                     return;
                  }
               }

               AxisAlignedBB var53 = this.playerEntity.getEntityBoundingBox().expand(0.0625, 0.0625, 0.0625).addCoord(0.0, -0.55, 0.0);
               if (this.serverController.isFlightAllowed() || this.playerEntity.capabilities.allowFlying || var5.checkBlockCollision(var53)) {
                  this.floatingTickCount = 0;
               } else if (var32 >= -0.03125) {
                  this.floatingTickCount++;
                  if (this.floatingTickCount > 80) {
                     logger.warn(this.playerEntity.getName() + " was kicked for floating too long!");
                     this.kickPlayerFromServer("Flying is not enabled on this server");
                     return;
                  }
               }

               this.playerEntity.onGround = c03PacketPlayer.isOnGround();
               this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
               this.playerEntity.handleFalling(this.playerEntity.posY - var20, c03PacketPlayer.isOnGround());
            } else if (this.networkTickCount - this.field_175090_f > 20) {
               this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
            }
         }
      }
   }

   public void setPlayerLocation(double double1, double double2, double double3, float float4, float float5) {
      this.setPlayerLocation(double1, double2, double3, float4, float5, Collections.emptySet());
   }

   public void setPlayerLocation(double double1, double double2, double double3, float float4, float float5, Set<S08PacketPlayerPosLook.EnumFlags> set) {
      this.hasMoved = false;
      this.lastPosX = double1;
      this.lastPosY = double2;
      this.lastPosZ = double3;
      if (set.contains(S08PacketPlayerPosLook.EnumFlags.X)) {
         this.lastPosX = this.lastPosX + this.playerEntity.posX;
      }

      if (set.contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
         this.lastPosY = this.lastPosY + this.playerEntity.posY;
      }

      if (set.contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
         this.lastPosZ = this.lastPosZ + this.playerEntity.posZ;
      }

      float var13 = float4;
      float var14 = float5;
      if (set.contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
         var13 = float4 + this.playerEntity.rotationYaw;
      }

      if (set.contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
         var14 = float5 + this.playerEntity.rotationPitch;
      }

      this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, var13, var14);
      this.playerEntity.playerNetServerHandler.sendPacket(new S08PacketPlayerPosLook(double1, double2, double3, float4, float5, set));
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void processPlayerDigging(C07PacketPlayerDigging c07PacketPlayerDigging) {
      PacketThreadUtil.checkThreadAndEnqueue(c07PacketPlayerDigging, this, this.playerEntity.getServerForPlayer());
      WorldServer var5 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      BlockPos var6 = c07PacketPlayerDigging.getPosition();
      this.playerEntity.markPlayerActive();
      switch (c07PacketPlayerDigging.getStatus()) {
         case DROP_ITEM:
            if (!this.playerEntity.isSpectator()) {
               this.playerEntity.dropOneItem(false);
            }

            return;
         case DROP_ALL_ITEMS:
            if (!this.playerEntity.isSpectator()) {
               this.playerEntity.dropOneItem(true);
            }

            return;
         case RELEASE_USE_ITEM:
            this.playerEntity.stopUsingItem();
            return;
         case START_DESTROY_BLOCK:
         case ABORT_DESTROY_BLOCK:
         case STOP_DESTROY_BLOCK:
            double var7 = this.playerEntity.posX - (var6.getX() + 0.5);
            double var9 = this.playerEntity.posY - (var6.getY() + 0.5) + 1.5;
            double var11 = this.playerEntity.posZ - (var6.getZ() + 0.5);
            double var13 = var7 * var7 + var9 * var9 + var11 * var11;
            if (var13 > 36.0) {
               return;
            } else if (var6.getY() >= this.serverController.getBuildLimit()) {
               return;
            } else {
               if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                  if (!this.serverController.isBlockProtected(var5, var6, this.playerEntity) && var5.getWorldBorder().contains(var6)) {
                     this.playerEntity.theItemInWorldManager.onBlockClicked(var6, c07PacketPlayerDigging.getFacing());
                  } else {
                     this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var5, var6));
                  }
               } else {
                  if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                     this.playerEntity.theItemInWorldManager.blockRemoving(var6);
                  } else if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
                     this.playerEntity.theItemInWorldManager.cancelDestroyingBlock();
                  }

                  if (var5.getBlockState(var6).getBlock().getMaterial() != Material.air) {
                     this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var5, var6));
                  }
               }

               return;
            }
         default:
            throw new IllegalArgumentException("Invalid player action");
      }
   }

   @Override
   public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement) {
      PacketThreadUtil.checkThreadAndEnqueue(c08PacketPlayerBlockPlacement, this, this.playerEntity.getServerForPlayer());
      WorldServer var5 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      ItemStack var6 = this.playerEntity.inventory.getCurrentItem();
      boolean var7 = false;
      BlockPos var8 = c08PacketPlayerBlockPlacement.getPosition();
      EnumFacing var9 = EnumFacing.getFront(c08PacketPlayerBlockPlacement.getPlacedBlockDirection());
      this.playerEntity.markPlayerActive();
      if (c08PacketPlayerBlockPlacement.getPlacedBlockDirection() == 255) {
         if (var6 == null) {
            return;
         }

         this.playerEntity.theItemInWorldManager.tryUseItem(this.playerEntity, var5, var6);
      } else if (var8.getY() < this.serverController.getBuildLimit() - 1 || var9 != EnumFacing.UP && var8.getY() < this.serverController.getBuildLimit()) {
         if (this.hasMoved
            && this.playerEntity.getDistanceSq(var8.getX() + 0.5, var8.getY() + 0.5, var8.getZ() + 0.5) < 64.0
            && !this.serverController.isBlockProtected(var5, var8, this.playerEntity)
            && var5.getWorldBorder().contains(var8)) {
            this.playerEntity
               .theItemInWorldManager
               .activateBlockOrUseItem(
                  this.playerEntity,
                  var5,
                  var6,
                  var8,
                  var9,
                  c08PacketPlayerBlockPlacement.Method302(),
                  c08PacketPlayerBlockPlacement.Method303(),
                  c08PacketPlayerBlockPlacement.Method304()
               );
         }

         var7 = true;
      } else {
         ChatComponentTranslation var10 = new ChatComponentTranslation("build.tooHigh", this.serverController.getBuildLimit());
         var10.getChatStyle().setColor(EnumChatFormatting.RED);
         this.playerEntity.playerNetServerHandler.sendPacket(new S02PacketChat(var10));
         var7 = true;
      }

      if (var7) {
         this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var5, var8));
         this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var5, var8.offset(var9)));
      }

      var6 = this.playerEntity.inventory.getCurrentItem();
      if (var6 != null && var6.stackSize == 0) {
         this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
         var6 = null;
      }

      if (var6 == null || var6.getMaxItemUseDuration() == 0) {
         this.playerEntity.isChangingQuantityOnly = true;
         this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]
         );
         Slot var12 = this.playerEntity.openContainer.getSlotFromInventory(this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
         this.playerEntity.openContainer.detectAndSendChanges();
         this.playerEntity.isChangingQuantityOnly = false;
         if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), c08PacketPlayerBlockPlacement.getStack())) {
            this.sendPacket(new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, var12.slotNumber, this.playerEntity.inventory.getCurrentItem()));
         }
      }
   }

   @Override
   public void handleSpectate(C18PacketSpectate c18PacketSpectate) {
      PacketThreadUtil.checkThreadAndEnqueue(c18PacketSpectate, this, this.playerEntity.getServerForPlayer());
      if (this.playerEntity.isSpectator()) {
         Entity var5 = null;

         for (WorldServer var9 : this.serverController.worldServers) {
            if (var9 != null) {
               var5 = c18PacketSpectate.getEntity(var9);
               if (var5 != null) {
                  break;
               }
            }
         }

         if (var5 != null) {
            this.playerEntity.setSpectatingEntity(this.playerEntity);
            this.playerEntity.mountEntity(null);
            if (var5.worldObj != this.playerEntity.worldObj) {
               WorldServer var10 = this.playerEntity.getServerForPlayer();
               WorldServer var11 = (WorldServer)var5.worldObj;
               this.playerEntity.dimension = var5.dimension;
               this.sendPacket(
                  new S07PacketRespawn(
                     this.playerEntity.dimension,
                     var10.getDifficulty(),
                     var10.getWorldInfo().getTerrainType(),
                     this.playerEntity.theItemInWorldManager.getGameType()
                  )
               );
               var10.removePlayerEntityDangerously(this.playerEntity);
               this.playerEntity.isDead = false;
               this.playerEntity.setLocationAndAngles(var5.posX, var5.posY, var5.posZ, var5.rotationYaw, var5.rotationPitch);
               if (this.playerEntity.isEntityAlive()) {
                  var10.updateEntityWithOptionalForce(this.playerEntity, false);
                  var11.spawnEntityInWorld(this.playerEntity);
                  var11.updateEntityWithOptionalForce(this.playerEntity, false);
               }

               this.playerEntity.setWorld(var11);
               this.serverController.getConfigurationManager().preparePlayer(this.playerEntity, var10);
               this.playerEntity.setPositionAndUpdate(var5.posX, var5.posY, var5.posZ);
               this.playerEntity.theItemInWorldManager.setWorld(var11);
               this.serverController.getConfigurationManager().updateTimeAndWeatherForPlayer(this.playerEntity, var11);
               this.serverController.getConfigurationManager().syncPlayerInventory(this.playerEntity);
            } else {
               this.playerEntity.setPositionAndUpdate(var5.posX, var5.posY, var5.posZ);
            }
         }
      }
   }

   @Override
   public void handleResourcePackStatus(C19PacketResourcePackStatus c19PacketResourcePackStatus) {
   }

   @Override
   public void onDisconnect(IChatComponent iChatComponent) {
      logger.info(this.playerEntity.getName() + " lost connection: " + iChatComponent);
      this.serverController.refreshStatusNextTick();
      ChatComponentTranslation var5 = new ChatComponentTranslation("multiplayer.player.left", this.playerEntity.getDisplayName());
      var5.getChatStyle().setColor(EnumChatFormatting.YELLOW);
      this.serverController.getConfigurationManager().sendChatMsg(var5);
      this.playerEntity.mountEntityAndWakeUp();
      this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
      if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
         logger.info("Stopping singleplayer server as player logged out");
         this.serverController.initiateShutdown();
      }
   }

   public void sendPacket(Packet packet) {
      if (packet instanceof S02PacketChat var5) {
         EntityPlayer.EnumChatVisibility var6 = this.playerEntity.getChatVisibility();
         if (var6 == EntityPlayer.EnumChatVisibility.HIDDEN) {
            return;
         }

         if (var6 == EntityPlayer.EnumChatVisibility.SYSTEM && !var5.isChat()) {
            return;
         }
      }

      try {
         this.netManager.sendPacket(packet);
      } catch (Throwable var7) {
         CrashReport var8 = CrashReport.makeCrashReport(var7, "Sending packet");
         var8.makeCategory("Packet being sent").addCrashSectionCallable("Packet class", () -> packet.getClass().getCanonicalName());
         throw new ReportedException(var8);
      }
   }

   @Override
   public void processHeldItemChange(C09PacketHeldItemChange c09PacketHeldItemChange) {
      PacketThreadUtil.checkThreadAndEnqueue(c09PacketHeldItemChange, this, this.playerEntity.getServerForPlayer());
      if (c09PacketHeldItemChange.getSlotId() >= 0 && c09PacketHeldItemChange.getSlotId() < InventoryPlayer.getHotbarSize()) {
         this.playerEntity.inventory.currentItem = c09PacketHeldItemChange.getSlotId();
         this.playerEntity.markPlayerActive();
      } else {
         logger.warn(this.playerEntity.getName() + " tried to set an invalid carried item");
      }
   }

   @Override
   public void processChatMessage(C01PacketChatMessage c01PacketChatMessage) {
      PacketThreadUtil.checkThreadAndEnqueue(c01PacketChatMessage, this, this.playerEntity.getServerForPlayer());
      if (this.playerEntity.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN) {
         ChatComponentTranslation var5 = new ChatComponentTranslation("chat.cannotSend");
         var5.getChatStyle().setColor(EnumChatFormatting.RED);
         this.sendPacket(new S02PacketChat(var5));
      } else {
         this.playerEntity.markPlayerActive();
         String var7 = StringUtils.normalizeSpace(c01PacketChatMessage.getMessage());

         for (int var6 = 0; var6 < var7.length(); var6++) {
            if (!ChatAllowedCharacters.isAllowedCharacter(var7.charAt(var6))) {
               this.kickPlayerFromServer("Illegal characters in chat");
               return;
            }
         }

         if (var7.startsWith("/")) {
            this.handleSlashCommand(var7);
         } else {
            ChatComponentTranslation var8 = new ChatComponentTranslation("chat.type.text", this.playerEntity.getDisplayName(), var7);
            this.serverController.getConfigurationManager().sendChatMsgImpl(var8, false);
         }

         this.chatSpamThresholdCount += 20;
         if (this.chatSpamThresholdCount > 200 && !this.serverController.getConfigurationManager().canSendCommands(this.playerEntity.getGameProfile())) {
            this.kickPlayerFromServer("disconnect.spam");
         }
      }
   }

   private void handleSlashCommand(String string) {
      this.serverController.getCommandManager().executeCommand(this.playerEntity, string);
   }

   @Override
   public void handleAnimation(C0APacketAnimation c0APacketAnimation) {
      PacketThreadUtil.checkThreadAndEnqueue(c0APacketAnimation, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      this.playerEntity.swingItem();
   }

   @Override
   public void processEntityAction(C0BPacketEntityAction packetIn)
   {
      PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();

      switch (packetIn.getAction())
      {
         case START_SNEAKING:
            this.playerEntity.setSneaking(true);
            break;

         case STOP_SNEAKING:
            this.playerEntity.setSneaking(false);
            break;

         case START_SPRINTING:
            this.playerEntity.setSprinting(true);
            break;

         case STOP_SPRINTING:
            this.playerEntity.setSprinting(false);
            break;

         case STOP_SLEEPING:
            this.playerEntity.wakeUpPlayer(false, true, true);
            this.hasMoved = false;
            break;

         case RIDING_JUMP:
            if (this.playerEntity.ridingEntity instanceof EntityHorse)
            {
               ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(packetIn.getAuxData());
            }

            break;

         case OPEN_INVENTORY:
            if (this.playerEntity.ridingEntity instanceof EntityHorse)
            {
               ((EntityHorse)this.playerEntity.ridingEntity).openGUI(this.playerEntity);
            }

            break;

         default:
            throw new IllegalArgumentException("Invalid client command!");
      }
   }

   @Override
   public void processUseEntity(C02PacketUseEntity c02PacketUseEntity) {
      PacketThreadUtil.checkThreadAndEnqueue(c02PacketUseEntity, this, this.playerEntity.getServerForPlayer());
      WorldServer var5 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      Entity var6 = c02PacketUseEntity.getEntityFromWorld(var5);
      this.playerEntity.markPlayerActive();
      if (var6 != null) {
         boolean var7 = this.playerEntity.canEntityBeSeen(var6);
         double var8 = 36.0;
         if (!var7) {
            var8 = 9.0;
         }

         if (this.playerEntity.getDistanceSqToEntity(var6) < var8) {
            if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.INTERACT) {
               this.playerEntity.interactWith(var6);
            } else if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.INTERACT_AT) {
               var6.interactAt(this.playerEntity, c02PacketUseEntity.getHitVec());
            } else if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.ATTACK) {
               if (var6 instanceof EntityItem || var6 instanceof EntityXPOrb || var6 instanceof EntityArrow || var6 == this.playerEntity) {
                  this.kickPlayerFromServer("Attempting to attack an invalid entity");
                  this.serverController.logWarning("Player " + this.playerEntity.getName() + " tried to attack an invalid entity");
                  return;
               }

               this.playerEntity.attackTargetEntityWithCurrentItem(var6);
            }
         }
      }
   }

   @Override
   public void processClientStatus(C16PacketClientStatus packetIn)
   {
      PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      C16PacketClientStatus.EnumState c16packetclientstatus$enumstate = packetIn.getStatus();

      switch (c16packetclientstatus$enumstate)
      {
         case PERFORM_RESPAWN:
            if (this.playerEntity.playerConqueredTheEnd)
            {
               this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, true);
            }
            else if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled())
            {
               if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner()))
               {
                  this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it\'s game over!");
                  this.serverController.deleteWorldAndStopServer();
               }
               else
               {
                  UserListBansEntry userlistbansentry = new UserListBansEntry(this.playerEntity.getGameProfile(), null, "(You just lost the game)", null, "Death in Hardcore");
                  this.serverController.getConfigurationManager().getBannedPlayers().addEntry(userlistbansentry);
                  this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it\'s game over!");
               }
            }
            else
            {
               if (this.playerEntity.getHealth() > 0.0F)
               {
                  return;
               }

               this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, false);
            }

            break;

         case REQUEST_STATS:
            this.playerEntity.getStatFile().func_150876_a(this.playerEntity);
            break;

         case OPEN_INVENTORY_ACHIEVEMENT:
            this.playerEntity.triggerAchievement(AchievementList.openInventory);
      }
   }

   @Override
   public void processCloseWindow(C0DPacketCloseWindow c0DPacketCloseWindow) {
      PacketThreadUtil.checkThreadAndEnqueue(c0DPacketCloseWindow, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.closeContainer();
   }

   @Override
   public void processClickWindow(C0EPacketClickWindow c0EPacketClickWindow) {
      PacketThreadUtil.checkThreadAndEnqueue(c0EPacketClickWindow, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      if (this.playerEntity.openContainer.windowId == c0EPacketClickWindow.getWindowId() && this.playerEntity.openContainer.getCanCraft(this.playerEntity)) {
         if (this.playerEntity.isSpectator()) {
            ArrayList var5 = Lists.newArrayList();

            for (int var6 = 0; var6 < this.playerEntity.openContainer.inventorySlots.size(); var6++) {
               var5.add(this.playerEntity.openContainer.inventorySlots.get(var6).getStack());
            }

            this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, var5);
         } else {
            ItemStack var8 = this.playerEntity
               .openContainer
               .slotClick(c0EPacketClickWindow.getSlotId(), c0EPacketClickWindow.getUsedButton(), c0EPacketClickWindow.getMode(), this.playerEntity);
            if (ItemStack.areItemStacksEqual(c0EPacketClickWindow.getClickedItem(), var8)) {
               this.playerEntity
                  .playerNetServerHandler
                  .sendPacket(new S32PacketConfirmTransaction(c0EPacketClickWindow.getWindowId(), c0EPacketClickWindow.getActionNumber(), true));
               this.playerEntity.isChangingQuantityOnly = true;
               this.playerEntity.openContainer.detectAndSendChanges();
               this.playerEntity.updateHeldItem();
               this.playerEntity.isChangingQuantityOnly = false;
            } else {
               this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, c0EPacketClickWindow.getActionNumber());
               this.playerEntity
                  .playerNetServerHandler
                  .sendPacket(new S32PacketConfirmTransaction(c0EPacketClickWindow.getWindowId(), c0EPacketClickWindow.getActionNumber(), false));
               this.playerEntity.openContainer.setCanCraft(this.playerEntity, false);
               ArrayList var9 = Lists.newArrayList();

               for (int var7 = 0; var7 < this.playerEntity.openContainer.inventorySlots.size(); var7++) {
                  var9.add(this.playerEntity.openContainer.inventorySlots.get(var7).getStack());
               }

               this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, var9);
            }
         }
      }
   }

   @Override
   public void processEnchantItem(C11PacketEnchantItem c11PacketEnchantItem) {
      PacketThreadUtil.checkThreadAndEnqueue(c11PacketEnchantItem, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      if (this.playerEntity.openContainer.windowId == c11PacketEnchantItem.getWindowId()
         && this.playerEntity.openContainer.getCanCraft(this.playerEntity)
         && !this.playerEntity.isSpectator()) {
         this.playerEntity.openContainer.enchantItem(this.playerEntity, c11PacketEnchantItem.getButton());
         this.playerEntity.openContainer.detectAndSendChanges();
      }
   }

   @Override
   public void processCreativeInventoryAction(C10PacketCreativeInventoryAction c10PacketCreativeInventoryAction) {
      PacketThreadUtil.checkThreadAndEnqueue(c10PacketCreativeInventoryAction, this, this.playerEntity.getServerForPlayer());
      if (this.playerEntity.theItemInWorldManager.isCreative()) {
         boolean var5 = c10PacketCreativeInventoryAction.getSlotId() < 0;
         ItemStack var6 = c10PacketCreativeInventoryAction.getStack();
         if (var6 != null && var6.hasTagCompound() && var6.getTagCompound().hasKey("BlockEntityTag", 10)) {
            NBTTagCompound var7 = var6.getTagCompound().getCompoundTag("BlockEntityTag");
            if (var7.hasKey("x") && var7.hasKey("y") && var7.hasKey("z")) {
               BlockPos var8 = new BlockPos(var7.getInteger("x"), var7.getInteger("y"), var7.getInteger("z"));
               TileEntity var9 = this.playerEntity.worldObj.getTileEntity(var8);
               if (var9 != null) {
                  NBTTagCompound var10 = new NBTTagCompound();
                  var9.writeToNBT(var10);
                  var10.removeTag("x");
                  var10.removeTag("y");
                  var10.removeTag("z");
                  var6.setTagInfo("BlockEntityTag", var10);
               }
            }
         }

         boolean var11 = c10PacketCreativeInventoryAction.getSlotId() >= 1
            && c10PacketCreativeInventoryAction.getSlotId() < 36 + InventoryPlayer.getHotbarSize();
         boolean var12 = var6 == null || var6.getItem() != null;
         boolean var13 = var6 == null || var6.getMetadata() >= 0 && var6.stackSize <= 64 && var6.stackSize > 0;
         if (var11 && var12 && var13) {
            this.playerEntity.inventoryContainer.putStackInSlot(c10PacketCreativeInventoryAction.getSlotId(), var6);
            this.playerEntity.inventoryContainer.setCanCraft(this.playerEntity, true);
         } else if (var5 && var12 && var13 && this.itemDropThreshold < 200) {
            this.itemDropThreshold += 20;
            EntityItem var14 = this.playerEntity.dropPlayerItemWithRandomChoice(var6, true);
            if (var14 != null) {
               var14.setAgeToCreativeDespawnTime();
            }
         }
      }
   }

   @Override
   public void processConfirmTransaction(C0FPacketConfirmTransaction c0FPacketConfirmTransaction) {
      PacketThreadUtil.checkThreadAndEnqueue(c0FPacketConfirmTransaction, this, this.playerEntity.getServerForPlayer());
      Short var5 = this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
      if (var5 != null
         && c0FPacketConfirmTransaction.getUid() == var5
         && this.playerEntity.openContainer.windowId == c0FPacketConfirmTransaction.getWindowId()
         && !this.playerEntity.openContainer.getCanCraft(this.playerEntity)
         && !this.playerEntity.isSpectator()) {
         this.playerEntity.openContainer.setCanCraft(this.playerEntity, true);
      }
   }

   @Override
   public void processUpdateSign(C12PacketUpdateSign c12PacketUpdateSign) {
      PacketThreadUtil.checkThreadAndEnqueue(c12PacketUpdateSign, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      WorldServer var5 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      BlockPos var6 = c12PacketUpdateSign.getPosition();
      if (var5.Method322(var6)) {
         if (!(var5.getTileEntity(var6) instanceof TileEntitySign var8)) {
            return;
         }

         if (!var8.getIsEditable() || var8.getPlayer() != this.playerEntity) {
            this.serverController.logWarning("Player " + this.playerEntity.getName() + " just tried to change non-editable sign");
            return;
         }

         IChatComponent[] var9 = c12PacketUpdateSign.getLines();

         for (int var10 = 0; var10 < var9.length; var10++) {
            var8.signText[var10] = new ChatComponentText(EnumChatFormatting.getTextWithoutFormattingCodes(var9[var10].getUnformattedText()));
         }

         var8.markDirty();
         var5.markBlockForUpdate(var6);
      }
   }

   @Override
   public void processKeepAlive(C00PacketKeepAlive c00PacketKeepAlive) {
      if (c00PacketKeepAlive.Method657() == this.field_147378_h) {
         int var5 = (int)(this.currentTimeMillis() - this.lastPingTime);
         this.playerEntity.ping = (this.playerEntity.ping * 3 + var5) / 4;
      }
   }

   private long currentTimeMillis() {
      return System.nanoTime() / 1000000L;
   }

   @Override
   public void processPlayerAbilities(C13PacketPlayerAbilities c13PacketPlayerAbilities) {
      PacketThreadUtil.checkThreadAndEnqueue(c13PacketPlayerAbilities, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.capabilities.isFlying = c13PacketPlayerAbilities.isFlying() && this.playerEntity.capabilities.allowFlying;
   }

   @Override
   public void processTabComplete(C14PacketTabComplete c14PacketTabComplete) {
      PacketThreadUtil.checkThreadAndEnqueue(c14PacketTabComplete, this, this.playerEntity.getServerForPlayer());
      ArrayList<String> var2 = Lists.newArrayList();
      var2.addAll(this.serverController.getTabCompletions(this.playerEntity, c14PacketTabComplete.getMessage(), c14PacketTabComplete.getTargetBlock()));
      this.playerEntity.playerNetServerHandler.sendPacket(new S3APacketTabComplete(var2.toArray(new String[0])));
   }

   @Override
   public void processClientSettings(C15PacketClientSettings c15PacketClientSettings) {
      PacketThreadUtil.checkThreadAndEnqueue(c15PacketClientSettings, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.handleClientSettings(c15PacketClientSettings);
   }

   @Override
   public void processVanilla250Packet(C17PacketCustomPayload c17PacketCustomPayload) {
      PacketThreadUtil.checkThreadAndEnqueue(c17PacketCustomPayload, this, this.playerEntity.getServerForPlayer());
      if ("MC|BEdit".equals(c17PacketCustomPayload.getChannelName())) {
         PacketBuffer var49 = new PacketBuffer(Unpooled.wrappedBuffer(c17PacketCustomPayload.getBufferData()));

         try {
            ItemStack var53 = var49.readItemStackFromBuffer();
            if (var53 == null) {
               return;
            }

            if (!ItemWritableBook.isNBTValid(var53.getTagCompound())) {
               throw new IOException("Invalid book tag!");
            }

            ItemStack var56 = this.playerEntity.inventory.getCurrentItem();
            if (var56 != null) {
               if (var53.getItem() == Items.writable_book && var53.getItem() == var56.getItem()) {
                  var56.setTagInfo("pages", var53.getTagCompound().getTagList("pages", 8));
               }

               return;
            }
         } catch (Exception var40) {
            logger.error("Couldn't handle book info", var40);
            return;
         } finally {
            var49.release();
         }

         return;
      } else if ("MC|BSign".equals(c17PacketCustomPayload.getChannelName())) {
         PacketBuffer var48 = new PacketBuffer(Unpooled.wrappedBuffer(c17PacketCustomPayload.getBufferData()));

         try {
            ItemStack var52 = var48.readItemStackFromBuffer();
            if (var52 == null) {
               return;
            }

            if (!ItemEditableBook.validBookTagContents(var52.getTagCompound())) {
               throw new IOException("Invalid book tag!");
            }

            ItemStack var55 = this.playerEntity.inventory.getCurrentItem();
            if (var55 != null) {
               if (var52.getItem() == Items.written_book && var55.getItem() == Items.writable_book) {
                  var55.setTagInfo("author", new NBTTagString(this.playerEntity.getName()));
                  var55.setTagInfo("title", new NBTTagString(var52.getTagCompound().getString("title")));
                  var55.setTagInfo("pages", var52.getTagCompound().getTagList("pages", 8));
                  var55.setItem(Items.written_book);
               }

               return;
            }
         } catch (Exception var42) {
            logger.error("Couldn't sign book", var42);
            return;
         } finally {
            var48.release();
         }

         return;
      } else if ("MC|TrSel".equals(c17PacketCustomPayload.getChannelName())) {
         try {
            int var6 = c17PacketCustomPayload.getBufferData().readInt();
            Container var7 = this.playerEntity.openContainer;
            if (var7 instanceof ContainerMerchant) {
               ((ContainerMerchant)var7).setCurrentRecipeIndex(var6);
            }
         } catch (Exception var39) {
            logger.error("Couldn't select trade", var39);
         }
      } else if ("MC|AdvCdm".equals(c17PacketCustomPayload.getChannelName())) {
         if (!this.serverController.isCommandBlockEnabled()) {
            this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notEnabled"));
         } else if (this.playerEntity.canCommandSenderUseCommand(2, "") && this.playerEntity.capabilities.isCreativeMode) {
            PacketBuffer var44 = c17PacketCustomPayload.getBufferData();

            try {
               byte var50 = var44.readByte();
               CommandBlockLogic var8 = null;
               if (var50 == 0) {
                  TileEntity var9 = this.playerEntity.worldObj.getTileEntity(new BlockPos(var44.readInt(), var44.readInt(), var44.readInt()));
                  if (var9 instanceof TileEntityCommandBlock) {
                     var8 = ((TileEntityCommandBlock)var9).getCommandBlockLogic();
                  }
               } else if (var50 == 1) {
                  Entity var57 = this.playerEntity.worldObj.getEntityByID(var44.readInt());
                  if (var57 instanceof EntityMinecartCommandBlock) {
                     var8 = ((EntityMinecartCommandBlock)var57).getCommandBlockLogic();
                  }
               }

               String var58 = var44.readStringFromBuffer(var44.readableBytes());
               boolean var10 = var44.readBoolean();
               if (var8 != null) {
                  var8.setCommand(var58);
                  var8.setTrackOutput(var10);
                  if (!var10) {
                     var8.setLastOutput(null);
                  }

                  var8.updateCommand();
                  this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.setCommand.success", var58));
               }
            } catch (Exception var37) {
               logger.error("Couldn't set command block", var37);
            } finally {
               var44.release();
            }
         } else {
            this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notAllowed"));
         }
      } else if ("MC|Beacon".equals(c17PacketCustomPayload.getChannelName())) {
         if (this.playerEntity.openContainer instanceof ContainerBeacon) {
            try {
               PacketBuffer var45 = c17PacketCustomPayload.getBufferData();
               int var51 = var45.readInt();
               int var54 = var45.readInt();
               ContainerBeacon var59 = (ContainerBeacon)this.playerEntity.openContainer;
               Slot var60 = var59.getSlot(0);
               if (var60.getHasStack()) {
                  var60.decrStackSize(1);
                  IInventory var11 = var59.func_180611_e();
                  var11.setField(1, var51);
                  var11.setField(2, var54);
                  var11.markDirty();
               }
            } catch (Exception var36) {
               logger.error("Couldn't set beacon", var36);
            }
         }
      } else if ("MC|ItemName".equals(c17PacketCustomPayload.getChannelName()) && this.playerEntity.openContainer instanceof ContainerRepair var5) {
         if (c17PacketCustomPayload.getBufferData() != null && c17PacketCustomPayload.getBufferData().readableBytes() >= 1) {
            String var47 = ChatAllowedCharacters.filterAllowedCharacters(c17PacketCustomPayload.getBufferData().readStringFromBuffer(32767));
            if (var47.length() <= 30) {
               var5.updateItemName(var47);
            }
         } else {
            var5.updateItemName("");
         }
      }
   }
}
