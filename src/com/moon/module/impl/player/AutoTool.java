package com.moon.module.impl.player;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.HeldItemEvent;
import com.moon.events.impl.player.SlotChangeEvent;
import com.moon.events.impl.render.RenderHeldItemEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldSettings;

public class AutoTool extends Module {
   private final BooleanSetting silent = BooleanSetting.builder("silent").defaults("Silent", false).build();
   private BlockPos miningPos;
   private boolean active = false;
   private int previousSlot = -1;

   public AutoTool(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Override
   public void onDisable() {
      this.previousSlot = -1;
   }

   @Override
   public void onEnable() {
      this.miningPos = null;
      this.previousSlot = -1;
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.PRE && mc.thePlayer != null && mc.thePlayer.ticksExisted <= 2) {
         this.previousSlot = -1;
      }
   }

   @Subscribe
   public void onHeldItem(HeldItemEvent event) {
      if (this.silent.getValue() && this.previousSlot != -1 && mc.playerController.getCurrentGameType() != WorldSettings.GameType.CREATIVE) {
         event.setSlot(this.previousSlot);
      }
   }

   @Subscribe
   public void onRenderHeldItem(RenderHeldItemEvent event) {
      if (this.silent.getValue() && this.previousSlot != -1 && mc.playerController.getCurrentGameType() != WorldSettings.GameType.CREATIVE) {
         event.setSlot(this.previousSlot);
      }
   }

   @Subscribe
   public void onSlotChange(SlotChangeEvent event) {
      if (this.silent.getValue() && mc.playerController.getCurrentGameType() != WorldSettings.GameType.CREATIVE && this.previousSlot != -1) {
         event.setCancelled(true);
      }
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (event.isSend()) {
         if (event.getPacket() instanceof C07PacketPlayerDigging diggingPacket && mc.playerController.getCurrentGameType() != WorldSettings.GameType.CREATIVE) {
            if (diggingPacket.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
               this.handleStartDestroyBlock(event, diggingPacket);
            } else {
               this.handleStopDestroyBlock(event, diggingPacket);
            }
         }
      }
   }

   private void handleStartDestroyBlock(PacketEvent event, C07PacketPlayerDigging packet) {
      this.miningPos = packet.getPosition();
      this.previousSlot = mc.thePlayer.inventory.currentItem;
      if (!mc.thePlayer.capabilities.isCreativeMode) {
         BlockPos blockPos = packet.getPosition();
         if (blockPos != null) {
            int bestSlot = this.findBestToolSlot(blockPos);
            mc.thePlayer.bC.switchToSlot(bestSlot);
            mc.playerController.syncCurrentPlayItem();
         }
      }
   }

   private void handleStopDestroyBlock(PacketEvent event, C07PacketPlayerDigging packet) {
      this.miningPos = null;
      event.setCancelled(true);
      mc.thePlayer.sendQueue.Method755(packet);
      mc.thePlayer.bC.switchToSlot(this.previousSlot);
      this.previousSlot = -1;
   }

   public void reset() {
      this.miningPos = null;
      mc.thePlayer.bC.switchToSlot(this.previousSlot);
      this.previousSlot = -1;
   }

   private int findBestToolSlot(BlockPos pos) {
      int bestSlot = mc.thePlayer.inventory.currentItem;
      Block block = mc.theWorld.getBlockState(pos).getBlock();
      if (block == null) {
         return bestSlot;
      }

      float bestSpeed = 1.0F;

      for (int i = 36; i < 45; i++) {
         ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
         if (stack != null) {
            float speed = stack.getItem().getStrVsBlock(stack, block);
            if (speed > bestSpeed) {
               bestSlot = i - 36;
               bestSpeed = speed;
            }
         }
      }

      return bestSlot;
   }
}
