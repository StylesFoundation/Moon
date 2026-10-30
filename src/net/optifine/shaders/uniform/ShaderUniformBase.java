package net.optifine.shaders.uniform;

import java.util.Arrays;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.ARBShaderObjects;

public abstract class ShaderUniformBase {
   private final String name;
   private int program = 0;
   private int[] locations = new int[]{-1};

   public ShaderUniformBase(String string) {
      this.name = string;
   }

   public void setProgram(int integer) {
      if (this.program != integer) {
         this.program = integer;
         this.expandLocations();
         this.onProgramSet(integer);
      }
   }

   private void expandLocations() {
      if (this.program >= this.locations.length) {
         int[] var4 = new int[this.program * 2];
         Arrays.fill(var4, Integer.MIN_VALUE);
         System.arraycopy(this.locations, 0, var4, 0, this.locations.length);
         this.locations = var4;
      }
   }

   protected abstract void onProgramSet(int integer);

   public String getName() {
      return this.name;
   }

   public int getProgram() {
      return this.program;
   }

   public int getLocation() {
      if (this.program <= 0) {
         return -1;
      }

      int var4 = this.locations[this.program];
      if (var4 == Integer.MIN_VALUE) {
         var4 = ARBShaderObjects.glGetUniformLocationARB(this.program, this.name);
         this.locations[this.program] = var4;
      }

      return var4;
   }

   public boolean isDefined() {
      return this.getLocation() >= 0;
   }

   public void disable() {
      this.locations[this.program] = -1;
   }

   public void reset() {
      this.program = 0;
      this.locations = new int[]{-1};
      this.resetValue();
   }

   protected abstract void resetValue();

   protected void checkGLError() {
      if (Shaders.checkGLError(this.name) != 0) {
         this.disable();
      }
   }

   @Override
   public String toString() {
      return this.name;
   }
}
