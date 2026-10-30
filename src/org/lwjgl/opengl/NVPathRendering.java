package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVPathRendering {
   public static final byte GL_CLOSE_PATH_NV = 0;
   public static final byte GL_MOVE_TO_NV = 2;
   public static final byte GL_RELATIVE_MOVE_TO_NV = 3;
   public static final byte GL_LINE_TO_NV = 4;
   public static final byte GL_RELATIVE_LINE_TO_NV = 5;
   public static final byte GL_HORIZONTAL_LINE_TO_NV = 6;
   public static final byte GL_RELATIVE_HORIZONTAL_LINE_TO_NV = 7;
   public static final byte GL_VERTICAL_LINE_TO_NV = 8;
   public static final byte GL_RELATIVE_VERTICAL_LINE_TO_NV = 9;
   public static final byte GL_QUADRATIC_CURVE_TO_NV = 10;
   public static final byte GL_RELATIVE_QUADRATIC_CURVE_TO_NV = 11;
   public static final byte GL_CUBIC_CURVE_TO_NV = 12;
   public static final byte GL_RELATIVE_CUBIC_CURVE_TO_NV = 13;
   public static final byte GL_SMOOTH_QUADRATIC_CURVE_TO_NV = 14;
   public static final byte GL_RELATIVE_SMOOTH_QUADRATIC_CURVE_TO_NV = 15;
   public static final byte GL_SMOOTH_CUBIC_CURVE_TO_NV = 16;
   public static final byte GL_RELATIVE_SMOOTH_CUBIC_CURVE_TO_NV = 17;
   public static final byte GL_SMALL_CCW_ARC_TO_NV = 18;
   public static final byte GL_RELATIVE_SMALL_CCW_ARC_TO_NV = 19;
   public static final byte GL_SMALL_CW_ARC_TO_NV = 20;
   public static final byte GL_RELATIVE_SMALL_CW_ARC_TO_NV = 21;
   public static final byte GL_LARGE_CCW_ARC_TO_NV = 22;
   public static final byte GL_RELATIVE_LARGE_CCW_ARC_TO_NV = 23;
   public static final byte GL_LARGE_CW_ARC_TO_NV = 24;
   public static final byte GL_RELATIVE_LARGE_CW_ARC_TO_NV = 25;
   public static final byte GL_CONIC_CURVE_TO_NV = 26;
   public static final byte GL_RELATIVE_CONIC_CURVE_TO_NV = 27;
   public static final byte GL_ROUNDED_RECT_NV = -24;
   public static final byte GL_RELATIVE_ROUNDED_RECT_NV = -23;
   public static final byte GL_ROUNDED_RECT2_NV = -22;
   public static final byte GL_RELATIVE_ROUNDED_RECT2_NV = -21;
   public static final byte GL_ROUNDED_RECT4_NV = -20;
   public static final byte GL_RELATIVE_ROUNDED_RECT4_NV = -19;
   public static final byte GL_ROUNDED_RECT8_NV = -18;
   public static final byte GL_RELATIVE_ROUNDED_RECT8_NV = -17;
   public static final byte GL_RESTART_PATH_NV = -16;
   public static final byte GL_DUP_FIRST_CUBIC_CURVE_TO_NV = -14;
   public static final byte GL_DUP_LAST_CUBIC_CURVE_TO_NV = -12;
   public static final byte GL_RECT_NV = -10;
   public static final byte GL_RELATIVE_RECT_NV = -9;
   public static final byte GL_CIRCULAR_CCW_ARC_TO_NV = -8;
   public static final byte GL_CIRCULAR_CW_ARC_TO_NV = -6;
   public static final byte GL_CIRCULAR_TANGENT_ARC_TO_NV = -4;
   public static final byte GL_ARC_TO_NV = -2;
   public static final byte GL_RELATIVE_ARC_TO_NV = -1;
   public static final int GL_PATH_FORMAT_SVG_NV = 36976;
   public static final int GL_PATH_FORMAT_PS_NV = 36977;
   public static final int GL_STANDARD_FONT_NAME_NV = 36978;
   public static final int GL_SYSTEM_FONT_NAME_NV = 36979;
   public static final int GL_FILE_NAME_NV = 36980;
   public static final int GL_STANDARD_FONT_FORMAT_NV = 37740;
   public static final int GL_SKIP_MISSING_GLYPH_NV = 37033;
   public static final int GL_USE_MISSING_GLYPH_NV = 37034;
   public static final int GL_FONT_GLYPHS_AVAILABLE_NV = 37736;
   public static final int GL_FONT_TARGET_UNAVAILABLE_NV = 37737;
   public static final int GL_FONT_UNAVAILABLE_NV = 37738;
   public static final int GL_FONT_UNINTELLIGIBLE_NV = 37739;
   public static final int GL_PATH_STROKE_WIDTH_NV = 36981;
   public static final int GL_PATH_INITIAL_END_CAP_NV = 36983;
   public static final int GL_PATH_TERMINAL_END_CAP_NV = 36984;
   public static final int GL_PATH_JOIN_STYLE_NV = 36985;
   public static final int GL_PATH_MITER_LIMIT_NV = 36986;
   public static final int GL_PATH_INITIAL_DASH_CAP_NV = 36988;
   public static final int GL_PATH_TERMINAL_DASH_CAP_NV = 36989;
   public static final int GL_PATH_DASH_OFFSET_NV = 36990;
   public static final int GL_PATH_CLIENT_LENGTH_NV = 36991;
   public static final int GL_PATH_DASH_OFFSET_RESET_NV = 37044;
   public static final int GL_PATH_FILL_MODE_NV = 36992;
   public static final int GL_PATH_FILL_MASK_NV = 36993;
   public static final int GL_PATH_FILL_COVER_MODE_NV = 36994;
   public static final int GL_PATH_STROKE_COVER_MODE_NV = 36995;
   public static final int GL_PATH_STROKE_MASK_NV = 36996;
   public static final int GL_PATH_STROKE_BOUND_NV = 36998;
   public static final int GL_PATH_END_CAPS_NV = 36982;
   public static final int GL_PATH_DASH_CAPS_NV = 36987;
   public static final int GL_COUNT_UP_NV = 37000;
   public static final int GL_COUNT_DOWN_NV = 37001;
   public static final int GL_PRIMARY_COLOR_NV = 34092;
   public static final int GL_SECONDARY_COLOR_NV = 34093;
   public static final int GL_PATH_OBJECT_BOUNDING_BOX_NV = 37002;
   public static final int GL_CONVEX_HULL_NV = 37003;
   public static final int GL_BOUNDING_BOX_NV = 37005;
   public static final int GL_TRANSLATE_X_NV = 37006;
   public static final int GL_TRANSLATE_Y_NV = 37007;
   public static final int GL_TRANSLATE_2D_NV = 37008;
   public static final int GL_TRANSLATE_3D_NV = 37009;
   public static final int GL_AFFINE_2D_NV = 37010;
   public static final int GL_AFFINE_3D_NV = 37012;
   public static final int GL_TRANSPOSE_AFFINE_2D_NV = 37014;
   public static final int GL_TRANSPOSE_AFFINE_3D_NV = 37016;
   public static final int GL_UTF8_NV = 37018;
   public static final int GL_UTF16_NV = 37019;
   public static final int GL_BOUNDING_BOX_OF_BOUNDING_BOXES_NV = 37020;
   public static final int GL_PATH_COMMAND_COUNT_NV = 37021;
   public static final int GL_PATH_COORD_COUNT_NV = 37022;
   public static final int GL_PATH_DASH_ARRAY_COUNT_NV = 37023;
   public static final int GL_PATH_COMPUTED_LENGTH_NV = 37024;
   public static final int GL_PATH_FILL_BOUNDING_BOX_NV = 37025;
   public static final int GL_PATH_STROKE_BOUNDING_BOX_NV = 37026;
   public static final int GL_SQUARE_NV = 37027;
   public static final int GL_ROUND_NV = 37028;
   public static final int GL_TRIANGULAR_NV = 37029;
   public static final int GL_BEVEL_NV = 37030;
   public static final int GL_MITER_REVERT_NV = 37031;
   public static final int GL_MITER_TRUNCATE_NV = 37032;
   public static final int GL_MOVE_TO_RESETS_NV = 37045;
   public static final int GL_MOVE_TO_CONTINUES_NV = 37046;
   public static final int GL_BOLD_BIT_NV = 1;
   public static final int GL_ITALIC_BIT_NV = 2;
   public static final int GL_PATH_ERROR_POSITION_NV = 37035;
   public static final int GL_PATH_FOG_GEN_MODE_NV = 37036;
   public static final int GL_PATH_STENCIL_FUNC_NV = 37047;
   public static final int GL_PATH_STENCIL_REF_NV = 37048;
   public static final int GL_PATH_STENCIL_VALUE_MASK_NV = 37049;
   public static final int GL_PATH_STENCIL_DEPTH_OFFSET_FACTOR_NV = 37053;
   public static final int GL_PATH_STENCIL_DEPTH_OFFSET_UNITS_NV = 37054;
   public static final int GL_PATH_COVER_DEPTH_FUNC_NV = 37055;
   public static final int GL_GLYPH_WIDTH_BIT_NV = 1;
   public static final int GL_GLYPH_HEIGHT_BIT_NV = 2;
   public static final int GL_GLYPH_HORIZONTAL_BEARING_X_BIT_NV = 4;
   public static final int GL_GLYPH_HORIZONTAL_BEARING_Y_BIT_NV = 8;
   public static final int GL_GLYPH_HORIZONTAL_BEARING_ADVANCE_BIT_NV = 16;
   public static final int GL_GLYPH_VERTICAL_BEARING_X_BIT_NV = 32;
   public static final int GL_GLYPH_VERTICAL_BEARING_Y_BIT_NV = 64;
   public static final int GL_GLYPH_VERTICAL_BEARING_ADVANCE_BIT_NV = 128;
   public static final int GL_GLYPH_HAS_KERNING_BIT_NV = 256;
   public static final int GL_FONT_X_MIN_BOUNDS_BIT_NV = 65536;
   public static final int GL_FONT_Y_MIN_BOUNDS_BIT_NV = 131072;
   public static final int GL_FONT_X_MAX_BOUNDS_BIT_NV = 262144;
   public static final int GL_FONT_Y_MAX_BOUNDS_BIT_NV = 524288;
   public static final int GL_FONT_UNITS_PER_EM_BIT_NV = 1048576;
   public static final int GL_FONT_ASCENDER_BIT_NV = 2097152;
   public static final int GL_FONT_DESCENDER_BIT_NV = 4194304;
   public static final int GL_FONT_HEIGHT_BIT_NV = 8388608;
   public static final int GL_FONT_MAX_ADVANCE_WIDTH_BIT_NV = 16777216;
   public static final int GL_FONT_MAX_ADVANCE_HEIGHT_BIT_NV = 33554432;
   public static final int GL_FONT_UNDERLINE_POSITION_BIT_NV = 67108864;
   public static final int GL_FONT_UNDERLINE_THICKNESS_BIT_NV = 134217728;
   public static final int GL_FONT_HAS_KERNING_BIT_NV = 268435456;
   public static final int GL_FONT_NUM_GLYPH_INDICES_BIT_NV = 536870912;
   public static final int GL_ACCUM_ADJACENT_PAIRS_NV = 37037;
   public static final int GL_ADJACENT_PAIRS_NV = 37038;
   public static final int GL_FIRST_TO_REST_NV = 37039;
   public static final int GL_PATH_GEN_MODE_NV = 37040;
   public static final int GL_PATH_GEN_COEFF_NV = 37041;
   public static final int GL_PATH_GEN_COLOR_FORMAT_NV = 37042;
   public static final int GL_PATH_GEN_COMPONENTS_NV = 37043;
   public static final int GL_FRAGMENT_INPUT_NV = 37741;
   public static final int GL_PATH_PROJECTION_NV = 5889;
   public static final int GL_PATH_MODELVIEW_NV = 5888;
   public static final int GL_PATH_MODELVIEW_STACK_DEPTH_NV = 2979;
   public static final int GL_PATH_MODELVIEW_MATRIX_NV = 2982;
   public static final int GL_PATH_MAX_MODELVIEW_STACK_DEPTH_NV = 3382;
   public static final int GL_PATH_TRANSPOSE_MODELVIEW_MATRIX_NV = 34019;
   public static final int GL_PATH_PROJECTION_STACK_DEPTH_NV = 2980;
   public static final int GL_PATH_PROJECTION_MATRIX_NV = 2983;
   public static final int GL_PATH_MAX_PROJECTION_STACK_DEPTH_NV = 3384;
   public static final int GL_PATH_TRANSPOSE_PROJECTION_MATRIX_NV = 34020;
   public static final int GL_2_BYTES_NV = 5127;
   public static final int GL_3_BYTES_NV = 5128;
   public static final int GL_4_BYTES_NV = 5129;
   public static final int GL_EYE_LINEAR_NV = 9216;
   public static final int GL_OBJECT_LINEAR_NV = 9217;
   public static final int GL_CONSTANT_NV = 34166;

   protected NVPathRendering() {
      throw new UnsupportedOperationException();
   }

   public static native void nglPathCommandsNV(int var0, int var1, long var2, int var4, int var5, long var6);

   public static void glPathCommandsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLubyte const *") ByteBuffer commands,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") ByteBuffer coords
   ) {
      nglPathCommandsNV(
         path,
         commands.remaining(),
         MemoryUtil.memAddress(commands),
         coords.remaining() >> GLChecks.typeToByteShift(coordType),
         coordType,
         MemoryUtil.memAddress(coords)
      );
   }

   public static void glPathCommandsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLubyte const *") ByteBuffer commands,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") ShortBuffer coords
   ) {
      nglPathCommandsNV(
         path,
         commands.remaining(),
         MemoryUtil.memAddress(commands),
         (int)((long)coords.remaining() << 1 >> GLChecks.typeToByteShift(coordType)),
         coordType,
         MemoryUtil.memAddress(coords)
      );
   }

   public static void glPathCommandsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLubyte const *") ByteBuffer commands,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") FloatBuffer coords
   ) {
      nglPathCommandsNV(
         path,
         commands.remaining(),
         MemoryUtil.memAddress(commands),
         (int)((long)coords.remaining() << 2 >> GLChecks.typeToByteShift(coordType)),
         coordType,
         MemoryUtil.memAddress(coords)
      );
   }

   public static native void nglPathCoordsNV(int var0, int var1, int var2, long var3);

   public static void glPathCoordsNV(@NativeType("GLuint") int path, @NativeType("GLenum") int coordType, @NativeType("void const *") ByteBuffer coords) {
      nglPathCoordsNV(path, coords.remaining() >> GLChecks.typeToByteShift(coordType), coordType, MemoryUtil.memAddress(coords));
   }

   public static void glPathCoordsNV(@NativeType("GLuint") int path, @NativeType("GLenum") int coordType, @NativeType("void const *") ShortBuffer coords) {
      nglPathCoordsNV(path, (int)((long)coords.remaining() << 1 >> GLChecks.typeToByteShift(coordType)), coordType, MemoryUtil.memAddress(coords));
   }

   public static void glPathCoordsNV(@NativeType("GLuint") int path, @NativeType("GLenum") int coordType, @NativeType("void const *") FloatBuffer coords) {
      nglPathCoordsNV(path, (int)((long)coords.remaining() << 2 >> GLChecks.typeToByteShift(coordType)), coordType, MemoryUtil.memAddress(coords));
   }

   public static native void nglPathSubCommandsNV(int var0, int var1, int var2, int var3, long var4, int var6, int var7, long var8);

   public static void glPathSubCommandsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLsizei") int commandStart,
      @NativeType("GLsizei") int commandsToDelete,
      @NativeType("GLubyte const *") ByteBuffer commands,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") ByteBuffer coords
   ) {
      nglPathSubCommandsNV(
         path,
         commandStart,
         commandsToDelete,
         commands.remaining(),
         MemoryUtil.memAddress(commands),
         coords.remaining() >> GLChecks.typeToByteShift(coordType),
         coordType,
         MemoryUtil.memAddress(coords)
      );
   }

   public static void glPathSubCommandsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLsizei") int commandStart,
      @NativeType("GLsizei") int commandsToDelete,
      @NativeType("GLubyte const *") ByteBuffer commands,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") ShortBuffer coords
   ) {
      nglPathSubCommandsNV(
         path,
         commandStart,
         commandsToDelete,
         commands.remaining(),
         MemoryUtil.memAddress(commands),
         (int)((long)coords.remaining() << 1 >> GLChecks.typeToByteShift(coordType)),
         coordType,
         MemoryUtil.memAddress(coords)
      );
   }

   public static void glPathSubCommandsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLsizei") int commandStart,
      @NativeType("GLsizei") int commandsToDelete,
      @NativeType("GLubyte const *") ByteBuffer commands,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") FloatBuffer coords
   ) {
      nglPathSubCommandsNV(
         path,
         commandStart,
         commandsToDelete,
         commands.remaining(),
         MemoryUtil.memAddress(commands),
         (int)((long)coords.remaining() << 2 >> GLChecks.typeToByteShift(coordType)),
         coordType,
         MemoryUtil.memAddress(coords)
      );
   }

   public static native void nglPathSubCoordsNV(int var0, int var1, int var2, int var3, long var4);

   public static void glPathSubCoordsNV(
      @NativeType("GLuint") int path, @NativeType("GLsizei") int coordStart, @NativeType("GLenum") int coordType, @NativeType("void const *") ByteBuffer coords
   ) {
      nglPathSubCoordsNV(path, coordStart, coords.remaining() >> GLChecks.typeToByteShift(coordType), coordType, MemoryUtil.memAddress(coords));
   }

   public static void glPathSubCoordsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLsizei") int coordStart,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") ShortBuffer coords
   ) {
      nglPathSubCoordsNV(
         path, coordStart, (int)((long)coords.remaining() << 1 >> GLChecks.typeToByteShift(coordType)), coordType, MemoryUtil.memAddress(coords)
      );
   }

   public static void glPathSubCoordsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLsizei") int coordStart,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") FloatBuffer coords
   ) {
      nglPathSubCoordsNV(
         path, coordStart, (int)((long)coords.remaining() << 2 >> GLChecks.typeToByteShift(coordType)), coordType, MemoryUtil.memAddress(coords)
      );
   }

   public static native void nglPathStringNV(int var0, int var1, int var2, long var3);

   public static void glPathStringNV(@NativeType("GLuint") int path, @NativeType("GLenum") int format, @NativeType("void const *") ByteBuffer pathString) {
      nglPathStringNV(path, format, pathString.remaining(), MemoryUtil.memAddress(pathString));
   }

   public static native void nglPathGlyphsNV(int var0, int var1, long var2, int var4, int var5, int var6, long var7, int var9, int var10, float var11);

   public static void glPathGlyphsNV(
      @NativeType("GLuint") int firstPathName,
      @NativeType("GLenum") int fontTarget,
      @NativeType("void const *") ByteBuffer fontName,
      @NativeType("GLbitfield") int fontStyle,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer charcodes,
      @NativeType("GLenum") int handleMissingGlyphs,
      @NativeType("GLuint") int pathParameterTemplate,
      @NativeType("GLfloat") float emScale
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(fontName);
      }

      nglPathGlyphsNV(
         firstPathName,
         fontTarget,
         MemoryUtil.memAddress(fontName),
         fontStyle,
         charcodes.remaining() / charcodeTypeToBytes(type),
         type,
         MemoryUtil.memAddress(charcodes),
         handleMissingGlyphs,
         pathParameterTemplate,
         emScale
      );
   }

   public static native void nglPathGlyphRangeNV(int var0, int var1, long var2, int var4, int var5, int var6, int var7, int var8, float var9);

   public static void glPathGlyphRangeNV(
      @NativeType("GLuint") int firstPathName,
      @NativeType("GLenum") int fontTarget,
      @NativeType("void const *") ByteBuffer fontName,
      @NativeType("GLbitfield") int fontStyle,
      @NativeType("GLuint") int firstGlyph,
      @NativeType("GLsizei") int numGlyphs,
      @NativeType("GLenum") int handleMissingGlyphs,
      @NativeType("GLuint") int pathParameterTemplate,
      @NativeType("GLfloat") float emScale
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(fontName);
      }

      nglPathGlyphRangeNV(
         firstPathName, fontTarget, MemoryUtil.memAddress(fontName), fontStyle, firstGlyph, numGlyphs, handleMissingGlyphs, pathParameterTemplate, emScale
      );
   }

   public static native int nglPathGlyphIndexArrayNV(int var0, int var1, long var2, int var4, int var5, int var6, int var7, float var8);

   @NativeType("GLenum")
   public static int glPathGlyphIndexArrayNV(
      @NativeType("GLuint") int firstPathName,
      @NativeType("GLenum") int fontTarget,
      @NativeType("void const *") ByteBuffer fontName,
      @NativeType("GLbitfield") int fontStyle,
      @NativeType("GLuint") int firstGlyphIndex,
      @NativeType("GLsizei") int numGlyphs,
      @NativeType("GLuint") int pathParameterTemplate,
      @NativeType("GLfloat") float emScale
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(fontName);
      }

      return nglPathGlyphIndexArrayNV(
         firstPathName, fontTarget, MemoryUtil.memAddress(fontName), fontStyle, firstGlyphIndex, numGlyphs, pathParameterTemplate, emScale
      );
   }

   public static native int nglPathMemoryGlyphIndexArrayNV(int var0, int var1, long var2, long var4, int var6, int var7, int var8, int var9, float var10);

   @NativeType("GLenum")
   public static int glPathMemoryGlyphIndexArrayNV(
      @NativeType("GLuint") int firstPathName,
      @NativeType("GLenum") int fontTarget,
      @NativeType("void const *") ByteBuffer fontData,
      @NativeType("GLsizei") int faceIndex,
      @NativeType("GLuint") int firstGlyphIndex,
      @NativeType("GLsizei") int numGlyphs,
      @NativeType("GLuint") int pathParameterTemplate,
      @NativeType("GLfloat") float emScale
   ) {
      return nglPathMemoryGlyphIndexArrayNV(
         firstPathName,
         fontTarget,
         fontData.remaining(),
         MemoryUtil.memAddress(fontData),
         faceIndex,
         firstGlyphIndex,
         numGlyphs,
         pathParameterTemplate,
         emScale
      );
   }

   public static native void glCopyPathNV(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void nglWeightPathsNV(int var0, int var1, long var2, long var4);

   public static void glWeightPathsNV(
      @NativeType("GLuint") int resultPath, @NativeType("GLuint const *") IntBuffer paths, @NativeType("GLfloat const *") FloatBuffer weights
   ) {
      if (Checks.CHECKS) {
         Checks.check(weights, paths.remaining());
      }

      nglWeightPathsNV(resultPath, paths.remaining(), MemoryUtil.memAddress(paths), MemoryUtil.memAddress(weights));
   }

   public static native void glInterpolatePathsNV(
      @NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2, @NativeType("GLfloat") float var3
   );

   public static native void nglTransformPathNV(int var0, int var1, int var2, long var3);

   public static void glTransformPathNV(
      @NativeType("GLuint") int resultPath,
      @NativeType("GLuint") int srcPath,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") FloatBuffer transformValues
   ) {
      if (Checks.CHECKS) {
         Checks.check(transformValues, transformTypeToElements(transformType));
      }

      nglTransformPathNV(resultPath, srcPath, transformType, MemoryUtil.memAddress(transformValues));
   }

   public static native void nglPathParameterivNV(int var0, int var1, long var2);

   public static void glPathParameterivNV(@NativeType("GLuint") int path, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglPathParameterivNV(path, pname, MemoryUtil.memAddress(value));
   }

   public static native void glPathParameteriNV(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLint") int var2);

   public static native void nglPathParameterfvNV(int var0, int var1, long var2);

   public static void glPathParameterfvNV(@NativeType("GLuint") int path, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglPathParameterfvNV(path, pname, MemoryUtil.memAddress(value));
   }

   public static native void glPathParameterfNV(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLfloat") float var2);

   public static native void nglPathDashArrayNV(int var0, int var1, long var2);

   public static void glPathDashArrayNV(@NativeType("GLuint") int path, @NativeType("GLfloat const *") FloatBuffer dashArray) {
      nglPathDashArrayNV(path, dashArray.remaining(), MemoryUtil.memAddress(dashArray));
   }

   @NativeType("GLuint")
   public static native int glGenPathsNV(@NativeType("GLsizei") int var0);

   public static native void glDeletePathsNV(@NativeType("GLuint") int var0, @NativeType("GLsizei") int var1);

   @NativeType("GLboolean")
   public static native boolean glIsPathNV(@NativeType("GLuint") int var0);

   public static native void glPathStencilFuncNV(@NativeType("GLenum") int var0, @NativeType("GLint") int var1, @NativeType("GLuint") int var2);

   public static native void glPathStencilDepthOffsetNV(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1);

   public static native void glStencilFillPathNV(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLuint") int var2);

   public static native void glStencilStrokePathNV(@NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLuint") int var2);

   public static native void nglStencilFillPathInstancedNV(int var0, int var1, long var2, int var4, int var5, int var6, int var7, long var8);

   public static void glStencilFillPathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLenum") int fillMode,
      @NativeType("GLuint") int mask,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") FloatBuffer transformValues
   ) {
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      nglStencilFillPathInstancedNV(
         numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, fillMode, mask, transformType, MemoryUtil.memAddress(transformValues)
      );
   }

   public static native void nglStencilStrokePathInstancedNV(int var0, int var1, long var2, int var4, int var5, int var6, int var7, long var8);

   public static void glStencilStrokePathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLint") int reference,
      @NativeType("GLuint") int mask,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") FloatBuffer transformValues
   ) {
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      nglStencilStrokePathInstancedNV(
         numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, reference, mask, transformType, MemoryUtil.memAddress(transformValues)
      );
   }

   public static native void glPathCoverDepthFuncNV(@NativeType("GLenum") int var0);

   public static native void nglPathColorGenNV(int var0, int var1, int var2, long var3);

   public static void glPathColorGenNV(
      @NativeType("GLenum") int color,
      @NativeType("GLenum") int genMode,
      @NativeType("GLenum") int colorFormat,
      @NativeType("GLfloat const *") FloatBuffer coeffs
   ) {
      if (Checks.CHECKS) {
         Checks.check(coeffs, genModeToElements(genMode) * colorFormatToComponents(colorFormat));
      }

      nglPathColorGenNV(color, genMode, colorFormat, MemoryUtil.memAddress(coeffs));
   }

   public static native void nglPathTexGenNV(int var0, int var1, int var2, long var3);

   public static void glPathTexGenNV(
      @NativeType("GLenum") int texCoordSet,
      @NativeType("GLenum") int genMode,
      @NativeType("GLint") int components,
      @NativeType("GLfloat const *") FloatBuffer coeffs
   ) {
      if (Checks.CHECKS) {
         Checks.check(coeffs, genModeToElements(genMode) * components);
      }

      nglPathTexGenNV(texCoordSet, genMode, components, MemoryUtil.memAddress(coeffs));
   }

   public static native void glPathFogGenNV(@NativeType("GLenum") int var0);

   public static native void glCoverFillPathNV(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static native void glCoverStrokePathNV(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static native void nglCoverFillPathInstancedNV(int var0, int var1, long var2, int var4, int var5, int var6, long var7);

   public static void glCoverFillPathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLenum") int coverMode,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") FloatBuffer transformValues
   ) {
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      nglCoverFillPathInstancedNV(
         numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, coverMode, transformType, MemoryUtil.memAddress(transformValues)
      );
   }

   public static native void nglCoverStrokePathInstancedNV(int var0, int var1, long var2, int var4, int var5, int var6, long var7);

   public static void glCoverStrokePathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLenum") int coverMode,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") FloatBuffer transformValues
   ) {
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      nglCoverStrokePathInstancedNV(
         numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, coverMode, transformType, MemoryUtil.memAddress(transformValues)
      );
   }

   public static native void glStencilThenCoverFillPathNV(
      @NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLuint") int var2, @NativeType("GLenum") int var3
   );

   public static native void glStencilThenCoverStrokePathNV(
      @NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLuint") int var2, @NativeType("GLenum") int var3
   );

   public static native void nglStencilThenCoverFillPathInstancedNV(int var0, int var1, long var2, int var4, int var5, int var6, int var7, int var8, long var9);

   public static void glStencilThenCoverFillPathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLenum") int fillMode,
      @NativeType("GLuint") int mask,
      @NativeType("GLenum") int coverMode,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") FloatBuffer transformValues
   ) {
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      nglStencilThenCoverFillPathInstancedNV(
         numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, fillMode, mask, coverMode, transformType, MemoryUtil.memAddress(transformValues)
      );
   }

   public static native void nglStencilThenCoverStrokePathInstancedNV(
      int var0, int var1, long var2, int var4, int var5, int var6, int var7, int var8, long var9
   );

   public static void glStencilThenCoverStrokePathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLint") int reference,
      @NativeType("GLuint") int mask,
      @NativeType("GLenum") int coverMode,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") FloatBuffer transformValues
   ) {
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      nglStencilThenCoverStrokePathInstancedNV(
         numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, reference, mask, coverMode, transformType, MemoryUtil.memAddress(transformValues)
      );
   }

   public static native int nglPathGlyphIndexRangeNV(int var0, long var1, int var3, int var4, float var5, long var6);

   @NativeType("GLenum")
   public static int glPathGlyphIndexRangeNV(
      @NativeType("GLenum") int fontTarget,
      @NativeType("void const *") ByteBuffer fontName,
      @NativeType("GLbitfield") int fontStyle,
      @NativeType("GLuint") int pathParameterTemplate,
      @NativeType("GLfloat") float emScale,
      @NativeType("GLuint *") IntBuffer baseAndCount
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(fontName);
         Checks.check(baseAndCount, 2);
      }

      return nglPathGlyphIndexRangeNV(
         fontTarget, MemoryUtil.memAddress(fontName), fontStyle, pathParameterTemplate, emScale, MemoryUtil.memAddress(baseAndCount)
      );
   }

   public static native void nglProgramPathFragmentInputGenNV(int var0, int var1, int var2, int var3, long var4);

   public static void glProgramPathFragmentInputGenNV(
      @NativeType("GLuint") int program,
      @NativeType("GLint") int location,
      @NativeType("GLenum") int genMode,
      @NativeType("GLint") int components,
      @NativeType("GLfloat const *") FloatBuffer coeffs
   ) {
      if (Checks.CHECKS) {
         Checks.check(coeffs, genModeToElements(genMode) * components);
      }

      nglProgramPathFragmentInputGenNV(program, location, genMode, components, MemoryUtil.memAddress(coeffs));
   }

   public static native void nglGetPathParameterivNV(int var0, int var1, long var2);

   public static void glGetPathParameterivNV(@NativeType("GLuint") int path, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglGetPathParameterivNV(path, pname, MemoryUtil.memAddress(value));
   }

   @NativeType("void")
   public static int glGetPathParameteriNV(@NativeType("GLuint") int path, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer value = stack.callocInt(1);
         nglGetPathParameterivNV(path, pname, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetPathParameterfvNV(int var0, int var1, long var2);

   public static void glGetPathParameterfvNV(@NativeType("GLuint") int path, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglGetPathParameterfvNV(path, pname, MemoryUtil.memAddress(value));
   }

   @NativeType("void")
   public static float glGetPathParameterfNV(@NativeType("GLuint") int path, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer value = stack.callocFloat(1);
         nglGetPathParameterfvNV(path, pname, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetPathCommandsNV(int var0, long var1);

   public static void glGetPathCommandsNV(@NativeType("GLuint") int path, @NativeType("GLubyte *") ByteBuffer commands) {
      if (Checks.CHECKS && Checks.DEBUG) {
         Checks.check(commands, glGetPathParameteriNV(path, 37021));
      }

      nglGetPathCommandsNV(path, MemoryUtil.memAddress(commands));
   }

   public static native void nglGetPathCoordsNV(int var0, long var1);

   public static void glGetPathCoordsNV(@NativeType("GLuint") int path, @NativeType("GLfloat *") FloatBuffer coords) {
      if (Checks.CHECKS && Checks.DEBUG) {
         Checks.check(coords, glGetPathParameteriNV(path, 37022));
      }

      nglGetPathCoordsNV(path, MemoryUtil.memAddress(coords));
   }

   public static native void nglGetPathDashArrayNV(int var0, long var1);

   public static void glGetPathDashArrayNV(@NativeType("GLuint") int path, @NativeType("GLfloat *") FloatBuffer dashArray) {
      if (Checks.CHECKS && Checks.DEBUG) {
         Checks.check(dashArray, glGetPathParameteriNV(path, 37023));
      }

      nglGetPathDashArrayNV(path, MemoryUtil.memAddress(dashArray));
   }

   public static native void nglGetPathMetricsNV(int var0, int var1, int var2, long var3, int var5, int var6, long var7);

   public static void glGetPathMetricsNV(
      @NativeType("GLbitfield") int metricQueryMask,
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLsizei") int stride,
      @NativeType("GLfloat *") FloatBuffer metrics
   ) {
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(metrics, numPaths * (stride == 0 ? Integer.bitCount(metricQueryMask) : stride >> 2));
      }

      nglGetPathMetricsNV(metricQueryMask, numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, stride, MemoryUtil.memAddress(metrics));
   }

   public static native void nglGetPathMetricRangeNV(int var0, int var1, int var2, int var3, long var4);

   public static void glGetPathMetricRangeNV(
      @NativeType("GLbitfield") int metricQueryMask,
      @NativeType("GLuint") int firstPathName,
      @NativeType("GLsizei") int numPaths,
      @NativeType("GLsizei") int stride,
      @NativeType("GLfloat *") FloatBuffer metrics
   ) {
      if (Checks.CHECKS) {
         Checks.check(metrics, numPaths * (stride == 0 ? Integer.bitCount(metricQueryMask) : stride >> 2));
      }

      nglGetPathMetricRangeNV(metricQueryMask, firstPathName, numPaths, stride, MemoryUtil.memAddress(metrics));
   }

   public static native void nglGetPathSpacingNV(int var0, int var1, int var2, long var3, int var5, float var6, float var7, int var8, long var9);

   public static void glGetPathSpacingNV(
      @NativeType("GLenum") int pathListMode,
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLfloat") float advanceScale,
      @NativeType("GLfloat") float kerningScale,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat *") FloatBuffer returnedSpacing
   ) {
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(returnedSpacing, (numPaths - 1) * (transformType == 37006 ? 1 : 2));
      }

      nglGetPathSpacingNV(
         pathListMode,
         numPaths,
         pathNameType,
         MemoryUtil.memAddress(paths),
         pathBase,
         advanceScale,
         kerningScale,
         transformType,
         MemoryUtil.memAddress(returnedSpacing)
      );
   }

   public static native void nglGetPathColorGenivNV(int var0, int var1, long var2);

   public static void glGetPathColorGenivNV(@NativeType("GLenum") int color, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglGetPathColorGenivNV(color, pname, MemoryUtil.memAddress(value));
   }

   @NativeType("void")
   public static int glGetPathColorGeniNV(@NativeType("GLenum") int color, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer value = stack.callocInt(1);
         nglGetPathColorGenivNV(color, pname, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetPathColorGenfvNV(int var0, int var1, long var2);

   public static void glGetPathColorGenfvNV(@NativeType("GLenum") int color, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglGetPathColorGenfvNV(color, pname, MemoryUtil.memAddress(value));
   }

   @NativeType("void")
   public static float glGetPathColorGenfNV(@NativeType("GLenum") int color, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer value = stack.callocFloat(1);
         nglGetPathColorGenfvNV(color, pname, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetPathTexGenivNV(int var0, int var1, long var2);

   public static void glGetPathTexGenivNV(@NativeType("GLenum") int texCoordSet, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglGetPathTexGenivNV(texCoordSet, pname, MemoryUtil.memAddress(value));
   }

   @NativeType("void")
   public static int glGetPathTexGeniNV(@NativeType("GLenum") int texCoordSet, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer value = stack.callocInt(1);
         nglGetPathTexGenivNV(texCoordSet, pname, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetPathTexGenfvNV(int var0, int var1, long var2);

   public static void glGetPathTexGenfvNV(@NativeType("GLenum") int texCoordSet, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglGetPathTexGenfvNV(texCoordSet, pname, MemoryUtil.memAddress(value));
   }

   @NativeType("void")
   public static float glGetPathTexGenfNV(@NativeType("GLenum") int texCoordSet, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer value = stack.callocFloat(1);
         nglGetPathTexGenfvNV(texCoordSet, pname, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsPointInFillPathNV(
      @NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   @NativeType("GLboolean")
   public static native boolean glIsPointInStrokePathNV(@NativeType("GLuint") int var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   @NativeType("GLfloat")
   public static native float glGetPathLengthNV(@NativeType("GLuint") int var0, @NativeType("GLsizei") int var1, @NativeType("GLsizei") int var2);

   public static native boolean nglPointAlongPathNV(int var0, int var1, int var2, float var3, long var4, long var6, long var8, long var10);

   @NativeType("GLboolean")
   public static boolean glPointAlongPathNV(
      @NativeType("GLuint") int path,
      @NativeType("GLsizei") int startSegment,
      @NativeType("GLsizei") int numSegments,
      @NativeType("GLfloat") float distance,
      @Nullable @NativeType("GLfloat *") FloatBuffer x,
      @Nullable @NativeType("GLfloat *") FloatBuffer y,
      @Nullable @NativeType("GLfloat *") FloatBuffer tangentX,
      @Nullable @NativeType("GLfloat *") FloatBuffer tangentY
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(x, 1);
         Checks.checkSafe(y, 1);
         Checks.checkSafe(tangentX, 1);
         Checks.checkSafe(tangentY, 1);
      }

      return nglPointAlongPathNV(
         path,
         startSegment,
         numSegments,
         distance,
         MemoryUtil.memAddressSafe(x),
         MemoryUtil.memAddressSafe(y),
         MemoryUtil.memAddressSafe(tangentX),
         MemoryUtil.memAddressSafe(tangentY)
      );
   }

   public static native void nglMatrixLoad3x2fNV(int var0, long var1);

   public static void glMatrixLoad3x2fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") FloatBuffer m) {
      if (Checks.CHECKS) {
         Checks.check(m, 6);
      }

      nglMatrixLoad3x2fNV(matrixMode, MemoryUtil.memAddress(m));
   }

   public static native void nglMatrixLoad3x3fNV(int var0, long var1);

   public static void glMatrixLoad3x3fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") FloatBuffer m) {
      if (Checks.CHECKS) {
         Checks.check(m, 9);
      }

      nglMatrixLoad3x3fNV(matrixMode, MemoryUtil.memAddress(m));
   }

   public static native void nglMatrixLoadTranspose3x3fNV(int var0, long var1);

   public static void glMatrixLoadTranspose3x3fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") FloatBuffer m) {
      if (Checks.CHECKS) {
         Checks.check(m, 9);
      }

      nglMatrixLoadTranspose3x3fNV(matrixMode, MemoryUtil.memAddress(m));
   }

   public static native void nglMatrixMult3x2fNV(int var0, long var1);

   public static void glMatrixMult3x2fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") FloatBuffer m) {
      if (Checks.CHECKS) {
         Checks.check(m, 6);
      }

      nglMatrixMult3x2fNV(matrixMode, MemoryUtil.memAddress(m));
   }

   public static native void nglMatrixMult3x3fNV(int var0, long var1);

   public static void glMatrixMult3x3fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") FloatBuffer m) {
      if (Checks.CHECKS) {
         Checks.check(m, 9);
      }

      nglMatrixMult3x3fNV(matrixMode, MemoryUtil.memAddress(m));
   }

   public static native void nglMatrixMultTranspose3x3fNV(int var0, long var1);

   public static void glMatrixMultTranspose3x3fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") FloatBuffer m) {
      if (Checks.CHECKS) {
         Checks.check(m, 9);
      }

      nglMatrixMultTranspose3x3fNV(matrixMode, MemoryUtil.memAddress(m));
   }

   public static native void nglGetProgramResourcefvNV(int var0, int var1, int var2, int var3, long var4, int var6, long var7, long var9);

   public static void glGetProgramResourcefvNV(
      @NativeType("GLuint") int program,
      @NativeType("GLenum") int programInterface,
      @NativeType("GLuint") int index,
      @NativeType("GLenum const *") IntBuffer props,
      @Nullable @NativeType("GLsizei *") IntBuffer length,
      @NativeType("GLfloat *") FloatBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(length, 1);
      }

      nglGetProgramResourcefvNV(
         program,
         programInterface,
         index,
         props.remaining(),
         MemoryUtil.memAddress(props),
         params.remaining(),
         MemoryUtil.memAddressSafe(length),
         MemoryUtil.memAddress(params)
      );
   }

   public static void glPathCommandsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLubyte const *") ByteBuffer commands,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") short[] coords
   ) {
      long __functionAddress = GL.getICD().glPathCommandsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(path, commands.remaining(), MemoryUtil.memAddress(commands), coords.length, coordType, coords, __functionAddress);
   }

   public static void glPathCommandsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLubyte const *") ByteBuffer commands,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") float[] coords
   ) {
      long __functionAddress = GL.getICD().glPathCommandsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(path, commands.remaining(), MemoryUtil.memAddress(commands), coords.length, coordType, coords, __functionAddress);
   }

   public static void glPathCoordsNV(@NativeType("GLuint") int path, @NativeType("GLenum") int coordType, @NativeType("void const *") short[] coords) {
      long __functionAddress = GL.getICD().glPathCoordsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(path, coords.length, coordType, coords, __functionAddress);
   }

   public static void glPathCoordsNV(@NativeType("GLuint") int path, @NativeType("GLenum") int coordType, @NativeType("void const *") float[] coords) {
      long __functionAddress = GL.getICD().glPathCoordsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(path, coords.length, coordType, coords, __functionAddress);
   }

   public static void glPathSubCommandsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLsizei") int commandStart,
      @NativeType("GLsizei") int commandsToDelete,
      @NativeType("GLubyte const *") ByteBuffer commands,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") short[] coords
   ) {
      long __functionAddress = GL.getICD().glPathSubCommandsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(
         path, commandStart, commandsToDelete, commands.remaining(), MemoryUtil.memAddress(commands), coords.length, coordType, coords, __functionAddress
      );
   }

   public static void glPathSubCommandsNV(
      @NativeType("GLuint") int path,
      @NativeType("GLsizei") int commandStart,
      @NativeType("GLsizei") int commandsToDelete,
      @NativeType("GLubyte const *") ByteBuffer commands,
      @NativeType("GLenum") int coordType,
      @NativeType("void const *") float[] coords
   ) {
      long __functionAddress = GL.getICD().glPathSubCommandsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(
         path, commandStart, commandsToDelete, commands.remaining(), MemoryUtil.memAddress(commands), coords.length, coordType, coords, __functionAddress
      );
   }

   public static void glPathSubCoordsNV(
      @NativeType("GLuint") int path, @NativeType("GLsizei") int coordStart, @NativeType("GLenum") int coordType, @NativeType("void const *") short[] coords
   ) {
      long __functionAddress = GL.getICD().glPathSubCoordsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(path, coordStart, coords.length, coordType, coords, __functionAddress);
   }

   public static void glPathSubCoordsNV(
      @NativeType("GLuint") int path, @NativeType("GLsizei") int coordStart, @NativeType("GLenum") int coordType, @NativeType("void const *") float[] coords
   ) {
      long __functionAddress = GL.getICD().glPathSubCoordsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(path, coordStart, coords.length, coordType, coords, __functionAddress);
   }

   public static void glWeightPathsNV(
      @NativeType("GLuint") int resultPath, @NativeType("GLuint const *") int[] paths, @NativeType("GLfloat const *") float[] weights
   ) {
      long __functionAddress = GL.getICD().glWeightPathsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(weights, paths.length);
      }

      JNI.callPPV(resultPath, paths.length, paths, weights, __functionAddress);
   }

   public static void glTransformPathNV(
      @NativeType("GLuint") int resultPath,
      @NativeType("GLuint") int srcPath,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") float[] transformValues
   ) {
      long __functionAddress = GL.getICD().glTransformPathNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(transformValues, transformTypeToElements(transformType));
      }

      JNI.callPV(resultPath, srcPath, transformType, transformValues, __functionAddress);
   }

   public static void glPathParameterivNV(@NativeType("GLuint") int path, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] value) {
      long __functionAddress = GL.getICD().glPathParameterivNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(path, pname, value, __functionAddress);
   }

   public static void glPathParameterfvNV(@NativeType("GLuint") int path, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] value) {
      long __functionAddress = GL.getICD().glPathParameterfvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(path, pname, value, __functionAddress);
   }

   public static void glPathDashArrayNV(@NativeType("GLuint") int path, @NativeType("GLfloat const *") float[] dashArray) {
      long __functionAddress = GL.getICD().glPathDashArrayNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(path, dashArray.length, dashArray, __functionAddress);
   }

   public static void glStencilFillPathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLenum") int fillMode,
      @NativeType("GLuint") int mask,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") float[] transformValues
   ) {
      long __functionAddress = GL.getICD().glStencilFillPathInstancedNV;
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      JNI.callPPV(numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, fillMode, mask, transformType, transformValues, __functionAddress);
   }

   public static void glStencilStrokePathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLint") int reference,
      @NativeType("GLuint") int mask,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") float[] transformValues
   ) {
      long __functionAddress = GL.getICD().glStencilStrokePathInstancedNV;
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      JNI.callPPV(numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, reference, mask, transformType, transformValues, __functionAddress);
   }

   public static void glPathColorGenNV(
      @NativeType("GLenum") int color, @NativeType("GLenum") int genMode, @NativeType("GLenum") int colorFormat, @NativeType("GLfloat const *") float[] coeffs
   ) {
      long __functionAddress = GL.getICD().glPathColorGenNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coeffs, genModeToElements(genMode) * colorFormatToComponents(colorFormat));
      }

      JNI.callPV(color, genMode, colorFormat, coeffs, __functionAddress);
   }

   public static void glPathTexGenNV(
      @NativeType("GLenum") int texCoordSet,
      @NativeType("GLenum") int genMode,
      @NativeType("GLint") int components,
      @NativeType("GLfloat const *") float[] coeffs
   ) {
      long __functionAddress = GL.getICD().glPathTexGenNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coeffs, genModeToElements(genMode) * components);
      }

      JNI.callPV(texCoordSet, genMode, components, coeffs, __functionAddress);
   }

   public static void glCoverFillPathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLenum") int coverMode,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") float[] transformValues
   ) {
      long __functionAddress = GL.getICD().glCoverFillPathInstancedNV;
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      JNI.callPPV(numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, coverMode, transformType, transformValues, __functionAddress);
   }

   public static void glCoverStrokePathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLenum") int coverMode,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") float[] transformValues
   ) {
      long __functionAddress = GL.getICD().glCoverStrokePathInstancedNV;
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      JNI.callPPV(numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, coverMode, transformType, transformValues, __functionAddress);
   }

   public static void glStencilThenCoverFillPathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLenum") int fillMode,
      @NativeType("GLuint") int mask,
      @NativeType("GLenum") int coverMode,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") float[] transformValues
   ) {
      long __functionAddress = GL.getICD().glStencilThenCoverFillPathInstancedNV;
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      JNI.callPPV(numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, fillMode, mask, coverMode, transformType, transformValues, __functionAddress);
   }

   public static void glStencilThenCoverStrokePathInstancedNV(
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLint") int reference,
      @NativeType("GLuint") int mask,
      @NativeType("GLenum") int coverMode,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat const *") float[] transformValues
   ) {
      long __functionAddress = GL.getICD().glStencilThenCoverStrokePathInstancedNV;
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(transformValues, numPaths * transformTypeToElements(transformType));
      }

      JNI.callPPV(numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, reference, mask, coverMode, transformType, transformValues, __functionAddress);
   }

   @NativeType("GLenum")
   public static int glPathGlyphIndexRangeNV(
      @NativeType("GLenum") int fontTarget,
      @NativeType("void const *") ByteBuffer fontName,
      @NativeType("GLbitfield") int fontStyle,
      @NativeType("GLuint") int pathParameterTemplate,
      @NativeType("GLfloat") float emScale,
      @NativeType("GLuint *") int[] baseAndCount
   ) {
      long __functionAddress = GL.getICD().glPathGlyphIndexRangeNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkNT1(fontName);
         Checks.check(baseAndCount, 2);
      }

      return JNI.callPPI(fontTarget, MemoryUtil.memAddress(fontName), fontStyle, pathParameterTemplate, emScale, baseAndCount, __functionAddress);
   }

   public static void glProgramPathFragmentInputGenNV(
      @NativeType("GLuint") int program,
      @NativeType("GLint") int location,
      @NativeType("GLenum") int genMode,
      @NativeType("GLint") int components,
      @NativeType("GLfloat const *") float[] coeffs
   ) {
      long __functionAddress = GL.getICD().glProgramPathFragmentInputGenNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coeffs, genModeToElements(genMode) * components);
      }

      JNI.callPV(program, location, genMode, components, coeffs, __functionAddress);
   }

   public static void glGetPathParameterivNV(@NativeType("GLuint") int path, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] value) {
      long __functionAddress = GL.getICD().glGetPathParameterivNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(path, pname, value, __functionAddress);
   }

   public static void glGetPathParameterfvNV(@NativeType("GLuint") int path, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] value) {
      long __functionAddress = GL.getICD().glGetPathParameterfvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(path, pname, value, __functionAddress);
   }

   public static void glGetPathCoordsNV(@NativeType("GLuint") int path, @NativeType("GLfloat *") float[] coords) {
      long __functionAddress = GL.getICD().glGetPathCoordsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         if (Checks.DEBUG) {
            Checks.check(coords, glGetPathParameteriNV(path, 37022));
         }
      }

      JNI.callPV(path, coords, __functionAddress);
   }

   public static void glGetPathDashArrayNV(@NativeType("GLuint") int path, @NativeType("GLfloat *") float[] dashArray) {
      long __functionAddress = GL.getICD().glGetPathDashArrayNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         if (Checks.DEBUG) {
            Checks.check(dashArray, glGetPathParameteriNV(path, 37023));
         }
      }

      JNI.callPV(path, dashArray, __functionAddress);
   }

   public static void glGetPathMetricsNV(
      @NativeType("GLbitfield") int metricQueryMask,
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLsizei") int stride,
      @NativeType("GLfloat *") float[] metrics
   ) {
      long __functionAddress = GL.getICD().glGetPathMetricsNV;
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(metrics, numPaths * (stride == 0 ? Integer.bitCount(metricQueryMask) : stride >> 2));
      }

      JNI.callPPV(metricQueryMask, numPaths, pathNameType, MemoryUtil.memAddress(paths), pathBase, stride, metrics, __functionAddress);
   }

   public static void glGetPathMetricRangeNV(
      @NativeType("GLbitfield") int metricQueryMask,
      @NativeType("GLuint") int firstPathName,
      @NativeType("GLsizei") int numPaths,
      @NativeType("GLsizei") int stride,
      @NativeType("GLfloat *") float[] metrics
   ) {
      long __functionAddress = GL.getICD().glGetPathMetricRangeNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(metrics, numPaths * (stride == 0 ? Integer.bitCount(metricQueryMask) : stride >> 2));
      }

      JNI.callPV(metricQueryMask, firstPathName, numPaths, stride, metrics, __functionAddress);
   }

   public static void glGetPathSpacingNV(
      @NativeType("GLenum") int pathListMode,
      @NativeType("GLenum") int pathNameType,
      @NativeType("void const *") ByteBuffer paths,
      @NativeType("GLuint") int pathBase,
      @NativeType("GLfloat") float advanceScale,
      @NativeType("GLfloat") float kerningScale,
      @NativeType("GLenum") int transformType,
      @NativeType("GLfloat *") float[] returnedSpacing
   ) {
      long __functionAddress = GL.getICD().glGetPathSpacingNV;
      int numPaths = paths.remaining() / pathNameTypeToBytes(pathNameType);
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(returnedSpacing, (numPaths - 1) * (transformType == 37006 ? 1 : 2));
      }

      JNI.callPPV(
         pathListMode,
         numPaths,
         pathNameType,
         MemoryUtil.memAddress(paths),
         pathBase,
         advanceScale,
         kerningScale,
         transformType,
         returnedSpacing,
         __functionAddress
      );
   }

   public static void glGetPathColorGenivNV(@NativeType("GLenum") int color, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] value) {
      long __functionAddress = GL.getICD().glGetPathColorGenivNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(color, pname, value, __functionAddress);
   }

   public static void glGetPathColorGenfvNV(@NativeType("GLenum") int color, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] value) {
      long __functionAddress = GL.getICD().glGetPathColorGenfvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(color, pname, value, __functionAddress);
   }

   public static void glGetPathTexGenivNV(@NativeType("GLenum") int texCoordSet, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] value) {
      long __functionAddress = GL.getICD().glGetPathTexGenivNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(texCoordSet, pname, value, __functionAddress);
   }

   public static void glGetPathTexGenfvNV(@NativeType("GLenum") int texCoordSet, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] value) {
      long __functionAddress = GL.getICD().glGetPathTexGenfvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(texCoordSet, pname, value, __functionAddress);
   }

   @NativeType("GLboolean")
   public static boolean glPointAlongPathNV(
      @NativeType("GLuint") int path,
      @NativeType("GLsizei") int startSegment,
      @NativeType("GLsizei") int numSegments,
      @NativeType("GLfloat") float distance,
      @Nullable @NativeType("GLfloat *") float[] x,
      @Nullable @NativeType("GLfloat *") float[] y,
      @Nullable @NativeType("GLfloat *") float[] tangentX,
      @Nullable @NativeType("GLfloat *") float[] tangentY
   ) {
      long __functionAddress = GL.getICD().glPointAlongPathNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(x, 1);
         Checks.checkSafe(y, 1);
         Checks.checkSafe(tangentX, 1);
         Checks.checkSafe(tangentY, 1);
      }

      return JNI.callPPPPZ(path, startSegment, numSegments, distance, x, y, tangentX, tangentY, __functionAddress);
   }

   public static void glMatrixLoad3x2fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") float[] m) {
      long __functionAddress = GL.getICD().glMatrixLoad3x2fNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(m, 6);
      }

      JNI.callPV(matrixMode, m, __functionAddress);
   }

   public static void glMatrixLoad3x3fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") float[] m) {
      long __functionAddress = GL.getICD().glMatrixLoad3x3fNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(m, 9);
      }

      JNI.callPV(matrixMode, m, __functionAddress);
   }

   public static void glMatrixLoadTranspose3x3fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") float[] m) {
      long __functionAddress = GL.getICD().glMatrixLoadTranspose3x3fNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(m, 9);
      }

      JNI.callPV(matrixMode, m, __functionAddress);
   }

   public static void glMatrixMult3x2fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") float[] m) {
      long __functionAddress = GL.getICD().glMatrixMult3x2fNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(m, 6);
      }

      JNI.callPV(matrixMode, m, __functionAddress);
   }

   public static void glMatrixMult3x3fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") float[] m) {
      long __functionAddress = GL.getICD().glMatrixMult3x3fNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(m, 9);
      }

      JNI.callPV(matrixMode, m, __functionAddress);
   }

   public static void glMatrixMultTranspose3x3fNV(@NativeType("GLenum") int matrixMode, @NativeType("GLfloat const *") float[] m) {
      long __functionAddress = GL.getICD().glMatrixMultTranspose3x3fNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(m, 9);
      }

      JNI.callPV(matrixMode, m, __functionAddress);
   }

   public static void glGetProgramResourcefvNV(
      @NativeType("GLuint") int program,
      @NativeType("GLenum") int programInterface,
      @NativeType("GLuint") int index,
      @NativeType("GLenum const *") int[] props,
      @Nullable @NativeType("GLsizei *") int[] length,
      @NativeType("GLfloat *") float[] params
   ) {
      long __functionAddress = GL.getICD().glGetProgramResourcefvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(length, 1);
      }

      JNI.callPPPV(program, programInterface, index, props.length, props, params.length, length, params, __functionAddress);
   }

   private static int charcodeTypeToBytes(int type) {
      switch (type) {
         case 5121:
         case 37018:
            return 1;
         case 5123:
         case 5127:
         case 37019:
            return 2;
         case 5125:
         case 5129:
            return 4;
         case 5128:
            return 3;
         default:
            throw new IllegalArgumentException(String.format("Unsupported charcode type: 0x%X", type));
      }
   }

   private static int pathNameTypeToBytes(int type) {
      switch (type) {
         case 5120:
         case 5121:
         case 37018:
            return 1;
         case 5122:
         case 5123:
         case 5127:
         case 37019:
            return 2;
         case 5124:
         case 5125:
         case 5129:
            return 4;
         case 5128:
            return 3;
         default:
            throw new IllegalArgumentException(String.format("Unsupported path name type: 0x%X", type));
      }
   }

   private static int transformTypeToElements(int type) {
      switch (type) {
         case 0:
            return 0;
         case 37006:
         case 37007:
            return 1;
         case 37008:
            return 2;
         case 37009:
            return 3;
         case 37010:
         case 37014:
            return 6;
         case 37012:
         case 37016:
            return 12;
         default:
            throw new IllegalArgumentException(String.format("Unsupported transform type: 0x%X", type));
      }
   }

   private static int colorFormatToComponents(int colorFormat) {
      switch (colorFormat) {
         case 6406:
         case 6409:
         case 32841:
            return 1;
         case 6407:
            return 3;
         case 6408:
            return 4;
         case 6410:
            return 2;
         default:
            throw new IllegalArgumentException(String.format("Unsupported colorFormat specified: 0x%X", colorFormat));
      }
   }

   private static int genModeToElements(int genMode) {
      switch (genMode) {
         case 0:
            return 0;
         case 9216:
            return 4;
         case 9217:
         case 37002:
            return 3;
         case 34166:
            return 1;
         default:
            throw new IllegalArgumentException(String.format("Unsupported genMode specified: 0x%X", genMode));
      }
   }

   static {
      GL.initialize();
   }
}
