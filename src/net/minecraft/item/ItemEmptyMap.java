package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class ItemEmptyMap extends ItemMapBase {
   protected ItemEmptyMap() {
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      ItemStack var7 = new ItemStack(Items.filled_map, 1, world.getUniqueDataId("map"));
      String var8 = "map_" + var7.getMetadata();
      MapData var9 = new MapData(var8);
      world.setItemData(var8, var9);
      var9.scale = 0;
      var9.calculateMapCenter(entityPlayer.posX, entityPlayer.posZ, var9.scale);
      var9.dimension = (byte)world.provider.getDimensionId();
      var9.markDirty();
      itemStack.stackSize--;
      if (itemStack.stackSize <= 0) {
         return var7;
      }

      if (!entityPlayer.inventory.addItemStackToInventory(var7.copy())) {
         entityPlayer.dropPlayerItemWithRandomChoice(var7, false);
      }

      entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
      return itemStack;
   }
}
