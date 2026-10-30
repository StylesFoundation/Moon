package net.minecraft.inventory;

import java.util.List;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerEnchantment extends Container {
   public IInventory tableInventory = new InventoryBasic("Enchant", true, 2) {
      @Override
      public int getInventoryStackLimit() {
         return 64;
      }

      @Override
      public void markDirty() {
         super.markDirty();
         ContainerEnchantment.this.onCraftMatrixChanged(this);
      }
   };
   private final World worldPointer;
   private final BlockPos position;
   private final Random rand = new Random();
   public int xpSeed;
   public int[] enchantLevels = new int[3];
   public int[] enchantmentIds = new int[]{-1, -1, -1};

   public ContainerEnchantment(InventoryPlayer inventoryPlayer, World world) {
      this(inventoryPlayer, world, BlockPos.ORIGIN);
   }

   public ContainerEnchantment(InventoryPlayer inventoryPlayer, World world, BlockPos blockPos) {
      this.worldPointer = world;
      this.position = blockPos;
      this.xpSeed = inventoryPlayer.player.getXPSeed();
      this.addSlotToContainer(new Slot(this.tableInventory, 0, 15, 47) {
         @Override
         public boolean isItemValid(ItemStack itemStack) {
            return true;
         }

         @Override
         public int getSlotStackLimit() {
            return 1;
         }
      });
      this.addSlotToContainer(new Slot(this.tableInventory, 1, 35, 47) {
         @Override
         public boolean isItemValid(ItemStack itemStack) {
            return itemStack.getItem() == Items.dye && EnumDyeColor.byDyeDamage(itemStack.getMetadata()) == EnumDyeColor.BLUE;
         }
      });

      for (int var7 = 0; var7 < 3; var7++) {
         for (int var8 = 0; var8 < 9; var8++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, var8 + var7 * 9 + 9, 8 + var8 * 18, 84 + var7 * 18));
         }
      }

      for (int var9 = 0; var9 < 9; var9++) {
         this.addSlotToContainer(new Slot(inventoryPlayer, var9, 8 + var9 * 18, 142));
      }
   }

   @Override
   public void onCraftGuiOpened(ICrafting iCrafting) {
      super.onCraftGuiOpened(iCrafting);
      iCrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
      iCrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
      iCrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
      iCrafting.sendProgressBarUpdate(this, 3, this.xpSeed & -16);
      iCrafting.sendProgressBarUpdate(this, 4, this.enchantmentIds[0]);
      iCrafting.sendProgressBarUpdate(this, 5, this.enchantmentIds[1]);
      iCrafting.sendProgressBarUpdate(this, 6, this.enchantmentIds[2]);
   }

   @Override
   public void detectAndSendChanges() {
      super.detectAndSendChanges();

      for (ICrafting var5 : this.crafters) {
         var5.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
         var5.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
         var5.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
         var5.sendProgressBarUpdate(this, 3, this.xpSeed & -16);
         var5.sendProgressBarUpdate(this, 4, this.enchantmentIds[0]);
         var5.sendProgressBarUpdate(this, 5, this.enchantmentIds[1]);
         var5.sendProgressBarUpdate(this, 6, this.enchantmentIds[2]);
      }
   }

   @Override
   public void updateProgressBar(int integer1, int integer2) {
      if (integer1 >= 0 && integer1 <= 2) {
         this.enchantLevels[integer1] = integer2;
      } else if (integer1 == 3) {
         this.xpSeed = integer2;
      } else if (integer1 >= 4 && integer1 <= 6) {
         this.enchantmentIds[integer1 - 4] = integer2;
      } else {
         super.updateProgressBar(integer1, integer2);
      }
   }

   @Override
   public void onCraftMatrixChanged(IInventory iInventory) {
      if (iInventory == this.tableInventory) {
         ItemStack var5 = iInventory.getStackInSlot(0);
         if (var5 != null && var5.isItemEnchantable()) {
            if (!this.worldPointer.isRemote) {
               int var10 = 0;

               for (int var7 = -1; var7 <= 1; var7++) {
                  for (int var8 = -1; var8 <= 1; var8++) {
                     if ((var7 != 0 || var8 != 0)
                        && this.worldPointer.isAirBlock(this.position.add(var8, 0, var7))
                        && this.worldPointer.isAirBlock(this.position.add(var8, 1, var7))) {
                        if (this.worldPointer.getBlockState(this.position.add(var8 * 2, 0, var7 * 2)).getBlock() == Blocks.bookshelf) {
                           var10++;
                        }

                        if (this.worldPointer.getBlockState(this.position.add(var8 * 2, 1, var7 * 2)).getBlock() == Blocks.bookshelf) {
                           var10++;
                        }

                        if (var8 != 0 && var7 != 0) {
                           if (this.worldPointer.getBlockState(this.position.add(var8 * 2, 0, var7)).getBlock() == Blocks.bookshelf) {
                              var10++;
                           }

                           if (this.worldPointer.getBlockState(this.position.add(var8 * 2, 1, var7)).getBlock() == Blocks.bookshelf) {
                              var10++;
                           }

                           if (this.worldPointer.getBlockState(this.position.add(var8, 0, var7 * 2)).getBlock() == Blocks.bookshelf) {
                              var10++;
                           }

                           if (this.worldPointer.getBlockState(this.position.add(var8, 1, var7 * 2)).getBlock() == Blocks.bookshelf) {
                              var10++;
                           }
                        }
                     }
                  }
               }

               this.rand.setSeed(this.xpSeed);

               for (int var11 = 0; var11 < 3; var11++) {
                  this.enchantLevels[var11] = EnchantmentHelper.calcItemStackEnchantability(this.rand, var11, var10, var5);
                  this.enchantmentIds[var11] = -1;
                  if (this.enchantLevels[var11] < var11 + 1) {
                     this.enchantLevels[var11] = 0;
                  }
               }

               for (int var12 = 0; var12 < 3; var12++) {
                  if (this.enchantLevels[var12] > 0) {
                     List<EnchantmentData> var13 = this.func_178148_a(var5, var12, this.enchantLevels[var12]);
                     if (var13 != null && !var13.isEmpty()) {
                        EnchantmentData var9 = var13.get(this.rand.nextInt(var13.size()));
                        this.enchantmentIds[var12] = var9.enchantmentobj.effectId | var9.enchantmentLevel << 8;
                     }
                  }
               }

               this.detectAndSendChanges();
            }
         } else {
            for (int var6 = 0; var6 < 3; var6++) {
               this.enchantLevels[var6] = 0;
               this.enchantmentIds[var6] = -1;
            }
         }
      }
   }

   @Override
   public boolean enchantItem(EntityPlayer entityPlayer, int integer) {
      ItemStack var6 = this.tableInventory.getStackInSlot(0);
      ItemStack var7 = this.tableInventory.getStackInSlot(1);
      int var8 = integer + 1;
      if ((var7 == null || var7.stackSize < var8) && !entityPlayer.capabilities.isCreativeMode) {
         return false;
      }

      if (this.enchantLevels[integer] > 0
         && var6 != null
         && (entityPlayer.experienceLevel >= var8 && entityPlayer.experienceLevel >= this.enchantLevels[integer] || entityPlayer.capabilities.isCreativeMode)) {
         if (!this.worldPointer.isRemote) {
            List<EnchantmentData> var9 = this.func_178148_a(var6, integer, this.enchantLevels[integer]);
            boolean var10 = var6.getItem() == Items.book;
            if (var9 != null) {
               entityPlayer.removeExperienceLevel(var8);
               if (var10) {
                  var6.setItem(Items.enchanted_book);
               }

               for (EnchantmentData var12 : var9) {
                  if (var10) {
                     Items.enchanted_book.addEnchantment(var6, var12);
                  } else {
                     var6.addEnchantment(var12.enchantmentobj, var12.enchantmentLevel);
                  }
               }

               if (!entityPlayer.capabilities.isCreativeMode) {
                  var7.stackSize -= var8;
                  if (var7.stackSize <= 0) {
                     this.tableInventory.setInventorySlotContents(1, null);
                  }
               }

               entityPlayer.triggerAchievement(StatList.field_181739_W);
               this.tableInventory.markDirty();
               this.xpSeed = entityPlayer.getXPSeed();
               this.onCraftMatrixChanged(this.tableInventory);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private List<EnchantmentData> func_178148_a(ItemStack itemStack, int integer2, int integer3) {
      this.rand.setSeed(this.xpSeed + integer2);
      List var7 = EnchantmentHelper.buildEnchantmentList(this.rand, itemStack, integer3);
      if (itemStack.getItem() == Items.book && var7 != null && var7.size() > 1) {
         var7.remove(this.rand.nextInt(var7.size()));
      }

      return var7;
   }

   public int getLapisAmount() {
      ItemStack var4 = this.tableInventory.getStackInSlot(1);
      return var4 == null ? 0 : var4.stackSize;
   }

   @Override
   public void onContainerClosed(EntityPlayer entityPlayer) {
      super.onContainerClosed(entityPlayer);
      if (!this.worldPointer.isRemote) {
         for (int var5 = 0; var5 < this.tableInventory.getSizeInventory(); var5++) {
            ItemStack var6 = this.tableInventory.removeStackFromSlot(var5);
            if (var6 != null) {
               entityPlayer.dropPlayerItemWithRandomChoice(var6, false);
            }
         }
      }
   }

   @Override
   public boolean canInteractWith(EntityPlayer entityPlayer) {
      return this.worldPointer.getBlockState(this.position).getBlock() == Blocks.enchanting_table
         && entityPlayer.getDistanceSq(this.position.getX() + 0.5, this.position.getY() + 0.5, this.position.getZ() + 0.5) <= 64.0;
   }

   @Override
   public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int integer) {
      ItemStack var6 = null;
      Slot var7 = this.inventorySlots.get(integer);
      if (var7 != null && var7.getHasStack()) {
         ItemStack var8 = var7.getStack();
         var6 = var8.copy();
         if (integer == 0) {
            if (!this.mergeItemStack(var8, 2, 38, true)) {
               return null;
            }
         } else if (integer == 1) {
            if (!this.mergeItemStack(var8, 2, 38, true)) {
               return null;
            }
         } else if (var8.getItem() == Items.dye && EnumDyeColor.byDyeDamage(var8.getMetadata()) == EnumDyeColor.BLUE) {
            if (!this.mergeItemStack(var8, 1, 2, true)) {
               return null;
            }
         } else {
            if (this.inventorySlots.get(0).getHasStack() || !this.inventorySlots.get(0).isItemValid(var8)) {
               return null;
            }

            if (var8.hasTagCompound() && var8.stackSize == 1) {
               this.inventorySlots.get(0).putStack(var8.copy());
               var8.stackSize = 0;
            } else if (var8.stackSize >= 1) {
               this.inventorySlots.get(0).putStack(new ItemStack(var8.getItem(), 1, var8.getMetadata()));
               var8.stackSize--;
            }
         }

         if (var8.stackSize == 0) {
            var7.putStack(null);
         } else {
            var7.onSlotChanged();
         }

         if (var8.stackSize == var6.stackSize) {
            return null;
         }

         var7.onPickupFromSlot(entityPlayer, var8);
      }

      return var6;
   }
}
