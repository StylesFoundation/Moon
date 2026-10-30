package org.lwjgl.glfw;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.JNI;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Platform;
import org.lwjgl.system.SharedLibrary;
import org.lwjgl.system.Struct;

public class GLFW {
   private static final SharedLibrary GLFW = Library.loadNative(
      GLFW.class, "org.lwjgl.glfw", Configuration.GLFW_LIBRARY_NAME.get(Platform.mapLibraryNameBundled("glfw")), true
   );
   public static final int GLFW_VERSION_MAJOR = 3;
   public static final int GLFW_VERSION_MINOR = 4;
   public static final int GLFW_VERSION_REVISION = 0;
   public static final int GLFW_TRUE = 1;
   public static final int GLFW_FALSE = 0;
   public static final int GLFW_RELEASE = 0;
   public static final int GLFW_PRESS = 1;
   public static final int GLFW_REPEAT = 2;
   public static final int GLFW_HAT_CENTERED = 0;
   public static final int GLFW_HAT_UP = 1;
   public static final int GLFW_HAT_RIGHT = 2;
   public static final int GLFW_HAT_DOWN = 4;
   public static final int GLFW_HAT_LEFT = 8;
   public static final int GLFW_HAT_RIGHT_UP = 3;
   public static final int GLFW_HAT_RIGHT_DOWN = 6;
   public static final int GLFW_HAT_LEFT_UP = 9;
   public static final int GLFW_HAT_LEFT_DOWN = 12;
   public static final int GLFW_KEY_UNKNOWN = -1;
   public static final int GLFW_KEY_SPACE = 32;
   public static final int GLFW_KEY_APOSTROPHE = 39;
   public static final int GLFW_KEY_COMMA = 44;
   public static final int GLFW_KEY_MINUS = 45;
   public static final int GLFW_KEY_PERIOD = 46;
   public static final int GLFW_KEY_SLASH = 47;
   public static final int GLFW_KEY_0 = 48;
   public static final int GLFW_KEY_1 = 49;
   public static final int GLFW_KEY_2 = 50;
   public static final int GLFW_KEY_3 = 51;
   public static final int GLFW_KEY_4 = 52;
   public static final int GLFW_KEY_5 = 53;
   public static final int GLFW_KEY_6 = 54;
   public static final int GLFW_KEY_7 = 55;
   public static final int GLFW_KEY_8 = 56;
   public static final int GLFW_KEY_9 = 57;
   public static final int GLFW_KEY_SEMICOLON = 59;
   public static final int GLFW_KEY_EQUAL = 61;
   public static final int GLFW_KEY_A = 65;
   public static final int GLFW_KEY_B = 66;
   public static final int GLFW_KEY_C = 67;
   public static final int GLFW_KEY_D = 68;
   public static final int GLFW_KEY_E = 69;
   public static final int GLFW_KEY_F = 70;
   public static final int GLFW_KEY_G = 71;
   public static final int GLFW_KEY_H = 72;
   public static final int GLFW_KEY_I = 73;
   public static final int GLFW_KEY_J = 74;
   public static final int GLFW_KEY_K = 75;
   public static final int GLFW_KEY_L = 76;
   public static final int GLFW_KEY_M = 77;
   public static final int GLFW_KEY_N = 78;
   public static final int GLFW_KEY_O = 79;
   public static final int GLFW_KEY_P = 80;
   public static final int GLFW_KEY_Q = 81;
   public static final int GLFW_KEY_R = 82;
   public static final int GLFW_KEY_S = 83;
   public static final int GLFW_KEY_T = 84;
   public static final int GLFW_KEY_U = 85;
   public static final int GLFW_KEY_V = 86;
   public static final int GLFW_KEY_W = 87;
   public static final int GLFW_KEY_X = 88;
   public static final int GLFW_KEY_Y = 89;
   public static final int GLFW_KEY_Z = 90;
   public static final int GLFW_KEY_LEFT_BRACKET = 91;
   public static final int GLFW_KEY_BACKSLASH = 92;
   public static final int GLFW_KEY_RIGHT_BRACKET = 93;
   public static final int GLFW_KEY_GRAVE_ACCENT = 96;
   public static final int GLFW_KEY_WORLD_1 = 161;
   public static final int GLFW_KEY_WORLD_2 = 162;
   public static final int GLFW_KEY_ESCAPE = 256;
   public static final int GLFW_KEY_ENTER = 257;
   public static final int GLFW_KEY_TAB = 258;
   public static final int GLFW_KEY_BACKSPACE = 259;
   public static final int GLFW_KEY_INSERT = 260;
   public static final int GLFW_KEY_DELETE = 261;
   public static final int GLFW_KEY_RIGHT = 262;
   public static final int GLFW_KEY_LEFT = 263;
   public static final int GLFW_KEY_DOWN = 264;
   public static final int GLFW_KEY_UP = 265;
   public static final int GLFW_KEY_PAGE_UP = 266;
   public static final int GLFW_KEY_PAGE_DOWN = 267;
   public static final int GLFW_KEY_HOME = 268;
   public static final int GLFW_KEY_END = 269;
   public static final int GLFW_KEY_CAPS_LOCK = 280;
   public static final int GLFW_KEY_SCROLL_LOCK = 281;
   public static final int GLFW_KEY_NUM_LOCK = 282;
   public static final int GLFW_KEY_PRINT_SCREEN = 283;
   public static final int GLFW_KEY_PAUSE = 284;
   public static final int GLFW_KEY_F1 = 290;
   public static final int GLFW_KEY_F2 = 291;
   public static final int GLFW_KEY_F3 = 292;
   public static final int GLFW_KEY_F4 = 293;
   public static final int GLFW_KEY_F5 = 294;
   public static final int GLFW_KEY_F6 = 295;
   public static final int GLFW_KEY_F7 = 296;
   public static final int GLFW_KEY_F8 = 297;
   public static final int GLFW_KEY_F9 = 298;
   public static final int GLFW_KEY_F10 = 299;
   public static final int GLFW_KEY_F11 = 300;
   public static final int GLFW_KEY_F12 = 301;
   public static final int GLFW_KEY_F13 = 302;
   public static final int GLFW_KEY_F14 = 303;
   public static final int GLFW_KEY_F15 = 304;
   public static final int GLFW_KEY_F16 = 305;
   public static final int GLFW_KEY_F17 = 306;
   public static final int GLFW_KEY_F18 = 307;
   public static final int GLFW_KEY_F19 = 308;
   public static final int GLFW_KEY_F20 = 309;
   public static final int GLFW_KEY_F21 = 310;
   public static final int GLFW_KEY_F22 = 311;
   public static final int GLFW_KEY_F23 = 312;
   public static final int GLFW_KEY_F24 = 313;
   public static final int GLFW_KEY_F25 = 314;
   public static final int GLFW_KEY_KP_0 = 320;
   public static final int GLFW_KEY_KP_1 = 321;
   public static final int GLFW_KEY_KP_2 = 322;
   public static final int GLFW_KEY_KP_3 = 323;
   public static final int GLFW_KEY_KP_4 = 324;
   public static final int GLFW_KEY_KP_5 = 325;
   public static final int GLFW_KEY_KP_6 = 326;
   public static final int GLFW_KEY_KP_7 = 327;
   public static final int GLFW_KEY_KP_8 = 328;
   public static final int GLFW_KEY_KP_9 = 329;
   public static final int GLFW_KEY_KP_DECIMAL = 330;
   public static final int GLFW_KEY_KP_DIVIDE = 331;
   public static final int GLFW_KEY_KP_MULTIPLY = 332;
   public static final int GLFW_KEY_KP_SUBTRACT = 333;
   public static final int GLFW_KEY_KP_ADD = 334;
   public static final int GLFW_KEY_KP_ENTER = 335;
   public static final int GLFW_KEY_KP_EQUAL = 336;
   public static final int GLFW_KEY_LEFT_SHIFT = 340;
   public static final int GLFW_KEY_LEFT_CONTROL = 341;
   public static final int GLFW_KEY_LEFT_ALT = 342;
   public static final int GLFW_KEY_LEFT_SUPER = 343;
   public static final int GLFW_KEY_RIGHT_SHIFT = 344;
   public static final int GLFW_KEY_RIGHT_CONTROL = 345;
   public static final int GLFW_KEY_RIGHT_ALT = 346;
   public static final int GLFW_KEY_RIGHT_SUPER = 347;
   public static final int GLFW_KEY_MENU = 348;
   public static final int GLFW_KEY_LAST = 348;
   public static final int GLFW_MOD_SHIFT = 1;
   public static final int GLFW_MOD_CONTROL = 2;
   public static final int GLFW_MOD_ALT = 4;
   public static final int GLFW_MOD_SUPER = 8;
   public static final int GLFW_MOD_CAPS_LOCK = 16;
   public static final int GLFW_MOD_NUM_LOCK = 32;
   public static final int GLFW_MOUSE_BUTTON_1 = 0;
   public static final int GLFW_MOUSE_BUTTON_2 = 1;
   public static final int GLFW_MOUSE_BUTTON_3 = 2;
   public static final int GLFW_MOUSE_BUTTON_4 = 3;
   public static final int GLFW_MOUSE_BUTTON_5 = 4;
   public static final int GLFW_MOUSE_BUTTON_6 = 5;
   public static final int GLFW_MOUSE_BUTTON_7 = 6;
   public static final int GLFW_MOUSE_BUTTON_8 = 7;
   public static final int GLFW_MOUSE_BUTTON_LAST = 7;
   public static final int GLFW_MOUSE_BUTTON_LEFT = 0;
   public static final int GLFW_MOUSE_BUTTON_RIGHT = 1;
   public static final int GLFW_MOUSE_BUTTON_MIDDLE = 2;
   public static final int GLFW_JOYSTICK_1 = 0;
   public static final int GLFW_JOYSTICK_2 = 1;
   public static final int GLFW_JOYSTICK_3 = 2;
   public static final int GLFW_JOYSTICK_4 = 3;
   public static final int GLFW_JOYSTICK_5 = 4;
   public static final int GLFW_JOYSTICK_6 = 5;
   public static final int GLFW_JOYSTICK_7 = 6;
   public static final int GLFW_JOYSTICK_8 = 7;
   public static final int GLFW_JOYSTICK_9 = 8;
   public static final int GLFW_JOYSTICK_10 = 9;
   public static final int GLFW_JOYSTICK_11 = 10;
   public static final int GLFW_JOYSTICK_12 = 11;
   public static final int GLFW_JOYSTICK_13 = 12;
   public static final int GLFW_JOYSTICK_14 = 13;
   public static final int GLFW_JOYSTICK_15 = 14;
   public static final int GLFW_JOYSTICK_16 = 15;
   public static final int GLFW_JOYSTICK_LAST = 15;
   public static final int GLFW_GAMEPAD_BUTTON_A = 0;
   public static final int GLFW_GAMEPAD_BUTTON_B = 1;
   public static final int GLFW_GAMEPAD_BUTTON_X = 2;
   public static final int GLFW_GAMEPAD_BUTTON_Y = 3;
   public static final int GLFW_GAMEPAD_BUTTON_LEFT_BUMPER = 4;
   public static final int GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER = 5;
   public static final int GLFW_GAMEPAD_BUTTON_BACK = 6;
   public static final int GLFW_GAMEPAD_BUTTON_START = 7;
   public static final int GLFW_GAMEPAD_BUTTON_GUIDE = 8;
   public static final int GLFW_GAMEPAD_BUTTON_LEFT_THUMB = 9;
   public static final int GLFW_GAMEPAD_BUTTON_RIGHT_THUMB = 10;
   public static final int GLFW_GAMEPAD_BUTTON_DPAD_UP = 11;
   public static final int GLFW_GAMEPAD_BUTTON_DPAD_RIGHT = 12;
   public static final int GLFW_GAMEPAD_BUTTON_DPAD_DOWN = 13;
   public static final int GLFW_GAMEPAD_BUTTON_DPAD_LEFT = 14;
   public static final int GLFW_GAMEPAD_BUTTON_LAST = 14;
   public static final int GLFW_GAMEPAD_BUTTON_CROSS = 0;
   public static final int GLFW_GAMEPAD_BUTTON_CIRCLE = 1;
   public static final int GLFW_GAMEPAD_BUTTON_SQUARE = 2;
   public static final int GLFW_GAMEPAD_BUTTON_TRIANGLE = 3;
   public static final int GLFW_GAMEPAD_AXIS_LEFT_X = 0;
   public static final int GLFW_GAMEPAD_AXIS_LEFT_Y = 1;
   public static final int GLFW_GAMEPAD_AXIS_RIGHT_X = 2;
   public static final int GLFW_GAMEPAD_AXIS_RIGHT_Y = 3;
   public static final int GLFW_GAMEPAD_AXIS_LEFT_TRIGGER = 4;
   public static final int GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER = 5;
   public static final int GLFW_GAMEPAD_AXIS_LAST = 5;
   public static final int GLFW_NO_ERROR = 0;
   public static final int GLFW_NOT_INITIALIZED = 65537;
   public static final int GLFW_NO_CURRENT_CONTEXT = 65538;
   public static final int GLFW_INVALID_ENUM = 65539;
   public static final int GLFW_INVALID_VALUE = 65540;
   public static final int GLFW_OUT_OF_MEMORY = 65541;
   public static final int GLFW_API_UNAVAILABLE = 65542;
   public static final int GLFW_VERSION_UNAVAILABLE = 65543;
   public static final int GLFW_PLATFORM_ERROR = 65544;
   public static final int GLFW_FORMAT_UNAVAILABLE = 65545;
   public static final int GLFW_NO_WINDOW_CONTEXT = 65546;
   public static final int GLFW_CURSOR_UNAVAILABLE = 65547;
   public static final int GLFW_FEATURE_UNAVAILABLE = 65548;
   public static final int GLFW_FEATURE_UNIMPLEMENTED = 65549;
   public static final int GLFW_PLATFORM_UNAVAILABLE = 65550;
   public static final int GLFW_FOCUSED = 131073;
   public static final int GLFW_ICONIFIED = 131074;
   public static final int GLFW_RESIZABLE = 131075;
   public static final int GLFW_VISIBLE = 131076;
   public static final int GLFW_DECORATED = 131077;
   public static final int GLFW_AUTO_ICONIFY = 131078;
   public static final int GLFW_FLOATING = 131079;
   public static final int GLFW_MAXIMIZED = 131080;
   public static final int GLFW_CENTER_CURSOR = 131081;
   public static final int GLFW_TRANSPARENT_FRAMEBUFFER = 131082;
   public static final int GLFW_HOVERED = 131083;
   public static final int GLFW_FOCUS_ON_SHOW = 131084;
   public static final int GLFW_MOUSE_PASSTHROUGH = 131085;
   public static final int GLFW_CURSOR = 208897;
   public static final int GLFW_STICKY_KEYS = 208898;
   public static final int GLFW_STICKY_MOUSE_BUTTONS = 208899;
   public static final int GLFW_LOCK_KEY_MODS = 208900;
   public static final int GLFW_RAW_MOUSE_MOTION = 208901;
   public static final int GLFW_CURSOR_NORMAL = 212993;
   public static final int GLFW_CURSOR_HIDDEN = 212994;
   public static final int GLFW_CURSOR_DISABLED = 212995;
   public static final int GLFW_ARROW_CURSOR = 221185;
   public static final int GLFW_IBEAM_CURSOR = 221186;
   public static final int GLFW_CROSSHAIR_CURSOR = 221187;
   public static final int GLFW_POINTING_HAND_CURSOR = 221188;
   public static final int GLFW_RESIZE_EW_CURSOR = 221189;
   public static final int GLFW_RESIZE_NS_CURSOR = 221190;
   public static final int GLFW_RESIZE_NWSE_CURSOR = 221191;
   public static final int GLFW_RESIZE_NESW_CURSOR = 221192;
   public static final int GLFW_RESIZE_ALL_CURSOR = 221193;
   public static final int GLFW_NOT_ALLOWED_CURSOR = 221194;
   public static final int GLFW_HRESIZE_CURSOR = 221189;
   public static final int GLFW_VRESIZE_CURSOR = 221190;
   public static final int GLFW_HAND_CURSOR = 221188;
   public static final int GLFW_CONNECTED = 262145;
   public static final int GLFW_DISCONNECTED = 262146;
   public static final int GLFW_JOYSTICK_HAT_BUTTONS = 327681;
   public static final int GLFW_ANGLE_PLATFORM_TYPE = 327682;
   public static final int GLFW_PLATFORM = 327683;
   public static final int GLFW_COCOA_CHDIR_RESOURCES = 331777;
   public static final int GLFW_COCOA_MENUBAR = 331778;
   public static final int GLFW_X11_XCB_VULKAN_SURFACE = 335873;
   public static final int GLFW_ANY_PLATFORM = 393216;
   public static final int GLFW_PLATFORM_WIN32 = 393217;
   public static final int GLFW_PLATFORM_COCOA = 393218;
   public static final int GLFW_PLATFORM_WAYLAND = 393219;
   public static final int GLFW_PLATFORM_X11 = 393220;
   public static final int GLFW_PLATFORM_NULL = 393221;
   public static final int GLFW_DONT_CARE = -1;
   public static final int GLFW_RED_BITS = 135169;
   public static final int GLFW_GREEN_BITS = 135170;
   public static final int GLFW_BLUE_BITS = 135171;
   public static final int GLFW_ALPHA_BITS = 135172;
   public static final int GLFW_DEPTH_BITS = 135173;
   public static final int GLFW_STENCIL_BITS = 135174;
   public static final int GLFW_ACCUM_RED_BITS = 135175;
   public static final int GLFW_ACCUM_GREEN_BITS = 135176;
   public static final int GLFW_ACCUM_BLUE_BITS = 135177;
   public static final int GLFW_ACCUM_ALPHA_BITS = 135178;
   public static final int GLFW_AUX_BUFFERS = 135179;
   public static final int GLFW_STEREO = 135180;
   public static final int GLFW_SAMPLES = 135181;
   public static final int GLFW_SRGB_CAPABLE = 135182;
   public static final int GLFW_REFRESH_RATE = 135183;
   public static final int GLFW_DOUBLEBUFFER = 135184;
   public static final int GLFW_CLIENT_API = 139265;
   public static final int GLFW_CONTEXT_VERSION_MAJOR = 139266;
   public static final int GLFW_CONTEXT_VERSION_MINOR = 139267;
   public static final int GLFW_CONTEXT_REVISION = 139268;
   public static final int GLFW_CONTEXT_ROBUSTNESS = 139269;
   public static final int GLFW_OPENGL_FORWARD_COMPAT = 139270;
   public static final int GLFW_CONTEXT_DEBUG = 139271;
   public static final int GLFW_OPENGL_DEBUG_CONTEXT = 139271;
   public static final int GLFW_OPENGL_PROFILE = 139272;
   public static final int GLFW_CONTEXT_RELEASE_BEHAVIOR = 139273;
   public static final int GLFW_CONTEXT_NO_ERROR = 139274;
   public static final int GLFW_CONTEXT_CREATION_API = 139275;
   public static final int GLFW_SCALE_TO_MONITOR = 139276;
   public static final int GLFW_COCOA_RETINA_FRAMEBUFFER = 143361;
   public static final int GLFW_COCOA_FRAME_NAME = 143362;
   public static final int GLFW_COCOA_GRAPHICS_SWITCHING = 143363;
   public static final int GLFW_X11_CLASS_NAME = 147457;
   public static final int GLFW_X11_INSTANCE_NAME = 147458;
   public static final int GLFW_WIN32_KEYBOARD_MENU = 151553;
   public static final int GLFW_NO_API = 0;
   public static final int GLFW_OPENGL_API = 196609;
   public static final int GLFW_OPENGL_ES_API = 196610;
   public static final int GLFW_NO_ROBUSTNESS = 0;
   public static final int GLFW_NO_RESET_NOTIFICATION = 200705;
   public static final int GLFW_LOSE_CONTEXT_ON_RESET = 200706;
   public static final int GLFW_OPENGL_ANY_PROFILE = 0;
   public static final int GLFW_OPENGL_CORE_PROFILE = 204801;
   public static final int GLFW_OPENGL_COMPAT_PROFILE = 204802;
   public static final int GLFW_ANY_RELEASE_BEHAVIOR = 0;
   public static final int GLFW_RELEASE_BEHAVIOR_FLUSH = 217089;
   public static final int GLFW_RELEASE_BEHAVIOR_NONE = 217090;
   public static final int GLFW_NATIVE_CONTEXT_API = 221185;
   public static final int GLFW_EGL_CONTEXT_API = 221186;
   public static final int GLFW_OSMESA_CONTEXT_API = 221187;
   public static final int GLFW_ANGLE_PLATFORM_TYPE_NONE = 225281;
   public static final int GLFW_ANGLE_PLATFORM_TYPE_OPENGL = 225282;
   public static final int GLFW_ANGLE_PLATFORM_TYPE_OPENGLES = 225283;
   public static final int GLFW_ANGLE_PLATFORM_TYPE_D3D9 = 225284;
   public static final int GLFW_ANGLE_PLATFORM_TYPE_D3D11 = 225285;
   public static final int GLFW_ANGLE_PLATFORM_TYPE_VULKAN = 225287;
   public static final int GLFW_ANGLE_PLATFORM_TYPE_METAL = 225288;

