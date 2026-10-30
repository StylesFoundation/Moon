package net.optifine.shaders.uniform;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform4i extends ShaderUniformBase {
   private int[][] programValues;

   public ShaderUniform4i(String string) {
      super(string);
      this.resetValue();
   }

   public void setValue(int integer1, int integer2, int integer3, int integer4) {
      int var8 = this.getProgram();
      int[] var9 = this.programValues[var8];
      if (var9[0] != integer1 || var9[1] != integer2 || var9[2] != integer3 || var9[3] != integer4) {
         var9[0] = integer1;
         var9[1] = integer2;
         var9[2] = integer3;
         var9[3] = integer4;
         int var10 = this.getLocation();
         if (var10 >= 0) {
            ARBShaderObjects.glUniform4iARB(var10, integer1, integer2, integer3, integer4);
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
         this.programValues[integer] = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
      }
   }

   @Override
   protected void resetValue() {
      this.programValues = new int[][]{{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE}};
   }
}
