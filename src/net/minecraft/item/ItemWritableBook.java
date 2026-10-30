package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemWritableBook extends Item {
   public ItemWritableBook() {
      this.setMaxStackSize(1);
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      entityPlayer.displayGUIBook(itemStack);
      entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
      return itemStack;
   }

   public static boolean isNBTValid(NBTTagCompound nBTTagCompound) {
      if (nBTTagCompound == null) {
         return false;
      }

      if (!nBTTagCompound.hasKey("pages", 9)) {
         return false;
      }

      NBTTagList var4 = nBTTagCompound.getTagList("pages", 8);

      for (int var5 = 0; var5 < var4.tagCount(); var5++) {
         String var6 = var4.getStringTagAt(var5);
         if (var6 == null) {
            return false;
         }

         if (var6.length() > 32767) {
            return false;
         }
      }

      return true;
   }
}
