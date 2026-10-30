package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockWorkbench extends Block {
   protected BlockWorkbench() {
      super(Material.wood);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote) {
         entityPlayer.displayGui(new BlockWorkbench.InterfaceCraftingTable(world, blockPos));
         entityPlayer.triggerAchievement(StatList.field_181742_Z);
      }

      return true;
   }

   public static class InterfaceCraftingTable implements IInteractionObject {
      private final World world;
      private final BlockPos position;

      public InterfaceCraftingTable(World world, BlockPos blockPos) {
         this.world = world;
         this.position = blockPos;
      }

      @Override
      public String getName() {
         return null;
      }

      @Override
      public boolean hasCustomName() {
         return false;
      }

      @Override
      public IChatComponent getDisplayName() {
         return new ChatComponentTranslation(Blocks.crafting_table.Method440() + ".name");
      }

      @Override
      public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
         return new ContainerWorkbench(inventoryPlayer, this.world, this.position);
      }

      @Override
      public String getGuiID() {
         return "minecraft:crafting_table";
      }
   }
}
