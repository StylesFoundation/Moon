package net.optifine.shaders.uniform;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniformM4 extends ShaderUniformBase {
   private boolean transpose;
   private FloatBuffer matrix;

   public ShaderUniformM4(String string) {
      super(string);
   }

   public void setValue(boolean boolean1, FloatBuffer floatBuffer) {
      this.transpose = boolean1;
      this.matrix = floatBuffer;
      int var6 = this.getLocation();
      if (var6 >= 0) {
         ARBShaderObjects.glUniformMatrix4ARB(var6, boolean1, floatBuffer);
         this.checkGLError();
      }
   }

   public float getValue(int integer1, int integer2) {
      if (this.matrix == null) {
         return 0.0F;
      }

      int var6 = this.transpose ? integer2 * 4 + integer1 : integer1 * 4 + integer2;
      return this.matrix.get(var6);
   }

   @Override
   protected void onProgramSet(int integer) {
   }

   @Override
   protected void resetValue() {
      this.matrix = null;
   }
}
