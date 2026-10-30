package net.minecraft.world.gen.layer;

public class GenLayerFuzzyZoom extends GenLayerZoom {
   public GenLayerFuzzyZoom(long long1, GenLayer genLayer) {
      super(long1, genLayer);
   }

   @Override
   protected int selectModeOrRandom(int integer1, int integer2, int integer3, int integer4) {
      return this.selectRandom(integer1, integer2, integer3, integer4);
   }
}
