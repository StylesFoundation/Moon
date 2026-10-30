package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockAnvil extends BlockFalling {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
   public static final PropertyInteger DAMAGE = PropertyInteger.create("damage", 0, 2);

   protected BlockAnvil() {
      super(Material.anvil);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DAMAGE, 0));
      this.setLightOpacity(0);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      EnumFacing var9 = entityLivingBase.getHorizontalFacing().rotateY();
      return super.onBlockPlaced(world, blockPos, enumFacing, float4, float5, float6, integer, entityLivingBase)
         .withProperty(FACING, var9)
         .withProperty(DAMAGE, integer >> 2);
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote) {
         entityPlayer.displayGui(new BlockAnvil.Anvil(world, blockPos));
      }

      return true;
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(DAMAGE);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      EnumFacing var6 = iBlockAccess.getBlockState(blockPos).getValue(FACING);
      if (var6.getAxis() == EnumFacing.Axis.X) {
         this.setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
      } else {
         this.setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
      }
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      list.add(new ItemStack(item, 1, 0));
      list.add(new ItemStack(item, 1, 1));
      list.add(new ItemStack(item, 1, 2));
   }

   @Override
   protected void onStartFalling(EntityFallingBlock entityFallingBlock) {
      entityFallingBlock.setHurtEntities(true);
   }

   @Override
   public void onEndFalling(World world, BlockPos blockPos) {
      world.playAuxSFX(1022, blockPos, 0);
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return true;
   }

   @Override
   public IBlockState getStateForEntityRender(IBlockState iBlockState) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(integer & 3)).withProperty(DAMAGE, (integer & 15) >> 2);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return 0 | iBlockState.getValue(FACING).getHorizontalIndex() | iBlockState.getValue(DAMAGE) << 2;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, DAMAGE);
   }

   public static class Anvil implements IInteractionObject {
      private final World world;
      private final BlockPos position;

      public Anvil(World world, BlockPos blockPos) {
         this.world = world;
         this.position = blockPos;
      }

      @Override
      public String getName() {
         return "anvil";
      }

      @Override
      public boolean hasCustomName() {
         return false;
      }

      @Override
      public IChatComponent getDisplayName() {
         return new ChatComponentTranslation(Blocks.anvil.Method440() + ".name");
      }

      @Override
      public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
         return new ContainerRepair(inventoryPlayer, this.world, this.position, entityPlayer);
      }

      @Override
      public String getGuiID() {
         return "minecraft:anvil";
      }
   }
}
