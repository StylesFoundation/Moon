package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCauldron extends Block {
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);

   public BlockCauldron() {
      super(Material.iron, MapColor.stoneColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0));
   }

   @Override
   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBoundsForItemRender();
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity) {
      int var8 = iBlockState.getValue(LEVEL);
      float var9 = blockPos.getY() + (6.0F + 3 * var8) / 16.0F;
      if (!world.isRemote && entity.isBurning() && var8 > 0 && entity.getEntityBoundingBox().minY <= var9) {
         entity.extinguish();
         this.setWaterLevel(world, blockPos, iBlockState, var8 - 1);
      }
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (world.isRemote) {
         return true;
      }

      ItemStack var12 = entityPlayer.inventory.getCurrentItem();
      if (var12 == null) {
         return true;
      }

      int var13 = iBlockState.getValue(LEVEL);
      Item var14 = var12.getItem();
      if (var14 == Items.water_bucket) {
         if (var13 < 3) {
            if (!entityPlayer.capabilities.isCreativeMode) {
               entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, new ItemStack(Items.bucket));
            }

            entityPlayer.triggerAchievement(StatList.field_181725_I);
            this.setWaterLevel(world, blockPos, iBlockState, 3);
         }

         return true;
      } else if (var14 == Items.glass_bottle) {
         if (var13 > 0) {
            if (!entityPlayer.capabilities.isCreativeMode) {
               ItemStack var17 = new ItemStack(Items.potionitem, 1, 0);
               if (!entityPlayer.inventory.addItemStackToInventory(var17)) {
                  world.spawnEntityInWorld(new EntityItem(world, blockPos.getX() + 0.5, blockPos.getY() + 1.5, blockPos.getZ() + 0.5, var17));
               } else if (entityPlayer instanceof EntityPlayerMP) {
                  ((EntityPlayerMP)entityPlayer).sendContainerToPlayer(entityPlayer.inventoryContainer);
               }

               entityPlayer.triggerAchievement(StatList.field_181726_J);
               var12.stackSize--;
               if (var12.stackSize <= 0) {
                  entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
               }
            }

            this.setWaterLevel(world, blockPos, iBlockState, var13 - 1);
         }

         return true;
      } else if (var13 > 0 && var14 instanceof ItemArmor var15 && var15.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && var15.hasColor(var12)) {
         var15.removeColor(var12);
         this.setWaterLevel(world, blockPos, iBlockState, var13 - 1);
         entityPlayer.triggerAchievement(StatList.field_181727_K);
         return true;
      } else if (var13 > 0 && var14 instanceof ItemBanner && TileEntityBanner.getPatterns(var12) > 0) {
         ItemStack var16 = var12.copy();
         var16.stackSize = 1;
         TileEntityBanner.removeBannerData(var16);
         if (var12.stackSize <= 1 && !entityPlayer.capabilities.isCreativeMode) {
            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, var16);
         } else {
            if (!entityPlayer.inventory.addItemStackToInventory(var16)) {
               world.spawnEntityInWorld(new EntityItem(world, blockPos.getX() + 0.5, blockPos.getY() + 1.5, blockPos.getZ() + 0.5, var16));
            } else if (entityPlayer instanceof EntityPlayerMP) {
               ((EntityPlayerMP)entityPlayer).sendContainerToPlayer(entityPlayer.inventoryContainer);
            }

            entityPlayer.triggerAchievement(StatList.field_181728_L);
            if (!entityPlayer.capabilities.isCreativeMode) {
               var12.stackSize--;
            }
         }

         if (!entityPlayer.capabilities.isCreativeMode) {
            this.setWaterLevel(world, blockPos, iBlockState, var13 - 1);
         }

         return true;
      } else {
         return false;
      }
   }

   public void setWaterLevel(World world, BlockPos blockPos, IBlockState iBlockState, int integer) {
      world.setBlockState(blockPos, iBlockState.withProperty(LEVEL, MathHelper.clamp_int(integer, 0, 3)), 2);
      world.updateComparatorOutputLevel(blockPos, this);
   }

   @Override
   public void fillWithRain(World world, BlockPos blockPos) {
      if (world.rand.nextInt(20) == 1) {
         IBlockState var6 = world.getBlockState(blockPos);
         if (var6.getValue(LEVEL) < 3) {
            world.setBlockState(blockPos, var6.cycleProperty(LEVEL), 2);
         }
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.cauldron;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.cauldron;
   }

   @Override
   public boolean hasComparatorInputOverride() {
      return true;
   }

   @Override
   public int getComparatorInputOverride(World world, BlockPos blockPos) {
      return world.getBlockState(blockPos).getValue(LEVEL);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(LEVEL, integer);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(LEVEL);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, LEVEL);
   }
}
