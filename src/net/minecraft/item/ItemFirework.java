package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemFirework extends Item {
   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote) {
         EntityFireworkRocket var12 = new EntityFireworkRocket(world, blockPos.getX() + float6, blockPos.getY() + float7, blockPos.getZ() + float8, itemStack);
         world.spawnEntityInWorld(var12);
         if (!entityPlayer.capabilities.isCreativeMode) {
            itemStack.stackSize--;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean boolean4) {
      if (itemStack.hasTagCompound()) {
         NBTTagCompound var8 = itemStack.getTagCompound().getCompoundTag("Fireworks");
         if (var8 != null) {
            if (var8.hasKey("Flight", 99)) {
               list.add(StatCollector.translateToLocal("item.fireworks.flight") + " " + var8.getByte("Flight"));
            }

            NBTTagList var9 = var8.getTagList("Explosions", 10);
            if (var9 != null && var9.tagCount() > 0) {
               for (int var10 = 0; var10 < var9.tagCount(); var10++) {
                  NBTTagCompound var11 = var9.getCompoundTagAt(var10);
                  ArrayList var12 = Lists.newArrayList();
                  ItemFireworkCharge.addExplosionInfo(var11, var12);
                  if (var12.size() > 0) {
                     for (int var13 = 1; var13 < var12.size(); var13++) {
                        var12.set(var13, "  " + (String)var12.get(var13));
                     }

                     list.addAll(var12);
                  }
               }
            }
         }
      }
   }
}
