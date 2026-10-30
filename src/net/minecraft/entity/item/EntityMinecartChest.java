package net.minecraft.entity.item;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityMinecartChest extends EntityMinecartContainer {
   public EntityMinecartChest(World world) {
      super(world);
   }

   public EntityMinecartChest(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   public void killMinecart(DamageSource damageSource) {
      super.killMinecart(damageSource);
      if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         this.dropItemWithOffset(Item.getItemFromBlock(Blocks.chest), 1, 0.0F);
      }
   }

   @Override
   public int getSizeInventory() {
      return 27;
   }

   @Override
   public EntityMinecart.EnumMinecartType getMinecartType() {
      return EntityMinecart.EnumMinecartType.CHEST;
   }

   @Override
   public IBlockState getDefaultDisplayTile() {
      return Blocks.chest.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.NORTH);
   }

   @Override
   public int getDefaultDisplayTileOffset() {
      return 8;
   }

   @Override
   public String getGuiID() {
      return "minecraft:chest";
   }

   @Override
   public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
      return new ContainerChest(inventoryPlayer, this, entityPlayer);
   }
}
