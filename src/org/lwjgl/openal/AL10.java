package org.lwjgl.openal;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class AL10 {
   public static final int AL_INVALID = -1;
   public static final int AL_NONE = 0;
   public static final int AL_FALSE = 0;
   public static final int AL_TRUE = 1;
   public static final int AL_NO_ERROR = 0;
   public static final int AL_INVALID_NAME = 40961;
   public static final int AL_INVALID_ENUM = 40962;
   public static final int AL_INVALID_VALUE = 40963;
   public static final int AL_INVALID_OPERATION = 40964;
   public static final int AL_OUT_OF_MEMORY = 40965;
   public static final int AL_DOPPLER_FACTOR = 49152;
   public static final int AL_DISTANCE_MODEL = 53248;
   public static final int AL_VENDOR = 45057;
   public static final int AL_VERSION = 45058;
   public static final int AL_RENDERER = 45059;
   public static final int AL_EXTENSIONS = 45060;
   public static final int AL_INVERSE_DISTANCE = 53249;
   public static final int AL_INVERSE_DISTANCE_CLAMPED = 53250;
   public static final int AL_SOURCE_ABSOLUTE = 513;
   public static final int AL_SOURCE_RELATIVE = 514;
   public static final int AL_POSITION = 4100;
   public static final int AL_VELOCITY = 4102;
   public static final int AL_GAIN = 4106;
   public static final int AL_CONE_INNER_ANGLE = 4097;
   public static final int AL_CONE_OUTER_ANGLE = 4098;
   public static final int AL_PITCH = 4099;
   public static final int AL_DIRECTION = 4101;
   public static final int AL_LOOPING = 4103;
   public static final int AL_BUFFER = 4105;
   public static final int AL_SOURCE_STATE = 4112;
   public static final int AL_CONE_OUTER_GAIN = 4130;
   public static final int AL_SOURCE_TYPE = 4135;
   public static final int AL_INITIAL = 4113;
   public static final int AL_PLAYING = 4114;
   public static final int AL_PAUSED = 4115;
   public static final int AL_STOPPED = 4116;
   public static final int AL_ORIENTATION = 4111;
   public static final int AL_BUFFERS_QUEUED = 4117;
   public static final int AL_BUFFERS_PROCESSED = 4118;
   public static final int AL_MIN_GAIN = 4109;
   public static final int AL_MAX_GAIN = 4110;
   public static final int AL_REFERENCE_DISTANCE = 4128;
   public static final int AL_ROLLOFF_FACTOR = 4129;
   public static final int AL_MAX_DISTANCE = 4131;
   public static final int AL_FREQUENCY = 8193;
   public static final int AL_BITS = 8194;
   public static final int AL_CHANNELS = 8195;
   public static final int AL_SIZE = 8196;
   public static final int AL_FORMAT_MONO8 = 4352;
   public static final int AL_FORMAT_MONO16 = 4353;
   public static final int AL_FORMAT_STEREO8 = 4354;
   public static final int AL_FORMAT_STEREO16 = 4355;
   public static final int AL_UNUSED = 8208;
   public static final int AL_PENDING = 8209;
   public static final int AL_PROCESSED = 8210;

   public static void alGetDouble(int p1, DoubleBuffer p2) {
      alGetDoublev(p1, p2);
   }

   public static void alGetFloat(int p1, FloatBuffer p2) {
      alGetFloatv(p1, p2);
   }

   public static void alGetInteger(int p1, IntBuffer p2) {
      alGetIntegerv(p1, p2);
   }

   public static void alGetListener(int p1, FloatBuffer p2) {
      alGetListenerfv(p1, p2);
   }

   public static void alGetSource(int p1, int p2, FloatBuffer p3) {
      alGetSourcefv(p1, p2, p3);
   }

   public static void alListener(int pname, FloatBuffer value) {
      alListenerfv(pname, value);
   }

   public static void alSource(int p1, int p2, FloatBuffer p3) {
      alSourcefv(p1, p2, p3);
   }

   public static void alSourcePause(IntBuffer p1) {
      alSourcePausev(p1);
   }

   public static void alSourcePlay(IntBuffer p1) {
      alSourcePlayv(p1);
   }

   public static void alSourceRewind(IntBuffer p1) {
      alSourceRewindv(p1);
   }

   public static void alSourceStop(IntBuffer p1) {
      alSourceStopv(p1);
   }

   protected AL10() {
      throw new UnsupportedOperationException();
   }

   static boolean isAvailable(ALCapabilities caps) {
      return Checks.checkFunctions(
         caps.alGetError,
         caps.alEnable,
         caps.alDisable,
         caps.alIsEnabled,
         caps.alGetBoolean,
         caps.alGetInteger,
         caps.alGetFloat,
         caps.alGetDouble,
         caps.alGetBooleanv,
         caps.alGetIntegerv,
         caps.alGetFloatv,
         caps.alGetDoublev,
         caps.alGetString,
         caps.alDistanceModel,
         caps.alDopplerFactor,
         caps.alDopplerVelocity,
         caps.alListenerf,
         caps.alListeneri,
         caps.alListener3f,
         caps.alListenerfv,
         caps.alGetListenerf,
         caps.alGetListeneri,
         caps.alGetListener3f,
         caps.alGetListenerfv,
         caps.alGenSources,
         caps.alDeleteSources,
         caps.alIsSource,
         caps.alSourcef,
         caps.alSource3f,
         caps.alSourcefv,
         caps.alSourcei,
         caps.alGetSourcef,
         caps.alGetSource3f,
         caps.alGetSourcefv,
         caps.alGetSourcei,
         caps.alGetSourceiv,
         caps.alSourceQueueBuffers,
         caps.alSourceUnqueueBuffers,
         caps.alSourcePlay,
         caps.alSourcePause,
         caps.alSourceStop,
         caps.alSourceRewind,
         caps.alSourcePlayv,
         caps.alSourcePausev,
         caps.alSourceStopv,
         caps.alSourceRewindv,
         caps.alGenBuffers,
         caps.alDeleteBuffers,
         caps.alIsBuffer,
         caps.alGetBufferf,
         caps.alGetBufferi,
         caps.alBufferData,
         caps.alGetEnumValue,
         caps.alGetProcAddress,
         caps.alIsExtensionPresent
      );
   }

   @NativeType("ALenum")
   public static int alGetError() {
      long __functionAddress = AL.getICD().alGetError;
      return JNI.invokeI(__functionAddress);
   }

   @NativeType("ALvoid")
   public static void alEnable(@NativeType("ALenum") int target) {
      long __functionAddress = AL.getICD().alEnable;
      JNI.invokeV(target, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alDisable(@NativeType("ALenum") int target) {
      long __functionAddress = AL.getICD().alDisable;
      JNI.invokeV(target, __functionAddress);
   }

   @NativeType("ALboolean")
   public static boolean alIsEnabled(@NativeType("ALenum") int target) {
      long __functionAddress = AL.getICD().alIsEnabled;
      return JNI.invokeZ(target, __functionAddress);
   }

   @NativeType("ALboolean")
   public static boolean alGetBoolean(@NativeType("ALenum") int paramName) {
      long __functionAddress = AL.getICD().alGetBoolean;
      return JNI.invokeZ(paramName, __functionAddress);
   }

   @NativeType("ALint")
   public static int alGetInteger(@NativeType("ALenum") int paramName) {
      long __functionAddress = AL.getICD().alGetInteger;
      return JNI.invokeI(paramName, __functionAddress);
   }

   @NativeType("ALfloat")
   public static float alGetFloat(@NativeType("ALenum") int paramName) {
      long __functionAddress = AL.getICD().alGetFloat;
      return JNI.invokeF(paramName, __functionAddress);
   }

   @NativeType("ALdouble")
   public static double alGetDouble(@NativeType("ALenum") int paramName) {
      long __functionAddress = AL.getICD().alGetDouble;
      return JNI.invokeD(paramName, __functionAddress);
   }

   public static void nalGetBooleanv(int paramName, long dest) {
      long __functionAddress = AL.getICD().alGetBooleanv;
      JNI.invokePV(paramName, dest, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetBooleanv(@NativeType("ALenum") int paramName, @NativeType("ALboolean *") ByteBuffer dest) {
      if (Checks.CHECKS) {
         Checks.check(dest, 1);
      }

      nalGetBooleanv(paramName, MemoryUtil.memAddress(dest));
   }

   public static void nalGetIntegerv(int paramName, long dest) {
      long __functionAddress = AL.getICD().alGetIntegerv;
      JNI.invokePV(paramName, dest, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetIntegerv(@NativeType("ALenum") int paramName, @NativeType("ALint *") IntBuffer dest) {
      if (Checks.CHECKS) {
         Checks.check(dest, 1);
      }

      nalGetIntegerv(paramName, MemoryUtil.memAddress(dest));
   }

   public static void nalGetFloatv(int paramName, long dest) {
      long __functionAddress = AL.getICD().alGetFloatv;
      JNI.invokePV(paramName, dest, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetFloatv(@NativeType("ALenum") int paramName, @NativeType("ALfloat *") FloatBuffer dest) {
      if (Checks.CHECKS) {
         Checks.check(dest, 1);
      }

      nalGetFloatv(paramName, MemoryUtil.memAddress(dest));
   }

   public static void nalGetDoublev(int paramName, long dest) {
      long __functionAddress = AL.getICD().alGetDoublev;
      JNI.invokePV(paramName, dest, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetDoublev(@NativeType("ALenum") int paramName, @NativeType("ALdouble *") DoubleBuffer dest) {
      if (Checks.CHECKS) {
         Checks.check(dest, 1);
      }

      nalGetDoublev(paramName, MemoryUtil.memAddress(dest));
   }

   public static long nalGetString(int paramName) {
      long __functionAddress = AL.getICD().alGetString;
      return JNI.invokeP(paramName, __functionAddress);
   }

   @Nullable
   @NativeType("ALchar const *")
   public static String alGetString(@NativeType("ALenum") int paramName) {
      long __result = nalGetString(paramName);
      return MemoryUtil.memUTF8Safe(__result);
   }

   @NativeType("ALvoid")
   public static void alDistanceModel(@NativeType("ALenum") int modelName) {
      long __functionAddress = AL.getICD().alDistanceModel;
      JNI.invokeV(modelName, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alDopplerFactor(@NativeType("ALfloat") float dopplerFactor) {
      long __functionAddress = AL.getICD().alDopplerFactor;
      JNI.invokeV(dopplerFactor, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alDopplerVelocity(@NativeType("ALfloat") float dopplerVelocity) {
      long __functionAddress = AL.getICD().alDopplerVelocity;
      JNI.invokeV(dopplerVelocity, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alListenerf(@NativeType("ALenum") int paramName, @NativeType("ALfloat") float value) {
      long __functionAddress = AL.getICD().alListenerf;
      JNI.invokeV(paramName, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alListeneri(@NativeType("ALenum") int paramName, @NativeType("ALint") int values) {
      long __functionAddress = AL.getICD().alListeneri;
      JNI.invokeV(paramName, values, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alListener3f(
      @NativeType("ALenum") int paramName, @NativeType("ALfloat") float value1, @NativeType("ALfloat") float value2, @NativeType("ALfloat") float value3
   ) {
      long __functionAddress = AL.getICD().alListener3f;
      JNI.invokeV(paramName, value1, value2, value3, __functionAddress);
   }

   public static void nalListenerfv(int paramName, long values) {
      long __functionAddress = AL.getICD().alListenerfv;
      JNI.invokePV(paramName, values, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alListenerfv(@NativeType("ALenum") int paramName, @NativeType("ALfloat const *") FloatBuffer values) {
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      nalListenerfv(paramName, MemoryUtil.memAddress(values));
   }

   public static void nalGetListenerf(int paramName, long value) {
      long __functionAddress = AL.getICD().alGetListenerf;
      JNI.invokePV(paramName, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetListenerf(@NativeType("ALenum") int paramName, @NativeType("ALfloat *") FloatBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nalGetListenerf(paramName, MemoryUtil.memAddress(value));
   }

   @NativeType("ALvoid")
   public static float alGetListenerf(@NativeType("ALenum") int paramName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer value = stack.callocFloat(1);
         nalGetListenerf(paramName, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void nalGetListeneri(int paramName, long value) {
      long __functionAddress = AL.getICD().alGetListeneri;
      JNI.invokePV(paramName, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetListeneri(@NativeType("ALenum") int paramName, @NativeType("ALint *") IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nalGetListeneri(paramName, MemoryUtil.memAddress(value));
   }

   @NativeType("ALvoid")
   public static int alGetListeneri(@NativeType("ALenum") int paramName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer value = stack.callocInt(1);
         nalGetListeneri(paramName, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void nalGetListener3f(int paramName, long value1, long value2, long value3) {
      long __functionAddress = AL.getICD().alGetListener3f;
      JNI.invokePPPV(paramName, value1, value2, value3, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetListener3f(
      @NativeType("ALenum") int paramName,
      @NativeType("ALfloat *") FloatBuffer value1,
      @NativeType("ALfloat *") FloatBuffer value2,
      @NativeType("ALfloat *") FloatBuffer value3
   ) {
      if (Checks.CHECKS) {
         Checks.check(value1, 1);
         Checks.check(value2, 1);
         Checks.check(value3, 1);
      }

      nalGetListener3f(paramName, MemoryUtil.memAddress(value1), MemoryUtil.memAddress(value2), MemoryUtil.memAddress(value3));
   }

   public static void nalGetListenerfv(int paramName, long values) {
      long __functionAddress = AL.getICD().alGetListenerfv;
      JNI.invokePV(paramName, values, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetListenerfv(@NativeType("ALenum") int paramName, @NativeType("ALfloat *") FloatBuffer values) {
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      nalGetListenerfv(paramName, MemoryUtil.memAddress(values));
   }

   public static void nalGenSources(int n, long srcNames) {
      long __functionAddress = AL.getICD().alGenSources;
      JNI.invokePV(n, srcNames, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGenSources(@NativeType("ALuint *") IntBuffer srcNames) {
      nalGenSources(srcNames.remaining(), MemoryUtil.memAddress(srcNames));
   }

   @NativeType("ALvoid")
   public static int alGenSources() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer srcNames = stack.callocInt(1);
         nalGenSources(1, MemoryUtil.memAddress(srcNames));
         return srcNames.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void nalDeleteSources(int n, long sources) {
      long __functionAddress = AL.getICD().alDeleteSources;
      JNI.invokePV(n, sources, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alDeleteSources(@NativeType("ALuint *") IntBuffer sources) {
      nalDeleteSources(sources.remaining(), MemoryUtil.memAddress(sources));
   }

   @NativeType("ALvoid")
   public static void alDeleteSources(@NativeType("ALuint *") int source) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer sources = stack.ints(source);
         nalDeleteSources(1, MemoryUtil.memAddress(sources));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("ALboolean")
   public static boolean alIsSource(@NativeType("ALuint") int sourceName) {
      long __functionAddress = AL.getICD().alIsSource;
      return JNI.invokeZ(sourceName, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourcef(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALfloat") float value) {
      long __functionAddress = AL.getICD().alSourcef;
      JNI.invokeV(source, param, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSource3f(
      @NativeType("ALuint") int source,
      @NativeType("ALenum") int param,
      @NativeType("ALfloat") float v1,
      @NativeType("ALfloat") float v2,
      @NativeType("ALfloat") float v3
   ) {
      long __functionAddress = AL.getICD().alSource3f;
      JNI.invokeV(source, param, v1, v2, v3, __functionAddress);
   }

   public static void nalSourcefv(int source, int param, long values) {
      long __functionAddress = AL.getICD().alSourcefv;
      JNI.invokePV(source, param, values, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourcefv(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALfloat const *") FloatBuffer values) {
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      nalSourcefv(source, param, MemoryUtil.memAddress(values));
   }

   @NativeType("ALvoid")
   public static void alSourcei(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALint") int value) {
      long __functionAddress = AL.getICD().alSourcei;
      JNI.invokeV(source, param, value, __functionAddress);
   }

   public static void nalGetSourcef(int source, int param, long value) {
      long __functionAddress = AL.getICD().alGetSourcef;
      JNI.invokePV(source, param, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetSourcef(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALfloat *") FloatBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nalGetSourcef(source, param, MemoryUtil.memAddress(value));
   }

   @NativeType("ALvoid")
   public static float alGetSourcef(@NativeType("ALuint") int source, @NativeType("ALenum") int param) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer value = stack.callocFloat(1);
         nalGetSourcef(source, param, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void nalGetSource3f(int source, int param, long v1, long v2, long v3) {
      long __functionAddress = AL.getICD().alGetSource3f;
      JNI.invokePPPV(source, param, v1, v2, v3, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetSource3f(
      @NativeType("ALuint") int source,
      @NativeType("ALenum") int param,
      @NativeType("ALfloat *") FloatBuffer v1,
      @NativeType("ALfloat *") FloatBuffer v2,
      @NativeType("ALfloat *") FloatBuffer v3
   ) {
      if (Checks.CHECKS) {
         Checks.check(v1, 1);
         Checks.check(v2, 1);
         Checks.check(v3, 1);
      }

      nalGetSource3f(source, param, MemoryUtil.memAddress(v1), MemoryUtil.memAddress(v2), MemoryUtil.memAddress(v3));
   }

   public static void nalGetSourcefv(int source, int param, long values) {
      long __functionAddress = AL.getICD().alGetSourcefv;
      JNI.invokePV(source, param, values, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetSourcefv(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALfloat *") FloatBuffer values) {
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      nalGetSourcefv(source, param, MemoryUtil.memAddress(values));
   }

   public static void nalGetSourcei(int source, int param, long value) {
      long __functionAddress = AL.getICD().alGetSourcei;
      JNI.invokePV(source, param, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetSourcei(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALint *") IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nalGetSourcei(source, param, MemoryUtil.memAddress(value));
   }

   @NativeType("ALvoid")
   public static int alGetSourcei(@NativeType("ALuint") int source, @NativeType("ALenum") int param) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer value = stack.callocInt(1);
         nalGetSourcei(source, param, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void nalGetSourceiv(int source, int param, long values) {
      long __functionAddress = AL.getICD().alGetSourceiv;
      JNI.invokePV(source, param, values, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetSourceiv(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALint *") IntBuffer values) {
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      nalGetSourceiv(source, param, MemoryUtil.memAddress(values));
   }

   public static void nalSourceQueueBuffers(int sourceName, int numBuffers, long bufferNames) {
      long __functionAddress = AL.getICD().alSourceQueueBuffers;
      JNI.invokePV(sourceName, numBuffers, bufferNames, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourceQueueBuffers(@NativeType("ALuint") int sourceName, @NativeType("ALuint *") IntBuffer bufferNames) {
      nalSourceQueueBuffers(sourceName, bufferNames.remaining(), MemoryUtil.memAddress(bufferNames));
   }

   @NativeType("ALvoid")
   public static void alSourceQueueBuffers(@NativeType("ALuint") int sourceName, @NativeType("ALuint *") int bufferName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer bufferNames = stack.ints(bufferName);
         nalSourceQueueBuffers(sourceName, 1, MemoryUtil.memAddress(bufferNames));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void nalSourceUnqueueBuffers(int sourceName, int numEntries, long bufferNames) {
      long __functionAddress = AL.getICD().alSourceUnqueueBuffers;
      JNI.invokePV(sourceName, numEntries, bufferNames, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourceUnqueueBuffers(@NativeType("ALuint") int sourceName, @NativeType("ALuint *") IntBuffer bufferNames) {
      nalSourceUnqueueBuffers(sourceName, bufferNames.remaining(), MemoryUtil.memAddress(bufferNames));
   }

   @NativeType("ALvoid")
   public static int alSourceUnqueueBuffers(@NativeType("ALuint") int sourceName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer bufferNames = stack.callocInt(1);
         nalSourceUnqueueBuffers(sourceName, 1, MemoryUtil.memAddress(bufferNames));
         return bufferNames.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("ALvoid")
   public static void alSourcePlay(@NativeType("ALuint") int source) {
      long __functionAddress = AL.getICD().alSourcePlay;
      JNI.invokeV(source, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourcePause(@NativeType("ALuint") int source) {
      long __functionAddress = AL.getICD().alSourcePause;
      JNI.invokeV(source, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourceStop(@NativeType("ALuint") int source) {
      long __functionAddress = AL.getICD().alSourceStop;
      JNI.invokeV(source, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourceRewind(@NativeType("ALuint") int source) {
      long __functionAddress = AL.getICD().alSourceRewind;
      JNI.invokeV(source, __functionAddress);
   }

   public static void nalSourcePlayv(int n, long sources) {
      long __functionAddress = AL.getICD().alSourcePlayv;
      JNI.invokePV(n, sources, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourcePlayv(@NativeType("ALuint const *") IntBuffer sources) {
      nalSourcePlayv(sources.remaining(), MemoryUtil.memAddress(sources));
   }

   public static void nalSourcePausev(int n, long sources) {
      long __functionAddress = AL.getICD().alSourcePausev;
      JNI.invokePV(n, sources, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourcePausev(@NativeType("ALuint const *") IntBuffer sources) {
      nalSourcePausev(sources.remaining(), MemoryUtil.memAddress(sources));
   }

   public static void nalSourceStopv(int n, long sources) {
      long __functionAddress = AL.getICD().alSourceStopv;
      JNI.invokePV(n, sources, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourceStopv(@NativeType("ALuint const *") IntBuffer sources) {
      nalSourceStopv(sources.remaining(), MemoryUtil.memAddress(sources));
   }

   public static void nalSourceRewindv(int n, long sources) {
      long __functionAddress = AL.getICD().alSourceRewindv;
      JNI.invokePV(n, sources, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourceRewindv(@NativeType("ALuint const *") IntBuffer sources) {
      nalSourceRewindv(sources.remaining(), MemoryUtil.memAddress(sources));
   }

   public static void nalGenBuffers(int n, long bufferNames) {
      long __functionAddress = AL.getICD().alGenBuffers;
      JNI.invokePV(n, bufferNames, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGenBuffers(@NativeType("ALuint *") IntBuffer bufferNames) {
      nalGenBuffers(bufferNames.remaining(), MemoryUtil.memAddress(bufferNames));
   }

   @NativeType("ALvoid")
   public static int alGenBuffers() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer bufferNames = stack.callocInt(1);
         nalGenBuffers(1, MemoryUtil.memAddress(bufferNames));
         return bufferNames.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void nalDeleteBuffers(int n, long bufferNames) {
      long __functionAddress = AL.getICD().alDeleteBuffers;
      JNI.invokePV(n, bufferNames, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alDeleteBuffers(@NativeType("ALuint const *") IntBuffer bufferNames) {
      nalDeleteBuffers(bufferNames.remaining(), MemoryUtil.memAddress(bufferNames));
   }

   @NativeType("ALvoid")
   public static void alDeleteBuffers(@NativeType("ALuint const *") int bufferName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer bufferNames = stack.ints(bufferName);
         nalDeleteBuffers(1, MemoryUtil.memAddress(bufferNames));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("ALboolean")
   public static boolean alIsBuffer(@NativeType("ALuint") int bufferName) {
      long __functionAddress = AL.getICD().alIsBuffer;
      return JNI.invokeZ(bufferName, __functionAddress);
   }

   public static void nalGetBufferf(int bufferName, int paramName, long value) {
      long __functionAddress = AL.getICD().alGetBufferf;
      JNI.invokePV(bufferName, paramName, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetBufferf(@NativeType("ALuint") int bufferName, @NativeType("ALenum") int paramName, @NativeType("ALfloat *") FloatBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nalGetBufferf(bufferName, paramName, MemoryUtil.memAddress(value));
   }

   @NativeType("ALvoid")
   public static float alGetBufferf(@NativeType("ALuint") int bufferName, @NativeType("ALenum") int paramName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer value = stack.callocFloat(1);
         nalGetBufferf(bufferName, paramName, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void nalGetBufferi(int bufferName, int paramName, long value) {
      long __functionAddress = AL.getICD().alGetBufferi;
      JNI.invokePV(bufferName, paramName, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetBufferi(@NativeType("ALuint") int bufferName, @NativeType("ALenum") int paramName, @NativeType("ALint *") IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nalGetBufferi(bufferName, paramName, MemoryUtil.memAddress(value));
   }

   @NativeType("ALvoid")
   public static int alGetBufferi(@NativeType("ALuint") int bufferName, @NativeType("ALenum") int paramName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer value = stack.callocInt(1);
         nalGetBufferi(bufferName, paramName, MemoryUtil.memAddress(value));
         return value.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void nalBufferData(int bufferName, int format, long data, int size, int frequency) {
      long __functionAddress = AL.getICD().alBufferData;
      JNI.invokePV(bufferName, format, data, size, frequency, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alBufferData(
      @NativeType("ALuint") int bufferName,
      @NativeType("ALenum") int format,
      @NativeType("ALvoid const *") ByteBuffer data,
      @NativeType("ALsizei") int frequency
   ) {
      nalBufferData(bufferName, format, MemoryUtil.memAddress(data), data.remaining(), frequency);
   }

   @NativeType("ALvoid")
   public static void alBufferData(
      @NativeType("ALuint") int bufferName,
      @NativeType("ALenum") int format,
      @NativeType("ALvoid const *") ShortBuffer data,
      @NativeType("ALsizei") int frequency
   ) {
      nalBufferData(bufferName, format, MemoryUtil.memAddress(data), data.remaining() << 1, frequency);
   }

   @NativeType("ALvoid")
   public static void alBufferData(
      @NativeType("ALuint") int bufferName,
      @NativeType("ALenum") int format,
      @NativeType("ALvoid const *") IntBuffer data,
      @NativeType("ALsizei") int frequency
   ) {
      nalBufferData(bufferName, format, MemoryUtil.memAddress(data), data.remaining() << 2, frequency);
   }

   @NativeType("ALvoid")
   public static void alBufferData(
      @NativeType("ALuint") int bufferName,
      @NativeType("ALenum") int format,
      @NativeType("ALvoid const *") FloatBuffer data,
      @NativeType("ALsizei") int frequency
   ) {
      nalBufferData(bufferName, format, MemoryUtil.memAddress(data), data.remaining() << 2, frequency);
   }

   public static int nalGetEnumValue(long enumName) {
      long __functionAddress = AL.getICD().alGetEnumValue;
      return JNI.invokePI(enumName, __functionAddress);
   }

   @NativeType("ALuint")
   public static int alGetEnumValue(@NativeType("ALchar const *") ByteBuffer enumName) {
      if (Checks.CHECKS) {
         Checks.checkNT1(enumName);
      }

      return nalGetEnumValue(MemoryUtil.memAddress(enumName));
   }

   @NativeType("ALuint")
   public static int alGetEnumValue(@NativeType("ALchar const *") CharSequence enumName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(enumName, true);
         long enumNameEncoded = stack.getPointerAddress();
         return nalGetEnumValue(enumNameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static long nalGetProcAddress(long funcName) {
      long __functionAddress = AL.getICD().alGetProcAddress;
      return JNI.invokePP(funcName, __functionAddress);
   }

   @NativeType("void *")
   public static long alGetProcAddress(@NativeType("ALchar const *") ByteBuffer funcName) {
      if (Checks.CHECKS) {
         Checks.checkNT1(funcName);
      }

      return nalGetProcAddress(MemoryUtil.memAddress(funcName));
   }

   @NativeType("void *")
   public static long alGetProcAddress(@NativeType("ALchar const *") CharSequence funcName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(funcName, true);
         long funcNameEncoded = stack.getPointerAddress();
         return nalGetProcAddress(funcNameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static boolean nalIsExtensionPresent(long extName) {
      long __functionAddress = AL.getICD().alIsExtensionPresent;
      return JNI.invokePZ(extName, __functionAddress);
   }

   @NativeType("ALCboolean")
   public static boolean alIsExtensionPresent(@NativeType("ALchar const *") ByteBuffer extName) {
      if (Checks.CHECKS) {
         Checks.checkNT1(extName);
      }

      return nalIsExtensionPresent(MemoryUtil.memAddress(extName));
   }

   @NativeType("ALCboolean")
   public static boolean alIsExtensionPresent(@NativeType("ALchar const *") CharSequence extName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(extName, true);
         long extNameEncoded = stack.getPointerAddress();
         return nalIsExtensionPresent(extNameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("ALvoid")
   public static void alGetIntegerv(@NativeType("ALenum") int paramName, @NativeType("ALint *") int[] dest) {
      long __functionAddress = AL.getICD().alGetIntegerv;
      if (Checks.CHECKS) {
         Checks.check(dest, 1);
      }

      JNI.invokePV(paramName, dest, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetFloatv(@NativeType("ALenum") int paramName, @NativeType("ALfloat *") float[] dest) {
      long __functionAddress = AL.getICD().alGetFloatv;
      if (Checks.CHECKS) {
         Checks.check(dest, 1);
      }

      JNI.invokePV(paramName, dest, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetDoublev(@NativeType("ALenum") int paramName, @NativeType("ALdouble *") double[] dest) {
      long __functionAddress = AL.getICD().alGetDoublev;
      if (Checks.CHECKS) {
         Checks.check(dest, 1);
      }

      JNI.invokePV(paramName, dest, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alListenerfv(@NativeType("ALenum") int paramName, @NativeType("ALfloat const *") float[] values) {
      long __functionAddress = AL.getICD().alListenerfv;
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      JNI.invokePV(paramName, values, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetListenerf(@NativeType("ALenum") int paramName, @NativeType("ALfloat *") float[] value) {
      long __functionAddress = AL.getICD().alGetListenerf;
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      JNI.invokePV(paramName, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetListeneri(@NativeType("ALenum") int paramName, @NativeType("ALint *") int[] value) {
      long __functionAddress = AL.getICD().alGetListeneri;
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      JNI.invokePV(paramName, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetListener3f(
      @NativeType("ALenum") int paramName,
      @NativeType("ALfloat *") float[] value1,
      @NativeType("ALfloat *") float[] value2,
      @NativeType("ALfloat *") float[] value3
   ) {
      long __functionAddress = AL.getICD().alGetListener3f;
      if (Checks.CHECKS) {
         Checks.check(value1, 1);
         Checks.check(value2, 1);
         Checks.check(value3, 1);
      }

      JNI.invokePPPV(paramName, value1, value2, value3, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetListenerfv(@NativeType("ALenum") int paramName, @NativeType("ALfloat *") float[] values) {
      long __functionAddress = AL.getICD().alGetListenerfv;
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      JNI.invokePV(paramName, values, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGenSources(@NativeType("ALuint *") int[] srcNames) {
      long __functionAddress = AL.getICD().alGenSources;
      JNI.invokePV(srcNames.length, srcNames, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alDeleteSources(@NativeType("ALuint *") int[] sources) {
      long __functionAddress = AL.getICD().alDeleteSources;
      JNI.invokePV(sources.length, sources, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourcefv(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALfloat const *") float[] values) {
      long __functionAddress = AL.getICD().alSourcefv;
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      JNI.invokePV(source, param, values, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetSourcef(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALfloat *") float[] value) {
      long __functionAddress = AL.getICD().alGetSourcef;
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      JNI.invokePV(source, param, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetSource3f(
      @NativeType("ALuint") int source,
      @NativeType("ALenum") int param,
      @NativeType("ALfloat *") float[] v1,
      @NativeType("ALfloat *") float[] v2,
      @NativeType("ALfloat *") float[] v3
   ) {
      long __functionAddress = AL.getICD().alGetSource3f;
      if (Checks.CHECKS) {
         Checks.check(v1, 1);
         Checks.check(v2, 1);
         Checks.check(v3, 1);
      }

      JNI.invokePPPV(source, param, v1, v2, v3, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetSourcefv(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALfloat *") float[] values) {
      long __functionAddress = AL.getICD().alGetSourcefv;
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      JNI.invokePV(source, param, values, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetSourcei(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALint *") int[] value) {
      long __functionAddress = AL.getICD().alGetSourcei;
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      JNI.invokePV(source, param, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetSourceiv(@NativeType("ALuint") int source, @NativeType("ALenum") int param, @NativeType("ALint *") int[] values) {
      long __functionAddress = AL.getICD().alGetSourceiv;
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      JNI.invokePV(source, param, values, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourceQueueBuffers(@NativeType("ALuint") int sourceName, @NativeType("ALuint *") int[] bufferNames) {
      long __functionAddress = AL.getICD().alSourceQueueBuffers;
      JNI.invokePV(sourceName, bufferNames.length, bufferNames, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourceUnqueueBuffers(@NativeType("ALuint") int sourceName, @NativeType("ALuint *") int[] bufferNames) {
      long __functionAddress = AL.getICD().alSourceUnqueueBuffers;
      JNI.invokePV(sourceName, bufferNames.length, bufferNames, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourcePlayv(@NativeType("ALuint const *") int[] sources) {
      long __functionAddress = AL.getICD().alSourcePlayv;
      JNI.invokePV(sources.length, sources, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourcePausev(@NativeType("ALuint const *") int[] sources) {
      long __functionAddress = AL.getICD().alSourcePausev;
      JNI.invokePV(sources.length, sources, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourceStopv(@NativeType("ALuint const *") int[] sources) {
      long __functionAddress = AL.getICD().alSourceStopv;
      JNI.invokePV(sources.length, sources, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alSourceRewindv(@NativeType("ALuint const *") int[] sources) {
      long __functionAddress = AL.getICD().alSourceRewindv;
      JNI.invokePV(sources.length, sources, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGenBuffers(@NativeType("ALuint *") int[] bufferNames) {
      long __functionAddress = AL.getICD().alGenBuffers;
      JNI.invokePV(bufferNames.length, bufferNames, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alDeleteBuffers(@NativeType("ALuint const *") int[] bufferNames) {
      long __functionAddress = AL.getICD().alDeleteBuffers;
      JNI.invokePV(bufferNames.length, bufferNames, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetBufferf(@NativeType("ALuint") int bufferName, @NativeType("ALenum") int paramName, @NativeType("ALfloat *") float[] value) {
      long __functionAddress = AL.getICD().alGetBufferf;
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      JNI.invokePV(bufferName, paramName, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alGetBufferi(@NativeType("ALuint") int bufferName, @NativeType("ALenum") int paramName, @NativeType("ALint *") int[] value) {
      long __functionAddress = AL.getICD().alGetBufferi;
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      JNI.invokePV(bufferName, paramName, value, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alBufferData(
      @NativeType("ALuint") int bufferName, @NativeType("ALenum") int format, @NativeType("ALvoid const *") short[] data, @NativeType("ALsizei") int frequency
   ) {
      long __functionAddress = AL.getICD().alBufferData;
      JNI.invokePV(bufferName, format, data, data.length << 1, frequency, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alBufferData(
      @NativeType("ALuint") int bufferName, @NativeType("ALenum") int format, @NativeType("ALvoid const *") int[] data, @NativeType("ALsizei") int frequency
   ) {
      long __functionAddress = AL.getICD().alBufferData;
      JNI.invokePV(bufferName, format, data, data.length << 2, frequency, __functionAddress);
   }

   @NativeType("ALvoid")
   public static void alBufferData(
      @NativeType("ALuint") int bufferName, @NativeType("ALenum") int format, @NativeType("ALvoid const *") float[] data, @NativeType("ALsizei") int frequency
   ) {
      long __functionAddress = AL.getICD().alBufferData;
      JNI.invokePV(bufferName, format, data, data.length << 2, frequency, __functionAddress);
   }
}
