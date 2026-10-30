package net.minecraft.world.gen.structure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public abstract class StructureStart {
   protected LinkedList<StructureComponent> components = new LinkedList<>();
   protected StructureBoundingBox boundingBox;
   private int chunkPosX;
   private int chunkPosZ;

   public StructureStart() {
   }

   public StructureStart(int integer1, int integer2) {
      this.chunkPosX = integer1;
      this.chunkPosZ = integer2;
   }

   public StructureBoundingBox getBoundingBox() {
      return this.boundingBox;
   }

   public LinkedList<StructureComponent> getComponents() {
      return this.components;
   }

   public void generateStructure(World world, Random random, StructureBoundingBox structureBoundingBox) {
      this.components
         .removeIf(
            structureComponent -> structureComponent.getBoundingBox().intersectsWith(structureBoundingBox)
               && !structureComponent.addComponentParts(world, random, structureBoundingBox)
         );
   }

   protected void updateBoundingBox() {
      this.boundingBox = StructureBoundingBox.getNewBoundingBox();

      for (StructureComponent var5 : this.components) {
         this.boundingBox.expandTo(var5.getBoundingBox());
      }
   }

   public NBTTagCompound writeStructureComponentsToNBT(int integer1, int integer2) {
      NBTTagCompound var6 = new NBTTagCompound();
      var6.setString("id", MapGenStructureIO.getStructureStartName(this));
      var6.setInteger("ChunkX", integer1);
      var6.setInteger("ChunkZ", integer2);
      var6.setTag("BB", this.boundingBox.toNBTTagIntArray());
      NBTTagList var7 = new NBTTagList();

      for (StructureComponent var9 : this.components) {
         var7.appendTag(var9.createStructureBaseNBT());
      }

      var6.setTag("Children", var7);
      this.writeToNBT(var6);
      return var6;
   }

   public void writeToNBT(NBTTagCompound nBTTagCompound) {
   }

   public void readStructureComponentsFromNBT(World world, NBTTagCompound nBTTagCompound) {
      this.chunkPosX = nBTTagCompound.getInteger("ChunkX");
      this.chunkPosZ = nBTTagCompound.getInteger("ChunkZ");
      if (nBTTagCompound.hasKey("BB")) {
         this.boundingBox = new StructureBoundingBox(nBTTagCompound.getIntArray("BB"));
      }

      NBTTagList var6 = nBTTagCompound.getTagList("Children", 10);

      for (int var7 = 0; var7 < var6.tagCount(); var7++) {
         this.components.add(MapGenStructureIO.getStructureComponent(var6.getCompoundTagAt(var7), world));
      }

      this.readFromNBT(nBTTagCompound);
   }

   public void readFromNBT(NBTTagCompound nBTTagCompound) {
   }

   protected void markAvailableHeight(World world, Random random, int integer) {
      int var7 = world.getSeaLevel() - integer;
      int var8 = this.boundingBox.getYSize() + 1;
      if (var8 < var7) {
         var8 += random.nextInt(var7 - var8);
      }

      int var9 = var8 - this.boundingBox.maxY;
      this.boundingBox.offset(0, var9, 0);
      Iterator var10 = this.components.iterator();

      while (var10.hasNext()) {
         ((StructureComponent)var10.next()).func_181138_a(0, var9, 0);
      }
   }

   protected void setRandomHeight(World world, Random random, int integer3, int integer4) {
      int var8 = integer4 - integer3 + 1 - this.boundingBox.getYSize();
      int var9;
      if (var8 > 1) {
         var9 = integer3 + random.nextInt(var8);
      } else {
         var9 = integer3;
      }

      int var10 = var9 - this.boundingBox.minY;
      this.boundingBox.offset(0, var10, 0);
      Iterator var11 = this.components.iterator();

      while (var11.hasNext()) {
         ((StructureComponent)var11.next()).func_181138_a(0, var10, 0);
      }
   }

   public boolean isSizeableStructure() {
      return true;
   }

   public boolean func_175788_a(ChunkCoordIntPair chunkCoordIntPair) {
      return true;
   }

   public void func_175787_b(ChunkCoordIntPair chunkCoordIntPair) {
   }

   public int getChunkPosX() {
      return this.chunkPosX;
   }

   public int getChunkPosZ() {
      return this.chunkPosZ;
   }
}
