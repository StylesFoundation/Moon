package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class StructureOceanMonument extends MapGenStructure {
   private int field_175800_f = 32;
   private int field_175801_g = 5;
   public static final List<BiomeGenBase> field_175802_d = Arrays.asList(
      BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver
   );
   private static final List<BiomeGenBase.SpawnListEntry> field_175803_h = Lists.newArrayList();

   public StructureOceanMonument() {
   }

   public StructureOceanMonument(Map<String, String> map) {
      this();

      for (Entry var6 : map.entrySet()) {
         if (((String)var6.getKey()).equals("spacing")) {
            this.field_175800_f = MathHelper.parseIntWithDefaultAndMax((String)var6.getValue(), this.field_175800_f, 1);
         } else if (((String)var6.getKey()).equals("separation")) {
            this.field_175801_g = MathHelper.parseIntWithDefaultAndMax((String)var6.getValue(), this.field_175801_g, 1);
         }
      }
   }

   @Override
   public String getStructureName() {
      return "Monument";
   }

   @Override
   protected boolean canSpawnStructureAtCoords(int integer1, int integer2) {
      int var6 = integer1;
      int var7 = integer2;
      if (integer1 < 0) {
         integer1 -= this.field_175800_f - 1;
      }

      if (integer2 < 0) {
         integer2 -= this.field_175800_f - 1;
      }

      int var8 = integer1 / this.field_175800_f;
      int var9 = integer2 / this.field_175800_f;
      Random var10 = this.worldObj.setRandomSeed(var8, var9, 10387313);
      var8 *= this.field_175800_f;
      var9 *= this.field_175800_f;
      var8 += (var10.nextInt(this.field_175800_f - this.field_175801_g) + var10.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
      var9 += (var10.nextInt(this.field_175800_f - this.field_175801_g) + var10.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
      if (var6 == var8 && var7 == var9) {
         return this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos(var6 * 16 + 8, 64, var7 * 16 + 8), null) != BiomeGenBase.deepOcean
            ? false
            : this.worldObj.getWorldChunkManager().areBiomesViable(var6 * 16 + 8, var7 * 16 + 8, 29, field_175802_d);
      } else {
         return false;
      }
   }

   @Override
   protected StructureStart getStructureStart(int integer1, int integer2) {
      return new StructureOceanMonument.StartMonument(this.worldObj, this.rand, integer1, integer2);
   }

   public List<BiomeGenBase.SpawnListEntry> getScatteredFeatureSpawnList() {
      return field_175803_h;
   }

   static {
      field_175803_h.add(new BiomeGenBase.SpawnListEntry(EntityGuardian.class, 1, 2, 4));
   }

   public static class StartMonument extends StructureStart {
      private final Set<ChunkCoordIntPair> field_175791_c = Sets.newHashSet();
      private boolean field_175790_d;

      public StartMonument() {
      }

      public StartMonument(World world, Random random, int integer3, int integer4) {
         super(integer3, integer4);
         this.func_175789_b(world, random, integer3, integer4);
      }

      private void func_175789_b(World world, Random random, int integer3, int integer4) {
         random.setSeed(world.getSeed());
         long var7 = random.nextLong();
         long var9 = random.nextLong();
         long var11 = integer3 * var7;
         long var13 = integer4 * var9;
         random.setSeed(var11 ^ var13 ^ world.getSeed());
         int var15 = integer3 * 16 + 8 - 29;
         int var16 = integer4 * 16 + 8 - 29;
         EnumFacing var17 = EnumFacing.Plane.HORIZONTAL.random(random);
         this.components.add(new StructureOceanMonumentPieces.MonumentBuilding(random, var15, var16, var17));
         this.updateBoundingBox();
         this.field_175790_d = true;
      }

      @Override
      public void generateStructure(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (!this.field_175790_d) {
            this.components.clear();
            this.func_175789_b(world, random, this.getChunkPosX(), this.getChunkPosZ());
         }

         super.generateStructure(world, random, structureBoundingBox);
      }

      @Override
      public boolean func_175788_a(ChunkCoordIntPair chunkCoordIntPair) {
         return !this.field_175791_c.contains(chunkCoordIntPair) && super.func_175788_a(chunkCoordIntPair);
      }

      @Override
      public void func_175787_b(ChunkCoordIntPair chunkCoordIntPair) {
         super.func_175787_b(chunkCoordIntPair);
         this.field_175791_c.add(chunkCoordIntPair);
      }

      @Override
      public void writeToNBT(NBTTagCompound nBTTagCompound) {
         super.writeToNBT(nBTTagCompound);
         NBTTagList var5 = new NBTTagList();

         for (ChunkCoordIntPair var7 : this.field_175791_c) {
            NBTTagCompound var8 = new NBTTagCompound();
            var8.setInteger("X", var7.chunkXPos);
            var8.setInteger("Z", var7.chunkZPos);
            var5.appendTag(var8);
         }

         nBTTagCompound.setTag("Processed", var5);
      }

      @Override
      public void readFromNBT(NBTTagCompound nBTTagCompound) {
         super.readFromNBT(nBTTagCompound);
         if (nBTTagCompound.hasKey("Processed", 9)) {
            NBTTagList var5 = nBTTagCompound.getTagList("Processed", 10);

            for (int var6 = 0; var6 < var5.tagCount(); var6++) {
               NBTTagCompound var7 = var5.getCompoundTagAt(var6);
               this.field_175791_c.add(new ChunkCoordIntPair(var7.getInteger("X"), var7.getInteger("Z")));
            }
         }
      }
   }
}
