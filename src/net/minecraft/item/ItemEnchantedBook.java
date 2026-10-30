package net.minecraft.item;

import java.util.List;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemEnchantedBook extends Item {
   @Override
   public boolean hasEffect(ItemStack itemStack) {
      return true;
   }

   @Override
   public boolean isItemTool(ItemStack itemStack) {
      return false;
   }

   @Override
   public EnumRarity getRarity(ItemStack itemStack) {
      return this.getEnchantments(itemStack).tagCount() > 0 ? EnumRarity.UNCOMMON : super.getRarity(itemStack);
   }

   public NBTTagList getEnchantments(ItemStack itemStack) {
      NBTTagCompound var5 = itemStack.getTagCompound();
      return var5 != null && var5.hasKey("StoredEnchantments", 9) ? (NBTTagList)var5.getTag("StoredEnchantments") : new NBTTagList();
   }

   @Override
   public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean boolean4) {
      super.addInformation(itemStack, entityPlayer, list, boolean4);
      NBTTagList var8 = this.getEnchantments(itemStack);
      if (var8 != null) {
         for (int var9 = 0; var9 < var8.tagCount(); var9++) {
            short var10 = var8.getCompoundTagAt(var9).getShort("id");
            short var11 = var8.getCompoundTagAt(var9).getShort("lvl");
            if (Enchantment.getEnchantmentById(var10) != null) {
               list.add(Enchantment.getEnchantmentById(var10).Method532(var11));
            }
         }
      }
   }

   public void addEnchantment(ItemStack itemStack, EnchantmentData enchantmentData) {
      NBTTagList var6 = this.getEnchantments(itemStack);
      boolean var7 = true;

      for (int var8 = 0; var8 < var6.tagCount(); var8++) {
         NBTTagCompound var9 = var6.getCompoundTagAt(var8);
         if (var9.getShort("id") == enchantmentData.enchantmentobj.effectId) {
            if (var9.getShort("lvl") < enchantmentData.enchantmentLevel) {
               var9.setShort("lvl", (short)enchantmentData.enchantmentLevel);
            }

            var7 = false;
            break;
         }
      }

      if (var7) {
         NBTTagCompound var10 = new NBTTagCompound();
         var10.setShort("id", (short)enchantmentData.enchantmentobj.effectId);
         var10.setShort("lvl", (short)enchantmentData.enchantmentLevel);
         var6.appendTag(var10);
      }

      if (!itemStack.hasTagCompound()) {
         itemStack.setTagCompound(new NBTTagCompound());
      }

      itemStack.getTagCompound().setTag("StoredEnchantments", var6);
   }

   public ItemStack getEnchantedItemStack(EnchantmentData enchantmentData) {
      ItemStack var2 = new ItemStack(this);
      this.addEnchantment(var2, enchantmentData);
      return var2;
   }

   public void getAll(Enchantment enchantment, List<ItemStack> list) {
      for (int var6 = enchantment.getMinLevel(); var6 <= enchantment.getMaxLevel(); var6++) {
         list.add(this.getEnchantedItemStack(new EnchantmentData(enchantment, var6)));
      }
   }

   public WeightedRandomChestContent getRandom(Random random) {
      return this.getRandom(random, 1, 1, 1);
   }

   public WeightedRandomChestContent getRandom(Random random, int integer2, int integer3, int integer4) {
      ItemStack var7 = new ItemStack(Items.book, 1, 0);
      EnchantmentHelper.addRandomEnchantment(random, var7, 30);
      return new WeightedRandomChestContent(var7, integer2, integer3, integer4);
   }
}
