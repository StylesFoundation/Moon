package net.minecraft.server.management;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;

public class ItemInWorldManager {
   public World theWorld;
   public EntityPlayerMP thisPlayerMP;
   private WorldSettings.GameType gameType = WorldSettings.GameType.NOT_SET;
   private boolean isDestroyingBlock;
   private int initialDamage;
   private BlockPos field_180240_f = BlockPos.ORIGIN;
   private int curblockDamage;
   private boolean receivedFinishDiggingPacket;
   private BlockPos field_180241_i = BlockPos.ORIGIN;
   private int initialBlockDamage;
   private int durabilityRemainingOnBlock = -1;

   public ItemInWorldManager(World world) {
      this.theWorld = world;
   }

   public void setGameType(WorldSettings.GameType gameType) {
      this.gameType = gameType;
      gameType.configurePlayerCapabilities(this.thisPlayerMP.capabilities);
      this.thisPlayerMP.sendPlayerAbilities();
      this.thisPlayerMP
         .mcServer
         .getConfigurationManager()
         .sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_GAME_MODE, this.thisPlayerMP));
   }

   public WorldSettings.GameType getGameType() {
      return this.gameType;
   }

   public boolean survivalOrAdventure() {
      return this.gameType.isSurvivalOrAdventure();
   }

   public boolean isCreative() {
      return this.gameType.isCreative();
   }

   public void initializeGameType(WorldSettings.GameType gameType) {
      if (this.gameType == WorldSettings.GameType.NOT_SET) {
         this.gameType = gameType;
      }

      this.setGameType(this.gameType);
   }

   public void updateBlockRemoving() {
      this.curblockDamage++;
      if (this.receivedFinishDiggingPacket) {
         int var4 = this.curblockDamage - this.initialBlockDamage;
         Block var5 = this.theWorld.getBlockState(this.field_180241_i).getBlock();
         if (var5.getMaterial() == Material.air) {
            this.receivedFinishDiggingPacket = false;
         } else {
            float var6 = var5.Method434(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (var4 + 1);
            int var7 = (int)(var6 * 10.0F);
            if (var7 != this.durabilityRemainingOnBlock) {
               this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180241_i, var7);
               this.durabilityRemainingOnBlock = var7;
            }

            if (var6 >= 1.0F) {
               this.receivedFinishDiggingPacket = false;
               this.tryHarvestBlock(this.field_180241_i);
            }
         }
      } else if (this.isDestroyingBlock) {
         Block var8 = this.theWorld.getBlockState(this.field_180240_f).getBlock();
         if (var8.getMaterial() == Material.air) {
            this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
            this.durabilityRemainingOnBlock = -1;
            this.isDestroyingBlock = false;
         } else {
            int var9 = this.curblockDamage - this.initialDamage;
            int var10 = (int)(var8.Method434(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (var9 + 1) * 10.0F);
            if (var10 != this.durabilityRemainingOnBlock) {
               this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, var10);
               this.durabilityRemainingOnBlock = var10;
            }
         }
      }
   }

   public void onBlockClicked(BlockPos blockPos, EnumFacing enumFacing) {
      if (this.isCreative()) {
         if (!this.theWorld.extinguishFire(null, blockPos, enumFacing)) {
            this.tryHarvestBlock(blockPos);
         }
      } else {
         Block var6 = this.theWorld.getBlockState(blockPos).getBlock();
         if (this.gameType.isAdventure()) {
            if (this.gameType == WorldSettings.GameType.SPECTATOR) {
               return;
            }

            if (!this.thisPlayerMP.isAllowEdit()) {
               ItemStack var7 = this.thisPlayerMP.getCurrentEquippedItem();
               if (var7 == null) {
                  return;
               }

               if (!var7.canDestroy(var6)) {
                  return;
               }
            }
         }

         this.theWorld.extinguishFire(null, blockPos, enumFacing);
         this.initialDamage = this.curblockDamage;
         float var9 = 1.0F;
         if (var6.getMaterial() != Material.air) {
            var6.onBlockClicked(this.theWorld, blockPos, this.thisPlayerMP);
            var9 = var6.Method434(this.thisPlayerMP, this.thisPlayerMP.worldObj, blockPos);
         }

         if (var6.getMaterial() != Material.air && var9 >= 1.0F) {
            this.tryHarvestBlock(blockPos);
         } else {
            this.isDestroyingBlock = true;
            this.field_180240_f = blockPos;
            int var8 = (int)(var9 * 10.0F);
            this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), blockPos, var8);
            this.durabilityRemainingOnBlock = var8;
         }
      }
   }

   public void blockRemoving(BlockPos blockPos) {
      if (blockPos.equals(this.field_180240_f)) {
         int var5 = this.curblockDamage - this.initialDamage;
         Block var6 = this.theWorld.getBlockState(blockPos).getBlock();
         if (var6.getMaterial() != Material.air) {
            float var7 = var6.Method434(this.thisPlayerMP, this.thisPlayerMP.worldObj, blockPos) * (var5 + 1);
            if (var7 >= 0.7F) {
               this.isDestroyingBlock = false;
               this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), blockPos, -1);
               this.tryHarvestBlock(blockPos);
            } else if (!this.receivedFinishDiggingPacket) {
               this.isDestroyingBlock = false;
               this.receivedFinishDiggingPacket = true;
               this.field_180241_i = blockPos;
               this.initialBlockDamage = this.initialDamage;
            }
         }
      }
   }

   public void cancelDestroyingBlock() {
      this.isDestroyingBlock = false;
      this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
   }

   private boolean removeBlock(BlockPos blockPos) {
      IBlockState var5 = this.theWorld.getBlockState(blockPos);
      var5.getBlock().onBlockHarvested(this.theWorld, blockPos, var5, this.thisPlayerMP);
      boolean var6 = this.theWorld.setBlockToAir(blockPos);
      if (var6) {
         var5.getBlock().onBlockDestroyedByPlayer(this.theWorld, blockPos, var5);
      }

      return var6;
   }

   public boolean tryHarvestBlock(BlockPos blockPos) {
      if (this.gameType.isCreative() && this.thisPlayerMP.getHeldItem() != null && this.thisPlayerMP.getHeldItem().getItem() instanceof ItemSword) {
         return false;
      }

      IBlockState var5 = this.theWorld.getBlockState(blockPos);
      TileEntity var6 = this.theWorld.getTileEntity(blockPos);
      if (this.gameType.isAdventure()) {
         if (this.gameType == WorldSettings.GameType.SPECTATOR) {
            return false;
         }

         if (!this.thisPlayerMP.isAllowEdit()) {
            ItemStack var7 = this.thisPlayerMP.getCurrentEquippedItem();
            if (var7 == null) {
               return false;
            }

            if (!var7.canDestroy(var5.getBlock())) {
               return false;
            }
         }
      }

      this.theWorld.playAuxSFXAtEntity(this.thisPlayerMP, 2001, blockPos, Block.getStateId(var5));
      boolean var10 = this.removeBlock(blockPos);
      if (this.isCreative()) {
         this.thisPlayerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(this.theWorld, blockPos));
      } else {
         ItemStack var8 = this.thisPlayerMP.getCurrentEquippedItem();
         boolean var9 = this.thisPlayerMP.canHarvestBlock(var5.getBlock(), this.thisPlayerMP.inventory.currentItem);
         if (var8 != null) {
            var8.onBlockDestroyed(this.theWorld, var5.getBlock(), blockPos, this.thisPlayerMP);
            if (var8.stackSize == 0) {
               this.thisPlayerMP.destroyCurrentEquippedItem();
            }
         }

         if (var10 && var9) {
            var5.getBlock().harvestBlock(this.theWorld, this.thisPlayerMP, blockPos, var5, var6);
         }
      }

      return var10;
   }

   public boolean tryUseItem(EntityPlayer entityPlayer, World world, ItemStack itemStack) {
      if (this.gameType == WorldSettings.GameType.SPECTATOR) {
         return false;
      }

      int var7 = itemStack.stackSize;
      int var8 = itemStack.getMetadata();
      ItemStack var9 = itemStack.useItemRightClick(world, entityPlayer);
      if (var9 != itemStack || var9 != null && (var9.stackSize != var7 || var9.getMaxItemUseDuration() > 0 || var9.getMetadata() != var8)) {
         entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = var9;
         if (this.isCreative()) {
            var9.stackSize = var7;
            if (var9.isItemStackDamageable()) {
               var9.setItemDamage(var8);
            }
         }

         if (var9.stackSize == 0) {
            entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = null;
         }

         if (!entityPlayer.isUsingItem()) {
            ((EntityPlayerMP)entityPlayer).sendContainerToPlayer(entityPlayer.inventoryContainer);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean activateBlockOrUseItem(
      EntityPlayer entityPlayer, World world, ItemStack itemStack, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (this.gameType == WorldSettings.GameType.SPECTATOR) {
         TileEntity var16 = world.getTileEntity(blockPos);
         if (var16 instanceof ILockableContainer var17) {
            Block var18 = world.getBlockState(blockPos).getBlock();
            if (var17 instanceof TileEntityChest && var18 instanceof BlockChest) {
               var17 = ((BlockChest)var18).getLockableContainer(world, blockPos);
            }

            if (var17 != null) {
               entityPlayer.displayGUIChest(var17);
               return true;
            }
         } else if (var16 instanceof IInventory) {
            entityPlayer.displayGUIChest((IInventory)var16);
            return true;
         }

         return false;
      } else {
         if (!entityPlayer.isSneaking() || entityPlayer.getHeldItem() == null) {
            IBlockState var12 = world.getBlockState(blockPos);
            if (var12.getBlock().onBlockActivated(world, blockPos, var12, entityPlayer, enumFacing, float6, float7, float8)) {
               return true;
            }
         }

         if (itemStack == null) {
            return false;
         } else if (this.isCreative()) {
            int var15 = itemStack.getMetadata();
            int var13 = itemStack.stackSize;
            boolean var14 = itemStack.onItemUse(entityPlayer, world, blockPos, enumFacing, float6, float7, float8);
            itemStack.setItemDamage(var15);
            itemStack.stackSize = var13;
            return var14;
         } else {
            return itemStack.onItemUse(entityPlayer, world, blockPos, enumFacing, float6, float7, float8);
         }
      }
   }

   public void setWorld(WorldServer worldServer) {
      this.theWorld = worldServer;
   }
}
