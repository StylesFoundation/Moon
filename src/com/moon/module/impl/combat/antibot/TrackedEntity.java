package com.moon.module.impl.combat.antibot;

public class TrackedEntity {
   private final long spawnTime;
   private final int entityId;
   private final double spawnDistance;
   private long lastPingTime;
   private int swingCount;
   private boolean madeStepSound;
   private boolean moved;

   public void setLastPingTime(long lastPingTime) {
      this.lastPingTime = lastPingTime;
   }

   public void incrementSwing() {
      this.swingCount++;
   }

   public void markStepSound() {
      this.madeStepSound = true;
   }

   public void markMoved() {
      this.moved = true;
   }

   public long getSpawnTime() {
      return this.spawnTime;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public double getSpawnDistance() {
      return this.spawnDistance;
   }

   public long getLastPingTime() {
      return this.lastPingTime;
   }

   public int getSwingCount() {
      return this.swingCount;
   }

   public boolean hasMadeStepSound() {
      return this.madeStepSound;
   }

   public boolean hasMoved() {
      return this.moved;
   }

   public TrackedEntity(long spawnTime, int entityId, double spawnDistance) {
      this.spawnTime = spawnTime;
      this.entityId = entityId;
      this.spawnDistance = spawnDistance;
   }

   public static boolean alwaysFalse() {
      return false;
   }

   static {
      if (alwaysFalse()) {
      }
   }
}
