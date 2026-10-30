package net.minecraft.village;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;

public class VillageCollection extends WorldSavedData {
   private World worldObj;
   private final List<BlockPos> villagerPositionsList = Lists.newArrayList();
   private final List<VillageDoorInfo> newDoors = Lists.newArrayList();
   private final List<Village> villageList = Lists.newArrayList();
   private int tickCounter;

   public VillageCollection(String string) {
      super(string);
   }

   public VillageCollection(World world) {
      super(Method1033(world.provider));
      this.worldObj = world;
      this.markDirty();
   }

   public void setWorldsForAll(World world) {
      this.worldObj = world;
      Iterator var5 = this.villageList.iterator();

      while (var5.hasNext()) {
         ((Village)var5.next()).setWorld(world);
      }
   }

   public void addToVillagerPositionList(BlockPos blockPos) {
      if (this.villagerPositionsList.size() <= 64 && !this.positionInList(blockPos)) {
         this.villagerPositionsList.add(blockPos);
      }
   }

   public void tick() {
      this.tickCounter++;

      for (Village var5 : this.villageList) {
         var5.tick(this.tickCounter);
      }

      this.removeAnnihilatedVillages();
      this.dropOldestVillagerPosition();
      this.addNewDoorsToVillageOrCreateVillage();
      if (this.tickCounter % 400 == 0) {
         this.markDirty();
      }
   }

   private void removeAnnihilatedVillages() {
      Iterator var4 = this.villageList.iterator();

      while (var4.hasNext()) {
         Village var5 = (Village)var4.next();
         if (var5.isAnnihilated()) {
            var4.remove();
            this.markDirty();
         }
      }
   }

   public List<Village> getVillageList() {
      return this.villageList;
   }

   public Village getNearestVillage(BlockPos blockPos, int integer) {
      Village var6 = null;
      double var7 = Float.MAX_VALUE;

      for (Village var10 : this.villageList) {
         double var11 = var10.getCenter().distanceSq(blockPos);
         if (var11 < var7) {
            float var13 = integer + var10.getVillageRadius();
            if (var11 <= var13 * var13) {
               var6 = var10;
               var7 = var11;
            }
         }
      }

      return var6;
   }

   private void dropOldestVillagerPosition() {
      if (!this.villagerPositionsList.isEmpty()) {
         this.addDoorsAround(this.villagerPositionsList.remove(0));
      }
   }

   private void addNewDoorsToVillageOrCreateVillage() {
      for (VillageDoorInfo var5 : this.newDoors) {
         Village var6 = this.getNearestVillage(var5.getDoorBlockPos(), 32);
         if (var6 == null) {
            var6 = new Village(this.worldObj);
            this.villageList.add(var6);
            this.markDirty();
         }

         var6.addVillageDoorInfo(var5);
      }

      this.newDoors.clear();
   }

   private void addDoorsAround(BlockPos blockPos) {
      byte var5 = 16;
      byte var7 = 16;

      for (int var8 = -16; var8 < var5; var8++) {
         for (int var9 = -4; var9 < 4; var9++) {
            for (int var10 = -16; var10 < var7; var10++) {
               BlockPos var11 = blockPos.add(var8, var9, var10);
               if (this.isWoodDoor(var11)) {
                  VillageDoorInfo var12 = this.checkDoorExistence(var11);
                  if (var12 == null) {
                     this.addToNewDoorsList(var11);
                  } else {
                     var12.func_179849_a(this.tickCounter);
                  }
               }
            }
         }
      }
   }

   private VillageDoorInfo checkDoorExistence(BlockPos blockPos) {
      for (VillageDoorInfo var6 : this.newDoors) {
         if (var6.getDoorBlockPos().getX() == blockPos.getX()
            && var6.getDoorBlockPos().getZ() == blockPos.getZ()
            && Math.abs(var6.getDoorBlockPos().getY() - blockPos.getY()) <= 1) {
            return var6;
         }
      }

      Iterator var8 = this.villageList.iterator();

      while (var8.hasNext()) {
         VillageDoorInfo var7 = ((Village)var8.next()).getExistedDoor(blockPos);
         if (var7 != null) {
            return var7;
         }
      }

      return null;
   }

   private void addToNewDoorsList(BlockPos blockPos) {
      EnumFacing var4 = BlockDoor.getFacing(this.worldObj, blockPos);
      EnumFacing var5 = var4.getOpposite();
      int var6 = this.countBlocksCanSeeSky(blockPos, var4, 5);
      int var7 = this.countBlocksCanSeeSky(blockPos, var5, var6 + 1);
      if (var6 != var7) {
         this.newDoors.add(new VillageDoorInfo(blockPos, var6 < var7 ? var4 : var5, this.tickCounter));
      }
   }

   private int countBlocksCanSeeSky(BlockPos blockPos, EnumFacing enumFacing, int integer) {
      int var7 = 0;

      for (int var8 = 1; var8 <= 5; var8++) {
         if (this.worldObj.canSeeSky(blockPos.offset(enumFacing, var8))) {
            if (++var7 >= integer) {
               return var7;
            }
         }
      }

      return var7;
   }

   private boolean positionInList(BlockPos blockPos) {
      for (BlockPos var6 : this.villagerPositionsList) {
         if (var6.equals(blockPos)) {
            return true;
         }
      }

      return false;
   }

   private boolean isWoodDoor(BlockPos blockPos) {
      Block var5 = this.worldObj.getBlockState(blockPos).getBlock();
      return var5 instanceof BlockDoor && var5.getMaterial() == Material.wood;
   }

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      this.tickCounter = nBTTagCompound.getInteger("Tick");
      NBTTagList var5 = nBTTagCompound.getTagList("Villages", 10);

      for (int var6 = 0; var6 < var5.tagCount(); var6++) {
         NBTTagCompound var7 = var5.getCompoundTagAt(var6);
         Village var8 = new Village();
         var8.readVillageDataFromNBT(var7);
         this.villageList.add(var8);
      }
   }

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setInteger("Tick", this.tickCounter);
      NBTTagList var5 = new NBTTagList();

      for (Village var7 : this.villageList) {
         NBTTagCompound var8 = new NBTTagCompound();
         var7.writeVillageDataToNBT(var8);
         var5.appendTag(var8);
      }

      nBTTagCompound.setTag("Villages", var5);
   }

   public static String Method1033(WorldProvider worldProvider) {
      return "villages" + worldProvider.getInternalNameSuffix();
   }
}
