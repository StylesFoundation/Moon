package net.minecraft.item;

import java.util.List;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemMonsterPlacer extends Item {
   public ItemMonsterPlacer() {
      this.setHasSubtypes(true);
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   @Override
   public String getItemStackDisplayName(ItemStack itemStack) {
      String var5 = (StatCollector.translateToLocal(this.getUnlocalizedName() + ".name") + "").trim();
      String var6 = EntityList.getStringFromID(itemStack.getMetadata());
      if (var6 != null) {
         var5 = var5 + " " + StatCollector.translateToLocal("entity." + var6 + ".name");
      }

      return var5;
   }

   @Override
   public int getColorFromItemStack(ItemStack itemStack, int integer) {
      EntityList.EntityEggInfo var6 = EntityList.entityEggs.get(itemStack.getMetadata());
      return var6 != null ? (integer == 0 ? var6.primaryColor : var6.secondaryColor) : 16777215;
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (world.isRemote) {
         return true;
      }

      if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
         return false;
      }

      IBlockState var12 = world.getBlockState(blockPos);
      if (var12.getBlock() == Blocks.mob_spawner) {
         TileEntity var13 = world.getTileEntity(blockPos);
         if (var13 instanceof TileEntityMobSpawner) {
            MobSpawnerBaseLogic var14 = ((TileEntityMobSpawner)var13).getSpawnerBaseLogic();
            var14.setEntityName(EntityList.getStringFromID(itemStack.getMetadata()));
            var13.markDirty();
            world.markBlockForUpdate(blockPos);
            if (!entityPlayer.capabilities.isCreativeMode) {
               itemStack.stackSize--;
            }

            return true;
         }
      }

      blockPos = blockPos.offset(enumFacing);
      double var17 = 0.0;
      if (enumFacing == EnumFacing.UP && var12 instanceof BlockFence) {
         var17 = 0.5;
      }

      Entity var15 = spawnCreature(world, itemStack.getMetadata(), blockPos.getX() + 0.5, blockPos.getY() + var17, blockPos.getZ() + 0.5);
      if (var15 != null) {
         if (var15 instanceof EntityLivingBase && itemStack.hasDisplayName()) {
            var15.setCustomNameTag(itemStack.getDisplayName());
         }

         if (!entityPlayer.capabilities.isCreativeMode) {
            itemStack.stackSize--;
         }
      }

      return true;
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if (!world.isRemote) {
         MovingObjectPosition var7 = this.getMovingObjectPositionFromPlayer(world, entityPlayer, true);
         if (var7 != null && var7.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos var8 = var7.getBlockPos();
            if (!world.isBlockModifiable(entityPlayer, var8)) {
               return itemStack;
            }

            if (!entityPlayer.canPlayerEdit(var8, var7.sideHit, itemStack)) {
               return itemStack;
            }

            if (world.getBlockState(var8).getBlock() instanceof BlockLiquid) {
               Entity var9 = spawnCreature(world, itemStack.getMetadata(), var8.getX() + 0.5, var8.getY() + 0.5, var8.getZ() + 0.5);
               if (var9 != null) {
                  if (var9 instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                     var9.setCustomNameTag(itemStack.getDisplayName());
                  }

                  if (!entityPlayer.capabilities.isCreativeMode) {
                     itemStack.stackSize--;
                  }

                  entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
               }
            }
         }
      }

      return itemStack;
   }

   public static Entity spawnCreature(World world, int integer, double double3, double double4, double double5) {
      if (!EntityList.entityEggs.containsKey(integer)) {
         return null;
      }

      Entity var11 = null;

      for (int var12 = 0; var12 < 1; var12++) {
         var11 = EntityList.Method512(integer, world);
         if (var11 instanceof EntityLivingBase) {
            EntityLiving var13 = (EntityLiving)var11;
            var11.setLocationAndAngles(double3, double4, double5, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
            var13.rotationYawHead = var13.rotationYaw;
            var13.renderYawOffset = var13.rotationYaw;
            var13.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(var13)), null);
            world.spawnEntityInWorld(var11);
            var13.playLivingSound();
         }
      }

      return var11;
   }

   @Override
   public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (EntityList.EntityEggInfo var8 : EntityList.entityEggs.values()) {
         list.add(new ItemStack(item, 1, var8.spawnedID));
      }
   }
}
