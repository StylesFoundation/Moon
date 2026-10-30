package net.minecraft.item;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.block.BlockSkull;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSkull extends Item {
   private static final String[] skullTypes = new String[]{"skeleton", "wither", "zombie", "char", "creeper"};

   public ItemSkull() {
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (enumFacing == EnumFacing.DOWN) {
         return false;
      }

      boolean var14 = world.getBlockState(blockPos).getBlock().isReplaceable(world, blockPos);
      if (!var14) {
         if (!world.getBlockState(blockPos).getBlock().getMaterial().isSolid()) {
            return false;
         }

         blockPos = blockPos.offset(enumFacing);
      }

      if (!entityPlayer.canPlayerEdit(blockPos, enumFacing, itemStack)) {
         return false;
      }

      if (!Blocks.skull.canPlaceBlockAt(world, blockPos)) {
         return false;
      }

      if (!world.isRemote) {
         world.setBlockState(blockPos, Blocks.skull.getDefaultState().withProperty(BlockSkull.FACING, enumFacing), 3);
         int var15 = 0;
         if (enumFacing == EnumFacing.UP) {
            var15 = MathHelper.floor_double(entityPlayer.rotationYaw * 16.0F / 360.0F + 0.5) & 15;
         }

         if (world.getTileEntity(blockPos) instanceof TileEntitySkull var17) {
            if (itemStack.getMetadata() == 3) {
               GameProfile var18 = null;
               if (itemStack.hasTagCompound()) {
                  NBTTagCompound var19 = itemStack.getTagCompound();
                  if (var19.hasKey("SkullOwner", 10)) {
                     var18 = NBTUtil.readGameProfileFromNBT(var19.getCompoundTag("SkullOwner"));
                  } else if (var19.hasKey("SkullOwner", 8) && var19.getString("SkullOwner").length() > 0) {
                     var18 = new GameProfile(null, var19.getString("SkullOwner"));
                  }
               }

               var17.setPlayerProfile(var18);
            } else {
               var17.setType(itemStack.getMetadata());
            }

            var17.setSkullRotation(var15);
            Blocks.skull.checkWitherSpawn(world, blockPos, var17);
         }

         itemStack.stackSize--;
      }

      return true;
   }

   @Override
   public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (int var7 = 0; var7 < skullTypes.length; var7++) {
         list.add(new ItemStack(item, 1, var7));
      }
   }

   @Override
   public int getMetadata(int integer) {
      return integer;
   }

   @Override
   public String getUnlocalizedName(ItemStack itemStack) {
      int var5 = itemStack.getMetadata();
      if (var5 < 0 || var5 >= skullTypes.length) {
         var5 = 0;
      }

      return super.getUnlocalizedName() + "." + skullTypes[var5];
   }

   @Override
   public String getItemStackDisplayName(ItemStack itemStack) {
      if (itemStack.getMetadata() == 3 && itemStack.hasTagCompound()) {
         if (itemStack.getTagCompound().hasKey("SkullOwner", 8)) {
            return StatCollector.translateToLocalFormatted("item.skull.player.name", itemStack.getTagCompound().getString("SkullOwner"));
         }

         if (itemStack.getTagCompound().hasKey("SkullOwner", 10)) {
            NBTTagCompound var5 = itemStack.getTagCompound().getCompoundTag("SkullOwner");
            if (var5.hasKey("Name", 8)) {
               return StatCollector.translateToLocalFormatted("item.skull.player.name", var5.getString("Name"));
            }
         }
      }

      return super.getItemStackDisplayName(itemStack);
   }

   @Override
   public boolean updateItemStackNBT(NBTTagCompound nBTTagCompound) {
      super.updateItemStackNBT(nBTTagCompound);
      if (nBTTagCompound.hasKey("SkullOwner", 8) && nBTTagCompound.getString("SkullOwner").length() > 0) {
         GameProfile var5 = TileEntitySkull.updateGameprofile(new GameProfile(null, nBTTagCompound.getString("SkullOwner")));
         nBTTagCompound.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), var5));
         return true;
      } else {
         return false;
      }
   }
}
