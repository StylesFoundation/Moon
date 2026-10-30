package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MapGenNetherBridge extends MapGenStructure {
   private final List<BiomeGenBase.SpawnListEntry> spawnList = Lists.newArrayList();

   public MapGenNetherBridge() {
      this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityBlaze.class, 10, 2, 3));
      this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
      this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
      this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
   }

   @Override
   public String getStructureName() {
      return "Fortress";
   }

   public List<BiomeGenBase.SpawnListEntry> getSpawnList() {
      return this.spawnList;
   }

   @Override
   protected boolean canSpawnStructureAtCoords(int integer1, int integer2) {
      int var6 = integer1 >> 4;
      int var7 = integer2 >> 4;
      this.rand.setSeed(var6 ^ var7 << 4 ^ this.worldObj.getSeed());
      this.rand.nextInt();
      return this.rand.nextInt(3) == 0 && integer1 == (var6 << 4) + 4 + this.rand.nextInt(8) && integer2 == (var7 << 4) + 4 + this.rand.nextInt(8);
   }

   @Override
   protected StructureStart getStructureStart(int integer1, int integer2) {
      return new MapGenNetherBridge.Start(this.worldObj, this.rand, integer1, integer2);
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(World world, Random random, int integer3, int integer4) {
         super(integer3, integer4);
         StructureNetherBridgePieces.Start var8 = new StructureNetherBridgePieces.Start(random, (integer3 << 4) + 2, (integer4 << 4) + 2);
         this.components.add(var8);
         var8.buildComponent(var8, this.components, random);
         List var9 = var8.field_74967_d;

         while (!var9.isEmpty()) {
            int var10 = random.nextInt(var9.size());
            StructureComponent var11 = (StructureComponent)var9.remove(var10);
            var11.buildComponent(var8, this.components, random);
         }

         this.updateBoundingBox();
         this.setRandomHeight(world, random, 48, 70);
      }
   }
}