   public static SharedLibrary getLibrary() {
      return GLFW;
   }

   protected GLFW() {
      throw new UnsupportedOperationException();
   }

   @NativeType("int")
   public static boolean glfwInit() {
      long __functionAddress = Functions.Init;
      EventLoop.check();
      return JNI.invokeI(__functionAddress) != 0;
   }

   public static void glfwTerminate() {
      long __functionAddress = Functions.Terminate;
      JNI.invokeV(__functionAddress);
   }

   public static void glfwInitHint(int hint, int value) {
      long __functionAddress = Functions.InitHint;
      JNI.invokeV(hint, value, __functionAddress);
   }

   public static void nglfwInitAllocator(long allocator) {
      long __functionAddress = Functions.InitAllocator;
      if (Checks.CHECKS && allocator != 0L) {
         GLFWAllocator.validate(allocator);
      }

      JNI.invokePV(allocator, __functionAddress);
   }

   public static void glfwInitAllocator(@Nullable @NativeType("GLFWallocator const *") GLFWAllocator allocator) {
      nglfwInitAllocator(MemoryUtil.memAddressSafe(allocator));
   }

   public static void nglfwGetVersion(long major, long minor, long rev) {
      long __functionAddress = Functions.GetVersion;
      JNI.invokePPPV(major, minor, rev, __functionAddress);
   }

