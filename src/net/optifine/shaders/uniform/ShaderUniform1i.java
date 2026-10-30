package net.optifine.shaders.uniform;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform1i extends ShaderUniformBase {
   private int[] programValues;

   public ShaderUniform1i(String string) {
      super(string);
      this.resetValue();
   }

   public void setValue(int integer) {
      int var5 = this.getProgram();
      int var6 = this.programValues[var5];
      if (integer != var6) {
         this.programValues[var5] = integer;
         int var7 = this.getLocation();
         if (var7 >= 0) {
            ARBShaderObjects.glUniform1iARB(var7, integer);
            this.checkGLError();
         }
      }
   }

   public int getValue() {
      int var1 = this.getProgram();
      return this.programValues[var1];
   }

   @Override
   protected void onProgramSet(int integer) {
      if (integer >= this.programValues.length) {
         int[] var5 = this.programValues;
         int[] var6 = new int[integer + 10];
         System.arraycopy(var5, 0, var6, 0, var5.length);

         for (int var7 = var5.length; var7 < var6.length; var7++) {
            var6[var7] = Integer.MIN_VALUE;
         }

         this.programValues = var6;
      }
   }

   @Override
   protected void resetValue() {
      this.programValues = new int[]{Integer.MIN_VALUE};
   }
}
