package net.minecraft.world.gen.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MapGenVillage extends MapGenStructure {
   public static final List<BiomeGenBase> villageSpawnBiomes = Arrays.asList(BiomeGenBase.plains, BiomeGenBase.desert, BiomeGenBase.savanna);
   private int terrainType;
   private int field_82665_g = 32;
   private final int field_82666_h = 8;

   public MapGenVillage() {
   }

   public MapGenVillage(Map<String, String> map) {
      this();

      for (Entry var6 : map.entrySet()) {
         if (((String)var6.getKey()).equals("size")) {
            this.terrainType = MathHelper.parseIntWithDefaultAndMax((String)var6.getValue(), this.terrainType, 0);
         } else if (((String)var6.getKey()).equals("distance")) {
            this.field_82665_g = MathHelper.parseIntWithDefaultAndMax((String)var6.getValue(), this.field_82665_g, this.field_82666_h + 1);
         }
      }
   }

   @Override
   public String getStructureName() {
      return "Village";
   }

   @Override
   protected boolean canSpawnStructureAtCoords(int integer1, int integer2) {
      int var6 = integer1;
      int var7 = integer2;
      if (integer1 < 0) {
         integer1 -= this.field_82665_g - 1;
      }

      if (integer2 < 0) {
         integer2 -= this.field_82665_g - 1;
      }

      int var8 = integer1 / this.field_82665_g;
      int var9 = integer2 / this.field_82665_g;
      Random var10 = this.worldObj.setRandomSeed(var8, var9, 10387312);
      var8 *= this.field_82665_g;
      var9 *= this.field_82665_g;
      var8 += var10.nextInt(this.field_82665_g - this.field_82666_h);
      var9 += var10.nextInt(this.field_82665_g - this.field_82666_h);
      return var6 == var8 && var7 == var9 ? this.worldObj.getWorldChunkManager().areBiomesViable(var6 * 16 + 8, var7 * 16 + 8, 0, villageSpawnBiomes) : false;
   }

   @Override
   protected StructureStart getStructureStart(int integer1, int integer2) {
      return new MapGenVillage.Start(this.worldObj, this.rand, integer1, integer2, this.terrainType);
   }

   public static class Start extends StructureStart {
      private boolean hasMoreThanTwoComponents;

      public Start() {
      }

      public Start(World world, Random random, int integer3, int integer4, int integer5) {
         super(integer3, integer4);
         List var9 = StructureVillagePieces.Method412(random, integer5);
         StructureVillagePieces.Start var10 = new StructureVillagePieces.Start(
            world.getWorldChunkManager(), 0, random, (integer3 << 4) + 2, (integer4 << 4) + 2, var9, integer5
         );
         this.components.add(var10);
         var10.buildComponent(var10, this.components, random);
         List var11 = var10.field_74930_j;
         List var12 = var10.field_74932_i;

         while (!var11.isEmpty() || !var12.isEmpty()) {
            if (var11.isEmpty()) {
               int var13 = random.nextInt(var12.size());
               StructureComponent var14 = (StructureComponent)var12.remove(var13);
               var14.buildComponent(var10, this.components, random);
            } else {
               int var16 = random.nextInt(var11.size());
               StructureComponent var18 = (StructureComponent)var11.remove(var16);
               var18.buildComponent(var10, this.components, random);
            }
         }

         this.updateBoundingBox();
         int var17 = 0;

         for (StructureComponent var15 : this.components) {
            if (!(var15 instanceof StructureVillagePieces.Road)) {
               var17++;
            }
         }

         this.hasMoreThanTwoComponents = var17 > 2;
      }

      @Override
      public boolean isSizeableStructure() {
         return this.hasMoreThanTwoComponents;
      }

      @Override
      public void writeToNBT(NBTTagCompound nBTTagCompound) {
         super.writeToNBT(nBTTagCompound);
         nBTTagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
      }

      @Override
      public void readFromNBT(NBTTagCompound nBTTagCompound) {
         super.readFromNBT(nBTTagCompound);
         this.hasMoreThanTwoComponents = nBTTagCompound.getBoolean("Valid");
      }
   }
}
