package net.optifine.entity.model.anim;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;

public enum ModelVariableType {
   POS_X("tx"),
   POS_Y("ty"),
   POS_Z("tz"),
   ANGLE_X("rx"),
   ANGLE_Y("ry"),
   ANGLE_Z("rz"),
   OFFSET_X("ox"),
   OFFSET_Y("oy"),
   OFFSET_Z("oz"),
   SCALE_X("sx"),
   SCALE_Y("sy"),
   SCALE_Z("sz");

   private final String name;
   public static ModelVariableType[] VALUES = values();

   ModelVariableType(String string3) {
      this.name = string3;
   }

   public String getName() {
      return this.name;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public float getFloat(ModelRenderer modelRenderer) {
      switch (this) {
         case POS_X:
            return modelRenderer.rotationPointX;
         case POS_Y:
            return modelRenderer.rotationPointY;
         case POS_Z:
            return modelRenderer.rotationPointZ;
         case ANGLE_X:
            return modelRenderer.rotateAngleX;
         case ANGLE_Y:
            return modelRenderer.rotateAngleY;
         case ANGLE_Z:
            return modelRenderer.rotateAngleZ;
         case OFFSET_X:
            return modelRenderer.offsetX;
         case OFFSET_Y:
            return modelRenderer.offsetY;
         case OFFSET_Z:
            return modelRenderer.offsetZ;
         case SCALE_X:
            return modelRenderer.scaleX;
         case SCALE_Y:
            return modelRenderer.scaleY;
         case SCALE_Z:
            return modelRenderer.scaleZ;
         default:
            Config.Method252("GetFloat not supported for: " + this);
            return 0.0F;
      }
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void setFloat(ModelRenderer modelRenderer, float float2) {
      switch (this) {
         case POS_X:
            modelRenderer.rotationPointX = float2;
            return;
         case POS_Y:
            modelRenderer.rotationPointY = float2;
            return;
         case POS_Z:
            modelRenderer.rotationPointZ = float2;
            return;
         case ANGLE_X:
            modelRenderer.rotateAngleX = float2;
            return;
         case ANGLE_Y:
            modelRenderer.rotateAngleY = float2;
            return;
         case ANGLE_Z:
            modelRenderer.rotateAngleZ = float2;
            return;
         case OFFSET_X:
            modelRenderer.offsetX = float2;
            return;
         case OFFSET_Y:
            modelRenderer.offsetY = float2;
            return;
         case OFFSET_Z:
            modelRenderer.offsetZ = float2;
            return;
         case SCALE_X:
            modelRenderer.scaleX = float2;
            return;
         case SCALE_Y:
            modelRenderer.scaleY = float2;
            return;
         case SCALE_Z:
            modelRenderer.scaleZ = float2;
            return;
         default:
            Config.Method252("SetFloat not supported for: " + this);
      }
   }

   public static ModelVariableType parse(String string) {
      for (ModelVariableType var7 : VALUES) {
         if (var7.getName().equals(string)) {
            return var7;
         }
      }

      return null;
   }

   private static ModelVariableType[] Method225() {
      return new ModelVariableType[]{POS_X, POS_Y, POS_Z, ANGLE_X, ANGLE_Y, ANGLE_Z, OFFSET_X, OFFSET_Y, OFFSET_Z, SCALE_X, SCALE_Y, SCALE_Z};
   }
}
