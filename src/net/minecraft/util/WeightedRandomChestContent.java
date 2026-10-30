package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;

public class WeightedRandomChestContent extends WeightedRandom.Item {
   private final ItemStack theItemId;
   private final int minStackSize;
   private final int maxStackSize;

   public WeightedRandomChestContent(Item item, int integer2, int integer3, int integer4, int integer5) {
      super(integer5);
      this.theItemId = new ItemStack(item, 1, integer2);
      this.minStackSize = integer3;
      this.maxStackSize = integer4;
   }

   public WeightedRandomChestContent(ItemStack itemStack, int integer2, int integer3, int integer4) {
      super(integer4);
      this.theItemId = itemStack;
      this.minStackSize = integer2;
      this.maxStackSize = integer3;
   }

   public static void generateChestContents(Random random, List<WeightedRandomChestContent> list, IInventory iInventory, int integer) {
      for (int var7 = 0; var7 < integer; var7++) {
         WeightedRandomChestContent var8 = WeightedRandom.getRandomItem(random, list);
         int var9 = var8.minStackSize + random.nextInt(var8.maxStackSize - var8.minStackSize + 1);
         if (var8.theItemId.getMaxStackSize() >= var9) {
            ItemStack var12 = var8.theItemId.copy();
            var12.stackSize = var9;
            iInventory.setInventorySlotContents(random.nextInt(iInventory.getSizeInventory()), var12);
         } else {
            for (int var10 = 0; var10 < var9; var10++) {
               ItemStack var11 = var8.theItemId.copy();
               var11.stackSize = 1;
               iInventory.setInventorySlotContents(random.nextInt(iInventory.getSizeInventory()), var11);
            }
         }
      }
   }

   public static void generateDispenserContents(Random random, List<WeightedRandomChestContent> list, TileEntityDispenser tileEntityDispenser, int integer) {
      for (int var7 = 0; var7 < integer; var7++) {
         WeightedRandomChestContent var8 = WeightedRandom.getRandomItem(random, list);
         int var9 = var8.minStackSize + random.nextInt(var8.maxStackSize - var8.minStackSize + 1);
         if (var8.theItemId.getMaxStackSize() >= var9) {
            ItemStack var12 = var8.theItemId.copy();
            var12.stackSize = var9;
            tileEntityDispenser.setInventorySlotContents(random.nextInt(tileEntityDispenser.getSizeInventory()), var12);
         } else {
            for (int var10 = 0; var10 < var9; var10++) {
               ItemStack var11 = var8.theItemId.copy();
               var11.stackSize = 1;
               tileEntityDispenser.setInventorySlotContents(random.nextInt(tileEntityDispenser.getSizeInventory()), var11);
            }
         }
      }
   }

   public static List<WeightedRandomChestContent> func_177629_a(List<WeightedRandomChestContent> list, WeightedRandomChestContent... arr) {
      ArrayList var2 = Lists.newArrayList(list);
      Collections.addAll(var2, arr);
      return var2;
   }
}
