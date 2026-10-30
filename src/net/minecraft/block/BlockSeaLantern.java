package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

public class BlockSeaLantern extends Block {
   public BlockSeaLantern(Material material) {
      super(material);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public int quantityDropped(Random random) {
      return 2 + random.nextInt(2);
   }

   @Override
   public int quantityDroppedWithBonus(int integer, Random random) {
      return MathHelper.clamp_int(this.quantityDropped(random) + random.nextInt(integer + 1), 1, 5);
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.prismarine_crystals;
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return MapColor.quartzColor;
   }

   @Override
   protected boolean canSilkHarvest() {
      return true;
   }
}
