package net.optifine.shaders.uniform;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform2f extends ShaderUniformBase {
   private float[][] programValues;

   public ShaderUniform2f(String string) {
      super(string);
      this.resetValue();
   }

   public void setValue(float float1, float float2) {
      int var6 = this.getProgram();
      float[] var7 = this.programValues[var6];
      if (var7[0] != float1 || var7[1] != float2) {
         var7[0] = float1;
         var7[1] = float2;
         int var8 = this.getLocation();
         if (var8 >= 0) {
            ARBShaderObjects.glUniform2fARB(var8, float1, float2);
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
         this.programValues[integer] = new float[]{-Float.MAX_VALUE, -Float.MAX_VALUE};
      }
   }

   @Override
   protected void resetValue() {
      this.programValues = new float[][]{{-Float.MAX_VALUE, -Float.MAX_VALUE}};
   }
}
