package net.minecraft.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class FlatLayerInfo {
   private final int field_175902_a;
   private IBlockState layerMaterial;
   private int layerCount = 1;
   private int layerMinimumY;

   public FlatLayerInfo(int integer, Block block) {
      this(3, integer, block);
   }

   public FlatLayerInfo(int integer1, int integer2, Block block) {
      this.field_175902_a = integer1;
      this.layerCount = integer2;
      this.layerMaterial = block.getDefaultState();
   }

   public FlatLayerInfo(int integer1, int integer2, Block block, int integer4) {
      this(integer1, integer2, block);
      this.layerMaterial = block.getStateFromMeta(integer4);
   }

   public int getLayerCount() {
      return this.layerCount;
   }

   public IBlockState getLayerMaterial() {
      return this.layerMaterial;
   }

   private Block getLayerMaterialBlock() {
      return this.layerMaterial.getBlock();
   }

   private int getFillBlockMeta() {
      return this.layerMaterial.getBlock().getMetaFromState(this.layerMaterial);
   }

   public int getMinY() {
      return this.layerMinimumY;
   }

   public void setMinY(int integer) {
      this.layerMinimumY = integer;
   }

   @Override
   public String toString() {
      String var3;
      if (this.field_175902_a >= 3) {
         ResourceLocation var4 = Block.blockRegistry.getNameForObject(this.getLayerMaterialBlock());
         var3 = var4 == null ? "null" : var4.toString();
         if (this.layerCount > 1) {
            var3 = this.layerCount + "*" + var3;
         }
      } else {
         var3 = Integer.toString(Block.getIdFromBlock(this.getLayerMaterialBlock()));
         if (this.layerCount > 1) {
            var3 = this.layerCount + "x" + var3;
         }
      }

      int var5 = this.getFillBlockMeta();
      if (var5 > 0) {
         var3 = var3 + ":" + var5;
      }

      return var3;
   }
}
