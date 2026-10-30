package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import org.lwjgl.opengl.ARBCopyBuffer;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTBlendFuncSeparate;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GLContext;
import oshi.SystemInfo;
import oshi.hardware.Processor;

public class OpenGlHelper {
   public static boolean nvidia;
   public static boolean ati;
   public static int GL_FRAMEBUFFER;
   public static int GL_RENDERBUFFER;
   public static int GL_COLOR_ATTACHMENT0;
   public static int GL_DEPTH_ATTACHMENT;
   public static int GL_FRAMEBUFFER_COMPLETE;
   public static int GL_FB_INCOMPLETE_ATTACHMENT;
   public static int GL_FB_INCOMPLETE_MISS_ATTACH;
   public static int GL_FB_INCOMPLETE_DRAW_BUFFER;
   public static int GL_FB_INCOMPLETE_READ_BUFFER;
   public static boolean framebufferSupported;
   public static int GL_LINK_STATUS;
   public static int GL_COMPILE_STATUS;
   public static int GL_VERTEX_SHADER;
   public static int GL_FRAGMENT_SHADER;
   public static int defaultTexUnit;
   public static int lightmapTexUnit;
   public static int GL_TEXTURE2;
   public static int GL_COMBINE;
   public static int GL_INTERPOLATE;
   public static int GL_PRIMARY_COLOR;
   public static int GL_CONSTANT;
   public static int GL_PREVIOUS;
   public static int GL_COMBINE_RGB;
   public static int GL_SOURCE0_RGB;
   public static int GL_SOURCE1_RGB;
   public static int GL_SOURCE2_RGB;
   public static int GL_OPERAND0_RGB;
   public static int GL_OPERAND1_RGB;
   public static int GL_OPERAND2_RGB;
   public static int GL_COMBINE_ALPHA;
   public static int GL_SOURCE0_ALPHA;
   public static int GL_SOURCE1_ALPHA;
   public static int GL_SOURCE2_ALPHA;
   public static int GL_OPERAND0_ALPHA;
   public static int GL_OPERAND1_ALPHA;
   public static int GL_OPERAND2_ALPHA;
   public static boolean extBlendFuncSeparate;
   public static boolean openGL21;
   public static boolean shadersSupported;
   public static boolean vboSupported;
   public static boolean vboSupportedAti;
   public static int GL_ARRAY_BUFFER;
   public static int GL_STATIC_DRAW;
   public static float lastBrightnessX = 0.0F;
   public static float lastBrightnessY = 0.0F;
   public static boolean openGL31;
   public static boolean vboRegions;
   public static int GL_COPY_READ_BUFFER;
   public static int GL_COPY_WRITE_BUFFER;
   private static int framebufferType;
   private static boolean shadersAvailable;
   private static boolean arbShaders;
   private static boolean arbMultitexture;
   private static boolean arbTextureEnvCombine;
   private static boolean openGL14;
   private static String logText = "";
   private static String cpu;
   private static boolean arbVbo;

