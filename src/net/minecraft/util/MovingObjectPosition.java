package net.minecraft.util;

import net.minecraft.entity.Entity;

public class MovingObjectPosition {
   private BlockPos blockPos;
   public MovingObjectPosition.MovingObjectType typeOfHit;
   public EnumFacing sideHit;
   public Vec3 hitVec;
   public Entity entityHit;

   public MovingObjectPosition(Vec3 vec3, EnumFacing enumFacing, BlockPos blockPos) {
      this(MovingObjectPosition.MovingObjectType.BLOCK, vec3, enumFacing, blockPos);
   }

   public MovingObjectPosition(Vec3 vec3, EnumFacing enumFacing) {
      this(MovingObjectPosition.MovingObjectType.BLOCK, vec3, enumFacing, BlockPos.ORIGIN);
   }

   public MovingObjectPosition(Entity entity) {
      this(entity, new Vec3(entity.posX, entity.posY, entity.posZ));
   }

   public MovingObjectPosition(MovingObjectPosition.MovingObjectType movingObjectType, Vec3 vec3, EnumFacing enumFacing, BlockPos blockPos) {
      this.typeOfHit = movingObjectType;
      this.blockPos = blockPos;
      this.sideHit = enumFacing;
      this.hitVec = new Vec3(vec3.xCoord, vec3.yCoord, vec3.zCoord);
   }

   public MovingObjectPosition(Entity entity, Vec3 vec3) {
      this.typeOfHit = MovingObjectPosition.MovingObjectType.ENTITY;
      this.entityHit = entity;
      this.hitVec = vec3;
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }

   @Override
   public String toString() {
      return "HitResult{type="
         + this.typeOfHit
         + ", blockpos="
         + this.blockPos
         + ", f="
         + this.sideHit
         + ", pos="
         + this.hitVec
         + ", entity="
         + this.entityHit
         + "}";
   }

   public enum MovingObjectType {
      MISS,
      BLOCK,
      ENTITY;

      private static MovingObjectPosition.MovingObjectType[] Method398() {
         return new MovingObjectPosition.MovingObjectType[]{MISS, BLOCK, ENTITY};
      }
   }
}
