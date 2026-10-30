package net.optifine.shaders.uniform;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform2i extends ShaderUniformBase {
   private int[][] programValues;

   public ShaderUniform2i(String string) {
      super(string);
      this.resetValue();
   }

   public void setValue(int integer1, int integer2) {
      int var6 = this.getProgram();
      int[] var7 = this.programValues[var6];
      if (var7[0] != integer1 || var7[1] != integer2) {
         var7[0] = integer1;
         var7[1] = integer2;
         int var8 = this.getLocation();
         if (var8 >= 0) {
            ARBShaderObjects.glUniform2iARB(var8, integer1, integer2);
            this.checkGLError();
         }
      }
   }

   public int[] getValue() {
      int var1 = this.getProgram();
      return this.programValues[var1];
   }

   @Override
   protected void onProgramSet(int integer) {
      if (integer >= this.programValues.length) {
         int[][] var5 = this.programValues;
         int[][] var6 = new int[integer + 10][];
         System.arraycopy(var5, 0, var6, 0, var5.length);
         this.programValues = var6;
      }

      if (this.programValues[integer] == null) {
         this.programValues[integer] = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
      }
   }

   @Override
   protected void resetValue() {
      this.programValues = new int[][]{{Integer.MIN_VALUE, Integer.MIN_VALUE}};
   }
}
