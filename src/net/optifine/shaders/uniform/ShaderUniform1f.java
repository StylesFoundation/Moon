package net.optifine.shaders.uniform;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform1f extends ShaderUniformBase {
   private float[] programValues;

   public ShaderUniform1f(String string) {
      super(string);
      this.resetValue();
   }

   public void setValue(float float1) {
      int var5 = this.getProgram();
      float var6 = this.programValues[var5];
      if (float1 != var6) {
         this.programValues[var5] = float1;
         int var7 = this.getLocation();
         if (var7 >= 0) {
            ARBShaderObjects.glUniform1fARB(var7, float1);
            this.checkGLError();
         }
      }
   }

   public float getValue() {
      int var1 = this.getProgram();
      return this.programValues[var1];
   }

   @Override
   protected void onProgramSet(int integer) {
      if (integer >= this.programValues.length) {
         float[] var5 = this.programValues;
         float[] var6 = new float[integer + 10];
         System.arraycopy(var5, 0, var6, 0, var5.length);

         for (int var7 = var5.length; var7 < var6.length; var7++) {
            var6[var7] = -Float.MAX_VALUE;
         }

         this.programValues = var6;
      }
   }

   @Override
   protected void resetValue() {
      this.programValues = new float[]{-Float.MAX_VALUE};
   }
}
