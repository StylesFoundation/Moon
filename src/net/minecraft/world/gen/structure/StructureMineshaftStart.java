package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.world.World;

public class StructureMineshaftStart extends StructureStart {
   public StructureMineshaftStart() {
   }

   public StructureMineshaftStart(World world, Random random, int integer3, int integer4) {
      super(integer3, integer4);
      StructureMineshaftPieces.Room var7 = new StructureMineshaftPieces.Room(0, random, (integer3 << 4) + 2, (integer4 << 4) + 2);
      this.components.add(var7);
      var7.buildComponent(var7, this.components, random);
      this.updateBoundingBox();
      this.markAvailableHeight(world, random, 10);
   }
}
