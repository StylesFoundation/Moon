package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StringUtils;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public abstract class MobSpawnerBaseLogic {
   private int spawnDelay = 20;
   private String mobID = "Pig";
   private final List<MobSpawnerBaseLogic.WeightedRandomMinecart> minecartToSpawn = Lists.newArrayList();
   private MobSpawnerBaseLogic.WeightedRandomMinecart randomEntity;
   private double mobRotation;
   private double prevMobRotation;
   private int minSpawnDelay = 200;
   private int maxSpawnDelay = 800;
   private int spawnCount = 4;
   private Entity cachedEntity;
   private int maxNearbyEntities = 6;
   private int activatingRangeFromPlayer = 16;
   private int spawnRange = 4;

   private String getEntityNameToSpawn() {
      if (this.getRandomEntity() == null) {
         if (this.mobID != null && this.mobID.equals("Minecart")) {
            this.mobID = "MinecartRideable";
         }

         return this.mobID;
      } else {
         return this.getRandomEntity().entityType;
      }
   }

   public void setEntityName(String string) {
      this.mobID = string;
   }

   private boolean isActivated() {
      BlockPos var1 = this.getSpawnerPosition();
      return this.getSpawnerWorld().isAnyPlayerWithinRangeAt(var1.getX() + 0.5, var1.getY() + 0.5, var1.getZ() + 0.5, this.activatingRangeFromPlayer);
   }

   public void updateSpawner() {
      if (this.isActivated()) {
         BlockPos var4 = this.getSpawnerPosition();
         if (this.getSpawnerWorld().isRemote) {
            double var5 = var4.getX() + this.getSpawnerWorld().rand.nextFloat();
            double var7 = var4.getY() + this.getSpawnerWorld().rand.nextFloat();
            double var9 = var4.getZ() + this.getSpawnerWorld().rand.nextFloat();
            this.getSpawnerWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var5, var7, var9, 0.0, 0.0, 0.0);
            this.getSpawnerWorld().spawnParticle(EnumParticleTypes.FLAME, var5, var7, var9, 0.0, 0.0, 0.0);
            if (this.spawnDelay > 0) {
               this.spawnDelay--;
            }

            this.prevMobRotation = this.mobRotation;
            this.mobRotation = (this.mobRotation + 1000.0F / (this.spawnDelay + 200.0F)) % 360.0;
         } else {
            if (this.spawnDelay == -1) {
               this.resetTimer();
            }

            if (this.spawnDelay > 0) {
               this.spawnDelay--;
               return;
            }

            boolean var16 = false;

            for (int var6 = 0; var6 < this.spawnCount; var6++) {
               Entity var17 = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.getSpawnerWorld());
               if (var17 == null) {
                  return;
               }

               int var8 = this.getSpawnerWorld()
                  .getEntitiesWithinAABB(
                     var17.getClass(),
                     new AxisAlignedBB(var4.getX(), var4.getY(), var4.getZ(), var4.getX() + 1, var4.getY() + 1, var4.getZ() + 1)
                        .expand(this.spawnRange, this.spawnRange, this.spawnRange)
                  )
                  .size();
               if (var8 >= this.maxNearbyEntities) {
                  this.resetTimer();
                  return;
               }

               double var18 = var4.getX() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * this.spawnRange + 0.5;
               double var11 = var4.getY() + this.getSpawnerWorld().rand.nextInt(3) - 1;
               double var13 = var4.getZ() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * this.spawnRange + 0.5;
               EntityLiving var15 = var17 instanceof EntityLiving ? (EntityLiving)var17 : null;
               var17.setLocationAndAngles(var18, var11, var13, this.getSpawnerWorld().rand.nextFloat() * 360.0F, 0.0F);
               if (var15 == null || var15.getCanSpawnHere() && var15.isNotColliding()) {
                  this.spawnNewEntity(var17, true);
                  this.getSpawnerWorld().playAuxSFX(2004, var4, 0);
                  if (var15 != null) {
                     var15.spawnExplosionParticle();
                  }

                  var16 = true;
               }
            }

            if (var16) {
               this.resetTimer();
            }
         }
      }
   }

   private Entity spawnNewEntity(Entity entity, boolean boolean2) {
      if (this.getRandomEntity() != null) {
         NBTTagCompound var6 = new NBTTagCompound();
         entity.writeToNBTOptional(var6);

         for (String var8 : this.getRandomEntity().nbtData.getKeySet()) {
            NBTBase var9 = this.getRandomEntity().nbtData.getTag(var8);
            var6.setTag(var8, var9.copy());
         }

         entity.readFromNBT(var6);
         if (entity.worldObj != null && boolean2) {
            entity.worldObj.spawnEntityInWorld(entity);
         }

         Entity var15 = entity;

         while (var6.hasKey("Riding", 10)) {
            NBTTagCompound var14 = var6.getCompoundTag("Riding");
            Entity var16 = EntityList.createEntityByName(var14.getString("id"), entity.worldObj);
            if (var16 != null) {
               NBTTagCompound var10 = new NBTTagCompound();
               var16.writeToNBTOptional(var10);

               for (String var12 : var14.getKeySet()) {
                  NBTBase var13 = var14.getTag(var12);
                  var10.setTag(var12, var13.copy());
               }

               var16.readFromNBT(var10);
               var16.setLocationAndAngles(var15.posX, var15.posY, var15.posZ, var15.rotationYaw, var15.rotationPitch);
               if (entity.worldObj != null && boolean2) {
                  entity.worldObj.spawnEntityInWorld(var16);
               }

               var15.mountEntity(var16);
            }

            var15 = var16;
            var6 = var14;
         }
      } else if (entity instanceof EntityLivingBase && entity.worldObj != null && boolean2) {
         if (entity instanceof EntityLiving) {
            ((EntityLiving)entity).onInitialSpawn(entity.worldObj.getDifficultyForLocation(new BlockPos(entity)), null);
         }

         entity.worldObj.spawnEntityInWorld(entity);
      }

      return entity;
   }

   private void resetTimer() {
      if (this.maxSpawnDelay <= this.minSpawnDelay) {
         this.spawnDelay = this.minSpawnDelay;
      } else {
         int var4 = this.maxSpawnDelay - this.minSpawnDelay;
         this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(var4);
      }

      if (this.minecartToSpawn.size() > 0) {
         this.setRandomEntity(WeightedRandom.getRandomItem(this.getSpawnerWorld().rand, this.minecartToSpawn));
      }

      this.func_98267_a(1);
   }

   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      this.mobID = nBTTagCompound.getString("EntityId");
      this.spawnDelay = nBTTagCompound.getShort("Delay");
      this.minecartToSpawn.clear();
      if (nBTTagCompound.hasKey("SpawnPotentials", 9)) {
         NBTTagList var5 = nBTTagCompound.getTagList("SpawnPotentials", 10);

         for (int var6 = 0; var6 < var5.tagCount(); var6++) {
            this.minecartToSpawn.add(new MobSpawnerBaseLogic.WeightedRandomMinecart(var5.getCompoundTagAt(var6)));
         }
      }

      if (nBTTagCompound.hasKey("SpawnData", 10)) {
         this.setRandomEntity(new MobSpawnerBaseLogic.WeightedRandomMinecart(nBTTagCompound.getCompoundTag("SpawnData"), this.mobID));
      } else {
         this.setRandomEntity(null);
      }

      if (nBTTagCompound.hasKey("MinSpawnDelay", 99)) {
         this.minSpawnDelay = nBTTagCompound.getShort("MinSpawnDelay");
         this.maxSpawnDelay = nBTTagCompound.getShort("MaxSpawnDelay");
         this.spawnCount = nBTTagCompound.getShort("SpawnCount");
      }

      if (nBTTagCompound.hasKey("MaxNearbyEntities", 99)) {
         this.maxNearbyEntities = nBTTagCompound.getShort("MaxNearbyEntities");
         this.activatingRangeFromPlayer = nBTTagCompound.getShort("RequiredPlayerRange");
      }

      if (nBTTagCompound.hasKey("SpawnRange", 99)) {
         this.spawnRange = nBTTagCompound.getShort("SpawnRange");
      }

      if (this.getSpawnerWorld() != null) {
         this.cachedEntity = null;
      }
   }

   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      String var5 = this.getEntityNameToSpawn();
      if (!StringUtils.isNullOrEmpty(var5)) {
         nBTTagCompound.setString("EntityId", var5);
         nBTTagCompound.setShort("Delay", (short)this.spawnDelay);
         nBTTagCompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
         nBTTagCompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
         nBTTagCompound.setShort("SpawnCount", (short)this.spawnCount);
         nBTTagCompound.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
         nBTTagCompound.setShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
         nBTTagCompound.setShort("SpawnRange", (short)this.spawnRange);
         if (this.getRandomEntity() != null) {
            nBTTagCompound.setTag("SpawnData", this.getRandomEntity().nbtData.copy());
         }

         if (this.getRandomEntity() != null || this.minecartToSpawn.size() > 0) {
            NBTTagList var6 = new NBTTagList();
            if (this.minecartToSpawn.size() > 0) {
               for (MobSpawnerBaseLogic.WeightedRandomMinecart var8 : this.minecartToSpawn) {
                  var6.appendTag(var8.toNBT());
               }
            } else {
               var6.appendTag(this.getRandomEntity().toNBT());
            }

            nBTTagCompound.setTag("SpawnPotentials", var6);
         }
      }
   }

   public Entity func_180612_a(World world) {
      if (this.cachedEntity == null) {
         Entity var5 = EntityList.createEntityByName(this.getEntityNameToSpawn(), world);
         if (var5 != null) {
            var5 = this.spawnNewEntity(var5, false);
            this.cachedEntity = var5;
         }
      }

      return this.cachedEntity;
   }

   public boolean setDelayToMin(int integer) {
      if (integer == 1 && this.getSpawnerWorld().isRemote) {
         this.spawnDelay = this.minSpawnDelay;
         return true;
      } else {
         return false;
      }
   }

   private MobSpawnerBaseLogic.WeightedRandomMinecart getRandomEntity() {
      return this.randomEntity;
   }

   public void setRandomEntity(MobSpawnerBaseLogic.WeightedRandomMinecart weightedRandomMinecart) {
      this.randomEntity = weightedRandomMinecart;
   }

   public abstract void func_98267_a(int integer);

   public abstract World getSpawnerWorld();

   public abstract BlockPos getSpawnerPosition();

   public double getMobRotation() {
      return this.mobRotation;
   }

   public double getPrevMobRotation() {
      return this.prevMobRotation;
   }

   public static class WeightedRandomMinecart extends WeightedRandom.Item {
      private final NBTTagCompound nbtData;
      private final String entityType;

      public WeightedRandomMinecart(NBTTagCompound nBTTagCompound) {
         this(nBTTagCompound.getCompoundTag("Properties"), nBTTagCompound.getString("Type"), nBTTagCompound.getInteger("Weight"));
      }

      public WeightedRandomMinecart(NBTTagCompound nBTTagCompound, String string) {
         this(nBTTagCompound, string, 1);
      }

      private WeightedRandomMinecart(NBTTagCompound nBTTagCompound, String string, int integer) {
         super(integer);
         if (string.equals("Minecart")) {
            if (nBTTagCompound != null) {
               string = EntityMinecart.EnumMinecartType.byNetworkID(nBTTagCompound.getInteger("Type")).getName();
            } else {
               string = "MinecartRideable";
            }
         }

         this.nbtData = nBTTagCompound;
         this.entityType = string;
      }

      public NBTTagCompound toNBT() {
         NBTTagCompound var1 = new NBTTagCompound();
         var1.setTag("Properties", this.nbtData);
         var1.setString("Type", this.entityType);
         var1.setInteger("Weight", this.itemWeight);
         return var1;
      }
   }
}
