package net.minecraft.inventory;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class InventoryHelper {
   private static final Random RANDOM = new Random();

   public static void dropInventoryItems(World world, BlockPos blockPos, IInventory iInventory) {
      dropInventoryItems(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), iInventory);
   }

   public static void dropInventoryItems(World world, Entity entity, IInventory iInventory) {
      dropInventoryItems(world, entity.posX, entity.posY, entity.posZ, iInventory);
   }

   private static void dropInventoryItems(World world, double double2, double double3, double double4, IInventory iInventory) {
      for (int var11 = 0; var11 < iInventory.getSizeInventory(); var11++) {
         ItemStack var12 = iInventory.getStackInSlot(var11);
         if (var12 != null) {
            spawnItemStack(world, double2, double3, double4, var12);
         }
      }
   }

   private static void spawnItemStack(World world, double double2, double double3, double double4, ItemStack itemStack) {
      float var11 = RANDOM.nextFloat() * 0.8F + 0.1F;
      float var12 = RANDOM.nextFloat() * 0.8F + 0.1F;
      float var13 = RANDOM.nextFloat() * 0.8F + 0.1F;

      while (itemStack.stackSize > 0) {
         int var14 = RANDOM.nextInt(21) + 10;
         if (var14 > itemStack.stackSize) {
            var14 = itemStack.stackSize;
         }

         itemStack.stackSize -= var14;
         EntityItem var15 = new EntityItem(
            world, double2 + var11, double3 + var12, double4 + var13, new ItemStack(itemStack.getItem(), var14, itemStack.getMetadata())
         );
         if (itemStack.hasTagCompound()) {
            var15.Method464().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
         }

         var15.motionX = RANDOM.nextGaussian() * 0.05F;
         var15.motionY = RANDOM.nextGaussian() * 0.05F + 0.2F;
         var15.motionZ = RANDOM.nextGaussian() * 0.05F;
         world.spawnEntityInWorld(var15);
      }
   }
}
