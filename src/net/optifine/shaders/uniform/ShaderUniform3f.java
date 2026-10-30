package net.optifine.shaders.uniform;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform3f extends ShaderUniformBase {
   private float[][] programValues;

   public ShaderUniform3f(String string) {
      super(string);
      this.resetValue();
   }

   public void setValue(float float1, float float2, float float3) {
      int var7 = this.getProgram();
      float[] var8 = this.programValues[var7];
      if (var8[0] != float1 || var8[1] != float2 || var8[2] != float3) {
         var8[0] = float1;
         var8[1] = float2;
         var8[2] = float3;
         int var9 = this.getLocation();
         if (var9 >= 0) {
            ARBShaderObjects.glUniform3fARB(var9, float1, float2, float3);
            this.checkGLError();
         }
      }
   }

   public float[] getValue() {
      int var1 = this.getProgram();
      return this.programValues[var1];
   }

   @Override
   protected void onProgramSet(int integer) {
      if (integer >= this.programValues.length) {
         float[][] var5 = this.programValues;
         float[][] var6 = new float[integer + 10][];
         System.arraycopy(var5, 0, var6, 0, var5.length);
         this.programValues = var6;
      }

      if (this.programValues[integer] == null) {
         this.programValues[integer] = new float[]{-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE};
      }
   }

   @Override
   protected void resetValue() {
      this.programValues = new float[][]{{-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE}};
   }
}
