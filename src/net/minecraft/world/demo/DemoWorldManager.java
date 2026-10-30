package net.minecraft.world.demo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DemoWorldManager extends ItemInWorldManager {
   private boolean field_73105_c;
   private boolean demoTimeExpired;
   private int field_73104_e;
   private int field_73102_f;

   public DemoWorldManager(World world) {
      super(world);
   }

   @Override
   public void updateBlockRemoving() {
      super.updateBlockRemoving();
      this.field_73102_f++;
      long var4 = this.theWorld.getTotalWorldTime();
      long var6 = var4 / 24000L + 1L;
      if (!this.field_73105_c && this.field_73102_f > 20) {
         this.field_73105_c = true;
         this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 0.0F));
      }

      this.demoTimeExpired = var4 > 120500L;
      if (this.demoTimeExpired) {
         this.field_73104_e++;
      }

      if (var4 % 24000L == 500L) {
         if (var6 <= 6L) {
            this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day." + var6));
         }
      } else if (var6 == 1L) {
         if (var4 == 100L) {
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 101.0F));
         } else if (var4 == 175L) {
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 102.0F));
         } else if (var4 == 250L) {
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 103.0F));
         }
      } else if (var6 == 5L && var4 % 24000L == 22000L) {
         this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day.warning"));
      }
   }

   private void sendDemoReminder() {
      if (this.field_73104_e > 100) {
         this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.reminder"));
         this.field_73104_e = 0;
      }
   }

   @Override
   public void onBlockClicked(BlockPos blockPos, EnumFacing enumFacing) {
      if (this.demoTimeExpired) {
         this.sendDemoReminder();
      } else {
         super.onBlockClicked(blockPos, enumFacing);
      }
   }

   @Override
   public void blockRemoving(BlockPos blockPos) {
      if (!this.demoTimeExpired) {
         super.blockRemoving(blockPos);
      }
   }

   @Override
   public boolean tryHarvestBlock(BlockPos blockPos) {
      return !this.demoTimeExpired && super.tryHarvestBlock(blockPos);
   }

   @Override
   public boolean tryUseItem(EntityPlayer entityPlayer, World world, ItemStack itemStack) {
      if (this.demoTimeExpired) {
         this.sendDemoReminder();
         return false;
      } else {
         return super.tryUseItem(entityPlayer, world, itemStack);
      }
   }

   @Override
   public boolean activateBlockOrUseItem(
      EntityPlayer entityPlayer, World world, ItemStack itemStack, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (this.demoTimeExpired) {
         this.sendDemoReminder();
         return false;
      } else {
         return super.activateBlockOrUseItem(entityPlayer, world, itemStack, blockPos, enumFacing, float6, float7, float8);
      }
   }
}
