package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.StatCollector;

public class ItemFireworkCharge extends Item {
   @Override
   public int getColorFromItemStack(ItemStack itemStack, int integer) {
      if (integer != 1) {
         return super.getColorFromItemStack(itemStack, integer);
      } else if (!(getExplosionTag(itemStack, "Colors") instanceof NBTTagIntArray var7)) {
         return 9079434;
      } else {
         int[] var8 = var7.getIntArray();
         if (var8.length == 1) {
            return var8[0];
         }

         int var9 = 0;
         int var10 = 0;
         int var11 = 0;

         for (int var15 : var8) {
            var9 += (var15 & 0xFF0000) >> 16;
            var10 += (var15 & 0xFF00) >> 8;
            var11 += var15 & 0xFF;
         }

         var9 /= var8.length;
         var10 /= var8.length;
         var11 /= var8.length;
         return var9 << 16 | var10 << 8 | var11;
      }
   }

   public static NBTBase getExplosionTag(ItemStack itemStack, String string) {
      if (itemStack.hasTagCompound()) {
         NBTTagCompound var5 = itemStack.getTagCompound().getCompoundTag("Explosion");
         if (var5 != null) {
            return var5.getTag(string);
         }
      }

      return null;
   }

   @Override
   public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean boolean4) {
      if (itemStack.hasTagCompound()) {
         NBTTagCompound var8 = itemStack.getTagCompound().getCompoundTag("Explosion");
         if (var8 != null) {
            addExplosionInfo(var8, list);
         }
      }
   }

   public static void addExplosionInfo(NBTTagCompound nBTTagCompound, List<String> list) {
      byte var5 = nBTTagCompound.getByte("Type");
      if (var5 >= 0 && var5 <= 4) {
         list.add(StatCollector.translateToLocal("item.fireworksCharge.type." + var5).trim());
      } else {
         list.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
      }

      int[] var6 = nBTTagCompound.getIntArray("Colors");
      if (var6.length > 0) {
         boolean var7 = true;
         StringBuilder var8 = new StringBuilder();

         for (int var12 : var6) {
            if (!var7) {
               var8.append(", ");
            }

            var7 = false;
            boolean var13 = false;

            for (int var14 = 0; var14 < ItemDye.dyeColors.length; var14++) {
               if (var12 == ItemDye.dyeColors[var14]) {
                  var13 = true;
                  var8.append(StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(var14).getUnlocalizedName()));
                  break;
               }
            }

            if (!var13) {
               var8.append(StatCollector.translateToLocal("item.fireworksCharge.customColor"));
            }
         }

         list.add(var8.toString());
      }

      int[] var16 = nBTTagCompound.getIntArray("FadeColors");
      if (var16.length > 0) {
         boolean var17 = true;
         StringBuilder var19 = new StringBuilder(StatCollector.translateToLocal("item.fireworksCharge.fadeTo") + " ");

         for (int var24 : var16) {
            if (!var17) {
               var19.append(", ");
            }

            var17 = false;
            boolean var25 = false;

            for (int var15 = 0; var15 < 16; var15++) {
               if (var24 == ItemDye.dyeColors[var15]) {
                  var25 = true;
                  var19.append(StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(var15).getUnlocalizedName()));
                  break;
               }
            }

            if (!var25) {
               var19.append(StatCollector.translateToLocal("item.fireworksCharge.customColor"));
            }
         }

         list.add(var19.toString());
      }

      boolean var18 = nBTTagCompound.getBoolean("Trail");
      if (var18) {
         list.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
      }

      boolean var20 = nBTTagCompound.getBoolean("Flicker");
      if (var20) {
         list.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
      }
   }
}
