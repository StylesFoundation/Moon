package net.minecraft.item;

import java.util.List;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Rotations;
import net.minecraft.world.World;

public class ItemArmorStand extends Item {
   public ItemArmorStand() {
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (enumFacing == EnumFacing.DOWN) {
         return false;
      }

      boolean var12 = world.getBlockState(blockPos).getBlock().isReplaceable(world, blockPos);
      BlockPos var13 = var12 ? blockPos : blockPos.offset(enumFacing);
      if (!entityPlayer.canPlayerEdit(var13, enumFacing, itemStack)) {
         return false;
      }

      BlockPos var14 = var13.up();
      boolean var15 = !world.isAirBlock(var13) && !world.getBlockState(var13).getBlock().isReplaceable(world, var13);
      var15 |= !world.isAirBlock(var14) && !world.getBlockState(var14).getBlock().isReplaceable(world, var14);
      if (var15) {
         return false;
      }

      double var16 = var13.getX();
      double var18 = var13.getY();
      double var20 = var13.getZ();
      List var22 = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.fromBounds(var16, var18, var20, var16 + 1.0, var18 + 2.0, var20 + 1.0));
      if (var22.size() > 0) {
         return false;
      }

      if (!world.isRemote) {
         world.setBlockToAir(var13);
         world.setBlockToAir(var14);
         EntityArmorStand var23 = new EntityArmorStand(world, var16 + 0.5, var18, var20 + 0.5);
         float var24 = MathHelper.floor_float((MathHelper.wrapAngleTo180_float(entityPlayer.rotationYaw - 180.0F) + 22.5F) / 45.0F) * 45.0F;
         var23.setLocationAndAngles(var16 + 0.5, var18, var20 + 0.5, var24, 0.0F);
         this.applyRandomRotations(var23, world.rand);
         NBTTagCompound var25 = itemStack.getTagCompound();
         if (var25 != null && var25.hasKey("EntityTag", 10)) {
            NBTTagCompound var26 = new NBTTagCompound();
            var23.writeToNBTOptional(var26);
            var26.merge(var25.getCompoundTag("EntityTag"));
            var23.readFromNBT(var26);
         }

         world.spawnEntityInWorld(var23);
      }

      itemStack.stackSize--;
      return true;
   }

   private void applyRandomRotations(EntityArmorStand entityArmorStand, Random random) {
      Rotations var3 = entityArmorStand.getHeadRotation();
      float var4 = random.nextFloat() * 5.0F;
      float var5 = random.nextFloat() * 20.0F - 10.0F;
      Rotations var6 = new Rotations(var3.getX() + var4, var3.getY() + var5, var3.getZ());
      entityArmorStand.setHeadRotation(var6);
      var3 = entityArmorStand.getBodyRotation();
      var4 = random.nextFloat() * 10.0F - 5.0F;
      var6 = new Rotations(var3.getX(), var3.getY() + var4, var3.getZ());
      entityArmorStand.setBodyRotation(var6);
   }
}
