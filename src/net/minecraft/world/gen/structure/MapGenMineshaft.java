package net.minecraft.world.gen.structure;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.MathHelper;

public class MapGenMineshaft extends MapGenStructure {
   private double field_82673_e = 0.004;

   public MapGenMineshaft() {
   }

   @Override
   public String getStructureName() {
      return "Mineshaft";
   }

   public MapGenMineshaft(Map<String, String> map) {
      for (Entry var6 : map.entrySet()) {
         if (((String)var6.getKey()).equals("chance")) {
            this.field_82673_e = MathHelper.parseDoubleWithDefault((String)var6.getValue(), this.field_82673_e);
         }
      }
   }

   @Override
   protected boolean canSpawnStructureAtCoords(int integer1, int integer2) {
      return this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(80) < Math.max(Math.abs(integer1), Math.abs(integer2));
   }

   @Override
   protected StructureStart getStructureStart(int integer1, int integer2) {
      return new StructureMineshaftStart(this.worldObj, this.rand, integer1, integer2);
   }
}
