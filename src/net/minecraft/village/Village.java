package net.minecraft.village;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Village {
   private World worldObj;
   private final List<VillageDoorInfo> villageDoorInfoList = Lists.newArrayList();
   private BlockPos centerHelper = BlockPos.ORIGIN;
   private BlockPos center = BlockPos.ORIGIN;
   private int villageRadius;
   private int lastAddDoorTimestamp;
   private int tickCounter;
   private int numVillagers;
   private int noBreedTicks;
   private final TreeMap<String, Integer> playerReputation = new TreeMap<>();
   private final List<Village.VillageAggressor> villageAgressors = Lists.newArrayList();
   private int numIronGolems;

   public Village() {
   }

   public Village(World world) {
      this.worldObj = world;
   }

   public void setWorld(World world) {
      this.worldObj = world;
   }

   public void tick(int integer) {
      this.tickCounter = integer;
      this.removeDeadAndOutOfRangeDoors();
      this.Method337();
      if (integer % 20 == 0) {
         this.updateNumVillagers();
      }

      if (integer % 30 == 0) {
         this.updateNumIronGolems();
      }

      int var5 = this.numVillagers / 10;
      if (this.numIronGolems < var5 && this.villageDoorInfoList.size() > 20 && this.worldObj.rand.nextInt(7000) == 0) {
         Vec3 var6 = this.func_179862_a(this.center, 2, 4, 2);
         if (var6 != null) {
            EntityIronGolem var7 = new EntityIronGolem(this.worldObj);
            var7.setPosition(var6.xCoord, var6.yCoord, var6.zCoord);
            this.worldObj.spawnEntityInWorld(var7);
            this.numIronGolems++;
         }
      }
   }

   private Vec3 func_179862_a(BlockPos blockPos, int integer2, int integer3, int integer4) {
      for (int var8 = 0; var8 < 10; var8++) {
         BlockPos var9 = blockPos.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
         if (this.func_179866_a(var9) && this.func_179861_a(new BlockPos(integer2, integer3, integer4), var9)) {
            return new Vec3(var9.getX(), var9.getY(), var9.getZ());
         }
      }

      return null;
   }

   private boolean func_179861_a(BlockPos blockPos1, BlockPos blockPos2) {
      if (!World.doesBlockHaveSolidTopSurface(this.worldObj, blockPos2.down())) {
         return false;
      }

      int var6 = blockPos2.getX() - blockPos1.getX() / 2;
      int var7 = blockPos2.getZ() - blockPos1.getZ() / 2;

      for (int var8 = var6; var8 < var6 + blockPos1.getX(); var8++) {
         for (int var9 = blockPos2.getY(); var9 < blockPos2.getY() + blockPos1.getY(); var9++) {
            for (int var10 = var7; var10 < var7 + blockPos1.getZ(); var10++) {
               if (this.worldObj.getBlockState(new BlockPos(var8, var9, var10)).getBlock().isNormalCube()) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private void updateNumIronGolems() {
      List var1 = this.worldObj
         .getEntitiesWithinAABB(
            EntityIronGolem.class,
            new AxisAlignedBB(
               this.center.getX() - this.villageRadius,
               this.center.getY() - 4,
               this.center.getZ() - this.villageRadius,
               this.center.getX() + this.villageRadius,
               this.center.getY() + 4,
               this.center.getZ() + this.villageRadius
            )
         );
      this.numIronGolems = var1.size();
   }

   private void updateNumVillagers() {
      List var4 = this.worldObj
         .getEntitiesWithinAABB(
            EntityVillager.class,
            new AxisAlignedBB(
               this.center.getX() - this.villageRadius,
               this.center.getY() - 4,
               this.center.getZ() - this.villageRadius,
               this.center.getX() + this.villageRadius,
               this.center.getY() + 4,
               this.center.getZ() + this.villageRadius
            )
         );
      this.numVillagers = var4.size();
      if (this.numVillagers == 0) {
         this.playerReputation.clear();
      }
   }

   public BlockPos getCenter() {
      return this.center;
   }

   public int getVillageRadius() {
      return this.villageRadius;
   }

   public int getNumVillageDoors() {
      return this.villageDoorInfoList.size();
   }

   public int getTicksSinceLastDoorAdding() {
      return this.tickCounter - this.lastAddDoorTimestamp;
   }

   public int getNumVillagers() {
      return this.numVillagers;
   }

   public boolean func_179866_a(BlockPos blockPos) {
      return this.center.distanceSq(blockPos) < this.villageRadius * this.villageRadius;
   }

   public List<VillageDoorInfo> getVillageDoorInfoList() {
      return this.villageDoorInfoList;
   }

   public VillageDoorInfo getNearestDoor(BlockPos blockPos) {
      VillageDoorInfo var5 = null;
      int var6 = Integer.MAX_VALUE;

      for (VillageDoorInfo var8 : this.villageDoorInfoList) {
         int var9 = var8.getDistanceToDoorBlockSq(blockPos);
         if (var9 < var6) {
            var5 = var8;
            var6 = var9;
         }
      }

      return var5;
   }

   public VillageDoorInfo getDoorInfo(BlockPos blockPos) {
      VillageDoorInfo var5 = null;
      int var6 = Integer.MAX_VALUE;

      for (VillageDoorInfo var8 : this.villageDoorInfoList) {
         int var9 = var8.getDistanceToDoorBlockSq(blockPos);
         if (var9 > 256) {
            var9 *= 1000;
         } else {
            var9 = var8.getDoorOpeningRestrictionCounter();
         }

         if (var9 < var6) {
            var5 = var8;
            var6 = var9;
         }
      }

      return var5;
   }

   public VillageDoorInfo getExistedDoor(BlockPos blockPos) {
      if (this.center.distanceSq(blockPos) > this.villageRadius * this.villageRadius) {
         return null;
      }

      for (VillageDoorInfo var6 : this.villageDoorInfoList) {
         if (var6.getDoorBlockPos().getX() == blockPos.getX()
            && var6.getDoorBlockPos().getZ() == blockPos.getZ()
            && Math.abs(var6.getDoorBlockPos().getY() - blockPos.getY()) <= 1) {
            return var6;
         }
      }

      return null;
   }

   public void addVillageDoorInfo(VillageDoorInfo villageDoorInfo) {
      this.villageDoorInfoList.add(villageDoorInfo);
      this.centerHelper = this.centerHelper.add(villageDoorInfo.getDoorBlockPos());
      this.updateVillageRadiusAndCenter();
      this.lastAddDoorTimestamp = villageDoorInfo.getInsidePosY();
   }

   public boolean isAnnihilated() {
      return this.villageDoorInfoList.isEmpty();
   }

   public void addOrRenewAgressor(EntityLivingBase entityLivingBase) {
      for (Village.VillageAggressor var6 : this.villageAgressors) {
         if (var6.agressor == entityLivingBase) {
            var6.agressionTime = this.tickCounter;
            return;
         }
      }

      this.villageAgressors.add(new Village.VillageAggressor(entityLivingBase, this.tickCounter));
   }

   public EntityLivingBase findNearestVillageAggressor(EntityLivingBase entityLivingBase) {
      double var5 = Double.MAX_VALUE;
      Village.VillageAggressor var7 = null;

      for (Village.VillageAggressor var9 : this.villageAgressors) {
         double var10 = var9.agressor.getDistanceSqToEntity(entityLivingBase);
         if (var10 <= var5) {
            var7 = var9;
            var5 = var10;
         }
      }

      return var7 != null ? var7.agressor : null;
   }

   public EntityPlayer getNearestTargetPlayer(EntityLivingBase entityLivingBase) {
      double var5 = Double.MAX_VALUE;
      EntityPlayer var7 = null;

      for (String var9 : this.playerReputation.keySet()) {
         if (this.isPlayerReputationTooLow(var9)) {
            EntityPlayer var10 = this.worldObj.getPlayerEntityByName(var9);
            if (var10 != null) {
               double var11 = var10.getDistanceSqToEntity(entityLivingBase);
               if (var11 <= var5) {
                  var7 = var10;
                  var5 = var11;
               }
            }
         }
      }

      return var7;
   }

   private void Method337() {
      this.villageAgressors
         .removeIf(villageAggressor -> !villageAggressor.agressor.isEntityAlive() || Math.abs(this.tickCounter - villageAggressor.agressionTime) > 300);
   }

   private void removeDeadAndOutOfRangeDoors() {
      boolean var4 = false;
      boolean var5 = this.worldObj.rand.nextInt(50) == 0;
      Iterator var6 = this.villageDoorInfoList.iterator();

      while (var6.hasNext()) {
         VillageDoorInfo var7 = (VillageDoorInfo)var6.next();
         if (var5) {
            var7.resetDoorOpeningRestrictionCounter();
         }

         if (!this.isWoodDoor(var7.getDoorBlockPos()) || Math.abs(this.tickCounter - var7.getInsidePosY()) > 1200) {
            this.centerHelper = this.centerHelper.subtract(var7.getDoorBlockPos());
            var4 = true;
            var7.setIsDetachedFromVillageFlag(true);
            var6.remove();
         }
      }

      if (var4) {
         this.updateVillageRadiusAndCenter();
      }
   }

   private boolean isWoodDoor(BlockPos blockPos) {
      Block var5 = this.worldObj.getBlockState(blockPos).getBlock();
      return var5 instanceof BlockDoor && var5.getMaterial() == Material.wood;
   }

   private void updateVillageRadiusAndCenter() {
      int var4 = this.villageDoorInfoList.size();
      if (var4 == 0) {
         this.center = new BlockPos(0, 0, 0);
         this.villageRadius = 0;
      } else {
         this.center = new BlockPos(this.centerHelper.getX() / var4, this.centerHelper.getY() / var4, this.centerHelper.getZ() / var4);
         int var5 = 0;
         Iterator var6 = this.villageDoorInfoList.iterator();

         while (var6.hasNext()) {
            var5 = Math.max(((VillageDoorInfo)var6.next()).getDistanceToDoorBlockSq(this.center), var5);
         }

         this.villageRadius = Math.max(32, (int)Math.sqrt(var5) + 1);
      }
   }

   public int getReputationForPlayer(String string) {
      Integer var5 = this.playerReputation.get(string);
      return var5 != null ? var5 : 0;
   }

   public int setReputationForPlayer(String string, int integer) {
      int var6 = MathHelper.clamp_int(this.getReputationForPlayer(string) + integer, -30, 10);
      this.playerReputation.put(string, var6);
      return var6;
   }

   public boolean isPlayerReputationTooLow(String string) {
      return this.getReputationForPlayer(string) <= -15;
   }

   public void readVillageDataFromNBT(NBTTagCompound nBTTagCompound) {
      this.numVillagers = nBTTagCompound.getInteger("PopSize");
      this.villageRadius = nBTTagCompound.getInteger("Radius");
      this.numIronGolems = nBTTagCompound.getInteger("Golems");
      this.lastAddDoorTimestamp = nBTTagCompound.getInteger("Stable");
      this.tickCounter = nBTTagCompound.getInteger("Tick");
      this.noBreedTicks = nBTTagCompound.getInteger("MTick");
      this.center = new BlockPos(nBTTagCompound.getInteger("CX"), nBTTagCompound.getInteger("CY"), nBTTagCompound.getInteger("CZ"));
      this.centerHelper = new BlockPos(nBTTagCompound.getInteger("ACX"), nBTTagCompound.getInteger("ACY"), nBTTagCompound.getInteger("ACZ"));
      NBTTagList var5 = nBTTagCompound.getTagList("Doors", 10);

      for (int var6 = 0; var6 < var5.tagCount(); var6++) {
         NBTTagCompound var7 = var5.getCompoundTagAt(var6);
         VillageDoorInfo var8 = new VillageDoorInfo(
            new BlockPos(var7.getInteger("X"), var7.getInteger("Y"), var7.getInteger("Z")),
            var7.getInteger("IDX"),
            var7.getInteger("IDZ"),
            var7.getInteger("TS")
         );
         this.villageDoorInfoList.add(var8);
      }

      NBTTagList var11 = nBTTagCompound.getTagList("Players", 10);

      for (int var12 = 0; var12 < var11.tagCount(); var12++) {
         NBTTagCompound var13 = var11.getCompoundTagAt(var12);
         if (var13.hasKey("UUID")) {
            PlayerProfileCache var9 = MinecraftServer.getServer().getPlayerProfileCache();
            GameProfile var10 = var9.getProfileByUUID(UUID.fromString(var13.getString("UUID")));
            if (var10 != null) {
               this.playerReputation.put(var10.getName(), var13.getInteger("S"));
            }
         } else {
            this.playerReputation.put(var13.getString("Name"), var13.getInteger("S"));
         }
      }
   }

   public void writeVillageDataToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setInteger("PopSize", this.numVillagers);
      nBTTagCompound.setInteger("Radius", this.villageRadius);
      nBTTagCompound.setInteger("Golems", this.numIronGolems);
      nBTTagCompound.setInteger("Stable", this.lastAddDoorTimestamp);
      nBTTagCompound.setInteger("Tick", this.tickCounter);
      nBTTagCompound.setInteger("MTick", this.noBreedTicks);
      nBTTagCompound.setInteger("CX", this.center.getX());
      nBTTagCompound.setInteger("CY", this.center.getY());
      nBTTagCompound.setInteger("CZ", this.center.getZ());
      nBTTagCompound.setInteger("ACX", this.centerHelper.getX());
      nBTTagCompound.setInteger("ACY", this.centerHelper.getY());
      nBTTagCompound.setInteger("ACZ", this.centerHelper.getZ());
      NBTTagList var5 = new NBTTagList();

      for (VillageDoorInfo var7 : this.villageDoorInfoList) {
         NBTTagCompound var8 = new NBTTagCompound();
         var8.setInteger("X", var7.getDoorBlockPos().getX());
         var8.setInteger("Y", var7.getDoorBlockPos().getY());
         var8.setInteger("Z", var7.getDoorBlockPos().getZ());
         var8.setInteger("IDX", var7.getInsideOffsetX());
         var8.setInteger("IDZ", var7.getInsideOffsetZ());
         var8.setInteger("TS", var7.getInsidePosY());
         var5.appendTag(var8);
      }

      nBTTagCompound.setTag("Doors", var5);
      NBTTagList var12 = new NBTTagList();

      for (String var14 : this.playerReputation.keySet()) {
         NBTTagCompound var9 = new NBTTagCompound();
         PlayerProfileCache var10 = MinecraftServer.getServer().getPlayerProfileCache();
         GameProfile var11 = var10.getGameProfileForUsername(var14);
         if (var11 != null) {
            var9.setString("UUID", var11.getId().toString());
            var9.setInteger("S", this.playerReputation.get(var14));
            var12.appendTag(var9);
         }
      }

      nBTTagCompound.setTag("Players", var12);
   }

   public void endMatingSeason() {
      this.noBreedTicks = this.tickCounter;
   }

   public boolean isMatingSeason() {
      return this.noBreedTicks == 0 || this.tickCounter - this.noBreedTicks >= 3600;
   }

   public void setDefaultPlayerReputation(int integer) {
      for (String var6 : this.playerReputation.keySet()) {
         this.setReputationForPlayer(var6, integer);
      }
   }

   static class VillageAggressor {
      public EntityLivingBase agressor;
      public int agressionTime;

      VillageAggressor(EntityLivingBase entityLivingBase, int integer) {
         this.agressor = entityLivingBase;
         this.agressionTime = integer;
      }
   }
}
