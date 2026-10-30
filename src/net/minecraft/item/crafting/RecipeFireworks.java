package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class RecipeFireworks implements IRecipe {
   private ItemStack field_92102_a;

   @Override
   public boolean matches(InventoryCrafting inventoryCrafting, World world) {
      this.field_92102_a = null;
      int var6 = 0;
      int var7 = 0;
      int var8 = 0;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;

      for (int var12 = 0; var12 < inventoryCrafting.getSizeInventory(); var12++) {
         ItemStack var13 = inventoryCrafting.getStackInSlot(var12);
         if (var13 != null) {
            if (var13.getItem() == Items.gunpowder) {
               var7++;
            } else if (var13.getItem() == Items.firework_charge) {
               var9++;
            } else if (var13.getItem() == Items.dye) {
               var8++;
            } else if (var13.getItem() == Items.paper) {
               var6++;
            } else if (var13.getItem() == Items.glowstone_dust) {
               var10++;
            } else if (var13.getItem() == Items.diamond) {
               var10++;
            } else if (var13.getItem() == Items.fire_charge) {
               var11++;
            } else if (var13.getItem() == Items.feather) {
               var11++;
            } else if (var13.getItem() == Items.gold_nugget) {
               var11++;
            } else {
               if (var13.getItem() != Items.skull) {
                  return false;
               }

               var11++;
            }
         }
      }

      var10 = var10 + var8 + var11;
      if (var7 > 3 || var6 > 1) {
         return false;
      }

      if (var7 >= 1 && var6 == 1 && var10 == 0) {
         this.field_92102_a = new ItemStack(Items.fireworks);
         if (var9 > 0) {
            NBTTagCompound var21 = new NBTTagCompound();
            NBTTagCompound var25 = new NBTTagCompound();
            NBTTagList var29 = new NBTTagList();

            for (int var30 = 0; var30 < inventoryCrafting.getSizeInventory(); var30++) {
               ItemStack var32 = inventoryCrafting.getStackInSlot(var30);
               if (var32 != null && var32.getItem() == Items.firework_charge && var32.hasTagCompound() && var32.getTagCompound().hasKey("Explosion", 10)) {
                  var29.appendTag(var32.getTagCompound().getCompoundTag("Explosion"));
               }
            }

            var25.setTag("Explosions", var29);
            var25.setByte("Flight", (byte)var7);
            var21.setTag("Fireworks", var25);
            this.field_92102_a.setTagCompound(var21);
         }

         return true;
      } else if (var7 == 1 && var6 == 0 && var9 == 0 && var8 > 0 && var11 <= 1) {
         this.field_92102_a = new ItemStack(Items.firework_charge);
         NBTTagCompound var20 = new NBTTagCompound();
         NBTTagCompound var24 = new NBTTagCompound();
         byte var28 = 0;
         ArrayList var15 = Lists.newArrayList();

         for (int var16 = 0; var16 < inventoryCrafting.getSizeInventory(); var16++) {
            ItemStack var17 = inventoryCrafting.getStackInSlot(var16);
            if (var17 != null) {
               if (var17.getItem() == Items.dye) {
                  var15.add(ItemDye.dyeColors[var17.getMetadata() & 15]);
               } else if (var17.getItem() == Items.glowstone_dust) {
                  var24.setBoolean("Flicker", true);
               } else if (var17.getItem() == Items.diamond) {
                  var24.setBoolean("Trail", true);
               } else if (var17.getItem() == Items.fire_charge) {
                  var28 = 1;
               } else if (var17.getItem() == Items.feather) {
                  var28 = 4;
               } else if (var17.getItem() == Items.gold_nugget) {
                  var28 = 2;
               } else if (var17.getItem() == Items.skull) {
                  var28 = 3;
               }
            }
         }

         int[] var31 = new int[var15.size()];

         for (int var33 = 0; var33 < var31.length; var33++) {
            var31[var33] = (Integer)var15.get(var33);
         }

         var24.setIntArray("Colors", var31);
         var24.setByte("Type", var28);
         var20.setTag("Explosion", var24);
         this.field_92102_a.setTagCompound(var20);
         return true;
      } else if (var7 == 0 && var6 == 0 && var9 == 1 && var8 > 0 && var8 == var10) {
         ArrayList var19 = Lists.newArrayList();

         for (int var22 = 0; var22 < inventoryCrafting.getSizeInventory(); var22++) {
            ItemStack var14 = inventoryCrafting.getStackInSlot(var22);
            if (var14 != null) {
               if (var14.getItem() == Items.dye) {
                  var19.add(ItemDye.dyeColors[var14.getMetadata() & 15]);
               } else if (var14.getItem() == Items.firework_charge) {
                  this.field_92102_a = var14.copy();
                  this.field_92102_a.stackSize = 1;
               }
            }
         }

         int[] var23 = new int[var19.size()];

         for (int var26 = 0; var26 < var23.length; var26++) {
            var23[var26] = (Integer)var19.get(var26);
         }

         if (this.field_92102_a != null && this.field_92102_a.hasTagCompound()) {
            NBTTagCompound var27 = this.field_92102_a.getTagCompound().getCompoundTag("Explosion");
            if (var27 == null) {
               return false;
            }

            var27.setIntArray("FadeColors", var23);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
      return this.field_92102_a.copy();
   }

   @Override
   public int getRecipeSize() {
      return 10;
   }

   @Override
   public ItemStack getRecipeOutput() {
      return this.field_92102_a;
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
}
