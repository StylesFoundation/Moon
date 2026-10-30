package net.minecraft.block.state;

import com.google.common.base.Predicate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockWorldState {
   private final World world;
   private final BlockPos pos;
   private final boolean field_181628_c;
   private IBlockState state;
   private TileEntity tileEntity;
   private boolean tileEntityInitialized;

   public BlockWorldState(World world, BlockPos blockPos, boolean boolean3) {
      this.world = world;
      this.pos = blockPos;
      this.field_181628_c = boolean3;
   }

   public IBlockState getBlockState() {
      if (this.state == null && (this.field_181628_c || this.world.Method322(this.pos))) {
         this.state = this.world.getBlockState(this.pos);
      }

      return this.state;
   }

   public TileEntity getTileEntity() {
      if (this.tileEntity == null && !this.tileEntityInitialized) {
         this.tileEntity = this.world.getTileEntity(this.pos);
         this.tileEntityInitialized = true;
      }

      return this.tileEntity;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public static Predicate<BlockWorldState> hasState(Predicate<IBlockState> predicate) {
      return blockWorldState -> blockWorldState != null && predicate.apply(blockWorldState.getBlockState());
   }
}
