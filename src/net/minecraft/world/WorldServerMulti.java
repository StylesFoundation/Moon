package net.minecraft.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.ISaveHandler;

public class WorldServerMulti extends WorldServer {
   private final WorldServer delegate;

   public WorldServerMulti(MinecraftServer minecraftServer, ISaveHandler iSaveHandler, int integer, WorldServer worldServer, Profiler profiler) {
      super(minecraftServer, iSaveHandler, new DerivedWorldInfo(worldServer.getWorldInfo()), integer, profiler);
      this.delegate = worldServer;
      worldServer.getWorldBorder().addListener(new IBorderListener() {
         @Override
         public void onSizeChanged(WorldBorder worldBorder, double double2) {
            WorldServerMulti.this.getWorldBorder().setTransition(double2);
         }

         @Override
         public void onTransitionStarted(WorldBorder worldBorder, double double2, double double3, long long4) {
            WorldServerMulti.this.getWorldBorder().setTransition(double2, double3, long4);
         }

         @Override
         public void onCenterChanged(WorldBorder worldBorder, double double2, double double3) {
            WorldServerMulti.this.getWorldBorder().setCenter(double2, double3);
         }

         @Override
         public void onWarningTimeChanged(WorldBorder worldBorder, int integer) {
            WorldServerMulti.this.getWorldBorder().setWarningTime(integer);
         }

         @Override
         public void onWarningDistanceChanged(WorldBorder worldBorder, int integer) {
            WorldServerMulti.this.getWorldBorder().setWarningDistance(integer);
         }

         @Override
         public void onDamageAmountChanged(WorldBorder worldBorder, double double2) {
            WorldServerMulti.this.getWorldBorder().setDamageAmount(double2);
         }

         @Override
         public void onDamageBufferChanged(WorldBorder worldBorder, double double2) {
            WorldServerMulti.this.getWorldBorder().setDamageBuffer(double2);
         }
      });
   }

   @Override
   protected void saveLevel() throws MinecraftException {
   }

   @Override
   public World init() {
      this.mapStorage = this.delegate.getMapStorage();
      this.worldScoreboard = this.delegate.getScoreboard();
      String var4 = VillageCollection.Method1033(this.provider);
      VillageCollection var5 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, var4);
      if (var5 == null) {
         this.villageCollectionObj = new VillageCollection(this);
         this.mapStorage.setData(var4, this.villageCollectionObj);
      } else {
         this.villageCollectionObj = var5;
         this.villageCollectionObj.setWorldsForAll(this);
      }

      return this;
   }
}
