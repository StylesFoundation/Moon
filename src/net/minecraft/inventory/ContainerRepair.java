package net.minecraft.inventory;

import java.util.Map;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerRepair extends Container {
   private static final Logger logger = LogManager.getLogger();
   private final IInventory outputSlot = new InventoryCraftResult();
   private final IInventory inputSlots = new InventoryBasic("Repair", true, 2) {
      @Override
      public void markDirty() {
         super.markDirty();
         ContainerRepair.this.onCraftMatrixChanged(this);
      }
   };
   private final World theWorld;
   private final BlockPos selfPosition;
   public int maximumCost;
   private int materialCost;
   private String repairedItemName;
   private final EntityPlayer thePlayer;

   public ContainerRepair(InventoryPlayer inventoryPlayer, World world, EntityPlayer entityPlayer) {
      this(inventoryPlayer, world, BlockPos.ORIGIN, entityPlayer);
   }

   public ContainerRepair(InventoryPlayer inventoryPlayer, World world, BlockPos blockPos, EntityPlayer entityPlayer) {
      this.selfPosition = blockPos;
      this.theWorld = world;
      this.thePlayer = entityPlayer;
      this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
      this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
      this.addSlotToContainer(
         new Slot(this.outputSlot, 2, 134, 47) {
            @Override
            public boolean isItemValid(ItemStack itemStack) {
               return false;
            }

            @Override
            public boolean canTakeStack(EntityPlayer entityPlayer) {
               return (entityPlayer.capabilities.isCreativeMode || entityPlayer.experienceLevel >= ContainerRepair.this.maximumCost)
                  && ContainerRepair.this.maximumCost > 0
                  && this.getHasStack();
            }

            @Override
            public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack) {
               if (!entityPlayer.capabilities.isCreativeMode) {
                  entityPlayer.addExperienceLevel(-ContainerRepair.this.maximumCost);
               }

               ContainerRepair.this.inputSlots.setInventorySlotContents(0, null);
               if (ContainerRepair.this.materialCost > 0) {
                  ItemStack var6 = ContainerRepair.this.inputSlots.getStackInSlot(1);
                  if (var6 != null && var6.stackSize > ContainerRepair.this.materialCost) {
                     var6.stackSize = var6.stackSize - ContainerRepair.this.materialCost;
                     ContainerRepair.this.inputSlots.setInventorySlotContents(1, var6);
                  } else {
                     ContainerRepair.this.inputSlots.setInventorySlotContents(1, null);
                  }
               } else {
                  ContainerRepair.this.inputSlots.setInventorySlotContents(1, null);
               }

               ContainerRepair.this.maximumCost = 0;
               IBlockState var8 = world.getBlockState(blockPos);
               if (!entityPlayer.capabilities.isCreativeMode && !world.isRemote && var8.getBlock() == Blocks.anvil && entityPlayer.getRNG().nextFloat() < 0.12F
                  )
                {
                  int var7 = var8.getValue(BlockAnvil.DAMAGE);
                  if (++var7 > 2) {
                     world.setBlockToAir(blockPos);
                     world.playAuxSFX(1020, blockPos, 0);
                  } else {
                     world.setBlockState(blockPos, var8.withProperty(BlockAnvil.DAMAGE, var7), 2);
                     world.playAuxSFX(1021, blockPos, 0);
                  }
               } else if (!world.isRemote) {
                  world.playAuxSFX(1021, blockPos, 0);
               }
            }
         }
      );

      for (int var8 = 0; var8 < 3; var8++) {
         for (int var9 = 0; var9 < 9; var9++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, var9 + var8 * 9 + 9, 8 + var9 * 18, 84 + var8 * 18));
         }
      }

      for (int var10 = 0; var10 < 9; var10++) {
         this.addSlotToContainer(new Slot(inventoryPlayer, var10, 8 + var10 * 18, 142));
      }
   }

   @Override
   public void onCraftMatrixChanged(IInventory iInventory) {
      super.onCraftMatrixChanged(iInventory);
      if (iInventory == this.inputSlots) {
         this.updateRepairOutput();
      }
   }

   public void updateRepairOutput() {
      ItemStack var11 = this.inputSlots.getStackInSlot(0);
      this.maximumCost = 1;
      int var12 = 0;
      byte var14 = 0;
      if (var11 == null) {
         this.outputSlot.setInventorySlotContents(0, null);
         this.maximumCost = 0;
      } else {
         ItemStack var15 = var11.copy();
         ItemStack var16 = this.inputSlots.getStackInSlot(1);
         Map<Integer, Integer> var17 = EnchantmentHelper.getEnchantments(var15);
         int var13 = 0 + var11.getRepairCost() + (var16 == null ? 0 : var16.getRepairCost());
         this.materialCost = 0;
         if (var16 != null) {
            boolean var18 = var16.getItem() == Items.enchanted_book && Items.enchanted_book.getEnchantments(var16).tagCount() > 0;
            if (var15.isItemStackDamageable() && var15.getItem().getIsRepairable(var11, var16)) {
               int var30 = Math.min(var15.getItemDamage(), var15.getMaxDamage() / 4);
               if (var30 <= 0) {
                  this.outputSlot.setInventorySlotContents(0, null);
                  this.maximumCost = 0;
                  return;
               }

               int var34;
               for (var34 = 0; var30 > 0 && var34 < var16.stackSize; var34++) {
                  int var36 = var15.getItemDamage() - var30;
                  var15.setItemDamage(var36);
                  var12++;
                  var30 = Math.min(var15.getItemDamage(), var15.getMaxDamage() / 4);
               }

               this.materialCost = var34;
            } else {
               if (!var18 && (var15.getItem() != var16.getItem() || !var15.isItemStackDamageable())) {
                  this.outputSlot.setInventorySlotContents(0, null);
                  this.maximumCost = 0;
                  return;
               }

               if (var15.isItemStackDamageable() && !var18) {
                  int var19 = var11.getMaxDamage() - var11.getItemDamage();
                  int var20 = var16.getMaxDamage() - var16.getItemDamage();
                  int var21 = var20 + var15.getMaxDamage() * 12 / 100;
                  int var22 = var19 + var21;
                  int var23 = var15.getMaxDamage() - var22;
                  if (var23 < 0) {
                     var23 = 0;
                  }

                  if (var23 < var15.getMetadata()) {
                     var15.setItemDamage(var23);
                     var12 += 2;
                  }
               }

               Map<Integer, Integer> var29 = EnchantmentHelper.getEnchantments(var16);

               for (int var35 : var29.keySet()) {
                  Enchantment var37 = Enchantment.getEnchantmentById(var35);
                  if (var37 != null) {
                     int var38 = var17.getOrDefault(var35, 0);
                     int var24 = (Integer)var29.get(var35);
                     int var25;
                     if (var38 == var24) {
                        var25 = ++var24;
                     } else {
                        var25 = Math.max(var24, var38);
                     }

                     var24 = var25;
                     boolean var26 = var37.canApply(var11);
                     if (this.thePlayer.capabilities.isCreativeMode || var11.getItem() == Items.enchanted_book) {
                        var26 = true;
                     }

                     for (int var28 : var17.keySet()) {
                        if (var28 != var35 && !var37.canApplyTogether(Enchantment.getEnchantmentById(var28))) {
                           var26 = false;
                           var12++;
                        }
                     }

                     if (var26) {
                        if (var24 > var37.getMaxLevel()) {
                           var24 = var37.getMaxLevel();
                        }

                        var17.put(var35, var24);
                        int var41 = 0;
                        switch (var37.getWeight()) {
                           case 1:
                              var41 = 8;
                              break;
                           case 2:
                              var41 = 4;
                           case 3:
                           case 4:
                           case 6:
                           case 7:
                           case 8:
                           case 9:
                           default:
                              break;
                           case 5:
                              var41 = 2;
                              break;
                           case 10:
                              var41 = 1;
                        }

                        if (var18) {
                           var41 = Math.max(1, var41 / 2);
                        }

                        var12 += var41 * var24;
                     }
                  }
               }
            }
         }

         if (StringUtils.isBlank(this.repairedItemName)) {
            if (var11.hasDisplayName()) {
               var14 = 1;
               var12++;
               var15.clearCustomName();
            }
         } else if (!this.repairedItemName.equals(var11.getDisplayName())) {
            var14 = 1;
            var12++;
            var15.setStackDisplayName(this.repairedItemName);
         }

         this.maximumCost = var13 + var12;
         if (var12 <= 0) {
            var15 = null;
         }

         if (var14 == var12 && var14 > 0 && this.maximumCost >= 40) {
            this.maximumCost = 39;
         }

         if (this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode) {
            var15 = null;
         }

         if (var15 != null) {
            int var31 = var15.getRepairCost();
            if (var16 != null && var31 < var16.getRepairCost()) {
               var31 = var16.getRepairCost();
            }

            var31 = var31 * 2 + 1;
            var15.setRepairCost(var31);
            EnchantmentHelper.setEnchantments(var17, var15);
         }

         this.outputSlot.setInventorySlotContents(0, var15);
         this.detectAndSendChanges();
      }
   }

   @Override
   public void onCraftGuiOpened(ICrafting iCrafting) {
      super.onCraftGuiOpened(iCrafting);
      iCrafting.sendProgressBarUpdate(this, 0, this.maximumCost);
   }

   @Override
   public void updateProgressBar(int integer1, int integer2) {
      if (integer1 == 0) {
         this.maximumCost = integer2;
      }
   }

   @Override
   public void onContainerClosed(EntityPlayer entityPlayer) {
      super.onContainerClosed(entityPlayer);
      if (!this.theWorld.isRemote) {
         for (int var5 = 0; var5 < this.inputSlots.getSizeInventory(); var5++) {
            ItemStack var6 = this.inputSlots.removeStackFromSlot(var5);
            if (var6 != null) {
               entityPlayer.dropPlayerItemWithRandomChoice(var6, false);
            }
         }
      }
   }

   @Override
   public boolean canInteractWith(EntityPlayer entityPlayer) {
      return this.theWorld.getBlockState(this.selfPosition).getBlock() == Blocks.anvil
         && entityPlayer.getDistanceSq(this.selfPosition.getX() + 0.5, this.selfPosition.getY() + 0.5, this.selfPosition.getZ() + 0.5) <= 64.0;
   }

   @Override
   public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int integer) {
      ItemStack var6 = null;
      Slot var7 = this.inventorySlots.get(integer);
      if (var7 != null && var7.getHasStack()) {
         ItemStack var8 = var7.getStack();
         var6 = var8.copy();
         if (integer == 2) {
            if (!this.mergeItemStack(var8, 3, 39, true)) {
               return null;
            }

            var7.onSlotChange(var8, var6);
         } else if (integer != 0 && integer != 1) {
            if (integer >= 3 && integer < 39 && !this.mergeItemStack(var8, 0, 2, false)) {
               return null;
            }
         } else if (!this.mergeItemStack(var8, 3, 39, false)) {
            return null;
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

   public void updateItemName(String string) {
      this.repairedItemName = string;
      if (this.getSlot(2).getHasStack()) {
         ItemStack var5 = this.getSlot(2).getStack();
         if (StringUtils.isBlank(string)) {
            var5.clearCustomName();
         } else {
            var5.setStackDisplayName(this.repairedItemName);
         }
      }

      this.updateRepairOutput();
   }
}
