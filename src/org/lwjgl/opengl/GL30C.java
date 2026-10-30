package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL30C extends GL21C {
   public static final int GL_MAJOR_VERSION = 33307;
   public static final int GL_MINOR_VERSION = 33308;
   public static final int GL_NUM_EXTENSIONS = 33309;
   public static final int GL_CONTEXT_FLAGS = 33310;
   public static final int GL_CONTEXT_FLAG_FORWARD_COMPATIBLE_BIT = 1;
   public static final int GL_COMPARE_REF_TO_TEXTURE = 34894;
   public static final int GL_CLIP_DISTANCE0 = 12288;
   public static final int GL_CLIP_DISTANCE1 = 12289;
   public static final int GL_CLIP_DISTANCE2 = 12290;
   public static final int GL_CLIP_DISTANCE3 = 12291;
   public static final int GL_CLIP_DISTANCE4 = 12292;
   public static final int GL_CLIP_DISTANCE5 = 12293;
   public static final int GL_CLIP_DISTANCE6 = 12294;
   public static final int GL_CLIP_DISTANCE7 = 12295;
   public static final int GL_MAX_CLIP_DISTANCES = 3378;
   public static final int GL_MAX_VARYING_COMPONENTS = 35659;
   public static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER = 35069;
   public static final int GL_SAMPLER_1D_ARRAY = 36288;
   public static final int GL_SAMPLER_2D_ARRAY = 36289;
   public static final int GL_SAMPLER_1D_ARRAY_SHADOW = 36291;
   public static final int GL_SAMPLER_2D_ARRAY_SHADOW = 36292;
   public static final int GL_SAMPLER_CUBE_SHADOW = 36293;
   public static final int GL_UNSIGNED_INT_VEC2 = 36294;
   public static final int GL_UNSIGNED_INT_VEC3 = 36295;
   public static final int GL_UNSIGNED_INT_VEC4 = 36296;
   public static final int GL_INT_SAMPLER_1D = 36297;
   public static final int GL_INT_SAMPLER_2D = 36298;
   public static final int GL_INT_SAMPLER_3D = 36299;
   public static final int GL_INT_SAMPLER_CUBE = 36300;
   public static final int GL_INT_SAMPLER_1D_ARRAY = 36302;
   public static final int GL_INT_SAMPLER_2D_ARRAY = 36303;
   public static final int GL_UNSIGNED_INT_SAMPLER_1D = 36305;
   public static final int GL_UNSIGNED_INT_SAMPLER_2D = 36306;
   public static final int GL_UNSIGNED_INT_SAMPLER_3D = 36307;
   public static final int GL_UNSIGNED_INT_SAMPLER_CUBE = 36308;
   public static final int GL_UNSIGNED_INT_SAMPLER_1D_ARRAY = 36310;
   public static final int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY = 36311;
   public static final int GL_MIN_PROGRAM_TEXEL_OFFSET = 35076;
   public static final int GL_MAX_PROGRAM_TEXEL_OFFSET = 35077;
   public static final int GL_QUERY_WAIT = 36371;
   public static final int GL_QUERY_NO_WAIT = 36372;
   public static final int GL_QUERY_BY_REGION_WAIT = 36373;
   public static final int GL_QUERY_BY_REGION_NO_WAIT = 36374;
   public static final int GL_MAP_READ_BIT = 1;
   public static final int GL_MAP_WRITE_BIT = 2;
   public static final int GL_MAP_INVALIDATE_RANGE_BIT = 4;
   public static final int GL_MAP_INVALIDATE_BUFFER_BIT = 8;
   public static final int GL_MAP_FLUSH_EXPLICIT_BIT = 16;
   public static final int GL_MAP_UNSYNCHRONIZED_BIT = 32;
   public static final int GL_BUFFER_ACCESS_FLAGS = 37151;
   public static final int GL_BUFFER_MAP_LENGTH = 37152;
   public static final int GL_BUFFER_MAP_OFFSET = 37153;
   public static final int GL_CLAMP_READ_COLOR = 35100;
   public static final int GL_FIXED_ONLY = 35101;
   public static final int GL_DEPTH_COMPONENT32F = 36012;
   public static final int GL_DEPTH32F_STENCIL8 = 36013;
   public static final int GL_FLOAT_32_UNSIGNED_INT_24_8_REV = 36269;
   public static final int GL_TEXTURE_RED_TYPE = 35856;
   public static final int GL_TEXTURE_GREEN_TYPE = 35857;
   public static final int GL_TEXTURE_BLUE_TYPE = 35858;
   public static final int GL_TEXTURE_ALPHA_TYPE = 35859;
   public static final int GL_TEXTURE_DEPTH_TYPE = 35862;
   public static final int GL_UNSIGNED_NORMALIZED = 35863;
   public static final int GL_RGBA32F = 34836;
   public static final int GL_RGB32F = 34837;
   public static final int GL_RGBA16F = 34842;
   public static final int GL_RGB16F = 34843;
   public static final int GL_R11F_G11F_B10F = 35898;
   public static final int GL_UNSIGNED_INT_10F_11F_11F_REV = 35899;
   public static final int GL_RGB9_E5 = 35901;
   public static final int GL_UNSIGNED_INT_5_9_9_9_REV = 35902;
   public static final int GL_TEXTURE_SHARED_SIZE = 35903;
   public static final int GL_FRAMEBUFFER = 36160;
   public static final int GL_READ_FRAMEBUFFER = 36008;
   public static final int GL_DRAW_FRAMEBUFFER = 36009;
   public static final int GL_RENDERBUFFER = 36161;
   public static final int GL_STENCIL_INDEX1 = 36166;
   public static final int GL_STENCIL_INDEX4 = 36167;
   public static final int GL_STENCIL_INDEX8 = 36168;
   public static final int GL_STENCIL_INDEX16 = 36169;
   public static final int GL_RENDERBUFFER_WIDTH = 36162;
   public static final int GL_RENDERBUFFER_HEIGHT = 36163;
   public static final int GL_RENDERBUFFER_INTERNAL_FORMAT = 36164;
   public static final int GL_RENDERBUFFER_RED_SIZE = 36176;
   public static final int GL_RENDERBUFFER_GREEN_SIZE = 36177;
   public static final int GL_RENDERBUFFER_BLUE_SIZE = 36178;
   public static final int GL_RENDERBUFFER_ALPHA_SIZE = 36179;
   public static final int GL_RENDERBUFFER_DEPTH_SIZE = 36180;
   public static final int GL_RENDERBUFFER_STENCIL_SIZE = 36181;
   public static final int GL_RENDERBUFFER_SAMPLES = 36011;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 36048;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 36049;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 36050;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 36051;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 36052;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 33296;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = 33297;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = 33298;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = 33299;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = 33300;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = 33301;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = 33302;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = 33303;
   public static final int GL_FRAMEBUFFER_DEFAULT = 33304;
   public static final int GL_COLOR_ATTACHMENT0 = 36064;
   public static final int GL_COLOR_ATTACHMENT1 = 36065;
   public static final int GL_COLOR_ATTACHMENT2 = 36066;
   public static final int GL_COLOR_ATTACHMENT3 = 36067;
   public static final int GL_COLOR_ATTACHMENT4 = 36068;
   public static final int GL_COLOR_ATTACHMENT5 = 36069;
   public static final int GL_COLOR_ATTACHMENT6 = 36070;
   public static final int GL_COLOR_ATTACHMENT7 = 36071;
   public static final int GL_COLOR_ATTACHMENT8 = 36072;
   public static final int GL_COLOR_ATTACHMENT9 = 36073;
   public static final int GL_COLOR_ATTACHMENT10 = 36074;
   public static final int GL_COLOR_ATTACHMENT11 = 36075;
   public static final int GL_COLOR_ATTACHMENT12 = 36076;
   public static final int GL_COLOR_ATTACHMENT13 = 36077;
   public static final int GL_COLOR_ATTACHMENT14 = 36078;
   public static final int GL_COLOR_ATTACHMENT15 = 36079;
   public static final int GL_COLOR_ATTACHMENT16 = 36080;
   public static final int GL_COLOR_ATTACHMENT17 = 36081;
   public static final int GL_COLOR_ATTACHMENT18 = 36082;
   public static final int GL_COLOR_ATTACHMENT19 = 36083;
   public static final int GL_COLOR_ATTACHMENT20 = 36084;
   public static final int GL_COLOR_ATTACHMENT21 = 36085;
   public static final int GL_COLOR_ATTACHMENT22 = 36086;
   public static final int GL_COLOR_ATTACHMENT23 = 36087;
   public static final int GL_COLOR_ATTACHMENT24 = 36088;
   public static final int GL_COLOR_ATTACHMENT25 = 36089;
   public static final int GL_COLOR_ATTACHMENT26 = 36090;
   public static final int GL_COLOR_ATTACHMENT27 = 36091;
   public static final int GL_COLOR_ATTACHMENT28 = 36092;
   public static final int GL_COLOR_ATTACHMENT29 = 36093;
   public static final int GL_COLOR_ATTACHMENT30 = 36094;
   public static final int GL_COLOR_ATTACHMENT31 = 36095;
   public static final int GL_DEPTH_ATTACHMENT = 36096;
   public static final int GL_STENCIL_ATTACHMENT = 36128;
   public static final int GL_DEPTH_STENCIL_ATTACHMENT = 33306;
   public static final int GL_MAX_SAMPLES = 36183;
   public static final int GL_FRAMEBUFFER_COMPLETE = 36053;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 36059;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 36060;
   public static final int GL_FRAMEBUFFER_UNSUPPORTED = 36061;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = 36182;
   public static final int GL_FRAMEBUFFER_UNDEFINED = 33305;
   public static final int GL_FRAMEBUFFER_BINDING = 36006;
   public static final int GL_DRAW_FRAMEBUFFER_BINDING = 36006;
   public static final int GL_READ_FRAMEBUFFER_BINDING = 36010;
   public static final int GL_RENDERBUFFER_BINDING = 36007;
   public static final int GL_MAX_COLOR_ATTACHMENTS = 36063;
   public static final int GL_MAX_RENDERBUFFER_SIZE = 34024;
   public static final int GL_INVALID_FRAMEBUFFER_OPERATION = 1286;
   public static final int GL_DEPTH_STENCIL = 34041;
   public static final int GL_UNSIGNED_INT_24_8 = 34042;
   public static final int GL_DEPTH24_STENCIL8 = 35056;
   public static final int GL_TEXTURE_STENCIL_SIZE = 35057;
   public static final int GL_HALF_FLOAT = 5131;
   public static final int GL_RGBA32UI = 36208;
   public static final int GL_RGB32UI = 36209;
   public static final int GL_RGBA16UI = 36214;
   public static final int GL_RGB16UI = 36215;
   public static final int GL_RGBA8UI = 36220;
   public static final int GL_RGB8UI = 36221;
   public static final int GL_RGBA32I = 36226;
   public static final int GL_RGB32I = 36227;
   public static final int GL_RGBA16I = 36232;
   public static final int GL_RGB16I = 36233;
   public static final int GL_RGBA8I = 36238;
   public static final int GL_RGB8I = 36239;
   public static final int GL_RED_INTEGER = 36244;
   public static final int GL_GREEN_INTEGER = 36245;
   public static final int GL_BLUE_INTEGER = 36246;
   public static final int GL_RGB_INTEGER = 36248;
   public static final int GL_RGBA_INTEGER = 36249;
   public static final int GL_BGR_INTEGER = 36250;
   public static final int GL_BGRA_INTEGER = 36251;
   public static final int GL_TEXTURE_1D_ARRAY = 35864;
   public static final int GL_TEXTURE_2D_ARRAY = 35866;
   public static final int GL_PROXY_TEXTURE_2D_ARRAY = 35867;
   public static final int GL_PROXY_TEXTURE_1D_ARRAY = 35865;
   public static final int GL_TEXTURE_BINDING_1D_ARRAY = 35868;
   public static final int GL_TEXTURE_BINDING_2D_ARRAY = 35869;
   public static final int GL_MAX_ARRAY_TEXTURE_LAYERS = 35071;
   public static final int GL_COMPRESSED_RED_RGTC1 = 36283;
   public static final int GL_COMPRESSED_SIGNED_RED_RGTC1 = 36284;
   public static final int GL_COMPRESSED_RG_RGTC2 = 36285;
   public static final int GL_COMPRESSED_SIGNED_RG_RGTC2 = 36286;
   public static final int GL_R8 = 33321;
   public static final int GL_R16 = 33322;
   public static final int GL_RG8 = 33323;
   public static final int GL_RG16 = 33324;
   public static final int GL_R16F = 33325;
   public static final int GL_R32F = 33326;
   public static final int GL_RG16F = 33327;
   public static final int GL_RG32F = 33328;
   public static final int GL_R8I = 33329;
   public static final int GL_R8UI = 33330;
   public static final int GL_R16I = 33331;
   public static final int GL_R16UI = 33332;
   public static final int GL_R32I = 33333;
   public static final int GL_R32UI = 33334;
   public static final int GL_RG8I = 33335;
   public static final int GL_RG8UI = 33336;
   public static final int GL_RG16I = 33337;
   public static final int GL_RG16UI = 33338;
   public static final int GL_RG32I = 33339;
   public static final int GL_RG32UI = 33340;
   public static final int GL_RG = 33319;
   public static final int GL_COMPRESSED_RED = 33317;
   public static final int GL_COMPRESSED_RG = 33318;
   public static final int GL_RG_INTEGER = 33320;
   public static final int GL_TRANSFORM_FEEDBACK_BUFFER = 35982;
   public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START = 35972;
   public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE = 35973;
   public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING = 35983;
   public static final int GL_INTERLEAVED_ATTRIBS = 35980;
   public static final int GL_SEPARATE_ATTRIBS = 35981;
   public static final int GL_PRIMITIVES_GENERATED = 35975;
   public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN = 35976;
   public static final int GL_RASTERIZER_DISCARD = 35977;
   public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS = 35978;
   public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS = 35979;
   public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS = 35968;
   public static final int GL_TRANSFORM_FEEDBACK_VARYINGS = 35971;
   public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE = 35967;
   public static final int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH = 35958;
   public static final int GL_VERTEX_ARRAY_BINDING = 34229;
   public static final int GL_FRAMEBUFFER_SRGB = 36281;

   protected GL30C() {
      throw new UnsupportedOperationException();
   }

   public static native long nglGetStringi(int var0, int var1);

   @Nullable
   @NativeType("GLubyte const *")
   public static String glGetStringi(@NativeType("GLenum") int name, @NativeType("GLuint") int index) {
      long __result = nglGetStringi(name, index);
      return MemoryUtil.memUTF8Safe(__result);
   }

   public static native void nglClearBufferiv(int var0, int var1, long var2);

   public static void glClearBufferiv(@NativeType("GLenum") int buffer, @NativeType("GLint") int drawbuffer, @NativeType("GLint const *") IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglClearBufferiv(buffer, drawbuffer, MemoryUtil.memAddress(value));
   }

   public static native void nglClearBufferuiv(int var0, int var1, long var2);

   public static void glClearBufferuiv(@NativeType("GLenum") int buffer, @NativeType("GLint") int drawbuffer, @NativeType("GLint const *") IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 4);
      }

      nglClearBufferuiv(buffer, drawbuffer, MemoryUtil.memAddress(value));
   }

   public static native void nglClearBufferfv(int var0, int var1, long var2);

   public static void glClearBufferfv(@NativeType("GLenum") int buffer, @NativeType("GLint") int drawbuffer, @NativeType("GLfloat const *") FloatBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglClearBufferfv(buffer, drawbuffer, MemoryUtil.memAddress(value));
   }

   public static native void glClearBufferfi(
      @NativeType("GLenum") int var0, @NativeType("GLint") int var1, @NativeType("GLfloat") float var2, @NativeType("GLint") int var3
   );

   public static native void glVertexAttribI1i(@NativeType("GLuint") int var0, @NativeType("GLint") int var1);

   public static native void glVertexAttribI2i(@NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glVertexAttribI3i(
      @NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2, @NativeType("GLint") int var3
   );

   public static native void glVertexAttribI4i(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4
   );

   public static native void glVertexAttribI1ui(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void glVertexAttribI2ui(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2);

   public static native void glVertexAttribI3ui(
      @NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2, @NativeType("GLint") int var3
   );

   public static native void glVertexAttribI4ui(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4
   );

   public static native void nglVertexAttribI1iv(int var0, long var1);

   public static void glVertexAttribI1iv(@NativeType("GLuint") int index, @NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttribI1iv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribI2iv(int var0, long var1);

   public static void glVertexAttribI2iv(@NativeType("GLuint") int index, @NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttribI2iv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribI3iv(int var0, long var1);

   public static void glVertexAttribI3iv(@NativeType("GLuint") int index, @NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttribI3iv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribI4iv(int var0, long var1);

   public static void glVertexAttribI4iv(@NativeType("GLuint") int index, @NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttribI4iv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribI1uiv(int var0, long var1);

   public static void glVertexAttribI1uiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttribI1uiv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribI2uiv(int var0, long var1);

   public static void glVertexAttribI2uiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttribI2uiv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribI3uiv(int var0, long var1);

   public static void glVertexAttribI3uiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttribI3uiv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribI4uiv(int var0, long var1);

   public static void glVertexAttribI4uiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttribI4uiv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribI4bv(int var0, long var1);

   public static void glVertexAttribI4bv(@NativeType("GLuint") int index, @NativeType("GLbyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttribI4bv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribI4sv(int var0, long var1);

   public static void glVertexAttribI4sv(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttribI4sv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribI4ubv(int var0, long var1);

   public static void glVertexAttribI4ubv(@NativeType("GLuint") int index, @NativeType("GLbyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttribI4ubv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribI4usv(int var0, long var1);

   public static void glVertexAttribI4usv(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttribI4usv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribIPointer(int var0, int var1, int var2, int var3, long var4);

   public static void glVertexAttribIPointer(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") ByteBuffer pointer
   ) {
      nglVertexAttribIPointer(index, size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexAttribIPointer(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") long pointer
   ) {
      nglVertexAttribIPointer(index, size, type, stride, pointer);
   }

   public static void glVertexAttribIPointer(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") ShortBuffer pointer
   ) {
      nglVertexAttribIPointer(index, size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexAttribIPointer(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") IntBuffer pointer
   ) {
      nglVertexAttribIPointer(index, size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static native void nglGetVertexAttribIiv(int var0, int var1, long var2);

   public static void glGetVertexAttribIiv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglGetVertexAttribIiv(index, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetVertexAttribIi(@NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetVertexAttribIiv(index, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetVertexAttribIuiv(int var0, int var1, long var2);

   public static void glGetVertexAttribIuiv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglGetVertexAttribIuiv(index, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetVertexAttribIui(@NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetVertexAttribIuiv(index, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glUniform1ui(@NativeType("GLint") int var0, @NativeType("GLuint") int var1);

   public static native void glUniform2ui(@NativeType("GLint") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2);

   public static native void glUniform3ui(
      @NativeType("GLint") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2, @NativeType("GLuint") int var3
   );

   public static native void glUniform4ui(
      @NativeType("GLint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLuint") int var2,
      @NativeType("GLuint") int var3,
      @NativeType("GLuint") int var4
   );

   public static native void nglUniform1uiv(int var0, int var1, long var2);

   public static void glUniform1uiv(@NativeType("GLint") int location, @NativeType("GLuint const *") IntBuffer value) {
      nglUniform1uiv(location, value.remaining(), MemoryUtil.memAddress(value));
   }

   public static native void nglUniform2uiv(int var0, int var1, long var2);

   public static void glUniform2uiv(@NativeType("GLint") int location, @NativeType("GLuint const *") IntBuffer value) {
      nglUniform2uiv(location, value.remaining() >> 1, MemoryUtil.memAddress(value));
   }

   public static native void nglUniform3uiv(int var0, int var1, long var2);

   public static void glUniform3uiv(@NativeType("GLint") int location, @NativeType("GLuint const *") IntBuffer value) {
      nglUniform3uiv(location, value.remaining() / 3, MemoryUtil.memAddress(value));
   }

   public static native void nglUniform4uiv(int var0, int var1, long var2);

   public static void glUniform4uiv(@NativeType("GLint") int location, @NativeType("GLuint const *") IntBuffer value) {
      nglUniform4uiv(location, value.remaining() >> 2, MemoryUtil.memAddress(value));
   }

   public static native void nglGetUniformuiv(int var0, int var1, long var2);

   public static void glGetUniformuiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetUniformuiv(program, location, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetUniformui(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetUniformuiv(program, location, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglBindFragDataLocation(int var0, int var1, long var2);

   public static void glBindFragDataLocation(
      @NativeType("GLuint") int program, @NativeType("GLuint") int colorNumber, @NativeType("GLchar const *") ByteBuffer name
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      nglBindFragDataLocation(program, colorNumber, MemoryUtil.memAddress(name));
   }

   public static void glBindFragDataLocation(
      @NativeType("GLuint") int program, @NativeType("GLuint") int colorNumber, @NativeType("GLchar const *") CharSequence name
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         nglBindFragDataLocation(program, colorNumber, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native int nglGetFragDataLocation(int var0, long var1);

   @NativeType("GLint")
   public static int glGetFragDataLocation(@NativeType("GLuint") int program, @NativeType("GLchar const *") ByteBuffer name) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      return nglGetFragDataLocation(program, MemoryUtil.memAddress(name));
   }

   @NativeType("GLint")
   public static int glGetFragDataLocation(@NativeType("GLuint") int program, @NativeType("GLchar const *") CharSequence name) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         return nglGetFragDataLocation(program, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glBeginConditionalRender(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static native void glEndConditionalRender();

   public static native long nglMapBufferRange(int var0, long var1, long var3, int var5);

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapBufferRange(
      @NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("GLsizeiptr") long length, @NativeType("GLbitfield") int access
   ) {
      long __result = nglMapBufferRange(target, offset, length, access);
      return MemoryUtil.memByteBufferSafe(__result, (int)length);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapBufferRange(
      @NativeType("GLenum") int target,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long length,
      @NativeType("GLbitfield") int access,
      @Nullable ByteBuffer old_buffer
   ) {
      long __result = nglMapBufferRange(target, offset, length, access);
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, (int)length);
   }

   public static native void glFlushMappedBufferRange(@NativeType("GLenum") int var0, @NativeType("GLintptr") long var1, @NativeType("GLsizeiptr") long var3);

   public static native void glClampColor(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1);

   @NativeType("GLboolean")
   public static native boolean glIsRenderbuffer(@NativeType("GLuint") int var0);

   public static native void glBindRenderbuffer(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void nglDeleteRenderbuffers(int var0, long var1);

   public static void glDeleteRenderbuffers(@NativeType("GLuint const *") IntBuffer renderbuffers) {
      nglDeleteRenderbuffers(renderbuffers.remaining(), MemoryUtil.memAddress(renderbuffers));
   }

   public static void glDeleteRenderbuffers(@NativeType("GLuint const *") int renderbuffer) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer renderbuffers = stack.ints(renderbuffer);
         nglDeleteRenderbuffers(1, MemoryUtil.memAddress(renderbuffers));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGenRenderbuffers(int var0, long var1);

   public static void glGenRenderbuffers(@NativeType("GLuint *") IntBuffer renderbuffers) {
      nglGenRenderbuffers(renderbuffers.remaining(), MemoryUtil.memAddress(renderbuffers));
   }

   @NativeType("void")
   public static int glGenRenderbuffers() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer renderbuffers = stack.callocInt(1);
         nglGenRenderbuffers(1, MemoryUtil.memAddress(renderbuffers));
         return renderbuffers.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glRenderbufferStorage(
      @NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLsizei") int var2, @NativeType("GLsizei") int var3
   );

   public static native void glRenderbufferStorageMultisample(
      @NativeType("GLenum") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLsizei") int var3,
      @NativeType("GLsizei") int var4
   );

   public static native void nglGetRenderbufferParameteriv(int var0, int var1, long var2);

   public static void glGetRenderbufferParameteriv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetRenderbufferParameteriv(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetRenderbufferParameteri(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetRenderbufferParameteriv(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsFramebuffer(@NativeType("GLuint") int var0);

   public static native void glBindFramebuffer(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void nglDeleteFramebuffers(int var0, long var1);

   public static void glDeleteFramebuffers(@NativeType("GLuint const *") IntBuffer framebuffers) {
      nglDeleteFramebuffers(framebuffers.remaining(), MemoryUtil.memAddress(framebuffers));
   }

   public static void glDeleteFramebuffers(@NativeType("GLuint const *") int framebuffer) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer framebuffers = stack.ints(framebuffer);
         nglDeleteFramebuffers(1, MemoryUtil.memAddress(framebuffers));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGenFramebuffers(int var0, long var1);

   public static void glGenFramebuffers(@NativeType("GLuint *") IntBuffer framebuffers) {
      nglGenFramebuffers(framebuffers.remaining(), MemoryUtil.memAddress(framebuffers));
   }

   @NativeType("void")
   public static int glGenFramebuffers() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer framebuffers = stack.callocInt(1);
         nglGenFramebuffers(1, MemoryUtil.memAddress(framebuffers));
         return framebuffers.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLenum")
   public static native int glCheckFramebufferStatus(@NativeType("GLenum") int var0);

   public static native void glFramebufferTexture1D(
      @NativeType("GLenum") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLuint") int var3,
      @NativeType("GLint") int var4
   );

   public static native void glFramebufferTexture2D(
      @NativeType("GLenum") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLuint") int var3,
      @NativeType("GLint") int var4
   );

   public static native void glFramebufferTexture3D(
      @NativeType("GLenum") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLuint") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLint") int var5
   );

   public static native void glFramebufferTextureLayer(
      @NativeType("GLenum") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLuint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4
   );

   public static native void glFramebufferRenderbuffer(
      @NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLenum") int var2, @NativeType("GLuint") int var3
   );

   public static native void nglGetFramebufferAttachmentParameteriv(int var0, int var1, int var2, long var3);

   public static void glGetFramebufferAttachmentParameteriv(
      @NativeType("GLenum") int target, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetFramebufferAttachmentParameteriv(target, attachment, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetFramebufferAttachmentParameteri(
      @NativeType("GLenum") int target, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetFramebufferAttachmentParameteriv(target, attachment, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glBlitFramebuffer(
      @NativeType("GLint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLint") int var5,
      @NativeType("GLint") int var6,
      @NativeType("GLint") int var7,
      @NativeType("GLbitfield") int var8,
      @NativeType("GLenum") int var9
   );

   public static native void glGenerateMipmap(@NativeType("GLenum") int var0);

   public static native void nglTexParameterIiv(int var0, int var1, long var2);

   public static void glTexParameterIiv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglTexParameterIiv(target, pname, MemoryUtil.memAddress(params));
   }

   public static void glTexParameterIi(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") int param) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.ints(param);
         nglTexParameterIiv(target, pname, MemoryUtil.memAddress(params));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglTexParameterIuiv(int var0, int var1, long var2);

   public static void glTexParameterIuiv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglTexParameterIuiv(target, pname, MemoryUtil.memAddress(params));
   }

   public static void glTexParameterIui(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint const *") int param) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.ints(param);
         nglTexParameterIuiv(target, pname, MemoryUtil.memAddress(params));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTexParameterIiv(int var0, int var1, long var2);

   public static void glGetTexParameterIiv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetTexParameterIiv(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetTexParameterIi(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetTexParameterIiv(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTexParameterIuiv(int var0, int var1, long var2);

   public static void glGetTexParameterIuiv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetTexParameterIuiv(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetTexParameterIui(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetTexParameterIuiv(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glColorMaski(
      @NativeType("GLuint") int var0,
      @NativeType("GLboolean") boolean var1,
      @NativeType("GLboolean") boolean var2,
      @NativeType("GLboolean") boolean var3,
      @NativeType("GLboolean") boolean var4
   );

   public static native void nglGetBooleani_v(int var0, int var1, long var2);

   public static void glGetBooleani_v(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLboolean *") ByteBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 1);
      }

      nglGetBooleani_v(target, index, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static boolean glGetBooleani(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         ByteBuffer data = stack.calloc(1);
         nglGetBooleani_v(target, index, MemoryUtil.memAddress(data));
         return data.get(0) != 0;
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetIntegeri_v(int var0, int var1, long var2);

   public static void glGetIntegeri_v(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint *") IntBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 1);
      }

      nglGetIntegeri_v(target, index, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static int glGetIntegeri(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer data = stack.callocInt(1);
         nglGetIntegeri_v(target, index, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glEnablei(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void glDisablei(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   @NativeType("GLboolean")
   public static native boolean glIsEnabledi(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void glBindBufferRange(
      @NativeType("GLenum") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLuint") int var2,
      @NativeType("GLintptr") long var3,
      @NativeType("GLsizeiptr") long var5
   );

   public static native void glBindBufferBase(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2);

   public static native void glBeginTransformFeedback(@NativeType("GLenum") int var0);

   public static native void glEndTransformFeedback();

   public static native void nglTransformFeedbackVaryings(int var0, int var1, long var2, int var4);

   public static void glTransformFeedbackVaryings(
      @NativeType("GLuint") int program, @NativeType("GLchar const **") PointerBuffer varyings, @NativeType("GLenum") int bufferMode
   ) {
      nglTransformFeedbackVaryings(program, varyings.remaining(), MemoryUtil.memAddress(varyings), bufferMode);
   }

   public static void glTransformFeedbackVaryings(
      @NativeType("GLuint") int program, @NativeType("GLchar const **") CharSequence[] varyings, @NativeType("GLenum") int bufferMode
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         long varyingsAddress = APIUtil.apiArray(stack, MemoryUtil::memASCII, varyings);
         nglTransformFeedbackVaryings(program, varyings.length, varyingsAddress, bufferMode);
         APIUtil.apiArrayFree(varyingsAddress, varyings.length);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glTransformFeedbackVaryings(
      @NativeType("GLuint") int program, @NativeType("GLchar const **") CharSequence varying, @NativeType("GLenum") int bufferMode
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         long varyingsAddress = APIUtil.apiArray(stack, MemoryUtil::memASCII, varying);
         nglTransformFeedbackVaryings(program, 1, varyingsAddress, bufferMode);
         APIUtil.apiArrayFree(varyingsAddress, 1);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTransformFeedbackVarying(int var0, int var1, int var2, long var3, long var5, long var7, long var9);

   public static void glGetTransformFeedbackVarying(
      @NativeType("GLuint") int program,
      @NativeType("GLuint") int index,
      @Nullable @NativeType("GLsizei *") IntBuffer length,
      @NativeType("GLsizei *") IntBuffer size,
      @NativeType("GLenum *") IntBuffer type,
      @NativeType("GLchar *") ByteBuffer name
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(length, 1);
         Checks.check(size, 1);
         Checks.check(type, 1);
      }

      nglGetTransformFeedbackVarying(
         program,
         index,
         name.remaining(),
         MemoryUtil.memAddressSafe(length),
         MemoryUtil.memAddress(size),
         MemoryUtil.memAddress(type),
         MemoryUtil.memAddress(name)
      );
   }

   @NativeType("void")
   public static String glGetTransformFeedbackVarying(
      @NativeType("GLuint") int program,
      @NativeType("GLuint") int index,
      @NativeType("GLsizei") int bufSize,
      @NativeType("GLsizei *") IntBuffer size,
      @NativeType("GLenum *") IntBuffer type
   ) {
      if (Checks.CHECKS) {
         Checks.check(size, 1);
         Checks.check(type, 1);
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer length = stack.ints(0);
         ByteBuffer name = stack.malloc(bufSize);
         nglGetTransformFeedbackVarying(
            program, index, bufSize, MemoryUtil.memAddress(length), MemoryUtil.memAddress(size), MemoryUtil.memAddress(type), MemoryUtil.memAddress(name)
         );
         return MemoryUtil.memASCII(name, length.get(0));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("void")
   public static String glGetTransformFeedbackVarying(
      @NativeType("GLuint") int program, @NativeType("GLuint") int index, @NativeType("GLsizei *") IntBuffer size, @NativeType("GLenum *") IntBuffer type
   ) {
      return glGetTransformFeedbackVarying(program, index, GL20.glGetProgrami(program, 35958), size, type);
   }

   public static native void glBindVertexArray(@NativeType("GLuint") int var0);

   public static native void nglDeleteVertexArrays(int var0, long var1);

   public static void glDeleteVertexArrays(@NativeType("GLuint const *") IntBuffer arrays) {
      nglDeleteVertexArrays(arrays.remaining(), MemoryUtil.memAddress(arrays));
   }

   public static void glDeleteVertexArrays(@NativeType("GLuint const *") int array) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer arrays = stack.ints(array);
         nglDeleteVertexArrays(1, MemoryUtil.memAddress(arrays));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGenVertexArrays(int var0, long var1);

   public static void glGenVertexArrays(@NativeType("GLuint *") IntBuffer arrays) {
      nglGenVertexArrays(arrays.remaining(), MemoryUtil.memAddress(arrays));
   }

   @NativeType("void")
   public static int glGenVertexArrays() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer arrays = stack.callocInt(1);
         nglGenVertexArrays(1, MemoryUtil.memAddress(arrays));
         return arrays.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsVertexArray(@NativeType("GLuint") int var0);

   public static void glClearBufferiv(@NativeType("GLenum") int buffer, @NativeType("GLint") int drawbuffer, @NativeType("GLint const *") int[] value) {
      long __functionAddress = GL.getICD().glClearBufferiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(buffer, drawbuffer, value, __functionAddress);
   }

   public static void glClearBufferuiv(@NativeType("GLenum") int buffer, @NativeType("GLint") int drawbuffer, @NativeType("GLint const *") int[] value) {
      long __functionAddress = GL.getICD().glClearBufferuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 4);
      }

      JNI.callPV(buffer, drawbuffer, value, __functionAddress);
   }

   public static void glClearBufferfv(@NativeType("GLenum") int buffer, @NativeType("GLint") int drawbuffer, @NativeType("GLfloat const *") float[] value) {
      long __functionAddress = GL.getICD().glClearBufferfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(buffer, drawbuffer, value, __functionAddress);
   }

   public static void glVertexAttribI1iv(@NativeType("GLuint") int index, @NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttribI1iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribI2iv(@NativeType("GLuint") int index, @NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttribI2iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribI3iv(@NativeType("GLuint") int index, @NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttribI3iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribI4iv(@NativeType("GLuint") int index, @NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttribI4iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribI1uiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttribI1uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribI2uiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttribI2uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribI3uiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttribI3uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribI4uiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttribI4uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribI4sv(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttribI4sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribI4usv(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttribI4usv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glGetVertexAttribIiv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribIiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(index, pname, params, __functionAddress);
   }

   public static void glGetVertexAttribIuiv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribIuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(index, pname, params, __functionAddress);
   }

   public static void glUniform1uiv(@NativeType("GLint") int location, @NativeType("GLuint const *") int[] value) {
      long __functionAddress = GL.getICD().glUniform1uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length, value, __functionAddress);
   }

   public static void glUniform2uiv(@NativeType("GLint") int location, @NativeType("GLuint const *") int[] value) {
      long __functionAddress = GL.getICD().glUniform2uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 1, value, __functionAddress);
   }

   public static void glUniform3uiv(@NativeType("GLint") int location, @NativeType("GLuint const *") int[] value) {
      long __functionAddress = GL.getICD().glUniform3uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 3, value, __functionAddress);
   }

   public static void glUniform4uiv(@NativeType("GLint") int location, @NativeType("GLuint const *") int[] value) {
      long __functionAddress = GL.getICD().glUniform4uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 2, value, __functionAddress);
   }

   public static void glGetUniformuiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint *") int[] params) {
      long __functionAddress = GL.getICD().glGetUniformuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(program, location, params, __functionAddress);
   }

   public static void glDeleteRenderbuffers(@NativeType("GLuint const *") int[] renderbuffers) {
      long __functionAddress = GL.getICD().glDeleteRenderbuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(renderbuffers.length, renderbuffers, __functionAddress);
   }

   public static void glGenRenderbuffers(@NativeType("GLuint *") int[] renderbuffers) {
      long __functionAddress = GL.getICD().glGenRenderbuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(renderbuffers.length, renderbuffers, __functionAddress);
   }

   public static void glGetRenderbufferParameteriv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetRenderbufferParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glDeleteFramebuffers(@NativeType("GLuint const *") int[] framebuffers) {
      long __functionAddress = GL.getICD().glDeleteFramebuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(framebuffers.length, framebuffers, __functionAddress);
   }

   public static void glGenFramebuffers(@NativeType("GLuint *") int[] framebuffers) {
      long __functionAddress = GL.getICD().glGenFramebuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(framebuffers.length, framebuffers, __functionAddress);
   }

   public static void glGetFramebufferAttachmentParameteriv(
      @NativeType("GLenum") int target, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      long __functionAddress = GL.getICD().glGetFramebufferAttachmentParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, attachment, pname, params, __functionAddress);
   }

   public static void glTexParameterIiv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glTexParameterIiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glTexParameterIuiv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint const *") int[] params) {
      long __functionAddress = GL.getICD().glTexParameterIuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glGetTexParameterIiv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetTexParameterIiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glGetTexParameterIuiv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
      long __functionAddress = GL.getICD().glGetTexParameterIuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glGetIntegeri_v(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint *") int[] data) {
      long __functionAddress = GL.getICD().glGetIntegeri_v;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 1);
      }

      JNI.callPV(target, index, data, __functionAddress);
   }

   public static void glGetTransformFeedbackVarying(
      @NativeType("GLuint") int program,
      @NativeType("GLuint") int index,
      @Nullable @NativeType("GLsizei *") int[] length,
      @NativeType("GLsizei *") int[] size,
      @NativeType("GLenum *") int[] type,
      @NativeType("GLchar *") ByteBuffer name
   ) {
      long __functionAddress = GL.getICD().glGetTransformFeedbackVarying;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(length, 1);
         Checks.check(size, 1);
         Checks.check(type, 1);
      }

      JNI.callPPPPV(program, index, name.remaining(), length, size, type, MemoryUtil.memAddress(name), __functionAddress);
   }

   public static void glDeleteVertexArrays(@NativeType("GLuint const *") int[] arrays) {
      long __functionAddress = GL.getICD().glDeleteVertexArrays;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(arrays.length, arrays, __functionAddress);
   }

   public static void glGenVertexArrays(@NativeType("GLuint *") int[] arrays) {
      long __functionAddress = GL.getICD().glGenVertexArrays;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(arrays.length, arrays, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