   public static void initializeTextures() {
      Config.initDisplay();
      ContextCapabilities var2 = GLContext.getCapabilities();
      arbMultitexture = var2.GL_ARB_multitexture && !var2.OpenGL13;
      arbTextureEnvCombine = var2.GL_ARB_texture_env_combine && !var2.OpenGL13;
      openGL31 = var2.OpenGL31;
      GL_COPY_READ_BUFFER = 36662;
      GL_COPY_WRITE_BUFFER = 36663;
      boolean var3 = openGL31 || var2.GL_ARB_copy_buffer;
      boolean var4 = var2.OpenGL14;
      vboRegions = var3 && var4;
      if (!vboRegions) {
         ArrayList var5 = new ArrayList();
         if (!var3) {
            var5.add("OpenGL 1.3, ARB_copy_buffer");
         }

         if (!var4) {
            var5.add("OpenGL 1.4");
         }

         String var6 = "VboRegions not supported, missing: " + Config.listToString(var5);
         Config.Method251(var6);
         logText = logText + var6 + "\n";
      }

      if (arbMultitexture) {
         logText = logText + "Using ARB_multitexture.\n";
      } else {
         logText = logText + "Using GL 1.3 multitexturing.\n";
      }

      defaultTexUnit = 33984;
      lightmapTexUnit = 33985;
      GL_TEXTURE2 = 33986;
      if (arbTextureEnvCombine) {
         logText = logText + "Using ARB_texture_env_combine.\n";
      } else {
         logText = logText + "Using GL 1.3 texture combiners.\n";
      }

      GL_COMBINE = 34160;
      GL_INTERPOLATE = 34165;
      GL_PRIMARY_COLOR = 34167;
      GL_CONSTANT = 34166;
      GL_PREVIOUS = 34168;
      GL_COMBINE_RGB = 34161;
      GL_SOURCE0_RGB = 34176;
      GL_SOURCE1_RGB = 34177;
      GL_SOURCE2_RGB = 34178;
      GL_OPERAND0_RGB = 34192;
      GL_OPERAND1_RGB = 34193;
      GL_OPERAND2_RGB = 34194;
      GL_COMBINE_ALPHA = 34162;
      GL_SOURCE0_ALPHA = 34184;
      GL_SOURCE1_ALPHA = 34185;
      GL_SOURCE2_ALPHA = 34186;
      GL_OPERAND0_ALPHA = 34200;
      GL_OPERAND1_ALPHA = 34201;
      GL_OPERAND2_ALPHA = 34202;
      extBlendFuncSeparate = var2.GL_EXT_blend_func_separate && !var2.OpenGL14;
      openGL14 = var2.OpenGL14 || var2.GL_EXT_blend_func_separate;
      framebufferSupported = openGL14 && (var2.GL_ARB_framebuffer_object || var2.GL_EXT_framebuffer_object || var2.OpenGL30);
      if (framebufferSupported) {
         logText = logText + "Using framebuffer objects because ";
         if (var2.OpenGL30) {
            logText = logText + "OpenGL 3.0 is supported and separate blending is supported.\n";
            framebufferType = 0;
            GL_FRAMEBUFFER = 36160;
            GL_RENDERBUFFER = 36161;
            GL_COLOR_ATTACHMENT0 = 36064;
            GL_DEPTH_ATTACHMENT = 36096;
            GL_FRAMEBUFFER_COMPLETE = 36053;
            GL_FB_INCOMPLETE_ATTACHMENT = 36054;
            GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
            GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
            GL_FB_INCOMPLETE_READ_BUFFER = 36060;
         } else if (var2.GL_ARB_framebuffer_object) {
            logText = logText + "ARB_framebuffer_object is supported and separate blending is supported.\n";
            framebufferType = 1;
            GL_FRAMEBUFFER = 36160;
            GL_RENDERBUFFER = 36161;
            GL_COLOR_ATTACHMENT0 = 36064;
            GL_DEPTH_ATTACHMENT = 36096;
            GL_FRAMEBUFFER_COMPLETE = 36053;
            GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
            GL_FB_INCOMPLETE_ATTACHMENT = 36054;
            GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
            GL_FB_INCOMPLETE_READ_BUFFER = 36060;
         } else if (var2.GL_EXT_framebuffer_object) {
            logText = logText + "EXT_framebuffer_object is supported.\n";
            framebufferType = 2;
            GL_FRAMEBUFFER = 36160;
            GL_RENDERBUFFER = 36161;
            GL_COLOR_ATTACHMENT0 = 36064;
            GL_DEPTH_ATTACHMENT = 36096;
            GL_FRAMEBUFFER_COMPLETE = 36053;
            GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
            GL_FB_INCOMPLETE_ATTACHMENT = 36054;
            GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
            GL_FB_INCOMPLETE_READ_BUFFER = 36060;
         }
      } else {
         logText = logText + "Not using framebuffer objects because ";
         logText = logText + "OpenGL 1.4 is " + (var2.OpenGL14 ? "" : "not ") + "supported, ";
         logText = logText + "EXT_blend_func_separate is " + (var2.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
         logText = logText + "OpenGL 3.0 is " + (var2.OpenGL30 ? "" : "not ") + "supported, ";
         logText = logText + "ARB_framebuffer_object is " + (var2.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
         logText = logText + "EXT_framebuffer_object is " + (var2.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
      }

      openGL21 = var2.OpenGL21;
      shadersAvailable = openGL21 || var2.GL_ARB_vertex_shader && var2.GL_ARB_fragment_shader && var2.GL_ARB_shader_objects;
      logText = logText + "Shaders are " + (shadersAvailable ? "" : "not ") + "available because ";
      if (shadersAvailable) {
         if (var2.OpenGL21) {
            logText = logText + "OpenGL 2.1 is supported.\n";
            arbShaders = false;
         } else {
            logText = logText + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
            arbShaders = true;
         }

         GL_LINK_STATUS = 35714;
         GL_COMPILE_STATUS = 35713;
         GL_VERTEX_SHADER = 35633;
         GL_FRAGMENT_SHADER = 35632;
      } else {
         logText = logText + "OpenGL 2.1 is " + (var2.OpenGL21 ? "" : "not ") + "supported, ";
         logText = logText + "ARB_shader_objects is " + (var2.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
         logText = logText + "ARB_vertex_shader is " + (var2.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
         logText = logText + "ARB_fragment_shader is " + (var2.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
      }

      shadersSupported = framebufferSupported && shadersAvailable;
      String var8 = GL11.glGetString(GL11.GL_VENDOR).toLowerCase();
      nvidia = var8.contains("nvidia");
      arbVbo = !var2.OpenGL15 && var2.GL_ARB_vertex_buffer_object;
      vboSupported = var2.OpenGL15 || arbVbo;
      logText = logText + "VBOs are " + (vboSupported ? "" : "not ") + "available because ";
      if (vboSupported) {
         if (arbVbo) {
            logText = logText + "ARB_vertex_buffer_object is supported.\n";
         } else {
            logText = logText + "OpenGL 1.5 is supported.\n";
         }

         GL_STATIC_DRAW = 35044;
         GL_ARRAY_BUFFER = 34962;
      }

      ati = var8.contains("ati");
      if (ati) {
         if (vboSupported) {
            vboSupportedAti = true;
         } else {
            GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0F);
         }
      }

      try {
         Processor[] var9 = new SystemInfo().getHardware().getProcessors();
         cpu = String.format("%dx %s", var9.length, var9[0]).replaceAll("\\s+", " ");
      } catch (Throwable var7) {
      }
   }

   public static boolean areShadersSupported() {
      return shadersSupported;
   }

   public static String getLogText() {
      return logText;
   }

   public static int glGetProgrami(int integer1, int integer2) {
      return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(integer1, integer2) : GL20.glGetProgrami(integer1, integer2);
   }

   public static void glAttachShader(int integer1, int integer2) {
      if (arbShaders) {
         ARBShaderObjects.glAttachObjectARB(integer1, integer2);
      } else {
         GL20.glAttachShader(integer1, integer2);
      }
   }

   public static void glDeleteShader(int integer) {
      if (arbShaders) {
         ARBShaderObjects.glDeleteObjectARB(integer);
      } else {
         GL20.glDeleteShader(integer);
      }
   }

   public static int glCreateShader(int integer) {
      return arbShaders ? ARBShaderObjects.glCreateShaderObjectARB(integer) : GL20.glCreateShader(integer);
   }

   public static void glShaderSource(int integer, ByteBuffer byteBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glShaderSourceARB(integer, byteBuffer);
      } else {
         GL20.glShaderSource(integer, byteBuffer);
      }
   }

   public static void glCompileShader(int integer) {
      if (arbShaders) {
         ARBShaderObjects.glCompileShaderARB(integer);
      } else {
         GL20.glCompileShader(integer);
      }
   }

   public static int glGetShaderi(int integer1, int integer2) {
      return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(integer1, integer2) : GL20.glGetShaderi(integer1, integer2);
   }

   public static String glGetShaderInfoLog(int integer1, int integer2) {
      return arbShaders ? ARBShaderObjects.glGetInfoLogARB(integer1, integer2) : GL20.glGetShaderInfoLog(integer1, integer2);
   }

   public static String glGetProgramInfoLog(int integer1, int integer2) {
      return arbShaders ? ARBShaderObjects.glGetInfoLogARB(integer1, integer2) : GL20.glGetProgramInfoLog(integer1, integer2);
   }

   public static void glUseProgram(int integer) {
      if (arbShaders) {
         ARBShaderObjects.glUseProgramObjectARB(integer);
      } else {
         GL20.glUseProgram(integer);
      }
   }

   public static int glCreateProgram() {
      return arbShaders ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
   }

   public static void glDeleteProgram(int integer) {
      if (arbShaders) {
         ARBShaderObjects.glDeleteObjectARB(integer);
      } else {
         GL20.glDeleteProgram(integer);
      }
   }

   public static void glLinkProgram(int integer) {
      if (arbShaders) {
         ARBShaderObjects.glLinkProgramARB(integer);
      } else {
         GL20.glLinkProgram(integer);
      }
   }

   public static int glGetUniformLocation(int integer, CharSequence charSequence) {
      return arbShaders ? ARBShaderObjects.glGetUniformLocationARB(integer, charSequence) : GL20.glGetUniformLocation(integer, charSequence);
   }

   public static void glUniform1(int integer, IntBuffer intBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glUniform1ARB(integer, intBuffer);
      } else {
         GL20.glUniform1(integer, intBuffer);
      }
   }

   public static void glUniform1i(int integer1, int integer2) {
      if (arbShaders) {
         ARBShaderObjects.glUniform1iARB(integer1, integer2);
      } else {
         GL20.glUniform1i(integer1, integer2);
      }
   }

   public static void glUniform1(int integer, FloatBuffer floatBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glUniform1ARB(integer, floatBuffer);
      } else {
         GL20.glUniform1(integer, floatBuffer);
      }
   }

   public static void glUniform2(int integer, IntBuffer intBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glUniform2ARB(integer, intBuffer);
      } else {
         GL20.glUniform2(integer, intBuffer);
      }
   }

   public static void glUniform2(int integer, FloatBuffer floatBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glUniform2ARB(integer, floatBuffer);
      } else {
         GL20.glUniform2(integer, floatBuffer);
      }
   }

