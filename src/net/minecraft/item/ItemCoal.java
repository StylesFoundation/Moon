package net.minecraft.item;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;

public class ItemCoal extends Item {
   public ItemCoal() {
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
      this.setCreativeTab(CreativeTabs.tabMaterials);
   }

   @Override
   public String getUnlocalizedName(ItemStack itemStack) {
      return itemStack.getMetadata() == 1 ? "item.charcoal" : "item.coal";
   }

   @Override
   public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      list.add(new ItemStack(item, 1, 0));
      list.add(new ItemStack(item, 1, 1));
   }
}
