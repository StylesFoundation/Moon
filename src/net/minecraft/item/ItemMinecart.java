package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemMinecart extends Item {
   private static final IBehaviorDispenseItem dispenserMinecartBehavior = new BehaviorDefaultDispenseItem() {
      private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();

      @Override
      public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
         EnumFacing var6 = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
         World var7 = iBlockSource.getWorld();
         double var8 = iBlockSource.getX() + var6.getFrontOffsetX() * 1.125;
         double var10 = Math.floor(iBlockSource.getY()) + var6.getFrontOffsetY();
         double var12 = iBlockSource.getZ() + var6.getFrontOffsetZ() * 1.125;
         BlockPos var14 = iBlockSource.getBlockPos().offset(var6);
         IBlockState var15 = var7.getBlockState(var14);
         BlockRailBase.EnumRailDirection var16 = var15.getBlock() instanceof BlockRailBase
            ? var15.getValue(((BlockRailBase)var15.getBlock()).getShapeProperty())
            : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         double var17;
         if (BlockRailBase.isRailBlock(var15)) {
            if (var16.isAscending()) {
               var17 = 0.6;
            } else {
               var17 = 0.1;
            }
         } else {
            if (var15.getBlock().getMaterial() != Material.air || !BlockRailBase.isRailBlock(var7.getBlockState(var14.down()))) {
               return this.behaviourDefaultDispenseItem.dispense(iBlockSource, itemStack);
            }

            IBlockState var19 = var7.getBlockState(var14.down());
            BlockRailBase.EnumRailDirection var20 = var19.getBlock() instanceof BlockRailBase
               ? var19.getValue(((BlockRailBase)var19.getBlock()).getShapeProperty())
               : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            if (var6 != EnumFacing.DOWN && var20.isAscending()) {
               var17 = -0.4;
            } else {
               var17 = -0.9;
            }
         }

         EntityMinecart var21 = EntityMinecart.getMinecart(var7, var8, var10 + var17, var12, ((ItemMinecart)itemStack.getItem()).minecartType);
         if (itemStack.hasDisplayName()) {
            var21.setCustomNameTag(itemStack.getDisplayName());
         }

         var7.spawnEntityInWorld(var21);
         itemStack.splitStack(1);
         return itemStack;
      }

      @Override
      protected void playDispenseSound(IBlockSource iBlockSource) {
         iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
      }
   };
   private final EntityMinecart.EnumMinecartType minecartType;

   public ItemMinecart(EntityMinecart.EnumMinecartType enumMinecartType) {
      this.maxStackSize = 1;
      this.minecartType = enumMinecartType;
      this.setCreativeTab(CreativeTabs.tabTransport);
      BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserMinecartBehavior);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      IBlockState var12 = world.getBlockState(blockPos);
      if (BlockRailBase.isRailBlock(var12)) {
         if (!world.isRemote) {
            BlockRailBase.EnumRailDirection var13 = var12.getBlock() instanceof BlockRailBase
               ? var12.getValue(((BlockRailBase)var12.getBlock()).getShapeProperty())
               : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            double var14 = 0.0;
            if (var13.isAscending()) {
               var14 = 0.5;
            }

            EntityMinecart var16 = EntityMinecart.getMinecart(
               world, blockPos.getX() + 0.5, blockPos.getY() + 0.0625 + var14, blockPos.getZ() + 0.5, this.minecartType
            );
            if (itemStack.hasDisplayName()) {
               var16.setCustomNameTag(itemStack.getDisplayName());
            }

            world.spawnEntityInWorld(var16);
         }

         itemStack.stackSize--;
         return true;
      } else {
         return false;
      }
   }
}
