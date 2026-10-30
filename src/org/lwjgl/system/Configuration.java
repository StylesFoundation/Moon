package org.lwjgl.system;

import java.util.function.Function;
import javax.annotation.Nullable;

public class Configuration<T> {
   public static final Configuration<String> LIBRARY_PATH = new Configuration<>("org.lwjgl.librarypath", Configuration.StateInit.STRING);
   public static final Configuration<Object> BUNDLED_LIBRARY_NAME_MAPPER = new Configuration<>(
      "org.lwjgl.system.bundledLibrary.nameMapper", Configuration.StateInit.STRING
   );
   public static final Configuration<Object> BUNDLED_LIBRARY_PATH_MAPPER = new Configuration<>(
      "org.lwjgl.system.bundledLibrary.pathMapper", Configuration.StateInit.STRING
   );
   public static final Configuration<String> SHARED_LIBRARY_EXTRACT_DIRECTORY = new Configuration<>(
      "org.lwjgl.system.SharedLibraryExtractDirectory", Configuration.StateInit.STRING
   );
   public static final Configuration<String> SHARED_LIBRARY_EXTRACT_PATH = new Configuration<>(
      "org.lwjgl.system.SharedLibraryExtractPath", Configuration.StateInit.STRING
   );
   public static final Configuration<Boolean> EMULATE_SYSTEM_LOADLIBRARY = new Configuration<>(
      "org.lwjgl.system.EmulateSystemLoadLibrary", Configuration.StateInit.BOOLEAN
   );
   public static final Configuration<String> LIBRARY_NAME = new Configuration<>("org.lwjgl.libname", Configuration.StateInit.STRING);
   public static final Configuration<Object> MEMORY_ALLOCATOR = new Configuration<>("org.lwjgl.system.allocator", Configuration.StateInit.STRING);
   public static final Configuration<Integer> STACK_SIZE = new Configuration<>("org.lwjgl.system.stackSize", Configuration.StateInit.INT);
   public static final Configuration<Integer> ARRAY_TLC_SIZE = new Configuration<>("org.lwjgl.system.arrayTLCSize", Configuration.StateInit.INT);
   public static final Configuration<Boolean> DISABLE_CHECKS = new Configuration<>("org.lwjgl.util.NoChecks", Configuration.StateInit.BOOLEAN);
   public static final Configuration<Boolean> DISABLE_FUNCTION_CHECKS = new Configuration<>("org.lwjgl.util.NoFunctionChecks", Configuration.StateInit.BOOLEAN);
   public static final Configuration<Boolean> DEBUG = new Configuration<>("org.lwjgl.util.Debug", Configuration.StateInit.BOOLEAN);
   public static final Configuration<Boolean> DEBUG_LOADER = new Configuration<>("org.lwjgl.util.DebugLoader", Configuration.StateInit.BOOLEAN);
   public static final Configuration<Object> DEBUG_STREAM = new Configuration<>("org.lwjgl.util.DebugStream", Configuration.StateInit.STRING);
   public static final Configuration<Boolean> DEBUG_MEMORY_ALLOCATOR = new Configuration<>("org.lwjgl.util.DebugAllocator", Configuration.StateInit.BOOLEAN);
   public static final Configuration<Boolean> DEBUG_MEMORY_ALLOCATOR_INTERNAL = new Configuration<>(
      "org.lwjgl.util.DebugAllocator.internal", Configuration.StateInit.BOOLEAN
   );
   public static final Configuration<Boolean> DEBUG_STACK = new Configuration<>("org.lwjgl.util.DebugStack", Configuration.StateInit.BOOLEAN);
   public static final Configuration<Boolean> DEBUG_FUNCTIONS = new Configuration<>("org.lwjgl.util.DebugFunctions", Configuration.StateInit.BOOLEAN);
   public static final Configuration<String> ASSIMP_LIBRARY_NAME = new Configuration<>("org.lwjgl.assimp.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> BGFX_LIBRARY_NAME = new Configuration<>("org.lwjgl.bgfx.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> CUDA_LIBRARY_NAME = new Configuration<>("org.lwjgl.cuda.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> CUDA_TOOLKIT_VERSION = new Configuration<>("org.lwjgl.cuda.toolkit.version", Configuration.StateInit.STRING);
   public static final Configuration<String> CUDA_TOOLKIT_PATH = new Configuration<>("org.lwjgl.cuda.toolkit.path", Configuration.StateInit.STRING);
   public static final Configuration<String> CUDA_NVRTC_LIBRARY_NAME = new Configuration<>("org.lwjgl.cuda.nvrtc.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> CUDA_NVRTC_BUILTINS_LIBRARY_NAME = new Configuration<>(
      "org.lwjgl.cuda.nvrtc-builtins.libname", Configuration.StateInit.STRING
   );
   public static final Configuration<Boolean> CUDA_API_PER_THREAD_DEFAULT_STREAM = new Configuration<>("org.lwjgl.cuda.ptds", Configuration.StateInit.BOOLEAN);
   public static final Configuration<Boolean> EGL_EXPLICIT_INIT = new Configuration<>("org.lwjgl.egl.explicitInit", Configuration.StateInit.BOOLEAN);
   public static final Configuration<String> EGL_LIBRARY_NAME = new Configuration<>("org.lwjgl.egl.libname", Configuration.StateInit.STRING);
   public static final Configuration<Object> EGL_EXTENSION_FILTER = new Configuration<>("org.lwjgl.egl.extensionFilter", Configuration.StateInit.STRING);
   public static final Configuration<String> GLFW_LIBRARY_NAME = new Configuration<>("org.lwjgl.glfw.libname", Configuration.StateInit.STRING);
   public static final Configuration<Boolean> GLFW_CHECK_THREAD0 = new Configuration<>("org.lwjgl.glfw.checkThread0", Configuration.StateInit.BOOLEAN);
   public static final Configuration<String> JAWT_LIBRARY_NAME = new Configuration<>("org.lwjgl.system.jawt.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> JEMALLOC_LIBRARY_NAME = new Configuration<>("org.lwjgl.system.jemalloc.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> LLVM_LIBRARY_NAME = new Configuration<>("org.lwjgl.llvm.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> LLVM_CLANG_LIBRARY_NAME = new Configuration<>("org.lwjgl.llvm.clang.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> LLVM_LTO_LIBRARY_NAME = new Configuration<>("org.lwjgl.llvm.lto.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> ODBC_LIBRARY_NAME = new Configuration<>("org.lwjgl.odbc.libname", Configuration.StateInit.STRING);
   public static final Configuration<Boolean> OPENAL_EXPLICIT_INIT = new Configuration<>("org.lwjgl.openal.explicitInit", Configuration.StateInit.BOOLEAN);
   public static final Configuration<String> OPENAL_LIBRARY_NAME = new Configuration<>("org.lwjgl.openal.libname", Configuration.StateInit.STRING);
   public static final Configuration<Object> OPENAL_EXTENSION_FILTER = new Configuration<>("org.lwjgl.openal.extensionFilter", Configuration.StateInit.STRING);
   public static final Configuration<Boolean> OPENCL_EXPLICIT_INIT = new Configuration<>("org.lwjgl.opencl.explicitInit", Configuration.StateInit.BOOLEAN);
   public static final Configuration<String> OPENCL_LIBRARY_NAME = new Configuration<>("org.lwjgl.opencl.libname", Configuration.StateInit.STRING);
   public static final Configuration<Object> OPENCL_EXTENSION_FILTER = new Configuration<>("org.lwjgl.opencl.extensionFilter", Configuration.StateInit.STRING);
   public static final Configuration<Boolean> OPENGL_EXPLICIT_INIT = new Configuration<>("org.lwjgl.opengl.explicitInit", Configuration.StateInit.BOOLEAN);
   public static final Configuration<String> OPENGL_LIBRARY_NAME = new Configuration<>("org.lwjgl.opengl.libname", Configuration.StateInit.STRING);
   public static final Configuration<Object> OPENGL_MAXVERSION = new Configuration<>("org.lwjgl.opengl.maxVersion", Configuration.StateInit.STRING);
   public static final Configuration<Object> OPENGL_EXTENSION_FILTER = new Configuration<>("org.lwjgl.opengl.extensionFilter", Configuration.StateInit.STRING);
   public static final Configuration<Boolean> OPENGLES_EXPLICIT_INIT = new Configuration<>("org.lwjgl.opengles.explicitInit", Configuration.StateInit.BOOLEAN);
   public static final Configuration<String> OPENGLES_LIBRARY_NAME = new Configuration<>("org.lwjgl.opengles.libname", Configuration.StateInit.STRING);
   public static final Configuration<Object> OPENGLES_MAXVERSION = new Configuration<>("org.lwjgl.opengles.maxVersion", Configuration.StateInit.STRING);
   public static final Configuration<Object> OPENGLES_EXTENSION_FILTER = new Configuration<>(
      "org.lwjgl.opengles.extensionFilter", Configuration.StateInit.STRING
   );
   public static final Configuration<String> OPENGLES_CONTEXT_API = new Configuration<>("org.lwjgl.opengles.contextAPI", Configuration.StateInit.STRING);
   public static final Configuration<String> OPENVR_LIBRARY_NAME = new Configuration<>("org.lwjgl.openvr.libname", Configuration.StateInit.STRING);
   public static final Configuration<Boolean> OPENXR_EXPLICIT_INIT = new Configuration<>("org.lwjgl.openxr.explicitInit", Configuration.StateInit.BOOLEAN);
   public static final Configuration<String> OPENXR_LIBRARY_NAME = new Configuration<>("org.lwjgl.openxr.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> OPUS_LIBRARY_NAME = new Configuration<>("org.lwjgl.opus.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> SHADERC_LIBRARY_NAME = new Configuration<>("org.lwjgl.shaderc.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> SHADERC_SPVC_LIBRARY_NAME = new Configuration<>("org.lwjgl.shaderc.spvc.libname", Configuration.StateInit.STRING);
   public static final Configuration<String> SPVC_LIBRARY_NAME = new Configuration<>("org.lwjgl.spvc.libname", Configuration.StateInit.STRING);
   public static final Configuration<Boolean> VULKAN_EXPLICIT_INIT = new Configuration<>("org.lwjgl.vulkan.explicitInit", Configuration.StateInit.BOOLEAN);
   public static final Configuration<String> VULKAN_LIBRARY_NAME = new Configuration<>("org.lwjgl.vulkan.libname", Configuration.StateInit.STRING);
   private final String property;
   @Nullable
   private T state;

   Configuration(String property, Configuration.StateInit<? extends T> init) {
      this.property = property;
      this.state = (T)init.apply(property);
   }

   public String getProperty() {
      return this.property;
   }

   public void set(@Nullable T value) {
      this.state = value;
   }

   @Nullable
   public T get() {
      return this.state;
   }

   public T get(T defaultValue) {
      T state = this.state;
      if (state == null) {
         state = defaultValue;
      }

      return state;
   }

   private interface StateInit<T> extends Function<String, T> {
      Configuration.StateInit<Boolean> BOOLEAN = property -> {
         String value = System.getProperty(property);
         return value == null ? null : Boolean.parseBoolean(value);
      };
      Configuration.StateInit<Integer> INT = Integer::getInteger;
      Configuration.StateInit<String> STRING = System::getProperty;
   }
}
