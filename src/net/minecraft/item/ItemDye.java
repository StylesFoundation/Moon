package net.minecraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemDye extends Item {
   public static final int[] dyeColors = new int[]{
      1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320
   };

   public ItemDye() {
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
      this.setCreativeTab(CreativeTabs.tabMaterials);
   }

   @Override
   public String getUnlocalizedName(ItemStack itemStack) {
      int var2 = itemStack.getMetadata();
      return super.getUnlocalizedName() + "." + EnumDyeColor.byDyeDamage(var2).getUnlocalizedName();
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
         return false;
      }

      EnumDyeColor var12 = EnumDyeColor.byDyeDamage(itemStack.getMetadata());
      if (var12 == EnumDyeColor.WHITE) {
         if (applyBonemeal(itemStack, world, blockPos)) {
            if (!world.isRemote) {
               world.playAuxSFX(2005, blockPos, 0);
            }

            return true;
         }
      } else if (var12 == EnumDyeColor.BROWN) {
         IBlockState var13 = world.getBlockState(blockPos);
         Block var14 = var13.getBlock();
         if (var14 == Blocks.log && var13.getValue(BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE) {
            if (enumFacing == EnumFacing.DOWN) {
               return false;
            }

            if (enumFacing == EnumFacing.UP) {
               return false;
            }

            blockPos = blockPos.offset(enumFacing);
            if (world.isAirBlock(blockPos)) {
               IBlockState var15 = Blocks.cocoa.onBlockPlaced(world, blockPos, enumFacing, float6, float7, float8, 0, entityPlayer);
               world.setBlockState(blockPos, var15, 2);
               if (!entityPlayer.capabilities.isCreativeMode) {
                  itemStack.stackSize--;
               }
            }

            return true;
         }
      }

      return false;
   }

   public static boolean applyBonemeal(ItemStack itemStack, World world, BlockPos blockPos) {
      IBlockState var6 = world.getBlockState(blockPos);
      if (var6.getBlock() instanceof IGrowable var7 && var7.canGrow(world, blockPos, var6, world.isRemote)) {
         if (!world.isRemote) {
            if (var7.canUseBonemeal(world, world.rand, blockPos, var6)) {
               var7.grow(world, world.rand, blockPos, var6);
            }

            itemStack.stackSize--;
         }

         return true;
      } else {
         return false;
      }
   }

   public static void spawnBonemealParticles(World world, BlockPos blockPos, int integer) {
      if (integer == 0) {
         integer = 15;
      }

      Block var6 = world.getBlockState(blockPos).getBlock();
      if (var6.getMaterial() != Material.air) {
         var6.setBlockBoundsBasedOnState(world, blockPos);

         for (int var7 = 0; var7 < integer; var7++) {
            double var8 = itemRand.nextGaussian() * 0.02;
            double var10 = itemRand.nextGaussian() * 0.02;
            double var12 = itemRand.nextGaussian() * 0.02;
            world.spawnParticle(
               EnumParticleTypes.VILLAGER_HAPPY,
               blockPos.getX() + itemRand.nextFloat(),
               blockPos.getY() + itemRand.nextFloat() * var6.getBlockBoundsMaxY(),
               blockPos.getZ() + itemRand.nextFloat(),
               var8,
               var10,
               var12
            );
         }
      }
   }

   @Override
   public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer entityPlayer, EntityLivingBase entityLivingBase) {
      if (entityLivingBase instanceof EntitySheep var7) {
         EnumDyeColor var8 = EnumDyeColor.byDyeDamage(itemStack.getMetadata());
         if (!var7.getSheared() && var7.getFleeceColor() != var8) {
            var7.setFleeceColor(var8);
            itemStack.stackSize--;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (int var7 = 0; var7 < 16; var7++) {
         list.add(new ItemStack(item, 1, var7));
      }
   }
}
