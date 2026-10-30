package com.moon.util.game;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotUtil {
   private final Minecraft mc = Minecraft.getMinecraft();
   private int b = 0;
   private int c = 0;

   @Deprecated
   public void deprecatedNoOp() {
   }

   @Deprecated
   public void deprecatedNoOp2() {
   }

   @Deprecated
   public boolean isDeprecatedActive() {
      return this.c > 0;
   }

   public void switchToSlot(int slot) {
      if (slot >= 0 && slot <= 8) {
         this.mc.thePlayer.inventory.currentItem = slot;
      }
   }

   @Deprecated
   public void deprecatedNoOpForSlot(int slot) {
   }

   @Deprecated
   public void deprecatedNoOp3() {
   }

   public boolean hasBlock() {
      for (int slot = 0; slot <= 8; slot++) {
         ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(slot);
         if (this.isValidBlockItem(stack)) {
            return true;
         }
      }

      return false;
   }

   public SlotUtil.Entry findBlock() {
      SlotUtil.Entry entry = new SlotUtil.Entry();

      for (int slot = 0; slot <= 8; slot++) {
         ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(slot);
         if (this.isValidBlockItem(stack)) {
            entry.setStack(stack);
            entry.setSlot(slot);
            break;
         }
      }

      return entry;
   }

   private boolean isValidBlockItem(ItemStack stack) {
      if (stack != null && stack.getItem() instanceof ItemBlock) {
         Block block = ((ItemBlock)stack.getItem()).getBlock();
         return block instanceof BlockContainer || !block.getMaterial().blocksMovement()
            ? false
            : !(block instanceof BlockSand)
               && !(block instanceof BlockGravel)
               && !(block instanceof BlockSoulSand)
               && !(block instanceof BlockAnvil)
               && !(block instanceof BlockTNT);
      } else {
         return false;
      }
   }

   public static class Entry {
      private ItemStack stack;
      private int slot;

      public ItemStack getStack() {
         return this.stack;
      }

      public void setStack(ItemStack stack) {
         this.stack = stack;
      }

      public int getSlot() {
         return this.slot;
      }

      public void setSlot(int slot) {
         this.slot = slot;
      }

      public int getStackSize() {
         return this.stack != null ? this.stack.stackSize : 0;
      }

      public boolean isPresent() {
         return this.stack != null;
      }
   }
}
