package net.minecraft.world.border;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;

public class WorldBorder {
   private final List<IBorderListener> listeners = Lists.newArrayList();
   private double centerX = 0.0;
   private double centerZ = 0.0;
   private double startDiameter = 6.0E7;
   private double endDiameter = this.startDiameter;
   private long endTime;
   private long startTime;
   private int worldSize = 29999984;
   private double damageAmount = 0.2;
   private double damageBuffer = 5.0;
   private int warningTime = 15;
   private int warningDistance = 5;

   public boolean contains(BlockPos blockPos) {
      return blockPos.getX() + 1 > this.minX() && blockPos.getX() < this.maxX() && blockPos.getZ() + 1 > this.minZ() && blockPos.getZ() < this.maxZ();
   }

   public boolean contains(ChunkCoordIntPair chunkCoordIntPair) {
      return chunkCoordIntPair.getXEnd() > this.minX()
         && chunkCoordIntPair.getXStart() < this.maxX()
         && chunkCoordIntPair.getZEnd() > this.minZ()
         && chunkCoordIntPair.getZStart() < this.maxZ();
   }

   public boolean contains(AxisAlignedBB axisAlignedBB) {
      return axisAlignedBB.maxX > this.minX() && axisAlignedBB.minX < this.maxX() && axisAlignedBB.maxZ > this.minZ() && axisAlignedBB.minZ < this.maxZ();
   }

   public double getClosestDistance(Entity entity) {
      return this.getClosestDistance(entity.posX, entity.posZ);
   }

   public double getClosestDistance(double double1, double double2) {
      double var8 = double2 - this.minZ();
      double var10 = this.maxZ() - double2;
      double var12 = double1 - this.minX();
      double var14 = this.maxX() - double1;
      double var16 = Math.min(var12, var14);
      var16 = Math.min(var16, var8);
      return Math.min(var16, var10);
   }

   public EnumBorderStatus getStatus() {
      return this.endDiameter < this.startDiameter
         ? EnumBorderStatus.SHRINKING
         : (this.endDiameter > this.startDiameter ? EnumBorderStatus.GROWING : EnumBorderStatus.STATIONARY);
   }

   public double minX() {
      double var4 = this.getCenterX() - this.getDiameter() / 2.0;
      if (var4 < -this.worldSize) {
         var4 = -this.worldSize;
      }

      return var4;
   }

   public double minZ() {
      double var4 = this.getCenterZ() - this.getDiameter() / 2.0;
      if (var4 < -this.worldSize) {
         var4 = -this.worldSize;
      }

      return var4;
   }

   public double maxX() {
      double var4 = this.getCenterX() + this.getDiameter() / 2.0;
      if (var4 > this.worldSize) {
         var4 = this.worldSize;
      }

      return var4;
   }

   public double maxZ() {
      double var4 = this.getCenterZ() + this.getDiameter() / 2.0;
      if (var4 > this.worldSize) {
         var4 = this.worldSize;
      }

      return var4;
   }

   public double getCenterX() {
      return this.centerX;
   }

   public double getCenterZ() {
      return this.centerZ;
   }

   public void setCenter(double double1, double double2) {
      this.centerX = double1;
      this.centerZ = double2;
      Iterator var8 = this.getListeners().iterator();

      while (var8.hasNext()) {
         ((IBorderListener)var8.next()).onCenterChanged(this, double1, double2);
      }
   }

   public double getDiameter() {
      if (this.getStatus() != EnumBorderStatus.STATIONARY) {
         double var4 = (float)(System.currentTimeMillis() - this.startTime) / (float)(this.endTime - this.startTime);
         if (var4 < 1.0) {
            return this.startDiameter + (this.endDiameter - this.startDiameter) * var4;
         }

         this.setTransition(this.endDiameter);
      }

      return this.startDiameter;
   }

   public long getTimeUntilTarget() {
      return this.getStatus() != EnumBorderStatus.STATIONARY ? this.endTime - System.currentTimeMillis() : 0L;
   }

   public double getTargetSize() {
      return this.endDiameter;
   }

   public void setTransition(double double1) {
      this.startDiameter = double1;
      this.endDiameter = double1;
      this.endTime = System.currentTimeMillis();
      this.startTime = this.endTime;
      Iterator var6 = this.getListeners().iterator();

      while (var6.hasNext()) {
         ((IBorderListener)var6.next()).onSizeChanged(this, double1);
      }
   }

   public void setTransition(double double1, double double2, long long3) {
      this.startDiameter = double1;
      this.endDiameter = double2;
      this.startTime = System.currentTimeMillis();
      this.endTime = this.startTime + long3;
      Iterator var10 = this.getListeners().iterator();

      while (var10.hasNext()) {
         ((IBorderListener)var10.next()).onTransitionStarted(this, double1, double2, long3);
      }
   }

   protected List<IBorderListener> getListeners() {
      return Lists.newArrayList(this.listeners);
   }

   public void addListener(IBorderListener iBorderListener) {
      this.listeners.add(iBorderListener);
   }

   public void setSize(int integer) {
      this.worldSize = integer;
   }

   public int getSize() {
      return this.worldSize;
   }

   public double getDamageBuffer() {
      return this.damageBuffer;
   }

   public void setDamageBuffer(double double1) {
      this.damageBuffer = double1;
      Iterator var6 = this.getListeners().iterator();

      while (var6.hasNext()) {
         ((IBorderListener)var6.next()).onDamageBufferChanged(this, double1);
      }
   }

   public double getDamageAmount() {
      return this.damageAmount;
   }

   public void setDamageAmount(double double1) {
      this.damageAmount = double1;
      Iterator var6 = this.getListeners().iterator();

      while (var6.hasNext()) {
         ((IBorderListener)var6.next()).onDamageAmountChanged(this, double1);
      }
   }

   public double getResizeSpeed() {
      return this.endTime == this.startTime ? 0.0 : Math.abs(this.startDiameter - this.endDiameter) / (this.endTime - this.startTime);
   }

   public int getWarningTime() {
      return this.warningTime;
   }

   public void setWarningTime(int integer) {
      this.warningTime = integer;
      Iterator var5 = this.getListeners().iterator();

      while (var5.hasNext()) {
         ((IBorderListener)var5.next()).onWarningTimeChanged(this, integer);
      }
   }

   public int getWarningDistance() {
      return this.warningDistance;
   }

   public void setWarningDistance(int integer) {
      this.warningDistance = integer;
      Iterator var5 = this.getListeners().iterator();

      while (var5.hasNext()) {
         ((IBorderListener)var5.next()).onWarningDistanceChanged(this, integer);
      }
   }
}
