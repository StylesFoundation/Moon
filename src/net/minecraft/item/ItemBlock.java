package net.minecraft.item;

import com.moon.viamcp.ViaMCP;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBlock extends Item {
   protected final Block block;

   public ItemBlock(Block block) {
      this.block = block;
   }

   public ItemBlock setUnlocalizedName(String string) {
      super.setUnlocalizedName(string);
      return this;
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      IBlockState var12 = world.getBlockState(blockPos);
      Block var13 = var12.getBlock();
      if (!var13.isReplaceable(world, blockPos)) {
         blockPos = blockPos.offset(enumFacing);
      }

      if (itemStack.stackSize == 0) {
         return false;
      }

      if (!entityPlayer.canPlayerEdit(blockPos, enumFacing, itemStack)) {
         return false;
      }

      if (world.canBlockBePlaced(this.block, blockPos, false, enumFacing, null, itemStack)) {
         int var14 = this.getMetadata(itemStack.getMetadata());
         IBlockState var15 = this.block.onBlockPlaced(world, blockPos, enumFacing, float6, float7, float8, var14, entityPlayer);
         if (world.setBlockState(blockPos, var15, 3)) {
            var15 = world.getBlockState(blockPos);
            if (var15.getBlock() == this.block) {
               setTileEntityNBT(world, entityPlayer, blockPos, itemStack);
               this.block.onBlockPlacedBy(world, blockPos, var15, entityPlayer, itemStack);
            }

            if (ViaMCP.getInstance().getVersion() != 47) {
               Minecraft.getMinecraft()
                  .theWorld
                  .playSoundAtPos(
                     blockPos.add(0.5, 0.5, 0.5),
                     this.block.stepSound.getPlaceSound(),
                     (this.block.stepSound.getVolume() + 1.0F) / 2.0F,
                     this.block.stepSound.getFrequency() * 0.8F,
                     false
                  );
            } else {
               world.playSoundEffect(
                  blockPos.getX() + 0.5F,
                  blockPos.getY() + 0.5F,
                  blockPos.getZ() + 0.5F,
                  this.block.stepSound.getPlaceSound(),
                  (this.block.stepSound.getVolume() + 1.0F) / 2.0F,
                  this.block.stepSound.getFrequency() * 0.8F
               );
            }

            itemStack.stackSize--;
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean setTileEntityNBT(World world, EntityPlayer entityPlayer, BlockPos blockPos, ItemStack itemStack) {
      MinecraftServer var7 = MinecraftServer.getServer();
      if (var7 != null && itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("BlockEntityTag", 10)) {
         TileEntity var8 = world.getTileEntity(blockPos);
         if (var8 != null) {
            if (!world.isRemote && var8.func_183000_F() && !var7.getConfigurationManager().canSendCommands(entityPlayer.getGameProfile())) {
               return false;
            }

            NBTTagCompound var9 = new NBTTagCompound();
            NBTTagCompound var10 = (NBTTagCompound)var9.copy();
            var8.writeToNBT(var9);
            NBTTagCompound var11 = (NBTTagCompound)itemStack.getTagCompound().getTag("BlockEntityTag");
            var9.merge(var11);
            var9.setInteger("x", blockPos.getX());
            var9.setInteger("y", blockPos.getY());
            var9.setInteger("z", blockPos.getZ());
            if (!var9.equals(var10)) {
               var8.readFromNBT(var9);
               var8.markDirty();
               return true;
            }
         }
      }

      return false;
   }

   public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, EnumFacing enumFacing, EntityPlayer entityPlayer, ItemStack itemStack) {
      Block var9 = world.getBlockState(blockPos).getBlock();
      if (var9 == Blocks.snow_layer) {
         enumFacing = EnumFacing.UP;
      } else if (!var9.isReplaceable(world, blockPos)) {
         blockPos = blockPos.offset(enumFacing);
      }

      return world.canBlockBePlaced(this.block, blockPos, false, enumFacing, null, itemStack);
   }

   @Override
   public String getUnlocalizedName(ItemStack itemStack) {
      return this.block.Method440();
   }

   @Override
   public String getUnlocalizedName() {
      return this.block.Method440();
   }

   @Override
   public CreativeTabs getCreativeTab() {
      return this.block.getCreativeTabToDisplayOn();
   }

   @Override
   public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      this.block.getSubBlocks(item, creativeTabs, list);
   }

   public Block getBlock() {
      return this.block;
   }
}
