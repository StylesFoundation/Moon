package net.minecraft.village;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MerchantRecipe {
   private ItemStack itemToBuy;
   private ItemStack secondItemToBuy;
   private ItemStack itemToSell;
   private int toolUses;
   private int maxTradeUses;
   private boolean rewardsExp;

   public MerchantRecipe(NBTTagCompound nBTTagCompound) {
      this.readFromTags(nBTTagCompound);
   }

   public MerchantRecipe(ItemStack itemStack1, ItemStack itemStack2, ItemStack itemStack3) {
      this(itemStack1, itemStack2, itemStack3, 0, 7);
   }

   public MerchantRecipe(ItemStack itemStack1, ItemStack itemStack2, ItemStack itemStack3, int integer4, int integer5) {
      this.itemToBuy = itemStack1;
      this.secondItemToBuy = itemStack2;
      this.itemToSell = itemStack3;
      this.toolUses = integer4;
      this.maxTradeUses = integer5;
      this.rewardsExp = true;
   }

   public MerchantRecipe(ItemStack itemStack1, ItemStack itemStack2) {
      this(itemStack1, null, itemStack2);
   }

   public MerchantRecipe(ItemStack itemStack, Item item) {
      this(itemStack, new ItemStack(item));
   }

   public ItemStack getItemToBuy() {
      return this.itemToBuy;
   }

   public ItemStack getSecondItemToBuy() {
      return this.secondItemToBuy;
   }

   public boolean hasSecondItemToBuy() {
      return this.secondItemToBuy != null;
   }

   public ItemStack getItemToSell() {
      return this.itemToSell;
   }

   public int getToolUses() {
      return this.toolUses;
   }

   public int getMaxTradeUses() {
      return this.maxTradeUses;
   }

   public void incrementToolUses() {
      this.toolUses++;
   }

   public void increaseMaxTradeUses(int integer) {
      this.maxTradeUses += integer;
   }

   public boolean isRecipeDisabled() {
      return this.toolUses >= this.maxTradeUses;
   }

   public void compensateToolUses() {
      this.toolUses = this.maxTradeUses;
   }

   public boolean getRewardsExp() {
      return this.rewardsExp;
   }

   public void readFromTags(NBTTagCompound nBTTagCompound) {
      NBTTagCompound var5 = nBTTagCompound.getCompoundTag("buy");
      this.itemToBuy = ItemStack.loadItemStackFromNBT(var5);
      NBTTagCompound var6 = nBTTagCompound.getCompoundTag("sell");
      this.itemToSell = ItemStack.loadItemStackFromNBT(var6);
      if (nBTTagCompound.hasKey("buyB", 10)) {
         this.secondItemToBuy = ItemStack.loadItemStackFromNBT(nBTTagCompound.getCompoundTag("buyB"));
      }

      if (nBTTagCompound.hasKey("uses", 99)) {
         this.toolUses = nBTTagCompound.getInteger("uses");
      }

      if (nBTTagCompound.hasKey("maxUses", 99)) {
         this.maxTradeUses = nBTTagCompound.getInteger("maxUses");
      } else {
         this.maxTradeUses = 7;
      }

      if (nBTTagCompound.hasKey("rewardExp", 1)) {
         this.rewardsExp = nBTTagCompound.getBoolean("rewardExp");
      } else {
         this.rewardsExp = true;
      }
   }

   public NBTTagCompound writeToTags() {
      NBTTagCompound var4 = new NBTTagCompound();
      var4.setTag("buy", this.itemToBuy.writeToNBT(new NBTTagCompound()));
      var4.setTag("sell", this.itemToSell.writeToNBT(new NBTTagCompound()));
      if (this.secondItemToBuy != null) {
         var4.setTag("buyB", this.secondItemToBuy.writeToNBT(new NBTTagCompound()));
      }

      var4.setInteger("uses", this.toolUses);
      var4.setInteger("maxUses", this.maxTradeUses);
      var4.setBoolean("rewardExp", this.rewardsExp);
      return var4;
   }
}