   public static void glfwGetVersion(
      @Nullable @NativeType("int *") IntBuffer major, @Nullable @NativeType("int *") IntBuffer minor, @Nullable @NativeType("int *") IntBuffer rev
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(major, 1);
         Checks.checkSafe(minor, 1);
         Checks.checkSafe(rev, 1);
      }

      nglfwGetVersion(MemoryUtil.memAddressSafe(major), MemoryUtil.memAddressSafe(minor), MemoryUtil.memAddressSafe(rev));
   }

   public static long nglfwGetVersionString() {
      long __functionAddress = Functions.GetVersionString;
      return JNI.invokeP(__functionAddress);
   }

   @NativeType("char const *")
   public static String glfwGetVersionString() {
      long __result = nglfwGetVersionString();
      return MemoryUtil.memASCII(__result);
   }

   public static int nglfwGetError(long description) {
      long __functionAddress = Functions.GetError;
      return JNI.invokePI(description, __functionAddress);
   }

   public static int glfwGetError(@Nullable @NativeType("char const **") PointerBuffer description) {
      if (Checks.CHECKS) {
         Checks.checkSafe(description, 1);
      }

      return nglfwGetError(MemoryUtil.memAddressSafe(description));
   }

   public static long nglfwSetErrorCallback(long cbfun) {
      long __functionAddress = Functions.SetErrorCallback;
      return JNI.invokePP(cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWerrorfun")
   public static GLFWErrorCallback glfwSetErrorCallback(@Nullable @NativeType("GLFWerrorfun") GLFWErrorCallbackI cbfun) {
      return GLFWErrorCallback.createSafe(nglfwSetErrorCallback(MemoryUtil.memAddressSafe(cbfun)));
   }

   public static int glfwGetPlatform() {
      long __functionAddress = Functions.GetPlatform;
      return JNI.invokeI(__functionAddress);
   }

   @NativeType("int")
   public static boolean glfwPlatformSupported(int platform) {
      long __functionAddress = Functions.PlatformSupported;
      return JNI.invokeI(platform, __functionAddress) != 0;
   }

   public static long nglfwGetMonitors(long count) {
      long __functionAddress = Functions.GetMonitors;
      return JNI.invokePP(count, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWmonitor **")
   public static PointerBuffer glfwGetMonitors() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();
      IntBuffer count = stack.callocInt(1);

      try {
         long __result = nglfwGetMonitors(MemoryUtil.memAddress(count));
         return MemoryUtil.memPointerBufferSafe(__result, count.get(0));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLFWmonitor *")
   public static long glfwGetPrimaryMonitor() {
      long __functionAddress = Functions.GetPrimaryMonitor;
      return JNI.invokeP(__functionAddress);
   }

   public static void nglfwGetMonitorPos(long monitor, long xpos, long ypos) {
      long __functionAddress = Functions.GetMonitorPos;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      JNI.invokePPPV(monitor, xpos, ypos, __functionAddress);
   }

   public static void glfwGetMonitorPos(
      @NativeType("GLFWmonitor *") long monitor, @Nullable @NativeType("int *") IntBuffer xpos, @Nullable @NativeType("int *") IntBuffer ypos
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(xpos, 1);
         Checks.checkSafe(ypos, 1);
      }

      nglfwGetMonitorPos(monitor, MemoryUtil.memAddressSafe(xpos), MemoryUtil.memAddressSafe(ypos));
   }

   public static void nglfwGetMonitorWorkarea(long monitor, long xpos, long ypos, long width, long height) {
      long __functionAddress = Functions.GetMonitorWorkarea;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      JNI.invokePPPPPV(monitor, xpos, ypos, width, height, __functionAddress);
   }

   public static void glfwGetMonitorWorkarea(
      @NativeType("GLFWmonitor *") long monitor,
      @Nullable @NativeType("int *") IntBuffer xpos,
      @Nullable @NativeType("int *") IntBuffer ypos,
      @Nullable @NativeType("int *") IntBuffer width,
      @Nullable @NativeType("int *") IntBuffer height
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(xpos, 1);
         Checks.checkSafe(ypos, 1);
         Checks.checkSafe(width, 1);
         Checks.checkSafe(height, 1);
      }

      nglfwGetMonitorWorkarea(
         monitor, MemoryUtil.memAddressSafe(xpos), MemoryUtil.memAddressSafe(ypos), MemoryUtil.memAddressSafe(width), MemoryUtil.memAddressSafe(height)
      );
   }

   public static void nglfwGetMonitorPhysicalSize(long monitor, long widthMM, long heightMM) {
      long __functionAddress = Functions.GetMonitorPhysicalSize;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      JNI.invokePPPV(monitor, widthMM, heightMM, __functionAddress);
   }

   public static void glfwGetMonitorPhysicalSize(
      @NativeType("GLFWmonitor *") long monitor, @Nullable @NativeType("int *") IntBuffer widthMM, @Nullable @NativeType("int *") IntBuffer heightMM
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(widthMM, 1);
         Checks.checkSafe(heightMM, 1);
      }

      nglfwGetMonitorPhysicalSize(monitor, MemoryUtil.memAddressSafe(widthMM), MemoryUtil.memAddressSafe(heightMM));
   }

   public static void nglfwGetMonitorContentScale(long monitor, long xscale, long yscale) {
      long __functionAddress = Functions.GetMonitorContentScale;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      JNI.invokePPPV(monitor, xscale, yscale, __functionAddress);
   }

   public static void glfwGetMonitorContentScale(
      @NativeType("GLFWmonitor *") long monitor, @Nullable @NativeType("float *") FloatBuffer xscale, @Nullable @NativeType("float *") FloatBuffer yscale
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(xscale, 1);
         Checks.checkSafe(yscale, 1);
      }

      nglfwGetMonitorContentScale(monitor, MemoryUtil.memAddressSafe(xscale), MemoryUtil.memAddressSafe(yscale));
   }

   public static long nglfwGetMonitorName(long monitor) {
      long __functionAddress = Functions.GetMonitorName;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      return JNI.invokePP(monitor, __functionAddress);
   }

   @Nullable
   @NativeType("char const *")
   public static String glfwGetMonitorName(@NativeType("GLFWmonitor *") long monitor) {
      long __result = nglfwGetMonitorName(monitor);
      return MemoryUtil.memUTF8Safe(__result);
   }

   public static void glfwSetMonitorUserPointer(@NativeType("GLFWmonitor *") long monitor, @NativeType("void *") long pointer) {
      long __functionAddress = Functions.SetMonitorUserPointer;
      if (Checks.CHECKS) {
         Checks.check(monitor);
         Checks.check(pointer);
      }

      JNI.invokePPV(monitor, pointer, __functionAddress);
   }

   @NativeType("void *")
   public static long glfwGetMonitorUserPointer(@NativeType("GLFWmonitor *") long monitor) {
      long __functionAddress = Functions.GetMonitorUserPointer;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      return JNI.invokePP(monitor, __functionAddress);
   }

   public static long nglfwSetMonitorCallback(long cbfun) {
      long __functionAddress = Functions.SetMonitorCallback;
      return JNI.invokePP(cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWmonitorfun")
   public static GLFWMonitorCallback glfwSetMonitorCallback(@Nullable @NativeType("GLFWmonitorfun") GLFWMonitorCallbackI cbfun) {
      return GLFWMonitorCallback.createSafe(nglfwSetMonitorCallback(MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwGetVideoModes(long monitor, long count) {
      long __functionAddress = Functions.GetVideoModes;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      return JNI.invokePPP(monitor, count, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWvidmode const *")
   public static GLFWVidMode.Buffer glfwGetVideoModes(@NativeType("GLFWmonitor *") long monitor) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();
      IntBuffer count = stack.callocInt(1);

      try {
         long __result = nglfwGetVideoModes(monitor, MemoryUtil.memAddress(count));
         return GLFWVidMode.createSafe(__result, count.get(0));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static long nglfwGetVideoMode(long monitor) {
      long __functionAddress = Functions.GetVideoMode;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      return JNI.invokePP(monitor, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWvidmode const *")
   public static GLFWVidMode glfwGetVideoMode(@NativeType("GLFWmonitor *") long monitor) {
      long __result = nglfwGetVideoMode(monitor);
      return GLFWVidMode.createSafe(__result);
   }

   public static void glfwSetGamma(@NativeType("GLFWmonitor *") long monitor, float gamma) {
      long __functionAddress = Functions.SetGamma;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      JNI.invokePV(monitor, gamma, __functionAddress);
   }

   public static long nglfwGetGammaRamp(long monitor) {
      long __functionAddress = Functions.GetGammaRamp;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      return JNI.invokePP(monitor, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWgammaramp const *")
   public static GLFWGammaRamp glfwGetGammaRamp(@NativeType("GLFWmonitor *") long monitor) {
      long __result = nglfwGetGammaRamp(monitor);
      return GLFWGammaRamp.createSafe(__result);
   }

   public static void nglfwSetGammaRamp(long monitor, long ramp) {
      long __functionAddress = Functions.SetGammaRamp;
      if (Checks.CHECKS) {
         Checks.check(monitor);
         GLFWGammaRamp.validate(ramp);
      }

      JNI.invokePPV(monitor, ramp, __functionAddress);
   }

   public static void glfwSetGammaRamp(@NativeType("GLFWmonitor *") long monitor, @NativeType("GLFWgammaramp const *") GLFWGammaRamp ramp) {
      nglfwSetGammaRamp(monitor, ramp.address());
   }

   public static void glfwDefaultWindowHints() {
      long __functionAddress = Functions.DefaultWindowHints;
      JNI.invokeV(__functionAddress);
   }

   public static void glfwWindowHint(int hint, int value) {
      long __functionAddress = Functions.WindowHint;
      JNI.invokeV(hint, value, __functionAddress);
   }

   public static void nglfwWindowHintString(int hint, long value) {
      long __functionAddress = Functions.WindowHintString;
      JNI.invokePV(hint, value, __functionAddress);
   }

   public static void glfwWindowHintString(int hint, @NativeType("char const *") ByteBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkNT1(value);
      }

      nglfwWindowHintString(hint, MemoryUtil.memAddress(value));
   }

   public static void glfwWindowHintString(int hint, @NativeType("char const *") CharSequence value) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(value, true);
         long valueEncoded = stack.getPointerAddress();
         nglfwWindowHintString(hint, valueEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static long nglfwCreateWindow(int width, int height, long title, long monitor, long share) {
      long __functionAddress = Functions.CreateWindow;
      return JNI.invokePPPP(width, height, title, monitor, share, __functionAddress);
   }

   @NativeType("GLFWwindow *")
   public static long glfwCreateWindow(
      int width, int height, @NativeType("char const *") ByteBuffer title, @NativeType("GLFWmonitor *") long monitor, @NativeType("GLFWwindow *") long share
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(title);
      }

      return nglfwCreateWindow(width, height, MemoryUtil.memAddress(title), monitor, share);
   }

   @NativeType("GLFWwindow *")
   public static long glfwCreateWindow(
      int width, int height, @NativeType("char const *") CharSequence title, @NativeType("GLFWmonitor *") long monitor, @NativeType("GLFWwindow *") long share
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(title, true);
         long titleEncoded = stack.getPointerAddress();
         return nglfwCreateWindow(width, height, titleEncoded, monitor, share);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glfwDestroyWindow(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.DestroyWindow;
      JNI.invokePV(window, __functionAddress);
   }

   @NativeType("int")
   public static boolean glfwWindowShouldClose(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.WindowShouldClose;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePI(window, __functionAddress) != 0;
   }

   public static void glfwSetWindowShouldClose(@NativeType("GLFWwindow *") long window, @NativeType("int") boolean value) {
      long __functionAddress = Functions.SetWindowShouldClose;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, value ? 1 : 0, __functionAddress);
   }

   public static void nglfwSetWindowTitle(long window, long title) {
      long __functionAddress = Functions.SetWindowTitle;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePPV(window, title, __functionAddress);
   }

   public static void glfwSetWindowTitle(@NativeType("GLFWwindow *") long window, @NativeType("char const *") ByteBuffer title) {
      if (Checks.CHECKS) {
         Checks.checkNT1(title);
      }

      nglfwSetWindowTitle(window, MemoryUtil.memAddress(title));
   }

   public static void glfwSetWindowTitle(@NativeType("GLFWwindow *") long window, @NativeType("char const *") CharSequence title) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(title, true);
         long titleEncoded = stack.getPointerAddress();
         nglfwSetWindowTitle(window, titleEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void nglfwSetWindowIcon(long window, int count, long images) {
      long __functionAddress = Functions.SetWindowIcon;
      if (Checks.CHECKS) {
         Checks.check(window);
         if (images != 0L) {
            Struct.validate(images, count, GLFWImage.SIZEOF, GLFWImage::validate);
         }
      }

      JNI.invokePPV(window, count, images, __functionAddress);
   }

   public static void glfwSetWindowIcon(@NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWimage const *") GLFWImage.Buffer images) {
      nglfwSetWindowIcon(window, Checks.remainingSafe(images), MemoryUtil.memAddressSafe(images));
   }

   public static void nglfwGetWindowPos(long window, long xpos, long ypos) {
      long __functionAddress = Functions.GetWindowPos;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePPPV(window, xpos, ypos, __functionAddress);
   }

   public static void glfwGetWindowPos(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("int *") IntBuffer xpos, @Nullable @NativeType("int *") IntBuffer ypos
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(xpos, 1);
         Checks.checkSafe(ypos, 1);
      }

      nglfwGetWindowPos(window, MemoryUtil.memAddressSafe(xpos), MemoryUtil.memAddressSafe(ypos));
   }

   public static void glfwSetWindowPos(@NativeType("GLFWwindow *") long window, int xpos, int ypos) {
      long __functionAddress = Functions.SetWindowPos;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, xpos, ypos, __functionAddress);
   }

   public static void nglfwGetWindowSize(long window, long width, long height) {
      long __functionAddress = Functions.GetWindowSize;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePPPV(window, width, height, __functionAddress);
   }

   public static void glfwGetWindowSize(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("int *") IntBuffer width, @Nullable @NativeType("int *") IntBuffer height
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(width, 1);
         Checks.checkSafe(height, 1);
      }

      nglfwGetWindowSize(window, MemoryUtil.memAddressSafe(width), MemoryUtil.memAddressSafe(height));
   }

   public static void glfwSetWindowSizeLimits(@NativeType("GLFWwindow *") long window, int minwidth, int minheight, int maxwidth, int maxheight) {
      long __functionAddress = Functions.SetWindowSizeLimits;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, minwidth, minheight, maxwidth, maxheight, __functionAddress);
   }

   public static void glfwSetWindowAspectRatio(@NativeType("GLFWwindow *") long window, int numer, int denom) {
      long __functionAddress = Functions.SetWindowAspectRatio;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, numer, denom, __functionAddress);
   }

   public static void glfwSetWindowSize(@NativeType("GLFWwindow *") long window, int width, int height) {
      long __functionAddress = Functions.SetWindowSize;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, width, height, __functionAddress);
   }

   public static void nglfwGetFramebufferSize(long window, long width, long height) {
      long __functionAddress = Functions.GetFramebufferSize;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePPPV(window, width, height, __functionAddress);
   }

   public static void glfwGetFramebufferSize(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("int *") IntBuffer width, @Nullable @NativeType("int *") IntBuffer height
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(width, 1);
         Checks.checkSafe(height, 1);
      }

      nglfwGetFramebufferSize(window, MemoryUtil.memAddressSafe(width), MemoryUtil.memAddressSafe(height));
   }

   public static void nglfwGetWindowFrameSize(long window, long left, long top, long right, long bottom) {
      long __functionAddress = Functions.GetWindowFrameSize;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePPPPPV(window, left, top, right, bottom, __functionAddress);
   }

   public static void glfwGetWindowFrameSize(
      @NativeType("GLFWwindow *") long window,
      @Nullable @NativeType("int *") IntBuffer left,
      @Nullable @NativeType("int *") IntBuffer top,
      @Nullable @NativeType("int *") IntBuffer right,
      @Nullable @NativeType("int *") IntBuffer bottom
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(left, 1);
         Checks.checkSafe(top, 1);
         Checks.checkSafe(right, 1);
         Checks.checkSafe(bottom, 1);
      }

      nglfwGetWindowFrameSize(
         window, MemoryUtil.memAddressSafe(left), MemoryUtil.memAddressSafe(top), MemoryUtil.memAddressSafe(right), MemoryUtil.memAddressSafe(bottom)
      );
   }

   public static void nglfwGetWindowContentScale(long window, long xscale, long yscale) {
      long __functionAddress = Functions.GetWindowContentScale;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePPPV(window, xscale, yscale, __functionAddress);
   }

   public static void glfwGetWindowContentScale(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("float *") FloatBuffer xscale, @Nullable @NativeType("float *") FloatBuffer yscale
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(xscale, 1);
         Checks.checkSafe(yscale, 1);
      }

      nglfwGetWindowContentScale(window, MemoryUtil.memAddressSafe(xscale), MemoryUtil.memAddressSafe(yscale));
   }

   public static float glfwGetWindowOpacity(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.GetWindowOpacity;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePF(window, __functionAddress);
   }

   public static void glfwSetWindowOpacity(@NativeType("GLFWwindow *") long window, float opacity) {
      long __functionAddress = Functions.SetWindowOpacity;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, opacity, __functionAddress);
   }

   public static void glfwIconifyWindow(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.IconifyWindow;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, __functionAddress);
   }

   public static void glfwRestoreWindow(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.RestoreWindow;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, __functionAddress);
   }

   public static void glfwMaximizeWindow(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.MaximizeWindow;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, __functionAddress);
   }

   public static void glfwShowWindow(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.ShowWindow;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, __functionAddress);
   }

   public static void glfwHideWindow(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.HideWindow;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, __functionAddress);
   }

   public static void glfwFocusWindow(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.FocusWindow;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, __functionAddress);
   }

   public static void glfwRequestWindowAttention(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.RequestWindowAttention;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, __functionAddress);
   }

   @NativeType("GLFWmonitor *")
   public static long glfwGetWindowMonitor(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.GetWindowMonitor;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePP(window, __functionAddress);
   }

   public static void glfwSetWindowMonitor(
      @NativeType("GLFWwindow *") long window, @NativeType("GLFWmonitor *") long monitor, int xpos, int ypos, int width, int height, int refreshRate
   ) {
      long __functionAddress = Functions.SetWindowMonitor;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePPV(window, monitor, xpos, ypos, width, height, refreshRate, __functionAddress);
   }

   public static int glfwGetWindowAttrib(@NativeType("GLFWwindow *") long window, int attrib) {
      long __functionAddress = Functions.GetWindowAttrib;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePI(window, attrib, __functionAddress);
   }

   public static void glfwSetWindowAttrib(@NativeType("GLFWwindow *") long window, int attrib, int value) {
      long __functionAddress = Functions.SetWindowAttrib;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, attrib, value, __functionAddress);
   }

   public static void glfwSetWindowUserPointer(@NativeType("GLFWwindow *") long window, @NativeType("void *") long pointer) {
      long __functionAddress = Functions.SetWindowUserPointer;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePPV(window, pointer, __functionAddress);
   }

   @NativeType("void *")
   public static long glfwGetWindowUserPointer(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.GetWindowUserPointer;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePP(window, __functionAddress);
   }

   public static long nglfwSetWindowPosCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetWindowPosCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWwindowposfun")
   public static GLFWWindowPosCallback glfwSetWindowPosCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWwindowposfun") GLFWWindowPosCallbackI cbfun
   ) {
      return GLFWWindowPosCallback.createSafe(nglfwSetWindowPosCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetWindowSizeCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetWindowSizeCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWwindowsizefun")
   public static GLFWWindowSizeCallback glfwSetWindowSizeCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWwindowsizefun") GLFWWindowSizeCallbackI cbfun
   ) {
      return GLFWWindowSizeCallback.createSafe(nglfwSetWindowSizeCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetWindowCloseCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetWindowCloseCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWwindowclosefun")
   public static GLFWWindowCloseCallback glfwSetWindowCloseCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWwindowclosefun") GLFWWindowCloseCallbackI cbfun
   ) {
      return GLFWWindowCloseCallback.createSafe(nglfwSetWindowCloseCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetWindowRefreshCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetWindowRefreshCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWwindowrefreshfun")
   public static GLFWWindowRefreshCallback glfwSetWindowRefreshCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWwindowrefreshfun") GLFWWindowRefreshCallbackI cbfun
   ) {
      return GLFWWindowRefreshCallback.createSafe(nglfwSetWindowRefreshCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetWindowFocusCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetWindowFocusCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWwindowfocusfun")
   public static GLFWWindowFocusCallback glfwSetWindowFocusCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWwindowfocusfun") GLFWWindowFocusCallbackI cbfun
   ) {
      return GLFWWindowFocusCallback.createSafe(nglfwSetWindowFocusCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetWindowIconifyCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetWindowIconifyCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWwindowiconifyfun")
   public static GLFWWindowIconifyCallback glfwSetWindowIconifyCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWwindowiconifyfun") GLFWWindowIconifyCallbackI cbfun
   ) {
      return GLFWWindowIconifyCallback.createSafe(nglfwSetWindowIconifyCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetWindowMaximizeCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetWindowMaximizeCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWwindowmaximizefun")
   public static GLFWWindowMaximizeCallback glfwSetWindowMaximizeCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWwindowmaximizefun") GLFWWindowMaximizeCallbackI cbfun
   ) {
      return GLFWWindowMaximizeCallback.createSafe(nglfwSetWindowMaximizeCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetFramebufferSizeCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetFramebufferSizeCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWframebuffersizefun")
   public static GLFWFramebufferSizeCallback glfwSetFramebufferSizeCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWframebuffersizefun") GLFWFramebufferSizeCallbackI cbfun
   ) {
      return GLFWFramebufferSizeCallback.createSafe(nglfwSetFramebufferSizeCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetWindowContentScaleCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetWindowContentScaleCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWwindowcontentscalefun")
   public static GLFWWindowContentScaleCallback glfwSetWindowContentScaleCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWwindowcontentscalefun") GLFWWindowContentScaleCallbackI cbfun
   ) {
      return GLFWWindowContentScaleCallback.createSafe(nglfwSetWindowContentScaleCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static void glfwPollEvents() {
      long __functionAddress = Functions.PollEvents;
      JNI.invokeV(__functionAddress);
   }

   public static void glfwWaitEvents() {
      long __functionAddress = Functions.WaitEvents;
      JNI.invokeV(__functionAddress);
   }

   public static void glfwWaitEventsTimeout(double timeout) {
      long __functionAddress = Functions.WaitEventsTimeout;
      JNI.invokeV(timeout, __functionAddress);
   }

   public static void glfwPostEmptyEvent() {
      long __functionAddress = Functions.PostEmptyEvent;
      JNI.invokeV(__functionAddress);
   }

   public static int glfwGetInputMode(@NativeType("GLFWwindow *") long window, int mode) {
      long __functionAddress = Functions.GetInputMode;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePI(window, mode, __functionAddress);
   }

   public static void glfwSetInputMode(@NativeType("GLFWwindow *") long window, int mode, int value) {
      long __functionAddress = Functions.SetInputMode;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, mode, value, __functionAddress);
   }

   @NativeType("int")
   public static boolean glfwRawMouseMotionSupported() {
      long __functionAddress = Functions.RawMouseMotionSupported;
      return JNI.invokeI(__functionAddress) != 0;
   }

   public static long nglfwGetKeyName(int key, int scancode) {
      long __functionAddress = Functions.GetKeyName;
      return JNI.invokeP(key, scancode, __functionAddress);
   }

   @Nullable
   @NativeType("char const *")
   public static String glfwGetKeyName(int key, int scancode) {
      long __result = nglfwGetKeyName(key, scancode);
      return MemoryUtil.memUTF8Safe(__result);
   }

   public static int glfwGetKeyScancode(int key) {
      long __functionAddress = Functions.GetKeyScancode;
      return JNI.invokeI(key, __functionAddress);
   }

   public static int glfwGetKey(@NativeType("GLFWwindow *") long window, int key) {
      long __functionAddress = Functions.GetKey;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePI(window, key, __functionAddress);
   }

   public static int glfwGetMouseButton(@NativeType("GLFWwindow *") long window, int button) {
      long __functionAddress = Functions.GetMouseButton;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePI(window, button, __functionAddress);
   }

   public static void nglfwGetCursorPos(long window, long xpos, long ypos) {
      long __functionAddress = Functions.GetCursorPos;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePPPV(window, xpos, ypos, __functionAddress);
   }

   public static void glfwGetCursorPos(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("double *") DoubleBuffer xpos, @Nullable @NativeType("double *") DoubleBuffer ypos
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(xpos, 1);
         Checks.checkSafe(ypos, 1);
      }

      nglfwGetCursorPos(window, MemoryUtil.memAddressSafe(xpos), MemoryUtil.memAddressSafe(ypos));
   }

   public static void glfwSetCursorPos(@NativeType("GLFWwindow *") long window, double xpos, double ypos) {
      long __functionAddress = Functions.SetCursorPos;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, xpos, ypos, __functionAddress);
   }

   public static long nglfwCreateCursor(long image, int xhot, int yhot) {
      long __functionAddress = Functions.CreateCursor;
      if (Checks.CHECKS) {
         GLFWImage.validate(image);
      }

      return JNI.invokePP(image, xhot, yhot, __functionAddress);
   }

   @NativeType("GLFWcursor *")
   public static long glfwCreateCursor(@NativeType("GLFWimage const *") GLFWImage image, int xhot, int yhot) {
      return nglfwCreateCursor(image.address(), xhot, yhot);
   }

   @NativeType("GLFWcursor *")
   public static long glfwCreateStandardCursor(int shape) {
      long __functionAddress = Functions.CreateStandardCursor;
      return JNI.invokeP(shape, __functionAddress);
   }

   public static void glfwDestroyCursor(@NativeType("GLFWcursor *") long cursor) {
      long __functionAddress = Functions.DestroyCursor;
      if (Checks.CHECKS) {
         Checks.check(cursor);
      }

      JNI.invokePV(cursor, __functionAddress);
   }

   public static void glfwSetCursor(@NativeType("GLFWwindow *") long window, @NativeType("GLFWcursor *") long cursor) {
      long __functionAddress = Functions.SetCursor;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePPV(window, cursor, __functionAddress);
   }

   public static long nglfwSetKeyCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetKeyCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWkeyfun")
   public static GLFWKeyCallback glfwSetKeyCallback(@NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWkeyfun") GLFWKeyCallbackI cbfun) {
      return GLFWKeyCallback.createSafe(nglfwSetKeyCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetCharCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetCharCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWcharfun")
   public static GLFWCharCallback glfwSetCharCallback(@NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWcharfun") GLFWCharCallbackI cbfun) {
      return GLFWCharCallback.createSafe(nglfwSetCharCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetCharModsCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetCharModsCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWcharmodsfun")
   public static GLFWCharModsCallback glfwSetCharModsCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWcharmodsfun") GLFWCharModsCallbackI cbfun
   ) {
      return GLFWCharModsCallback.createSafe(nglfwSetCharModsCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetMouseButtonCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetMouseButtonCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWmousebuttonfun")
   public static GLFWMouseButtonCallback glfwSetMouseButtonCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWmousebuttonfun") GLFWMouseButtonCallbackI cbfun
   ) {
      return GLFWMouseButtonCallback.createSafe(nglfwSetMouseButtonCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetCursorPosCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetCursorPosCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWcursorposfun")
   public static GLFWCursorPosCallback glfwSetCursorPosCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWcursorposfun") GLFWCursorPosCallbackI cbfun
   ) {
      return GLFWCursorPosCallback.createSafe(nglfwSetCursorPosCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetCursorEnterCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetCursorEnterCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWcursorenterfun")
   public static GLFWCursorEnterCallback glfwSetCursorEnterCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWcursorenterfun") GLFWCursorEnterCallbackI cbfun
   ) {
      return GLFWCursorEnterCallback.createSafe(nglfwSetCursorEnterCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetScrollCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetScrollCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWscrollfun")
   public static GLFWScrollCallback glfwSetScrollCallback(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWscrollfun") GLFWScrollCallbackI cbfun
   ) {
      return GLFWScrollCallback.createSafe(nglfwSetScrollCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   public static long nglfwSetDropCallback(long window, long cbfun) {
      long __functionAddress = Functions.SetDropCallback;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPP(window, cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWdropfun")
   public static GLFWDropCallback glfwSetDropCallback(@NativeType("GLFWwindow *") long window, @Nullable @NativeType("GLFWdropfun") GLFWDropCallbackI cbfun) {
      return GLFWDropCallback.createSafe(nglfwSetDropCallback(window, MemoryUtil.memAddressSafe(cbfun)));
   }

   @NativeType("int")
   public static boolean glfwJoystickPresent(int jid) {
      long __functionAddress = Functions.JoystickPresent;
      return JNI.invokeI(jid, __functionAddress) != 0;
   }

   public static long nglfwGetJoystickAxes(int jid, long count) {
      long __functionAddress = Functions.GetJoystickAxes;
      return JNI.invokePP(jid, count, __functionAddress);
   }

   @Nullable
   @NativeType("float const *")
   public static FloatBuffer glfwGetJoystickAxes(int jid) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();
      IntBuffer count = stack.callocInt(1);

      try {
         long __result = nglfwGetJoystickAxes(jid, MemoryUtil.memAddress(count));
         return MemoryUtil.memFloatBufferSafe(__result, count.get(0));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static long nglfwGetJoystickButtons(int jid, long count) {
      long __functionAddress = Functions.GetJoystickButtons;
      return JNI.invokePP(jid, count, __functionAddress);
   }

   @Nullable
   @NativeType("unsigned char const *")
   public static ByteBuffer glfwGetJoystickButtons(int jid) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();
      IntBuffer count = stack.callocInt(1);

      try {
         long __result = nglfwGetJoystickButtons(jid, MemoryUtil.memAddress(count));
         return MemoryUtil.memByteBufferSafe(__result, count.get(0));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static long nglfwGetJoystickHats(int jid, long count) {
      long __functionAddress = Functions.GetJoystickHats;
      return JNI.invokePP(jid, count, __functionAddress);
   }

   @Nullable
   @NativeType("unsigned char const *")
   public static ByteBuffer glfwGetJoystickHats(int jid) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();
      IntBuffer count = stack.callocInt(1);

      try {
         long __result = nglfwGetJoystickHats(jid, MemoryUtil.memAddress(count));
         return MemoryUtil.memByteBufferSafe(__result, count.get(0));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static long nglfwGetJoystickName(int jid) {
      long __functionAddress = Functions.GetJoystickName;
      return JNI.invokeP(jid, __functionAddress);
   }

   @Nullable
   @NativeType("char const *")
   public static String glfwGetJoystickName(int jid) {
      long __result = nglfwGetJoystickName(jid);
      return MemoryUtil.memUTF8Safe(__result);
   }

   public static long nglfwGetJoystickGUID(int jid) {
      long __functionAddress = Functions.GetJoystickGUID;
      return JNI.invokeP(jid, __functionAddress);
   }

   @Nullable
   @NativeType("char const *")
   public static String glfwGetJoystickGUID(int jid) {
      long __result = nglfwGetJoystickGUID(jid);
      return MemoryUtil.memUTF8Safe(__result);
   }

   public static void glfwSetJoystickUserPointer(int jid, @NativeType("void *") long pointer) {
      long __functionAddress = Functions.SetJoystickUserPointer;
      if (Checks.CHECKS) {
         Checks.check(pointer);
      }

      JNI.invokePV(jid, pointer, __functionAddress);
   }

   @NativeType("void *")
   public static long glfwGetJoystickUserPointer(int jid) {
      long __functionAddress = Functions.GetJoystickUserPointer;
      return JNI.invokeP(jid, __functionAddress);
   }

   @NativeType("int")
   public static boolean glfwJoystickIsGamepad(int jid) {
      long __functionAddress = Functions.JoystickIsGamepad;
      return JNI.invokeI(jid, __functionAddress) != 0;
   }

   public static long nglfwSetJoystickCallback(long cbfun) {
      long __functionAddress = Functions.SetJoystickCallback;
      return JNI.invokePP(cbfun, __functionAddress);
   }

   @Nullable
   @NativeType("GLFWjoystickfun")
   public static GLFWJoystickCallback glfwSetJoystickCallback(@Nullable @NativeType("GLFWjoystickfun") GLFWJoystickCallbackI cbfun) {
      return GLFWJoystickCallback.createSafe(nglfwSetJoystickCallback(MemoryUtil.memAddressSafe(cbfun)));
   }

   public static int nglfwUpdateGamepadMappings(long string) {
      long __functionAddress = Functions.UpdateGamepadMappings;
      return JNI.invokePI(string, __functionAddress);
   }

   @NativeType("int")
   public static boolean glfwUpdateGamepadMappings(@NativeType("char const *") ByteBuffer string) {
      if (Checks.CHECKS) {
         Checks.checkNT1(string);
      }

      return nglfwUpdateGamepadMappings(MemoryUtil.memAddress(string)) != 0;
   }

   public static long nglfwGetGamepadName(int jid) {
      long __functionAddress = Functions.GetGamepadName;
      return JNI.invokeP(jid, __functionAddress);
   }

   @Nullable
   @NativeType("char const *")
   public static String glfwGetGamepadName(int jid) {
      long __result = nglfwGetGamepadName(jid);
      return MemoryUtil.memUTF8Safe(__result);
   }

   public static int nglfwGetGamepadState(int jid, long state) {
      long __functionAddress = Functions.GetGamepadState;
      return JNI.invokePI(jid, state, __functionAddress);
   }

   @NativeType("int")
   public static boolean glfwGetGamepadState(int jid, @NativeType("GLFWgamepadstate *") GLFWGamepadState state) {
      return nglfwGetGamepadState(jid, state.address()) != 0;
   }

   public static void nglfwSetClipboardString(long window, long string) {
      long __functionAddress = Functions.SetClipboardString;
      JNI.invokePPV(window, string, __functionAddress);
   }

   public static void glfwSetClipboardString(@NativeType("GLFWwindow *") long window, @NativeType("char const *") ByteBuffer string) {
      if (Checks.CHECKS) {
         Checks.checkNT1(string);
      }

      nglfwSetClipboardString(window, MemoryUtil.memAddress(string));
   }

   public static void glfwSetClipboardString(@NativeType("GLFWwindow *") long window, @NativeType("char const *") CharSequence string) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(string, true);
         long stringEncoded = stack.getPointerAddress();
         nglfwSetClipboardString(window, stringEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static long nglfwGetClipboardString(long window) {
      long __functionAddress = Functions.GetClipboardString;
      return JNI.invokePP(window, __functionAddress);
   }

   @Nullable
   @NativeType("char const *")
   public static String glfwGetClipboardString(@NativeType("GLFWwindow *") long window) {
      long __result = nglfwGetClipboardString(window);
      return MemoryUtil.memUTF8Safe(__result);
   }

   public static double glfwGetTime() {
      long __functionAddress = Functions.GetTime;
      return JNI.invokeD(__functionAddress);
   }

   public static void glfwSetTime(double time) {
      long __functionAddress = Functions.SetTime;
      JNI.invokeV(time, __functionAddress);
   }

   @NativeType("uint64_t")
   public static long glfwGetTimerValue() {
      long __functionAddress = Functions.GetTimerValue;
      return JNI.invokeJ(__functionAddress);
   }

   @NativeType("uint64_t")
   public static long glfwGetTimerFrequency() {
      long __functionAddress = Functions.GetTimerFrequency;
      return JNI.invokeJ(__functionAddress);
   }

   public static void glfwMakeContextCurrent(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.MakeContextCurrent;
      JNI.invokePV(window, __functionAddress);
   }

   @NativeType("GLFWwindow *")
   public static long glfwGetCurrentContext() {
      long __functionAddress = Functions.GetCurrentContext;
      return JNI.invokeP(__functionAddress);
   }

   public static void glfwSwapBuffers(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.SwapBuffers;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      JNI.invokePV(window, __functionAddress);
   }

   public static void glfwSwapInterval(int interval) {
      long __functionAddress = Functions.SwapInterval;
      JNI.invokeV(interval, __functionAddress);
   }

   public static int nglfwExtensionSupported(long extension) {
      long __functionAddress = Functions.ExtensionSupported;
      return JNI.invokePI(extension, __functionAddress);
   }

   @NativeType("int")
   public static boolean glfwExtensionSupported(@NativeType("char const *") ByteBuffer extension) {
      if (Checks.CHECKS) {
         Checks.checkNT1(extension);
      }

      return nglfwExtensionSupported(MemoryUtil.memAddress(extension)) != 0;
   }

   @NativeType("int")
   public static boolean glfwExtensionSupported(@NativeType("char const *") CharSequence extension) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(extension, true);
         long extensionEncoded = stack.getPointerAddress();
         return nglfwExtensionSupported(extensionEncoded) != 0;
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static long nglfwGetProcAddress(long procname) {
      long __functionAddress = Functions.GetProcAddress;
      return JNI.invokePP(procname, __functionAddress);
   }

   @NativeType("GLFWglproc")
   public static long glfwGetProcAddress(@NativeType("char const *") ByteBuffer procname) {
      if (Checks.CHECKS) {
         Checks.checkNT1(procname);
      }

      return nglfwGetProcAddress(MemoryUtil.memAddress(procname));
   }

   @NativeType("GLFWglproc")
   public static long glfwGetProcAddress(@NativeType("char const *") CharSequence procname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(procname, true);
         long procnameEncoded = stack.getPointerAddress();
         return nglfwGetProcAddress(procnameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glfwGetVersion(
      @Nullable @NativeType("int *") int[] major, @Nullable @NativeType("int *") int[] minor, @Nullable @NativeType("int *") int[] rev
   ) {
      long __functionAddress = Functions.GetVersion;
      if (Checks.CHECKS) {
         Checks.checkSafe(major, 1);
         Checks.checkSafe(minor, 1);
         Checks.checkSafe(rev, 1);
      }

      JNI.invokePPPV(major, minor, rev, __functionAddress);
   }

   public static void glfwGetMonitorPos(
      @NativeType("GLFWmonitor *") long monitor, @Nullable @NativeType("int *") int[] xpos, @Nullable @NativeType("int *") int[] ypos
   ) {
      long __functionAddress = Functions.GetMonitorPos;
      if (Checks.CHECKS) {
         Checks.check(monitor);
         Checks.checkSafe(xpos, 1);
         Checks.checkSafe(ypos, 1);
      }

      JNI.invokePPPV(monitor, xpos, ypos, __functionAddress);
   }

   public static void glfwGetMonitorWorkarea(
      @NativeType("GLFWmonitor *") long monitor,
      @Nullable @NativeType("int *") int[] xpos,
      @Nullable @NativeType("int *") int[] ypos,
      @Nullable @NativeType("int *") int[] width,
      @Nullable @NativeType("int *") int[] height
   ) {
      long __functionAddress = Functions.GetMonitorWorkarea;
      if (Checks.CHECKS) {
         Checks.check(monitor);
         Checks.checkSafe(xpos, 1);
         Checks.checkSafe(ypos, 1);
         Checks.checkSafe(width, 1);
         Checks.checkSafe(height, 1);
      }

      JNI.invokePPPPPV(monitor, xpos, ypos, width, height, __functionAddress);
   }

   public static void glfwGetMonitorPhysicalSize(
      @NativeType("GLFWmonitor *") long monitor, @Nullable @NativeType("int *") int[] widthMM, @Nullable @NativeType("int *") int[] heightMM
   ) {
      long __functionAddress = Functions.GetMonitorPhysicalSize;
      if (Checks.CHECKS) {
         Checks.check(monitor);
         Checks.checkSafe(widthMM, 1);
         Checks.checkSafe(heightMM, 1);
      }

      JNI.invokePPPV(monitor, widthMM, heightMM, __functionAddress);
   }

   public static void glfwGetMonitorContentScale(
      @NativeType("GLFWmonitor *") long monitor, @Nullable @NativeType("float *") float[] xscale, @Nullable @NativeType("float *") float[] yscale
   ) {
      long __functionAddress = Functions.GetMonitorContentScale;
      if (Checks.CHECKS) {
         Checks.check(monitor);
         Checks.checkSafe(xscale, 1);
         Checks.checkSafe(yscale, 1);
      }

      JNI.invokePPPV(monitor, xscale, yscale, __functionAddress);
   }

   public static void glfwGetWindowPos(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("int *") int[] xpos, @Nullable @NativeType("int *") int[] ypos
   ) {
      long __functionAddress = Functions.GetWindowPos;
      if (Checks.CHECKS) {
         Checks.check(window);
         Checks.checkSafe(xpos, 1);
         Checks.checkSafe(ypos, 1);
      }

      JNI.invokePPPV(window, xpos, ypos, __functionAddress);
   }

   public static void glfwGetWindowSize(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("int *") int[] width, @Nullable @NativeType("int *") int[] height
   ) {
      long __functionAddress = Functions.GetWindowSize;
      if (Checks.CHECKS) {
         Checks.check(window);
         Checks.checkSafe(width, 1);
         Checks.checkSafe(height, 1);
      }

      JNI.invokePPPV(window, width, height, __functionAddress);
   }

   public static void glfwGetFramebufferSize(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("int *") int[] width, @Nullable @NativeType("int *") int[] height
   ) {
      long __functionAddress = Functions.GetFramebufferSize;
      if (Checks.CHECKS) {
         Checks.check(window);
         Checks.checkSafe(width, 1);
         Checks.checkSafe(height, 1);
      }

      JNI.invokePPPV(window, width, height, __functionAddress);
   }

   public static void glfwGetWindowFrameSize(
      @NativeType("GLFWwindow *") long window,
      @Nullable @NativeType("int *") int[] left,
      @Nullable @NativeType("int *") int[] top,
      @Nullable @NativeType("int *") int[] right,
      @Nullable @NativeType("int *") int[] bottom
   ) {
      long __functionAddress = Functions.GetWindowFrameSize;
      if (Checks.CHECKS) {
         Checks.check(window);
         Checks.checkSafe(left, 1);
         Checks.checkSafe(top, 1);
         Checks.checkSafe(right, 1);
         Checks.checkSafe(bottom, 1);
      }

      JNI.invokePPPPPV(window, left, top, right, bottom, __functionAddress);
   }

   public static void glfwGetWindowContentScale(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("float *") float[] xscale, @Nullable @NativeType("float *") float[] yscale
   ) {
      long __functionAddress = Functions.GetWindowContentScale;
      if (Checks.CHECKS) {
         Checks.check(window);
         Checks.checkSafe(xscale, 1);
         Checks.checkSafe(yscale, 1);
      }

      JNI.invokePPPV(window, xscale, yscale, __functionAddress);
   }

   public static void glfwGetCursorPos(
      @NativeType("GLFWwindow *") long window, @Nullable @NativeType("double *") double[] xpos, @Nullable @NativeType("double *") double[] ypos
   ) {
      long __functionAddress = Functions.GetCursorPos;
      if (Checks.CHECKS) {
         Checks.check(window);
         Checks.checkSafe(xpos, 1);
         Checks.checkSafe(ypos, 1);
      }

      JNI.invokePPPV(window, xpos, ypos, __functionAddress);
   }

   public static final class Functions {
      public static final long Init = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwInit");
      public static final long Terminate = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwTerminate");
      public static final long InitHint = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwInitHint");
      public static final long InitAllocator = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwInitAllocator");
      public static final long GetVersion = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetVersion");
      public static final long GetVersionString = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetVersionString");
      public static final long GetError = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetError");
      public static final long SetErrorCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetErrorCallback");
      public static final long GetPlatform = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetPlatform");
      public static final long PlatformSupported = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwPlatformSupported");
      public static final long GetMonitors = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetMonitors");
      public static final long GetPrimaryMonitor = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetPrimaryMonitor");
      public static final long GetMonitorPos = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetMonitorPos");
      public static final long GetMonitorWorkarea = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetMonitorWorkarea");
      public static final long GetMonitorPhysicalSize = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetMonitorPhysicalSize");
      public static final long GetMonitorContentScale = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetMonitorContentScale");
      public static final long GetMonitorName = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetMonitorName");
      public static final long SetMonitorUserPointer = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetMonitorUserPointer");
      public static final long GetMonitorUserPointer = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetMonitorUserPointer");
      public static final long SetMonitorCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetMonitorCallback");
      public static final long GetVideoModes = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetVideoModes");
      public static final long GetVideoMode = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetVideoMode");
      public static final long SetGamma = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetGamma");
      public static final long GetGammaRamp = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetGammaRamp");
      public static final long SetGammaRamp = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetGammaRamp");
      public static final long DefaultWindowHints = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwDefaultWindowHints");
      public static final long WindowHint = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwWindowHint");
      public static final long WindowHintString = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwWindowHintString");
      public static final long CreateWindow = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwCreateWindow");
      public static final long DestroyWindow = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwDestroyWindow");
      public static final long WindowShouldClose = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwWindowShouldClose");
      public static final long SetWindowShouldClose = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowShouldClose");
      public static final long SetWindowTitle = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowTitle");
      public static final long SetWindowIcon = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowIcon");
      public static final long GetWindowPos = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetWindowPos");
      public static final long SetWindowPos = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowPos");
      public static final long GetWindowSize = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetWindowSize");
      public static final long SetWindowSizeLimits = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowSizeLimits");
      public static final long SetWindowAspectRatio = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowAspectRatio");
      public static final long SetWindowSize = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowSize");
      public static final long GetFramebufferSize = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetFramebufferSize");
      public static final long GetWindowFrameSize = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetWindowFrameSize");
      public static final long GetWindowContentScale = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetWindowContentScale");
      public static final long GetWindowOpacity = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetWindowOpacity");
      public static final long SetWindowOpacity = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowOpacity");
      public static final long IconifyWindow = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwIconifyWindow");
      public static final long RestoreWindow = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwRestoreWindow");
      public static final long MaximizeWindow = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwMaximizeWindow");
      public static final long ShowWindow = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwShowWindow");
      public static final long HideWindow = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwHideWindow");
      public static final long FocusWindow = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwFocusWindow");
      public static final long RequestWindowAttention = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwRequestWindowAttention");
      public static final long GetWindowMonitor = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetWindowMonitor");
      public static final long SetWindowMonitor = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowMonitor");
      public static final long GetWindowAttrib = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetWindowAttrib");
      public static final long SetWindowAttrib = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowAttrib");
      public static final long SetWindowUserPointer = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowUserPointer");
      public static final long GetWindowUserPointer = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetWindowUserPointer");
      public static final long SetWindowPosCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowPosCallback");
      public static final long SetWindowSizeCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowSizeCallback");
      public static final long SetWindowCloseCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowCloseCallback");
      public static final long SetWindowRefreshCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowRefreshCallback");
      public static final long SetWindowFocusCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowFocusCallback");
      public static final long SetWindowIconifyCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowIconifyCallback");
      public static final long SetWindowMaximizeCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowMaximizeCallback");
      public static final long SetFramebufferSizeCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetFramebufferSizeCallback");
      public static final long SetWindowContentScaleCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetWindowContentScaleCallback");
      public static final long PollEvents = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwPollEvents");
      public static final long WaitEvents = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwWaitEvents");
      public static final long WaitEventsTimeout = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwWaitEventsTimeout");
      public static final long PostEmptyEvent = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwPostEmptyEvent");
      public static final long GetInputMode = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetInputMode");
      public static final long SetInputMode = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetInputMode");
      public static final long RawMouseMotionSupported = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwRawMouseMotionSupported");
      public static final long GetKeyName = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetKeyName");
      public static final long GetKeyScancode = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetKeyScancode");
      public static final long GetKey = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetKey");
      public static final long GetMouseButton = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetMouseButton");
      public static final long GetCursorPos = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetCursorPos");
      public static final long SetCursorPos = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetCursorPos");
      public static final long CreateCursor = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwCreateCursor");
      public static final long CreateStandardCursor = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwCreateStandardCursor");
      public static final long DestroyCursor = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwDestroyCursor");
      public static final long SetCursor = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetCursor");
      public static final long SetKeyCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetKeyCallback");
      public static final long SetCharCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetCharCallback");
      public static final long SetCharModsCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetCharModsCallback");
      public static final long SetMouseButtonCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetMouseButtonCallback");
      public static final long SetCursorPosCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetCursorPosCallback");
      public static final long SetCursorEnterCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetCursorEnterCallback");
      public static final long SetScrollCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetScrollCallback");
      public static final long SetDropCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetDropCallback");
      public static final long JoystickPresent = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwJoystickPresent");
      public static final long GetJoystickAxes = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetJoystickAxes");
      public static final long GetJoystickButtons = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetJoystickButtons");
      public static final long GetJoystickHats = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetJoystickHats");
      public static final long GetJoystickName = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetJoystickName");
      public static final long GetJoystickGUID = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetJoystickGUID");
      public static final long SetJoystickUserPointer = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetJoystickUserPointer");
      public static final long GetJoystickUserPointer = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetJoystickUserPointer");
      public static final long JoystickIsGamepad = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwJoystickIsGamepad");
      public static final long SetJoystickCallback = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetJoystickCallback");
      public static final long UpdateGamepadMappings = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwUpdateGamepadMappings");
      public static final long GetGamepadName = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetGamepadName");
      public static final long GetGamepadState = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetGamepadState");
      public static final long SetClipboardString = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetClipboardString");
      public static final long GetClipboardString = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetClipboardString");
      public static final long GetTime = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetTime");
      public static final long SetTime = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSetTime");
      public static final long GetTimerValue = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetTimerValue");
      public static final long GetTimerFrequency = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetTimerFrequency");
      public static final long MakeContextCurrent = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwMakeContextCurrent");
      public static final long GetCurrentContext = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetCurrentContext");
      public static final long SwapBuffers = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSwapBuffers");
      public static final long SwapInterval = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwSwapInterval");
      public static final long ExtensionSupported = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwExtensionSupported");
      public static final long GetProcAddress = APIUtil.apiGetFunctionAddress(org.lwjgl.glfw.GLFW.GLFW, "glfwGetProcAddress");

      private Functions() {
      }
   }
}
