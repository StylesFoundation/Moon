package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MapGenStronghold extends MapGenStructure {
   private final List<BiomeGenBase> field_151546_e;
   private boolean ranBiomeCheck;
   private ChunkCoordIntPair[] structureCoords = new ChunkCoordIntPair[3];
   private double field_82671_h = 32.0;
   private int field_82672_i = 3;

   public MapGenStronghold() {
      this.field_151546_e = Lists.newArrayList();

      for (BiomeGenBase var7 : BiomeGenBase.getBiomeGenArray()) {
         if (var7 != null && var7.minHeight > 0.0F) {
            this.field_151546_e.add(var7);
         }
      }
   }

   public MapGenStronghold(Map<String, String> map) {
      this();

      for (Entry var6 : map.entrySet()) {
         if (((String)var6.getKey()).equals("distance")) {
            this.field_82671_h = MathHelper.parseDoubleWithDefaultAndMax((String)var6.getValue(), this.field_82671_h, 1.0);
         } else if (((String)var6.getKey()).equals("count")) {
            this.structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax((String)var6.getValue(), this.structureCoords.length, 1)];
         } else if (((String)var6.getKey()).equals("spread")) {
            this.field_82672_i = MathHelper.parseIntWithDefaultAndMax((String)var6.getValue(), this.field_82672_i, 1);
         }
      }
   }

   @Override
   public String getStructureName() {
      return "Stronghold";
   }

   @Override
   protected boolean canSpawnStructureAtCoords(int integer1, int integer2) {
      if (!this.ranBiomeCheck) {
         Random var6 = new Random();
         var6.setSeed(this.worldObj.getSeed());
         double var7 = var6.nextDouble() * Math.PI * 2.0;
         int var9 = 1;

         for (int var10 = 0; var10 < this.structureCoords.length; var10++) {
            double var11 = (1.25 * var9 + var6.nextDouble()) * this.field_82671_h * var9;
            int var13 = (int)Math.round(Math.cos(var7) * var11);
            int var14 = (int)Math.round(Math.sin(var7) * var11);
            BlockPos var15 = this.worldObj.getWorldChunkManager().findBiomePosition((var13 << 4) + 8, (var14 << 4) + 8, 112, this.field_151546_e, var6);
            if (var15 != null) {
               var13 = var15.getX() >> 4;
               var14 = var15.getZ() >> 4;
            }

            this.structureCoords[var10] = new ChunkCoordIntPair(var13, var14);
            var7 += (Math.PI * 2) * var9 / this.field_82672_i;
            if (var10 == this.field_82672_i) {
               var9 += 2 + var6.nextInt(5);
               this.field_82672_i = this.field_82672_i + 1 + var6.nextInt(2);
            }
         }

         this.ranBiomeCheck = true;
      }

      for (ChunkCoordIntPair var18 : this.structureCoords) {
         if (integer1 == var18.chunkXPos && integer2 == var18.chunkZPos) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected List<BlockPos> getCoordList() {
      ArrayList var4 = Lists.newArrayList();

      for (ChunkCoordIntPair var8 : this.structureCoords) {
         if (var8 != null) {
            var4.add(var8.getCenterBlock(64));
         }
      }

      return var4;
   }

   @Override
   protected StructureStart getStructureStart(int integer1, int integer2) {
      MapGenStronghold.Start var6 = new MapGenStronghold.Start(this.worldObj, this.rand, integer1, integer2);

      while (var6.getComponents().isEmpty() || ((StructureStrongholdPieces.Stairs2)var6.getComponents().get(0)).strongholdPortalRoom == null) {
         var6 = new MapGenStronghold.Start(this.worldObj, this.rand, integer1, integer2);
      }

      return var6;
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(World world, Random random, int integer3, int integer4) {
         super(integer3, integer4);
         StructureStrongholdPieces.prepareStructurePieces();
         StructureStrongholdPieces.Stairs2 var8 = new StructureStrongholdPieces.Stairs2(0, random, (integer3 << 4) + 2, (integer4 << 4) + 2);
         this.components.add(var8);
         var8.buildComponent(var8, this.components, random);
         List var9 = var8.field_75026_c;

         while (!var9.isEmpty()) {
            int var10 = random.nextInt(var9.size());
            StructureComponent var11 = (StructureComponent)var9.remove(var10);
            var11.buildComponent(var8, this.components, random);
         }

         this.updateBoundingBox();
         this.markAvailableHeight(world, random, 10);
      }
   }
}