   public static void glUniform3(int integer, IntBuffer intBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glUniform3ARB(integer, intBuffer);
      } else {
         GL20.glUniform3(integer, intBuffer);
      }
   }

   public static void glUniform3(int integer, FloatBuffer floatBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glUniform3ARB(integer, floatBuffer);
      } else {
         GL20.glUniform3(integer, floatBuffer);
      }
   }

   public static void glUniform4(int integer, IntBuffer intBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glUniform4ARB(integer, intBuffer);
      } else {
         GL20.glUniform4(integer, intBuffer);
      }
   }

   public static void glUniform4(int integer, FloatBuffer floatBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glUniform4ARB(integer, floatBuffer);
      } else {
         GL20.glUniform4(integer, floatBuffer);
      }
   }

   public static void glUniformMatrix2(int integer, boolean boolean2, FloatBuffer floatBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glUniformMatrix2ARB(integer, boolean2, floatBuffer);
      } else {
         GL20.glUniformMatrix2(integer, boolean2, floatBuffer);
      }
   }

   public static void glUniformMatrix3(int integer, boolean boolean2, FloatBuffer floatBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glUniformMatrix3ARB(integer, boolean2, floatBuffer);
      } else {
         GL20.glUniformMatrix3(integer, boolean2, floatBuffer);
      }
   }

   public static void glUniformMatrix4(int integer, boolean boolean2, FloatBuffer floatBuffer) {
      if (arbShaders) {
         ARBShaderObjects.glUniformMatrix4ARB(integer, boolean2, floatBuffer);
      } else {
         GL20.glUniformMatrix4(integer, boolean2, floatBuffer);
      }
   }

   public static int glGetAttribLocation(int integer, CharSequence charSequence) {
      return arbShaders ? ARBVertexShader.glGetAttribLocationARB(integer, charSequence) : GL20.glGetAttribLocation(integer, charSequence);
   }

   public static int glGenBuffers() {
      return arbVbo ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
   }

   public static void glBindBuffer(int integer1, int integer2) {
      if (arbVbo) {
         ARBVertexBufferObject.glBindBufferARB(integer1, integer2);
      } else {
         GL15.glBindBuffer(integer1, integer2);
      }
   }

   public static void glBufferData(int integer1, ByteBuffer byteBuffer, int integer3) {
      if (arbVbo) {
         ARBVertexBufferObject.glBufferDataARB(integer1, byteBuffer, integer3);
      } else {
         GL15.glBufferData(integer1, byteBuffer, integer3);
      }
   }

   public static void glDeleteBuffers(int integer) {
      if (arbVbo) {
         ARBVertexBufferObject.glDeleteBuffersARB(integer);
      } else {
         GL15.glDeleteBuffers(integer);
      }
   }

   public static boolean useVbo() {
      return !Config.isMultiTexture() && (!Config.isRenderRegions() || vboRegions) && vboSupported && Minecraft.getMinecraft().gameSettings.useVbo;
   }

   public static void glBindFramebuffer(int integer1, int integer2) {
      if (framebufferSupported) {
         switch (framebufferType) {
            case 0:
               GL30.glBindFramebuffer(integer1, integer2);
               break;
            case 1:
               ARBFramebufferObject.glBindFramebuffer(integer1, integer2);
               break;
            case 2:
               EXTFramebufferObject.glBindFramebufferEXT(integer1, integer2);
         }
      }
   }

   public static void glBindRenderbuffer(int integer1, int integer2) {
      if (framebufferSupported) {
         switch (framebufferType) {
            case 0:
               GL30.glBindRenderbuffer(integer1, integer2);
               break;
            case 1:
               ARBFramebufferObject.glBindRenderbuffer(integer1, integer2);
               break;
            case 2:
               EXTFramebufferObject.glBindRenderbufferEXT(integer1, integer2);
         }
      }
   }

   public static void glDeleteRenderbuffers(int integer) {
      if (framebufferSupported) {
         switch (framebufferType) {
            case 0:
               GL30.glDeleteRenderbuffers(integer);
               break;
            case 1:
               ARBFramebufferObject.glDeleteRenderbuffers(integer);
               break;
            case 2:
               EXTFramebufferObject.glDeleteRenderbuffersEXT(integer);
         }
      }
   }

   public static void glDeleteFramebuffers(int integer) {
      if (framebufferSupported) {
         switch (framebufferType) {
            case 0:
               GL30.glDeleteFramebuffers(integer);
               break;
            case 1:
               ARBFramebufferObject.glDeleteFramebuffers(integer);
               break;
            case 2:
               EXTFramebufferObject.glDeleteFramebuffersEXT(integer);
         }
      }
   }

   public static int glGenFramebuffers() {
      if (!framebufferSupported) {
         return -1;
      }

      switch (framebufferType) {
         case 0:
            return GL30.glGenFramebuffers();
         case 1:
            return ARBFramebufferObject.glGenFramebuffers();
         case 2:
            return EXTFramebufferObject.glGenFramebuffersEXT();
         default:
            return -1;
      }
   }

   public static int glGenRenderbuffers() {
      if (!framebufferSupported) {
         return -1;
      }

      switch (framebufferType) {
         case 0:
            return GL30.glGenRenderbuffers();
         case 1:
            return ARBFramebufferObject.glGenRenderbuffers();
         case 2:
            return EXTFramebufferObject.glGenRenderbuffersEXT();
         default:
            return -1;
      }
   }

   public static void glRenderbufferStorage(int integer1, int integer2, int integer3, int integer4) {
      if (framebufferSupported) {
         switch (framebufferType) {
            case 0:
               GL30.glRenderbufferStorage(integer1, integer2, integer3, integer4);
               break;
            case 1:
               ARBFramebufferObject.glRenderbufferStorage(integer1, integer2, integer3, integer4);
               break;
            case 2:
               EXTFramebufferObject.glRenderbufferStorageEXT(integer1, integer2, integer3, integer4);
         }
      }
   }

   public static void glFramebufferRenderbuffer(int integer1, int integer2, int integer3, int integer4) {
      if (framebufferSupported) {
         switch (framebufferType) {
            case 0:
               GL30.glFramebufferRenderbuffer(integer1, integer2, integer3, integer4);
               break;
            case 1:
               ARBFramebufferObject.glFramebufferRenderbuffer(integer1, integer2, integer3, integer4);
               break;
            case 2:
               EXTFramebufferObject.glFramebufferRenderbufferEXT(integer1, integer2, integer3, integer4);
         }
      }
   }

   public static int glCheckFramebufferStatus(int integer) {
      if (!framebufferSupported) {
         return -1;
      }

      switch (framebufferType) {
         case 0:
            return GL30.glCheckFramebufferStatus(integer);
         case 1:
            return ARBFramebufferObject.glCheckFramebufferStatus(integer);
         case 2:
            return EXTFramebufferObject.glCheckFramebufferStatusEXT(integer);
         default:
            return -1;
      }
   }

   public static void glFramebufferTexture2D(int integer1, int integer2, int integer3, int integer4, int integer5) {
      if (framebufferSupported) {
         switch (framebufferType) {
            case 0:
               GL30.glFramebufferTexture2D(integer1, integer2, integer3, integer4, integer5);
               break;
            case 1:
               ARBFramebufferObject.glFramebufferTexture2D(integer1, integer2, integer3, integer4, integer5);
               break;
            case 2:
               EXTFramebufferObject.glFramebufferTexture2DEXT(integer1, integer2, integer3, integer4, integer5);
         }
      }
   }

   public static void setActiveTexture(int integer) {
      if (arbMultitexture) {
         ARBMultitexture.glActiveTextureARB(integer);
      } else {
         GL13.glActiveTexture(integer);
      }
   }

   public static void setClientActiveTexture(int integer) {
      if (arbMultitexture) {
         ARBMultitexture.glClientActiveTextureARB(integer);
      } else {
         GL13.glClientActiveTexture(integer);
      }
   }

   public static void setLightmapTextureCoords(int integer, float float2, float float3) {
      if (arbMultitexture) {
         ARBMultitexture.glMultiTexCoord2fARB(integer, float2, float3);
      } else {
         GL13.glMultiTexCoord2f(integer, float2, float3);
      }

      if (integer == lightmapTexUnit) {
         lastBrightnessX = float2;
         lastBrightnessY = float3;
      }
   }

   public static void glBlendFunc(int integer1, int integer2, int integer3, int integer4) {
      if (openGL14) {
         if (extBlendFuncSeparate) {
            EXTBlendFuncSeparate.glBlendFuncSeparateEXT(integer1, integer2, integer3, integer4);
         } else {
            GL14.glBlendFuncSeparate(integer1, integer2, integer3, integer4);
         }
      } else {
         GL11.glBlendFunc(integer1, integer2);
      }
   }

   public static boolean isFramebufferEnabled() {
      return !Config.isAntialiasing() && framebufferSupported && Minecraft.getMinecraft().gameSettings.fboEnable;
   }

   public static void glBufferData(int integer1, long long2, int integer3) {
      if (arbVbo) {
         ARBVertexBufferObject.glBufferDataARB(integer1, long2, integer3);
      } else {
         GL15.glBufferData(integer1, long2, integer3);
      }
   }

   public static void glBufferSubData(int integer, long long2, ByteBuffer byteBuffer) {
      if (arbVbo) {
         ARBVertexBufferObject.glBufferSubDataARB(integer, long2, byteBuffer);
      } else {
         GL15.glBufferSubData(integer, long2, byteBuffer);
      }
   }

   public static void glCopyBufferSubData(int integer1, int integer2, long long3, long long4, long long5) {
      if (openGL31) {
         GL31.glCopyBufferSubData(integer1, integer2, long3, long4, long5);
      } else {
         ARBCopyBuffer.glCopyBufferSubData(integer1, integer2, long3, long4, long5);
      }
   }

   public static String getCpu() {
      return cpu == null ? "<unknown>" : cpu;
   }
}
