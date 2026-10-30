package net.minecraft.village;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;

public class MerchantRecipeList extends ArrayList<MerchantRecipe> {
   public MerchantRecipeList() {
   }

   public MerchantRecipeList(NBTTagCompound nBTTagCompound) {
      this.readRecipiesFromTags(nBTTagCompound);
   }

   public MerchantRecipe canRecipeBeUsed(ItemStack itemStack1, ItemStack itemStack2, int integer) {
      if (integer > 0 && integer < this.size()) {
         MerchantRecipe var9 = this.get(integer);
         return this.func_181078_a(itemStack1, var9.getItemToBuy())
               && (itemStack2 == null && !var9.hasSecondItemToBuy() || var9.hasSecondItemToBuy() && this.func_181078_a(itemStack2, var9.getSecondItemToBuy()))
               && itemStack1.stackSize >= var9.getItemToBuy().stackSize
               && (!var9.hasSecondItemToBuy() || itemStack2.stackSize >= var9.getSecondItemToBuy().stackSize)
            ? var9
            : null;
      }

      for (int var7 = 0; var7 < this.size(); var7++) {
         MerchantRecipe var8 = this.get(var7);
         if (this.func_181078_a(itemStack1, var8.getItemToBuy())
            && itemStack1.stackSize >= var8.getItemToBuy().stackSize
            && (
               !var8.hasSecondItemToBuy() && itemStack2 == null
                  || var8.hasSecondItemToBuy()
                     && this.func_181078_a(itemStack2, var8.getSecondItemToBuy())
                     && itemStack2.stackSize >= var8.getSecondItemToBuy().stackSize
            )) {
            return var8;
         }
      }

      return null;
   }

   private boolean func_181078_a(ItemStack itemStack1, ItemStack itemStack2) {
      return ItemStack.areItemsEqual(itemStack1, itemStack2)
         && (
            !itemStack2.hasTagCompound()
               || itemStack1.hasTagCompound() && NBTUtil.func_181123_a(itemStack2.getTagCompound(), itemStack1.getTagCompound(), false)
         );
   }

   public void writeToBuf(PacketBuffer packetBuffer) {
      packetBuffer.writeByte((byte)(this.size() & 0xFF));

      for (MerchantRecipe var6 : this) {
         packetBuffer.writeItemStackToBuffer(var6.getItemToBuy());
         packetBuffer.writeItemStackToBuffer(var6.getItemToSell());
         ItemStack var7 = var6.getSecondItemToBuy();
         packetBuffer.writeBoolean(var7 != null);
         if (var7 != null) {
            packetBuffer.writeItemStackToBuffer(var7);
         }

         packetBuffer.writeBoolean(var6.isRecipeDisabled());
         packetBuffer.writeInt(var6.getToolUses());
         packetBuffer.writeInt(var6.getMaxTradeUses());
      }
   }

   public static MerchantRecipeList readFromBuf(PacketBuffer packetBuffer) throws IOException {
      MerchantRecipeList var4 = new MerchantRecipeList();
      int var5 = packetBuffer.readByte() & 255;

      for (int var6 = 0; var6 < var5; var6++) {
         ItemStack var7 = packetBuffer.readItemStackFromBuffer();
         ItemStack var8 = packetBuffer.readItemStackFromBuffer();
         ItemStack var9 = null;
         if (packetBuffer.readBoolean()) {
            var9 = packetBuffer.readItemStackFromBuffer();
         }

         boolean var10 = packetBuffer.readBoolean();
         int var11 = packetBuffer.readInt();
         int var12 = packetBuffer.readInt();
         MerchantRecipe var13 = new MerchantRecipe(var7, var9, var8, var11, var12);
         if (var10) {
            var13.compensateToolUses();
         }

         var4.add(var13);
      }

      return var4;
   }

   public void readRecipiesFromTags(NBTTagCompound nBTTagCompound) {
      NBTTagList var5 = nBTTagCompound.getTagList("Recipes", 10);

      for (int var6 = 0; var6 < var5.tagCount(); var6++) {
         NBTTagCompound var7 = var5.getCompoundTagAt(var6);
         this.add(new MerchantRecipe(var7));
      }
   }

   public NBTTagCompound getRecipiesAsTags() {
      NBTTagCompound var4 = new NBTTagCompound();
      NBTTagList var5 = new NBTTagList();

      for (MerchantRecipe var7 : this) {
         var5.appendTag(var7.writeToTags());
      }

      var4.setTag("Recipes", var5);
      return var4;
   }
}
