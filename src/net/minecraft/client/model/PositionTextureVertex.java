package net.minecraft.client.model;

import net.minecraft.util.Vec3;

public class PositionTextureVertex {
   public Vec3 vector3D;
   public float texturePositionX;
   public float texturePositionY;

   public PositionTextureVertex(float float1, float float2, float float3, float float4, float float5) {
      this(new Vec3(float1, float2, float3), float4, float5);
   }

   public PositionTextureVertex setTexturePosition(float float1, float float2) {
      return new PositionTextureVertex(this, float1, float2);
   }

   public PositionTextureVertex(PositionTextureVertex positionTextureVertex, float float2, float float3) {
      this.vector3D = positionTextureVertex.vector3D;
      this.texturePositionX = float2;
      this.texturePositionY = float3;
   }

   public PositionTextureVertex(Vec3 vec3, float float2, float float3) {
      this.vector3D = vec3;
      this.texturePositionX = float2;
      this.texturePositionY = float3;
   }
}
