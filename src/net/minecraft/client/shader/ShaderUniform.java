package net.minecraft.client.shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderUniform {
   private static final Logger logger = LogManager.getLogger();
   private int uniformLocation;
   private final int uniformCount;
   private final int uniformType;
   private final IntBuffer uniformIntBuffer;
   private final FloatBuffer uniformFloatBuffer;
   private final String shaderName;
   private boolean dirty;
   private final ShaderManager shaderManager;

   public ShaderUniform(String string, int integer2, int integer3, ShaderManager shaderManager) {
      this.shaderName = string;
      this.uniformCount = integer3;
      this.uniformType = integer2;
      this.shaderManager = shaderManager;
      if (integer2 <= 3) {
         this.uniformIntBuffer = BufferUtils.createIntBuffer(integer3);
         this.uniformFloatBuffer = null;
      } else {
         this.uniformIntBuffer = null;
         this.uniformFloatBuffer = BufferUtils.createFloatBuffer(integer3);
      }

      this.uniformLocation = -1;
      this.markDirty();
   }

   private void markDirty() {
      this.dirty = true;
      if (this.shaderManager != null) {
         this.shaderManager.markDirty();
      }
   }

   public static int parseType(String string) {
      byte var3 = -1;
      if (string.equals("int")) {
         var3 = 0;
      } else if (string.equals("float")) {
         var3 = 4;
      } else if (string.startsWith("matrix")) {
         if (string.endsWith("2x2")) {
            var3 = 8;
         } else if (string.endsWith("3x3")) {
            var3 = 9;
         } else if (string.endsWith("4x4")) {
            var3 = 10;
         }
      }

      return var3;
   }

   public void setUniformLocation(int integer) {
      this.uniformLocation = integer;
   }

   public String getShaderName() {
      return this.shaderName;
   }

   public void set(float float1) {
      this.uniformFloatBuffer.position(0);
      this.uniformFloatBuffer.put(0, float1);
      this.markDirty();
   }

   public void set(float float1, float float2) {
      this.uniformFloatBuffer.position(0);
      this.uniformFloatBuffer.put(0, float1);
      this.uniformFloatBuffer.put(1, float2);
      this.markDirty();
   }

   public void set(float float1, float float2, float float3) {
      this.uniformFloatBuffer.position(0);
      this.uniformFloatBuffer.put(0, float1);
      this.uniformFloatBuffer.put(1, float2);
      this.uniformFloatBuffer.put(2, float3);
      this.markDirty();
   }

   public void set(float float1, float float2, float float3, float float4) {
      this.uniformFloatBuffer.position(0);
      this.uniformFloatBuffer.put(float1);
      this.uniformFloatBuffer.put(float2);
      this.uniformFloatBuffer.put(float3);
      this.uniformFloatBuffer.put(float4);
      this.uniformFloatBuffer.flip();
      this.markDirty();
   }

   public void func_148092_b(float float1, float float2, float float3, float float4) {
      this.uniformFloatBuffer.position(0);
      if (this.uniformType >= 4) {
         this.uniformFloatBuffer.put(0, float1);
      }

      if (this.uniformType >= 5) {
         this.uniformFloatBuffer.put(1, float2);
      }

      if (this.uniformType >= 6) {
         this.uniformFloatBuffer.put(2, float3);
      }

      if (this.uniformType >= 7) {
         this.uniformFloatBuffer.put(3, float4);
      }

      this.markDirty();
   }

   public void set(int integer1, int integer2, int integer3, int integer4) {
      this.uniformIntBuffer.position(0);
      if (this.uniformType >= 0) {
         this.uniformIntBuffer.put(0, integer1);
      }

      if (this.uniformType >= 1) {
         this.uniformIntBuffer.put(1, integer2);
      }

      if (this.uniformType >= 2) {
         this.uniformIntBuffer.put(2, integer3);
      }

      if (this.uniformType >= 3) {
         this.uniformIntBuffer.put(3, integer4);
      }

      this.markDirty();
   }

   public void set(float[] arr) {
      if (arr.length < this.uniformCount) {
         logger.warn("Uniform.set called with a too-small value array (expected " + this.uniformCount + ", got " + arr.length + "). Ignoring.");
      } else {
         this.uniformFloatBuffer.position(0);
         this.uniformFloatBuffer.put(arr);
         this.uniformFloatBuffer.position(0);
         this.markDirty();
      }
   }

   public void set(
      float float1,
      float float2,
      float float3,
      float float4,
      float float5,
      float float6,
      float float7,
      float float8,
      float float9,
      float float10,
      float float11,
      float float12,
      float float13,
      float float14,
      float float15,
      float float16
   ) {
      this.uniformFloatBuffer.position(0);
      this.uniformFloatBuffer.put(0, float1);
      this.uniformFloatBuffer.put(1, float2);
      this.uniformFloatBuffer.put(2, float3);
      this.uniformFloatBuffer.put(3, float4);
      this.uniformFloatBuffer.put(4, float5);
      this.uniformFloatBuffer.put(5, float6);
      this.uniformFloatBuffer.put(6, float7);
      this.uniformFloatBuffer.put(7, float8);
      this.uniformFloatBuffer.put(8, float9);
      this.uniformFloatBuffer.put(9, float10);
      this.uniformFloatBuffer.put(10, float11);
      this.uniformFloatBuffer.put(11, float12);
      this.uniformFloatBuffer.put(12, float13);
      this.uniformFloatBuffer.put(13, float14);
      this.uniformFloatBuffer.put(14, float15);
      this.uniformFloatBuffer.put(15, float16);
      this.markDirty();
   }

   public void set(Matrix4f matrix4f) {
      this.set(
         matrix4f.m00,
         matrix4f.m01,
         matrix4f.m02,
         matrix4f.m03,
         matrix4f.m10,
         matrix4f.m11,
         matrix4f.m12,
         matrix4f.m13,
         matrix4f.m20,
         matrix4f.m21,
         matrix4f.m22,
         matrix4f.m23,
         matrix4f.m30,
         matrix4f.m31,
         matrix4f.m32,
         matrix4f.m33
      );
   }

   public void Method380() {
      if (!this.dirty) {
      }

      this.dirty = false;
      if (this.uniformType <= 3) {
         this.Method381();
      } else if (this.uniformType <= 7) {
         this.Method382();
      } else {
         if (this.uniformType > 10) {
            logger.warn("Uniform.upload called, but type value (" + this.uniformType + ") is not a valid type. Ignoring.");
            return;
         }

         this.uploadFloatMatrix();
      }
   }

   private void Method381() {
      switch (this.uniformType) {
         case 0:
            OpenGlHelper.glUniform1(this.uniformLocation, this.uniformIntBuffer);
            break;
         case 1:
            OpenGlHelper.glUniform2(this.uniformLocation, this.uniformIntBuffer);
            break;
         case 2:
            OpenGlHelper.glUniform3(this.uniformLocation, this.uniformIntBuffer);
            break;
         case 3:
            OpenGlHelper.glUniform4(this.uniformLocation, this.uniformIntBuffer);
            break;
         default:
            logger.warn("Uniform.upload called, but count value (" + this.uniformCount + ") is  not in the range of 1 to 4. Ignoring.");
      }
   }

   private void Method382() {
      switch (this.uniformType) {
         case 4:
            OpenGlHelper.glUniform1(this.uniformLocation, this.uniformFloatBuffer);
            break;
         case 5:
            OpenGlHelper.glUniform2(this.uniformLocation, this.uniformFloatBuffer);
            break;
         case 6:
            OpenGlHelper.glUniform3(this.uniformLocation, this.uniformFloatBuffer);
            break;
         case 7:
            OpenGlHelper.glUniform4(this.uniformLocation, this.uniformFloatBuffer);
            break;
         default:
            logger.warn("Uniform.upload called, but count value (" + this.uniformCount + ") is not in the range of 1 to 4. Ignoring.");
      }
   }

   private void uploadFloatMatrix() {
      switch (this.uniformType) {
         case 8:
            OpenGlHelper.glUniformMatrix2(this.uniformLocation, true, this.uniformFloatBuffer);
            break;
         case 9:
            OpenGlHelper.glUniformMatrix3(this.uniformLocation, true, this.uniformFloatBuffer);
            break;
         case 10:
            OpenGlHelper.glUniformMatrix4(this.uniformLocation, true, this.uniformFloatBuffer);
      }
   }
}
