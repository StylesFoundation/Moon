package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShapelessRecipes implements IRecipe {
   private final ItemStack recipeOutput;
   private final List<ItemStack> recipeItems;

   public ShapelessRecipes(ItemStack itemStack, List<ItemStack> list) {
      this.recipeOutput = itemStack;
      this.recipeItems = list;
   }

   @Override
   public ItemStack getRecipeOutput() {
      return this.recipeOutput;
   }

   @Override
   public ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting) {
      ItemStack[] var5 = new ItemStack[inventoryCrafting.getSizeInventory()];

      for (int var6 = 0; var6 < var5.length; var6++) {
         ItemStack var7 = inventoryCrafting.getStackInSlot(var6);
         if (var7 != null && var7.getItem().hasContainerItem()) {
            var5[var6] = new ItemStack(var7.getItem().getContainerItem());
         }
      }

      return var5;
   }

   @Override
   public boolean matches(InventoryCrafting inventoryCrafting, World world) {
      List<ItemStack> var6 = Lists.newArrayList(this.recipeItems);

      for (int var7 = 0; var7 < inventoryCrafting.getHeight(); var7++) {
         for (int var8 = 0; var8 < inventoryCrafting.getWidth(); var8++) {
            ItemStack var9 = inventoryCrafting.getStackInRowAndColumn(var8, var7);
            if (var9 != null) {
               boolean var10 = false;

               for (ItemStack var12 : var6) {
                  if (var9.getItem() == var12.getItem() && (var12.getMetadata() == 32767 || var9.getMetadata() == var12.getMetadata())) {
                     var10 = true;
                     var6.remove(var12);
                     break;
                  }
               }

               if (!var10) {
                  return false;
               }
            }
         }
      }

      return var6.isEmpty();
   }

   @Override
   public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
      return this.recipeOutput.copy();
   }

   @Override
   public int getRecipeSize() {
      return this.recipeItems.size();
   }
}
