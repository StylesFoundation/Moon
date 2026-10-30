package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RecipeBookCloning implements IRecipe {
   @Override
   public boolean matches(InventoryCrafting inventoryCrafting, World world) {
      int var6 = 0;
      ItemStack var7 = null;

      for (int var8 = 0; var8 < inventoryCrafting.getSizeInventory(); var8++) {
         ItemStack var9 = inventoryCrafting.getStackInSlot(var8);
         if (var9 != null) {
            if (var9.getItem() == Items.written_book) {
               if (var7 != null) {
                  return false;
               }

               var7 = var9;
            } else {
               if (var9.getItem() != Items.writable_book) {
                  return false;
               }

               var6++;
            }
         }
      }

      return var7 != null && var6 > 0;
   }

   @Override
   public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
      int var5 = 0;
      ItemStack var6 = null;

      for (int var7 = 0; var7 < inventoryCrafting.getSizeInventory(); var7++) {
         ItemStack var8 = inventoryCrafting.getStackInSlot(var7);
         if (var8 != null) {
            if (var8.getItem() == Items.written_book) {
               if (var6 != null) {
                  return null;
               }

               var6 = var8;
            } else {
               if (var8.getItem() != Items.writable_book) {
                  return null;
               }

               var5++;
            }
         }
      }

      if (var6 != null && var5 >= 1 && ItemEditableBook.getGeneration(var6) < 2) {
         ItemStack var9 = new ItemStack(Items.written_book, var5);
         var9.setTagCompound((NBTTagCompound)var6.getTagCompound().copy());
         var9.getTagCompound().setInteger("generation", ItemEditableBook.getGeneration(var6) + 1);
         if (var6.hasDisplayName()) {
            var9.setStackDisplayName(var6.getDisplayName());
         }

         return var9;
      } else {
         return null;
      }
   }

   @Override
   public int getRecipeSize() {
      return 9;
   }

   @Override
   public ItemStack getRecipeOutput() {
      return null;
   }

   @Override
   public ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting) {
      ItemStack[] var5 = new ItemStack[inventoryCrafting.getSizeInventory()];

      for (int var6 = 0; var6 < var5.length; var6++) {
         ItemStack var7 = inventoryCrafting.getStackInSlot(var6);
         if (var7 != null && var7.getItem() instanceof ItemEditableBook) {
            var5[var6] = var7;
            break;
         }
      }

      return var5;
   }
}
