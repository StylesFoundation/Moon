package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Set;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL11 {
   public static final int GL_ACCUM = 256;
   public static final int GL_LOAD = 257;
   public static final int GL_RETURN = 258;
   public static final int GL_MULT = 259;
   public static final int GL_ADD = 260;
   public static final int GL_NEVER = 512;
   public static final int GL_LESS = 513;
   public static final int GL_EQUAL = 514;
   public static final int GL_LEQUAL = 515;
   public static final int GL_GREATER = 516;
   public static final int GL_NOTEQUAL = 517;
   public static final int GL_GEQUAL = 518;
   public static final int GL_ALWAYS = 519;
   public static final int GL_CURRENT_BIT = 1;
   public static final int GL_POINT_BIT = 2;
   public static final int GL_LINE_BIT = 4;
   public static final int GL_POLYGON_BIT = 8;
   public static final int GL_POLYGON_STIPPLE_BIT = 16;
   public static final int GL_PIXEL_MODE_BIT = 32;
   public static final int GL_LIGHTING_BIT = 64;
   public static final int GL_FOG_BIT = 128;
   public static final int GL_DEPTH_BUFFER_BIT = 256;
   public static final int GL_ACCUM_BUFFER_BIT = 512;
   public static final int GL_STENCIL_BUFFER_BIT = 1024;
   public static final int GL_VIEWPORT_BIT = 2048;
   public static final int GL_TRANSFORM_BIT = 4096;
   public static final int GL_ENABLE_BIT = 8192;
   public static final int GL_COLOR_BUFFER_BIT = 16384;
   public static final int GL_HINT_BIT = 32768;
   public static final int GL_EVAL_BIT = 65536;
   public static final int GL_LIST_BIT = 131072;
   public static final int GL_TEXTURE_BIT = 262144;
   public static final int GL_SCISSOR_BIT = 524288;
   public static final int GL_ALL_ATTRIB_BITS = 1048575;
   public static final int GL_POINTS = 0;
   public static final int GL_LINES = 1;
   public static final int GL_LINE_LOOP = 2;
   public static final int GL_LINE_STRIP = 3;
   public static final int GL_TRIANGLES = 4;
   public static final int GL_TRIANGLE_STRIP = 5;
   public static final int GL_TRIANGLE_FAN = 6;
   public static final int GL_QUADS = 7;
   public static final int GL_QUAD_STRIP = 8;
   public static final int GL_POLYGON = 9;
   public static final int GL_ZERO = 0;
   public static final int GL_ONE = 1;
   public static final int GL_SRC_COLOR = 768;
   public static final int GL_ONE_MINUS_SRC_COLOR = 769;
   public static final int GL_SRC_ALPHA = 770;
   public static final int GL_ONE_MINUS_SRC_ALPHA = 771;
   public static final int GL_DST_ALPHA = 772;
   public static final int GL_ONE_MINUS_DST_ALPHA = 773;
   public static final int GL_DST_COLOR = 774;
   public static final int GL_ONE_MINUS_DST_COLOR = 775;
   public static final int GL_SRC_ALPHA_SATURATE = 776;
   public static final int GL_TRUE = 1;
   public static final int GL_FALSE = 0;
   public static final int GL_CLIP_PLANE0 = 12288;
   public static final int GL_CLIP_PLANE1 = 12289;
   public static final int GL_CLIP_PLANE2 = 12290;
   public static final int GL_CLIP_PLANE3 = 12291;
   public static final int GL_CLIP_PLANE4 = 12292;
   public static final int GL_CLIP_PLANE5 = 12293;
   public static final int GL_BYTE = 5120;
   public static final int GL_UNSIGNED_BYTE = 5121;
   public static final int GL_SHORT = 5122;
   public static final int GL_UNSIGNED_SHORT = 5123;
   public static final int GL_INT = 5124;
   public static final int GL_UNSIGNED_INT = 5125;
   public static final int GL_FLOAT = 5126;
   public static final int GL_2_BYTES = 5127;
   public static final int GL_3_BYTES = 5128;
   public static final int GL_4_BYTES = 5129;
   public static final int GL_DOUBLE = 5130;
   public static final int GL_NONE = 0;
   public static final int GL_FRONT_LEFT = 1024;
   public static final int GL_FRONT_RIGHT = 1025;
   public static final int GL_BACK_LEFT = 1026;
   public static final int GL_BACK_RIGHT = 1027;
   public static final int GL_FRONT = 1028;
   public static final int GL_BACK = 1029;
   public static final int GL_LEFT = 1030;
   public static final int GL_RIGHT = 1031;
   public static final int GL_FRONT_AND_BACK = 1032;
   public static final int GL_AUX0 = 1033;
   public static final int GL_AUX1 = 1034;
   public static final int GL_AUX2 = 1035;
   public static final int GL_AUX3 = 1036;
   public static final int GL_NO_ERROR = 0;
   public static final int GL_INVALID_ENUM = 1280;
   public static final int GL_INVALID_VALUE = 1281;
   public static final int GL_INVALID_OPERATION = 1282;
   public static final int GL_STACK_OVERFLOW = 1283;
   public static final int GL_STACK_UNDERFLOW = 1284;
   public static final int GL_OUT_OF_MEMORY = 1285;
   public static final int GL_2D = 1536;
   public static final int GL_3D = 1537;
   public static final int GL_3D_COLOR = 1538;
   public static final int GL_3D_COLOR_TEXTURE = 1539;
   public static final int GL_4D_COLOR_TEXTURE = 1540;
   public static final int GL_PASS_THROUGH_TOKEN = 1792;
   public static final int GL_POINT_TOKEN = 1793;
   public static final int GL_LINE_TOKEN = 1794;
   public static final int GL_POLYGON_TOKEN = 1795;
   public static final int GL_BITMAP_TOKEN = 1796;
   public static final int GL_DRAW_PIXEL_TOKEN = 1797;
   public static final int GL_COPY_PIXEL_TOKEN = 1798;
   public static final int GL_LINE_RESET_TOKEN = 1799;
   public static final int GL_EXP = 2048;
   public static final int GL_EXP2 = 2049;
   public static final int GL_CW = 2304;
   public static final int GL_CCW = 2305;
   public static final int GL_COEFF = 2560;
   public static final int GL_ORDER = 2561;
   public static final int GL_DOMAIN = 2562;
   public static final int GL_CURRENT_COLOR = 2816;
   public static final int GL_CURRENT_INDEX = 2817;
   public static final int GL_CURRENT_NORMAL = 2818;
   public static final int GL_CURRENT_TEXTURE_COORDS = 2819;
   public static final int GL_CURRENT_RASTER_COLOR = 2820;
   public static final int GL_CURRENT_RASTER_INDEX = 2821;
   public static final int GL_CURRENT_RASTER_TEXTURE_COORDS = 2822;
   public static final int GL_CURRENT_RASTER_POSITION = 2823;
   public static final int GL_CURRENT_RASTER_POSITION_VALID = 2824;
   public static final int GL_CURRENT_RASTER_DISTANCE = 2825;
   public static final int GL_POINT_SMOOTH = 2832;
   public static final int GL_POINT_SIZE = 2833;
   public static final int GL_POINT_SIZE_RANGE = 2834;
   public static final int GL_POINT_SIZE_GRANULARITY = 2835;
   public static final int GL_LINE_SMOOTH = 2848;
   public static final int GL_LINE_WIDTH = 2849;
   public static final int GL_LINE_WIDTH_RANGE = 2850;
   public static final int GL_LINE_WIDTH_GRANULARITY = 2851;
   public static final int GL_LINE_STIPPLE = 2852;
   public static final int GL_LINE_STIPPLE_PATTERN = 2853;
   public static final int GL_LINE_STIPPLE_REPEAT = 2854;
   public static final int GL_LIST_MODE = 2864;
   public static final int GL_MAX_LIST_NESTING = 2865;
   public static final int GL_LIST_BASE = 2866;
   public static final int GL_LIST_INDEX = 2867;
   public static final int GL_POLYGON_MODE = 2880;
   public static final int GL_POLYGON_SMOOTH = 2881;
   public static final int GL_POLYGON_STIPPLE = 2882;
   public static final int GL_EDGE_FLAG = 2883;
   public static final int GL_CULL_FACE = 2884;
   public static final int GL_CULL_FACE_MODE = 2885;
   public static final int GL_FRONT_FACE = 2886;
   public static final int GL_LIGHTING = 2896;
   public static final int GL_LIGHT_MODEL_LOCAL_VIEWER = 2897;
   public static final int GL_LIGHT_MODEL_TWO_SIDE = 2898;
   public static final int GL_LIGHT_MODEL_AMBIENT = 2899;
   public static final int GL_SHADE_MODEL = 2900;
   public static final int GL_COLOR_MATERIAL_FACE = 2901;
   public static final int GL_COLOR_MATERIAL_PARAMETER = 2902;
   public static final int GL_COLOR_MATERIAL = 2903;
   public static final int GL_FOG = 2912;
   public static final int GL_FOG_INDEX = 2913;
   public static final int GL_FOG_DENSITY = 2914;
   public static final int GL_FOG_START = 2915;
   public static final int GL_FOG_END = 2916;
   public static final int GL_FOG_MODE = 2917;
   public static final int GL_FOG_COLOR = 2918;
   public static final int GL_DEPTH_RANGE = 2928;
   public static final int GL_DEPTH_TEST = 2929;
   public static final int GL_DEPTH_WRITEMASK = 2930;
   public static final int GL_DEPTH_CLEAR_VALUE = 2931;
   public static final int GL_DEPTH_FUNC = 2932;
   public static final int GL_ACCUM_CLEAR_VALUE = 2944;
   public static final int GL_STENCIL_TEST = 2960;
   public static final int GL_STENCIL_CLEAR_VALUE = 2961;
   public static final int GL_STENCIL_FUNC = 2962;
   public static final int GL_STENCIL_VALUE_MASK = 2963;
   public static final int GL_STENCIL_FAIL = 2964;
   public static final int GL_STENCIL_PASS_DEPTH_FAIL = 2965;
   public static final int GL_STENCIL_PASS_DEPTH_PASS = 2966;
   public static final int GL_STENCIL_REF = 2967;
   public static final int GL_STENCIL_WRITEMASK = 2968;
   public static final int GL_MATRIX_MODE = 2976;
   public static final int GL_NORMALIZE = 2977;
   public static final int GL_VIEWPORT = 2978;
   public static final int GL_MODELVIEW_STACK_DEPTH = 2979;
   public static final int GL_PROJECTION_STACK_DEPTH = 2980;
   public static final int GL_TEXTURE_STACK_DEPTH = 2981;
   public static final int GL_MODELVIEW_MATRIX = 2982;
   public static final int GL_PROJECTION_MATRIX = 2983;
   public static final int GL_TEXTURE_MATRIX = 2984;
   public static final int GL_ATTRIB_STACK_DEPTH = 2992;
   public static final int GL_CLIENT_ATTRIB_STACK_DEPTH = 2993;
   public static final int GL_ALPHA_TEST = 3008;
   public static final int GL_ALPHA_TEST_FUNC = 3009;
   public static final int GL_ALPHA_TEST_REF = 3010;
   public static final int GL_DITHER = 3024;
   public static final int GL_BLEND_DST = 3040;
   public static final int GL_BLEND_SRC = 3041;
   public static final int GL_BLEND = 3042;
   public static final int GL_LOGIC_OP_MODE = 3056;
   public static final int GL_INDEX_LOGIC_OP = 3057;
   public static final int GL_LOGIC_OP = 3057;
   public static final int GL_COLOR_LOGIC_OP = 3058;
   public static final int GL_AUX_BUFFERS = 3072;
   public static final int GL_DRAW_BUFFER = 3073;
   public static final int GL_READ_BUFFER = 3074;
   public static final int GL_SCISSOR_BOX = 3088;
   public static final int GL_SCISSOR_TEST = 3089;
   public static final int GL_INDEX_CLEAR_VALUE = 3104;
   public static final int GL_INDEX_WRITEMASK = 3105;
   public static final int GL_COLOR_CLEAR_VALUE = 3106;
   public static final int GL_COLOR_WRITEMASK = 3107;
   public static final int GL_INDEX_MODE = 3120;
   public static final int GL_RGBA_MODE = 3121;
   public static final int GL_DOUBLEBUFFER = 3122;
   public static final int GL_STEREO = 3123;
   public static final int GL_RENDER_MODE = 3136;
   public static final int GL_PERSPECTIVE_CORRECTION_HINT = 3152;
   public static final int GL_POINT_SMOOTH_HINT = 3153;
   public static final int GL_LINE_SMOOTH_HINT = 3154;
   public static final int GL_POLYGON_SMOOTH_HINT = 3155;
   public static final int GL_FOG_HINT = 3156;
   public static final int GL_TEXTURE_GEN_S = 3168;
   public static final int GL_TEXTURE_GEN_T = 3169;
   public static final int GL_TEXTURE_GEN_R = 3170;
   public static final int GL_TEXTURE_GEN_Q = 3171;
   public static final int GL_PIXEL_MAP_I_TO_I = 3184;
   public static final int GL_PIXEL_MAP_S_TO_S = 3185;
   public static final int GL_PIXEL_MAP_I_TO_R = 3186;
   public static final int GL_PIXEL_MAP_I_TO_G = 3187;
   public static final int GL_PIXEL_MAP_I_TO_B = 3188;
   public static final int GL_PIXEL_MAP_I_TO_A = 3189;
   public static final int GL_PIXEL_MAP_R_TO_R = 3190;
   public static final int GL_PIXEL_MAP_G_TO_G = 3191;
   public static final int GL_PIXEL_MAP_B_TO_B = 3192;
   public static final int GL_PIXEL_MAP_A_TO_A = 3193;
   public static final int GL_PIXEL_MAP_I_TO_I_SIZE = 3248;
   public static final int GL_PIXEL_MAP_S_TO_S_SIZE = 3249;
   public static final int GL_PIXEL_MAP_I_TO_R_SIZE = 3250;
   public static final int GL_PIXEL_MAP_I_TO_G_SIZE = 3251;
   public static final int GL_PIXEL_MAP_I_TO_B_SIZE = 3252;
   public static final int GL_PIXEL_MAP_I_TO_A_SIZE = 3253;
   public static final int GL_PIXEL_MAP_R_TO_R_SIZE = 3254;
   public static final int GL_PIXEL_MAP_G_TO_G_SIZE = 3255;
   public static final int GL_PIXEL_MAP_B_TO_B_SIZE = 3256;
   public static final int GL_PIXEL_MAP_A_TO_A_SIZE = 3257;
   public static final int GL_UNPACK_SWAP_BYTES = 3312;
   public static final int GL_UNPACK_LSB_FIRST = 3313;
   public static final int GL_UNPACK_ROW_LENGTH = 3314;
   public static final int GL_UNPACK_SKIP_ROWS = 3315;
   public static final int GL_UNPACK_SKIP_PIXELS = 3316;
   public static final int GL_UNPACK_ALIGNMENT = 3317;
   public static final int GL_PACK_SWAP_BYTES = 3328;
   public static final int GL_PACK_LSB_FIRST = 3329;
   public static final int GL_PACK_ROW_LENGTH = 3330;
   public static final int GL_PACK_SKIP_ROWS = 3331;
   public static final int GL_PACK_SKIP_PIXELS = 3332;
   public static final int GL_PACK_ALIGNMENT = 3333;
   public static final int GL_MAP_COLOR = 3344;
   public static final int GL_MAP_STENCIL = 3345;
   public static final int GL_INDEX_SHIFT = 3346;
   public static final int GL_INDEX_OFFSET = 3347;
   public static final int GL_RED_SCALE = 3348;
   public static final int GL_RED_BIAS = 3349;
   public static final int GL_ZOOM_X = 3350;
   public static final int GL_ZOOM_Y = 3351;
   public static final int GL_GREEN_SCALE = 3352;
   public static final int GL_GREEN_BIAS = 3353;
   public static final int GL_BLUE_SCALE = 3354;
   public static final int GL_BLUE_BIAS = 3355;
   public static final int GL_ALPHA_SCALE = 3356;
   public static final int GL_ALPHA_BIAS = 3357;
   public static final int GL_DEPTH_SCALE = 3358;
   public static final int GL_DEPTH_BIAS = 3359;
   public static final int GL_MAX_EVAL_ORDER = 3376;
   public static final int GL_MAX_LIGHTS = 3377;
   public static final int GL_MAX_CLIP_PLANES = 3378;
   public static final int GL_MAX_TEXTURE_SIZE = 3379;
   public static final int GL_MAX_PIXEL_MAP_TABLE = 3380;
   public static final int GL_MAX_ATTRIB_STACK_DEPTH = 3381;
   public static final int GL_MAX_MODELVIEW_STACK_DEPTH = 3382;
   public static final int GL_MAX_NAME_STACK_DEPTH = 3383;
   public static final int GL_MAX_PROJECTION_STACK_DEPTH = 3384;
   public static final int GL_MAX_TEXTURE_STACK_DEPTH = 3385;
   public static final int GL_MAX_VIEWPORT_DIMS = 3386;
   public static final int GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = 3387;
   public static final int GL_SUBPIXEL_BITS = 3408;
   public static final int GL_INDEX_BITS = 3409;
   public static final int GL_RED_BITS = 3410;
   public static final int GL_GREEN_BITS = 3411;
   public static final int GL_BLUE_BITS = 3412;
   public static final int GL_ALPHA_BITS = 3413;
   public static final int GL_DEPTH_BITS = 3414;
   public static final int GL_STENCIL_BITS = 3415;
   public static final int GL_ACCUM_RED_BITS = 3416;
   public static final int GL_ACCUM_GREEN_BITS = 3417;
   public static final int GL_ACCUM_BLUE_BITS = 3418;
   public static final int GL_ACCUM_ALPHA_BITS = 3419;
   public static final int GL_NAME_STACK_DEPTH = 3440;
   public static final int GL_AUTO_NORMAL = 3456;
   public static final int GL_MAP1_COLOR_4 = 3472;
   public static final int GL_MAP1_INDEX = 3473;
   public static final int GL_MAP1_NORMAL = 3474;
   public static final int GL_MAP1_TEXTURE_COORD_1 = 3475;
   public static final int GL_MAP1_TEXTURE_COORD_2 = 3476;
   public static final int GL_MAP1_TEXTURE_COORD_3 = 3477;
   public static final int GL_MAP1_TEXTURE_COORD_4 = 3478;
   public static final int GL_MAP1_VERTEX_3 = 3479;
   public static final int GL_MAP1_VERTEX_4 = 3480;
   public static final int GL_MAP2_COLOR_4 = 3504;
   public static final int GL_MAP2_INDEX = 3505;
   public static final int GL_MAP2_NORMAL = 3506;
   public static final int GL_MAP2_TEXTURE_COORD_1 = 3507;
   public static final int GL_MAP2_TEXTURE_COORD_2 = 3508;
   public static final int GL_MAP2_TEXTURE_COORD_3 = 3509;
   public static final int GL_MAP2_TEXTURE_COORD_4 = 3510;
   public static final int GL_MAP2_VERTEX_3 = 3511;
   public static final int GL_MAP2_VERTEX_4 = 3512;
   public static final int GL_MAP1_GRID_DOMAIN = 3536;
   public static final int GL_MAP1_GRID_SEGMENTS = 3537;
   public static final int GL_MAP2_GRID_DOMAIN = 3538;
   public static final int GL_MAP2_GRID_SEGMENTS = 3539;
   public static final int GL_TEXTURE_1D = 3552;
   public static final int GL_TEXTURE_2D = 3553;
   public static final int GL_FEEDBACK_BUFFER_POINTER = 3568;
   public static final int GL_FEEDBACK_BUFFER_SIZE = 3569;
   public static final int GL_FEEDBACK_BUFFER_TYPE = 3570;
   public static final int GL_SELECTION_BUFFER_POINTER = 3571;
   public static final int GL_SELECTION_BUFFER_SIZE = 3572;
   public static final int GL_TEXTURE_WIDTH = 4096;
   public static final int GL_TEXTURE_HEIGHT = 4097;
   public static final int GL_TEXTURE_INTERNAL_FORMAT = 4099;
   public static final int GL_TEXTURE_COMPONENTS = 4099;
   public static final int GL_TEXTURE_BORDER_COLOR = 4100;
   public static final int GL_TEXTURE_BORDER = 4101;
   public static final int GL_DONT_CARE = 4352;
   public static final int GL_FASTEST = 4353;
   public static final int GL_NICEST = 4354;
   public static final int GL_LIGHT0 = 16384;
   public static final int GL_LIGHT1 = 16385;
   public static final int GL_LIGHT2 = 16386;
   public static final int GL_LIGHT3 = 16387;
   public static final int GL_LIGHT4 = 16388;
   public static final int GL_LIGHT5 = 16389;
   public static final int GL_LIGHT6 = 16390;
   public static final int GL_LIGHT7 = 16391;
   public static final int GL_AMBIENT = 4608;
   public static final int GL_DIFFUSE = 4609;
   public static final int GL_SPECULAR = 4610;
   public static final int GL_POSITION = 4611;
   public static final int GL_SPOT_DIRECTION = 4612;
   public static final int GL_SPOT_EXPONENT = 4613;
   public static final int GL_SPOT_CUTOFF = 4614;
   public static final int GL_CONSTANT_ATTENUATION = 4615;
   public static final int GL_LINEAR_ATTENUATION = 4616;
   public static final int GL_QUADRATIC_ATTENUATION = 4617;
   public static final int GL_COMPILE = 4864;
   public static final int GL_COMPILE_AND_EXECUTE = 4865;
   public static final int GL_CLEAR = 5376;
   public static final int GL_AND = 5377;
   public static final int GL_AND_REVERSE = 5378;
   public static final int GL_COPY = 5379;
   public static final int GL_AND_INVERTED = 5380;
   public static final int GL_NOOP = 5381;
   public static final int GL_XOR = 5382;
   public static final int GL_OR = 5383;
   public static final int GL_NOR = 5384;
   public static final int GL_EQUIV = 5385;
   public static final int GL_INVERT = 5386;
   public static final int GL_OR_REVERSE = 5387;
   public static final int GL_COPY_INVERTED = 5388;
   public static final int GL_OR_INVERTED = 5389;
   public static final int GL_NAND = 5390;
   public static final int GL_SET = 5391;
   public static final int GL_EMISSION = 5632;
   public static final int GL_SHININESS = 5633;
   public static final int GL_AMBIENT_AND_DIFFUSE = 5634;
   public static final int GL_COLOR_INDEXES = 5635;
   public static final int GL_MODELVIEW = 5888;
   public static final int GL_PROJECTION = 5889;
   public static final int GL_TEXTURE = 5890;
   public static final int GL_COLOR = 6144;
   public static final int GL_DEPTH = 6145;
   public static final int GL_STENCIL = 6146;
   public static final int GL_COLOR_INDEX = 6400;
   public static final int GL_STENCIL_INDEX = 6401;
   public static final int GL_DEPTH_COMPONENT = 6402;
   public static final int GL_RED = 6403;
   public static final int GL_GREEN = 6404;
   public static final int GL_BLUE = 6405;
   public static final int GL_ALPHA = 6406;
   public static final int GL_RGB = 6407;
   public static final int GL_RGBA = 6408;
   public static final int GL_LUMINANCE = 6409;
   public static final int GL_LUMINANCE_ALPHA = 6410;
   public static final int GL_BITMAP = 6656;
   public static final int GL_POINT = 6912;
   public static final int GL_LINE = 6913;
   public static final int GL_FILL = 6914;
   public static final int GL_RENDER = 7168;
   public static final int GL_FEEDBACK = 7169;
   public static final int GL_SELECT = 7170;
   public static final int GL_FLAT = 7424;
   public static final int GL_SMOOTH = 7425;
   public static final int GL_KEEP = 7680;
   public static final int GL_REPLACE = 7681;
   public static final int GL_INCR = 7682;
   public static final int GL_DECR = 7683;
   public static final int GL_VENDOR = 7936;
   public static final int GL_RENDERER = 7937;
   public static final int GL_VERSION = 7938;
   public static final int GL_EXTENSIONS = 7939;
   public static final int GL_S = 8192;
   public static final int GL_T = 8193;
   public static final int GL_R = 8194;
   public static final int GL_Q = 8195;
   public static final int GL_MODULATE = 8448;
   public static final int GL_DECAL = 8449;
   public static final int GL_TEXTURE_ENV_MODE = 8704;
   public static final int GL_TEXTURE_ENV_COLOR = 8705;
   public static final int GL_TEXTURE_ENV = 8960;
   public static final int GL_EYE_LINEAR = 9216;
   public static final int GL_OBJECT_LINEAR = 9217;
   public static final int GL_SPHERE_MAP = 9218;
   public static final int GL_TEXTURE_GEN_MODE = 9472;
   public static final int GL_OBJECT_PLANE = 9473;
   public static final int GL_EYE_PLANE = 9474;
   public static final int GL_NEAREST = 9728;
   public static final int GL_LINEAR = 9729;
   public static final int GL_NEAREST_MIPMAP_NEAREST = 9984;
   public static final int GL_LINEAR_MIPMAP_NEAREST = 9985;
   public static final int GL_NEAREST_MIPMAP_LINEAR = 9986;
   public static final int GL_LINEAR_MIPMAP_LINEAR = 9987;
   public static final int GL_TEXTURE_MAG_FILTER = 10240;
   public static final int GL_TEXTURE_MIN_FILTER = 10241;
   public static final int GL_TEXTURE_WRAP_S = 10242;
   public static final int GL_TEXTURE_WRAP_T = 10243;
   public static final int GL_CLAMP = 10496;
   public static final int GL_REPEAT = 10497;
   public static final int GL_CLIENT_PIXEL_STORE_BIT = 1;
   public static final int GL_CLIENT_VERTEX_ARRAY_BIT = 2;
   public static final int GL_CLIENT_ALL_ATTRIB_BITS = -1;
   public static final int GL_POLYGON_OFFSET_FACTOR = 32824;
   public static final int GL_POLYGON_OFFSET_UNITS = 10752;
   public static final int GL_POLYGON_OFFSET_POINT = 10753;
   public static final int GL_POLYGON_OFFSET_LINE = 10754;
   public static final int GL_POLYGON_OFFSET_FILL = 32823;
   public static final int GL_ALPHA4 = 32827;
   public static final int GL_ALPHA8 = 32828;
   public static final int GL_ALPHA12 = 32829;
   public static final int GL_ALPHA16 = 32830;
   public static final int GL_LUMINANCE4 = 32831;
   public static final int GL_LUMINANCE8 = 32832;
   public static final int GL_LUMINANCE12 = 32833;
   public static final int GL_LUMINANCE16 = 32834;
   public static final int GL_LUMINANCE4_ALPHA4 = 32835;
   public static final int GL_LUMINANCE6_ALPHA2 = 32836;
   public static final int GL_LUMINANCE8_ALPHA8 = 32837;
   public static final int GL_LUMINANCE12_ALPHA4 = 32838;
   public static final int GL_LUMINANCE12_ALPHA12 = 32839;
   public static final int GL_LUMINANCE16_ALPHA16 = 32840;
   public static final int GL_INTENSITY = 32841;
   public static final int GL_INTENSITY4 = 32842;
   public static final int GL_INTENSITY8 = 32843;
   public static final int GL_INTENSITY12 = 32844;
   public static final int GL_INTENSITY16 = 32845;
   public static final int GL_R3_G3_B2 = 10768;
   public static final int GL_RGB4 = 32847;
   public static final int GL_RGB5 = 32848;
   public static final int GL_RGB8 = 32849;
   public static final int GL_RGB10 = 32850;
   public static final int GL_RGB12 = 32851;
   public static final int GL_RGB16 = 32852;
   public static final int GL_RGBA2 = 32853;
   public static final int GL_RGBA4 = 32854;
   public static final int GL_RGB5_A1 = 32855;
   public static final int GL_RGBA8 = 32856;
   public static final int GL_RGB10_A2 = 32857;
   public static final int GL_RGBA12 = 32858;
   public static final int GL_RGBA16 = 32859;
   public static final int GL_TEXTURE_RED_SIZE = 32860;
   public static final int GL_TEXTURE_GREEN_SIZE = 32861;
   public static final int GL_TEXTURE_BLUE_SIZE = 32862;
   public static final int GL_TEXTURE_ALPHA_SIZE = 32863;
   public static final int GL_TEXTURE_LUMINANCE_SIZE = 32864;
   public static final int GL_TEXTURE_INTENSITY_SIZE = 32865;
   public static final int GL_PROXY_TEXTURE_1D = 32867;
   public static final int GL_PROXY_TEXTURE_2D = 32868;
   public static final int GL_TEXTURE_PRIORITY = 32870;
   public static final int GL_TEXTURE_RESIDENT = 32871;
   public static final int GL_TEXTURE_BINDING_1D = 32872;
   public static final int GL_TEXTURE_BINDING_2D = 32873;
   public static final int GL_VERTEX_ARRAY = 32884;
   public static final int GL_NORMAL_ARRAY = 32885;
   public static final int GL_COLOR_ARRAY = 32886;
   public static final int GL_INDEX_ARRAY = 32887;
   public static final int GL_TEXTURE_COORD_ARRAY = 32888;
   public static final int GL_EDGE_FLAG_ARRAY = 32889;
   public static final int GL_VERTEX_ARRAY_SIZE = 32890;
   public static final int GL_VERTEX_ARRAY_TYPE = 32891;
   public static final int GL_VERTEX_ARRAY_STRIDE = 32892;
   public static final int GL_NORMAL_ARRAY_TYPE = 32894;
   public static final int GL_NORMAL_ARRAY_STRIDE = 32895;
   public static final int GL_COLOR_ARRAY_SIZE = 32897;
   public static final int GL_COLOR_ARRAY_TYPE = 32898;
   public static final int GL_COLOR_ARRAY_STRIDE = 32899;
   public static final int GL_INDEX_ARRAY_TYPE = 32901;
   public static final int GL_INDEX_ARRAY_STRIDE = 32902;
   public static final int GL_TEXTURE_COORD_ARRAY_SIZE = 32904;
   public static final int GL_TEXTURE_COORD_ARRAY_TYPE = 32905;
   public static final int GL_TEXTURE_COORD_ARRAY_STRIDE = 32906;
   public static final int GL_EDGE_FLAG_ARRAY_STRIDE = 32908;
   public static final int GL_VERTEX_ARRAY_POINTER = 32910;
   public static final int GL_NORMAL_ARRAY_POINTER = 32911;
   public static final int GL_COLOR_ARRAY_POINTER = 32912;
   public static final int GL_INDEX_ARRAY_POINTER = 32913;
   public static final int GL_TEXTURE_COORD_ARRAY_POINTER = 32914;
   public static final int GL_EDGE_FLAG_ARRAY_POINTER = 32915;
   public static final int GL_V2F = 10784;
   public static final int GL_V3F = 10785;
   public static final int GL_C4UB_V2F = 10786;
   public static final int GL_C4UB_V3F = 10787;
   public static final int GL_C3F_V3F = 10788;
   public static final int GL_N3F_V3F = 10789;
   public static final int GL_C4F_N3F_V3F = 10790;
   public static final int GL_T2F_V3F = 10791;
   public static final int GL_T4F_V4F = 10792;
   public static final int GL_T2F_C4UB_V3F = 10793;
   public static final int GL_T2F_C3F_V3F = 10794;
   public static final int GL_T2F_N3F_V3F = 10795;
   public static final int GL_T2F_C4F_N3F_V3F = 10796;
   public static final int GL_T4F_C4F_N3F_V4F = 10797;

   public static void glColorPointer(int size, boolean unsigned, int stride, ByteBuffer pointer) {
      glColorPointer(size, unsigned ? 5121 : 5120, stride, pointer);
   }

   public static void glColorPointer(int size, int stride, FloatBuffer pointer) {
      glColorPointer(size, 5126, stride, pointer);
   }

   public static void glFog(int p1, FloatBuffer p2) {
      glFogfv(p1, p2);
   }

   public static void glFog(int p1, IntBuffer p2) {
      glFogiv(p1, p2);
   }

   public static void glGetBoolean(int p1, ByteBuffer p2) {
      glGetBooleanv(p1, p2);
   }

   public static void glGetDouble(int p1, DoubleBuffer p2) {
      glGetDoublev(p1, p2);
   }

   public static void glGetFloat(int p1, FloatBuffer p2) {
      glGetFloatv(p1, p2);
   }

   public static void glGetInteger(int p1, IntBuffer p2) {
      glGetIntegerv(p1, p2);
   }

   public static void glGetLight(int p1, int p2, FloatBuffer p3) {
      glGetLightfv(p1, p2, p3);
   }

   public static void glGetLight(int p1, int p2, IntBuffer p3) {
      glGetLightiv(p1, p2, p3);
   }

   public static void glGetMap(int p1, int p2, DoubleBuffer p3) {
      glGetMapdv(p1, p2, p3);
   }

   public static void glGetMap(int p1, int p2, FloatBuffer p3) {
      glGetMapfv(p1, p2, p3);
   }

   public static void glGetMap(int p1, int p2, IntBuffer p3) {
      glGetMapiv(p1, p2, p3);
   }

   public static void glGetMaterial(int p1, int p2, FloatBuffer p3) {
      glGetMaterialfv(p1, p2, p3);
   }

   public static void glGetMaterial(int p1, int p2, IntBuffer p3) {
      glGetMaterialiv(p1, p2, p3);
   }

   public static void glGetPixelMap(int p1, FloatBuffer p2) {
      glGetPixelMapfv(p1, p2);
   }

   public static void glGetPixelMapu(int p1, IntBuffer p2) {
      glGetPixelMapuiv(p1, p2);
   }

   public static void glGetPixelMapu(int p1, ShortBuffer p2) {
      glGetPixelMapusv(p1, p2);
   }

   public static void glGetTexEnv(int p1, int p2, FloatBuffer p3) {
      glGetTexEnvfv(p1, p2, p3);
   }

   public static void glGetTexEnv(int p1, int p2, IntBuffer p3) {
      glGetTexEnviv(p1, p2, p3);
   }

   public static void glGetTexGen(int p1, int p2, DoubleBuffer p3) {
      glGetTexGendv(p1, p2, p3);
   }

   public static void glGetTexGen(int p1, int p2, FloatBuffer p3) {
      glGetTexGenfv(p1, p2, p3);
   }

   public static void glGetTexGen(int p1, int p2, IntBuffer p3) {
      glGetTexGeniv(p1, p2, p3);
   }

   public static void glGetTexLevelParameter(int target, int level, int pname, FloatBuffer params) {
      glGetTexLevelParameterfv(target, level, pname, params);
   }

   public static void glGetTexLevelParameter(int target, int level, int pname, IntBuffer params) {
      glGetTexLevelParameteriv(target, level, pname, params);
   }

   public static void glGetTexParameter(int target, int pname, FloatBuffer params) {
      glGetTexParameterfv(target, pname, params);
   }

   public static void glGetTexParameter(int target, int pname, IntBuffer params) {
      glGetTexParameteriv(target, pname, params);
   }

   public static void glLight(int light, int pname, FloatBuffer params) {
      glLightfv(light, pname, params);
   }

   public static void glLight(int light, int pname, IntBuffer params) {
      glLightiv(light, pname, params);
   }

   public static void glLightModel(int pname, FloatBuffer params) {
      glLightModelfv(pname, params);
   }

   public static void glLightModel(int pname, IntBuffer params) {
      glLightModeliv(pname, params);
   }

   public static void glLoadMatrix(DoubleBuffer m) {
      glLoadMatrixd(m);
   }

   public static void glLoadMatrix(FloatBuffer m) {
      glLoadMatrixf(m);
   }

   public static void glMaterial(int p1, int p2, FloatBuffer p3) {
      glMaterialfv(p1, p2, p3);
   }

   public static void glMaterial(int p1, int p2, IntBuffer p3) {
      glMaterialiv(p1, p2, p3);
   }

   public static void glMultMatrix(DoubleBuffer p1) {
      glMultMatrixd(p1);
   }

   public static void glMultMatrix(FloatBuffer p1) {
      glMultMatrixf(p1);
   }

   public static void glNormalPointer(int stride, ByteBuffer pointer) {
      glNormalPointer(5120, stride, pointer);
   }

   public static void glNormalPointer(int stride, FloatBuffer pointer) {
      glNormalPointer(5126, stride, pointer);
   }

   public static void glNormalPointer(int stride, IntBuffer pointer) {
      glNormalPointer(5124, stride, pointer);
   }

   public static void glNormalPointer(int stride, ShortBuffer pointer) {
      glNormalPointer(5122, stride, pointer);
   }

   public static void glPixelMap(int p1, FloatBuffer p2) {
      glPixelMapfv(p1, p2);
   }

   public static void glPixelMapu(int p1, IntBuffer p2) {
      glPixelMapuiv(p1, p2);
   }

   public static void glPixelMapu(int p1, ShortBuffer p2) {
      glPixelMapusv(p1, p2);
   }

   public static void glTexCoordPointer(int size, int stride, FloatBuffer pointer) {
      glTexCoordPointer(size, 5126, stride, pointer);
   }

   public static void glTexCoordPointer(int size, int stride, IntBuffer pointer) {
      glTexCoordPointer(size, 5124, stride, pointer);
   }

   public static void glTexCoordPointer(int size, int stride, ShortBuffer pointer) {
      glTexCoordPointer(size, 5122, stride, pointer);
   }

   public static void glTexEnv(int p1, int p2, FloatBuffer p3) {
      glTexEnvfv(p1, p2, p3);
   }

   public static void glTexEnv(int p1, int p2, IntBuffer p3) {
      glTexEnviv(p1, p2, p3);
   }

   public static void glTexGen(int p1, int p2, DoubleBuffer p3) {
      glTexGendv(p1, p2, p3);
   }

   public static void glTexGen(int p1, int p2, FloatBuffer p3) {
      glTexGenfv(p1, p2, p3);
   }

   public static void glTexGen(int p1, int p2, IntBuffer p3) {
      glTexGeniv(p1, p2, p3);
   }

   public static void glVertexPointer(int size, int stride, FloatBuffer pointer) {
      glVertexPointer(size, 5126, stride, pointer);
   }

   public static void glVertexPointer(int size, int stride, IntBuffer pointer) {
      glVertexPointer(size, 5124, stride, pointer);
   }

   public static void glVertexPointer(int size, int stride, ShortBuffer pointer) {
      glVertexPointer(size, 5122, stride, pointer);
   }

   protected GL11() {
      throw new UnsupportedOperationException();
   }

   static boolean isAvailable(GLCapabilities caps, Set<String> ext, boolean fc) {
      return (
            fc
               || Checks.checkFunctions(
                  caps.glAccum,
                  caps.glAlphaFunc,
                  caps.glAreTexturesResident,
                  caps.glArrayElement,
                  caps.glBegin,
                  caps.glBitmap,
                  caps.glCallList,
                  caps.glCallLists,
                  caps.glClearAccum,
                  caps.glClearIndex,
                  caps.glClipPlane,
                  caps.glColor3b,
                  caps.glColor3s,
                  caps.glColor3i,
                  caps.glColor3f,
                  caps.glColor3d,
                  caps.glColor3ub,
                  caps.glColor3us,
                  caps.glColor3ui,
                  caps.glColor3bv,
                  caps.glColor3sv,
                  caps.glColor3iv,
                  caps.glColor3fv,
                  caps.glColor3dv,
                  caps.glColor3ubv,
                  caps.glColor3usv,
                  caps.glColor3uiv,
                  caps.glColor4b,
                  caps.glColor4s,
                  caps.glColor4i,
                  caps.glColor4f,
                  caps.glColor4d,
                  caps.glColor4ub,
                  caps.glColor4us,
                  caps.glColor4ui,
                  caps.glColor4bv,
                  caps.glColor4sv,
                  caps.glColor4iv,
                  caps.glColor4fv,
                  caps.glColor4dv,
                  caps.glColor4ubv,
                  caps.glColor4usv,
                  caps.glColor4uiv,
                  caps.glColorMaterial,
                  caps.glColorPointer,
                  caps.glCopyPixels,
                  caps.glDeleteLists,
                  caps.glDrawPixels,
                  caps.glEdgeFlag,
                  caps.glEdgeFlagv,
                  caps.glEdgeFlagPointer,
                  caps.glEnd,
                  caps.glEvalCoord1f,
                  caps.glEvalCoord1fv,
                  caps.glEvalCoord1d,
                  caps.glEvalCoord1dv,
                  caps.glEvalCoord2f,
                  caps.glEvalCoord2fv,
                  caps.glEvalCoord2d,
                  caps.glEvalCoord2dv,
                  caps.glEvalMesh1,
                  caps.glEvalMesh2,
                  caps.glEvalPoint1,
                  caps.glEvalPoint2,
                  caps.glFeedbackBuffer,
                  caps.glFogi,
                  caps.glFogiv,
                  caps.glFogf,
                  caps.glFogfv,
                  caps.glGenLists,
                  caps.glGetClipPlane,
                  caps.glGetLightiv,
                  caps.glGetLightfv,
                  caps.glGetMapiv,
                  caps.glGetMapfv,
                  caps.glGetMapdv,
                  caps.glGetMaterialiv,
                  caps.glGetMaterialfv,
                  caps.glGetPixelMapfv,
                  caps.glGetPixelMapusv,
                  caps.glGetPixelMapuiv,
                  caps.glGetPolygonStipple,
                  caps.glGetTexEnviv,
                  caps.glGetTexEnvfv,
                  caps.glGetTexGeniv,
                  caps.glGetTexGenfv,
                  caps.glGetTexGendv,
                  caps.glIndexi,
                  caps.glIndexub,
                  caps.glIndexs,
                  caps.glIndexf,
                  caps.glIndexd,
                  caps.glIndexiv,
                  caps.glIndexubv,
                  caps.glIndexsv,
                  caps.glIndexfv,
                  caps.glIndexdv,
                  caps.glIndexMask,
                  caps.glIndexPointer,
                  caps.glInitNames,
                  caps.glInterleavedArrays,
                  caps.glIsList,
                  caps.glLightModeli,
                  caps.glLightModelf,
                  caps.glLightModeliv,
                  caps.glLightModelfv,
                  caps.glLighti,
                  caps.glLightf,
                  caps.glLightiv,
                  caps.glLightfv,
                  caps.glLineStipple,
                  caps.glListBase,
                  caps.glLoadMatrixf,
                  caps.glLoadMatrixd,
                  caps.glLoadIdentity,
                  caps.glLoadName,
                  caps.glMap1f,
                  caps.glMap1d,
                  caps.glMap2f,
                  caps.glMap2d,
                  caps.glMapGrid1f,
                  caps.glMapGrid1d,
                  caps.glMapGrid2f,
                  caps.glMapGrid2d,
                  caps.glMateriali,
                  caps.glMaterialf,
                  caps.glMaterialiv,
                  caps.glMaterialfv,
                  caps.glMatrixMode,
                  caps.glMultMatrixf,
                  caps.glMultMatrixd,
                  caps.glFrustum,
                  caps.glNewList,
                  caps.glEndList,
                  caps.glNormal3f,
                  caps.glNormal3b,
                  caps.glNormal3s,
                  caps.glNormal3i,
                  caps.glNormal3d,
                  caps.glNormal3fv,
                  caps.glNormal3bv,
                  caps.glNormal3sv,
                  caps.glNormal3iv,
                  caps.glNormal3dv,
                  caps.glNormalPointer,
                  caps.glOrtho,
                  caps.glPassThrough,
                  caps.glPixelMapfv,
                  caps.glPixelMapusv,
                  caps.glPixelMapuiv,
                  caps.glPixelTransferi,
                  caps.glPixelTransferf,
                  caps.glPixelZoom,
                  caps.glPolygonStipple,
                  caps.glPushAttrib,
                  caps.glPushClientAttrib,
                  caps.glPopAttrib,
                  caps.glPopClientAttrib,
                  caps.glPopMatrix,
                  caps.glPopName,
                  caps.glPrioritizeTextures,
                  caps.glPushMatrix,
                  caps.glPushName,
                  caps.glRasterPos2i,
                  caps.glRasterPos2s,
                  caps.glRasterPos2f,
                  caps.glRasterPos2d,
                  caps.glRasterPos2iv,
                  caps.glRasterPos2sv,
                  caps.glRasterPos2fv,
                  caps.glRasterPos2dv,
                  caps.glRasterPos3i,
                  caps.glRasterPos3s,
                  caps.glRasterPos3f,
                  caps.glRasterPos3d,
                  caps.glRasterPos3iv,
                  caps.glRasterPos3sv,
                  caps.glRasterPos3fv,
                  caps.glRasterPos3dv,
                  caps.glRasterPos4i,
                  caps.glRasterPos4s,
                  caps.glRasterPos4f,
                  caps.glRasterPos4d,
                  caps.glRasterPos4iv,
                  caps.glRasterPos4sv,
                  caps.glRasterPos4fv,
                  caps.glRasterPos4dv,
                  caps.glRecti,
                  caps.glRects,
                  caps.glRectf,
                  caps.glRectd,
                  caps.glRectiv,
                  caps.glRectsv,
                  caps.glRectfv,
                  caps.glRectdv,
                  caps.glRenderMode,
                  caps.glRotatef,
                  caps.glRotated,
                  caps.glScalef,
                  caps.glScaled,
                  caps.glSelectBuffer,
                  caps.glShadeModel,
                  caps.glTexCoord1f,
                  caps.glTexCoord1s,
                  caps.glTexCoord1i,
                  caps.glTexCoord1d,
                  caps.glTexCoord1fv,
                  caps.glTexCoord1sv,
                  caps.glTexCoord1iv,
                  caps.glTexCoord1dv,
                  caps.glTexCoord2f,
                  caps.glTexCoord2s,
                  caps.glTexCoord2i,
                  caps.glTexCoord2d,
                  caps.glTexCoord2fv,
                  caps.glTexCoord2sv,
                  caps.glTexCoord2iv,
                  caps.glTexCoord2dv,
                  caps.glTexCoord3f,
                  caps.glTexCoord3s,
                  caps.glTexCoord3i,
                  caps.glTexCoord3d,
                  caps.glTexCoord3fv,
                  caps.glTexCoord3sv,
                  caps.glTexCoord3iv,
                  caps.glTexCoord3dv,
                  caps.glTexCoord4f,
                  caps.glTexCoord4s,
                  caps.glTexCoord4i,
                  caps.glTexCoord4d,
                  caps.glTexCoord4fv,
                  caps.glTexCoord4sv,
                  caps.glTexCoord4iv,
                  caps.glTexCoord4dv,
                  caps.glTexCoordPointer,
                  caps.glTexEnvi,
                  caps.glTexEnviv,
                  caps.glTexEnvf,
                  caps.glTexEnvfv,
                  caps.glTexGeni,
                  caps.glTexGeniv,
                  caps.glTexGenf,
                  caps.glTexGenfv,
                  caps.glTexGend,
                  caps.glTexGendv,
                  caps.glTranslatef,
                  caps.glTranslated,
                  caps.glVertex2f,
                  caps.glVertex2s,
                  caps.glVertex2i,
                  caps.glVertex2d,
                  caps.glVertex2fv,
                  caps.glVertex2sv,
                  caps.glVertex2iv,
                  caps.glVertex2dv,
                  caps.glVertex3f,
                  caps.glVertex3s,
                  caps.glVertex3i,
                  caps.glVertex3d,
                  caps.glVertex3fv,
                  caps.glVertex3sv,
                  caps.glVertex3iv,
                  caps.glVertex3dv,
                  caps.glVertex4f,
                  caps.glVertex4s,
                  caps.glVertex4i,
                  caps.glVertex4d,
                  caps.glVertex4fv,
                  caps.glVertex4sv,
                  caps.glVertex4iv,
                  caps.glVertex4dv,
                  caps.glVertexPointer
               )
         )
         && Checks.checkFunctions(
            caps.glEnable,
            caps.glDisable,
            caps.glBindTexture,
            caps.glBlendFunc,
            caps.glClear,
            caps.glClearColor,
            caps.glClearDepth,
            caps.glClearStencil,
            caps.glColorMask,
            caps.glCullFace,
            caps.glDepthFunc,
            caps.glDepthMask,
            caps.glDepthRange,
            ext.contains("GL_NV_vertex_buffer_unified_memory") ? caps.glDisableClientState : -1L,
            caps.glDrawArrays,
            caps.glDrawBuffer,
            caps.glDrawElements,
            ext.contains("GL_NV_vertex_buffer_unified_memory") ? caps.glEnableClientState : -1L,
            caps.glFinish,
            caps.glFlush,
            caps.glFrontFace,
            caps.glGenTextures,
            caps.glDeleteTextures,
            caps.glGetBooleanv,
            caps.glGetFloatv,
            caps.glGetIntegerv,
            caps.glGetDoublev,
            caps.glGetError,
            caps.glGetPointerv,
            caps.glGetString,
            caps.glGetTexImage,
            caps.glGetTexLevelParameteriv,
            caps.glGetTexLevelParameterfv,
            caps.glGetTexParameteriv,
            caps.glGetTexParameterfv,
            caps.glHint,
            caps.glIsEnabled,
            caps.glIsTexture,
            caps.glLineWidth,
            caps.glLogicOp,
            caps.glPixelStorei,
            caps.glPixelStoref,
            caps.glPointSize,
            caps.glPolygonMode,
            caps.glPolygonOffset,
            caps.glReadBuffer,
            caps.glReadPixels,
            caps.glScissor,
            caps.glStencilFunc,
            caps.glStencilMask,
            caps.glStencilOp,
            caps.glTexImage1D,
            caps.glTexImage2D,
            caps.glCopyTexImage1D,
            caps.glCopyTexImage2D,
            caps.glCopyTexSubImage1D,
            caps.glCopyTexSubImage2D,
            caps.glTexParameteri,
            caps.glTexParameteriv,
            caps.glTexParameterf,
            caps.glTexParameterfv,
            caps.glTexSubImage1D,
            caps.glTexSubImage2D,
            caps.glViewport
         );
   }

   public static void glEnable(@NativeType("GLenum") int target) {
      GL11C.glEnable(target);
   }

   public static void glDisable(@NativeType("GLenum") int target) {
      GL11C.glDisable(target);
   }

   public static native void glAccum(@NativeType("GLenum") int var0, @NativeType("GLfloat") float var1);

   public static native void glAlphaFunc(@NativeType("GLenum") int var0, @NativeType("GLfloat") float var1);

   public static native boolean nglAreTexturesResident(int var0, long var1, long var3);

   @NativeType("GLboolean")
   public static boolean glAreTexturesResident(@NativeType("GLuint const *") IntBuffer textures, @NativeType("GLboolean *") ByteBuffer residences) {
      if (Checks.CHECKS) {
         Checks.check(residences, textures.remaining());
      }

      return nglAreTexturesResident(textures.remaining(), MemoryUtil.memAddress(textures), MemoryUtil.memAddress(residences));
   }

   @NativeType("GLboolean")
   public static boolean glAreTexturesResident(@NativeType("GLuint const *") int texture, @NativeType("GLboolean *") ByteBuffer residences) {
      if (Checks.CHECKS) {
         Checks.check(residences, 1);
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer textures = stack.ints(texture);
         return nglAreTexturesResident(1, MemoryUtil.memAddress(textures), MemoryUtil.memAddress(residences));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glArrayElement(@NativeType("GLint") int var0);

   public static native void glBegin(@NativeType("GLenum") int var0);

   public static void glBindTexture(@NativeType("GLenum") int target, @NativeType("GLuint") int texture) {
      GL11C.glBindTexture(target, texture);
   }

   public static native void nglBitmap(int var0, int var1, float var2, float var3, float var4, float var5, long var6);

   public static void glBitmap(
      @NativeType("GLsizei") int w,
      @NativeType("GLsizei") int h,
      @NativeType("GLfloat") float xOrig,
      @NativeType("GLfloat") float yOrig,
      @NativeType("GLfloat") float xInc,
      @NativeType("GLfloat") float yInc,
      @Nullable @NativeType("GLubyte const *") ByteBuffer data
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(data, (w + 7 >> 3) * h);
      }

      nglBitmap(w, h, xOrig, yOrig, xInc, yInc, MemoryUtil.memAddressSafe(data));
   }

   public static void glBitmap(
      @NativeType("GLsizei") int w,
      @NativeType("GLsizei") int h,
      @NativeType("GLfloat") float xOrig,
      @NativeType("GLfloat") float yOrig,
      @NativeType("GLfloat") float xInc,
      @NativeType("GLfloat") float yInc,
      @Nullable @NativeType("GLubyte const *") long data
   ) {
      nglBitmap(w, h, xOrig, yOrig, xInc, yInc, data);
   }

   public static void glBlendFunc(@NativeType("GLenum") int sfactor, @NativeType("GLenum") int dfactor) {
      GL11C.glBlendFunc(sfactor, dfactor);
   }

   public static native void glCallList(@NativeType("GLuint") int var0);

   public static native void nglCallLists(int var0, int var1, long var2);

   public static void glCallLists(@NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer lists) {
      nglCallLists(lists.remaining() / GLChecks.typeToBytes(type), type, MemoryUtil.memAddress(lists));
   }

   public static void glCallLists(@NativeType("void const *") ByteBuffer lists) {
      nglCallLists(lists.remaining(), 5121, MemoryUtil.memAddress(lists));
   }

   public static void glCallLists(@NativeType("void const *") ShortBuffer lists) {
      nglCallLists(lists.remaining(), 5123, MemoryUtil.memAddress(lists));
   }

   public static void glCallLists(@NativeType("void const *") IntBuffer lists) {
      nglCallLists(lists.remaining(), 5125, MemoryUtil.memAddress(lists));
   }

   public static void glClear(@NativeType("GLbitfield") int mask) {
      GL11C.glClear(mask);
   }

   public static native void glClearAccum(
      @NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   public static void glClearColor(
      @NativeType("GLfloat") float red, @NativeType("GLfloat") float green, @NativeType("GLfloat") float blue, @NativeType("GLfloat") float alpha
   ) {
      GL11C.glClearColor(red, green, blue, alpha);
   }

   public static void glClearDepth(@NativeType("GLdouble") double depth) {
      GL11C.glClearDepth(depth);
   }

   public static native void glClearIndex(@NativeType("GLfloat") float var0);

   public static void glClearStencil(@NativeType("GLint") int s) {
      GL11C.glClearStencil(s);
   }

   public static native void nglClipPlane(int var0, long var1);

   public static void glClipPlane(@NativeType("GLenum") int plane, @NativeType("GLdouble const *") DoubleBuffer equation) {
      if (Checks.CHECKS) {
         Checks.check(equation, 4);
      }

      nglClipPlane(plane, MemoryUtil.memAddress(equation));
   }

   public static native void glColor3b(@NativeType("GLbyte") byte var0, @NativeType("GLbyte") byte var1, @NativeType("GLbyte") byte var2);

   public static native void glColor3s(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2);

   public static native void glColor3i(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glColor3f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glColor3d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4);

   public static native void glColor3ub(@NativeType("GLubyte") byte var0, @NativeType("GLubyte") byte var1, @NativeType("GLubyte") byte var2);

   public static native void glColor3us(@NativeType("GLushort") short var0, @NativeType("GLushort") short var1, @NativeType("GLushort") short var2);

   public static native void glColor3ui(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void nglColor3bv(long var0);

   public static void glColor3bv(@NativeType("GLbyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglColor3bv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor3sv(long var0);

   public static void glColor3sv(@NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglColor3sv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor3iv(long var0);

   public static void glColor3iv(@NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglColor3iv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor3fv(long var0);

   public static void glColor3fv(@NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglColor3fv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor3dv(long var0);

   public static void glColor3dv(@NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglColor3dv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor3ubv(long var0);

   public static void glColor3ubv(@NativeType("GLubyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglColor3ubv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor3usv(long var0);

   public static void glColor3usv(@NativeType("GLushort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglColor3usv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor3uiv(long var0);

   public static void glColor3uiv(@NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglColor3uiv(MemoryUtil.memAddress(v));
   }

   public static native void glColor4b(
      @NativeType("GLbyte") byte var0, @NativeType("GLbyte") byte var1, @NativeType("GLbyte") byte var2, @NativeType("GLbyte") byte var3
   );

   public static native void glColor4s(
      @NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2, @NativeType("GLshort") short var3
   );

   public static native void glColor4i(
      @NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2, @NativeType("GLint") int var3
   );

   public static native void glColor4f(
      @NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   public static native void glColor4d(
      @NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4, @NativeType("GLdouble") double var6
   );

   public static native void glColor4ub(
      @NativeType("GLubyte") byte var0, @NativeType("GLubyte") byte var1, @NativeType("GLubyte") byte var2, @NativeType("GLubyte") byte var3
   );

   public static native void glColor4us(
      @NativeType("GLushort") short var0, @NativeType("GLushort") short var1, @NativeType("GLushort") short var2, @NativeType("GLushort") short var3
   );

   public static native void glColor4ui(
      @NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2, @NativeType("GLint") int var3
   );

   public static native void nglColor4bv(long var0);

   public static void glColor4bv(@NativeType("GLbyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglColor4bv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor4sv(long var0);

   public static void glColor4sv(@NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglColor4sv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor4iv(long var0);

   public static void glColor4iv(@NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglColor4iv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor4fv(long var0);

   public static void glColor4fv(@NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglColor4fv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor4dv(long var0);

   public static void glColor4dv(@NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglColor4dv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor4ubv(long var0);

   public static void glColor4ubv(@NativeType("GLubyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglColor4ubv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor4usv(long var0);

   public static void glColor4usv(@NativeType("GLushort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglColor4usv(MemoryUtil.memAddress(v));
   }

   public static native void nglColor4uiv(long var0);

   public static void glColor4uiv(@NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglColor4uiv(MemoryUtil.memAddress(v));
   }

   public static void glColorMask(
      @NativeType("GLboolean") boolean red,
      @NativeType("GLboolean") boolean green,
      @NativeType("GLboolean") boolean blue,
      @NativeType("GLboolean") boolean alpha
   ) {
      GL11C.glColorMask(red, green, blue, alpha);
   }

   public static native void glColorMaterial(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1);

   public static native void nglColorPointer(int var0, int var1, int var2, long var3);

   public static void glColorPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ByteBuffer pointer
   ) {
      nglColorPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glColorPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") long pointer
   ) {
      nglColorPointer(size, type, stride, pointer);
   }

   public static void glColorPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ShortBuffer pointer
   ) {
      nglColorPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glColorPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") IntBuffer pointer
   ) {
      nglColorPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glColorPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") FloatBuffer pointer
   ) {
      nglColorPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static native void glCopyPixels(
      @NativeType("GLint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLsizei") int var2,
      @NativeType("GLsizei") int var3,
      @NativeType("GLenum") int var4
   );

   public static void glCullFace(@NativeType("GLenum") int mode) {
      GL11C.glCullFace(mode);
   }

   public static native void glDeleteLists(@NativeType("GLuint") int var0, @NativeType("GLsizei") int var1);

   public static void glDepthFunc(@NativeType("GLenum") int func) {
      GL11C.glDepthFunc(func);
   }

   public static void glDepthMask(@NativeType("GLboolean") boolean flag) {
      GL11C.glDepthMask(flag);
   }

   public static void glDepthRange(@NativeType("GLdouble") double zNear, @NativeType("GLdouble") double zFar) {
      GL11C.glDepthRange(zNear, zFar);
   }

   public static native void glDisableClientState(@NativeType("GLenum") int var0);

   public static void glDrawArrays(@NativeType("GLenum") int mode, @NativeType("GLint") int first, @NativeType("GLsizei") int count) {
      GL11C.glDrawArrays(mode, first, count);
   }

   public static void glDrawBuffer(@NativeType("GLenum") int buf) {
      GL11C.glDrawBuffer(buf);
   }

   public static void nglDrawElements(int mode, int count, int type, long indices) {
      GL11C.nglDrawElements(mode, count, type, indices);
   }

   public static void glDrawElements(
      @NativeType("GLenum") int mode, @NativeType("GLsizei") int count, @NativeType("GLenum") int type, @NativeType("void const *") long indices
   ) {
      GL11C.glDrawElements(mode, count, type, indices);
   }

   public static void glDrawElements(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer indices) {
      GL11C.glDrawElements(mode, type, indices);
   }

   public static void glDrawElements(@NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indices) {
      GL11C.glDrawElements(mode, indices);
   }

   public static void glDrawElements(@NativeType("GLenum") int mode, @NativeType("void const *") ShortBuffer indices) {
      GL11C.glDrawElements(mode, indices);
   }

   public static void glDrawElements(@NativeType("GLenum") int mode, @NativeType("void const *") IntBuffer indices) {
      GL11C.glDrawElements(mode, indices);
   }

   public static native void nglDrawPixels(int var0, int var1, int var2, int var3, long var4);

   public static void glDrawPixels(
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer pixels
   ) {
      nglDrawPixels(width, height, format, type, MemoryUtil.memAddress(pixels));
   }

   public static void glDrawPixels(
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long pixels
   ) {
      nglDrawPixels(width, height, format, type, pixels);
   }

   public static void glDrawPixels(
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ShortBuffer pixels
   ) {
      nglDrawPixels(width, height, format, type, MemoryUtil.memAddress(pixels));
   }

   public static void glDrawPixels(
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") IntBuffer pixels
   ) {
      nglDrawPixels(width, height, format, type, MemoryUtil.memAddress(pixels));
   }

   public static void glDrawPixels(
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") FloatBuffer pixels
   ) {
      nglDrawPixels(width, height, format, type, MemoryUtil.memAddress(pixels));
   }

   public static native void glEdgeFlag(@NativeType("GLboolean") boolean var0);

   public static native void nglEdgeFlagv(long var0);

   public static void glEdgeFlagv(@NativeType("GLboolean const *") ByteBuffer flag) {
      if (Checks.CHECKS) {
         Checks.check(flag, 1);
      }

      nglEdgeFlagv(MemoryUtil.memAddress(flag));
   }

   public static native void nglEdgeFlagPointer(int var0, long var1);

   public static void glEdgeFlagPointer(@NativeType("GLsizei") int stride, @NativeType("GLboolean const *") ByteBuffer pointer) {
      nglEdgeFlagPointer(stride, MemoryUtil.memAddress(pointer));
   }

   public static void glEdgeFlagPointer(@NativeType("GLsizei") int stride, @NativeType("GLboolean const *") long pointer) {
      nglEdgeFlagPointer(stride, pointer);
   }

   public static native void glEnableClientState(@NativeType("GLenum") int var0);

   public static native void glEnd();

   public static native void glEvalCoord1f(@NativeType("GLfloat") float var0);

   public static native void nglEvalCoord1fv(long var0);

   public static void glEvalCoord1fv(@NativeType("GLfloat const *") FloatBuffer u) {
      if (Checks.CHECKS) {
         Checks.check(u, 1);
      }

      nglEvalCoord1fv(MemoryUtil.memAddress(u));
   }

   public static native void glEvalCoord1d(@NativeType("GLdouble") double var0);

   public static native void nglEvalCoord1dv(long var0);

   public static void glEvalCoord1dv(@NativeType("GLdouble const *") DoubleBuffer u) {
      if (Checks.CHECKS) {
         Checks.check(u, 1);
      }

      nglEvalCoord1dv(MemoryUtil.memAddress(u));
   }

   public static native void glEvalCoord2f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1);

   public static native void nglEvalCoord2fv(long var0);

   public static void glEvalCoord2fv(@NativeType("GLfloat const *") FloatBuffer u) {
      if (Checks.CHECKS) {
         Checks.check(u, 2);
      }

      nglEvalCoord2fv(MemoryUtil.memAddress(u));
   }

   public static native void glEvalCoord2d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2);

   public static native void nglEvalCoord2dv(long var0);

   public static void glEvalCoord2dv(@NativeType("GLdouble const *") DoubleBuffer u) {
      if (Checks.CHECKS) {
         Checks.check(u, 2);
      }

      nglEvalCoord2dv(MemoryUtil.memAddress(u));
   }

   public static native void glEvalMesh1(@NativeType("GLenum") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glEvalMesh2(
      @NativeType("GLenum") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4
   );

   public static native void glEvalPoint1(@NativeType("GLint") int var0);

   public static native void glEvalPoint2(@NativeType("GLint") int var0, @NativeType("GLint") int var1);

   public static native void nglFeedbackBuffer(int var0, int var1, long var2);

   public static void glFeedbackBuffer(@NativeType("GLenum") int type, @NativeType("GLfloat *") FloatBuffer buffer) {
      nglFeedbackBuffer(buffer.remaining(), type, MemoryUtil.memAddress(buffer));
   }

   public static void glFinish() {
      GL11C.glFinish();
   }

   public static void glFlush() {
      GL11C.glFlush();
   }

   public static native void glFogi(@NativeType("GLenum") int var0, @NativeType("GLint") int var1);

   public static native void nglFogiv(int var0, long var1);

   public static void glFogiv(@NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglFogiv(pname, MemoryUtil.memAddress(params));
   }

   public static native void glFogf(@NativeType("GLenum") int var0, @NativeType("GLfloat") float var1);

   public static native void nglFogfv(int var0, long var1);

   public static void glFogfv(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglFogfv(pname, MemoryUtil.memAddress(params));
   }

   public static void glFrontFace(@NativeType("GLenum") int dir) {
      GL11C.glFrontFace(dir);
   }

   @NativeType("GLuint")
   public static native int glGenLists(@NativeType("GLsizei") int var0);

   public static void nglGenTextures(int n, long textures) {
      GL11C.nglGenTextures(n, textures);
   }

   public static void glGenTextures(@NativeType("GLuint *") IntBuffer textures) {
      GL11C.glGenTextures(textures);
   }

   @NativeType("void")
   public static int glGenTextures() {
      return GL11C.glGenTextures();
   }

   public static void nglDeleteTextures(int n, long textures) {
      GL11C.nglDeleteTextures(n, textures);
   }

   public static void glDeleteTextures(@NativeType("GLuint const *") IntBuffer textures) {
      GL11C.glDeleteTextures(textures);
   }

   public static void glDeleteTextures(@NativeType("GLuint const *") int texture) {
      GL11C.glDeleteTextures(texture);
   }

   public static native void nglGetClipPlane(int var0, long var1);

   public static void glGetClipPlane(@NativeType("GLenum") int plane, @NativeType("GLdouble *") DoubleBuffer equation) {
      if (Checks.CHECKS) {
         Checks.check(equation, 4);
      }

      nglGetClipPlane(plane, MemoryUtil.memAddress(equation));
   }

   public static void nglGetBooleanv(int pname, long params) {
      GL11C.nglGetBooleanv(pname, params);
   }

   public static void glGetBooleanv(@NativeType("GLenum") int pname, @NativeType("GLboolean *") ByteBuffer params) {
      GL11C.glGetBooleanv(pname, params);
   }

   @NativeType("void")
   public static boolean glGetBoolean(@NativeType("GLenum") int pname) {
      return GL11C.glGetBoolean(pname);
   }

   public static void nglGetFloatv(int pname, long params) {
      GL11C.nglGetFloatv(pname, params);
   }

   public static void glGetFloatv(@NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
      GL11C.glGetFloatv(pname, params);
   }

   @NativeType("void")
   public static float glGetFloat(@NativeType("GLenum") int pname) {
      return GL11C.glGetFloat(pname);
   }

   public static void nglGetIntegerv(int pname, long params) {
      GL11C.nglGetIntegerv(pname, params);
   }

   public static void glGetIntegerv(@NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      GL11C.glGetIntegerv(pname, params);
   }

   @NativeType("void")
   public static int glGetInteger(@NativeType("GLenum") int pname) {
      return GL11C.glGetInteger(pname);
   }

   public static void nglGetDoublev(int pname, long params) {
      GL11C.nglGetDoublev(pname, params);
   }

   public static void glGetDoublev(@NativeType("GLenum") int pname, @NativeType("GLdouble *") DoubleBuffer params) {
      GL11C.glGetDoublev(pname, params);
   }

   @NativeType("void")
   public static double glGetDouble(@NativeType("GLenum") int pname) {
      return GL11C.glGetDouble(pname);
   }

   @NativeType("GLenum")
   public static int glGetError() {
      return GL11C.glGetError();
   }

   public static native void nglGetLightiv(int var0, int var1, long var2);

   public static void glGetLightiv(@NativeType("GLenum") int light, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 4);
      }

      nglGetLightiv(light, pname, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static int glGetLighti(@NativeType("GLenum") int light, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer data = stack.callocInt(1);
         nglGetLightiv(light, pname, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetLightfv(int var0, int var1, long var2);

   public static void glGetLightfv(@NativeType("GLenum") int light, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 4);
      }

      nglGetLightfv(light, pname, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static float glGetLightf(@NativeType("GLenum") int light, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer data = stack.callocFloat(1);
         nglGetLightfv(light, pname, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetMapiv(int var0, int var1, long var2);

   public static void glGetMapiv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLint *") IntBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 4);
      }

      nglGetMapiv(target, query, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static int glGetMapi(@NativeType("GLenum") int target, @NativeType("GLenum") int query) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer data = stack.callocInt(1);
         nglGetMapiv(target, query, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetMapfv(int var0, int var1, long var2);

   public static void glGetMapfv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLfloat *") FloatBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 4);
      }

      nglGetMapfv(target, query, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static float glGetMapf(@NativeType("GLenum") int target, @NativeType("GLenum") int query) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer data = stack.callocFloat(1);
         nglGetMapfv(target, query, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetMapdv(int var0, int var1, long var2);

   public static void glGetMapdv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLdouble *") DoubleBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 4);
      }

      nglGetMapdv(target, query, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static double glGetMapd(@NativeType("GLenum") int target, @NativeType("GLenum") int query) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         DoubleBuffer data = stack.callocDouble(1);
         nglGetMapdv(target, query, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetMaterialiv(int var0, int var1, long var2);

   public static void glGetMaterialiv(@NativeType("GLenum") int face, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 1);
      }

      nglGetMaterialiv(face, pname, MemoryUtil.memAddress(data));
   }

   public static native void nglGetMaterialfv(int var0, int var1, long var2);

   public static void glGetMaterialfv(@NativeType("GLenum") int face, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 1);
      }

      nglGetMaterialfv(face, pname, MemoryUtil.memAddress(data));
   }

   public static native void nglGetPixelMapfv(int var0, long var1);

   public static void glGetPixelMapfv(@NativeType("GLenum") int map, @NativeType("GLfloat *") FloatBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 32);
      }

      nglGetPixelMapfv(map, MemoryUtil.memAddress(data));
   }

   public static void glGetPixelMapfv(@NativeType("GLenum") int map, @NativeType("GLfloat *") long data) {
      nglGetPixelMapfv(map, data);
   }

   public static native void nglGetPixelMapusv(int var0, long var1);

   public static void glGetPixelMapusv(@NativeType("GLenum") int map, @NativeType("GLushort *") ShortBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 32);
      }

      nglGetPixelMapusv(map, MemoryUtil.memAddress(data));
   }

   public static void glGetPixelMapusv(@NativeType("GLenum") int map, @NativeType("GLushort *") long data) {
      nglGetPixelMapusv(map, data);
   }

   public static native void nglGetPixelMapuiv(int var0, long var1);

   public static void glGetPixelMapuiv(@NativeType("GLenum") int map, @NativeType("GLuint *") IntBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 32);
      }

      nglGetPixelMapuiv(map, MemoryUtil.memAddress(data));
   }

   public static void glGetPixelMapuiv(@NativeType("GLenum") int map, @NativeType("GLuint *") long data) {
      nglGetPixelMapuiv(map, data);
   }

   public static void nglGetPointerv(int pname, long params) {
      GL11C.nglGetPointerv(pname, params);
   }

   public static void glGetPointerv(@NativeType("GLenum") int pname, @NativeType("void **") PointerBuffer params) {
      GL11C.glGetPointerv(pname, params);
   }

   @NativeType("void")
   public static long glGetPointer(@NativeType("GLenum") int pname) {
      return GL11C.glGetPointer(pname);
   }

   public static native void nglGetPolygonStipple(long var0);

   public static void glGetPolygonStipple(@NativeType("void *") ByteBuffer pattern) {
      if (Checks.CHECKS) {
         Checks.check(pattern, 128);
      }

      nglGetPolygonStipple(MemoryUtil.memAddress(pattern));
   }

   public static void glGetPolygonStipple(@NativeType("void *") long pattern) {
      nglGetPolygonStipple(pattern);
   }

   public static long nglGetString(int name) {
      return GL11C.nglGetString(name);
   }

   @Nullable
   @NativeType("GLubyte const *")
   public static String glGetString(@NativeType("GLenum") int name) {
      return GL11C.glGetString(name);
   }

   public static native void nglGetTexEnviv(int var0, int var1, long var2);

   public static void glGetTexEnviv(@NativeType("GLenum") int env, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 1);
      }

      nglGetTexEnviv(env, pname, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static int glGetTexEnvi(@NativeType("GLenum") int env, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer data = stack.callocInt(1);
         nglGetTexEnviv(env, pname, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTexEnvfv(int var0, int var1, long var2);

   public static void glGetTexEnvfv(@NativeType("GLenum") int env, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 1);
      }

      nglGetTexEnvfv(env, pname, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static float glGetTexEnvf(@NativeType("GLenum") int env, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer data = stack.callocFloat(1);
         nglGetTexEnvfv(env, pname, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTexGeniv(int var0, int var1, long var2);

   public static void glGetTexGeniv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 1);
      }

      nglGetTexGeniv(coord, pname, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static int glGetTexGeni(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer data = stack.callocInt(1);
         nglGetTexGeniv(coord, pname, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTexGenfv(int var0, int var1, long var2);

   public static void glGetTexGenfv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 4);
      }

      nglGetTexGenfv(coord, pname, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static float glGetTexGenf(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer data = stack.callocFloat(1);
         nglGetTexGenfv(coord, pname, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTexGendv(int var0, int var1, long var2);

   public static void glGetTexGendv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLdouble *") DoubleBuffer data) {
      if (Checks.CHECKS) {
         Checks.check(data, 4);
      }

      nglGetTexGendv(coord, pname, MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static double glGetTexGend(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         DoubleBuffer data = stack.callocDouble(1);
         nglGetTexGendv(coord, pname, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void nglGetTexImage(int tex, int level, int format, int type, long pixels) {
      GL11C.nglGetTexImage(tex, level, format, type, pixels);
   }

   public static void glGetTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ByteBuffer pixels
   ) {
      GL11C.glGetTexImage(tex, level, format, type, pixels);
   }

   public static void glGetTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") long pixels
   ) {
      GL11C.glGetTexImage(tex, level, format, type, pixels);
   }

   public static void glGetTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ShortBuffer pixels
   ) {
      GL11C.glGetTexImage(tex, level, format, type, pixels);
   }

   public static void glGetTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") IntBuffer pixels
   ) {
      GL11C.glGetTexImage(tex, level, format, type, pixels);
   }

   public static void glGetTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") FloatBuffer pixels
   ) {
      GL11C.glGetTexImage(tex, level, format, type, pixels);
   }

   public static void glGetTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") DoubleBuffer pixels
   ) {
      GL11C.glGetTexImage(tex, level, format, type, pixels);
   }

   public static void nglGetTexLevelParameteriv(int target, int level, int pname, long params) {
      GL11C.nglGetTexLevelParameteriv(target, level, pname, params);
   }

   public static void glGetTexLevelParameteriv(
      @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      GL11C.glGetTexLevelParameteriv(target, level, pname, params);
   }

   @NativeType("void")
   public static int glGetTexLevelParameteri(@NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname) {
      return GL11C.glGetTexLevelParameteri(target, level, pname);
   }

   public static void nglGetTexLevelParameterfv(int target, int level, int pname, long params) {
      GL11C.nglGetTexLevelParameterfv(target, level, pname, params);
   }

   public static void glGetTexLevelParameterfv(
      @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params
   ) {
      GL11C.glGetTexLevelParameterfv(target, level, pname, params);
   }

   @NativeType("void")
   public static float glGetTexLevelParameterf(@NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname) {
      return GL11C.glGetTexLevelParameterf(target, level, pname);
   }

   public static void nglGetTexParameteriv(int target, int pname, long params) {
      GL11C.nglGetTexParameteriv(target, pname, params);
   }

   public static void glGetTexParameteriv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      GL11C.glGetTexParameteriv(target, pname, params);
   }

   @NativeType("void")
   public static int glGetTexParameteri(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      return GL11C.glGetTexParameteri(target, pname);
   }

   public static void nglGetTexParameterfv(int target, int pname, long params) {
      GL11C.nglGetTexParameterfv(target, pname, params);
   }

   public static void glGetTexParameterfv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
      GL11C.glGetTexParameterfv(target, pname, params);
   }

   @NativeType("void")
   public static float glGetTexParameterf(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      return GL11C.glGetTexParameterf(target, pname);
   }

   public static void glHint(@NativeType("GLenum") int target, @NativeType("GLenum") int hint) {
      GL11C.glHint(target, hint);
   }

   public static native void glIndexi(@NativeType("GLint") int var0);

   public static native void glIndexub(@NativeType("GLubyte") byte var0);

   public static native void glIndexs(@NativeType("GLshort") short var0);

   public static native void glIndexf(@NativeType("GLfloat") float var0);

   public static native void glIndexd(@NativeType("GLdouble") double var0);

   public static native void nglIndexiv(long var0);

   public static void glIndexiv(@NativeType("GLint const *") IntBuffer index) {
      if (Checks.CHECKS) {
         Checks.check(index, 1);
      }

      nglIndexiv(MemoryUtil.memAddress(index));
   }

   public static native void nglIndexubv(long var0);

   public static void glIndexubv(@NativeType("GLubyte const *") ByteBuffer index) {
      if (Checks.CHECKS) {
         Checks.check(index, 1);
      }

      nglIndexubv(MemoryUtil.memAddress(index));
   }

   public static native void nglIndexsv(long var0);

   public static void glIndexsv(@NativeType("GLshort const *") ShortBuffer index) {
      if (Checks.CHECKS) {
         Checks.check(index, 1);
      }

      nglIndexsv(MemoryUtil.memAddress(index));
   }

   public static native void nglIndexfv(long var0);

   public static void glIndexfv(@NativeType("GLfloat const *") FloatBuffer index) {
      if (Checks.CHECKS) {
         Checks.check(index, 1);
      }

      nglIndexfv(MemoryUtil.memAddress(index));
   }

   public static native void nglIndexdv(long var0);

   public static void glIndexdv(@NativeType("GLdouble const *") DoubleBuffer index) {
      if (Checks.CHECKS) {
         Checks.check(index, 1);
      }

      nglIndexdv(MemoryUtil.memAddress(index));
   }

   public static native void glIndexMask(@NativeType("GLuint") int var0);

   public static native void nglIndexPointer(int var0, int var1, long var2);

   public static void glIndexPointer(@NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ByteBuffer pointer) {
      nglIndexPointer(type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glIndexPointer(@NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") long pointer) {
      nglIndexPointer(type, stride, pointer);
   }

   public static void glIndexPointer(@NativeType("GLsizei") int stride, @NativeType("void const *") ByteBuffer pointer) {
      nglIndexPointer(5121, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glIndexPointer(@NativeType("GLsizei") int stride, @NativeType("void const *") ShortBuffer pointer) {
      nglIndexPointer(5122, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glIndexPointer(@NativeType("GLsizei") int stride, @NativeType("void const *") IntBuffer pointer) {
      nglIndexPointer(5124, stride, MemoryUtil.memAddress(pointer));
   }

   public static native void glInitNames();

   public static native void nglInterleavedArrays(int var0, int var1, long var2);

   public static void glInterleavedArrays(@NativeType("GLenum") int format, @NativeType("GLsizei") int stride, @NativeType("void const *") ByteBuffer pointer) {
      nglInterleavedArrays(format, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glInterleavedArrays(@NativeType("GLenum") int format, @NativeType("GLsizei") int stride, @NativeType("void const *") long pointer) {
      nglInterleavedArrays(format, stride, pointer);
   }

   public static void glInterleavedArrays(@NativeType("GLenum") int format, @NativeType("GLsizei") int stride, @NativeType("void const *") ShortBuffer pointer) {
      nglInterleavedArrays(format, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glInterleavedArrays(@NativeType("GLenum") int format, @NativeType("GLsizei") int stride, @NativeType("void const *") IntBuffer pointer) {
      nglInterleavedArrays(format, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glInterleavedArrays(@NativeType("GLenum") int format, @NativeType("GLsizei") int stride, @NativeType("void const *") FloatBuffer pointer) {
      nglInterleavedArrays(format, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glInterleavedArrays(@NativeType("GLenum") int format, @NativeType("GLsizei") int stride, @NativeType("void const *") DoubleBuffer pointer) {
      nglInterleavedArrays(format, stride, MemoryUtil.memAddress(pointer));
   }

   @NativeType("GLboolean")
   public static boolean glIsEnabled(@NativeType("GLenum") int cap) {
      return GL11C.glIsEnabled(cap);
   }

   @NativeType("GLboolean")
   public static native boolean glIsList(@NativeType("GLuint") int var0);

   @NativeType("GLboolean")
   public static boolean glIsTexture(@NativeType("GLuint") int texture) {
      return GL11C.glIsTexture(texture);
   }

   public static native void glLightModeli(@NativeType("GLenum") int var0, @NativeType("GLint") int var1);

   public static native void glLightModelf(@NativeType("GLenum") int var0, @NativeType("GLfloat") float var1);

   public static native void nglLightModeliv(int var0, long var1);

   public static void glLightModeliv(@NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglLightModeliv(pname, MemoryUtil.memAddress(params));
   }

   public static native void nglLightModelfv(int var0, long var1);

   public static void glLightModelfv(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglLightModelfv(pname, MemoryUtil.memAddress(params));
   }

   public static native void glLighti(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLint") int var2);

   public static native void glLightf(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLfloat") float var2);

   public static native void nglLightiv(int var0, int var1, long var2);

   public static void glLightiv(@NativeType("GLenum") int light, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglLightiv(light, pname, MemoryUtil.memAddress(params));
   }

   public static native void nglLightfv(int var0, int var1, long var2);

   public static void glLightfv(@NativeType("GLenum") int light, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglLightfv(light, pname, MemoryUtil.memAddress(params));
   }

   public static native void glLineStipple(@NativeType("GLint") int var0, @NativeType("GLushort") short var1);

   public static void glLineWidth(@NativeType("GLfloat") float width) {
      GL11C.glLineWidth(width);
   }

   public static native void glListBase(@NativeType("GLuint") int var0);

   public static native void nglLoadMatrixf(long var0);

   public static void glLoadMatrixf(@NativeType("GLfloat const *") FloatBuffer m) {
      if (Checks.CHECKS) {
         Checks.check(m, 16);
      }

      nglLoadMatrixf(MemoryUtil.memAddress(m));
   }

   public static native void nglLoadMatrixd(long var0);

   public static void glLoadMatrixd(@NativeType("GLdouble const *") DoubleBuffer m) {
      if (Checks.CHECKS) {
         Checks.check(m, 16);
      }

      nglLoadMatrixd(MemoryUtil.memAddress(m));
   }

   public static native void glLoadIdentity();

   public static native void glLoadName(@NativeType("GLuint") int var0);

   public static void glLogicOp(@NativeType("GLenum") int op) {
      GL11C.glLogicOp(op);
   }

   public static native void nglMap1f(int var0, float var1, float var2, int var3, int var4, long var5);

   public static void glMap1f(
      @NativeType("GLenum") int target,
      @NativeType("GLfloat") float u1,
      @NativeType("GLfloat") float u2,
      @NativeType("GLint") int stride,
      @NativeType("GLint") int order,
      @NativeType("GLfloat const *") FloatBuffer points
   ) {
      if (Checks.CHECKS) {
         Checks.check(points, order * stride);
      }

      nglMap1f(target, u1, u2, stride, order, MemoryUtil.memAddress(points));
   }

   public static native void nglMap1d(int var0, double var1, double var3, int var5, int var6, long var7);

   public static void glMap1d(
      @NativeType("GLenum") int target,
      @NativeType("GLdouble") double u1,
      @NativeType("GLdouble") double u2,
      @NativeType("GLint") int stride,
      @NativeType("GLint") int order,
      @NativeType("GLdouble const *") DoubleBuffer points
   ) {
      if (Checks.CHECKS) {
         Checks.check(points, stride * order);
      }

      nglMap1d(target, u1, u2, stride, order, MemoryUtil.memAddress(points));
   }

   public static native void nglMap2f(int var0, float var1, float var2, int var3, int var4, float var5, float var6, int var7, int var8, long var9);

   public static void glMap2f(
      @NativeType("GLenum") int target,
      @NativeType("GLfloat") float u1,
      @NativeType("GLfloat") float u2,
      @NativeType("GLint") int ustride,
      @NativeType("GLint") int uorder,
      @NativeType("GLfloat") float v1,
      @NativeType("GLfloat") float v2,
      @NativeType("GLint") int vstride,
      @NativeType("GLint") int vorder,
      @NativeType("GLfloat const *") FloatBuffer points
   ) {
      if (Checks.CHECKS) {
         Checks.check(points, ustride * uorder * vstride * vorder);
      }

      nglMap2f(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, MemoryUtil.memAddress(points));
   }

   public static native void nglMap2d(int var0, double var1, double var3, int var5, int var6, double var7, double var9, int var11, int var12, long var13);

   public static void glMap2d(
      @NativeType("GLenum") int target,
      @NativeType("GLdouble") double u1,
      @NativeType("GLdouble") double u2,
      @NativeType("GLint") int ustride,
      @NativeType("GLint") int uorder,
      @NativeType("GLdouble") double v1,
      @NativeType("GLdouble") double v2,
      @NativeType("GLint") int vstride,
      @NativeType("GLint") int vorder,
      @NativeType("GLdouble const *") DoubleBuffer points
   ) {
      if (Checks.CHECKS) {
         Checks.check(points, ustride * uorder * vstride * vorder);
      }

      nglMap2d(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, MemoryUtil.memAddress(points));
   }

   public static native void glMapGrid1f(@NativeType("GLint") int var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glMapGrid1d(@NativeType("GLint") int var0, @NativeType("GLdouble") double var1, @NativeType("GLdouble") double var3);

   public static native void glMapGrid2f(
      @NativeType("GLint") int var0,
      @NativeType("GLfloat") float var1,
      @NativeType("GLfloat") float var2,
      @NativeType("GLint") int var3,
      @NativeType("GLfloat") float var4,
      @NativeType("GLfloat") float var5
   );

   public static native void glMapGrid2d(
      @NativeType("GLint") int var0,
      @NativeType("GLdouble") double var1,
      @NativeType("GLdouble") double var3,
      @NativeType("GLint") int var5,
      @NativeType("GLdouble") double var6,
      @NativeType("GLdouble") double var8
   );

   public static native void glMateriali(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLint") int var2);

   public static native void glMaterialf(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLfloat") float var2);

   public static native void nglMaterialiv(int var0, int var1, long var2);

   public static void glMaterialiv(@NativeType("GLenum") int face, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglMaterialiv(face, pname, MemoryUtil.memAddress(params));
   }

   public static native void nglMaterialfv(int var0, int var1, long var2);

   public static void glMaterialfv(@NativeType("GLenum") int face, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglMaterialfv(face, pname, MemoryUtil.memAddress(params));
   }

   public static native void glMatrixMode(@NativeType("GLenum") int var0);

   public static native void nglMultMatrixf(long var0);

   public static void glMultMatrixf(@NativeType("GLfloat const *") FloatBuffer m) {
      if (Checks.CHECKS) {
         Checks.check(m, 16);
      }

      nglMultMatrixf(MemoryUtil.memAddress(m));
   }

   public static native void nglMultMatrixd(long var0);

   public static void glMultMatrixd(@NativeType("GLdouble const *") DoubleBuffer m) {
      if (Checks.CHECKS) {
         Checks.check(m, 16);
      }

      nglMultMatrixd(MemoryUtil.memAddress(m));
   }

   public static native void glFrustum(
      @NativeType("GLdouble") double var0,
      @NativeType("GLdouble") double var2,
      @NativeType("GLdouble") double var4,
      @NativeType("GLdouble") double var6,
      @NativeType("GLdouble") double var8,
      @NativeType("GLdouble") double var10
   );

   public static native void glNewList(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static native void glEndList();

   public static native void glNormal3f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glNormal3b(@NativeType("GLbyte") byte var0, @NativeType("GLbyte") byte var1, @NativeType("GLbyte") byte var2);

   public static native void glNormal3s(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2);

   public static native void glNormal3i(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glNormal3d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4);

   public static native void nglNormal3fv(long var0);

   public static void glNormal3fv(@NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglNormal3fv(MemoryUtil.memAddress(v));
   }

   public static native void nglNormal3bv(long var0);

   public static void glNormal3bv(@NativeType("GLbyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglNormal3bv(MemoryUtil.memAddress(v));
   }

   public static native void nglNormal3sv(long var0);

   public static void glNormal3sv(@NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglNormal3sv(MemoryUtil.memAddress(v));
   }

   public static native void nglNormal3iv(long var0);

   public static void glNormal3iv(@NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglNormal3iv(MemoryUtil.memAddress(v));
   }

   public static native void nglNormal3dv(long var0);

   public static void glNormal3dv(@NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglNormal3dv(MemoryUtil.memAddress(v));
   }

   public static native void nglNormalPointer(int var0, int var1, long var2);

   public static void glNormalPointer(@NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ByteBuffer pointer) {
      nglNormalPointer(type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glNormalPointer(@NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") long pointer) {
      nglNormalPointer(type, stride, pointer);
   }

   public static void glNormalPointer(@NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ShortBuffer pointer) {
      nglNormalPointer(type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glNormalPointer(@NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") IntBuffer pointer) {
      nglNormalPointer(type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glNormalPointer(@NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") FloatBuffer pointer) {
      nglNormalPointer(type, stride, MemoryUtil.memAddress(pointer));
   }

   public static native void glOrtho(
      @NativeType("GLdouble") double var0,
      @NativeType("GLdouble") double var2,
      @NativeType("GLdouble") double var4,
      @NativeType("GLdouble") double var6,
      @NativeType("GLdouble") double var8,
      @NativeType("GLdouble") double var10
   );

   public static native void glPassThrough(@NativeType("GLfloat") float var0);

   public static native void nglPixelMapfv(int var0, int var1, long var2);

   public static void glPixelMapfv(@NativeType("GLenum") int map, @NativeType("GLsizei") int size, @NativeType("GLfloat const *") long values) {
      nglPixelMapfv(map, size, values);
   }

   public static void glPixelMapfv(@NativeType("GLenum") int map, @NativeType("GLfloat const *") FloatBuffer values) {
      nglPixelMapfv(map, values.remaining(), MemoryUtil.memAddress(values));
   }

   public static native void nglPixelMapusv(int var0, int var1, long var2);

   public static void glPixelMapusv(@NativeType("GLenum") int map, @NativeType("GLsizei") int size, @NativeType("GLushort const *") long values) {
      nglPixelMapusv(map, size, values);
   }

   public static void glPixelMapusv(@NativeType("GLenum") int map, @NativeType("GLushort const *") ShortBuffer values) {
      nglPixelMapusv(map, values.remaining(), MemoryUtil.memAddress(values));
   }

   public static native void nglPixelMapuiv(int var0, int var1, long var2);

   public static void glPixelMapuiv(@NativeType("GLenum") int map, @NativeType("GLsizei") int size, @NativeType("GLuint const *") long values) {
      nglPixelMapuiv(map, size, values);
   }

   public static void glPixelMapuiv(@NativeType("GLenum") int map, @NativeType("GLuint const *") IntBuffer values) {
      nglPixelMapuiv(map, values.remaining(), MemoryUtil.memAddress(values));
   }

   public static void glPixelStorei(@NativeType("GLenum") int pname, @NativeType("GLint") int param) {
      GL11C.glPixelStorei(pname, param);
   }

   public static void glPixelStoref(@NativeType("GLenum") int pname, @NativeType("GLfloat") float param) {
      GL11C.glPixelStoref(pname, param);
   }

   public static native void glPixelTransferi(@NativeType("GLenum") int var0, @NativeType("GLint") int var1);

   public static native void glPixelTransferf(@NativeType("GLenum") int var0, @NativeType("GLfloat") float var1);

   public static native void glPixelZoom(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1);

   public static void glPointSize(@NativeType("GLfloat") float size) {
      GL11C.glPointSize(size);
   }

   public static void glPolygonMode(@NativeType("GLenum") int face, @NativeType("GLenum") int mode) {
      GL11C.glPolygonMode(face, mode);
   }

   public static void glPolygonOffset(@NativeType("GLfloat") float factor, @NativeType("GLfloat") float units) {
      GL11C.glPolygonOffset(factor, units);
   }

   public static native void nglPolygonStipple(long var0);

   public static void glPolygonStipple(@NativeType("GLubyte const *") ByteBuffer pattern) {
      if (Checks.CHECKS) {
         Checks.check(pattern, 128);
      }

      nglPolygonStipple(MemoryUtil.memAddress(pattern));
   }

   public static void glPolygonStipple(@NativeType("GLubyte const *") long pattern) {
      nglPolygonStipple(pattern);
   }

   public static native void glPushAttrib(@NativeType("GLbitfield") int var0);

   public static native void glPushClientAttrib(@NativeType("GLbitfield") int var0);

   public static native void glPopAttrib();

   public static native void glPopClientAttrib();

   public static native void glPopMatrix();

   public static native void glPopName();

   public static native void nglPrioritizeTextures(int var0, long var1, long var3);

   public static void glPrioritizeTextures(@NativeType("GLuint const *") IntBuffer textures, @NativeType("GLfloat const *") FloatBuffer priorities) {
      if (Checks.CHECKS) {
         Checks.check(priorities, textures.remaining());
      }

      nglPrioritizeTextures(textures.remaining(), MemoryUtil.memAddress(textures), MemoryUtil.memAddress(priorities));
   }

   public static native void glPushMatrix();

   public static native void glPushName(@NativeType("GLuint") int var0);

   public static native void glRasterPos2i(@NativeType("GLint") int var0, @NativeType("GLint") int var1);

   public static native void glRasterPos2s(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1);

   public static native void glRasterPos2f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1);

   public static native void glRasterPos2d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2);

   public static native void nglRasterPos2iv(long var0);

   public static void glRasterPos2iv(@NativeType("GLint const *") IntBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 2);
      }

      nglRasterPos2iv(MemoryUtil.memAddress(coords));
   }

   public static native void nglRasterPos2sv(long var0);

   public static void glRasterPos2sv(@NativeType("GLshort const *") ShortBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 2);
      }

      nglRasterPos2sv(MemoryUtil.memAddress(coords));
   }

   public static native void nglRasterPos2fv(long var0);

   public static void glRasterPos2fv(@NativeType("GLfloat const *") FloatBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 2);
      }

      nglRasterPos2fv(MemoryUtil.memAddress(coords));
   }

   public static native void nglRasterPos2dv(long var0);

   public static void glRasterPos2dv(@NativeType("GLdouble const *") DoubleBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 2);
      }

      nglRasterPos2dv(MemoryUtil.memAddress(coords));
   }

   public static native void glRasterPos3i(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glRasterPos3s(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2);

   public static native void glRasterPos3f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glRasterPos3d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4);

   public static native void nglRasterPos3iv(long var0);

   public static void glRasterPos3iv(@NativeType("GLint const *") IntBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 3);
      }

      nglRasterPos3iv(MemoryUtil.memAddress(coords));
   }

   public static native void nglRasterPos3sv(long var0);

   public static void glRasterPos3sv(@NativeType("GLshort const *") ShortBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 3);
      }

      nglRasterPos3sv(MemoryUtil.memAddress(coords));
   }

   public static native void nglRasterPos3fv(long var0);

   public static void glRasterPos3fv(@NativeType("GLfloat const *") FloatBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 3);
      }

      nglRasterPos3fv(MemoryUtil.memAddress(coords));
   }

   public static native void nglRasterPos3dv(long var0);

   public static void glRasterPos3dv(@NativeType("GLdouble const *") DoubleBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 3);
      }

      nglRasterPos3dv(MemoryUtil.memAddress(coords));
   }

   public static native void glRasterPos4i(
      @NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2, @NativeType("GLint") int var3
   );

   public static native void glRasterPos4s(
      @NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2, @NativeType("GLshort") short var3
   );

   public static native void glRasterPos4f(
      @NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   public static native void glRasterPos4d(
      @NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4, @NativeType("GLdouble") double var6
   );

   public static native void nglRasterPos4iv(long var0);

   public static void glRasterPos4iv(@NativeType("GLint const *") IntBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 4);
      }

      nglRasterPos4iv(MemoryUtil.memAddress(coords));
   }

   public static native void nglRasterPos4sv(long var0);

   public static void glRasterPos4sv(@NativeType("GLshort const *") ShortBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 4);
      }

      nglRasterPos4sv(MemoryUtil.memAddress(coords));
   }

   public static native void nglRasterPos4fv(long var0);

   public static void glRasterPos4fv(@NativeType("GLfloat const *") FloatBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 4);
      }

      nglRasterPos4fv(MemoryUtil.memAddress(coords));
   }

   public static native void nglRasterPos4dv(long var0);

   public static void glRasterPos4dv(@NativeType("GLdouble const *") DoubleBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 4);
      }

      nglRasterPos4dv(MemoryUtil.memAddress(coords));
   }

   public static void glReadBuffer(@NativeType("GLenum") int src) {
      GL11C.glReadBuffer(src);
   }

   public static void nglReadPixels(int x, int y, int width, int height, int format, int type, long pixels) {
      GL11C.nglReadPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ByteBuffer pixels
   ) {
      GL11C.glReadPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") long pixels
   ) {
      GL11C.glReadPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ShortBuffer pixels
   ) {
      GL11C.glReadPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") IntBuffer pixels
   ) {
      GL11C.glReadPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") FloatBuffer pixels
   ) {
      GL11C.glReadPixels(x, y, width, height, format, type, pixels);
   }

   public static native void glRecti(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2, @NativeType("GLint") int var3);

   public static native void glRects(
      @NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2, @NativeType("GLshort") short var3
   );

   public static native void glRectf(
      @NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   public static native void glRectd(
      @NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4, @NativeType("GLdouble") double var6
   );

   public static native void nglRectiv(long var0, long var2);

   public static void glRectiv(@NativeType("GLint const *") IntBuffer v1, @NativeType("GLint const *") IntBuffer v2) {
      if (Checks.CHECKS) {
         Checks.check(v1, 2);
         Checks.check(v2, 2);
      }

      nglRectiv(MemoryUtil.memAddress(v1), MemoryUtil.memAddress(v2));
   }

   public static native void nglRectsv(long var0, long var2);

   public static void glRectsv(@NativeType("GLshort const *") ShortBuffer v1, @NativeType("GLshort const *") ShortBuffer v2) {
      if (Checks.CHECKS) {
         Checks.check(v1, 2);
         Checks.check(v2, 2);
      }

      nglRectsv(MemoryUtil.memAddress(v1), MemoryUtil.memAddress(v2));
   }

   public static native void nglRectfv(long var0, long var2);

   public static void glRectfv(@NativeType("GLfloat const *") FloatBuffer v1, @NativeType("GLfloat const *") FloatBuffer v2) {
      if (Checks.CHECKS) {
         Checks.check(v1, 2);
         Checks.check(v2, 2);
      }

      nglRectfv(MemoryUtil.memAddress(v1), MemoryUtil.memAddress(v2));
   }

   public static native void nglRectdv(long var0, long var2);

   public static void glRectdv(@NativeType("GLdouble const *") DoubleBuffer v1, @NativeType("GLdouble const *") DoubleBuffer v2) {
      if (Checks.CHECKS) {
         Checks.check(v1, 2);
         Checks.check(v2, 2);
      }

      nglRectdv(MemoryUtil.memAddress(v1), MemoryUtil.memAddress(v2));
   }

   @NativeType("GLint")
   public static native int glRenderMode(@NativeType("GLenum") int var0);

   public static native void glRotatef(
      @NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   public static native void glRotated(
      @NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4, @NativeType("GLdouble") double var6
   );

   public static native void glScalef(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glScaled(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4);

   public static void glScissor(@NativeType("GLint") int x, @NativeType("GLint") int y, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height) {
      GL11C.glScissor(x, y, width, height);
   }

   public static native void nglSelectBuffer(int var0, long var1);

   public static void glSelectBuffer(@NativeType("GLuint *") IntBuffer buffer) {
      nglSelectBuffer(buffer.remaining(), MemoryUtil.memAddress(buffer));
   }

   public static native void glShadeModel(@NativeType("GLenum") int var0);

   public static void glStencilFunc(@NativeType("GLenum") int func, @NativeType("GLint") int ref, @NativeType("GLuint") int mask) {
      GL11C.glStencilFunc(func, ref, mask);
   }

   public static void glStencilMask(@NativeType("GLuint") int mask) {
      GL11C.glStencilMask(mask);
   }

   public static void glStencilOp(@NativeType("GLenum") int sfail, @NativeType("GLenum") int dpfail, @NativeType("GLenum") int dppass) {
      GL11C.glStencilOp(sfail, dpfail, dppass);
   }

   public static native void glTexCoord1f(@NativeType("GLfloat") float var0);

   public static native void glTexCoord1s(@NativeType("GLshort") short var0);

   public static native void glTexCoord1i(@NativeType("GLint") int var0);

   public static native void glTexCoord1d(@NativeType("GLdouble") double var0);

   public static native void nglTexCoord1fv(long var0);

   public static void glTexCoord1fv(@NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglTexCoord1fv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord1sv(long var0);

   public static void glTexCoord1sv(@NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglTexCoord1sv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord1iv(long var0);

   public static void glTexCoord1iv(@NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglTexCoord1iv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord1dv(long var0);

   public static void glTexCoord1dv(@NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglTexCoord1dv(MemoryUtil.memAddress(v));
   }

   public static native void glTexCoord2f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1);

   public static native void glTexCoord2s(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1);

   public static native void glTexCoord2i(@NativeType("GLint") int var0, @NativeType("GLint") int var1);

   public static native void glTexCoord2d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2);

   public static native void nglTexCoord2fv(long var0);

   public static void glTexCoord2fv(@NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglTexCoord2fv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord2sv(long var0);

   public static void glTexCoord2sv(@NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglTexCoord2sv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord2iv(long var0);

   public static void glTexCoord2iv(@NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglTexCoord2iv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord2dv(long var0);

   public static void glTexCoord2dv(@NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglTexCoord2dv(MemoryUtil.memAddress(v));
   }

   public static native void glTexCoord3f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glTexCoord3s(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2);

   public static native void glTexCoord3i(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glTexCoord3d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4);

   public static native void nglTexCoord3fv(long var0);

   public static void glTexCoord3fv(@NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglTexCoord3fv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord3sv(long var0);

   public static void glTexCoord3sv(@NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglTexCoord3sv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord3iv(long var0);

   public static void glTexCoord3iv(@NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglTexCoord3iv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord3dv(long var0);

   public static void glTexCoord3dv(@NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglTexCoord3dv(MemoryUtil.memAddress(v));
   }

   public static native void glTexCoord4f(
      @NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   public static native void glTexCoord4s(
      @NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2, @NativeType("GLshort") short var3
   );

   public static native void glTexCoord4i(
      @NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2, @NativeType("GLint") int var3
   );

   public static native void glTexCoord4d(
      @NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4, @NativeType("GLdouble") double var6
   );

   public static native void nglTexCoord4fv(long var0);

   public static void glTexCoord4fv(@NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglTexCoord4fv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord4sv(long var0);

   public static void glTexCoord4sv(@NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglTexCoord4sv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord4iv(long var0);

   public static void glTexCoord4iv(@NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglTexCoord4iv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoord4dv(long var0);

   public static void glTexCoord4dv(@NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglTexCoord4dv(MemoryUtil.memAddress(v));
   }

   public static native void nglTexCoordPointer(int var0, int var1, int var2, long var3);

   public static void glTexCoordPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ByteBuffer pointer
   ) {
      nglTexCoordPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glTexCoordPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") long pointer
   ) {
      nglTexCoordPointer(size, type, stride, pointer);
   }

   public static void glTexCoordPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ShortBuffer pointer
   ) {
      nglTexCoordPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glTexCoordPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") IntBuffer pointer
   ) {
      nglTexCoordPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glTexCoordPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") FloatBuffer pointer
   ) {
      nglTexCoordPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static native void glTexEnvi(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLint") int var2);

   public static native void nglTexEnviv(int var0, int var1, long var2);

   public static void glTexEnviv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglTexEnviv(target, pname, MemoryUtil.memAddress(params));
   }

   public static native void glTexEnvf(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLfloat") float var2);

   public static native void nglTexEnvfv(int var0, int var1, long var2);

   public static void glTexEnvfv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglTexEnvfv(target, pname, MemoryUtil.memAddress(params));
   }

   public static native void glTexGeni(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLint") int var2);

   public static native void nglTexGeniv(int var0, int var1, long var2);

   public static void glTexGeniv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglTexGeniv(coord, pname, MemoryUtil.memAddress(params));
   }

   public static native void glTexGenf(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLfloat") float var2);

   public static native void nglTexGenfv(int var0, int var1, long var2);

   public static void glTexGenfv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglTexGenfv(coord, pname, MemoryUtil.memAddress(params));
   }

   public static native void glTexGend(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLdouble") double var2);

   public static native void nglTexGendv(int var0, int var1, long var2);

   public static void glTexGendv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLdouble const *") DoubleBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglTexGendv(coord, pname, MemoryUtil.memAddress(params));
   }

   public static void nglTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, long pixels) {
      GL11C.nglTexImage1D(target, level, internalformat, width, border, format, type, pixels);
   }

   public static void glTexImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ByteBuffer pixels
   ) {
      GL11C.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
   }

   public static void glTexImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") long pixels
   ) {
      GL11C.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
   }

   public static void glTexImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ShortBuffer pixels
   ) {
      GL11C.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
   }

   public static void glTexImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") IntBuffer pixels
   ) {
      GL11C.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
   }

   public static void glTexImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") FloatBuffer pixels
   ) {
      GL11C.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
   }

   public static void glTexImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") DoubleBuffer pixels
   ) {
      GL11C.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
   }

   public static void nglTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, long pixels) {
      GL11C.nglTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
   }

   public static void glTexImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ByteBuffer pixels
   ) {
      GL11C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
   }

   public static void glTexImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") long pixels
   ) {
      GL11C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
   }

   public static void glTexImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ShortBuffer pixels
   ) {
      GL11C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
   }

   public static void glTexImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") IntBuffer pixels
   ) {
      GL11C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
   }

   public static void glTexImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") FloatBuffer pixels
   ) {
      GL11C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
   }

   public static void glTexImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") DoubleBuffer pixels
   ) {
      GL11C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
   }

   public static void glCopyTexImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int internalFormat,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border
   ) {
      GL11C.glCopyTexImage1D(target, level, internalFormat, x, y, width, border);
   }

   public static void glCopyTexImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int internalFormat,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border
   ) {
      GL11C.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
   }

   public static void glCopyTexSubImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width
   ) {
      GL11C.glCopyTexSubImage1D(target, level, xoffset, x, y, width);
   }

   public static void glCopyTexSubImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height
   ) {
      GL11C.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
   }

   public static void glTexParameteri(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint") int param) {
      GL11C.glTexParameteri(target, pname, param);
   }

   public static void nglTexParameteriv(int target, int pname, long params) {
      GL11C.nglTexParameteriv(target, pname, params);
   }

   public static void glTexParameteriv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      GL11C.glTexParameteriv(target, pname, params);
   }

   public static void glTexParameterf(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat") float param) {
      GL11C.glTexParameterf(target, pname, param);
   }

   public static void nglTexParameterfv(int target, int pname, long params) {
      GL11C.nglTexParameterfv(target, pname, params);
   }

   public static void glTexParameterfv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
      GL11C.glTexParameterfv(target, pname, params);
   }

   public static void nglTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, long pixels) {
      GL11C.nglTexSubImage1D(target, level, xoffset, width, format, type, pixels);
   }

   public static void glTexSubImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer pixels
   ) {
      GL11C.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
   }

   public static void glTexSubImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long pixels
   ) {
      GL11C.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
   }

   public static void glTexSubImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ShortBuffer pixels
   ) {
      GL11C.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
   }

   public static void glTexSubImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") IntBuffer pixels
   ) {
      GL11C.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
   }

   public static void glTexSubImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") FloatBuffer pixels
   ) {
      GL11C.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
   }

   public static void glTexSubImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") DoubleBuffer pixels
   ) {
      GL11C.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
   }

   public static void nglTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, long pixels) {
      GL11C.nglTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTexSubImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer pixels
   ) {
      GL11C.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTexSubImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long pixels
   ) {
      GL11C.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTexSubImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ShortBuffer pixels
   ) {
      GL11C.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTexSubImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") IntBuffer pixels
   ) {
      GL11C.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTexSubImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") FloatBuffer pixels
   ) {
      GL11C.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTexSubImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") DoubleBuffer pixels
   ) {
      GL11C.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static native void glTranslatef(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glTranslated(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4);

   public static native void glVertex2f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1);

   public static native void glVertex2s(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1);

   public static native void glVertex2i(@NativeType("GLint") int var0, @NativeType("GLint") int var1);

   public static native void glVertex2d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2);

   public static native void nglVertex2fv(long var0);

   public static void glVertex2fv(@NativeType("GLfloat const *") FloatBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 2);
      }

      nglVertex2fv(MemoryUtil.memAddress(coords));
   }

   public static native void nglVertex2sv(long var0);

   public static void glVertex2sv(@NativeType("GLshort const *") ShortBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 2);
      }

      nglVertex2sv(MemoryUtil.memAddress(coords));
   }

   public static native void nglVertex2iv(long var0);

   public static void glVertex2iv(@NativeType("GLint const *") IntBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 2);
      }

      nglVertex2iv(MemoryUtil.memAddress(coords));
   }

   public static native void nglVertex2dv(long var0);

   public static void glVertex2dv(@NativeType("GLdouble const *") DoubleBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 2);
      }

      nglVertex2dv(MemoryUtil.memAddress(coords));
   }

   public static native void glVertex3f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glVertex3s(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2);

   public static native void glVertex3i(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glVertex3d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4);

   public static native void nglVertex3fv(long var0);

   public static void glVertex3fv(@NativeType("GLfloat const *") FloatBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 3);
      }

      nglVertex3fv(MemoryUtil.memAddress(coords));
   }

   public static native void nglVertex3sv(long var0);

   public static void glVertex3sv(@NativeType("GLshort const *") ShortBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 3);
      }

      nglVertex3sv(MemoryUtil.memAddress(coords));
   }

   public static native void nglVertex3iv(long var0);

   public static void glVertex3iv(@NativeType("GLint const *") IntBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 3);
      }

      nglVertex3iv(MemoryUtil.memAddress(coords));
   }

   public static native void nglVertex3dv(long var0);

   public static void glVertex3dv(@NativeType("GLdouble const *") DoubleBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 3);
      }

      nglVertex3dv(MemoryUtil.memAddress(coords));
   }

   public static native void glVertex4f(
      @NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   public static native void glVertex4s(
      @NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2, @NativeType("GLshort") short var3
   );

   public static native void glVertex4i(
      @NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2, @NativeType("GLint") int var3
   );

   public static native void glVertex4d(
      @NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4, @NativeType("GLdouble") double var6
   );

   public static native void nglVertex4fv(long var0);

   public static void glVertex4fv(@NativeType("GLfloat const *") FloatBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 4);
      }

      nglVertex4fv(MemoryUtil.memAddress(coords));
   }

   public static native void nglVertex4sv(long var0);

   public static void glVertex4sv(@NativeType("GLshort const *") ShortBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 4);
      }

      nglVertex4sv(MemoryUtil.memAddress(coords));
   }

   public static native void nglVertex4iv(long var0);

   public static void glVertex4iv(@NativeType("GLint const *") IntBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 4);
      }

      nglVertex4iv(MemoryUtil.memAddress(coords));
   }

   public static native void nglVertex4dv(long var0);

   public static void glVertex4dv(@NativeType("GLdouble const *") DoubleBuffer coords) {
      if (Checks.CHECKS) {
         Checks.check(coords, 4);
      }

      nglVertex4dv(MemoryUtil.memAddress(coords));
   }

   public static native void nglVertexPointer(int var0, int var1, int var2, long var3);

   public static void glVertexPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ByteBuffer pointer
   ) {
      nglVertexPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") long pointer
   ) {
      nglVertexPointer(size, type, stride, pointer);
   }

   public static void glVertexPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ShortBuffer pointer
   ) {
      nglVertexPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") IntBuffer pointer
   ) {
      nglVertexPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") FloatBuffer pointer
   ) {
      nglVertexPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glViewport(@NativeType("GLint") int x, @NativeType("GLint") int y, @NativeType("GLsizei") int w, @NativeType("GLsizei") int h) {
      GL11C.glViewport(x, y, w, h);
   }

   @NativeType("GLboolean")
   public static boolean glAreTexturesResident(@NativeType("GLuint const *") int[] textures, @NativeType("GLboolean *") ByteBuffer residences) {
      long __functionAddress = GL.getICD().glAreTexturesResident;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(residences, textures.length);
      }

      return JNI.callPPZ(textures.length, textures, MemoryUtil.memAddress(residences), __functionAddress);
   }

   public static void glClipPlane(@NativeType("GLenum") int plane, @NativeType("GLdouble const *") double[] equation) {
      long __functionAddress = GL.getICD().glClipPlane;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(equation, 4);
      }

      JNI.callPV(plane, equation, __functionAddress);
   }

   public static void glColor3sv(@NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glColor3sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor3iv(@NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glColor3iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor3fv(@NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glColor3fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor3dv(@NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glColor3dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor3usv(@NativeType("GLushort const *") short[] v) {
      long __functionAddress = GL.getICD().glColor3usv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor3uiv(@NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glColor3uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor4sv(@NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glColor4sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor4iv(@NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glColor4iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor4fv(@NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glColor4fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor4dv(@NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glColor4dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor4usv(@NativeType("GLushort const *") short[] v) {
      long __functionAddress = GL.getICD().glColor4usv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor4uiv(@NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glColor4uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glDrawPixels(
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") short[] pixels
   ) {
      long __functionAddress = GL.getICD().glDrawPixels;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(width, height, format, type, pixels, __functionAddress);
   }

   public static void glDrawPixels(
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") int[] pixels
   ) {
      long __functionAddress = GL.getICD().glDrawPixels;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(width, height, format, type, pixels, __functionAddress);
   }

   public static void glDrawPixels(
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") float[] pixels
   ) {
      long __functionAddress = GL.getICD().glDrawPixels;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(width, height, format, type, pixels, __functionAddress);
   }

   public static void glEvalCoord1fv(@NativeType("GLfloat const *") float[] u) {
      long __functionAddress = GL.getICD().glEvalCoord1fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(u, 1);
      }

      JNI.callPV(u, __functionAddress);
   }

   public static void glEvalCoord1dv(@NativeType("GLdouble const *") double[] u) {
      long __functionAddress = GL.getICD().glEvalCoord1dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(u, 1);
      }

      JNI.callPV(u, __functionAddress);
   }

   public static void glEvalCoord2fv(@NativeType("GLfloat const *") float[] u) {
      long __functionAddress = GL.getICD().glEvalCoord2fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(u, 2);
      }

      JNI.callPV(u, __functionAddress);
   }

   public static void glEvalCoord2dv(@NativeType("GLdouble const *") double[] u) {
      long __functionAddress = GL.getICD().glEvalCoord2dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(u, 2);
      }

      JNI.callPV(u, __functionAddress);
   }

   public static void glFeedbackBuffer(@NativeType("GLenum") int type, @NativeType("GLfloat *") float[] buffer) {
      long __functionAddress = GL.getICD().glFeedbackBuffer;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(buffer.length, type, buffer, __functionAddress);
   }

   public static void glFogiv(@NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glFogiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(pname, params, __functionAddress);
   }

   public static void glFogfv(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      long __functionAddress = GL.getICD().glFogfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(pname, params, __functionAddress);
   }

   public static void glGenTextures(@NativeType("GLuint *") int[] textures) {
      GL11C.glGenTextures(textures);
   }

   public static void glDeleteTextures(@NativeType("GLuint const *") int[] textures) {
      GL11C.glDeleteTextures(textures);
   }

   public static void glGetClipPlane(@NativeType("GLenum") int plane, @NativeType("GLdouble *") double[] equation) {
      long __functionAddress = GL.getICD().glGetClipPlane;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(equation, 4);
      }

      JNI.callPV(plane, equation, __functionAddress);
   }

   public static void glGetFloatv(@NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
      GL11C.glGetFloatv(pname, params);
   }

   public static void glGetIntegerv(@NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      GL11C.glGetIntegerv(pname, params);
   }

   public static void glGetDoublev(@NativeType("GLenum") int pname, @NativeType("GLdouble *") double[] params) {
      GL11C.glGetDoublev(pname, params);
   }

   public static void glGetLightiv(@NativeType("GLenum") int light, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] data) {
      long __functionAddress = GL.getICD().glGetLightiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 4);
      }

      JNI.callPV(light, pname, data, __functionAddress);
   }

   public static void glGetLightfv(@NativeType("GLenum") int light, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] data) {
      long __functionAddress = GL.getICD().glGetLightfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 4);
      }

      JNI.callPV(light, pname, data, __functionAddress);
   }

   public static void glGetMapiv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLint *") int[] data) {
      long __functionAddress = GL.getICD().glGetMapiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 4);
      }

      JNI.callPV(target, query, data, __functionAddress);
   }

   public static void glGetMapfv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLfloat *") float[] data) {
      long __functionAddress = GL.getICD().glGetMapfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 4);
      }

      JNI.callPV(target, query, data, __functionAddress);
   }

   public static void glGetMapdv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLdouble *") double[] data) {
      long __functionAddress = GL.getICD().glGetMapdv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 4);
      }

      JNI.callPV(target, query, data, __functionAddress);
   }

   public static void glGetMaterialiv(@NativeType("GLenum") int face, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] data) {
      long __functionAddress = GL.getICD().glGetMaterialiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 1);
      }

      JNI.callPV(face, pname, data, __functionAddress);
   }

   public static void glGetMaterialfv(@NativeType("GLenum") int face, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] data) {
      long __functionAddress = GL.getICD().glGetMaterialfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 1);
      }

      JNI.callPV(face, pname, data, __functionAddress);
   }

   public static void glGetPixelMapfv(@NativeType("GLenum") int map, @NativeType("GLfloat *") float[] data) {
      long __functionAddress = GL.getICD().glGetPixelMapfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 32);
      }

      JNI.callPV(map, data, __functionAddress);
   }

   public static void glGetPixelMapusv(@NativeType("GLenum") int map, @NativeType("GLushort *") short[] data) {
      long __functionAddress = GL.getICD().glGetPixelMapusv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 32);
      }

      JNI.callPV(map, data, __functionAddress);
   }

   public static void glGetPixelMapuiv(@NativeType("GLenum") int map, @NativeType("GLuint *") int[] data) {
      long __functionAddress = GL.getICD().glGetPixelMapuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 32);
      }

      JNI.callPV(map, data, __functionAddress);
   }

   public static void glGetTexEnviv(@NativeType("GLenum") int env, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] data) {
      long __functionAddress = GL.getICD().glGetTexEnviv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 1);
      }

      JNI.callPV(env, pname, data, __functionAddress);
   }

   public static void glGetTexEnvfv(@NativeType("GLenum") int env, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] data) {
      long __functionAddress = GL.getICD().glGetTexEnvfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 1);
      }

      JNI.callPV(env, pname, data, __functionAddress);
   }

   public static void glGetTexGeniv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] data) {
      long __functionAddress = GL.getICD().glGetTexGeniv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 1);
      }

      JNI.callPV(coord, pname, data, __functionAddress);
   }

   public static void glGetTexGenfv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] data) {
      long __functionAddress = GL.getICD().glGetTexGenfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 4);
      }

      JNI.callPV(coord, pname, data, __functionAddress);
   }

   public static void glGetTexGendv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLdouble *") double[] data) {
      long __functionAddress = GL.getICD().glGetTexGendv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(data, 4);
      }

      JNI.callPV(coord, pname, data, __functionAddress);
   }

   public static void glGetTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") short[] pixels
   ) {
      GL11C.glGetTexImage(tex, level, format, type, pixels);
   }

   public static void glGetTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") int[] pixels
   ) {
      GL11C.glGetTexImage(tex, level, format, type, pixels);
   }

   public static void glGetTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") float[] pixels
   ) {
      GL11C.glGetTexImage(tex, level, format, type, pixels);
   }

   public static void glGetTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") double[] pixels
   ) {
      GL11C.glGetTexImage(tex, level, format, type, pixels);
   }

   public static void glGetTexLevelParameteriv(
      @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      GL11C.glGetTexLevelParameteriv(target, level, pname, params);
   }

   public static void glGetTexLevelParameterfv(
      @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params
   ) {
      GL11C.glGetTexLevelParameterfv(target, level, pname, params);
   }

   public static void glGetTexParameteriv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      GL11C.glGetTexParameteriv(target, pname, params);
   }

   public static void glGetTexParameterfv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
      GL11C.glGetTexParameterfv(target, pname, params);
   }

   public static void glIndexiv(@NativeType("GLint const *") int[] index) {
      long __functionAddress = GL.getICD().glIndexiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(index, 1);
      }

      JNI.callPV(index, __functionAddress);
   }

   public static void glIndexsv(@NativeType("GLshort const *") short[] index) {
      long __functionAddress = GL.getICD().glIndexsv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(index, 1);
      }

      JNI.callPV(index, __functionAddress);
   }

   public static void glIndexfv(@NativeType("GLfloat const *") float[] index) {
      long __functionAddress = GL.getICD().glIndexfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(index, 1);
      }

      JNI.callPV(index, __functionAddress);
   }

   public static void glIndexdv(@NativeType("GLdouble const *") double[] index) {
      long __functionAddress = GL.getICD().glIndexdv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(index, 1);
      }

      JNI.callPV(index, __functionAddress);
   }

   public static void glInterleavedArrays(@NativeType("GLenum") int format, @NativeType("GLsizei") int stride, @NativeType("void const *") short[] pointer) {
      long __functionAddress = GL.getICD().glInterleavedArrays;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(format, stride, pointer, __functionAddress);
   }

   public static void glInterleavedArrays(@NativeType("GLenum") int format, @NativeType("GLsizei") int stride, @NativeType("void const *") int[] pointer) {
      long __functionAddress = GL.getICD().glInterleavedArrays;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(format, stride, pointer, __functionAddress);
   }

   public static void glInterleavedArrays(@NativeType("GLenum") int format, @NativeType("GLsizei") int stride, @NativeType("void const *") float[] pointer) {
      long __functionAddress = GL.getICD().glInterleavedArrays;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(format, stride, pointer, __functionAddress);
   }

   public static void glInterleavedArrays(@NativeType("GLenum") int format, @NativeType("GLsizei") int stride, @NativeType("void const *") double[] pointer) {
      long __functionAddress = GL.getICD().glInterleavedArrays;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(format, stride, pointer, __functionAddress);
   }

   public static void glLightModeliv(@NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glLightModeliv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(pname, params, __functionAddress);
   }

   public static void glLightModelfv(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      long __functionAddress = GL.getICD().glLightModelfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(pname, params, __functionAddress);
   }

   public static void glLightiv(@NativeType("GLenum") int light, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glLightiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(light, pname, params, __functionAddress);
   }

   public static void glLightfv(@NativeType("GLenum") int light, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      long __functionAddress = GL.getICD().glLightfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(light, pname, params, __functionAddress);
   }

   public static void glLoadMatrixf(@NativeType("GLfloat const *") float[] m) {
      long __functionAddress = GL.getICD().glLoadMatrixf;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(m, 16);
      }

      JNI.callPV(m, __functionAddress);
   }

   public static void glLoadMatrixd(@NativeType("GLdouble const *") double[] m) {
      long __functionAddress = GL.getICD().glLoadMatrixd;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(m, 16);
      }

      JNI.callPV(m, __functionAddress);
   }

   public static void glMap1f(
      @NativeType("GLenum") int target,
      @NativeType("GLfloat") float u1,
      @NativeType("GLfloat") float u2,
      @NativeType("GLint") int stride,
      @NativeType("GLint") int order,
      @NativeType("GLfloat const *") float[] points
   ) {
      long __functionAddress = GL.getICD().glMap1f;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(points, order * stride);
      }

      JNI.callPV(target, u1, u2, stride, order, points, __functionAddress);
   }

   public static void glMap1d(
      @NativeType("GLenum") int target,
      @NativeType("GLdouble") double u1,
      @NativeType("GLdouble") double u2,
      @NativeType("GLint") int stride,
      @NativeType("GLint") int order,
      @NativeType("GLdouble const *") double[] points
   ) {
      long __functionAddress = GL.getICD().glMap1d;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(points, stride * order);
      }

      JNI.callPV(target, u1, u2, stride, order, points, __functionAddress);
   }

   public static void glMap2f(
      @NativeType("GLenum") int target,
      @NativeType("GLfloat") float u1,
      @NativeType("GLfloat") float u2,
      @NativeType("GLint") int ustride,
      @NativeType("GLint") int uorder,
      @NativeType("GLfloat") float v1,
      @NativeType("GLfloat") float v2,
      @NativeType("GLint") int vstride,
      @NativeType("GLint") int vorder,
      @NativeType("GLfloat const *") float[] points
   ) {
      long __functionAddress = GL.getICD().glMap2f;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(points, ustride * uorder * vstride * vorder);
      }

      JNI.callPV(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, points, __functionAddress);
   }

   public static void glMap2d(
      @NativeType("GLenum") int target,
      @NativeType("GLdouble") double u1,
      @NativeType("GLdouble") double u2,
      @NativeType("GLint") int ustride,
      @NativeType("GLint") int uorder,
      @NativeType("GLdouble") double v1,
      @NativeType("GLdouble") double v2,
      @NativeType("GLint") int vstride,
      @NativeType("GLint") int vorder,
      @NativeType("GLdouble const *") double[] points
   ) {
      long __functionAddress = GL.getICD().glMap2d;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(points, ustride * uorder * vstride * vorder);
      }

      JNI.callPV(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder, points, __functionAddress);
   }

   public static void glMaterialiv(@NativeType("GLenum") int face, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glMaterialiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(face, pname, params, __functionAddress);
   }

   public static void glMaterialfv(@NativeType("GLenum") int face, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      long __functionAddress = GL.getICD().glMaterialfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(face, pname, params, __functionAddress);
   }

   public static void glMultMatrixf(@NativeType("GLfloat const *") float[] m) {
      long __functionAddress = GL.getICD().glMultMatrixf;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(m, 16);
      }

      JNI.callPV(m, __functionAddress);
   }

   public static void glMultMatrixd(@NativeType("GLdouble const *") double[] m) {
      long __functionAddress = GL.getICD().glMultMatrixd;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(m, 16);
      }

      JNI.callPV(m, __functionAddress);
   }

   public static void glNormal3fv(@NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glNormal3fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glNormal3sv(@NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glNormal3sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glNormal3iv(@NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glNormal3iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glNormal3dv(@NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glNormal3dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glPixelMapfv(@NativeType("GLenum") int map, @NativeType("GLfloat const *") float[] values) {
      long __functionAddress = GL.getICD().glPixelMapfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(map, values.length, values, __functionAddress);
   }

   public static void glPixelMapusv(@NativeType("GLenum") int map, @NativeType("GLushort const *") short[] values) {
      long __functionAddress = GL.getICD().glPixelMapusv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(map, values.length, values, __functionAddress);
   }

   public static void glPixelMapuiv(@NativeType("GLenum") int map, @NativeType("GLuint const *") int[] values) {
      long __functionAddress = GL.getICD().glPixelMapuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(map, values.length, values, __functionAddress);
   }

   public static void glPrioritizeTextures(@NativeType("GLuint const *") int[] textures, @NativeType("GLfloat const *") float[] priorities) {
      long __functionAddress = GL.getICD().glPrioritizeTextures;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(priorities, textures.length);
      }

      JNI.callPPV(textures.length, textures, priorities, __functionAddress);
   }

   public static void glRasterPos2iv(@NativeType("GLint const *") int[] coords) {
      long __functionAddress = GL.getICD().glRasterPos2iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 2);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glRasterPos2sv(@NativeType("GLshort const *") short[] coords) {
      long __functionAddress = GL.getICD().glRasterPos2sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 2);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glRasterPos2fv(@NativeType("GLfloat const *") float[] coords) {
      long __functionAddress = GL.getICD().glRasterPos2fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 2);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glRasterPos2dv(@NativeType("GLdouble const *") double[] coords) {
      long __functionAddress = GL.getICD().glRasterPos2dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 2);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glRasterPos3iv(@NativeType("GLint const *") int[] coords) {
      long __functionAddress = GL.getICD().glRasterPos3iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 3);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glRasterPos3sv(@NativeType("GLshort const *") short[] coords) {
      long __functionAddress = GL.getICD().glRasterPos3sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 3);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glRasterPos3fv(@NativeType("GLfloat const *") float[] coords) {
      long __functionAddress = GL.getICD().glRasterPos3fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 3);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glRasterPos3dv(@NativeType("GLdouble const *") double[] coords) {
      long __functionAddress = GL.getICD().glRasterPos3dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 3);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glRasterPos4iv(@NativeType("GLint const *") int[] coords) {
      long __functionAddress = GL.getICD().glRasterPos4iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 4);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glRasterPos4sv(@NativeType("GLshort const *") short[] coords) {
      long __functionAddress = GL.getICD().glRasterPos4sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 4);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glRasterPos4fv(@NativeType("GLfloat const *") float[] coords) {
      long __functionAddress = GL.getICD().glRasterPos4fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 4);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glRasterPos4dv(@NativeType("GLdouble const *") double[] coords) {
      long __functionAddress = GL.getICD().glRasterPos4dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 4);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glReadPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") short[] pixels
   ) {
      GL11C.glReadPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") int[] pixels
   ) {
      GL11C.glReadPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") float[] pixels
   ) {
      GL11C.glReadPixels(x, y, width, height, format, type, pixels);
   }

   public static void glRectiv(@NativeType("GLint const *") int[] v1, @NativeType("GLint const *") int[] v2) {
      long __functionAddress = GL.getICD().glRectiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v1, 2);
         Checks.check(v2, 2);
      }

      JNI.callPPV(v1, v2, __functionAddress);
   }

   public static void glRectsv(@NativeType("GLshort const *") short[] v1, @NativeType("GLshort const *") short[] v2) {
      long __functionAddress = GL.getICD().glRectsv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v1, 2);
         Checks.check(v2, 2);
      }

      JNI.callPPV(v1, v2, __functionAddress);
   }

   public static void glRectfv(@NativeType("GLfloat const *") float[] v1, @NativeType("GLfloat const *") float[] v2) {
      long __functionAddress = GL.getICD().glRectfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v1, 2);
         Checks.check(v2, 2);
      }

      JNI.callPPV(v1, v2, __functionAddress);
   }

   public static void glRectdv(@NativeType("GLdouble const *") double[] v1, @NativeType("GLdouble const *") double[] v2) {
      long __functionAddress = GL.getICD().glRectdv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v1, 2);
         Checks.check(v2, 2);
      }

      JNI.callPPV(v1, v2, __functionAddress);
   }

   public static void glSelectBuffer(@NativeType("GLuint *") int[] buffer) {
      long __functionAddress = GL.getICD().glSelectBuffer;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(buffer.length, buffer, __functionAddress);
   }

   public static void glTexCoord1fv(@NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glTexCoord1fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord1sv(@NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glTexCoord1sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord1iv(@NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glTexCoord1iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord1dv(@NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glTexCoord1dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord2fv(@NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glTexCoord2fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord2sv(@NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glTexCoord2sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord2iv(@NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glTexCoord2iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord2dv(@NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glTexCoord2dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord3fv(@NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glTexCoord3fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord3sv(@NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glTexCoord3sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord3iv(@NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glTexCoord3iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord3dv(@NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glTexCoord3dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord4fv(@NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glTexCoord4fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord4sv(@NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glTexCoord4sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord4iv(@NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glTexCoord4iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord4dv(@NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glTexCoord4dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexEnviv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glTexEnviv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glTexEnvfv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      long __functionAddress = GL.getICD().glTexEnvfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glTexGeniv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glTexGeniv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(coord, pname, params, __functionAddress);
   }

   public static void glTexGenfv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      long __functionAddress = GL.getICD().glTexGenfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(coord, pname, params, __functionAddress);
   }

   public static void glTexGendv(@NativeType("GLenum") int coord, @NativeType("GLenum") int pname, @NativeType("GLdouble const *") double[] params) {
      long __functionAddress = GL.getICD().glTexGendv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(coord, pname, params, __functionAddress);
   }

   public static void glTexImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") short[] pixels
   ) {
      GL11C.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
   }

   public static void glTexImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") int[] pixels
   ) {
      GL11C.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
   }

   public static void glTexImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") float[] pixels
   ) {
      GL11C.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
   }

   public static void glTexImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") double[] pixels
   ) {
      GL11C.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
   }

   public static void glTexImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") short[] pixels
   ) {
      GL11C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
   }

   public static void glTexImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") int[] pixels
   ) {
      GL11C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
   }

   public static void glTexImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") float[] pixels
   ) {
      GL11C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
   }

   public static void glTexImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") double[] pixels
   ) {
      GL11C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
   }

   public static void glTexParameteriv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      GL11C.glTexParameteriv(target, pname, params);
   }

   public static void glTexParameterfv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      GL11C.glTexParameterfv(target, pname, params);
   }

   public static void glTexSubImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") short[] pixels
   ) {
      GL11C.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
   }

   public static void glTexSubImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") int[] pixels
   ) {
      GL11C.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
   }

   public static void glTexSubImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") float[] pixels
   ) {
      GL11C.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
   }

   public static void glTexSubImage1D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") double[] pixels
   ) {
      GL11C.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
   }

   public static void glTexSubImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") short[] pixels
   ) {
      GL11C.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTexSubImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") int[] pixels
   ) {
      GL11C.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTexSubImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") float[] pixels
   ) {
      GL11C.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTexSubImage2D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") double[] pixels
   ) {
      GL11C.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glVertex2fv(@NativeType("GLfloat const *") float[] coords) {
      long __functionAddress = GL.getICD().glVertex2fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 2);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glVertex2sv(@NativeType("GLshort const *") short[] coords) {
      long __functionAddress = GL.getICD().glVertex2sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 2);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glVertex2iv(@NativeType("GLint const *") int[] coords) {
      long __functionAddress = GL.getICD().glVertex2iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 2);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glVertex2dv(@NativeType("GLdouble const *") double[] coords) {
      long __functionAddress = GL.getICD().glVertex2dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 2);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glVertex3fv(@NativeType("GLfloat const *") float[] coords) {
      long __functionAddress = GL.getICD().glVertex3fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 3);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glVertex3sv(@NativeType("GLshort const *") short[] coords) {
      long __functionAddress = GL.getICD().glVertex3sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 3);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glVertex3iv(@NativeType("GLint const *") int[] coords) {
      long __functionAddress = GL.getICD().glVertex3iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 3);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glVertex3dv(@NativeType("GLdouble const *") double[] coords) {
      long __functionAddress = GL.getICD().glVertex3dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 3);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glVertex4fv(@NativeType("GLfloat const *") float[] coords) {
      long __functionAddress = GL.getICD().glVertex4fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 4);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glVertex4sv(@NativeType("GLshort const *") short[] coords) {
      long __functionAddress = GL.getICD().glVertex4sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 4);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glVertex4iv(@NativeType("GLint const *") int[] coords) {
      long __functionAddress = GL.getICD().glVertex4iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 4);
      }

      JNI.callPV(coords, __functionAddress);
   }

   public static void glVertex4dv(@NativeType("GLdouble const *") double[] coords) {
      long __functionAddress = GL.getICD().glVertex4dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coords, 4);
      }

      JNI.callPV(coords, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
