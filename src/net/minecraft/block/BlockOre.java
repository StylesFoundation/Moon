package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockOre extends Block {
   public BlockOre() {
      this(Material.rock.getMaterialMapColor());
   }

   public BlockOre(MapColor mapColor) {
      super(Material.rock, mapColor);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return this == Blocks.coal_ore
         ? Items.coal
         : (
            this == Blocks.diamond_ore
               ? Items.diamond
               : (
                  this == Blocks.lapis_ore
                     ? Items.dye
                     : (this == Blocks.emerald_ore ? Items.emerald : (this == Blocks.quartz_ore ? Items.quartz : Item.getItemFromBlock(this)))
               )
         );
   }

   @Override
   public int quantityDropped(Random random) {
      return this == Blocks.lapis_ore ? 4 + random.nextInt(5) : 1;
   }

   @Override
   public int quantityDroppedWithBonus(int integer, Random random) {
      if (integer > 0
         && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), random, integer)) {
         int var6 = random.nextInt(integer + 2) - 1;
         if (var6 < 0) {
            var6 = 0;
         }

         return this.quantityDropped(random) * (var6 + 1);
      } else {
         return this.quantityDropped(random);
      }
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      super.dropBlockAsItemWithChance(world, blockPos, iBlockState, float4, integer);
      if (this.getItemDropped(iBlockState, world.rand, integer) != Item.getItemFromBlock(this)) {
         int var9 = 0;
         if (this == Blocks.coal_ore) {
            var9 = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
         } else if (this == Blocks.diamond_ore) {
            var9 = MathHelper.getRandomIntegerInRange(world.rand, 3, 7);
         } else if (this == Blocks.emerald_ore) {
            var9 = MathHelper.getRandomIntegerInRange(world.rand, 3, 7);
         } else if (this == Blocks.lapis_ore) {
            var9 = MathHelper.getRandomIntegerInRange(world.rand, 2, 5);
         } else if (this == Blocks.quartz_ore) {
            var9 = MathHelper.getRandomIntegerInRange(world.rand, 2, 5);
         }

         this.dropXpOnBlockBreak(world, blockPos, var9);
      }
   }

   @Override
   public int getDamageValue(World world, BlockPos blockPos) {
      return 0;
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return this == Blocks.lapis_ore ? EnumDyeColor.BLUE.getDyeDamage() : 0;
   }
}
