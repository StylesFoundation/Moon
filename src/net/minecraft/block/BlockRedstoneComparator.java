package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider {
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   public static final PropertyEnum<BlockRedstoneComparator.Mode> MODE = PropertyEnum.create("mode", BlockRedstoneComparator.Mode.class);

   public BlockRedstoneComparator(boolean boolean1) {
      super(boolean1);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(FACING, EnumFacing.NORTH)
            .withProperty(POWERED, Boolean.FALSE)
            .withProperty(MODE, BlockRedstoneComparator.Mode.COMPARE)
      );
      this.isBlockContainer = true;
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal("item.comparator.name");
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.comparator;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.comparator;
   }

   @Override
   protected int getDelay(IBlockState iBlockState) {
      return 2;
   }

   @Override
   protected IBlockState getPoweredState(IBlockState iBlockState) {
      Boolean var2 = iBlockState.getValue(POWERED);
      BlockRedstoneComparator.Mode var3 = iBlockState.getValue(MODE);
      EnumFacing var4 = iBlockState.getValue(FACING);
      return Blocks.powered_comparator.getDefaultState().withProperty(FACING, var4).withProperty(POWERED, var2).withProperty(MODE, var3);
   }

   @Override
   protected IBlockState getUnpoweredState(IBlockState iBlockState) {
      Boolean var2 = iBlockState.getValue(POWERED);
      BlockRedstoneComparator.Mode var3 = iBlockState.getValue(MODE);
      EnumFacing var4 = iBlockState.getValue(FACING);
      return Blocks.unpowered_comparator.getDefaultState().withProperty(FACING, var4).withProperty(POWERED, var2).withProperty(MODE, var3);
   }

   @Override
   protected boolean isPowered(IBlockState iBlockState) {
      return this.isRepeaterPowered || iBlockState.getValue(POWERED);
   }

   @Override
   protected int getActiveSignal(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState) {
      TileEntity var7 = iBlockAccess.getTileEntity(blockPos);
      return var7 instanceof TileEntityComparator ? ((TileEntityComparator)var7).getOutputSignal() : 0;
   }

   private int calculateOutput(World world, BlockPos blockPos, IBlockState iBlockState) {
      return iBlockState.getValue(MODE) == BlockRedstoneComparator.Mode.SUBTRACT
         ? Math.max(this.calculateInputStrength(world, blockPos, iBlockState) - this.getPowerOnSides(world, blockPos, iBlockState), 0)
         : this.calculateInputStrength(world, blockPos, iBlockState);
   }

   @Override
   protected boolean shouldBePowered(World world, BlockPos blockPos, IBlockState iBlockState) {
      int var7 = this.calculateInputStrength(world, blockPos, iBlockState);
      if (var7 >= 15) {
         return true;
      }

      if (var7 == 0) {
         return false;
      }

      int var8 = this.getPowerOnSides(world, blockPos, iBlockState);
      return var8 == 0 || var7 >= var8;
   }

   @Override
   protected int calculateInputStrength(World world, BlockPos blockPos, IBlockState iBlockState) {
      int var7 = super.calculateInputStrength(world, blockPos, iBlockState);
      EnumFacing var8 = iBlockState.getValue(FACING);
      BlockPos var9 = blockPos.offset(var8);
      Block var10 = world.getBlockState(var9).getBlock();
      if (var10.hasComparatorInputOverride()) {
         var7 = var10.getComparatorInputOverride(world, var9);
      } else if (var7 < 15 && var10.isNormalCube()) {
         var9 = var9.offset(var8);
         var10 = world.getBlockState(var9).getBlock();
         if (var10.hasComparatorInputOverride()) {
            var7 = var10.getComparatorInputOverride(world, var9);
         } else if (var10.getMaterial() == Material.air) {
            EntityItemFrame var11 = this.findItemFrame(world, var8, var9);
            if (var11 != null) {
               var7 = var11.func_174866_q();
            }
         }
      }

      return var7;
   }

   private EntityItemFrame findItemFrame(World world, EnumFacing enumFacing, BlockPos blockPos) {
      List var7 = world.getEntitiesWithinAABB(
         EntityItemFrame.class,
         new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1),
         entity -> entity != null && entity.getHorizontalFacing() == enumFacing
      );
      return var7.size() == 1 ? (EntityItemFrame)var7.get(0) : null;
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!entityPlayer.capabilities.allowEdit) {
         return false;
      }

      iBlockState = iBlockState.cycleProperty(MODE);
      world.playSoundEffect(
         blockPos.getX() + 0.5,
         blockPos.getY() + 0.5,
         blockPos.getZ() + 0.5,
         "random.click",
         0.3F,
         iBlockState.getValue(MODE) == BlockRedstoneComparator.Mode.SUBTRACT ? 0.55F : 0.5F
      );
      world.setBlockState(blockPos, iBlockState, 2);
      this.onStateChange(world, blockPos, iBlockState);
      return true;
   }

   @Override
   protected void updateState(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!world.isBlockTickPending(blockPos, this)) {
         int var7 = this.calculateOutput(world, blockPos, iBlockState);
         TileEntity var8 = world.getTileEntity(blockPos);
         int var9 = var8 instanceof TileEntityComparator ? ((TileEntityComparator)var8).getOutputSignal() : 0;
         if (var7 != var9 || this.isPowered(iBlockState) != this.shouldBePowered(world, blockPos, iBlockState)) {
            if (this.isFacingTowardsRepeater(world, blockPos, iBlockState)) {
               world.updateBlockTick(blockPos, this, 2, -1);
            } else {
               world.updateBlockTick(blockPos, this, 2, 0);
            }
         }
      }
   }

   private void onStateChange(World world, BlockPos blockPos, IBlockState iBlockState) {
      int var7 = this.calculateOutput(world, blockPos, iBlockState);
      TileEntity var8 = world.getTileEntity(blockPos);
      int var9 = 0;
      if (var8 instanceof TileEntityComparator var10) {
         var9 = var10.getOutputSignal();
         var10.setOutputSignal(var7);
      }

      if (var9 != var7 || iBlockState.getValue(MODE) == BlockRedstoneComparator.Mode.COMPARE) {
         boolean var12 = this.shouldBePowered(world, blockPos, iBlockState);
         boolean var11 = this.isPowered(iBlockState);
         if (var11 && !var12) {
            world.setBlockState(blockPos, iBlockState.withProperty(POWERED, Boolean.FALSE), 2);
         } else if (!var11 && var12) {
            world.setBlockState(blockPos, iBlockState.withProperty(POWERED, Boolean.TRUE), 2);
         }

         this.notifyNeighbors(world, blockPos, iBlockState);
      }
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (this.isRepeaterPowered) {
         world.setBlockState(blockPos, this.getUnpoweredState(iBlockState).withProperty(POWERED, Boolean.TRUE), 4);
      }

      this.onStateChange(world, blockPos, iBlockState);
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      super.onBlockAdded(world, blockPos, iBlockState);
      world.setTileEntity(blockPos, this.createNewTileEntity(world, 0));
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      super.breakBlock(world, blockPos, iBlockState);
      world.removeTileEntity(blockPos);
      this.notifyNeighbors(world, blockPos, iBlockState);
   }

   @Override
   public boolean onBlockEventReceived(World world, BlockPos blockPos, IBlockState iBlockState, int integer4, int integer5) {
      super.onBlockEventReceived(world, blockPos, iBlockState, integer4, integer5);
      TileEntity var9 = world.getTileEntity(blockPos);
      return var9 != null && var9.receiveClientEvent(integer4, integer5);
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityComparator();
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState()
         .withProperty(FACING, EnumFacing.getHorizontal(integer))
         .withProperty(POWERED, (integer & 8) > 0)
         .withProperty(MODE, (integer & 4) > 0 ? BlockRedstoneComparator.Mode.SUBTRACT : BlockRedstoneComparator.Mode.COMPARE);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getHorizontalIndex();
      if (iBlockState.getValue(POWERED)) {
         var5 |= 8;
      }

      if (iBlockState.getValue(MODE) == BlockRedstoneComparator.Mode.SUBTRACT) {
         var5 |= 4;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, MODE, POWERED);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState()
         .withProperty(FACING, entityLivingBase.getHorizontalFacing().getOpposite())
         .withProperty(POWERED, Boolean.FALSE)
         .withProperty(MODE, BlockRedstoneComparator.Mode.COMPARE);
   }

   public enum Mode implements IStringSerializable {
      COMPARE("compare"),
      SUBTRACT("subtract");

      private final String name;

      Mode(String string3) {
         this.name = string3;
      }

      @Override
      public String toString() {
         return this.name;
      }

      @Override
      public String getName() {
         return this.name;
      }

      private static BlockRedstoneComparator.Mode[] Method916() {
         return new BlockRedstoneComparator.Mode[]{COMPARE, SUBTRACT};
      }
   }
}
