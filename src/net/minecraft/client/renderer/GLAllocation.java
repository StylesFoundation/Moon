package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GLAllocation {
   public static synchronized int generateDisplayLists(int integer) {
      int var1 = GL11.glGenLists(integer);
      if (var1 == 0) {
         int var2 = GL11.glGetError();
         String var3 = "No error code reported";
         if (var2 != 0) {
            var3 = GLU.gluErrorString(var2);
         }

         throw new IllegalStateException("glGenLists returned an ID of 0 for a count of " + integer + ", GL error (" + var2 + "): " + var3);
      } else {
         return var1;
      }
   }

   public static synchronized void deleteDisplayLists(int integer1, int integer2) {
      GL11.glDeleteLists(integer1, integer2);
   }

   public static synchronized void deleteDisplayLists(int integer) {
      GL11.glDeleteLists(integer, 1);
   }

   public static synchronized ByteBuffer createDirectByteBuffer(int integer) {
      return ByteBuffer.allocateDirect(integer).order(ByteOrder.nativeOrder());
   }

   public static IntBuffer createDirectIntBuffer(int integer) {
      return createDirectByteBuffer(integer << 2).asIntBuffer();
   }

   public static FloatBuffer createDirectFloatBuffer(int integer) {
      return createDirectByteBuffer(integer << 2).asFloatBuffer();
   }
}
