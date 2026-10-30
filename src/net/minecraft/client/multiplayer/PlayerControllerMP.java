package net.minecraft.client.multiplayer;

import com.moon.Moon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import org.lwjgl.opengl.Display;

public class PlayerControllerMP {
   private final Minecraft mc;
   private final NetHandlerPlayClient netClientHandler;
   private BlockPos currentBlock = new BlockPos(-1, -1, -1);
   private ItemStack currentItemHittingBlock;
   public float curBlockDamageMP;
   private float stepSoundTickCounter;
   public int blockHitDelay;
   private boolean isHittingBlock;
   private WorldSettings.GameType currentGameType = WorldSettings.GameType.SURVIVAL;
   private int currentPlayerItem;

   public PlayerControllerMP(Minecraft minecraft, NetHandlerPlayClient netHandlerPlayClient) {
      this.mc = minecraft;
      this.netClientHandler = netHandlerPlayClient;
   }

   public static void clickBlockCreative(Minecraft minecraft, PlayerControllerMP playerControllerMP, BlockPos blockPos, EnumFacing enumFacing) {
      if (!minecraft.theWorld.extinguishFire(minecraft.thePlayer, blockPos, enumFacing)) {
         playerControllerMP.onPlayerDestroyBlock(blockPos, enumFacing);
      }
   }

   public void setPlayerCapabilities(EntityPlayer entityPlayer) {
      this.currentGameType.configurePlayerCapabilities(entityPlayer.capabilities);
   }

   public boolean isSpectator() {
      return this.currentGameType == WorldSettings.GameType.SPECTATOR;
   }

   public void setGameType(WorldSettings.GameType gameType) {
      this.currentGameType = gameType;
      this.currentGameType.configurePlayerCapabilities(this.mc.thePlayer.capabilities);
   }

   public void flipPlayer(EntityPlayer entityPlayer) {
      entityPlayer.rotationYaw = -180.0F;
   }

   public boolean shouldDrawHUD() {
      return this.currentGameType.isSurvivalOrAdventure();
   }

   public boolean onPlayerDestroyBlock(BlockPos blockPos, EnumFacing enumFacing) {
      if (this.currentGameType.isAdventure()) {
         if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return false;
         }

         if (!this.mc.thePlayer.isAllowEdit()) {
            Block var7 = this.mc.theWorld.getBlockState(blockPos).getBlock();
            ItemStack var8 = this.mc.thePlayer.getCurrentEquippedItem();
            if (var8 == null) {
               return false;
            }

            if (!var8.canDestroy(var7)) {
               return false;
            }
         }
      }

