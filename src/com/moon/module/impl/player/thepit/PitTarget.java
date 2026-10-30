package com.moon.module.impl.player.thepit;

import com.moon.module.impl.player.ThePit;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public record PitTarget(ThePit.Features features, Slot slot, ItemStack itemStack) {
   public ThePit.Features getFeature() {
      return this.features;
   }

   public Slot getSlot() {
      return this.slot;
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }
}
