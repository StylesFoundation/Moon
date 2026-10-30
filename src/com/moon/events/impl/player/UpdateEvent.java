package com.moon.events.impl.player;

import com.moon.events.EventCancellable;

public class UpdateEvent extends EventCancellable {
   private final UpdateEvent.Sequence sequence;
   private double x;
   private double y;
   private double z;
   private float yaw;
   private float pitch;
   private boolean ground;

   public UpdateEvent.Sequence getSequence() {
      return this.sequence;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public boolean isOnGround() {
      return this.ground;
   }

   public UpdateEvent setX(double x) {
      this.x = x;
      return this;
   }

   public UpdateEvent setY(double y) {
      this.y = y;
      return this;
   }

   public UpdateEvent setZ(double z) {
      this.z = z;
      return this;
   }

   public UpdateEvent setYaw(float yaw) {
      this.yaw = yaw;
      return this;
   }

   public UpdateEvent setPitch(float pitch) {
      this.pitch = pitch;
      return this;
   }

   public UpdateEvent setOnGround(boolean ground) {
      this.ground = ground;
      return this;
   }

   public UpdateEvent(UpdateEvent.Sequence sequence, double x, double y, double z, float yaw, float pitch, boolean ground) {
      this.sequence = sequence;
      this.x = x;
      this.y = y;
      this.z = z;
      this.yaw = yaw;
      this.pitch = pitch;
      this.ground = ground;
   }

   public static boolean dummy() {
      return false;
   }

   static {
      if (dummy()) {
      }
   }

   public enum Sequence {
      PRE,
      POST,
      BEFORE;
   }
}
