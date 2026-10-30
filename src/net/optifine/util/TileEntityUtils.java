package net.optifine.util;

import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldNameable;

public class TileEntityUtils {
   public static String getTileEntityName(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return getTileEntityName(iBlockAccess.getTileEntity(blockPos));
   }

   public static String getTileEntityName(TileEntity tileEntity) {
      if (tileEntity instanceof IWorldNameable var4) {
         updateTileEntityName(tileEntity);
         return !var4.hasCustomName() ? null : var4.getName();
      } else {
         return null;
      }
   }

   public static void updateTileEntityName(TileEntity tileEntity) {
      BlockPos var4 = tileEntity.getPos();
      String var5 = getTileEntityRawName(tileEntity);
      if (var5 == null) {
         String var6 = Config.normalize(getServerTileEntityRawName(var4));
         Method432(tileEntity, var6);
      }
   }

   public static String getServerTileEntityRawName(BlockPos blockPos) {
      TileEntity var4 = IntegratedServerUtils.getTileEntity(blockPos);
      return var4 == null ? null : getTileEntityRawName(var4);
   }

   public static String getTileEntityRawName(TileEntity tileEntity) {
      if (tileEntity instanceof TileEntityBeacon) {
         return ((TileEntityBeacon)tileEntity).w;
      } else if (tileEntity instanceof TileEntityBrewingStand) {
         return ((TileEntityBrewingStand)tileEntity).customName;
      } else if (tileEntity instanceof TileEntityEnchantmentTable) {
         return ((TileEntityEnchantmentTable)tileEntity).customName;
      } else if (tileEntity instanceof TileEntityFurnace) {
         return ((TileEntityFurnace)tileEntity).furnaceCustomName;
      } else {
         return tileEntity instanceof IWorldNameable var4 && var4.hasCustomName() ? var4.getName() : null;
      }
   }

   public static boolean Method432(TileEntity tileEntity, String string) {
      if (tileEntity instanceof TileEntityBeacon) {
         ((TileEntityBeacon)tileEntity).w = string;
         return true;
      } else if (tileEntity instanceof TileEntityBrewingStand) {
         ((TileEntityBrewingStand)tileEntity).customName = string;
         return true;
      } else if (tileEntity instanceof TileEntityEnchantmentTable) {
         ((TileEntityEnchantmentTable)tileEntity).customName = string;
         return true;
      } else if (tileEntity instanceof TileEntityFurnace) {
         ((TileEntityFurnace)tileEntity).furnaceCustomName = string;
         return true;
      } else if (tileEntity instanceof TileEntityChest) {
         ((TileEntityChest)tileEntity).setCustomName(string);
         return true;
      } else if (tileEntity instanceof TileEntityDispenser) {
         ((TileEntityDispenser)tileEntity).setCustomName(string);
         return true;
      } else if (tileEntity instanceof TileEntityHopper) {
         ((TileEntityHopper)tileEntity).setCustomName(string);
         return true;
      } else {
         return false;
      }
   }
}
