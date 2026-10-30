package net.optifine.shaders.uniform;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform4f extends ShaderUniformBase {
   private float[][] programValues;

   public ShaderUniform4f(String string) {
      super(string);
      this.resetValue();
   }

   public void setValue(float float1, float float2, float float3, float float4) {
      int var8 = this.getProgram();
      float[] var9 = this.programValues[var8];
      if (var9[0] != float1 || var9[1] != float2 || var9[2] != float3 || var9[3] != float4) {
         var9[0] = float1;
         var9[1] = float2;
         var9[2] = float3;
         var9[3] = float4;
         int var10 = this.getLocation();
         if (var10 >= 0) {
            ARBShaderObjects.glUniform4fARB(var10, float1, float2, float3, float4);
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
         this.programValues[integer] = new float[]{-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE};
      }
   }

   @Override
   protected void resetValue() {
      this.programValues = new float[][]{{-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE}};
   }
}
