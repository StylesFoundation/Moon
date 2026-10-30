package net.minecraft.item;

import java.util.List;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBanner extends ItemBlock {
   public ItemBanner() {
      super(Blocks.standing_banner);
      this.maxStackSize = 16;
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (enumFacing == EnumFacing.DOWN) {
         return false;
      }

      if (!world.getBlockState(blockPos).getBlock().getMaterial().isSolid()) {
         return false;
      }

      blockPos = blockPos.offset(enumFacing);
      if (!entityPlayer.canPlayerEdit(blockPos, enumFacing, itemStack)) {
         return false;
      }

      if (!Blocks.standing_banner.canPlaceBlockAt(world, blockPos)) {
         return false;
      }

      if (world.isRemote) {
         return true;
      }

      if (enumFacing == EnumFacing.UP) {
         int var12 = MathHelper.floor_double((entityPlayer.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5) & 15;
         world.setBlockState(blockPos, Blocks.standing_banner.getDefaultState().withProperty(BlockStandingSign.ROTATION, var12), 3);
      } else {
         world.setBlockState(blockPos, Blocks.wall_banner.getDefaultState().withProperty(BlockWallSign.FACING, enumFacing), 3);
      }

      itemStack.stackSize--;
      TileEntity var14 = world.getTileEntity(blockPos);
      if (var14 instanceof TileEntityBanner) {
         ((TileEntityBanner)var14).setItemValues(itemStack);
      }

      return true;
   }

   @Override
   public String getItemStackDisplayName(ItemStack itemStack) {
      EnumDyeColor var3 = this.getBaseColor(itemStack);
      return StatCollector.translateToLocal("item.banner." + var3.getUnlocalizedName() + ".name");
   }

   @Override
   public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean boolean4) {
      NBTTagCompound var8 = itemStack.getSubCompound("BlockEntityTag", false);
      if (var8 != null && var8.hasKey("Patterns")) {
         NBTTagList var9 = var8.getTagList("Patterns", 10);

         for (int var10 = 0; var10 < var9.tagCount() && var10 < 6; var10++) {
            NBTTagCompound var11 = var9.getCompoundTagAt(var10);
            EnumDyeColor var12 = EnumDyeColor.byDyeDamage(var11.getInteger("Color"));
            TileEntityBanner.EnumBannerPattern var13 = TileEntityBanner.EnumBannerPattern.getPatternByID(var11.getString("Pattern"));
            if (var13 != null) {
               list.add(StatCollector.translateToLocal("item.banner." + var13.getPatternName() + "." + var12.getUnlocalizedName()));
            }
         }
      }
   }

   @Override
   public int getColorFromItemStack(ItemStack itemStack, int integer) {
      return integer == 0 ? 16777215 : this.getBaseColor(itemStack).getMapColor().colorValue;
   }

   @Override
   public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (EnumDyeColor var10 : EnumDyeColor.values()) {
         NBTTagCompound var11 = new NBTTagCompound();
         TileEntityBanner.setBaseColorAndPatterns(var11, var10.getDyeDamage(), null);
         NBTTagCompound var12 = new NBTTagCompound();
         var12.setTag("BlockEntityTag", var11);
         ItemStack var13 = new ItemStack(item, 1, var10.getDyeDamage());
         var13.setTagCompound(var12);
         list.add(var13);
      }
   }

   @Override
   public CreativeTabs getCreativeTab() {
      return CreativeTabs.tabDecorations;
   }

   private EnumDyeColor getBaseColor(ItemStack itemStack) {
      NBTTagCompound var5 = itemStack.getSubCompound("BlockEntityTag", false);
      EnumDyeColor var6;
      if (var5 != null && var5.hasKey("Base")) {
         var6 = EnumDyeColor.byDyeDamage(var5.getInteger("Base"));
      } else {
         var6 = EnumDyeColor.byDyeDamage(itemStack.getMetadata());
      }

      return var6;
   }
}