      if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
         return false;
      }

      WorldClient var12 = this.mc.theWorld;
      IBlockState var13 = var12.getBlockState(blockPos);
      Block var9 = var13.getBlock();
      if (var9.getMaterial() == Material.air) {
         return false;
      }

      var12.playAuxSFX(2001, blockPos, Block.getStateId(var13));
      boolean var10 = var12.setBlockToAir(blockPos);
      if (var10) {
         var9.onBlockDestroyedByPlayer(var12, blockPos, var13);
      }

      this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
      if (!this.currentGameType.isCreative()) {
         ItemStack var11 = this.mc.thePlayer.getCurrentEquippedItem();
         if (var11 != null) {
            var11.onBlockDestroyed(var12, var9, blockPos, this.mc.thePlayer);
            if (var11.stackSize == 0) {
               this.mc.thePlayer.destroyCurrentEquippedItem();
            }
         }
      }

      return var10;
   }

   public boolean clickBlock(BlockPos blockPos, EnumFacing enumFacing) {
      if (this.currentGameType.isAdventure()) {
         if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return false;
         }

         if (!this.mc.thePlayer.isAllowEdit()) {
            Block var7 = this.mc.theWorld.getBlockState(blockPos).getBlock();
            ItemStack var8 = this.mc.thePlayer.getCurrentEquippedItem();
            if (var8 == null) {
               return false;
            }

            if (!var8.canDestroy(var7)) {
               return false;
            }
         }
      }

      if (!this.mc.theWorld.getWorldBorder().contains(blockPos)) {
         return false;
      }

      if (this.currentGameType.isCreative()) {
         this.netClientHandler.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, enumFacing));
         clickBlockCreative(this.mc, this, blockPos, enumFacing);
         this.blockHitDelay = 5;
      } else if (!this.isHittingBlock || !this.isHittingPosition(blockPos)) {
         if (this.isHittingBlock) {
            this.netClientHandler.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, enumFacing));
         }

         this.netClientHandler.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, enumFacing));
         Block var9 = this.mc.theWorld.getBlockState(blockPos).getBlock();
         boolean var10 = var9.getMaterial() != Material.air;
         if (var10 && this.curBlockDamageMP == 0.0F) {
            var9.onBlockClicked(this.mc.theWorld, blockPos, this.mc.thePlayer);
         }

         if (var10 && var9.Method434(this.mc.thePlayer, this.mc.thePlayer.worldObj, blockPos) >= 1.0F) {
            this.onPlayerDestroyBlock(blockPos, enumFacing);
         } else {
            this.isHittingBlock = true;
            this.currentBlock = blockPos;
            this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
            this.curBlockDamageMP = 0.0F;
            this.stepSoundTickCounter = 0.0F;
            this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
         }
      }

      return true;
   }

   public void resetBlockRemoving() {
      if (this.isHittingBlock) {
         this.netClientHandler.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
         this.isHittingBlock = false;
         this.curBlockDamageMP = 0.0F;
         this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, -1);
      }
   }

   public boolean onPlayerDamageBlock(BlockPos blockPos, EnumFacing enumFacing) {
      this.syncCurrentPlayItem();
      if (this.blockHitDelay > 0) {
         this.blockHitDelay--;
         return true;
      }

      if (this.currentGameType.isCreative() && this.mc.theWorld.getWorldBorder().contains(blockPos)) {
         this.blockHitDelay = 5;
         this.netClientHandler.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, enumFacing));
         clickBlockCreative(this.mc, this, blockPos, enumFacing);
         return true;
      }

      if (this.isHittingPosition(blockPos)) {
         Block var7 = this.mc.theWorld.getBlockState(blockPos).getBlock();
         if (var7.getMaterial() == Material.air) {
            this.isHittingBlock = false;
            return false;
         }

         this.curBlockDamageMP = this.curBlockDamageMP + var7.Method434(this.mc.thePlayer, this.mc.thePlayer.worldObj, blockPos);
         if (this.stepSoundTickCounter % 4.0F == 0.0F) {
            this.mc
               .getSoundHandler()
               .playSound(
                  new PositionedSoundRecord(
                     new ResourceLocation(var7.stepSound.getStepSound()),
                     (var7.stepSound.getVolume() + 1.0F) / 8.0F,
                     var7.stepSound.getFrequency() * 0.5F,
                     blockPos.getX() + 0.5F,
                     blockPos.getY() + 0.5F,
                     blockPos.getZ() + 0.5F
                  )
               );
         }

         this.stepSoundTickCounter++;
         if (this.curBlockDamageMP >= 1.0F) {
            this.isHittingBlock = false;
            this.netClientHandler.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, enumFacing));
            this.onPlayerDestroyBlock(blockPos, enumFacing);
            this.curBlockDamageMP = 0.0F;
            this.stepSoundTickCounter = 0.0F;
            this.blockHitDelay = 5;
         }

         this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
         return true;
      } else {
         return this.clickBlock(blockPos, enumFacing);
      }
   }

   public float getBlockReachDistance() {
      return this.currentGameType.isCreative() ? 5.0F : 4.5F;
   }

   public void updateController() {
      this.syncCurrentPlayItem();
      if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
         this.netClientHandler.getNetworkManager().processReceivedPackets();
      } else {
         this.netClientHandler.getNetworkManager().checkDisconnected();
      }
   }

   private boolean isHittingPosition(BlockPos blockPos) {
      ItemStack var6 = this.mc.thePlayer.getHeldItem();
      boolean var7 = this.currentItemHittingBlock == null && var6 == null;
      if (this.currentItemHittingBlock != null && var6 != null) {
         var7 = var6.getItem() == this.currentItemHittingBlock.getItem()
            && ItemStack.areItemStackTagsEqual(var6, this.currentItemHittingBlock)
            && (var6.isItemStackDamageable() || var6.getMetadata() == this.currentItemHittingBlock.getMetadata());
      }

      return blockPos.equals(this.currentBlock) && var7;
   }

   public void syncCurrentPlayItem() {
      int var4 = this.mc.thePlayer.inventory.currentItem;
      if (var4 != this.currentPlayerItem) {
         this.currentPlayerItem = var4;
         this.netClientHandler.Method754(new C09PacketHeldItemChange(this.currentPlayerItem));
      }
   }

   public boolean onPlayerRightClick(
      EntityPlayerSP entityPlayerSP, WorldClient worldClient, ItemStack itemStack, BlockPos blockPos, EnumFacing enumFacing, Vec3 vec3
   ) {
      this.syncCurrentPlayItem();
      float var10 = (float)(vec3.xCoord - blockPos.getX());
      float var11 = (float)(vec3.yCoord - blockPos.getY());
      float var12 = (float)(vec3.zCoord - blockPos.getZ());
      var10 = MathHelper.clamp_float(var10, 0.0F, 1.0F);
      var11 = MathHelper.clamp_float(var11, 0.0F, 1.0F);
      var12 = MathHelper.clamp_float(var12, 0.0F, 1.0F);
      boolean var13 = false;
      if (!this.mc.theWorld.getWorldBorder().contains(blockPos)) {
         return false;
      }

      if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
         IBlockState var14 = worldClient.getBlockState(blockPos);
         if ((!entityPlayerSP.isSneaking() || entityPlayerSP.getHeldItem() == null)
            && var14.getBlock().onBlockActivated(worldClient, blockPos, var14, entityPlayerSP, enumFacing, var10, var11, var12)) {
            var13 = true;
         }

         if (!var13
            && itemStack != null
            && itemStack.getItem() instanceof ItemBlock var15
            && !var15.canPlaceBlockOnSide(worldClient, blockPos, enumFacing, entityPlayerSP, itemStack)) {
            return false;
         }
      }

      this.netClientHandler
         .Method754(new C08PacketPlayerBlockPlacement(blockPos, enumFacing.getIndex(), entityPlayerSP.inventory.getCurrentItem(), var10, var11, var12));
      if (var13 || this.currentGameType == WorldSettings.GameType.SPECTATOR) {
         return true;
      } else if (itemStack == null) {
         return false;
      } else if (this.currentGameType.isCreative()) {
         int var20 = itemStack.getMetadata();
         int var21 = itemStack.stackSize;
         boolean var22 = itemStack.onItemUse(entityPlayerSP, worldClient, blockPos, enumFacing, var10, var11, var12);
         itemStack.setItemDamage(var20);
         itemStack.stackSize = var21;
         return var22;
      } else {
         return itemStack.onItemUse(entityPlayerSP, worldClient, blockPos, enumFacing, var10, var11, var12);
      }
   }

   public boolean sendUseItem(EntityPlayer entityPlayer, World world, ItemStack itemStack) {
      if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
         return false;
      }

      this.syncCurrentPlayItem();
      this.netClientHandler.Method754(new C08PacketPlayerBlockPlacement(entityPlayer.inventory.getCurrentItem()));
      int var7 = itemStack.stackSize;
      ItemStack var8 = itemStack.useItemRightClick(world, entityPlayer);
      if (var8 != itemStack || var8 != null && var8.stackSize != var7) {
         entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = var8;
         if (var8.stackSize == 0) {
            entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = null;
         }

         return true;
      } else {
         return false;
      }
   }

   public EntityPlayerSP func_178892_a(World world, StatFileWriter statFileWriter) {
      return new EntityPlayerSP(this.mc, world, this.netClientHandler, statFileWriter);
   }

   public void attackEntity(EntityPlayer entityPlayer, Entity entity) {
      this.syncCurrentPlayItem();
      this.netClientHandler.Method754(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
      if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
         entityPlayer.attackTargetEntityWithCurrentItem(entity);
      }
   }

   public boolean interactWithEntitySendPacket(EntityPlayer entityPlayer, Entity entity) {
      this.syncCurrentPlayItem();
      this.netClientHandler.Method754(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.INTERACT));
      return this.currentGameType != WorldSettings.GameType.SPECTATOR && entityPlayer.interactWith(entity);
   }

   public boolean isPlayerRightClickingOnEntity(EntityPlayer entityPlayer, Entity entity, MovingObjectPosition movingObjectPosition) {
      this.syncCurrentPlayItem();
      Vec3 var7 = new Vec3(
         movingObjectPosition.hitVec.xCoord - entity.posX, movingObjectPosition.hitVec.yCoord - entity.posY, movingObjectPosition.hitVec.zCoord - entity.posZ
      );
      this.netClientHandler.Method754(new C02PacketUseEntity(entity, var7));
      return this.currentGameType != WorldSettings.GameType.SPECTATOR && entity.interactAt(entityPlayer, var7);
   }

   public ItemStack windowClick(int integer1, int integer2, int integer3, int integer4, EntityPlayer entityPlayer) {
      short var6 = entityPlayer.openContainer.getNextTransactionID(entityPlayer.inventory);
      ItemStack var7 = entityPlayer.openContainer.slotClick(integer2, integer3, integer4, entityPlayer);
      this.netClientHandler.Method754(new C0EPacketClickWindow(integer1, integer2, integer3, integer4, var7, var6));
      return var7;
   }

   public void sendEnchantPacket(int integer1, int integer2) {
      this.netClientHandler.Method754(new C11PacketEnchantItem(integer1, integer2));
   }

   public void sendSlotPacket(ItemStack itemStack, int integer) {
      if (this.currentGameType.isCreative()) {
         this.netClientHandler.Method754(new C10PacketCreativeInventoryAction(integer, itemStack));
      }
   }

   public void sendPacketDropItem(ItemStack itemStack) {
      if (this.currentGameType.isCreative() && itemStack != null) {
         this.netClientHandler.Method754(new C10PacketCreativeInventoryAction(-1, itemStack));
      }
   }

   public void onStoppedUsingItem(EntityPlayer entityPlayer) {
      this.syncCurrentPlayItem();
      this.netClientHandler.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      entityPlayer.stopUsingItem();
   }

   public boolean gameIsSurvivalOrAdventure() {
      return this.currentGameType.isSurvivalOrAdventure();
   }

   public boolean isNotCreative() {
      return !this.currentGameType.isCreative();
   }

   public boolean isInCreativeMode() {
      return this.currentGameType.isCreative();
   }

   public boolean extendedReach() {
      return this.currentGameType.isCreative();
   }

   public boolean isRidingHorse() {
      return this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof EntityHorse;
   }

   public boolean isSpectatorMode() {
      return this.currentGameType == WorldSettings.GameType.SPECTATOR;
   }

   public WorldSettings.GameType getCurrentGameType() {
      return this.currentGameType;
   }

   public boolean getIsHittingBlock() {
      return this.isHittingBlock;
   }

   public PlayerControllerMP Method586(int integer) {
      this.currentPlayerItem = integer;
      return this;
   }

   public int Method587() {
      return this.currentPlayerItem;
   }
}
